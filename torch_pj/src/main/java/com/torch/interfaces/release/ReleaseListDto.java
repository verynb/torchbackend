package com.torch.interfaces.release;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.joda.time.DateTime;

/**
 * Created by yuanj on 2017/6/1.
 */
@Data
@Builder
public class ReleaseListDto {

  @ApiModelProperty(name = "发布批次ID", required = true, position = 1)
  private Long releaseId;
  @ApiModelProperty(name = "发布批次", required = true, position = 2)
  private String batchNo;
  @ApiModelProperty(name = "发布时间", required = true, position = 3)
  private Long createTime;
  @ApiModelProperty(name = "省份", required = true, position = 4)
  private String province;
  @ApiModelProperty(name = "市", required = true, position = 5)
  private String city;
  @ApiModelProperty(name = "学生ID", required = true, position = 6)
  private Long studentId;
  @ApiModelProperty(name = "学生姓名", required = true, position = 7)
  private String studentName;
  @ApiModelProperty(name = "年级", required = true, position = 8)
  private String grade;

  @ApiModelProperty(name = "审核得分", required = true, position = 9)
  private double scores;

}
