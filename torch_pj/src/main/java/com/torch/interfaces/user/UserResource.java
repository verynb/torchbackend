/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.user;

import static com.torch.interfaces.common.ApiPaths.API_CONTEXT_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import com.google.common.collect.Lists;
import com.querydsl.core.BooleanBuilder;
import com.torch.domain.model.release.QRelease;
import com.torch.domain.model.user.DictVolunteerRole;
import com.torch.domain.model.user.DictVolunteerRoleRepository;
import com.torch.domain.model.user.QUser;
import com.torch.domain.model.user.User;
import com.torch.domain.model.user.UserRepository;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.common.facade.dto.ReturnDto;
import com.torch.interfaces.user.command.SponsorAddCommand;
import com.torch.interfaces.user.command.SponsorUpdateCommand;
import com.torch.interfaces.user.command.VolunteerAddCommand;
import com.torch.interfaces.user.command.VolunteerUpdateCommand;
import com.torch.interfaces.user.facade.dto.SponsorDetailDto;
import com.torch.interfaces.user.facade.dto.SponsorDetailResultDto;
import com.torch.interfaces.user.facade.dto.SponsorListDto;
import com.torch.interfaces.user.facade.dto.VolunteerDetailDto;
import com.torch.interfaces.user.facade.dto.VolunteerDetailResultDto;
import com.torch.interfaces.user.facade.dto.VolunteerListDto;
import com.torch.interfaces.user.internal.assembler.UserDTOAssembler;
import io.swagger.annotations.ApiParam;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.torch.interfaces.common.security.Session;
import com.torch.interfaces.common.security.annotation.RoleCheck;
import com.torch.interfaces.user.facade.UserFacade;
import com.torch.interfaces.user.facade.dto.UserAddDTO;
import com.torch.interfaces.user.facade.dto.UserGetDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author yuanj 2017-01-12 16:41:05
 * @version 1.0
 */
@RestController
@RequestMapping(path = API_CONTEXT_PATH, produces = APPLICATION_JSON_VALUE)
@Api(value = "UserResource", description = "用户相关api")
public class UserResource {

  private final UserFacade userFacade;

  private final UserRepository userRepository;

  private final DictVolunteerRoleRepository roleRepository;

  @Autowired
  public UserResource(final UserFacade userFacade,
      final UserRepository userRepository,
      final DictVolunteerRoleRepository roleRepository) {
    this.userFacade = userFacade;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  @RoleCheck
  @ApiOperation(value = "新增义工", notes = "", response = UserAddDTO.class, httpMethod = "POST")
  @RequestMapping(path = "/user/volunteer", method = POST)
  @ResponseStatus(HttpStatus.CREATED)
  public UserAddDTO addVolunteer(@Valid @RequestBody VolunteerAddCommand command) {
    return userFacade.addVolunteer(command);
  }

  @ApiOperation(value = "更新义工", notes = "", httpMethod = "PUT")
  @RequestMapping(path = "/user/volunteer", method = PUT)
  @ResponseStatus(HttpStatus.OK)
  public ReturnDto updateUser(@Valid @RequestBody VolunteerUpdateCommand command) {
    userFacade.updateVolunteer(command);
    return ReturnDto.builder()
        .codeMessage(new CodeMessage())
        .build();
  }

  @ApiOperation(value = "删除义工", notes = "", httpMethod = "DELETE")
  @RequestMapping(path = "/user/volunteer/{id}", method = DELETE)
  @ResponseStatus(HttpStatus.OK)
  public ReturnDto deleteUser(@PathVariable("id") Long id) {
    userFacade.deleteUser(id);
    return ReturnDto.builder()
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "获取义工", notes = "", response = VolunteerDetailDto.class, httpMethod = "GET")
  @RequestMapping(path = "/user/volunteer/{id}", method = GET)
  public VolunteerDetailDto getUser(@PathVariable("id") Long id) {
    return userFacade.getUser(id);
  }

  @RoleCheck
  @ApiOperation(value = "修改义工/资助人密码", notes = "", httpMethod = "PUT")
  @RequestMapping(path = "/user/password/{id}", method = PUT)
  public ReturnDto updateUser(@PathVariable("id") Long id, String password) {
    userFacade.updatePassword(id, password);
    return ReturnDto.builder()
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "获取义工列表", notes = "", httpMethod = "GET")
  @RequestMapping(path = "/user/volunteers", method = GET)
  public VolunteerListDto getVolunteers(
      @ApiParam(value = "分页条数") @RequestParam(required = false, defaultValue = "15") Integer pageSize,
      @ApiParam(value = "当前页") @RequestParam(required = false, defaultValue = "0") Integer currentPage
  ) {
    Pageable pageable = new PageRequest(currentPage, pageSize);
    Page<User> page = userRepository.findAll(QUser.user.type.eq(0), pageable);
    List<VolunteerDetailResultDto> dtos = Lists.newArrayList();
    if (CollectionUtils.isNotEmpty(page.getContent())) {
      page.getContent().forEach(user -> {
        DictVolunteerRole role = roleRepository
            .findOne(user.getRoleId() == null ? 0L : user.getRoleId());
        dtos.add(UserDTOAssembler.toUserResultDTO(user, role));
      });
      return VolunteerListDto.builder()
          .resultDtos(dtos)
          .codeMessage(new CodeMessage())
          .build();
    } else {
      return VolunteerListDto.builder()
          .resultDtos(Collections.EMPTY_LIST)
          .codeMessage(new CodeMessage())
          .build();
    }
  }

  @RoleCheck
  @ApiOperation(value = "新增资助人", notes = "", response = UserAddDTO.class, httpMethod = "POST")
  @RequestMapping(path = "/user/sponsor", method = POST)
  @ResponseStatus(HttpStatus.CREATED)
  public UserAddDTO addSponsor(@Valid @RequestBody SponsorAddCommand command) {
    return userFacade.addSponsor(command);
  }

  @ApiOperation(value = "更新资助人", notes = "", httpMethod = "PUT")
  @RequestMapping(path = "/user/sponsor", method = PUT)
  @ResponseStatus(HttpStatus.OK)
  public ReturnDto updateSponsor(@Valid @RequestBody SponsorUpdateCommand command) {
    userFacade.updateSponsor(command);
    return ReturnDto.builder()
        .codeMessage(new CodeMessage())
        .build();
  }

  @ApiOperation(value = "删除资助人", notes = "", httpMethod = "DELETE")
  @RequestMapping(path = "/user/sponsor/{id}", method = DELETE)
  @ResponseStatus(HttpStatus.OK)
  public ReturnDto deleteSponsor(@PathVariable("id") Long id) {
    userFacade.deleteUser(id);
    return ReturnDto.builder()
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "获取资助人", notes = "", response = VolunteerDetailDto.class, httpMethod = "GET")
  @RequestMapping(path = "/user/sponsor/{id}", method = GET)
  public SponsorDetailDto getSponsor(@PathVariable("id") Long id) {
    return userFacade.getSponsorDetail(id);
  }

  @RoleCheck
  @ApiOperation(value = "获取资助人列表", notes = "", httpMethod = "GET")
  @RequestMapping(path = "/user/sponsors", method = GET)
  public SponsorListDto getSponsors(
      @ApiParam(value = "分页条数") @RequestParam(required = false) Integer pageSize,
      @ApiParam(value = "当前页") @RequestParam(required = false) Integer currentPage,
      @ApiParam(value = "手机号")@RequestParam(required = false) String phone
  ) {
    Pageable pageable = null;
    if (pageSize != null && pageSize != 0 && currentPage != null && currentPage != 0) {
      pageable = new PageRequest(currentPage, pageSize);
    }
    BooleanBuilder conditions = new BooleanBuilder(QUser.user.type.eq(1));
    if(StringUtils.isNotBlank(phone)){
      conditions.and(QUser.user.mobile.eq(phone));
    }
    Page<User> page = userRepository.findAll(conditions, pageable);
    List<SponsorDetailResultDto> dtos = Lists.newArrayList();
    if (CollectionUtils.isNotEmpty(page.getContent())) {
      page.getContent().forEach(user -> {
        dtos.add(UserDTOAssembler.toSponsorResultDto(user));
      });
      return SponsorListDto.builder()
          .codeMessage(new CodeMessage())
          .resultDtos(dtos)
          .build();
    } else {
      return SponsorListDto.builder()
          .codeMessage(new CodeMessage())
          .resultDtos(Collections.EMPTY_LIST)
          .build();
    }
  }
}
