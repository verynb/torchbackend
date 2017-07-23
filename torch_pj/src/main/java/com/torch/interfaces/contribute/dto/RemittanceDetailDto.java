package com.torch.interfaces.contribute.dto;

import com.torch.domain.model.common.IdEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * Created by yuanj on 2017/6/28.
 * 汇款记录
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RemittanceDetailDto extends IdEntity implements Serializable {

  private final static long serialVersionUID = 1L;
  @ApiModelProperty(name = "捐助人ID", required = true, position = 1)
  private Long contributeId;//捐助人ID
  @ApiModelProperty(name = "捐助学生ID", required = true, position = 2)
  private Long studentId;//学生ID
  @ApiModelProperty(name = "汇款时间", required = true, position = 3)
  private String remittanceTime;
  @ApiModelProperty(name = "汇款金额", required = true, position = 4)
  private double remittanceMoney;
  @ApiModelProperty(name = "备注", required = true, position = 5)
  private String remark;

}
