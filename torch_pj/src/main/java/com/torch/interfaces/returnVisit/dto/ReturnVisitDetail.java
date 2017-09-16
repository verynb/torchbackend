package com.torch.interfaces.returnVisit.dto;

import com.torch.interfaces.common.facade.dto.CodeMessage;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
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

  //学生本人
  @ApiModelProperty(value = "学生本人图片,一张或多张", required = false)
  private List<String> studentPhotos;

  //家庭成员
  @ApiModelProperty(value = "家庭成员图片,一张或多张", required = false)
  private List<String> familyPhotos;

  //家庭环境
  @ApiModelProperty(value = "现场环境,一张或多张", required = false)
  private List<String> environmentPhotos;


}
