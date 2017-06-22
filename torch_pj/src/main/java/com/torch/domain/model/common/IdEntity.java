package com.torch.domain.model.common;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import org.joda.time.DateTime;

/**
 * 统一定义id的entity基类
 *
 * @author admin@yj.org.cn
 * @date 2017年1月9日 下午5:25:10
 */
@MappedSuperclass
public abstract class IdEntity {

  private Long id;


  private DateTime createTime;

  private DateTime lastUpdateTime;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Lob
  @Column(columnDefinition = "mediumblob")
  public DateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(DateTime createTime) {
    this.createTime = createTime;
  }

  @Lob
  @Column(columnDefinition = "mediumblob")
  public DateTime getLastUpdateTime() {
    return lastUpdateTime;
  }

  public void setLastUpdateTime(DateTime lastUpdateTime) {
    this.lastUpdateTime = lastUpdateTime;
  }
}
