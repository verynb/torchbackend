package com.torch.interfaces.user.internal;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Administrator on 2017/5/20.
 */
@Data
@Builder
public class VolunteerRoleDto {
  @ApiModelProperty("义工角色ID")
  private Long id;
  @ApiModelProperty("义工角色名")
  private String name;

  private String roleCode;

}
