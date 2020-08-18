package com.servicelive.esb.integration.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.servicelive.esb.integration.domain.AssurantBuyerNotification;

public class AssurantBuyerNotificationRowMapper extends
		AbstractIntegrationDomainRowMapper<AssurantBuyerNotification> {

	public AssurantBuyerNotification mapRow(ResultSet rs, int rowNum) throws SQLException {
		AssurantBuyerNotification returnValue = new AssurantBuyerNotification(
			rs.getLong("assurantBuyerNotificationId"),
			getNullOrValueFrom(rs.getLong("buyerNotificationId"), rs.wasNull()),
			getNullOrValueFrom(rs.getTimestamp("etaOrShippingDate"), rs.wasNull()),
			rs.getString("shippingAirBillNumber"),
			rs.getString("returnAirBillNumber"),
			rs.getString("incidentActionDescription"));
		return returnValue;
	}

}
