package com.newco.marketplace.api.mobile.services.v2_0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.addinvoicesoproviderpart.AddInvoiceSOProviderPartRequest;
import com.newco.marketplace.api.mobile.beans.addinvoicesoproviderpart.AddInvoiceSOProviderPartResponse;
import com.newco.marketplace.api.mobile.beans.addinvoicesoproviderpart.InvoicePart;
import com.newco.marketplace.api.mobile.beans.addinvoicesoproviderpart.PartInvoice;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.inhomeoutboundnotification.service.INotificationService;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.MoneyUtil;
import com.newco.marketplace.vo.mobile.InvoicePartsVO;

/**
 * This class would act as a Service for Add part invoice info
 * 
 * @author Infosys
 * @version 1.0
 */

@APIRequestClass(AddInvoiceSOProviderPartRequest.class)
@APIResponseClass(AddInvoiceSOProviderPartResponse.class)
public class AddInvoiceSOProviderPartService extends BaseService {
	private Logger logger = Logger.getLogger(AddInvoiceSOProviderPartService.class);
	private IMobileSOActionsBO mobileSOActionsBO;
	private INotificationService notificationService;
	private Results results = new Results();
	
	public AddInvoiceSOProviderPartService() {
		// calling the BaseService constructor to initialize
		super();
	}

	/**
	 * Logic for handing add part Invoice Info API request
	 */
	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		logger.info("Inside AddInvoiceSOProviderPartService.execute()");

		// declaring variables		

		  AddInvoiceSOProviderPartResponse addPartInvoiceInfoResponse = new AddInvoiceSOProviderPartResponse() ;
	/*	  commenting code as no need of this in response
	      InvoicePartsList invoicePartsResponse=new InvoicePartsList();*/
		  List<PartInvoice> invoicePartRequestlist=null;
		  List<InvoicePartsVO> existingInvoicePartlist=null;
		
		 // get the so Id from the apiVO
		  String soId = (String) apiVO.getSOId();
		// get the request object from apiVO
			String userName = null;
			Integer providerId = apiVO.getProviderResourceId();
			SecurityContext securityContext = getSecurityContextForVendor(providerId);
			if(null != securityContext){
				userName = securityContext.getUsername();
			}			
		  AddInvoiceSOProviderPartRequest addInvoiceSOProviderPartRequest = (AddInvoiceSOProviderPartRequest) apiVO.getRequestFromPostPut();
		  String tripStatus = addInvoiceSOProviderPartRequest.getTripStatus();
		  String currentTrip = addInvoiceSOProviderPartRequest.getCurrentTripNo();
		  Integer currentTripId =null;	
		
	    	if (null != addInvoiceSOProviderPartRequest 
				&& null != addInvoiceSOProviderPartRequest.getProviderPartsList() && null!=addInvoiceSOProviderPartRequest.getProviderPartsList().getProviderPart()&&
						addInvoiceSOProviderPartRequest.getProviderPartsList().getProviderPart().size()>0){
			  invoicePartRequestlist=addInvoiceSOProviderPartRequest.getProviderPartsList().getProviderPart();
				
			  try {
				
				          //Fetching the buyer Id,and Wf state Id of So				 
				          ServiceOrder so = mobileSOActionsBO.getServiceOrder(soId);
				          if(null!=so){
				    	 //Fetching Existing invoice parts associated with service order
						  existingInvoicePartlist = mobileSOActionsBO.checkIfPartExists(so.getSoId());
						  
					 	  List<ErrorResult> validationErrors =validateInvoiceParts(invoicePartRequestlist, so,existingInvoicePartlist);
					 		if(!validationErrors.isEmpty()){				
					 			results.setError(validationErrors);
					 			addPartInvoiceInfoResponse.setResults(results);
					 			return addPartInvoiceInfoResponse;
					 		}
					 		 
					 		//Mapping the request inputs into corresponding invoice parts associated with SO
					 	  existingInvoicePartlist=mapInvoicePart(invoicePartRequestlist,existingInvoicePartlist);
					 		//TODO hard cording user name
					 		//Updating invoice information of the parts
					 	  if(StringUtils.isNotBlank(currentTrip) && StringUtils.isNumeric(currentTrip)){
					 		  
					 		  //R12.0 Sprint5 adding condition to check whether trip number is '999'
					 		  if(MPConstants.TRIP_NUMBER_DEFAULT.equals(currentTrip)){
					 			  
					 			  //R12_0 SLM-88 Send Message API on change of part status
				            	 	List<InvoicePartsVO> existingPartStatusList = new ArrayList<InvoicePartsVO>();
					            	existingPartStatusList = mobileSOActionsBO.getExistingPartStatusList(soId);
					            	
					 			 mobileSOActionsBO.addInvoicePartsInfo(existingInvoicePartlist,soId,userName); 
					 			 
					 			// Check the change of part status
				            	  if(null!= existingPartStatusList && !(existingPartStatusList.isEmpty())){
					            	for (PartInvoice requestPartVO : invoicePartRequestlist) {
				       					if(null!=requestPartVO && StringUtils.isNotBlank(requestPartVO.getPartStatus())){
				       						for(InvoicePartsVO existPartVO:existingPartStatusList){
				       							if(null!=existPartVO && existPartVO.getInvoiceId().equals(requestPartVO.getInvoiceId())
				       								&& StringUtils.isNotBlank(existPartVO.getPartStatus()) 
				       								  && !(existPartVO.getPartStatus().equalsIgnoreCase(requestPartVO.getPartStatus()))){
				       								
				       								updateNPSForAddInvoiceSOProviderPart(soId,MPConstants.INHOME_BUYER,requestPartVO,existPartVO);
				       							}
				       						}
				       						}
				       				}
				            	  }
					 		  }
					 		  else{
					 			 //R12.3 - SL-230763 - START 
					 		  //1)Remove the latest open trip validation.
					 		  //2)Check if the trip status is ENDED, then update the status to REOPEN 
					          // openTrip = mobileSOActionsBO.validateLatestOpenTrip(Integer.parseInt(currentTrip), soId);
						       currentTripId = mobileSOActionsBO.getTripId(Integer.parseInt(currentTrip), soId);
					            if(null!=currentTripId) {
					            	 /* List<ErrorResult> validationErrorsBeforeInsert = validateInvoiceParts(existingInvoicePartlist); 
					            	  if(!validationErrorsBeforeInsert.isEmpty()){				
								 			results.setError(validationErrorsBeforeInsert);
								 			addPartInvoiceInfoResponse.setResults(results);
								 			return addPartInvoiceInfoResponse;
								 		}*/

					            	//R12_0 SLM-88 Send Message API on change of part status
					            	//R12.3 - SL-230763 - END
					            	List<InvoicePartsVO> existingPartStatusList = new ArrayList<InvoicePartsVO>();
					            	existingPartStatusList = mobileSOActionsBO.getExistingPartStatusList(soId);
					            	
					            	  mobileSOActionsBO.addInvoicePartsInfo(existingInvoicePartlist,soId,userName);
					            	// Check the change of part status
					            	  if(null!= existingPartStatusList && !(existingPartStatusList.isEmpty())){
						            	for (PartInvoice requestPartVO : invoicePartRequestlist) {
					       					if(null!=requestPartVO && StringUtils.isNotBlank(requestPartVO.getPartStatus())){
					       						for(InvoicePartsVO existPartVO:existingPartStatusList){
					       							if(null!=existPartVO && existPartVO.getInvoiceId().equals(requestPartVO.getInvoiceId())
					       								&& StringUtils.isNotBlank(existPartVO.getPartStatus()) 
					       								  && !(existPartVO.getPartStatus().equalsIgnoreCase(requestPartVO.getPartStatus()))){
					       								
					       								updateNPSForAddInvoiceSOProviderPart(soId,MPConstants.INHOME_BUYER,requestPartVO,existPartVO);
					       							}
					       						}
					       						}
					       				}
					            	  }
					            	  mobileSOActionsBO.addTripDetails(currentTripId,MPConstants.PART_CHANGE_TYPE, MPConstants.PART_INVOICE_ADDED, userName);
					            	   
					             }
					            else{
					            	   this.results=Results.getError(ResultsCode.INVALID_TRIP_NUMBER.getMessage(),
					   								ResultsCode.INVALID_TRIP_NUMBER.getCode());
					   				                addPartInvoiceInfoResponse.setResults(this.results);
					   				                return 	 addPartInvoiceInfoResponse;
					               }
					            		   
					 		  } 
					 	  }			 		
					 		/*commenting code as no need of this in response
					 		List<InvoicePart> invoicePartResponse =mapInvoicePartResponse( existingInvoicePartlist);*/
					 		
					     	/*	 commenting code as no need of this in response
					 		invoicePartsResponse.setInvoicePart(invoicePartResponse);*/
					 		 
					 		 //Mapping Response after processing the request 
					 		addPartInvoiceInfoResponse = new AddInvoiceSOProviderPartResponse(soId, Results.getSuccess());
					 	  }
					// return the success message
					//TODO price should be rounded off before setting 
			

			} catch (Exception exception) {
				logger.error("AddInvoiceSOProviderPartService.execute(): exception due to "
						+ exception.getMessage());
				// set the error code as internal server error
				this.results=Results.getError(
						ResultsCode.INTERNAL_SERVER_ERROR
								.getMessage(),
								ResultsCode.INTERNAL_SERVER_ERROR
								.getCode());
				addPartInvoiceInfoResponse.setResults(this.results);
			}
		} else {
			this.results=Results.getError(
					ResultsCode.UNABLE_TO_PROCESS_THE_REQUEST.getMessage(),
					ResultsCode.UNABLE_TO_PROCESS_THE_REQUEST.getCode());
			addPartInvoiceInfoResponse.setResults(this.results);
		}
		if (logger.isInfoEnabled()) {
			logger.info("Leaving AddInvoiceSOProviderPartService.execute()");
		}
				// return the response object
		return addPartInvoiceInfoResponse;
	}
	//R12_0 SLM-88 Send Message API on change of part status
	private void updateNPSForAddInvoiceSOProviderPart (String soId, Integer inhomeBuyer,
			PartInvoice partInvoice, InvoicePartsVO invVO) {
		boolean isEligibleForNPSNotification = false;
		String partMessage = null;
		
		try{
    		//call validation method for NPS notification
			isEligibleForNPSNotification = notificationService.validateNPSNotificationEligibility(inhomeBuyer,soId);
    		
			if(isEligibleForNPSNotification){
	    		//Creating message for NPS update
				partMessage= MPConstants.PARTS_MESSAGE1+MPConstants.WHITE_SPACE+invVO.getPartNumber()+MPConstants.WHITE_SPACE+MPConstants.HYPHEN+MPConstants.WHITE_SPACE+invVO.getPartDescription()+MPConstants.PARTS_MESSAGE2+invVO.getPartStatus()+MPConstants.TO+partInvoice.getPartStatus()+MPConstants.DOT;
				//Call Insertion method for NPS notification
				notificationService.insertNotification(soId,partMessage);
			}
    	}catch(Exception e){
			logger.info("Caught Exception while insertNotification"+e);
		}
		
	}
	/**
	 * @param invoicePartlist
	 * Mapping the request inputs into corresponding invoice parts associated with SO
	 * @param existingInvoicePartlist */
	
	private List<InvoicePartsVO> mapInvoicePart(List<PartInvoice> invoicePartlist,  List<InvoicePartsVO> existingInvoicePartlist) {
		 List<InvoicePartsVO> partlist=new ArrayList<InvoicePartsVO>();
		for(PartInvoice partInvoice :invoicePartlist){
			if(null!=partInvoice){
			for(InvoicePartsVO invoicePartsVO :existingInvoicePartlist){
				if(null!=invoicePartsVO){
				if(partInvoice.getInvoiceId().equals(invoicePartsVO.getInvoiceId())){
					//invoicePartsVO.setPartCoverage(partInvoice.getPartCoverage());
					invoicePartsVO.setPartSource(partInvoice.getPartSource());
					invoicePartsVO.setNonSearsSource(partInvoice.getNonSearsSource());
					invoicePartsVO.setInvoiceNumber(partInvoice.getInvoiceNumber());
					invoicePartsVO.setUnitCost(partInvoice.getUnitCost());
					
					if(null != partInvoice && StringUtils.isNotBlank(partInvoice.getRetailPrice())){
						invoicePartsVO.setRetailPrice(partInvoice.getRetailPrice());
					}
					
					
					if(null != partInvoice && StringUtils.isNotBlank(partInvoice.getPartStatus())){
						invoicePartsVO.setPartStatus(partInvoice.getPartStatus());
					}
					if(null!= partInvoice && null!= partInvoice.getQty()){
						invoicePartsVO.setQty(partInvoice.getQty());
					}
					if(null != partInvoice.getDocument() && null!= partInvoice.getDocument().getDocumentId()){
					   invoicePartsVO.setDocumentIdList(partInvoice.getDocument().getDocumentId());
					}
					partlist.add(invoicePartsVO);
				}}
			}}
		}
		return partlist;
	}
	/**
	 * @param existingInvoicePartlist
	 * Mapping Response after processing the request 
	 * @param existingInvoicePartlist	 */
	private List<InvoicePart>   mapInvoicePartResponse( List<InvoicePartsVO> existingInvoicePartlist) {
		List<InvoicePart> invoicePartResponse =  new ArrayList<InvoicePart>();
		for(InvoicePartsVO invoicePartsVO :existingInvoicePartlist){		
			if(null!=invoicePartsVO){
			        InvoicePart invoicePart =new InvoicePart();
			        invoicePart.setInvoicePartId(invoicePartsVO.getInvoiceId()) ; 
					invoicePart.setEstProviderPayement(invoicePartsVO.getEstProviderPayement());
					invoicePart.setFinalPrice(invoicePartsVO.getFinalPrice());
					invoicePartResponse.add(invoicePart);
			}
		}
		return invoicePartResponse;
	}
 
	/**
	 * @param invoicePartlist
	 *  @param ServiceOrder
	 * Mapping Response after processing the request 
	 * @param invoicePartVOlist */
	private List<ErrorResult> validateInvoiceParts( List<PartInvoice> invoicePartlist ,ServiceOrder so, List<InvoicePartsVO> existingInvoicePartlist)throws BusinessServiceException {
		//TODO how to do validate if part not present
		List<ErrorResult> validationErrors = new ArrayList<ErrorResult>();
		ErrorResult result = new ErrorResult();
		Integer buyerId=0;
		Integer wfState=0;	
		if(null!=so){
		    buyerId=so.getBuyerId();
		    wfState=so.getWfStateId();
		    
		}	
				
			if (PublicMobileAPIConstant.HSR_BUYER_ID.equals(buyerId)) {
				if(PublicMobileAPIConstant.ORDER_ACTIVE.equals(wfState))
				{					
					for(PartInvoice partInvoice :invoicePartlist){
						if(null!=partInvoice){
						//Declaring variable to check if part is present
						 int isPartPresent=0;
					     int noOfDocuments = 0;
					     int docCount = 0;
					     List<Integer> documentIdList=null;
					     if(null != partInvoice.getDocument() && !(partInvoice.getDocument().getDocumentId().isEmpty())){
					    	 noOfDocuments=partInvoice.getDocument().getDocumentId().size();
					    	 documentIdList = partInvoice.getDocument().getDocumentId();
					     }
						 if (existingInvoicePartlist != null&&existingInvoicePartlist.size()>0) {
						  for(InvoicePartsVO invoicePartsVO :existingInvoicePartlist){
							 if(partInvoice.getInvoiceId().equals(invoicePartsVO.getInvoiceId())) {
								 //if part is present setting variable as 1 								
								    isPartPresent=1;
								     if(noOfDocuments > 0){
									   //Validate the existence of documents in so_document table
									    docCount = mobileSOActionsBO.getSoDocumentIdList(documentIdList,so.getSoId());
								      }
								     break;
							        } 
						         }	
						    }      
						   if(null==existingInvoicePartlist||isPartPresent==0){
											   //Validating if given provider parts are associated with service order							
										    	result.setCode(ResultsCode.PART_NOT_ASSOCIATED_SO_OR_DELETED.getCode());
											    result.setMessage(	ResultsCode.PART_NOT_ASSOCIATED_SO_OR_DELETED.getMessage());											
											    validationErrors.add(result);
											    break;	
							}else if(isPartPresent==1 && (noOfDocuments != docCount)){
												result.setCode(ResultsCode.INVALID_SO_DOCUMENT.getCode());
											    result.setMessage(	ResultsCode.INVALID_SO_DOCUMENT.getMessage());											
											    validationErrors.add(result);
							                    break;	
							}
							/*}else if (StringUtils.isNotBlank(partInvoice.getUnitCost())
												&& Double.parseDouble(partInvoice.getUnitCost()) > 999) {													
												result.setCode(ResultsCode.UPDATE_SO_INVOICE_PARTS_INFO_ERROR1.getCode());
												result.setMessage(	ResultsCode.UPDATE_SO_INVOICE_PARTS_INFO_ERROR1.getMessage());											
												validationErrors.add(result);
												break;
												// Unit Price should not exceed $999.00.
						    } else if (StringUtils.isNotBlank(partInvoice.getRetailPrice())
											&& Double.parseDouble(partInvoice.getRetailPrice()) > 999) {
												result.setMessage(ResultsCode.UPDATE_SO_INVOICE_PARTS_INFO_ERROR2	.getMessage());
												result.setCode(ResultsCode.UPDATE_SO_INVOICE_PARTS_INFO_ERROR2.getCode());					
												validationErrors.add(result);
										        break;
										        // Retail Price should not exceed $999.00.
							}*/ else if (StringUtils.isNotBlank(partInvoice.getPartSource())
											&& (MPConstants.NON_SEARS).equals(partInvoice.getPartSource())
											&& StringUtils.isBlank(partInvoice.getNonSearsSource())) {
										        result.setMessage(ResultsCode.UPDATE_SO_INVOICE_PARTS_INFO_ERROR3.getMessage());
											    result.setCode(ResultsCode.UPDATE_SO_INVOICE_PARTS_INFO_ERROR3.getCode());			
											    validationErrors.add(result);
										        break;
										        // Please enter the name of local part supplier.
									  }									
								
								  }}
											 
					       }
										
				else{	
					//If order not in active status
					result.setMessage(ResultsCode.INVALID_SO_STATUS.getMessage());
					result.setCode(ResultsCode.INVALID_SO_STATUS.getCode());
					
					validationErrors.add(result);
				}
				
			}
			else{
				//If order not belongs to HSR
				result.setMessage(ResultsCode.INVALID_BUYER
								.getMessage());
				result.setCode(ResultsCode.INVALID_BUYER
								.getCode());			
				validationErrors.add(result);
			}
			return validationErrors;
		}
	
	
	/**@Description:This validation will be called just before inserting invoice parts details with pricing informations
	 * @param soProviderInvoiceParts
	 */
	private List<ErrorResult> validateInvoiceParts(List<InvoicePartsVO> soProviderInvoiceParts) {
		List<ErrorResult> validationErrors = new ArrayList<ErrorResult>();
		ErrorResult result = new ErrorResult();
		Double netPayment = 0.00;
		Double finalPayment = 0.00;
		Double finalPaymentTotal = 0.00;
		Double payment = 0.00;
		String maxValueForInvoicePartsHSR = PropertiesUtils
				.getPropertyValue(PublicMobileAPIConstant.HSR_MAX_VALUE_INVOICE_PARTS);
		Double maxValue = Double
				.parseDouble(maxValueForInvoicePartsHSR);

			if(null!=soProviderInvoiceParts && !soProviderInvoiceParts.isEmpty()){
				for(InvoicePartsVO invoicePart : soProviderInvoiceParts){
					if(invoicePart!=null){

						try {

							Integer quantity = invoicePart.getQty();
							HashMap<String, Double> buyerPriceCalcValues = mobileSOActionsBO.getReimbursementRate(invoicePart.getPartCoverage(),invoicePart.getPartSource());
							if (null != buyerPriceCalcValues&& !buyerPriceCalcValues.isEmpty()) {
								String reImbRate = buyerPriceCalcValues.get("reImbursementRate").toString();
								String grossValue = buyerPriceCalcValues.get("grossupVal").toString();
								if (StringUtils.isNotEmpty(reImbRate)&& StringUtils.isNotEmpty(grossValue)
										 && null!=invoicePart.getRetailPrice()) {

									Double reimbursementRate = Double.parseDouble(reImbRate);
									Double slGrossUpValue = Double.parseDouble(grossValue);
									Double retailPrice = null;
									if(null!=invoicePart && StringUtils.isNotBlank(invoicePart.getRetailPrice()) ){
										retailPrice =new Double(invoicePart.getRetailPrice());
									}

									netPayment = MoneyUtil.getRoundedMoney(retailPrice
													* (reimbursementRate / 100)
													* 100) / 100;
									netPayment = netPayment * quantity;

									finalPayment = MoneyUtil
											.getRoundedMoney(retailPrice
													* slGrossUpValue * 100) / 100;
									finalPayment = finalPayment * quantity;
									finalPaymentTotal = finalPayment
											+ finalPaymentTotal;
									payment = payment + netPayment;

								}
							}

						} catch (BusinessServiceException e) {
							logger.error("Exception in fetching reimbursement rate"
									+ e.getMessage());
						}
					
					}
					if (finalPaymentTotal > maxValue) {
						result.setCode(ResultsCode.UPDATE_SO_COMPLETION_PARTS_MAX_PRICE_EXCEEDED.getCode());
						result.setMessage(ResultsCode.UPDATE_SO_COMPLETION_PARTS_MAX_PRICE_EXCEEDED.getMessage());
						validationErrors.add(result);
					}

				}
			}
			return validationErrors;
	}

	


	public IMobileSOActionsBO getMobileSOActionsBO() {
		return mobileSOActionsBO;
	}

	public void setMobileSOActionsBO(IMobileSOActionsBO mobileSOActionsBO) {
		this.mobileSOActionsBO = mobileSOActionsBO;
	}

	public INotificationService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(INotificationService notificationService) {
		this.notificationService = notificationService;
	}

	
}