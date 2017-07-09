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

import com.torch.application.contribute.ContributeService;
import com.torch.domain.model.gradeMoney.DictGradeMoney;
import com.torch.domain.model.gradeMoney.DictGradeMoneyRepository;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.common.facade.dto.ReturnDto;
import com.torch.interfaces.common.security.annotation.RoleCheck;
import com.torch.interfaces.contribute.dto.CreateRemittanceDto;
import com.torch.interfaces.contribute.dto.SubscribeDto;
import com.torch.interfaces.gradeMoney.dto.ListDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
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
@Api(value = "ContributeResource", description = "认捐相关api")
public class ContributeResource {

  private final ContributeService contributeService;

  @Autowired
  public ContributeResource(final ContributeService contributeService) {
    this.contributeService = contributeService;
  }

  @RoleCheck
  @ApiOperation(value = "在线认捐接口", notes = "", httpMethod = "PUT")
  @RequestMapping(path = "/subscribe", method = PUT)
  @ResponseStatus(HttpStatus.OK)
  public ReturnDto createSubscribe(@Valid @RequestBody SubscribeDto dto) {
    contributeService.contribute(dto.getBatchId(), dto.getStudentIds());
    return ReturnDto.builder()
        .codeMessage(new CodeMessage())
        .build();
  }

  @RoleCheck
  @ApiOperation(value = "汇款录入", notes = "", httpMethod = "POST")
  @RequestMapping(path = "/remittance", method = POST)
  @ResponseStatus(HttpStatus.OK)
  public ReturnDto createRemittance( @RequestBody CreateRemittanceDto dto) {
    contributeService.createRemittance(dto);
    return ReturnDto.builder()
        .codeMessage(new CodeMessage())
        .build();
  }
}
