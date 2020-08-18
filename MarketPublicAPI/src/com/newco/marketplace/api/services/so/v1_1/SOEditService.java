/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 22-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.create.SOCreateResponse;
import com.newco.marketplace.api.beans.so.edit.SOEditRequest;
import com.newco.marketplace.api.beans.so.edit.SOEditResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.exceptions.ValidationException;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.SOEditMapper;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFServiceOrderMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.LanguageParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.StarParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.ZipParameterBean;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.providersearch.IMasterCalculatorBO;
import com.newco.marketplace.business.iBusiness.providersearch.IProviderSearchBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IBuyerSOTemplateBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;
import com.newco.marketplace.dto.vo.serviceorder.FundingVO;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.LedgerConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

public class SOEditService extends SOBaseService {
	private Logger logger = Logger.getLogger(SOEditService.class);
	private IServiceOrderBO serviceOrderBO;
	private IProviderSearchBO provSearchObj;
	private IMasterCalculatorBO masterCalcObj;
	private IBuyerSOTemplateBO templateBO;
	private SOEditMapper soEditMapper;
	private IDocumentBO documentBO;
	private OFServiceOrderMapper ofServiceOrderMapper; 

	public SOEditService() {
		super(PublicAPIConstant.EDIT_XSD, PublicAPIConstant.SORESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,
				SOEditRequest.class, SOEditResponse.class);

	}

	/**
	 * This method does either edit order or increase funds.
	 */
	@Override
	protected IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
		logger.info("Entering SOEditService.executeLegacyService()");
		String svcOrdrId = apiVO.getSOId();
		int buyerId = apiVO.getBuyerIdInteger();
		SOEditRequest soEditRequest = (SOEditRequest) apiVO.getRequestFromPostPut();
		SecurityContext securityContext = getSecCtxtForBuyerAdmin(buyerId);
		try{
			if (isServiceOrderEditable(svcOrdrId)){
				OrderFulfillmentRequest request = ofServiceOrderMapper.editServiceOrder(svcOrdrId, soEditRequest.getServiceOrder(), securityContext);
				OrderFulfillmentResponse ofResponse = ofHelper.runOrderFulfillmentProcess(svcOrdrId, SignalType.EDIT_ORDER, request);
				return ofServiceOrderMapper.editSOResponseMapping(ofResponse);
			}
			//the below code has been moved to another API updateSO
			
			/*else {
				OrderFulfillmentRequest request = ofServiceOrderMapper.mapServiceOrderPrice(soEditRequest.getServiceOrder().getPricing(), securityContext);
				OrderFulfillmentResponse ofResponse = ofHelper.runOrderFulfillmentProcess(svcOrdrId, SignalType.ADD_FUND, request);
				return ofServiceOrderMapper.editSOResponseMapping(ofResponse);
			}*/
			//code included for returning error response
			else{
				return createErrorResponse(ResultsCode.EDIT_INVALID_STATE.getMessage(), ResultsCode.EDIT_INVALID_STATE.getCode());
			}
        }
		catch(ValidationException slb){ 
       	 	return createErrorResponse(slb.getErrorMessages(), ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS.getCode());
		} 
		catch (BusinessServiceException bse){
			logger.error("executeOrderFulfillmentService(): BusinessServiceException occured: ", bse);
			return createGenericErrorResponse();
		}
	}

	private boolean isServiceOrderEditable(String soId) throws BusinessServiceException {
		ServiceOrder serviceOrder = serviceOrderBO.getServiceOrder(soId);
		int statusId = serviceOrder.getWfStateId();
		return statusId == OrderConstants.DRAFT_STATUS || statusId == OrderConstants.ROUTED_STATUS;
	}
	
	private SOEditResponse createGenericErrorResponse(){
		return createErrorResponse(ResultsCode.GENERIC_ERROR.getMessage(), ResultsCode.GENERIC_ERROR.getCode());
	}
	
	private SOEditResponse createErrorResponse(String message, String code){
		SOEditResponse createResponse = new SOEditResponse();
		Results results = Results.getError(message, code);
		createResponse.setResults(results);
		return createResponse;
	}
	
	/**
	 * This method dispatches the Edit Service order request.
	 * 
	 * @param apiVO
	 *            APIRequestVO
	 * @return IAPIResponse
	 */
	public IAPIResponse executeLegacyService(APIRequestVO apiVO) {
		SecurityContext securityContext = null;
		logger.info("Entering SOEditService.executeLegacyService()");
		SOEditRequest soEditRequest = new SOEditRequest();
		SOEditResponse soEditResponse = new SOEditResponse();
		List<String> invalidDocumentList = new ArrayList<String>();
		ServiceOrder serviceOrderEdited = null;
		soEditRequest = (SOEditRequest) apiVO.getRequestFromPostPut();
		int buyerId = apiVO.getBuyerIdInteger();
		String soId =apiVO.getSOId();
		try {
				securityContext = getSecurityContextForBuyerAdmin(buyerId);
			} catch (Exception exception) {
				logger
						.error("Exception occurred while accessing security context using resourceId");
			}
				;
				ServiceOrder serviceOrder = null;
				// Getting service order details
				try {
					serviceOrder = serviceOrderBO.getServiceOrder(soId);
						int statusId = serviceOrder.getWfStateId();
						if (statusId == OrderConstants.DRAFT_STATUS
								|| statusId == OrderConstants.ROUTED_STATUS
								|| statusId == OrderConstants.EXPIRED_STATUS) {
							
							//TODO: The below code is used for editing Parts details for a Sears Order. 
							//The following business logic needs to be analyzed before un-commenting the code.
							/*if (statusId == OrderConstants.ROUTED_STATUS || statusId == OrderConstants.EXPIRED_STATUS) {								
								if (!isEditAllowed(soEditRequest, serviceOrder,
										securityContext, buyerId)) {
									Results results = Results.getError(
											ResultsCode.CANNOT_EDIT_SO
													.getMessage(),
											ResultsCode.CANNOT_EDIT_SO
													.getCode());
									soEditResponse.setResults(results);

									return soEditResponse;
								}
							}*/
							
							HashMap<String, Object> soMap = soEditMapper
									.mapServiceOrder(serviceOrder,
											soEditRequest.getServiceOrder(),
											securityContext);
							if (null != soMap.get(PublicAPIConstant.ERROR_KEY)) {
								Collection errorValues = soMap.values();
								Iterator errorList = errorValues.iterator();
								if (errorList.hasNext()) {
									String errorMessage = (String) errorList
											.next();
									Results results = Results
											.getError(
													errorMessage,
													ResultsCode.INVALID_OR_MISSING_REQUIRED_PARAMS
															.getCode());
									soEditResponse.setResults(results);

									return soEditResponse;

								}
							}
							serviceOrderEdited = (ServiceOrder) soMap
									.get(PublicAPIConstant.SERVICE_ORDER);
							if ((statusId == OrderConstants.ROUTED_STATUS || statusId == OrderConstants.EXPIRED_STATUS)
									&& soEditRequest.getProviderRouteInfo() != null) {
								findRoutedProviders(soEditRequest,
										serviceOrderEdited, buyerId);
							}else if(soEditRequest.getProviderRouteInfo() == null){
								List<RoutedProvider> providerList=serviceOrderEdited.getRoutedResources();
									Iterator<RoutedProvider> routedProviderItr = providerList.iterator();
									while (routedProviderItr.hasNext()) {
										RoutedProvider provider = (RoutedProvider) routedProviderItr.next();
										provider.setEmailSent(false);
									}
							}
							List titleList = (List) soMap
									.get(PublicAPIConstant.FILE_TITLE);
							logger
									.info("Edit SO Mapping Completed. Now calling ServiceOrderBO"
											+ "for actual SO Edit");
							ProcessResponse processResponse = new ProcessResponse();
							if (statusId == OrderConstants.ROUTED_STATUS
									|| statusId == OrderConstants.EXPIRED_STATUS) {
								if (serviceOrderEdited.getRoutedResources()
										.size() > 0) {
									processResponse = serviceOrderBO
											.processUpdateDraftSO(
													serviceOrderEdited,
													securityContext);
									processResponse = serviceOrderBO
											.processReRouteSO(buyerId, soId,
													false, securityContext);
									if (!(processResponse.getCode()
											.equalsIgnoreCase(ServiceConstants.VALID_RC))) {
										Results results = Results.getError(
												processResponse.getMessages()
														.get(0),
												ResultsCode.EDIT_FAILURE
														.getCode());
										soEditResponse.setResults(results);

										return soEditResponse;
									}
								} else {
									Results results = Results.getError(
											ResultsCode.EDIT_ROUTE_NO_PROVIDERS
													.getMessage(),
											ResultsCode.EDIT_ROUTE_NO_PROVIDERS
													.getCode());
									soEditResponse.setResults(results);

									return soEditResponse;
								}
							} else {
								processResponse = serviceOrderBO
										.processUpdateDraftSO(
												serviceOrderEdited,
												securityContext);
							}
							logger
									.info("Association of Buyer Documents with SOId--->starts");
							if ((null != titleList) && titleList.size() > 0) {
								associateSOWithDocumentsForTitle(
										serviceOrderEdited.getSoId(),
										securityContext.getCompanyId(),
										titleList, invalidDocumentList);
							} else if (null != soEditRequest.getServiceOrder()
									.getAttachments()
									&& null != soEditRequest.getServiceOrder()
											.getAttachments().getFilenameList()) {
								List<String> filenameList = soEditRequest
										.getServiceOrder().getAttachments()
										.getFilenameList();
								if (!filenameList.isEmpty()) {
									invokeDocumentList(serviceOrderEdited
											.getSoId(), securityContext
											.getCompanyId(), filenameList,
											invalidDocumentList);
								}
							}

						} else if (statusId == OrderConstants.ACCEPTED_STATUS
								|| statusId == OrderConstants.ACTIVE_STATUS
								|| statusId == OrderConstants.CANCELLED_STATUS
								|| statusId == OrderConstants.VOIDED_STATUS
								|| statusId == OrderConstants.PROBLEM_STATUS
								|| statusId == OrderConstants.COMPLETED_STATUS) {
							try {
								if (statusId == OrderConstants.ACCEPTED_STATUS
										|| statusId == OrderConstants.ACTIVE_STATUS
										|| statusId == OrderConstants.CANCELLED_STATUS
										|| statusId == OrderConstants.VOIDED_STATUS) {
									editSubStatus(soEditRequest, serviceOrder,
											securityContext);
								}
								if (statusId == OrderConstants.ACCEPTED_STATUS
										|| statusId == OrderConstants.ACTIVE_STATUS
										|| statusId == OrderConstants.PROBLEM_STATUS) {
									editPrice(soEditRequest, serviceOrder,
											buyerId, securityContext);

								}
								if (statusId == OrderConstants.ACCEPTED_STATUS
										|| statusId == OrderConstants.ACTIVE_STATUS
										|| statusId == OrderConstants.COMPLETED_STATUS
										|| statusId == OrderConstants.PROBLEM_STATUS) {
									editParts(soEditRequest, serviceOrder,
											securityContext);
								}
							} catch (Exception e) {
								Results results = Results.getError(e
										.getMessage(), ResultsCode.EDIT_FAILURE
										.getCode());
								soEditResponse.setResults(results);

								return soEditResponse;

							}
						} else {
							Results results = Results
									.getError(ResultsCode.EDIT_INVALID_STATE
											.getMessage(),
											ResultsCode.EDIT_FAILURE.getCode());
							soEditResponse.setResults(results);

							return soEditResponse;
						}
						soEditResponse = new SOEditResponse();
						soEditResponse = soEditMapper.editSOResponseMapping(
								serviceOrder.getSoId(), invalidDocumentList);
					} catch (Exception e) {
					Results results = Results.getError(ResultsCode.EDIT_FAILURE
							.getMessage(), ResultsCode.EDIT_FAILURE.getCode());
					soEditResponse.setResults(results);

					return soEditResponse;
				}
			
			logger.info("Leaving SOEditService.execute()");

		return soEditResponse;

	}

	/**
	 * This method for editing parts details
	 * 
	 * @param soEditRequest,serviceOrder,securityContext
	 *            throws Exception
	 */
	private boolean isEditAllowed(SOEditRequest soEditRequest,
			ServiceOrder serviceOrder, SecurityContext securityContext,
			int buyerId) throws Exception {
		boolean result = false;
		boolean preFunded = true;
		try {
			if (securityContext != null)
				securityContext.setIncreaseSpendLimitInd(false);
			if (serviceOrder.getFundingTypeId() != null
					&& LedgerConstants.FUNDING_TYPE_NON_FUNDED == serviceOrder
							.getFundingTypeId().intValue()) {
				preFunded = false;
			}
			FundingVO fundingVO = new FundingVO();
			if (serviceOrder.getWfStateId() == OrderConstants.ROUTED_STATUS
					&& preFunded) {
				fundingVO = serviceOrderBO
						.checkBuyerFundsForIncreasedSpendLimit(serviceOrder,
								buyerId);
			}
			if (!fundingVO.isEnoughFunds()
					&& (serviceOrder.getFundingTypeId() != null 
						&& LedgerConstants.SHC_FUNDING_TYPE != serviceOrder.getFundingTypeId().intValue()
						&& LedgerConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER != serviceOrder.getFundingTypeId().intValue())) {
				serviceOrder.setUpdateSoPriceFlag(false);
				if (securityContext != null)
					securityContext.setIncreaseSpendLimitInd(true);
			}
			if (LedgerConstants.SHC_FUNDING_TYPE == serviceOrder.getFundingTypeId().intValue()
					|| LedgerConstants.ACH_FUNDING_TYPE_EXTERNAL_BUYER == serviceOrder.getFundingTypeId().intValue()
					|| (fundingVO.isEnoughFunds() && !securityContext
							.isIncreaseSpendLimitInd())) {
				result = true;

			}
			return result;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * This method for editing parts details
	 * 
	 * @param soEditRequest,serviceOrder,securityContext
	 *            throws Exception
	 */
	private void editParts(SOEditRequest soEditRequest,
			ServiceOrder serviceOrder, SecurityContext securityContext)
			throws Exception {
		try {
			ProcessResponse processResponse = null;
			if (soEditRequest.getServiceOrder().getParts() != null) {
				HashMap<String, Object> soMap = soEditMapper
						.mapServiceOrderForParts(serviceOrder, soEditRequest
								.getServiceOrder(), securityContext);
				if (null != soMap.get(PublicAPIConstant.ERROR_KEY)) {
					Collection errorValues = soMap.values();
					Iterator errorList = errorValues.iterator();
					if (errorList.hasNext()) {
						String errorMessage = (String) errorList.next();
						throw new Exception(errorMessage);

					}
				}
				ServiceOrder serviceOrderEdited = (ServiceOrder) soMap
						.get(PublicAPIConstant.SERVICE_ORDER);

				processResponse=serviceOrderBO.updatePartsShippingInfo(serviceOrderEdited
						.getSoId(), serviceOrderEdited.getParts(),
						securityContext);
				if (!(processResponse.getCode()
						.equalsIgnoreCase(ServiceConstants.VALID_RC))) {
					throw new Exception(processResponse.getMessages()
							.get(0));
				}
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * This method for editing substatus
	 * 
	 * @param soEditRequest,serviceOrder,securityContext
	 *            throws Exception
	 */
	private void editSubStatus(SOEditRequest soEditRequest,
			ServiceOrder serviceOrder, SecurityContext securityContext)
			throws Exception {
		try {
			if (soEditRequest.getServiceOrder().getSubStatus() != null) {
				Integer subStatusId = serviceOrderBO.getSubStatusId(
						soEditRequest.getServiceOrder().getSubStatus(),
						serviceOrder.getWfStateId());
				if (subStatusId == null || subStatusId == 0) {
					throw new Exception(
							"No such substatus exist for the status");

				} else {
					serviceOrderBO.updateSOSubStatus(serviceOrder.getSoId(),
							subStatusId, securityContext);
				}
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * This method for editing Pricing details
	 * 
	 * @param soEditRequest,serviceOrder,securityContext
	 *            throws Exception
	 */
	private void editPrice(SOEditRequest soEditRequest,
			ServiceOrder serviceOrder, int buyerId,
			SecurityContext securityContext) throws Exception {
		ProcessResponse processResponse = null;
		if (soEditRequest.getServiceOrder().getPricing() != null) {
			String reqSpendLabour = soEditRequest.getServiceOrder()
					.getPricing().getLaborSpendLimit();
			String reqSpendParts = soEditRequest.getServiceOrder().getPricing()
					.getPartsSpendLimit();
			Double spendLimitLabour = reqSpendLabour != null ? Double
					.parseDouble(reqSpendLabour) : serviceOrder
					.getSpendLimitLabor();
			Double spendLimitParts = reqSpendParts != null ? Double
					.parseDouble(reqSpendParts) : serviceOrder
					.getSpendLimitParts();
			String comment = soEditRequest.getServiceOrder().getPricing()
					.getSpendLimitComments() != null ? soEditRequest
					.getServiceOrder().getPricing().getSpendLimitComments()
					: "";
			if (reqSpendLabour != null || reqSpendParts != null) {
				try {
					processResponse = serviceOrderBO.updateSOSpendLimit(
							serviceOrder.getSoId(), spendLimitLabour,
							spendLimitParts, comment, buyerId, true, false,
							securityContext);
					if (!(processResponse.getCode()
							.equalsIgnoreCase(ServiceConstants.VALID_RC))) {
						throw new Exception(processResponse.getMessages()
								.get(0));
					}
				} catch (Exception e) {
					throw new Exception(e.getMessage());
				}
			}
		}
	}
	

	/**
	 * This method for finding providers
	 * 
	 * @param soEditRequest,serviceOrderEdited,buyerId
	 * 
	 */
	private void findRoutedProviders(SOEditRequest soEditRequest,
			ServiceOrder serviceOrderEdited, int buyerId) {
		List<Integer> routedResourceIds = new ArrayList<Integer>();
		if (soEditRequest.getProviderRouteInfo().getSpecificProviders() != null
				&& soEditRequest.getProviderRouteInfo().getSpecificProviders()
						.getResourceId().size() > 0) {
			routedResourceIds = soEditRequest.getProviderRouteInfo()
					.getSpecificProviders().getResourceId();

		} else {
			// Getting the list of routed providers based on
			// the
			// filter criteria
			ArrayList<RatingParameterBean> ratingParamBeans = new ArrayList<RatingParameterBean>();

			// Getting filter condition for zip
			if (soEditRequest.getProviderRouteInfo().getMaxDistance() != null) {
				ZipParameterBean zipBean = new ZipParameterBean();
				zipBean.setRadius(Integer.parseInt(soEditRequest
						.getProviderRouteInfo().getMaxDistance()));
				zipBean.setZipcode(serviceOrderEdited.getServiceLocation()
						.getZip());
				zipBean.setCredentialId(serviceOrderEdited
						.getPrimarySkillCatId());
				ratingParamBeans.add(zipBean);
			}

			// Getting filter condition for minimum rating
			if (soEditRequest.getProviderRouteInfo().getMinRating() != null
					&& Double.parseDouble(soEditRequest.getProviderRouteInfo()
							.getMinRating()) >= 0) {
				StarParameterBean starBean = new StarParameterBean();
				starBean.setNumberOfStars(Double.parseDouble(soEditRequest
						.getProviderRouteInfo().getMinRating()));
				ratingParamBeans.add(starBean);
			}
			// Getting filter condition for language
			if (soEditRequest.getProviderRouteInfo().getLanguage() != null) {
				LanguageParameterBean langBean = new LanguageParameterBean();
				List<Integer> languageIds = new ArrayList<Integer>();
				// Calling BO method for getting list of
				// language
				// ids from language names
				languageIds = serviceOrderBO.getLanguageIds(soEditRequest
						.getProviderRouteInfo().getLanguage().getLanguages());
				langBean.setSelectedLangs(languageIds);
				ratingParamBeans.add(langBean);
			}

			ProviderSearchCriteriaVO provSearchVO = new ProviderSearchCriteriaVO();
			provSearchVO.setBuyerID(buyerId);
			provSearchVO.setServiceLocation(serviceOrderEdited
					.getServiceLocation());
			provSearchVO.setServiceOrderID(serviceOrderEdited.getSoId());
			if (serviceOrderEdited.getPrimarySkillCatId() != null) {
				ArrayList<Integer> skillNodeIds = new ArrayList<Integer>();
				List<Integer> skillTypes = new ArrayList<Integer>();
				if (null != serviceOrderEdited.getTasks()
						&& serviceOrderEdited.getTasks().size() > 0) {
					// If we have tasks then assign the
					// SubCategory/Category
					// to the list else select the main
					// category
					for (ServiceOrderTask taskDto : serviceOrderEdited
							.getTasks()) {
						if (taskDto.getSubCategoryName() != null) {
							skillNodeIds.add(taskDto.getSkillNodeId());
						} else if (taskDto.getCategoryName() != null) {
							skillNodeIds.add(taskDto.getSkillNodeId());
						} else {
							skillNodeIds.add(serviceOrderEdited
									.getPrimarySkillCatId());
						}
						if (taskDto.getServiceTypeId() != null) {
							skillTypes.add(taskDto.getServiceTypeId());
						}
					}
					provSearchVO.setSkillNodeIds(skillNodeIds);
					provSearchVO.setSkillServiceTypeId(skillTypes);
				} else {
					skillNodeIds.add(serviceOrderEdited.getPrimarySkillCatId());
					provSearchVO.setSkillNodeIds(skillNodeIds);
				}
			}
			// Add SPN id to provider search filters; if
			// available
			// in
			// the buyer template

			BuyerSOTemplateDTO template = templateBO
					.loadBuyerSOTemplate(buyerId, soEditRequest
							.getProviderRouteInfo().getTemplate());
			if (template != null && template.getSpnId() != null) {
				provSearchVO.setSpnID(template.getSpnId());
			}

			// Do initial provider search
			ArrayList<ProviderResultVO> resultsAL = provSearchObj
					.getProviderList(provSearchVO);

			// Apply filters and get refined results
			List<ProviderResultVO> filteredResultsAL = masterCalcObj
					.getFilteredProviderList(ratingParamBeans, resultsAL);

			// Further filtering on percentage matching
			// providers

			for (ProviderResultVO vo : filteredResultsAL) {
				if (template != null) {
					if (vo.getPercentageMatch().doubleValue() >= template
							.getSpnPercentageMatch().doubleValue()) {
						routedResourceIds.add(Integer.valueOf(vo
								.getResourceId()));
					}
				} else {
					routedResourceIds.add(Integer.valueOf(vo.getResourceId()));
				}
			}

		}
		List<RoutedProvider> routedResources = new ArrayList<RoutedProvider>();
		RoutedProvider routedProvider = null;
		Integer tierId = null;
		Iterator<Integer> routedProviderItr = routedResourceIds.iterator();
		while (routedProviderItr.hasNext()) {
			routedProvider = new RoutedProvider();

			routedProvider.setResourceId((Integer) routedProviderItr.next());
			routedProvider.setSoId(serviceOrderEdited.getSoId());
			routedProvider.setSpnId(serviceOrderEdited.getSpnId());
			routedProvider.setPriceModel(serviceOrderEdited.getPriceModel());
			routedProvider.setTierId(tierId);
			routedResources.add(routedProvider);
		}
		serviceOrderEdited.setRoutedResources(routedResources);

	}

	/**
	 * This method is for associating the documents with service order.
	 * 
	 * @param serviceOrderId
	 *            String
	 * @param ownerID
	 *            Integer
	 * @param fileNameList
	 *            List<String>
	 * @param invalidDocumentList
	 *            List<String>
	 * @return String
	 */
	private List<String> invokeDocumentList(String serviceOrderId,
			Integer ownerID, List<String> fileNameList,
			List<String> invalidDocumentList) {
		invalidDocumentList = associateSOWithDocuments(serviceOrderId, ownerID,
				fileNameList, invalidDocumentList);
		return invalidDocumentList;
	}

	/**
	 * This method is for associating the documents with service order.
	 * 
	 * @param serviceOrderId
	 *            String
	 * @param ownerID
	 *            Integer
	 * @param fileNameList
	 *            List<String>
	 * @return String
	 */

	public List<String> associateSOWithDocuments(String serviceOrderId,
			Integer ownerID, List<String> fileNameList,
			List<String> invalidDocumentList) {
		DocumentVO documentVo = null;
		try {
			for (String fileName : fileNameList) {
				logger.info("Checks if the document with the same name::"
						+ fileName + " already exists");
				documentVo = documentBO.retrieveDocumentByFileNameAndEntityID(
						Constants.DocumentTypes.BUYER, fileName, ownerID);
				if (null != documentVo) {
					getDocumentDetails(documentVo.getTitle(), ownerID,
							serviceOrderId, fileName, invalidDocumentList);
				} else {
					if (null != fileName) {
						invalidDocumentList.add(fileName);
					}
					logger.info("Adding the unAssociated Document"
							+ " to invalidDocumentList");
				}
			}
		} catch (Exception e) {
			logger.error("Association of Buyer Document with SoId was"
					+ " not Successful " + e);
		}
		return invalidDocumentList;
	}

	public List<String> associateSOWithDocumentsForTitle(String serviceOrderId,
			Integer ownerID, List<String> fileNameList,
			List<String> invalidDocumentList) {
		try {
			for (String title : fileNameList) {
				getDocumentDetails(title, ownerID, serviceOrderId, null,
						invalidDocumentList);
			}
		} catch (Exception e) {
			logger.error("Association of Buyer Document with SoId was"
					+ " not Successful " + e);
		}
		return invalidDocumentList;
	}

	/**
	 * This method is for getting the documents and associating with service
	 * order.
	 * 
	 * @param title
	 *            String
	 * @param ownerID
	 *            Integer
	 * @param serviceOrderId
	 *            String
	 * @return String
	 */
	private void getDocumentDetails(String title, Integer ownerID,
			String serviceOrderId, String fileName,
			List<String> invalidDocumentList) {

		DocumentVO documentVo = null;
		try {

			logger.info("Getting the document details using SO Title");
			documentVo = documentBO.retrieveDocumentByTitleAndEntityID(
					Constants.DocumentTypes.BUYER, title, ownerID);
		} catch (Exception exception) {
			logger.error("Exception occurred while retrieving the document"
					+ exception);
		}
		try {
			if (null != documentVo) {
				documentVo.setDocumentId(null);
				documentVo.setSoId(serviceOrderId);
				documentVo.setCompanyId(ownerID);
				logger.info("Going to associate document with ServiceOrder");
				ProcessResponse insertDocResponse = documentBO
						.insertServiceOrderDocument(documentVo);
				if (null != insertDocResponse) {
					if (!insertDocResponse.isSuccess()) {
						logger.info("Adding the unAssociated Document to "
								+ "invalidDocumentList");
						if (null != fileName) {
							invalidDocumentList.add(fileName);
						}
					}
				} else {
					if (null != fileName) {
						invalidDocumentList.add(fileName);
					}
					logger.info("Adding the unAssociated Document to "
							+ "invalidDocumentList");
				}

			} else {
				if (null != fileName) {
					invalidDocumentList.add(fileName);
				} else {
					invalidDocumentList.add(title);
				}
				logger.info("Adding the unAssociated Document to "
						+ "invalidDocumentList");
			}
		} catch (Exception e) {
			logger.error("caught exception");
		}

	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public void setProvSearchObj(IProviderSearchBO provSearchObj) {
		this.provSearchObj = provSearchObj;
	}

	public void setMasterCalcObj(IMasterCalculatorBO masterCalcObj) {
		this.masterCalcObj = masterCalcObj;
	}

	public void setTemplateBO(IBuyerSOTemplateBO templateBO) {
		this.templateBO = templateBO;
	}

	public void setSoEditMapper(SOEditMapper soEditMapper) {
		this.soEditMapper = soEditMapper;
	}

	public void setDocumentBO(IDocumentBO documentBO) {
		this.documentBO = documentBO;
	}

	public void setOfServiceOrderMapper(OFServiceOrderMapper ofServiceOrderMapper) {
		this.ofServiceOrderMapper = ofServiceOrderMapper;
	}

}
