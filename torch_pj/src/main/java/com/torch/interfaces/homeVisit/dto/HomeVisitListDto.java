package com.torch.interfaces.homeVisit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by yuanj on 2017/6/30.
 */
@Data
@Builder
public class HomeVisitListDto {
  @ApiModelProperty("家访列表ID")
  private Long homeVisitId;

  @ApiModelProperty("家访时间")
  private String homeVisitTime;

  @ApiModelProperty("家访人")
  private String homeVisitor;

}
