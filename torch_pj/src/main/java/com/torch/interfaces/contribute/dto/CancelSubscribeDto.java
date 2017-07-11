package com.torch.interfaces.contribute.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created by yuanj on 2017/6/28.
 */
@Data
public class CancelSubscribeDto {

  @ApiModelProperty(name = "认捐学生ID", required = true, position = 2)

  private Long studentId;
}
