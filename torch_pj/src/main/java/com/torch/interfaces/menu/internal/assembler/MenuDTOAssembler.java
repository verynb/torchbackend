package com.torch.interfaces.menu.internal.assembler;

import com.torch.domain.model.menu.Menu;
import com.torch.interfaces.menu.facade.dto.MenuAddDTO;

/**
 * 
 * 
 * @author service#yangle.org.cn
 * @date 2017年1月16日 下午12:23:10 
 *
 */
public class MenuDTOAssembler {
	
	public static MenuAddDTO toMenuAddDTO(Menu menu) {
		MenuAddDTO menuAddDTO = new MenuAddDTO();
		menuAddDTO.setId(menu.getId());
		return menuAddDTO;
	}

}
