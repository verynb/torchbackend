package com.torch.interfaces.user.facade.dto;

import com.torch.interfaces.common.facade.dto.CodeMessage;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Created by yuanj on 2017/6/19.
 */
@Data
@Builder
public class VolunteerListDto {

  private CodeMessage codeMessage;
  private List<VolunteerDetailResultDto> resultDtos;

}
