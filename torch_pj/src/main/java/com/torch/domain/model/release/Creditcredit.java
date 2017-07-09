/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.domain.model.release;

import com.torch.domain.model.common.IdEntity;
import java.io.Serializable;
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
@Table(name = "creditcredit")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Creditcredit extends IdEntity implements Serializable {

  private final static long serialVersionUID = 1L;

  private Long studentId;

  /**
   * 姓名
   */
  private String name;

  /**
   * 性别
   */
  private String gender;

  /**
   * 出生日期
   */
  private String birthday;
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

  /**
   * 放款金额
   */
  private Double money;

  /**
   * 资助人ID
   */
  private Long sponsorId;

  private DateTime creditTime;

  @Lob
  @Column(columnDefinition = "mediumblob")
  public DateTime getCreditTime() {
    return creditTime;
  }

  public void setCreditTime(DateTime creditTime) {
    this.creditTime = creditTime;
  }
}