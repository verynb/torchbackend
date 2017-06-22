/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.application.menu;

import com.torch.domain.model.menu.Menu;
import com.torch.interfaces.menu.command.MenuAddCommand;

/**
 * 
 * @author 杨乐 2017-01-18 14:31:25 
 * @version 1.0
 */
public interface MenuService {

	Menu addMenu(MenuAddCommand menuAddCommand);

	
}