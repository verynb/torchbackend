package com.torch.interfaces.audit.dto;

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
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuditListDto {

  private CodeMessage codeMessage;

  private List<AuditDto> auditDtoList;


}
