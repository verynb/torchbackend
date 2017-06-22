package com.torch.interfaces.upload;

import lombok.Builder;
import lombok.Data;

/**
 * Created by yuanj on 2017/6/19.
 */
@Data
@Builder
public class UploadRusltDto {
  private String absolutePath;
}
