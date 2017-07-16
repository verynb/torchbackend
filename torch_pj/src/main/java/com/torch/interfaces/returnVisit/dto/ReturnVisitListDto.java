package com.torch.interfaces.returnVisit.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yuanj on 2017/7/11.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnVisitListDto {

  @ApiModelProperty("回访ID")
  private Long returnVisitId;

  @ApiModelProperty("回访时间")
  private String returnVisitTime;

  @ApiModelProperty("回访人")
  private String returnVisitor;

  @ApiModelProperty("学生姓名")
  private String studentName;

}
