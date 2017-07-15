package com.torch.interfaces.returnVisit.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yuanj on 2017/7/15.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateReturnVisitDto {

  @NotNull
  private Long studentId;

  /**
   * 回访内容
   */
  private String returnInfo;

  /**
   * 回访图片
   */
  private List<String> returnPhotos;

}
