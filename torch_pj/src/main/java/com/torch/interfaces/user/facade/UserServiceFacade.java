package com.torch.interfaces.user.facade;

import com.torch.interfaces.common.facade.dto.ReturnIdDto;
import com.torch.interfaces.user.command.AuthenticateCommand;
import com.torch.interfaces.user.facade.dto.UserDTO;
import com.torch.interfaces.user.command.BindPhoneCommand;
import com.torch.interfaces.user.facade.dto.TokenDTO;

import java.util.Optional;

/**
 * Created by yuanj on 9/18/16.
 */
public interface UserServiceFacade {

    Optional<UserDTO> ofId(Long id);

    Optional<TokenDTO> authenticate(AuthenticateCommand authenticateCommand);

    ReturnIdDto updatePassword(Long userId,String password,String newPassword);

}
