package com.servicelive.integrationtest.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.servicelive.integrationtest.domain.Transaction;


public class TransactionRowMapper implements RowMapper {

	public Transaction mapRow(ResultSet resultSet, int row) throws SQLException {
		Transaction returnVal = new Transaction();
		
		returnVal.setTransactionId(resultSet.getLong("transactionId"));
		returnVal.setBatchId(resultSet.getLong("batchId"));
		returnVal.setTypeName(resultSet.getString("typeName"));
		returnVal.setExternalOrderNumber(resultSet.getString("externalOrderNumber"));
		returnVal.setCreatedOn(resultSet.getTimestamp("createdOn"));
		returnVal.setProcessAfter(resultSet.getTimestamp("processAfter"));
		returnVal.setClaimedOn(resultSet.getTimestamp("claimedOn"));
		returnVal.setClaimedBy(resultSet.getString("claimedBy"));
		returnVal.setStatusName(resultSet.getString("statusName"));
		returnVal.setException(resultSet.getString("exception"));
		returnVal.setServiceLiveOrderId(resultSet.getString("serviceLiveOrderId"));
		
		return returnVal;
	}

}
