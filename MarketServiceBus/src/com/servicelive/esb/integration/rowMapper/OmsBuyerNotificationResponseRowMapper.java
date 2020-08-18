package com.servicelive.esb.integration.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.servicelive.esb.integration.domain.OmsBuyerNotificationResponse;

public class OmsBuyerNotificationResponseRowMapper extends
AbstractIntegrationDomainRowMapper<OmsBuyerNotificationResponse> {

	@Override
	public OmsBuyerNotificationResponse mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		OmsBuyerNotificationResponse returnValue = new OmsBuyerNotificationResponse(
				rs.getLong("buyerNotificationResponseId"), rs.getLong("transactionId"), 
				rs.getLong("buyerNotificationId"),
				rs.getString("returnCode"), rs.getBoolean("responseStatusSuccess"), 
				rs.getString("responseMessage"),
				rs.getString("processId"), rs.getString("salesCheckNumber"), 
				rs.getString("salesCheckDate"),
				rs.getString("npsStatus"),
				rs.getString("serviceOrderNumber"),
				rs.getString("serviceUnitNumber"),
				rs.getString("serviceLiveOrderId")
			);
		return returnValue;
	}

}
