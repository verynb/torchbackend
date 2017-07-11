package com.torch.interfaces.contribute.dto;

import com.torch.interfaces.common.facade.dto.CodeMessage;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
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
public class SubscribeListDto {

  private CodeMessage codeMessage;

  @ApiModelProperty(name = "认捐列表", required = true, position = 2)
  private List<SubscribeDto> subscribeDtos;

}
