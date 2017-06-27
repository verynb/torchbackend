package com.torch.interfaces.gradeMoney.dto;

import com.torch.domain.model.gradeMoney.DictGradeMoney;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2017/6/27.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListDto {
  private CodeMessage codeMessage;
  private List<DictGradeMoney> gradeMoneyList;
}
