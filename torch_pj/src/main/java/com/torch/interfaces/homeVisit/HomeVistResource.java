/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.homeVisit;

import static com.torch.interfaces.common.ApiPaths.API_CONTEXT_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.torch.application.homeVisit.HomeVisitService;
import com.torch.domain.model.homeVisit.HomeVisitAuditItemRepository;
import com.torch.domain.model.homeVisit.HomeVisitRepository;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.common.facade.dto.ReturnDto;
import com.torch.interfaces.common.security.annotation.RoleCheck;
import com.torch.interfaces.homeVisit.dto.CreateHomeVisitCommand;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

  @Autowired
  public HomeVistResource(final HomeVisitRepository homeVistRepository,
      final HomeVisitAuditItemRepository homeVistAuditItemRepository,
      final HomeVisitService homeVisitService) {
    this.homeVistRepository = homeVistRepository;
    this.homeVistAuditItemRepository = homeVistAuditItemRepository;
    this.homeVisitService = homeVisitService;
  }

  @RoleCheck
  @ApiOperation(value = "保存家访内容", notes = "", response = Long.class, httpMethod = "POST")
  @RequestMapping(path = "/homeVisit", method = POST)
  @ResponseStatus(HttpStatus.CREATED)
  public ReturnDto addRelease(@Valid @RequestBody CreateHomeVisitCommand command) {
    homeVisitService.saveHomeVisit(command);
    return ReturnDto.builder()
        .codeMessage(new CodeMessage())
        .build();
  }

}
