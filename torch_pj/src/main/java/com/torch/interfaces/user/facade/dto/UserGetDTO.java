package com.torch.interfaces.user.facade.dto;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author service#yangle.org.cn
 * @date 2017年1月12日 下午4:49:16 
 */
public class UserGetDTO {
	
	@NotBlank(message = "user.id.required")
	@ApiModelProperty(value = "id", required = true)
	private Long id;

	@NotBlank(message = "user.name.required")
	@ApiModelProperty(value = "姓名", required = true)
	private String name;

	@NotBlank(message = "user.gender.required")
	@ApiModelProperty(value = "性别：1、男，0、女", required = true)
	private Integer gender;

	@ApiModelProperty(value = "移动电话")
	private String mobile;

	@ApiModelProperty(value = "邮件")
	private String email;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
