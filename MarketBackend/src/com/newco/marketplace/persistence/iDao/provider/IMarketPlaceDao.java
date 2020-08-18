package com.newco.marketplace.persistence.iDao.provider;


import java.util.List;
import java.util.Map;

import com.newco.marketplace.auth.UserActivityVO;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.provider.MarketPlaceVO;
import com.newco.marketplace.vo.vibes.VendorResourceSmsSubscription;


public interface IMarketPlaceDao {
	public MarketPlaceVO getData(MarketPlaceVO marketPlaceVO) throws DBException;
	public boolean updateMobileNo(String mobileNo, String contactId) throws DBException;
	public MarketPlaceVO updateData(MarketPlaceVO marketPlaceVO) throws DBException;
	public void insertActivity(UserActivityVO aProviderActivityVO) throws DBException;
	public void deleteActivity(UserActivityVO aProviderActivityVO) throws DBException;
	
	/**
	 * @param resourceId
	 * @return
	 * @throws DBException
	 * @author KSudhanshu
	 */
	public MarketPlaceVO getUserNameForResouceId(String resourceId) throws DBException;
	
	/**
	 * @param userName
	 * @return
	 * @throws DBException
	 */
	public MarketPlaceVO getContactDetails(String userName) throws DBException;
	
	public MarketPlaceVO getPrimaryIndicator(String resourceId) throws DBException;
	
	public MarketPlaceVO getContactDetailsForResource(String resourceId) throws DBException;
	public void insertSMS(AlertTask alertTask)throws DBException ;
	
	public MarketPlaceVO updateContactDetails(MarketPlaceVO marketPlaceVO)
			throws DBException;
	
	//R16_1: SL-18979: Fetching constant values from application constants
	public Map <String, String> fetchApiDetails(List<String> apiDetailsNeeded) throws DataServiceException;
	
	//R16_1: SL-18979: Saving Add Participant api details in db
	public void insertAddParticipantApiResponse(VendorResourceSmsSubscription
			addParticipantResponse)throws DataServiceException;
	public void updateAddParticipantApiResponse(VendorResourceSmsSubscription
			addParticipantResponse)throws DataServiceException;
	public void updateVibesDetails(
			VendorResourceSmsSubscription addParticipantResponse) throws DataServiceException;
	
	//R16_1: SL-18979: Checking whether sms subscription details exists for a resource in db
	public Integer getSmsSubscriptionDetailsForResource(String resourceId) throws DataServiceException;
	//R16_1: SL-18979: Checking whether sms subscription details exists for a sms no in db
	public VendorResourceSmsSubscription getSmsSubscriptionDetailsForSmsNo(
			String smsAddress)throws DataServiceException;
	
	//R16_1: SL-18979: Fetching person_id for a resource in db
	public String getPersonIdForResource(String resourceId) throws DataServiceException;
	
	//R16_1: SL-18979: Deleting subscription details for a resource in db
	public void deleteVibesDetails(
			VendorResourceSmsSubscription response)
			throws DataServiceException;

	//R16_1: SL-18979: Fetching vendor Ids for a sms no 
	public List<Integer> fetchVendorIdsforSmsNo(String smsAddress) throws DataServiceException;
	
	//R16_1: SL-18979: Fetching vendorId for the resource
	public Integer getVendorId(String resourceId)
			throws DataServiceException ;
	
	//R16_1: SL-18979: Fetching details for a sms no
	public VendorResourceSmsSubscription getSmsSubscriptionDetails(
			String smsAddress) throws DataServiceException;
	//R16_1: SL-18979: Fetching details for a sms no
	public boolean checkForSMSNumDuplicates(String smsAddress)throws DataServiceException ;
	
	//R16_1: SL-18979: Fetching switch value
	public boolean getVibesSwitch(String vibesSwitch) throws DataServiceException;
}
