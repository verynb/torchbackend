package com.torch.interfaces.user.command;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author service#yuanj.org.cn
 * @date 2017年1月16日 下午2:19:36
 */
public class AuthenticateCommand {

  @NotBlank(message = "user.username.required")
  @ApiModelProperty(name = "登录名或者手机号", required = true, position = 1)
  private String username;

  @NotBlank(message = "user.password.required")
  @ApiModelProperty(value = "密码", required = true, position = 2)
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
