/**
 * 
 */
package com.servicelive.esb.integration.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.servicelive.esb.integration.domain.BuyerNotification;

/**
 * @author sahmed
 * 
 */
public class BuyerNotificationRowMapper extends
		AbstractIntegrationDomainRowMapper<BuyerNotification> {

	public BuyerNotification mapRow(ResultSet rs, int rowNum) throws SQLException {
		BuyerNotification returnValue = new BuyerNotification(
			rs.getLong("buyerNotificationId"),
			getNullOrValueFrom(rs.getLong("transactionId"), rs.wasNull()),
			rs.getString("notificationEvent"),
			rs.getString("notificationEventSubType"),
			getNullOrValueFrom(rs.getLong("relatedServiceOrderId"), rs.wasNull()));
		return returnValue;
	}
}
