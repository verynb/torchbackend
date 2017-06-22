/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.domain.model.menu;

import org.springframework.data.repository.CrudRepository;

/**
 * 
 * @author 杨乐 2017-01-18 14:31:25 
 * @version 1.0
 */
public interface MenuRepository extends CrudRepository<Menu, Long> {
	Menu findByMenuId(String menuId);
}
