/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.domain.model.apiRole;

import com.torch.domain.model.common.IdEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 
 * @author 杨乐 2017-01-19 18:24:42 
 * @version 1.0
 */
@Entity
@Table(name = "api_role")
public class ApiRole extends IdEntity implements Serializable {

	private final static long serialVersionUID = 1L;
	
	/** 接口 */
	@Column
	private String path;
	/** 请求方式（POST,GET,DELETE,PUT） */
	@Column
	private String method;
	/** 有权限访问接口的用户组 */
	@Column
	private String groupid;
	/** 客户ID */
	@Column
	private Long tenantId;

	public void setPath(String path){
		this.path = path;
	}
	public String getPath(){
		return this.path;
	}
	public void setMethod(String method){
		this.method = method;
	}
	public String getMethod(){
		return this.method;
	}
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

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("{");
		string.append("id="+this.getId());
		string.append(",path="+this.getPath());
		string.append(",method="+this.getMethod());
		string.append(",groupid="+this.getGroupid());
		string.append(",tenantId="+this.getTenantId());
		string.append("}");
		return string.toString();
	}
	
	//----------  create builder ----------//
	public static ApiRoleBuilder apiRoleBuilder() {
        return new ApiRoleBuilder();
    }
    
	public static class ApiRoleBuilder {
	
		private Long id;	 
		private String path;	 
		private String method;	 
		private String groupid;	 
		private Long tenantId;	 
		
		public ApiRoleBuilder withId(Long id) {
            this.id = id;
            return this;
        }
		public ApiRoleBuilder withPath(String path) {
            this.path = path;
            return this;
        }
		public ApiRoleBuilder withMethod(String method) {
            this.method = method;
            return this;
        }
		public ApiRoleBuilder withGroupid(String groupid) {
            this.groupid = groupid;
            return this;
        }
		public ApiRoleBuilder withTenantId(Long tenantId) {
            this.tenantId = tenantId;
            return this;
        }
		public ApiRole build() {
            ApiRole apiRole = new ApiRole();
            apiRole.setId(this.id);
            apiRole.setPath(this.path);
            apiRole.setMethod(this.method);
            apiRole.setGroupid(this.groupid);
            apiRole.setTenantId(this.tenantId);
			return apiRole;
        }
		 
	}
}