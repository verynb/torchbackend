package com.torch.interfaces.region.internal.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yuanj on 2017/5/21.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegionDto {
  @ApiModelProperty("区域id")
  private Long id;
  @ApiModelProperty("区域名称")
  private String name;
}
