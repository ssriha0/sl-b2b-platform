package com.servicelive.esb.integration.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.servicelive.esb.integration.domain.InhomePart;


public class InhomePartRowMapper implements RowMapper{
    public InhomePart mapRow(ResultSet resultSet, int rowNum) throws SQLException {
    	
    	InhomePart returnVal = new InhomePart();
    	returnVal.setPartDivNo(resultSet.getString("partDivNo"));
		returnVal.setPartPlsNo(resultSet.getString("partPlsNo"));
		returnVal.setPartPartNo(resultSet.getString("partPartNo"));
		returnVal.setPartOrderQty(resultSet.getString("partOrderQty"));
		returnVal.setPartInstallQty(resultSet.getString("partInstallQty"));
		returnVal.setPartCoverageCode(resultSet.getString("partCoverageCode"));
		returnVal.setPartPrice(resultSet.getString("partPrice"));
        return returnVal;
    }
}
