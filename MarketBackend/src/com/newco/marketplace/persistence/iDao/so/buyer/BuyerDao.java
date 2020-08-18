package com.newco.marketplace.persistence.iDao.so.buyer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



import com.newco.marketplace.dto.vo.ManageTaskVO;
import com.newco.marketplace.dto.vo.ach.OFACProcessQueueVO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.buyerauthenticationdetails.BuyerAuthenticationDetailsVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.BuyerCancellationPostingFeeVO;
import com.newco.marketplace.dto.vo.serviceorder.BuyerDetail;
import com.newco.marketplace.dto.vo.serviceorder.BuyerResource;
import com.newco.marketplace.dto.vo.serviceorder.ContactLocationVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.SOEntityInfoVO;
import com.newco.marketplace.dto.vo.spn.SPNetHeaderVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.buyer.BuyerRegistrationVO;
import com.newco.marketplace.vo.buyer.PersonalIdentificationVO;
import com.newco.marketplace.vo.buyer.WorkOrderVO;


public interface BuyerDao {

	public Buyer query(Buyer buyer) throws DataServiceException;
	//SL-21142
	public List<Buyer> queryForMatchedBuyerIdORName(Buyer buyerIdorName) throws DataServiceException;
	public BuyerDetail getBuyerDetailForServiceOrder(int serviceOrderId ) throws DataServiceException;
	public BuyerDetail getBuyerDetailForServiceOrder(String serviceOrderId ) throws DataServiceException;
	public ContactLocationVO getBuyer(Buyer buyer) throws DataServiceException;
	public void saveBuyerBlackoutNotification(Integer buyerId, Integer resourceId, Integer nodeId, String zip, String userName) 
												   throws DataServiceException;
	public ContactLocationVO getBuyerResource(BuyerResource buyerResource) throws DataServiceException;
	public List<BuyerReferenceVO> getBuyerReferenceTypes(Integer buyerId) throws DataServiceException;
	public List<BuyerReferenceVO> getBuyerReferences(Integer buyerId) throws DataServiceException;	
	public List<BuyerReferenceVO> getAllBuyerReferences() throws DataServiceException;
	public List<BuyerReferenceVO> getProviderReferences(String soId) throws DataServiceException;	
	public List<BuyerReferenceVO> getProviderRefVals(String soId) throws DataServiceException;
	
	public boolean isBuyerResourceBuyerAdmin(String userName) throws DataServiceException;
	public Buyer insert(Buyer buyer)throws DataServiceException;
	public int update(Buyer buyer)throws DataServiceException;
	public Buyer loadData(Buyer buyer) throws DataServiceException;
  	public int updateBuyerCompanyProfile(Buyer buyer) throws DataServiceException;
  	public int updateBuyerMailing(Buyer buyer) throws DataServiceException;
  	public void updateCardLocationDetails(Buyer buyer) throws DataServiceException;
  	
  	public String getBuyerEIN(Integer buyerId) throws DataServiceException;
  	//fetch buyer tax id information 
  	public Buyer getBuyerPII(Integer buyerId) throws DataServiceException;
  	
  	public List<PersonalIdentificationVO> getPiiHistory(Integer buyerId)throws DataServiceException;
  	//fetch buyer business name and user name as input for saving in PII history
  	public Buyer getBuyerName(Integer buyerId)throws DataServiceException;
  	
  	public BuyerResource getQueryByBuyerId(int buyerId) throws DataServiceException;
  	
  	public void updateBuyerEIN(Buyer buyer) throws DataServiceException;
  	public boolean checkBlackoutState(String stateCd) throws DataServiceException;
  	public void saveBlackoutBuyerLead(BuyerRegistrationVO buyerRegVO) throws DataServiceException;
  	public List<String> getBlackoutStateCodes() throws DataServiceException;

  	public ContactLocationVO getBuyerResourceDefaultLocation(Integer resourceId) throws DataServiceException;
  	public Integer getBuyerRole(String username) throws DataServiceException;
	public int insertBuyerCustomReference(BuyerReferenceVO buyerRefVO);
	public void updateBuyerCustomReference(BuyerReferenceVO buyerRefVO);
	public void deleteBuyerCustomReference(BuyerReferenceVO buyerRefVO);	
	public void updateBuyerSSN(Buyer buyer) throws DataServiceException;
	public void updateBuyerAltId(Buyer buyer) throws DataServiceException;
	public OFACProcessQueueVO getEntityInfo(Integer entityId,String userType) throws DataServiceException;
	public SOEntityInfoVO getSoBuyerVendorEntities(String soId) throws DataServiceException;
	public OFACProcessQueueVO getEntityInfoForActivation(Integer entityId,String userType) throws DataServiceException;
	/**
	 * Get Contact and Location details for the Buyer Contact associated with the SO
	 * @param so_id
	 * @return ContactLocationVO
	 */
	public ContactLocationVO getBuyerResContactLocationForSO(String soId) throws DataServiceException;
	/**
	 * Get Contact and Location details for the Buyer Contact Id
	 * @param so_id
	 * @return ContactLocationVO
	 */
	public ContactLocationVO getBuyerResContactLocationForContactId(Integer contactId) throws DataServiceException;

	public void lockBuyer(Integer buyerId);
	
	public BuyerResource getBuyerResource(int buyerId,Integer resourceId) throws DataServiceException;
	public List<String> getValidWorkOrderSKUs(WorkOrderVO workOrderVO) throws DataServiceException;
	public List<ManageTaskVO> populateTaskFromSKUs(WorkOrderVO workOrder)throws DataServiceException; 
	public List<RoutedProvider> getCompletedDate(List<Integer> duplicateList, String criteriaLevel, Integer buyerId);
	public SPNetHeaderVO getSpnDetails(Integer spnId);
	public void deleteTierEligibleProviders(String soId);
	
	/**to fetch the custom reference value
	  * @param soId
	  * @param coverageTypeLabor
	  */
	public String getCustomReference(String soId, String referenceType);
	public String getFeature(Long buyerID, String feature)throws DataServiceException;
	public Integer getByerAdminContactIdbyResourceId(Integer resourceId) throws DataServiceException;
	
	//SL-18825
	//To fetch the buyer custom references with attribute 'display_no_value',which is set to 1
	public List<BuyerReferenceVO> getBuyerCustomReferenceWithDisplayNoValue(Integer buyerId)
			throws DataServiceException;
	//changes for SL-20461 starts
	public boolean updateBuyerAdmin(BuyerRegistrationVO buyerRegistrationVO) throws DataServiceException;
	
	//Changes for SL-20461 ends
	
	//Changes for SLT-272
	public HashMap<Long, Long> getBuyerRoutingDistance() throws DataServiceException;
	public BuyerAuthenticationDetailsVO getBuyerAuthenticationdetails(Integer buyerId) throws DataServiceException;
	//SLT-1444
	public void initialiseMarkets(Integer buyerId) throws DataServiceException;
	//SLT-1805
	public void addBuyerFeatureSetWithDefaultValue(Integer buyerId) throws DataServiceException;
	//SLT-1726
	public void insertBuyerCancellationRefund(List<BuyerCancellationPostingFeeVO> buyerCancellationRefundList) throws DataServiceException;
}
