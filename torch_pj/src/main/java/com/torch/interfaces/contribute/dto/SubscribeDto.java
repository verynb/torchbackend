package com.torch.interfaces.contribute.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created by yuanj on 2017/6/28.
 */
@Data
public class SubscribeDto {

  @ApiModelProperty(name = "发布批次ID", required = true, position = 1)
  @NotNull
  private Long batchId;
  @ApiModelProperty(name = "认捐学生", required = true, position = 2)

  private List<Long> studentIds;
}
