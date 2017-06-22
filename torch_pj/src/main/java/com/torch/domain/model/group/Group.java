/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.domain.model.group;

import com.torch.domain.model.common.IdEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 
 * @author 杨乐 2017-01-16 12:44:43 
 * @version 1.0
 */
@Entity
@Table(name = "base_group")
public class Group extends IdEntity implements Serializable {

	private final static long serialVersionUID = 1L;
	
	/** 描述 */
	@Column
	private String description;	 
	@Column
	private String groupid;	 
	/** 名字 */
	@Column
	private String name;	 
	/** 状态 */
	@Column
	private Integer status;	 
	@Column
	private Long tenantId;	 
	/** 类型（sys，user） */
	@Column
	private String type;	 

	public void setDescription(String description){
		this.description = description;
	}
	public String getDescription(){
		return this.description;
	}
	public void setGroupid(String groupid){
		this.groupid = groupid;
	}
	public String getGroupid(){
		return this.groupid;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
	public void setStatus(Integer status){
		this.status = status;
	}
	public Integer getStatus(){
		return this.status;
	}
	public void setTenantId(Long tenantId){
		this.tenantId = tenantId;
	}
	public Long getTenantId(){
		return this.tenantId;
	}
	public void setType(String type){
		this.type = type;
	}
	public String getType(){
		return this.type;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("{");
		string.append("id="+this.getId());
		string.append(",description="+this.getDescription());
		string.append(",groupid="+this.getGroupid());
		string.append(",name="+this.getName());
		string.append(",status="+this.getStatus());
		string.append(",tenantId="+this.getTenantId());
		string.append(",type="+this.getType());
		string.append("}");
		return string.toString();
	}
	
	//----------  create builder ----------//
	public static TGroupBuilder tGroupBuilder() {
        return new TGroupBuilder();
    }
    
	public static class TGroupBuilder {
	
		private Long id;	 
		private String description;	 
		private String groupid;	 
		private String name;	 
		private Integer status;	 
		private Long tenantId;	 
		private String type;	 
		
		public TGroupBuilder withId(Long id) {
            this.id = id;
            return this;
        }
		public TGroupBuilder withDescription(String description) {
            this.description = description;
            return this;
        }
		public TGroupBuilder withGroupid(String groupid) {
            this.groupid = groupid;
            return this;
        }
		public TGroupBuilder withName(String name) {
            this.name = name;
            return this;
        }
		public TGroupBuilder withStatus(Integer status) {
            this.status = status;
            return this;
        }
		public TGroupBuilder withTenantId(Long tenantId) {
            this.tenantId = tenantId;
            return this;
        }
		public TGroupBuilder withType(String type) {
            this.type = type;
            return this;
        }
		public Group build() {
            Group tGroup = new Group();
            tGroup.setId(this.id);
            tGroup.setDescription(this.description);
            tGroup.setGroupid(this.groupid);
            tGroup.setName(this.name);
            tGroup.setStatus(this.status);
            tGroup.setTenantId(this.tenantId);
            tGroup.setType(this.type);
			return tGroup;
        }
		 
	}
}