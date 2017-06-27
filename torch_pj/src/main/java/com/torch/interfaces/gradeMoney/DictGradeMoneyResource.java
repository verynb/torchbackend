/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.gradeMoney;

import static com.torch.interfaces.common.ApiPaths.API_CONTEXT_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.torch.application.homeVisit.HomeVisitService;
import com.torch.domain.model.gradeMoney.DictGradeMoney;
import com.torch.domain.model.gradeMoney.DictGradeMoneyRepository;
import com.torch.domain.model.homeVisit.HomeVisitAuditItemRepository;
import com.torch.domain.model.homeVisit.HomeVisitRepository;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.common.facade.dto.ReturnDto;
import com.torch.interfaces.common.security.annotation.RoleCheck;
import com.torch.interfaces.gradeMoney.dto.ListDto;
import com.torch.interfaces.homeVisit.dto.CreateHomeVisitCommand;
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
@Api(value = "DictGradeMoneyResource", description = "年级对应默认金额配置相关api")
public class DictGradeMoneyResource {

  private final DictGradeMoneyRepository dictGradeMoneyRepository;

  @Autowired
  public DictGradeMoneyResource(final DictGradeMoneyRepository dictGradeMoneyRepository) {
    this.dictGradeMoneyRepository = dictGradeMoneyRepository;
  }

  @RoleCheck
  @ApiOperation(value = "查询年级对应默认金额", notes = "", response = DictGradeMoney.class, httpMethod = "GET")
  @RequestMapping(path = "/gradeMoney", method = GET)
  @ResponseStatus(HttpStatus.OK)
  public ListDto getDefaultMoney() {
    return ListDto.builder()
        .codeMessage(new CodeMessage())
        .gradeMoneyList((List<DictGradeMoney>) dictGradeMoneyRepository.findAll())
        .build();
  }
}
