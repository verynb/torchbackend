package com.torch.interfaces.student;

import com.torch.domain.model.school.School;
import com.torch.domain.model.student.Student;
import com.torch.interfaces.common.facade.dto.CodeMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Created by yuanj on 2017/6/19.
 */
@Data
@Builder
public class StudentDto {
  @ApiModelProperty("CODE MESSAGE")
  private CodeMessage codeMessage;

  private Student student;

}
