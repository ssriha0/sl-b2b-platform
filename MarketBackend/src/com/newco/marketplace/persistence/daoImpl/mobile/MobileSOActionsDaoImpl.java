package com.newco.marketplace.persistence.daoImpl.mobile;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.api.mobile.beans.sodetails.AddonPayment;
import com.newco.marketplace.api.mobile.beans.sodetails.PermitAddon;
import com.newco.marketplace.api.mobile.beans.sodetails.PermitTask;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.AddOn;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.AddOnPaymentDetails;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.AddOnPaymentHistoryDetails;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Pricing;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Signature;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.dto.vo.serviceorder.SoTripVo;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.persistence.iDao.mobile.IMobileSOActionsDao;
import com.newco.marketplace.vo.mobile.BuyerTemplateMapperVO;
import com.newco.marketplace.vo.mobile.CustomReferenceVO;
import com.newco.marketplace.vo.mobile.DocIdVO;
import com.newco.marketplace.vo.mobile.InvoicePartsVO;
import com.newco.marketplace.vo.mobile.PartsVO;
import com.newco.marketplace.vo.mobile.ProviderHistoryVO;
import com.newco.marketplace.vo.mobile.SOPartLaborPriceReasonVO;
import com.newco.marketplace.vo.mobile.TaskVO;
import com.newco.marketplace.vo.mobile.TemplateDetailsVO;
import com.newco.marketplace.vo.mobile.UpdateApptTimeVO;
import com.newco.marketplace.vo.mobile.UserDetailsVO;
import com.newco.marketplace.vo.mobile.v2_0.LpnVO;
import com.newco.marketplace.vo.mobile.v2_0.SORevisitNeededVO;
import com.newco.marketplace.vo.mobile.v2_0.SOTripDetailsVO;
import com.newco.marketplace.vo.mobile.v2_0.SupplierVO;
import com.sears.os.dao.impl.ABaseImplDao;

public class MobileSOActionsDaoImpl extends ABaseImplDao implements IMobileSOActionsDao{

	//To validate resourceId
	public Integer validateResourceId(Integer resourceId) throws DataServiceException {
		Integer resId = 0;
		try {
			resId = (Integer)queryForObject("validateResourceId.query", resourceId);
		} catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsDAOImpl-->validateResourceId()");
			throw new DataServiceException(ex.getMessage());
		}
		return resId;
	}

	//To validate soId
	public ServiceOrder getServiceOrder(String soId) throws DataServiceException {
		ServiceOrder so= new ServiceOrder();
		try {
			// code change for SLT-2112
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("soId", soId);
			so = (ServiceOrder)queryForObject("getServiceOrder.query", parameter);
		} catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->getServiceOrder()");
			throw new DataServiceException(ex.getMessage());
		}
		return so;
	}

	//To validate the association of soId and providerId
	public String toValidateSoIdForProvider(Map<String, String> validateMap) throws DataServiceException {
		String soId="";
		try {
			soId = (String)queryForObject("toValidateSoIdForProvider.query", validateMap);
		} catch (Exception ex) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->toValidateSoIdForProvider()");
			throw new DataServiceException(ex.getMessage());
		}
		return soId;
	}

	//To insert Note into so_notes 
	public ServiceOrderNote insertAddNote(ServiceOrderNote note)throws DataServiceException {
		int noteId = (Integer) insert("soNote.insert", note);
		note.setNoteId(new Long(noteId));
		return note; 
		
	}
	
	
	/**
	 * fetch vendor business name details according to vendor id
	 * 
	 * @param uniqueNewVendorsList
	 * @return
	 */
	public String getVendorBNameList(List<Integer> uniqueNewVendorsList) {
		logger.info("start getVendorBNameList @ serviceOrderDao");
		try {

			if (null == uniqueNewVendorsList || uniqueNewVendorsList.size() < 1) {
				logger.warn("vendor list is empty.");
				return null;
			}

			List<String> vendorDataList = queryForList("vendorBNameList.query", uniqueNewVendorsList);
			logger.debug("result list : " + vendorDataList);

			if (null == vendorDataList || vendorDataList.size() < 1) {
				return null;
			}

			logger.info("vendorIdsList  size from DB : " + vendorDataList.size());

			JSONArray jsonArray = new JSONArray();

			for (int i = 0; i < vendorDataList.size(); i++) {
				String vendorData = vendorDataList.get(i);

				JSONObject jsonObject = new JSONObject();
				jsonObject.element("firmid", (String) vendorData.split("_")[0]);
				jsonObject.element("firmname", URLEncoder.encode((String) vendorData.substring(vendorData.indexOf("_") + 1), "UTF-8"));
				jsonArray.add(jsonObject);
			}

			logger.debug("result list : " + jsonArray);
			return jsonArray.toString();
		} catch (Exception e) {
			logger.error("Exception in getFirmIdList" + e);
			return null;
		}
	} 	


	/**
	 * 
	 * to get the buyer id  and wfStateId of service order
	 * @param soId
	 * @return
	 */
	//R12.0 SLM-5:method updated for querying wfStateId for NPS notification
	public HashMap<String, Object> getBuyerAndWfStateForSO(String soId) throws DataServiceException{
		// TODO Auto-generated method stub
		HashMap<String, Object>map =new HashMap<String, Object>();
		try{
			// code change for SLT-2112
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("soId", soId);
			map = (HashMap<String, Object>)queryForObject("getBuyerAndwfStateIdForSo.query", parameter);
			
		}catch(Exception e){
			logger.error("Exception occured in getBuyerForServiceOrder() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return map;
	}


	/**
	 * 
	 * check if the order is assigned to firm
	 * 
	 * @param soId
	 * @return
	 */
	public boolean checkIfAssignedToFirm(String soId) throws DataServiceException{
		// TODO Auto-generated method stub
		boolean assignedToFirmInd =  Boolean.FALSE;
		try{
			
			String assignmentType = (String)queryForObject("getAssignmentTypeForSo.query", soId);
			if(null != assignmentType && assignmentType.equals(OrderConstants.SO_ASSIGNMENT_TYPE_FIRM)){
				assignedToFirmInd = true;
			}
			
		}catch(Exception e){
			logger.error("Exception occured in checkIfAssignedToFirm() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return assignedToFirmInd;
	}


	/**
	 * to get mandatory custom references
	 * 
	 * @param soId
	 * @return
	 */
	public List<BuyerReferenceVO> getRequiredBuyerReferences(String soId,  boolean mandatoryInd)
			throws DataServiceException {
		List<BuyerReferenceVO> mandatoryReferences =  null;
		HashMap<String, String> map = new HashMap<String, String>();
		try{
			map.put("soId", soId);
			if(mandatoryInd){
				map.put("mandatoryInd", "1");

			}
			else{
				map.put("mandatoryInd", "0");

			}
				mandatoryReferences = (List<BuyerReferenceVO>)queryForList("getMandatoryReferencesForSo.query", map);
				
			}catch(Exception e){
				logger.error("Exception occured in getRequiredBuyerReferences() due to "+e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			return mandatoryReferences;
	}


	/**
	 * @param partCoverage
	 * @param partSource
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public HashMap<String, Double> getReimbursementRate(String partCoverage, String partSource) throws DataServiceException {
		HashMap<String, Double> buyerPriceCalcValues = new HashMap<String, Double>();
		HashMap<String, String> map = new HashMap<String, String>();
		try{
			map.put("coverage", partCoverage);
			map.put("source", partSource);

			buyerPriceCalcValues = (HashMap<String, Double>)queryForObject("getReimbursmentRate.query", map);
			
			
		}catch(Exception e){
			logger.error("Exception occured in getReimbursementRate() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return buyerPriceCalcValues;
	}
	
	public HashMap<String, String> getAutoAdjudicationValues() throws DataServiceException{
		HashMap <String, String> autoAdjudicationValues = new HashMap <String, String>();
		try{
			//autoAdjudicationValues =(HashMap <String, String>) queryForList("getAutoAdjudicationValues.query",null);
			
			autoAdjudicationValues = (HashMap<String, String>) queryForMap("getAutoAdjudicationValues.query",null,"app_constant_key","app_constant_value");

			
		}
		catch(Exception e){
			logger.info("Exception occurred in getAutoAdjudicationValues"+e);
			throw new DataServiceException("Exception occurred in getAutoAdjudicationValues :"+e.getMessage(),e);
		} 
		return autoAdjudicationValues;
	}

	
	
	
	public InvoicePartsVO getBuyerPartsPriceCalculationValues(String partCoverage, String partSource) throws DataServiceException{

		InvoicePartsVO invoicePartsVO = new InvoicePartsVO();
		HashMap<String, String> map = new HashMap<String, String>();
		try{
			map.put("coverage", partCoverage);
			map.put("source", partSource);
			invoicePartsVO = (InvoicePartsVO)queryForObject("getBuyerPartsPriceCalcValues.query", map);
			
		}catch(Exception e){
			logger.error("Exception occured in getBuyerPartsPriceCalculationValues() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return invoicePartsVO;
	
	}
	
	
	
	
	/**
	 * @param map
	 * @return
	 */
	public int updateSoSubStatus(HashMap<String, Object> map) throws DataServiceException{
			return update("soSubStatusData.update", map); 
	}
	/**
	 * @param map
	 * @return
	 */
	
	public int updateSoResolutionComments(HashMap<String, Object> map) throws DataServiceException{
			return update("soResolutionComments.update", map); 
	}
	/**
	 * @param addOn
	 * @return
	 */
	public void updateAddon(AddOn addOn) throws DataServiceException{
	
		try{
			update("soAddonData.update", addOn);
		
		}
		catch(Exception e){
			logger.error("Exception occured in updateAddon() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	/**
	 * @param paymentDetails
	 * @return
	 */
	public void updatePaymentDetails(AddOnPaymentDetails paymentDetails) throws DataServiceException{
		try{			
			update("soAdditionalPaymentDetails.update", paymentDetails); 	
			}
		catch(Exception e){
			logger.error("Exception occured in updatePaymentDetails() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}			
	}
	/**
	 * @param paymentDetails
	 * @return
	 */
	public void insertPaymentDetails(AddOnPaymentDetails paymentDetails)
			throws DataServiceException {
		try{
			deleteAdditionalPaymentDetails(paymentDetails.getSoId());
			if(paymentDetails.getPaymentType().equals(MPConstants.PAYMENT_TYPE_CHECK))
			{
			insert("soAdditionalPaymentDetails.check.insert",paymentDetails);
			}
			else{
				
			insert("soAdditionalPayment.insert",paymentDetails);
			}
		
		}
		catch(Exception e){
			logger.error("Exception occured in insertPaymentDetails() due to "+e.getMessage());
			e.printStackTrace();
			throw new DataServiceException(e.getMessage());
		}	
	}
	
	/**
	 * 
	 * to update so_additional_payment_history
	 * 
	 * @param addOnPaymentHistoryDetails
	 * @return
	 */
	public void insertPaymentHistoryDetails(AddOnPaymentHistoryDetails addOnPaymentHistoryDetails) throws DataServiceException{
		try{
			insert("soAdditionalPaymentHistory.insert",addOnPaymentHistoryDetails);
		}catch(Exception e){
			logger.error("Exception occured in insertPaymentHistoryDetails() due to "+e.getMessage());
			e.printStackTrace();
			throw new DataServiceException(e.getMessage());
		}	
	}
	
	public void deleteAdditionalPaymentDetails(String soID)
	{
		delete("soAdditionalPayment.delete", soID);
	}
	/**
	 * @param pricing
	 * @return
	 */
	public void updatePrisingDetails(Pricing pricing) throws DataServiceException{
		try{
			update("soPrisingDetails.update", pricing); 	 
		
		}
		catch(Exception e){
			logger.error("Exception occured in updatePrisingDetails() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}	
	}
	/**
	 * @param cardType
	 * @return
	 */
	public Integer getCreditCardID(String  cardType) throws DataServiceException{
		// TODO Auto-generated method stub
		Integer cardId =null;
		try{
			cardId = (Integer)queryForObject("getCreditCardID.query", cardType);
			
		}catch(Exception e){
			logger.error("Exception occured in getCreditCardID() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return cardId;
		
	}
	/**
	 * to get corresponding payment type   * 
	 * @param paymentType
	 * @return
	 */
	public String getCreditCardAlias(String paymentType) throws DataServiceException {
		// TODO Auto-generated method stub
		String type =  null;
		try{
			type = (String)queryForObject("getcreditCardAlias.query", paymentType);
		}
		catch(DataAccessException e){
			logger.error("Exception occured in getCreditCardAlias() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return type;
	}
	/**
	 * to get corresponding card type id for card type	 * 
	 * @param ccType
	 * @return
	 */
	public Integer getCardType(String ccType) throws DataServiceException {
		// TODO Auto-generated method stub
		Integer creditCardTypeId =  null;
		try{
			
			creditCardTypeId = (Integer)queryForObject("getCardType.query", ccType);
			
		}
		catch(DataAccessException e){
			logger.error("Exception occured in getCardType() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return creditCardTypeId;
	}
	
	/**
	 * 
	 * to update Task Details
	 * 
	 * @param TaskVO
	 * @return
	 */
	public void updateTaskDetails(TaskVO taskVO) throws DataServiceException {
		// TODO Auto-generated method stub
		try{
			update("updateTaskDetails.update",taskVO);
		}
		catch(DataAccessException e){
			logger.error("Exception occured in updatingTaskDetails due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	/**
	 * 
	 * to get carrier id
	 * 
	 * @param carrier
	 * @return
	 */
	public Integer getCarrierId(String carrier) throws DataServiceException {
		// TODO Auto-generated method stub
		Integer carrierId =  null;
		try{
			
			carrierId = (Integer)queryForObject("getCarrierId.query", carrier);
			
		}
		catch(DataAccessException e){
			logger.error("Exception occured in getCarrierId() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return carrierId;
	}
	/**
	 * 
	 * to update Parts Details
	 * 
	 * @param PartsVO
	 * @return
	 */
	public void updatePartsDetails(PartsVO pvo)  throws DataServiceException {
		try{
			update("updatePartsDetails.update",pvo);
		}
		catch(DataAccessException e){
			logger.error("Exception occured in updatePartsDetails due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	/**
	 * 
	 * to insert Invoice part Details
	 * 
	 * @param InvoicePartsVO
	 * @return
	 */
	public void insertInvoicePartsDetails(InvoicePartsVO invVO) throws DataServiceException{
		try{
			insert("insertInvoicePartsDetails.insert",invVO);
		}
		catch(DataAccessException e){
			logger.error("Exception occured in insertInvoicePartsDetails due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	/**
	 * 
	 * to insert AddNote History
	 * 
	 * @param ProviderHistoryVO
	 * @return
	 */
	public void insertAddNoteHistory(ProviderHistoryVO hisVO)throws DataServiceException {
		try{
			insert("insertAddNoteHistory.insert",hisVO);
		}
		catch(DataAccessException e){
			logger.error("Exception occured in insertAddNoteHistory due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		
	}

	/**
	 * @param soId
	 */
	@SuppressWarnings("unchecked")
	public List<CustomReferenceVO> getCustomReferncesForSO(String soId) throws DataServiceException {
		// TODO Auto-generated method stub
		List<CustomReferenceVO> customReferenceVOs = new ArrayList<CustomReferenceVO>();
		try{
			
			customReferenceVOs = (List<CustomReferenceVO>)queryForList("getCustomeReferences.query", soId);
			
		}
		catch(DataAccessException e){
			logger.error("Exception occured in getCustomReferncesForSO() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		
		return customReferenceVOs;
		
	}


	/**
	 * @param customReferenceVOs
	 * @throws DataServiceException
	 */
	public void addCustomeReferences(List<CustomReferenceVO> customReferenceVOs)
			throws DataServiceException {
		// TODO Auto-generated method stub
		try{
			for(CustomReferenceVO customReferenceVO :customReferenceVOs){
				if(null!=customReferenceVO){
					insert("insertCustomReference.query", customReferenceVO);
				}

			}
		}
		catch(DataAccessException e){
			logger.error("Exception occured in addCustomeReferences due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	
	/**
	 * @param referenceVO
	 * @throws DataServiceException
	 */
	public void updateCustomRefValue(CustomReferenceVO referenceVO)
			throws DataServiceException {
		// TODO Auto-generated method stub
		try{
				if(null!=referenceVO){
					insert("updateCustomReference.query", referenceVO);
				}

		}
		catch(DataAccessException e){
			logger.error("Exception occured in updateCustomRefValue due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}


	/**
	 * @param resourceId
	 * @return
	 */
	public Contact getVendorResourceContact(int resourceId)
			throws DataServiceException {
	    Contact returnVal = new Contact();
	    returnVal = (Contact)queryForObject("mobile.vendorResourceContactInfoFromResourceId.query", resourceId);
		   return returnVal;
	}
	/**
	 * To check if there is entry in so additional payment
	 * 
	 * @param addonPayment	
	 * @return boolean
	 * @throws DataServiceException
	 */
	
	public boolean isAdditionalPaymentPresent(String soId) throws DataServiceException{
		// TODO Auto-generated method stub
		boolean isPresent = Boolean.FALSE;
		int count = 0;
		try {			
			count = (Integer) queryForObject("selectAdditionalPayment.query",soId);
			if (count ==1) {
				isPresent = true;
			}
		} catch (Exception e) {
			logger.error("Exception occured in isAdditionalPaymentPresent() due to "
					+ e.getMessage());
		}
		return isPresent;
	}

	/**
	 * @param soId
	 * @return
	 */
	public boolean checkIfSOIsActive(String soId) {
		String id = (String)queryForObject("mobile.checkIfSOIsActive.query", soId);
		if(null!=id){
			return true;
		}
		return false;
		
	}


	/**
	 * @param reasonVOs
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void updateLabourPartPriceReasons(
			List<SOPartLaborPriceReasonVO> reasonVOs) throws DataServiceException{

		try{
		
		for(SOPartLaborPriceReasonVO laborPriceReasonVO : reasonVOs){

			Integer id = (Integer)queryForObject("mobile.checkIfReasonAlreadyExists", laborPriceReasonVO);
			if((MPConstants.LABOR).equals(laborPriceReasonVO.getPriceType())){
				if(id!=null){
					laborPriceReasonVO.setReasonId(id.intValue());
					update("mobile.updateReason.query", laborPriceReasonVO);
				}
				else{
					
					insert("mobile.inserReason.query",laborPriceReasonVO);
				}
			}
			
			else if((MPConstants.PARTS).equals(laborPriceReasonVO.getPriceType())){
				if(id!=null){
					laborPriceReasonVO.setReasonId(id.intValue());
					update("mobile.updateReason.query", laborPriceReasonVO);
				}
				else{
					
					insert("mobile.inserReason.query",laborPriceReasonVO);
				}
			}
		}
		}catch(DataAccessException e){
			logger.error("Exception occured in updateLabourPartPriceReasons due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
			}
		
	}
	
	public void insertSignatureDetails(Signature signature, String soId, String signatureType)
			throws DataServiceException {
		// TODO Auto-generated method stub
		HashMap<String, Object> params =  new HashMap<String, Object>();
		params.put("soId", soId);
		params.put("documentId", signature.getDocumentId());
		params.put("resourceName", signature.getName());
		params.put("resourceInd", signatureType);
		params.put("customerEmail", signature.getCustomerEmail());
		params.put("pdfGenStatus", MPConstants.PDF_GEN_STATUS);
		
		// Ideally this is not needed as the DEFAULT value is 0
		params.put("emailSentInd", 0); 
		params.put("createdDate", new Date());		
		params.put("modifiedDate", new Date());		

		try{
				insert("mobile.insertSignatureDetails.query", params);
		}
		catch(DataAccessException e){
		logger.error("Exception occured in insertSignatureDetails due to "+e.getMessage());
		throw new DataServiceException(e.getMessage());
		}
	}
	
	public void updateSignatureDetails(Signature signature, String soId, String signatureType)
	throws DataServiceException {
		// TODO Auto-generated method stub
		HashMap<String, Object> params =  new HashMap<String, Object>();
		params.put("soId", soId);
		params.put("documentId", signature.getDocumentId());
		params.put("resourceName", signature.getName());
		params.put("resourceInd", signatureType);
		params.put("customerEmail", signature.getCustomerEmail());
		params.put("pdfGenStatus", MPConstants.PDF_GEN_STATUS);
		
		// Blindly setting the value to 0 for re-sending e-mail 
		// for any signature updates
		params.put("emailSentInd", 0); 
		params.put("modifiedDate", new Date());
		try{
				update("mobile.updateSignatureDetails.query", params);
		}
		catch(DataAccessException e){
		logger.error("Exception occured in updateSignatureDetails due to "+e.getMessage());
		throw new DataServiceException(e.getMessage());
		}
	}
	
	public Integer fetchSignatureDetails(String soId, String signatureType)throws DataServiceException{
		HashMap<String, Object> params =  new HashMap<String, Object>();
		Integer docId = null;
		params.put("soId", soId);
		params.put("resourceInd", signatureType);
		try{
			
			docId = (Integer)queryForObject("mobile.fetchSignatureDetails.query", params);
		}
		catch(DataAccessException e){
		logger.error("Exception occured in fetchSignatureDetails due to "+e.getMessage());
		throw new DataServiceException(e.getMessage());
	}
		return docId;
	}

	public void updatePermitTask(PermitTask permit) throws DataServiceException{
		try{
			update("soPermitTaskData.update", permit); 		
		}
		catch(Exception e){
			logger.error("Exception occured in updatePermitTask() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}

	public void updatePermitAddon(PermitAddon permit) throws DataServiceException{
		try{
			 update("soPermitAddonData.update", permit); 
		
		}
		catch(Exception e){
			logger.error("Exception occured in updatePermitAddon() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	
	public void insertPermitAddon(PermitAddon permit)throws DataServiceException{
		try{
			 update("soPermitAddonData.insert", permit); 
		
		}
		catch(Exception e){
			logger.error("Exception occured in insertPermitAddon() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}


	/**
	 * @param soId
	 */
	public void deleteInvoicePartsDetails(String soId) throws DataServiceException{
		try{
			List<Integer> invoiceIds = getAvailableInvoicePartIdList(soId);
			if(null != invoiceIds && !invoiceIds.isEmpty()){
				try {
					
					deleteInvoicePartDetail(invoiceIds);
				} catch (DataServiceException e) {
					logger.info(" Data exception in deleteInvoicePartDetail"+e.getMessage());
				}
				catch(Exception e){
					logger.info("Exception in deletePartLocationDetails"+e.getMessage());
				}
				
			}
			
		}
		catch(DataAccessException e){
			logger.error("Exception occured in deleteInvoicePartsDetails due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}

	}

	

	/**
	 * @param invoiceIds
	 * 
	 * for deleting documents
	 */
	private void deletePartDocumentsForPartInvoiceIds(List<Integer> invoiceIds) {
		delete("deletePartDocumentsForPartInvoiceIds.query", invoiceIds);
	}
	/**
	 * 
	 * to get the signature details of 10 service orders
	 *
	 * @throws BusinessServiceException
	 */
	public List<Signature> getSignatureDetails(String limitValue) throws DataServiceException{
		
			List<Signature> signatureList = new ArrayList<Signature>();	
			List<String> soIdList = new ArrayList<String>();
			try{	
				Integer limit = Integer.parseInt(limitValue);
				Date start = new Date();
				soIdList = (List<String>) queryForList("getSignatureDetails.soId.query", limit);
				logger.info("getSignatureDetails.soId.query executed successfully");
				if(null != soIdList && !soIdList.isEmpty()){
					signatureList = (List<Signature>)queryForList("getSignatureDetails.query",soIdList);	
					logger.info("getSignatureDetails.query executed successfully");
				}
				Date end = new Date();
				long timeTakenForQuery = end.getTime() - start.getTime();
				logger.info("getSignatureDetails.query took "+timeTakenForQuery+" ms");
			}
			catch(DataAccessException e){
				e.printStackTrace();
				logger.error("Exception occured in getSignatureDetails() due to "+e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			 return signatureList;
	}
	/**
	 * @param signature
	 * @return
	 */
	public void updateSignature(Signature signature) throws DataServiceException{
		try{
			update("updateSignature.update", signature); 		
		}
		catch(Exception e){
			logger.error("Exception occured in updateSignature() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	
	/**
	 * @param signature
	 * @return
	 */
	public void updateSignatureEmailSent(Signature signature) throws DataServiceException{
		try{
			update("updateSignature.updateEmailSent", signature); 		
		}
		catch(Exception e){
			logger.error("Exception occured in updateSignature() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	/**
	 * @param soId
	 * @return
	 */
	public boolean selectSubStatus(String soId) throws DataServiceException{
		Integer stateId=null;
		boolean isCompleted=false;
		try{
			stateId = (Integer)queryForObject("getSoStatusData.query",soId);	
			if(null!=stateId){
				isCompleted= stateId.equals(MPConstants.COMPLETED);
			}
		}
		catch(Exception e){
			logger.error("Exception occured in selectSubStatus() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());			
		}
		return isCompleted;
	}
	/**
	 * 
	 * to get the resource and firm data of  service order
	 *
	 * @throws BusinessServiceException
	 */
	public ProviderHistoryVO getResourceFirmData(String soId) throws DataServiceException{
		
		ProviderHistoryVO hisVo = new ProviderHistoryVO();
			try{					
				hisVo = (ProviderHistoryVO)queryForObject("soResourceAndFirmId.query",soId);					
			}
			catch(DataAccessException e){
				logger.error("Exception occured in getResourceFirmData() due to "+e.getMessage());
				throw new DataServiceException(e.getMessage());
			}
			 return hisVo;
	}
	
	/**
	 * 
	 * to get additional payment details of a service order
	 *
	 * @throws BusinessServiceException
	 */
	public AddonPayment getAdditionalPaymentData(String soId) throws DataServiceException{
		
		AddonPayment additional = new AddonPayment();
			try{					
				additional = (AddonPayment)queryForObject("additionalPaymentData.query",soId);					
			}
			catch(DataAccessException e){
				logger.error("Exception occured in getAdditionalPaymentData() due to "+e);
				throw new DataServiceException(e.getMessage());
			}
			 return additional;
	}


	/**
	 * @param soId
	 * @return
	 */
	public List<DocumentVO> getServiceOrderDocuments(String soId)  throws DataServiceException{
		List<DocumentVO> documentVoList = new ArrayList<DocumentVO>();	
		try{					 
			documentVoList = (List<DocumentVO>)queryForList("getServiceOrderDocuments.query",soId);					
		}
		catch(DataAccessException e){
			logger.error("Exception occured in getServiceOrderDocuments() due to "+e);
			throw new DataServiceException(e.getMessage());
		}
		return documentVoList;
	}
	/**
	 * Method for getting document path
	 * @param soId,documentName
	 * @return
	 */
	public String getDocumentPath(String soId,String documentName)throws DataServiceException{
		String docPath = "";
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("soId", soId);
		params.put("documentName", documentName);
		
		try{
			docPath = (String)queryForObject("getDocumentPathData.query", params);
			
		}catch(Exception e){
			logger.info("Exception in getDocumentPath() "+e);
		}
		return docPath;
	}

	/**
	 * @param soId
	 * @return
	 */
	public List<Signature> getServiceOrderSignature(String soId)
			throws DataServiceException {
		
		List<Signature> signatureList = new ArrayList<Signature>();
		try{					
			signatureList = (List<Signature>)queryForList("getServiceOrderSignatureDetails.query",soId);					
		}
		catch(DataAccessException e){
			logger.error("Exception occured in getServiceOrderSignature() due to "+e);
		
		}
		 return signatureList;
	}

	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public List<Integer> getSOTasks(String soId) throws DataServiceException {
		List<Integer> taskIdList = new ArrayList<Integer>();
		try{					
			taskIdList = (List<Integer>)queryForList("getNonDeletedSOTasks.query",soId);					
		}
		catch(DataAccessException e){
			logger.error("Exception occured in getSOTasks() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		 return taskIdList;
	}
	/**
	 * @param soId
	 * @param docId
	 * @return
	 * @throws DataServiceException
	 */
	public void updateDocumentDetails(Integer docId) throws DataServiceException{ 
		try{					
			update("updateDocumentDetails.update", docId);					
		}
		catch(DataAccessException e){
			logger.error("Exception occured in updateDocumentDetails() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		
	}

	/**
	 * @param soId
	 */
	public void updateExistingPermitAddOnQty(String soId, boolean autoGenInd) throws DataServiceException {
		try{	
			if(!autoGenInd){
				update("updateExistingPermitAddOnQty.update", soId);
			}else{
				update("updateExistingAutoGenPermitAddOnQty.update", soId);
			}
		}
		catch(DataAccessException e){
			logger.error("Exception occured in updateExistingPermitAddOnQty() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		
	}

	public List<Integer> getSOPermitTaks(String soId)
			throws DataServiceException {
		List<Integer> taskIdList = new ArrayList<Integer>();
		try{					
			taskIdList = (List<Integer>)queryForList("getPermitSOTasks.query",soId);					
		}
		catch(DataAccessException e){
			logger.error("Exception occured in getSOPermitTaks() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		 return taskIdList;
	}

	/**
	 * @param addOnList
	 * @return
	 */
	public List<AddOn> getAddOnMiscInds(List<AddOn> addOnList) throws DataServiceException{
		List<AddOn> addOnMiscInds = new ArrayList<AddOn>();
		List<Integer> addOnIds = new ArrayList<Integer>();
		try{
			for(AddOn addOn :addOnList){
				if(addOn!=null){
					addOnIds.add(addOn.getSoAddonId());
				}
			}
		
			addOnMiscInds = (List<AddOn>)queryForList("getAddOnMiscInds.query", addOnIds);
			
			
		}catch(Exception e){
			logger.error("Exception occured in getAddOnMiscInds() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return addOnMiscInds;
	}

	@SuppressWarnings("unchecked")
	public List<AddOn> getSOAddonDetails(String soId)
			throws DataServiceException {
		List<AddOn> addonList = new ArrayList<AddOn>();
		try{
			addonList = (List<AddOn>)queryForList("getAddonDetails.query", soId);
		}catch(Exception e){
			logger.error("Exception occured in getSOAddonDetails() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return addonList;
	}
	
	/**
	 * Get property
	 */
	public String getPropertyFromDB(String appKey)	throws DataServiceException{
		String value = null;
		try{
			value=(String) queryForObject("getAppKeyValue.query",appKey);
		}catch(Exception e){
			logger.error("Exception occured in getPropertyFromDB() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return value;
	}
	
	/**
	 * R12.0 SPRINT 2
	 * @param curTripNo
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer validateTrip(Integer curTripNo,String soId) throws DataServiceException{
		Integer soTripId = null;
		try {
			HashMap<String, Object> params =  new HashMap<String, Object>();
			params.put("soId", soId);
			params.put("tripNo", curTripNo);
			soTripId = (Integer)queryForObject("mobileSOManageSchedule.validateTrip.query", params);
		}catch (Exception e) {
			throw new DataServiceException(e.getMessage(), e);
		}
		return soTripId;
	}
	
	
	/**
	 * R12.0 SPRINT 2
	 * @param soTripDetailsVO
	 * @return
	 * @throws DataServiceException
	 */
	public Integer addTripDetails(SOTripDetailsVO soTripDetailsVO)throws DataServiceException{
		Integer soTripDetailsId = null;
		try {
			soTripDetailsId = (Integer) insert("soTripDetails.insert",	soTripDetailsVO);
		}catch (Exception e) {
			throw new DataServiceException(e.getMessage(), e);
		}
		return soTripDetailsId;
	}
	
	/**
	 * R12.0 SPRINT 2
	 * @param revisitNeededVO
	 * @return
	 * @throws DataServiceException
	 */
	public void updateRevisitNeeded(SORevisitNeededVO revisitNeededVO) throws DataServiceException{
		try{					
			update("updateRevisitNeeded.update", revisitNeededVO);
			// This will update the current appointment date data with the appointment data of so_hdr if
			// current appointment data in so_trip is null(to be exactly current_appt_start_date in so_trip is null).
			update("updateCurrentApptDetailsInTrip.update", revisitNeededVO);
		}
		catch(DataAccessException e){
			logger.error("Exception occured in updateRevisitNeeded() due to "+e);
			throw new DataServiceException(e.getMessage());
		}
	}
	
	/**
	 * R12.0 SPRINT 2 - Method to update service date and time in so_hdr with the next appointment date
	 * time data received from revisit needed API.
	 * @param revisitNeededVO
	 * @return
	 * @throws DataServiceException
	 */
	public void updateAppointmentDateTime(SORevisitNeededVO revisitNeededVO) throws DataServiceException{
		try{					
			update("updateAppointmentDateTime.update", revisitNeededVO);					
		}
		catch(DataAccessException e){
			logger.error("Exception occured in updateRevisitNeeded() due to "+e);
			throw new DataServiceException(e.getMessage());
		}
	}
	
	/**
	 * R12.0 SPRINT 2 - Method to update schedule details in so_schedule and so_schedule_history 
	 * @param revisitNeededVO
	 * @return
	 * @throws DataServiceException
	 */
	public void updateSOScheduleForRevisit(SORevisitNeededVO revisitNeededVO) throws DataServiceException{
		try{					
			update("updateSOScheduleForRevisit.update", revisitNeededVO);
			insert("insertSOScheduleHistoryForRevisit.insert", revisitNeededVO);
		}
		catch(DataAccessException e){
			logger.error("Exception occured in updateSOScheduleForRevisit() due to "+e);
			throw new DataServiceException(e.getMessage());
		}
	}
	
	/**
	 * To check if there is entry in so invoice parts
	 * 
	 * @param soId	
	 * @return boolean
	 * @throws DataServiceException
	 */
	
	public List<InvoicePartsVO> checkIfPartExists(String soId) throws DataServiceException{
		List<InvoicePartsVO> invoicePartsVOList=null;
		try {	
			// code change for SLT-2112
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("soId", soId);
			invoicePartsVOList = (List<InvoicePartsVO>)queryForList("checkIfPartExists.query",parameter);
			
		} catch (Exception e) {
			logger.error("Exception occured in checkIfPartExists() due to "
					+ e.getMessage());
		}
		return invoicePartsVOList;
	}
	
	public void updateInvoicePartsDetails(InvoicePartsVO invoicePart) throws DataServiceException{
		try{					
			update("updateInvoicePartsDetails.update", invoicePart);					
		}
		catch(DataAccessException e){
			logger.error("Exception occured in updateInvoicePartsDetails() due to "+e);
			throw new DataServiceException(e.getMessage());
		}
	}


	public List<Integer> getAvailableInvoicePartIdList(String soId) throws DataServiceException {
		List<Integer> invoicePartIdList = null;
		try{
			invoicePartIdList = queryForList("getAvailablePartIds.query",soId);
		}catch (Exception e) {
			logger.error("Exception Occured in getAvailableInvoicePartIdList-->due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return invoicePartIdList;
	}

	

	/**
	 * @param partIdList
	 * @throws DataServiceException
	 * to delete all part details
	 */
	public void deleteInvoicePartDetail(List<Integer> partIdList)throws DataServiceException {
		try{
			if(null!=partIdList&&partIdList.size()>0){				
				delete("deletePartDocDetails.delete", partIdList);
				delete("deletePartLocationDetails.delete", partIdList);
			    delete("deleteInvoicePartsDetails.delete", partIdList);
			}
		}
		catch(DataAccessException e){
			logger.error("Exception occured in deleteInvoicePartDetail due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	
	/**
	 * To update into so in home parts 
	 * @param invoicePart	
	 * @throws DataServiceException
	 */
	
	public void insertInhomePartDetails(List<InvoicePartsVO> partlist) throws DataServiceException{
		try{
			insert("insertInhomePartDetails.insert",partlist);
		}
		catch(DataAccessException e){
			logger.error("Exception occured in insertInhomePartDetails due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		
	}
	
	/**
	 * To update into so in home parts and insert into 
	 * @param invoicePart	
	 * @throws DataServiceException
	 */
	
	public void updateInhomePartDetails(SupplierVO supplierVO) throws DataServiceException{
		try{
			if (null != supplierVO) {
				if (StringUtils.isNotBlank(supplierVO.getPartStatus())) {
				update("updateInhomePartDetails.update", supplierVO);
				}			
			}			
		}
		catch(DataAccessException e){
			logger.error("Exception occured in updateInhomePartDetails due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		
	}

	
	public HashMap<String, String> fetchApiDetails(String lpnServiceId)throws DataServiceException {
		HashMap<String, String> apiDetails = null;
		try{
			apiDetails =(HashMap <String, String>) queryForObject("fetchApiDetails.query", lpnServiceId);
		}catch (Exception e) {
			logger.error("Exception when trying to retrieve LPN Web service Details:"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return apiDetails;
	}
	
	/**
	 * To insert into provider part location parts 
	 * @param invoicePart	
	 * @throws DataServiceException
	 */
	
	public void insertPartLocationDetails(List<SupplierVO> supplierPartList) throws DataServiceException{
		try{
			insert("insertPartLocationDetails.insert", supplierPartList);
		}
		catch(DataAccessException e){
			logger.error("Exception occured in insertPartLocationDetails due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		
	}
	public void deletePartLocationDetails(List<Integer> invoiceIdList)throws DataServiceException {
		try{
			//update("deletePartLocationDetails.update", invoiceIdList);
			delete("deletePartLocationDetails.delete",invoiceIdList);
		}
		catch(DataAccessException e){
			logger.error("Exception occured in deletePartLocationDetails due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		
	}
	@SuppressWarnings("unchecked")
	public List<LpnVO> fetchLpnClientUrlHeader()throws DataServiceException {
		List<LpnVO> resultList = null;
		List<String> parameterList = new ArrayList<String>();
		parameterList.add(MPConstants.LPN_URL);
		parameterList.add(MPConstants.LPN_HEADER);
		try{
			resultList = queryForList("getLpnClientUrlHeader.query", parameterList);
		}catch (Exception e) {
			logger.error("Exception when trying to retrieve LPN Web service Details:"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return resultList;
	}
	public List<Integer> getDocumentIdList(List<Integer> documentId) throws DataServiceException{
		List<Integer> returnIdList = null;
		try{
			returnIdList = queryForList("getDocumentIdList.query",documentId);
		}catch (Exception e) {
			logger.error("Exception in validatiing document Ids "+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return returnIdList;
		
	}


	public void saveInvoicePartDocument(InvoicePartsVO invoicePart)throws DataServiceException {
		
		List<Integer> docIdList=null;
		List<DocIdVO> docVoList=new ArrayList<DocIdVO>();		
		try{
			
			if(null!=invoicePart&&null!=invoicePart.getDocumentIdList()&&invoicePart.getDocumentIdList().size()!=0){
				//deleting existing document related to same invoice Id
				
				delete("deleteInvoicePartDocumentDetails.delete", invoicePart.getInvoiceId());
				docIdList=invoicePart.getDocumentIdList();
				//mapping into vo for inserting as list 
				for(Integer docId:docIdList){
					DocIdVO docIdVO=new DocIdVO();
					docIdVO.setDocId(docId);
					docIdVO.setInvoiceId(invoicePart.getInvoiceId());
					docVoList.add(docIdVO);
				}
				 insert("saveInvoicePartDocument.insert", docVoList);				
			
		}
		}catch (Exception e) {
			logger.error("Exception in saving document Ids "+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		
	}

	public List<Integer> getSoDocumentIdList(List<Integer> documentIdList,String soId) throws DataServiceException {
		List<Integer> returnIdList = null;
		Map parameterMap = new HashMap();
		try{
			parameterMap.put("documentIdList",documentIdList);
			parameterMap.put("soid",soId);
			returnIdList = queryForList("getSoDocumentIdList.query",parameterMap);
		}catch (Exception e) {
			logger.error("Exception in validatiing SoDocumentIds "+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return returnIdList;
	}

	public SoTripVo getLatestOpenTrip(String soId) throws DataServiceException {
		SoTripVo latestOpenTripVo=null;
		try{
			latestOpenTripVo =  (SoTripVo) queryForObject("getLatestOpenTrip.query",soId);
		}catch (Exception e) {
			logger.error("Exception in validating Trip Number"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return latestOpenTripVo;
	}
	
	
	public Boolean  getAutoAdjudicationInd() throws DataServiceException{
		String autoAdjudicationInd="";
		try{
			autoAdjudicationInd =  (String) queryForObject("getAutoAdjudicationInd.query",null);
			if("true".equals(autoAdjudicationInd)){
				return true;
			}
			return false;
		}catch (Exception e) {
			logger.error("Exception in getting the autoAdjudicationInd"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}		
		
	}
	
	
	public String   getInvoicePartInd(String soId)  throws DataServiceException{
		String invoicePartInd="";
		try{
			invoicePartInd =  (String) queryForObject("getInvoicePartInd.query",soId);
			return invoicePartInd;
		}catch (Exception e) {
			logger.error("Exception in getting the invoicePartInd"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}	
		
		
	}


	
	public void insertPartsRequiredInd(String soId,String requiredType) throws DataServiceException {
		Map parameterMap = new HashMap();
		try{
			parameterMap.put("soId",soId);
			parameterMap.put("requiredType",requiredType);
			update("insertPartRequiredIndicator.update", parameterMap);
		}catch (Exception e) {
			logger.error("Exception in inserting parts required  indicator"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	public List<Integer> getAssociatedDocuments(List<Integer> inputList, boolean afterDeletion) throws DataServiceException{
		List<Integer> documentIdList = new ArrayList<Integer>();
		try{
			if(afterDeletion){
				documentIdList = (List<Integer>) queryForList("getInvoicePartIdList.query",inputList);
			}else{
				documentIdList = (List<Integer>) queryForList("getInvoiceDocumentIdList.query",inputList);
			}
		}catch (Exception e) {
			logger.error("Exception in getAssociatedDocuments "+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return documentIdList;
	}	
	public void deleteDocumentFromParentTables(List<Integer> documentIdList)throws DataServiceException{
		try{
			delete("deleteInvoiceDocFromSODocuments.delete",documentIdList);
			delete("deleteInvoiceDocFromDocuments.delete",documentIdList);
		}
		catch(DataAccessException e){
			logger.error("Exception occured in deleteDocumentFromParentTables due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	
	/**
	 * getCustomReference
	 */
	public String getCustomReference(String soId,String custRefType) throws DataServiceException{
		String refValue = null;
		try { 
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("soId", soId);
			params.put("custRefType", custRefType);
			
			refValue = (String) queryForObject("getCovType.query", params);

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return refValue;
	}
	
	/**
	 * to fetch the min and max window for buyer
	 * 
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public UpdateApptTimeVO getBuyerMinAndMaxTimeWindow(String soId)
			throws DataServiceException {
		UpdateApptTimeVO timeWindowVO = null;
		try { 
			// code change for SLT-2112
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("soId", soId);
			
			timeWindowVO = (UpdateApptTimeVO) queryForObject("getBuyerTimeWindow.query", parameter);

		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			throw new DataServiceException(ex.getMessage(), ex);
		}
		return timeWindowVO;
	}
	//R12_0 SLM-88 Send Message API
	public List<SupplierVO> getExistingInvoicePartStatusList(String soId)
			throws DataServiceException {
		List<SupplierVO> invoicePartStatusList = new ArrayList<SupplierVO>();
		try{
			invoicePartStatusList = queryForList("getExistingPartStatus.query",soId);
		}catch (Exception e) {
			logger.error("Exception Occured in getExistingInvoicePartStatusList-->due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return invoicePartStatusList;
	}
	//R12_0 SLM-88 Send Message API
	public List<InvoicePartsVO> getExistingPartStatusList(String soId)
			throws DataServiceException {
		List<InvoicePartsVO> invoicePartStatusList = new ArrayList<InvoicePartsVO>();
		try{
			invoicePartStatusList = queryForList("getExistingPartStatusList.query",soId);
		}catch (Exception e) {
			logger.error("Exception Occured in getExistingPartStatusList-->due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return invoicePartStatusList;
	}
	
	public Map<String, Object> getCustomerEmailDetails(String soId) throws DataServiceException{
		Map<String, Object> customerEmailDetails = new HashMap<String, Object>();
		try{
			customerEmailDetails = (HashMap<String, Object>) queryForObject("getCustomerEmailDetails.query",soId);					
		}
		catch(DataAccessException e){
			logger.error("Exception occured in getCustomerEmailDetails() due to "+e);
			throw new DataServiceException(e.getMessage());
		}
		return customerEmailDetails;
	}
	
	public List<BuyerTemplateMapperVO> getBuyerTemplatesMap()
			throws DataServiceException {
		List<BuyerTemplateMapperVO> buyerTemplateMapper = null;
		try {
			buyerTemplateMapper = (List<BuyerTemplateMapperVO>)queryForList("getBuyerTemplatesMap.query",null);
		} catch (Exception e) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->getBuyerTemplatesMap()"
					+ e);
			throw new DataServiceException(e.getMessage());
		}
		return buyerTemplateMapper;
	}

	public List<TemplateDetailsVO> getAllTemplates(
			List<Integer> templateIds) throws DataServiceException {
		List<TemplateDetailsVO> templates = null;
		try {
			templates = (List<TemplateDetailsVO>)queryForList("getAllTemplates.query", templateIds);
		} catch (Exception e) {
			logger.error("Exception Occured in MobileSOActionsBOImpl-->getBuyerTemplatesMap()"
					+ e);
			throw new DataServiceException(e.getMessage());
		}
		return templates;
	}
//SLM-88 Getting part details for deltepart API
	public List<InvoicePartsVO> getPartDeatilsForPartId(List<Integer> partIdList)
			throws DataServiceException {
		List<InvoicePartsVO> partDetailList = new ArrayList<InvoicePartsVO>();
		try{
			partDetailList = queryForList("getPartDeatilsForPartId.query",partIdList);
		}catch (Exception e) {
			logger.error("Exception Occured in partDetailList-->due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return partDetailList;
	}


	/**
	 * R12.0 updates parts indicator in so_workflow_controls for UpdateSoCompletion v1.0 flow
	 * On Delete invoice details, update the indicator to NO_PARTS_ADDED
	 * On Parts added, update the indicator to PARTS_ADDED
	 * @param soId
	 * @param invoicepartsInd
	 * @throws DataServiceException
	 */
	public void updateInvoicePartsInd(String soId, String invoicepartsInd)
			throws DataServiceException {
		HashMap<String, Object> params =  new HashMap<String, Object>();
		params.put("soId", soId);
		params.put("invoicepartsInd", invoicepartsInd);
		try{
			update("mobile.updateInvoicePartsInd.query", params);
		}
		catch(DataAccessException e){
			logger.error("Exception occured in updateInvoicePartsInd due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	public void associatingInvoicePartDocument(String invoiceId,List<Integer> docIdList) throws DataServiceException{
		List<DocIdVO> docVoList=new ArrayList<DocIdVO>();
		   for(Integer docId:docIdList){
			    DocIdVO docIdVO=new DocIdVO();
			    docIdVO.setDocId(docId);
			    docIdVO.setInvoiceId(invoiceId);
			    docVoList.add(docIdVO);
		    }
		try{
		    insert("saveInvoicePartDocument.insert", docVoList);
		    }catch (Exception e) {
		logger.error("Exception in associating invoice documents on save invoice"+ e.getMessage());
		throw new DataServiceException(e.getMessage());
		}
		
	}
	
	public String getConstantValueFromDB(String appkey)
			throws DataServiceException {
		String value = null;
		try{
			value=(String) queryForObject("getConstantValue.query",appkey);
		}catch(Exception e){
			logger.error("Exception occured in getPropertyFromDB() due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return value;
	}
	
	public void saveInvoicePartPricingModel(String soID,String pricingModel)throws DataServiceException{
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("pricingModel", pricingModel);
		params.put("soId", soID);
		try{
			update("mobile.updateInvoicePartsPricingModel.query", params);
		}
		catch(DataAccessException e){
			logger.error("Exception occured in saveInvoicePartPricingModel due to "+e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
	}
	
	public String getInvoicePartsPricingModel(String soId)  throws DataServiceException{
		String invoicePricingModel="";
		try{
			// code change for SLT-2112
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("soId", soId);
			invoicePricingModel =  (String) queryForObject("mobile.getInvoicePartsPricingModel.query",parameter);
			return invoicePricingModel;
		}catch (Exception e) {
			logger.error("Exception in getting the InvoicePartsPricingModel"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}	
		
		
	}
	
	public Double getFirmLevelReimbursementRate(String soId) throws DataServiceException{
		Double firmReimbursementRate=null;
		try{
			firmReimbursementRate =  (Double) queryForObject("mobile.getFirmReimbursementRate.query",soId);
			return firmReimbursementRate;
		}catch (Exception e) {
			logger.error("Exception in getting the getFirmLevelReimbursementRate"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}		
	}
	
	public HashMap<String, String> getReimbursementModelValues() throws DataServiceException{
		HashMap <String, String> reimbursementModelValues = new HashMap <String, String>();
		try{
			
			reimbursementModelValues = (HashMap<String, String>) queryForMap("getReimbursementModelValues.query",null,"app_constant_key","app_constant_value");
		
		}
		catch(Exception e){
			logger.info("Exception occurred in getReimbursementModelValues"+e);
			throw new DataServiceException("Exception occurred in getReimbursementModelValues :"+e.getMessage(),e);
		} 
		return reimbursementModelValues;
	}
	
	//method to update trip status
	public void updateTripStatus(HashMap<String, Object> map) throws DataServiceException{
		
		try{
			update("tripStatusData.update", map);
			
		}catch(Exception exc){
			logger.error("Exception in updateTripStatus() method in MobileSOActionsDAOImpl: "+ exc.getMessage());
			throw new DataServiceException(exc.getMessage());
		}
}
	//method to obtain trip id
	public Integer getTripId(HashMap<String, Object> map) throws DataServiceException {
		Integer tripId = null;
		try{
			tripId =  (Integer) queryForObject("getTripID.query",map);
		}catch (Exception e) {
			logger.error("Exception in getting Trip ID"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return tripId;
	}
 
	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 * to get count of so's addon having  qty>0
	 */
	//SL-20652: Method to get count of addOns
	public Integer getAddOnForSO(String soId) throws DataServiceException {
		Integer addOnCount = null;
		try{
			addOnCount = (Integer)queryForObject("mobile.getAddonForSO.query", soId);
		}
		catch(Exception e){
			logger.error("Exception in getAddOnForSO() method in MobileSOActionsDaoImpl:"+ e.getMessage());
			throw new DataServiceException(e.getMessage());
		}
		return addOnCount;
	}
	
}