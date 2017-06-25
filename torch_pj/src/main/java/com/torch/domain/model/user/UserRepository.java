package com.torch.domain.model.user;

import com.torch.interfaces.common.facade.dto.ReturnIdDto;
import java.util.List;
import java.util.Optional;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by yuanj on 9/18/16.
 */
public interface UserRepository extends CrudRepository<User, Long>,QueryDslPredicateExecutor {

    Optional<User> getById(Long id);

    Optional<User> getByMobile(String aliasName);

    List<User> findByType(int type);

    User findByMobile(String mobile);

}
