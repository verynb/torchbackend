/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.contribute;

import static com.torch.interfaces.common.ApiPaths.API_CONTEXT_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import com.google.common.collect.Lists;
import com.torch.application.contribute.ContributeService;
import com.torch.domain.model.contribute.ContributeRecord;
import com.torch.domain.model.contribute.ContributeRecordRepository;
import com.torch.domain.model.contribute.QContributeRecord;
import com.torch.domain.model.contribute.QRemittance;
import com.torch.domain.model.contribute.Remittance;
import com.torch.domain.model.contribute.RemittanceRepository;
import com.torch.domain.model.homeVisit.HomeVisit;
import com.torch.domain.model.homeVisit.HomeVisitRepository;
import com.torch.domain.model.homeVisit.QHomeVisit;
import com.torch.domain.model.release.QReleaseStudent;
import com.torch.domain.model.release.Release;
import com.torch.domain.model.release.ReleaseRepository;
import com.torch.domain.model.release.ReleaseStudent;
import com.torch.domain.model.release.ReleaseStudentRepository;
import com.torch.domain.model.returnVisit.ReturnVisit;
import com.torch.domain.model.returnVisit.ReturnVisitRepository;
import com.torch.domain.model.school.School;
import com.torch.domain.model.school.SchoolRepository;
import com.torch.domain.model.student.Student;
import com.torch.domain.model.student.StudentRepository;
import com.torch.domain.model.user.User;
import com.torch.domain.model.user.UserRepository;
import com.torch.interfaces.common.exceptions.TorchException;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.common.facade.dto.ReturnDto;
import com.torch.interfaces.common.security.Session;
import com.torch.interfaces.common.security.annotation.RoleCheck;
import com.torch.interfaces.contribute.dto.CancelSubscribeDto;
import com.torch.interfaces.contribute.dto.CreateRemittanceDto;
import com.torch.interfaces.contribute.dto.CreateSubscribeDto;
import com.torch.interfaces.contribute.dto.HomeVisitDto;
import com.torch.interfaces.contribute.dto.RemittanceDetailDto;
import com.torch.interfaces.contribute.dto.SubscribeDetailDto;
import com.torch.interfaces.contribute.dto.SubscribeDto;
import com.torch.interfaces.contribute.dto.SubscribeListDto;
import com.torch.interfaces.returnVisit.dto.ReturnVisitDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanj 2017-01-12 16:41:05
 * @version 1.0
 */
@RestController
@RequestMapping(path = API_CONTEXT_PATH, produces = APPLICATION_JSON_VALUE)
@Api(value = "ContributeResource", description = "认捐相关api")
public class ContributeResource {

  private final ContributeService contributeService;

  private final ContributeRecordRepository contributeRecordRepository;

  private final StudentRepository studentRepository;

  private final SchoolRepository schoolRepository;

  private final ReleaseRepository releaseRepository;

  private final ReleaseStudentRepository releaseStudentRepository;

  private final HomeVisitRepository homeVistRepository;

  private final RemittanceRepository remittanceRepository;

  private final ReturnVisitRepository returnVisitRepository;

  private final UserRepository userRepository;

  @Autowired
  public ContributeResource(final ContributeService contributeService,
      final ContributeRecordRepository contributeRecordRepository,
      final StudentRepository studentRepository,
      final SchoolRepository schoolRepository,
      final ReleaseRepository releaseRepository,
      final ReleaseStudentRepository releaseStudentRepository,
      final HomeVisitRepository homeVistRepository,
      final RemittanceRepository remittanceRepository,
      final ReturnVisitRepository returnVisitRepository,
      final UserRepository userRepository) {
    this.contributeService = contributeService;
    this.contributeRecordRepository = contributeRecordRepository;
    this.studentRepository = studentRepository;
    this.schoolRepository = schoolRepository;
    this.releaseRepository = releaseRepository;
    this.releaseStudentRepository = releaseStudentRepository;
    this.homeVistRepository = homeVistRepository;
    this.remittanceRepository = remittanceRepository;
    this.returnVisitRepository = returnVisitRepository;
    this.userRepository = userRepository;
  }

  @RoleCheck
  @ApiOperation(value = "在线认捐接口", notes = "", httpMethod = "PUT")
  @RequestMapping(path = "/subscribe", method = PUT)
  @ResponseStatus(HttpStatus.OK)
  public ReturnDto createSubscribe(@Valid @RequestBody CreateSubscribeDto dto) {
    contributeService.contribute(dto.getBatchId(), dto.getStudentIds());
    return ReturnDto.builder()
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "取消捐赠接口", notes = "", httpMethod = "PUT")
  @RequestMapping(path = "/cancelSubscribe", method = PUT)
  @ResponseStatus(HttpStatus.OK)
  public ReturnDto cancelSubscribe(@Valid @RequestBody CancelSubscribeDto dto) {
    contributeService.cancelContribute(dto);
    return ReturnDto.builder()
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "汇款录入", notes = "", httpMethod = "POST")
  @RequestMapping(path = "/remittance", method = POST)
  @ResponseStatus(HttpStatus.OK)
  public ReturnDto createRemittance(@RequestBody CreateRemittanceDto dto) {
    contributeService.createRemittance(dto);
    return ReturnDto.builder()
        .codeMessage(new CodeMessage())
        .build();
  }


  @RoleCheck
  @ApiOperation(value = "查询认捐列表", notes = "", httpMethod = "GET")
  @RequestMapping(path = "/subscribes", method = GET)
  @ResponseStatus(HttpStatus.OK)
  public SubscribeListDto getSubscribes() {
    List<SubscribeDto> subscribeDtos = Lists.newArrayList();
    List<ContributeRecord> crs = (List<ContributeRecord>) contributeRecordRepository
        .findAll(QContributeRecord.contributeRecord.contributeId.eq(Session.getUserId()));
    crs.forEach(cr -> {
      Student student = studentRepository.findOne(cr.getStudentId() == null ? 0L : cr.getStudentId());
      SubscribeDto dto = SubscribeDto.builder()
          .studentId(student==null?0l:student.getId())
          .studentName(student == null ? "" : student.getName())
          .subscribeId(cr.getId())
          .subscribeTime(cr.getCreateTime() == null ? "" : cr.getCreateTime().toString("yyyy-MM-dd"))
          .build();
      subscribeDtos.add(dto);
    });
    return SubscribeListDto.builder()
        .codeMessage(new CodeMessage())
        .subscribeDtos(subscribeDtos)
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "根据认捐ID查询认捐详细", notes = "", httpMethod = "GET")
  @RequestMapping(path = "/subscribe/{id}", method = GET)
  @ResponseStatus(HttpStatus.OK)
  public SubscribeDetailDto getSubscribeDetail(@PathVariable("id") Long id) {

    List<HomeVisitDto> homeVisitDtos = Lists.newArrayList();

    List<ReturnVisitDto> returnDtos = Lists.newArrayList();

    ContributeRecord cr = contributeRecordRepository
        .findOne(id);
    if (cr == null) {
      throw new TorchException("无效的认捐ID");
    }
    Student student = studentRepository.findOne(cr.getStudentId() == null ? 0L : cr.getStudentId());
    School school = schoolRepository.findOne(student == null ? 0L : student.getSchoolId());

    Release release = releaseRepository.findOne(cr.getBatchId() == null ? 0l : cr.getBatchId());

    List<ReleaseStudent> releaseStudents = (List<ReleaseStudent>) releaseStudentRepository.findAll(
        QReleaseStudent.releaseStudent.batchId.eq(cr.getBatchId() == null ? 0l : cr.getBatchId())
            .and(QReleaseStudent.releaseStudent.studentId.eq(cr.getStudentId() == null ? 0L : cr.getStudentId())));

    List<HomeVisit> homevisits = (List<HomeVisit>) homeVistRepository
        .findAll(QHomeVisit.homeVisit.studentId.eq(cr.getStudentId() == null ? 0L : cr.getStudentId()));

    List<ReturnVisit> returnVisits = (List<ReturnVisit>) returnVisitRepository
        .findByStudentId(cr.getStudentId() == null ? 0L : cr.getStudentId());

    homevisits.forEach(home -> {

      homeVisitDtos.add(HomeVisitDto.builder()
          .homeVisitId(home.getId())
          .homeVisitTime(home.getHomeVisitTime() == null ? "" : home.getHomeVisitTime().toString("yyyy-MM-dd"))
          .homeVistor(home.getHomeVistor())
          .build());
    });

    returnVisits.forEach(returnVisit -> {
      User user = userRepository.findOne(returnVisit.getReturnVisitor() == null ? 0l : returnVisit.getReturnVisitor());

      returnDtos.add(ReturnVisitDto.builder()
          .returnVisitId(returnVisit.getId())
          .returnVisitTime(
              returnVisit.getReturnTime() == null ? "" : returnVisit.getReturnTime().toString("yyyy-MM-dd"))
          .returnVistor(user == null ? "" : user.getName())
          .build());

    });

    List<Remittance> remittances = (List<Remittance>) remittanceRepository
        .findAll(QRemittance.remittance.contributeId.eq(Session.getUserId())
            .and(QRemittance.remittance.studentId.eq(cr.getStudentId() == null ? 0L : cr.getStudentId())));

    List<RemittanceDetailDto> remittanceDetailDtos=Lists.newArrayList();
    remittances.forEach(remittance -> {
      remittanceDetailDtos.add(RemittanceDetailDto.builder()
          .contributeId(remittance.getContributeId())
          .remark(remittance.getRemark())
          .remittanceMoney(remittance.getRemittanceMoney())
          .remittanceTime(remittance.getRemittanceTime()==null?"":remittance.getRemittanceTime().toString("yyyy-MM-dd"))
          .studentId(remittance.getStudentId())
          .build());
    });
    return SubscribeDetailDto.builder()
        .codeMessage(new CodeMessage())
        .homeVisitDtos(homeVisitDtos)
        .returnVisitDtos(returnDtos)
        .needMoney(CollectionUtils.isEmpty(releaseStudents) ? 0 : releaseStudents.get(0).getNeedMoney())
        .releaseTime(release == null ? ""
            : release.getLastUpdateTime() == null ? "" : release.getLastUpdateTime().toString("yyyy-MM-dd"))
        .remittances(remittanceDetailDtos)
        .studentId(student == null ? 0l :student.getId())
        .schoolName(school == null ? "" : school.getSchoolName())
        .studentAdree(student == null ? "" : student.getAddress())
        .studentAge(student == null ? null : student.getAge())
        .studentName(student == null ? null : student.getName())
        .build();
  }
}
