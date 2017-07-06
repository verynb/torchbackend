package com.torch.interfaces.release;

import lombok.Builder;
import lombok.Data;

/**
 * Created by Administrator on 2017/6/28.
 */
@Data
@Builder
public class ReleaseBatch {

  private Long id;
  /**
   * 批次
   */
  private String batchNo;

  private String province;

  private String city;

}
