/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.domain.model.homeVisit;

import com.torch.domain.model.common.IdEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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


}