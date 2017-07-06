package com.torch.interfaces.release;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by yuanj on 2017/7/6.
 */
@Data
public class ReleaseStudentDto {

  @ApiModelProperty(name = "发布学生关系ID", required = true, position = 1)
  private Long releaseStudentId;
  @ApiModelProperty(name = "需要的金额", required = true, position = 1)
  private double needMoney;

}
