package com.servicelive.orderfulfillment.integration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.servicelive.orderfulfillment.integration.domain.Transaction;
import com.servicelive.orderfulfillment.integration.domain.TransactionType;

/**
 * User: Mustafa Motiwala
 * Date: Apr 9, 2010
 * Time: 4:07:02 PM
 */
public class TransactionRowMapper implements RowMapper{
    public Transaction mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Transaction returnVal = new Transaction();
        returnVal.setId(resultSet.getLong("transactionId"));
        returnVal.setBatchId(resultSet.getLong("batchId"));
        returnVal.setExternalOrderNumber(resultSet.getString("externalOrderNumber"));
        returnVal.setType(TransactionType.fromId(resultSet.getInt("transactionTypeId")));
        returnVal.setBuyerName(resultSet.getString("buyerName"));
        returnVal.setPlatformBuyerId(resultSet.getLong("platformBuyerId"));
        returnVal.setServiceOrderId(resultSet.getLong("serviceOrderId"));
        returnVal.setPlatformBuyerRoleId(resultSet.getInt("platformRoleId"));
        returnVal.setBuyerState(resultSet.getString("state"));
        
        returnVal.setBuyerResourceId(resultSet.getLong("buyerResourceId"));
        if(resultSet.wasNull()) returnVal.setBuyerResourceId(null);

        Date createdOnDate = resultSet.getTimestamp("createdOn");
        if (!resultSet.wasNull() && createdOnDate != null) {
        	Calendar createdOn = Calendar.getInstance();
        	createdOn.setTimeInMillis(createdOnDate.getTime());
            returnVal.setCreatedOn(createdOn);
        }
        return returnVal;
    }
}
