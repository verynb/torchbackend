package com.torch.interfaces.me;

import com.torch.interfaces.common.ApiPaths;
import com.torch.interfaces.common.Responses;
import com.torch.interfaces.user.facade.UserServiceFacade;
import com.torch.interfaces.user.facade.dto.UserDTO;
import com.torch.interfaces.common.Users;
import com.torch.interfaces.user.command.BindPhoneCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(path = ApiPaths.API_CONTEXT_PATH + "/me", produces = APPLICATION_JSON_VALUE)
public class MeResource {

    private final UserServiceFacade userServiceFacade;

    @Autowired
    public MeResource(final UserServiceFacade userServiceFacade) {
        this.userServiceFacade = userServiceFacade;
    }

//    @LoginRequired(role = "admin")
    @RequestMapping(path = "/profile", method = GET, produces = APPLICATION_JSON_VALUE)
    public UserDTO getProfile() {
        return Responses.notFound(userServiceFacade.ofId(Users.currentUserId()));
    }
}
