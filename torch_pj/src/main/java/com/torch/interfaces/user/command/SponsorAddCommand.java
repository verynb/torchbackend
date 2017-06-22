package com.torch.interfaces.user.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@ApiModel(value = "新增资助人参数")
@Data
public class SponsorAddCommand {

  @NotBlank(message = "user.username.required")
  @ApiModelProperty(value = "姓名", required = false)
  private String name;

  @ApiModelProperty(value = "网名", required = false)
  private String netName;

  @ApiModelProperty(value = "年龄", required = false)
  private Integer age;

  @ApiModelProperty(value = "QQ", required = false)
  private String qq;

  @ApiModelProperty(value = "邮箱", required = false)
  private String email;
  @NotBlank
  @ApiModelProperty(value = "手机", required = true)
  private String mobile;

  @ApiModelProperty(value = "省份", required = false)
  private String province;

  @ApiModelProperty(value = "市", required = false)
  private String city;

  @ApiModelProperty(value = "区", required = false)
  private String area;

  @ApiModelProperty(value = "详细地址", required = false)
  private String address;

  @ApiModelProperty(value = "注册时间,时间戳", required = true)
  private Long joinTime;

  @ApiModelProperty(value = "密码", required = true)
  private String password;

  @ApiModelProperty(value = "头像", required = false)
  private String photo;

}
