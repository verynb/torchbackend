package com.torch.interfaces.homeVisit.dto;

import com.torch.interfaces.common.facade.dto.CodeMessage;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Created by yuanj on 2017/6/30.
 */
@Data
@Builder
public class HomeVisitDetail {

  private CodeMessage codeMessage;

  @ApiModelProperty("家访批次号")
  private String batchNo;
  /**
   * 申请表图片
   */
  @ApiModelProperty("申请表图片")
  private List<String> applicationForms;

  //学生本人
  @ApiModelProperty("学生本人")
  private  List<String> studentPhotos;

  //家庭成员
  @ApiModelProperty("家庭成员")
  private List<String> familyPhotos;

  //家庭环境
  @ApiModelProperty("家庭环境")
  private List<String> homePhotos;

  //互动照片
  @ApiModelProperty("互动照片")
  private List<String> InteractivePhotos;

  //家访内容
  @ApiModelProperty("家访内容")
  private String visitInfo;

  @ApiModelProperty("审核项title已经答案")
  private List<AuditChoiced> auditChoiceds;


}
