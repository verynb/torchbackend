package com.torch.interfaces.homeVisit.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created by yuanj on 2017/6/23.
 */
@Data
public class CreateHomeVisitCommand {

  @NotNull
  @ApiModelProperty(name = "发布批次ID", required = true, position = 1)
  private Long batchId;

  @ApiModelProperty(value = "学生ID", required = true)
  private Long studentId;
  /**
   * 申请表图片
   */
  @ApiModelProperty(value = "申请表图片,一张或多张", required = false)
  private List<String> applicationForms;

  //学生本人
  @ApiModelProperty(value = "学生本人图片,一张或多张", required = false)
  private List<String> studentPhotos;

  //家庭成员
  @ApiModelProperty(value = "家庭成员图片,一张或多张", required = false)
  private List<String> familyPhotos;

  //家庭环境
  @ApiModelProperty(value = "家庭环境图片,一张或多张", required = false)
  private List<String> homePhotos;

  @ApiModelProperty(value = "家庭特写环境图片,一张或多张", required = false)
  private List<String>homeFeaturePhotos;

  //互动照片
  @ApiModelProperty(value = "互动照片图片,一张或多张", required = false)
  private List<String> InteractivePhotos;

  //家访内容
  @ApiModelProperty(value = "家访内容", required = false)
  private String visitInfo;

  @ApiModelProperty(value = "审核标准IDS", required = false)
  private List<Long> auditItemIds;

  private String homeVistor;

  private Long homeVistorId;

  @ApiModelProperty("家访时间")
  private String homeVisitTime;

}
