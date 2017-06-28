package com.torch.interfaces.release;

import com.torch.domain.model.release.Release;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Created by Administrator on 2017/6/28.
 */
@Data
@Builder
public class ReleaseDto {
  private CodeMessage codeMessage;
  private List<Release> releases;

}
