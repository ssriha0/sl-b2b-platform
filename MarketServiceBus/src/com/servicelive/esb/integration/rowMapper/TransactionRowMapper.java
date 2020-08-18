package com.servicelive.esb.integration.rowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.servicelive.esb.integration.domain.ProcessingStatus;
import com.servicelive.esb.integration.domain.Transaction;
import com.servicelive.esb.integration.domain.TransactionType;

public class TransactionRowMapper extends AbstractIntegrationDomainRowMapper<Transaction> {

	public Transaction mapRow(ResultSet rs, int row) throws SQLException {
		Transaction returnVal = new Transaction(
			rs.getLong("transactionId"),
			this.getNullOrValueFrom(rs.getLong("batchId"), rs.wasNull()),
			null, // handle the transaction type separately
			rs.getString("externalOrderNumber"),
			rs.getString("serviceLiveOrderId"),
			this.getNullOrValueFrom(rs.getDate("processAfter"), rs.wasNull()),
			null, // handle the processing status separately
			this.getNullOrValueFrom(rs.getTimestamp("createdOn"), rs.wasNull()),
			this.getNullOrValueFrom(rs.getTimestamp("claimedOn"), rs.wasNull()),
			rs.getString("claimedBy"));

		int transactionTypeId = rs.getInt("transactionTypeId");
		if (!rs.wasNull()) returnVal.setTransactionType(TransactionType.fromId(transactionTypeId));
		
		int processingStatusId = rs.getInt("statusId");
		if (!rs.wasNull()) returnVal.setProcessingStatus(ProcessingStatus.fromId(processingStatusId));

		return returnVal;
    }

}
