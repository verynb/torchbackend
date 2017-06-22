/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.user;

import static com.torch.interfaces.common.ApiPaths.API_CONTEXT_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.google.common.collect.Lists;
import com.torch.domain.model.user.DictVolunteerRole;
import com.torch.domain.model.user.DictVolunteerRoleRepository;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.common.security.annotation.RoleCheck;
import com.torch.interfaces.user.internal.VolunteerRoleDto;
import com.torch.interfaces.user.internal.VolunteerRoleList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanj 2017-01-12 16:41:05
 * @version 1.0
 */
@RestController
@RequestMapping(value = API_CONTEXT_PATH + "/volunteerRoles", produces = APPLICATION_JSON_VALUE)
@Api(tags = {"DictVolunteerRole-api"}, description = "义工选择角色相关api")
public class DictVolunteerRoleResource {

  private final DictVolunteerRoleRepository dictVolunteerRoleRepository;

  @Autowired
  public DictVolunteerRoleResource(final DictVolunteerRoleRepository dictVolunteerRoleRepository) {
    this.dictVolunteerRoleRepository = dictVolunteerRoleRepository;
  }

  @RoleCheck
  @ApiOperation(value = "获取义工角色列表")
  @RequestMapping(method = RequestMethod.GET)
  public VolunteerRoleList getRoles() {
    List<DictVolunteerRole> roles = (List<DictVolunteerRole>) dictVolunteerRoleRepository.findAll();
    List<VolunteerRoleDto> roleDtos = Lists.newArrayList();
    roles.forEach(role -> {
      VolunteerRoleDto dto = VolunteerRoleDto.builder()
          .id(role.getId())
          .name(role.getRoleName())
          .roleCode(role.getRoleCode())
          .build();
      roleDtos.add(dto);
    });
    return VolunteerRoleList.builder()
        .codeMessage(new CodeMessage())
        .roleDtos(roleDtos)
        .build();
  }
}
