/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.domain.model.release;

import com.torch.domain.model.common.IdEntity;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class Release extends IdEntity implements Serializable {

  private final static long serialVersionUID = 1L;
  /**
   * 批次
   */
  private String batchNo;

  /**
   * 省份
   */
  private String province;

  /**
   * 市
   */
  private String city;

  /**
   * 发布批次状态
   * 0.创建
   * 1.初始
   * 2.发布
   * 3.认捐中
   * 4.认捐完成
   */
  private Integer status;

}