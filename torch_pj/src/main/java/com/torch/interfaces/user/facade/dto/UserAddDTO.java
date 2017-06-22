package com.torch.interfaces.user.facade.dto;

import com.torch.interfaces.common.facade.dto.CodeMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author service#yangle.org.cn
 * @date 2017年1月12日 下午4:49:16
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserAddDTO {

  private CodeMessage codeMessage;
  @ApiModelProperty(value = "用户ID")
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
