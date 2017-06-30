package com.torch.interfaces.homeVisit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by yuanj on 2017/6/30.
 */
@Data
@Builder
public class AuditChoiced {

  @ApiModelProperty("选择审核项的TITLE")
  private String title;

  @ApiModelProperty("审核项的答案")
  private String options;

}
