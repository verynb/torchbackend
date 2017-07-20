package com.torch.interfaces.release;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.torch.domain.model.release.QRelease;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by yuanj on 2017/6/1.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseSearchDto {

  @ApiModelProperty(value = "分页条数")
  @NotNull
  private Integer pageSize;
  @ApiModelProperty(value = "当前页")
  @NotNull
  private Integer currentPage;

  @ApiModelProperty(name = "开始时间", required = true, position = 1)
  private Long startTime;
  @ApiModelProperty(name = "结束时间", required = true, position = 2)
  private Long endTime;
  @ApiModelProperty(name = "省份", required = true, position = 3)
  private String province;
  @ApiModelProperty(name = "市", required = true, position = 4)
  private String city;
  @ApiModelProperty(name = "批次编号", required = true, position = 5)
  private String batchNo;

  public Predicate toPredicate() {

    BooleanBuilder conditions = new BooleanBuilder(
        QRelease.release.status.ne(0).and(QRelease.release.status.ne(1)));
    if (getStartTime() != null) {
      conditions.and(QRelease.release.createTime
          .after(new DateTime(getStartTime()).minuteOfDay().withMinimumValue()));
    }

    if (getEndTime() != null) {
      conditions.and(QRelease.release.createTime
          .before(new DateTime(getEndTime()).minuteOfDay().withMaximumValue()));
    }

    if (StringUtils.isNotBlank(getProvince())) {
      conditions.and(QRelease.release.province.eq(getProvince()));
    }
    if (StringUtils.isNotBlank(getCity())) {
      conditions.and(QRelease.release.city.eq(getCity()));
    }

    if (StringUtils.isNotBlank(getBatchNo())) {
      conditions.and(QRelease.release.batchNo.eq(getBatchNo()));
    }
    return conditions;
  }
}
