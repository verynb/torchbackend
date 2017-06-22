/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.release;

import static com.torch.interfaces.common.ApiPaths.API_CONTEXT_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import com.google.common.collect.Lists;
import com.torch.application.release.ReleaseService;
import com.torch.domain.model.release.QRelease;
import com.torch.domain.model.release.QReleaseStudent;
import com.torch.domain.model.release.Release;
import com.torch.domain.model.release.ReleaseRepository;
import com.torch.domain.model.release.ReleaseStudent;
import com.torch.domain.model.release.ReleaseStudentRepository;
import com.torch.domain.model.student.Student;
import com.torch.domain.model.student.StudentRepository;
import com.torch.domain.model.user.DictVolunteerRole;
import com.torch.domain.model.user.DictVolunteerRoleRepository;
import com.torch.domain.model.user.QUser;
import com.torch.domain.model.user.User;
import com.torch.domain.model.user.UserRepository;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.common.facade.dto.ReturnDto;
import com.torch.interfaces.common.facade.dto.ReturnIdDto;
import com.torch.interfaces.common.security.annotation.RoleCheck;
import com.torch.interfaces.user.command.SponsorAddCommand;
import com.torch.interfaces.user.command.SponsorUpdateCommand;
import com.torch.interfaces.user.command.VolunteerAddCommand;
import com.torch.interfaces.user.command.VolunteerUpdateCommand;
import com.torch.interfaces.user.facade.UserFacade;
import com.torch.interfaces.user.facade.dto.SponsorDetailDto;
import com.torch.interfaces.user.facade.dto.UserAddDTO;
import com.torch.interfaces.user.facade.dto.VolunteerDetailDto;
import com.torch.interfaces.user.internal.assembler.UserDTOAssembler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Collections;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.collections.CollectionUtils;
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
@Api(value = "ReleaseResource", description = "发布相关api")
public class ReleaseResource {

  private final ReleaseService releaseService;

  private final ReleaseRepository releaseRepository;

  private final ReleaseStudentRepository releaseStudentRepository;

  private final StudentRepository studentRepository;

  @Autowired
  public ReleaseResource(final ReleaseService releaseService,
      final ReleaseRepository releaseRepository,
      final ReleaseStudentRepository releaseStudentRepository,
      final StudentRepository studentRepository) {
    this.releaseService = releaseService;
    this.releaseRepository = releaseRepository;
    this.releaseStudentRepository = releaseStudentRepository;
    this.studentRepository = studentRepository;
  }

  @RoleCheck
  @ApiOperation(value = "创建发布", notes = "", response = Long.class, httpMethod = "POST")
  @RequestMapping(path = "/release", method = POST)
  @ResponseStatus(HttpStatus.CREATED)
  public ReturnIdDto addRelease(@Valid @RequestBody AddReleaseCommand command) {
    return ReturnIdDto.builder()
        .id(releaseService.addRelease(command))
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "添加发布批次学生", notes = "", httpMethod = "POST")
  @RequestMapping(path = "/release/students", method = POST)
  @ResponseStatus(HttpStatus.CREATED)
  public ReturnDto addReleaseStudent(@Valid @RequestBody List<AddReleaseStudentCommand> commands) {
    releaseService.addReleaseStudent(commands);
    return ReturnDto.builder()
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "删除发布批次中添加的学生", notes = "", httpMethod = "DELETE")
  @RequestMapping(path = "/release/student/{id}", method = DELETE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ReturnDto deleteReleaseStudent(@PathVariable("id") Long id) {
    releaseService.deleteReleaseStudent(id);
    return ReturnDto.builder()
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "发布批次", notes = "", httpMethod = "PUT")
  @RequestMapping(path = "/release/{batchId}", method = PUT)
  public ReturnDto release(@PathVariable("batchId") Long batchId,
      @ApiParam(value = "发布项学生IDS") @RequestBody List<Long> releaseStudentIds) {
    releaseService.release(batchId, releaseStudentIds);
    return ReturnDto.builder()
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "根据{开始时间，结束时间，省份，市}查询发布", notes = "", httpMethod = "POST")
  @RequestMapping(path = "/releases", method = POST)
  public ReleaseListResultDto getReleases(@Valid @RequestBody ReleaseSearchDto dto) {
    List<Release> releases = (List<Release>) releaseRepository.findAll(dto.toPredicate());
    if (CollectionUtils.isEmpty(releases)) {
      return ReleaseListResultDto.builder()
          .releaseList(Collections.EMPTY_LIST)
          .codeMessage(new CodeMessage())
          .build();
    }
    List<Student> students = (List<Student>) studentRepository.findAll();
    if (CollectionUtils.isEmpty(students)) {
      return ReleaseListResultDto.builder()
          .releaseList(Collections.EMPTY_LIST)
          .codeMessage(new CodeMessage())
          .build();
    }
    List<ReleaseListDto> resultList = Lists.newArrayList();
    releases.forEach(release -> {
      List<ReleaseStudent> releaseStudents = (List<ReleaseStudent>) releaseStudentRepository
          .findAll(QReleaseStudent.releaseStudent.batchId.eq(release.getId()));
      releaseStudents.forEach(releaseStudent -> {
        Student filteredStudent = students.stream()
            .filter(student -> student.getId().equals(releaseStudent.getStudentId()))
            .findFirst()
            .orElse(null);
        resultList.add(toDto(release, filteredStudent));
      });
    });
    return ReleaseListResultDto.builder()
        .releaseList(resultList)
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "根据批次号查询发布", notes = "", httpMethod = "GET")
  @RequestMapping(path = "/release/{batchNo}", method = GET)
  public ReleaseListResultDto getRelease(@PathVariable("batchNo") String batch) {
    List<Release> releases = (List<Release>) releaseRepository.findAll(QRelease.release.batchNo.eq(batch));
    if (CollectionUtils.isEmpty(releases)) {
      return ReleaseListResultDto.builder()
          .releaseList(Collections.EMPTY_LIST)
          .codeMessage(new CodeMessage())
          .build();
    }
    List<Student> students = (List<Student>) studentRepository.findAll();
    if (CollectionUtils.isEmpty(students)) {
      return ReleaseListResultDto.builder()
          .releaseList(Collections.EMPTY_LIST)
          .codeMessage(new CodeMessage())
          .build();
    }

    List<ReleaseListDto> resultList = Lists.newArrayList();

    releases.forEach(release -> {
      List<ReleaseStudent> releaseStudents = (List<ReleaseStudent>) releaseStudentRepository
          .findAll(QReleaseStudent.releaseStudent.batchId.eq(release.getId()));
      releaseStudents.forEach(releaseStudent -> {
        Student filteredStudent = students.stream()
            .filter(student -> student.getId().equals(releaseStudent.getStudentId()))
            .findFirst()
            .orElse(null);
        resultList.add(toDto(release, filteredStudent));
      });
    });
    return ReleaseListResultDto.builder()
        .releaseList(resultList)
        .codeMessage(new CodeMessage())
        .build();
  }

  private ReleaseListDto toDto(Release release, Student student) {
    if (release == null || student == null) {
      return ReleaseListDto.builder().build();
    }
    return ReleaseListDto.builder()
        .batchNo(release.getBatchNo())
        .city(release.getCity())
        .createTime(release.getCreateTime().getMillis())
        .grade(student.getGrade())
        .province(release.getProvince())
        .releaseId(release.getId())
        .studentId(student.getId())
        .studentName(student.getName())
        .build();
  }


}
