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
import com.torch.domain.model.contribute.Remittance;
import com.torch.domain.model.contribute.RemittanceRepository;
import com.torch.domain.model.homeVisit.HomeVisit;
import com.torch.domain.model.homeVisit.HomeVisitAuditItem;
import com.torch.domain.model.homeVisit.HomeVisitAuditItemRepository;
import com.torch.domain.model.homeVisit.HomeVisitRepository;
import com.torch.domain.model.release.Release;
import com.torch.domain.model.release.ReleaseRepository;
import com.torch.domain.model.student.Student;
import com.torch.domain.model.student.StudentRepository;
import com.torch.interfaces.common.exceptions.TorchException;
import com.torch.interfaces.common.security.Session;
import com.torch.interfaces.contribute.dto.CreateRemittanceDto;
import com.torch.interfaces.homeVisit.dto.CreateHomeVisitCommand;
import com.torch.util.cache.RedisUtils;
import java.beans.Transient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


  @Autowired
  public ContributeServiceImpl(final RedisUtils redisUtils,
      final ContributeRecordRepository contributeRecordRepository,
      final StudentRepository studentRepository,
      final ReleaseRepository releaseRepository,
      final RemittanceRepository remittanceRepository) {
    this.redisUtils = redisUtils;
    this.contributeRecordRepository = contributeRecordRepository;
    this.studentRepository = studentRepository;
    this.releaseRepository = releaseRepository;
    this.remittanceRepository = remittanceRepository;
  }

  /**
   * 认捐接口
   */
  @Override
  @Transient
  public synchronized void contribute(Long batchId, List<Long> studentIds) {
    if (contributeMap == null) {
      contributeMap = Maps.newConcurrentMap();
      Map<String, String> map = redisUtils.getMap(batchId.toString());
      contributeMap.putAll(map);
      if (isContributed()) {
        throw new TorchException("此批次学生已经全部认捐");
      }
    }
    studentIds.forEach(studentId -> {
      String contribute = contributeMap.get(studentId.toString());
      if (StringUtils.isNotBlank(contribute) && contribute.equals("0")) {
        contributeMap.put(studentId.toString(), "1");
        redisUtils.putKeys(batchId.toString(), studentId.toString(), "1");
        createContributedRecord(studentId, Session.getUserId(), batchId);
        updateStudentContribute(studentId, Session.getUserId());
      }
    });
    if (isContributed()) {
      contributeMap = null;
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
  public void createRemittance(CreateRemittanceDto dto) {
    Remittance remittance = Remittance.builder()
        .contributeId(dto.getContributeId())
        .remittanceMoney(dto.getRemittanceMoney())
        .remittanceTime(new DateTime())
        .studentId(dto.getStudentId())
        .remark(dto.getRemark())
        .build();
    remittanceRepository.save(remittance);
  }

  private boolean isContributed() {
    List<String> values = Lists.newArrayList();
    for (Map.Entry<String, String> entry : contributeMap.entrySet()) {
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
        .ContributeId(contributeId)
        .studentId(studentId)
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
}
