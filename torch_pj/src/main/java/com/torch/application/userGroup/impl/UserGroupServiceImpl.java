/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.application.userGroup.impl;

import com.torch.util.cache.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.torch.application.userGroup.UserGroupService;
import com.torch.domain.model.userGroup.UserGroup;
import com.torch.domain.model.userGroup.UserGroupRepository;
import java.util.List;
import java.util.Objects;

/**
 * 
 * @author 杨乐 2017-01-18 19:16:45
 * @version 1.0
 */
@Service
public class UserGroupServiceImpl implements UserGroupService {
	@Autowired
	private RedisUtils redisUtils;
	private final UserGroupRepository userGroupRepository;

	@Autowired
	public UserGroupServiceImpl(final UserGroupRepository userGroupRepository) {
		this.userGroupRepository = userGroupRepository;
	}

	@Override
	public void addUserGroup(String username, String groupid) {
		UserGroup userGroup = UserGroup.baseUserGroupBuilder().withGroupid(groupid).withUsername(username).build();
		userGroupRepository.save(userGroup);
	}

	@Override
	public boolean isSalesSupervisor(String username){
		UserGroup userGroup = userGroupRepository.findByUsername(username);
		if(Objects.isNull(userGroup)) return false;
		return true;
	}

	@Override
	public List<UserGroup> findUserGroupByUserName(String userName) {
		List<UserGroup> userGroups=(List<UserGroup>)redisUtils.get("usergroup/"+userName);
		return userGroups;
	}
}
