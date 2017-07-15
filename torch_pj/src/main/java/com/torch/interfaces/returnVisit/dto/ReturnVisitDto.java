package com.torch.interfaces.returnVisit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yuanj on 2017/7/11.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnVisitDto {

  private Long returnVisitId;

  private String returnVisitTime;

  private String returnVistor;

}
