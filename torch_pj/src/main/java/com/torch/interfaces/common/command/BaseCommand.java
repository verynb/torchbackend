package com.torch.interfaces.common.command;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

public class BaseCommand {
	
	@NotNull(message="base.id.required")
	@ApiModelProperty(required=true)
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
