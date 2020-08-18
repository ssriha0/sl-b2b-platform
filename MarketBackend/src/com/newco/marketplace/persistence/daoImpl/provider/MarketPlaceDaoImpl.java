package com.newco.marketplace.persistence.daoImpl.provider;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.provider.IMarketPlaceDao;
import com.newco.marketplace.vo.provider.MarketPlaceVO;
import com.newco.marketplace.vo.vibes.VendorResourceSmsSubscription;

/**
 * @author Covansys - Offshore
 * 
 */
public class MarketPlaceDaoImpl extends SqlMapClientDaoSupport implements
		IMarketPlaceDao {

	private static final Logger logger = Logger.getLogger(MarketPlaceDaoImpl.class);

	public MarketPlaceVO getData(MarketPlaceVO marketPlaceVO)
			throws DBException {
		List<UserActivityVO> activityList = null;
		try {
			activityList = getActivityList(marketPlaceVO);

			String contactId = marketPlaceVO.getContactID();
			String serviceCall = null;
			if (contactId != null) {
				marketPlaceVO.setContactID(contactId);
				serviceCall = (String) getSqlMapClient().queryForObject(
						"market_place_serviceCall.query", marketPlaceVO);
			}

			marketPlaceVO.setServiceCall(serviceCall);
			marketPlaceVO.setActivityList(activityList);
		} catch (SQLException ex) {
			ex.printStackTrace();
			errorMessage(ex, "getData");
		} catch (Exception ex) {
			ex.printStackTrace();
			errorMessage(ex, "getData");
		}
		return marketPlaceVO;
	}

	/**
	 * Function to fetch the 'Contact Details'
	 * 
	 * @param marketPlaceVO
	 * @return
	 * @throws DBException
	 */
	public MarketPlaceVO getContactDetails(String userName) throws DBException {
		MarketPlaceVO marketPlaceVO = null;
		try {
			marketPlaceVO = (MarketPlaceVO) getSqlMapClient().queryForObject(
					"market_place_contact.query", userName);
		} catch (SQLException ex) {
			ex.printStackTrace();
			errorMessage(ex, "getContactDetails");
		} catch (Exception ex) {
			ex.printStackTrace();
			errorMessage(ex, "getContactDetails");
		}
		return marketPlaceVO;
	}

	/**
	 * Function to fetch the 'Contact Details'
	 * 
	 * @param marketPlaceVO
	 * @return
	 * @throws DBException
	 */
	public MarketPlaceVO getContactDetailsForResource(String resourceId)
			throws DBException {
		MarketPlaceVO marketPlaceVO = null;
		try {
			marketPlaceVO = (MarketPlaceVO) getSqlMapClient().queryForObject(
					"mp.resource.contact.query", resourceId);
		} catch (SQLException ex) {
			ex.printStackTrace();
			errorMessage(ex, "getContactDetailsForResource");
		} catch (Exception ex) {
			ex.printStackTrace();
			errorMessage(ex, "getContactDetailsForResource");
		}
		return marketPlaceVO;
	}

	/**
	 * Function to get the 'Activity List'
	 * 
	 * @param marketPlaceVO
	 * @return
	 * @throws DBException
	 */
	private List<UserActivityVO> getActivityList(MarketPlaceVO marketPlaceVO)
			throws DBException {
		List<UserActivityVO> activity = null;
	List<UserActivityVO> finalList = new ArrayList<UserActivityVO>();
		Integer adminResourceId = 0;
		String adminUserName = "";
		boolean adminFlag = false;
		try {
			logger.info("[MarketPlaceDaoImpl] User Name "
					+ marketPlaceVO.getUserName());
			activity = getSqlMapClient().queryForList(
					"market_place_activity.query", marketPlaceVO);
			//If roleId is a provider, check in vendor_hdr table if its a admin
			if(Integer.parseInt(marketPlaceVO.getRoleID())==OrderConstants.PROVIDER_ROLEID)
			{
				adminResourceId = (Integer) getSqlMapClient().queryForObject("user_management.vendorAdmin.select",Integer.parseInt(marketPlaceVO.getEntityId()));
				if(adminResourceId.intValue()==marketPlaceVO.getLoggedInResourceId().intValue())
					adminFlag = true;
			}
			else if(Integer.parseInt(marketPlaceVO.getRoleID())==OrderConstants.BUYER_ROLEID || Integer.parseInt(marketPlaceVO.getRoleID())==OrderConstants.SIMPLE_BUYER_ROLEID)
			{
				adminUserName = (String) getSqlMapClient().queryForObject("buyerAdmin.select",Integer.parseInt(marketPlaceVO.getEntityId()));
				if(adminUserName.equals(marketPlaceVO.getUserName()))
					adminFlag = true;
			}
			else
			{
				Integer returnVal = (Integer) getSqlMapClient().queryForObject("newcoAdmin.select",marketPlaceVO.getUserName());
				if(returnVal!=null && returnVal > 0)
					adminFlag = true;
			}
			for (UserActivityVO uavo : activity) {
					if(!adminFlag && uavo.getAdminInd()>0)
						continue;
					finalList.add(uavo);
				}
			logger.info("[MarketPlaceDaoImpl] List Value  " + activity);
		} catch (SQLException ex) {
			errorMessage(ex, "getActivityList");
		} catch (Exception ex) {
			ex.printStackTrace();
			errorMessage(ex, "getActivityList");
		}
		return finalList;
	}

	/**
	 * method to update mobile no of provider
	 * @param resourceId
	 * @param mobileNo
	 * @param contactId
	 * @return
	 * @throws DBException
	 */
	public boolean updateMobileNo(String mobileNo,
			String contactId) throws DBException {
		int updateResult = 0;
		try {
			Map<String, String> parameters = new HashMap<String, String>();
			parameters.put("mobileNo", mobileNo);
			parameters.put("contactId", contactId);
			updateResult = getSqlMapClient().update(
					"market_place_contact.updateMobileNo", parameters);
			logger.info("[MarketPlaceDaoImpl] Update Result " + updateResult);
		} catch (SQLException ex) {
			errorMessage(ex, "updateData");
		} catch (Exception ex) {
			errorMessage(ex, "updateData");
		}
		return true;
	}
	
	public MarketPlaceVO updateData(MarketPlaceVO marketPlaceVO)
			throws DBException {
		int updateResult = 0;
		try {
			// String contactId = getContactID(marketPlaceVO);
			// marketPlaceVO.setContactID(contactId);
			updateResult = getSqlMapClient().update(
					"market_place_contact.update", marketPlaceVO);
			logger.info("[MarketPlaceDaoImpl] Update Result " + updateResult);

		} catch (SQLException ex) {
			errorMessage(ex, "updateData");
		} catch (Exception ex) {
			errorMessage(ex, "updateData");
		}

		return marketPlaceVO;
	}

	/**
	 * Function to get the 'Contact ID' using 'User Name'
	 */
	private String getContactID(MarketPlaceVO marketPlaceVO) throws DBException {
		String contactId = null;
		try {
			logger.info("[MarketPlaceDaoImpl] Inside getContactID ");
			logger.info("[MarketPlaceDaoImpl] Inside getContactID - User Name "
					+ marketPlaceVO.getUserName());

			contactId = (String) getSqlMapClient().queryForObject(
					"market_place_contactID.query", marketPlaceVO);

			logger
					.info("[MarketPlaceDaoImpl] Inside getContactID - Contact ID "
							+ contactId);
		} catch (SQLException ex) {
			errorMessage(ex, "getContactID");
		} catch (Exception ex) {
			errorMessage(ex, "getContactID");
		}
		return contactId;
	}

	public void insertActivity(UserActivityVO UserActivityVO)
			throws DBException {
		try {
			getSqlMapClient().insert("market_place_userProfPermissions.insert",
					UserActivityVO);

		} catch (SQLException ex) {
			ex.printStackTrace();
			errorMessage(ex, "insertActivity");
		} catch (Exception ex) {
			ex.printStackTrace();
			errorMessage(ex, "insertActivity");
		}
	}

	public void deleteActivity(UserActivityVO UserActivityVO)
			throws DBException {
		try {

			getSqlMapClient().update("market_place_userProfPermissions.delete",
					UserActivityVO);

		} catch (SQLException ex) {
			ex.printStackTrace();
			errorMessage(ex, "deleteActivity");
		} catch (Exception ex) {
			ex.printStackTrace();
			errorMessage(ex, "deleteActivity");
		}
	}

	public MarketPlaceVO getUserNameForResouceId(String resourceId)
			throws DBException {
		MarketPlaceVO marketPlaceVO = null;
		try {
			marketPlaceVO = (MarketPlaceVO) getSqlMapClient().queryForObject(
					"market.place.getusername", resourceId);

		} catch (SQLException ex) {
			ex.printStackTrace();
			errorMessage(ex, "getUserNameForResouceId");
		} catch (Exception ex) {
			ex.printStackTrace();
			errorMessage(ex, "getUserNameForResouceId");
		}
		return marketPlaceVO;
	}

	public MarketPlaceVO getPrimaryIndicator(String resourceId)
			throws DBException {
		MarketPlaceVO marketPlaceVO = null;
		try {
			marketPlaceVO = (MarketPlaceVO) getSqlMapClient().queryForObject(
					"market.place.getPrimaryInd", resourceId);

		} catch (SQLException ex) {
			ex.printStackTrace();
			errorMessage(ex, "getPrimaryIndicator");
		} catch (Exception ex) {
			ex.printStackTrace();
			errorMessage(ex, "getPrimaryIndicator");
		}
		return marketPlaceVO;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.persistence.iDao.provider.IMarketPlaceDao#getBuyerResourceInfo(java.lang.Integer)
	 */
	public Contact getBuyerResourceInfo(Integer buyerResId) throws DBException {
		Contact contactInfo = null;
		try {
			contactInfo = (Contact) getSqlMapClient().queryForObject(
					"contactInfo.query", buyerResId);

		} catch (SQLException ex) {
			errorMessage(ex, "getBuyerResourceInfo");
		} catch (Exception ex) {
			errorMessage(ex, "getBuyerResourceInfo");
		}
		return contactInfo;

	}
	//creates entry in alert task table for subscribing to sms
	public void insertSMS(AlertTask alertTask) throws DBException {
		
		try {
			getSqlMapClient().insert("alert_SMS_subscription.insert",alertTask);

		} catch (SQLException ex) {
			errorMessage(ex, "insertSMS");
		} catch (Exception ex) {
			errorMessage(ex, "insertSMS");
		}
		
	}
	


	private void errorMessage(Exception ex, String methodName)
			throws DBException {
		logger.info("SQL Exception @MarketPlaceDaoImpl." + methodName
				+ "() due to" + ex.getMessage());
		throw new DBException("SQL Exception @MarketPlaceDaoImpl." + methodName
				+ "() due to " + ex.getMessage());
	}

	public MarketPlaceVO updateContactDetails(MarketPlaceVO marketPlaceVO)
			throws DBException {
		int updateResult = 0;
		try {
			updateResult = getSqlMapClient().update(
					"provider_contact.update", marketPlaceVO);
			logger.info("[MarketPlaceDaoImpl] Update Result " + updateResult);

		} catch (SQLException ex) {
			errorMessage(ex, "updateContactDetails");
		} catch (Exception ex) {
			errorMessage(ex, "updateContactDetails");
		}

		return marketPlaceVO;
	}
	
	//R16_1: SL-18979: Fetching constant values from application constants
		public Map <String, String> fetchApiDetails(List<String> apiDetailsNeeded) throws DataServiceException{
	        Map <String, String> apiDetails = new HashMap <String, String>();
	        try{
	                apiDetails =(HashMap <String, String>) getSqlMapClient().queryForMap("fetchAddParticipantApiDetails.query", apiDetailsNeeded, "app_constant_key","app_constant_value");
	        }
	        catch(Exception e){
	                logger.error("Exception occurred in fetchAddParticipantApiDetails: "+e.getMessage());
	                throw new DataServiceException("Exception occurred in fetchAddParticipantApiDetails: "+e.getMessage(),e);
	        }
	        return apiDetails;
	}
		
		//R16_1: SL-18979: Checking whether sms subscription details exists for a resource in db
		public Integer getSmsSubscriptionDetailsForResource(String resourceId) throws DataServiceException{
			Integer smsExists =null;
			try{
				smsExists = (Integer) getSqlMapClient().queryForObject(
						"vendorResourceSmsSubscriptionInfo.query", resourceId);
			} 
			catch(Exception e){
                logger.error("Exception occurred in getSmsSubscriptionDetailsForResource: "+e.getMessage());
                throw new DataServiceException("Exception occurred in getSmsSubscriptionDetailsForResource: "+e.getMessage(),e);
        }
        return smsExists;
		}
		
		//R16_1: SL-18979: Saving Add Participant api details in vendor_resource_sms_subscription
		public void insertAddParticipantApiResponse(VendorResourceSmsSubscription
				addParticipantResponse) throws DataServiceException{
			try {
				getSqlMapClient().insert("addParticipantResponse.insert",addParticipantResponse);

			} catch(Exception e){
                logger.error("Exception occurred in insertAddParticipantApiResponse: "+e.getMessage());
                throw new DataServiceException("Exception occurred in insertAddParticipantApiResponse: "+e.getMessage(),e);
        }
		}
		
		
		//R16_1: SL-18979: Updating Add Participant api details in vendor_resource_sms_subscription if resource already exists
		public void updateAddParticipantApiResponse(VendorResourceSmsSubscription
				addParticipantResponse)
				throws DataServiceException {
			try {
				getSqlMapClient().update(
						"addParticipantResponse.update", addParticipantResponse);
				
			} catch(Exception e){
                logger.error("Exception occurred in updateAddParticipantApiResponse: "+e.getMessage());
                throw new DataServiceException("Exception occurred in updateAddParticipantApiResponse: "+e.getMessage(),e);
        }
			

		}

		/* R16_1: SL-18979: Updating Add Participant api details in vendor_resource_sms_subscription if resource already exists 
		 * Add Participant returned 409 status: Provider already exists in subscription list
		 * Also, when Delete Subscription API is called
		 * @see com.newco.marketplace.persistence.iDao.provider.IMarketPlaceDao#updateConflictErrorDetails(com.newco.marketplace.vo.vibes.VendorResourceSmsSubscription)
		 */
		public void updateVibesDetails(
				VendorResourceSmsSubscription response)
				throws DataServiceException {
			try {
				getSqlMapClient().update(
						"vibesDetails.update", response);
				
			} catch(Exception e){
                logger.error("Exception occurred in updateVibesDetails: "+e.getMessage());
                throw new DataServiceException("Exception occurred in updateVibesDetails: "+e.getMessage(),e);
        }
			
		}

		/* R16_1: SL-18979: Checking whether sms subscription details exists for a sms no in db
		 * (non-Javadoc)
		 * @see com.newco.marketplace.persistence.iDao.provider.IMarketPlaceDao#getSmsSubscriptionDetailsForSmsNo(java.lang.String)
		 */
		public VendorResourceSmsSubscription getSmsSubscriptionDetailsForSmsNo(
				String smsAddress) throws DataServiceException {
			VendorResourceSmsSubscription resourceSmsSubscription = null;
			try{
				resourceSmsSubscription = (VendorResourceSmsSubscription) getSqlMapClient().queryForObject(
						"vendorResourceSmsSubscriptionInfoSmsNo.query", smsAddress);
			}
			catch(Exception e){
                logger.error("Exception occurred in getSmsSubscriptionDetailsForSmsNo: "+e.getMessage());
                throw new DataServiceException("Exception occurred in getSmsSubscriptionDetailsForSmsNo: "+e.getMessage(),e);
        }
			return resourceSmsSubscription;
		}

		/* R16_1: SL-18979: Fetching person_id for a resource in case of Delete Subscription API
		 * 
		 */
		public String getPersonIdForResource(String resourceId)
				throws DataServiceException {
			String personId  =null;
			try{
				personId = (String) getSqlMapClient().queryForObject(
						"vendorResourcePersonId.query", resourceId);
			} 
			catch(Exception e){
                logger.error("Exception occurred in getPersonIdForResource: "+e.getMessage());
                throw new DataServiceException("Exception occurred in getPersonIdForResource: "+e.getMessage(),e);
        }
        return personId;
		}
		
		
		/** R16_1: SL-18979: Deleting subscription details for a resource 
		 * @param response
		 * @throws DataServiceException
		 */
		public void deleteVibesDetails(
				VendorResourceSmsSubscription response)
				throws DataServiceException {
			try {
				getSqlMapClient().update(
						"vibesDetails.delete", response);
				
			} catch(Exception e){
                logger.error("Exception occurred in deleteVibesDetails: "+e.getMessage());
                throw new DataServiceException("Exception occurred in deleteVibesDetails: "+e.getMessage(),e);
        }	
		}
		
		/** R16_1: SL-18979: Fetching vendor Ids for a sms no 
		 * @param smsAddress
		 * @throws DataServiceException
		 */
		public List<Integer> fetchVendorIdsforSmsNo(String smsAddress) throws DataServiceException{
			List<Integer> vendorIdList =new ArrayList<Integer>();
			try{
				vendorIdList = (List<Integer>)getSqlMapClient().queryForList("fetchVendorIds.query",smsAddress);
			} 
			catch(Exception e){
                logger.error("Exception occurred in fetchVendorIdsforSmsNo: "+e.getMessage());
                throw new DataServiceException("Exception occurred in fetchVendorIdsforSmsNo: "+e.getMessage(),e);
        }
        return vendorIdList;
		}
		
		/* R16_1: SL-18979: Fetching vendor_id for a resource 
		 * 
		 */
		public Integer getVendorId(String resourceId)
				throws DataServiceException {
			Integer vendorId  =null;
			try{
				vendorId = (Integer) getSqlMapClient().queryForObject(
						"vendorId.query", resourceId);
			} 
			catch(Exception e){
                logger.error("Exception occurred in getVendorId: "+e.getMessage());
                throw new DataServiceException("Exception occurred in getVendorId: "+e.getMessage(),e);
        }
        return vendorId;
		}
		
		/* R16_1: SL-18979: Fetching details for a sms no in db
		 * (non-Javadoc)
		 * 
		 */
		public VendorResourceSmsSubscription getSmsSubscriptionDetails(
				String smsAddress) throws DataServiceException {
			VendorResourceSmsSubscription resourceSmsSubscription = null;
			try{
				resourceSmsSubscription = (VendorResourceSmsSubscription) getSqlMapClient().queryForObject(
						"SmsSubscriptionInfo.query", smsAddress);
			}
			catch(Exception e){
                logger.error("Exception occurred in getSmsSubscriptionDetails: "+e.getMessage());
                throw new DataServiceException("Exception occurred in getSmsSubscriptionDetails: "+e.getMessage(),e);
        }
			return resourceSmsSubscription;
		}

		/* (non-Javadoc)
		 * @see com.newco.marketplace.persistence.iDao.provider.IMarketPlaceDao#checkForSMSNumDuplicates(java.lang.String)
		 */
		public boolean checkForSMSNumDuplicates(String smsAddress) throws DataServiceException {
			boolean smsNumDuplicateExists =false;
			Integer countSMS = null;
			try{
				countSMS = (Integer) getSqlMapClient().queryForObject(
						"checkForSMSNumDuplicates.query", smsAddress);
				if(null!= countSMS && countSMS.intValue()>1){
					smsNumDuplicateExists = true;
				}
			} 
			catch(Exception e){
				logger.error("Exception occurred in checkForSMSNumDuplicates: "+e.getMessage());
				throw new DataServiceException("Exception occurred in checkForSMSNumDuplicates: "+e.getMessage(),e);
			}
			return smsNumDuplicateExists;
		}

		public boolean getVibesSwitch(String vibesSwitch)
				throws DataServiceException {
			try{
				String isSwitch= (String) getSqlMapClient().queryForObject("vibes_sms_switch.query", vibesSwitch);
				return Boolean.parseBoolean(isSwitch);
			}catch(Exception e){
				logger.error("Exception occurred in getVibesSwitch: "+e.getMessage());
				throw new DataServiceException("Exception occurred in getVibesSwitch: "+e.getMessage(),e);
			}
		}
		
}
