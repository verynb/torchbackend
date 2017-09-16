/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.returnVisit;

import static com.torch.interfaces.common.ApiPaths.API_CONTEXT_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import com.google.common.collect.Lists;
import com.torch.application.release.ReleaseService;
import com.torch.application.returnVisit.ReturnVisitService;
import com.torch.application.upload.ImageUploadService;
import com.torch.application.upload.PhotoPath;
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
import com.torch.domain.model.returnVisit.ReturnVisit;
import com.torch.domain.model.returnVisit.ReturnVisitRepository;
import com.torch.domain.model.school.SchoolRepository;
import com.torch.domain.model.student.Student;
import com.torch.domain.model.student.StudentRepository;
import com.torch.domain.model.user.User;
import com.torch.domain.model.user.UserRepository;
import com.torch.interfaces.common.exceptions.TorchException;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.common.facade.dto.ReturnDto;
import com.torch.interfaces.common.facade.dto.ReturnIdDto;
import com.torch.interfaces.common.security.annotation.RoleCheck;
import com.torch.interfaces.release.AddReleaseCommand;
import com.torch.interfaces.release.AddReleaseStudentCommand;
import com.torch.interfaces.release.CreateCreditDto;
import com.torch.interfaces.release.ReleaseBatch;
import com.torch.interfaces.release.ReleaseDto;
import com.torch.interfaces.release.ReleaseListDto;
import com.torch.interfaces.release.ReleaseListResultDto;
import com.torch.interfaces.release.ReleaseSearchDto;
import com.torch.interfaces.release.ReleaseStudentDto;
import com.torch.interfaces.returnVisit.dto.CreateReturnVisitDto;
import com.torch.interfaces.returnVisit.dto.ReturnList;
import com.torch.interfaces.returnVisit.dto.ReturnVisitDetail;
import com.torch.interfaces.returnVisit.dto.ReturnVisitListDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.*;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@Api(value = "ReturnVisitResource", description = "回访相关api")
public class ReturnVisitResource {

  private final ReturnVisitService returnVisitService;

  private final ImageUploadService imageUploadService;

  private final ReturnVisitRepository returnVisitRepository;

  private final StudentRepository studentRepository;

  private final UserRepository userRepository;

  @Value("${torch.photo.path.ip}")
  private String SERVER_PREFIX;

  @Autowired
  public ReturnVisitResource(final ReturnVisitService returnVisitService,
      final ImageUploadService imageUploadService,
      final ReturnVisitRepository returnVisitRepository,
      final StudentRepository studentRepository,
      final UserRepository userRepository) {
    this.returnVisitService = returnVisitService;
    this.imageUploadService = imageUploadService;
    this.returnVisitRepository = returnVisitRepository;
    this.studentRepository = studentRepository;
    this.userRepository = userRepository;
  }


  @RoleCheck
  @ApiOperation(value = "新增回访记录,次接口需要登录否则去不到回访人会报错", notes = "", response = ReturnIdDto.class, httpMethod = "POST")
  @RequestMapping(path = "/returnVisit", method = POST)
  @ResponseStatus(HttpStatus.OK)
  public ReturnIdDto createReturnVisit(@Valid @RequestBody CreateReturnVisitDto dto) {
    if (CollectionUtils.isNotEmpty(dto.getStudentPhotos())) {
      dto.setStudentPhotos(uploadVistPhoto(dto.getStudentPhotos()));
    }
    if (CollectionUtils.isNotEmpty(dto.getFamilyPhotos())) {
      dto.setFamilyPhotos(uploadVistPhoto(dto.getFamilyPhotos()));
    }
    if (CollectionUtils.isNotEmpty(dto.getEnvironmentPhotos())) {
      dto.setEnvironmentPhotos(uploadVistPhoto(dto.getEnvironmentPhotos()));
    }
    return ReturnIdDto.builder()
        .codeMessage(new CodeMessage())
        .id(returnVisitService.createReturnVisti(dto))
        .build();
  }


  @RoleCheck
  @ApiOperation(value = "查询回访记录详细", notes = "", response = ReturnVisitDetail.class, httpMethod = "GET")
  @RequestMapping(path = "/returnVisit/{id}", method = GET)
  @ResponseStatus(HttpStatus.OK)
  public ReturnVisitDetail getReturnVisitDetail(@PathVariable("id") long id) {
    return returnVisitService.getDetail(id);
  }

  private List<String> uploadVistPhoto(List<String> photos) {
    List<String> visitPhotos = Lists.newArrayList();
    if (CollectionUtils.isNotEmpty(photos)) {
      photos.forEach(str -> {
        String path = imageUploadService.GenerateImage(str, PhotoPath.PHOTO_PATH);
        visitPhotos.add(SERVER_PREFIX + path);
      });
    }
    return visitPhotos;
  }


  @RoleCheck
  @ApiOperation(value = "查询回访列表", notes = "", response = ReturnList.class, httpMethod = "GET")
  @RequestMapping(path = "/returnVisits/{studentId}", method = GET)
  @ResponseStatus(HttpStatus.OK)
  public ReturnList getReturnVisits(@PathVariable("studentId") Long studentId) {
    List<ReturnVisitListDto> returnVisitListDtoList = Lists.newArrayList();
    Student s = studentRepository.findOne(studentId);
    List<ReturnVisit> lists = returnVisitRepository.findByStudentId(studentId);
    if (CollectionUtils.isNotEmpty(lists)) {
      lists = lists.stream().filter(list -> list.getReturnTime() != null)
          .sorted(Comparator.comparing(ReturnVisit::getReturnTime))
          .collect(Collectors.toList());
    }
    lists.forEach((ReturnVisit list) -> {
      User user = userRepository.findOne(list.getReturnVisitor() == null ? 0 : list.getReturnVisitor());
      returnVisitListDtoList.add(ReturnVisitListDto.builder()
          .returnVisitId(list.getId())
          .returnVisitor(user == null ? "" : user.getName())
          .studentName(s == null ? "" : s.getName())
          .returnVisitTime(list.getReturnTime() == null ? "" : list.getReturnTime().toString("yyyy-MM-dd"))
          .build());
    });
    return ReturnList.builder()
        .codeMessage(new CodeMessage())
        .returnVisitListDtoList(returnVisitListDtoList)
        .build();
  }


}
