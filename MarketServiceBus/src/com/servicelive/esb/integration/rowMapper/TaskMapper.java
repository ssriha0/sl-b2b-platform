package com.servicelive.esb.integration.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.servicelive.esb.integration.domain.Task;

public class TaskMapper extends AbstractIntegrationDomainRowMapper<Task> {

	@Override
	public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
		Task task = new Task(
				rs.getLong("taskId"),
				this.getNullOrValueFrom(rs.getLong("serviceOrderId"), rs.wasNull()),
				rs.getString("title"),
				rs.getString("comments"),
				this.getNullOrValueFrom(rs.getBoolean("default"), rs.wasNull()),
				rs.getString("externalSku"),
				rs.getString("specialtyCode"),
				this.getNullOrValueFrom(rs.getDouble("amount"), rs.wasNull()),
				rs.getString("category"),
				rs.getString("subCategory"),
				rs.getString("serviceType"),
				rs.getInt("sequence_number")
				);
		return task;
	}

}
