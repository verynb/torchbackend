/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.domain.model.gradeMoney;

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
@Table(name = "dict_grade_money")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictGradeMoney extends IdEntity implements Serializable {

  private final static long serialVersionUID = 1L;

  /**
   * 年级名字
   */
  private String gradeName;
  /**
   * 年级code
   */
  private String gradeCode;
  /**
   * 默认金额
   */
  private double defaultMoney;
}