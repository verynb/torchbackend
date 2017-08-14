/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.application.release.impl;

import com.torch.application.release.ReleaseService;
import com.torch.application.school.SchoolService;
import com.torch.domain.model.homeVisit.HomeVisit;
import com.torch.domain.model.homeVisit.HomeVisitRepository;
import com.torch.domain.model.homeVisit.QHomeVisit;
import com.torch.domain.model.release.CreditRepository;
import com.torch.domain.model.release.Creditcredit;
import com.torch.domain.model.release.QRelease;
import com.torch.domain.model.release.QReleaseStudent;
import com.torch.domain.model.release.Release;
import com.torch.domain.model.release.ReleaseRepository;
import com.torch.domain.model.release.ReleaseStudent;
import com.torch.domain.model.release.ReleaseStudentRepository;
import com.torch.domain.model.school.School;
import com.torch.domain.model.school.SchoolRepository;
import com.torch.domain.model.student.Student;
import com.torch.domain.model.student.StudentRepository;
import com.torch.interfaces.common.exceptions.TorchException;
import com.torch.interfaces.common.security.Session;
import com.torch.interfaces.release.AddReleaseCommand;
import com.torch.interfaces.release.AddReleaseStudentCommand;
import com.torch.interfaces.release.CreateCreditDto;
import com.torch.interfaces.release.ReleaseStudentDto;
import com.torch.interfaces.release.UpdateReleaseCommand;
import com.torch.interfaces.school.AddSchoolCommand;
import com.torch.interfaces.school.UpdateSchoolCommand;
import com.torch.util.cache.RedisUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

/**
 * @author yuanj 2017-01-18 14:31:25
 * @version 1.0
 */
@Service
public class ReleaseServiceImpl implements ReleaseService {

  private final ReleaseRepository releaseRepository;

  private final ReleaseStudentRepository releaseStudentRepository;

  private final StudentRepository studentRepository;

  private final RedisUtils redisUtils;

  private final HomeVisitRepository homeVisitRepository;

  private final CreditRepository creditRepository;

  @Autowired
  public ReleaseServiceImpl(final ReleaseRepository releaseRepository,
      final ReleaseStudentRepository releaseStudentRepository,
      final StudentRepository studentRepository,
      final RedisUtils redisUtils,
      final HomeVisitRepository homeVisitRepository,
      final CreditRepository creditRepository) {
    this.releaseRepository = releaseRepository;
    this.releaseStudentRepository = releaseStudentRepository;
    this.studentRepository = studentRepository;
    this.redisUtils = redisUtils;
    this.homeVisitRepository = homeVisitRepository;
    this.creditRepository = creditRepository;
  }

  @Override
  @Transient
  public Long addRelease(AddReleaseCommand command) {
    Release release = new Release();
    if (releaseRepository.count(QRelease.release.batchNo.eq(command.getBatchNo())) > 0) {
      throw new TorchException("批次重复");
    }
    BeanUtils.copyProperties(command, release);
    release.setCreateTime(new DateTime());
    release.setLastUpdateTime(new DateTime());
    release.setStatus(0);
    releaseRepository.save(release);
    return release.getId();
  }

  @Override
  @Transient
  public Long updateRelease(UpdateReleaseCommand command) {
    Release release = releaseRepository.findOne(command.getId());

    if (release == null || release.getStatus() == null || (release.getStatus() != 0 && release.getStatus() != 1)) {
      throw new TorchException("批次已发布不能修改");
    }
    long count = releaseStudentRepository.count(QReleaseStudent.releaseStudent.batchId.eq(command.getId()));
    if(count>0){
      throw new TorchException("该批次下有学生,暂不能修改");
    }
    release.setCity(command.getCity());
    release.setProvince(command.getProvince());
    releaseRepository.save(release);
    return release.getId();
  }

  /**
   * 预发布学生信息
   */
  @Override
  @Transient
  public void addReleaseStudent(List<AddReleaseStudentCommand> commands) {
    if (CollectionUtils.isNotEmpty(commands)) {
      commands.forEach(command -> {
        ReleaseStudent releaseStudent = new ReleaseStudent();
        BeanUtils.copyProperties(command, releaseStudent);
        releaseStudent.setOperatorId(Session.getUserId());
        releaseStudent.setOperatorName(Session.getUsername());
        releaseStudentRepository.save(releaseStudent);
        Student student = studentRepository.findOne(command.getStudentId());
        if (student != null) {
          student.setStatus(3);
          studentRepository.save(student);
        }
      });
    }
  }

  @Override
  @Transient
  public void deleteReleaseStudent(Long id) {
    ReleaseStudent releaseStudent = releaseStudentRepository.findOne(id);
    if (!Objects.isNull(releaseStudent)) {
      releaseStudentRepository.delete(releaseStudent);
      Student student = studentRepository.findOne(releaseStudent.getStudentId());
      if (student != null) {
        if (student.getStatus() >= 4) {
          throw new TorchException("已经发布不能删除");
        }
        student.setStatus(0);
        studentRepository.save(student);
      }
    }
  }

  @Override
  @Transient
  public void release(Long batchId, List<ReleaseStudentDto> releaseStudentIds) {

    releaseStudentIds.forEach(id -> {
      ReleaseStudent releaseStudent = releaseStudentRepository.findOne(id.getReleaseStudentId());
      if (releaseStudent != null) {
        if (id.getNeedMoney() <= 0) {
          throw new TorchException("请确认好发布学生所需金额");
        }
        Long count = homeVisitRepository.count(QHomeVisit.homeVisit.batchId.eq(batchId)
            .and(QHomeVisit.homeVisit.studentId.eq(releaseStudent.getStudentId())));
        if (count <= 0) {
          throw new TorchException("请确认好发布学生是否做过家纺");
        }
        releaseStudent.setNeedMoney(id.getNeedMoney());
        releaseStudent.setStatus(4);
        releaseStudent.setRemark(id.getRemark());
        releaseStudentRepository.save(releaseStudent);
        Student student = studentRepository.findOne(releaseStudent.getStudentId());
        student.setStatus(4);
        studentRepository.save(student);
      }
    });

    if (releaseStudentRepository.count(QReleaseStudent.releaseStudent.batchId.eq(batchId)
        .and(QReleaseStudent.releaseStudent.status.ne(4))) == 0) {
      Release release = releaseRepository.findOne(batchId);
      release.setLastUpdateTime(new DateTime());
      release.setStatus(2);
      releaseRepository.save(release);
      //查询此批次所有已经发布的学生
      List<ReleaseStudent> rss = (List<ReleaseStudent>) releaseStudentRepository
          .findAll(QReleaseStudent.releaseStudent.batchId.eq(batchId));
      Map<String, String> map = new HashMap<String, String>();
      rss.forEach(rs -> {
        map.put(rs.getStudentId().toString(), "0");
      });
      redisUtils.putMap(batchId.toString(), map);
    }
  }

  @Override
  @Transient
  public void createCredit(CreateCreditDto dto) {
    Student student = studentRepository.findOne(dto.getStudentId());
    if (student == null) {
      throw new TorchException("当前学生不存在");
    }
    Creditcredit creditcre = Creditcredit.builder()
        .studentId(dto.getStudentId())
        .address(student.getAddress())
        .age(student.getAge())
        .area(student.getArea())
        .birthday(student.getBirthday())
        .city(student.getCity())
        .gender(student.getGender())
        .grade(student.getGrade())
        .gradeCode(student.getGradeCode())
        .identityCard(student.getIdentityCard())
        .name(student.getName())
        .province(student.getProvince())
        .schoolId(student.getSchoolId())
        .status(student.getStatus())
        .creditTime(dto.toDatetime())
        .money(dto.getMoney())
        .sponsorId(dto.getSponsorId())
        .build();
    creditRepository.save(creditcre);
  }
}
