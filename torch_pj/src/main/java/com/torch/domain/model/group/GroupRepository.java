/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.domain.model.group;

import org.springframework.data.repository.CrudRepository;

/**
 * 
 * @author 杨乐 2017-01-16 11:44:31
 * @version 1.0
 */
public interface GroupRepository extends CrudRepository<Group, Long> {
	Group findByGroupid(String groupid);
}
