/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.application.student.impl;

import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.torch.application.student.StudentService;
import com.torch.domain.model.school.School;
import com.torch.domain.model.school.SchoolRepository;
import com.torch.domain.model.student.QStudent;
import com.torch.domain.model.student.Student;
import com.torch.domain.model.student.StudentRepository;
import com.torch.domain.model.user.*;
import com.torch.interfaces.common.exceptions.TorchException;
import com.torch.interfaces.common.security.Session;
import com.torch.interfaces.student.AddStudentCommand;
import com.torch.interfaces.student.StudentDetail;
import com.torch.interfaces.student.StudentDetailDto;
import com.torch.interfaces.student.StudentSearchDto;
import com.torch.interfaces.student.UpdateStudentCommand;

import java.util.List;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * @author yuanj 2017-01-18 14:31:25
 * @version 1.0
 */
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final SchoolRepository schoolRepository;

    private final UserRepository userRepository;

    private final DictVolunteerRoleRepository dictVolunteerRoleRepository;

    private final TeacherSchoolRepository teacherSchoolRepository;

    @Autowired
    public StudentServiceImpl(final StudentRepository studentRepository,
                              final SchoolRepository schoolRepository,
                              final UserRepository userRepository,
                              final DictVolunteerRoleRepository dictVolunteerRoleRepository,
                              final TeacherSchoolRepository teacherSchoolRepository) {
        this.studentRepository = studentRepository;
        this.schoolRepository = schoolRepository;
        this.userRepository = userRepository;
        this.dictVolunteerRoleRepository = dictVolunteerRoleRepository;
        this.teacherSchoolRepository = teacherSchoolRepository;
    }

    @Override
    @Transient
    public Student addStudent(AddStudentCommand command) {
        long count = studentRepository.count(QStudent.student.sNo.eq(command.getsNo()));
        if (count > 0) {
            throw new TorchException("编号重复");
        }
        Student student = new Student();
        BeanUtils.copyProperties(command, student);
        student.setStatus(0);
        studentRepository.save(student);
        return student;
    }

    @Override
    @Transient
    public Student updateStudent(UpdateStudentCommand command) {
        Student before = studentRepository.findOne(command.getId());
        User user = userRepository.findOne(Session.getUserId());
        DictVolunteerRole role = dictVolunteerRoleRepository.findOne(user == null ? 0l : user.getRoleId());
        if (role != null && role.getRoleCode().equals("teacher")) {
            if (before.getStatus() >= 4) {
                throw new TorchException("学生已经发布不能做修改！");
            }
        }
        if (before.getsNo() != null && !before.getsNo().equals(command.getsNo())) {
            long count = studentRepository.count(QStudent.student.sNo.eq(command.getsNo()));
            if (count > 0) {
                throw new TorchException("编号重复");
            }
        }
        Student student = new Student();
        BeanUtils.copyProperties(command, student);
        studentRepository.save(student);
        return student;
    }

    @Override
    public StudentDetail getStudentDetail(Long id) {
        Student student = studentRepository.findOne(id);
        return toDetail(student);
    }

    @Override
    public List<StudentDetailDto> getAllStudents(Integer pageSize, Integer currentPage,
                                                 String province, String city) {
        List<StudentDetailDto> list = Lists.newArrayList();
        Pageable pageable = null;
        if (pageSize != null && pageSize != 0 && currentPage != null && currentPage != 0) {
            pageable = new PageRequest(currentPage, pageSize);
        }
        BooleanBuilder conditions = new BooleanBuilder(QStudent.student.status.eq(0));
        if (StringUtils.isNotBlank(province)) {
            conditions.and(QStudent.student.province.eq(province));
        }
        if (StringUtils.isNotBlank(city)) {
            conditions.and(QStudent.student.city.eq(city));
        }
        Page<Student> page = studentRepository.findAll(conditions, pageable);
        if (CollectionUtils.isNotEmpty(page.getContent())) {
            page.getContent().forEach(student -> {
                list.add(toDetailDto(student));
            });
        }
        return list;
    }

    @Override
    public List<StudentDetailDto> filterStudents(Integer pageSize, Integer currentPage,
                                                 StudentSearchDto dto) {
        User user = userRepository.findOne(Session.getUserId());
        DictVolunteerRole role = dictVolunteerRoleRepository.findOne(user == null ? 0l : user.getRoleId());
        List<TeacherSchool> tschools=Lists.newArrayList();
        if (role != null && role.getRoleCode().equals("teacher")) {
            tschools = (List<TeacherSchool>) teacherSchoolRepository.findAll(QTeacherSchool.teacherSchool.techerId.eq(user.getId()));
        }
        List<StudentDetailDto> list = Lists.newArrayList();
        Pageable pageable = new PageRequest(currentPage, pageSize);
        Page<Student> page = studentRepository
                .findAll(dto == null ? null : dto.toPredicate(tschools), pageable);
        if (CollectionUtils.isNotEmpty(page.getContent())) {
            for(Student student:page.getContent()){
                list.add(toDetailDto(student));
            }
        }
        return list;
    }

    @Override
    @Transient
    public void deleteStudent(Long id) {
        studentRepository.delete(id);
    }


    private StudentDetail toDetail(Student student) {
        StudentDetail dto = new StudentDetail();
        if (!Objects.isNull(student)) {
            BeanUtils.copyProperties(student, dto);
            School school = schoolRepository
                    .findOne(student.getSchoolId() == null ? 0 : student.getSchoolId());
            dto.setSchoolName(school == null ? "" : school.getSchoolName());
            User user = userRepository
                    .findOne(student.getSponsorId() == null ? 0 : student.getSponsorId());
            dto.setSponsorName(user == null ? "" : user.getName());
            dto.setSponsorId(student.getSponsorId());
        }
        return dto;
    }

    private StudentDetailDto toDetailDto(Student student) {
        StudentDetailDto dto = new StudentDetailDto();
        if (!Objects.isNull(student)) {
            BeanUtils.copyProperties(student, dto);
            School school = schoolRepository
                    .findOne(student.getSchoolId() == null ? 0 : student.getSchoolId());
            dto.setSchoolName(school == null ? "" : school.getSchoolName());
            User user = userRepository
                    .findOne(student.getSponsorId() == null ? 0 : student.getSponsorId());
            dto.setSponsorName(user == null ? "" : user.getName());
        }
        return dto;
    }
}
