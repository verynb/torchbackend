package com.torch.domain.model.student;

import com.torch.domain.model.school.School;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by yuanj on 9/18/16.
 */
public interface StudentRepository extends CrudRepository<Student, Long>, QueryDslPredicateExecutor {

}
