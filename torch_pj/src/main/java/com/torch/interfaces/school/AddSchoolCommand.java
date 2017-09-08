/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.school;

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

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddSchoolCommand {

  /**
   * 姓名
   */
  @ApiModelProperty(name = "学校名称", required = true, position = 1)
  private String schoolName;

  /**
   * 省份
   */
  @ApiModelProperty(name = "省份", required = true, position = 2)
  private String province;

  /**
   * 市
   */
  @ApiModelProperty(name = "市", required = true, position = 3)
  private String city;
  /**
   *区
   */
  @ApiModelProperty(name = "区", required = true, position = 4)
  private String area;
  /**
   * 详细地址
   */
  @ApiModelProperty(name = "详细地址", required = true, position = 5)
  private String address;
  //负责人姓名
  @ApiModelProperty(name = "负责人姓名", required = true, position = 6)
  private String leaderName;

  @ApiModelProperty(name = "负责人QQ", required = true, position = 7)
  private String qq;

  @ApiModelProperty(name = "负责人邮箱", required = true, position = 8)
  private String email;

  @ApiModelProperty(name = "负责人电话", required = true, position = 9)
  private String mobile;


  //对接人信息
  @ApiModelProperty(name = "对接人姓名", required = true, position = 6)
  private String buttName;

  @ApiModelProperty(name = "对接人QQ", required = true, position = 7)
  private String buttqq;

  @ApiModelProperty(name = "对接人邮箱", required = true, position = 8)
  private String buttEmail;

  @ApiModelProperty(name = "对接人电话", required = true, position = 9)
  private String buttMobile;


  @ApiModelProperty(name = "备注", required = true, position = 10)
  private String remark;

}