package com.newco.marketplace.persistence.iDao.mobile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.newco.marketplace.vo.mobile.BuyerTemplateMapperVO;
import com.newco.marketplace.vo.mobile.CustomReferenceVO;
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



public interface IMobileSOActionsDao {
	
	//To validate resourceId
	public Integer validateResourceId(Integer resourceId) throws DataServiceException;
	
	//To validate soId
	public ServiceOrder getServiceOrder(String soId) throws DataServiceException;
	
	//To validate the association of soId and providerId
	public String toValidateSoIdForProvider(Map<String, String> validateMap) throws DataServiceException;
	
	//To insert Note into so_notes 
	public ServiceOrderNote insertAddNote(ServiceOrderNote note) throws DataServiceException;
	
	/**
	 * fetch vendor business name according to vendor id
	 * 
	 * @param uniqueNewVendorsList
	 * @return
	 */
	public String getVendorBNameList(List<Integer> uniqueNewVendorsList);

	/**
	 * 
	 * to get the buyer id and wfStateId of service order
	 * @param soId
	 * @return
	 */
	public HashMap<String, Object> getBuyerAndWfStateForSO(String soId) throws DataServiceException;

	/**
	 * 
	 * check if the order is assigne dto firm
	 * 
	 * @param soId
	 * @return
	 */
	public boolean checkIfAssignedToFirm(String soId) throws DataServiceException;

	/**
	 * to get mandatory custom references
	 * 
	 * @param soId
	 * @return
	 */
	public List<BuyerReferenceVO> getRequiredBuyerReferences(String soId, boolean mandatoryInd)throws DataServiceException;

	
	public HashMap<String, String> getAutoAdjudicationValues() throws DataServiceException;

	public HashMap<String, String> getReimbursementModelValues() throws DataServiceException;

	/**
	 * @param partCoverage
	 * @param partSource
	 * @return
	 */
	public HashMap<String, Double> getReimbursementRate(String partCoverage, String partSource) throws DataServiceException;
	
	/**
	 * @param partCoverage
	 * @param partSource
	 * @return
	 */
	public InvoicePartsVO getBuyerPartsPriceCalculationValues(String partCoverage, String partSource) throws DataServiceException;
	/**
	 * @param map
	 * @return
	 */
	public int updateSoSubStatus(HashMap<String, Object> map) throws DataServiceException;
	/**
	 * @param map
	 * @return
	 */
	
	public int updateSoResolutionComments(HashMap<String, Object> map) throws DataServiceException;

	/**
	 * @param permit
	 * @return
	 */
	public void updatePermitTask(PermitTask permit) throws DataServiceException;
	/**
	 * @param permit
	 * @return
	 */
	public void updatePermitAddon(PermitAddon permit) throws DataServiceException;
	/**
	 * @param addOn
	 * @return
	 */
	public void updateAddon(AddOn addOn) throws DataServiceException;
	/**
	 * @param paymentDetails
	 * @return
	 */
	public void updatePaymentDetails(AddOnPaymentDetails paymentDetails) throws DataServiceException;	
	/**
	 * @param paymentDetails
	 * @return
	 */
	public void insertPaymentDetails(AddOnPaymentDetails paymentDetails) throws DataServiceException;	
	/**
	 * @param addOnPaymentHistoryDetails
	 * @return
	 */
	public void insertPaymentHistoryDetails(AddOnPaymentHistoryDetails addOnPaymentHistoryDetails) throws DataServiceException;
	
	/**
	 * to get corresponding payment type   * 
	 * @param paymentType
	 * @return
	 */
	public String getCreditCardAlias(String paymentType) throws DataServiceException;
	/**
	 * @param cardType
	 * @return
	 */
	public Integer getCreditCardID(String  cardType) throws DataServiceException;	
	/**
	 * @param pricing
	 * @return
	 */
	public void updatePrisingDetails(Pricing pricing) throws DataServiceException;
	
	/**
	 * 
	 * to get corresponding card type id for card type
	 * 
	 * @param ccType
	 * @return
	 */
	public Integer getCardType(String ccType) throws DataServiceException;
	
	/**
	 * 
	 * to update taskDetails
	 * 
	 * @param TaskVO
	 * @return
	 */
	public void updateTaskDetails(TaskVO vo) throws DataServiceException;
	
	/**
	 * 
	 * to get carrier Id
	 * 
	 * @param carrier
	 * @return
	 */
	public Integer getCarrierId(String carrier) throws DataServiceException;
	
	/**
	 * 
	 * to update Parts Details
	 * 
	 * @param PartsVO
	 * @return
	 */

	public void updatePartsDetails(PartsVO pvo) throws DataServiceException;
	/**
	 * 
	 * to insert Invoice Parts Details
	 * 
	 * @param PartsVO
	 * @return
	 */
	public void insertInvoicePartsDetails(InvoicePartsVO invVO) throws DataServiceException;

	
	/**
	 * @param soId
	 * @return 
	 */
	public List<CustomReferenceVO> getCustomReferncesForSO(String soId)  throws DataServiceException ;

	/**
	 * @param customReferenceVOs
	 * @throws DataServiceException
	 */
	public void addCustomeReferences(List<CustomReferenceVO> customReferenceVOs) throws DataServiceException;

	/**
	 * @param referenceVO
	 * @throws DataServiceException
	 */
	public void updateCustomRefValue(CustomReferenceVO referenceVO) throws DataServiceException;

	/**
	 * @param resourceId
	 * @return
	 */
	public Contact getVendorResourceContact(int resourceId)throws DataServiceException;
	/**
	 * To check if there is entry in so additional payment
	 * 
	 * @param addonPayment	
	 * @return boolean
	 * @throws DataServiceException
	 */
	
	public boolean isAdditionalPaymentPresent(String soId) throws DataServiceException;
	/**
	 * @param soId
	 * @return
	 */
	public boolean checkIfSOIsActive(String soId); 
	/**
	 * 
	 * to insert history for add note
	 * 
	 * @param ProviderHistoryVO
	 * @return
	 */
	public void insertAddNoteHistory(ProviderHistoryVO hisVO)throws DataServiceException;
	
	public void insertSignatureDetails(Signature signature,String soId, String signatureType)throws DataServiceException; 
	
	public Integer fetchSignatureDetails(String soId, String signatureType)throws DataServiceException;

	public void updateSignatureDetails(Signature signature,String soId, String signatureType)throws DataServiceException;
	
	/**
	 * @param reasonVOs
	 * @return
	 */
	public void updateLabourPartPriceReasons(
			List<SOPartLaborPriceReasonVO> reasonVOs) throws DataServiceException;

	/**
	 * @param permit
	 */
	public void insertPermitAddon(PermitAddon permit)throws DataServiceException;

	/**
	 * @param soId
	 */
	public void deleteInvoicePartsDetails(String soId) throws DataServiceException;
	
	/**
	 * 
	 * to get the signature details of 10 service orders
	 *
	 * @throws BusinessServiceException
	 */
	public List<Signature> getSignatureDetails(String limit) throws DataServiceException;
	/**
	 * @param signature
	 * @return
	 */
	public void updateSignature(Signature signature) throws DataServiceException;
	
	/**
	 * @param signature
	 * @return
	 */
	public void updateSignatureEmailSent(Signature signature) throws DataServiceException;
	
	/**
	 * @param soId
	 * @return
	 */
	public boolean selectSubStatus(String soId) throws DataServiceException;
	/**
	 * 
	 * to get the resource and firm data of  service order
	 *
	 * @throws BusinessServiceException
	 */
	public ProviderHistoryVO getResourceFirmData(String soId) throws DataServiceException;
	/**
	 * 
	 * to get additional payment details of a service order
	 *
	 * @throws BusinessServiceException
	 */
	public AddonPayment getAdditionalPaymentData(String soId) throws DataServiceException;


	/**
	 * @param soId
	 * @return
	 */
	public List<DocumentVO> getServiceOrderDocuments(String soId) throws DataServiceException;
	/**
	 * Method for getting document path
	 * @param soId,documentName
	 * @return
	 */
	public String getDocumentPath(String soId,String documentName)throws DataServiceException;
	/**
	 * @param soId
	 * @return
	 */
	public List<Signature> getServiceOrderSignature(String soId)  throws DataServiceException;

	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public List<Integer> getSOTasks(String soId)   throws DataServiceException;
	/**
	 * 
	 * @param docId
	 * @return
	 * @throws DataServiceException
	 */
	public void updateDocumentDetails(Integer docId) throws DataServiceException;
	
	/**
	 * @param soID
	 */
	public void deleteAdditionalPaymentDetails(String soID);

	/**
	 * @param soId
	 */
	public void updateExistingPermitAddOnQty(String soId,boolean autoGenInd)  throws DataServiceException;

	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public List<Integer> getSOPermitTaks(String soId) throws DataServiceException;

	/**
	 * @param addOnList
	 * @return
	 */
	public List<AddOn> getAddOnMiscInds(List<AddOn> addOnList)  throws DataServiceException;
	
	public List<AddOn> getSOAddonDetails(String soId) throws DataServiceException;
	
	/**
	 * @param appKey
	 * @return
	 * @throws DataServiceException
	 */
	public String getPropertyFromDB(String appKey)	throws DataServiceException;

	
	/**
	 * R12.0 SPRINT 2
	 * @param curTripNo
	 * @param soId
	 * @return
	 * @throws BusinessServiceException
	 */
	public Integer validateTrip(Integer curTripNo,String soId) throws DataServiceException;
	
	/**
	 * R12.0 SPRINT 2
	 * @param soTripDetailsVO
	 * @return
	 * @throws DataServiceException
	 */
	
	public Integer addTripDetails(SOTripDetailsVO soTripDetailsVO)throws DataServiceException;

	/**
	 * R12.0 SPRINT 2
	 * @param revisitNeededVO
	 * @throws DataServiceException
	 */
	public void updateRevisitNeeded(SORevisitNeededVO revisitNeededVO) throws DataServiceException;
	
	/**
	 * R12.0 SPRINT 2
	 * @param revisitNeededVO
	 * @throws DataServiceException
	 */
	public void updateAppointmentDateTime(SORevisitNeededVO revisitNeededVO) throws DataServiceException;
	
	/**
	 * R12.0 SPRINT 2
	 * @param revisitNeededVO
	 * @throws DataServiceException
	 */
	public void updateSOScheduleForRevisit(SORevisitNeededVO revisitNeededVO) throws DataServiceException;
	/**
	 * To check if there is entry in so invoice parts
	 * 
	 * @param soId	
	 * @return boolean
	 * @throws DataServiceException
	 */
	
	public List<InvoicePartsVO> checkIfPartExists(String soId) throws DataServiceException;
	
	/**
	 * To update into so invoice parts 
	 * @param invoicePart	
	 * @throws DataServiceException
	 */
	
	public void updateInvoicePartsDetails(InvoicePartsVO invoicePart) throws DataServiceException;


	/**
	 * @param soId
	 * @return Integer List
	 * @throws DataServiceException
	 */
	public List<Integer> getAvailableInvoicePartIdList(String soId)throws DataServiceException;
	
	/**
	 * @param partIdList
	 * @throws DataServiceException
	 */
	public void deleteInvoicePartDetail(List<Integer> partIdList)  throws DataServiceException;
	/**
	 * To update into so in home parts 
	 * @param invoicePart	
	 * @throws DataServiceException
	 */
	
	public void insertInhomePartDetails(List<InvoicePartsVO> partlist) throws DataServiceException;
	/**
	 * To update into so__provider_invoice_parts and insert into provider_part_location  
	 * @param supplierVO	
	 * @throws DataServiceException
	 */
	
	public void updateInhomePartDetails(SupplierVO supplierVOList) throws DataServiceException;

	/**
	 * @param lpnServiceId
	 * @return
	 * @throws DataServiceException
	 */
	public HashMap<String, String> fetchApiDetails(String lpnServiceId)throws DataServiceException;

	/**
	 * To insert into provider part location parts 
	 * @param invoicePart	
	 * @throws DataServiceException
	 */
	
	public void insertPartLocationDetails(List<SupplierVO> supplierPartList) throws DataServiceException;

	/**To soft delete invoice parts location details
	 * @param supplierPartList
	 * @throws DataServiceException
	 */
	public void deletePartLocationDetails(List<Integer> invoiceIdList)throws DataServiceException;

	/**
	 * @return
	 * @throws DataServiceException
	 */
	public List<LpnVO> fetchLpnClientUrlHeader()throws DataServiceException;

	/**
	 * @param documentIdList
	 * @return
	 * @throws DataServiceException
	 */
	public List<Integer> getDocumentIdList(List<Integer> documentIdList) throws DataServiceException;

	/**
	 * @param invoicePart
	 * @throws DataServiceException
	 */
	public void saveInvoicePartDocument(InvoicePartsVO invoicePart) throws DataServiceException;
	/**
	 * @return
	 * @throws DataServiceException
	 */
	public List<Integer> getSoDocumentIdList(List<Integer> documentIdList,String soId) throws DataServiceException;

	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public SoTripVo getLatestOpenTrip(String soId) throws DataServiceException;
	
	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public void insertPartsRequiredInd(String soId,String requiredType) throws DataServiceException;
	
	/**
	 * @param partIdList
	 * @return
	 * @throws DataServiceException
	 */
	public List<Integer> getAssociatedDocuments(List<Integer> inputList, boolean afterDeletion) throws DataServiceException;
	/**
	 * @param documentIdList
	 * @throws DataServiceException
	 */
	public void deleteDocumentFromParentTables(List<Integer> documentIdList)throws DataServiceException;
	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public Boolean  getAutoAdjudicationInd() throws DataServiceException;
	
	public String   getInvoicePartInd(String soId)  throws DataServiceException;

	
	/**
	 * Get the custom ref value
	 * @param soId
	 * @param custRefType
	 * @return
	 * @throws DataServiceException
	 */
	public String getCustomReference(String soId,String custRefType) throws DataServiceException;
	
	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public List<SupplierVO> getExistingInvoicePartStatusList(String soId)throws DataServiceException;
	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public List<InvoicePartsVO> getExistingPartStatusList(String soId)throws DataServiceException;

	/**
	 * to fetch the min and max window for buyer
	 * 
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public UpdateApptTimeVO getBuyerMinAndMaxTimeWindow(String soId)throws DataServiceException;
	
	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 */
	public Map<String, Object> getCustomerEmailDetails(String soId) throws DataServiceException;

	/**
	 * @return
	 * @throws DataServiceException
	 */
	public List<BuyerTemplateMapperVO> getBuyerTemplatesMap() throws DataServiceException;

	/**
	 * @param templateIds
	 * @return
	 * @throws DataServiceException
	 */
	public List<TemplateDetailsVO> getAllTemplates(List<Integer> templateIds) throws DataServiceException;
	/**
	 * @param partIdList
	 * @return
	 * @throws DataServiceException
	 */
	public List<InvoicePartsVO> getPartDeatilsForPartId(List<Integer> partIdList)throws DataServiceException;
	
	/**
	 * R12.0 updates parts indicator in so_workflow_controls for UpdateSoCompletion v1.0 flow
	 * On Delete invoice details, update the indicator to NO_PARTS_ADDED
	 * On Parts added, update the indicator to PARTS_ADDED
	 * @param soId
	 * @param invoicepartsInd
	 * @throws DataServiceException
	 */
	public void updateInvoicePartsInd(String soId, String invoicepartsInd) throws DataServiceException;

	public void associatingInvoicePartDocument(String invoiceId,List<Integer> docIdList)throws DataServiceException;
	
	public String getConstantValueFromDB(String appkey) throws DataServiceException;
	
	public void saveInvoicePartPricingModel(String soID,String pricingModel)throws DataServiceException;
	
	public String getInvoicePartsPricingModel(String soId) throws DataServiceException;
	
	public Double getFirmLevelReimbursementRate(String soId) throws DataServiceException;
	
	//method to update trip status
	public void updateTripStatus(HashMap<String, Object> map) throws DataServiceException;
	
	//method to obtain trip id
	public Integer getTripId(HashMap<String, Object> map) throws DataServiceException;

	/**
	 * @param soId
	 * @return
	 * @throws DataServiceException
	 * to get count of sop addom having  qty>0
	 */
	public Integer getAddOnForSO(String soId)throws DataServiceException;
}
