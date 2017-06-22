/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.domain.model.menu;

import com.torch.domain.model.common.IdEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 
 * @author 杨乐 2017-01-18 17:29:41 
 * @version 1.0
 */
@Entity
@Table(name = "base_menu")
public class Menu extends IdEntity implements Serializable {

	private final static long serialVersionUID = 1L;
	
	/** 菜单编号 */
	@Column
	private String menuId;	 
	/** 名称 */
	@Column
	private String name;	 
	/** 功能代码 */
	@Column
	private String url;	 
	/** 图标 */
	@Column
	private String icon;	 
	/** 父ID */
	@Column
	private String menuIdParent;	 
	/** 排序值 */
	@Column
	private Long orderNo;	 
	/** 是否菜单 1 是 0 否 */
	@Column
	private Boolean isMenu;	 
	/** 用于其他配置，如:target:xxxx 则表示菜单打开的方式，是什么 */
	@Column
	private String plugin;	 
	/** 父单位 */
	@Column
	private String parents;	 
	/** 描述说明 */
	@Column
	private String description;	 

	public void setMenuId(String menuId){
		this.menuId = menuId;
	}
	public String getMenuId(){
		return this.menuId;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	public void setUrl(String url){
		this.url = url;
	}
	public String getUrl(){
		return this.url;
	}
	public void setIcon(String icon){
		this.icon = icon;
	}
	public String getIcon(){
		return this.icon;
	}
	public void setMenuIdParent(String menuIdParent){
		this.menuIdParent = menuIdParent;
	}
	public String getMenuIdParent(){
		return this.menuIdParent;
	}
	public void setOrderNo(Long orderNo){
		this.orderNo = orderNo;
	}
	public Long getOrderNo(){
		return this.orderNo;
	}
	public void setIsMenu(Boolean isMenu){
		this.isMenu = isMenu;
	}
	public Boolean getIsMenu(){
		return this.isMenu;
	}
	public void setPlugin(String plugin){
		this.plugin = plugin;
	}
	public String getPlugin(){
		return this.plugin;
	}
	public void setParents(String parents){
		this.parents = parents;
	}
	public String getParents(){
		return this.parents;
	}
	public void setDescription(String description){
		this.description = description;
	}
	public String getDescription(){
		return this.description;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("{");
		string.append("id="+this.getId());
		string.append(",menuId="+this.getMenuId());
		string.append(",name="+this.getName());
		string.append(",url="+this.getUrl());
		string.append(",icon="+this.getIcon());
		string.append(",menuIdParent="+this.getMenuIdParent());
		string.append(",orderNo="+this.getOrderNo());
		string.append(",isMenu="+this.getIsMenu());
		string.append(",plugin="+this.getPlugin());
		string.append(",parents="+this.getParents());
		string.append(",description="+this.getDescription());
		string.append("}");
		return string.toString();
	}
	
	//----------  create builder ----------//
	public static BaseMenuBuilder baseMenuBuilder() {
        return new BaseMenuBuilder();
    }
    
	public static class BaseMenuBuilder {
	
		private Long id;	 
		private String menuId;	 
		private String name;	 
		private String url;	 
		private String icon;	 
		private String menuIdParent;	 
		private Long orderNo;	 
		private Boolean isMenu;	 
		private String plugin;	 
		private String parents;	 
		private String description;	 
		
		public BaseMenuBuilder withId(Long id) {
            this.id = id;
            return this;
        }
		public BaseMenuBuilder withMenuId(String menuId) {
            this.menuId = menuId;
            return this;
        }
		public BaseMenuBuilder withName(String name) {
            this.name = name;
            return this;
        }
		public BaseMenuBuilder withUrl(String url) {
            this.url = url;
            return this;
        }
		public BaseMenuBuilder withIcon(String icon) {
            this.icon = icon;
            return this;
        }
		public BaseMenuBuilder withMenuIdParent(String menuIdParent) {
            this.menuIdParent = menuIdParent;
            return this;
        }
		public BaseMenuBuilder withOrderNo(Long orderNo) {
            this.orderNo = orderNo;
            return this;
        }
		public BaseMenuBuilder withIsMenu(Boolean isMenu) {
            this.isMenu = isMenu;
            return this;
        }
		public BaseMenuBuilder withPlugin(String plugin) {
            this.plugin = plugin;
            return this;
        }
		public BaseMenuBuilder withParents(String parents) {
            this.parents = parents;
            return this;
        }
		public BaseMenuBuilder withDescription(String description) {
            this.description = description;
            return this;
        }
		public Menu build() {
            Menu baseMenu = new Menu();
            baseMenu.setId(this.id);
            baseMenu.setMenuId(this.menuId);
            baseMenu.setName(this.name);
            baseMenu.setUrl(this.url);
            baseMenu.setIcon(this.icon);
            baseMenu.setMenuIdParent(this.menuIdParent);
            baseMenu.setOrderNo(this.orderNo);
            baseMenu.setIsMenu(this.isMenu);
            baseMenu.setPlugin(this.plugin);
            baseMenu.setParents(this.parents);
            baseMenu.setDescription(this.description);
			return baseMenu;
        }
		 
	}
}