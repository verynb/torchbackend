/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.application.menu.impl;

import com.torch.domain.model.menu.Menu;
import com.torch.domain.model.menu.MenuRepository;
import com.torch.domain.model.menu.exceptions.MenuIdAlreadyExistException;
import com.torch.interfaces.menu.command.MenuAddCommand;
import com.torch.application.menu.MenuService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @author yuanj 2017-01-18 14:31:25
 * @version 1.0
 */
 @Service
public class MenuServiceImpl implements MenuService {

	private final MenuRepository menuRepository;

    @Autowired
    public MenuServiceImpl(final MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

	@Override
	public Menu addMenu(MenuAddCommand menuAddCommand) {
		Menu menu = menuRepository.findByMenuId(menuAddCommand.getMenuId());
		if (menu!=null) {
			throw new MenuIdAlreadyExistException(menuAddCommand.getMenuId());
		}else{
			menu = new Menu();
		}
		BeanUtils.copyProperties(menuAddCommand, menu);
		if (menuAddCommand.getMenuIdParent()!=null) {
			Menu menuParent = menuRepository.findByMenuId(menuAddCommand.getMenuIdParent());
			if (menuParent==null) {
				menu.setMenuIdParent("0");
				menu.setParents(menu.getMenuId());
			}else{
				menu.setParents(menu.getMenuId() + "," + menuParent.getParents());
			}
		}else{
			menu.setMenuIdParent("0");
			menu.setParents(menu.getId().toString());
		}
		
		return menuRepository.save(menu);
	}

}
