/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.interfaces.release;

import com.torch.domain.model.common.IdEntity;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author yj 2017-01-18 11:16:14
 * @version 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddReleaseCommand {

  /**
   * 批次
   */
  @NotBlank
  @ApiModelProperty(name = "发布批次", required = true, position = 1)
  private String batchNo;

  /**
   * 省份
   */
  @NotBlank
  @ApiModelProperty(name = "省份", required = true, position = 1)
  private String province;

  /**
   * 市
   */
  @NotBlank
  @ApiModelProperty(name = "市", required = true, position = 1)
  private String city;


}