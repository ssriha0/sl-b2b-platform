package com.servicelive.integrationtest.rowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.jdbc.core.RowMapper;

import com.servicelive.integrationtest.domain.QueryCell;
import com.servicelive.integrationtest.domain.QueryRow;

public class GenericMapper implements RowMapper {
	public Map<String, Object> mapRow2(ResultSet resultSet, int row) throws SQLException {
		
		ResultSetMetaData metaData = resultSet.getMetaData();
		Map<String, Object> results = new TreeMap<String, Object>();
		int colCount = metaData.getColumnCount();
		for (int i = 1; i <= colCount; i++) {
			results.put(metaData.getColumnName(i), resultSet.getObject(i));
		}
		return results;
		
	}
	
public QueryRow mapRow(ResultSet resultSet, int row) throws SQLException {
		
		ResultSetMetaData metaData = resultSet.getMetaData();
		int colCount = metaData.getColumnCount();
		
		QueryRow results = new QueryRow();
		
		for (int i = 1; i <= colCount; i++) {
			QueryCell result = new QueryCell();
			result.setColumnName(metaData.getColumnName(i));
			if (resultSet.getObject(i) != null) {
				result.setResult(resultSet.getObject(i).toString());
			} else {
				result.setResult("(null)");
			}
			results.addQueryCell(result);
		}
		
		return results;
		
	}
}
