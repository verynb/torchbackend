/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.interfaces.menu;

import com.torch.interfaces.menu.command.MenuAddCommand;
import com.torch.interfaces.menu.facade.MenuFacade;
import com.torch.interfaces.menu.facade.dto.MenuAddDTO;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static com.torch.interfaces.common.ApiPaths.API_CONTEXT_PATH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 
 * @author 杨乐 2017-01-18 14:31:25 
 * @version 1.0
 */
@RestController
@RequestMapping(path = API_CONTEXT_PATH, produces = APPLICATION_JSON_VALUE)
public class MenuResource {
	
	private final MenuFacade menuFacade;

    @Autowired
    public MenuResource(final MenuFacade menuFacade) {
        this.menuFacade = menuFacade;
    }
    
   	@ApiOperation(value = "新增菜单", notes = "", response = MenuAddDTO.class, httpMethod = "POST")
   	@RequestMapping(path = "/menu", method = POST)
   	public MenuAddDTO addMenu(@Valid @RequestBody MenuAddCommand menuAddCommand) {
   		return menuFacade.addMenu(menuAddCommand);
   	}
    
}
