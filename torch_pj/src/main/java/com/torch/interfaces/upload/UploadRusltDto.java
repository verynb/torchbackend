package com.torch.interfaces.upload;

import com.torch.interfaces.common.facade.dto.CodeMessage;
import lombok.Builder;
import lombok.Data;

/**
 * Created by yuanj on 2017/6/19.
 */
@Data
@Builder
public class UploadRusltDto {
  private String absolutePath;
  private CodeMessage codeMessage;
}
