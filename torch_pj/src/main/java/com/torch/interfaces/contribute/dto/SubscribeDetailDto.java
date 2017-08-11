package com.torch.interfaces.contribute.dto;

import com.torch.domain.model.contribute.Remittance;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.returnVisit.dto.ReturnVisitDto;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yuanj on 2017/6/28.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubscribeDetailDto {

  private CodeMessage codeMessage;

  @ApiModelProperty(name = "认捐学生ID", required = true, position = 1)
  private Long studentId;

  @ApiModelProperty(name = "认捐学生姓名", required = true, position = 1)
  private String studentName;

  @ApiModelProperty(name = "学生年龄", required = true, position = 2)
  private Integer studentAge;

  @ApiModelProperty(name = "学生地址", required = true, position = 3)
  private String studentAdree;

  @ApiModelProperty(name = "学校", required = true, position = 4)
  private String schoolName;
  @ApiModelProperty(name = "发布时间", required = true, position = 5)
  private String releaseTime;
  @ApiModelProperty(name = "资助金额", required = true, position = 6)
  private double needMoney;

  @ApiModelProperty(name = "家访", required = true, position = 7)
  private List<HomeVisitDto> homeVisitDtos;

  @ApiModelProperty(name = "回访", required = true, position = 7)
  private List<ReturnVisitDto> returnVisitDtos;

  @ApiModelProperty(name = "汇款记录", required = true, position = 8)
  private List<RemittanceDetailDto> remittances;


}
