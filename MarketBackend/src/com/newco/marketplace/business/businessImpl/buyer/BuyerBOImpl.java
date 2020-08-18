package com.newco.marketplace.business.businessImpl.buyer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.buyerCallBackEvent.BuyerCallbackEventsCache;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.dto.vo.CryptographyVO;
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
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerHoldTimeDao;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerSOCountsDAO;
import com.newco.marketplace.persistence.iDao.contact.ContactDao;
import com.newco.marketplace.persistence.iDao.so.buyer.BuyerDao;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.utils.ValidationUtils;
import com.newco.marketplace.vo.buyer.BuyerHoldTimeVO;
import com.newco.marketplace.vo.buyer.WorkOrderVO;


/**
 * $Revision: 1.10 $ $Author: bgangaj $ $Date: 2008/05/22 19:12:15 $
 */
public class BuyerBOImpl implements IBuyerBO{
	
   private BuyerDao buyerDao;
   private ContactDao contactDao;
   private IBuyerHoldTimeDao buyerHoldTimeDao;
   private IBuyerSOCountsDAO buyerSOCountsDAO;
   private Cryptography cryptography;
   private BuyerCallbackEventsCache buyerCallbackEventsCache;


private static Logger logger = Logger.getLogger(BuyerBOImpl.class.getName());
	
   public ContactLocationVO getBuyerContactLocationVO(int buyerId) {
		Buyer buyer =new Buyer();
		buyer.setBuyerId(buyerId);
		ContactLocationVO contLoVo = null;
		try {
			contLoVo = buyerDao.getBuyer(buyer);
		} catch (DataServiceException e) {
			logger.info("Got Exception in getBuyerContactLocationVO(" + buyerId
					+ ") - ignoring", e);
		}
		return contLoVo;
	}

	public ContactLocationVO getBuyerResContactLocationVO(int buyerId,
			int buyerResId) {
		BuyerResource buyerResource = new BuyerResource();
		buyerResource.setResourceId(buyerResId);
		buyerResource.setBuyerId(buyerId);
		ContactLocationVO contLoVo = null;
		try {
			contLoVo = buyerDao.getBuyerResource(buyerResource);
		} catch (DataServiceException e) {
			logger.info("Got Exception in getBuyerResContactLocationVO("
					+ buyerId + "," + buyerResId + ") - ignoring", e);
		}
		return contLoVo;
	}

	public ContactLocationVO getBuyerResDefaultLocationVO(Integer buyerResId) {
		ContactLocationVO contLoVo = null;
		try {
			contLoVo = buyerDao.getBuyerResourceDefaultLocation(buyerResId);
		} catch (DataServiceException e) {
			logger
					.info(
							"Got Exception in getBuyerResDefaultLocationVO() - ignoring",
							e);
		}
		return contLoVo;
	}

	public BuyerDao getBuyerDao() {
		return buyerDao;
	}

	public void setBuyerDao(BuyerDao buyerDao) {
		this.buyerDao = buyerDao;
	}
	
	public BuyerCallbackEventsCache getBuyerCallbackEventsCache() {
		return buyerCallbackEventsCache;
	}

	public void setBuyerCallbackEventsCache(
			BuyerCallbackEventsCache buyerCallbackEventsCache) {
		this.buyerCallbackEventsCache = buyerCallbackEventsCache;
	}

	public void saveBuyerBlackoutNotification(Integer buyerId,
			Integer resourceId, Integer nodeId, String zip, String userName) {
		try {
			buyerDao.saveBuyerBlackoutNotification(buyerId, resourceId, nodeId,
					zip, userName);
		} catch (DataServiceException e) {
			logger.info("Got Exception in saveBuyerBlackoutNotification("
					+ buyerId + "," + resourceId + "," + nodeId + "," + zip
					+ "," + userName + ") - ignoring", e);
		}
	}

	public List<BuyerReferenceVO> getBuyerReferenceTypes(Integer buyerId)
			throws DataServiceException {
		return getBuyerDao().getBuyerReferenceTypes(buyerId);
	}

	public List<BuyerReferenceVO> getBuyerReferences(Integer buyerId)
			throws DataServiceException {
		return getBuyerDao().getBuyerReferences(buyerId);
	}

	public List<BuyerReferenceVO> getAllBuyerReferences() throws DataServiceException {
		return getBuyerDao().getAllBuyerReferences();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.business.iBusiness.buyer.IBuyerBO#getProviderReferences(java.lang.String)
	 */
	public List<BuyerReferenceVO> getProviderReferences(String soId)
			throws DataServiceException {
		//Get all active provider entries from the buyer_reference
		List<BuyerReferenceVO> allRefs = getBuyerDao().getProviderReferences(soId);
		//Get all values for the buyer_reference if any
		List<BuyerReferenceVO> refsWithVals = getBuyerDao().getProviderRefVals(soId);
		// match the values to the overall collection. 
		for (BuyerReferenceVO provRefNoVal : allRefs) {
			for (BuyerReferenceVO provRefVal : refsWithVals) {
				if (provRefNoVal.getBuyerRefTypeId().equals(provRefVal.getBuyerRefTypeId())){
					provRefNoVal.setReferenceValue(provRefVal.getReferenceValue());
					break;
				}
			}
		}
		return allRefs;
	}

	public boolean isBuyerResourceBuyerAdmin(String userName)
			throws BusinessServiceException {

		boolean toReturn = false;
		try {
			toReturn = buyerDao.isBuyerResourceBuyerAdmin(userName);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(
					"Unable to determine if buyer resource is buyer admin", e);
		}
		return toReturn;
	}

	public Buyer getBuyer(Integer buyerId) throws BusinessServiceException {
		Buyer buyer = new Buyer();
		try {
			buyer.setBuyerId(buyerId);
			buyer = buyerDao.query(buyer);
		} catch (DataServiceException e) {
			logger.error("Error loading buyer object ", e);
			throw new BusinessServiceException("Error loading buyer object ", e);
		}
		return buyer;
	}
	
	public Contact getBuyerContactByContactId(Integer contactId) throws BusinessServiceException {
		Contact contact = new Contact();
		try {
			contact = contactDao.query(contactId);
		} catch (DataServiceException e) {
			throw new BusinessServiceException("Error loading contact object ", e);
		}
		return contact;
	}

	public String getBuyerEIN(Integer buyerId) throws BusinessServiceException {
		String ein = null;
		try {
			ein = buyerDao.getBuyerEIN(buyerId);
		} catch (DataServiceException e) {
			logger.error("Error Saving Buyer EIN --> ", e);
			throw new BusinessServiceException("Error Saving Buyer EIN -->", e);
		}
		return ein;
	}

	public void updateBuyerEIN(Integer buyerId, String ein)
			throws BusinessServiceException {
		Buyer buyer = new Buyer();
		try {

			// Encrypt the EinNo and then save
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(ein);
			cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
			cryptographyVO = cryptography.encryptKey(cryptographyVO);

			buyer.setBuyerId(buyerId);
			buyer.setEinNoEnc(cryptographyVO.getResponse());

			buyerDao.updateBuyerEIN(buyer);
		} catch (DataServiceException e) {
			logger.error("Error Saving Buyer EIN ");
			throw new BusinessServiceException("Error Saving Buyer EIN -->", e);
		}

	}
	
	  public Buyer getBuyerName(Integer buyerId) throws BusinessServiceException {
		  Buyer buyer = new Buyer();
			try {
				buyer = buyerDao.getBuyerName(buyerId);
			} catch (DataServiceException e) {
				logger.error("Error loading buyer object ", e);
				throw new BusinessServiceException("Error loading buyer object ", e);
			}
			return buyer;
	  }
	
	  //old code
	  public void updateBuyerSSN(Integer buyerId, String ssn)
		throws BusinessServiceException {
		Buyer buyer = new Buyer();
		try {
			// Encrypt the SSNNo and then save
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(ssn);
			cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
			cryptographyVO = cryptography.encryptKey(cryptographyVO);
		
			buyer.setBuyerId(buyerId);
			buyer.setEinNoEnc(cryptographyVO.getResponse());
			buyerDao.updateBuyerSSN(buyer);
		} catch (DataServiceException e) {
			logger.error("Error Saving Buyer SSN --> ", e);
			throw new BusinessServiceException("Error Saving Buyer SSN -->", e);
		}
	}
	  
	public void updateBuyerSSNId(Integer buyerId, String ssn, String dob)
		throws BusinessServiceException {
		Buyer buyer = new Buyer();
		try {
			// Encrypt the SSN No and then save
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(ssn);
			cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
			cryptographyVO = cryptography.encryptKey(cryptographyVO);
		
			buyer.setBuyerId(buyerId);
			buyer.setEinNoEnc(cryptographyVO.getResponse());
			buyer.setDob(dob);
			buyerDao.updateBuyerSSN(buyer);
			
		} catch (DataServiceException e) {
			logger.error("Error Saving Buyer SSN --> ", e);
			throw new BusinessServiceException("Error Saving Buyer SSN -->", e);
		}
	}
	
	public void updateBuyerAltId(Integer buyerId, String documentType, String documentNo, String country, String dob)
 	throws BusinessServiceException{
	 Buyer buyer = new Buyer();
		try {
			// Encrypt the alternate tax id number and then save
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(documentNo);
			cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
			cryptographyVO = cryptography.encryptKey(cryptographyVO);
		
			buyer.setBuyerId(buyerId);
			buyer.setAltIDDocType(documentType);
			buyer.setEinNoEnc(cryptographyVO.getResponse());
			buyer.setAltIDCountryIssue(country);
			buyer.setDob(dob);
			
			buyerDao.updateBuyerAltId(buyer);
			
		} catch (DataServiceException e) {
			logger.error("Error Saving Buyer Alternate tax id --> ", e);
			throw new BusinessServiceException("Error Saving Buyer Alternate tax id -->", e);
		}
 }

	public Cryptography getCryptography() {
		return cryptography;
	}

	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.buyer.IBuyerBO#getBuyerHoldTimeByDayDiffAndBuyerID(java.lang.Integer, java.lang.Integer)
	 */
	public BuyerHoldTimeVO getBuyerHoldTimeByDayDiffAndBuyerID(Integer dayDiff,
			Integer buyerId) throws BusinessServiceException {
		BuyerHoldTimeVO buyerHoldTime = null;
		try {
			buyerHoldTime = buyerHoldTimeDao.getBuyerHoldTimeByDayDiffAndBuyerID(dayDiff, buyerId);
		} catch (DataServiceException e) {
			logger.error("Error getting Buyer Hold Time --> ",e);
			throw new BusinessServiceException("Error getting Buyer Hold Time -->",e);
		}
		return buyerHoldTime;
	}
	

	public void insertBuyerCustomReference(BuyerReferenceVO buyerRefVO) {
		try {
			int id = buyerDao.insertBuyerCustomReference(buyerRefVO);
		} catch (Exception e) {
			logger.info("Exception in insertBuyerCustomReference("
					+ buyerRefVO.getBuyerId() + ","
					+ buyerRefVO.getReferenceType() + ") - ignoring", e);
		}
	}

	public void deleteBuyerCustomReference(BuyerReferenceVO buyerRefVO) {
		try {
			buyerDao.deleteBuyerCustomReference(buyerRefVO);
		} catch (Exception e) {
			logger.info("Exception in deleteBuyerCustomReference("
					+ buyerRefVO.getBuyerId() + ","
					+ buyerRefVO.getReferenceType() + ") - ignoring", e);
		}
	}

	public void updateBuyerCustomReference(BuyerReferenceVO buyerRefVO) {
		try {
			buyerDao.updateBuyerCustomReference(buyerRefVO);
		} catch (Exception e) {
			logger.info("Exception in updateBuyerCustomReference("
					+ buyerRefVO.getBuyerId() + ","
					+ buyerRefVO.getReferenceType() + ") - ignoring", e);
		}
	}
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.buyer.IBuyerBO#getMaxDayDiff(java.lang.Integer)
	 */
	public Integer getMaxDayDiff(Integer buyerId)
	throws BusinessServiceException {
		Integer maxDayDiff = null;
		BuyerHoldTimeVO buyerHoldTime = new BuyerHoldTimeVO();
		buyerHoldTime.setBuyerId(buyerId);
		try {
			maxDayDiff = buyerHoldTimeDao.getMaxDayDiff(buyerHoldTime);
		} catch (DataServiceException e) {
			logger.error("Error returned trying to get maximum of day difference",e);
			throw new BusinessServiceException("Error returned trying to get maximum of day difference",e);
		}
		return maxDayDiff;
	}
	

	/**
	 * @return the buyerHoldTimeDao
	 */
	public IBuyerHoldTimeDao getBuyerHoldTimeDao() {
		return buyerHoldTimeDao;
	}

	/**
	 * @param buyerHoldTimeDao the buyerHoldTimeDao to set
	 */
	public void setBuyerHoldTimeDao(IBuyerHoldTimeDao buyerHoldTimeDao) {
		this.buyerHoldTimeDao = buyerHoldTimeDao;
	}
	
	public void updateBuyerSOMCounts() throws BusinessServiceException{
		try {
			buyerSOCountsDAO.updateSOMCountsForBuyer();
		} catch (DBException e) {
			logger.error("Error while trying to update SOM Counts for buyer",e);
			throw new BusinessServiceException("Error while trying to update SOM Counts for buyer",e);
		}
	}
	
	public void updateBuyerCompletedOrdersCount() throws BusinessServiceException{
		try {
			buyerSOCountsDAO.updateBuyerCompletedOrdersCount();
		} catch (DBException e) {
			logger.error("Error while trying to update SOM Counts for buyer",e);
			throw new BusinessServiceException("Error while trying to update SOM Counts for buyer",e);
		}
	}	
	public IBuyerSOCountsDAO getBuyerSOCountsDAO() {
		return buyerSOCountsDAO;
	}


	/**
	 * Get Contact and Location details for the Buyer Contact associated with the SO
	 * @param so_id
	 * @return conLocVo
	 */
	public ContactLocationVO getBuyerResContactLocationVOForSO(String soID) throws BusinessServiceException{
	   	ContactLocationVO contLoVo = null;
		try {
			contLoVo = buyerDao.getBuyerResContactLocationForSO(soID);
		} catch (DataServiceException e) {
			throw new BusinessServiceException("Error in getBuyerContactLocationVOForSO",e);
		}
		return contLoVo;
   }
	/**
	 * Get Contact and Location details for the Buyer Contact associated with the SO
	 * @param so_id
	 * @return conLocVo
	 */
	public ContactLocationVO getBuyerResContactLocationVOForContactId(Integer contactId) throws BusinessServiceException{
	   	ContactLocationVO contLoVo = null;
		try {
			contLoVo = buyerDao.getBuyerResContactLocationForContactId(contactId);
		} catch (DataServiceException e) {
			throw new BusinessServiceException("Error in getBuyerContactLocationVOForcontactId",e);
		}
		return contLoVo;
   }
	   
	public void setBuyerSOCountsDAO(IBuyerSOCountsDAO buyerSOCountsDAO) {
		this.buyerSOCountsDAO = buyerSOCountsDAO;
	}

	public void setContactDao(ContactDao contactDao) {
		this.contactDao = contactDao;
	}
	
	
	public BuyerResource getBuyerResource(int buyerId,Integer resourceId) throws BusinessServiceException{
		 BuyerResource buyerResource = null;
		 try {
			 buyerResource = buyerDao.getBuyerResource(buyerId,resourceId);
			} catch (DataServiceException e) {
				throw new BusinessServiceException("Error in getBuyerContactLocationVOForSO",e);
			}
		return buyerResource;
	 }
   
	/******* Begin SL 18041 ******/
	/**
	 * This method validates a list of work order skus.
	 * @param cancelSkus : List of String which contains names of work order SKUs.
	 *  		buyerId : Buyer Id.
	 *  @return List of invalid SKUs. An empty list is returned when all the given SKUs are valid 
	 *  @throws BusinessServiceException
	 * */
	public List<String> validateWorkOrderSKUs(WorkOrderVO workOrderVO) throws BusinessServiceException{ 
		try{
			List<String> validSKUs = buyerDao.getValidWorkOrderSKUs(workOrderVO);
			//Remove duplicate entries to avoid multiple error messages for
			//same SKUs.
			List<String> providedSKUs = new ArrayList<String>(new LinkedHashSet<String>(workOrderVO.getWorkOrderSKUs())); 
			/*If at least one SKU is in valid, then return all the invalid SKUs */
			if(validSKUs.size()!= providedSKUs.size()){
				providedSKUs.removeAll(validSKUs);
				return providedSKUs; //Will contain only the invalid SKUs.
			}
		}catch(DataServiceException e) {
			logger.error(this.getClass().getName()+" : validateWorkOrderSKUs : " + e);
			throw new BusinessServiceException(e);			
		}
		return new ArrayList<String>();
	}

	/**
	 * Method fetches the details of the list of SKUs for
	 * a provided buyerId
	 * @param workOrder {@link WorkOrderVO} 
	 * @return {@link ManageTaskVO}
	 * @throws BusinessServiceException
	 * */
	public List<ManageTaskVO> populateTasksFromSKUs(WorkOrderVO workOrder) throws BusinessServiceException{
		List<ManageTaskVO> manageTaskVO= new ArrayList<ManageTaskVO>();
		try{
			manageTaskVO=buyerDao.populateTaskFromSKUs(workOrder);
		}catch(DataServiceException e) {
			logger.error(this.getClass().getName()+" : populateTasksFromSKUs :"+ e);
			throw new BusinessServiceException(e);	
		}
		return manageTaskVO ;
	}
	/******* End SL 18041 ******/
	
	public List<RoutedProvider> getCompletedDate(List<Integer> duplicateList, String criteriaLevel, Integer buyerId) {
		List<RoutedProvider> dateList = null;
		try{
			dateList = buyerDao.getCompletedDate(duplicateList, criteriaLevel, buyerId);
		}
		catch(Exception e){
			logger.error("Exception in BuyerBOImpl getCompletedDate() due to "+ e);
		}
		return dateList;
	}
	
	public SPNetHeaderVO getSpnDetails(Integer spnId){
		SPNetHeaderVO hdr = null;
		try{
			hdr = buyerDao.getSpnDetails(spnId);
		}
		catch(Exception e){
			logger.error("Exception in BuyerBOImpl getCompletedDate() due to "+ e);
		}
		return hdr;
	}
	
	public void deleteTierEligibleProviders(String soId){
		try{
			buyerDao.deleteTierEligibleProviders(soId);
		}
		catch(Exception e){
			logger.error("Exception in BuyerBOImpl getCompletedDate() due to "+ e);
		}
	}

	public Boolean validateFeatureSet(Long buyerId, String nonFunded) {
			Boolean foundFeature = null;
			
			try {
				String strfeature = buyerDao.getFeature(buyerId, nonFunded);
				if (strfeature != null) {
					foundFeature = new Boolean(true);
				}
				else {
					foundFeature = new Boolean(false);
				}
			} catch (DataServiceException e) {
				logger.error(e.getMessage(), e);
			}
			return foundFeature;
	}
	
	/**to fetch the custom reference value
	  * @param soId
	  * @param coverageTypeLabor
	  */
	  public String getCustomReference(String soId, String referenceType){
		  String value = null;
		  try{
			 value = buyerDao.getCustomReference(soId, referenceType);
		  }
		  catch(Exception e){
			  logger.error("Exception in BuyerBOImpl getCustomReference() due to "+ e);
		  } 
		  return value;
	  }

	public Integer getByerAdminContactIdbyResourceId(Integer resourceId)
			throws BusinessServiceException {
		Integer contactId = null;
		try {
			contactId = buyerDao.getByerAdminContactIdbyResourceId(resourceId);
		} catch (Exception e) {
			logger
					.error("Exception in BuyerBOImpl getByerAdminContactIdbyResourceId() due to "
							+ e);
			throw new BusinessServiceException(e);
		}
		return contactId;
	}
	
	//SL-18825
	/**to fetch the buyer custom references with attribute 'display_no_value',which is set to 1
	  * @param soId
	  * @param coverageTypeLabor
	  */
	  public List<BuyerReferenceVO> getBuyerCustomReferenceWithDisplayNoValue(Integer buyerId)	throws BusinessServiceException {
		  List<BuyerReferenceVO> value = null;
		  try{
			 value = buyerDao.getBuyerCustomReferenceWithDisplayNoValue(buyerId);
		  }
		  catch(Exception e){
			  logger.error("Exception in BuyerBOImpl getBuyerCustomReferenceWithDisplayNoValue() due to "+ e);
			  throw new BusinessServiceException(e);
		  } 
		  return value;
	  }
	  
	  public List<Buyer> getMatchedBuyerByNameOrId(String searchIdorName) throws BusinessServiceException{
		  int buyerId = 0;
		  List<Buyer> matchedBuyers = null;
		  Buyer buyer = new Buyer();
		  buyerId = ValidationUtils.isInteger(searchIdorName) ? Integer.valueOf(searchIdorName) : -1;
		  buyer.setBuyerId(buyerId);
		  buyer.setBusinessName(searchIdorName);
		  try{
			  matchedBuyers = buyerDao.queryForMatchedBuyerIdORName(buyer);
		  }catch(DataServiceException dse){
			  logger.error("Exception in BuyerBOImpl getMatchedBuyerByNameOrId() due to "+ dse);
			  throw new BusinessServiceException(dse);
		  }
		 return matchedBuyers;
	  }
	  
		public HashMap<Long, Long> getBuyerRoutingDistance()
				throws BusinessServiceException {
			HashMap<Long, Long> resultMap = new HashMap<Long, Long>();
			try {
				resultMap = buyerDao.getBuyerRoutingDistance();
			} catch (Exception e) {
				logger.error("Exception in BuyerBOImpl getBuyerRoutingDistance() due to "
						+ e);
				throw new BusinessServiceException(e);
			}
			return resultMap;
		}
		
		public BuyerDetailsEventCallbackVO getBuyerCallbackAPIDetails(Integer buyerId) throws BusinessServiceException{
			BuyerDetailsEventCallbackVO buyerDetailsEventCallbackVO = null; 
			try {
				buyerDetailsEventCallbackVO = buyerCallbackEventsCache.fetchBuyerCallbackAPIDetails(buyerId);
			} catch (Exception e) {
				logger.error("Exception in BuyerBOImpl getBuyerRoutingDistance() due to "
						+ e);
				throw new BusinessServiceException(e);
			}
			return buyerDetailsEventCallbackVO;
		  }
		
		public BuyerCallbackEvent getBuyerCallbackEventDetails(Integer buyerId, Integer actionId) throws BusinessServiceException{
			BuyerCallbackEvent buyerCallbackEvent = null; 
			try {
				buyerCallbackEvent = buyerCallbackEventsCache.fetchBuyerCallbackEvent(buyerId.toString(),actionId.toString());
			} catch (Exception e) {
				logger.error("Exception in BuyerBOImpl getBuyerRoutingDistance() due to "
						+ e);
				throw new BusinessServiceException(e);
			}
			return buyerCallbackEvent;
		  }
		
		public BuyerAuthenticationDetailsVO getBuyerAuthenticationdetails(Integer buyerId) throws BusinessServiceException{
			BuyerAuthenticationDetailsVO buyerAuthenticationDetailsVO=null;
			try {
				buyerAuthenticationDetailsVO=buyerDao.getBuyerAuthenticationdetails(buyerId);
			} catch (Exception e) {
				logger.error("Exception in BuyerBOImpl getBuyerAuthenticationdetails() due to "
						+ e);
				throw new BusinessServiceException(e);
			}
			return buyerAuthenticationDetailsVO;
		}
	
	
}
