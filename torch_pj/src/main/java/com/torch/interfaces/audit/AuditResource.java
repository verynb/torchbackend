/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.audit;

import static com.torch.interfaces.common.ApiPaths.API_CONTEXT_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import com.google.common.collect.Lists;
import com.torch.domain.model.audit.Audit;
import com.torch.domain.model.audit.AuditItem;
import com.torch.domain.model.audit.AuditItemRepository;
import com.torch.domain.model.audit.AuditRepository;
import com.torch.domain.model.audit.QAuditItem;
import com.torch.domain.model.gradeMoney.DictGradeMoney;
import com.torch.domain.model.gradeMoney.DictGradeMoneyRepository;
import com.torch.interfaces.audit.dto.AuditDto;
import com.torch.interfaces.audit.dto.AuditItemDto;
import com.torch.interfaces.audit.dto.AuditListDto;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.common.security.annotation.RoleCheck;
import com.torch.interfaces.gradeMoney.dto.ListDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yuanj 2017-01-12 16:41:05
 * @version 1.0
 */
@RestController
@RequestMapping(path = API_CONTEXT_PATH, produces = APPLICATION_JSON_VALUE)
@Api(value = "AuditResource", description = "审核标准查询相关api")
public class AuditResource {

  private final AuditRepository auditRepository;

  private final AuditItemRepository auditItemRepository;

  @Autowired
  public AuditResource(final AuditRepository auditRepository,
      final AuditItemRepository auditItemRepository) {
    this.auditRepository = auditRepository;
    this.auditItemRepository = auditItemRepository;
  }

//  @RoleCheck
  @ApiOperation(value = "查询所有审核项", notes = "", response = AuditListDto.class, httpMethod = "GET")
  @RequestMapping(path = "/audits", method = GET)
  @ResponseStatus(HttpStatus.OK)
  public AuditListDto getAudits() {
    AuditListDto dto = AuditListDto.builder()
        .auditDtoList(Lists.newArrayList())
        .codeMessage(new CodeMessage())
        .build();
    List<Audit> audits = (List<Audit>) auditRepository.findAll();
    if (CollectionUtils.isEmpty(audits)) {
       return dto;
    }
    audits.forEach(audit -> {
      List<AuditItem> auditItems = auditItemRepository.findByAuditId(audit.getId());
      AuditDto auditDto = AuditDto.builder()
          .id(audit.getId())
          .title(audit.getTitle())
          .auditItemList(Lists.newArrayList())
          .build();
      auditItems.forEach(item -> {
        AuditItemDto itemDto = AuditItemDto.builder()
            .auditId(item.getAuditId())
            .options(item.getOptions())
            .scores(item.getScores())
            .build();
        auditDto.getAuditItemList().add(itemDto);
      });
      dto.getAuditDtoList().add(auditDto);
    });
    return dto;
  }
}
