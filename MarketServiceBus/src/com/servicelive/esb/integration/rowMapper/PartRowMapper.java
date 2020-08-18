package com.servicelive.esb.integration.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.servicelive.esb.integration.domain.Part;

public class PartRowMapper extends AbstractIntegrationDomainRowMapper<Part> {
	public Part mapRow(ResultSet rs, int row) throws SQLException {
		Part returnVal = new Part();
		returnVal.setServiceOrderId(rs.getLong("serviceOrderId"));
		returnVal.setClassCode(rs.getString("classCode"));
		returnVal.setClassComments(rs.getString("classComments"));
		returnVal.setQuantity(rs.getInt("quantity"));
		returnVal.setManufacturer(rs.getString("Manufacturer"));
		returnVal.setModelNumber(rs.getString("modelNumber"));
        return returnVal;
    }
}
