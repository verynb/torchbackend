/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.application.school.impl;

import com.torch.application.school.SchoolService;
import com.torch.domain.model.school.School;
import com.torch.domain.model.school.SchoolRepository;
import com.torch.interfaces.school.AddSchoolCommand;
import com.torch.interfaces.school.UpdateSchoolCommand;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

/**
 * @author 杨乐 2017-01-18 14:31:25
 * @version 1.0
 */
@Service
public class SchoolServiceImpl implements SchoolService {

  private final SchoolRepository schoolRepository;

  @Autowired
  public SchoolServiceImpl(final SchoolRepository schoolRepository) {
    this.schoolRepository = schoolRepository;
  }

  @Override
  @Transient
  public School addSchool(AddSchoolCommand command) {
    School school = new School();
    BeanUtils.copyProperties(command, school);
    schoolRepository.save(school);
    return school;
  }

  @Override
  @Transient
  public School updateSchool(UpdateSchoolCommand command) {
    School school = new School();
    BeanUtils.copyProperties(command, school);
    schoolRepository.save(school);
    return school;
  }

  @Override
  public School getSchoolDetail(Long id) {
    return schoolRepository.findOne(id);
  }

  @Override
  public void deleteSchool(Long id) {
    schoolRepository.delete(id);
  }

}
