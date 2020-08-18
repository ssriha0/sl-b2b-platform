package com.servicelive.esb.integration.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.servicelive.esb.integration.DataException;
import com.servicelive.esb.integration.domain.AssurantBuyerNotification;
import com.servicelive.esb.integration.domain.Batch;
import com.servicelive.esb.integration.domain.BuyerNotification;
import com.servicelive.esb.integration.domain.Contact;
import com.servicelive.esb.integration.domain.Document;
import com.servicelive.esb.integration.domain.HsrBuyerNotification;
import com.servicelive.esb.integration.domain.IntegrationName;
import com.servicelive.esb.integration.domain.Location;
import com.servicelive.esb.integration.domain.Lock;
import com.servicelive.esb.integration.domain.OmsBuyerNotification;
import com.servicelive.esb.integration.domain.OmsBuyerNotificationResponse;
import com.servicelive.esb.integration.domain.OmsBuyerNotificationResponseMessage;
import com.servicelive.esb.integration.domain.OmsTask;
import com.servicelive.esb.integration.domain.Part;
import com.servicelive.esb.integration.domain.Phone;
import com.servicelive.esb.integration.domain.ProcessingStatus;
import com.servicelive.esb.integration.domain.ServiceOrder;
import com.servicelive.esb.integration.domain.Task;
import com.servicelive.esb.integration.domain.Transaction;
import com.servicelive.esb.integration.domain.TransactionType;
import com.servicelive.esb.integration.rowMapper.AssurantBuyerNotificationRowMapper;
import com.servicelive.esb.integration.rowMapper.AssurantCustomReferenceRowMapper;
import com.servicelive.esb.integration.rowMapper.BatchRowMapper;
import com.servicelive.esb.integration.rowMapper.BuyerNotificationRowMapper;
import com.servicelive.esb.integration.rowMapper.HSRCustomReferenceRowMapper;
import com.servicelive.esb.integration.rowMapper.HsrBuyerNotificationRowMapper;
import com.servicelive.esb.integration.rowMapper.LockRowMapper;
import com.servicelive.esb.integration.rowMapper.OMSCustomReferenceRowMapper;
import com.servicelive.esb.integration.rowMapper.OmsBuyerNotificationResponseMessageRowMapper;
import com.servicelive.esb.integration.rowMapper.OmsBuyerNotificationResponseRowMapper;
import com.servicelive.esb.integration.rowMapper.OmsBuyerNotificationRowMapper;
import com.servicelive.esb.integration.rowMapper.OmsTaskMapper;
import com.servicelive.esb.integration.rowMapper.PartRowMapper;
import com.servicelive.esb.integration.rowMapper.ServiceOrderRetryMapper;
import com.servicelive.esb.integration.rowMapper.ServiceOrderRowMapper;
import com.servicelive.esb.integration.rowMapper.TaskMapper;
import com.servicelive.esb.integration.rowMapper.TransactionExternalOrderNumberRowMapper;
import com.servicelive.esb.integration.rowMapper.TransactionRowMapper;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;

public class IntegrationDao implements IIntegrationDao {

	private static final Log logger = LogFactory.getLog(IntegrationDao.class);
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    private static final String sqlInsertBatch =
    	" INSERT INTO batches (integrationId, fileName, statusId, createdOn, exception) \n" + 
    	" VALUES (:integrationId, :fileName, :statusId, :createdOn, :exception)";
    
    private static final String sqlUpdateBatch =
    	" UPDATE batches \n" +
    	" SET \n" +
    	"   fileName = :fileName, \n" +
    	"   statusId = :statusId, \n" +
    	"   exception = :exception \n" +
    	" WHERE batchId = :batchId";
    
    private static final String sqlUpdateBatchFileName =
    	" UPDATE batches \n" +
    	" SET fileName = :fileName \n" +
    	" WHERE batchId = :batchId";
    
    private static final String sqlUpdateTransactionStatus =
    	" UPDATE transactions \n" +
    	" SET \n" +
    	"   statusId = :statusId, \n" +
    	"   statusReason = :statusReason \n" +
    	" WHERE transactionId IN (:transactionIdsList)";
    
    private static final String sqlGetServiceOrdersByBatchInputFile =
    	" SELECT so.serviceOrderId, so.customRefs, so.transactionId \n" +
    	" FROM serviceorders so \n" + 
    	"   INNER JOIN transactions t ON so.transactionId = t.transactionId \n" +
    	"   INNER JOIN batches b ON t.batchId = b.batchId \n" + 
    	" WHERE b.fileName = :fileName \n" +
    	"   AND b.statusId = :processingStatusId ";
    
    private static final String sqlGetServiceOrdersByTransactionIdsList =
    	" SELECT so.serviceOrderId, so.customRefs, so.transactionId, so.serviceWindowStartDate \n" +
    	"   FROM serviceorders so \n" +
    	"  WHERE so.transactionId IN (:transactionIdsList)";
    
    private static final String sqlUpdateTransactionStatusByInputFile = 
    	" UPDATE transactions \n" +
    	"   INNER JOIN batches ON transactions.batchId = batches.batchId \n" +
    	" SET transactions.statusId = :newTransactionStatus \n" +
    	" WHERE transactions.statusId = :oldTransactionStatus \n" +
    	"   AND batches.statusId = :batchStatus \n" +
    	" AND fileName = :fileName";
    
    private static final String sqlGetExistingExternalServiceOrderIds =
    	" SELECT t.externalOrderNumber \n" +
    	" FROM transactions t \n" +
    	" WHERE t.transactionTypeId = :transactionTypeId \n" +
    	"   AND (t.migrationStatus <> :migrationStatusComplete \n" +
    	"   OR migrationStatus IS NULL) \n" +
    	"   AND t.externalOrderNumber IN (:serviceOrderIds)";
    
    private static final String sqlGetLatestServiceOrderThatMatchesOrderNumberBeforeTestSuffix =
		" SELECT t.* \n" +
		" FROM transactions t \n" +
		" WHERE t.transactionTypeId = :transactionTypeId \n" +
		"   AND t.externalOrderNumber LIKE :externalOrderNumber";
    
    private static final String sqlGetOmsCustomReferences =
    	" SELECT cro.* FROM customrefs_oms cro WHERE cro.serviceOrderId = :serviceOrderId";
    
    private static final String sqlGetAssurantCustomReferences =
    	" SELECT cra.* FROM customrefs_assurant cra WHERE cra.serviceOrderId = :serviceOrderId";
    
    private static final String sqlGetHSRCustomReferences =
    	" SELECT crh.* FROM customrefs_hsr crh WHERE crh.serviceOrderId = :serviceOrderId";
    
    private static final String sqlSaveServiceOrderCustomRefs =
    	" UPDATE serviceorders \n" +
    	" SET customRefs = :customRefs \n" +
    	" WHERE serviceOrderId = :serviceOrderId";
    
    private static final String sqlGetBatchById =
    	" SELECT b.* FROM batches b WHERE b.batchId = :batchId";
    
    private static final String sqlGetLatestBatchByFileName = 
    	" SELECT b.* FROM batches b WHERE b.fileName = :fileName order by createdOn desc limit 1";

    private static final String sqlfindLatestServiceOrderByExternalOrderNumber =
    	" SELECT so.* FROM transactions t \n" +
    	"   INNER JOIN serviceorders so ON t.transactionId = so.transactionId \n" +
    	" WHERE t.externalOrderNumber = :externalOrderNumber \n" +
    	"   AND t.transactionTypeId IN (1, 2, 3, 4, 5) \n" +
    	" ORDER BY t.createdOn DESC LIMIT 1";
    
    private static final String sqlFindBatchesByIntegrationIdAndStatus =
    	" SELECT b.* FROM batches b WHERE b.integrationId = :integrationId AND b.statusId = :statusId";
    
    private static final String sqlInsertTransaction = 
    	" INSERT INTO transactions (batchId, transactionTypeId, externalOrderNumber, processAfter, createdOn, statusId, serviceLiveOrderId, buyerResourceId) \n" +
    	" VALUES (:batchId, :transactionTypeId, :externalOrderNumber, :processAfter, :createdOn, :statusId, :serviceLiveOrderId, :buyerResourceId)";

    private static final String sqlInsertServiceOrder = 
    	" INSERT INTO serviceorders (transactionId, laborSpendLimit, partsSpendLimit, title, description, providerInstructions, serviceWindowStartDate, serviceWindowStartTime, serviceWindowEndDate, serviceWindowEndTime, templateId, providerServiceConfirmInd, partsSuppliedBy, serviceWindowTypeFixed, mainServiceCategory, buyerTermsAndConditions) \n" +
    	" VALUES (:transactionId, :laborSpendLimit, :partsSpendLimit, :title, :description, :providerInstructions, :serviceWindowStartDate, :serviceWindowStartTime, :serviceWindowEndDate, :serviceWindowEndTime, :templateId, :providerServiceConfirmInd, :partsSuppliedBy, :serviceWindowTypeFixed, :mainServiceCategory, :buyerTermsAndConditions)";
    
    private static final String sqlInsertContact = 
    	" INSERT INTO contacts (serviceOrderId, businessName, firstName, lastName, email) \n" +
    	" VALUES (:serviceOrderId, :businessName, :firstName, :lastName, :email)";
    
    private static final String sqlInsertPhone = 
    	" INSERT INTO phones (contactId, phoneNumber, phoneType, phones.primary) \n" +
    	" VALUES (:contactId, :phoneNumber, :phoneType, :primary)";
    
    private static final String sqlInsertLocation = 
    	" INSERT INTO locations (serviceOrderId, locationClassification, locationNotes, addressLine1, addressLine2, city, state, zipCode) \n" +
    	" VALUES (:serviceOrderId, :locationClassification, :locationNotes, :addressLine1, :addressLine2, :city, :state, :zipCode)";
    
    private static final String sqlInsertTask = 
    	" INSERT INTO tasks (serviceOrderId, title, comments, tasks.default, externalSku, specialtyCode, amount, category, subCategory, serviceType, sequence_number) \n" +
    	" VALUES (:serviceOrderId, :title, :comments, :default, :externalSku, :specialtyCode, :amount, :category, :subCategory, :serviceType, :sequence_number)";
    
    private static final String sqlInsertOmsTask = 
    	" INSERT INTO tasks_oms (`taskId`, `chargeCode`, `coverage`, `type`, `description`) \n" +
    	"VALUES (:taskId, :chargeCode, :coverage, :type, :description)";

    private static final String sqlInsertPart = 
    	" INSERT INTO parts (serviceOrderId, manufacturer, partName, modelNumber, description, quantity, inboundCarrier, inboundTrackingNumber, outboundCarrier, outboundTrackingNumber, classCode, classComments) \n" +
    	" VALUES (:serviceOrderId, :manufacturer, :partName, :modelNumber, :description, :quantity, :inboundCarrier, :inboundTrackingNumber, :outboundCarrier, :outboundTrackingNumber, :classCode, :classComments)";
    
    private static final String sqlInsertDocument = 
    	" INSERT INTO documents (serviceOrderId, documentTitle, description, isLogo) \n" +
    	" VALUES (:serviceOrderId, :documentTitle, :description, :isLogo)";
    
    private static final String sqlInsertBuyerNotification =
    	" INSERT INTO buyer_notifications (transactionId, notificationEvent, notificationEventSubType, relatedServiceOrderId) \n" +
    	" VALUES (:transactionId, :notificationEvent, :notificationEventSubType, :relatedServiceOrderId)";
    
    private static final String sqlInsertOmsBuyerNotification =
    	" INSERT INTO oms_buyer_notifications (buyerNotificationId, techComment, modelNumber, serialNumber, installationDate, amountCollected, paymentMethod, paymentAccountNumber, paymentExpirationDate, paymentAuthorizationNumber,maskedAccountNo,token) \n" +
    	" VALUES (:buyerNotificationId, :techComment, :modelNumber, :serialNumber, :installationDate, :amountCollected, :paymentMethod, :paymentAccountNumber, :paymentExpirationDate, :paymentAuthorizationNumber,:maskedAccountNo,:token)";
    
    private static final String sqlInsertAssurantBuyerNotification =
    	" INSERT INTO assurant_buyer_notifications (buyerNotificationId, etaOrShippingDate, shippingAirBillNumber, returnAirBillNumber, incidentActionDescription) \n" +
    	" VALUES (:buyerNotificationId, :etaOrShippingDate, :shippingAirBillNumber, :returnAirBillNumber, :incidentActionDescription)";
    
    private static final String sqlInsertHsrBuyerNotification =
    	" INSERT INTO hsr_buyer_notifications (buyerNotificationId, unitNumber, orderNumber, routedDate, techId) \n" +
    	" VALUES (:buyerNotificationId, :unitNumber, :orderNumber, :routedDate, :techId)";
    
    private static final String sqlGetTransactionCountByBatchIdAndStatusId =
    	" SELECT COUNT(1) FROM transactions t WHERE t.batchId = :batchId AND t.statusId = :statusId";

    private static final String sqlGetTransactionsByBatchIdAndStatusId =
    	" SELECT t.* FROM transactions t WHERE t.batchId = :batchId AND t.statusId = :statusId";
    
    private static final String getTransactionCountByExternalOrderNumberAndProcessingStatusAndNotificationEventAndTransactionType =
    	" SELECT COUNT(1) \n" +
    	" FROM transactions t \n" +
    	"   INNER JOIN buyer_notifications bn ON t.transactionId = bn.transactionId \n" +
    	" WHERE t.serviceLiveOrderId = :serviceLiveOrderId \n" +
    	"   AND t.statusId = :statusId AND t.transactionTypeId = :transactionTypeId \n" +
    	" AND bn.notificationEvent = :notificationEvent";
    
    private static final String sqlGetTransactionsByBatchId = 
    	" SELECT * FROM transactions where batchId = :batchId";
    
    private static final String sqlGetBuyerNotificationByTransactionId =
    	" SELECT * FROM buyer_notifications where transactionId = :transactionId";
    
    private static final String sqlGetBuyerNotificationsByTransactionIds =
    	" SELECT * FROM buyer_notifications where transactionId in (:transactionIds)";
    
    private static final String sqlGetAssurantBuyerNotificationByBuyerNotificationId =
    	" SELECT * from assurant_buyer_notifications where buyerNotificationId = :buyerNotificationId";
    
    private static final String sqlGetHsrBuyerNotificationByBuyerNotificationId =
    	" SELECT * from hsr_buyer_notifications where buyerNotificationId = :buyerNotificationId";
    
    private static final String sqlGetOmsBuyerNotificationsByBuyerNotificationIds =
    	" SELECT * from oms_buyer_notifications where buyerNotificationId in (:buyerNotificationIds)";
    
    private static final String sqlGetServiceOrderByTransactionId = 
    	" SELECT * from serviceorders where transactionId = :transactionId";
    
    private static final String sqlGetPartsByServiceOrderId =
    	" SELECT * from parts where serviceOrderId = :serviceOrderId";
    
    private static final String sqlGetTasksByServiceOrderIdExcludingCoverage = 
    	" SELECT t.*" +
    	"   FROM tasks t" +
    	"   JOIN tasks_oms `to`" +
    	"     ON t.taskId = to.taskId" +
    	"  WHERE t.serviceOrderId = :serviceOrderId" +
    	"    AND to.coverage <> :coverage";
    
    private static final String sqlGetTasksByServiceOrderId = 
    	" SELECT t.*" +
    	"   FROM tasks t" +
    	"   JOIN tasks_oms `to`" +
    	"     ON t.taskId = to.taskId" +
    	"  WHERE t.serviceOrderId = :serviceOrderId";
        
    private static final String sqlGetTasksByServiceOrderIdsList = 
    	" SELECT * from tasks where serviceOrderId IN (:serviceOrderIdsList)";
    
    private static final String sqlGetOmsTasksByTaskIds =
    	" SELECT * from tasks_oms WHERE taskId IN (:taskIds) ORDER BY taskOmsId ASC";
    
    private static final String sqlGetOmsBuyerNotificationResponsesByTransactionIds = 
    	" SELECT \n" +
    	"   bnr.*, \n" +
    	"   obnr.processId, \n" +
    	"   obnr.salesCheckNumber, \n" +
    	"   obnr.salesCheckDate, \n" +
    	"   obnr.serviceOrderNumber, \n" +
    	"   obnr.serviceUnitNumber, \n" +
    	"   obnr.npsStatus, \n" +
    	"   t.serviceLiveOrderId \n" +
    	" FROM buyer_notification_responses bnr \n" + 
    	"   INNER JOIN oms_buyer_notification_responses obnr \n" +
    	"     ON bnr.buyerNotificationResponseId = obnr.buyerNotificationResponseId \n" +
    	"   LEFT OUTER JOIN buyer_notifications bn \n" +
    	"     ON bn.buyerNotificationId = bnr.buyerNotificationId \n" + 
    	"   LEFT OUTER JOIN serviceorders s on s.serviceOrderId = bn.relatedServiceOrderId \n" + 
    	"   LEFT OUTER JOIN transactions t on t.transactionId = s.transactionId \n" +
    	" WHERE bnr.transactionid in (:transactionIds) ";
    
    private static final String sqlGetOmsBuyerNotificationResponseMessagesByTransactionIds = 
    	" SELECT \n" +
    	"   m.*, \n" +
    	"   lao.audit_owner_id, \n" +
    	"   lao.owner_name, \n" +
    	"   lao.email_ids, \n" +
    	"   lam.work_instruction, \n" +
    	"   lam.success_ind, lam.reportable \n" +
    	" FROM oms_buyer_notification_response_messages m \n" +
    	"   INNER JOIN buyer_notification_responses bnr \n" +
    	"     ON bnr.buyerNotificationResponseId = m.buyerNotificationResponseId \n" +
    	"   INNER JOIN oms_buyer_notification_responses obnr \n" +
    	"     ON obnr.buyerNotificationResponseId = bnr.buyerNotificationResponseId \n" +
    	"   LEFT OUTER JOIN lu_audit_messages lam \n" +
    	"     ON lam.message = m.message AND lam.nps_status = obnr.npsStatus \n" +
    	"   LEFT OUTER JOIN lu_audit_owners lao ON lam.audit_owner_id = lao.audit_owner_id \n" +
    	" WHERE bnr.transactionid in (:transactionIds) ";
    
    /* Finds all Call Close transactions that have not received a response */
    private static final String sqlGetCallCloseTransactionsWithoutResponses =
    	" SELECT \n" +
    	"   t.transactionId, \n" +
    	"   t.batchId, \n" +
    	"   t.transactionTypeId, \n" +
    	"   t.externalOrderNumber, \n" +
    	"   t.processAfter, \n" +
    	"   t.createdOn, \n" +
    	"   t.claimedOn, \n" +
    	"   t.claimedBy, \n" +
    	"   t.statusId, \n" +
    	"   t.statusReason, \n" +
    	"   t.processedOn, \n" +
    	"   t.serviceLiveOrderId, \n" +
    	"   t.buyerResourceId, \n" + 
    	"   t.migrationStatus \n" +
    	" FROM transactions t \n" +
    	"   INNER JOIN batches b ON t.batchid = b.batchid \n" +
    	"   LEFT OUTER JOIN (buyer_notifications bn, buyer_notification_responses bnr) \n" +
    	"     ON (bn.buyerNotificationId = bnr.buyerNotificationId AND bn.transactionId = t.transactionId) \n" +
    	"	LEFT OUTER JOIN transactions cct \n" +
    	"	  ON t.externalOrderNumber = cct.externalOrderNumber AND cct.transactionTypeId = :closeAckTransactionTypeId \n" + 
    	" WHERE t.processedOn < :intervalInTimeStamp \n" +
    	"   AND t.transactiontypeid = :callCloseTransactionTypeId \n" + 
    	"   AND t.statusId <> :resentStatusId \n" +
    	"   AND b.integrationId = :outboundIntegrationId \n" +
    	"   AND bnr.buyerNotificationResponseId IS NULL \n" +
    	"	AND cct.transactionId IS NULL \n" +
    	" ORDER BY t.processedOn \n" +
    	" LIMIT :transactionLimit";
    	    
    private static final String sqlGetCloseAuditTransactionsReadyForResend = 
    	" SELECT \n" +
    	"   t.transactionId, \n" +
    	"   t.batchId, \n" +
    	"   t.transactionTypeId, \n" +
    	"   t.externalOrderNumber, \n" +
    	"   t.processAfter, \n" +
    	"   t.createdOn, \n" +
    	"   t.claimedOn, \n" +
    	"   t.claimedBy, \n" +
    	"   t.statusId, \n" +
    	"   t.statusReason, \n" +
    	"   t.processedOn, \n" +
    	"   t.serviceLiveOrderId, \n" +
    	"   t.buyerResourceId, \n" + 
    	"   t.migrationStatus \n" + 
    	" FROM transactions t \n" +
    	"   INNER JOIN batches b ON b.batchid = t.batchid \n" +
    	" WHERE b.integrationId = :auditIntegrationId AND t.transactiontypeid = :auditTransactionTypeId \n" +
    	"   AND t.statusId = :resendStatusId \n" + 
    	" LIMIT :transactionLimit";
    
    private static final String sqlGetCallCloseTransactionByAuditTransactionId = 
    	" SELECT t.* \n" +
    	" FROM buyer_notification_responses bnr \n" +
    	"   INNER JOIN buyer_notifications bn ON bnr.buyernotificationid = bn.buyernotificationid \n" +
    	"   INNER JOIN transactions t ON t.transactionid = bn.transactionid \n" +
    	" WHERE bnr.transactionId = :auditTransactionId ";
    
    private static final String sqlGetNonUniqueNewTransactionsByInputFile =
    	" SELECT t.* \n" +
    	" FROM transactions t \n" +
    	"   INNER JOIN batches b ON t.batchid = b.batchid \n" +
		" WHERE b.fileName = :fileName \n" +
    	"   AND t.transactiontypeid = :newTransactionTypeId \n" +
    	"   AND t.statusId = :createdStatusId \n" +
    	"   AND t.externalOrderNumber IN ( \n" +
    	"	    SELECT t1.externalOrderNumber \n" +
    	"       FROM transactions t1 \n" + 
    	"	      INNER JOIN batches b1 ON t1.batchid = b1.batchid \n" +
    	"	    WHERE t1.transactionId <> t.transactionId \n" +
    	"	      AND t1.transactionTypeId = :newTransactionTypeId \n" +
    	"         AND (t1.statusId = :successStatusId OR t1.statusId = :readyForProcessingStatusId) \n" +
    	"	      AND b1.integrationId = b.integrationId \n" +
    	"   )";
    
    private static final String sqlAcquireLock =
    	" UPDATE locks \n" +
    	" SET \n" +
    	"   acquired = 1, \n" +
    	"   acquired_on = NOW(), \n" +
    	"   acquired_by = :acquiredBy " +
    	" WHERE lock_name = :lockName \n" +
    	"   AND ((acquired <> 1) OR acquired_on < DATE_SUB(NOW(), INTERVAL :lockExpiration MINUTE)) ";
    
    private static final String sqlCreateAndAcquireLock =
    	" INSERT INTO locks (lock_name, acquired, acquired_by, acquired_on) \n" +
    	" VALUES (:lockName, 1, :acquiredBy, NOW()) ";
    
    private static final String sqlReleaseLock =
    	" UPDATE locks \n" +
    	" SET acquired = 0 \n" +
    	" WHERE lock_name = :lockName ";
    
    private static final String sqlGetLockInformation =
    	" SELECT l.* \n" +
    	" FROM locks l \n" +
    	" WHERE l.lock_name = :lockName ";
    
    private static final String sqlFindTransactionsByServiceLiveOrderIdTransactionTypeIdAndStatusId =
    	" SELECT t.* \n" +
    	" FROM transactions t \n" +
    	" WHERE t.serviceLiveOrderId = :serviceLiveOrderId \n" +
    	"   AND t.transactionTypeId = :transactionTypeId \n" +
    	"   AND t.statusId = :statusId \n" +
    	" ;";
    /* Finds all Cancellation transactions that have not received a response */
    private static final String sqlGetCancellationTransactionsWithoutResponses =
    	" SELECT \n" +
    	"   t.transactionId, \n" +
    	"   t.batchId, \n" +
    	"   t.transactionTypeId, \n" +
    	"   t.externalOrderNumber, \n" +
    	"   t.processAfter, \n" +
    	"   t.createdOn, \n" +
    	"   t.claimedOn, \n" +
    	"   t.claimedBy, \n" +
    	"   t.statusId, \n" +
    	"   t.statusReason, \n" +
    	"   t.processedOn, \n" +
    	"   t.serviceLiveOrderId, \n" +
    	"   t.buyerResourceId, \n" + 
    	"   t.migrationStatus \n" +
    	" FROM transactions t \n" +
    	"   INNER JOIN batches b ON t.batchid = b.batchid \n" +
    	"   LEFT OUTER JOIN (buyer_notifications bn, buyer_notification_responses bnr) \n" +
    	"     ON (bn.buyerNotificationId = bnr.buyerNotificationId AND bn.transactionId = t.transactionId) \n" +
    	"	LEFT OUTER JOIN transactions cct \n" +
    	"	  ON t.externalOrderNumber = cct.externalOrderNumber AND cct.transactionTypeId = :cancelAckTransactionTypeId \n" + 
    	" WHERE t.processedOn < DATE_SUB(NOW(), INTERVAL :intervalInMinutes MINUTE) \n" +
    	"   AND t.transactiontypeid = :cancelTransactionTypeId \n" + 
    	"   AND t.statusId <> :resentStatusId \n" +
    	"   AND b.integrationId = :outboundIntegrationId \n" +
    	"   AND bnr.buyerNotificationResponseId IS NULL \n" +
    	"	AND cct.transactionId IS NULL \n" +
    	" ORDER BY t.processedOn \n" +
    	" LIMIT :transactionLimit";
    
    private static final String sqlGetTransactionTypeById = 
    	" SELECT transactionTypeId from transactions where transactionId = :transactionId";
     
    // SL-21931 Query to fetch username from buyer table for buyer_id 1000
    private static final String sqlgetUserNameByBuyerId="SELECT b.user_name FROM supplier_prod.buyer b WHERE b.buyer_id = :id";	 
		
    @SuppressWarnings("unchecked")
	public List<Transaction> getTransactionsByBatchId(Long batchId) throws DataException {
    	final String methodName = "getTransactionsByBatchId";
		logger.debug(String.format("entered %s", methodName));

		Map<String, Long> namedParameters = new HashMap<String, Long>();
		namedParameters.put("batchId", batchId);

		List<Transaction> foundTransactions;

		try {
			foundTransactions = jdbcTemplate.query(sqlGetTransactionsByBatchId, namedParameters, new TransactionRowMapper());
			logger.debug(String.format("found %d transactions", foundTransactions.size()));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find transactions for batchId %d .", batchId), e);
		}

		logger.debug(String.format("exiting %s", methodName));
		return foundTransactions;
    }
    
    @SuppressWarnings("unchecked")
	public BuyerNotification getBuyerNotificationByTransactionId(Long transactionId) throws DataException {
		final String methodName = "getBuyerNotificationByTransactionId";
		logger.debug(String.format("entered %s", methodName));

		Map<String, Long> namedParameters = new HashMap<String, Long>();
		namedParameters.put("transactionId", transactionId);

		List<BuyerNotification> foundBuyerNotifications;

		try {
			foundBuyerNotifications = jdbcTemplate.query(sqlGetBuyerNotificationByTransactionId, namedParameters, new BuyerNotificationRowMapper());
			logger.debug(String.format("found %d BuyerNotifications", foundBuyerNotifications.size()));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find BuyerNotifications for transactionId %d .", transactionId), e);
		}

		logger.info(String.format("exiting %s", methodName));
		if (foundBuyerNotifications != null && foundBuyerNotifications.size() > 0) {
			return foundBuyerNotifications.get(0);
		}
		return null;
	}
    
    @SuppressWarnings("unchecked")
	public List<BuyerNotification> getBuyerNotificationsByTransactionIds(List<Long> transactionIds) throws DataException {
		final String methodName = "getBuyerNotificationsByTransactionIds";
		logger.debug(String.format("entered %s", methodName));
		
		if (CollectionUtils.isEmpty(transactionIds)) {
			logger.debug(String.format("exiting %s", methodName));
			return Collections.emptyList();
		}

		Map<String, List<Long>> namedParameters = new HashMap<String, List<Long>>();
		namedParameters.put("transactionIds", transactionIds);

		List<BuyerNotification> buyerNotifications;

		try {
			buyerNotifications = jdbcTemplate.query(sqlGetBuyerNotificationsByTransactionIds, namedParameters, new BuyerNotificationRowMapper());
			logger.debug(String.format("found %d responses", buyerNotifications.size()));
		}
		catch (DataAccessException e) {
			throw new DataException("Unable to find buyer notifications for the list of transactions.", e);
		}

		logger.debug(String.format("exiting %s", methodName));
		return buyerNotifications;
		
	}
    
	@SuppressWarnings("unchecked")
	public AssurantBuyerNotification getAssurantBuyerNotificationByBuyerNotificationId(Long buyerNotificationId) throws DataException {
		final String methodName = "getAssurantBuyerNotificationByBuyerNotificationId";
		logger.info(String.format("entered %s", methodName));

		Map<String, Long> namedParameters = new HashMap<String, Long>();
		namedParameters.put("buyerNotificationId", buyerNotificationId);

		List<AssurantBuyerNotification> foundAssurantBuyerNotifications;

		try {
			foundAssurantBuyerNotifications = jdbcTemplate.query(sqlGetAssurantBuyerNotificationByBuyerNotificationId, namedParameters, new AssurantBuyerNotificationRowMapper());
			logger.debug(String.format("found %d AssurantBuyerNotifications", foundAssurantBuyerNotifications.size()));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find AssurantBuyerNotifications for buyerNotificationId %d .", buyerNotificationId), e);
		}

		logger.info(String.format("exiting %s", methodName));
		if (foundAssurantBuyerNotifications != null && foundAssurantBuyerNotifications.size() > 0) {
			return foundAssurantBuyerNotifications.get(0);
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public HsrBuyerNotification getHsrBuyerNotificationByBuyerNotificationId(Long buyerNotificationId) throws DataException {
		final String methodName = "getHsrBuyerNotificationByBuyerNotificationId";
		logger.info(String.format("entered %s", methodName));

		Map<String, Long> namedParameters = new HashMap<String, Long>();
		namedParameters.put("buyerNotificationId", buyerNotificationId);

		List<HsrBuyerNotification> hsrBuyerNotifications;

		try {
			hsrBuyerNotifications = jdbcTemplate.query(sqlGetHsrBuyerNotificationByBuyerNotificationId, namedParameters, new HsrBuyerNotificationRowMapper());
			logger.debug(String.format("found %d HsrBuyerNotification", hsrBuyerNotifications.size()));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find HsrBuyerNotifications for buyerNotificationId %d .", buyerNotificationId), e);
		}

		logger.info(String.format("exiting %s", methodName));
		if (hsrBuyerNotifications != null && hsrBuyerNotifications.size() > 0) {
			return hsrBuyerNotifications.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<OmsBuyerNotification> getOmsBuyerNotificationsByBuyerNotificationIds(List<Long> buyerNotificationIds) throws DataException {
		final String methodName = "getOmsBuyerNotificationsByBuyerNotificationIds";
		logger.info(String.format("entered %s", methodName));

		if (CollectionUtils.isEmpty(buyerNotificationIds)) {
			logger.info(String.format("exiting %s", methodName));
			return Collections.emptyList();
		}

		Map<String, List<Long>> namedParameters = new HashMap<String, List<Long>>();
		namedParameters.put("buyerNotificationIds", buyerNotificationIds);

		List<OmsBuyerNotification> omsBuyerNotifications;

		try {
			omsBuyerNotifications = jdbcTemplate.query(sqlGetOmsBuyerNotificationsByBuyerNotificationIds, namedParameters, new OmsBuyerNotificationRowMapper());
			logger.debug(String.format("found %d OmsBuyerNotifications", omsBuyerNotifications.size()));
		}
		catch (DataAccessException e) {
			throw new DataException("Unable to find OmsBuyerNotifications for the list of buyerNotificationIds.", e);
		}

		logger.info(String.format("exiting %s", methodName));
		return omsBuyerNotifications;
	}
	
	@SuppressWarnings("unchecked")
	public ServiceOrder getServiceOrderByTransactionId(Long transactionId) throws DataException {
		final String methodName = "getServiceOrderByTransactionId";
		logger.info(String.format("entered %s", methodName));

		Map<String, Long> namedParameters = new HashMap<String, Long>();
		namedParameters.put("transactionId", transactionId);

		List<ServiceOrder> foundServiceOrders;

		try {
			foundServiceOrders = jdbcTemplate.query(sqlGetServiceOrderByTransactionId, namedParameters, new ServiceOrderRowMapper());
			logger.debug(String.format("found %d Service Orders", foundServiceOrders.size()));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find Service Orders for transactionId %d .", transactionId), e);
		}

		logger.info(String.format("exiting %s", methodName));
		if (foundServiceOrders != null && foundServiceOrders.size() > 0) {
			return foundServiceOrders.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<Part> getPartsByServiceOrderId(Long serviceOrderId) throws DataException {
		final String methodName = "getPartsByServiceOrderId";
		logger.info(String.format("entered %s", methodName));

		Map<String, Long> namedParameters = new HashMap<String, Long>();
		namedParameters.put("serviceOrderId", serviceOrderId);

		List<Part> foundParts;

		try {
			foundParts = jdbcTemplate.query(sqlGetPartsByServiceOrderId, namedParameters, new PartRowMapper());
			logger.debug(String.format("found %d parts", foundParts.size()));
		}
		catch (DataException e) {
			throw new DataException(String.format("Unable to find parts for serviceOrderId %d .", serviceOrderId), e);
		}

		logger.info(String.format("exiting %s", methodName));
		return foundParts;
	}
    
    public Batch getBatchById(long batchId) throws DataException {
		final String methodName = "getBatchById";
		logger.debug(String.format("entered %s", methodName));

		Map<String, Long> namedParameters = new HashMap<String, Long>();
		namedParameters.put("batchId", batchId);

		Batch batch;
		try {
			batch = (Batch)jdbcTemplate.queryForObject(sqlGetBatchById,namedParameters,new BatchRowMapper());
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find batch with id %d.", batchId), e);
		}

		logger.debug(String.format("exiting %s", methodName));
		return batch;
	}
    
    public Batch getBatchbyInputFile(String fileName) {
    	final String methodName = "getBatchbyInputFile";
		logger.info(String.format("entered %s", methodName));

		Map<String, String> namedParameters = new HashMap<String, String>();
		namedParameters.put("fileName", fileName);

		Batch batch;
		try {
			batch = (Batch)jdbcTemplate.queryForObject(sqlGetLatestBatchByFileName,namedParameters,new BatchRowMapper());
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find batch with filename %s.", fileName), e);
		}

		logger.info(String.format("exiting %s", methodName));
		return batch;
    }

	@SuppressWarnings("unchecked")
	public List<ServiceOrder> getServiceOrdersByBatchInputFile(String inputFile) throws DataException {
		final String methodName = "getServiceOrdersByBatchInputFile";
		logger.info(String.format("entered %s", methodName));

		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("fileName", inputFile);
		namedParameters.put("processingStatusId", ProcessingStatus.SUCCESS.getId());

		List<ServiceOrder> orders;
		try {
			orders = jdbcTemplate.query(sqlGetServiceOrdersByBatchInputFile,namedParameters,new ServiceOrderRowMapper());
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find ServiceOrders by batch input file '%s'.", inputFile), e);
		}

		logger.info(String.format("exiting %s", methodName));
		return orders;
	}
	
	public void markTransactionsCompleteByInputFile(String inputFile) throws DataException {
		final String methodName = "markTransactionsCompleteByInputFile";
		logger.info(String.format("entered %s", methodName));

		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("fileName", inputFile);
		namedParameters.put("batchStatus", ProcessingStatus.SUCCESS.getId());
		namedParameters.put("oldTransactionStatus", ProcessingStatus.CREATED.getId());
		namedParameters.put("newTransactionStatus", ProcessingStatus.READY_TO_PROCESS.getId());

		try {
			@SuppressWarnings("unused")
			int rowsAffected = jdbcTemplate.update(sqlUpdateTransactionStatusByInputFile, new MapSqlParameterSource(namedParameters));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to update transactions for input file: %s.", inputFile), e);
		}

		logger.info(String.format("exiting %s", methodName));
	}
	
	public List<Map<String, String>> getOMSCustomReferencesByServiceOrderId(
			Long serviceOrderId) throws DataException {
		
		final String methodName = "getOMSCustomReferencesByServiceOrderId";
		logger.info(String.format("entered %s", methodName));

		List<Map<String, String>> customRefMaps = this
				.getCustomReferencesUsing(serviceOrderId,
						sqlGetOmsCustomReferences,
						new OMSCustomReferenceRowMapper());

		logger.info(String.format("exiting %s", methodName));
		return customRefMaps;
	}
	
	public List<Map<String, String>> getAssurantCustomReferencesByServiceOrderId(
			Long serviceOrderId) throws DataException {
		
		final String methodName = "getAssurantCustomReferencesByServiceOrderId";
		logger.info(String.format("entered %s", methodName));

		List<Map<String, String>> customRefMaps = this
				.getCustomReferencesUsing(serviceOrderId,
						sqlGetAssurantCustomReferences,
						new AssurantCustomReferenceRowMapper());

		logger.info(String.format("exiting %s", methodName));
		return customRefMaps;
	}
	
	public List<Map<String, String>> getHSRCustomReferencesByServiceOrderId(
			Long serviceOrderId) throws DataException {
		
		final String methodName = "getHSRCustomReferencesByServiceOrderId";
		logger.info(String.format("entered %s", methodName));

		List<Map<String, String>> customRefMaps = this
				.getCustomReferencesUsing(serviceOrderId,
						sqlGetHSRCustomReferences,
						new HSRCustomReferenceRowMapper());

		logger.info(String.format("exiting %s", methodName));
		return customRefMaps;
	}
	
	@SuppressWarnings("unchecked")
	private List<Map<String, String>> getCustomReferencesUsing(
			Long serviceOrderId, String query, RowMapper rowMapper) throws DataException {
		
		Map<String, Long> namedParameters = new HashMap<String, Long>();
		namedParameters.put("serviceOrderId", serviceOrderId);

		List<Map<String, String>> customRefMaps;
		try {
			customRefMaps = jdbcTemplate.query(query, namedParameters, rowMapper);
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find custom references for serviceOrderId %d.", serviceOrderId), e);
		}

		return customRefMaps;
	}
	
	@SuppressWarnings("unchecked")
	public List<Batch> findBatchesFor(Long integrationId, ProcessingStatus processingStatus) throws DataException {
		final String methodName = "findOpenBatchFor";
		logger.info(String.format("entered %s", methodName));
		
		Map<String, Long> namedParameters = new HashMap<String, Long>();
		namedParameters.put("integrationId", integrationId);
		namedParameters.put("statusId", (long)processingStatus.getId());

		List<Batch> batches;
		try {
			batches = jdbcTemplate.query(
				sqlFindBatchesByIntegrationIdAndStatus, namedParameters,
				new BatchRowMapper());
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find batches for Service Order with integrationId %d and ProcessingStatus '%s'.", integrationId, processingStatus.toString()), e);
		}

		logger.info(String.format("exiting %s", methodName));
		return batches;
	}

	public void createBatch(Batch batch) throws DataException {
		final String methodName = "createBatch";
		logger.debug(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("integrationId", batch.getIntegrationId());
		namedParameters.put("fileName", batch.getFileName());
		namedParameters.put("statusId", batch.getProcessingStatus().getId());
		namedParameters.put("createdOn", batch.getCreatedOn());
		namedParameters.put("exception", batch.getException());
		KeyHolder generatedKeys = new GeneratedKeyHolder();
		
		long batchId;
		try {
			jdbcTemplate.update(sqlInsertBatch, new MapSqlParameterSource(namedParameters), 
				generatedKeys);
		
			batchId = generatedKeys.getKey().longValue();
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to create batch: %s.", batch.toString()), e);
		}

		logger.debug(String.format("created batch with id %d", batchId));

		batch.setBatchId(batchId);

		logger.debug(String.format("exiting %s", methodName));
		return;
	}
	
	public void updateBatch(Batch batch) throws DataException {
		final String methodName = "updateBatch";
		logger.debug(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("batchId", batch.getBatchId());
		namedParameters.put("fileName", batch.getFileName());
		namedParameters.put("statusId", batch.getProcessingStatus().getId());
		namedParameters.put("exception", batch.getException());

		try {
			jdbcTemplate.update(sqlUpdateBatch, new MapSqlParameterSource(namedParameters));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to update batch: %s.", batch.toString()), e);
		}	

		logger.debug(String.format("exiting %s", methodName));
		return;
	}
	
	public void updateBatchFileName(Batch batch) throws DataException {
		
		final String methodName = "updateBatchFileName";
		logger.info(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("batchId", batch.getBatchId());
		namedParameters.put("fileName", batch.getFileName());

		try {
			jdbcTemplate.update(sqlUpdateBatchFileName, new MapSqlParameterSource(namedParameters));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to update the filename for batch: %s.", batch.toString()), e);
		}	

		logger.info(String.format("exiting %s", methodName));
		return;
	}
	
	public void updateStatusForTransactionsList(Collection<Long> transactionIds, ProcessingStatus processingStatus, String statusReason) throws DataException {
		final String methodName = "markTransactionsAsProcessed";
		logger.info(String.format("entered %s", methodName));
		
		if (CollectionUtils.isEmpty(transactionIds)) {
			logger.info(String.format("exiting %s", methodName));
			return;
		}

		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("transactionIdsList", transactionIds);
		namedParameters.put("statusId", processingStatus.getId());
		namedParameters.put("statusReason", statusReason);

		try {
			jdbcTemplate.update(sqlUpdateTransactionStatus, new MapSqlParameterSource(namedParameters));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to update status for transactions with transactionId in (%s) to '%s'.", StringUtils.collectionToCommaDelimitedString(transactionIds), processingStatus.toString()), e);
		}

		logger.info(String.format("exiting %s", methodName));
		return;
	}
	
	public ServiceOrder findLatestServiceOrderByExternalOrderNumber(String externalOrderNumber) throws DataException {
		final String methodName = "findLatestServiceOrderByExternalOrderNumber";
		logger.info(String.format("entered %s", methodName));
			
		Map<String, String> namedParameters = new HashMap<String, String>();
		namedParameters.put("externalOrderNumber", externalOrderNumber);

		ServiceOrder serviceOrder;
		try {
			serviceOrder = (ServiceOrder) jdbcTemplate.queryForObject(
				sqlfindLatestServiceOrderByExternalOrderNumber,
				namedParameters, new ServiceOrderRowMapper());
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find latest Service Order by externalOrderNumber '%s'.", externalOrderNumber), e);
		}
		
		logger.info(String.format("exiting %s", methodName));
		return serviceOrder;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getExistingExternalServiceOrdersIds(List<String> serviceOrderIds) throws DataException {
		final String methodName = "getExistingExternalServiceOrdersIds";
		logger.debug(String.format("entered %s", methodName));
		
		if (CollectionUtils.isEmpty(serviceOrderIds)) {
			logger.debug(String.format("exiting %s", methodName));
			return Collections.emptyList();
		}

		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("serviceOrderIds", serviceOrderIds);
		namedParameters.put("transactionTypeId", TransactionType.NEW.getId());
		namedParameters.put("migrationStatusComplete", ProcessingStatus.SUCCESS.getId());

		List<String> foundOrderIds;
		try {
			foundOrderIds = jdbcTemplate.query(sqlGetExistingExternalServiceOrderIds,namedParameters,new TransactionExternalOrderNumberRowMapper());
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find existing external service order ids for serviceOrderIds: %s.", StringUtils.collectionToCommaDelimitedString(serviceOrderIds)), e);
		}

		logger.debug(String.format("exiting %s", methodName));
		return foundOrderIds;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getServiceOrdersThatMatchOrderNumberWithTestSuffix(
			String externalOrderNumber, String testSuffix) throws DataException {
		final String methodName = "getServiceOrdersThatMatchOrderNumberWithTestSuffix";
		logger.info(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("externalOrderNumber", externalOrderNumber + testSuffix + '%');
		namedParameters.put("transactionTypeId", TransactionType.NEW.getId());

		List<Transaction> foundTransactions;
		try {
			foundTransactions = jdbcTemplate.query(sqlGetLatestServiceOrderThatMatchesOrderNumberBeforeTestSuffix, namedParameters, new TransactionRowMapper());
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find service orders matching external order number '%s' and test suffix '%s'.", externalOrderNumber, testSuffix), e);
		}

		logger.info(String.format("exiting %s", methodName));
		return foundTransactions;		
	}

	public void saveServiceOrderCustomRefs(ServiceOrder serviceOrder, Map<String, String> customRefs) throws DataException {
		final String methodName = "saveServiceOrderCustomRefs";
		logger.info(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		
		namedParameters.put("serviceOrderId", serviceOrder.getServiceOrderId());
		namedParameters.put("customRefs", customRefs);
		
		final ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(customRefs);
			oos.close();
		}
		catch (IOException e) {
			throw new DataException(String.format("Unable to convert customRefs map to ByteArrayOutputStream for ServiceOrder: %s.", serviceOrder.toString()), e);
		}

		try {
			LobHandler lobHandler = new DefaultLobHandler();
			jdbcTemplate.execute(sqlSaveServiceOrderCustomRefs, namedParameters,
				new AbstractLobCreatingPreparedStatementCallback(lobHandler) {                         
					protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
						lobCreator.setBlobAsBytes(ps, 1, bos.toByteArray());
				}
			}
			);
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to save serialized customRefs map for ServiceOrder: %s", serviceOrder.toString()), e);
		}

		logger.info(String.format("exiting %s", methodName));
	}

	public void createTransaction(Transaction transaction) throws DataException {
		final String methodName = "createTransaction";
		logger.info(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("batchId", transaction.getBatchId());
		namedParameters.put("transactionTypeId", transaction.getTransactionType().getId());
		namedParameters.put("externalOrderNumber", transaction.getExternalOrderNumber());
		namedParameters.put("processAfter", transaction.getProcessAfter());
		namedParameters.put("createdOn", transaction.getCreatedOn());
		namedParameters.put("statusId", transaction.getProcessingStatus().getId());
		namedParameters.put("serviceLiveOrderId", transaction.getServiceLiveOrderId());
		namedParameters.put("buyerResourceId", transaction.getBuyerResourceId());
		
		logger.info("transaction.processAfter() : " + transaction.getProcessAfter());
		logger.info("transaction.createdOn() : " + transaction.getCreatedOn());
		
		long transactionId;
		KeyHolder generatedKeys = new GeneratedKeyHolder();
		try {
			jdbcTemplate.update(sqlInsertTransaction, new MapSqlParameterSource(namedParameters), 
				generatedKeys);
		
			transactionId = generatedKeys.getKey().longValue();
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to create transaction: %s.", transaction.toString()), e);
		}
		logger.debug(String.format("created transaction with id %d", transactionId));

		transaction.setTransactionId(transactionId);

		logger.info(String.format("exiting %s", methodName));
		return;
	}
	
	public void createServiceOrder(ServiceOrder serviceOrder) throws DataException {
		final String methodName = "createServiceOrder";
		logger.info(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		
		namedParameters.put("transactionId", serviceOrder.getTransactionId());
		namedParameters.put("laborSpendLimit", serviceOrder.getLaborSpendLimit());
		namedParameters.put("partsSpendLimit", serviceOrder.getPartsSpendLimit());
		namedParameters.put("title", serviceOrder.getTitle());
		namedParameters.put("description", serviceOrder.getDescription());
		namedParameters.put("providerInstructions", serviceOrder.getProviderInstructions());
		namedParameters.put("serviceWindowStartDate", serviceOrder.getStartDate());
		namedParameters.put("serviceWindowStartTime", serviceOrder.getStartTime());
		namedParameters.put("serviceWindowEndDate", serviceOrder.getEndDate());
		namedParameters.put("serviceWindowEndTime", serviceOrder.getEndTime());
		namedParameters.put("templateId", serviceOrder.getTemplateId());
		namedParameters.put("providerServiceConfirmInd", serviceOrder.getProviderServiceConfirmInd());
		namedParameters.put("partsSuppliedBy", serviceOrder.getPartsSuppliedBy());
		namedParameters.put("serviceWindowTypeFixed", serviceOrder.getServiceWindowTypeFixed());
		namedParameters.put("mainServiceCategory", serviceOrder.getMainServiceCategory());
		namedParameters.put("buyerTermsAndConditions", serviceOrder.getBuyerTermsAndConditions());
		
		KeyHolder generatedKeys = new GeneratedKeyHolder();
		long soId;
		try {
			jdbcTemplate.update(sqlInsertServiceOrder, new MapSqlParameterSource(namedParameters), 
				generatedKeys);
		
			soId = generatedKeys.getKey().longValue();
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to create service order: %s.", serviceOrder.toString()), e);
		}
		logger.debug(String.format("created service order with id %d", soId));

		serviceOrder.setServiceOrderId(soId);

		logger.info(String.format("exiting %s", methodName));
		return;
	}
	
	public void createContact(Contact contact) throws DataException {
	    final String methodName = "createContact";
		logger.info(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		
		namedParameters.put("serviceOrderId", contact.getServiceOrderId());
		namedParameters.put("businessName", contact.getBusinessName());
		namedParameters.put("firstName", contact.getFirstName());
		namedParameters.put("lastName", contact.getLastName());
		namedParameters.put("email", contact.getEmail());
		
		KeyHolder generatedKeys = new GeneratedKeyHolder();
		long id;
		try {
			jdbcTemplate.update(sqlInsertContact, new MapSqlParameterSource(namedParameters), 
				generatedKeys);
		
			id = generatedKeys.getKey().longValue();
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to create contact: %s.", contact.toString()), e);
		}
		logger.debug(String.format("created contact with id %d", id));

		contact.setContactId(id);

		logger.info(String.format("exiting %s", methodName));
		return;
	    
	}
	
	public void createPhone(Phone phone) throws DataException {
	   final String methodName = "createPhone";
		logger.info(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		
		namedParameters.put("contactId", phone.getContactId());
		namedParameters.put("phoneNumber", phone.getPhoneNumber());
		namedParameters.put("phoneType", phone.getPhoneType());
		namedParameters.put("primary", phone.getPrimary());
		
		KeyHolder generatedKeys = new GeneratedKeyHolder();
		long id;
		try {
			jdbcTemplate.update(sqlInsertPhone, new MapSqlParameterSource(namedParameters), 
				generatedKeys);
		
			id = generatedKeys.getKey().longValue();
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to create phone: %s.", phone.toString()), e);
		}
		logger.debug(String.format("created contact phone with id %d", id));

		logger.info(String.format("exiting %s", methodName));
		return;
	}
	
	public void createLocation(Location location) throws DataException {
	    final String methodName = "createLocation";
		logger.info(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		
		namedParameters.put("serviceOrderId", location.getServiceOrderId());
		namedParameters.put("locationClassification", location.getLocationClassification());
		namedParameters.put("locationNotes", location.getLocationNotes());
		namedParameters.put("addressLine1", location.getAddressLine1());
		namedParameters.put("addressLine2", location.getAddressLine2());
		namedParameters.put("city", location.getCity());
		namedParameters.put("state", location.getState());
		namedParameters.put("zipCode", location.getZipCode());
		
		KeyHolder generatedKeys = new GeneratedKeyHolder();
		long id;
		try {
			jdbcTemplate.update(sqlInsertLocation, new MapSqlParameterSource(namedParameters), 
				generatedKeys);
		
			id = generatedKeys.getKey().longValue();
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to create location: %s.", location.toString()), e);
		}
		logger.debug(String.format("created location with id %d", id));

		logger.info(String.format("exiting %s", methodName));
		return;
	    
	}
	
	public void createTask(Task task) throws DataException {
	    final String methodName = "createTask";
		logger.info(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		
		namedParameters.put("serviceOrderId", task.getServiceOrderId());
		namedParameters.put("title", task.getTitle());
		namedParameters.put("comments", task.getComments());
		namedParameters.put("default", task.getDefaultTask());
		namedParameters.put("externalSku", task.getExternalSku());
		namedParameters.put("specialtyCode", task.getSpecialtyCode());
		namedParameters.put("amount", task.getAmount());
		namedParameters.put("category", task.getCategory());
		namedParameters.put("subCategory", task.getSubCategory());
		namedParameters.put("serviceType", task.getServiceType());
		namedParameters.put("sequence_number", task.getSequenceNumber());
		
		KeyHolder generatedKeys = new GeneratedKeyHolder();
		long id;
		try {
			jdbcTemplate.update(sqlInsertTask, new MapSqlParameterSource(namedParameters), 
				generatedKeys);
		
			id = generatedKeys.getKey().longValue();
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to create task: %s.", task.toString()), e);
		}
		logger.debug(String.format("created task with id %d", id));

		task.setTaskId(id);
		logger.info(String.format("exiting %s", methodName));
		return;	   
	}
	
	public void createPart(Part part) throws DataException {
	    final String methodName = "createPart";
		logger.info(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		
		namedParameters.put("serviceOrderId", part.getServiceOrderId());
		namedParameters.put("manufacturer", part.getManufacturer());
		namedParameters.put("partName", part.getPartName());
		namedParameters.put("modelNumber", part.getModelNumber());
		namedParameters.put("description", part.getDescription());
		namedParameters.put("quantity", part.getQuantity());
		namedParameters.put("inboundCarrier", part.getInboundCarrier());
		namedParameters.put("inboundTrackingNumber", part.getInboundTrackingNumber());
		namedParameters.put("outboundCarrier", part.getOutboundCarrier());
		namedParameters.put("outboundTrackingNumber", part.getOutboundTrackingNumber());
		namedParameters.put("classCode", part.getClassCode());
		namedParameters.put("classComments", part.getClassComments());
		
		KeyHolder generatedKeys = new GeneratedKeyHolder();
		long id;
		try {
			jdbcTemplate.update(sqlInsertPart, new MapSqlParameterSource(namedParameters), 
				generatedKeys);
		
			id = generatedKeys.getKey().longValue();
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to create part: %s.", part.toString()), e);
		}
		logger.debug(String.format("created part with id %d", id));

		logger.info(String.format("exiting %s", methodName));
		return;
	}
	
	public void createDocument(Document document) throws DataException {
		final String methodName = "createDocument";
		logger.info(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		
		namedParameters.put("serviceOrderId", document.getServiceOrderId());
		namedParameters.put("documentTitle", document.getDocumentTitle());
		namedParameters.put("description", document.getDescription());
		namedParameters.put("isLogo", document.getLogo());
		
		KeyHolder generatedKeys = new GeneratedKeyHolder();
		long id;
		try {
			jdbcTemplate.update(sqlInsertDocument, new MapSqlParameterSource(namedParameters), 
				generatedKeys);
		
			id = generatedKeys.getKey().longValue();
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to create document: %s.", document.toString()), e);
		}
		logger.debug(String.format("created document with id %d", id));

		logger.info(String.format("exiting %s", methodName));
		return;
	}

	public void createBuyerNotification(BuyerNotification buyerNotification) throws DataException {
		final String methodName = "createBuyerNotification";
		logger.debug(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("transactionId", buyerNotification.getTransactionId());
		namedParameters.put("notificationEvent", buyerNotification.getNotificationEvent());
		namedParameters.put("notificationEventSubType", buyerNotification.getNotificationEventSubType());
		namedParameters.put("relatedServiceOrderId", buyerNotification.getRelatedServiceOrderId());

		long buyerNotificationId;
		KeyHolder generatedKeys = new GeneratedKeyHolder();
		
		try {
			jdbcTemplate.update(sqlInsertBuyerNotification, new MapSqlParameterSource(namedParameters), 
				generatedKeys);
		
			buyerNotificationId = generatedKeys.getKey().longValue();
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to create buyerNotification: %s.", buyerNotification.toString()), e);
		}
		
		logger.debug(String.format("created buyerNotification with id %d", buyerNotificationId));

		buyerNotification.setBuyerNotificationId(buyerNotificationId);

		logger.debug(String.format("exiting %s", methodName));
		return;
	}
	
	public void createOmsBuyerNotification(OmsBuyerNotification omsBuyerNotification) throws DataException {
		final String methodName = "createOmsBuyerNotification";
		logger.info(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("buyerNotificationId", omsBuyerNotification.getBuyerNotificationId());
		namedParameters.put("techComment", omsBuyerNotification.getTechComment());
		namedParameters.put("modelNumber", omsBuyerNotification.getModelNumber());
		namedParameters.put("serialNumber", omsBuyerNotification.getSerialNumber());
		namedParameters.put("installationDate", omsBuyerNotification.getInstallationDate());
		namedParameters.put("amountCollected", omsBuyerNotification.getAmountCollected());
		namedParameters.put("paymentMethod", omsBuyerNotification.getPaymentMethod());
		namedParameters.put("paymentAccountNumber", omsBuyerNotification.getPaymentAccountNumber());
		namedParameters.put("paymentExpirationDate", omsBuyerNotification.getPaymentExpirationDate());
		namedParameters.put("paymentAuthorizationNumber", omsBuyerNotification.getPaymentAuthorizationNumber());
		namedParameters.put("maskedAccountNo", omsBuyerNotification.getMaskedAccountNo());
		namedParameters.put("token", omsBuyerNotification.getToken());
		long omsBuyerNotificationId;
		KeyHolder generatedKeys = new GeneratedKeyHolder();
		
		try {
			jdbcTemplate.update(sqlInsertOmsBuyerNotification, new MapSqlParameterSource(namedParameters), 
				generatedKeys);
		
			omsBuyerNotificationId = generatedKeys.getKey().longValue();
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to create omsBuyerNotification: %s.", omsBuyerNotification.toString()), e);
		}
		
		logger.debug(String.format("created omsBuyerNotification with id %d", omsBuyerNotificationId));

		omsBuyerNotification.setOmsBuyerNotificationId(omsBuyerNotificationId);

		logger.info(String.format("exiting %s", methodName));
		return;
	}
	
	public void createAssurantBuyerNotification(AssurantBuyerNotification assurantBuyerNotification) throws DataException {
		final String methodName = "createAssurantBuyerNotification";
		logger.info(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("buyerNotificationId", assurantBuyerNotification.getBuyerNotificationId());
		namedParameters.put("etaOrShippingDate", assurantBuyerNotification.getEtaOrShippingDate());
		namedParameters.put("shippingAirBillNumber", assurantBuyerNotification.getShippingAirBillNumber());
		namedParameters.put("returnAirBillNumber", assurantBuyerNotification.getReturnAirBillNumber());
		namedParameters.put("incidentActionDescription", assurantBuyerNotification.getIncidentActionDescription());

		long assurantBuyerNotificationId;
		KeyHolder generatedKeys = new GeneratedKeyHolder();
		
		try {
			jdbcTemplate.update(sqlInsertAssurantBuyerNotification, new MapSqlParameterSource(namedParameters), 
				generatedKeys);
		
			assurantBuyerNotificationId = generatedKeys.getKey().longValue();
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to create assurantBuyerNotification: %s.", assurantBuyerNotification.toString()), e);
		}
		
		logger.debug(String.format("created assurantBuyerNotification with id %d", assurantBuyerNotificationId));

		assurantBuyerNotification.setAssurantBuyerNotificationId(assurantBuyerNotificationId);

		logger.info(String.format("exiting %s", methodName));
		return;
	}

	public void createHsrBuyerNotification(HsrBuyerNotification hsrBuyerNotification) throws DataException {
		final String methodName = "createHsrBuyerNotification";
		logger.info(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("buyerNotificationId", hsrBuyerNotification.getBuyerNotificationId());
		namedParameters.put("unitNumber", hsrBuyerNotification.getUnitNumber());
		namedParameters.put("orderNumber", hsrBuyerNotification.getOrderNumber());
		namedParameters.put("routedDate", hsrBuyerNotification.getRoutedDate());
		namedParameters.put("techId", hsrBuyerNotification.getTechId());
		
		long hsrBuyerNotificationId;
		KeyHolder generatedKeys = new GeneratedKeyHolder();
		
		try {
			jdbcTemplate.update(sqlInsertHsrBuyerNotification, new MapSqlParameterSource(namedParameters), 
				generatedKeys);
		
			hsrBuyerNotificationId = generatedKeys.getKey().longValue();
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to create hsrBuyerNotification: %s.", hsrBuyerNotification.toString()), e);
		}
		
		logger.debug(String.format("created hsrBuyerNotification with id %d", hsrBuyerNotificationId));

		hsrBuyerNotification.setHsrBuyerNotificationId(hsrBuyerNotificationId);

		logger.info(String.format("exiting %s", methodName));
		return;
	}
	
	public long getTransactionCountByBatchAndProcessingStatus(Long batchId, ProcessingStatus processingStatus) throws DataException {
		final String methodName = "getTransactionCountByBatchAndProcessingStatus";
		logger.info(String.format("entered %s", methodName));

		Map<String, Long> namedParameters = new HashMap<String, Long>();
		namedParameters.put("batchId", batchId);
		namedParameters.put("statusId", (long)processingStatus.getId());
		long count;
		
		try {
			count = jdbcTemplate.queryForLong(sqlGetTransactionCountByBatchIdAndStatusId, namedParameters);
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find transaction count for batchId %d and Processing Status '%s'.", batchId, processingStatus.toString()), e);
		}

		logger.info(String.format("exiting %s", methodName));
		return count;
	}
	
	public long getBuyerNotificationTransactionCount(
			String serviceLiveOrderId, ProcessingStatus processingStatus, String notificationEvent) throws DataException {
		final String methodName = "getBuyerNotificationTransactionCount";
		logger.info(String.format("entered %s", methodName));

		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("serviceLiveOrderId", serviceLiveOrderId);
		namedParameters.put("statusId", (long)processingStatus.getId());
		namedParameters.put("notificationEvent", notificationEvent);
		namedParameters.put("transactionTypeId", TransactionType.BUYER_NOTIFICATION.getId());
		long count;
		
		try {
			count = jdbcTemplate.queryForLong(getTransactionCountByExternalOrderNumberAndProcessingStatusAndNotificationEventAndTransactionType, namedParameters);
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find transaction count for externalOrderNumber %s and Processing Status '%s' and notification event %s.", serviceLiveOrderId, processingStatus.toString(), notificationEvent), e);
		}

		logger.info(String.format("exiting %s", methodName));
		return count;
	}

	@SuppressWarnings("unchecked")
	public List<Transaction> getTransactionsByBatchAndProcessingStatus(Long batchId, ProcessingStatus processingStatus) throws DataException {
		final String methodName = "getTransactionsByBatchAndProcessingStatus";
		logger.info(String.format("entered %s", methodName));

		Map<String, Long> namedParameters = new HashMap<String, Long>();
		namedParameters.put("batchId", batchId);
		namedParameters.put("statusId", (long)processingStatus.getId());

		List<Transaction> foundTransactions;

		try {
			foundTransactions = jdbcTemplate.query(sqlGetTransactionsByBatchIdAndStatusId, namedParameters, new TransactionRowMapper());
			logger.debug(String.format("found %d transactions", foundTransactions.size()));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find transactions for batchId %d and Processing Status '%s'.", batchId, processingStatus.toString()), e);
		}

		logger.info(String.format("exiting %s", methodName));
		return foundTransactions;
	}
	
	@SuppressWarnings("unchecked")
	public List<OmsBuyerNotificationResponse> getOmsBuyerNotificationResponsesByTransactionIds(
			List<Long> transactionIds) {
		
		final String methodName = "getOmsBuyerNotificationResponsesByTransactionIds";
		logger.debug(String.format("entered %s", methodName));

		if (CollectionUtils.isEmpty(transactionIds)) {
			logger.debug(String.format("exiting %s", methodName));
			return Collections.emptyList();
		}

		Map<String, List<Long>> namedParameters = new HashMap<String, List<Long>>();
		namedParameters.put("transactionIds", transactionIds);

		List<OmsBuyerNotificationResponse> buyerNotificationResponses;

		try {
			buyerNotificationResponses = jdbcTemplate.query(sqlGetOmsBuyerNotificationResponsesByTransactionIds, namedParameters, new OmsBuyerNotificationResponseRowMapper());
			logger.debug(String.format("found %d responses", buyerNotificationResponses.size()));
		}
		catch (DataAccessException e) {
			throw new DataException("Unable to find buyer notification responses for the list of transactions.", e);
		}

		logger.debug(String.format("exiting %s", methodName));
		return buyerNotificationResponses;
	}
	
	@SuppressWarnings("unchecked")
	public List<OmsBuyerNotificationResponseMessage> getOmsBuyerNotificationResponseMessagesByTransactionIds(
			List<Long> transactionIds) {
				
		final String methodName = "getOmsBuyerNotificationResponseMessagesByTransactionIds";
		logger.debug(String.format("entered %s", methodName));

		if (CollectionUtils.isEmpty(transactionIds)) {
			logger.debug(String.format("exiting %s", methodName));
			return Collections.emptyList();
		}

		Map<String, List<Long>> namedParameters = new HashMap<String, List<Long>>();
		namedParameters.put("transactionIds", transactionIds);

		List<OmsBuyerNotificationResponseMessage> buyerNotificationResponseMessages;

		try {
			buyerNotificationResponseMessages = jdbcTemplate.query(sqlGetOmsBuyerNotificationResponseMessagesByTransactionIds, namedParameters, new OmsBuyerNotificationResponseMessageRowMapper());
			logger.debug(String.format("found %d messages", buyerNotificationResponseMessages.size()));
		}
		catch (DataAccessException e) {
			throw new DataException("Unable to find buyer notification response messages for the list of transactions.", e);
		}

		logger.debug(String.format("exiting %s", methodName));
		return buyerNotificationResponseMessages;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getCallCloseTransationsWithoutResponses(Long retryMinutes, Long transactionLimit) {
		final String methodName = "getCallCloseTransationsWithoutResponses";
		logger.info(String.format("entered %s", methodName));
		//This is added to pass query param to remove date_sub from current time and retry minutes
		Date date = Calendar.getInstance().getTime();
		Timestamp currentTimeStampe =  new Timestamp(date.getTime());
		long currentTimeStampinMls= currentTimeStampe.getTime();
		long subtractMls=retryMinutes*60*1000;
		Timestamp theNewTimestamp=new Timestamp (currentTimeStampinMls-subtractMls);
		logger.info("intervalInTimeStamp "+ theNewTimestamp);
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("outboundIntegrationId", IntegrationName.RI_OUTBOUND.getId());
		namedParameters.put("callCloseTransactionTypeId", TransactionType.BUYER_NOTIFICATION.getId());
		namedParameters.put("resentStatusId", ProcessingStatus.RESENT.getId());
		namedParameters.put("intervalInTimeStamp", theNewTimestamp);
		namedParameters.put("transactionLimit", transactionLimit);
		namedParameters.put("closeAckTransactionTypeId", TransactionType.CLOSE_ACKNOWLEDGEMENT.getId());
		
		List<Transaction> foundTransactions;

		try {
			foundTransactions = jdbcTemplate.query(sqlGetCallCloseTransactionsWithoutResponses, namedParameters, new TransactionRowMapper());
			logger.debug(String.format("found %d transactions", foundTransactions.size()));
		}
		catch (DataAccessException e) {
			throw new DataException("Unable to find call close transactions without responses.", e);
		}

		logger.info(String.format("exiting %s", methodName));
		return foundTransactions;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getCloseAuditTransationsMarkedForResend(Long transactionLimit) {
		final String methodName = "getCloseAuditTransationsMarkedForResend";
		logger.info(String.format("entered %s", methodName));

		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("auditIntegrationId", IntegrationName.NPS_CLOSE_AUDIT.getId());
		namedParameters.put("auditTransactionTypeId", TransactionType.AUDIT.getId());
		namedParameters.put("resendStatusId", ProcessingStatus.READY_TO_RESEND.getId());
		namedParameters.put("transactionLimit", transactionLimit);

		List<Transaction> foundTransactions;

		try {
			foundTransactions = jdbcTemplate.query(sqlGetCloseAuditTransactionsReadyForResend, namedParameters, new TransactionRowMapper());
			logger.debug(String.format("found %d transactions", foundTransactions.size()));
		}
		catch (DataAccessException e) {
			throw new DataException("Unable to find call close transactions without responses.", e);
		}

		logger.info(String.format("exiting %s", methodName));
		return foundTransactions;
		
	}
	
	public Transaction getCallCloseTransactionByAuditTransactionId(Long auditTransactionId) throws DataException {
		final String methodName = "getCallCloseTransactionByAuditTransactionId";
		logger.info(String.format("entered %s", methodName));

		Map<String, Long> namedParameters = new HashMap<String, Long>();
		namedParameters.put("auditTransactionId", auditTransactionId);

		Transaction callCloseTransaction;

		try {
			callCloseTransaction = (Transaction) jdbcTemplate.queryForObject(sqlGetCallCloseTransactionByAuditTransactionId, namedParameters, new TransactionRowMapper());
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find call close transactions from audit transaction with transactionId '%d'.", auditTransactionId), e);
		}

		logger.info(String.format("exiting %s", methodName));
		return callCloseTransaction;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getNonUniqueNewTransactionsByInputFile(String inputFile) {
		final String methodName = "getNonUniqueNewTransactionsByInputFile";
		logger.info(String.format("entered %s", methodName));

		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("fileName", inputFile);
		namedParameters.put("newTransactionTypeId", TransactionType.NEW.getId());
		namedParameters.put("createdStatusId", ProcessingStatus.CREATED.getId());
		namedParameters.put("successStatusId", ProcessingStatus.SUCCESS.getId());
		namedParameters.put("readyForProcessingStatusId", ProcessingStatus.READY_TO_PROCESS.getId());

		List<Transaction> foundTransactions;

		try {
			foundTransactions = jdbcTemplate.query(sqlGetNonUniqueNewTransactionsByInputFile, namedParameters, new TransactionRowMapper());
			logger.debug(String.format("found %d transactions", foundTransactions.size()));
		}
		catch (DataAccessException e) {
			throw new DataException("Unable to find non-unique new transactions.", e);
		}

		logger.info(String.format("exiting %s", methodName));
		return foundTransactions;
	}
		
	public boolean acquireLock(String lockName, String acquiredBy, Double lockExpirationMinutes) {
		final String methodName = "acquireLock";
		logger.debug(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("lockName", lockName);
		namedParameters.put("acquiredBy", acquiredBy);
		namedParameters.put("lockExpiration", lockExpirationMinutes);

		int updated = 0;
		try {
			updated = jdbcTemplate.update(sqlAcquireLock, new MapSqlParameterSource(namedParameters));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to acquire lock: %s.", lockName), e);
		}	

		logger.debug(String.format("exiting %s", methodName));
		return updated > 0;
	}
	
	public boolean createAndAcquireLock(String lockName, String acquiredBy) {
		final String methodName = "createAndAcquireLock";
		logger.debug(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("lockName", lockName);
		namedParameters.put("acquiredBy", acquiredBy);

		int updated = 0;
		try {
			updated = jdbcTemplate.update(sqlCreateAndAcquireLock, new MapSqlParameterSource(namedParameters));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to acquire lock: %s.", lockName), e);
		}	

		logger.debug(String.format("exiting %s", methodName));
		return updated > 0;
	}
	
	public boolean releaseLock(String lockName) {
		final String methodName = "releaseLock";
		logger.debug(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("lockName", lockName);

		int updated = 0;
		try {
			updated = jdbcTemplate.update(sqlReleaseLock, new MapSqlParameterSource(namedParameters));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to release lock: %s.", lockName), e);
		}	

		logger.debug(String.format("exiting %s", methodName));
		return updated > 0;
	}
	
	public Lock getLockInformation(String lockName) throws DataException {
		final String methodName = "getLockInformation";
		logger.debug(String.format("entered %s", methodName));

		Map<String, String> namedParameters = new HashMap<String, String>();
		namedParameters.put("lockName", lockName);

		Lock lock = null;

		try {
			lock = (Lock) jdbcTemplate.queryForObject(sqlGetLockInformation, namedParameters, new LockRowMapper());
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find a lock with name '%s'.", lockName), e);
		}

		logger.debug(String.format("exiting %s", methodName));
		return lock;
	}
	@SuppressWarnings("unchecked")
	public List<Transaction> findTransactions(String serviceLiveOrderId, TransactionType transactionType) {
		final String methodName = "findTransactions";
		logger.info(String.format("entered %s", methodName));

		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("serviceLiveOrderId", serviceLiveOrderId);
		namedParameters.put("transactionTypeId", transactionType.getId());
		namedParameters.put("statusId", ProcessingStatus.SUCCESS.getId());

		List<Transaction> foundTransactions;

		try {
			foundTransactions = jdbcTemplate.query(sqlFindTransactionsByServiceLiveOrderIdTransactionTypeIdAndStatusId, namedParameters, new TransactionRowMapper());
			logger.debug(String.format("found %d transactions", foundTransactions.size()));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find transactions for serviceLiveOrderId '%s', transactionType '%s', and processingStatus '%s'.", serviceLiveOrderId, transactionType.toString(), ProcessingStatus.SUCCESS.toString()), e);
		}

		logger.info(String.format("exiting %s", methodName));
		return foundTransactions;
	}

	public void createOmsTask(OmsTask omsTask) throws DataException {
	    final String methodName = "createOmsTask";
		logger.debug(String.format("entered %s", methodName));
	
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		
		namedParameters.put("taskId", omsTask.getTaskId());
		namedParameters.put("chargeCode", omsTask.getChargeCode());
		namedParameters.put("coverage", omsTask.getCoverage());
		namedParameters.put("type", omsTask.getType());
		namedParameters.put("description", omsTask.getDescription());
		
		KeyHolder generatedKeys = new GeneratedKeyHolder();
		long id;
		try {
			jdbcTemplate.update(sqlInsertOmsTask, new MapSqlParameterSource(namedParameters), 
				generatedKeys);
		
			id = generatedKeys.getKey().longValue();
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to create OmsTask: %s.", omsTask.toString()), e);
		}
		logger.debug(String.format("created OmsTask with id %d", id));

		omsTask.setOmsTaskId(id);
		logger.debug(String.format("exiting %s", methodName));
		return;
	}

	@SuppressWarnings("unchecked")
	public List<OmsTask> getOmsTasksByTaskIds(List<Long> taskIds) {
    	final String methodName = "getOmsTasksByTaskIds";
		logger.debug(String.format("entered %s", methodName));

		if (CollectionUtils.isEmpty(taskIds)) {
			logger.info(String.format("exiting %s", methodName));
			return Collections.emptyList();
		}

		Map<String, List<Long>> namedParameters = new HashMap<String, List<Long>>();
		namedParameters.put("taskIds", taskIds);

		List<OmsTask> foundOmsTasks;

		try {
			foundOmsTasks = jdbcTemplate.query(sqlGetOmsTasksByTaskIds, namedParameters, new OmsTaskMapper());
			logger.debug(String.format("found %d OmsTasks", foundOmsTasks.size()));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find OmsTasks for taksIds %s.", StringUtils.collectionToCommaDelimitedString(taskIds)), e);
		}

		logger.debug(String.format("exiting %s", methodName));
		return foundOmsTasks;
	}

	@SuppressWarnings("unchecked")
	public List<Task> getTasksByServiceOrderIdExcludingCoverage(long serviceOrderId, String coverage) {
    	final String methodName = "getTasksByServiceOrderId";
		logger.debug(String.format("entered %s", methodName));

		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("serviceOrderId", serviceOrderId);
		namedParameters.put("coverage", coverage);

		List<Task> foundTasks;
		try {
			foundTasks = jdbcTemplate.query(sqlGetTasksByServiceOrderIdExcludingCoverage, namedParameters, new TaskMapper());
			logger.debug(String.format("found %d tasks", foundTasks.size()));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find tasks for serviceOrderId %d.", serviceOrderId), e);
		}

		logger.debug(String.format("exiting %s", methodName));
		return foundTasks;
	}
	
	@SuppressWarnings("unchecked")
	public List<Task> getTasksByServiceOrderId(long serviceOrderId) {
    	final String methodName = "getTasksByServiceOrderId";
		logger.debug(String.format("entered %s", methodName));

		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("serviceOrderId", serviceOrderId);
		
		List<Task> foundTasks;
		try {
			foundTasks = jdbcTemplate.query(sqlGetTasksByServiceOrderId, namedParameters, new TaskMapper());
			logger.debug(String.format("found %d tasks", foundTasks.size()));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find tasks for serviceOrderId %d.", serviceOrderId), e);
		}

		logger.debug(String.format("exiting %s", methodName));
		return foundTasks;
	}

	@SuppressWarnings("unchecked")
	public List<Task> getTasksByServiceOrderIdsList(
			List<Long> serviceOrderIdsList) throws DataException {

		final String methodName = "getTasksByServiceOrderIdsList";
		logger.debug(String.format("entered %s", methodName));

		if (CollectionUtils.isEmpty(serviceOrderIdsList)) {
			logger.debug(String.format("exiting %s", methodName));
			return Collections.emptyList();
		}

		Map<String, List<Long>> namedParameters = new HashMap<String, List<Long>>();
		namedParameters.put("serviceOrderIdsList", serviceOrderIdsList);

		List<Task> foundTasks;
		try {
			foundTasks = jdbcTemplate.query(sqlGetTasksByServiceOrderIdsList, namedParameters, new TaskMapper());
			logger.debug(String.format("found %d tasks", foundTasks.size()));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find tasks for serviceOrderIds %s.", StringUtils.collectionToCommaDelimitedString(serviceOrderIdsList)), e);
		}

		logger.debug(String.format("exiting %s", methodName));
		return foundTasks;
	}

	@SuppressWarnings("unchecked")
	public List<ServiceOrder> getServiceOrdersByTransactionIds(List<Long> transactionIdsList) throws DataException {
		final String methodName = "getServiceOrdersByTransactionIds";
		logger.debug(String.format("entered %s", methodName));
	
		if (CollectionUtils.isEmpty(transactionIdsList)) {
			logger.debug(String.format("exiting %s", methodName));
			return Collections.emptyList();
		}

		Map<String, List<Long>> namedParameters = new HashMap<String, List<Long>>();
		namedParameters.put("transactionIdsList", transactionIdsList);

		List<ServiceOrder> orders;
		try {
			orders = jdbcTemplate.query(sqlGetServiceOrdersByTransactionIdsList, namedParameters, new ServiceOrderRetryMapper());
			logger.debug(String.format("found %d ServiceOrders", orders.size()));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find ServiceOrders for transactionIds %s.", StringUtils.collectionToCommaDelimitedString(transactionIdsList)), e);
		}

		logger.debug(String.format("exiting %s", methodName));
		return orders;
	}
	
	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	
	public Transaction getCancelTransactionByAuditTransactionId(Long auditTransactionId) throws DataException {
		final String methodName = "getCancelTransactionByAuditTransactionId";
		logger.debug(String.format("entered %s", methodName));

		Map<String, Long> namedParameters = new HashMap<String, Long>();
		namedParameters.put("auditTransactionId", auditTransactionId);

		Transaction cancelTransaction;

		try {
			cancelTransaction = (Transaction) jdbcTemplate.queryForObject(sqlGetCallCloseTransactionByAuditTransactionId, namedParameters, new TransactionRowMapper());
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find cancellation transactions from audit transaction with transactionId '%d'.", auditTransactionId), e);
		}

		logger.debug(String.format("exiting %s", methodName));
		return cancelTransaction;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getCancelAuditTransationsMarkedForResend(Long transactionLimit) {
		final String methodName = "getCancelAuditTransationsMarkedForResend";
		logger.debug(String.format("entered %s", methodName));

		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("auditIntegrationId", IntegrationName.NPS_CANCEL_AUDIT.getId());
		namedParameters.put("auditTransactionTypeId", TransactionType.AUDIT.getId());
		namedParameters.put("resendStatusId", ProcessingStatus.READY_TO_RESEND.getId());
		namedParameters.put("transactionLimit", transactionLimit);

		List<Transaction> foundTransactions;

		try {
			//reusing the call close audit query as there is no change
			foundTransactions = jdbcTemplate.query(sqlGetCloseAuditTransactionsReadyForResend, namedParameters, new TransactionRowMapper());
			logger.debug(String.format("found %d transactions", foundTransactions.size()));
		}
		catch (DataAccessException e) {
			throw new DataException("Unable to find cancellation transactions without responses.", e);
		}

		logger.debug(String.format("exiting %s", methodName));
		return foundTransactions;
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getCancellationTransationsWithoutResponses(Long retryMinutes, Long transactionLimit) {
		final String methodName = "getCancellationTransationsWithoutResponses";
		logger.debug(String.format("entered %s", methodName));

		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("outboundIntegrationId", IntegrationName.RI_CANCEL_OUTBOUND.getId());
		namedParameters.put("cancelTransactionTypeId", TransactionType.BUYER_NOTIFICATION.getId());
		namedParameters.put("resentStatusId", ProcessingStatus.RESENT.getId());
		namedParameters.put("intervalInMinutes", retryMinutes);
		namedParameters.put("transactionLimit", transactionLimit);
		namedParameters.put("cancelAckTransactionTypeId", TransactionType.CANCEL.getId());
		
		List<Transaction> foundTransactions;

		try {
			foundTransactions = jdbcTemplate.query(sqlGetCancellationTransactionsWithoutResponses, namedParameters, new TransactionRowMapper());
			logger.debug(String.format("found %d transactions", foundTransactions.size()));
		}
		catch (DataAccessException e) {
			throw new DataException("Unable to find cancellation transactions without responses.", e);
		}

		logger.debug(String.format("exiting %s", methodName));
		return foundTransactions;
	}

	@SuppressWarnings("unchecked")
	public int getTransactionTypeById(Long transactionId) {
		final String methodName = "getTransactionTypeById";
		logger.debug(String.format("entered %s", methodName));
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("transactionId", transactionId );
		int transactionType;
		
		try {
			transactionType = jdbcTemplate.queryForInt(sqlGetTransactionTypeById, namedParameters);
		}
		catch(DataAccessException e){
			throw new DataException("Unable to find transaction type by transactionId", e);
		}
		logger.debug(String.format("exiting %s", methodName));
		return transactionType;
	}
	
	/* SL-21931 -- fetching buyer from database and removing hard coded username from property file*/
	
	@SuppressWarnings("unchecked")
	public String getBuyerUserNameByBuyerId(Integer buyerId) throws DataException 
	{
		final String methodName = "getBuyerUserNameByBuyerId";
		logger.info(String.format("entered %s", methodName + buyerId));

		Map<String, Integer> namedParameters = new HashMap<String, Integer>();
		namedParameters.put("id", buyerId);

		String username= new String();
		
		
		try {
			username= (String) jdbcTemplate.queryForObject(sqlgetUserNameByBuyerId, namedParameters, String.class);
			logger.info(String.format("found buyer username : -----------------" + username));
		}
		catch (DataAccessException e) {
			throw new DataException(String.format("Unable to find buyer for  buyerId %d .", buyerId), e);
		}

		logger.info(String.format("exiting %s", methodName));
		return username;
	}
	
	
}
