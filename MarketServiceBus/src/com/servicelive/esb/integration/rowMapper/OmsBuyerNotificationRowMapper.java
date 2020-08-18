package com.servicelive.esb.integration.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.servicelive.esb.integration.domain.OmsBuyerNotification;

public class OmsBuyerNotificationRowMapper extends
		AbstractIntegrationDomainRowMapper<OmsBuyerNotification> {

	@Override
	public OmsBuyerNotification mapRow(ResultSet rs, int rowNum) throws SQLException {
		OmsBuyerNotification returnValue = new OmsBuyerNotification(
				rs.getLong("omsBuyerNotificationId"),
				getNullOrValueFrom(rs.getLong("buyerNotificationId"), rs.wasNull()),
				rs.getString("techComment"),
				rs.getString("modelNumber"),
				rs.getString("serialNumber"),
				getNullOrValueFrom(rs.getDate("installationDate"), rs.wasNull()),
				getNullOrValueFrom(rs.getBigDecimal("amountCollected"), rs.wasNull()),
				rs.getString("paymentMethod"),
				rs.getString("paymentAccountNumber"),
				rs.getString("paymentExpirationDate"),
				rs.getString("paymentAuthorizationNumber"),
				rs.getString("maskedAccountNo"),
				rs.getString("token")
			);
		return returnValue;
	}

}
