/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.application.student.impl;

import com.google.common.collect.Lists;
import com.torch.SendMailUtils;
import com.torch.application.upload.PhotoPath;
import com.torch.domain.model.release.QReleaseStudent;
import com.torch.domain.model.release.ReleaseStudent;
import com.torch.domain.model.release.ReleaseStudentRepository;
import com.torch.domain.model.school.School;
import com.torch.domain.model.school.SchoolRepository;
import com.torch.domain.model.student.Student;
import com.torch.domain.model.student.StudentRepository;
import com.torch.domain.model.user.User;
import com.torch.domain.model.user.UserRepository;
import com.torch.interfaces.common.exceptions.TorchException;
import com.torch.interfaces.student.StudentDetail;
import com.torch.interfaces.student.StudentExportConfigEnum;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import javax.imageio.ImageIO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;


/**
 * @author yuanj 2017-01-18 14:31:25
 * @version 1.0
 */
@Service
public class StudentExcelServiceImpl {

  private final StudentRepository studentRepository;

  private final SchoolRepository schoolRepository;

  private final UserRepository userRepository;

  private final ReleaseStudentRepository releaseStudentRepository;


  @Autowired
  public StudentExcelServiceImpl(final StudentRepository studentRepository,
      final SchoolRepository schoolRepository,
      final UserRepository userRepository,
      final ReleaseStudentRepository releaseStudentRepository
  ) {
    this.studentRepository = studentRepository;
    this.schoolRepository = schoolRepository;
    this.userRepository = userRepository;
    this.releaseStudentRepository = releaseStudentRepository;
  }

  public StudentDetail getStudentDetail(Long id) {
    Student student = studentRepository.findOne(id);
    return toDetail(student);
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

  public void exportStudent(Long id, String email) throws Exception {
    InputStream inputStream = new FileInputStream(PhotoPath.EXCEL_TEMP + "学生信息备案表.xls");
    Workbook workbook = WorkbookFactory.create(inputStream);
    Sheet sheet = workbook.getSheet("sheet1");
    StudentDetail detail = getStudentDetail(id);
    sheet.getRow(StudentExportConfigEnum.S_NO.getRowIndex())
        .getCell(StudentExportConfigEnum.S_NO.getColumnIndex())
        .setCellValue(detail.getsNo() == null ? "" : detail.getsNo());

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

    String path = PhotoPath.NEW_EXCEL + new DateTime().getMillis() + ".xls";
    OutputStream out = new FileOutputStream(path);
    workbook.write(out);
    out.close();
    String[] tops = {email};
    JavaMailSenderImpl sender = SendMailUtils.initJavaMailSender();
    SendMailUtils.sendWithAttament(sender, tops, "学生资料导出", "", "学生资料导出.xls", path);
  }


  public void exportStudentPhoto(Long id, String email, String url) throws Exception {
    Student student = studentRepository.findOne(id);
    if (Objects.isNull(student)) {
      throw new TorchException("学生无效");
    }
    Long spId = student.getSponsorId();
    User sp = userRepository.findOne(spId == null ? 0l : spId);
    InputStream inputStream = new FileInputStream(PhotoPath.EXCEL_TEMP + "\\照片打印.xls");
    Workbook workbook = WorkbookFactory.create(inputStream);
    Sheet sheet = workbook.getSheet("sheet1");

    sheet.getRow(0)
        .getCell(1)
        .setCellValue(StringUtils.isBlank(student.getsNo()) ? "" : student.getsNo());
    sheet.getRow(0)
        .getCell(4)
        .setCellValue(StringUtils.isBlank(student.getName()) ? "" : student.getName());
    sheet.getRow(1)
        .getCell(1)
        .setCellValue((sp == null || StringUtils.isBlank(sp.getName())) ? "" : sp.getName());
    sheet.getRow(1)
        .getCell(4)
        .setCellValue((sp == null || StringUtils.isBlank(sp.getMobile())) ? "" : sp.getMobile());

//    if (StringUtils.isNotBlank(student.getHeadPhoto())) {
    URL u = new URL("http://" + url);
    Drawing patriarch = sheet.createDrawingPatriarch();
    insertImage(workbook, patriarch, getImageData(ImageIO.read(u)), 2, 0, 1);
//    }

    String path = PhotoPath.NEW_EXCEL + new DateTime().getMillis() + ".xls";
    OutputStream out = new FileOutputStream(path);
    workbook.write(out);
    out.close();
    String[] tops = {email};
    JavaMailSenderImpl sender = SendMailUtils.initJavaMailSender();
    SendMailUtils.sendWithAttament(sender, tops, "照片资料打印", "", "照片资料打印.xls", path);

  }


  //自定义的方法,插入某个图片到指定索引的位置
  private static void insertImage(Workbook wb, Drawing pa, byte[] data, int row, int column, int index) {
    int x1 = index * 250;
    int y1 = 0;
    int x2 = x1 + 255;
    int y2 = 255;
    HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 0, 0, (short) 0, 2, (short) 7, 30);
    anchor.setAnchorType(2);
    pa.createPicture(anchor, wb.addPicture(data, Workbook.PICTURE_TYPE_JPEG));
  }

  //从图片里面得到字节数组
  private static byte[] getImageData(BufferedImage bi) {
    try {
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      ImageIO.write(bi, "PNG", bout);
      return bout.toByteArray();
    } catch (Exception exe) {
      exe.printStackTrace();
      return null;
    }
  }


  /**
   * 承诺书导出
   */
  public void exportUndertaking(Long studentId, String email) throws Exception {
    Student student = studentRepository.findOne(studentId);
    if (Objects.isNull(student)) {
      throw new TorchException("结对受助人为空");
    }
    School school = schoolRepository.findOne(student.getSchoolId() == null ? 0l : student.getSchoolId());

    User user = userRepository.findOne(student.getSponsorId() == null ? 0l : student.getSponsorId());
    List<ReleaseStudent> res = (List<ReleaseStudent>) releaseStudentRepository
        .findAll(QReleaseStudent.releaseStudent.studentId.eq(studentId));

    InputStream inputStream = new FileInputStream(PhotoPath.EXCEL_TEMP + "\\受助人承诺书.xls");
//    InputStream inputStream = new FileInputStream( "D:\\受助人承诺书.xls");
    Workbook workbook = WorkbookFactory.create(inputStream);
    Sheet sheet = workbook.getSheet("sheet1");

    sheet.getRow(11)
        .getCell(1)
        .setCellValue(StringUtils.isBlank(student.getName()) ? "" : student.getName());
    sheet.getRow(11)
        .getCell(4)
        .setCellValue(StringUtils.isBlank(student.getIdentityCard()) ? "" : student.getIdentityCard());
    sheet.getRow(12)
        .getCell(1)
        .setCellValue((school == null || StringUtils.isBlank(school.getSchoolName())) ? "" : school.getSchoolName());

    sheet.getRow(12)
        .getCell(5)
        .setCellValue((StringUtils.isBlank(student.getGrade())) ? "" : student.getGrade());

    sheet.getRow(13)
        .getCell(1)
        .setCellValue((StringUtils.isBlank(student.getAddress())) ? "" : student.getAddress());

    sheet.getRow(14)
        .getCell(1)
        .setCellValue("");

    sheet.getRow(14)
        .getCell(5)
        .setCellValue("");

    //联系电话待维护

    sheet.getRow(17)
        .getCell(1)
        .setCellValue((user == null || StringUtils.isBlank(user.getName())) ? "" : user.getName());

    sheet.getRow(17)
        .getCell(4)
        .setCellValue((user == null || StringUtils.isBlank(user.getMobile())) ? "" : user.getMobile());

    sheet.getRow(18)
        .getCell(1)
        .setCellValue((user == null || StringUtils.isBlank(user.getAddress())) ? "" : user.getAddress());

    sheet.getRow(18)
        .getCell(6)
        .setCellValue((user == null || StringUtils.isBlank(user.getEmail())) ? "" : user.getEmail());

    sheet.getRow(40)
        .getCell(1)
        .setCellValue(new DateTime().toString("yyyy-MM-dd"));

    sheet.getRow(40)
        .getCell(4)
        .setCellValue(
            CollectionUtils.isEmpty(res) ? "" : res.get(0).getNeedMoney() + "");

    sheet.getRow(42)
        .getCell(3)
        .setCellValue(
            StringUtils.isBlank(student.getName()) ? "" : student.getName());

    String path = PhotoPath.NEW_EXCEL + new DateTime().getMillis() + ".xls";
//    String path = "D:\\" + new DateTime().getMillis() + ".xls";
    OutputStream out = new FileOutputStream(path);
    workbook.write(out);
    out.close();
    String[] tops = {email};
    JavaMailSenderImpl sender = SendMailUtils.initJavaMailSender();
    SendMailUtils.sendWithAttament(sender, tops, "受助人承诺书打印", "", "受助人承诺书打印.xls", path);

  }

  @Transient
  public void importStudent() throws Exception {

    InputStream inputStream = new FileInputStream("D:\\火炬助学\\torchbackend\\student.xlsx");
    Workbook workbook = WorkbookFactory.create(inputStream);
    Sheet sheet = workbook.getSheet("sheet1");
//    List<Student> students = Lists.newArrayList();
    for (int i = 2; i < 92; i++) {
      String sNo = String.valueOf(sheet.getRow(i).getCell(0).getStringCellValue());
//      String sNo="";
      String area = "三都水族自治县";
      String city = "普安县";
      String name = sheet.getRow(i).getCell(3).getStringCellValue();
      String gender = sheet.getRow(i).getCell(4).getStringCellValue();
      String nation = sheet.getRow(i).getCell(5).getStringCellValue();
      String identityCard = sheet.getRow(i).getCell(6).getStringCellValue();
      String attendSchool = sheet.getRow(i).getCell(7).getStringCellValue();
      Long schoolId = 0L;
      if (attendSchool.equals("交梨民族中学")) {
        schoolId = 38L;
      }
      if (attendSchool.equals("普安中学")) {
        schoolId = 40L;
      }
      String grade = "";
      String gradeCode = "";
      String clbum = "";
      String gradeN = sheet.getRow(i).getCell(8).getStringCellValue();
      if (gradeN.contains("七")) {
        grade = "七年级";
        gradeCode = "seven";
      }
      if (gradeN.contains("八")) {
        grade = "八年级";
        gradeCode = "eight";
      }
      if (gradeN.contains("九")) {
        grade = "九年级";
        gradeCode = "nine";
      }
      if (gradeN.contains("1")) {
        clbum = "1";
      }
      if (gradeN.contains("2")) {
        clbum = "2";
      }
      if (gradeN.contains("3")) {
        clbum = "3";
      }
      if (gradeN.contains("4")) {
        clbum = "4";
      }
      if (gradeN.contains("5")) {
        clbum = "5";
      }
      String address = sheet.getRow(i).getCell(9).getStringCellValue();
      String familyPhone = sheet.getRow(i).getCell(10).getStringCellValue();
      Boolean approval = true;
      Long sponsorId = 0L;
      Integer status = 0;

      Student student = new Student();
      student.setsNo(sNo);
      student.setArea(area);
      student.setCity(city);
      student.setName(name);
      student.setGender(gender);
      student.setNation(nation);
      student.setIdentityCard(identityCard);
      student.setAttendSchool(attendSchool);
      student.setSchoolId(schoolId);
      student.setGrade(grade);
      student.setGradeCode(gradeCode);
      student.setClbum(clbum);
      student.setAddress(address);
      student.setFamilyPhone(familyPhone);
      student.setApproval(approval);
      student.setSponsorId(sponsorId);
      student.setStatus(status);
      studentRepository.save(student);
    }
  }


}
