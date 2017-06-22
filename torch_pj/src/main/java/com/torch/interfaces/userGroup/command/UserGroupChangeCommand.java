package com.torch.interfaces.userGroup.command;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户-用户组增加删除")
public class UserGroupChangeCommand {

	@NotBlank(message = "group.groupid.required")
	@ApiModelProperty(value = "groupid", required = true)
	private String groupid;

	@NotBlank(message = "user.username.required")
	@ApiModelProperty(value = "用户登录名", required = true)
	private String username;

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	//
}
