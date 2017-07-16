package com.torch.interfaces.returnVisit.dto;

import com.torch.interfaces.common.facade.dto.CodeMessage;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yuanj on 2017/7/16.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnList {

  private CodeMessage codeMessage;

  private List<ReturnVisitListDto> returnVisitListDtoList;

}
