/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.domain.model.returnVisit;

import com.torch.domain.model.common.IdEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
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
@Table(name = "return_visit")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnVisit extends IdEntity implements Serializable {

  private final static long serialVersionUID = 1L;
  /**
   * 学生ID
   */
  private Long studentId;

  /**
   * 回访人
   */
  private Long returnVisitor;

  /**
   * 回访时间
   */
  private DateTime returnTime;

  /**
   * 回访内容
   */
  private String returnInfo;

  /**
   * 回访图片
   */
  private String returnPhotos;

  public Long getStudentId() {
    return studentId;
  }

  public void setStudentId(Long studentId) {
    this.studentId = studentId;
  }

  public Long getReturnVisitor() {
    return returnVisitor;
  }

  public void setReturnVisitor(Long returnVisitor) {
    this.returnVisitor = returnVisitor;
  }

  @Lob
  @Column(columnDefinition = "mediumblob")
  public DateTime getReturnTime() {
    return returnTime;
  }

  public void setReturnTime(DateTime returnTime) {
    this.returnTime = returnTime;
  }

  public String getReturnInfo() {
    return returnInfo;
  }

  public void setReturnInfo(String returnInfo) {
    this.returnInfo = returnInfo;
  }

  public String getReturnPhotos() {
    return returnPhotos;
  }

  public void setReturnPhotos(String returnPhotos) {
    this.returnPhotos = returnPhotos;
  }
}