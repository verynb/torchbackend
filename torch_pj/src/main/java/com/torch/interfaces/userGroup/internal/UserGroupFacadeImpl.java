/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.userGroup.internal;

import com.torch.application.userGroup.UserGroupService;
import com.torch.domain.model.group.Group;
import com.torch.domain.model.group.GroupRepository;
import com.torch.domain.model.user.User;
import com.torch.domain.model.user.UserRepository;
import com.torch.domain.model.userGroup.UserGroup;
import com.torch.domain.model.userGroup.UserGroupRepository;
import com.torch.domain.model.userGroup.exceptions.UserOrGroupNotExistException;
import com.torch.interfaces.common.security.Session;
import com.torch.interfaces.userGroup.command.UserGroupChangeCommand;
import com.torch.interfaces.userGroup.facade.UserGroupFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author yuanj 2017-01-18 19:16:46
 * @version 1.0
 */
@Service
public class UserGroupFacadeImpl implements UserGroupFacade {

  private final UserGroupService userGroupService;
  private final UserGroupRepository userGroupRepository;

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  public UserGroupFacadeImpl(final UserGroupService userGroupService,
      final UserGroupRepository userGroupRepository) {
    this.userGroupService = userGroupService;
    this.userGroupRepository = userGroupRepository;
  }

  @Override
  public void changeUserGroup(UserGroupChangeCommand userGroupChangeCommand) {
    String groupid = userGroupChangeCommand.getGroupid();
    String username = userGroupChangeCommand.getUsername();

//		User user = userRepository.findByUsername(username);
    User user = null;
    Group group = groupRepository.findByGroupid(groupid);
    if (user == null || group == null) {
      throw new UserOrGroupNotExistException(username, groupid);
    }

    UserGroup userGroup = userGroupRepository
        .findByGroupidAndUsername(groupid, username);
    if (userGroup != null) {
      userGroupRepository.delete(userGroup);
    } else {
      userGroupService.addUserGroup(username, groupid);
    }
  }

}
