package com.servicelive.gl.db;

import com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource;
import com.servicelive.gl.constants.DBConstants;

/**
 * 
 * @author sldev
 *
 */

public class MysqlDataSource extends MysqlConnectionPoolDataSource {

	private static final String user = DBConstants.DB_USER;
	private static final String password = DBConstants.DB_PASSWORD;
	private static final String url = DBConstants.DB_URL;
	
	
	
	public MysqlDataSource() {
		super();
		super.setUrl(url);
		super.setUser(user);
		super.setPassword(password);
		
	}
	
	
	public MysqlDataSource(String theUrl, String usr, String pwd) {
		super();
		super.setUrl(theUrl);
		super.setPassword(pwd);
		super.setUser(usr);
	}


	private static final long serialVersionUID = 1L;

}
