package com.torch.interfaces.menu.facade.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author service#yangle.org.cn
 * @date 2017年1月12日 下午4:49:16
 */
public class MenuAddDTO {

	@ApiModelProperty(value = "Menu ID")
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
