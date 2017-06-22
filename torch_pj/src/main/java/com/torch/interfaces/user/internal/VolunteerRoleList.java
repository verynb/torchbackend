package com.torch.interfaces.user.internal;

import com.torch.interfaces.common.facade.dto.CodeMessage;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Administrator on 2017/5/20.
 */
@Data
@Builder
public class VolunteerRoleList {

  private CodeMessage codeMessage;
  @ApiModelProperty("义工角色")
  private List<VolunteerRoleDto> roleDtos;

}
