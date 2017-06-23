/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.domain.model.audit;

import com.torch.domain.model.group.Group;
import org.springframework.data.repository.CrudRepository;

/**
 * 
 * @author yuanj 2017-01-16 11:44:31
 * @version 1.0
 */
public interface AuditRepository extends CrudRepository<Audit, Long> {
}
