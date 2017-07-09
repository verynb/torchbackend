package com.torch.domain.model.release;

import java.util.List;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by yuanj on 9/18/16.
 */
public interface CreditRepository extends CrudRepository<Creditcredit, Long>, QueryDslPredicateExecutor {

   List<Creditcredit> findByStudentId(Long id) ;
}
