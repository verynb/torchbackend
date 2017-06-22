package com.torch.interfaces.common.facade.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by yuanj on 2017/6/19.
 */
@Data
@Builder
public class ReturnDto {
  @ApiModelProperty("CODE MESSAGE")
  private CodeMessage codeMessage;

}
