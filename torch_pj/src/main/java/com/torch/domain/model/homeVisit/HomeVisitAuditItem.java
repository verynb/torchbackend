/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.domain.model.homeVisit;

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
@Table(name = "home_visit_audititem")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HomeVisitAuditItem extends IdEntity implements Serializable {

  private final static long serialVersionUID = 1L;

  /**
   * 发布批次ID
   */
  private Long batchId;
  /**
   * 家访ID
   */
  private Long homeVisitId;

  /**
   * 审核项的ID
   */
  private Long auditItemId;

  /**
   * 学生ID
   */
  private Long studentId;
}