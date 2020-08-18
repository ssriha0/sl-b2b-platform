package com.servicelive.orderfulfillment.integration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.servicelive.orderfulfillment.integration.domain.KeyValuePairs;


public class KeyValueRowMapper implements RowMapper {
   
	public KeyValuePairs mapRow(ResultSet resultSet, int i) throws SQLException {
       KeyValuePairs returnVal = new KeyValuePairs();
       
       returnVal.setCategory(resultSet.getString("category"));
       returnVal.setSubCategory(resultSet.getString("sub_category"));
       returnVal.setKeyCode(resultSet.getString("key_code"));
       returnVal.setKeyName(resultSet.getString("key_name"));
       returnVal.setKeyValue(resultSet.getString("key_value"));
       returnVal.setPriorityIndicator(resultSet.getInt("priority_indicator"));
       
       return returnVal;
    }
}
