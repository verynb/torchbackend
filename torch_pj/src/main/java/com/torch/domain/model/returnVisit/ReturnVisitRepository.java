package com.torch.domain.model.returnVisit;

import com.torch.domain.model.school.School;
import java.util.List;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by yuanj on 9/18/16.
 */
public interface ReturnVisitRepository extends CrudRepository<ReturnVisit, Long>, QueryDslPredicateExecutor {

  public List<ReturnVisit> findByStudentId(Long studentId);
}
