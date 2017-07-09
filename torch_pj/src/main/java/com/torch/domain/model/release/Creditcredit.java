/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.domain.model.release;

import com.torch.domain.model.common.IdEntity;
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
 * @author yj 2017-01-18 11:16:14
 * @version 1.0
 */
@Entity
@Table(name = "release_info")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Creditcredit extends IdEntity implements Serializable {

  private final static long serialVersionUID = 1L;

  private Long studentId;
  /**
   * 放款金额
   */
  private Double money;

  /**
   * 资助人ID
   */
  private Long sponsorId;

  private DateTime creditTime;

  @Lob
  @Column(columnDefinition = "mediumblob")
  public DateTime getCreditTime() {
    return creditTime;
  }

  public void setCreditTime(DateTime creditTime) {
    this.creditTime = creditTime;
  }
}