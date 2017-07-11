/**
 * Copyright (c) 2017
 * 成都博智云创科技有限公司.
 */
package com.torch.domain.model.contribute;

import com.torch.domain.model.gradeMoney.DictGradeMoney;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author yuanj 2017-01-16 11:44:31
 * @version 1.0
 */
public interface ContributeRecordRepository extends CrudRepository<ContributeRecord, Long>, QueryDslPredicateExecutor {


}
