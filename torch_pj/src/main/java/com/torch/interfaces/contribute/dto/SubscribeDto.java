package com.torch.interfaces.contribute.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yuanj on 2017/6/28.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeDto {

  @ApiModelProperty(name = "认捐记录ID", required = true, position = 0)
  private Long subscribeId;
  @ApiModelProperty(name = "认捐学生ID", required = true, position = 1)
  private Long studentId;

  @ApiModelProperty(name = "认捐学生姓名", required = true, position = 1)
  private String studentName;

  @ApiModelProperty(name = "认捐时间", required = true, position = 2)
  private String subscribeTime;

}
