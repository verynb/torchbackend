/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.domain.model.student;

import com.torch.domain.model.common.IdEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yj 2017-01-18 11:16:14
 * @version 1.0
 */
@Entity
@Table(name = "base_student")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student extends IdEntity implements Serializable {

  private final static long serialVersionUID = 1L;

  /**
   * 头像
   */
  private String headPhoto;

  /**
   * 编号
   */
  private String sNo;

  /**
   * 姓名
   */
  private String name;

  /**
   * 性别
   */
  private String gender;

  /**
   * 名族
   */
  private String nation;

  /**
   * 出生日期
   */
  private String birthday;

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
   *区
   */
  private Integer age;
  /**
   * 省份
   */
  private String province;

  /**
   * 市
   */
  private String city;
  /**
   *区
   */
  private String area;
  /**
   * 详细地址
   */
  private String address;
  //身份证
  private String identityCard;
  //学校ID
  private Long schoolId;
  //年级
  private String grade;

  private String gradeCode;
  //当前资助人
  private Long sponsorId;

  /**
   * 0.正常
   * 1.结案
   * 2.冻结
   * 3.预发布
   * 4.已发布
   * 5.预订
   * 6.已结队
   */
  private Integer status;

  private String other;

  //学生新增一下字段
  @ApiModelProperty(name = "家庭人员构成及身体状况", required = true, position = 11)
  private String familyComposition;

  @ApiModelProperty(name = "家庭人员经济", required = true, position = 11)
  private String familyEconomy;

  @ApiModelProperty(name = "学习状况", required = true, position = 11)
  private String learningStatus;


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

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getNation() {
    return nation;
  }

  public void setNation(String nation) {
    this.nation = nation;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
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

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
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