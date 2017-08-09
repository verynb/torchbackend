package com.torch.interfaces.student;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by yuanj on 2017/7/13.
 */
@NoArgsConstructor
@Getter
public enum StudentExportConfigEnum {

  S_NO(5, 1),
  NAME(1, 3),
  HEIGHT(3, 3),
  WEIGHT(5, 3),
  GENDER(1, 4),
  BIRTHDAY(3, 4),
  AGE(5, 4),
  AREA(1, 5),
  IDENTITYCARD(5, 5),
  ADDRESS(1, 6),
  SCHOOLNAME(1, 7),
  NATION(5,7),
  GRADE(1, 8),
  CLBUM(3, 8),
  CLASSTEACHER(5, 8),
  OTHER(0, 11);

  public int columnIndex;

  public int rowIndex;


  StudentExportConfigEnum(int columnIndex,int rowIndex) {
    this.columnIndex = columnIndex;
    this.rowIndex = rowIndex;
  }


}
