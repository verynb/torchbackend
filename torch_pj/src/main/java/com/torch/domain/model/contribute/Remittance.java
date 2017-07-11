package com.torch.domain.model.contribute;

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
 * Created by yuanj on 2017/6/28.
 * 汇款记录
 */
@Entity
@Table(name = "remittance")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Remittance extends IdEntity implements Serializable {

  private final static long serialVersionUID = 1L;

  private Long contributeId;//捐助人ID
  private Long studentId;//学生ID

  private DateTime remittanceTime;

  private double remittanceMoney;

  private String remark;

  @Lob
  @Column(columnDefinition = "mediumblob")
  public DateTime getRemittanceTime() {
    return remittanceTime;
  }

  public Long getContributeId() {
    return contributeId;
  }

  public void setContributeId(Long contributeId) {
    this.contributeId = contributeId;
  }

  public Long getStudentId() {
    return studentId;
  }

  public void setStudentId(Long studentId) {
    this.studentId = studentId;
  }

  public void setRemittanceTime(DateTime remittanceTime) {
    this.remittanceTime = remittanceTime;
  }

  public double getRemittanceMoney() {
    return remittanceMoney;
  }

  public void setRemittanceMoney(double remittanceMoney) {
    this.remittanceMoney = remittanceMoney;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
