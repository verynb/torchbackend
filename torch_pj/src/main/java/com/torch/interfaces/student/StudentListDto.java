package com.torch.interfaces.student;

import com.torch.interfaces.common.facade.dto.CodeMessage;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Created by yuanj on 2017/6/19.
 */
@Data
@Builder
public class StudentListDto {
  @ApiModelProperty("CODE MESSAGE")
  private CodeMessage codeMessage;

  private List<StudentDetailDto> studentLists;

}
