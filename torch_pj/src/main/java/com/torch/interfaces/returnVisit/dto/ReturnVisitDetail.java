package com.torch.interfaces.returnVisit.dto;

import com.torch.interfaces.common.facade.dto.CodeMessage;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * Created by yuanj on 2017/7/15.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnVisitDetail {
  private CodeMessage codeMessage;

  /**
   * 回访人
   */
  private String returnVisitor;

  /**
   * 回访时间
   */
  private String returnTime;

  /**
   * 回访内容
   */
  private String returnInfo;

  /**
   * 回访图片
   */
  private List<String> returnPhotos;

}
