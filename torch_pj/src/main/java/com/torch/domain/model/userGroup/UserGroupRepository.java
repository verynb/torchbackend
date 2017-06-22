/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.domain.model.userGroup;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * 
 * @author 杨乐 2017-01-18 19:12:46 
 * @version 1.0
 */
public interface UserGroupRepository extends CrudRepository<UserGroup, Long> {
	
	UserGroup findByGroupidAndUsername(String groupid, String username);

	@Query("SELECT u FROM UserGroup u WHERE u.groupid='salesSupervisor' AND u.username = ?1")
	UserGroup findByUsername(String username);
}
