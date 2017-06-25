package com.torch.interfaces.me;

import com.torch.interfaces.common.ApiPaths;
import com.torch.interfaces.common.Responses;
import com.torch.interfaces.common.facade.dto.ReturnIdDto;
import com.torch.interfaces.common.security.Session;
import com.torch.interfaces.common.security.annotation.RoleCheck;
import com.torch.interfaces.me.dto.PasswordUpdateDto;
import com.torch.interfaces.user.facade.UserServiceFacade;
import com.torch.interfaces.user.facade.dto.TokenDTO;
import com.torch.interfaces.user.facade.dto.UserDTO;
import com.torch.interfaces.common.Users;
import com.torch.interfaces.user.command.BindPhoneCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;


@RestController
@RequestMapping(path = ApiPaths.API_CONTEXT_PATH + "/me", produces = APPLICATION_JSON_VALUE)
@Api(tags ={"AuthenticationResource-api"}, description = "个人中心相关api")
public class MeResource {

  private final UserServiceFacade userServiceFacade;

  @Autowired
  public MeResource(final UserServiceFacade userServiceFacade) {
    this.userServiceFacade = userServiceFacade;
  }

  //    @LoginRequired(role = "admin")
  @ApiOperation(value = "修改密码", notes = "", response = ReturnIdDto.class, httpMethod = "PUT")
  @RoleCheck
  @RequestMapping(path = "/password", method = PUT, produces = APPLICATION_JSON_VALUE)
  public ReturnIdDto updatePassword(@Valid @RequestBody PasswordUpdateDto dto) {
    if(!dto.getNewPassword().equals(dto.getNewPasswordTwo())){
      throw new RuntimeException("新密码确认不一致");
    }
    return userServiceFacade.updatePassword(Session.getUserId(), dto.getPassword(), dto.getNewPassword());
  }
}
