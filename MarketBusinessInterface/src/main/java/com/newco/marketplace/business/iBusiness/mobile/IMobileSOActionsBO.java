package com.newco.marketplace.business.iBusiness.mobile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.api.mobile.beans.sodetails.PermitTasks;
import com.newco.marketplace.api.mobile.beans.sodetails.Permits;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.AddOn;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.AddOnPaymentDetails;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.HSCreditCardResponse;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Pricing;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.Signature;
import com.newco.marketplace.api.mobile.beans.updateSoCompletion.UpdateSOCompletionRequest;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.mobile.CustomReferenceVO;
import com.newco.marketplace.vo.mobile.InvoicePartsVO;
import com.newco.marketplace.vo.mobile.ProviderHistoryVO;
import com.newco.marketplace.vo.mobile.SOPartLaborPriceReasonVO;
import com.newco.marketplace.vo.mobile.UpdateApptTimeVO;
import com.newco.marketplace.vo.mobile.v2_0.SORevisitNeededVO;
import com.newco.marketplace.vo.mobile.v2_0.SupplierVO;

/**
 * @author karthika_g
 *
 */
public interface IMobileSOActionsBO {
	
	//To validate resourceId
	public Integer validateResourceId(Integer resourceId) throws BusinessServiceException;
	
	//To validate soId
	public ServiceOrder getServiceOrder(String soId)throws BusinessServiceException;
	
	//To validate the association of soId and providerId
	public String toValidateSoIdForProvider(Map<String, String> validateMap) throws BusinessServiceException;
	
	//To insert Note into so_notes 
	public void insertAddNote(Integer resourceId, Integer roleId, String soId, String subject, String message, Integer noteTypeId, String createdByName, String modifiedBy, Integer entityId, String privateInd) throws BusinessServiceException;
	
	public void historyLogging(ProviderHistoryVO hisVO) throws BusinessServiceException;

	/**
	 * 
	 * to get the buyer id and wfStateId of service order
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public HashMap<String, Object> getBuyerAndWfStateForSO(String soId) throws BusinessServiceException;

	/**
	 * 
	 * to check whether the SO is assigned at firm level
	 * 
	 * @param soId
	 * @return
	 */
	public boolean checkIfAssignedToFirm(String soId) throws BusinessServiceException ;

	/**
	 * Get credit card type id
	 * @param ccType
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer getCardType(String ccType) throws BusinessServiceException ;

	/**
	 * 
	 * to get mandatory custom references
	 * 
	 * @param soId
	 * 
	 * 
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<BuyerReferenceVO> getRequiredBuyerReferences(String soId, boolean mandatoryInd) throws BusinessServiceException;

	
	
	/**
	 * @param updateSOCompletionRequest
	 * @param serviceOrder 
	 * @return
	 */
	public com.newco.marketplace.api.mobile.beans.updateSoCompletion.v3_0.UpdateSOCompletionRequest preProcessUpdateSOCompletion(
			com.newco.marketplace.api.mobile.beans.updateSoCompletion.v3_0.UpdateSOCompletionRequest updateSOCompletionRequest);
	
	
	/**
	 * @param updateSOCompletionRequest
	 * @param serviceOrder 
	 * @return
	 */
	public UpdateSOCompletionRequest preProcessUpdateSOCompletion(
			UpdateSOCompletionRequest updateSOCompletionRequest);

	
	/**
	 * @param partCoverage
	 * @param partSource
	 * @return
	 */
	public HashMap<String, Double> getReimbursementRate(String partCoverage, String partSource)  throws BusinessServiceException;
	/**
	 * @param addOnList
	 * @return
	 */
	public void updateAddons(List<AddOn> addOnList,String soId, String userName)throws BusinessServiceException;
	/**
	 * @param paymentDetails
	 * @return
	 */
    public boolean mapPaymentDetails(AddOnPaymentDetails paymentDetails,String userName)throws BusinessServiceException;
    /**
	 * @param pricing
	 * @return
	 */
    public void updatePrisingDetails(Pricing pricing)throws BusinessServiceException;
	
	/**
	 * method updated by passing buyerId and wfstateId for nps notification service
	 * @param soId
	 * @param subStatus
	 * @param comments
	 * @return
	 */
	public void updateSoCompletion(String soId,Integer subStatus,String comments,Integer buyerId,Integer wfStateId)throws BusinessServiceException ;
			
	
	/**
	 * @param updateSOCompletionRequest
	 * @param soId
	 * @return
	 * @throws BusinessServiceException 
	 */
	public void updateTaskDetails(UpdateSOCompletionRequest updateSOCompletionRequest,String soId) throws BusinessServiceException;
	
	
	
	/**
	 * @param customReferenceVOs
	 * @param soId
	 */
	public void updateBuyerReferences(List<CustomReferenceVO> customReferenceVOs, String soId)throws BusinessServiceException;

	/**
	 * @param intValue
	 * @return
	 */
	public Contact getVendorResourceContact(int resourceId) throws BusinessServiceException;
	/**
	 * @param resolutionComments
	 * * @param soId
	 * @return
	 * @throws BusinessServiceException 
	 */
	public void updateResolutionComments(String resolutionComments,String soId) throws BusinessServiceException;
	

	
	//SLT-4658
	/**
	 * @param updateSOCompletionRequest v3.0
	 * * @param soId
	 * @param userName 
	 * @return
	 * @throws BusinessServiceException 
	 */
	public void updatePartsDetails(com.newco.marketplace.api.mobile.beans.updateSoCompletion.v3_0.UpdateSOCompletionRequest
			updateSOCompletionRequest, String soId)throws BusinessServiceException;
	
	
	/**
	 * @param updateSOCompletionRequest
	 * * @param soId
	 * @param userName 
	 * @return
	 * @throws BusinessServiceException 
	 */
	public void updatePartsDetails(UpdateSOCompletionRequest updateSOCompletionRequest, String soId)throws BusinessServiceException;
	
	/**
	 * @param updateSOCompletionRequest
	 * * @param soId
	 * @return
	 * @throws BusinessServiceException 
	 */
	public void insertInvoicePartsDetails(UpdateSOCompletionRequest updateSOCompletionRequest, String soId, String userName)throws BusinessServiceException;
	
	
	/**
	 TODO: R12_0 Delete as it is no longer required
	 * @param updateSOCompletionRequest v2.0
	 * * @param soId
	 * @return
	 * @throws BusinessServiceException 
	
	public void insertInvoicePartsDetails(com.newco.marketplace.api.mobile.beans.updateSoCompletion.v2_0.UpdateSOCompletionRequest 
			updateSOCompletionRequest, String soId, String userName)throws BusinessServiceException;
	 */
	
	
	/**
	 * @param soId
	 * @throws BusinessServiceException
	 */
	public void deleteInvoicePartsDetails(String soId)throws BusinessServiceException;

	/**
	 * @param soId
	 * @return
	 */
	public boolean checkIfSOIsActive(String soId) throws BusinessServiceException;

	/**
	 * @param reasonVOs
	 */
	public void updateLabourPartPriceReasons(
			List<SOPartLaborPriceReasonVO> reasonVOs) throws BusinessServiceException;

	/**
	 * @param soId
	 * @return
	 */
	public void insertSignatureDetails(UpdateSOCompletionRequest request,String soId) throws BusinessServiceException;
	
	

	
	/**
	 * v3.0
	 * @param soId
	 * @return
	 */
	public void insertSignatureDetails(com.newco.marketplace.api.mobile.beans.updateSoCompletion.v3_0.UpdateSOCompletionRequest 
			request,String soId) throws BusinessServiceException;
	
	
	/**update permit tasks
	 * @param permitTasks
	 * @param soId
	 * @throws BusinessServiceException
	 */
	public void updatePermitTasks(PermitTasks permitTasks, String soId)throws BusinessServiceException;

	/**update permit addons
	 * @param permitAddons
	 * @param soId
	 * @throws BusinessServiceException
	 */
	public void updateAddonPermits(Permits permits, String soId)throws BusinessServiceException;

	/**
	 * @param soId
	 * @return
	 */
	public List<DocumentVO> getServiceOrderDocuments(String soId)  throws BusinessServiceException;

	/**
	 * @param soId
	 * @return
	 */
	public List<Signature> getServiceOrderSignature(String soId)  throws BusinessServiceException;

	/**
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<Integer> getSOTasks(String soId) throws BusinessServiceException;

	/**
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<Integer> getSOPermitTaks(String soId) throws BusinessServiceException;
	
	public List<AddOn> getSOAddonDetails(String soId) throws BusinessServiceException;
	
	/**
	 * R12.0 SPRINT 2
	 * @param curTripNo
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer validateTrip(Integer curTripNo,String soId) throws BusinessServiceException;

	/**
	 * R12.0 SPRINT 2 - Add the trip details
	 * 
	 * @param currentSOTripId
	 * @param changeType
	 * @param changeCOmment
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer addTripDetails(Integer currentSOTripId,String changeType, String changeComment,String createdBy)
			throws BusinessServiceException;

	/**
	 * R12.0 SPRINT 2 - update revisit needed data in so_trip
	 * 
	 * @param revisitNeededVO
	 * @throws BusinessServiceException
	 */
	public void updateRevisitNeeded(SORevisitNeededVO revisitNeededVO) throws BusinessServiceException;
	
	
	/**
	 * R12.0 SPRINT 2 - update revisit needed appointment date in so_hdr
	 * 
	 * @param revisitNeededVO
	 * @throws BusinessServiceException
	 */
	public void updateAppointmentDateTime(SORevisitNeededVO revisitNeededVO) throws BusinessServiceException;
	/**
	 * @param InvoicePartsVO
	 * @param soId
	 * @return InvoicePartsVO
	 * @throws BusinessServiceException 
	 */
	public void addInvoicePartsInfo(List<InvoicePartsVO> invoicePartVOlist, String soId, String userName) throws BusinessServiceException ;
	/**
	 * @param invoicePartsVO
	 * @param soId
	 * @return InvoicePartsVO
	 * @throws BusinessServiceException 
	 */
	public List<InvoicePartsVO>  checkIfPartExists(String soId) throws BusinessServiceException;


	/**
	 * @param soId
	 * @return Integer List
	 * @throws BusinessServiceException
	 */
	public List<Integer> getAvailableInvoicePartIdList(String soId) throws BusinessServiceException;
	
	/**
	 * @param partIdList
	 * @param soId 
	 * @throws BusinessServiceException 
	 */
	public void deleteInvoicePartDetail(List<Integer> partIdList, String soId)throws BusinessServiceException;
	
	/**
	 * @param partList
	 * @throws BusinessServiceException 
	 */
	public void insertInhomePartDetails(List<InvoicePartsVO> partlist,String soId, String userName) throws BusinessServiceException;
	
	/**
	 * R12.0 SPRINT 2 - Method to update schedule details in so_schedule and so_schedule_history 
	 * @param revisitNeededVO
	 * @throws BusinessServiceException
	 */
	public void updateSOScheduleForRevisit(SORevisitNeededVO revisitNeededVO) throws BusinessServiceException;
	/**
	 * @param userName
	 * @param soId
	 * @param supplierPartList
	 * 
	 * @throws BusinessServiceException 
	 */
	public void updateInhomePartDetails(List<SupplierVO> supplierPartList,String soId,String userName) throws BusinessServiceException;

	/**
	 * @param lpnServiceId
	 * @return
	 * @throws BusinessServiceException
	 */
	public HashMap<String, String> fetchApiDetails(String lpnServiceId)throws BusinessServiceException;

	/**
	 * @return
	 * @throws BusinessServiceException
	 */
	public Map<String, String> fetchLpnClientUrlHeader()throws BusinessServiceException;

	/**
	 * @param documentIdList
	 * @return
	 * @throws BusinessServiceException
	 */
	public int getDocumentIdList(List<Integer> documentIdList)throws BusinessServiceException;
	/**
	 * @param SodocumentIdList,soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public int getSoDocumentIdList(List<Integer> documentIdList,String soId)throws BusinessServiceException;
	
	/**
	 * @param curTripNo
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer validateLatestOpenTrip(Integer curTripNo,String soId)throws BusinessServiceException;
	
	/**
	 * @param soTripId
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	
	public void insertPartsRequiredInd(String soId, String requiredType)throws BusinessServiceException;
	
	/**
	 * @param soId
	 * @param subStatus
	 * @throws BusinessServiceException
	 */
	public void updateSoSubStatus(String soId, Integer subStatus) throws BusinessServiceException;
	
	/**
	 * @param partIdList
	 * @return
	 * @throws BusinessServiceException
	 */
	public List<Integer> getAssociatedDocuments(List<Integer> inputList, boolean afterDeletion) throws BusinessServiceException;
	/**
	 * @param documentIdList
	 * @throws BusinessServiceException 
	 */
	public void deleteDocumentFromParentTables(List<Integer> documentIdList)throws BusinessServiceException;

	/**
	 * @param invoicePart
	 * @return
	 */
	public InvoicePartsVO calculatePriceofInvoicePartsAdjudication(
			InvoicePartsVO invoicePart);

	/**
	 * @param invoicePart
	 * @return
	 */
	public InvoicePartsVO calculatePriceofInvoiceParts(
			InvoicePartsVO invoicePart);
	
	public InvoicePartsVO calculatePriceofParts (InvoicePartsVO invoicePartsVO,String pricingModel);
	public Boolean  getAutoAdjudicationInd() throws BusinessServiceException;
	
	public String   getInvoicePartInd(String soId)  throws BusinessServiceException;
	/**
	 * @param soId
	 * @param SupplierVO
	 * @throws BusinessServiceException
	 */
	public List<SupplierVO> getExistingInvoicePartStatusList(String soId)throws BusinessServiceException;
	/**
	 * @param soId
	 * @param InvoicePartsVO
	 * @throws BusinessServiceException
	 */
	public List<InvoicePartsVO> getExistingPartStatusList(String soId)throws BusinessServiceException;

	/**
	 * @param soId
	 * @return
	 * 
	 * method to fetch the min and max window for the buyer
	 * 
	 */
	public UpdateApptTimeVO getBuyerMinAndMaxTimeWindow(String soId) throws BusinessServiceException;
	/**
	 * @param partIdlist
	 * @param soId
	 * @return
	 * 
	 */
	public void deletePartNPSCall(List<InvoicePartsVO> partIdlist, String soId)throws BusinessServiceException;

	/**
	 * R12.0 updates parts indicator in so_workflow_controls for UpdateSoCompletion v1.0 flow
	 * On Delete invoice details, update the indicator to NO_PARTS_ADDED
	 * On Parts added, update the indicator to PARTS_ADDED
	 * @param soId
	 * @param invoicepartsInd
	 * @throws BusinessServiceException
	 */
	public void updateInvoicePartsInd(String soId, String invoicepartsInd) throws BusinessServiceException;

	public void associatingInvoicePartDocument(String invoiceId,List<Integer> docIdList)throws BusinessServiceException;
	
	public String getConstantValueFromDB(String appkey) throws BusinessServiceException;
	
	public void saveInvoicePartPricingModel(String soID, String pricingModel) throws BusinessServiceException;
	
	public String getInvoicePartsPricingModel(String soId) throws BusinessServiceException;
	
	public String mapPartCoverage(String soId) throws BusinessServiceException;
	
	//R12.3 SL-20673
	/**
	 * @param curTripId 
	 * @return
	 * 
	 * method to update trip status
	 * 
	 */
	public void updateTripStatus(Integer curTripId, String tripStatus) throws BusinessServiceException;
	/**
	 * @param soId
	 * @param curTripNo
	 * @return
	 * 
	 * method to obtain trip id
	 * 
	 */
	public Integer getTripId(Integer curTripNo,String soId) throws BusinessServiceException;

	public String getVendorBNameList(List<Integer> vendorIds);
	
	  public boolean mapPaymentDetails(AddOnPaymentDetails paymentDetails, HSCreditCardResponse hSCreditCardResponse,String userName)throws BusinessServiceException;
	
	
	
}
