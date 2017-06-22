/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.interfaces.userGroup.facade;

import com.torch.interfaces.userGroup.command.UserGroupChangeCommand;

/**
 * 
 * @author 杨乐 2017-01-18 19:16:45 
 * @version 1.0
 */
public interface UserGroupFacade {

	/**
	 * 用户-用户组增加删除（存在就删除，不存在就新增)
	 *  
	 * @param userGroupChangeCommand
	 * @return void     
	 */
	void changeUserGroup(UserGroupChangeCommand userGroupChangeCommand);

}
