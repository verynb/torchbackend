package com.torch.interfaces.contribute.dto;

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
public class HomeVisitDto {

  private Long homeVisitId;

  private String homeVisitTime;

  private String homeVistor;

}
