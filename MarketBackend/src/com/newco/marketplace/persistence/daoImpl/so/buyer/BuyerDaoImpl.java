package com.newco.marketplace.persistence.daoImpl.so.buyer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

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
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderDetail;
import com.newco.marketplace.dto.vo.spn.SPNetHeaderVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.persistence.iDao.so.buyer.BuyerDao;
import com.newco.marketplace.utils.StackTraceHelper;
import com.newco.marketplace.vo.buyer.BuyerRegistrationVO;
import com.newco.marketplace.vo.buyer.PersonalIdentificationVO;
import com.newco.marketplace.vo.buyer.WorkOrderVO;
import com.sears.os.dao.impl.ABaseImplDao;

public class BuyerDaoImpl extends ABaseImplDao implements BuyerDao {

	Logger logger = Logger.getLogger(BuyerDaoImpl.class);

	public Buyer query(Buyer buyer) throws DataServiceException {
		return (Buyer) queryForObject("buyer.query", buyer);
	}
	
	public List<Buyer> queryForMatchedBuyerIdORName(Buyer buyerIdorName) throws DataServiceException{
		List<Buyer> matchedBuyerList = null;
		try{
			matchedBuyerList = queryForList("buyer.queryByIdOrName",buyerIdorName);
		}  catch (Exception ex) {
			logger
			.info("[BuyerDaoImpl.queryForMatchedBuyerIdORName - Exception] "
					+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
		return matchedBuyerList;
	}

	public BuyerResource getQueryByBuyerId(int buyerId)
			throws DataServiceException {
		BuyerResource buyerResource = null;
		try {
			buyerResource = (BuyerResource) getSqlMapClient().queryForObject(
					"BuyerResourceBybuyerId.query", buyerId);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @BuyerdaoImpl.getQueryBybuyerId() due to"
							+ ex.getMessage());
			throw new DataServiceException(
					"SQL Exception @BuyerdaoImpl.getQueryBybuyerId() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @BuyerdaoImpl.getQueryBybuyerId() due to"
							+ ex.getMessage());
			throw new DataServiceException(
					"General Exception @BuyerdaoImpl.getQueryBybuyerId() due to "
							+ ex.getMessage());
		}
		return buyerResource;
	}

	public BuyerDetail getBuyerDetailForServiceOrder(String serviceOrderId)
			throws DataServiceException {
		BuyerDetail buyerDetail = null;
		try {
			ServiceOrderDetail serviceOrderDetail = (ServiceOrderDetail) queryForObject(
					"soId.query", serviceOrderId);
			if (serviceOrderDetail != null)
				buyerDetail = serviceOrderDetail.getBuyerDetails();
		} catch (Exception e) {
			logger
					.error(
							"Exception Occured - BuyerDaoImpl- getBuyerDetailForServiceOrder()",
							e);
			throw new DataServiceException(
					"Exception Occured - BuyerDaoImpl- getBuyerDetailForServiceOrder()",
					e);
		}
		return buyerDetail;
	}

	public BuyerDetail getBuyerDetailForServiceOrder(int serviceOrderId)
			throws DataServiceException {

		return null;
	}

	public ContactLocationVO getBuyer(Buyer buyer) throws DataServiceException {

		ContactLocationVO buy1 = (ContactLocationVO) queryForObject(
				"buyer.querry", buyer);

		return buy1;

	}

	public ContactLocationVO getBuyerResource(BuyerResource buyerResource)
			throws DataServiceException {

		ContactLocationVO buy1 = (ContactLocationVO) queryForObject(
				"buyerResource.query", buyerResource);

		return buy1;
	}

	public ContactLocationVO getBuyerResourceDefaultLocation(Integer resourceId)
			throws DataServiceException {

		ContactLocationVO buy1 = (ContactLocationVO) queryForObject(
				"buyerResource.defaultLoc.query", resourceId);

		return buy1;
	}

	public void updateCardLocationDetails(Buyer buyer)
			throws DataServiceException {
		try {
			update("buyer.updateCardLocation", buyer);
		} catch (Exception ex) {
			logger
					.info("[CreditCardDaoImpl.updateCardLocationDetails - Exception] "
							+ StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}
	}

	public void saveBuyerBlackoutNotification(Integer buyerId,
			Integer resourceId, Integer nodeId, String zip, String userName)
			throws DataServiceException {
		try {
			HashMap hm = new HashMap();
			hm.put("buyerId", buyerId);
			if (resourceId != null)
				hm.put("resourceId", resourceId);
			hm.put("nodeId", nodeId);
			hm.put("zip", zip);
			hm.put("modifiedBy", userName);
			insert("saveBlackoutNotification.insert", hm);
		} catch (Exception ex) {
			logger
					.error(
							"Exception Occured - BuyerDaoImpl- saveBuyerBlackoutNotification()",
							ex);
			throw new DataServiceException(
					"Exception Occured - BuyerDaoImpl- saveBuyerBlackoutNotification()",
					ex);
		}
	}

	public List<BuyerReferenceVO> getBuyerReferenceTypes(Integer buyerId)
			throws DataServiceException {
		List<BuyerReferenceVO> list = null;
		try {
			list = queryForList("select.buyer_reference_types", buyerId);
		} catch (Exception ex) {
			logger
					.error(
							"Exception Occured - BuyerDaoImpl- getBuyerReferenceTypes()",
							ex);
			throw new DataServiceException(
					"Exception Occured - BuyerDaoImpl- getBuyerReferenceTypes()",
					ex);
		}
		return list;
	}

	public List<BuyerReferenceVO> getBuyerReferences(Integer buyerId)
			throws DataServiceException {
		List<BuyerReferenceVO> list = null;
		try {
			list = queryForList("select.buyer_references", buyerId);
		} catch (Exception ex) {
			logger.error(
					"Exception Occured - BuyerDaoImpl- getBuyerReferences()",
					ex);
			throw new DataServiceException(
					"Exception Occured - BuyerDaoImpl- getBuyerReferences()",
					ex);
		}
		return list;
	}

	public List<BuyerReferenceVO> getAllBuyerReferences() throws DataServiceException {
		List<BuyerReferenceVO> list = null;
		try {
			list = queryForList("select.all_buyer_references");
		} catch (Exception ex) {
			logger.error("Exception Occured - BuyerDaoImpl- getBuyerReferences()", ex);
			throw new DataServiceException("Exception Occured - BuyerDaoImpl- getBuyerReferences()", ex);
		}
		return list;
	}

	public List<BuyerReferenceVO> getProviderRefVals(String soId)
			throws DataServiceException {
		List<BuyerReferenceVO> list = null;
		try {
			list = queryForList("select.provider_custom_ref_vals", soId);
		} catch (Exception ex) {
			logger
					.error(
							"Exception Occured - BuyerDaoImpl- getProviderReferences()",
							ex);
			throw new DataServiceException(
					"Exception Occured - BuyerDaoImpl- getProviderReferences()",
					ex);
		}
		return list;
	}

	public List<BuyerReferenceVO> getProviderReferences(String soId)
			throws DataServiceException {
		List<BuyerReferenceVO> list = null;
		try {
			list = queryForList("select.provider_custom_references", soId);
		} catch (Exception ex) {
			logger
					.error(
							"Exception Occured - BuyerDaoImpl- getProviderReferences()",
							ex);
			throw new DataServiceException(
					"Exception Occured - BuyerDaoImpl- getProviderReferences()",
					ex);
		}
		return list;
	}
	
	public boolean isBuyerResourceBuyerAdmin(String userName)
			throws DataServiceException {
		boolean toReturn = false;

		try {
			Integer count = (Integer) queryForObject(
					"select.buyerResourceIsBuyerAdmin", userName);
			if (count.intValue() > 0) {
				toReturn = true;
			}
		} catch (DataAccessException e) {
			logger
					.error(
							"Exception Occured - BuyerDaoImpl- isBuyerResourceBuyerAdmin()",
							e);
			throw new DataServiceException(
					"Exception Occured - BuyerDaoImpl- isBuyerResourceBuyerAdmin()",
					e);
		}
		return toReturn;
	}

	public Buyer insert(Buyer buyer) throws DataServiceException {
		Integer id = null;
		try {
			id = (Integer) getSqlMapClient().insert("new_buyer.insert", buyer);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @BuyerDaoImpl.insert() due to"
					+ ex.getMessage());
			throw new DataServiceException(
					"SQL Exception @BuyerDaoImpl.insert() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @BuyerDaoImpl.insert() due to"
					+ ex.getMessage());
			throw new DataServiceException(
					"General Exception @BuyerDaoImpl.insert() due to "
							+ ex.getMessage());
		}
		buyer.setBuyerId(id.intValue());
		return buyer;
	}

	public int update(Buyer buyer) throws DataServiceException {
		int result = 0;
		try {
			result = getSqlMapClient().update("buyer.update", buyer);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @BuyerDaoImpl.update() due to"
					+ ex.getMessage());
			throw new DataServiceException(
					"SQL Exception @BuyerDaoImpl.update() due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @BuyerDaoImpl.update() due to"
					+ ex.getMessage());
			throw new DataServiceException(
					"General Exception @BuyerDaoImpl.update() due to "
							+ ex.getMessage());
		}
		return result;
	}

	public int updateBuyerCompanyProfile(Buyer buyer)
			throws DataServiceException {
		int result = 0;
		try {
			result = getSqlMapClient().update("buyerCompanyProfile.update",
					buyer);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger
					.info("SQL Exception @BuyerDaoImpl.updateBuyerCompanyProfile due to"
							+ ex.getMessage());
			throw new DataServiceException(
					"SQL Exception @BuyerDaoImpl.updateBuyerCompanyProfile due to "
							+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("General Exception @BuyerDaoImpl.updateBuyerCompanyProfile due to"
							+ ex.getMessage());
			throw new DataServiceException(
					"General Exception @BuyerDaoImpl.updateBuyerCompanyProfile due to "
							+ ex.getMessage());
		}
		return result;
	}

	public Buyer loadData(Buyer buyer) throws DataServiceException {
		Buyer buyerDetail = null;
		try {
			buyer = (Buyer) queryForObject("buyer_company_profile.query", buyer);
		} catch (Exception e) {
			logger.error("Exception Occured - BuyerDaoImpl- loadData()", e);
			throw new DataServiceException(
					"Exception Occured - BuyerDaoImpl- loadData()", e);
		}
		return buyer;
	}

	public int updateBuyerMailing(Buyer buyer) throws DataServiceException {
		int result = 0;
		try {
			result = getSqlMapClient().update("buyer_mailing_location.update",
					buyer);
		} catch (Exception e) {
			logger.error("Exception Occured - BuyerDaoImpl- loadData()", e);
			throw new DataServiceException(
					"Exception Occured - BuyerDaoImpl- loadData()", e);
		}
		return result;
	}
	
	public String getBuyerEIN(Integer buyerId) throws DataServiceException {
		String ein = null;
		try {
			ein = (String) queryForObject("select.buyerEIN", buyerId);
		} catch (Exception ex) {
			logger.error("Exception @BuyerDaoImpl.getBuyerEIN -->", ex);
			throw new DataServiceException(
					"Exception @BuyerDaoImpl.getBuyerEIN -->", ex);
		}
		return ein;
	}
	
	
	public Buyer getBuyerPII(Integer buyerId) throws DataServiceException {
		Buyer buyerPII = null;
		try {
			buyerPII =  (Buyer)queryForObject("select.buyerPII", buyerId);
		} catch (Exception ex) {
			logger.error("Exception @BuyerDaoImpl.getBuyerPII -->", ex);
			throw new DataServiceException(
					"Exception @BuyerDaoImpl.getBuyerPII -->", ex);
		}
		return buyerPII;
		
	
	}	
	
	//fetch buyer business name and user name as input for saving in PII history
  	public Buyer getBuyerName(Integer buyerId)throws DataServiceException {
  		Buyer buyerName = null;
  		try {
  			buyerName = (Buyer)queryForObject("select.buyerName", buyerId);
  		}catch (Exception ex) {
			logger.error("Exception @BuyerDaoImpl.getBuyerName -->", ex);
			throw new DataServiceException(
					"Exception @BuyerDaoImpl.getBuyerName -->", ex);
		}
		return buyerName;
  	}

	public void updateBuyerEIN(Buyer buyer) throws DataServiceException {
		int result = 0;
		try {
			result = getSqlMapClient().update("buyerEIN.update", buyer);
		} catch (SQLException sqlex) {
			logger.error("SQL Exception @BuyerDaoImpl.updateBuyerEIN -->",
					sqlex);
			throw new DataServiceException(
					"SQL Exception @BuyerDaoImpl.updateBuyerEIN -->", sqlex);
		} catch (Exception ex) {
			logger.error("Exception @BuyerDaoImpl.updateBuyerEIN -->", ex);
			throw new DataServiceException(
					"Exception @BuyerDaoImpl.updateBuyerEIN -->", ex);
		}
	}

	public void updateBuyerSSN(Buyer buyer) throws DataServiceException {
		try {
			getSqlMapClient().update("buyerSSN.update", buyer);
		} catch (SQLException sqlex) {
			logger.error("SQL Exception @BuyerDaoImpl.updateBuyerSSN -->",
					sqlex);
			throw new DataServiceException(
					"SQL Exception @BuyerDaoImpl.updateBuyerSSN -->", sqlex);
		} catch (Exception ex) {
			logger.error("Exception @BuyerDaoImpl.updateBuyerSSN -->", ex);
			throw new DataServiceException(
					"Exception @BuyerDaoImpl.updateBuyerSSN -->", ex);
		}
	}

	public void updateBuyerAltId(Buyer buyer) throws DataServiceException{
		try {
			getSqlMapClient().update("buyerAltId.update", buyer);
		} catch (SQLException sqlex) {
			logger.error("SQL Exception @BuyerDaoImpl.updateBuyerAltId -->",
					sqlex);
			throw new DataServiceException(
					"SQL Exception @BuyerDaoImpl.updateBuyerAltId -->", sqlex);
		} catch (Exception ex) {
			logger.error("Exception @BuyerDaoImpl.updateBuyerAltId -->", ex);
			throw new DataServiceException(
					"Exception @BuyerDaoImpl.updateBuyerAltId -->", ex);
		}
	}
	
	public boolean checkBlackoutState(String stateCd)
			throws DataServiceException {
		Integer i = 0;
		boolean flag = false;
		try {
			i = (Integer) queryForObject("blackoutState.count", stateCd);
			if (i > 0) {
				flag = true;
			}
		} catch (Exception e) {
			logger.error("Exception @ BuyerDaoImpl.checkBlackoutState");
			throw new DataServiceException(
					"Exception @ BuyerDaoImpl.checkBlackoutState");
		}

		return flag;
	}

	public void saveBlackoutBuyerLead(BuyerRegistrationVO buyerRegVO)
			throws DataServiceException {
		try {
			insert("blackout_buyerLead.insert", buyerRegVO);
		} catch (Exception e) {
			logger.error("Exception @ BuyerDaoImpl.saveBlackoutBuyerLead");
			throw new DataServiceException(
					"Exception @ BuyerDaoImpl.saveBlackoutBuyerLead");
		}

	}

	public List<String> getBlackoutStateCodes() throws DataServiceException {
		List<String> stateCds = new ArrayList<String>();
		try {
			stateCds = queryForList("blackoutStates.select", null);
		} catch (Exception e) {
			logger.error("Exception@BuyerDaoImpl.getBlackoutstateCodes");
			throw new DataServiceException(
					"Exception@BuyerDaoImpl.getBlackoutstateCodes");
		}
		return stateCds;
	}

	public Integer getBuyerRole(String username) throws DataServiceException {
		Integer roleId = null;
		try {
			// code change for SLT-2112
			Map<String, String> parameter = new HashMap<String, String>();
					parameter.put("username", username);
			 roleId = (Integer) queryForObject("buyerRole.query", parameter);
		} catch (Exception e) {
			logger.error("Exception@BuyerDaoImpl.getBuyerRole");
		}
				return roleId;

	}

	public int insertBuyerCustomReference(BuyerReferenceVO buyerRefVO) {
		Integer id = null;
		try {
			id = (Integer) getSqlMapClient().insert("new_buyer_ref.insert",
					buyerRefVO);
		} catch (SQLException ex) {
			ex.printStackTrace();
			logger.info("SQL Exception @new_buyer_ref.insert() due to"
					+ ex.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @new_buyer_ref.insert() due to"
					+ ex.getMessage());
		}
		return id.intValue();

	}

	public void deleteBuyerCustomReference(BuyerReferenceVO buyerRefVO) {
		Integer id = null;
		try {
			update("buyer_ref_active.update", buyerRefVO);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @buyer_ref_active.update() due to"
					+ ex.getMessage());
		}
	}

	public void updateBuyerCustomReference(BuyerReferenceVO buyerRefVO) {
		try {
			update("buyer_ref.update", buyerRefVO);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @buyer_ref.update() due to"
					+ ex.getMessage());
		}
	}
	
	public OFACProcessQueueVO getEntityInfo(Integer entityId,String userType) throws DataServiceException
	{
		OFACProcessQueueVO entityInfo = new OFACProcessQueueVO();
		try
		{
			entityInfo.setEntityId(entityId);
			if(userType.equals(LedgerConstants.USER_TYPE_BUYER))
				entityInfo= (OFACProcessQueueVO) queryForObject("entityStateCodeBuyer.query", entityInfo);
			else
				entityInfo= (OFACProcessQueueVO) queryForObject("entityStateCodeProvider.query", entityInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("General Exception @BuyerDaoImpl.getEntityStateCode() due to"
					+ e.getMessage());
			throw new DataServiceException(
			"Exception@BuyerDaoImpl.getEntityStateCode");
		}
		return entityInfo;
	}
	
	public SOEntityInfoVO getSoBuyerVendorEntities(String soId) throws DataServiceException
	{
		try
		{
			return (SOEntityInfoVO) queryForObject("soBuyerVendorEntities.query",soId);
		}
		catch (Exception ex) {
			ex.printStackTrace();
			logger
					.info("Data access exception @BuyerDaoImpl.getSoBuyerVendorEntities due to"
							+ ex.getMessage());
			throw new DataServiceException(
					"Data access exception @BuyerDaoImpl.getSoBuyerVendorEntities due to "
							+ ex.getMessage());
		}
	}
	
	public OFACProcessQueueVO getEntityInfoForActivation(Integer entityId,String userType) throws DataServiceException
	{
		OFACProcessQueueVO entityInfo = new OFACProcessQueueVO();
		try
		{
			entityInfo.setUserType(userType);
			entityInfo.setEntityId(entityId);
			entityInfo= (OFACProcessQueueVO) queryForObject("entityStateCodeForActivation.query", entityInfo);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.info("General Exception @BuyerDaoImpl.getEntityInfoForActivation() due to"
					+ e.getMessage());
			throw new DataServiceException(
			"Exception@BuyerDaoImpl.getEntityInfoForActivation");
		}
		return entityInfo;
	}
	/**
	 * Get Contact and Location details for the Buyer Contact associated with the SO
	 * @param so_id
	 * @return ContactLocationVO
	 */
	public ContactLocationVO getBuyerResContactLocationForSO(String soId) throws DataServiceException{
		try{
			ContactLocationVO buyerContactInfo = (ContactLocationVO) queryForObject(
					"buyer.getBuyerResContactLocationForSO", soId);
			return buyerContactInfo;
		}catch(Exception e){
			logger.info("General Exception @BuyerDaoImpl.getBuyerResContactLocationForSO() due to"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	/**
	 * Get Contact and Location details for the Buyer Contact associated with the SO
	 * @param so_id
	 * @return ContactLocationVO
	 */
	public ContactLocationVO getBuyerResContactLocationForContactId(Integer contactId) throws DataServiceException{
		try{
			// code change for SLT-2112
			Map<String, Integer> parameter = new HashMap<String, Integer>();
					parameter.put("contactId", contactId);
			ContactLocationVO buyerContactInfo = (ContactLocationVO) queryForObject(		
				"buyer.getBuyerResContactLocationForContactId", parameter);
			return buyerContactInfo;
		}catch(Exception e){
			logger.info("General Exception @BuyerDaoImpl.getBuyerResContactLocationForContactId() due to"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	public void lockBuyer(Integer buyerId) {
		// code change for SLT-2112
		Map<String, Integer> parameter = new HashMap<String, Integer>();
		parameter.put("buyerId", buyerId);
		insert("replace.lockBuyer", parameter);
	}
	
	@SuppressWarnings("unchecked")
	public List<PersonalIdentificationVO> getPiiHistory(Integer buyerId)throws DataServiceException{
		try {
			 List<PersonalIdentificationVO>  resultMap = new ArrayList<PersonalIdentificationVO>();
			 resultMap =queryForList("pii.getHistory", buyerId);
			return resultMap;

		} catch (Exception exception) {
			logger.error("Exception Caught:"+exception);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public BuyerResource getBuyerResource(int buyerId,Integer resourceId) throws DataServiceException{
		try{
			HashMap hm = new HashMap();
			hm.put("buyerId", buyerId);
			hm.put("resourceId", resourceId);
			BuyerResource BuyerResource = (BuyerResource) queryForObject("buyer.getBuyerResource", hm);
			return BuyerResource;
		}catch(Exception e){
			logger.info("General Exception @BuyerDaoImpl.getBuyerResContactLocationForSO() due to"
					+ e.getMessage());
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	
	/******* Begin SL 18041 ******/
	/**
	 * This method returns the list of valid SKUs present in the list of provided
	 * SKUs or empty list, if all given SKUs are invalid.
	 * @param workOrderVO {@link WorkOrderVO} : contains the buyer id and list of skus to be validated.
	 * @return invalidSKUs : List of valid SKUs
	 * @throws DataServiceException
	 * */

	@SuppressWarnings("unchecked")
	public List<String> getValidWorkOrderSKUs(WorkOrderVO workOrderVO) throws DataServiceException
	{
		List<String> invalidSKUs;
		try{	
			invalidSKUs = queryForList("buyer.getValidSKUs", workOrderVO);
		}catch(Exception e){
			logger.error(this.getClass().getName()+" : getValidWorkOrderSKUs :"+ e);
			throw new DataServiceException(e.getMessage(),e);
		}
		return null==invalidSKUs?new ArrayList<String>():invalidSKUs;
	}
	
	/**
	 * Returns a list of task Object for given sku names and buyerId
	 * @param workOrderVO {@link WorkOrderVO} : contains the buyer id and list of skus
	 * @return {@link ManageTaskVO}
	 * @throws DataServiceException
	 * */
	public List<ManageTaskVO> populateTaskFromSKUs(WorkOrderVO workOrder) throws DataServiceException
	{
		List<ManageTaskVO> taskList;
		try
		{
			ManageTaskVO result = null;
			Integer buyerId = workOrder.getBuyerId();
			List<String> skus = workOrder.getWorkOrderSKUs();
			WorkOrderVO workOrderVO = new WorkOrderVO();
			workOrderVO.setBuyerId(buyerId);
			taskList = new ArrayList<ManageTaskVO>();
			for(String sku : skus){
				workOrderVO.setSkuName(sku);
				result = (ManageTaskVO) queryForObject("buyer.populateTaskFromSKUs", workOrderVO );
				if(null != result){
					taskList.add(result);
				}
			}
			return taskList;
		}
		catch(Exception e){
			logger.error(this.getClass().getName()+" : populateTaskFromSKUs :"+ e);
			throw new DataServiceException(e.getMessage(),e);
		}
	}
	/******* End SL 18041 ******/
	
	public List<RoutedProvider> getCompletedDate(List<Integer> duplicateList, String criteriaLevel, Integer buyerId){
		List<RoutedProvider> dateList = new ArrayList<RoutedProvider>();
		Date completedDate = null;
		Date date = new Date(2000, 01, 01);
		try{
			if(null != criteriaLevel && criteriaLevel.equals("FIRM")){
				for(Integer firmId : duplicateList){
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("firmId", firmId);
					params.put("buyerId", buyerId);
					
					completedDate = (Date)getSqlMapClient().queryForObject("fetchFirmCompletedDate.query", params);
					RoutedProvider rp = new RoutedProvider();
					rp.setVendorId(firmId);
					if(null != completedDate){
						rp.setCompletedDate(completedDate);
					}else{
						rp.setCompletedDate(date);
					}
					dateList.add(rp);
				}
			}
			else{
				for(Integer provId : duplicateList){
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("provId", provId);
					params.put("buyerId", buyerId);
					
					completedDate = (Date)getSqlMapClient().queryForObject("fetchProvCompletedDate.query", params);
					RoutedProvider rp = new RoutedProvider();
					rp.setResourceId(provId);
					if(null != completedDate){
						rp.setCompletedDate(completedDate);
					}else{
						rp.setCompletedDate(date);
					}
					dateList.add(rp);
				}
			}
			
		}catch(Exception e){
			logger.error("Exception in SPNRoutingPriorityDaoImpl.getCompletedDate() due to "+ e.getMessage());
		}
		return dateList;
	}
	
	public SPNetHeaderVO getSpnDetails(Integer spnId){
		SPNetHeaderVO hdr = null;
		try{
			hdr = (SPNetHeaderVO)getSqlMapClient().queryForObject("fetchPerfCriteriaLevel.query", spnId);
		}
		catch(Exception e){
			logger.error("Exception in BuyerDaoImpl getCriteriaLevel() due to "+ e);
		}
		//return (criteriaLevel==null?"N/A":criteriaLevel);
		return hdr;
	}
	
	public void deleteTierEligibleProviders(String soId){
		try{
			
		getSqlMapClient().delete("deleteRoutedResources.query", soId);
		}catch(Exception e){
			logger.error("Exception in BuyerDaoImpl deleteRoutedResources() due to "+ e);
		}
		
	}
	
	/**to fetch the custom reference value
	  * @param soId
	  * @param coverageTypeLabor
	  */
	public String getCustomReference(String soId, String referenceType){
		String value = null;
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("soId", soId);
		params.put("refType", referenceType);
		
		try{
			value = (String)getSqlMapClient().queryForObject("getValueForRef.query", params);
			
		}catch(Exception e){
			logger.error("Exception in BuyerDaoImpl getCustomReference() due to "+ e);
		}
		return value;
	}
	public String getFeature(Long buyerID, String feature) throws DataServiceException {
		String foundFeature = null;
		try{
			Map<String, String> params = new HashMap<String, String>();
			params.put("buyerID", String.valueOf(buyerID.intValue()));
			params.put("feature", feature);
			foundFeature = (String) getSqlMapClient(). queryForObject("buyerFeatuerSet.getFeature", params);
    	}catch (SQLException ex) {
            ex.printStackTrace();
		     logger.info("SQL Exception @BuyerFeatureSetDAOImpl.getFeature() due to"+ex.getMessage());
		     throw new DataServiceException("SQL Exception @BuyerFeatureSetDAOImpl.getFeature() due to "+ex.getMessage());
       }catch (Exception ex) {
           ex.printStackTrace();
		     logger.info("General Exception @BuyerFeatureSetDAOImpl.getFeature() due to"+ex.getMessage());
		     throw new DataServiceException("General Exception @BuyerFeatureSetDAOImpl.getFeature() due to "+ex.getMessage());
      }
		return foundFeature;
	}

	public Integer getByerAdminContactIdbyResourceId(Integer resourceId)
			throws DataServiceException {

		Integer contactId = null;
		try {
			contactId = (Integer) queryForObject(
					"adminBuyerContactByResourceId.query", resourceId);
		} catch (Exception e) {
			logger.error("Exception in getting Admin Contact Id " + e);
			throw new DataServiceException(null);

		}
		return contactId;
	}
	
	//SL-18825
	//To fetch the buyer custom references with attribute 'display_no_value',which is set to 1
	public List<BuyerReferenceVO> getBuyerCustomReferenceWithDisplayNoValue(Integer buyerId)
			throws DataServiceException {
		List<BuyerReferenceVO> list = null;
		try {
			list = queryForList("select.all_buyer_references_display_no_value", buyerId);
		} catch (Exception ex) {
			logger
					.error(
							"Exception Occured - BuyerDaoImpl- getBuyerCustomReferenceWithDisplayNoValue()",
							ex);
			throw new DataServiceException(
					"Exception Occured - BuyerDaoImpl- getBuyerCustomReferenceWithDisplayNoValue()",
					ex);
		}
		return list;
	}
	
    //changes for Buyer admin name starts SL-20461
	
	
	public boolean updateBuyerAdmin (BuyerRegistrationVO buyerRegistrationVO)
	{
		
		logger.info("Inside BuyerDaoImpl---updateBuyerAdmin function");
		Map<String,String> changeAdminNameMap=new HashMap<String,String>();
        Map<String,String> auditChangeAdminNameMap=new HashMap<String,String>();
		changeAdminNameMap.put("in_newAdminUser_name", buyerRegistrationVO.getNewAdminUserName());
		changeAdminNameMap.put("in_oldAdminUser_name", buyerRegistrationVO.getCurrentAdminUserName());
		changeAdminNameMap.put("in_buyer_id", buyerRegistrationVO.getBuyerId().toString());
		
        auditChangeAdminNameMap.put("OldAdminName",buyerRegistrationVO.getCurrentAdminLastName()+","+buyerRegistrationVO.getCurrentAdminFirstname()+"("+buyerRegistrationVO.getCurrentAdminResourceId()+"#)");
        auditChangeAdminNameMap.put("NewAdminName",buyerRegistrationVO.getNewAdminLastName()+","+buyerRegistrationVO.getNewAdminFirstName()+"("+buyerRegistrationVO.getNewAdminResourceId()+"#)");
        auditChangeAdminNameMap.put("Buyer_Id", buyerRegistrationVO.getBuyerId().toString());
        auditChangeAdminNameMap.put("modified_by",buyerRegistrationVO.getModifiedBy());
        auditChangeAdminNameMap.put("associated_entity", "Buyer Firm");
        
        try {
			getSqlMapClient().queryForObject("updateBuyerAdminName.procedure",changeAdminNameMap);
			logger.info("returned succesfully from BuyerAdminChange Procedure");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		logger.info("Sucessfully Changed Admin: From: "+buyerRegistrationVO.getCurrentAdminUserName()+" To: "+buyerRegistrationVO.getNewAdminUserName());
		try {
			getSqlMapClient().insert("auditBuyerAdminNameChange.insert", auditChangeAdminNameMap);
			logger.info(" Data inserted in audit_buyer_record ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		logger.info("Sucessfully inserted data in Table audit_buyer_record");
        
        
		return true;
		
	}
	
	
	//changes for Buyer admin name ends	SL-20461	
	
	public HashMap<Long, Long> getBuyerRoutingDistance()
			throws DataServiceException {
		HashMap<Long, Long> resultMap = new HashMap<Long, Long>();
		try {
			resultMap = (HashMap<Long, Long>) queryForMap("buyerRoutingDistance.query", null, "buyerId", "distance");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("General Exception @new_buyer_ref.insert() due to"
					+ ex.getMessage());
		}
		return resultMap;
	}
	
	public BuyerAuthenticationDetailsVO getBuyerAuthenticationdetails(Integer buyerId) throws DataServiceException {
		BuyerAuthenticationDetailsVO buyerAuthenticationDetailsVO = null;
		try{
			buyerAuthenticationDetailsVO = (BuyerAuthenticationDetailsVO)getSqlMapClient().queryForObject("fetchBuyerAuthenticationDetails.query", buyerId);
		}
		catch(Exception e){
			logger.error("Exception in BuyerDaoImpl getBuyerAuthenticationdetails() due to "+ e);
		}
		
		return buyerAuthenticationDetailsVO;
	}
	
	//changes for SLT-1444
	public void initialiseMarkets(Integer in_buyer_id) throws DataServiceException {
		try{
			getSqlMapClient().insert("insertMarketsForBuyer.insert",in_buyer_id);
			logger.info("returned succesfully from initialiseMarkets Procedure");
		}
		catch(Exception e){
			logger.error("Exception in BuyerDaoImpl initialiseMarkets() due to "+ e);

		}
	}

	//SLT-1805:Insert default entry for all the feature sets at buyer registration for new buyer
	public void addBuyerFeatureSetWithDefaultValue(Integer buyer_id) throws DataServiceException {
		try{
			getSqlMapClient().insert("insertBuyerFeatureDfltValue.insert",buyer_id);
			logger.info("Succesfully added buyerfeatureset with default value for new buyers");
		}
		catch(Exception e){
			logger.error("Exception in BuyerDaoImpl addBuyerFeatureSetWithDefaultValue() due to "+ e);

		}
	}
	
	public void insertBuyerCancellationRefund(List<BuyerCancellationPostingFeeVO> buyerCancellationRefundList)
			throws DataServiceException {
		try {
			batchInsert("BuyerCancellationRefundDetails.insert", buyerCancellationRefundList);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		logger.info("buyer cancellation record executed");
	}
	
}


