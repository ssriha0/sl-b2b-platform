package com.servicelive.esb.integration.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.servicelive.esb.integration.domain.Batch;
import com.servicelive.esb.integration.domain.ProcessingStatus;

public class BatchRowMapper extends AbstractIntegrationDomainRowMapper<Batch> {
	public Batch mapRow(ResultSet rs, int row) throws SQLException {
        Batch returnVal = new Batch(
    		rs.getLong("batchId"),
    		getNullOrValueFrom(rs.getLong("integrationId"), rs.wasNull()),
    		rs.getString("fileName"),
    		getNullOrValueFrom(rs.getDate("createdOn"), rs.wasNull()),
    		null, //handle processing status separately
    		rs.getString("exception"));
        
        int processingStatusId = rs.getInt("statusId");
        if (!rs.wasNull()) returnVal.setProcessingStatus(ProcessingStatus.fromId(processingStatusId));

        return returnVal;
    }
}
