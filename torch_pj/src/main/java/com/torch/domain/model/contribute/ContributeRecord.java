package com.torch.domain.model.contribute;

import com.torch.domain.model.common.IdEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by yuanj on 2017/6/28.
 * 捐助记录
 */
@Entity
@Table(name = "contribute_record")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContributeRecord extends IdEntity implements Serializable {
  private final static long serialVersionUID = 1L;

  private Long contributeId;//捐助人ID
  private Long studentId;//学生ID

  private Long batchId;//批次ID

  private Boolean ableRemit;//true为能汇款

}
