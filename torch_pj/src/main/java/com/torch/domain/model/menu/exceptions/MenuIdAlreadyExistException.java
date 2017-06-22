package com.torch.domain.model.menu.exceptions;

@SuppressWarnings("serial")
public class MenuIdAlreadyExistException extends RuntimeException {

	private String menuId;

	public MenuIdAlreadyExistException(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

}
