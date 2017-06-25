package com.torch.interfaces.user.command;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@ApiModel(value = "新增义工参数")
@Data
public class VolunteerAddCommand {

  @NotBlank(message = "user.username.required")
  @ApiModelProperty(value = "姓名", required = false)
  private String name;

  @ApiModelProperty(value = "网名", required = false)
  private String netName;

  @ApiModelProperty(value = "年龄", required = false)
  private Integer age;

  @ApiModelProperty(value = "角色", required = true)
  private Long roleId;

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

  @ApiModelProperty(value = "加入时间,时间戳", required = true)
  private String joinTime;

  @ApiModelProperty(value = "密码", required = true)
  private String password;

  @ApiModelProperty(value = "头像", required = false)
  private String photo;

  @ApiModelProperty(value = "学校IDS", required = false)
  private List<Long> schoolIds;


}
