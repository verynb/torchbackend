/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.application.student.impl;

import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.torch.SendMailUtils;
import com.torch.application.student.StudentService;
import com.torch.domain.model.school.School;
import com.torch.domain.model.school.SchoolRepository;
import com.torch.domain.model.student.QStudent;
import com.torch.domain.model.student.Student;
import com.torch.domain.model.student.StudentRepository;
import com.torch.domain.model.user.User;
import com.torch.domain.model.user.UserRepository;
import com.torch.interfaces.common.exceptions.TorchException;
import com.torch.interfaces.student.AddStudentCommand;
import com.torch.interfaces.student.StudentDetail;
import com.torch.interfaces.student.StudentDetailDto;
import com.torch.interfaces.student.StudentExportConfigEnum;
import com.torch.interfaces.student.StudentSearchDto;
import com.torch.interfaces.student.UpdateStudentCommand;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSenderImpl;
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

  private final String EXPORT_TEMP = "D:\\学生信息备案表.xls";

  private final String PATH="D:\\data\\excel\\";

  @Autowired
  public StudentServiceImpl(final StudentRepository studentRepository,
      final SchoolRepository schoolRepository,
      final UserRepository userRepository) {
    this.studentRepository = studentRepository;
    this.schoolRepository = schoolRepository;
    this.userRepository = userRepository;
  }

  @Override
  @Transient
  public Student addStudent(AddStudentCommand command) {
    long count = studentRepository.count(QStudent.student.sNo.eq(command.getSNo()));
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
    if (before.getStatus() >= 4) {
      throw new TorchException("学生已经发布不能做修改！");
    }
    if (before.getSNo() != null && !before.getSNo().equals(command.getSNo())) {
      long count = studentRepository.count(QStudent.student.sNo.eq(command.getSNo()));
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
    List<StudentDetailDto> list = Lists.newArrayList();
    Pageable pageable = new PageRequest(currentPage, pageSize);
    Page<Student> page = studentRepository
        .findAll(dto == null ? null : dto.toPredicate(), pageable);
    if (CollectionUtils.isNotEmpty(page.getContent())) {
      page.getContent().forEach(student -> {
        list.add(toDetailDto(student));
      });
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

  public void exportStudent(Long id, String email) throws Exception {
    InputStream inputStream = new FileInputStream(EXPORT_TEMP);
    Workbook workbook = WorkbookFactory.create(inputStream);
    Sheet sheet = workbook.getSheet("sheet1");
    StudentDetail detail = getStudentDetail(id);
    sheet.getRow(StudentExportConfigEnum.S_NO.getRowIndex())
        .getCell(StudentExportConfigEnum.S_NO.getColumnIndex())
        .setCellValue(detail.getSNo() == null ? "" : detail.getSNo());

    sheet.getRow(StudentExportConfigEnum.NAME.getRowIndex())
        .getCell(StudentExportConfigEnum.NAME.getColumnIndex())
        .setCellValue(detail.getName() == null ? "" : detail.getName());

    sheet.getRow(StudentExportConfigEnum.HEIGHT.getRowIndex())
        .getCell(StudentExportConfigEnum.HEIGHT.getColumnIndex())
        .setCellValue(detail.getHeight() == null ? "" : detail.getHeight());

    sheet.getRow(StudentExportConfigEnum.WEIGHT.getRowIndex())
        .getCell(StudentExportConfigEnum.WEIGHT.getColumnIndex())
        .setCellValue(detail.getWeight() == null ? "" : detail.getWeight());

    sheet.getRow(StudentExportConfigEnum.GENDER.getRowIndex())
        .getCell(StudentExportConfigEnum.GENDER.getColumnIndex())
        .setCellValue(detail.getGender() == null ? "" : detail.getGender());

    sheet.getRow(StudentExportConfigEnum.BIRTHDAY.getRowIndex())
        .getCell(StudentExportConfigEnum.BIRTHDAY.getColumnIndex())
        .setCellValue(detail.getBirthday() == null ? "" : detail.getBirthday());

    sheet.getRow(StudentExportConfigEnum.AGE.getRowIndex())
        .getCell(StudentExportConfigEnum.AGE.getColumnIndex())
        .setCellValue(detail.getAge() == null ? "" : detail.getAge().toString());

    String province = detail.getProvince();
    String city = detail.getCity();
    String area = detail.getArea();

    if (StringUtils.isNotBlank(province) && StringUtils.isNotBlank(city) && StringUtils.isNotBlank(area)) {
      String s = province + city + area;
      sheet.getRow(StudentExportConfigEnum.AREA.getRowIndex())
          .getCell(StudentExportConfigEnum.AREA.getColumnIndex())
          .setCellValue(s);
    }

    sheet.getRow(StudentExportConfigEnum.IDENTITYCARD.getRowIndex())
        .getCell(StudentExportConfigEnum.IDENTITYCARD.getColumnIndex())
        .setCellValue(detail.getIdentityCard() == null ? "" : detail.getIdentityCard());

    sheet.getRow(StudentExportConfigEnum.ADDRESS.getRowIndex())
        .getCell(StudentExportConfigEnum.ADDRESS.getColumnIndex())
        .setCellValue(detail.getAddress() == null ? "" : detail.getAddress());

    sheet.getRow(StudentExportConfigEnum.SCHOOLNAME.getRowIndex())
        .getCell(StudentExportConfigEnum.SCHOOLNAME.getColumnIndex())
        .setCellValue(detail.getSchoolName() == null ? "" : detail.getSchoolName());

    sheet.getRow(StudentExportConfigEnum.NATION.getRowIndex())
        .getCell(StudentExportConfigEnum.NATION.getColumnIndex())
        .setCellValue(detail.getNation() == null ? "" : detail.getNation());

    sheet.getRow(StudentExportConfigEnum.GRADE.getRowIndex())
        .getCell(StudentExportConfigEnum.GRADE.getColumnIndex())
        .setCellValue(detail.getGrade() == null ? "" : detail.getGrade());

    sheet.getRow(StudentExportConfigEnum.CLBUM.getRowIndex())
        .getCell(StudentExportConfigEnum.CLBUM.getColumnIndex())
        .setCellValue(detail.getClbum() == null ? "" : detail.getClbum());

    sheet.getRow(StudentExportConfigEnum.CLASSTEACHER.getRowIndex())
        .getCell(StudentExportConfigEnum.CLASSTEACHER.getColumnIndex())
        .setCellValue(detail.getClassTeacher() == null ? "" : detail.getClassTeacher());

    sheet.getRow(StudentExportConfigEnum.OTHER.getRowIndex())
        .getCell(StudentExportConfigEnum.OTHER.getColumnIndex())
        .setCellValue(detail.getOther() == null ? "" : detail.getOther());

    String path=PATH+new DateTime().getMillis()+".xlsx";
    OutputStream out = new FileOutputStream(path);
    workbook.write(out);
    String[] tops = {email};
    JavaMailSenderImpl sender = SendMailUtils.initJavaMailSender();
    SendMailUtils.sendWithAttament(sender, tops, "学生资料导出", "", "学生资料导出", path);

  }
}
