package com.torch.interfaces.student;

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
public class CreditRecordDto {
  /**
   * 资助人ID
   */
  private String sponsorName;

  private List<CreditRecord> recordList;

}
