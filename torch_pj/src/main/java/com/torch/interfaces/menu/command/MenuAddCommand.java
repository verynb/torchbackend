package com.torch.interfaces.menu.command;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * 
 * @author service#yangle.org.cn
 * @date 2017年1月18日 下午3:58:44
 *
 */
@ApiModel(value = "新增菜单")
public class MenuAddCommand {

	@NotNull(message = "menu.menuId.required")
	@ApiModelProperty(value = "编号", required = true)
	private String menuId;

	@NotBlank(message = "menu.name.required")
	@ApiModelProperty(value = "名称", required = true)
	private String name;

	@NotBlank(message = "menu.url.required")
	@ApiModelProperty(value = "链接", required = true)
	private String url;

	@NotNull(message = "menu.orderNo.required")
	@ApiModelProperty(value = "排序值", required = true)
	private Long orderNo;

	@NotNull(message = "menu.isMenu.required")
	@ApiModelProperty(value = "是否菜单 1 是 0 否", required = true)
	private Boolean isMenu;

	@ApiModelProperty(value = "上级（父）ID")
	private String menuIdParent;

	@ApiModelProperty(value = "图标")
	private String icon;

	@ApiModelProperty(value = "用于其他配置，如:target:xxxx 则表示菜单打开的方式，是什么")
	private String plugin;

	@ApiModelProperty(value = "描述说明")
	private String description;

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public Boolean getIsMenu() {
		return isMenu;
	}

	public void setIsMenu(Boolean isMenu) {
		this.isMenu = isMenu;
	}

	public String getMenuIdParent() {
		return menuIdParent;
	}

	public void setMenuIdParent(String menuIdParent) {
		this.menuIdParent = menuIdParent;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPlugin() {
		return plugin;
	}

	public void setPlugin(String plugin) {
		this.plugin = plugin;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
