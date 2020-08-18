package com.newco.marketplace.business.businessImpl.smsdatamigration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.provider.IMarketPlaceDao;
import com.newco.marketplace.persistence.iDao.smsdatamigration.ISMSDataMigrationDao;
import com.newco.marketplace.smsdatamigration.service.ISMSDataMigrationService;
import com.newco.marketplace.smsdatasynch.vo.SMSDataSynchVO;
import com.newco.marketplace.vo.vibes.AddParticipantAPIRequest;
import com.newco.marketplace.vo.vibes.CustomField;
import com.newco.marketplace.vo.vibes.MobilePhone;
import com.newco.marketplace.vo.vibes.VibesResponseVO;
import com.servicelive.SimpleRestClient;

/**
 * @author Infosys
 * SL-18979
 * Service Implementation class for SMS Data Migration
 */

public class SMSDataMigrationServiceImpl implements ISMSDataMigrationService {
	
	private static final Logger logger = Logger.getLogger(SMSDataMigrationServiceImpl.class);
	private ISMSDataMigrationDao smsDataMigrationDao;
    private IMarketPlaceDao iMarketPlaceDao;
    Map <String, String> apiDetails = new HashMap<String, String>();
    List<String> apiDetailsNeeded = new ArrayList<String>();
    String url;
    String header;
    
    /**@Description: 
     *   1)set requst, url and header params to invoke vibe
     *   2)Invoke vibe's AddParticipant
     *   3) Map response to vo
     * @param migartionVo
     * @return
     * @throws BusinessServiceException
     */
    public SMSDataSynchVO addParticipantinVibes(SMSDataSynchVO migartionVo) throws BusinessServiceException{
    	SMSDataSynchVO addParticipantResponseVO =migartionVo;
    	try{
    		if(apiDetails.isEmpty() && apiDetailsNeeded.isEmpty()){
    			apiDetailsNeeded = PublicAPIConstant.ADD_PARTICIPANT_DETAILS();
    			apiDetails = iMarketPlaceDao.fetchApiDetails(apiDetailsNeeded);
    		}
    		if(StringUtils.isEmpty(header) && StringUtils.isEmpty(url)){
    			header = apiDetails.get(OrderConstants.HEADER);
    			url = apiDetails.get(OrderConstants.ADD_PARTICIPANT_API_URL);
    			url = url.replace(OrderConstants.URL_COMPANY_ID, apiDetails.get(OrderConstants.COMPANY_ID));
   			    url = url.replace(OrderConstants.URL_ACQUISITION_CAMPAIGN_ID, apiDetails.get(OrderConstants.ACQUISITION_CAMPAIGN_ID));
    		  }
   			   apiDetails.put(OrderConstants.URL,url);
			   //forming header
			   apiDetails.put(OrderConstants.HEADER, header);
			   //forming request
			   String addParticipantAPIRequest = setApiParticipantRequest(migartionVo);
			   //updating the request in map
			   apiDetails.put(OrderConstants.REQUEST,addParticipantAPIRequest);
			   try{
			      addParticipantResponseVO = invokeVibeForAddParticipant(apiDetails,migartionVo);
			   }catch (BusinessServiceException bse) {
				  logger.error("General Exception @MarketPlaceBOImpl.callAddParticipantVibes() due to"+ bse.getMessage());
				  addParticipantResponseVO.setStatusCode(OrderConstants.STATUS_CODE_EXCEPTION);
				   
			}
    		
    	}catch (Exception e) {
    		logger.error("General Exception @SMSDataMigrationServiceImpl.createResponseFromVibes() in"+ e.getMessage());
   			throw new BusinessServiceException("General Exception @SMSDataMigrationServiceImpl.createResponseFromVibes() due to "+ e.getMessage());
		}
		return addParticipantResponseVO;
    }
    
    /**
	 * @param apiDetailsMap
	 * @param migartionVo 
	 * @return
	 * @throws BusinessServiceException
	 */
	private SMSDataSynchVO invokeVibeForAddParticipant(Map<String, String> apiDetailsMap, SMSDataSynchVO migartionVo) throws BusinessServiceException {
		String url = apiDetails.get(OrderConstants.URL);
		String request = apiDetails.get(OrderConstants.REQUEST);
		String header = apiDetails.get(OrderConstants.HEADER);
		try{
			if(StringUtils.isNotBlank(url) && StringUtils.isNotBlank(request) && StringUtils.isNotBlank(header)){
				VibesResponseVO response = createResponseFromVibes(request,url,header);
				migartionVo = mapResponse(response,migartionVo);
			}
		}catch (Exception e) {
			e.printStackTrace();
   			throw new BusinessServiceException("General Exception @SMSDataMigrationServiceImpl.createResponseFromVibes() due to "+ e.getMessage());
		}
		return migartionVo;
	}

   

	/** To Call Add Participant API
   	 * @param request
   	 * @param url
   	 * @param header
   	 * @return
   	 * @throws BusinessServiceException
   	 */
   	private VibesResponseVO createResponseFromVibes(String request, String url, String header) throws BusinessServiceException{
   		
   		Map<String,String> parameterMap = null;
   		// construct parameter map from header.
   		if(StringUtils.isNotBlank(header)){
   			parameterMap  = getHttpHeaders(header);
   		}
   		VibesResponseVO addParticipantResponseVO =null;
   		try{
   			SimpleRestClient simpleRestClient = new SimpleRestClient(url,null,null,false);
   			// Calling postMethod in SimpleRestClient to call the web service.
   			addParticipantResponseVO = simpleRestClient.postMethodVibes(request, parameterMap);
   		}
   		catch (Exception e) {
   			logger.error("General Exception @SMSDataMigrationServiceImpl.createResponseFromVibes() in" + request + "due to"+ e.getMessage());
   			throw new BusinessServiceException("General Exception @SMSDataMigrationServiceImpl.createResponseFromVibes() due to "+ e.getMessage());
   		}
   		return addParticipantResponseVO;
   	}
    
   	/**@Description : construct parameter map
   	 * @param parameters
   	 * @return
   	 */
   	private Map<String,String> getHttpHeaders(String parameters){
		Map<String,String> map = new HashMap<String,String>();
		String[] splittedValue= parameters.split(",");
		
		for(int count = 0;count< splittedValue.length;count++){
			String value = splittedValue[count];
			String[] againSplitted = value.split(":");
			map.put(againSplitted[0], againSplitted[1]);
		}
		return map;
	}
   	
    /**@Description: Mapping sms no,first name and last name of resource to api request
     * @param migartionVo
     * @return
     */
    private String setApiParticipantRequest(SMSDataSynchVO migartionVo) {
    	AddParticipantAPIRequest addParticipantAPIRequest = new AddParticipantAPIRequest();
    	MobilePhone mobilePhone = new MobilePhone();
		mobilePhone.setMdn(migartionVo.getMdn());
		addParticipantAPIRequest.setMobile_phone(mobilePhone);
		CustomField customField = new CustomField();
		customField.setFirstName(migartionVo.getFirstName());
		customField.setLastName(migartionVo.getLastName());
		addParticipantAPIRequest.setCustom_field(customField);
		String addParticipantAPIReq = formJson(addParticipantAPIRequest);
		return addParticipantAPIReq;
	}

    /**@Description : forming json request to call add participant api in vibes
     * @param addParticipantAPIReq
     * @return
     */
    private String formJson(AddParticipantAPIRequest addParticipantAPIReq) {
		String addParticipantReqJson = null;
		JSON j = JSONSerializer.toJSON(addParticipantAPIReq);
		addParticipantReqJson  = j.toString();  
		return addParticipantReqJson;
	}
    /**@Description : Mapping response to vo 
     * @param response
     * @param migartionVo
     * @return
     * @throws BusinessServiceException 
     */
    private SMSDataSynchVO mapResponse(VibesResponseVO response,SMSDataSynchVO migartionVo) throws BusinessServiceException {
		if(null!= response && StringUtils.isNotBlank(response.getResponse())){
			JSONObject jsonObject;
			try{
				if(OrderConstants.STATUS_CODE_SUCCESS1== response.getStatusCode()|| OrderConstants.STATUS_CODE_SUCCESS2== response.getStatusCode()){
					jsonObject = (JSONObject)JSONSerializer.toJSON(response.getResponse());
					//Setting status as PENDING for add Participant
					migartionVo.setStatus(OrderConstants.PENDING_STATUS);
					migartionVo.setParticipationDateString(jsonObject.getString(OrderConstants.PARTICIPATION_DATE));
					migartionVo.setExpireDateString(jsonObject.getString(OrderConstants.EXPIRE_DATE));
			  		String person = jsonObject.getString(OrderConstants.PERSON);
			  		jsonObject = (JSONObject)JSONSerializer.toJSON(person);
			  		migartionVo.setPersonId(jsonObject.getString(OrderConstants.PERSON_ID));
		  		
				}else{
		  		    migartionVo.setErrorString(extractErrorMessage(response.getResponse()));
		  		}
				migartionVo.setModifiedBy(OrderConstants.MIGRATION_BATCH);
		  		migartionVo.setStatusCode(response.getStatusCode());
		  		migartionVo.setStatusText(response.getStatusText());
			}catch (Exception e) {
				logger.error("Exception in forming Response to JSON");
				throw new BusinessServiceException(e);
			}
		}
		return migartionVo;
	} 

	
	
	 /**@Description: Get all the market ready providers from the tmp table
		 * @return
		 * @throws BusinessServiceException
		 */
		public  List<SMSDataSynchVO> getRecordsForMigrationForSms() throws BusinessServiceException{
			List<SMSDataSynchVO> marketReadySmsVendorList=null;
			try{
				marketReadySmsVendorList = smsDataMigrationDao.getMarketReadySMSVendorsForSms(); 
			}
			catch (Exception e) {
				logger.error("Exception in SMSDataMigrationServiceImpl method getRecordsForMigrationForSms()" +e.getMessage());
				throw new BusinessServiceException("Exception in SMSDataMigrationServiceImpl method getRecordsForMigrationForSms()",e);
			}
			return marketReadySmsVendorList;
			
		}
		
	
	/**@Description: Get all the providers not in inactive/deleted from the sms_subscription table
	 * @return
	 * @throws BusinessServiceException
	 */
	public  List<SMSDataSynchVO> getSubscriptionDetailsForSms(String mdn) throws BusinessServiceException{
		List<SMSDataSynchVO> activeSMSVendors  =null;
		try{
			activeSMSVendors = smsDataMigrationDao.getActiveVendorsForSms(mdn);
		}
		catch (Exception e) {
			logger.error("Exception in SMSDataMigrationServiceImpl method getSubscriptionDetailsForSms()" +e.getMessage());
			throw new BusinessServiceException("Exception in SMSDataMigrationServiceImpl method getSubscriptionDetailsForSms()",e);
		}
		return activeSMSVendors;
		
	}
	
	/**@Description: Update the migration status of the resources
	 * @return
	 * @throws BusinessServiceException
	 */
	public void updateMigrationDetails(List<SMSDataSynchVO> toBeAddedResources,
			String status, String message) throws BusinessServiceException{
		try{
			if(null != toBeAddedResources && !toBeAddedResources.isEmpty()){
				for(SMSDataSynchVO resource : toBeAddedResources){
					if(null != resource){
						resource.setStatus(status);
						resource.setStatusText(message);
					}
				}
				smsDataMigrationDao.updateMigrationDetails(toBeAddedResources);
			}
		}
		catch (Exception e) {
			new BusinessServiceException("Exception in SMSDataMigrationDaoImpl method updateMigrationDetails()",e);
		}
	}
	
	
	
	/**@Description: Update the migration status of the resources
	 * @return
	 * @throws BusinessServiceException
	 */
	public void updateMigrationDetailsForSms(SMSDataSynchVO smsDataSynchVO,
			String status, String message) throws BusinessServiceException{
		try{
					if(null != smsDataSynchVO){
						smsDataSynchVO.setStatus(status);
						smsDataSynchVO.setStatusText(message);
						smsDataMigrationDao.updateMigrationDetailsForSms(smsDataSynchVO);
					}
				
		
		}
		catch (Exception e) {
			throw new BusinessServiceException("Exception in SMSDataMigrationServiceImpl method updateMigrationDetailsForSms()",e);
		}
	}
	
	
	/**@Description: Get the inactive subscription record for an SMS
	 * @return
	 * @throws BusinessServiceException
	 */
	public SMSDataSynchVO getInactiveSubscriptionDetail(String smsNo) throws BusinessServiceException{
		
		SMSDataSynchVO inactiveRecord = null;
		try{
			
			inactiveRecord = smsDataMigrationDao.getInactiveSubscriptionDetail(smsNo);
			
		}
		catch (Exception e) {
			throw new BusinessServiceException("Exception in SMSDataMigrationServiceImpl method getInactiveSubscriptionDetail()",e);
		}
		return inactiveRecord;
	}
    
    
    /**
     * @param line
     * @return
     * @throws BusinessServiceException
     */
    private String extractErrorMessage(String line) throws BusinessServiceException {
        JSONObject jsonObject;
  		StringBuilder sb= new StringBuilder();
  		try {
  			jsonObject = (JSONObject)JSONSerializer.toJSON(line);
  			JSONArray jsonArray = jsonObject.getJSONArray(OrderConstants.ERRORS);
  		 
  			for (int index = 0; index < jsonArray.length(); index++) {
  				jsonObject = jsonArray.getJSONObject(index);
  				if(jsonObject.containsKey(OrderConstants.MESSAGE)){
  					sb.append(jsonObject.getString(OrderConstants.MESSAGE)).append(OrderConstants.PIPE_DELIMITER);
  				}
  			}
  		}catch (Exception e) {
  			logger.error("General Exception @SMSDataMigrationServiceImpl.extractErrorMessage() due to"+ e.getMessage());
			throw new BusinessServiceException("General Exception @SMSDataMigrationServiceImpl.extractErrorMessage() due to "+ e.getMessage());
  		}
  		return sb.toString();
  	} 
    

	/**@Description: insert the subscription details in  sms_subscription table
	* @param resourceList
	* @throws BusinessServiceException
	*/
	public void insertSubscriptionDetails(List<SMSDataSynchVO> resourceList) throws BusinessServiceException {
		try{
			if(null!= resourceList && !resourceList.isEmpty()){
				smsDataMigrationDao.insertSubscriptionDetails(resourceList);
			}
		}
		catch (Exception e) {
	  		logger.error("General Exception @SMSDataMigrationServiceImpl.insertSubscriptionDetails() due to"+ e.getMessage());
			throw new BusinessServiceException("General Exception @SMSDataMigrationServiceImpl.insertSubscriptionDetails() due to "+ e.getMessage());
	  	}
	}
	
	
	/**@Description: insert the subscription details in  sms_subscription table
	* @param resourceList
	* @throws BusinessServiceException
	*/
	public void insertSubscriptionDetailsForSms(SMSDataSynchVO smsDataSynchVO) throws BusinessServiceException {
		try{
			if(null!= smsDataSynchVO){
				smsDataMigrationDao.insertSubscriptionDetailsForSms(smsDataSynchVO);
			}
		}
		catch (Exception e) {
	  		logger.error("General Exception @SMSDataMigrationServiceImpl.insertSubscriptionDetails() due to"+ e.getMessage());
			throw new BusinessServiceException("General Exception @SMSDataMigrationServiceImpl.insertSubscriptionDetails() due to "+ e.getMessage());
	  	}
	}
	

    
	public ISMSDataMigrationDao getSmsDataMigrationDao() {
		return smsDataMigrationDao;
	}

	public void setSmsDataMigrationDao(ISMSDataMigrationDao smsDataMigrationDao) {
		this.smsDataMigrationDao = smsDataMigrationDao;
	}

	public IMarketPlaceDao getiMarketPlaceDao() {
		return iMarketPlaceDao;
	}

	public void setiMarketPlaceDao(IMarketPlaceDao iMarketPlaceDao) {
		this.iMarketPlaceDao = iMarketPlaceDao;
	}
	

}
