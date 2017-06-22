package com.torch.interfaces.common;

import static com.torch.util.Constants.PAGE_SIZE_MAX_LIMIT;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.torch.interfaces.common.command.PageCommand;
import com.torch.interfaces.common.facade.dto.ResultDTO;
import com.torch.util.CommonFun;
import com.torch.util.Param;

/**
 * 
 * @ClassName: DbUtil
 * @author service#yangle.org.cn
 * @date 2017年1月12日 下午2:27:37
 *
 */
public class CommonService {

	@PersistenceContext
	protected  EntityManager em;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 
	 * @param sql  原生sql
	 * @param params
	 * @return
	 * @return List<Map<String,Object>>     
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> query(String sql, Map params) {
//		logger.info(sql);
//		logger.info(CommonFun.isNe(params)?"":params.toString());
		Session session = em.unwrap(org.hibernate.Session.class);
		SQLQuery query = session.createSQLQuery(sql);
		
		if(params != null) {
			query.setProperties(params);
		}
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> result = query.list();
		return result;
	}

	/**
	 * 查询hql并封装结果集
	 * 
	 * @Title: queryBean
	 * @param hql
	 * @param params 查询的参数，规则: key=value
	 * @param pageCommand 分页参数
	 * @return List 返回类型
	 */
	@SuppressWarnings("rawtypes")
	public ResultDTO queryBean(String hql, Map params, PageCommand pageCommand) {
		logger.info(hql);
		logger.info(CommonFun.isNe(params)?"":params.toString());
		
		ResultDTO result = new ResultDTO();
		Session session = em.unwrap(org.hibernate.Session.class);
		
		try {
			// 排序
			if (!Objects.isNull(pageCommand)) {
				hql = hql + " ORDER BY " + pageCommand.getSortWay() + " " + pageCommand.getOrder();
			}
			
			Query query = session.createQuery(hql);
			
			query.setProperties(params);

			if(CollectionUtils.isNotEmpty(query.list())){
				int count = query.list().size();
				result.setTotalSize(count);
			}
			
			if (pageCommand != null && pageCommand.isNotNull()) {
				query.setFirstResult(getStart(pageCommand.getPage(), pageCommand.getSize())).setMaxResults(pageCommand.getSize());
			} else {
				query.setMaxResults(PAGE_SIZE_MAX_LIMIT);
			}
			
			List<?> list = query.list();
			result.setResultList(list);
			
			if (pageCommand!=null) {
				result.setTotalPage(getTotalPage(result.getTotalSize(), pageCommand.getSize()));
			}
			
			return result;
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}
	
	/**
	 * 查询原生sql并封装结果集
	 * 
	 * @Title: queryBean
	 * @param sql
	 * @param params  查询的参数，规则: key=value
	 * @param pageCommand 分页参数
	 * @return List 返回类型
	 */
	@SuppressWarnings("rawtypes")
	public ResultDTO queryNativeBean(String sql, Map params, PageCommand pageCommand) {
		logger.info(sql);
		logger.info(CommonFun.isNe(params)?"":params.toString());
		
		ResultDTO result = new ResultDTO();
		Session session = em.unwrap(org.hibernate.Session.class);
		
		try {
			// 排序
			if (!Objects.isNull(pageCommand)) {
				sql = sql + " ORDER BY " + pageCommand.getSortWay() + " " + pageCommand.getOrder();
			}
			
			SQLQuery query = session.createSQLQuery(sql);
			
			query.setProperties(params);

			if(CollectionUtils.isNotEmpty(query.list())){
				int count = query.list().size();
				result.setTotalSize(count);
			}
			
			if (pageCommand != null && pageCommand.isNotNull()) {
				query.setFirstResult(getStart(pageCommand.getPage(), pageCommand.getSize())).setMaxResults(pageCommand.getSize());
			} else {
				query.setMaxResults(PAGE_SIZE_MAX_LIMIT);
			}
			
			List<?> list = query.list();
			result.setResultList(list);
			
			if (pageCommand!=null) {
				result.setTotalPage(getTotalPage(result.getTotalSize(), pageCommand.getSize()));
			}
			
			return result;
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}
	
	/**
	 * 查询原生sql并封装结果集
	 * 
	 * @Title: queryBean
	 * @param sql
	 * @param params  查询的参数，规则: key=value
	 * @param pageCommand 分页参数
	 * @param clazz
	 * @return ResultDTO 返回类型
	 */
	@SuppressWarnings("rawtypes")
	public <T> ResultDTO queryNativeBean(String sql, Map params, PageCommand pageCommand, Class<T> clazz) {
		logger.info(sql);
		logger.info(CommonFun.isNe(params)?"":params.toString());
		
		ResultDTO result = new ResultDTO();
		Session session = em.unwrap(org.hibernate.Session.class);
		
		try {
			// 排序
			if (!Objects.isNull(pageCommand)) {
				sql = sql + " ORDER BY " + pageCommand.getSortWay() + " " + pageCommand.getOrder();
			}
			
			SQLQuery query = session.createSQLQuery(sql);
			
			query.setProperties(params);

			if(CollectionUtils.isNotEmpty(query.list())){
				int count = query.list().size();
				result.setTotalSize(count);
			}
			
			if (pageCommand != null && pageCommand.isNotNull()) {
				query.setFirstResult(getStart(pageCommand.getPage(), pageCommand.getSize())).setMaxResults(pageCommand.getSize());
			} else {
				query.setMaxResults(PAGE_SIZE_MAX_LIMIT);
			}
			
			if (clazz!=null) {
				query.setResultTransformer(Transformers.aliasToBean(clazz));
//				query.addEntity(clazz);
			}
			List<T> list = query.list();
			result.setResultList(list);
			
			if (pageCommand!=null) {
				result.setTotalPage(getTotalPage(result.getTotalSize(), pageCommand.getSize()));
			}
			
			return result;
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return null;
	}

	public <T> ResultDTO queryNativeBean(String sql,PageCommand pageCommand , Class<T> clazz) {
		logger.info(sql);

		ResultDTO result = new ResultDTO();
		Session session = em.unwrap(org.hibernate.Session.class);

		try {

			SQLQuery query = session.createSQLQuery(sql);

			if(CollectionUtils.isNotEmpty(query.list())){
				int count = query.list().size();
				result.setTotalSize(count);
			}

			if (pageCommand != null && pageCommand.isNotNull()) {
				query.setFirstResult(getStart(pageCommand.getPage(), pageCommand.getSize())).setMaxResults(pageCommand.getSize());
			} else {
				query.setMaxResults(PAGE_SIZE_MAX_LIMIT);
			}

			if (clazz!=null) {
//				query.addEntity(clazz);
                query.setResultTransformer(Transformers.aliasToBean(clazz));
			}
			List<?> list = query.list();
			result.setResultList(list);

			if (pageCommand!=null) {
				result.setTotalPage(getTotalPage(result.getTotalSize(), pageCommand.getSize()));
			}

			return result;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getStart(int page, int size) {
        int start = (page - 1) * size;
        return start;
    }

	public  int getTotalPage(int totalSize, int size) {
		int totalPage = 0;
        if (totalSize > 0) {
            totalPage = totalSize % size == 0 ? totalSize / size
                    : (totalSize / size + 1);
        }
        return totalPage;
    }
	
	/**
	 * 为sql添加参数,通过Object对象 .
	 * 
	 * @param value 值
	 * @param key	对应对象中的参数
	 * @param sql 	sql
	 * @param param 参数对象
	 * @return void     
	 */
	protected void setParameter(Object value, String key, StringBuilder sql, Param param) {
		if (!CommonFun.isNe(value)) {
			sql.append(" AND " + key + " = :" + key);
			param.put(key, value);
		}
	}

	protected void setOrParameter(Object value, String key, StringBuilder sql, Param param) {
		if (!CommonFun.isNe(value)) {
			sql.append(" OR " + key + " = :" + key);
			param.put(key, " LIKE '%" + value + "%'");
		}
	}
}
