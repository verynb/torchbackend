/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.domain.model.homeVisit;

import com.torch.domain.model.common.IdEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
@Table(name = "home_visit")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeVisit extends IdEntity implements Serializable {

  private final static long serialVersionUID = 1L;

  /**
   * 发布批次ID
   */
  private Long batchId;
  /**
   * 学生ID
   */
  private Long studentId;
  /**
   * 申请表图片
   */
  private String  applicationForm;

  //学生本人
  private  String studentPhoto;

  //家庭成员
  private String familyPhoto;

  //家庭环境
  private String homePhoto;

  private String homeFeaturePhoto;

  //互动照片
  private String InteractivePhoto;

  //家访内容
  private String visitInfo;

  private String homeVistor;

  private Long homeVistorId;

  @ApiModelProperty("家访时间")
  private DateTime homeVisitTime;

  public Long getBatchId() {
    return batchId;
  }

  public void setBatchId(Long batchId) {
    this.batchId = batchId;
  }

  public Long getStudentId() {
    return studentId;
  }

  public void setStudentId(Long studentId) {
    this.studentId = studentId;
  }

  public String getApplicationForm() {
    return applicationForm;
  }

  public void setApplicationForm(String applicationForm) {
    this.applicationForm = applicationForm;
  }

  public String getStudentPhoto() {
    return studentPhoto;
  }

  public void setStudentPhoto(String studentPhoto) {
    this.studentPhoto = studentPhoto;
  }

  public String getFamilyPhoto() {
    return familyPhoto;
  }

  public void setFamilyPhoto(String familyPhoto) {
    this.familyPhoto = familyPhoto;
  }

  public String getHomePhoto() {
    return homePhoto;
  }

  public void setHomePhoto(String homePhoto) {
    this.homePhoto = homePhoto;
  }

  public String getHomeFeaturePhoto() {
    return homeFeaturePhoto;
  }

  public void setHomeFeaturePhoto(String homeFeaturePhoto) {
    this.homeFeaturePhoto = homeFeaturePhoto;
  }

  public String getInteractivePhoto() {
    return InteractivePhoto;
  }

  public void setInteractivePhoto(String interactivePhoto) {
    InteractivePhoto = interactivePhoto;
  }

  public String getVisitInfo() {
    return visitInfo;
  }

  public void setVisitInfo(String visitInfo) {
    this.visitInfo = visitInfo;
  }

  public String getHomeVistor() {
    return homeVistor;
  }

  public void setHomeVistor(String homeVistor) {
    this.homeVistor = homeVistor;
  }

  public Long getHomeVistorId() {
    return homeVistorId;
  }

  public void setHomeVistorId(Long homeVistorId) {
    this.homeVistorId = homeVistorId;
  }

  public void setHomeVisitTime(DateTime homeVisitTime) {
    this.homeVisitTime = homeVisitTime;
  }

  @Lob
  @Column(columnDefinition="mediumblob")
  public DateTime getHomeVisitTime() {
    return homeVisitTime;
  }

}