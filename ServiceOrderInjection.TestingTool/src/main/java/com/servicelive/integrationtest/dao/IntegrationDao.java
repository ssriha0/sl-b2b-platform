package com.servicelive.integrationtest.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servicelive.integrationtest.domain.Transaction;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.servicelive.integrationtest.rowMapper.TransactionRowMapper;

public class IntegrationDao implements IIntegrationDao {
	
	private NamedParameterJdbcTemplate jdbcIntegrationTemplate;
	private NamedParameterJdbcTemplate jdbcSupplierTemplate;
	
	
	private static final String sqlRetrieveBatchTransactions = "SELECT * FROM transactions AS t " +
		"JOIN transaction_types AS types ON t.transactionTypeId = types.transactionTypeId JOIN processing_statuses AS status ON t.statusId = status.statusId " +
		"WHERE t.externalOrderNumber IN (:orderIds)";
	
	private static final String sqlRetrieveBatchTransaction = "SELECT * FROM transactions AS t " +
		"JOIN transaction_types AS types ON t.transactionTypeId = types.transactionTypeId JOIN processing_statuses AS status ON t.statusId = status.statusId " +
		"WHERE t.externalOrderNumber LIKE :orderId";
	
	private static final String sqlRetrieveSoIdByExternalOrderNum = "SELECT so_id FROM so_custom_reference s " +
		"WHERE buyer_ref_type_id IN (2, 40) AND buyer_ref_value = :externalOrderNum";
	

	@SuppressWarnings("unchecked")
	public List<Transaction> retrieveAllTransactions(List<String> externalOrderIds) {
		String orderIds = externalOrderIds.toString();
		orderIds = orderIds.substring(1, orderIds.length() - 1);
		Map namedParameters = new HashMap<String, Object>();
		namedParameters.put("orderIds", orderIds);
		List<Transaction> transactions = jdbcIntegrationTemplate.query(sqlRetrieveBatchTransactions, namedParameters, new TransactionRowMapper());
	
		return transactions;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> retrieveSingleOrderTransactions(String externalOrderId) {
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		// use a LIKE clause to retrieve transactions whose order numbers begin with externalOrderId
		namedParameters.put("orderId", externalOrderId + "%");
		List<Transaction> transactions = jdbcIntegrationTemplate.query(sqlRetrieveBatchTransaction, namedParameters, new TransactionRowMapper());
	
		return transactions;
	}
	
	public String getSoIdByExternalOrderNum(String externalOrderNum) {
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("externalOrderNum", externalOrderNum);
		return (String) jdbcSupplierTemplate.queryForObject(sqlRetrieveSoIdByExternalOrderNum, namedParameters, String.class);
	}
	
	public void setJdbcIntegrationTemplate(
			NamedParameterJdbcTemplate jdbcIntegrationTemplate) {
		this.jdbcIntegrationTemplate = jdbcIntegrationTemplate;
	}

	public void setJdbcSupplierTemplate(
			NamedParameterJdbcTemplate jdbcSupplierTemplate) {
		this.jdbcSupplierTemplate = jdbcSupplierTemplate;
	}
	
}
