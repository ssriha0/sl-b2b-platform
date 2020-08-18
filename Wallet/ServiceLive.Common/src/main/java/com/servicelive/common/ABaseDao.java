package com.servicelive.common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.servicelive.common.exception.DataServiceException;

// TODO: Auto-generated Javadoc
/**
 * $Revision: 1.7 $ $Author: kvuppal $ $Date: 2008/04/30 15:42:59 $
 */

/*
 * Maintenance History: See bottom of file
 */
public abstract class ABaseDao extends SqlMapClientDaoSupport {

	/** logger. */
	public static final Logger logger = Logger.getLogger(ABaseDao.class.getName());

	/**
	 * Added by Priti.
	 * 
	 * @param parameterObject 
	 * 
	 * @throws DataAccessException 
	 */
	public void batchInsert(final HashMap<String, ArrayList> parameterObject) throws DataAccessException {

		if (parameterObject == null || parameterObject.isEmpty() ) return;

		if (logger.isInfoEnabled()) logger.info("Start  batchInsert:" + parameterObject.size());

		getSqlMapClientTemplate().execute(new SqlMapClientCallback() {

			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {

				executor.startBatch();
				int counter = 0;
				for (Map.Entry<String, ArrayList> entry : parameterObject.entrySet()) {
					String key = entry.getKey();
					List valueList = entry.getValue();
					for (Object obj : valueList) {
						if (obj != null) {
							executor.insert(key, obj);
							counter++;
						}
					}
				}
				executor.executeBatch();
				if (logger.isInfoEnabled()) logger.info("batchInsert: inserting " + counter + " rows in batch; ");
				return null;
			}
		});
		logger.info("End  batchInsert");
	}

	/**
	 * Added by Priti.
	 * 
	 * @param id 
	 * @param parameterObject 
	 * 
	 * @throws DataAccessException 
	 */
	public void batchInsert(final String id, final List parameterObject) throws DataAccessException {

		if (parameterObject == null) {
			return;
		}

		if (logger.isInfoEnabled()) {
			logger.info("Start  batchInsert:" + parameterObject.size());
		}

		getSqlMapClientTemplate().execute(new SqlMapClientCallback() {

			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {

				executor.startBatch();
				for (Object obj : parameterObject) {
					if (obj != null) {
						executor.insert(id, obj);
					}
				}
				executor.executeBatch();
				return null;
			}
		});
		logger.info("End  batchInsert");
	}

	/**
	 * delete.
	 * 
	 * @param id 
	 * @param parameterObject 
	 * 
	 * @return int
	 * 
	 * @throws DataAccessException 
	 */
	public int delete(String id, Object parameterObject) throws DataAccessException {

		int i = getSqlMapClientTemplate().delete(id, parameterObject);

		if (logger.isDebugEnabled()) {
			// stopWatch.stop();
			logTimer("delete", id);
		}
		return i;
	}

	/**
	 * getAppPropertiesValue.
	 * 
	 * @param key 
	 * 
	 * @return String
	 * 
	 * @throws DataServiceException 
	 */
	public String getAppPropertiesValue(String key) throws DataServiceException {

		String value = null;
		try {
			value = (String) queryForObject("application_propertiesbykey.query", key);
		} catch (Exception e) {
			throw new DataServiceException("Error", e);
		}
		return value;
	}

	/**
	 * insert.
	 * 
	 * @param id 
	 * @param parameterObject 
	 * 
	 * @return Object
	 * 
	 * @throws DataAccessException 
	 */
	public Object insert(String id, Object parameterObject) throws DataAccessException {

		final Object o = getSqlMapClientTemplate().insert(id, parameterObject);

		if (logger.isDebugEnabled()) {
			logTimer("insert", id);
		}

		return o;
	}

	/**
	 * logTimer.
	 * 
	 * @param method 
	 * @param id 
	 * 
	 * @return void
	 */
	private void logTimer(String method, String id) {

		if (logger.isDebugEnabled()) {
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

	/**
	 * queryForList.
	 * 
	 * @param id 
	 * 
	 * @return List
	 * 
	 * @throws DataAccessException 
	 */
	public List queryForList(String id) throws DataAccessException {

		final List list = getSqlMapClientTemplate().queryForList(id);
		if (logger.isDebugEnabled()) {
			logTimer("queryForList", id);
		}
		return list;
	}

	/**
	 * queryForList.
	 * 
	 * @param id 
	 * @param parameterObject 
	 * 
	 * @return List
	 * 
	 * @throws DataAccessException 
	 */
	public List queryForList(String id, Object parameterObject) throws DataAccessException {

		final List list = getSqlMapClientTemplate().queryForList(id, parameterObject);

		if (logger.isDebugEnabled()) {
			logTimer("queryForList", id);
		}

		return list;
	}

	/**
	 * queryForObject.
	 * 
	 * @param id 
	 * @param parameterObject 
	 * 
	 * @return Object
	 * 
	 * @throws DataAccessException 
	 */
	public Object queryForObject(String id, Object parameterObject) throws DataAccessException {

		final Object o = getSqlMapClientTemplate().queryForObject(id, parameterObject);

		if (logger.isDebugEnabled()) {
			logTimer("queryForObject", id);
		}

		return o;
	}

	/**
	 * update.
	 * 
	 * @param id 
	 * @param parameterObject 
	 * 
	 * @return int
	 * 
	 * @throws DataAccessException 
	 */
	public int update(String id, Object parameterObject) throws DataAccessException {

		int i = getSqlMapClientTemplate().update(id, parameterObject);

		if (logger.isDebugEnabled()) {
			// stopWatch.stop();
			logTimer("update", id);
		}

		return i;
	}
	
	/**
	 * queryForList.
	 * 
	 * @param id 
	 * 
	 * @return List
	 * 
	 * @throws DataAccessException 
	 */
	public Map queryForMap(String id, Object parameterObject, String keyProperty, String valueProperty) throws DataAccessException {

		final Map map = getSqlMapClientTemplate().queryForMap(id,parameterObject,keyProperty,valueProperty);
		if (logger.isDebugEnabled()) {
			logTimer("queryForMap", id);
		}
		return map;
	}
	
	/**
	 * @param id
	 * @param parameterObject
	 * @throws DataAccessException
	 */
	public void batchUpdate(final String id, final List parameterObject) throws DataAccessException {
		if (parameterObject == null) {
			return;
		}

		if (logger.isInfoEnabled()) {
			logger.info("Start  batchUpdate:" + parameterObject.size());
		}

		getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				executor.startBatch();
				for (Object obj : parameterObject) {
					if (obj != null)
						executor.update(id, obj);
				}
				executor.executeBatch();
				return null;
			}
		});

		if (logger.isInfoEnabled()) {
			logger.info("End  batchUpdate");
		}
	}
}
/*
 * Maintenance History
 * $Log: ABaseImplDao.java,v $
 * Revision 1.7 2008/04/30 15:42:59 kvuppal
 * *** empty log message ***
 * 
 * Revision 1.6 2008/04/26 00:51:53 glacy
 * Shyam: Merged I18_Fin to HEAD.
 * 
 * Revision 1.4.6.1 2008/04/23 11:42:00 glacy
 * Shyam: Merged HEAD to finance.
 * 
 * Revision 1.5 2008/04/23 05:17:47 hravi
 * Shyam: Reverting to build 247.
 * 
 * Revision 1.4 2008/02/26 18:21:13 mhaye05
 * Merged Iteration 17 Branch into Head
 * 
 * Revision 1.3.2.1 2008/02/25 23:49:49 spatsa
 * Changes made for scheduler reconcile data
 * 
 * Revision 1.3 2008/02/14 23:44:13 mhaye05
 * Merged Feb4_release branch into head
 * 
 * Revision 1.2.18.1 2008/02/06 21:26:23 mhaye05
 * updated logger
 */