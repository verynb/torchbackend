/**
 * Copyright (c) 2017 
 *	成都博智云创科技有限公司.
 */
package com.torch.domain.model.apiRole;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.torch.interfaces.common.CommonService;

/**
 * 
 * 
 * @author service#yangle.org.cn
 * @date 2017年1月19日 下午6:30:21
 *
 */
@Service
public class ApiRoleRepositoryJpa extends CommonService {

	public List<Map<String, Object>> findAllApiRole() {
		String sql = "SELECT path, method, tenant_id, GROUP_CONCAT(groupid) groups FROM api_role GROUP BY path, method, tenant_id";
		return query(sql, null);
	}
}
