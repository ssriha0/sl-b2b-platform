package com.servicelive.esb.integration.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.servicelive.esb.constant.MarketESBConstant;

public class OMSCustomReferenceRowMapper implements RowMapper {
	public Map<String, String> mapRow(ResultSet resultSet, int row) throws SQLException {
        Map<String, String> returnMap = new HashMap<String, String>();
        returnMap.put(MarketESBConstant.CUSTOM_REF_UNIT_NUM, resultSet.getString("unitNumber"));
		returnMap.put(MarketESBConstant.CUSTOM_REF_ORDER_NUM, resultSet.getString("orderNumber"));
		returnMap.put(MarketESBConstant.CUSTOM_REF_SALES_CHECK_NUM, resultSet.getString("salesCheckNumber"));
		returnMap.put(MarketESBConstant.CUSTOM_REF_SALES_CHECK_DATE, resultSet.getString("salesCheckDate"));
		returnMap.put(MarketESBConstant.CUSTOM_REF_SALES_CHECK_TIME, resultSet.getString("salesCheckTime"));
		returnMap.put(MarketESBConstant.CUSTOM_REF_PREF_LANG, resultSet.getString("preferredLanguage"));
		returnMap.put(MarketESBConstant.CUSTOM_REF_SERVICE_REQUESTED, resultSet.getString("serviceRequested"));
		returnMap.put(MarketESBConstant.CUSTOM_REF_SELLING_ASSOC, resultSet.getString("sellingAssociate"));
		returnMap.put(MarketESBConstant.CUSTOM_REF_STORE_NUMBER, resultSet.getString("storeNumber"));
		returnMap.put("SCIMH Handlng Code", resultSet.getString("scimHandlingCode"));
		returnMap.put("SCIMH Handlng Description", resultSet.getString("scimHandlingDescription"));
		returnMap.put("Last Maintenance Date", resultSet.getString("lastMaintenanceDate"));
		returnMap.put(MarketESBConstant.CUSTOM_REF_MERCHANDISE_AVAILABILITY_DATE, resultSet.getString("merchandiseAvailabilityDate"));
		returnMap.put(MarketESBConstant.CUSTOM_REF_PICKUP_LOCATION_CODE, resultSet.getString("pickupLocationCode"));
		returnMap.put(MarketESBConstant.CUSTOM_REF_SPECIALTY_CODE, resultSet.getString("specialtyCode"));
		returnMap.put(MarketESBConstant.CUSTOM_REF_MERCHANDISE_CODE, resultSet.getString("merchandiseCode"));
		returnMap.put(MarketESBConstant.CUSTOM_REF_BRAND, resultSet.getString("brand"));
		returnMap.put(MarketESBConstant.CUSTOM_REF_ORDERID_STRING, resultSet.getString("orderIdString"));
		returnMap.put(MarketESBConstant.CUSTOM_REF_DATE_CALCULATION_METHOD, resultSet.getString("dateCalculationMethod"));
		returnMap.put(MarketESBConstant.CUSTOM_REF_MAIN_SKU, resultSet.getString("mainSku"));
		returnMap.put(MarketESBConstant.CUSTOM_REF_PROCESS_ID, resultSet.getString("processId"));
		returnMap.put(MarketESBConstant.CUSTOM_REF_DIVISION, resultSet.getString("division"));
        
        return returnMap;
    }
}
