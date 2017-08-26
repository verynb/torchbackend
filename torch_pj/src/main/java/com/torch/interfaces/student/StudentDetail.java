/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.student;

import com.torch.interfaces.common.facade.dto.CodeMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author yj 2017-01-18 11:16:14
 * @version 1.0
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  @ApiModelProperty("CODE MESSAGE")
  private CodeMessage codeMessage;

  @ApiModelProperty(name = "学生ID", required = true, position = 1)
  private Long id;

  private String headPhoto;

  /**
   * 编号
   */
  @NotBlank
  private String sNo;

  /**
   * 姓名
   */
  @ApiModelProperty(name = "姓名", required = true, position = 2)
  private String name;

  /**
   * 名族
   */
  private String nation;
  /**
   * 性别
   */
  @ApiModelProperty(name = "性别", required = true, position = 3)
  private String gender;

  /**
   * 出生日期
   */
  @ApiModelProperty(name = "出生日期", required = true, position = 4)
  private String birthday;
  /**
   * 年龄
   */
  @ApiModelProperty(name = "年龄", required = true, position = 5)
  private Integer age;

  /**
   * 身高
   */
  private String height;

  /**
   * 体重
   */
  private String weight;

  /**
   * 班级
   */
  private String clbum;

  /**
   * 班主任
   */
  private String classTeacher;
  /**
   * 省份
   */
  @ApiModelProperty(name = "省份", required = true, position = 6)
  private String province;

  /**
   * 市
   */
  @ApiModelProperty(name = "市", required = true, position = 7)
  private String city;
  /**
   * 区
   */
  @ApiModelProperty(name = "区", required = true, position = 8)
  private String area;
  /**
   * 详细地址
   */
  @ApiModelProperty(name = "详细地址", required = true, position = 9)
  private String address;
  //身份证
  @ApiModelProperty(name = "身份证", required = true, position = 10)
  private String identityCard;
  //学校ID
  @ApiModelProperty(name = "学校ID", required = true, position = 11)
  private Long schoolId;

  @ApiModelProperty(name = "学校名称", required = true, position = 11)
  private String schoolName;

  //年级
  @ApiModelProperty(name = "年级", required = true, position = 12)
  private String grade;

  private String gradeCode;
  //当前资助人
  @ApiModelProperty(name = "当前资助人ID", required = true, position = 13)
  private Long sponsorId;

  @ApiModelProperty(name = "当前资助人名字", required = true, position = 13)
  private String sponsorName;

  /**
   * 0.正常
   * 1.结案
   * 2.冻结
   */
  @ApiModelProperty(name = "状态{0.正常,1.结案,2.冻结}", required = true, position = 14)
  private Integer status;

  private String other;

  public CodeMessage getCodeMessage() {
    return codeMessage;
  }

  public void setCodeMessage(CodeMessage codeMessage) {
    this.codeMessage = codeMessage;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getHeadPhoto() {
    return headPhoto;
  }

  public void setHeadPhoto(String headPhoto) {
    this.headPhoto = headPhoto;
  }

  public String getsNo() {
    return sNo;
  }

  public void setsNo(String sNo) {
    this.sNo = sNo;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNation() {
    return nation;
  }

  public void setNation(String nation) {
    this.nation = nation;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getHeight() {
    return height;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public String getWeight() {
    return weight;
  }

  public void setWeight(String weight) {
    this.weight = weight;
  }

  public String getClbum() {
    return clbum;
  }

  public void setClbum(String clbum) {
    this.clbum = clbum;
  }

  public String getClassTeacher() {
    return classTeacher;
  }

  public void setClassTeacher(String classTeacher) {
    this.classTeacher = classTeacher;
  }

  public String getProvince() {
    return province;
  }

  public void setProvince(String province) {
    this.province = province;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getIdentityCard() {
    return identityCard;
  }

  public void setIdentityCard(String identityCard) {
    this.identityCard = identityCard;
  }

  public Long getSchoolId() {
    return schoolId;
  }

  public void setSchoolId(Long schoolId) {
    this.schoolId = schoolId;
  }

  public String getSchoolName() {
    return schoolName;
  }

  public void setSchoolName(String schoolName) {
    this.schoolName = schoolName;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public String getGradeCode() {
    return gradeCode;
  }

  public void setGradeCode(String gradeCode) {
    this.gradeCode = gradeCode;
  }

  public Long getSponsorId() {
    return sponsorId;
  }

  public void setSponsorId(Long sponsorId) {
    this.sponsorId = sponsorId;
  }

  public String getSponsorName() {
    return sponsorName;
  }

  public void setSponsorName(String sponsorName) {
    this.sponsorName = sponsorName;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getOther() {
    return other;
  }

  public void setOther(String other) {
    this.other = other;
  }
}