package com.torch.interfaces.homeVisit.dto;

import com.torch.interfaces.common.facade.dto.CodeMessage;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Created by yuanj on 2017/6/30.
 */
@Data
@Builder
public class HomeList {

  private CodeMessage codeMessage;

  @ApiModelProperty("家访列表dto")
  private List<HomeVisitListDto> homeVisitListDtos;

}
