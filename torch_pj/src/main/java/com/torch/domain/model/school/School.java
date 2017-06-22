/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.domain.model.school;

import com.torch.domain.model.common.IdEntity;
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
@Table(name = "base_school")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class School extends IdEntity implements Serializable {

  private final static long serialVersionUID = 1L;
  /**
   * 姓名
   */
  private String schoolName;

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
  //负责人姓名
  private String leaderName;

  private String qq;

  private String email;

  private String mobile;

  private String remark;

}