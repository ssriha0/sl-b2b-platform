package com.servicelive.esb.integration.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public abstract class AbstractIntegrationDomainRowMapper<T> implements RowMapper {
	public abstract T mapRow(ResultSet rs, int rowNum) throws SQLException;
	protected <V> V getNullOrValueFrom(V value, boolean returnNull) {
		if (returnNull) return null;
		else return value;
	}

}
