package com.sears.os.dao.impl;

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
import com.newco.marketplace.exception.core.DataServiceException;
import com.sears.os.dao.ABaseDao;

/**
 * 
 *
 */
public abstract class ABaseImplDao extends SqlMapClientDaoSupport implements ABaseDao {
	public final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 * @param id
	 * @param parameterObject
	 * @return List
	 * @throws DataAccessException
	 */
	public List queryForList(String id, Object parameterObject) throws DataAccessException {
		List list = getSqlMapClientTemplate().queryForList(id, parameterObject);
		return list;
	}

	/**
	 * 
	 * @param id
	 * @return List
	 * @throws DataAccessException
	 */
	public List queryForList(String id) throws DataAccessException {
		List list = getSqlMapClientTemplate().queryForList(id);
		return list;
	}

	/**
	 * 
	 * @param id
	 * @param parameterObject
	 * @return Object
	 * @throws DataAccessException
	 */
	public Object queryForObject(String id, Object parameterObject) throws DataAccessException {
		Object o = getSqlMapClientTemplate().queryForObject(id, parameterObject);
		return o;
	}
	/**
	 * 
	 * @param id
	 * @param parameterObject
	 * @param keyProperty
	 * @param valueProperty
	 * @return Map
	 * @throws DataAccessException
	 */
	public Object queryForMap(String id, Object parameterObject,String keyProperty,String valueProperty) throws DataAccessException {
		Object o = getSqlMapClientTemplate().queryForMap(id, parameterObject, keyProperty, valueProperty);
		return o;
	}

	/**
	 * 
	 * @param id
	 * @param parameterObject
	 * @return Object
	 * @throws DataAccessException
	 */
	public Object insert(String id, Object parameterObject) throws DataAccessException {
		Object o = getSqlMapClientTemplate().insert(id, parameterObject);
		return o;
	}

	/**
	 * @param id
	 * @param parameterObject
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
					if (obj != null)
						executor.insert(id, obj);
				}
				executor.executeBatch();
				return null;
			}
		});

		if (logger.isInfoEnabled()) {
			logger.info("End  batchInsert");
		}
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
	/**
	 * @param id
	 * @param parameterObject
	 * @throws DataAccessException
	 */
	public void batchDelete(final String id, final List parameterObject) throws DataAccessException {
		if (parameterObject == null) {
			return;
		}

		if (logger.isInfoEnabled()) {
			logger.info("Start  batchDelete:" + parameterObject.size());
		}

		getSqlMapClientTemplate().execute(new SqlMapClientCallback() {
			public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				executor.startBatch();
				for (Object obj : parameterObject) {
					if (obj != null)
						executor.delete(id, obj);
				}
				executor.executeBatch();
				return null;
			}
		});

		if (logger.isInfoEnabled()) {
			logger.info("End  batchDelete");
		}
	}

	/**
	 * @param id
	 * @param parameterObject
	 * @throws DataAccessException
	 */

	public void batchInsert(final HashMap<String, ArrayList> parameterObject) throws DataAccessException {
		if (parameterObject == null || parameterObject.size() == 0) {
			return;
		}

		if (logger.isInfoEnabled()) {
			logger.info("Start  batchInsert:" + parameterObject.size());
		}

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
				if (logger.isInfoEnabled()) {
					logger.info("batchInsert: inserting " + counter + " rows in batch; ");
				}
				return null;
			}
		});
		logger.info("End  batchInsert");
	}

	/**
	 * 
	 * @param id
	 * @param parameterObject
	 * @return int
	 * @throws DataAccessException
	 */
	public int update(String id, Object parameterObject) throws DataAccessException {
		int i = getSqlMapClientTemplate().update(id, parameterObject);
		return i;
	}

	/**
	 * 
	 * @param id
	 * @param parameterObject
	 * @return int
	 * @throws DataAccessException
	 */
	public int delete(String id, Object parameterObject) throws DataAccessException {
		int i = getSqlMapClientTemplate().delete(id, parameterObject);
		return i;
	}

	/**
	 * 
	 * @param key
	 * @return String
	 * @throws DataServiceException
	 */
	public String getAppPropertiesValue(String key) throws DataServiceException {
		try {
			return (String) queryForObject("application_propertiesbykey.query", key);
		} catch (Exception e) {
			throw new DataServiceException("Error", e);
		}
	}

}
