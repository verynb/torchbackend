/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.interfaces.userGroup;

import static com.torch.interfaces.common.ApiPaths.API_CONTEXT_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.torch.interfaces.common.security.annotation.RoleCheck;
import com.torch.interfaces.userGroup.command.UserGroupChangeCommand;
import com.torch.interfaces.userGroup.facade.UserGroupFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author 杨乐 2017-01-18 19:16:45 
 * @version 1.0
 */
@RestController
@RequestMapping(path = API_CONTEXT_PATH, produces = APPLICATION_JSON_VALUE)
@Api(value = "UserGroupResource", description = "用户-用户组关系维护")
public class UserGroupResource {
	
	private final UserGroupFacade userGroupFacade;

    @Autowired
    public UserGroupResource(final UserGroupFacade userGroupFacade) {
        this.userGroupFacade = userGroupFacade;
    }
    
    @RoleCheck()
	@ApiOperation(value = "用户组增加删除", notes = "" , httpMethod = "POST")
	@RequestMapping(path = "/userGroup/change", method = POST)
	public void changeUserGroup(@Valid @RequestBody UserGroupChangeCommand userGroupChangeCommand) {
		userGroupFacade.changeUserGroup(userGroupChangeCommand);
	}
}
