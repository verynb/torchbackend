package com.torch.interfaces.student;

import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.contribute.dto.RemittanceDetailDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yuanj on 2017/7/23.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditAndRemanRecordDto {

  private CodeMessage codeMessage;

  private List<CreditAndRemanRecordListDto> recordDtos;

}
