package com.servicelive.esb.integration.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.servicelive.esb.integration.domain.Lock;

public class LockRowMapper extends AbstractIntegrationDomainRowMapper<Lock> {

	@Override
	public Lock mapRow(ResultSet rs, int rowNum) throws SQLException {
		Lock returnVal = new Lock();
		returnVal.setLockName(rs.getString("lock_name"));
		returnVal.setAcquired(rs.getBoolean("acquired"));
		returnVal.setAcquiredBy(rs.getString("acquired_by"));
		returnVal.setAcquiredOn(this.getNullOrValueFrom(rs.getDate("acquired_on"), rs.wasNull()));

		return returnVal;
	}

}
