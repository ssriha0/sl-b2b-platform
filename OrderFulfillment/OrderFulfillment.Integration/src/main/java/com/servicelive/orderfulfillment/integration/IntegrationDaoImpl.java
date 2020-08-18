package com.servicelive.orderfulfillment.integration;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.servicelive.orderfulfillment.common.ServiceOrderValidationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;

import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.SOPart;
import com.servicelive.orderfulfillment.domain.SOPhone;
import com.servicelive.orderfulfillment.domain.SOSalesCheckItems;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.integration.domain.KeyValuePairs;
import com.servicelive.orderfulfillment.integration.domain.IntegrationDirection;
import com.servicelive.orderfulfillment.integration.domain.ProcessingStatus;
import com.servicelive.orderfulfillment.integration.domain.SimpleCache;
import com.servicelive.orderfulfillment.integration.domain.Transaction;
import com.servicelive.orderfulfillment.integration.rowmapper.KeyValueRowMapper;
import com.servicelive.orderfulfillment.integration.rowmapper.SOContactRowMapper;
import com.servicelive.orderfulfillment.integration.rowmapper.SOLocationRowMapper;
import com.servicelive.orderfulfillment.integration.rowmapper.SONotesRowMapper;
import com.servicelive.orderfulfillment.integration.rowmapper.SOPartsRowMapper;
import com.servicelive.orderfulfillment.integration.rowmapper.SOPhoneRowMapper;
import com.servicelive.orderfulfillment.integration.rowmapper.SOSalesItemsRowMapper;
import com.servicelive.orderfulfillment.integration.rowmapper.SOTaskRowMapper;
import com.servicelive.orderfulfillment.integration.rowmapper.ServiceOrderRowMapper;
import com.servicelive.orderfulfillment.integration.rowmapper.TransactionIdRowMapper;
import com.servicelive.orderfulfillment.integration.rowmapper.TransactionRowMapper;

/**
 * User: Mustafa Motiwala
 * Date: Apr 12, 2010
 * Time: 10:09:46 AM
 */
public class IntegrationDaoImpl implements IntegrationDao {
    private static final Log log = LogFactory.getLog(IntegrationDao.class);
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Find transactions that need processing
     */
    private static final String sqlFindTransactionsReadyForProcessing =
		    " SELECT t.transactionId \n" +
		    " FROM transactions t \n" +
		    "   JOIN batches b ON t.batchId = b.batchId \n" +
		    "   JOIN integrations i ON b.integrationId = i.integrationId \n" +
		    " WHERE i.direction = :direction \n" +
		    "   AND t.statusId = :currentStatus \n" +
		    "   AND t.processAfter < NOW() \n" +
		    "   AND t.serviceLiveOrderId IS NULL \n" +
		    " ORDER BY t.transactionId ASC \n" +
		    " LIMIT :limit \n" +
		    " FOR UPDATE";

    /**
     * Find transactions that need processing
     */
    private static final String sqlFindTransactionsReadyForProcessingNew =
		    " SELECT t.transactionId \n" +
		    " FROM transactions t \n" +
		    "   JOIN batches b ON t.batchId = b.batchId \n" +
		    "   JOIN integrations i ON b.integrationId = i.integrationId \n" +
		    " WHERE i.direction = :direction \n" +
		    "   AND t.statusId = :currentStatus \n" +
		    "   AND t.processAfter < NOW() \n" +
		    "   AND t.serviceLiveOrderId IS NULL \n" +
		    " ORDER BY t.transactionId ASC \n" +
		    " LIMIT :limit";
    
    /**
     * Find transactions that need processing with priority processing
     */
    private static final String sqlFindTransactionsReadyForProcessingWithPriority =
    	"SELECT t.transactionId, \n" +
        ":selectClause \n" +
    	"FROM transactions t \n" +
        "JOIN batches b ON t.batchid = b.batchid \n" +
        "JOIN integrations i ON b.integrationid = i.integrationid \n" +
        "JOIN serviceorders s ON s.transactionid = t.transactionid \n" +
    	"WHERE i.direction = :direction \n" +
        "AND t.statusid = :currentStatus \n" +
        "AND t.serviceLiveOrderId IS NULL \n" ;   

    
    /**
     * Find transactions that are being processed and have been claimed for
     * too long
     */
    private static final String sqlFindTransactionsStuckInProcessingState = 
	        " SELECT t.transactionId \n" +
	        " FROM transactions t \n" +
	        " WHERE t.statusId = :currentStatus \n" +
	        "   AND t.claimedOn < DATE_SUB(NOW(), INTERVAL :expiredDelayInMinutes MINUTE) \n" +
	        " ORDER BY t.transactionId ASC \n" +
	        " LIMIT :limit \n" +
	        " FOR UPDATE";

    private static final String sqlAcquireTransactionsByIdList = 
	        " UPDATE transactions t \n" +
	        " SET \n" +
	        "   t.claimedBy = :claimedBy, \n" +
	        "   t.statusId = :newStatus, \n" +
	        "   t.claimedOn = NOW() \n" + 
	        " WHERE t.transactionId IN (:transactionIdsList)";

    /**
     * SQL statement used to get all Transactions for a workerId.
     */
    private static final String sqlTransactions =
    		" SELECT \n" +
            "       tx.*,\n" +
            "       b.buyerName,\n" +
            "       b.platformBuyerId,\n" +
            "       b.platformRoleId,\n" +
            "       b.state,\n" +
            "       so.serviceOrderId\n" +
            "  FROM transactions tx\n" +
            "   JOIN batches bat ON tx.batchId = bat.batchId\n" +
            "   JOIN integrations integ ON bat.integrationId = integ.integrationId\n" +
            "   JOIN buyers b ON integ.buyerId = b.buyerId\n" +
            "   JOIN serviceorders so ON so.transactionId = tx.transactionId\n" +
            " WHERE tx.claimedBy = :workerId\n" +
            "   AND tx.statusId = :claimedStatus\n" +
            "   AND integ.direction = :direction\n" +
            " FOR UPDATE;";

    /**
     * SQL statement used to get all ServiceOrders for a given TransactionId.
     */
    private static final String sqlServiceOrderByTransaction =
            " SELECT \n" +
            "   buyer.platformBuyerId, \n" +
            "   tx.externalOrderNumber, \n" +
            "   tx.buyerResourceId, \n" +
            "   so.*\n" +
            " FROM serviceorders so\n" +
            "   JOIN transactions tx ON so.transactionId = tx.transactionId\n" +
            "   JOIN batches bat ON tx.batchId = bat.batchId\n" +
            "   JOIN integrations integ ON bat.integrationId = integ.integrationId\n" +
            "   JOIN buyers buyer ON integ.buyerId = buyer.buyerId\n" +
            " WHERE tx.transactionId= :transactionId;";

    /**
     * Mark a transaction as ERROR and update the exception info.
     */
    private static final String sqlTransactionError =
            " UPDATE transactions\n" +
            " SET\n" +
            "   statusId = :status,\n" +
            "       exception=:exception\n"+
            " WHERE transactionId = :transactionId;";

    /**
     * Mark the given Transaction as completed successfully.
     */
    private static final String sqlTransactionSuccess =
            " UPDATE transactions\n" +
            " SET\n" +
            "   statusId =:status,\n" +
            "       processedOn = NOW(),\n" +
            "       serviceLiveOrderId = :slOrderId\n" +
            " WHERE transactionId = :transactionId";

    /**
     * SQL to release a transaction, to be picked up later.
     */
    private static final String sqlReleaseTransaction =
    		" UPDATE transactions\n" +
            " SET\n" +
            "   claimedBy = NULL,\n" +
            "       statusId = :readyToProcessStatus,\n" +
            "       exception = NULL,\n" +
            "       processedOn = NULL,\n" +
            "       serviceLiveOrderId = NULL,\n" +
            "       claimedOn = NULL,\n" +
            "       processAfter = DATE_ADD(NOW(), INTERVAL :processingDelayInMinutes MINUTE)" +
            " WHERE transactionId = :transactionId;";

    /**
     * SQL to load Tasks for a given ServiceOrder
     */
    private static final String sqlTasksForServiceOrder =
            " SELECT *\n" +
            " FROM tasks\n" +
            " WHERE serviceOrderId=:serviceOrderId";
    
    /**
     * SQL to load sales items for a given ServiceOrder
     */
    private static final String sqlSalesItemsForServiceOrder =
            " SELECT *\n" +
            " FROM sales_check_items\n" +
            " WHERE serviceOrderId=:serviceOrderId";

    /**
     * SQL to load all notes for a given ServiceOrder
     */
    private static final String sqlNotesForServiceOrder =
            " SELECT *\n" +
            " FROM notes\n" +
            " WHERE serviceOrderId=:serviceOrderId";
    /**
     * SQL to load logo for service order there should be only one
     */
    private static final String sqlLogoForServiceOrder =
            " SELECT documentTitle\n" +
            " FROM documents\n" +
            " WHERE serviceOrderId=:serviceOrderId AND isLogo = 1";
    /**
     * SQL to load all documents other than logo
     */
    private static final String sqlDocumentForServiceOrder =
            " SELECT documentTitle\n" +
            " FROM documents\n" +
            " WHERE serviceOrderId=:serviceOrderId AND isLogo = 0";
    /**
     * SQL to load all Contact information for a given ServiceOrder.
     */
    private static final String sqlContactsForServiceOrder =
            " SELECT *\n" +
            " FROM contacts\n" +
            " WHERE serviceOrderId=:serviceOrderId";

    /**
     * SQL to load all phone information for a given Contact.
     */
    private static final String sqlPhoneForContact =
            " SELECT *\n" +
            " FROM phones\n" +
            " WHERE contactId=:contactId";

    /**
     * SQL to load all Location information for a given ServiceOrder.
     */
    private static final String sqlLocationForServiceOrder =
            " SELECT *\n" +
            " FROM locations\n" +
            " WHERE serviceOrderId=:serviceOrderId";
    /**
     * SQL to load all Parts for given ServiceOrder
     */
    private static final String sqlPartsForServiceOrder =
            " SELECT *\n" +
            " FROM parts\n" +
            " WHERE serviceOrderId=:serviceOrderId";

    /**
     * Record error that occurred during transaction processing
     */
     private static final String sqlWorkerError =
            " UPDATE transactions\n" +
            " SET\n" +
            "   statusId = :status,\n" +
            "       exception=:exception\n"+
            " WHERE claimedBy = :workerId" +
            " AND statusId = :currentStatus";

     /**
      * Record error for transactions that have not been processed in a
      * timely manner
      */
     private static final String sqlErrorExpired =
            " UPDATE transactions t\n" +
            "  INNER JOIN batches b ON t.batchId = b.batchId\n" +
            "  INNER JOIN integrations i ON b.integrationId = i.integrationId\n" +
            " SET\n" +
            "   t.statusId = :status,\n" +
            "     t.exception = :exception\n" +
            " WHERE DATE_SUB(NOW(), INTERVAL :expirationIntervalInHours HOUR) > t.createdOn\n" +
            "   AND t.statusId = :notrun\n" +
            "   AND i.direction = :direction;";

    /**
      * SQL to find ancestor transaction record
      */
     private static final String sqlFindAncestor =
			" SELECT serviceLiveOrderId\n" +
			" FROM transactions tx \n" +
			" WHERE tx.externalOrderNumber = :externalOrderNumber \n" +
			"   AND tx.transactionId <> :currentTransactionId \n" +
			"   AND tx.transactionTypeId IN (1, 2)" + // 1 => 'NEW', 2 => 'UPDATE'
			"   AND tx.statusId = 1 " + //make sure the record successfully completed
			" ORDER BY tx.transactionId DESC \n" +
			" LIMIT 1";
     
     private static final String sqlGetKeyValuePairs = "" +
		"SELECT * from key_value_pairs \n" + 
		"WHERE category = :category \n" +
		"AND sub_category = :sub_category \n" + 
		"ORDER BY priority_indicator DESC";
     
     /**SL-19776
      * SQL to get the statusCode for externalOrderNo
      */
     private static final String sqlServiceOrderStatusCode =
             " SELECT externalStatus \n" +
             " FROM serviceorders \n" +
             " WHERE transactionId = :transactionId";
     
     /**
      * Record exception that occurred during transaction processing
      */
      private static final String sqlException =
             " UPDATE transactions \n" +
             " SET exception=:exception, \n" +
             " statusId = 2 \n" +
             " WHERE transactionId = :transactionId";
    	 
    /**
     * Gets all available transaction that have not been processed by
     * the workerId.
     * @param workerId worker from whom transactions have been requested.
     * @return List of Transaction objects.
     */
     
     /**
      * SL-18883 Query to insert entry to prevent duplicate service order creation
      * Insert entry into table
      * @param buyerId buyerId from whom transactions have been requested.
      * @param externalOrderNumber externalOrderNumber  from whom transactions have been requested.
      */
     private static final String sqlInsertQuery=""+
      "INSERT INTO processed_external_order_number (buyerId, externalOrderNumber) VALUES (:buyerId, :externalOrderNumber)";
     
    @SuppressWarnings("unchecked")
    public List<Transaction> getAvailableTransactions(String workerId) {
        Map<String,Object> parameters = new HashMap<String, Object>(1);
        parameters.put("workerId",workerId);
        parameters.put("claimedStatus", ProcessingStatus.PROCESSING.getId());
        parameters.put("direction", IntegrationDirection.INCOMING.name());
        List<Transaction> returnVal = jdbcTemplate.query(sqlTransactions,parameters, new TransactionRowMapper());
        for(Transaction transaction:returnVal){
            parameters.put("transactionId", transaction.getId());
            ServiceOrder so = (ServiceOrder)jdbcTemplate.queryForObject(sqlServiceOrderByTransaction,parameters,new ServiceOrderRowMapper());
            parameters.put("serviceOrderId", so.getSoId());
            so.setSoId(null);
            loadLogo(so, parameters);
            loadParts(so,parameters);
            loadLocations(so, parameters);
            loadContacts(so, parameters);
            loadNotes(so, parameters);
            loadTasks(so, parameters);
            transaction.setServiceOrder(so);
        }
        return returnVal;
    }


    private void loadLogo(ServiceOrder so, Map<String, Object> parameters) {
		try{
	    	String documentTitle = (String) jdbcTemplate.queryForObject(sqlLogoForServiceOrder, parameters, String.class);
			so.setLogoDocumentTitle(documentTitle);
		} catch (EmptyResultDataAccessException erdae){
			//ignoring the exception since there was no logo defined for this service order
		}
	}

    @SuppressWarnings("unchecked")
	public List<String> getDocumentTitles(Long serviceOrderId){
        Map<String,Object> parameters = new HashMap<String, Object>(3);
        parameters.put("serviceOrderId",serviceOrderId);
        return jdbcTemplate.query(sqlDocumentForServiceOrder, parameters, new RowMapper() {
			
			public Object mapRow(ResultSet resultSet, int arg1) throws SQLException {
				return resultSet.getString("documentTitle");
			}
		});
    }

	@SuppressWarnings("unchecked")
    private void loadParts(ServiceOrder so, Map<String, Object> parameters) {
        List<SOPart> parts= jdbcTemplate.query(sqlPartsForServiceOrder,parameters,new SOPartsRowMapper(so));
        so.setParts(parts);
    }

    @SuppressWarnings("unchecked")
    private void loadLocations(ServiceOrder so, Map<String,Object> parameters) {
        List<SOLocation> locations= jdbcTemplate.query(sqlLocationForServiceOrder,parameters,new SOLocationRowMapper(so));
        so.setLocations(locations);
    }

    @SuppressWarnings("unchecked")
    private void loadContacts(ServiceOrder so, Map<String,Object> parameters) {
        List<SOContact> contacts= jdbcTemplate.query(sqlContactsForServiceOrder,parameters,new SOContactRowMapper(so));
        for(SOContact contact:contacts){
            parameters.put("contactId",contact.getSoContactId());
            List<SOPhone> phones=jdbcTemplate.query(sqlPhoneForContact, parameters,new SOPhoneRowMapper(contact));
            contact.setSoContactId(null);
            contact.setPhones(new HashSet<SOPhone>(phones));
        }
        so.setContacts(contacts);
    }

    @SuppressWarnings("unchecked")
    private void loadNotes(ServiceOrder so, Map<String,Object> parameters) {
        List<SONote> notes= jdbcTemplate.query(sqlNotesForServiceOrder,parameters,new SONotesRowMapper(so));
        so.setNotes(notes);
    }

    @SuppressWarnings("unchecked")
    private void loadTasks(ServiceOrder so, Map<String,Object> parameters) {
        List<SOTask> tasks = jdbcTemplate.query(sqlTasksForServiceOrder,parameters,new SOTaskRowMapper(so));
        //SL-20167 : Populating purchase amount in so_tasks table
        List<SOSalesCheckItems> items = jdbcTemplate.query(sqlSalesItemsForServiceOrder,parameters,new SOSalesItemsRowMapper());
        log.info("loadTasks() : after fetching items");
        if(null != items){
        	for(SOTask task : tasks){
            	for(SOSalesCheckItems item : items){
            		if(null != task && null != item){
            			log.info("loadTasks() : " + task.getExternalSku() + "-" + item.getItemNumber());
            			if(task.getExternalSku().equals(item.getItemNumber())){
            				BigDecimal purchaseAmt = new BigDecimal("0.00");
            				if(null != item.getPurchaseAmt()){
            					purchaseAmt = new BigDecimal(item.getPurchaseAmt());
            				}
            				log.info("loadTasks() : purchaseAmt :" + purchaseAmt);
            				task.setPurchaseAmount(purchaseAmt);
            			}
            		}
            	}
            }
        }
        so.setTasks(tasks);
        so.setItems(items);
    }

    public void markError(Transaction transaction, Exception exception) {
        Map<String,Object> parameters = new HashMap<String, Object>(3);
        parameters.put("transactionId",transaction.getId());
//SL-19412:set the status as success only during the duplicate SO creation scenario
        if (exception.getClass().toString().contains("DataIntegrityViolationException")){
        	parameters.put("status", ProcessingStatus.SUCCESS.getId());
        } else {
        	parameters.put("status", ProcessingStatus.ERROR.getId());
        	
        }
        parameters.put("exception",null);
        log.debug("Marking the transaction error");
        final StringBuffer exceptionStr = new StringBuffer();
        //check if the exception is validation error than log it differently
        if(exception instanceof ServiceOrderValidationException){
            exceptionStr.append(((ServiceOrderValidationException)exception).getValidationErrorString());
        } else {
        final StringWriter exceptionBuffer = new StringWriter();
        exception.printStackTrace(new PrintWriter(exceptionBuffer));
            exceptionStr.append(exceptionBuffer.toString());
        }
        LobHandler lobHandler = new DefaultLobHandler();

        try{
            jdbcTemplate.execute(sqlTransactionError, parameters, new AbstractLobCreatingPreparedStatementCallback(lobHandler){
                @Override
                protected void setValues(PreparedStatement preparedStatement, LobCreator lobCreator) throws SQLException, DataAccessException {
                    lobCreator.setClobAsString(preparedStatement, 2, exceptionStr.toString());
                }
            });
        }catch(Exception e){
            log.error("Exception trying to update Transaction Error:",e);
        }
    }

    public String findLatestSLOrderIdForExternalOrderNum(String externalOrderNumber, Long currentTransactionId) {
        Map<String,Object> parameters = new HashMap<String, Object>(2);
        parameters.put("externalOrderNumber",externalOrderNumber);
        parameters.put("currentTransactionId", currentTransactionId);
        String query = sqlFindAncestor;
        try{
            return (String)jdbcTemplate.queryForObject(query,parameters,String.class);
        }catch(EmptyResultDataAccessException e){
            return null;
        }
    }

    public void markCompleted(Transaction t) {
        Map<String,Object> parameters = new HashMap<String, Object>(4);
        parameters.put("transactionId",t.getId());
        parameters.put("status",ProcessingStatus.SUCCESS.getId());
        parameters.put("slOrderId",t.getServiceLiveOrderId());
        log.debug("marking the transaction complete");
        jdbcTemplate.update(sqlTransactionSuccess,parameters);
    }

    public void release(Transaction t, int processingDelayInMinutes) {
        Map<String,Object> parameters = new HashMap<String, Object>(2);
        parameters.put("transactionId", t.getId());
        parameters.put("readyToProcessStatus", ProcessingStatus.READY_TO_PROCESS.getId());
        parameters.put("processingDelayInMinutes", processingDelayInMinutes);
        jdbcTemplate.update(sqlReleaseTransaction, parameters);
    }

 	@SuppressWarnings("unchecked")
    public synchronized boolean acquireWork(String id, Integer maxRecords) {
        int recordCount = 0;
        Map<String,Object> parameters = new HashMap<String, Object>(5);
        parameters.put("direction", IntegrationDirection.INCOMING.name());
        parameters.put("currentStatus", ProcessingStatus.READY_TO_PROCESS.getId());
        parameters.put("limit", maxRecords);

        log.debug("finding new work");
        
        ArrayList<String> prioritySet = getPrioritySetting();
        List<Long> transactionIds = null;
     	
        if(prioritySet != null && prioritySet.size() > 0)
        {
        	log.info("Acquiring transactions based on Priority Settings");
        	String selectClause = "";
        	String orderByClause = "";
        	int size = prioritySet.size() - 1;
        	for(int i = size; i >= 0; i--){        	
        		String str = prioritySet.get(i);
        		StringTokenizer tokens = new StringTokenizer(str, stringTokenizer);
        		
        		String key = "" , value = "";
        		if(tokens.countTokens() >= 2){
        			key = tokens.nextToken();
        			value = tokens.nextToken();
        		}
        		orderByClause += " FIELD(" + key + "," + value;
        		selectClause += key;
        		if(i != 0)
        		{
        			orderByClause += ") DESC, ";
        			selectClause += ", ";
        		}
        		else{
        			orderByClause += ") DESC";
        		}
        			
        		log.debug("Values: " + key + " for " + value);
        	}       
        	
        	parameters.put("selectClause", selectClause);
        	log.debug("orderByClause - " + orderByClause);
        	log.debug("selectClause - " + selectClause);
        	
        	String sql = sqlFindTransactionsReadyForProcessingWithPriority + "ORDER BY " +  orderByClause + ",t.transactionId ASC LIMIT :limit";
        	transactionIds = 
        		jdbcTemplate.query(sql, 
        				parameters, new TransactionIdRowMapper());        
        }
        else{      
        	transactionIds = jdbcTemplate.query(
        			sqlFindTransactionsReadyForProcessingNew, parameters, new TransactionIdRowMapper());        	
        }
        if (transactionIds != null && !transactionIds.isEmpty()) {
    		parameters.clear();
    		parameters.put("claimedBy", id);
    		parameters.put("newStatus", ProcessingStatus.PROCESSING.getId());
    		parameters.put("transactionIdsList", transactionIds);

    		log.debug("acquiring found work");

    		recordCount = jdbcTemplate.update(sqlAcquireTransactionsByIdList, parameters);
    	}
        return recordCount > 0;
    }

 	private static String priorityCategory = "PRIORITY_PROCESSING";
 	private static String prioritySubCategory = "FLAG";
 	private static String priorityParameterSubCategory = "PRIORITY";
 	private static String priorityKeyName = "PROCESS_BY_PRIORITY";
 	private static String priorityCacheName = "priorityprocessing";
 	private static String stringTokenizer = "$";
 	
    @SuppressWarnings("unchecked")
	private ArrayList<String> getPrioritySetting()
    {
    	Object priorityProcessingObj =  SimpleCache.getInstance().get(priorityCacheName);
    	ArrayList<String> orderByList = new ArrayList<String>();
    	if(priorityProcessingObj != null)
    	{
    		log.debug("Got the values from cache");
    		return (ArrayList<String>) priorityProcessingObj;    		
    	}
    	
    	log.debug("Obtaining values from DB");
    	List<KeyValuePairs> resultSet = null;

    	resultSet = getPropertyByCategory(priorityCategory, prioritySubCategory);

    	//Iterate over key value pairs
    	boolean priorityFlag = false;
    	if(resultSet != null){
    	Iterator<KeyValuePairs> iter_properties = resultSet.iterator();
    	while(iter_properties.hasNext())
    	{
    		KeyValuePairs keyValuePair = iter_properties.next();
    		if(keyValuePair.getKeyName().equals(priorityKeyName) &&
    				("true").equals(keyValuePair.getKeyValue()))					
    		{
    			priorityFlag = true;
    			break;
    		}
    	}
    	
    	if(priorityFlag)
    	{
    		resultSet = getPropertyByCategory(priorityCategory, priorityParameterSubCategory);
    		if(resultSet == null)
    			return orderByList;
    		iter_properties = resultSet.iterator();
    		while(iter_properties.hasNext())
    		{
    			KeyValuePairs keyValuePair = iter_properties.next();
    			if(("true").equals(keyValuePair.getKeyValue()))					
    			{					
    				List<KeyValuePairs> priorityResultSet = 
    					getPropertyByCategory(priorityCategory, keyValuePair.getKeyName());
    				if(priorityResultSet != null){
    					String orderByString = "";
    					Iterator<KeyValuePairs> iter_prioritySet = priorityResultSet.iterator();
    					while(iter_prioritySet.hasNext())
    					{
    						KeyValuePairs esbPropObj = iter_prioritySet.next();
    						if(esbPropObj.getKeyCode()!= null && esbPropObj.getKeyCode().equals("date"))
    						{
    							orderByString += "DATE(Utc_timestamp()) " + esbPropObj.getKeyValue();
    						}
    						else
    							orderByString += esbPropObj.getKeyValue();
    						if(iter_prioritySet.hasNext()){
    							orderByString += ", ";
    						}
    					}
    					orderByList.add(removeQuotes(keyValuePair.getKeyCode()) + stringTokenizer + orderByString);				
    				}

    			}
    		}
    	}
    	}
    	log.debug("Putting values in Cache");
    	SimpleCache.getInstance().put(priorityCacheName, orderByList, SimpleCache.FIVE_MINUTES); 
    	return orderByList;
    }
    
	private String removeQuotes(String value){
		if(value != null)
		{
			return value.replaceAll("'", "");
		}
		return null;
	}
		
    private List<KeyValuePairs> getPropertyByCategory(
			String category, String subCategory) {
		
		List<KeyValuePairs> resultSet = null;
				
		if(resultSet == null){
		Map<String, Object> parameters = new HashMap<String, Object> ();
		parameters.put("category", category);
		parameters.put("sub_category", subCategory);
		log.debug("Getting Priority settings from DB for category " + category + "subcategory " + subCategory);
		try{
		resultSet = jdbcTemplate.query(sqlGetKeyValuePairs, parameters,
						new KeyValueRowMapper());
		}
		catch(DataAccessException e)
		{
			resultSet = null;
		}
	
		}
		
		return resultSet;
	}

    public synchronized boolean acquireExpired(String id, Integer maxRecords, Integer expireDelay){
        int recordCount = 0;
        Map<String,Object> parameters = new HashMap<String, Object>(6);
        parameters.put("currentStatus",ProcessingStatus.PROCESSING.getId());
        parameters.put("expiredDelayInMinutes", Math.abs(expireDelay));
        parameters.put("limit", maxRecords);

        log.debug("finding expired work");

        @SuppressWarnings("unchecked")
        List<Long> transactionIds = jdbcTemplate.query(sqlFindTransactionsStuckInProcessingState, parameters, new TransactionIdRowMapper());
        if (transactionIds != null && !transactionIds.isEmpty()) {
	        parameters.clear();
	        parameters.put("claimedBy", id);
	        parameters.put("newStatus", ProcessingStatus.PROCESSING.getId());
        	parameters.put("transactionIdsList", transactionIds);

        	log.debug("acquiring found work");
        	
        	recordCount = jdbcTemplate.update(sqlAcquireTransactionsByIdList, parameters);
        }
        
        return recordCount > 0;
    }

    public void markError(String workerId, Exception exception) {
        Map<String,Object> parameters = new HashMap<String, Object>(3);
        parameters.put("workerId",workerId);
        parameters.put("status", ProcessingStatus.ERROR.getId());
        parameters.put("exception",null);
        parameters.put("currentStatus", ProcessingStatus.PROCESSING.getId());
        log.debug("Error happened while acquiring work here are the parameters for the update query " + parameters);
        final StringWriter exceptionBuffer = new StringWriter();
        exception.printStackTrace(new PrintWriter(exceptionBuffer));
        LobHandler lobHandler = new DefaultLobHandler();
        try{
            jdbcTemplate.execute(sqlWorkerError, parameters, new AbstractLobCreatingPreparedStatementCallback(lobHandler){
                @Override
                protected void setValues(PreparedStatement preparedStatement, LobCreator lobCreator) throws SQLException, DataAccessException {
                    lobCreator.setClobAsString(preparedStatement, 2, exceptionBuffer.toString());
                }
            });
        }catch(Exception e){
            log.error("Exception trying to update Worker as Error:",e);
        }
    }

    public void errorExpired(Integer maximumExpireThresholdInHours) {
        Map<String,Object> parameters = new HashMap<String, Object>(4);
        parameters.put("status", ProcessingStatus.ERROR.getId());
        parameters.put("notrun", ProcessingStatus.READY_TO_PROCESS.getId());
        parameters.put("exception","Transactions have expired after waiting for a Preceding Transaction for more than the threshold value.");
        parameters.put("expirationIntervalInHours", maximumExpireThresholdInHours);
        parameters.put("direction", IntegrationDirection.INCOMING.name());
        log.debug("expiring the work that is not being processed for the maximum threshold");
        jdbcTemplate.update(sqlErrorExpired,parameters);
    }

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    // SL-18883: CLONE - Duplicate Service Orders is being created for a single external order number
    // Method to save buyer id and external order number in table processed_external_order_number
    public synchronized void saveExternalOrderNumber(Transaction transaction)
    {
    	Long buyerId=transaction.getPlatformBuyerId();
    	log.info("Buyer Id from saveExternalOrderNumber method");
    	String externalOrderNumber=transaction.getExternalOrderNumber();
    	Map<String,Object> parameters = new HashMap<String, Object>(4);
        parameters.put("buyerId",buyerId);
        parameters.put("externalOrderNumber", externalOrderNumber);
        log.info("Inserting entry into processed_external_order_number with buyer id "+buyerId+"and externalOrderNumber"+externalOrderNumber);
    	jdbcTemplate.update(sqlInsertQuery, parameters);
    	log.info("Insert query-->>"+sqlInsertQuery);
    	log.info("Successffully inserted entry into processed_external_order_number table");
    	
    }
    
    /**
     * SL-19776: checking the statusCode for transactionId.
     * @param transactionId
     * @return 
     */
	public String getServiceOrdeStatusCode(Long transactionId){
		String statusCode = null;
		Map<String,Object> parameters = new HashMap<String, Object>(3);
        parameters.put("transactionId",transactionId);
		try{
			statusCode = (String) jdbcTemplate.queryForObject(sqlServiceOrderStatusCode, parameters, String.class);
		} catch(Exception e){
            log.error("Exception trying to getServiceOrdeStatusCode",e);
            statusCode = null;
        }
		return statusCode;
	}
	
	/**
     * SL-19776: Save exception if cancellation code is injected with transactionType NEW
     * @param transaction
     * @param exception
     */
	public void updateException(Transaction transaction, String cancellationException){
		Map<String,Object> parameters = new HashMap<String, Object>(3);
		parameters.put("transactionId", transaction.getId());
		parameters.put("exception", cancellationException);
		try{
			jdbcTemplate.update(sqlException,parameters);
		} catch(Exception e){
            log.error("Exception trying to saveException",e);
        }
	}
}
