package com.servicelive.esb.integration.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.servicelive.esb.constant.MarketESBConstant;

public class HSRCustomReferenceRowMapper extends
		AbstractIntegrationDomainRowMapper<Map<String, String>> {

	public Map<String, String> mapRow(ResultSet rs, int row) throws SQLException {
        Map<String, String> returnMap = new HashMap<String, String>();
		populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_UNIT_NUM_HSR, this.getNullOrValueFrom(rs.getString("unitNumber"), rs.wasNull()));
		populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_ORDER_NUM_HSR, this.getNullOrValueFrom(rs.getString("orderNumber"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_ORDERID_STRING, this.getNullOrValueFrom(rs.getString("orderIdstring"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_CUSTOMER_NUMBER, this.getNullOrValueFrom(rs.getString("customerNumber"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_PAYMENT_METHOD_IND, this.getNullOrValueFrom(rs.getString("paymentMethod"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_PRIORITY_IND, this.getNullOrValueFrom(rs.getString("priorityIndicator"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_COVERAGE_TYPE_LABOR_HSR, this.getNullOrValueFrom(rs.getString("coverageTypeLabor"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_COVERAGE_TYPE_PARTS_HSR, this.getNullOrValueFrom(rs.getString("coverageTypeParts"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_DIVISION, this.getNullOrValueFrom(rs.getString("division"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_MERCHANDISE_CODE, this.getNullOrValueFrom(rs.getString("merchandiseCode"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_BRAND, this.getNullOrValueFrom(rs.getString("brand"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_MODEL, this.getNullOrValueFrom(rs.getString("model"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_SERIAL_NUMBER, this.getNullOrValueFrom(rs.getString("serialNumber"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_STORE_NUMBER, this.getNullOrValueFrom(rs.getString("searsStoreNumber"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_PURCHASE_DATE, this.getNullOrValueFrom(rs.getString("purchaseDate"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_PROMOTION_IND, this.getNullOrValueFrom(rs.getString("promotionIndicator"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_PROC_ID, this.getNullOrValueFrom(rs.getString("procId"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_CONTRACT_NUMBER, this.getNullOrValueFrom(rs.getString("contractNumber"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_CONTRACT_EXP, this.getNullOrValueFrom(rs.getString("contractExpDate"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_AUTH_NUMBER, this.getNullOrValueFrom(rs.getString("authorizationNumber"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_MODIFIED_BY_EMPLOYEE_ID, this.getNullOrValueFrom(rs.getString("lastModifiedEmployeeID"), rs.wasNull()));
        populateNonNullKeyValuesInMap(returnMap, MarketESBConstant.CUSTOM_REF_EXTERNAL_STATUS, this.getNullOrValueFrom(rs.getString("serviceOrderStatusCode"), rs.wasNull()));

        return returnMap;
    }
	
	private void populateNonNullKeyValuesInMap(Map<String, String> map, String key, String value) {
		if (value != null) map.put(key, value);
	}
}
