package com.torch.interfaces.release;

import com.torch.interfaces.common.facade.dto.CodeMessage;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Created by yuanj on 2017/6/1.
 */
@Data
@Builder
public class ReleaseListResultDto {

  @ApiModelProperty("CODE MESSAGE")
  private CodeMessage codeMessage;

  private List<ReleaseListDto> releaseList;

}
