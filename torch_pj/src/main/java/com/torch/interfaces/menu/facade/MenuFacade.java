/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.interfaces.menu.facade;

import com.torch.interfaces.menu.command.MenuAddCommand;
import com.torch.interfaces.menu.facade.dto.MenuAddDTO;

/**
 * 
 * @author 杨乐 2017-01-18 14:31:25 
 * @version 1.0
 */
public interface MenuFacade {

	MenuAddDTO addMenu(MenuAddCommand menuAddCommand);

}
