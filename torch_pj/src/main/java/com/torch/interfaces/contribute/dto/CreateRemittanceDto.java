package com.torch.interfaces.contribute.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created by yuanj on 2017/6/28.
 */
@Data
public class CreateRemittanceDto {

  private Long contributeId;
  private Long studentId;

  private double remittanceMoney;

  private String remark;


}
