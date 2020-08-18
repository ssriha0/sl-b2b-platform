package com.servicelive.esb.integration.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.servicelive.esb.constant.MarketESBConstant;

public class AssurantCustomReferenceRowMapper implements RowMapper {
	public Map<String, String> mapRow(ResultSet resultSet, int row) throws SQLException {
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put(MarketESBConstant.CUSTOM_REF_PARTSLABORFLAG, resultSet.getString("partLaborFlag"));
        returnMap.put(MarketESBConstant.CUSTOM_REF_CONTRACTDATE, dateFormatter.format(resultSet.getDate("contractDate")));
        returnMap.put(MarketESBConstant.CUSTOM_REF_ASSOCIATED_INCIDENT, resultSet.getString("associatedIncident"));
        returnMap.put(MarketESBConstant.CUSTOM_REF_CONTRACT_NUMBER, resultSet.getString("contractNumber"));
        returnMap.put(MarketESBConstant.CUSTOM_REF_SL_INCIDENT_ID, resultSet.getString("serviceOrderId"));
        returnMap.put(MarketESBConstant.CUSTOM_REF_INCIDENT_ID, resultSet.getString("incidentId"));
        returnMap.put(MarketESBConstant.CUSTOM_REF_RETAILER, resultSet.getString("retailer"));
        returnMap.put(MarketESBConstant.CUSTOM_REF_CLASSCODE, resultSet.getString("classCode"));
        returnMap.put(MarketESBConstant.CUSTOM_REF_PRIMARY_PARTNUMBER, resultSet.getString("primaryPartNumber"));
        returnMap.put(MarketESBConstant.CUSTOM_REF_CONTRACTTYPE, resultSet.getString("incidentContractType"));
		
        return returnMap;
    }
}
