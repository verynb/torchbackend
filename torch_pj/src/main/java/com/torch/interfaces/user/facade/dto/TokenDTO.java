package com.torch.interfaces.user.facade.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.torch.domain.model.user.DictVolunteerRole;
import com.torch.domain.model.version.DictUpgrade;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by long on 9/21/16.
 */
@Data
@Builder
public class TokenDTO {

  @JsonProperty("x-auth-token")
  private String Authorization;
  private CodeMessage codeMessage;
  private Long userId;
  private String userName;
  private String mobile;
  private DictVolunteerRole role;
  @ApiModelProperty("用户类型0义工，1资助人")
  private int userType;
  private DictUpgrade version;

}
