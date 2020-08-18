package com.servicelive.partsManagement.services.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.persistence.dao.partsManagement.PartsManagementDao;
import com.newco.marketplace.persistence.iDao.inhomeAutoCloseNotification.IInhomeAutoCloseDao;
import com.newco.marketplace.persistence.iDao.mobile.IMobileSOActionsDao;
import com.newco.marketplace.persistence.iDao.mobile.IMobileSOManagementDao;
import com.newco.marketplace.vo.mobile.InvoiceDetailsVO;
import com.newco.marketplace.vo.mobile.InvoicePartsVO;
import com.newco.marketplace.vo.mobile.Item;
import com.newco.marketplace.vo.mobile.PartDetailsDTO;
import com.servicelive.partsManagement.services.IPartsManagementService;


/**
 * $Revision: 1.0 $ $Author: Infosys $ $Date: 2015/01/15
 * for parts management
 *
 */
public class PartsManagementServiceImpl implements IPartsManagementService {



	private PartsManagementDao partsManagementDao;
	private IMobileSOActionsBO mobileSOActionsBO;
	private static final Logger LOGGER = Logger
			.getLogger(PartsManagementServiceImpl.class);
	private IMobileSOActionsDao mobileSOActionsDao;
	private IMobileSOManagementDao mobileSOManagementDao;
	private INotificationService notificationService;
	private LISClient lisClient;
	private IInhomeAutoCloseDao inhomeAutoCloseDao;

	public LISClient getLisClient() {
		return lisClient;
	}
	public void setLisClient(LISClient lisClient) {
		this.lisClient = lisClient;
	}
	public INotificationService getNotificationService() {
		return notificationService;
	}
	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
	}
	public IMobileSOManagementDao getMobileSOManagementDao() {
		return mobileSOManagementDao;
	}

	public IInhomeAutoCloseDao getInhomeAutoCloseDao() {
		return inhomeAutoCloseDao;
	}
	public void setInhomeAutoCloseDao(IInhomeAutoCloseDao inhomeAutoCloseDao) {
		this.inhomeAutoCloseDao = inhomeAutoCloseDao;
	}
	public void setMobileSOManagementDao(
			IMobileSOManagementDao mobileSOManagementDao) {
		this.mobileSOManagementDao = mobileSOManagementDao;
	}

	public IMobileSOActionsDao getMobileSOActionsDao() {
		return mobileSOActionsDao;
	}

	public void setMobileSOActionsDao(IMobileSOActionsDao mobileSOActionsDao) {
		this.mobileSOActionsDao = mobileSOActionsDao;
	}

	public IMobileSOActionsBO getMobileSOActionsBO() {
		return mobileSOActionsBO;
	}

	public void setMobileSOActionsBO(IMobileSOActionsBO mobileSOActionsBO) {
		this.mobileSOActionsBO = mobileSOActionsBO;
	}

	public PartsManagementDao getPartsManagementDao() {
		return partsManagementDao;
	}

	public void setPartsManagementDao(PartsManagementDao partsManagementDao) {
		this.partsManagementDao = partsManagementDao;
	}

	/**
	 *  @param partVO
	 * 
	 */

	public void savePartsDetails(InvoicePartsVO partVO) {
		//Integer currentTripId;
		//String soId=partVO.getSoId();
		try{
			/*currentTripId = partsManagementDao.getLatestOpenTrip(soId);
		if(null!=currentTripId){
    		mobileSOActionsBO.addTripDetails(currentTripId,MPConstants.PART_CHANGE_TYPE, MPConstants.PART_INVOICE_ADDED, userName);	
    	}*/
			Boolean autoAdjudicationInd=mobileSOActionsBO.getAutoAdjudicationInd();	
			if(null!=autoAdjudicationInd && autoAdjudicationInd){
				partVO.setAutoAdjudicationInd(true);
			}else{
				partVO.setAutoAdjudicationInd(false);
			}
			//SLM 119 : Setting default value for part source("MANUAL");
			//partVO.setPartNoSource(MPConstants.PART_SOURCE_MANUAL);
			partsManagementDao.savePartsDetails(partVO);
			//R12_0 SLM-88 Send Message API on adding part
			updateNPSForAddPart(partVO);
			//R12_0 Sprint 5
			mobileSOActionsBO.insertPartsRequiredInd(partVO.getSoId(),MPConstants.INDICATOR_PARTS_ADDED);
		}
		catch (Exception e) {
			LOGGER.info("exception in savePartsDetails"+e.getMessage());

		} 
	}
	/**
	 * @param soId
	 * @return
	 * method to fetch the retail price sum
	 */
	public Double getRetailPriceTotal(String soId,Integer partInvoiceId){

		Double retailPriceSum=null;
		try{
			retailPriceSum=partsManagementDao.getRetailPriceTotal(soId,partInvoiceId);
		}
		catch(Exception e){
			LOGGER.info("exception in validateRetailPrice"+e.getMessage());
		}

		return retailPriceSum;

	}
	/**
	 * @param invoicePartVO
	 * @param soId
	 * @param userName
	 */
	public void editPartDetails(InvoicePartsVO invoicePartVO,String soId, String userName) {
		List<InvoicePartsVO> invoicePartsVOs = new ArrayList<InvoicePartsVO>();
		invoicePartsVOs.add(invoicePartVO);
		//Integer currentTripId;
		try {
			//currentTripId = partsManagementDao.getLatestOpenTrip(soId);
			//R12_0 SLM-88 Send Message API on change of part status
			// Get the existing part details of SO
			List<InvoicePartsVO> existingPartsList = new ArrayList<InvoicePartsVO>();
			existingPartsList = getExistingPartsList(soId);

			// setting partno source to see wheteher it is from Lis or manaual		
			if(null!=existingPartsList && !(existingPartsList.isEmpty())){
				for(InvoicePartsVO existPart:existingPartsList){
					for(InvoicePartsVO editPartVO:invoicePartsVOs){
						if(null!= existPart && existPart.getInvoiceId().equals(editPartVO.getPartInvoiceId().toString())
								){
							editPartVO.setPartNoSource(existPart.getPartNoSource());
						}
					}
				}
			}


			addInvoicePartsInfo(invoicePartsVOs,soId,userName);

			// Check if there are changes in part status then insert into NPS
			if(null!=existingPartsList && !(existingPartsList.isEmpty())){
				for(InvoicePartsVO existPart:existingPartsList){
					for(InvoicePartsVO editPartVO:invoicePartsVOs){
						if(null!= existPart && existPart.getInvoiceId().equals(editPartVO.getInvoiceId())
								&& StringUtils.isNotEmpty(existPart.getPartStatus()) 
								&& StringUtils.isNotEmpty(editPartVO.getPartStatus()) &&
								!(existPart.getPartStatus().equalsIgnoreCase(editPartVO.getPartStatus()))){
							//Call Send message API
							updateNPSForPartStatus(soId,editPartVO,existPart,MPConstants.INHOME_BUYER);
						}
					}
				}
			}
			/*if(null!=currentTripId){
    		mobileSOActionsBO.addTripDetails(currentTripId,MPConstants.PART_CHANGE_TYPE, MPConstants.PART_INVOICE_ADDED, userName);	
    	}*/
		}
		catch (BusinessServiceException e) {
			LOGGER.info("exception in edit trip details"+e.getMessage());
		}
	}


	/**
	 * @param InvoicePartsVO
	 * @param soId
	 * @return InvoicePartsVO
	 * @throws BusinessServiceException 
	 */
	public void addInvoicePartsInfo(List<InvoicePartsVO> invoicePartVOlist, String soId, String userName) throws BusinessServiceException {

		try{


			if(invoicePartVOlist!=null){	
				String coverage = mapPartCoverage(soId);
				String pricingModel=mobileSOActionsBO.getInvoicePartsPricingModel(soId);

				for(InvoicePartsVO invoicePart:invoicePartVOlist){

					invoicePart.setSoId(soId);
					invoicePart.setPartCoverage(coverage);


					invoicePart= mobileSOActionsBO.calculatePriceofParts(invoicePart,pricingModel);	
					invoicePart.setPartInvoiceSource(MPConstants.WEB);
					if((MPConstants.NON_SEARS).equals(invoicePart.getPartSource())){
						invoicePart.setPartSource(MPConstants.NON_SEARS);
						invoicePart.setNonSearsSource(invoicePart.getNonSearsSource());
					} 
					else if((MPConstants.SEARS_SOURCE).equals(invoicePart.getPartSource())){
						invoicePart.setPartSource(MPConstants.SEARS_SOURCE);
						invoicePart.setNonSearsSource("");
					}


					invoicePart.setModifiedDate(new java.util.Date());
					invoicePart.setModifiedBy(userName);
					invoicePart.setInvoiceId(invoicePart.getPartInvoiceId().toString());

				}
				//partsManagementDao.updateInvoicePartsDetails(invoicePartVOlist);
				partsManagementDao.updateInvoiceParts(invoicePartVOlist);
				//Associate Invoice Documents to parts which is not associated with the already uploaded document
				// Save invoice documents
				//partsManagementDao.saveInvoicePartDocument(invoicePartVOlist);

			}
		}
		catch (DataServiceException ex) {
			LOGGER.error("Exception Occured in PartsManagementServiceImpl-->updateInvoicePartsDetails()");
			throw new BusinessServiceException(ex.getMessage());
		}
		catch (Exception ex) {
			LOGGER.error("Exception Occured in PartsManagementServiceImpl-->updateInvoicePartsDetails()");
			throw new BusinessServiceException(ex.getMessage());
		}

	}


	/**
	 * @param invoicePartId
	 * @return
	 */
	public InvoicePartsVO loadPartDetails(Integer invoicePartId) {

		InvoicePartsVO invoicePartsVO = null;
		try {

			invoicePartsVO = partsManagementDao.loadPartDetails(invoicePartId); 

		} catch (DataServiceException e) {
			LOGGER.info("exception in loadPartDetails"+e.getMessage());
		}
		return invoicePartsVO;

	}

	/**
	 * @param invoicePartsVO
	 * @return
	 * 
	 * to map part coverage according to custom reference
	 * @throws DataServiceException 
	 * 
	 */
	private String mapPartCoverage(String soId) throws DataServiceException{

		String customRefValue = mobileSOManagementDao.
				getCustomReference(soId, MPConstants.COVERAGE_TYPE_LABOR);	
		String coverage = null;

		if(null != customRefValue){
			if((MPConstants.COVERAGE_TYPE_LABOR_PA.equalsIgnoreCase(customRefValue) 
					|| (MPConstants.COVERAGE_TYPE_LABOR_SP.equalsIgnoreCase(customRefValue)))){

				coverage = MPConstants.PROTECTION_AGREEMENT;

			}else if((MPConstants.COVERAGE_TYPE_LABOR_IW.equalsIgnoreCase(customRefValue))){			

				coverage = MPConstants.IN_WARRANTY;

			}
			else if((MPConstants.COVERAGE_TYPE_LABOR_PT.equalsIgnoreCase(customRefValue))){			

				coverage = MPConstants.PAY_ROLL_TRANSFER;

			}
			else if((MPConstants.COVERAGE_TYPE_LABOR_CC.equalsIgnoreCase(customRefValue))){			

				coverage = MPConstants.CUSTOMER_COLLECT;

			}

		}


		return coverage;
	}
	/**
	 * @param invoicePartIds
	 * @return
	 */
	public InvoiceDetailsVO loadInvoiceDetails(List<Integer> invoicePartIds) {
		InvoiceDetailsVO invoiceDetails = null;
		try {

			invoiceDetails = partsManagementDao.loadInvoiceDetails(invoicePartIds); 			
		} catch (DataServiceException e) {
			LOGGER.info("exception in loadInvoiceDetails"+e.getMessage());
		}
		return invoiceDetails;
	}


	/**
	 * @param invoiceNum
	 * @param soId 
	 * @return
	 */
	public InvoiceDetailsVO loadInvoiceDetailsForInvoiceNum(String invoiceNum, String soId) {
		InvoiceDetailsVO invoiceDetails = null;
		try {

			invoiceDetails = partsManagementDao.loadInvoiceDetailsForInvoiceNum(invoiceNum,soId); 

		} catch (DataServiceException e) {
			LOGGER.info("exception in loadInvoiceDetailsForInvoiceNum"+e.getMessage());
		}
		return invoiceDetails;
	}
	/**
	 * @param partInvoiceId
	 * @return
	 */
	public void deletePartDetails(Integer partInvoiceId,String soId) {
		List<Integer> documentIdList = new ArrayList<Integer>();
		Integer invoiceIdCountForDocId = null;
		try {
			//Deleting the part details from so_provider_invoice_part_location
			partsManagementDao.deletePartLocnDetails(partInvoiceId); 
			//Deleting the part associated doc details
			documentIdList = partsManagementDao.getDocumentId(partInvoiceId);
			if(null!= documentIdList && documentIdList.size()>0 && !documentIdList.isEmpty()){
				//Checking whether the doc is associated to more than one part
				invoiceIdCountForDocId = getInvoiceIdCountForDocId(documentIdList);
				//Deleting from so_provider_invoice_doc
				deletePartDocumentForPartInvoiceId(partInvoiceId); 
				if(invoiceIdCountForDocId == 1){
					documentHardDelete(documentIdList,soId);
				}
			}
			//SLM-88 
			//1.Get the part name and part number for the invoice id
			List<Integer> partIdlist = new ArrayList<Integer>();
			List<InvoicePartsVO> partDetailsList = new ArrayList<InvoicePartsVO>();
			partIdlist.add(partInvoiceId);
			if(null!=partIdlist && !(partIdlist.isEmpty())){
				partDetailsList = mobileSOActionsDao.getPartDeatilsForPartId(partIdlist);
			}

			//Deleting the part details from so_provider_invoice_parts
			partsManagementDao.deletePartInvoiceDetails(partInvoiceId);

			//2.Call the NPS for sending the message.	
			mobileSOActionsBO.deletePartNPSCall(partDetailsList,soId);

			// R12_0 Sprint 5: Changes to update parts
			// indicator in so_workflow_controls..
			// ..when all the corresponding parts have
			// been deleted.
			List<Integer> remainingInvoicePartlist = new ArrayList<Integer>();
			remainingInvoicePartlist = mobileSOActionsBO
					.getAvailableInvoicePartIdList(soId);
			if (null != remainingInvoicePartlist
					&& remainingInvoicePartlist.size() == 0) {
				mobileSOActionsBO
				.insertPartsRequiredInd(
						soId,
						MPConstants.INDICATOR_NO_PARTS_ADDED);
			}
		} catch (DataServiceException e) {
			LOGGER.info(" Data exception in deletePartDetails"+e.getMessage());
		}
		catch(Exception e){
			LOGGER.info("Exception in deletePartDetails"+e.getMessage());
		}

	}

	public void documentHardDelete(List<Integer> documentIdList, String soId){
		try {
			//Deleting from so_document
			partsManagementDao.deleteSODoc(documentIdList,soId);
			//Deleting from document
			partsManagementDao.deleteDoc(documentIdList);
		} catch (DataServiceException e) {
			LOGGER.info("exception in documentHardDelete"+e.getMessage());
		}
	}

	public void deletePartDocumentForPartInvoiceId(Integer partInvoiceId){
		try {
			partsManagementDao.deletePartDocumentDetails(partInvoiceId); 
		} catch (DataServiceException e) {
			LOGGER.info("exception in deletePartDocumentForPartInvoiceId"+e.getMessage());
		}
	}

	public void deletePartDocumentForDocumentId(Integer documentId){
		try {
			partsManagementDao.deletePartDocumentForDocumentId(documentId);
		} catch (DataServiceException e) {
			LOGGER.info("exception in deletePartDocumentForDocumentId"+e.getMessage());
		}
	}

	public Integer getInvoiceIdCountForDocId(List<Integer> documentIdList){
		Integer count = 0;
		try {
			count = partsManagementDao.getInvoiceIdCountForDocId(documentIdList);
		} catch (DataServiceException e) {
			LOGGER.info("exception in getInvoiceIdCountForDocId"+e.getMessage());
		}
		return count;
	}

	/**
	 * @param invoicePartsVOs
	 * @param soId
	 * @param userName
	 * 
	 * to save invoice details
	 * 
	 */
	public void saveInvoiceDetails(InvoiceDetailsVO invoiceDetailsVO,
			String soId, String userName,Map<Integer , List<Integer>>newDocumentMap) {	
		try {

			//R12_0 SLM-88 Send Message API on change of part status
			// Get the existing part details of SO
			List<InvoicePartsVO> existingPartsList = new ArrayList<InvoicePartsVO>();
			existingPartsList = getExistingPartsList(soId);


			// setting partno source to see wheteher it is from Lis or manaual		
			if(null!=existingPartsList && !(existingPartsList.isEmpty())){
				for(InvoicePartsVO editPart:invoiceDetailsVO.getInvoicePartsVOs()){																
					for(InvoicePartsVO existPart:existingPartsList){
						if(null!= existPart && existPart.getInvoiceId().equals(editPart.getPartInvoiceId().toString())){
							editPart.setPartNoSource(existPart.getPartNoSource());

						}
					}

				}
			}



			List<InvoicePartsVO> invoicePartsVOs = mapInvoiceDetails(invoiceDetailsVO);
			if(null!=invoicePartsVOs && !invoicePartsVOs.isEmpty()){
				addInvoicePartsInfo(invoicePartsVOs,soId,userName);
			}
			//check if documents associated with parts,if not associate
			List<Integer> docIdList =null;
			if(null != invoicePartsVOs && !(invoicePartsVOs.isEmpty())){
				for(InvoicePartsVO invoicePart:invoicePartsVOs){
					if(null != invoicePart && StringUtils.isNotEmpty(invoicePart.getInvoiceId())){											
						docIdList = newDocumentMap.get(invoicePart.getPartInvoiceId());  							
						if(null != docIdList && !(docIdList.isEmpty())){
							mobileSOActionsBO.associatingInvoicePartDocument(invoicePart.getInvoiceId(),docIdList);																			
						}
					}							
				}
			}

			// Check if there are changes in part status then insert in NPS
			if(null!=existingPartsList && !(existingPartsList.isEmpty())){
				for(InvoicePartsVO editPart:invoiceDetailsVO.getInvoicePartsVOs()){
					if(null!=editPart && StringUtils.isNotEmpty(editPart.getPartStatus())){
						for(InvoicePartsVO existPart:existingPartsList){
							if(null!= existPart && existPart.getInvoiceId().equals(editPart.getInvoiceId())
									&& StringUtils.isNotEmpty(existPart.getPartStatus()) && 
									!(existPart.getPartStatus().equalsIgnoreCase(editPart.getPartStatus()))){
								//Call Send message API
								updateNPSForPartStatus(soId,editPart,existPart,MPConstants.INHOME_BUYER);
							}
						}
					}
				}
			}


		}
		catch (BusinessServiceException e) {
			LOGGER.info("exception in saveInvoiceDetails"+e.getMessage());
		}

	}

	/**
	 * @param invoiceDetailsVO
	 * @return
	 * 
	 * to map the common details to all VOs
	 * 
	 */
	private List<InvoicePartsVO> mapInvoiceDetails(
			InvoiceDetailsVO invoiceDetailsVO) {
		List<InvoicePartsVO> invoicePartsVOs = null;
		invoicePartsVOs = invoiceDetailsVO.getInvoicePartsVOs();
		if(invoicePartsVOs != null && !invoicePartsVOs.isEmpty()){
			String source = invoiceDetailsVO.getPartSource();
			String coverage = invoiceDetailsVO.getPartCoverage();
			String invoiceNumber = invoiceDetailsVO.getInvoiceNumber();
			String nonSearsSource= invoiceDetailsVO.getNonSearsSource();
			List<Integer> invoiceDocuments = invoiceDetailsVO.getInvoiceDocumentIds();
			for(InvoicePartsVO invoicePartsVO :invoicePartsVOs){
				if(null != invoicePartsVO){
					invoicePartsVO.setPartSource(source);
					invoicePartsVO.setPartCoverage(coverage);
					invoicePartsVO.setInvoiceNumber(invoiceNumber);
					invoicePartsVO.setNonSearsSource(nonSearsSource);
					if(null!=invoiceDocuments && !invoiceDocuments.isEmpty()){
						invoicePartsVO.setDocumentIdList(invoiceDocuments);
					}
				}

			}
		}

		return invoicePartsVOs;
	}

	/**
	 * @param List<InvoicePartsVO>
	 * @param soId
	 * @return InvoicePartsVO
	 * @throws BusinessServiceException 
	 */
	public void updatePartStatus(List<InvoicePartsVO> invoicePartVOlist, String userName,String soId){

		try{
			if(invoicePartVOlist!=null){			
				for(InvoicePartsVO invoicePart:invoicePartVOlist){
					invoicePart.setModifiedDate(new java.util.Date());
					invoicePart.setModifiedBy(userName);
					invoicePart.setInvoiceId(invoicePart.getPartInvoiceId().toString());

				}
				//R12_0 SLM-88 Send Message API on change of part status
				// Get the existing part details of SO
				List<InvoicePartsVO> existingPartsList = new ArrayList<InvoicePartsVO>();
				if(StringUtils.isNotEmpty(soId)){
					existingPartsList = getExistingPartsList(soId);
				}

				partsManagementDao.updatePartStatus(invoicePartVOlist);
				// Check if there are changes in part status
				if(null!=existingPartsList && !(existingPartsList.isEmpty())){
					for(InvoicePartsVO editPart:invoicePartVOlist){
						if(null!=editPart && StringUtils.isNotEmpty(editPart.getPartStatus())){
							for(InvoicePartsVO existPart:existingPartsList){
								if(null!= existPart && existPart.getInvoiceId().equals(editPart.getInvoiceId())
										&& StringUtils.isNotEmpty(existPart.getPartStatus()) && 
										!(existPart.getPartStatus().equalsIgnoreCase(editPart.getPartStatus()))){
									//Call Send message API
									updateNPSForPartStatus(soId,editPart,existPart,MPConstants.INHOME_BUYER);
								}
							}
						}
					}
				}
			}
		}
		catch (DataServiceException ex) {
			LOGGER.error("Exception Occured in PartsManagementServiceImpl-->updatePartStatus()");
		}
		catch (Exception ex) {
			LOGGER.error("Exception Occured in PartsManagementServiceImpl-->updatePartStatus()");
		}

	}

	public void deleteSelectedPartDocument(List<Integer> partInvoiceIdList,Integer documentId) {
		try {
			partsManagementDao.deleteSelectedPartDocument(partInvoiceIdList,documentId);
		} catch (DataServiceException e) {
			LOGGER.info("exception in deleteSelectedPartDocument"+e.getMessage());
		}

	}

	public Boolean getAutoAdjudicationInd() {
		Boolean autoAdjudicationInd = null;
		try {
			autoAdjudicationInd = mobileSOActionsBO.getAutoAdjudicationInd();

		} catch (Exception e) {
			LOGGER.info("exception in getAutoAdjudicationInd"
					+ e.getMessage());
		}
		return autoAdjudicationInd;
	}


	public String   getInvoicePartInd(String soId)  {
		String invoicePartInd = null;
		try {
			invoicePartInd = mobileSOActionsBO.getInvoicePartInd(soId);

		} catch (Exception e) {
			LOGGER.info("exception in getInvoicePartInd"
					+ e.getMessage());
		}
		return invoicePartInd;
	}

	public List<InvoicePartsVO> getExistingPartsList(String soId) {
		List<InvoicePartsVO> invoicePartStatusList = new ArrayList<InvoicePartsVO>();
		try{
			invoicePartStatusList = mobileSOActionsDao.getExistingPartStatusList(soId);
		}catch (Exception e) {
			LOGGER.error("Exception Occured in PartsManagementServiceImpl-->getExistingPartStatusList()" + e.getMessage());
		}
		return invoicePartStatusList;
	}
	//R12_0 SLM-88 NPS insertion when part status is changed
	public void updateNPSForPartStatus(String soId,InvoicePartsVO editPart, InvoicePartsVO existPart, Integer hsrBuyerId) {
		boolean isEligibleForNPSNotification = false;
		String partMessage = null;

		try{
			//call validation method for NPS notification
			isEligibleForNPSNotification = notificationService.validateNPSNotificationEligibility(hsrBuyerId,soId);

			if(isEligibleForNPSNotification){
				//Creating message for NPS update
				partMessage= MPConstants.PARTS_MESSAGE1+MPConstants.WHITE_SPACE+existPart.getPartNumber()+MPConstants.WHITE_SPACE+MPConstants.HYPHEN+MPConstants.WHITE_SPACE+existPart.getPartDescription()+MPConstants.PARTS_MESSAGE2+existPart.getPartStatus()+MPConstants.TO+editPart.getPartStatus()+MPConstants.DOT;
				//Call Insertion method for NPS notification
				notificationService.insertNotification(soId,partMessage);
			}
		}catch(Exception e){
			LOGGER.info("Caught Exception while insertNotification in updateNPSForPartStatus"+e);
		}

	}
	//R12_0 SLM-88 NPS insertion when part status is added

	public void updateNPSForAddPart(InvoicePartsVO partVO) {
		boolean isEligibleForNPSNotification = false;
		String partMessage = null;

		try{
			//call validation method for NPS notification
			if(StringUtils.isNotEmpty(partVO.getSoId())){
				isEligibleForNPSNotification = notificationService.validateNPSNotificationEligibility(MPConstants.INHOME_BUYER,partVO.getSoId());
			}
			if(isEligibleForNPSNotification){
				//Creating message for NPS update
				partMessage= partVO.getPartNumber()+MPConstants.WHITE_SPACE+MPConstants.HYPHEN+MPConstants.WHITE_SPACE+partVO.getPartDescription()+MPConstants.PARTS_ADD_MESSAGE;
				//Call Insertion method for NPS notification
				notificationService.insertNotification(partVO.getSoId(),partMessage);
			}
		}catch(Exception e){
			LOGGER.info("Caught Exception while insertNotification in updateNPSForAddPart"+e);
		}

	}

	/**
	 * @param invoicePartVOlist
	 * to batch update invoice document details
	 */
	public void saveInvoicePartDocument(List<InvoicePartsVO> invoicePartVOlist){
		try{	
			partsManagementDao.saveInvoicePartDocument(invoicePartVOlist);
		}catch (Exception e) {
			LOGGER.info("Caught Exception while saveInvoicePartDocument"+e);
		}
	}

	/**
	 * Method to get the documents already associated to each part
	 * @param invoiceDetailsVO
	 * @param soId
	 * @param userName
	 */
	public Map<Integer , List<Integer>> getDBDocumentList (InvoiceDetailsVO invoiceDetailsVO,
			String soId, String userName){

		Map<Integer , List<Integer>> dBInvoiceDocMap =new HashMap<Integer , List<Integer>>();
		try {		
			List<InvoicePartsVO> invoicePartsVOs = mapInvoiceDetails(invoiceDetailsVO);			
			//check if documents associated with parts,if not associate
			List<Integer> partIdList= null;			
			if(null != invoicePartsVOs && !(invoicePartsVOs.isEmpty())){
				for(InvoicePartsVO invoicePart:invoicePartsVOs){
					if(null != invoicePart && StringUtils.isNotEmpty(invoicePart.getPartInvoiceId().toString())){
						partIdList= new ArrayList<Integer>();  
						partIdList.add(new Integer(invoicePart.getPartInvoiceId()));
						List<Integer> docIdListDB = mobileSOActionsBO.getAssociatedDocuments(partIdList,false);						
						dBInvoiceDocMap.put(invoicePart.getPartInvoiceId(), docIdListDB);
					}											
				}
			}
		}
		catch (BusinessServiceException e) {
			LOGGER.info("exception in getDBDocumentList"+e.getMessage());
		}
		return dBInvoiceDocMap;

	}

	public String  getConstantValueFromDB(String appkey) throws Exception{
		String constantValue=null;
		try{	
			constantValue= mobileSOActionsDao.getConstantValueFromDB(appkey);
		}catch (Exception e) {
			LOGGER.info("Caught Exception while getConstantValueFromDB"+e);
			throw new Exception(e.getMessage());
		}
		return constantValue;
	}

	public void saveInvoicePartPricingModel(String soID, String pricingModel){
		try{	
			mobileSOActionsDao.saveInvoicePartPricingModel(soID,pricingModel);
		}catch (Exception e) {
			LOGGER.info("Caught Exception while saveInvoicePartPricingModel"+e);
		}	
	}

	public String getInvoicePartsPricingModel(String soId){
		String pricingModel=null;
		try{	
			pricingModel= mobileSOActionsDao.getInvoicePartsPricingModel(soId);
		}catch (Exception e) {
			LOGGER.info("Caught Exception while getInvoicePartsPricingModel"+e);
		}
		return pricingModel;
	}
	/**
	 * R15_2 : SL-20555:method to hit the LIS API after forming the url
	 * @param partNum
	 * @return
	 * @throws Exception
	 */
	public List<InvoicePartsVO> searchParts(String partNum,String soId) throws Exception{
		String consumerKey = null;
		PartDetailsDTO partDetailsDTO  = new PartDetailsDTO();

		List<InvoicePartsVO> invoicePartsVOs = new ArrayList<InvoicePartsVO>();
		try {
			//method to fetch the application constant value when search with key
			String applicationConst = inhomeAutoCloseDao.getRecipientIdFromDB(MPConstants.LIS_APPKEY);	
			String[] parts = applicationConst.split("\\?");
			String url = parts[0]; 
			consumerKey = parts[1]; 
			String [] key = consumerKey.split("=");
			consumerKey = key[1];

			LOGGER.info("Inside searchParts of PartsManagementServiceImpl for part: "+partNum+" and for SO: "+soId);
			partDetailsDTO = lisClient.searchPartDetails(url,consumerKey,partNum);
			if(null != partDetailsDTO.getErrorMessage()||StringUtils.isNotBlank(partDetailsDTO.getErrorMessage())){
				InvoicePartsVO partsVO = new InvoicePartsVO();
				partsVO.setErrorMessage(partDetailsDTO.getErrorMessage());
				invoicePartsVOs.add(partsVO);
			}
			else{
			invoicePartsVOs = mapLisResponse(partDetailsDTO,soId);
			}

		}
		catch (DataServiceException e) {
			LOGGER.error("Caught Exception while searchParts:"+e.getMessage());
			e.printStackTrace();
		}

		return invoicePartsVOs;
	}
	/**
	 * R15_2 : SL-20555:method to map the response of LIS api to invoice parts VO
	 * @param partDetailsDTO
	 * @return
	 */
	private List<InvoicePartsVO> mapLisResponse(PartDetailsDTO partDetailsDTO,String soId) {
		List<Item> itemDetails = new ArrayList<Item>();
		String itemId = null;
		List<InvoicePartsVO> invoicePart = new ArrayList<InvoicePartsVO>();
		if(null!= partDetailsDTO && null!= partDetailsDTO.getItems() && null!=partDetailsDTO.getItems().getItem()){
			itemDetails = partDetailsDTO.getItems().getItem();
			for(int i=0;i< itemDetails.size();i++){
				Item lisItem = itemDetails.get(i);
				InvoicePartsVO invoicePartsVO = new InvoicePartsVO();
				if(null !=lisItem.getPartNo()){
				invoicePartsVO.setPartNumber(lisItem.getPartNo());
				}
				if(null !=lisItem.getSubPartNo()){
				invoicePartsVO.setPartNumber(lisItem.getSubPartNo());
				}
				if(null !=lisItem.getItemDescription()){
				invoicePartsVO.setPartDescription(lisItem.getItemDescription());
				}
				if(null !=lisItem.getSubItemDescription()){
					invoicePartsVO.setPartDescription(lisItem.getSubItemDescription());
				}
				if(null!=lisItem.getItemSellingPrice()){
				invoicePartsVO.setRetailPrice(lisItem.getItemSellingPrice());
				}
				if(null!=lisItem.getSubItemSellingPrice()){
					invoicePartsVO.setRetailPrice(lisItem.getSubItemSellingPrice());
				}
				if(null !=lisItem.getItemId()){
				    itemId=lisItem.getItemId();
				}
				if(null !=lisItem.getSubItemId()){
				    itemId=lisItem.getSubItemId();
				}
				String divisionNo=itemId.substring(0, 4);
				String sourceNo = itemId.substring(4, 7);
				invoicePartsVO.setDivisionNumber(divisionNo);
				invoicePartsVO.setSourceNumber(sourceNo);
				invoicePartsVO.setPartIdentifier(100+i);
				if(null!=soId){
					invoicePartsVO.setSoId(soId);
				}
				invoicePart.add(invoicePartsVO);
			}
		}	
	
		return invoicePart;
	}
}

