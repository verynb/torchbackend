/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.application.contribute.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.torch.application.contribute.ContributeService;
import com.torch.application.homeVisit.HomeVisitService;
import com.torch.domain.model.contribute.ContributeRecord;
import com.torch.domain.model.contribute.ContributeRecordRepository;
import com.torch.domain.model.contribute.QContributeRecord;
import com.torch.domain.model.contribute.Remittance;
import com.torch.domain.model.contribute.RemittanceRepository;
import com.torch.domain.model.homeVisit.HomeVisit;
import com.torch.domain.model.homeVisit.HomeVisitAuditItem;
import com.torch.domain.model.homeVisit.HomeVisitAuditItemRepository;
import com.torch.domain.model.homeVisit.HomeVisitRepository;
import com.torch.domain.model.release.QReleaseStudent;
import com.torch.domain.model.release.Release;
import com.torch.domain.model.release.ReleaseRepository;
import com.torch.domain.model.release.ReleaseStudent;
import com.torch.domain.model.release.ReleaseStudentRepository;
import com.torch.domain.model.student.Student;
import com.torch.domain.model.student.StudentRepository;
import com.torch.interfaces.common.exceptions.TorchException;
import com.torch.interfaces.common.security.Session;
import com.torch.interfaces.contribute.dto.CancelSubscribeDto;
import com.torch.interfaces.contribute.dto.CreateRemittanceDto;
import com.torch.interfaces.homeVisit.dto.CreateHomeVisitCommand;
import com.torch.util.cache.RedisUtils;

import java.beans.Transient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yuanj 2017-01-18 14:31:25
 * @version 1.0
 */
@Service
public class ContributeServiceImpl implements ContributeService {

    private Map<String, String> contributeMap = null;

    private final RedisUtils redisUtils;
    private final ContributeRecordRepository contributeRecordRepository;

    private final StudentRepository studentRepository;

    private final ReleaseRepository releaseRepository;

    private final RemittanceRepository remittanceRepository;

    private final ReleaseStudentRepository releaseStudentRepository;


    @Autowired
    public ContributeServiceImpl(final RedisUtils redisUtils,
                                 final ContributeRecordRepository contributeRecordRepository,
                                 final StudentRepository studentRepository,
                                 final ReleaseRepository releaseRepository,
                                 final RemittanceRepository remittanceRepository,
                                 final ReleaseStudentRepository releaseStudentRepository) {
        this.redisUtils = redisUtils;
        this.contributeRecordRepository = contributeRecordRepository;
        this.studentRepository = studentRepository;
        this.releaseRepository = releaseRepository;
        this.remittanceRepository = remittanceRepository;
        this.releaseStudentRepository = releaseStudentRepository;
    }

    /**
     * 认捐接口
     */
    @Override
    @Transient
    public synchronized void contribute(Long batchId, List<Long> studentIds) {
        Map<String, String> map = redisUtils.getMap(batchId.toString());
        if (isContributed(map)) {
            throw new TorchException("此批次学生已经全部认捐");
        }
        studentIds.forEach(studentId -> {
            String contribute = map.get(studentId.toString());
            if (StringUtils.isNotBlank(contribute) && contribute.equals("0")) {
                redisUtils.putKeys(batchId.toString(), studentId.toString(), "1");
                map.put(studentId.toString(), "1");
                createContributedRecord(studentId, Session.getUserId(), batchId);
                updateStudentContribute(studentId, Session.getUserId());
                updateReleaseStudent(studentId, batchId);
            } else {
                Student student = studentRepository.findOne(studentId);
                throw new TorchException(student == null ? "" : student.getName() + "已经认捐");
            }
        });
        if (isContributed(map)) {
            //更新发布状态
            Release release = releaseRepository.findOne(batchId);
            if (release != null) {
                release.setStatus(4);
                releaseRepository.save(release);
            }
        }
    }

    @Override
    @Transient
    public void cancelContribute(CancelSubscribeDto dto) {
        List<ContributeRecord> crs = (List<ContributeRecord>) contributeRecordRepository
                .findAll(QContributeRecord.contributeRecord.studentId.eq(dto.getStudentId()));
        Long bathId = 0l;
        for (ContributeRecord cr : crs) {
            cr.setAbleRemit(false);
            contributeRecordRepository.save(cr);
            bathId = cr.getBatchId();
        }
        Student student = studentRepository.findOne(dto.getStudentId());
        Release release = releaseRepository.findOne(bathId);
        if (release != null && release.getStatus() != 4) {
            student.setStatus(4);
            List<ReleaseStudent> rss = (List<ReleaseStudent>) releaseStudentRepository.findAll(QReleaseStudent.releaseStudent.batchId.eq(release.getId())
                    .and(QReleaseStudent.releaseStudent.studentId.eq(student.getId())));
            if (CollectionUtils.isNotEmpty(rss)) {
                rss.get(0).setStatus(4);
                releaseStudentRepository.save(rss.get(0));
                redisUtils.putKeys(release.getId().toString(), student.getId().toString(), "0");
            }
        } else {
            student.setStatus(0);
        }
        student.setSponsorId(0L);
        studentRepository.save(student);
    }

    @Override
    @Transient
    public void createRemittance(CreateRemittanceDto dto) {
        Remittance remittance = Remittance.builder()
                .contributeId(dto.getContributeId())
                .remittanceMoney(dto.getRemittanceMoney())
                .remittanceTime(new DateTime())
                .studentId(dto.getStudentId())
                .remark(dto.getRemark())
                .build();
        remittanceRepository.save(remittance);
        Student student = studentRepository.findOne(dto.getStudentId());
        if (!Objects.isNull(student)) {
            student.setStatus(6);
            studentRepository.save(student);
        }

    }

    private boolean isContributed(Map<String, String> map) {
        List<String> values = Lists.newArrayList();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            values.add(entry.getValue());
        }
        if (values.stream()
                .filter(value -> value.equals("0"))
                .count() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Transient
    private void createContributedRecord(Long studentId, Long contributeId, Long batchId) {
        ContributeRecord c = ContributeRecord.builder()
                .batchId(batchId)
                .contributeId(contributeId)
                .studentId(studentId)
                .ableRemit(true)
                .build();
        c.setCreateTime(new DateTime());
        contributeRecordRepository.save(c);
    }

    @Transient
    private void updateStudentContribute(Long studentId, Long contributeId) {
        Student student = studentRepository.findOne(studentId);
        if (student != null) {
            student.setSponsorId(contributeId);
            student.setStatus(5);
            studentRepository.save(student);
        }
    }

    @Transient
    private void updateReleaseStudent(Long studentId, Long bathId) {
        List<ReleaseStudent> releaseStudents = (List<ReleaseStudent>) releaseStudentRepository.findAll(
                QReleaseStudent.releaseStudent.batchId.eq(bathId).and(QReleaseStudent.releaseStudent.studentId.eq(studentId)));
        releaseStudents.forEach(releaseStudent -> {
            releaseStudent.setStatus(5);
            releaseStudentRepository.save(releaseStudent);
        });
    }

}
