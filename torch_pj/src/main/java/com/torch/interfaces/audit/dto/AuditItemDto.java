package com.torch.interfaces.audit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2017/6/27.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuditItemDto {
  /**
   * 审核项ID
   */
  private Long auditItemId;
  /**
   *选择项
   */
  private String options;
  /**
   * 分数
   */
  private double scores;
}
