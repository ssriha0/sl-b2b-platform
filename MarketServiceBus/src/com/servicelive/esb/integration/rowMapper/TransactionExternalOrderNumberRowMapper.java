package com.servicelive.esb.integration.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TransactionExternalOrderNumberRowMapper extends AbstractIntegrationDomainRowMapper<String> {

	public String mapRow(ResultSet resultSet, int row) throws SQLException {
		String returnVal = resultSet.getString("externalOrderNumber");
        return returnVal;
	}

}
