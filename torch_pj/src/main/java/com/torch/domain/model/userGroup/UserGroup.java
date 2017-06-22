/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.domain.model.userGroup;

import com.torch.domain.model.common.IdEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 
 * @author 杨乐 2017-01-18 19:12:45 
 * @version 1.0
 */
@Entity
@Table(name = "base_user_group")
public class UserGroup extends IdEntity implements Serializable {

	private final static long serialVersionUID = 1L;
	
	@Column
	private String groupid;
	@Column
	private Long tenantId;
	@Column
	private String username;

	public void setGroupid(String groupid){
		this.groupid = groupid;
	}
	public String getGroupid(){
		return this.groupid;
	}
	public void setTenantId(Long tenantId){
		this.tenantId = tenantId;
	}
	public Long getTenantId(){
		return this.tenantId;
	}
	public void setUsername(String username){
		this.username = username;
	}
	public String getUsername(){
		return this.username;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("{");
		string.append("id="+this.getId());
		string.append(",groupid="+this.getGroupid());
		string.append(",tenantId="+this.getTenantId());
		string.append(",username="+this.getUsername());
		string.append("}");
		return string.toString();
	}
	
	//----------  create builder ----------//
	public static BaseUserGroupBuilder baseUserGroupBuilder() {
        return new BaseUserGroupBuilder();
    }
    
	public static class BaseUserGroupBuilder {
	
		private Long id;	 
		private String groupid;	 
		private Long tenantId;	 
		private String username;	 
		
		public BaseUserGroupBuilder withId(Long id) {
            this.id = id;
            return this;
        }
		public BaseUserGroupBuilder withGroupid(String groupid) {
            this.groupid = groupid;
            return this;
        }
		public BaseUserGroupBuilder withTenantId(Long tenantId) {
            this.tenantId = tenantId;
            return this;
        }
		public BaseUserGroupBuilder withUsername(String username) {
            this.username = username;
            return this;
        }
		public UserGroup build() {
            UserGroup baseUserGroup = new UserGroup();
            baseUserGroup.setId(this.id);
            baseUserGroup.setGroupid(this.groupid);
            baseUserGroup.setTenantId(this.tenantId);
            baseUserGroup.setUsername(this.username);
			return baseUserGroup;
        }
		 
	}
}