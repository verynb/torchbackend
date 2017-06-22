package com.torch.domain.model.user;

import java.util.List;
import java.util.Optional;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by yuanj on 9/18/16.
 */
public interface TeacherSchoolRepository extends CrudRepository<TeacherSchool, Long>,QueryDslPredicateExecutor {

}
