package com.torch.interfaces.student;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.torch.domain.model.student.QStudent;
import io.swagger.annotations.ApiModelProperty;
import java.util.Objects;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by yuanj on 2017/6/1.
 */
@Data
public class StudentSearchDto {
  private Integer pageSize;
  private Integer currentPage;
  @ApiModelProperty(name = "学生姓名", required = true, position = 1)
  private String name;
  @ApiModelProperty(name = "编号", required = true, position = 2)
  private String sNo;
  @ApiModelProperty(name = "身份证", required = true, position = 2)
  private String identityCard;
  @ApiModelProperty(name = "学校ID", required = true, position = 3)
  private Long schoolId;

  public Predicate toPredicate(){
    BooleanBuilder conditions = new BooleanBuilder();
    if(StringUtils.isNotBlank(getName())){
      conditions.and(QStudent.student.name.contains(getName()));
    }
    if(StringUtils.isNotBlank(getsNo())){
      conditions.and(QStudent.student.sNo.eq(getsNo()));
    }
    if(StringUtils.isNotBlank(getIdentityCard())){
      conditions.and(QStudent.student.identityCard.eq(getIdentityCard()));
    }
    if(!Objects.isNull(getSchoolId())){
      conditions.and(QStudent.student.schoolId.eq(getSchoolId()));
    }
    return conditions;
  }
  public String getsNo() {
    return sNo;
  }
  public void setsNo(String sNo) {
    this.sNo = sNo;
  }
}
