/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.application.release.impl;

import com.torch.application.release.ReleaseService;
import com.torch.application.school.SchoolService;
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
import com.torch.interfaces.common.security.Session;
import com.torch.interfaces.release.AddReleaseCommand;
import com.torch.interfaces.release.AddReleaseStudentCommand;
import com.torch.interfaces.school.AddSchoolCommand;
import com.torch.interfaces.school.UpdateSchoolCommand;
import java.util.List;
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

  @Autowired
  public ReleaseServiceImpl(final ReleaseRepository releaseRepository,
      final ReleaseStudentRepository releaseStudentRepository,
      final StudentRepository studentRepository) {
    this.releaseRepository = releaseRepository;
    this.releaseStudentRepository = releaseStudentRepository;
    this.studentRepository = studentRepository;
  }

  @Override
  @Transient
  public Long addRelease(AddReleaseCommand command) {
    Release release = new Release();
    if (releaseRepository.count(QRelease.release.batchNo.eq(command.getBatchNo())) > 0) {
      throw new RuntimeException("批次重复");
    }
    BeanUtils.copyProperties(command, release);
    release.setCreateTime(new DateTime());
    release.setLastUpdateTime(new DateTime());
    releaseRepository.save(release);
    release.setStatus(0);
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
          throw new RuntimeException("已经发布不能删除");
        }
        student.setStatus(0);
        studentRepository.save(student);
      }
    }
  }

  @Override
  @Transient
  public void release(Long batchId, List<Long> releaseStudentIds) {
    releaseStudentIds.forEach(id -> {

      ReleaseStudent releaseStudent = releaseStudentRepository.findOne(id);
      if (releaseStudent != null) {
        releaseStudent.setStatus(4);
        releaseStudentRepository.save(releaseStudent);
        Student student = studentRepository.findOne(id);
        student.setStatus(4);
        studentRepository.save(student);
      }
      if(releaseStudentRepository.count(QReleaseStudent.releaseStudent.batchId.eq(batchId)
          .and(QReleaseStudent.releaseStudent.status.ne(4))) == 0){
        Release release =releaseRepository.findOne(batchId);
        release.setStatus(2);
        releaseRepository.save(release);
      }
    });
  }
}
