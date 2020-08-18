package com.servicelive.esb.integration.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.servicelive.esb.integration.domain.HsrBuyerNotification;

public class HsrBuyerNotificationRowMapper extends
		AbstractIntegrationDomainRowMapper<HsrBuyerNotification> {

	public HsrBuyerNotification mapRow(ResultSet rs, int rowNum) throws SQLException {
		HsrBuyerNotification returnValue = new HsrBuyerNotification(
			rs.getLong("hsrBuyerNotificationId"),
			getNullOrValueFrom(rs.getLong("buyerNotificationId"), rs.wasNull()),
			rs.getString("unitNumber"),
			rs.getString("orderNumber"),
			getNullOrValueFrom(rs.getTimestamp("routedDate"), rs.wasNull()),
			rs.getString("techId"));
		return returnValue;
	}

}
