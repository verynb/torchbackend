/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.interfaces.menu.internal;

import com.torch.application.menu.MenuService;
import com.torch.interfaces.menu.command.MenuAddCommand;
import com.torch.interfaces.menu.facade.MenuFacade;
import com.torch.interfaces.menu.facade.dto.MenuAddDTO;
import com.torch.interfaces.menu.internal.assembler.MenuDTOAssembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author 杨乐 2017-01-18 14:31:25 
 * @version 1.0
 */
@Service
public class MenuFacadeImpl implements MenuFacade {

    private final MenuService menuService;

    @Autowired
    public MenuFacadeImpl(final MenuService menuService) {
        this.menuService = menuService;
    }

	@Override
	public MenuAddDTO addMenu(MenuAddCommand menuAddCommand) {
		return MenuDTOAssembler.toMenuAddDTO(menuService.addMenu(menuAddCommand));
	}

}

