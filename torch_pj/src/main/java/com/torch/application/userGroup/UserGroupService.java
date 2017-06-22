/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.application.userGroup;

import com.torch.domain.model.userGroup.UserGroup;

import java.util.List;

/**
 * 
 * @author 杨乐 2017-01-18 19:16:45
 * @version 1.0
 */
public interface UserGroupService {

	void addUserGroup(String username, String groupid);

	boolean isSalesSupervisor(String username);

	List<UserGroup>findUserGroupByUserName(String userName);

}