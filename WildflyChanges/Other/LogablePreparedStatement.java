package com.servicelive.manage1099.db;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

import com.servicelive.manage1099.util.CommonUtil;

/**
 * A wrapper class around preparedStatement to log the queries.
 * 
 * @author mjoshi1
 * 
 */
public class LogablePreparedStatement implements PreparedStatement {
	PreparedStatement stmt;
	String sql;
	String[] parm;

	public LogablePreparedStatement(Connection con, String sql) throws SQLException {
		this.stmt = con.prepareStatement(sql);
		this.sql = sql;
		parm = new String[CommonUtil.countOccurrences(sql, "?")];

	}

	public String toString() {

		if (sql != null && parm != null) {

			for (int i = 0; i < parm.length; i++) {

				sql = CommonUtil.replaceFirstOccurrence(sql, "?", parm[i]);

			}
		}
		return sql;
	}

	/**************************************************************************/
	/* implement PreparedStatement / */

	public ResultSet executeQuery() throws SQLException {
		return stmt.executeQuery();
	}

	public int executeUpdate() throws SQLException {
		return stmt.executeUpdate();
	}

	public void setNull(int parameterIndex, int sqlType) throws SQLException {
		parm[parameterIndex - 1] = null;
		stmt.setNull(parameterIndex, sqlType);
	}

	public void setBoolean(int parameterIndex, boolean x) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setBoolean(parameterIndex, x);
	}

	public void setByte(int parameterIndex, byte x) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setByte(parameterIndex, x);
	}

	public void setShort(int parameterIndex, short x) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setShort(parameterIndex, x);
	}

	public void setInt(int parameterIndex, int x) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setInt(parameterIndex, x);
	}

	public void setLong(int parameterIndex, long x) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setLong(parameterIndex, x);
	}

	public void setFloat(int parameterIndex, float x) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setFloat(parameterIndex, x);
	}

	public void setDouble(int parameterIndex, double x) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setDouble(parameterIndex, x);
	}

	public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setBigDecimal(parameterIndex, x);
	}

	public void setString(int parameterIndex, String x) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setString(parameterIndex, x);
	}

	public void setBytes(int parameterIndex, byte[] x) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setBytes(parameterIndex, x);
	}

	public void setDate(int parameterIndex, java.sql.Date x) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setDate(parameterIndex, x);
	}

	public void setTime(int parameterIndex, Time x) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setTime(parameterIndex, x);
	}

	public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setTimestamp(parameterIndex, x);
	}

	public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setAsciiStream(parameterIndex, x, length);
	}

	@Deprecated
	public void setUnicodeStream(int parameterIndex, InputStream x, int length) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setUnicodeStream(parameterIndex, x, length);
	}

	public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setBinaryStream(parameterIndex, x, length);
	}

	public void clearParameters() throws SQLException {
		parm = new String[parm.length];
		stmt.clearParameters();
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType, int scale) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setObject(parameterIndex, x, targetSqlType, scale);
	}

	public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
		parm[parameterIndex - 1] = "" + x;
		stmt.setObject(parameterIndex, x, targetSqlType);
	}

	public void setObject(int parameterIndex, Object x) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setObject(parameterIndex, x);
	}

	public boolean execute() throws SQLException {
		return stmt.execute();
	}

	public void addBatch() throws SQLException {
		stmt.addBatch();
	}

	public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
		parm[parameterIndex - 1] = reader.toString();
		stmt.setCharacterStream(parameterIndex, reader, length);
	}

	public void setRef(int i, Ref x) throws SQLException {
		parm[i - 1] = String.valueOf(x);
		stmt.setRef(i, x);
	}

	public void setBlob(int i, Blob x) throws SQLException {
		parm[i - 1] = String.valueOf(x);
		stmt.setBlob(i, x);
	}

	public void setClob(int i, Clob x) throws SQLException {
		parm[i - 1] = String.valueOf(x);
		stmt.setClob(i, x);
	}

	public void setArray(int i, Array x) throws SQLException {
		parm[i - 1] = String.valueOf(x);
		stmt.setArray(i, x);
	}

	public ResultSetMetaData getMetaData() throws SQLException {
		return stmt.getMetaData();
	}

	public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setDate(parameterIndex, x, cal);
	}

	public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setTime(parameterIndex, x, cal);
	}

	public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
		parm[parameterIndex - 1] = String.valueOf(x);
		stmt.setTimestamp(parameterIndex, x, cal);
	}

	public void setNull(int paramIndex, int sqlType, String typeName) throws SQLException {
		parm[paramIndex - 1] = null;
		stmt.setNull(paramIndex, sqlType, typeName);
	}

	public ResultSet executeQuery(String sql) throws SQLException {
		return stmt.executeQuery(sql);
	}

	public int executeUpdate(String sql) throws SQLException {
		return stmt.executeUpdate(sql);
	}

	public void close() throws SQLException {
		stmt.close();
	}

	public int getMaxFieldSize() throws SQLException {
		return stmt.getMaxFieldSize();
	}

	public void setMaxFieldSize(int max) throws SQLException {
		stmt.setMaxFieldSize(max);
	}

	public int getMaxRows() throws SQLException {
		return stmt.getMaxRows();
	}

	public void setMaxRows(int max) throws SQLException {
		stmt.setMaxRows(max);
	}

	public void setEscapeProcessing(boolean enable) throws SQLException {
		stmt.setEscapeProcessing(enable);
	}

	public int getQueryTimeout() throws SQLException {
		return stmt.getQueryTimeout();
	}

	public void setQueryTimeout(int seconds) throws SQLException {
		stmt.setQueryTimeout(seconds);
	}

	public void cancel() throws SQLException {
		stmt.cancel();
	}

	public SQLWarning getWarnings() throws SQLException {
		return stmt.getWarnings();
	}

	public void clearWarnings() throws SQLException {
		stmt.clearWarnings();
	}

	public void setCursorName(String name) throws SQLException {
		stmt.setCursorName(name);
	}

	public boolean execute(String sql) throws SQLException {
		return stmt.execute(sql);
	}

	public ResultSet getResultSet() throws SQLException {
		return stmt.getResultSet();
	}

	public int getUpdateCount() throws SQLException {
		return stmt.getUpdateCount();
	}

	public boolean getMoreResults() throws SQLException {
		return stmt.getMoreResults();
	}

	public void setFetchDirection(int direction) throws SQLException {
		stmt.setFetchDirection(direction);
	}

	public int getFetchDirection() throws SQLException {
		return stmt.getFetchDirection();
	}

	public void setFetchSize(int rows) throws SQLException {
		stmt.setFetchSize(rows);
	}

	public int getFetchSize() throws SQLException {
		return stmt.getFetchSize();
	}

	public int getResultSetConcurrency() throws SQLException {
		return stmt.getResultSetConcurrency();
	}

	public int getResultSetType() throws SQLException {
		return stmt.getResultSetType();
	}

	public void addBatch(String sql) throws SQLException {
		stmt.addBatch(sql);
	}

	public void clearBatch() throws SQLException {
		stmt.clearBatch();
	}

	public int[] executeBatch() throws SQLException {
		return stmt.executeBatch();
	}

	public Connection getConnection() throws SQLException {
		return stmt.getConnection();
	}

	public ParameterMetaData getParameterMetaData() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setURL(int parameterIndex, URL x) throws SQLException {
		// TODO Auto-generated method stub

	}

	public boolean execute(String sql, int autoGeneratedKeys) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean execute(String sql, int[] columnIndexes) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean execute(String sql, String[] columnNames) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public int executeUpdate(String sql, int autoGeneratedKeys) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int executeUpdate(String sql, int[] columnIndexes) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public int executeUpdate(String sql, String[] columnNames) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public ResultSet getGeneratedKeys() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean getMoreResults(int current) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public int getResultSetHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setPoolable(boolean poolable) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isPoolable() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void closeOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isCloseOnCompletion() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRowId(int parameterIndex, RowId x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNString(int parameterIndex, String value) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int parameterIndex, NClob value) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setClob(int parameterIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setNClob(int parameterIndex, Reader reader) throws SQLException {
		// TODO Auto-generated method stub
		
	}

}
