/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.homeVisit;

import static com.torch.interfaces.common.ApiPaths.API_CONTEXT_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.google.common.collect.Lists;
import com.torch.application.homeVisit.HomeVisitService;
import com.torch.application.upload.ImageUploadService;
import com.torch.application.upload.PhotoPath;
import com.torch.domain.model.audit.Audit;
import com.torch.domain.model.audit.AuditItem;
import com.torch.domain.model.audit.AuditItemRepository;
import com.torch.domain.model.audit.AuditRepository;
import com.torch.domain.model.homeVisit.HomeVisit;
import com.torch.domain.model.homeVisit.HomeVisitAuditItem;
import com.torch.domain.model.homeVisit.HomeVisitAuditItemRepository;
import com.torch.domain.model.homeVisit.HomeVisitRepository;
import com.torch.domain.model.homeVisit.QHomeVisit;
import com.torch.domain.model.homeVisit.QHomeVisitAuditItem;
import com.torch.domain.model.release.Release;
import com.torch.domain.model.release.ReleaseRepository;
import com.torch.domain.model.student.Student;
import com.torch.domain.model.student.StudentRepository;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.common.facade.dto.ReturnIdDto;
import com.torch.interfaces.common.security.annotation.RoleCheck;
import com.torch.interfaces.homeVisit.dto.AuditChoiced;
import com.torch.interfaces.homeVisit.dto.CreateHomeVisitCommand;
import com.torch.interfaces.homeVisit.dto.HomeList;
import com.torch.interfaces.homeVisit.dto.HomeVisitDetail;
import com.torch.interfaces.homeVisit.dto.HomeVisitListDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
@Api(value = "HomeVistResource", description = "家访录入保存相关api")
public class HomeVistResource {

  private final HomeVisitRepository homeVistRepository;

  private final HomeVisitAuditItemRepository homeVistAuditItemRepository;

  private final HomeVisitService homeVisitService;

  private final AuditRepository auditRepository;

  private final AuditItemRepository auditItemRepository;

  private final ReleaseRepository releaseRepository;

  private final ImageUploadService imageUploadService;

  private final PhotoPath photoPath;

  private final String SERVER_PREFIX = "120.76.191.33";

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  public HomeVistResource(final HomeVisitRepository homeVistRepository,
      final HomeVisitAuditItemRepository homeVistAuditItemRepository,
      final HomeVisitService homeVisitService,
      final AuditRepository auditRepository,
      final AuditItemRepository auditItemRepository,
      final ReleaseRepository releaseRepository,
      final ImageUploadService imageUploadService,
      final PhotoPath photoPath
  ) {
    this.homeVistRepository = homeVistRepository;
    this.homeVistAuditItemRepository = homeVistAuditItemRepository;
    this.homeVisitService = homeVisitService;
    this.auditRepository = auditRepository;
    this.auditItemRepository = auditItemRepository;
    this.releaseRepository = releaseRepository;
    this.imageUploadService = imageUploadService;
    this.photoPath = photoPath;
  }

  @RoleCheck
  @ApiOperation(value = "保存家访内容", notes = "", response = Long.class, httpMethod = "POST")
  @RequestMapping(path = "/homeVisit", method = POST)
  @ResponseStatus(HttpStatus.CREATED)
  public ReturnIdDto addRelease(@Valid @RequestBody CreateHomeVisitCommand command) {
    command.setApplicationForms(uploadVistPhoto(command.getApplicationForms()));
    command.setFamilyPhotos(uploadVistPhoto(command.getFamilyPhotos()));
    command.setHomePhotos(uploadVistPhoto(command.getHomePhotos()));
    command.setInteractivePhotos(uploadVistPhoto(command.getInteractivePhotos()));
    command.setStudentPhotos(uploadVistPhoto(command.getStudentPhotos()));
    command.setHomeFeaturePhotos(uploadVistPhoto(command.getHomeFeaturePhotos()));
    homeVisitService.saveHomeVisit(command);
    return ReturnIdDto.builder()
        .codeMessage(new CodeMessage())
        .id(command.getStudentId())
        .build();
  }

  private List<String> uploadVistPhoto(List<String> photos) {
    List<String> applicationForms = Lists.newArrayList();
    if (CollectionUtils.isNotEmpty(photos)) {
      photos.forEach(str -> {
        String path = imageUploadService.GenerateImage(str, photoPath.getVisit());
        applicationForms.add(SERVER_PREFIX+path);
      });
    }
    return applicationForms;
  }

  @RoleCheck
  @ApiOperation(value = "根据学生ID查询家访列表", notes = "", response = HomeList.class, httpMethod = "GET")
  @RequestMapping(path = "/homeVisits/{studentId}", method = GET)
  @ResponseStatus(HttpStatus.OK)
  public HomeList getList(@PathVariable("studentId") Long studentId) {

    List<HomeVisit> homevisits = (List<HomeVisit>) homeVistRepository
        .findAll(QHomeVisit.homeVisit.studentId.eq(studentId));

    homevisits = homevisits.stream()
        .filter(homeVisit -> homeVisit.getHomeVisitTime() != null)
        .sorted((visit1, visit2) ->
            visit1.getHomeVisitTime().compareTo(visit2.getHomeVisitTime())
        ).collect(Collectors.toList());
    List<HomeVisitListDto> homeVisitListDtos = Lists.newArrayList();
    homevisits.forEach(homeVisit -> {
      homeVisitListDtos.add(HomeVisitListDto.builder()
          .homeVisitId(homeVisit.getId())
          .homeVisitor(homeVisit.getHomeVistor())
          .homeVisitTime(
              homeVisit.getHomeVisitTime() == null ? "" : homeVisit.getHomeVisitTime().toString("yyyy-MM-dd"))
          .build());
    });
    return HomeList.builder()
        .codeMessage(new CodeMessage())
        .homeVisitListDtos(homeVisitListDtos)
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "根据家访ID查询家访详细", notes = "", response = HomeList.class, httpMethod = "GET")
  @RequestMapping(path = "/homeVisit/{id}", method = GET)
  @ResponseStatus(HttpStatus.OK)
  public HomeVisitDetail getHomevisitDetail(@PathVariable("id") Long id) {

    HomeVisit homevisit = homeVistRepository.findOne(id);
    if (homevisit == null) {
      return HomeVisitDetail.builder()
          .codeMessage(new CodeMessage())
          .build();
    }
    Student student = studentRepository.findOne(homevisit.getStudentId() == null ? 0l : homevisit.getStudentId());
    List<AuditChoiced> auditChoiceds = Lists.newArrayList();

    Release release = releaseRepository.findOne(homevisit.getBatchId());

    List<Audit> audits = (List<Audit>) auditRepository.findAll();
    List<HomeVisitAuditItem> visitAuditItems = (List<HomeVisitAuditItem>) homeVistAuditItemRepository
        .findAll(QHomeVisitAuditItem.homeVisitAuditItem.homeVisitId.eq(id));
    visitAuditItems.forEach(visitAuditItem -> {
      AuditItem auditItem = auditItemRepository.findOne(visitAuditItem.getAuditItemId());
      Optional<Audit> op = audits.stream()
          .filter(audit -> audit.getId().equals(auditItem.getAuditId()))
          .findFirst();
      if (op.isPresent()) {
        auditChoiceds.add(AuditChoiced.builder()
            .options(auditItem.getOptions())
            .title(op.get().getTitle())
            .build());
      }
    });
    return HomeVisitDetail.builder()
        .codeMessage(new CodeMessage())
        .studentName(student == null ? "" : student.getName())
        .homeVisitTime(
            homevisit.getHomeVisitTime() == null ? "" : homevisit.getHomeVisitTime().toString("yyyy-MM-dd"))
        .createTime(homevisit.getCreateTime() == null ? "" : homevisit.getCreateTime().toString("yyyy-MM-dd"))
        .applicationForms(buildPhoto(homevisit.getApplicationForm()))
        .auditChoiceds(auditChoiceds)
        .familyPhotos(buildPhoto(homevisit.getFamilyPhoto()))
        .homePhotos(buildPhoto(homevisit.getHomePhoto()))
        .InteractivePhotos(buildPhoto(homevisit.getInteractivePhoto()))
        .studentPhotos(buildPhoto(homevisit.getStudentPhoto()))
        .homeFeaturePhoto(buildPhoto(homevisit.getHomeFeaturePhoto()))
        .visitInfo(homevisit.getVisitInfo())
        .batchNo(release == null ? "" : release.getBatchNo())
        .build();
  }

  private List<String> buildPhoto(String photo) {
    if (StringUtils.isBlank(photo)) {
      return Collections.EMPTY_LIST;
    }
    String[] arr = photo.split(",");
    List<String> list = Lists.newArrayList();
    for (int i = 0; i < arr.length; i++) {
      if (!StringUtils.isBlank(arr[i])) {
        list.add(arr[i]);
      }

    }
    return list;
  }

}
