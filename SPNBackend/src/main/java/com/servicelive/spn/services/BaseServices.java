/**
 *
 */
package com.servicelive.spn.services;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author hoza
 *
 */
public abstract class BaseServices {
	protected final Log logger = LogFactory.getLog(this.getClass());
	protected SqlMapClient sqlMapClient = null;

	//if you are implementing any Date specific operation this method will become handy. 
	//For example you are converting GMT to CST or 
	//If you are converting DB Sttored time to Someonele locale  this should be available handy 
	protected abstract void handleDates(Object entity);

	/**
	 * @return the sqlMapClient
	 */
	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	/**
	 * @param sqlMapClient the sqlMapClient to set
	 */
	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}

}
