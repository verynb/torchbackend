package com.torch.interfaces.student;

import com.torch.interfaces.common.facade.dto.CodeMessage;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2017/7/9.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditRecordListDto {

  private CodeMessage codeMessage;
  private List<CreditRecordDto> recordDtos;

}
