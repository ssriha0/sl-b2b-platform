package com.servicelive.orderfulfillment.integration.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author Samir Ahmed
 * @since Aug 10, 2010 12:31:02 PM
 */
public class TransactionIdRowMapper implements RowMapper {
	public Long mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Long returnVal = resultSet.getLong("transactionId");
		return returnVal;
	}
}
