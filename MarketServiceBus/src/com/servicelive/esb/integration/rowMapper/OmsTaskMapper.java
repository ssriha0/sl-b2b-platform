package com.servicelive.esb.integration.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.servicelive.esb.integration.domain.OmsTask;

public class OmsTaskMapper extends AbstractIntegrationDomainRowMapper<OmsTask> {

	@Override
	public OmsTask mapRow(ResultSet rs, int row) throws SQLException {
		OmsTask omsTask = new OmsTask(
				rs.getLong("taskOmsId"),
				this.getNullOrValueFrom(rs.getLong("taskId"), rs.wasNull()),
				rs.getString("chargeCode"),
				rs.getString("coverage"),
				rs.getString("type"),
				rs.getString("description")
			);
		return omsTask;
	}

}
