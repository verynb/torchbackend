/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.domain.model.release;

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
@Table(name = "release_student")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseStudent extends IdEntity implements Serializable {

  private final static long serialVersionUID = 1L;
  /**
   * 批次ID
   */
  private Long batchId;

  /**
   * 学生ID
   */
  private Long studentId;

  /**
   * 所需金额
   */
  private double needMoney;

  /**
   * 操作人ID
   */
  private Long operatorId;

  /**
   * 操作人名字
   */
  private String operatorName;
  /*
     4.已发布
   * 5.预订
   * 6.已结队
   */
  private Integer status;


}