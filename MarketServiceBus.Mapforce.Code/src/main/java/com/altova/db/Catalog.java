package com.altova.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;

public class Catalog 
{
	private Connection connection = null;
	private HashMap<Integer, Statement> map = null;
	private int visible;
	
	public Catalog(Connection conn)
	{
		connection = conn;
		map = new HashMap<Integer, Statement>();
		visible = 1;
	}
	
	public void close() throws Exception
	{
		freeConnection();
	}
	
	public void freeConnection() throws Exception
	{
		visible--;
		if (visible == 0)
			connection.close();		
	}
	
	public void allocateConnection()
	{
		visible++;
	}
	
	public Connection getConnection()
	{
		return connection;
	}
	
	public Statement createStatement(int id, String statement_string) throws Exception
	{
		Statement statement = new Statement(this, statement_string);
		map.put(id, statement);
		return statement;
	}
	
	public Statement getStatement(int id)
	{
		return map.get(id);
	}
}
