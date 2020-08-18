package com.newco.marketplace.business.iBusiness.buyer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;






import com.newco.marketplace.dto.vo.ManageTaskVO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackEvent;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerDetailsEventCallbackVO;
import com.newco.marketplace.dto.vo.buyerauthenticationdetails.BuyerAuthenticationDetailsVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.BuyerResource;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ContactLocationVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.spn.SPNetHeaderVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.buyer.BuyerHoldTimeVO;
import com.newco.marketplace.vo.buyer.WorkOrderVO;


/**
 * @author SALI030
 *
 */
public interface IBuyerBO {
  public ContactLocationVO getBuyerContactLocationVO(int buyerId);
  public ContactLocationVO getBuyerResContactLocationVO(int buyerId, int buyerResId);
  public ContactLocationVO getBuyerResDefaultLocationVO(Integer buyerResId);
  public void saveBuyerBlackoutNotification(Integer buyerId, Integer resourceId, Integer nodeId, String zip, String userName);
  public List<BuyerReferenceVO> getBuyerReferenceTypes(Integer buyerId) throws DataServiceException;
  public List<BuyerReferenceVO> getBuyerReferences(Integer buyerId) throws DataServiceException;  
  public List<BuyerReferenceVO> getAllBuyerReferences() throws DataServiceException;
  public List<BuyerReferenceVO> getProviderReferences(String soId) throws DataServiceException;  
  public boolean isBuyerResourceBuyerAdmin(String userName) throws BusinessServiceException;
  public Buyer getBuyer(Integer buyerId) throws BusinessServiceException;
  public Contact getBuyerContactByContactId(Integer contactId) throws BusinessServiceException;
  
  public String getBuyerEIN(Integer buyerId) throws BusinessServiceException;
  public void updateBuyerEIN(Integer buyerId, String ein) throws BusinessServiceException;
  public Buyer getBuyerName(Integer buyerId) throws BusinessServiceException;
  /**
   * Retrieves a BuyerHoldTime object for the specified day difference and buyer ID
   * 
   * @param dayDiff
   * @param buyerId
   * @return BuyerHoldTime
   * @throws DataServiceException
   */
	BuyerHoldTimeVO getBuyerHoldTimeByDayDiffAndBuyerID(Integer dayDiff, Integer buyerId) throws BusinessServiceException;
	
	/**
     * Gets the maximum number of days of difference for holding a order for a specific buyer
     * 
     * @param buyerId
     * @return Maximum of Day_diff
     * @throws DataServiceException
     */
	Integer getMaxDayDiff(Integer buyerId) throws BusinessServiceException;
  public void insertBuyerCustomReference(BuyerReferenceVO buyerRefVO);
  public void updateBuyerCustomReference(BuyerReferenceVO buyerRefVO);
  public void deleteBuyerCustomReference(BuyerReferenceVO buyerRefVO);	
  //old code
  public void updateBuyerSSN(Integer buyerId, String ssn)throws BusinessServiceException;
  public void updateBuyerSSNId(Integer buyerId, String ssn, String dob)throws BusinessServiceException;
  public void updateBuyerAltId(Integer buyerId, String documentType, String documentNo, String country, String dob)throws BusinessServiceException;
  	/**
	 * Get Contact and Location details for the Buyer Contact associated with the SO
	 * @param so_id
	 * @return conLocVo
	 */
  public ContactLocationVO getBuyerResContactLocationVOForSO(String soID) throws BusinessServiceException;
  	/**
	 * Get Contact and Location details for the Buyer Contact Id
	 * @param so_id
	 * @return conLocVo
	 */
  public ContactLocationVO getBuyerResContactLocationVOForContactId(Integer contactId) throws BusinessServiceException;
  
  public void updateBuyerSOMCounts() throws BusinessServiceException;
  public void updateBuyerCompletedOrdersCount() throws BusinessServiceException;
  
  public BuyerResource getBuyerResource(int buyerId,Integer resourceId) throws BusinessServiceException;
  public List<String> validateWorkOrderSKUs(WorkOrderVO workOrderVO) throws BusinessServiceException;
  public List<ManageTaskVO> populateTasksFromSKUs(WorkOrderVO workOrder)throws BusinessServiceException;
  public List<RoutedProvider> getCompletedDate(List<Integer> duplicateList, String criteriaLevel, Integer buyerId);
  public SPNetHeaderVO getSpnDetails(Integer spnId);
  public void deleteTierEligibleProviders(String soId);
  
  /**to fetch the custom reference value
  * @param soId
  * @param coverageTypeLabor
  */
  public String getCustomReference(String soId, String referenceType);

  public Boolean validateFeatureSet(Long buyerId, String nonFunded);
  public Integer getByerAdminContactIdbyResourceId(Integer resourceId) throws BusinessServiceException;

  //SL-18825
  //To fetch the buyer custom references with attribute 'display_no_value',which is set to 1
  public List<BuyerReferenceVO> getBuyerCustomReferenceWithDisplayNoValue(Integer buyerId)	throws BusinessServiceException ;
  //SL-21142 
  //For buyerId and buyer name autosuggestion
  public List<Buyer> getMatchedBuyerByNameOrId(String searchIdorName) throws BusinessServiceException;
  
  //Changes for SLT-272
  public HashMap<Long, Long> getBuyerRoutingDistance() throws BusinessServiceException;
  public BuyerDetailsEventCallbackVO getBuyerCallbackAPIDetails(Integer buyerId) throws BusinessServiceException;
  //changes for SLT-1064
  public BuyerCallbackEvent getBuyerCallbackEventDetails(Integer buyerId, Integer actionId) throws BusinessServiceException;
  public BuyerAuthenticationDetailsVO getBuyerAuthenticationdetails(Integer buyerId) throws BusinessServiceException;
}
