/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.domain.model.userGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.torch.interfaces.common.CommonService;

/**
 * 
 * @author 杨乐 2017-01-18 19:12:46 
 * @version 1.0
 */
public interface UserGroupRepositoryJpa{
	public List<UserGroup> findAllUserGroup();

	public List<UserGroup>findUserGroupByUserName(String userName);
}
