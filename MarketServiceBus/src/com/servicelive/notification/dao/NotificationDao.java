package com.servicelive.notification.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.servicelive.esb.integration.DataException;
import com.servicelive.esb.integration.domain.Customer;
import com.servicelive.esb.integration.domain.InhomePart;
import com.servicelive.esb.integration.rowMapper.CustomerRowMapper;
import com.servicelive.esb.integration.rowMapper.InhomePartRowMapper;
import com.servicelive.notification.vo.OutboundNotificationVO;

public class NotificationDao implements INotificationDao {
	
	private static final Logger logger = Logger.getLogger(NotificationDao.class);

	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String sqlOutBoundFlag = "SELECT app.app_value"
			+ "  FROM application_flags app"
			+ "  WHERE app.app_key =:outBoundFlag";
			
	
	//SL-21246: Code change starts
	
	private static final String sqlErrorList = "SELECT error.ErrorDetail"
            + "  FROM ERRORS_INHOME error";
	
	//Code change ends
	
	private static final String sqlserialNumberFlag = "SELECT app.app_value"
			+ "  FROM application_flags app"
			+ "  WHERE app.app_key =:serialNumberFlag";
	
	private static final String sqlInsertOutboundNotification = 
		"INSERT INTO buyer_outbound_notification"
		+ " (seq_no, so_id, service_id, xml, no_of_retries, active_ind, created_date, modified_date, status, exception)"
		+ " VALUES "
		+ " (:seqNo, :soId, :serviceId, :xml, :noOfRetries, :activeInd, NOW(), NOW(), :status, :exception)";
	
	private static final String sqlPartDetails = 
		"SELECT if(division_number is null,'',division_number) AS partDivNo,"
		+ " if(source_number is null,'',source_number) AS partPlsNo,"
		+ " if(part_no is null,'',part_no) AS partPartNo,"
		+ " if(qty is null,'0',qty) AS partOrderQty,"
		+ " if(qty is null,'0',qty) AS partInstallQty,"
		+ " if(part_coverage is null,'',part_coverage) AS partCoverageCode,"
		+ " if(retail_price is null,'0.00',retail_price) AS partPrice"
		+ " FROM so_provider_invoice_parts"
		+ " WHERE so_id = :soId"
		+ " AND part_status = :partStatus";
	
	private static final String sqlCustomerDetails = 
		"SELECT '' AS custKey,"
		+ " IF(c.honorific IS NULL,'', c.honorific) AS namePrefix,"
		+ " IF(c.first_name IS NULL,'', c.first_name) AS firstName,"
		+ " IF(c.mi IS NULL,'', c.mi) AS secondName,"
		+ " IF(c.last_name IS NULL,'', c.last_name) AS lastName,"
		+ " IF(c.suffix IS NULL,'', c.suffix) AS nameSuffix,"
		+ " IF(l.street_1 IS NULL,'', l.street_1) AS addressLine1,"
		+ " IF(l.street_2 IS NULL,'', l.street_2) AS addressLine2,"
		+ " IF(l.apt_no IS NULL,'', l.apt_no) AS aptNo,"
		+ " IF(l.city IS NULL,'', l.city) AS city,"
		+ " IF(l.state_cd IS NULL,'', l.state_cd) AS state,"
		+ " IF(l.zip IS NULL,'', l.zip) AS zipCode,"
		+ " '' AS zipCodeExtension,"
		+ " IF(p.phone_no IS NULL,'', p.phone_no) AS custPhoneNum,"
		+ " 'Y' AS preferredPrimaryCntFl,"
		+ " IF(c.email IS NULL,'', c.email) AS email"
		+ " FROM so_contact c"
		+ " JOIN so_location l"
		+ " ON c.so_id = l.so_id"
		+ " JOIN so_contact_phones p"
		+ " ON c.so_contact_id = p.so_contact_id"
		+ " WHERE c.so_contact_type_id = 10 "
		+ " AND l.so_locn_type_id = 10 "
		+ " AND p.phone_type_id = 1 "
		+ " AND c.so_id = :soId"
		+ " LIMIT 1";
	
	private static final String sqlNPSInactivInd = "SELECT nps_inactive_ind"
		+ "  FROM so_workflow_controls"
		+ "  WHERE so_id =:soId";
	
	private static final String sqlStatusChangeMessage = "SELECT message FROM " +
			"lu_inhome_outbound_notification_messages " +
			"WHERE status_id =:statusId  AND substatus_id IS NULL";
	
	private static final String sqlCorrelationId = 
		"SELECT MAX(notification_id) FROM buyer_outbound_notification";
	
    private static final String sqlgetAllMessage = "SELECT ExtractValue(xml, '//sendMessage')  FROM buyer_outbound_notification " +
    		"WHERE service_id = 3 AND so_id =:soId ORDER BY created_date DESC ";
    
    //SL-21241
    private static final String sqljobCodeForMainCategory = "SELECT job_code FROM " +
			"lu_jobcode WHERE primary_skill_category_id =:primarySkillCatId";
    
    
	/**get the value of inhome_outbound_flag
	 * @return
	 * @throws DataException
	 */
	public String getOutBoundFlag() throws DataException{
		
		logger.info("Entering getOutBoundFlag");
		Map<String, String> namedParameters = new HashMap<String, String>();
		namedParameters.put("outBoundFlag", "inhome_outbound_flag");

		String flag = null;
		try {
			flag = (String) jdbcTemplate.queryForObject(sqlOutBoundFlag, namedParameters, String.class);
			
		} catch (DataAccessException e) {
			logger.error("Exception in NotificationDao.getOutBoundFlag() due to "+e.getMessage());
			throw new DataException("Exception in NotificationDao.getOutBoundFlag() due to "+e.getMessage(), e);
		}

		return flag;
	}
	
	
	//SL-21246: Code change starts
    
    public List<String> getErrorList() throws DataException{
           
           logger.info("Entering getErrorList");
           Map<String, String> namedParameters = new HashMap<String, String>();

           List <String> errors = new ArrayList<String>();
           
           try {
        	   
        	   		List<Map<String, String>> rows = jdbcTemplate.queryForList(sqlErrorList, namedParameters);

        	   		for (Map<String, String> row : rows) {

        	   			for (Iterator<Map.Entry<String, String>> it = row.entrySet().iterator(); it.hasNext();) {
		
		        			   Map.Entry<String, String> entry = it.next();
		
		        			   String key = entry.getKey();
		
		        			   String value = entry.getValue();
		        			   
		        			   errors.add(value);
		
		        			   logger.info("Key : "+key + " + Value : " + value);
		        		   }
        	   }


           } catch (DataAccessException e) {
                  logger.error("Exception in NotificationDao.getErrorList() due to "+e.getMessage());
                  throw new DataException("Exception in NotificationDao.getErrorList() due to "+e.getMessage(), e);
           }

           return errors;
    }

    //Code change ends
	
	
	
	/**insert details into buyer_outbound_notification
	 * @param outBoundNotificationVO
	 * @throws DataException
	 */
	public void insertInHomeOutBoundNotification(OutboundNotificationVO outBoundNotificationVO) throws DataException{
		
		logger.info("Entering insertInHomeOutBoundNotification");
		Map<String, String> namedParameters = new HashMap<String, String>();
		namedParameters.put("soId", outBoundNotificationVO.getSoId());
		namedParameters.put("serviceId", outBoundNotificationVO.getServiceId().toString());
		namedParameters.put("xml", outBoundNotificationVO.getXml());
		namedParameters.put("noOfRetries", outBoundNotificationVO.getNoOfRetries().toString());
		namedParameters.put("activeInd", outBoundNotificationVO.getActiveInd().toString());
		namedParameters.put("status", outBoundNotificationVO.getStatus());
		namedParameters.put("exception", outBoundNotificationVO.getException());
		namedParameters.put("seqNo", outBoundNotificationVO.getSeqNo());
		KeyHolder generatedKeys = new GeneratedKeyHolder();
		
		try {
			jdbcTemplate.update(sqlInsertOutboundNotification, new MapSqlParameterSource(namedParameters), generatedKeys);
			
		} catch (DataAccessException e) {
			e.printStackTrace();
			logger.error("Exception in NotificationDao.insertInHomeOutBoundNotification() due to "+e.getMessage());
			throw new DataException("Exception in NotificationDao.insertInHomeOutBoundNotification() due to "+e.getMessage(), e);
		}
	}

	/**get the part details of SO
	 * @param soId
	 * @return
	 */
	public List<InhomePart> getPartDetails(String soId) {
		
		logger.info("Entering getPartDetails");
		Map<String, String> namedParameters = new HashMap<String, String>();
		namedParameters.put("soId", soId);
		namedParameters.put("partStatus", "Installed");
		List<InhomePart> returnVal = null;		
		try {
			returnVal = jdbcTemplate.query(sqlPartDetails, namedParameters, new InhomePartRowMapper());
			
		} catch (DataAccessException e) {
			logger.error("Exception in NotificationDao.insertInHomeOutBoundNotification() due to "+e.getMessage());
			throw new DataException("Exception in NotificationDao.getPartDetails() due to "+e.getMessage(), e);
		}
		return returnVal;
	}

	/**get customer info for SO
	 * @param soId
	 * @return
	 */
	public Customer getCustomerDetails(String soId) throws DataException {
		
		logger.info("Entering getCustomerDetails");
		Map<String, String> namedParameters = new HashMap<String, String>();
		namedParameters.put("soId", soId);
		
		Customer returnVal = null;		
		try {
			returnVal = (Customer)jdbcTemplate.queryForObject(sqlCustomerDetails, namedParameters, new CustomerRowMapper());
			
		} catch (DataAccessException e) {
			logger.error("Exception in NotificationDao.getCustomerDetails() due to "+e.getMessage());
			throw new DataException("Exception in NotificationDao.getPartDetails() due to "+e.getMessage(), e);
		}
		return returnVal;
	}
	
	/**get nps inactive ind for the SO 
	 * @param soId
	 * @return
	 * @throws DataException
	 */
	public Integer getNPSInactiveInd(String soId) throws DataException{
		
		logger.info("Entering getNPSInactiveInd");
		Map<String, String> namedParameters = new HashMap<String, String>();
		namedParameters.put("soId", soId);

		Integer inactiveInd = null;
		try {
			inactiveInd = (Integer) jdbcTemplate.queryForObject(sqlNPSInactivInd, namedParameters, Integer.class);
			
		} catch (DataAccessException e) {
			logger.error("Exception in NotificationDao.getNPSInactiveInd() due to "+e.getMessage());
			throw new DataException("Exception in NotificationDao.getNPSInactiveInd() due to "+e.getMessage(), e);
		}
		return inactiveInd;
	}

	/**get the message for status change
	 * @param wfStateId
	 * @return
	 * @throws DataException
	 */
	public String getMessageForStatusChange(Integer wfStateId) throws DataException {
		Map<String, Integer> namedParameters = new HashMap<String, Integer>();
		namedParameters.put("statusId", wfStateId);
		String message="";
		try{
			message= (String) jdbcTemplate.queryForObject(sqlStatusChangeMessage, namedParameters,String.class);
			
		}catch(DataAccessException e){
			logger.error("Exception in getting message for status change");
			throw new DataException("Exception in getting message for status change"+ e.getMessage());
		}
		return message;
	}
	
	/**get the correlation ID
	 * @return
	 * throws DataException
	 */
	public String getCorrelationId() throws DataException {
		
		logger.info("Entering getSeqNo");
		Map<String, String> namedParameters = new HashMap<String, String>();

		Integer seqNo = null;
		try {
			seqNo = (Integer) jdbcTemplate.queryForObject(sqlCorrelationId, namedParameters, Integer.class);
			if(null == seqNo){
				seqNo = 0;
			}
			seqNo = seqNo+1;
			
		} catch (DataAccessException e) {
			logger.error("Exception in NotificationDao.getNPSInactiveInd() due to "+e.getMessage());
			throw new DataException("Exception in NotificationDao.getNPSInactiveInd() due to "+e.getMessage(), e);
		}
		return seqNo.toString();		
	}

	public List<String> getAllMessageForSo(String soId) throws DataException {
		 List<String> allMessages = null;
		 Map<String, String> namedParameters = new HashMap<String, String>();
		 namedParameters.put("soId", soId);
		 try{
			 allMessages = jdbcTemplate.queryForList(sqlgetAllMessage,namedParameters,String.class);
		 }catch (DataAccessException e) {
		  logger.error("Exception in NotificationDao.getAllMessageForSo() due to "+ e);
		  throw new DataException("Exception in NotificationDao.getAllMessageForSo() due to "+e.getMessage(), e);
		}
		 return allMessages;
	}
	
	/**get the value of inhome_serial_number_flag
	 * @return
	 * @throws DataException
	 */
	public String getSerialNumberFlag() throws DataException{
		
		logger.info("Entering getSerialNumberConstant");
		Map<String, String> namedParameters = new HashMap<String, String>();
		namedParameters.put("serialNumberFlag", "inhome_serial_number_flag");

		String flag = null;
		try {
			flag = (String) jdbcTemplate.queryForObject(sqlserialNumberFlag, namedParameters, String.class);
			
		} catch (DataAccessException e) {
			logger.error("Exception in NotificationDao.getSerialNumberFlag() due to "+e.getMessage());
			throw new DataException("Exception in NotificationDao.getSerialNumberFlag() due to "+e.getMessage(), e);
		}

		return flag;
	}

	/**SL-21241
	 * get the jobCode for a main category from lu_jobcode
	 * @param primarySkillCatId
	 * @return
	 * @throws DataException
	 */
	public String getJobCodeForMainCategory(Integer primarySkillCatId) throws DataException{
		
		Map<String, Integer> namedParameters = new HashMap<String, Integer>();
		namedParameters.put("primarySkillCatId", primarySkillCatId);

		String jobCode = null;
		try {
			jobCode = (String) jdbcTemplate.queryForObject(sqljobCodeForMainCategory, namedParameters, String.class);
			
		} catch (Exception e) {
			logger.error("Exception in NotificationDao.getJobCodeForMainCategory() due to "+e.getMessage(), e);
		}

		return jobCode;
	}
	
	
	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	

}
