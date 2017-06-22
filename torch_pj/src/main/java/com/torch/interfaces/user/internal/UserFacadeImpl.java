/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.user.internal;

import com.google.common.collect.Lists;
import com.torch.domain.model.school.School;
import com.torch.domain.model.school.SchoolRepository;
import com.torch.domain.model.user.DictVolunteerRole;
import com.torch.domain.model.user.DictVolunteerRoleRepository;
import com.torch.domain.model.user.QTeacherSchool;
import com.torch.domain.model.user.QUser;
import com.torch.domain.model.user.TeacherSchool;
import com.torch.domain.model.user.TeacherSchoolRepository;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.user.command.SponsorAddCommand;
import com.torch.interfaces.user.command.SponsorUpdateCommand;
import com.torch.interfaces.user.command.VolunteerAddCommand;
import com.torch.interfaces.user.command.VolunteerUpdateCommand;

import com.torch.interfaces.user.facade.dto.SponsorDetailDto;
import com.torch.interfaces.user.facade.dto.VolunteerDetailDto;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.torch.application.user.UserService;
import com.torch.domain.model.user.User;
import com.torch.domain.model.user.UserRepository;
import com.torch.interfaces.user.facade.UserFacade;
import com.torch.interfaces.user.facade.dto.UserAddDTO;
import com.torch.interfaces.user.internal.assembler.UserDTOAssembler;

/**
 * @author yuanj 2017-01-12 18:12:07
 * @version 1.0
 */
@Service
public class UserFacadeImpl implements UserFacade {

  private final DictVolunteerRoleRepository roleRepository;
  private final UserService userService;
  private final UserRepository userRepository;

  @Autowired
  private DictVolunteerRoleRepository dictVolunteerRoleRepository;

  @Autowired
  private TeacherSchoolRepository teacherSchoolRepository;

  @Autowired
  private SchoolRepository schoolRepository;

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  public UserFacadeImpl(final DictVolunteerRoleRepository roleRepository,
      final UserService userService,
      final UserRepository userRepository) {
    this.roleRepository = roleRepository;
    this.userService = userService;
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public UserAddDTO addVolunteer(VolunteerAddCommand command) {
    List<User> users = (List<User>) userRepository
        .findAll(QUser.user.mobile.eq(command.getMobile()));
    if (CollectionUtils.isNotEmpty(users)) {
      throw new RuntimeException("手机号重复");
    }
    if (!Objects.isNull(command.getRoleId())) {
      DictVolunteerRole role = dictVolunteerRoleRepository.findOne(command.getRoleId());
      if (role != null && role.getRoleCode().equals("teacher")) {
        if (CollectionUtils.isEmpty(command.getSchoolIds())) {
          throw new RuntimeException("请选择学校");
        }
      }
    }
    return UserAddDTO.builder()
        .id(userService.addVolunteer(command))
        .codeMessage(new CodeMessage())
        .build();
  }

  @Override
  @Transactional
  public void updateVolunteer(VolunteerUpdateCommand command) {
    userService.updateVolunteer(command);
  }

  @Override
  @Transactional
  public void deleteUser(Long id) {
    User user = userRepository.findOne(id);
    if (user != null) {
      userRepository.delete(id);
    }
    List<TeacherSchool> tss = (List<TeacherSchool>) teacherSchoolRepository
        .findAll(QTeacherSchool.teacherSchool.techerId.eq(id));
    tss.forEach(ts -> {
      teacherSchoolRepository.delete(ts);
    });
  }

  @Override
  public VolunteerDetailDto getUser(Long id) {
    User user = userRepository.findOne(id);
    DictVolunteerRole role = roleRepository
        .findOne(user.getRoleId() == null ? 0L : user.getRoleId());
    List<School> schools = Lists.newArrayList();
    List<TeacherSchool> tss = (List<TeacherSchool>) teacherSchoolRepository
        .findAll(QTeacherSchool.teacherSchool.techerId.eq(id));
    if (CollectionUtils.isNotEmpty(tss)) {
      tss.forEach(ts -> {
        School school = schoolRepository.findOne(ts.getSchoolId());
        if (school != null) {
          schools.add(school);
        }
      });
    }
    return UserDTOAssembler.toUserGetDTO(user, role, schools);
  }

  @Override
  public SponsorDetailDto getSponsorDetail(Long id) {
    User user = userRepository.findOne(id);
    return UserDTOAssembler.toSponsorDto(user);
  }

  @Override
  @Transactional
  public void updatePassword(Long id, String password) {
    User user = userRepository.findOne(id);
    user.setEncryptPassword(password);
    userRepository.save(user);
  }

  @Override
  @Transactional
  public UserAddDTO addSponsor(SponsorAddCommand command) {
    List<User> users = (List<User>) userRepository
        .findAll(QUser.user.mobile.eq(command.getMobile()));
    if (CollectionUtils.isNotEmpty(users)) {
      throw new RuntimeException("手机号重复");
    }
    return UserAddDTO.builder()
        .id(userService.addSponsor(command))
        .codeMessage(new CodeMessage())
        .build();
  }

  @Override
  @Transactional
  public void updateSponsor(SponsorUpdateCommand command) {
    userService.updateSponsor(command);
  }

}
