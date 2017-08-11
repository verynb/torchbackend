/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.release;

import io.swagger.annotations.ApiModelProperty;
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
public class UpdateReleaseCommand {

  /**
   * 批次
   */
  @ApiModelProperty(name = "发布批次ID", required = true, position = 1)
  private Long id;

  /**
   * 省份
   */
  @NotBlank
  @ApiModelProperty(name = "省份", required = true, position = 1)
  private String province;

  /**
   * 市
   */
  @NotBlank
  @ApiModelProperty(name = "市", required = true, position = 1)
  private String city;


}