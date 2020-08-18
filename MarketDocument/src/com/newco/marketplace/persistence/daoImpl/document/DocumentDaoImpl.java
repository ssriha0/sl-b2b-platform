package com.newco.marketplace.persistence.daoImpl.document;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.dao.document.DocumentDao;

/**
 *  This class contains all base methods for data access and manipulation.
 */

public  class DocumentDaoImpl extends SqlMapClientDaoSupport implements DocumentDao {
	
	private final Logger logger =  Logger.getLogger(DocumentDaoImpl.class.getName());	
	
	@SuppressWarnings("unchecked")
	public List queryForList(String id, Object parameterObject)
		throws DataAccessException {

		if (logger.isDebugEnabled()) {
			//stopWatch.reset();
			//stopWatch.start();
		}

		List list = getSqlMapClientTemplate().queryForList(id, parameterObject);

		if (logger.isDebugEnabled()) {
			logTimer("queryForList", id);
		}

		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List queryForList(String id) throws DataAccessException {
		if (logger.isDebugEnabled()) {
			//stopWatch.reset();
			//stopWatch.start();
		}
		List list = getSqlMapClientTemplate().queryForList(id);
		if (logger.isDebugEnabled()) {
			logTimer("queryForList", id);
		}
		return list;
	}


	
	public Object queryForObject(String id, Object parameterObject)
		throws DataAccessException {

		if (logger.isDebugEnabled()) {
			//stopWatch.reset();
			//stopWatch.start();
		}

		Object o =
			getSqlMapClientTemplate().queryForObject(id, parameterObject);

		if (logger.isDebugEnabled()) {
			logTimer("queryForObject", id);
		}

		return o;
	}
	

	public Object insert(String id, Object parameterObject)
		throws DataAccessException {

		if (logger.isDebugEnabled()) {
			//stopWatch.reset();
			//stopWatch.start();
		}

		Object o = getSqlMapClientTemplate().insert(id, parameterObject);

		if (logger.isDebugEnabled()) {
			logTimer("insert", id);
		}

		return o;
	}

	public int update(String id, Object parameterObject)
		throws DataAccessException {

		if (logger.isDebugEnabled()) {
			//stopWatch.reset();
			//stopWatch.start();
		}

		int i = getSqlMapClientTemplate().update(id, parameterObject);

		if (logger.isDebugEnabled()) {
			logTimer("update", id);
		}

		return i;
	}

	public int delete(String id, Object parameterObject)
		throws DataAccessException {

		if (logger.isDebugEnabled()) {
			//stopWatch.reset();
			//stopWatch.start();
		}

		int i = getSqlMapClientTemplate().delete(id, parameterObject);

		if (logger.isDebugEnabled()) {
			logTimer("delete", id);
		}
		return i;
	}
	
	public String getAppPropertiesValue(String key) throws DataServiceException{
		String value = null;
		try{
			value = (String)queryForObject("application_propertiesbykey.query", key);
		}catch(Exception e){
			throw new DataServiceException("Error", e);
		}
		return value;
	}
	
	private void logTimer(String method, String id) {
		StringBuffer sb = new StringBuffer();
		sb.append("DAO ID[");
		sb.append(Thread.currentThread().hashCode());
		sb.append("] METHOD[");
		sb.append(method);
		sb.append("] SQL[");
		sb.append(id);
		sb.append("] completed RUN TIME[");
		sb.append("]");
		logger.debug(sb.toString());
	}
	
}
