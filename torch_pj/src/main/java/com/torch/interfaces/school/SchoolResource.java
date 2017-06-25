/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.school;

import static com.torch.interfaces.common.ApiPaths.API_CONTEXT_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import com.querydsl.core.BooleanBuilder;
import com.torch.application.school.SchoolService;
import com.torch.domain.model.school.QSchool;
import com.torch.domain.model.school.School;
import com.torch.domain.model.school.SchoolRepository;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.common.facade.dto.ReturnDto;
import com.torch.interfaces.common.security.annotation.RoleCheck;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import javax.validation.Valid;
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

/**
 * @author yuanj 2017-01-12 16:41:05
 * @version 1.0
 */
@RestController
@RequestMapping(path = API_CONTEXT_PATH, produces = APPLICATION_JSON_VALUE)
@Api(value = "SchoolResource", description = "学校相关api")
public class SchoolResource {

  private final SchoolRepository schoolRepository;

  private final SchoolService schoolService;

  @Autowired
  public SchoolResource(final SchoolRepository schoolRepository,
      final SchoolService schoolService) {
    this.schoolRepository = schoolRepository;
    this.schoolService = schoolService;
  }

  @RoleCheck
  @ApiOperation(value = "新增学校", notes = "", response = School.class, httpMethod = "POST")
  @RequestMapping(path = "/school", method = POST)
  @ResponseStatus(HttpStatus.CREATED)
  public SchoolDto addSchool(@Valid @RequestBody AddSchoolCommand command) {
    return SchoolDto.builder()
        .school(schoolService.addSchool(command))
        .codeMessage(new CodeMessage())
        .build();
  }

  @ApiOperation(value = "更新学校", notes = "", httpMethod = "PUT")
  @RequestMapping(path = "/school", method = PUT)
  @ResponseStatus(HttpStatus.OK)
  public SchoolDto updateSchool(@Valid @RequestBody UpdateSchoolCommand command) {
    return SchoolDto.builder()
        .school(schoolService.updateSchool(command))
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "删除学校", notes = "", httpMethod = "DELETE")
  @RequestMapping(path = "/school/{id}", method = DELETE)
  @ResponseStatus(HttpStatus.OK)
  public ReturnDto deleteSchool(@PathVariable("id") Long id) {
    schoolService.deleteSchool(id);
    return ReturnDto.builder()
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "获取学校详细", notes = "", response = School.class, httpMethod = "GET")
  @RequestMapping(path = "/school/{id}", method = GET)
  public SchoolDto getSchoolDetail(@PathVariable("id") Long id) {
    return SchoolDto.builder()
        .school(schoolService.getSchoolDetail(id))
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "获取学校列表", notes = "", httpMethod = "GET")
  @RequestMapping(path = "/schools", method = GET)
  public SchoolListDto getVolunteers(
      @ApiParam(value = "分页条数") @RequestParam(required = false) int pageSize,
      @ApiParam(value = "当前页") @RequestParam(required = false) int currentPage,
      @ApiParam(value = "学校名称") @RequestParam(required = false) String schoolName
  ) {

    Pageable pageable = new PageRequest(currentPage, pageSize);
    if(pageSize==0 || currentPage==0){
      pageable=null;
    }
    BooleanBuilder conditions = new BooleanBuilder();
    conditions.and(QSchool.school.isNotNull());
    if(StringUtils.isNotBlank(schoolName)){
      conditions.and(QSchool.school.schoolName.eq(schoolName));
    }
    Page<School> page = schoolRepository.findAll(conditions, pageable);
    return SchoolListDto.builder()
        .schools(page.getContent())
        .codeMessage(new CodeMessage())
        .build();
  }
}
