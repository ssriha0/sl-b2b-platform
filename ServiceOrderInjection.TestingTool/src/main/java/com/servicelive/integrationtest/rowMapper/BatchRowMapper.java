package com.servicelive.integrationtest.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.servicelive.integrationtest.domain.Batch;

public class BatchRowMapper implements RowMapper {
	public Batch mapRow(ResultSet resultSet, int row) throws SQLException {
        Batch returnVal = new Batch();
        returnVal.setBatchId(resultSet.getLong("batchId"));
        returnVal.setCreatedOn(resultSet.getTimestamp("createdOn"));
        returnVal.setException(resultSet.getString("exception"));
        returnVal.setFileName(resultSet.getString("fileName"));
        returnVal.setStatusName(resultSet.getString("statusName"));
        
        return returnVal;
    }
}
