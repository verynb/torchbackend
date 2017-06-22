package com.torch.application.user.impl;

import com.torch.domain.model.user.QTeacherSchool;
import com.torch.domain.model.user.TeacherSchool;
import com.torch.domain.model.user.TeacherSchoolRepository;
import com.torch.interfaces.user.command.SponsorAddCommand;
import com.torch.interfaces.user.command.SponsorUpdateCommand;
import com.torch.interfaces.user.command.VolunteerAddCommand;
import com.torch.interfaces.user.command.VolunteerUpdateCommand;
import java.util.List;
import java.util.Optional;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.torch.application.user.UserService;
import com.torch.domain.model.user.User;
import com.torch.domain.model.user.UserRepository;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  @Autowired
  private TeacherSchoolRepository teacherSchoolRepository;

  @Autowired
  public UserServiceImpl(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Optional<User> authenticate(String username, String password) {

    final Optional<User> user = userRepository.getByMobile(username);
    if (!user.isPresent()) {
      return Optional.empty();
    }
    if (user.get().isValidPassword(password)) {
      return user;
    }
    return Optional.empty();
  }

  @Override
  public Long addVolunteer(VolunteerAddCommand command) {
    User user = new User();
    BeanUtils.copyProperties(command, user);
    user.setEncryptPassword(command.getPassword());
    user.setJoinTime(new DateTime(command.getJoinTime()));
    user.setCreateTime(new DateTime());
    user.setLastUpdateTime(new DateTime());
    user.setType(0);
    userRepository.save(user);
    if (CollectionUtils.isNotEmpty(command.getSchoolIds())) {
      command.getSchoolIds().forEach(schoolId -> {
        TeacherSchool ts = TeacherSchool.builder()
            .schoolId(schoolId)
            .techerId(user.getId())
            .build();
        teacherSchoolRepository.save(ts);
      });
    }
    return user.getId();
  }

  @Override
  public Long addSponsor(SponsorAddCommand command) {
    User user = new User();
    BeanUtils.copyProperties(command, user);
    user.setEncryptPassword(command.getPassword());
    user.setJoinTime(new DateTime(command.getJoinTime()));
//    user.setCreateTime(new DateTime());
//    user.setLastUpdateTime(new DateTime());
    user.setType(1);
    return userRepository.save(user).getId();
  }

  @Override
  public void updateVolunteer(VolunteerUpdateCommand command) {
    User user = userRepository.findOne(command.getId());
    user.setLastUpdateTime(new DateTime());
    user.setAddress(command.getAddress());
    user.setAge(command.getAge());
    user.setProvince(command.getProvince());
    user.setCity(command.getCity());
    user.setArea(command.getArea());
    user.setEmail(command.getEmail());
    user.setJoinTime(new DateTime(command.getJoinTime()));
    user.setMobile(command.getMobile());
    user.setName(command.getName());
    user.setNetName(command.getNetName());
    user.setPhoto(command.getPhoto());
    user.setQq(command.getQq());
    user.setRoleId(command.getRoleId());
    userRepository.save(user);
    if (CollectionUtils.isNotEmpty(command.getSchoolIds())) {
      List<TeacherSchool> tss = (List<TeacherSchool>) teacherSchoolRepository
          .findAll(QTeacherSchool.teacherSchool.techerId.eq(user.getId()));
      tss.forEach(ts -> {
        teacherSchoolRepository.delete(ts);
      });
      command.getSchoolIds().forEach(schoolId -> {
        teacherSchoolRepository.save(TeacherSchool.builder()
            .techerId(user.getId())
            .schoolId(schoolId)
            .build());
      });
    }
  }

  @Override
  public void updateSponsor(SponsorUpdateCommand command) {
    User user = userRepository.findOne(command.getId());
    user.setLastUpdateTime(new DateTime());
    user.setAddress(command.getAddress());
    user.setAge(command.getAge());
    user.setProvince(command.getProvince());
    user.setCity(command.getCity());
    user.setArea(command.getArea());
    user.setEmail(command.getEmail());
    user.setJoinTime(new DateTime(command.getJoinTime()));
    user.setMobile(command.getMobile());
    user.setName(command.getName());
    user.setNetName(command.getNetName());
    user.setPhoto(command.getPhoto());
    user.setQq(command.getQq());
    user.setRoleId(command.getRoleId());
    userRepository.save(user);
  }

}
