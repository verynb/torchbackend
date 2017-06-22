package com.torch.interfaces.user;

import com.torch.interfaces.common.ApiPaths;
import com.torch.interfaces.common.Responses;
import com.torch.interfaces.user.command.AuthenticateCommand;
import com.torch.interfaces.user.facade.UserServiceFacade;
import com.torch.interfaces.user.facade.dto.TokenDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@CrossOrigin
@RequestMapping(path = ApiPaths.API_CONTEXT_PATH)
@Api(tags ={"AuthenticationResource-api"}, description = "用户登录相关api")
public class AuthenticationResource {

    private final UserServiceFacade userServiceFacade;

    @Autowired
    public AuthenticationResource(final UserServiceFacade userServiceFacade) {
        this.userServiceFacade = userServiceFacade;
    }

    @ApiOperation(value = "登录", notes = "", response = TokenDTO.class, httpMethod = "POST")
    @RequestMapping(path = "/authenticate", method = RequestMethod.POST, produces = APPLICATION_JSON_VALUE)
    public TokenDTO authenticate(@Valid @RequestBody AuthenticateCommand authenticateCommand) {
        return Responses.unAuthorized(userServiceFacade.authenticate(authenticateCommand));
    }
}
