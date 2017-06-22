package com.torch.interfaces.user.internal;

import com.torch.application.user.UserService;
import com.torch.domain.model.user.DictVolunteerRole;
import com.torch.domain.model.user.DictVolunteerRoleRepository;
import com.torch.domain.model.version.DictUpgrade;
import com.torch.domain.model.version.DictUpgradeRepository;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.common.security.TokenService;
import com.torch.interfaces.user.command.AuthenticateCommand;
import com.torch.interfaces.user.facade.dto.UserDTO;
import com.torch.domain.model.user.UserRepository;
import com.torch.interfaces.common.security.Session;
import com.torch.interfaces.user.command.BindPhoneCommand;
import com.torch.interfaces.user.facade.UserServiceFacade;
import com.torch.interfaces.user.facade.dto.TokenDTO;
import com.torch.interfaces.user.internal.assembler.UserDTOAssembler;
import com.torch.util.cache.RedisUtils;

import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.torch.domain.model.user.User;
import java.util.Optional;


/**
 * Created by yuanj on 9/18/16.
 */
@Service
public class UserServiceFacadeImpl implements UserServiceFacade {

  final UserRepository userRepository;
  final UserService userService;
  final TokenService tokenService;

  @Autowired
  private RedisUtils residUtil;
  @Autowired
  private Environment env;

  @Autowired
  private DictUpgradeRepository dictUpgradeRepository;

  @Autowired
  private DictVolunteerRoleRepository dictVolunteerRoleRepository;

  @Autowired
  public UserServiceFacadeImpl(final UserRepository userRepository,
      final TokenService tokenService,
      final UserService userService) {
    this.userRepository = userRepository;
    this.userService = userService;
    this.tokenService = tokenService;
  }

  @Override
  public Optional<UserDTO> ofId(Long id) {
    Optional<User> user = userRepository.getById(id);
    return UserDTOAssembler.toOptionalDTO(user);
  }

  @Override
  public Optional<TokenDTO> authenticate(AuthenticateCommand authenticateCommand) {
    final Optional<User> user = userService
        .authenticate(authenticateCommand.getUsername(), authenticateCommand.getPassword());
    if (!user.isPresent()) {
      return Optional.empty();
    }
    final Session session = Session.buildSession(user.get());
    String token = tokenService.generate(session);
    residUtil.set("token/" + token, user.get().getName(), Long.parseLong(env.getProperty("expireTime.login")));
    List<DictUpgrade> dictUpgrades = (List<DictUpgrade>) dictUpgradeRepository.findAll();
    DictVolunteerRole role = dictVolunteerRoleRepository
        .findOne(user.get().getRoleId() == null ? 0 : user.get().getRoleId());
    return Optional.of(TokenDTO.builder()
        .Authorization(token)
        .codeMessage(new CodeMessage())
        .mobile(user.get().getMobile())
        .userId(user.get().getId())
        .userName(user.get().getName())
        .userType(user.get().getType())
        .version(CollectionUtils.isEmpty(dictUpgrades) ? null : dictUpgrades.get(0))
        .role(role)
        .build());
  }


}
