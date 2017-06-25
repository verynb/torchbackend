/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.student;

import com.torch.domain.model.common.IdEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author yj 2017-01-18 11:16:14
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddStudentCommand{

  /**
   * 姓名
   */
  @NotBlank
  @ApiModelProperty(name = "姓名", required = true, position = 1)
  private String name;

  /**
   * 性别
   */
  @NotBlank
  @ApiModelProperty(name = "性别", required = true, position = 2)
  private String gender;

  /**
   * 出生日期
   */
  @NotBlank
  @ApiModelProperty(name = "出生日期", required = true, position = 3)
  private String birthday;
  /**
   *年龄
   */
  @NotNull
  @ApiModelProperty(name = "年龄", required = true, position = 4)
  private Integer age;
  /**
   * 省份
   */
  @NotBlank
  @ApiModelProperty(name = "省份", required = true, position = 5)
  private String province;

  /**
   * 市
   */
  @NotBlank
  @ApiModelProperty(name = "市", required = true, position = 6)
  private String city;
  /**
   *区
   */
  @NotBlank
  @ApiModelProperty(name = "区", required = true, position = 7)
  private String area;
  /**
   * 详细地址
   */
  @NotBlank
  @ApiModelProperty(name = "详细地址", required = true, position = 8)
  private String address;
  //身份证
  @ApiModelProperty(name = "身份证", required = true, position = 9)
  private String identityCard;
  //学校ID
  @ApiModelProperty(name = "学校ID", required = true, position = 10)
  private Long schoolId;
  //年级
  @ApiModelProperty(name = "年级", required = true, position = 11)
  private String grade;
  //当前资助人
 /* @ApiModelProperty(name = "当前资助人ID", required = true, position = 12)
  private Long sponsorId;*/

  /**
   * 0.正常
   * 1.结案
   * 2.冻结
   */
  @ApiModelProperty(name = "状态{0.正常,1.结案,2.冻结}", required = true, position = 13)
  private Integer status;

}