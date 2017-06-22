/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.domain.model.user;

import com.torch.util.Util;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.torch.domain.model.common.IdEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * @author yj 2017-01-18 11:16:14
 * @version 1.0
 */
@Entity
@Table(name = "base_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends IdEntity implements Serializable {

  private final static long serialVersionUID = 1L;
  /**
   * 姓名
   */
  private String name;
  /**
   * 网名
   */
  private String netName;
  /**
   * 年龄
   */
  private Integer age;
  /**
   * 角色
   */
  private Long roleId;
  /**
   * QQ
   */
  private String qq;
  /**
   * 邮箱
   */
  private String email;
  /**
   * 手机
   */
  private String mobile;

  /**
   * 密码
   */
  private String password;
  /**
   * 头像
   */
  private String photo;

  /**
   * 0义工，1资助人
   */
  private Integer type;
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
  /**
   * 加入时间
   */
  private DateTime joinTime;

  @Lob
  @Column(columnDefinition="mediumblob")
  public DateTime getJoinTime() {
    return joinTime;
  }


  public void setEncryptPassword(String password) {
    this.setPassword(Util.hashPasswordAddingSalt(password));
  }

  public boolean isValidPassword(String password) {
    return Util.isValidPassword(password, this.getPassword());
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getNetName() {
    return netName;
  }

  public void setNetName(String netName) {
    this.netName = netName;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public String getQq() {
    return qq;
  }

  public void setQq(String qq) {
    this.qq = qq;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
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

  public void setJoinTime(DateTime joinTime) {
    this.joinTime = joinTime;
  }
}