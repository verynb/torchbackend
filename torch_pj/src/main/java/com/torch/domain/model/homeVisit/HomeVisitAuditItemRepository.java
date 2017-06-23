package com.torch.domain.model.homeVisit;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by yuanj on 9/18/16.
 */
public interface HomeVisitAuditItemRepository extends CrudRepository<HomeVisitAuditItem, Long>,
    QueryDslPredicateExecutor {

}
