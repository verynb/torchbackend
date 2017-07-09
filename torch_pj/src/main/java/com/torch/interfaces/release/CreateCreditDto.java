package com.torch.interfaces.release;

import io.swagger.annotations.ApiModelProperty;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;

/**
 * Created by Administrator on 2017/7/9.
 */
@Data
public class CreateCreditDto {
  @NotNull
  private Long studentId;

  @ApiModelProperty(name = "放款金额", required = true, position = 2)
  private double money;

  @ApiModelProperty(name = "资助人ID", required = true, position = 2)
  @NotNull
  private Long sponsorId;

  @ApiModelProperty(name = "放款时间", required = true, position = 2)
  @NotBlank
  private String creditTime;

  public DateTime toDatetime() {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HHmmss");
      try {
        return (new DateTime(sdf.parse(this.getCreditTime()).getTime()));
      } catch (ParseException e) {

      }
      return null;
    }
}
