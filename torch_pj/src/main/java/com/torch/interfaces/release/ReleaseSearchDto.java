package com.torch.interfaces.release;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.torch.domain.model.release.QRelease;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

/**
 * Created by yuanj on 2017/6/1.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseSearchDto {

  @ApiModelProperty(name = "开始时间", required = true, position = 1)
  @NotNull
  private Long startTime;
  @ApiModelProperty(name = "结束时间", required = true, position = 2)
  @NotNull
  private Long endTime;
  @ApiModelProperty(name = "省份", required = true, position = 3)
  private String province;
  @ApiModelProperty(name = "市", required = true, position = 4)
  private String city;

  public Predicate toPredicate(){
    BooleanBuilder conditions = new BooleanBuilder();
    if(getStartTime()!=null){
      conditions.and(QRelease.release.createTime.after(new DateTime(getStartTime()).minuteOfDay().withMinimumValue()));
    }
    if(StringUtils.isNotBlank(getProvince())){
      conditions.and(QRelease.release.province.eq(getProvince()));
    }
    if(StringUtils.isNotBlank(getCity())){
      conditions.and(QRelease.release.city.eq(getCity()));
    }
    return conditions;
  }
}
