/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 20-Oct-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so.v1_1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.post.SOPostRequest;
import com.newco.marketplace.api.beans.so.post.SOPostResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFPostMapper;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SOPostMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.LanguageParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.StarParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.ZipParameterBean;
import com.newco.marketplace.business.iBusiness.providersearch.IMasterCalculatorBO;
import com.newco.marketplace.business.iBusiness.providersearch.IProviderSearchBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IBuyerSOTemplateBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.sears.os.utils.DateValidationUtils;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

/**
 * This is the service class for executing the Post Service Order request
 * 
 * @author Infosys
 * @version 1.0
 */
@APIRequestClass(SOPostRequest.class)
@APIResponseClass(SOPostResponse.class)
public class SOPostService extends SOBaseService {
	private Logger logger = Logger.getLogger(SOPostService.class);
	private IServiceOrderBO serviceOrderBO;
	private IProviderSearchBO provSearchObj;
	private IMasterCalculatorBO masterCalcObj;
	private IBuyerSOTemplateBO templateBO;
	private SOPostMapper postMapper;
	private OFPostMapper ofPostMapper;


	/**
	 * public constructor
	 */
	public SOPostService() {
		super(PublicAPIConstant.POST_XSD, PublicAPIConstant.SORESPONSE_XSD,
				PublicAPIConstant.SORESPONSE_NAMESPACE,
				PublicAPIConstant.SO_RESOURCES_SCHEMAS_V1_1,
				PublicAPIConstant.SORESPONSE_SCHEMALOCATION,
				SOPostRequest.class, SOPostResponse.class);
	}

	/**
	 * This method dispatches the Post Service Order request.
	 */
	@Override
	public IAPIResponse executeOrderFulfillmentService(APIRequestVO apiVO) {
		logger.info("Entering SOPostService.executeOrderFulfillmentService()");
		SOPostRequest postRequest = (SOPostRequest) apiVO.getRequestFromPostPut();
		String soId = apiVO.getSOId();
		SecurityContext securityContext = getSecCtxtForBuyerAdmin(apiVO.getBuyerIdInteger());
		SOElementCollection soec = new SOElementCollection();

		//populate routed providers
		SOPostResponse soPostResponse = new SOPostResponse();
		ProcessResponse postResponse = new ProcessResponse();
		List<Integer> providerIds = new ArrayList<Integer>();
		ServiceOrder so = null;
		try {
			so = serviceOrderBO.getServiceOrder(apiVO.getSOId());
			if(so!=null){
				
				int substatus = 0;
				if(so.getWfSubStatusId() != null){
					substatus = so.getWfSubStatusId().intValue();
				}
				if (so.getWfStateId() == OrderConstants.ROUTED_STATUS
						|| (so.getWfStateId() == OrderConstants.DRAFT_STATUS 
								&& OrderConstants.MISSING_INFORMATION_SUBSTATUS != substatus )
								// Removing SL-20531
								// && OrderConstants.MANUAL_REVIEW != substatus 
								// && OrderConstants.REPEAT_REPAIR != substatus)
						|| so.getWfStateId() == OrderConstants.EXPIRED_STATUS){


					// Validating the service order
					postResponse = validateServiceOrder(so,apiVO.getBuyerIdInteger());
					if (postResponse.getCode().equalsIgnoreCase(PublicAPIConstant.ONE)) {
						if (postRequest.getProviderRouteInfo().getSpecificProviders() != null
								&& postRequest.getProviderRouteInfo().getSpecificProviders().getResourceId().size() > 0) {
							
							// Validate the providers in the list.
							List<Integer> routedResourceIds = postRequest.getProviderRouteInfo()
								.getSpecificProviders().getResourceId();
							
							// SL-20531 validate the provider status
							List<Integer> invalidProviderList = new ArrayList<Integer>();
							List<Integer> provList = new ArrayList<Integer>();
							List<VendorResource> vendorResources = new ArrayList<VendorResource>();
							vendorResources = provSearchObj.getVendorResourceList(routedResourceIds);

							if (null != vendorResources && vendorResources.size() > 0) {
								for (VendorResource resource : vendorResources) {
									logger.info("Add the Valid provider "+ resource.getResourceId());
									provList.add(resource.getResourceId());
								}
								
								// Check the count 
								if(routedResourceIds.size()!=provList.size()){
									routedResourceIds.removeAll(provList);
									invalidProviderList.addAll(routedResourceIds);
									logger.info("Invalid providers are present..."+invalidProviderList.size());
								}								
								if (invalidProviderList.size() > 0) {
									logger.info("Few providers in the list are not in Approved status");
									String errormessage = ResultsCode.NOT_APPROVED_PROVIDERS.getMessage();
									errormessage = errormessage
											+ " ["
											+ StringUtils.join(invalidProviderList, ',')
											+ "].";
									return createErrorResponse(errormessage,ResultsCode.NOT_APPROVED_PROVIDERS
										.getCode());
								} else {
									// SL-20531 Changed to avoid repeated DB calls
									ofPostMapper.setProvidersInSoElementFromResourceObject(soec, vendorResources);
								}
							} else {
								logger.info("None of the provider(s) in the request is available in the DB");
								String errormessage = ResultsCode.NOT_APPROVED_PROVIDERS.getMessage();
								errormessage = errormessage
										+ " ["
										+ StringUtils.join(routedResourceIds, ',')
										+ "].";
								return createErrorResponse(errormessage,ResultsCode.NOT_APPROVED_PROVIDERS
									.getCode());
							}
						} else {
							providerIds = findResources(apiVO, so);
							if (providerIds.isEmpty()) {
								logger.info("No Providers selected");
								return createErrorResponse(ResultsCode.EDIT_ROUTE_NO_PROVIDERS.getMessage(),
										ResultsCode.EDIT_ROUTE_NO_PROVIDERS.getCode());
							} else {
								ofPostMapper.setProvidersInSoElement(soec,providerIds);
							}
						}
					} else {
						return createErrorResponse(postResponse.getMessage(),postResponse.getCode());
					}
				} else {
					logger.info("Invalid State");
					return createErrorResponse(ResultsCode.POST_INVALID_STATE.getMessage(), 
							ResultsCode.POST_INVALID_STATE.getCode());
				}
			}
		} catch (BusinessServiceException e) {
			logger.info("BusinessServiceException ::" + e.getMessage());
			return createErrorResponse(e.getMessage(),ResultsCode.GENERIC_ERROR.getCode());
		}
		if (securityContext.getRoleId().intValue() != 5) {
			try {

				Double buyerMaxTransactionLimit = serviceOrderBO.getBuyerMaxTransactionLimit(securityContext
					.getVendBuyerResId(), apiVO.getBuyerIdInteger());
				securityContext.setMaxSpendLimitPerSO(buyerMaxTransactionLimit.doubleValue());
				if (isOrderPriceAboveMaxTransactionLimit(so, securityContext,true)) {
					return createErrorResponse(ResultsCode.BUYER_UPFUND_LIMIT_PER_TRANSACTION_ERROR
									.getMessage(),ResultsCode.BUYER_UPFUND_LIMIT_PER_TRANSACTION_ERROR
									.getCode());
				}

			} catch (Exception e) {
				logger.error("SOPostService.execute(): Exception occurred while " +
						"accessing max transaction limit with Buyer id: ",e);
			}
		}
		Identification identity = OFMapper.createBuyerIdentFromSecCtx(securityContext);
		OrderFulfillmentResponse ofResponse = ofHelper.
			runOrderFulfillmentProcess(soId, SignalType.POST_ORDER, soec,identity);
		return ofPostMapper.mapServiceOrder(ofHelper.getServiceOrder(soId),ofResponse);
	}

	public List<Integer> findResources(APIRequestVO apiVO, ServiceOrder serviceOrder){
		SOPostResponse soPostResponse = new SOPostResponse();
		List<Integer> routedResourceIds = new ArrayList<Integer>();
		Results results = null;
		SecurityContext securityContext = null;


		SOPostRequest postRequest = (SOPostRequest) apiVO.getRequestFromPostPut();
		int buyerId = apiVO.getBuyerIdInteger();
		String soId = apiVO.getSOId();
		ArrayList<RatingParameterBean> ratingParamBeans = new ArrayList<RatingParameterBean>();
		try{
			// Getting filter condition for zip
			if (postRequest.getProviderRouteInfo().getMaxDistance() != null) {
				addZipBean(ratingParamBeans, postRequest.getProviderRouteInfo().getMaxDistance(), serviceOrder);
			}

			// Getting filter condition for minimum rating
			if (postRequest.getProviderRouteInfo().getMinRating() != null
					&& Double.parseDouble(postRequest.getProviderRouteInfo().getMinRating()) >= 0) {
				addStarRatingBean(ratingParamBeans,postRequest.getProviderRouteInfo().getMinRating());

			}
			// Getting filter condition for language
			if (postRequest.getProviderRouteInfo().getLanguage() != null) {
				addLanguageBean(ratingParamBeans,postRequest.getProviderRouteInfo().getLanguage().getLanguages());
			}

			ProviderSearchCriteriaVO provSearchVO = new ProviderSearchCriteriaVO();
			String template = postRequest.getProviderRouteInfo().getTemplate();
			generateProviderSearchCriteria(provSearchVO,serviceOrder,buyerId);

			// Add SPN id to provider search filters; if available in the buyer template
			BuyerSOTemplateDTO templateCriteria = templateBO.loadBuyerSOTemplate(buyerId, template);
			if (templateCriteria != null && templateCriteria.getSpnId() != null) {
				provSearchVO.setSpnID(templateCriteria.getSpnId());
			}

			// Do initial provider search
			ArrayList<ProviderResultVO> resultsAL = provSearchObj.getProviderList(provSearchVO);

			// Apply filters and get refined results
			List<ProviderResultVO> filteredResultsAL = masterCalcObj.getFilteredProviderList(ratingParamBeans, resultsAL);

			// Further filtering on percentage matching providers
			filterproviders(filteredResultsAL,templateCriteria,routedResourceIds);

		}

		catch (Exception e) {
			logger.error("SOPostService.execute(): Exception " + "Occurred: ", e);
			results = Results.getError(e.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			soPostResponse.setResults(results);
		}			
		return routedResourceIds;


	}

	/**
	 * @param filteredResultsAL
	 * @param templateCriteria
	 * @param routedResourceIds
	 */
	private void filterproviders(List<ProviderResultVO> filteredResultsAL,
			BuyerSOTemplateDTO templateCriteria, List<Integer> routedResourceIds) {
		for (ProviderResultVO vo : filteredResultsAL) {
			if (templateCriteria != null) {
				if (vo.getPercentageMatch() != null && templateCriteria.getSpnPercentageMatch() != null
						&& vo.getPercentageMatch().doubleValue() >= templateCriteria.getSpnPercentageMatch().doubleValue()) {
					routedResourceIds.add(Integer.valueOf(vo.getResourceId()));
				}
			} 
			else {
				routedResourceIds.add(Integer.valueOf(vo.getResourceId()));
			}
		}

	}

	/**
	 * @param provSearchVO
	 * @param serviceOrder
	 * @param buyerId
	 * @param template
	 */
	private void generateProviderSearchCriteria(
			ProviderSearchCriteriaVO provSearchVO, ServiceOrder serviceOrder,
			int buyerId) {
		provSearchVO.setBuyerID(buyerId);
		provSearchVO.setServiceLocation(serviceOrder.getServiceLocation());
		provSearchVO.setServiceOrderID(serviceOrder.getSoId());
		if (serviceOrder.getPrimarySkillCatId() != null) {
			ArrayList<Integer> skillNodeIds = new ArrayList<Integer>();
			List<Integer> skillTypes = new ArrayList<Integer>();
			if (null != serviceOrder.getTasks() && serviceOrder.getTasks().size() > 0) {
				// If we have tasks then assign the SubCategory/Category
				// to the list else select the main category
				for (ServiceOrderTask taskDto : serviceOrder.getTasks()) {
					if (taskDto.getSubCategoryName() != null) {
						skillNodeIds.add(taskDto.getSkillNodeId());
					} 
					else if (taskDto.getCategoryName() != null) {
						skillNodeIds.add(taskDto.getSkillNodeId());
					} 
					else {
						skillNodeIds.add(serviceOrder.getPrimarySkillCatId());
					}
					if (taskDto.getServiceTypeId() != null) {
						skillTypes.add(taskDto.getServiceTypeId());
					}
				}
				provSearchVO.setSkillNodeIds(skillNodeIds);
				provSearchVO.setSkillServiceTypeId(skillTypes);
			} 
			else {
				skillNodeIds.add(serviceOrder.getPrimarySkillCatId());
				provSearchVO.setSkillNodeIds(skillNodeIds);
			}
		}


	}

	/**
	 * @param ratingParamBeans
	 * @param languages
	 */
	//to add Language beans to rating parameter beans
	
	private void addLanguageBean(
			ArrayList<RatingParameterBean> ratingParamBeans,
			List<String> languages) {
		LanguageParameterBean langBean = new LanguageParameterBean();
		List<Integer> languageIds = new ArrayList<Integer>();
		// Calling BO method for getting list of language ids from language names
		languageIds = serviceOrderBO.getLanguageIds(languages);
		langBean.setSelectedLangs(languageIds);
		ratingParamBeans.add(langBean);

	}

	/**
	 * @param ratingParamBeans
	 * @param minRating
	 */
	//to add StarRating beans to rating parameter beans
	
	private void addStarRatingBean(ArrayList<RatingParameterBean> ratingParamBeans,
			String minRating) {

		StarParameterBean starBean = new StarParameterBean();
		starBean.setNumberOfStars(Double.parseDouble(minRating));
		ratingParamBeans.add(starBean);
	}

	/**
	 * @param ratingParamBeans
	 * @param distance
	 * @param serviceOrder
	 */
	//to add zip beans to rating parameter beans
	
	public void addZipBean(ArrayList<RatingParameterBean> ratingParamBeans, String distance, ServiceOrder serviceOrder){
		ZipParameterBean zipBean = new ZipParameterBean();
		zipBean.setRadius(Integer.parseInt(distance));
		zipBean.setZipcode(serviceOrder.getServiceLocation().getZip());
		zipBean.setCredentialId(serviceOrder.getPrimarySkillCatId());
		ratingParamBeans.add(zipBean);
	}

	/**
	 * @param collection
	 * @return
	 */
	private boolean areThereRoutedProviders(SOElementCollection collection) {
		return !collection.getElements().isEmpty();
	}

	/**
	 * @param message
	 * @param code
	 * @return
	 */
	private SOPostResponse createErrorResponse(String message, String code){
		SOPostResponse postResponse = new SOPostResponse();
		Results results = Results.getError(message, code);
		postResponse.setResults(results);
		return postResponse;
	}

	/**
	 * Legacy code to be eventually supplanted by executeOrderFulfillmentService().
	 */
	@Override
	public IAPIResponse executeLegacyService(APIRequestVO apiVO) {
		logger.info("Entering SOPostService.execute()");
		SOPostResponse soPostResponse = new SOPostResponse();
		ProcessResponse postResponse = new ProcessResponse();

		List<String> arrMessage = new ArrayList<String>();
		List<Integer> routedResourceIds = new ArrayList<Integer>();
		Results results = null;
		SecurityContext securityContext = null;

		SOPostRequest postRequest = (SOPostRequest) apiVO
				.getRequestFromPostPut();
		int buyerId = apiVO.getBuyerIdInteger();
		String soId = apiVO.getSOId();

		try {
			securityContext = getSecurityContextForBuyerAdmin(buyerId);
		} catch (NumberFormatException nme) {
			logger.error("SOPostService.execute(): Number Format Exception "
					+ "occurred for resourceId: " + buyerId, nme);
		} catch (Exception exception) {
			logger.error("SOPostService.execute():  Exception occurred while "
					+ "accessing security context with resourceId: " + buyerId, exception);
		}

		int buyer_Id = securityContext.getCompanyId();
		ServiceOrder serviceOrder = null;
		try {
			serviceOrder = serviceOrderBO.getServiceOrder(soId);
			postMapper.mapServiceContactPhone(serviceOrder);
			boolean isBulletinBoardOrder = Constants.PriceModel.BULLETIN.equals(serviceOrder.getPriceModel());
			if (serviceOrder.getWfStateId() == OrderConstants.ROUTED_STATUS
					|| serviceOrder.getWfStateId() == OrderConstants.DRAFT_STATUS
					|| serviceOrder.getWfStateId() == OrderConstants.EXPIRED_STATUS) {
				// Validating the service order
				postResponse = validateServiceOrder(serviceOrder,buyer_Id);
				if (postResponse.getCode().equalsIgnoreCase(PublicAPIConstant.ONE)) {
					// bulletin board orders
					if (isBulletinBoardOrder) {
						// do nothing
					}
					// Setting the list of routed providers when it is specified in the input xml
					else if (postRequest.getProviderRouteInfo().getSpecificProviders() != null
							&& postRequest.getProviderRouteInfo().getSpecificProviders().getResourceId().size() > 0) {
						routedResourceIds = postRequest.getProviderRouteInfo().getSpecificProviders().getResourceId();
					}
					// others
					else {
						// Getting the list of routed providers based on the filter criteria
						ArrayList<RatingParameterBean> ratingParamBeans = new ArrayList<RatingParameterBean>();

						// Getting filter condition for zip
						if (postRequest.getProviderRouteInfo().getMaxDistance() != null) {
							ZipParameterBean zipBean = new ZipParameterBean();
							zipBean.setRadius(Integer.parseInt(postRequest.getProviderRouteInfo().getMaxDistance()));
							zipBean.setZipcode(serviceOrder.getServiceLocation().getZip());
							zipBean.setCredentialId(serviceOrder.getPrimarySkillCatId());
							ratingParamBeans.add(zipBean);
						}

						// Getting filter condition for minimum rating
						if (postRequest.getProviderRouteInfo().getMinRating() != null
								&& Double.parseDouble(postRequest.getProviderRouteInfo().getMinRating()) >= 0) {
							StarParameterBean starBean = new StarParameterBean();
							starBean.setNumberOfStars(Double.parseDouble(postRequest.getProviderRouteInfo().getMinRating()));
							ratingParamBeans.add(starBean);
						}
						// Getting filter condition for language
						if (postRequest.getProviderRouteInfo().getLanguage() != null) {
							LanguageParameterBean langBean = new LanguageParameterBean();
							List<Integer> languageIds = new ArrayList<Integer>();
							// Calling BO method for getting list of language ids from language names
							languageIds = serviceOrderBO.getLanguageIds(postRequest.getProviderRouteInfo().getLanguage().getLanguages());
							langBean.setSelectedLangs(languageIds);
							ratingParamBeans.add(langBean);
						}

						ProviderSearchCriteriaVO provSearchVO = new ProviderSearchCriteriaVO();
						provSearchVO.setBuyerID(buyer_Id);
						provSearchVO.setServiceLocation(serviceOrder.getServiceLocation());
						provSearchVO.setServiceOrderID(serviceOrder.getSoId());
						if (serviceOrder.getPrimarySkillCatId() != null) {
							ArrayList<Integer> skillNodeIds = new ArrayList<Integer>();
							List<Integer> skillTypes = new ArrayList<Integer>();
							if (null != serviceOrder.getTasks() && serviceOrder.getTasks().size() > 0) {
								// If we have tasks then assign the SubCategory/Category
								// to the list else select the main category
								for (ServiceOrderTask taskDto : serviceOrder.getTasks()) {
									if (taskDto.getSubCategoryName() != null) {
										skillNodeIds.add(taskDto.getSkillNodeId());
									} else if (taskDto.getCategoryName() != null) {
										skillNodeIds.add(taskDto.getSkillNodeId());
									} else {
										skillNodeIds.add(serviceOrder.getPrimarySkillCatId());
									}
									if (taskDto.getServiceTypeId() != null) {
										skillTypes.add(taskDto.getServiceTypeId());
									}
								}
								provSearchVO.setSkillNodeIds(skillNodeIds);
								provSearchVO.setSkillServiceTypeId(skillTypes);
							} else {
								skillNodeIds.add(serviceOrder.getPrimarySkillCatId());
								provSearchVO.setSkillNodeIds(skillNodeIds);
							}
						}

						// Add SPN id to provider search filters; if available in the buyer template
						BuyerSOTemplateDTO template = templateBO.loadBuyerSOTemplate(buyer_Id, 
								postRequest.getProviderRouteInfo().getTemplate());
						if (template != null && template.getSpnId() != null) {
							provSearchVO.setSpnID(template.getSpnId());
						}

						// Do initial provider search
						ArrayList<ProviderResultVO> resultsAL = provSearchObj.getProviderList(provSearchVO);

						// Apply filters and get refined results
						List<ProviderResultVO> filteredResultsAL = masterCalcObj.getFilteredProviderList(ratingParamBeans, resultsAL);

						// Further filtering on percentage matching providers
						for (ProviderResultVO vo : filteredResultsAL) {
							if (template != null) {
								if (vo.getPercentageMatch() != null && template.getSpnPercentageMatch() != null
										&& vo.getPercentageMatch().doubleValue() >= template.getSpnPercentageMatch().doubleValue()) {
									routedResourceIds.add(Integer.valueOf(vo.getResourceId()));
								}
							} else {
								routedResourceIds.add(Integer.valueOf(vo.getResourceId()));
							}
						}

					}
					if ((routedResourceIds == null || routedResourceIds.size() == 0) && !isBulletinBoardOrder) {
						logger.info("No Providers selected");
						arrMessage.add(CommonUtility.getMessage(PublicAPIConstant.NO_PROVIDERS_ERROR_CODE));
						postResponse.setMessages(arrMessage);
						postResponse.setCode(ServiceConstants.USER_ERROR_RC);

					} else {
						logger.info("Posting Service Order to " + routedResourceIds.size() + " providers");

						// TODO: Pushkar - The following tierID value needs to be set to
						// an appropriate value after checking with the CAR team.

						List<RoutedProvider> routedResources = new ArrayList<RoutedProvider>();
						RoutedProvider routedProvider = null;
						Integer tierId = null;
						Iterator<Integer> routedProviderItr = routedResourceIds.iterator();
						while (routedProviderItr.hasNext()) {
							routedProvider = new RoutedProvider();

							routedProvider.setResourceId((Integer) routedProviderItr.next());
							routedProvider.setSoId(serviceOrder.getSoId());
							routedProvider.setSpnId(serviceOrder.getSpnId());
							routedProvider.setTierId(tierId);
							routedProvider.setPriceModel(serviceOrder.getPriceModel());
							routedResources.add(routedProvider);

						}
						serviceOrder.setRoutedResources(routedResources);
						serviceOrder.getBuyer().setRoleId(securityContext.getRoleId());
						postResponse = serviceOrderBO.processUpdateDraftSO(serviceOrder, securityContext);
						if (serviceOrder.getWfStateId() == OrderConstants.ROUTED_STATUS) {
							postResponse = serviceOrderBO.processReRouteSO(buyer_Id, soId, false, securityContext);

						} else if (serviceOrder.getWfStateId() == OrderConstants.DRAFT_STATUS) {
							postResponse = serviceOrderBO.processRouteSO(buyer_Id, soId, securityContext);
						}
						if (postResponse.getCode().equalsIgnoreCase(ServiceConstants.VALID_RC)) {
							arrMessage.add(PublicAPIConstant.POST_RESULT1 + routedResourceIds.size() + PublicAPIConstant.POST_RESULT2);
							postResponse.setMessages(arrMessage);
						}

					}
				} else {
					postResponse.setCode(ServiceConstants.USER_ERROR_RC);
				}
				ServiceOrder soFinal = serviceOrderBO.getServiceOrder(soId);
				soPostResponse = postMapper.mapServiceOrder(postResponse, soFinal);
			} else {
				logger.info("SOPostService.execute(): Service Order can't be posted: " + soId);
				results = Results.getError(ResultsCode.POST_INVALID_STATE.getMessage(), ResultsCode.POST_INVALID_STATE.getCode());
				soPostResponse.setResults(results);
			}

		} catch (BusinessServiceException bse) {
			logger.error("SOPostService.execute(): Exception Occurred" + " while getting service order ", bse);
			results = Results.getError(bse.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			soPostResponse.setResults(results);
		} catch (DataException de) {
			logger.error("SOPostService.execute(): Data Exception " + "Occurred while mapping request: ", de);
			results = Results.getError(de.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			soPostResponse.setResults(results);
		} catch (Exception e) {
			logger.error("SOPostService.execute(): Exception " + "Occurred: ", e);
			results = Results.getError(e.getMessage(), ResultsCode.INTERNAL_SERVER_ERROR.getCode());
			soPostResponse.setResults(results);
		}			

		logger.info("Leaving  SOPostService.execute()");
		return soPostResponse;
	}

	/**
	 * This method validates the service order.Checks if the service dates and
	 * the labor price are valid
	 * @param buyer_Id 
	 * 
	 * @param ServiceOrder
	 *            serviceOrder
	 * @return ProcessResponse
	 */
	public ProcessResponse validateServiceOrder(ServiceOrder serviceOrder, int buyer_Id) {

		ProcessResponse response = new ProcessResponse();
		response.setCode(PublicAPIConstant.ONE);
		List<String> message = new ArrayList<String>();
		logger.info("Checking if dates are valid");
		boolean isCurrentDate = DateValidationUtils
				.fromDateGreaterCurrentDate(serviceOrder.getServiceDate1()
						.toString());
		boolean isPastDateTime = false;
		if(buyer_Id == PublicAPIConstant.TECHTALK_SERVICES_BUYER_ID){
			isPastDateTime = TimeUtils.isPastCurrentUTCTime(serviceOrder
                    .getServiceDateGMT1(), serviceOrder.getServiceTimeStartGMT());
		}else{
			isPastDateTime = TimeUtils.isPastCurrentTime(serviceOrder
					.getServiceDate1(), serviceOrder.getServiceTimeStart());
			
		}
		if (isCurrentDate && isPastDateTime) {
			message.add(CommonUtility
					.getMessage(PublicAPIConstant.FROM_DATETIME_ERROR_CODE));
			response.setMessages(message);
			response.setCode(ServiceConstants.USER_ERROR_RC);
		} else if (!isCurrentDate) {
			message.add(CommonUtility
					.getMessage(PublicAPIConstant.FROM_DATE_ERROR_CODE));
			response.setMessages(message);
			response.setCode(ServiceConstants.USER_ERROR_RC);
		}

		if (serviceOrder.getServiceDateTypeId() != null
				&& serviceOrder.getServiceDateTypeId() != 1) {
			if (!DateValidationUtils.fromDateLesserToDate(serviceOrder
					.getServiceDate1().toString(), serviceOrder
					.getServiceDate2().toString())) {

				message.add(CommonUtility
						.getMessage(PublicAPIConstant.FROM_TODATE_ERROR_CODE));
				response.setMessages(message);
				response.setCode(ServiceConstants.USER_ERROR_RC);

			} else if (serviceOrder
					.getServiceDate1()
					.toString()
					.equalsIgnoreCase(serviceOrder.getServiceDate2().toString())) {
				if (!DateValidationUtils.timeValidation(serviceOrder
						.getServiceTimeStart(), serviceOrder
						.getServiceTimeEnd())) {
					logger.info("End time less than start time");
					message.add(CommonUtility
							.getMessage(PublicAPIConstant.TIME_ERROR_CODE));
					response.setMessages(message);
					response.setCode(ServiceConstants.USER_ERROR_RC);
				}

			}
		}

		return response;
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public IProviderSearchBO getProvSearchObj() {
		return provSearchObj;
	}

	public void setProvSearchObj(IProviderSearchBO provSearchObj) {
		this.provSearchObj = provSearchObj;
	}

	public IMasterCalculatorBO getMasterCalcObj() {
		return masterCalcObj;
	}

	public void setMasterCalcObj(IMasterCalculatorBO masterCalcObj) {
		this.masterCalcObj = masterCalcObj;
	}

	public IBuyerSOTemplateBO getTemplateBO() {
		return templateBO;
	}

	public void setTemplateBO(IBuyerSOTemplateBO templateBO) {
		this.templateBO = templateBO;
	}

	public void setPostMapper(SOPostMapper postMapper) {
		this.postMapper = postMapper;
	}

	public void setOfPostMapper(OFPostMapper ofPostMapper) {
		this.ofPostMapper = ofPostMapper;
	}
}


