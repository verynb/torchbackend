package com.torch.domain.model.release;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by yuanj on 9/18/16.
 */
public interface ReleaseStudentRepository extends CrudRepository<ReleaseStudent, Long>,
    QueryDslPredicateExecutor {

}
