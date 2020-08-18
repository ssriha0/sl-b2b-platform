package com.servicelive.ibatis.sqlchecker;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class MySqlExplainPlanManager {
	public ExplainPlan runExplain(String sql, Connection con) throws SQLException {
		ExplainPlan result = new ExplainPlan();
		String explain = "explain " + sql;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			rs = stmt.executeQuery(explain);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while(rs.next()) {
				ExplainPlanRow row = new ExplainPlanRow();
				for(int i = 1; i <= columnCount; i++) {
					String columnName = rsmd.getColumnName(i);
					Object columnValue = rs.getObject(i);
					row.addColumn(columnName, columnValue);
				}
				result.addRow(row);
			}
		} finally {
			ConnectionUtil.closeNoException(rs);
			ConnectionUtil.closeNoException(stmt);
		}
		return result;
		
	}
}
