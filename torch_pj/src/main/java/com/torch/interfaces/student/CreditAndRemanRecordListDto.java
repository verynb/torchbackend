package com.torch.interfaces.student;

import com.torch.interfaces.common.facade.dto.CodeMessage;
import com.torch.interfaces.contribute.dto.RemittanceDetailDto;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * Created by yuanj on 2017/7/23.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreditAndRemanRecordListDto {

  @ApiModelProperty(name = "捐助人", required = true, position = 1)
  private String sponsorName;
  @ApiModelProperty(name = "学生ID", required = true, position = 2)
  private Long studentId;

  @ApiModelProperty(name = "放款/汇款金额", required = true, position = 3)
  private Double money;

  @ApiModelProperty(name = "放款/汇款时间", required = true, position = 3)
  private String formatedTime;

  private DateTime time;
  @ApiModelProperty(name = "时否是放款true为放款(及为受助记录)，false为汇款（认捐人汇款）", required = true, position = 3)
  private Boolean isCredit;

}
