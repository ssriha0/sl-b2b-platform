package com.newco.marketplace.persistence.dao.document;

import java.util.List;

import org.springframework.dao.DataAccessException;
import com.newco.marketplace.exception.core.DataServiceException;

public interface DocumentDao {

	public List queryForList(String id, Object parameterObject) throws DataAccessException;
	
	public List queryForList(String id) throws DataAccessException;
	
	public Object queryForObject(String id, Object parameterObject)throws DataAccessException;
	
	public Object insert(String id, Object parameterObject)throws DataAccessException;
	
	public int update(String id, Object parameterObject)throws DataAccessException;
	
	public int delete(String id, Object parameterObject) throws DataAccessException;
	
	public String getAppPropertiesValue(String key) throws DataServiceException;
}
