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
import com.torch.domain.model.audit.AuditItem;
import com.torch.domain.model.audit.AuditItemRepository;
import com.torch.domain.model.homeVisit.HomeVisitAuditItem;
import com.torch.domain.model.homeVisit.HomeVisitAuditItemRepository;
import com.torch.domain.model.homeVisit.QHomeVisitAuditItem;
import com.torch.domain.model.release.QRelease;
import com.torch.domain.model.release.QReleaseStudent;
import com.torch.domain.model.release.Release;
import com.torch.domain.model.release.ReleaseRepository;
import com.torch.domain.model.release.ReleaseStudent;
import com.torch.domain.model.release.ReleaseStudentRepository;
import com.torch.domain.model.school.SchoolRepository;
import com.torch.domain.model.student.Student;
import com.torch.domain.model.student.StudentRepository;
import com.torch.domain.model.user.DictVolunteerRole;
import com.torch.domain.model.user.DictVolunteerRoleRepository;
import com.torch.domain.model.user.QUser;
import com.torch.domain.model.user.User;
import com.torch.domain.model.user.UserRepository;
import com.torch.interfaces.common.exceptions.TorchException;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.common.facade.dto.ReturnDto;
import com.torch.interfaces.common.facade.dto.ReturnIdDto;
import com.torch.interfaces.common.security.Session;
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
import java.util.Objects;
import java.util.stream.Collectors;
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

  private final HomeVisitAuditItemRepository homeVisitAuditItemRepository;

  private final AuditItemRepository auditItemRepository;

  private final SchoolRepository schoolRepository;

  private final UserRepository userRepository;

  private final DictVolunteerRoleRepository dictVolunteerRoleRepository;

  @Autowired
  public ReleaseResource(final ReleaseService releaseService,
      final ReleaseRepository releaseRepository,
      final ReleaseStudentRepository releaseStudentRepository,
      final StudentRepository studentRepository,
      final HomeVisitAuditItemRepository homeVisitAuditItemRepository,
      final AuditItemRepository auditItemRepository,
      final SchoolRepository schoolRepository,
      final UserRepository userRepository,
      final DictVolunteerRoleRepository dictVolunteerRoleRepository) {
    this.releaseService = releaseService;
    this.releaseRepository = releaseRepository;
    this.releaseStudentRepository = releaseStudentRepository;
    this.studentRepository = studentRepository;
    this.homeVisitAuditItemRepository = homeVisitAuditItemRepository;
    this.auditItemRepository = auditItemRepository;
    this.schoolRepository = schoolRepository;
    this.userRepository = userRepository;
    this.dictVolunteerRoleRepository = dictVolunteerRoleRepository;
  }


  @RoleCheck
  @ApiOperation(value = "查询已结发布的批次", notes = "", response = ReleaseDto.class, httpMethod = "GET")
  @RequestMapping(path = "/release/released", method = GET)
  @ResponseStatus(HttpStatus.OK)
  public ReleaseDto getReleasedList() {
    List<Release> releases = (List<Release>) releaseRepository
        .findAll(QRelease.release.status.eq(2).or(QRelease.release.status.eq(1)));
    List<ReleaseBatch> resultList = Lists.newArrayList();
    releases.forEach(re -> {
      resultList.add(ReleaseBatch.builder()
          .id(re.getId())
          .batchNo(re.getBatchNo())
          .city(re.getCity())
          .province(re.getProvince())
          .build());
    });
    return ReleaseDto.builder()
        .releases(resultList)
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "查询草稿的批次", notes = "", response = ReleaseDto.class, httpMethod = "GET")
  @RequestMapping(path = "/release", method = GET)
  @ResponseStatus(HttpStatus.OK)
  public ReleaseDto addRelease() {
    List<Release> releases = (List<Release>) releaseRepository
        .findAll(QRelease.release.status.eq(0).or(QRelease.release.status.eq(1)));
    User user = userRepository.findOne(Session.getUserId());
    DictVolunteerRole role = dictVolunteerRoleRepository.findOne(user == null ? 0l : user.getRoleId());
    if (role != null && role.getRoleCode().equals("teacher")) {
      if (CollectionUtils.isNotEmpty(releases) && StringUtils.isNotBlank(user.getProvince())) {
        releases = releases.stream().filter(release -> release.getProvince().equals(user.getProvince()))
            .collect(Collectors.toList());
      }
      if (CollectionUtils.isNotEmpty(releases) && StringUtils.isNotBlank(user.getCity())) {
        releases = releases.stream().filter(release -> release.getCity().equals(user.getCity()))
            .collect(Collectors.toList());
      }
    }
    List<ReleaseBatch> resultList = Lists.newArrayList();
    releases.forEach(re -> {
      resultList.add(ReleaseBatch.builder()
          .id(re.getId())
          .batchNo(re.getBatchNo())
          .city(re.getCity())
          .province(re.getProvince())
          .build());
    });
    return ReleaseDto.builder()
        .releases(resultList)
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "创建发布", notes = "", response = ReturnIdDto.class, httpMethod = "POST")
  @RequestMapping(path = "/release", method = POST)
  @ResponseStatus(HttpStatus.CREATED)
  public ReturnIdDto addRelease(@Valid @RequestBody AddReleaseCommand command) {
    return ReturnIdDto.builder()
        .id(releaseService.addRelease(command))
        .codeMessage(new CodeMessage())
        .build();
  }


  @RoleCheck
  @ApiOperation(value = "编辑发布批次省市", notes = "", response = ReturnIdDto.class, httpMethod = "PUT")
  @RequestMapping(path = "/release", method = PUT)
  @ResponseStatus(HttpStatus.CREATED)
  public ReturnIdDto addRelease(@Valid @RequestBody UpdateReleaseCommand command) {
    return ReturnIdDto.builder()
        .id(releaseService.updateRelease(command))
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
  @ResponseStatus(HttpStatus.OK)
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
      @ApiParam(value = "发布项学生IDS") @RequestBody List<ReleaseStudentDto> releaseStudentIds) {

   /* if (releaseRepository.count(QRelease.release.status.eq(2)) > 1) {
      throw new TorchException("当前有已经发布的批次");
    }*/
    releaseService.release(batchId, releaseStudentIds);
    return ReturnDto.builder()
        .codeMessage(new CodeMessage())
        .build();
  }





  @RoleCheck
  @ApiOperation(value = "根据{开始时间，结束时间，省份，市}查询发布", notes = "", httpMethod = "POST")
  @RequestMapping(path = "/releases", method = POST)
  public ReleaseDto getReleases(@Valid @RequestBody ReleaseSearchDto dto) {
    Pageable pageable = new PageRequest(dto.getCurrentPage(), dto.getPageSize());
    Page<Release> releases = releaseRepository.findAll(dto.toPredicate(), pageable);
    if (CollectionUtils.isEmpty(releases.getContent())) {
      return ReleaseDto.builder()
          .releases(Collections.EMPTY_LIST)
          .codeMessage(new CodeMessage())
          .build();
    }
    List<ReleaseBatch> resultList = Lists.newArrayList();
    for (Release re : releases) {
      resultList.add(ReleaseBatch.builder()
          .id(re.getId())
          .batchNo(re.getBatchNo())
          .city(re.getCity())
          .province(re.getProvince())
          .build());
    }
    User user = userRepository.findOne(Session.getUserId());
    DictVolunteerRole role = dictVolunteerRoleRepository.findOne(user == null ? 0l : user.getRoleId());
    if (role != null && role.getRoleCode().equals("teacher")) {
      if (CollectionUtils.isNotEmpty(resultList) && StringUtils.isNotBlank(user.getProvince())) {
        resultList = resultList.stream().filter(release -> release.getProvince().equals(user.getProvince()))
            .collect(Collectors.toList());
      }
      if (CollectionUtils.isNotEmpty(resultList) && StringUtils.isNotBlank(user.getCity())) {
        resultList = resultList.stream().filter(release -> release.getCity().equals(user.getCity()))
            .collect(Collectors.toList());
      }
    }
    return ReleaseDto.builder()
        .releases(resultList)
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "根据批次号查询发布", notes = "", httpMethod = "GET")
  @RequestMapping(path = "/release/{batchNo}", method = GET)
  public ReleaseListResultDto getRelease(@PathVariable("batchNo") String batch) {
    List<Release> releases = (List<Release>) releaseRepository
        .findAll(QRelease.release.batchNo.eq(batch));
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
    List<AuditItem> auditItems = (List<AuditItem>) auditItemRepository.findAll();
    List<ReleaseListDto> resultList = Lists.newArrayList();

    releases.forEach(release -> {
      List<ReleaseStudent> releaseStudents = (List<ReleaseStudent>) releaseStudentRepository
          .findAll(QReleaseStudent.releaseStudent.batchId.eq(release.getId()));
      releaseStudents.forEach(releaseStudent -> {
        Student filteredStudent = students.stream()
            .filter(student -> student.getId().equals(releaseStudent.getStudentId()))
            .findFirst()
            .orElse(null);
        resultList.add(toDto(releaseStudent, release, filteredStudent, auditItems));
      });
    });
    return ReleaseListResultDto.builder()
        .releaseList(resultList)
        .codeMessage(new CodeMessage())
        .build();
  }


  @RoleCheck
  @ApiOperation(value = "根据批ID查询发布", notes = "", httpMethod = "GET")
  @RequestMapping(path = "/release/id", method = GET)
  public ReleaseListResultDto getRelease(Long id) {
    Release release = releaseRepository.findOne(id);
    if (Objects.isNull(release)) {
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
    List<AuditItem> auditItems = (List<AuditItem>) auditItemRepository.findAll();
    List<ReleaseListDto> resultList = Lists.newArrayList();
    List<ReleaseStudent> releaseStudents = (List<ReleaseStudent>) releaseStudentRepository
        .findAll(QReleaseStudent.releaseStudent.batchId.eq(release.getId()));
    releaseStudents.forEach(releaseStudent -> {
      /*if (releaseStudent.getStatus() != null && releaseStudent.getStatus() == 5) {
        return;
      }*/
      Student filteredStudent = students.stream()
          .filter(student -> student.getId().equals(releaseStudent.getStudentId()))
          .findFirst()
          .orElse(null);
      resultList.add(toDto(releaseStudent, release, filteredStudent, auditItems));
    });
    return ReleaseListResultDto.builder()
        .releaseList(resultList)
        .codeMessage(new CodeMessage())
        .build();
  }


  @RoleCheck
  @ApiOperation(value = "根据批ID查询发布", notes = "", httpMethod = "GET")
  @RequestMapping(path = "/ableReleases/id", method = GET)
  public ReleaseListResultDto getableReleases(Long id) {
    Release release = releaseRepository.findOne(id);
    if (Objects.isNull(release)) {
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
    List<AuditItem> auditItems = (List<AuditItem>) auditItemRepository.findAll();
    List<ReleaseListDto> resultList = Lists.newArrayList();
    List<ReleaseStudent> releaseStudents = (List<ReleaseStudent>) releaseStudentRepository
        .findAll(QReleaseStudent.releaseStudent.batchId.eq(release.getId()));
    releaseStudents.forEach(releaseStudent -> {
      if (releaseStudent.getStatus() != null && releaseStudent.getStatus() == 5) {
        return;
      }
      Student filteredStudent = students.stream()
          .filter(student -> student.getId().equals(releaseStudent.getStudentId()))
          .findFirst()
          .orElse(null);
      resultList.add(toDto(releaseStudent, release, filteredStudent, auditItems));
    });
    return ReleaseListResultDto.builder()
        .releaseList(resultList)
        .codeMessage(new CodeMessage())
        .build();
  }



  private ReleaseListDto toDto(ReleaseStudent releaseStudent, Release release, Student student,
      List<AuditItem> auditItems) {
    if (release == null || student == null) {
      return ReleaseListDto.builder().build();
    }
    return ReleaseListDto.builder()
        .releaseStudentId(releaseStudent.getId())
        .needMoney(releaseStudent.getNeedMoney())
        .batchNo(release.getBatchNo())
        .city(release.getCity())
        .createTime(release.getCreateTime().getMillis())
        .grade(student.getGrade())
        .gradeCode(student.getGradeCode())
        .province(release.getProvince())
        .releaseId(release.getId())
        .studentId(student.getId())
        .scores(buildScores(release.getId(), student.getId(), auditItems))
        .studentName(student.getName())
        .address(student.getAddress())
        .age(student.getAge())
        .area(student.getArea())
        .birthday(student.getBirthday())
        .sCity(student.getCity())
        .sProvince(student.getProvince())
        .gender(student.getGender())
        .identityCard(student.getIdentityCard())
        .schoolName(schoolRepository.findOne(student.getSchoolId()) == null ? ""
            : schoolRepository.findOne(student.getSchoolId()).getSchoolName())
        .build();
  }

  private double buildScores(Long batchId, Long studentId, List<AuditItem> auditItems) {
    double scores = 0d;
    List<HomeVisitAuditItem> items = (List<HomeVisitAuditItem>) homeVisitAuditItemRepository
        .findAll(QHomeVisitAuditItem.homeVisitAuditItem.batchId.eq(batchId)
            .and(QHomeVisitAuditItem.homeVisitAuditItem.studentId.eq(studentId)));
    if (CollectionUtils.isEmpty(auditItems) || CollectionUtils.isEmpty(items)) {
      return scores;
    }
    for (HomeVisitAuditItem item : items) {
      AuditItem filterItem = auditItems.stream()
          .filter(auditItem -> auditItem.getId().equals(item.getAuditItemId()))
          .findFirst()
          .orElse(null);
      if (filterItem != null) {
        scores += filterItem.getScores();
      }
    }
    return scores;
  }

  @RoleCheck
  @ApiOperation(value = "放款录入", notes = "", httpMethod = "POST")
  @RequestMapping(path = "/release/credit", method = POST)
  @ResponseStatus(HttpStatus.OK)
  public ReturnDto credit(@RequestBody CreateCreditDto dto) {
    releaseService.createCredit(dto);
    return ReturnDto.builder()
        .codeMessage(new CodeMessage())
        .build();
  }
}
