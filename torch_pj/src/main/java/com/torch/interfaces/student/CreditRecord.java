package com.torch.interfaces.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * Created by Administrator on 2017/7/9.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditRecord {
  private Long studentId;
  /**
   * 放款金额
   */
  private Double money;


  private String creditTime;

}
