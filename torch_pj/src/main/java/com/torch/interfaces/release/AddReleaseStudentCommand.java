/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.release;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yj 2017-01-18 11:16:14
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddReleaseStudentCommand {

  /**
   * 批次ID
   */
  @NotNull
  @ApiModelProperty(name = "发布批次ID", required = true, position = 1)
  private Long batchId;

  /**
   * 学生ID
   */
  @NotNull
  @ApiModelProperty(name = "学生ID", required = true, position = 2)
  private Long studentId;

  /**
   * 所需金额
   */
  @NotNull
  @ApiModelProperty(name = "所需金额", required = true, position = 3)
  private Double needMoney;

}