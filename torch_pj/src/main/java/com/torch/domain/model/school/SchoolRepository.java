package com.torch.domain.model.school;

import com.torch.domain.model.region.DictRegion;
import java.util.List;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by yuanj on 9/18/16.
 */
public interface SchoolRepository extends CrudRepository<School, Long>, QueryDslPredicateExecutor {

}
