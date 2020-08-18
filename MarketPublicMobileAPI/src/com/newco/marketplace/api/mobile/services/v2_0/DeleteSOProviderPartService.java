
package com.newco.marketplace.api.mobile.services.v2_0;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.mobile.beans.v2_0.DeleteSOProviderPartRequest;
import com.newco.marketplace.api.mobile.beans.v2_0.DeleteSOProviderPartResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.mobile.IMobileSOActionsBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.vo.mobile.InvoicePartsVO;

/**
 * This class would act as a Servicer class for delete part invoice 
 * 
 * @author Infosys
 * @version 1.0
 */

@APIRequestClass(DeleteSOProviderPartRequest.class)
@APIResponseClass(DeleteSOProviderPartResponse.class)
public class DeleteSOProviderPartService extends BaseService {

	private Logger logger = Logger.getLogger(DeleteSOProviderPartService.class);
	private IMobileSOActionsBO mobileSOActionsBO;
	
	public IMobileSOActionsBO getMobileSOActionsBO() {
		return mobileSOActionsBO;
	}

	public void setMobileSOActionsBO(IMobileSOActionsBO mobileSOActionsBO) {
		this.mobileSOActionsBO = mobileSOActionsBO;
	}
	
	/**
	 * This method is for delete  part invoice  for provider.
	 * 
	 * @param fromDate
	 *            String,toDate String
	 * @return String
	 */
	
	public DeleteSOProviderPartService() {
		super();
	}


	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {

		logger.info("Entering execute method");
		List<Integer> existingInvoicePartIdlist = new ArrayList<Integer>();
		List<InvoicePartsVO> existingInvoicePartlist = new ArrayList<InvoicePartsVO>();

		// get the so Id from the apiVO
		String soId = (String) apiVO.getSOId();
		DeleteSOProviderPartResponse deleteSOProviderPartResponse = new DeleteSOProviderPartResponse();
		DeleteSOProviderPartRequest deleteSOProviderPartRequest = (DeleteSOProviderPartRequest) apiVO
				.getRequestFromPostPut();
		String currentTrip = deleteSOProviderPartRequest.getCurrentTripNo();
		String tripStatus = deleteSOProviderPartRequest.getTripStatus();
		Integer currentTripId = null;
		String userName = null;
		Integer providerId = apiVO.getProviderResourceId();
		SecurityContext securityContext = getSecurityContextForVendor(providerId);
		if (null != securityContext) {
			userName = securityContext.getUsername();
		}

		try {

			// Checking if partIdlist is Empty

			if (null != deleteSOProviderPartRequest
					&& null != deleteSOProviderPartRequest.getInvoiceIdList()
					&& null != deleteSOProviderPartRequest.getInvoiceIdList()
							.getInvoiceId()
					&& deleteSOProviderPartRequest.getInvoiceIdList()
							.getInvoiceId().size() > 0) {

				List<Integer> partIdList = new ArrayList<Integer>();
				partIdList = deleteSOProviderPartRequest.getInvoiceIdList()
						.getInvoiceId();

				// Fetch existing invoice parts details associated with service
				// order
				existingInvoicePartlist = mobileSOActionsBO
						.checkIfPartExists(soId);
				// Fetching the buyer Id,and Wf state Id of So
				ServiceOrder serviceOrder = mobileSOActionsBO
						.getServiceOrder(soId);
				if (null != serviceOrder) {
					Integer wfStateId = serviceOrder.getWfStateId();
					Integer buyerId = serviceOrder.getBuyerId();

					if (!PublicMobileAPIConstant.HSR_BUYER_ID.equals(buyerId)) {
						deleteSOProviderPartResponse.setResults(Results
								.getError(
										ResultsCode.INVALID_BUYER.getMessage(),
										ResultsCode.INVALID_BUYER.getCode()));
						return deleteSOProviderPartResponse;
					}
					if (!PublicMobileAPIConstant.ORDER_ACTIVE.equals(wfStateId)) {
						deleteSOProviderPartResponse
								.setResults(Results.getError(
										ResultsCode.INVALID_SO_STATUS
												.getMessage(),
										ResultsCode.INVALID_SO_STATUS.getCode()));
						return deleteSOProviderPartResponse;
					}

					if (StringUtils.isNotBlank(currentTrip)
							&& StringUtils.isNumeric(currentTrip)) {

							//R12.3 - SL-230763 - START 
						/*openTrip = mobileSOActionsBO.validateLatestOpenTrip(
								Integer.parseInt(currentTrip), soId);*/
						
						currentTripId = mobileSOActionsBO.getTripId(
								Integer.parseInt(currentTrip), soId);
						if (null == currentTripId) {
							deleteSOProviderPartResponse.setResults(Results
									.getError(ResultsCode.INVALID_TRIP_NUMBER
											.getMessage(),
											ResultsCode.INVALID_TRIP_NUMBER
													.getCode()));
							return deleteSOProviderPartResponse;
						} else {
								 //R12.3 - SL-230763 - END
							// R12_0 Sprint 5:
							// Step 1)Check if all parts added have status
							// ADDED. If no, then throw an exception
							// Step 2)Else, Delete the part details.
							if (null != existingInvoicePartlist
									&& existingInvoicePartlist.size() > 0) {
								for (InvoicePartsVO partVO : existingInvoicePartlist) {
									if (null != partVO.getInvoiceId()) {
										//adding invoiceIds to the list
										Integer invoiceId= new Integer(partVO.getInvoiceId());
										existingInvoicePartIdlist.add(invoiceId);
									}

								}
								// Assuming that by the end of this for loop, if
								// there are no validation errors then we will
								// get the list of
								// partIds present in DB in
								// existingInvoicePartIdlist object
								if (existingInvoicePartIdlist
										.containsAll(partIdList)) {
									mobileSOActionsBO.addTripDetails(currentTripId,
											MPConstants.PART_CHANGE_TYPE,
											MPConstants.PART_DELETED, userName);
									
									//On so parts deletion, if the associated document is not mapped to any other part of another so, 
									//then should delete the document from so_document and document table
									
									//get the document id's from so_provider_invoice_doc associated with all the parts of the SO
									boolean afterDeletion = false;
									List<Integer> documentIdListBeforeDeletion = mobileSOActionsBO.getAssociatedDocuments(partIdList,afterDeletion);
									
									//Delete SO Parts
									mobileSOActionsBO
											.deleteInvoicePartDetail(partIdList,soId);
									
									//get the document id's from so_provider_invoice_doc after deletion, 
									//to check whether the document id which is deleted is associated with another so
									afterDeletion = true;
									List<Integer> documentIdListAfterDeletion = null;
									if(null!= documentIdListBeforeDeletion && documentIdListBeforeDeletion.size()>0 ){
										documentIdListAfterDeletion = mobileSOActionsBO.getAssociatedDocuments(documentIdListBeforeDeletion,afterDeletion);
									}
									if(null != documentIdListBeforeDeletion && null != documentIdListAfterDeletion ){
										List<Integer> documentIdExistingList = new ArrayList<Integer>();
										for(Integer documentIdBeforeDeletion: documentIdListBeforeDeletion){
											for(Integer documentIdtAfterDeletion: documentIdListAfterDeletion){
												if(documentIdBeforeDeletion.intValue() == documentIdtAfterDeletion.intValue()){
													documentIdExistingList.add(documentIdBeforeDeletion);
												}
											}
										}
										documentIdListBeforeDeletion.removeAll(documentIdExistingList);
										if(null != documentIdListBeforeDeletion && documentIdListBeforeDeletion.size()>0){
											mobileSOActionsBO.deleteDocumentFromParentTables(documentIdListBeforeDeletion);
										}
									}
									
									// R12_0 Sprint 4: Changes to update parts
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

								} else {
									deleteSOProviderPartResponse
											.setResults(Results
													.getError(
															ResultsCode.PART_NOT_ASSOCIATED_SO_OR_DELETED
																	.getMessage(),
															ResultsCode.PART_NOT_ASSOCIATED_SO_OR_DELETED
																	.getCode()));
									return deleteSOProviderPartResponse;
								}
							}

						}
						deleteSOProviderPartResponse = new DeleteSOProviderPartResponse(
								soId, Results.getSuccess());

					}

				}
			} else {
				deleteSOProviderPartResponse.setResults(Results.getError(
						ResultsCode.UNABLE_TO_PROCESS_THE_REQUEST.getMessage(),
						ResultsCode.UNABLE_TO_PROCESS_THE_REQUEST.getCode()));
				return deleteSOProviderPartResponse;
			}

		}

		catch (Exception e) {

			logger.error("DeleteSOProviderPartService.execute(): exception due to "
					+ e.getMessage());
			// set the error code as internal server error
			deleteSOProviderPartResponse.setResults(Results.getError(
					ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),
					ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
		}
		if (logger.isInfoEnabled()) {
			logger.info("Leaving DeleteSOProviderPartService.execute()");
		}
		// return the response object
		return deleteSOProviderPartResponse;
	}
	
	
}

			