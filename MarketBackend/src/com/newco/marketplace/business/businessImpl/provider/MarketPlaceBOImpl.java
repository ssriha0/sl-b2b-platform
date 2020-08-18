package com.newco.marketplace.business.businessImpl.provider;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.iBusiness.provider.IMarketPlaceBO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.exception.core.DataServiceException;

import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.provider.IActivityRegistryDao;
import com.newco.marketplace.persistence.iDao.provider.IContactDao;
import com.newco.marketplace.persistence.iDao.provider.ILookupDAO;
import com.newco.marketplace.persistence.iDao.provider.IMarketPlaceDao;
import com.newco.marketplace.utils.ActivityRegistryConstants;
import com.newco.marketplace.vo.provider.MarketPlaceVO;
import com.newco.marketplace.vo.vibes.AddParticipantAPIRequest;
import com.newco.marketplace.vo.vibes.VendorResourceSmsSubscription;
import com.newco.marketplace.vo.vibes.VibesResponseVO;
import com.newco.marketplace.vo.vibes.CustomField;
import com.newco.marketplace.vo.vibes.MobilePhone;
import com.servicelive.SimpleRestClient;


/**
 * @author Covansys - Offshore
 *
 * $Revision: 1.22 $ $Author: gjacks8 $ $Date: 2008/05/29 16:32:33 $
 */

/*
 * Maintenance History: See bottom of file
 */

public class MarketPlaceBOImpl extends ABaseBO implements IMarketPlaceBO {
	
	private static final Logger logger = Logger.getLogger(MarketPlaceBOImpl.class);
	private ILookupDAO iLookupDAO;
	private IMarketPlaceDao iMarketPlaceDao;
	private IActivityRegistryDao activityRegistryDao;
	private IContactDao iContactDao;

	/**
	 * @param iLookupDAO
	 * @param iMarketPlaceDao
	 * @param activityRegistryDao
	 * @param contactDao
	 */
	public MarketPlaceBOImpl(ILookupDAO iLookupDAO, 
							IMarketPlaceDao iMarketPlaceDao,
							IActivityRegistryDao activityRegistryDao,
							IContactDao contactDao) {
		this.iLookupDAO = iLookupDAO;
		this.iMarketPlaceDao = iMarketPlaceDao;
		this.activityRegistryDao = activityRegistryDao;
		iContactDao = contactDao;
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IMarketPlaceBO#loadMarketPlace(com.newco.marketplace.vo.provider.MarketPlaceVO, java.lang.String)
	 */
	public MarketPlaceVO loadMarketPlace(
			MarketPlaceVO marketPlaceVO, String provUsername)
			throws BusinessServiceException {

		List contactList = null;
		
		
		// checking username is exist or not
		MarketPlaceVO tempMarketPlaceVO = null;
		MarketPlaceVO providerMarketPlaceVO = null;
		MarketPlaceVO userMarketPlaceVO = null;
		MarketPlaceVO marketPlaceVO2 = null;
		String providerUserName = null;
		String userName = null;
		
		try {
			tempMarketPlaceVO = iMarketPlaceDao.getUserNameForResouceId(marketPlaceVO
					.getResourceID());
			if (tempMarketPlaceVO != null) {
				userName = tempMarketPlaceVO.getUserName();				
			}
			
			providerUserName = marketPlaceVO.getUserName();
			providerMarketPlaceVO = iMarketPlaceDao.getContactDetails(provUsername);
			userMarketPlaceVO = iMarketPlaceDao.getContactDetailsForResource(marketPlaceVO.getResourceID());
			
			if (userName != null && userName.trim().length() > 0) {
				marketPlaceVO.setEditEmailInd("false");
				marketPlaceVO.setUserName(userName);								
			} else {
				marketPlaceVO.setEditEmailInd("true");				
			}
			
			if (userMarketPlaceVO != null) {
				marketPlaceVO.setBusinessPhone(userMarketPlaceVO.getBusinessPhone());
				marketPlaceVO.setBusinessExtn(userMarketPlaceVO.getBusinessExtn());
				marketPlaceVO.setSmsAddress(userMarketPlaceVO.getSmsAddress());
				marketPlaceVO.setMobilePhone(userMarketPlaceVO.getMobilePhone());
				marketPlaceVO.setEmail(userMarketPlaceVO.getEmail());
				marketPlaceVO.setAltEmail(userMarketPlaceVO.getAltEmail());
				marketPlaceVO.setPrimaryContact(userMarketPlaceVO.getPrimaryContact());
				marketPlaceVO.setContactID(userMarketPlaceVO.getContactID());
				marketPlaceVO.setSecondaryContact1(userMarketPlaceVO.getSecondaryContact1());
			}
			
			if (Boolean.parseBoolean(marketPlaceVO.getEditEmailInd())){
				if (providerMarketPlaceVO != null) {
					//LMA...If the email was set from the userMarketPlaceVO...take that one not the provider's
					if (marketPlaceVO.getEmail() == null || marketPlaceVO.getEmail().length() <1 ){
						marketPlaceVO.setEmail(providerMarketPlaceVO.getEmail());
					}
					//END
					if (marketPlaceVO.getBusinessPhone() == null){
						marketPlaceVO.setBusinessPhone(providerMarketPlaceVO.getBusinessPhone());
						marketPlaceVO.setBusinessExtn(providerMarketPlaceVO.getBusinessExtn());
						marketPlaceVO.setSmsAddress(providerMarketPlaceVO.getSmsAddress());
						marketPlaceVO.setMobilePhone(providerMarketPlaceVO.getMobilePhone());
						//LMA...If the altEmail was set from the userMarketPlaceVO...take that one not the provider's
						if (marketPlaceVO.getAltEmail() == null || marketPlaceVO.getAltEmail().length() <1 ){
							marketPlaceVO.setAltEmail(providerMarketPlaceVO.getAltEmail());
						}
						//END
						marketPlaceVO.setPrimaryContact(providerMarketPlaceVO.getPrimaryContact());
						marketPlaceVO.setSecondaryContact1(providerMarketPlaceVO.getSecondaryContact1());
					}
				}
			}
						
			
		} catch (DBException ex) {
			logger
					.info("DB Exception @MarketPlaceBOImpl.loadMarketPlace() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"DB Exception @MarketPlaceBOImpl.loadMarketPlace() due to"
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @MarketPlaceBOImpl.loadMarketPlace() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @MarketPlaceBOImpl.loadMarketPlace() due to "
							+ ex.getMessage());
		}
		
		try {
			//Gets the Data related to the contacts
			marketPlaceVO.setEditorUserName(provUsername);
			marketPlaceVO = iMarketPlaceDao.getData(marketPlaceVO);
			
			//Loads the Contact List for the 'Primary Contact List' and 'Secondary Contact List'
			contactList = iLookupDAO.loadContactMethod();
			marketPlaceVO.setPrimaryContList(contactList);
			marketPlaceVO.setSecondaryContList(contactList);
			
			/**
			 * Added for 'Provider Admin' 
			 */
			marketPlaceVO2 = iMarketPlaceDao.getPrimaryIndicator(marketPlaceVO.getResourceID());
			
			if (marketPlaceVO2 != null)
				marketPlaceVO.setPrimaryIndicator(marketPlaceVO2.getPrimaryIndicator());
			else
				marketPlaceVO.setPrimaryIndicator("0");
			
		} catch (DBException  ex) {
			logger
					.info("DB Exception @MarketPlaceBOImpl.loadMarketPlace() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @MarketPlaceBOImpl.loadMarketPlace() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @MarketPlaceBOImpl.loadMarketPlace() due to "
							+ ex.getMessage());
		}
		return marketPlaceVO;
	}	
	
	/**
	 * Update provider mobile no
	 * @param resourceId
	 * @param mobileNo
	 * @return
	 * @throws BusinessServiceException
	 * @throws DuplicateUserException
	 */
	public boolean updateMobileNo(String resourceId, String mobileNo)
			throws BusinessServiceException {
		MarketPlaceVO tMPlaceVO = null;
		String contactId;
		try {
			tMPlaceVO = iMarketPlaceDao.getUserNameForResouceId(resourceId);
			if (tMPlaceVO != null) {
				contactId = tMPlaceVO.getContactID();
				iMarketPlaceDao.updateMobileNo(mobileNo, contactId);
			}
		} catch (DBException ex) {
			logger.info("DB Exception @MarketPlaceBOImpl.updateMobileNo() due to"
					+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @MarketPlaceBOImpl.updateMobileNo() due to"
					+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @MarketPlaceBOImpl.updateMobileNo() due to "
							+ ex.getMessage());
		}
		return true;
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.provider.IMarketPlaceBO#updateMarketPlace(com.newco.marketplace.vo.provider.MarketPlaceVO)
	 */
	public MarketPlaceVO updateMarketPlace(
			MarketPlaceVO marketPlaceVO)
			throws BusinessServiceException, DuplicateUserException {
		
			String userName = null;
			MarketPlaceVO tMPlaceVO = null;
			MarketPlaceVO userMarketPlaceVO = null;
			  
		try {
			tMPlaceVO = iMarketPlaceDao.getUserNameForResouceId(marketPlaceVO
						.getResourceID());
			if (tMPlaceVO!=null) {
				userName = tMPlaceVO.getUserName();
				marketPlaceVO.setContactID(tMPlaceVO.getContactID());
			}
			logger.info(" mktplacevo contact***********************"+marketPlaceVO.getContactID());
			if (userName != null && userName.trim().length() > 0) {
				marketPlaceVO.setUserName(userName);
				Contact findContact = new Contact();
				Contact returnContact = null;
				//MarketPlaceVO tempMarketPlaceVO2 = null;
				findContact.setUserName(userName);
				findContact.setEmail(marketPlaceVO.getEmail());
				findContact.setContactId(Integer.parseInt(marketPlaceVO.getContactID()));
				
				/**
				 * Commented for the Story:Enhancement-- Allow multiple user IDs to one email address [id=24957] 
				 */	
				
//				try {
//					returnContact = iContactDao.queryPvalidateEmailId(findContact);
//					
//					if(returnContact != null && (!returnContact.getContactId().equals(findContact.getContactId())))
//					{
//						
//						throw new DuplicateUserException("The EMAIL ID("
//								+ marketPlaceVO.getEmail()
//								+ ") has already been used.");
//					}
//					
//				} catch (DBException ex) {
//					throw ex;
//				}
				
			} /*LMA...REMOVED the else.  This is preventing the SAVE of the email addr of the primary 's email when
			the user is set up as a person that does not have a logon
			else {
				marketPlaceVO.setEmail(null);
			}*/
			
			//Starting of the Update Execution Transaction
			
			//Inserts SMS no to alert task for creating subscription
			userMarketPlaceVO = iMarketPlaceDao.getContactDetailsForResource(marketPlaceVO.getResourceID());
			boolean smsChangeInd = false;
			
			// adding switch for all the message triggering parts
			boolean vibesSwitch = iMarketPlaceDao.getVibesSwitch(OrderConstants.VIBES_SWITCH);
			//If secondaryContact is sms
			if(AlertConstants.ALT_CONTACT_SMS.equals(marketPlaceVO.getSecondaryContact1())){
				if((!(marketPlaceVO.getSmsAddress().equals(userMarketPlaceVO.getSmsAddress())))||(userMarketPlaceVO.getSecondaryContact1()== null)||(!AlertConstants.ALT_CONTACT_SMS.equals(userMarketPlaceVO.getSecondaryContact1()))){
					// if sms is changed or existing secondary contact is not sms
					marketPlaceVO.setFirstName(userMarketPlaceVO.getFirstName());
					marketPlaceVO.setLastName(userMarketPlaceVO.getLastName());
					if(vibesSwitch){
						//R16_1: SL-18979: Change of sms number
						if ((null!=marketPlaceVO)&& (null!=userMarketPlaceVO)&& 
								 (AlertConstants.ALT_CONTACT_SMS.equals(userMarketPlaceVO.getSecondaryContact1()))&& 							
								 (!(marketPlaceVO.getSmsAddress().equalsIgnoreCase(userMarketPlaceVO.getSmsAddress())))){
							//sms no changed
							marketPlaceVO = smsChange(marketPlaceVO, userMarketPlaceVO.getSmsAddress());
						}
						else {
							//adding a sms number, already sms number was not there
							//Calling add participant api of vibes if a sms number is used for first time
							marketPlaceVO = callAddParticipantVibes(marketPlaceVO,smsChangeInd);
						}
					}
					else{
						marketPlaceVO = iMarketPlaceDao.updateData(marketPlaceVO);
					}

				}
				
			}
			//Changing secondary contact from SMS, delete api of vibes to be called for deleting from subscription list
			else if (null!=userMarketPlaceVO.getSecondaryContact1() && AlertConstants.ALT_CONTACT_SMS.equals(userMarketPlaceVO.getSecondaryContact1())
					&& !(AlertConstants.ALT_CONTACT_SMS.equals(marketPlaceVO.getSecondaryContact1()))){
				//Updating changes in contact table
				marketPlaceVO = iMarketPlaceDao.updateData(marketPlaceVO);
				if(vibesSwitch){
					callDeleteSubscriptionVibes(marketPlaceVO.getResourceId(),userMarketPlaceVO.getSmsAddress(),smsChangeInd, marketPlaceVO.getLoggedInUserName());
				}
			}
			else{
				//for all the other cases contact table updation is handled earlier according to the response from Vibes
				//Updates all the new changes.
				marketPlaceVO = iMarketPlaceDao.updateData(marketPlaceVO);
			}
			if (StringUtils.isBlank(marketPlaceVO.getVibesError())) {
				//R16_1: SL-18979: Updating contact table is handled earlier according to the response from Vibes
				//Updates all the new changes.
				//marketPlaceVO = iMarketPlaceDao.updateData(marketPlaceVO);
				
				//Add/Update/Delete the User Permissions 
				UpdateActivity(marketPlaceVO);
				
				//Updates Activity Registry Table
				activityRegistryDao.updateResourceActivityStatus(marketPlaceVO.getResourceID(), 
																ActivityRegistryConstants.RESOURCE_MARKETPLACE);
			}
			
		} catch (DuplicateUserException de) {
			logger
			.info("DB Exception @MarketPlaceBOImpl.updateMarketPlace() due to"
					+ de.getMessage());
			throw de;
		} catch (DBException  ex) {
			logger
			.info("DB Exception @MarketPlaceBOImpl.updateMarketPlace() due to"
					+ ex.getMessage());
			throw new BusinessServiceException(ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @MarketPlaceBOImpl.updateMarketPlace() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @MarketPlaceBOImpl.updateMarketPlace() due to "
							+ ex.getMessage());
		}
		return marketPlaceVO;
	}
	
	private AlertTask getAlertTaskForSMS(MarketPlaceVO marketPlaceVO){

		AlertTask alertTask = new AlertTask();
		Date date = new Date();
		String templateInput = null;
		templateInput = AlertConstants.SMS_NO + AlertConstants.EQUALS + marketPlaceVO.getSmsAddress()+ AlertConstants.SMS_DELIMITOR + AlertConstants.FIRST_NAME + AlertConstants.EQUALS + marketPlaceVO.getFirstName()+AlertConstants.SMS_DELIMITOR + AlertConstants.LAST_NAME + AlertConstants.EQUALS +marketPlaceVO.getLastName();
		alertTask.setAlertedTimestamp(null); 
		alertTask.setAlertTypeId(AlertConstants.ALERT_TYPE_SMS);
		alertTask.setCreatedDate(date);
		alertTask.setModifiedDate(date);
		alertTask.setTemplateId(AlertConstants.TEMPLATE_SMS_SUBSCRIBE);
		alertTask.setAlertFrom(AlertConstants.SERVICE_LIVE_MAILID);
		alertTask.setAlertTo(marketPlaceVO.getSmsAddress());
		alertTask.setAlertBcc("");
		alertTask.setAlertCc("");
		alertTask.setCompletionIndicator(AlertConstants.INCOMPLETE_INDICATOR);
		alertTask.setPriority(AlertConstants.SMS_PRIORITY);
		alertTask.setTemplateInputValue(templateInput);
		return alertTask;
	}
	//method which calls 3rd party service for validating sms number
	//R16_1: SL-18979: Commenting out code since validating sms no is done through vibes call
	/*public boolean validateSmsNo(String number) {
		String smsUrl =System.getProperty("smsalert.provider.host");
		String path = AlertConstants.SMSALERT_PROVIDER_LOOKUP;
		String xmlExtn = AlertConstants.XML_EXTN;
		StringBuffer smsprovidermethod = new StringBuffer();
		smsprovidermethod.append(path);
		smsprovidermethod.append(number);
		smsprovidermethod.append(xmlExtn);
		String smsProviderSSLPort = System.getProperty("https.port");
		
		if (smsUrl == null || smsprovidermethod == null || smsProviderSSLPort == null) {
		
			return false;
		}
		
		HttpClient client = new HttpClient();
		HostConfiguration host = client.getHostConfiguration();
		Integer sslPort = Integer.valueOf(smsProviderSSLPort);

		try{
			host.setHost(smsUrl,sslPort.intValue(), new Protocol(
		               "https",
		               (ProtocolSocketFactory) new EasySSLProtocolSocketFactory(),
		               sslPort.intValue()));
			
			String proxyEnabled=System.getProperty("http.proxy.set");
            if(!(proxyEnabled.equalsIgnoreCase(null))&& proxyEnabled.equals("true"))
            {
			String portValue=System.getProperty("http.proxy.port");
			int port=0;
			if(portValue!=null){
				try{
					port=Integer.parseInt(portValue);
				}catch(NumberFormatException e){
			    	logger.info("Exception Occured :"+ e);
			    	return false;
				}
			}
			host.setProxy(System.getProperty("http.proxy.host"), port);
            }
		    
		    PostMethod method = new PostMethod(smsUrl + smsprovidermethod.toString());
		    
		    try {
		    	
				method.addRequestHeader(CommonConstants.SMSALERT_PROVIDER_HEADER_NAME, System
						.getProperty("smsalert.provider.header.value"));
				
				int returnCode = client.executeMethod(method);

				if(returnCode == HttpStatus.SC_OK){
					logger.info("Valid number");
					
					return true;
				
				}else{
					logger.info("Invalid number");
					return false;
				}
	   		}
		    catch (IllegalArgumentException e) {
		    	logger.info("Exception Occured :"+ e);
		    	return false;
				
			}finally {
				method.releaseConnection();
			}
						
		}
		catch (Exception e) {
			logger.info("Exception Occured :"+ e);
	    	
		}
		return false;
	}*/
	
	private void UpdateActivity(MarketPlaceVO marketPlaceVO) throws BusinessServiceException
	{
		List<UserActivityVO> activiList = marketPlaceVO.getActivityList();
		logger.info("----------> Updating activities " + activiList);

		try
		{
			for (int count = 0; count < activiList.size(); count++)
				{
				logger.info("----------> Updating activity " + activiList.get(count));
					UserActivityVO myVo = activiList.get(count);
					if (myVo.isChecked())
					{
						logger.info(" inserting activity " + activiList.get(count));
						
						iMarketPlaceDao.insertActivity(myVo);
	 					
					}else
					{
						logger.info(" deleting activity " + activiList.get(count));
						logger.info("inside the delete****************************");
						
						iMarketPlaceDao.deleteActivity(myVo);
					}

				
			}
		}catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @MarketPlaceBOImpl.updateMarketPlace() due to"
							+ ex.getMessage());
			throw new BusinessServiceException(
					"General Exception @MarketPlaceBOImpl.updateMarketPlace() due to "
							+ ex.getMessage());
		}
	}
	
	//R16_1: SL-18979: method to call add participant api in vibes
    private MarketPlaceVO callAddParticipantVibes(
            MarketPlaceVO providerRegistrationVO, boolean smsChangedInd) throws BusinessServiceException
            {
    	String response = null;
		String request = null;
		Integer vendorId = null;
		List<Integer> vendorIdListSameSMS = null;
    	try{
    		//Fetching vendor_id for that resource
    		vendorId = iMarketPlaceDao.getVendorId(providerRegistrationVO.getResourceId());
    		if (null!=vendorId){
    			//Fetching vendorIds for which that sms number exists with status not as DELETED,INACTIVE
    			vendorIdListSameSMS = iMarketPlaceDao.fetchVendorIdsforSmsNo(providerRegistrationVO.getSmsAddress());
    			if (null!=vendorIdListSameSMS && !(vendorIdListSameSMS.isEmpty())){
    				//sms number already exists
    				providerRegistrationVO = copySmsSubscriptionDetails(providerRegistrationVO, vendorIdListSameSMS, vendorId,smsChangedInd);
    			}
        		else{
        			// sms number not shared
        			 Map <String, String> apiDetails = fetchAddParticipantApiDetails(providerRegistrationVO);
            		 VibesResponseVO addParticipantResponseVO = null;
                     if(null != apiDetails){
         				String url = apiDetails.get(OrderConstants.URL);
         				request = apiDetails.get(OrderConstants.REQUEST);
         				String header = apiDetails.get(OrderConstants.HEADER);
         				try{
         					addParticipantResponseVO = createResponseFromVibes(request, url, header);
         				}
         				catch (BusinessServiceException e){
         		    		logger
         					.error("General Exception @MarketPlaceBOImpl.callAddParticipantVibes() due to"
         							+ e.getMessage());
         		    		providerRegistrationVO.setVibesError(OrderConstants.MOBILE_NO_GENERIC_ERROR);
         		    	}
         				if(null != addParticipantResponseVO){
        					response = addParticipantResponseVO.getResponse();
        					providerRegistrationVO.setVibesStatusCode(addParticipantResponseVO.getStatusCode());
        					//handling response from Add participant api
        					providerRegistrationVO = handleAddParticipantResponse(providerRegistrationVO, smsChangedInd, response);	
        				}
                     }
        		}
    		}
    	
    	}
    	catch (Exception e) {
			logger
			.error("General Exception @MarketPlaceBOImpl.callAddParticipantVibes() due to"
					+ e.getMessage());
			throw new BusinessServiceException(
			"General Exception @MarketPlaceBOImpl.callAddParticipantVibes() due to "
					+ e.getMessage());
		}
    		return providerRegistrationVO; 
              
            }
              
    /**R16_1: SL-18979: fetching details from application_constants to call add participant/delete subscriber api in vibes
     * @param apiName
     * @return
     * @throws BusinessServiceException 
     */
    private Map<String,String> fetchApiDetails(String apiName) throws BusinessServiceException{
    	 List<String> apiDetailsNeeded = new ArrayList<String>();
    	 //Common constants for add and delete api's
		  apiDetailsNeeded.add(OrderConstants.COMPANY_ID);
		  apiDetailsNeeded.add(OrderConstants.HEADER);
		  //Constants needed to call Add participant API
		  if(apiName.equalsIgnoreCase(OrderConstants.ADD)){
			  apiDetailsNeeded.add(OrderConstants.ADD_PARTICIPANT_API_URL);
			  apiDetailsNeeded.add(OrderConstants.ACQUISITION_CAMPAIGN_ID);
		  }
		//Constants needed to call Delete subscription API
		  else if (apiName.equalsIgnoreCase(OrderConstants.DELETE)){
			  apiDetailsNeeded.add(OrderConstants.DELETE_SUBSCRIBER_API_URL);
			  apiDetailsNeeded.add(OrderConstants.SUBSCRIPTION_ID);
		  }
		Map<String, String> apiDetailsFromDb;
		try {
			apiDetailsFromDb = iMarketPlaceDao.fetchApiDetails(apiDetailsNeeded); 
		}
		catch (Exception e) {
			logger
			.error("General Exception @MarketPlaceBOImpl.fetchApiDetails() due to"
					+ e.getMessage());
			throw new BusinessServiceException(
			"General Exception @MarketPlaceBOImpl.fetchApiDetails() due to "
					+ e.getMessage());
		}
		  return apiDetailsFromDb;
    }

//R16_1: SL-18979: fetching details to call add participant api in vibes
	private Map <String, String> fetchAddParticipantApiDetails(MarketPlaceVO providerRegistrationVO) throws BusinessServiceException{
    	  Map <String,String> apiDetails = new HashMap<String, String>();
    	  Map<String, String> apiDetailsFromDb;
		try {
			apiDetailsFromDb = fetchApiDetails(OrderConstants.ADD);
		    if (null!= apiDetailsFromDb){
		    	//forming url
			  String url = apiDetailsFromDb.get(OrderConstants.ADD_PARTICIPANT_API_URL);
			  url = url.replace(OrderConstants.URL_COMPANY_ID, apiDetailsFromDb.get(OrderConstants.COMPANY_ID));
			  url = url.replace(OrderConstants.URL_ACQUISITION_CAMPAIGN_ID, apiDetailsFromDb.get(OrderConstants.ACQUISITION_CAMPAIGN_ID));
			  apiDetails.put(OrderConstants.URL,url);
			  //forming header
			  apiDetails.put(OrderConstants.HEADER, apiDetailsFromDb.get(OrderConstants.HEADER));
			  //forming request
			  AddParticipantAPIRequest addParticipantAPIRequest = new AddParticipantAPIRequest();
			  if (null!=providerRegistrationVO){
				  MobilePhone mobilePhone = new MobilePhone();
				  mobilePhone.setMdn(providerRegistrationVO.getSmsAddress());
				  addParticipantAPIRequest.setMobile_phone(mobilePhone);
				  CustomField customField = new CustomField();
				  customField.setFirstName(providerRegistrationVO.getFirstName());
				  customField.setLastName(providerRegistrationVO.getLastName());
				  addParticipantAPIRequest.setCustom_field(customField);
				  String addParticipantAPIReq = formJson(addParticipantAPIRequest);
				  apiDetails.put(OrderConstants.REQUEST,addParticipantAPIReq);
			  }
			 
		  }
		}
		catch (Exception e) {
			logger
			.error("General Exception @MarketPlaceBOImpl.fetchAddParticipantApiDetails() due to"
					+ e.getMessage());
			throw new BusinessServiceException(
			"General Exception @MarketPlaceBOImpl.fetchAddParticipantApiDetails() due to "
					+ e.getMessage());
		}
		  return apiDetails;
      }

	//R16_1: SL-18979: forming json request to call add participant api in vibes
      private String formJson(AddParticipantAPIRequest addParticipantAPIReq) {
  		// TODO Auto-generated method stub
  		String addParticipantReqJson = null;
  		JSON j = JSONSerializer.toJSON(addParticipantAPIReq);
  		addParticipantReqJson  = j.toString();  
  		return addParticipantReqJson;
  	}
      
    //R16_1: SL-18979: saving response details of add participant api of vibes
      private void saveResponseAddParticipantApiDetails(String response, String resourceId, String smsNumber, String loggedInUserName) throws BusinessServiceException {
    	JSONObject jsonObject;
  		VendorResourceSmsSubscription addParticipantResponse = new VendorResourceSmsSubscription();
  		// Extracting fields from Json response
  		try {
  		jsonObject = (JSONObject)JSONSerializer.toJSON(response);
  		
  		addParticipantResponse.setStatus(OrderConstants.PENDING_STATUS);
  		addParticipantResponse.setParticipationDate(jsonObject.getString(OrderConstants.PARTICIPATION_DATE));
  		addParticipantResponse.setExpireDate(jsonObject.getString(OrderConstants.EXPIRE_DATE));
  		String person = jsonObject.getString(OrderConstants.PERSON);
  		jsonObject = (JSONObject)JSONSerializer.toJSON(person);
  		addParticipantResponse.setPersonId(jsonObject.getString(OrderConstants.PERSON_ID));
  		addParticipantResponse.setResourceId(resourceId);
  		addParticipantResponse.setSmsNumber(smsNumber);
  		addParticipantResponse.setModifiedBy(loggedInUserName);
		addParticipantResponse.setOptInInd(null);
		addParticipantResponse.setOptInDate(null);
		addParticipantResponse.setOptOutDate(null);
		
  		 // Saving response details in vendor_resource_sms_subscription table
  		// If already record exists for provider, it is updated, else new record is inserted
  		if (null!= iMarketPlaceDao.getSmsSubscriptionDetailsForResource(resourceId)){
  				//Updating the subscription details the resource had earlier
  	  			iMarketPlaceDao.updateAddParticipantApiResponse(addParticipantResponse);
  	  		}
  			else {
  				//Inserting new record
  				addParticipantResponse.setCreatedBy(loggedInUserName);
  				iMarketPlaceDao.insertAddParticipantApiResponse(addParticipantResponse);
  			}
  		
  		} catch (Exception e) {
  			logger
			.error("General Exception @MarketPlaceBOImpl.saveResponseAddParticipantApiDetails() due to"
					+ e.getMessage());
			throw new BusinessServiceException(
			"General Exception @MarketPlaceBOImpl.saveResponseAddParticipantApiDetails() due to "
					+ e.getMessage());
  		}
  	}
      
  	/** R16_1: SL-18979: Add Participant API of vibes returned a response of 409, very unlikely scenario
       * @param resourceId
       * @param smsAddress
       * @param loggedInUserName
       * @throws BusinessServiceException 
       */
      private MarketPlaceVO processConflictError(MarketPlaceVO marketPlaceVO, boolean smsChangedInd) throws BusinessServiceException {
      	VendorResourceSmsSubscription resourceSmsSubscription = new VendorResourceSmsSubscription();
      	VendorResourceSmsSubscription existingSubscription = null;
  
      	try{
      		resourceSmsSubscription.setStatus(OrderConstants.ACTIVE_RECORD);
  			resourceSmsSubscription.setOptInInd(OrderConstants.ACTIVE_IND);
  			resourceSmsSubscription.setSmsNumber(marketPlaceVO.getSmsAddress());
  			resourceSmsSubscription.setResourceId(marketPlaceVO.getResourceId());
      		resourceSmsSubscription.setModifiedBy(marketPlaceVO.getLoggedInUserName());
   
      			//Checking sms number exists in vendor_resource_sms_subscription. 
      			existingSubscription = iMarketPlaceDao.getSmsSubscriptionDetailsForSmsNo(marketPlaceVO.getSmsAddress());
      			if (null != existingSubscription){
          			//Copying the details from the existing latest record 
      				resourceSmsSubscription.setPersonId(existingSubscription.getPersonId());
      				resourceSmsSubscription.setParticipationDate(existingSubscription.getParticipationDate());
      				resourceSmsSubscription.setExpireDate(existingSubscription.getExpireDate());
      				resourceSmsSubscription.setOptInDate(existingSubscription.getOptInDate());
      				resourceSmsSubscription.setOptOutDate(existingSubscription.getOptOutDate());
      				if (!smsChangedInd){
      					if (null!= iMarketPlaceDao.getSmsSubscriptionDetailsForResource(marketPlaceVO.getResourceId())){
          					//Updating changes in contact table
          					marketPlaceVO = iMarketPlaceDao.updateData(marketPlaceVO);
          	  				//Updating the subscription details the resource had earlier
          	  	  			iMarketPlaceDao.updateAddParticipantApiResponse(resourceSmsSubscription);
          	  	  		}
          	  			else {
          	  				//Inserting new record
          	  				resourceSmsSubscription.setCreatedBy(marketPlaceVO.getLoggedInUserName());
          	  				//Updating changes in contact table
          					marketPlaceVO = iMarketPlaceDao.updateData(marketPlaceVO);
          	  				//inserting new record in vendor_resource_sms_subscription
          	  				iMarketPlaceDao.insertAddParticipantApiResponse(resourceSmsSubscription);      	  		
          	  				}
      				}
      				else{
      					//sms change, saving details to save in db after delete api is called
      					marketPlaceVO.setSmsSubscription(resourceSmsSubscription);
      				}
      				
      			}
      			// else(sms no does not exist in vendor_resource_sms_subscription)
      			//show generic error message
      			else{
      				marketPlaceVO.setVibesError(OrderConstants.MOBILE_NO_GENERIC_ERROR2);
      			}
      		
      	}
      	catch (Exception e) {
    			logger
  			.error("General Exception @MarketPlaceBOImpl.processConflictError() due to"
  					+ e.getMessage());
  			throw new BusinessServiceException(
  			"General Exception @MarketPlaceBOImpl.processConflictError() due to "
  					+ e.getMessage());
    		}
      	return marketPlaceVO;
    	}
      
      //R16_1: SL-18979: extracting error response details of add participant api in vibes
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
  			logger
			.error("General Exception @MarketPlaceBOImpl.extractErrorMessage() due to"
					+ e.getMessage());
			throw new BusinessServiceException(
			"General Exception @MarketPlaceBOImpl.extractErrorMessage() due to "
					+ e.getMessage());
  		}
  		return sb.toString();
  		} 
      
      
  	/**R16_1: SL-18979: To call Delete Subscriber API of vibes
  	 * @param resourceId, smsAddress, smsChangeFlag, userName
  	 * @throws BusinessServiceException 
  	 */
      private boolean callDeleteSubscriptionVibes(String resourceId, String smsAddress, boolean smsChangeFlag, String loggedInUserName) throws BusinessServiceException {
    	  int statusCode = 0;
    	  Map <String,String> apiDetails = null;
    	  boolean errorOccured = false;
    	  boolean deleteFlag =false;

    	  try{
    		  // check if sms number exists for duplicate resources
    		  boolean ifDuplicateOccur = checkForSMSNumDuplicates(smsAddress);
    		  if(!ifDuplicateOccur){
    			  // delete the subscriber only if there are no duplicates
    			  //fetching details from db to call Delete Subscription API
    			  apiDetails = fetchDeleteSubscriptionApiDetails(resourceId);

    			  VibesResponseVO responseVO = null;
    			  if (null!=apiDetails){
    				  String url = apiDetails.get(OrderConstants.URL);
    				  String header = apiDetails.get(OrderConstants.HEADER);
    				  //calling Delete Subscription api of vibes
    				  responseVO = deleteMethodVibes(url, header);
    				  if(null!=responseVO){
    					  statusCode = responseVO.getStatusCode();
    				  }
    				  //flag to indicate whether deletion was success/not
    				  //Delete Subscription API returned Success Response
    				  if (OrderConstants.STATUS_CODE_DELETED == statusCode || OrderConstants.STATUS_CODE_NOT_FOUND == statusCode){
    					  //Updating vendor_resource_sms_subscription
    					  deleteFlag = true;
    					  saveResponseDeleteSubscriptionApiDetails(resourceId,smsAddress,deleteFlag,loggedInUserName);
    				  }
    				  else{
    					  if (smsChangeFlag){
    						  //Show error message if change of sms no occurs
    						  errorOccured = true;
    					  }
    					  else{
    						  //allow user to continue if secondary contact method is changed to email
    						  saveResponseDeleteSubscriptionApiDetails(resourceId,smsAddress,deleteFlag,loggedInUserName);
    					  }

    				  }
    			  }
    		  }else{
				  //allow user to continue and update the corresponding resource sms subscription status as DELETED
				  deleteFlag = true;
    			  saveResponseDeleteSubscriptionApiDetails(resourceId,smsAddress,deleteFlag,loggedInUserName);
    		  }

    	  }
    	  catch (Exception e) {
    		  logger
    		  .error("General Exception @MarketPlaceBOImpl.callDeleteSubscriptionVibes() due to"
    				  + e.getMessage());
    		  throw new BusinessServiceException(
    				  "General Exception @MarketPlaceBOImpl.callDeleteSubscriptionVibes() due to "
    						  + e.getMessage());
    	  }
    	  return errorOccured;
      }
      

    
    /**
     * @param smsAddress
     * @return
     * check if sms number is subscribed for multiple resources
     */
    private boolean checkForSMSNumDuplicates(String smsAddress) throws DataServiceException {
		// TODO Auto-generated method stub
		return iMarketPlaceDao.checkForSMSNumDuplicates(smsAddress);
	}

	/**R16_1: SL-18979: Fetching Details to call Delete Subscription API of vibes
     * @param resourceId
     * @return
     * @throws BusinessServiceException
     */
    private Map <String, String> fetchDeleteSubscriptionApiDetails(String resourceId) throws BusinessServiceException{
  	  Map <String,String> apiDetails = null;
  	  Map<String, String> apiDetailsFromDb;
  	  String personId = null;
		try {
			//fetching details from application_constants
			apiDetailsFromDb = fetchApiDetails(OrderConstants.DELETE);
		    if (null!= apiDetailsFromDb){
		    //forming the url
			  String url = apiDetailsFromDb.get(OrderConstants.DELETE_SUBSCRIBER_API_URL);
			  url = url.replace(OrderConstants.URL_COMPANY_ID, apiDetailsFromDb.get(OrderConstants.COMPANY_ID));
			  url = url.replace(OrderConstants.URL_SUBSCRIPTION_ID, apiDetailsFromDb.get(OrderConstants.SUBSCRIPTION_ID));
			  //Get person_id from vendor_resource_sms_subscription
			  personId = iMarketPlaceDao.getPersonIdForResource(resourceId);
			  if(StringUtils.isNotBlank(personId)){
				  apiDetails=  new HashMap<String, String>();
				  url = url.replace(OrderConstants.URL_PERSON_ID, personId);
				  apiDetails.put(OrderConstants.URL,url);
				  //header(username & password)
				  apiDetails.put(OrderConstants.HEADER, apiDetailsFromDb.get(OrderConstants.HEADER));
			  }
			  } 
		  }
		catch (Exception e) {
			logger
			.error("General Exception @MarketPlaceBOImpl.fetchAddParticipantApiDetails() due to"
					+ e.getMessage());
			throw new BusinessServiceException(
			"General Exception @MarketPlaceBOImpl.fetchAddParticipantApiDetails() due to "
					+ e.getMessage());
		}
		  return apiDetails;
    }
    
    /**R16_1: SL-18979: Saving Delete Subscription API details of vibes to vendor_resource_sms_subscription
     * @param status 
     * @param resourceId, smsAddress
     * @return
     * @throws BusinessServiceException
     */
    private void saveResponseDeleteSubscriptionApiDetails(String resourceId,
			String smsAddress, boolean deleteFlag, String userName) throws BusinessServiceException {
    	VendorResourceSmsSubscription resourceSmsSubscription = new VendorResourceSmsSubscription();
    	try{
    		//Deleted if subscription in vibes is deleted
    		if(deleteFlag){
    			resourceSmsSubscription.setStatus(OrderConstants.DELETED_CAPS);
    		}
    		//Made inactive if deletion in vibes is unsuccessful
    		else {
    			resourceSmsSubscription.setStatus(OrderConstants.INACTIVE);
    		}
			resourceSmsSubscription.setSmsNumber(smsAddress);
			resourceSmsSubscription.setResourceId(resourceId);
    		resourceSmsSubscription.setModifiedBy(userName);
    	
    		//updating in vendor_resource_sms_subscription
    		iMarketPlaceDao.deleteVibesDetails(resourceSmsSubscription);
    		
	}
    	catch (Exception e) {
			logger
			.error("General Exception @MarketPlaceBOImpl.saveResponseDeleteSubscriptionApiDetails() due to"
					+ e.getMessage());
			throw new BusinessServiceException(
			"General Exception @MarketPlaceBOImpl.saveResponseDeleteSubscriptionApiDetails() due to "
					+ e.getMessage());
		}
    }
    
    /** R16_1: SL-18979: Handling change of sms number scenario
     * @param marketPlaceVO
     * @param oldSmsAddress
     * @return
     * @throws BusinessServiceException
     */
    private MarketPlaceVO smsChange(MarketPlaceVO marketPlaceVO,String oldSmsAddress) throws BusinessServiceException{
    	
		boolean smsChangeInd =true;
		boolean errorOccuredinDelete = false;
		try{
			//call add participant for the new no
			marketPlaceVO = callAddParticipantVibes(marketPlaceVO,smsChangeInd);
			if(StringUtils.isBlank(marketPlaceVO.getVibesError())){
				//call delete subscription for the old no, if add participant does not have error
				errorOccuredinDelete = callDeleteSubscriptionVibes(marketPlaceVO.getResourceId(),oldSmsAddress,smsChangeInd,marketPlaceVO.getLoggedInUserName());
				if (errorOccuredinDelete){
					//mobile no cannot be updated since deletion of existing no was not success
					marketPlaceVO.setVibesError(OrderConstants.MOBILE_NO_GENERIC_ERROR);
				}
				else {
					int statusCode = marketPlaceVO.getVibesStatusCode();
					//copying details from an existing resource scenario
					VendorResourceSmsSubscription smsSubscription = marketPlaceVO.getSmsSubscription();
					if(null!= smsSubscription && statusCode == 0){
						//Already the sms no exists for same vendor, add participant api() is not called, so status code will be 0
						marketPlaceVO = saveAddParticipantSmsChange(marketPlaceVO,smsSubscription);
					}
					else{
						//save add participant api response in db
						if ((OrderConstants.STATUS_CODE_SUCCESS1 == statusCode || OrderConstants.STATUS_CODE_SUCCESS2 == statusCode) && StringUtils.isNotBlank(marketPlaceVO.getVibesResponse())){
								//Updating changes in contact table
								marketPlaceVO = iMarketPlaceDao.updateData(marketPlaceVO);
								//Updating vendor_resource_sms_subscription
								saveResponseAddParticipantApiDetails(marketPlaceVO.getVibesResponse(),marketPlaceVO.getResourceId(),marketPlaceVO.getSmsAddress(),marketPlaceVO.getLoggedInUserName());
							}
						//If status is 409, Conflict error- Provider already in subscription list
						else if (OrderConstants.STATUS_CODE_ERROR409 == statusCode){
							if(null!=smsSubscription){
								marketPlaceVO = saveAddParticipantSmsChange(marketPlaceVO,smsSubscription);
							}
						}
						else{
							//very unlikely scenario
							marketPlaceVO.setVibesError(OrderConstants.MOBILE_NO_GENERIC_ERROR);
						}
					}
					
				}
			}			
		}
	
		catch (Exception e) {
			logger
			.error("General Exception @MarketPlaceBOImpl.smsChange() due to"
					+ e.getMessage());
			throw new BusinessServiceException(
			"General Exception @MarketPlaceBOImpl.smsChange() due to "
					+ e.getMessage());
		}
		return marketPlaceVO;
    }	
    
    /** R16_1: SL-18979: Handling Add participant response
     * @param providerRegistrationVO
     * @param smsChangedInd
     * @param response
     * @return
     * @throws BusinessServiceException
     */
    private MarketPlaceVO handleAddParticipantResponse(MarketPlaceVO providerRegistrationVO, boolean smsChangedInd, String response) throws BusinessServiceException{
    	String message = null;
    	int statusCode = providerRegistrationVO.getVibesStatusCode();
    	try{
    		//If Status is Ok, then response data to be stored in vendor_resource_sms_subscription for each providers. 
    		//The history details to be inserted in vendor_resource_sms_subscription_history table.
    		if ((OrderConstants.STATUS_CODE_SUCCESS1 == statusCode || OrderConstants.STATUS_CODE_SUCCESS2 == statusCode) && StringUtils.isNotBlank(response)){
    			//In sms change scenario, db update for add participant is done only after delete subscription is called
    			if (!smsChangedInd){
    				//Updating changes in contact table
    				providerRegistrationVO = iMarketPlaceDao.updateData(providerRegistrationVO);
    				//Updating vendor_resource_sms_subscription
    				saveResponseAddParticipantApiDetails(response,providerRegistrationVO.getResourceId(),providerRegistrationVO.getSmsAddress(),providerRegistrationVO.getLoggedInUserName());
    			}
    			else{
    				//add details in vo class for db update
    				providerRegistrationVO.setVibesResponse(response);
    			}
    			
    		}
    		//If status is 409, Conflict error- Provider already in subscription list (very unlikely scenario)
    		else if (OrderConstants.STATUS_CODE_ERROR409 == statusCode){
    			//In sms change scenario, db update for add participant is done only after delete subscription is called
    	
    				//Updating vendor_resource_sms_subscription
    				providerRegistrationVO = processConflictError(providerRegistrationVO,smsChangedInd);
    		
    		}
    		//Setting error messages
    		//Invalid Mobile No error
    		else if (OrderConstants.STATUS_CODE_ERROR422 == statusCode && StringUtils.isNotBlank(response)){
    			message = extractErrorMessage(response);
    			if (StringUtils.isNotBlank(message) && (message.contains(OrderConstants.INVALID_MOBILE2) 
    					|| message.contains(OrderConstants.INVALID_MOBILE3) || message.contains(OrderConstants.INVALID_MOBILE4))){
    				providerRegistrationVO.setVibesError(OrderConstants.MOBILE_NO_INVALID);
    			}
    			else {
    				providerRegistrationVO.setVibesError(OrderConstants.MOBILE_NO_GENERIC_ERROR);
    			}
    		}
    		else {
    			providerRegistrationVO.setVibesError(OrderConstants.MOBILE_NO_GENERIC_ERROR);
    		}
        }
    	catch (Exception e) {
			logger
			.error("General Exception @MarketPlaceBOImpl.handleAddParticipantResponse() due to"
					+ e.getMessage());
			throw new BusinessServiceException(
			"General Exception @MarketPlaceBOImpl.handleAddParticipantResponse() due to "
					+ e.getMessage());
		}
    	return providerRegistrationVO;
    	}
    
    
    /**R16_1: SL-18979: Copying sms subscription details
     * @param providerRegistrationVO
     * @param vendorIdList
     * @param vendorId
     * @return
     * @throws BusinessServiceException
     */
    private MarketPlaceVO copySmsSubscriptionDetails(MarketPlaceVO providerRegistrationVO, List<Integer> vendorIdList, Integer vendorId, boolean smsChangedInd) 
    		throws BusinessServiceException{

    	try{
    		//sms no already exists
    		for (Integer firmId:vendorIdList){
    			if (!(firmId.equals(vendorId))){
    				//sms no exists for another vendor
    				providerRegistrationVO.setVibesError(OrderConstants.MOBILE_NO_EXISTS_ERROR);
    				break;
    			}
    		}
    		if (StringUtils.isBlank(providerRegistrationVO.getVibesError())){
    			//sms no exists for provider under the same firm, copy the existing details
    			VendorResourceSmsSubscription smsSubscription = null;
    			smsSubscription = iMarketPlaceDao.getSmsSubscriptionDetails(providerRegistrationVO.getSmsAddress());
      			if (null != smsSubscription){
          			//Copying the details from the existing latest record 
      				smsSubscription.setSmsNumber(providerRegistrationVO.getSmsAddress());
      				smsSubscription.setResourceId(providerRegistrationVO.getResourceId());
      				smsSubscription.setModifiedBy(providerRegistrationVO.getLoggedInUserName());
      				if (!smsChangedInd){
      				//Updating changes in contact table
          				providerRegistrationVO = iMarketPlaceDao.updateData(providerRegistrationVO);
          				if (null!= iMarketPlaceDao.getSmsSubscriptionDetailsForResource(providerRegistrationVO.getResourceId())){
          	  				//Updating the subscription details the resource had earlier
          	  	  			iMarketPlaceDao.updateAddParticipantApiResponse(smsSubscription);
          	  	  		}
          	  			else {
          	  				//Inserting new record
          	  				smsSubscription.setCreatedBy(providerRegistrationVO.getLoggedInUserName());
          	  				iMarketPlaceDao.insertAddParticipantApiResponse(smsSubscription);
          	  			}
      				}
      				else{
      					providerRegistrationVO.setSmsSubscription(smsSubscription);
      				}
      				
      			}
    		}
    	}
    	catch (Exception e) {
			logger
			.error("General Exception @MarketPlaceBOImpl.copySmsSubscriptionDetails() due to"
					+ e.getMessage());
			throw new BusinessServiceException(
			"General Exception @MarketPlaceBOImpl.copySmsSubscriptionDetails() due to "
					+ e.getMessage());
		}
    		return providerRegistrationVO; 
    }
    
    /** To Call Add Participant API
	 * @param request
	 * @param url
	 * @param header
	 * @return
	 * @throws BusinessServiceException
	 */
	public VibesResponseVO createResponseFromVibes(String request, String url, String header) throws BusinessServiceException{
		
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
			logger.error("General Exception @MarketPlaceBOImpl.createResponseFromVibes() in" + request + "due to"
					+ e.getMessage());
			throw new BusinessServiceException(
			"General Exception @VibesAPIClient.createResponseFromVibes() due to "
					+ e.getMessage());
		}
		return addParticipantResponseVO;
	}
	
	//Calling delete participant api of vibes
	public VibesResponseVO deleteMethodVibes(String url, String header){
		
		Map<String,String> parameterMap = null;
		// construct parameter map from header.
		if(StringUtils.isNotBlank(header)){
			parameterMap  = getHttpHeaders(header);
		}
		VibesResponseVO responseVO = null;
		try{
			SimpleRestClient simpleRestClient = new SimpleRestClient(url,null,null,false);
			// Calling postMethod in SimpleRestClient to call the web service.
			responseVO = simpleRestClient.deleteMethodVibes(parameterMap);
			
			if(null!=responseVO){
				logger.info("Delete Subscription API Response code for"+ url + "is::::"+responseVO.getStatusCode());
	            logger.info("Delete Subscription API Response text for"+ url + "is::::"+responseVO.getStatusText());
			}
		}
		catch (Exception e) {
			logger.error("General Exception @MarketPlaceBOImpl.deleteMethodVibes() in "+ url + "due to"
					+ e.getMessage());
		} 
		return responseVO;
	}
	//construct parameter map
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
		
		/**R16_1: SL-18979: Saving Add participant API details in case of copying from already existing record
		 * @param marketPlaceVO
		 * @param smsSubscription
		 * @return
		 * @throws BusinessServiceException
		 */
		private MarketPlaceVO saveAddParticipantSmsChange(MarketPlaceVO marketPlaceVO,VendorResourceSmsSubscription smsSubscription) throws BusinessServiceException{
			try{
				//Updating contact table
				marketPlaceVO = iMarketPlaceDao.updateData(marketPlaceVO);
				if (null!= iMarketPlaceDao.getSmsSubscriptionDetailsForResource(marketPlaceVO.getResourceId())){
					//Updating the subscription details the resource had earlier
		  			iMarketPlaceDao.updateAddParticipantApiResponse(smsSubscription);
		  		}
				else {
					//Inserting new record
					smsSubscription.setCreatedBy(marketPlaceVO.getLoggedInUserName());
					iMarketPlaceDao.insertAddParticipantApiResponse(smsSubscription);
				}
			}
			catch (Exception e) {
				logger
				.error("General Exception @MarketPlaceBOImpl.saveAddParticipantSmsChange() due to"
						+ e.getMessage());
				throw new BusinessServiceException(
				"General Exception @MarketPlaceBOImpl.saveAddParticipantSmsChange() due to "
						+ e.getMessage());
			}
			return marketPlaceVO;
			
		}
		
		
    
}

/*
 * Maintenance History
 * $Log: MarketPlaceBOImpl.java,v $
 * Revision 1.22  2008/05/29 16:32:33  gjacks8
 * added editor user name
 *
 * Revision 1.21  2008/05/21 22:54:26  akashya
 * I21 Merged
 *
 * Revision 1.20.6.2  2008/05/14 17:48:04  nsanzer
 * Backend and jsp Changes for the Edit Account and locations info
 *
 * Revision 1.20.6.1  2008/05/13 22:02:20  nsanzer
 * Backend Changes for the Edit Account and locations info
 *
 * Revision 1.20  2008/04/26 00:40:26  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.18.10.1  2008/04/23 11:42:15  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.19  2008/04/23 05:01:45  hravi
 * Reverting to build 247.
 *
 * Revision 1.18  2008/02/20 16:06:48  langara
 * LMA: Alert issues:  The correct email and alt email addresses are being saved if the admin chooses to use the provider email or the team mbr's email.  But upon load, the provider email and alt email address is always being pulled instead of the team mbr resource email / alt email, if one exists.
 *
 * Revision 1.17  2008/02/19 21:06:04  langara
 * LMA: Alert issues:  The provider admin email addr is not being saved when the team mbr is added as not being able to logon. Commented out the email addr setter so that it does not default to null.  Also, the SMS is being saved in the table as null,null,null.  Modified the code so that if part 1, part 2 and part 3 are not null, then save the sms number or default the setter to null.
 *
 * Revision 1.16  2008/02/15 02:39:25  hrajago
 * Allow multiple user IDs to one email address
 *
 * Revision 1.15  2008/02/06 19:38:53  mhaye05
 * merged with Feb4_release branch
 *
 * Revision 1.13.8.2  2008/02/06 17:38:22  mhaye05
 * all transactions are not handled by Spring AOP.  no more beginWork!!
 * 
 * Revision 1.14  2008/02/05 22:19:46  mhaye05
 * removed commented out code
 *
 */