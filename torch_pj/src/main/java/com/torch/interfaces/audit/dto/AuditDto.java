package com.torch.interfaces.audit.dto;

import com.torch.domain.model.audit.AuditItem;
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
public class AuditDto {
  private Long id;
  private String title;

  private List<AuditItemDto> auditItemList;
}
