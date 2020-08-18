package com.servicelive.esb.integration.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.servicelive.esb.integration.domain.OmsBuyerNotificationResponseMessage;

public class OmsBuyerNotificationResponseMessageRowMapper extends
AbstractIntegrationDomainRowMapper<OmsBuyerNotificationResponseMessage> {

	@Override
	public OmsBuyerNotificationResponseMessage mapRow(ResultSet rs, int rowNum)
			throws SQLException {
		OmsBuyerNotificationResponseMessage returnValue = new OmsBuyerNotificationResponseMessage(
				rs.getLong("omsBuyerNotificationResponseMessageId"), 
				rs.getLong("buyerNotificationResponseId"),
				rs.getString("message"),
				rs.getInt("audit_owner_id"),
				rs.getString("owner_name"),
				rs.getString("email_ids"),
				rs.getString("work_instruction"),
				getNullOrValueFrom(rs.getBoolean("success_ind"), rs.wasNull()),
				getNullOrValueFrom(rs.getBoolean("reportable"), rs.wasNull())
			);
		return returnValue;
	}

}
