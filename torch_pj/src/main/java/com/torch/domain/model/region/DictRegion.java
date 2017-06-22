/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.domain.model.region;

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
@Table(name = "dict_region")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DictRegion extends IdEntity implements Serializable {

  private final static long serialVersionUID = 1L;
  /**
   * 名称
   */
  private String name;

  private Long parentId;

  private String shortName;

  private int levelType;

  private String cityCode;

  private String zipCode;

  private String mergerName;

  private String lng;

  private String lat;

  private String pinyin;

}