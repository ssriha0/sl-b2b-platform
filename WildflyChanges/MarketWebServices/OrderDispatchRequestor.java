package com.newco.marketplace.webservices.dispatcher.so.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.BackgroundCheckParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.CredentialParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.LanguageParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.StarParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.ZipParameterBean;
import com.newco.marketplace.business.businessImpl.so.order.ServiceOrderBO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.business.iBusiness.financeManager.IFinanceManagerBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IRouteOrderGroupBO;
import com.newco.marketplace.business.iBusiness.providersearch.IMasterCalculatorBO;
import com.newco.marketplace.business.iBusiness.providersearch.IProviderSearchBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IBuyerSOTemplateBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IIncidentBO;
import com.newco.marketplace.business.iBusiness.serviceorder.ISOEventBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderUpsellBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.incident.IncidentTrackingVO;
import com.newco.marketplace.dto.vo.ledger.Account;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;
import com.newco.marketplace.dto.vo.serviceorder.ABaseRequestDispatcher;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.exception.DataException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.util.so.ServiceOrderUtil;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.utils.SimpleCache;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.webservices.base.response.ABaseResponse;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.newco.marketplace.webservices.dto.serviceorder.ABaseWebserviceRequest;
import com.newco.marketplace.webservices.dto.serviceorder.AddDocumentsRequest;
import com.newco.marketplace.webservices.dto.serviceorder.AddDocumentsResponse;
import com.newco.marketplace.webservices.dto.serviceorder.CancelSORequest;
import com.newco.marketplace.webservices.dto.serviceorder.CancelSOResponse;
import com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.ClientServiceOrderNoteResponse;
import com.newco.marketplace.webservices.dto.serviceorder.CloseServiceOrderRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftResponse;
import com.newco.marketplace.webservices.dto.serviceorder.CreatePartRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreatePartsRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateTaskReponse;
import com.newco.marketplace.webservices.dto.serviceorder.CreateTaskRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CustomRef;
import com.newco.marketplace.webservices.dto.serviceorder.Document;
import com.newco.marketplace.webservices.dto.serviceorder.EventRequest;
import com.newco.marketplace.webservices.dto.serviceorder.GetSOStatusRequest;
import com.newco.marketplace.webservices.dto.serviceorder.GetSOStatusResponse;
import com.newco.marketplace.webservices.dto.serviceorder.GetServiceOrderResponse;
import com.newco.marketplace.webservices.dto.serviceorder.NoteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.OrderStatus;
import com.newco.marketplace.webservices.dto.serviceorder.RescheduleSORequest;
import com.newco.marketplace.webservices.dto.serviceorder.RescheduleSOResponse;
import com.newco.marketplace.webservices.dto.serviceorder.RouteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.RouteResponse;
import com.newco.marketplace.webservices.dto.serviceorder.ServiceOrderResponse;
import com.newco.marketplace.webservices.dto.serviceorder.UpdateIncidentTrackingRequest;
import com.newco.marketplace.webservices.dto.serviceorder.UpdateIncidentTrackingResponse;
import com.newco.marketplace.webservices.dto.serviceorder.UpdatePartResponse;
import com.newco.marketplace.webservices.dto.serviceorder.UpdatePartsResponse;
import com.newco.marketplace.webservices.response.WSErrorInfo;
import com.newco.marketplace.webservices.response.WSProcessResponse;
import com.newco.marketplace.webservices.sei.serviceorder.ServiceOrderSEI;
import com.newco.marketplace.webservices.util.StringToWSBeanConverter;
import com.newco.marketplace.webservices.util.WSConstants;
import com.newco.marketplace.webservices.validation.so.draft.CreateDraftValidator;
import com.newco.marketplace.webservices.validation.so.part.PartValidator;
import com.servicelive.routing.tiered.services.TierRouteController;
import com.servicelive.routingrulesengine.services.OrderProcessingService;

/**
 * Acts as a controller for the webservice operations
 */
public class OrderDispatchRequestor extends ABaseRequestDispatcher implements ServiceOrderSEI {

	private static final long serialVersionUID = -20090826L;
	private static final Logger logger = Logger.getLogger(OrderDispatchRequestor.class.getName());

	/**
	 * IVR is not capable of passing user/passwords, this acts as a default
	 */
	private static final String IVR_USERNAME = "ivr";

	/**
	 * IVR is not capable of passing user/passwords, this acts as a default
	 */
	private static final String IVR_PASSWORD = "ivr";

	/**
	 * Clear any empty error or warnings so they do not appear in the response
	 *
	 * @param response
	 * @return
	 */
	private WSProcessResponse processResponse(WSProcessResponse response) {
		if (response.getErrorList().isEmpty()) {
			response.setErrorList(null);
		}
		if (response.getWarningList().isEmpty()) {
			response.setWarningList(null);
		}
		return response;
	}

	/**
	 * A helper method to ensure all authentication errors are returned with the
	 * same code
	 *
	 * @param serviceResponse
	 * @return
	 */
	private WSProcessResponse processSecurityResponseError(WSProcessResponse serviceResponse) {

		// Create error object
		WSErrorInfo error = new WSErrorInfo();
		error.setCode(WSConstants.WSErrors.Codes.WS_AUTH_UID_PWD);
		error.setMessage(WSConstants.WSErrors.Messages.WS_AUTH_UID_PWD);

		// Add error object to errors list in the response
		List<WSErrorInfo> errorsList = serviceResponse.getErrorList();
		if (errorsList == null) {
			errorsList = new ArrayList<WSErrorInfo>();
			serviceResponse.setErrorList(errorsList);
		}
		errorsList.add(error);

		serviceResponse.setSLServiceOrderId(WSConstants.FAILED_SERVICE_ORDER_NO);
		serviceResponse.setProcessStatus(WSConstants.WSProcessStatus.FAILURE);

		return serviceResponse;
	}

	/**
	 * This method hashes the password and calls the authenticate method.
	 *
	 * @param ABaseServiceRequestVO
	 */
	protected ABaseResponse doWSServiceAccessAuthenication(ABaseWebserviceRequest request) {

		String encPassword = "";

		if (request.getPasswordFlag().equals("internal")) {
			encPassword = request.getPassword();
		} else {
			// set up the password with encryption
			encPassword = CryptoUtil.generateHash(request.getPassword());
		}
		boolean canAccessService = true;
		try {
			LoginCredentialVO login = createLoginCredential(request.getUserId(), encPassword);
			canAccessService = getAccessSecurity().authenticate(login);
		} catch (Exception e) {
			return getFailureResponse();
		}

		if (canAccessService) {
			return getPassResponse();
		}
		return getFailureResponse();
	}

	private WSProcessResponse processBusinessError(WSProcessResponse response, Exception ex, String errCode, String errMsg, boolean warningOnly, String processStatus) {
		if (ex != null) {
			errCode = WSConstants.WSErrors.Codes.EX_GENERAL;
			errMsg = ex.getMessage();
		}
		WSErrorInfo error = new WSErrorInfo();
		error.setCode(errCode);
		error.setMessage(errMsg);
		response.setProcessStatus(processStatus);
		if (warningOnly) {
			response.getWarningList().add(error);
		} else {
			response.getErrorList().add(error);
		}
		return response;
	}

	private void processBusinessResponse(String slSOId, WSProcessResponse response, ProcessResponse busProcessResponse) {
		if (busProcessResponse != null && busProcessResponse.isSuccess()) {
			response.setSLServiceOrderId(slSOId);
			response.setProcessStatus(WSConstants.WSProcessStatus.SUCCESS);
		} else {
			response.setProcessStatus(WSConstants.WSProcessStatus.FAILURE);
			if(busProcessResponse != null) {
				for (String message : busProcessResponse.getMessages()) {
					WSErrorInfo error = new WSErrorInfo();
					error.setCode(busProcessResponse.getCode());
					error.setMessage(message);
					response.getErrorList().add(error);
				}
			}
		}
	}

	/**
	 * WS call to close a service order
	 */
	public ServiceOrderResponse closeServiceOrder(CloseServiceOrderRequest request) {

		ServiceOrderResponse serviceResponse = new ServiceOrderResponse();
		serviceResponse.setProcessCode(WSConstants.WSProcessCodes.CLOSE_SO);

		// WebService Authentication
		ABaseResponse securityResponse = doWSServiceAccessAuthenication(request);
		if (securityResponse.isError()) {
			return (ServiceOrderResponse) processSecurityResponseError(serviceResponse);
		}

		// Call business method
		ProcessResponse busProcessResponse = null;
		IServiceOrderBO businessObject = (IServiceOrderBO) this.getBusinessBeanFacility(SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE);
		SecurityContext securityContext = createSecurityContext(request.getUserId(), request.getPassword());
		if (request.getBuyerId() != null) {
			ServiceOrderUtil.enrichSecurityContext(securityContext, request.getBuyerId());
		}
		try {
			busProcessResponse = businessObject.processCloseSO(request.getBuyerId(), request.getServiceOrderID(), request.getFinalPartsPrice(), request.getFinalLaborPrice(), securityContext);
		} catch (BusinessServiceException bsEx) {
			logger.error("Error in closeServiceOrder()", bsEx);
			return (ServiceOrderResponse) processBusinessError(serviceResponse, bsEx, null, null, false, WSConstants.WSProcessStatus.FAILURE);
		}

		processBusinessResponse(request.getServiceOrderID(), serviceResponse, busProcessResponse);

		logger.info("Exiting OrderDispatchRequestor.closeServiceOrder().");
		return (ServiceOrderResponse) processResponse(serviceResponse);

	}

	/**
	 * Create Draft Implementation
	 *
	 * @param request
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public CreateDraftResponse createDraft(CreateDraftRequest request) {
		logger.info("Starting CreateDraftResponse");

		CreateDraftResponse serviceResponse = new CreateDraftResponse();
		serviceResponse.setProcessCode(WSConstants.WSProcessCodes.CREATE_DRAFT);

		// WebService Authentication
		ABaseResponse securityResponse = doWSServiceAccessAuthenication(request);
		if (securityResponse.isError()) {
			return (CreateDraftResponse) processSecurityResponseError(serviceResponse);
		}

		// Missing Template
		//IBuyerSOTemplateBO templateBO = (IBuyerSOTemplateBO) getBusinessBeanFacility(BUYER_TEMPLATE_BO);
		//BuyerSOTemplateDTO template = templateBO.loadBuyerSOTemplate(request
		//		.getBuyerId(), request.getTemplateName());

		BuyerSOTemplateDTO template = getBuyerSoTemplate(request.getBuyerId(), request.getTemplateName());
		if (template != null) {
			request = SOTemplateWSMapper.mapTemplateToCreateDraftRequest(request, template);
		} else {
			logger.error(WSConstants.WSErrors.Messages.CR_DR_MISSING_TEMPLATE);
			processBusinessError(serviceResponse, null, WSConstants.WSErrors.Codes.CR_DR_MISSING_TEMPLATE, WSConstants.WSErrors.Messages.CR_DR_MISSING_TEMPLATE, true, null);
			serviceResponse.setAutoRoute(Boolean.FALSE);
		}

		// Adapt request maps request object values to VO's needed for the so to
		// save
		// If the request can not be mapped it can not be used - return error
		ServiceOrder so = null;
		List<ServiceOrderAddonVO> soAddons = null;
		boolean adaptedOrder = false;
		try {
			so = OrderDispatcherHelper.adaptRequest(request);
			soAddons = OrderDispatcherHelper.adaptAddonRequest(request);
			adaptedOrder = true;
		} catch (DataException dataEx) {
			logger.error("OrderDispatchRequestor.createDraft() while adapting request", dataEx);
			processBusinessError(serviceResponse, null, WSConstants.WSErrors.Codes.CR_DR_InvalidDate_Validation, WSConstants.WSErrors.Messages.CR_DR_InvalidFormatDate_Validation, false, WSConstants.WSProcessStatus.FAILURE);
			return serviceResponse;
		}

		// Set Buyer Resource Id and Buyer Contact Id in the ServiceOrder from
		// SecurityContext
		SecurityContext securityContext = createSecurityContext(request.getUserId(), request.getPassword());
		if (securityContext.getCompanyId() != null) {
			ServiceOrderUtil.enrichSecurityContext(securityContext, securityContext.getCompanyId());
		}

		// Enabled caching -Shekhar
		//IFinanceManagerBO financeManagerBO = (IFinanceManagerBO) MPSpringLoaderPlugIn.getCtx().getBean("financeManagerBO");
		//Account acct = financeManagerBO.getAccountDetails(securityContext.getCompanyId());

		Account acct = getAccountDetails(securityContext.getCompanyId());
		securityContext.setAccountID(acct.getAccount_id());

		if (template != null) {
			/* save spnId only if it is newSpn, hence spnId in so_hdr relates only to spnId of spnet_hdr */
			if(template.getIsNewSpn()!= null && template.getIsNewSpn().booleanValue()){
				so.setSpnId(template.getSpnId());
			}
			so.setBuyerContactId(template.getAltBuyerContactId());
			if (template.getAltBuyerContact() != null && template.getAltBuyerContact().getResourceId() != null) {
				so.setBuyerResourceId(template.getAltBuyerContact().getResourceId());
				if (so.getBuyerResourceId() == null) {
					logger.error("Buyer Resource ID is being set to null from the altBuyerContact. buyerId=" + so.getBuyerId());
				}
			} else {
				so.setBuyerResourceId(securityContext.getVendBuyerResId());
				if (so.getBuyerResourceId() == null) {
					logger.error("Buyer Resource ID is being set to null from the securityContext vendBuyerResId. template is not null. buyerId=" + so.getBuyerId());
				}
			}
		} else {
			so.setBuyerContactId(securityContext.getRoles().getContactId());
			so.setBuyerResourceId(securityContext.getVendBuyerResId());
			if (so.getBuyerResourceId() == null) {
				logger.error("Buyer Resource ID is being set to null from the securityContext vendBuyerResId. template is null. buyerId=" + so.getBuyerId());
			}
		}

		// Now since we have buyerResourceId available in "so" object; we could translate notes requests too
		List<NoteRequest> notes = request.getNotes();
		OrderDispatcherHelper.adaptRequestNotes(so, notes);

		// Validate primary skill cat - it has to be valid
		boolean goodPrimaryCategory = false;
		IServiceOrderBO orderBusinessBean = (IServiceOrderBO) getBusinessBeanFacility(SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE);
		IServiceOrderUpsellBO orderAddonBusinessBean = (IServiceOrderUpsellBO) getBusinessBeanFacility(SERVICE_ORDER_UPSELL_BUSINESS_OBJECT_REFERENCE);
		try {
			if (so.getPrimarySkillCatId() != null) {
				//goodPrimaryCategory = orderBusinessBean.isValidMainCategory(so
				//		.getPrimarySkillCatId());
				goodPrimaryCategory = isPrimaryCategoryGood(so.getPrimarySkillCatId());
			}
			if (!goodPrimaryCategory) {
				throw new Exception(WSConstants.WSErrors.Messages.INVALID_MAINSERVICECATEGORYID);
			}
		} catch (Exception ex) {
			logger.error("OrderDispatchRequestor.createDraft() while validating main category", ex);
			processBusinessError(serviceResponse, null, WSConstants.WSErrors.Codes.INVALID_MAINSERVICECATEGORYID, WSConstants.WSErrors.Messages.INVALID_MAINSERVICECATEGORYID, false, WSConstants.WSProcessStatus.FAILURE);
			return serviceResponse;
		}

		ProcessResponse busProcessResponse = null;
		ServiceOrder savedOrder = null;
		String externalServiceOrderID = null;
		if (adaptedOrder) {
			// init the custom ref list
			so.setCustomRefs(new ArrayList<ServiceOrderCustomRefVO>());
			try {
				externalServiceOrderID = addCustomReferences(request, so, externalServiceOrderID);
			} catch (Exception ex) {
				logger.error("OrderDispatchRequestor.createDraft() while identifying existing SO based on custom references", ex);
				processBusinessError(serviceResponse, null, WSConstants.WSErrors.Codes.CR_DR_INVALID_CUSTOMREF, WSConstants.WSErrors.Messages.CR_DR_INVALID_CUSTOMREF, false, WSConstants.WSProcessStatus.FAILURE);
				return serviceResponse;
			}

			// set buyer logo via template value
			if (template != null && template.getDocumentLogo() != null) {
				DocumentVO documentVO = null;
				try {
					documentVO = getDocumentVo(request.getBuyerId(), template.getDocumentLogo());
				} catch (BusinessServiceException bsEx) {
					logger.error("OrderDispatchRequestor.createDraft() while retrieving documents", bsEx);
					processBusinessError(serviceResponse, bsEx, null, null, false, null);
				}
				if (documentVO != null && documentVO.getDocumentId() != null) {
					so.setLogoDocumentId(documentVO.getDocumentId());
				}
			}

			CreateDraftValidator createDraftValidator = new CreateDraftValidator();
			Map<String, List<WSErrorInfo>> map = createDraftValidator.validateCreateDraft(so);
			serviceResponse.getErrorList().addAll(map.get(OrderConstants.SOW_TAB_ERROR));
			serviceResponse.getWarningList().addAll(map.get(OrderConstants.SOW_TAB_WARNING));

			// see if the order exists by custom ref and pull
			try {
				savedOrder = orderBusinessBean.getByCustomReferenceValue(externalServiceOrderID, request.getBuyerId());
				Integer subStatus = new Integer(0);

				// check if buyer has conditional auto route
				boolean isConditionalRouteFeatureBuyer = validateFeature(request.getBuyerId(), OrderConstants.BUYER_FEATURE_CONDITIONAL_ROUTE);
				OrderProcessingService orderProcessingService = (OrderProcessingService) this.getBusinessBeanFacility("orderProcessingService");

				boolean attemptSave = true;
				if (serviceResponse.getErrorList() != null && serviceResponse.getErrorList().size() > 0) {
					logger.error("Errors are present in so - not attempting save");
					attemptSave = false;
				} else if ((serviceResponse.getWarningList() != null && serviceResponse.getWarningList().size() > 0) || (request.getAutoRoute() != null && !request.getAutoRoute().booleanValue())) {
					subStatus = new Integer(OrderConstants.MISSING_INFORMATION_SUBSTATUS);
				}

				if (null == savedOrder && attemptSave) {
					String clientStatus = request.getClientStatus();
					if (StringUtils.equals(clientStatus, OrderConstants.ClientStatus.CLOSED)) {
						logger.warn("Update on a CLOSED order - request is not processed. ServiceUnitNumber|ServiceOrderNumber = " + externalServiceOrderID);
					} else {
						Integer conditionalId = null;
						if (isConditionalRouteFeatureBuyer) {
							String mainJobCode = OrderDispatcherHelper.retrieveMainJobCode(request);

							try {
								conditionalId = orderProcessingService.checkConditionalRoutingQualification(so, mainJobCode);
							} catch (Exception e) {
//								TODO need to figure out what to do.
							}

							//re-price CAR eligible order and generate CreateTaskResponses on serviceResponse
							if (conditionalId != null) {
								repriceSoBasedOnConditionalPrices(so, request, serviceResponse, conditionalId);
							}
						}
						busProcessResponse = orderBusinessBean.processCreateDraftSO(so, subStatus, securityContext);
						if (busProcessResponse.isSuccess()) {
							// if soAddons exist for a service order add them to
							// the so_addon table.
							if (soAddons != null && soAddons.size() > 0) {
								for (ServiceOrderAddonVO addon : soAddons) {
									addon.setSoId(so.getSoId());
								}
								ProcessResponse addonProcessResponse = orderAddonBusinessBean.processCreateAddons(soAddons);
								if (addonProcessResponse.isError()) {
									StringBuilder errMsgs = new StringBuilder("Error while saving addons!\n");
									for (String msg : addonProcessResponse.getMessages()) {
										errMsgs.append(msg).append("\n");
									}
									BusinessServiceException busEx = new BusinessServiceException(errMsgs.toString());
									logger.error(busEx.getMessage(), busEx);
								}
							}

							// SO_ID is available, update so_id on rule relationship.
							if (isConditionalRouteFeatureBuyer && conditionalId != null) {
								try {
									orderProcessingService.associateRuleIdToSo(conditionalId, so);
								} catch (Exception e) {
									// TODO Figure out what to do
								}
							}
						}

						TierRouteController trController = (TierRouteController) getBusinessBeanFacility(TRCONTROLLER_BUSINESS_OBJECT_REFERENCE);
						// group order
						// Do not go there for CAR orders
						boolean isOrderGrpFeatureBuyer = validateFeature(request.getBuyerId(), OrderConstants.BUYER_FEATURE_ORDER_GROUP);

						if (isOrderGrpFeatureBuyer) {
							if (conditionalId == null) {
								groupTheOrder(request, so, trController, securityContext);
							}
						} else if (validateFeature(request.getBuyerId(), BuyerFeatureConstants.AUTO_ROUTE)) {
							if (conditionalId == null) {
								boolean isTierRoutingEnabled = trController.isTierRoutingRequried(so.getSpnId(), request.getBuyerId());
								if (isTierRoutingEnabled) {
									trController.route(so.getSoId(), Boolean.FALSE, SPNConstants.TR_REASON_ID_START_TIER_ROUTING);
								} else {
									IRouteOrderGroupBO routeOrderGrpObj = (IRouteOrderGroupBO) this.getBusinessBeanFacility(ROUTE_ORDER_GROUP_BUSINESS_OBJECT);
									ServiceOrderSearchResultsVO serviceOrderVO = new ServiceOrderSearchResultsVO();
									serviceOrderVO.setSoId(so.getSoId());
									routeOrderGrpObj.routeIndividualOrder(serviceOrderVO, OrderConstants.NON_TIER);
								}
							}
						}
					}

					// this is SO update logic
				} else if (attemptSave) {
					//FIXME savedOrder could theoretically be null at this location
					savedOrder.setExternalSoId(externalServiceOrderID);
					if (isConditionalRouteFeatureBuyer) {
						// Check eligibility
						Integer conditionalId = null;
						try {
							conditionalId = orderProcessingService.findRuleIdForSO(savedOrder.getSoId());
						} catch (Exception e) {
							// TODO figure out what to do
						}

						//re-price CAR eligible order and generate CreateTaskResponses on serviceResponse
						if (conditionalId != null) {
							repriceSoBasedOnConditionalPrices(so, request, serviceResponse, conditionalId);
						}
					}
					busProcessResponse = orderBusinessBean.updateServiceOrder(savedOrder, so, securityContext, request.getClientStatus(), request.getTemplateName());
					if (busProcessResponse != null && busProcessResponse.isSuccess()) {
						// if soAddons exist for a service order add them to
						// the so_addon table.
						if (soAddons != null && soAddons.size() > 0) {
							for (ServiceOrderAddonVO addon : soAddons) {
								addon.setSoId(savedOrder.getSoId());
							}
							//ProcessResponse addonProcessResponse = orderAddonBusinessBean.processUpdateAddons(soAddons);
							ProcessResponse addonProcessResponse = orderAddonBusinessBean.processUpdateAddonsInBatch(savedOrder.getSoId(), soAddons);

							if (addonProcessResponse.isError()) {
								StringBuilder errMsgs = new StringBuilder("Error while saving addons!\n");
								for (String msg : addonProcessResponse.getMessages()) {
									errMsgs.append(msg).append("\n");
								}
								BusinessServiceException busEx = new BusinessServiceException(errMsgs.toString());
								logger.error(busEx.getMessage(), busEx);
							}
						}
					}
					if (busProcessResponse != null && busProcessResponse.isSuccess() && busProcessResponse.getObj() == null)
						busProcessResponse.setObj(savedOrder.getSoId());
				}

			} catch (BusinessServiceException coreBSEx) {
				return processException(serviceResponse, busProcessResponse, coreBSEx);
			}
		}

		if (busProcessResponse != null && busProcessResponse.isSuccess()) {
			String soId = (String) busProcessResponse.getObj();
			logger.info("~~~~~~~~~~~~ [ORDERSTRING][SO_ID] returned from web service = [" + externalServiceOrderID + "][" + soId + "]");
			serviceResponse.setSLServiceOrderId(soId);
			serviceResponse.setTemplateName(request.getTemplateName());
			serviceResponse.setAutoRoute(request.getAutoRoute());
			if (null == savedOrder) {
				serviceResponse.setOrderUpdate(false);
			} else {
				serviceResponse.setOrderUpdate(true);
			}
			// save the documents
			if (request.getDocuments() != null && request.getDocuments().size() > 0) {
				saveDocumentsToServiceOrder(request.getDocuments(), request.getBuyerId(), serviceResponse.getSLServiceOrderId(), securityContext, serviceResponse);
			}
			serviceResponse.setProcessStatus(WSConstants.WSProcessStatus.SUCCESS);
		} else if (busProcessResponse == null || busProcessResponse.isError()) {
			StringBuilder sbErrors = new StringBuilder("");
			for (WSErrorInfo error : serviceResponse.getErrorList()) {
				sbErrors.append(error.getMessage()).append(",");
			}
			logger.error(sbErrors.toString(), new Exception("Error while injecting the SO: " + externalServiceOrderID));
			serviceResponse.setSLServiceOrderId(WSConstants.FAILED_SERVICE_ORDER_NO);
			serviceResponse.setProcessStatus(WSConstants.WSProcessStatus.FAILURE);
		}

		CreateDraftResponse obj = (CreateDraftResponse) processResponse(serviceResponse);
		logger.info("Ending CreateDraftResponse");
		return obj;

	}

	private void repriceSoBasedOnConditionalPrices(ServiceOrder so, CreateDraftRequest request, CreateDraftResponse serviceResponse, Integer ruleId) {

		// get skus from request
		List<String> skus = OrderDispatcherHelper.getSkusAsList(request.getTasks());
		OrderProcessingService orderProcessingService = (OrderProcessingService) this.getBusinessBeanFacility("orderProcessingService");

		// send skus for reprice
		Map<String, BigDecimal> repricedItems = null;
		try {
			String specialty = OrderDispatcherHelper.getSpecialtyFromRequest(request).toString();
			repricedItems = orderProcessingService.applyConditionalPriceOverrides(skus, ruleId, specialty);
		} catch (Exception e) {
			// TODO Figure out what to do
			logger.error("Couldn't calculate which skus to override the price of because of the following error.", e);
		}

		if (repricedItems != null && repricedItems.size() > 0) {
			// re-calculate order total
			List<CreateTaskReponse> taskResp = repriceOrder(so, repricedItems, request);

			// set new sku prices on the response
			serviceResponse.setTasks(taskResp);

		}
	}

	private List<CreateTaskReponse> repriceOrder(ServiceOrder so, Map<String, BigDecimal> repricedItems, CreateDraftRequest request) {

		Double newSpendLimitLabor = calculateNewSpendLimit(request, repricedItems);

		so.setSpendLimitLabor(newSpendLimitLabor);

		List<CreateTaskReponse> taskResp = new ArrayList<CreateTaskReponse>(repricedItems.keySet().size());
		List<ServiceOrderTask> tasks = so.getTasks();
		for (String sku : repricedItems.keySet()) {
			CreateTaskReponse task = new CreateTaskReponse();
			BigDecimal price = repricedItems.get(sku);

			task.setJobCode(sku);
			task.setLaborPrice(Double.valueOf(price.doubleValue()));

			taskResp.add(task);
			for (ServiceOrderTask serviceOrderTask : tasks) {
				String taskName=serviceOrderTask.getTaskName();				
				int skuIndex = serviceOrderTask.getTaskName().indexOf(SurveyConstants.HYPHEN);				
				if(skuIndex  > 0){
					if(taskName.substring(0, skuIndex).equalsIgnoreCase(sku)){
						serviceOrderTask.setPrice(task.getLaborPrice());
					}
				}
			}
		}
		
		
		
		return taskResp;
	}

	/**
	 * This method is very sensitive as it is recalculates the price
	 * of the SO based on the rule overrides and saves it back on so
	 * as Spend Limit Labor.
	 *
	 * @param request
	 * @param repricedItems
	 * @return
	 */
	private Double calculateNewSpendLimit(CreateDraftRequest request, Map<String, BigDecimal> repricedItems) {
		double total = 0d;

		// This list will contain skus that have been added to the total
		// Skus can map to multiple task and each tasks has original price
		List<String> processedSkus = new ArrayList<String>();

		for (CreateTaskRequest task : request.getTasks()) {
			String jobCode = task.getJobCode();
			// Null check is in order since jobless and priceless tasks are totally valid
			// example:  merchandise delivery
			if (jobCode != null) {
				if (!processedSkus.contains(jobCode))  {
					if (repricedItems.containsKey(jobCode)) {
						total += repricedItems.get(jobCode).doubleValue();
					} else {
						total += task.getLaborPrice().doubleValue();
					}
					processedSkus.add(jobCode);
				}
			}
		}

		// This is necessary to get rid of rounding problems, which theoretically
		// should not exist, but....
		BigDecimal scaledPrice = BigDecimal.valueOf(total);
		scaledPrice = scaledPrice.setScale(2, RoundingMode.HALF_DOWN);

		return Double.valueOf(scaledPrice.doubleValue());
	}

	/**
	 * check for matching order to group it and if matching orders are already posted
	 * then post the order
	 * @param request
	 * @param so
	 * @param securityContext
	 * @throws com.newco.marketplace.exception.BusinessServiceException
	 * @throws BusinessServiceException
	 */
	private void groupTheOrder(CreateDraftRequest request, ServiceOrder so, TierRouteController trController, SecurityContext securityContext) throws com.newco.marketplace.exception.BusinessServiceException, BusinessServiceException {
		IRouteOrderGroupBO routeOrderGrpObj = (IRouteOrderGroupBO) this.getBusinessBeanFacility(ROUTE_ORDER_GROUP_BUSINESS_OBJECT);
		IOrderGroupBO orderGrpBusinessBean = (IOrderGroupBO) getBusinessBeanFacility(GROUP_ORDER_BUSINESS_OBJECT);
		// search for posted group & route it
		String postedParentGroupId = groupOrder(so, String.valueOf(OrderConstants.ROUTED_STATUS));
		if (StringUtils.isNotBlank(postedParentGroupId)) {
			logger.info("OrderDispatcherRequest-->order " + so.getSoId() + "is grouped with posted order in the group " + postedParentGroupId);
			request.setAutoRoute(new Boolean(true));
			List<ServiceOrderSearchResultsVO> groupedOrders = orderGrpBusinessBean.getServiceOrdersForGroup(postedParentGroupId);

			// Calculate the price for Order Group
			orderGrpBusinessBean.priceOrderGroup(groupedOrders);
			// Repost the orders
			// set AUTO ACH info
			try {
				IFinanceManagerBO financeManagerBOObj = (IFinanceManagerBO) this.getBusinessBeanFacility(FINANCE_MANAGER_BUSINESS_OBJECT);
				Buyer buyer = so.getBuyer();
				Integer buyerId = buyer.getBuyerId();
				Account autoAch = financeManagerBOObj.getAutoFundingIndicator(buyerId);
				if (autoAch.isEnabled_ind()) {
					securityContext.setAutoACH(true);
					securityContext.setRole(OrderConstants.BUYER_ROLE);
					securityContext.setAccountID(autoAch.getAccount_id());
				} else {
					securityContext.setAutoACH(false);
				}
			} catch (com.newco.marketplace.exception.core.BusinessServiceException e) {
				logger.info("Error while getting AUTOACH info for security Conext: ");
				throw new BusinessServiceException("Error Error while getting AUTOACH info for security Conext:");
			}

			/* spnId will be updated only in case if grouped orders had different spnId which would be rare */
			boolean isValidSpnForTier = routeOrderGrpObj.validateAndUpdateSpnForGroupedOrders(postedParentGroupId);
			boolean isTierRoutingEnabled = trController.isTierRoutingRequried(so.getSpnId(), request.getBuyerId());

			if (isValidSpnForTier && isTierRoutingEnabled) {
				trController.route(postedParentGroupId, Boolean.TRUE, SPNConstants.TR_REASON_ID_RESTART_TIER_ROUTING);
			} else
				routeOrderGrpObj.processRouteOrderGroup(groupedOrders, null, securityContext);

		} else {
			String draftParentGroupId = groupOrder(so, String.valueOf(OrderConstants.DRAFT_STATUS));
			if (StringUtils.isNotBlank(draftParentGroupId)) {
				logger.info("OrderDispatcherRequest-->order " + so.getSoId() + " is grouped with draft order in the group " + draftParentGroupId);
			}
			request.setAutoRoute(new Boolean(false));
		}
	}

	private String addCustomReferences(CreateDraftRequest request, ServiceOrder so, String externalServiceOrderID) throws Exception {
		// Custom refs are in a list and the keys must match the buyer
		// keys in the DB
		if (null != request.getCustomRef() && request.getCustomRef().size() > 0) {
			// get the buyer bo ot get ref IDs

			//IBuyerBO buyerBusinessBean = (IBuyerBO) getBusinessBeanFacility(BUYER_BUSINESS_OBJECT);
			//List<BuyerReferenceVO> reflist = buyerBusinessBean
			//		.getBuyerReferenceTypes(request.getBuyerId());

			List<BuyerReferenceVO> reflist = getBuyerReferenceTypes(request.getBuyerId());

			for (CustomRef cs : request.getCustomRef()) {
				// get the VO
				ServiceOrderCustomRefVO customRefVo = new ServiceOrderCustomRefVO();
				customRefVo.setRefType(cs.getKey());
				customRefVo.setRefValue(cs.getValue());

				if (WSConstants.CUSTOM_REF_TEMPLATE_NAME.equals(cs.getKey())) {
					// If left null instead of "", then later database
					// insert query for custom ref would fail
					String templateName = request.getTemplateName();
					if (templateName == null) {
						templateName = StringUtils.EMPTY;
					}
					customRefVo.setRefValue(templateName);
				}

				// now get the ref id by matching the key
				boolean found = false;
				for (BuyerReferenceVO refVO : reflist) {
					if (refVO.getReferenceType().equalsIgnoreCase(cs.getKey())) {
						customRefVo.setRefTypeId(refVO.getBuyerRefTypeId());
						// see if it is the so identifier
						if (refVO.isSoIdentifier()) {
							externalServiceOrderID = customRefVo.getRefValue();
						}
						found = true;
						break;
					}
				}
				// business rule is to ignore the ref if not found
				if (found) {
					so.getCustomRefs().add(customRefVo);
				}
			}
		}
		return externalServiceOrderID;
	}

	private boolean isPrimaryCategoryGood(Integer mainCategoryId) throws Exception {
		final String key = "goodPrimaryCategory_" + mainCategoryId;
		Boolean goodPrimaryCategory = (Boolean) SimpleCache.getInstance().get(key);
		if (goodPrimaryCategory == null) {
			IServiceOrderBO orderBusinessBean = (IServiceOrderBO) getBusinessBeanFacility(SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE);
			goodPrimaryCategory = Boolean.valueOf(orderBusinessBean.isValidMainCategory(mainCategoryId));
			SimpleCache.getInstance().put(key, goodPrimaryCategory, SimpleCache.FIVE_MINUTES);
		}
		return goodPrimaryCategory.booleanValue();
	}

	public BuyerSOTemplateDTO getBuyerSoTemplate(Integer buyerId, String templateName) {
		final String key = "BuyerSoTemplate_buyerId_" + buyerId + "_tname_" + templateName;
		BuyerSOTemplateDTO template = (BuyerSOTemplateDTO) SimpleCache.getInstance().get(key);
		if (template == null) {
			IBuyerSOTemplateBO templateBO = (IBuyerSOTemplateBO) getBusinessBeanFacility(BUYER_TEMPLATE_BO);
			template = templateBO.loadBuyerSOTemplate(buyerId, templateName);
			SimpleCache.getInstance().put(key, template, SimpleCache.FIVE_MINUTES);
		}
		return template;
	}

	private DocumentVO getDocumentVo(Integer buyerId, String docName) throws BusinessServiceException {
		DocumentVO documentVO = null;
		final String key = "DocumentVO_buyerId_" + buyerId + "_name_" + docName;
		documentVO = (DocumentVO) SimpleCache.getInstance().get(key);
		if (documentVO == null) {
			IDocumentBO documentBO = (IDocumentBO) this.getBusinessBeanFacility(DOCUMENT_BUSINESS_OBJECT);
			documentVO = documentBO.retrieveDocumentByTitleAndEntityID(Constants.DocumentTypes.BUYER, docName, buyerId);
			SimpleCache.getInstance().put(key, documentVO, SimpleCache.FIVE_MINUTES);
		}
		return documentVO;
	}

	private List<BuyerReferenceVO> getBuyerReferenceTypes(Integer buyerId) throws DataServiceException {
		final String key = "BuyerReferenceTypes_buyerId_" + buyerId;
		List<BuyerReferenceVO> list = (List) SimpleCache.getInstance().get(key);
		if (list == null) {
			IBuyerBO buyerBusinessBean = (IBuyerBO) getBusinessBeanFacility(BUYER_BUSINESS_OBJECT);
			list = buyerBusinessBean.getBuyerReferenceTypes(buyerId);
			SimpleCache.getInstance().put(key, list, SimpleCache.FIVE_MINUTES);
		}
		return list;
	}

	private boolean validateFeature(Integer buyerId, String feature) {
		final String key = feature + "_buyerId_" + buyerId;
		boolean vFlag = false;
		Boolean flag = (Boolean) SimpleCache.getInstance().get(key);
		if (flag != null) {
			vFlag = flag.booleanValue();
		} else {
			IBuyerFeatureSetBO buyerFeatureObj = (IBuyerFeatureSetBO) this.getBusinessBeanFacility(BUYER_FEATURE_SET_BUSINESS_OBJECT);
			vFlag = buyerFeatureObj.validateFeature(buyerId, feature).booleanValue();
			SimpleCache.getInstance().put(key, new Boolean(vFlag), SimpleCache.FIVE_MINUTES);
		}
		return vFlag;
	}

	private Account getAccountDetails(Integer companyId) {
		//final String key = "financeManagerAccount" + "_companyId_" + companyId;
		//Account acc = (Account)SimpleCache.getInstance().get(key);

		//if (acc == null) {
		IFinanceManagerBO financeManagerBO = (IFinanceManagerBO) MPSpringLoaderPlugIn.getCtx().getBean("financeManagerBO");
		Account acc = financeManagerBO.getAccountDetails(companyId);
		//SimpleCache.getInstance().put(key, acc, SimpleCache.TWO_MINUTES);
		//}
		return acc;
	}

	/**
	 * @param serviceResponse
	 * @param busProcessResponse
	 * @param coreBSEx
	 * @return
	 */
	private CreateDraftResponse processException(CreateDraftResponse serviceResponse, ProcessResponse busProcessResponse, Exception coreBSEx) {
		logger.error("Error in OrderDispatchRequestor.createDraft() while creating/updating SO", coreBSEx);
		processBusinessError(serviceResponse, coreBSEx, null, null, false, WSConstants.WSProcessStatus.FAILURE);
		if (busProcessResponse != null && (busProcessResponse.getObj() == null || busProcessResponse.getObj().toString().equals(WSConstants.FAILED_SERVICE_ORDER_NO))) {
			serviceResponse.setSLServiceOrderId(WSConstants.FAILED_SERVICE_ORDER_NO);

		}
		return serviceResponse;
	}

	/**
	 * Route a service order based on criteria in the request
	 */
	public RouteResponse routeServiceOrder(RouteRequest request) {

		RouteResponse serviceResponse = new RouteResponse();
		serviceResponse.setClientServiceOrderId(request.getServiceOrderId());
		serviceResponse.setProcessCode(WSConstants.WSProcessCodes.ROUTE_SO);

		// WebService Authentication
		ABaseResponse securityResponse = doWSServiceAccessAuthenication(request);
		if (securityResponse.isError()) {
			return (RouteResponse) processSecurityResponseError(serviceResponse);
		}

		// Retrieve SO based on soId in input request
		IServiceOrderBO businessObject = (IServiceOrderBO) this.getBusinessBeanFacility(SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE);
		IProviderSearchBO provSearchObj = (IProviderSearchBO) this.getBusinessBeanFacility(PROVIDER_SEARCH_BUSINESS_OBJECT_REFERENCE);
		IMasterCalculatorBO masterCalcObj = (IMasterCalculatorBO) this.getBusinessBeanFacility(MASTER_CALCULATOR_BUSINESS_OBJECT_REFERENCE);
		ServiceOrder so = null;
		try {
			so = businessObject.getServiceOrder(request.getServiceOrderId());
			if (null == so) {
				throw new BusinessServiceException("The returned SO is null");
			}
		} catch (BusinessServiceException bsEx) {
			logger.error("Error routing service order " + request.getServiceOrderId(), bsEx);
			processBusinessError(serviceResponse, bsEx, null, null, false, WSConstants.WSProcessStatus.FAILURE);
			//FIXME should handle exception
		}

		// Setup Provider Search Filters
		ArrayList<RatingParameterBean> ratingParamBeans = new ArrayList<RatingParameterBean>();

		ZipParameterBean zipBean = new ZipParameterBean();
		zipBean.setRadius(request.getDistance());
		//FIXME so can be null at this point, so should handle.
		zipBean.setZipcode(so.getServiceLocation().getZip());
		zipBean.setCredentialId(so.getPrimarySkillCatId());
		ratingParamBeans.add(zipBean);

		BackgroundCheckParameterBean backgroundBean = new BackgroundCheckParameterBean();
		backgroundBean.setBackgroundCheck(request.getBackgroundCheck());
		ratingParamBeans.add(backgroundBean);

		if (request.getRating() != null && request.getRating().doubleValue() >= 0) {
			StarParameterBean starBean = new StarParameterBean();
			starBean.setNumberOfStars(request.getRating());
			ratingParamBeans.add(starBean);
		}

		if (request.getLanguageList() != null) {
			LanguageParameterBean langBean = new LanguageParameterBean();
			langBean.setSelectedLangs(request.getLanguageList());
			ratingParamBeans.add(langBean);
		}

		if (request.getCredentialTypeId() != null) {
			CredentialParameterBean credentialBean = new CredentialParameterBean();
			credentialBean.setCredentialId(request.getCredentialTypeId());
			credentialBean.setCredentialTypeId(request.getCredentialCatagoryId());
			ratingParamBeans.add(credentialBean);
		}

		ProviderSearchCriteriaVO provSearchVO = new ProviderSearchCriteriaVO();
		provSearchVO.setBuyerID(request.getBuyerId());
		provSearchVO.setServiceLocation(so.getServiceLocation());
		provSearchVO.setServiceOrderID(request.getServiceOrderId());

		if (so.getPrimarySkillCatId() != null) {
			ArrayList<Integer> skillNodeIds = new ArrayList<Integer>();
			List<Integer> skillTypes = new ArrayList<Integer>();
			if (null != so.getTasks() && so.getTasks().size() > 0) {
				// If we have tasks then assign the SubCategory/Category to the
				// list
				// else select the main category
				for (ServiceOrderTask taskDto : so.getTasks()) {
					if (taskDto.getSubCategoryName() != null) {
						skillNodeIds.add(taskDto.getSkillNodeId());
					} else if (taskDto.getCategoryName() != null) {
						skillNodeIds.add(taskDto.getSkillNodeId());
					} else {
						skillNodeIds.add(so.getPrimarySkillCatId());
					}
					if (taskDto.getServiceTypeId() != null) {
						skillTypes.add(taskDto.getServiceTypeId());
					}
				}
				provSearchVO.setSkillNodeIds(skillNodeIds);
				provSearchVO.setSkillServiceTypeId(skillTypes);
			} else {
				skillNodeIds.add(so.getPrimarySkillCatId());
				provSearchVO.setSkillNodeIds(skillNodeIds);
			}
		}

		// Add SPN id to provider search filters; if available in the buyer
		// template
		//IBuyerSOTemplateBO templateBO = (IBuyerSOTemplateBO) getBusinessBeanFacility(BUYER_TEMPLATE_BO);
		//BuyerSOTemplateDTO template = templateBO.loadBuyerSOTemplate(request
		//		.getBuyerId(), request.getTemplateName());
		BuyerSOTemplateDTO template = getBuyerSoTemplate(request.getBuyerId(), request.getTemplateName());
		if (template != null && template.getSpnId() != null) {
			provSearchVO.setSpnID(template.getSpnId());
		}

		// Do initial provider search
		ArrayList<ProviderResultVO> resultsAL = provSearchObj.getProviderList(provSearchVO);

		// Apply filters and get refined results
		List<ProviderResultVO> filteredResultsAL = masterCalcObj.getFilteredProviderList(ratingParamBeans, resultsAL);

		// Further filtering on percentage matching providers
		List<Integer> routedResourceIds = new ArrayList<Integer>();
		for (ProviderResultVO vo : filteredResultsAL) {
			if (template != null) {
				if (vo.getPercentageMatch().doubleValue() >= template.getSpnPercentageMatch().doubleValue()) {
					routedResourceIds.add(Integer.valueOf(vo.getResourceId()));
				}
			} else {
				routedResourceIds.add(Integer.valueOf(vo.getResourceId()));
			}
		}

		// Call business method to route the SO to searched providers
		SecurityContext securityContext = createSecurityContext(request.getUserId(), request.getPassword());
		if (request.getBuyerId() != null) {
			ServiceOrderUtil.enrichSecurityContext(securityContext, request.getBuyerId());
		}
		ProcessResponse businessProcessResponse = businessObject.processRouteSO(request.getBuyerId(), request.getServiceOrderId(), routedResourceIds, OrderConstants.NON_TIER, securityContext);
		processBusinessResponse(request.getServiceOrderId(), serviceResponse, businessProcessResponse);

		return (RouteResponse) processResponse(serviceResponse);
	}

	/**
	 * Cancel a service order based on request criteria
	 */
	public CancelSOResponse cancelSO(CancelSORequest request) {

		CancelSOResponse serviceResponse = new CancelSOResponse();
		serviceResponse.setProcessCode(WSConstants.WSProcessCodes.CANCEL_SO);

		// WebService Authentication
		ABaseResponse securityResponse = doWSServiceAccessAuthenication(request);
		if (securityResponse.isError()) {
			return (CancelSOResponse) processSecurityResponseError(serviceResponse);
		}

		ProcessResponse busProcessResponse = new ProcessResponse();
		SecurityContext securityContext = createSecurityContext(request.getUserId(), request.getPassword());
		if (request.getBuyerId() != null) {
			ServiceOrderUtil.enrichSecurityContext(securityContext, request.getBuyerId());
		}

		IServiceOrderBO businessObject = (IServiceOrderBO) this.getBusinessBeanFacility(SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE);

		ServiceOrder so = null;
		try {
			so = businessObject.getServiceOrder(request.getServiceOrderID());
			if (null == so) {
				throw new BusinessServiceException("The returned SO is null for input soId:" + request.getServiceOrderID());
			}
		} catch (BusinessServiceException bsEx) {
			logger.error(bsEx.getMessage(), bsEx);
			processBusinessError(serviceResponse, bsEx, null, null, false, WSConstants.WSProcessStatus.FAILURE);
		}

		// Setting a default comment when canceled using a WebService (as cancel
		// comments are optional as per WSDL definition)
		request.setCancelComment("Cancelled by WebService");

		// Call business API to Void or Cancel or send email notification based
		// on SO status
		int status = so.getWfStateId().intValue();
		logger.debug("The status inside CancelSODispatchRequestor " + status);
		try {
			if (status == OrderConstants.DRAFT_STATUS || status == OrderConstants.ROUTED_STATUS) {
				busProcessResponse = businessObject.processVoidSOForWS(request.getBuyerId().intValue(), request.getServiceOrderID(), securityContext);
			} else if (status == OrderConstants.ACCEPTED_STATUS) {
				busProcessResponse = businessObject.processCancelSOInAccepted(request.getBuyerId().intValue(), request.getServiceOrderID(), request.getCancelComment(), request.getBuyerName(), securityContext);
			} else if (status == OrderConstants.ACTIVE_STATUS) {
				busProcessResponse = businessObject.sendCancelEmailInActiveStatus(request.getServiceOrderID(), request.getBuyerId().intValue());
			}
		} catch (BusinessServiceException bsEx) {
			//FIXME This exception should be handled
		}

		if (busProcessResponse.isSuccess()) {
			serviceResponse.setProcessStatus(WSConstants.WSProcessStatus.SUCCESS);
			serviceResponse.setHasError(false);
			if (request.getClientId() != null) {
				serviceResponse.setClientServiceOrderId(request.getClientId());
			}
			if (request.getServiceOrderID() != null) {
				serviceResponse.setSLServiceOrderId(request.getServiceOrderID());
			}
		} else if (busProcessResponse.isError()) {
			if (status == OrderConstants.DRAFT_STATUS || status == OrderConstants.ROUTED_STATUS || status == OrderConstants.ACCEPTED_STATUS) {
				if ((String) busProcessResponse.getObj() != null) {
					serviceResponse.setOrderStatus((String) busProcessResponse.getObj());
					serviceResponse.setProcessStatus(WSConstants.WSProcessStatus.FAILURE);
					serviceResponse.setHasError(true);
					List<WSErrorInfo> errorList = new ArrayList<WSErrorInfo>();
					for (String Message : busProcessResponse.getMessages()) {
						WSErrorInfo ErrorInfo = new WSErrorInfo();
						ErrorInfo = CreateErrorList(WSConstants.WSErrors.Codes.CA_SO_BL_ERR, Message);
						errorList.add(ErrorInfo);
						errorList.add(ErrorInfo);
					}
					serviceResponse.setErrorList(errorList);
					serviceResponse.setClientServiceOrderId(request.getClientId());
					serviceResponse.setSLServiceOrderId(request.getServiceOrderID());
				}
			} else {
				logger.warn("InValid status to Cancel. The Status is" + status);
				serviceResponse.setProcessStatus(WSConstants.WSProcessStatus.FAILURE);
				serviceResponse.setHasError(true);
				ArrayList<WSErrorInfo> errorList = new ArrayList<WSErrorInfo>();
				WSErrorInfo ErrorInfo = new WSErrorInfo();
				ErrorInfo = CreateErrorList(WSConstants.WSErrors.Codes.CA_SO_BL_ERR, OrderConstants.SERVICE_ORDER_IN_OTHER_STATE);
				errorList.add(ErrorInfo);
				serviceResponse.setErrorList(errorList);
				return serviceResponse;
			}
		}
		return (CancelSOResponse) processResponse(serviceResponse);
	}

	public WSErrorInfo CreateErrorList(String Code, String Message) {
		WSErrorInfo ErrorInfo = new WSErrorInfo();
		if (Code != null)
			ErrorInfo.setCode(Code);
		if (Message != null)
			ErrorInfo.setMessage(Message);
		return ErrorInfo;
	}

	/**
	 * Description of the Method
	 *
	 * @return Description of the Return Value
	 */
	public boolean ping() {
		return false;
	}

	/**
	 * Call to reschedule an SO - this should make the call to directly effect
	 * the SO not just a reschedule request
	 *
	 * @author gjacks8
	 * @param RescheduleSORequest
	 */
	public RescheduleSOResponse rescheduleSO(RescheduleSORequest request) {

		RescheduleSOResponse serviceResponse = new RescheduleSOResponse();
		String soID = request.getServiceOrderID();
		serviceResponse.setClientServiceOrderId(soID);
		serviceResponse.setProcessCode(WSConstants.WSProcessCodes.RESCHEDULE_SO);

		// WebService Authentication
		ABaseResponse securityResponse = doWSServiceAccessAuthenication(request);
		if (securityResponse.isError()) {
			return (RescheduleSOResponse) processSecurityResponseError(serviceResponse);
		}
		try {
			Timestamp startDate = null;
			if (request.getNewDateStart() != null) {
				startDate = new Timestamp(OrderDispatcherHelper.parseXMLGregorian(request.getNewDateStart()).getTime());
			}
			Timestamp endDate = null;
			if (request.getNewDateEnd() != null) {
				endDate = new Timestamp(OrderDispatcherHelper.parseXMLGregorian(request.getNewDateEnd()).getTime());
			}
			String newTimeStart = request.getNewTimeStart();
			String newTimeEnd = request.getNewTimeEnd();
			Integer buyerId = request.getBuyerId();
			SecurityContext securityContext = createSecurityContext(request.getUserId(), request.getPassword());
			if (request.getBuyerId() != null) {
				ServiceOrderUtil.enrichSecurityContext(securityContext, request.getBuyerId());
			}

			Integer roleId = securityContext.getRoleId();
			IServiceOrderBO businessObject = (IServiceOrderBO) this.getBusinessBeanFacility(SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE);
			ProcessResponse busResponse = businessObject.requestRescheduleSO(soID, startDate, endDate, newTimeStart, newTimeEnd, null, roleId, buyerId, null, securityContext);
			processBusinessResponse(soID, serviceResponse, busResponse);
		} catch (Exception ex) {
			logger.error("Error updating service order schedule", ex);
			processBusinessError(serviceResponse, ex, null, null, false, WSConstants.WSProcessStatus.FAILURE);
		}
		return (RescheduleSOResponse) processResponse(serviceResponse);
	}

	/**
	 * Update Part
	 */
	public UpdatePartResponse updatePart(CreatePartRequest request) {
		UpdatePartResponse serviceResponse = new UpdatePartResponse();
		serviceResponse.setClientServiceOrderId(request.getSoId());
		serviceResponse.setProcessCode(WSConstants.WSProcessCodes.UPDATE_PARTS);

		// WebService Authentication
		ABaseResponse securityResponse = doWSServiceAccessAuthenication(request);
		if (securityResponse.isError()) {
			logger.error("Username and Password error");
			return (UpdatePartResponse) processSecurityResponseError(serviceResponse);
		}

		try {
			serviceResponse = savePart(request, serviceResponse);
		} catch (Exception ex) {
			logger.error("Error in updatePart()", ex);
			processBusinessError(serviceResponse, ex, null, null, false, WSConstants.WSProcessStatus.FAILURE);
		}
		return (UpdatePartResponse) processResponse(serviceResponse);
	}

	public UpdatePartsResponse updateParts(CreatePartsRequest request) {
		UpdatePartsResponse serviceResponse = new UpdatePartsResponse();
		serviceResponse.setProcessCode(WSConstants.WSProcessCodes.UPDATE_PARTS);

		// WebService Authentication
		ABaseResponse securityResponse = doWSServiceAccessAuthenication(request);
		if (securityResponse.isError()) {
			logger.error("Username and Password error");
			return (UpdatePartsResponse) processSecurityResponseError(serviceResponse);
		}

		try {
			// loop thru the list and call update part on each
			String soId = null;
			for (CreatePartRequest part : request.getParts()) {
				soId = part.getSoId();
				serviceResponse.setClientServiceOrderId(soId);
				serviceResponse.getResponses().add(savePart(part, new UpdatePartResponse()));
			}
			serviceResponse.setSLServiceOrderId(soId);
			serviceResponse.setProcessStatus(WSConstants.WSProcessStatus.SUCCESS);
		} catch (Exception ex) {
			logger.error("Error in updateParts()", ex);
			processBusinessError(serviceResponse, ex, null, null, false, WSConstants.WSProcessStatus.FAILURE);
		}
		return (UpdatePartsResponse) processResponse(serviceResponse);
	}

	private UpdatePartResponse savePart(CreatePartRequest request, UpdatePartResponse serviceResponse) throws Exception {
		Part part = OrderDispatcherHelper.adaptRequest(request);
		Map<String, List<WSErrorInfo>> map = PartValidator.validate(part);
		if (!map.isEmpty()) {
			logger.info("Update Part validation has errors/warnings");
			serviceResponse.setErrorList(map.get("error"));
			serviceResponse.setWarningList(map.get("warning"));
			return serviceResponse;
		}
		IServiceOrderBO businessObject = (IServiceOrderBO) this.getBusinessBeanFacility(SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE);
		try {
			ProcessResponse busProcessResponse = businessObject.processUpdatePart(part, new Integer(OrderConstants.SOW_SOW_BUYER_PROVIDES_PART));
			if (busProcessResponse.isSuccess()) {
				serviceResponse.setProcessStatus(WSConstants.WSProcessStatus.SUCCESS);
			} else if (busProcessResponse.isError()) {
				serviceResponse.setProcessStatus(WSConstants.WSProcessStatus.FAILURE);
			}
		} catch (BusinessServiceException ex) {
			logger.error("updatePart()-->EXCEPTION-->", ex);
		}
		return serviceResponse;
	}

	/**
	 * String techID, String serviceOrderID, String activity, String reasonCode
	 *
	 * @param techID
	 * @param serviceOrderID
	 * @param activity -
	 *            arrive, depart complete, depart issues
	 * @param reasonCode -
	 *            only for issues 1. Cust not home 2. Project Out Of scope 3.
	 *            Site not ready 4. Wrong Part
	 * @return response
	 */
	public String insertEvent(EventRequest request) {
		// IVR can not collect the username and password for the buyer so
		// default to ivr/ivr
		// ABaseResponse securityResponse =
		// doWSServiceAccessAuthenication(request);

		if (!request.getUserId().equals(IVR_USERNAME) || !request.getPassword().equals(IVR_PASSWORD)) {
			// set up the return with the error and pass to handler method to
			// fill rest
			return WSConstants.WSProcessStatus.FAILURE;
		}
		ISOEventBO businessObject = (ISOEventBO) this.getBusinessBeanFacility(SERVICE_ORDER_EVENT_BUSINESS_OBJECT);
		try {
			ProcessResponse busProcessResponse = businessObject.insertEvent(request.getServiceOrderID(), request.getResourceID(), request.getEventReasonCode(), request.getEventTypeID());
			if (busProcessResponse.isSuccess()) {
				return WSConstants.WSProcessStatus.SUCCESS;
			} else if (busProcessResponse.isError()) {
				return WSConstants.WSProcessStatus.FAILURE;
			}
		} catch (BusinessServiceException ex) {
			logger.error("Error in insertEvent()", ex);
			return WSConstants.WSProcessStatus.FAILURE;
		}
		return WSConstants.WSProcessStatus.SUCCESS;
	}

	public String validateAServiceOrder(String resourceId, String soId) {
		// make a new response object to return
		String serviceResponse = "";
		// set up known values
		if (StringUtils.isEmpty(resourceId) || StringUtils.isEmpty(soId) || soId.length() < 13) {
			logger.error("Error missing or invalid fields in validate a service order");
			return WSConstants.WSProcessStatus.FAILURE;
		}
		// check for int
		Integer iresourceId;
		try {
			iresourceId = Integer.valueOf(resourceId);
		} catch (NumberFormatException ex) {
			logger.error("Error parsing service order resourceId " + resourceId);
			return WSConstants.WSProcessStatus.FAILURE;
		}
		// check for dashes in the service order and place them if not found
		// the soId is checked for length earlier
		if (soId.indexOf('-') < 0) {
			char[] nums = soId.toCharArray();
			StringBuilder sb = new StringBuilder();
			sb.append(nums[0]).append(nums[1]).append(nums[2]).append('-').append(nums[3]).append(nums[4]).append(nums[5]).append(nums[6]).append('-').append(nums[7]).append(nums[8]).append(nums[9]).append(nums[10]).append('-').append(nums[11]).append(nums[12]);
			soId = sb.toString();
		}
		try {
			IServiceOrderBO businessObject = (IServiceOrderBO) this.getBusinessBeanFacility(SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE);
			boolean valid = businessObject.isAValidServiceOrder(iresourceId, soId);
			if (valid) {
				serviceResponse = businessObject.getSoCustomReferencesWS(soId);
			} else {
				serviceResponse = WSConstants.WSProcessStatus.INVALID;
			}
		} catch (Exception ex) {
			logger.error("Error in business object " + ex.getMessage());
			return WSConstants.WSProcessStatus.FAILURE;
		}

		return serviceResponse;
	}

	public String[] validateServiceOrders(String resourceId, String last6digits) {
		// make a new response object to return
		String[] serviceResponse = new String[0];
		if (StringUtils.isEmpty(resourceId) || StringUtils.isEmpty(last6digits) || last6digits.length() < 6) {
			logger.error("Error missing fields in validate a service order");
			return new String[] { WSConstants.WSProcessStatus.FAILURE };
		}
		// strip dashes if they exist
		if (last6digits.indexOf('-') >= 0) {
			last6digits = last6digits.replaceAll("-", "");
		}
		// check for int
		Integer iresourceId = null;
		try {
			iresourceId = Integer.valueOf(resourceId);
		} catch (NumberFormatException ex) {
			logger.error("Error parsing fields in validate a service order " + ex.getMessage());
			return new String[] { WSConstants.WSProcessStatus.FAILURE };
		}
		try {
			IServiceOrderBO businessObject = (IServiceOrderBO) this.getBusinessBeanFacility(SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE);
			List<String> orders = businessObject.findValidServiceOrders(iresourceId, last6digits);
			return orders.toArray(serviceResponse);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			return new String[] { WSConstants.WSProcessStatus.FAILURE };
		}
	}

	public String validateServiceOrdersString(String techid, String last6digits) {
		String tokenizedServiceOrders = WSConstants.WSProcessStatus.FAILURE;
		String[] validServiceOrders = validateServiceOrders(techid, last6digits);
		if (validServiceOrders != null) {
			if (validServiceOrders.length == 1) {
				String soId = validServiceOrders[0];
				IServiceOrderBO businessObject = (IServiceOrderBO) this.getBusinessBeanFacility(SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE);
				tokenizedServiceOrders = businessObject.getSoCustomReferencesWS(soId);
			} else {
				StringBuilder strBuilder = new StringBuilder();
				for (int i = 0; i < validServiceOrders.length; i++) {
					strBuilder.append(validServiceOrders[i]);
					if (i != (validServiceOrders.length - 1)) {
						strBuilder.append("|");
					}
				}
				tokenizedServiceOrders = strBuilder.toString();
			}
		}

		return tokenizedServiceOrders;
	}

	public AddDocumentsResponse addSODocuments(AddDocumentsRequest request) {

		AddDocumentsResponse serviceResponse = new AddDocumentsResponse();
		serviceResponse.setClientServiceOrderId(request.getServiceOrderID());
		serviceResponse.setProcessCode(WSConstants.WSProcessCodes.ADD_DOCUMENT_SO);

		// WebService Authentication
		ABaseResponse securityResponse = doWSServiceAccessAuthenication(request);
		if (securityResponse.isError()) {
			return (AddDocumentsResponse) processSecurityResponseError(serviceResponse);
		}

		// Call Business API to add documents
		SecurityContext securityContext = createSecurityContext(request.getUserId(), request.getPassword());
		if (request.getBuyerId() != null) {
			ServiceOrderUtil.enrichSecurityContext(securityContext, request.getBuyerId());
		}

		saveDocumentsToServiceOrder(request.getDocuments(), request.getBuyerId(), request.getServiceOrderID(), securityContext, serviceResponse);
		return (AddDocumentsResponse) processResponse(serviceResponse);
	}

	private void saveDocumentsToServiceOrder(List<Document> documents, Integer buyerId, String serviceOrderId, SecurityContext securityContext, WSProcessResponse serviceResponse) {
		IDocumentBO documentBO = (IDocumentBO) this.getBusinessBeanFacility(DOCUMENT_BUSINESS_OBJECT);
		// for loop of documents
		for (Document document : documents) {
			try {
				// get DocumentVO by title
				DocumentVO documentVO = documentBO.retrieveDocumentByTitleAndEntityID(Constants.DocumentTypes.BUYER, document.getTitle(), buyerId);
				if (null != documentVO) {
					// push into service order
					// the document needs to be copied so wipe out the id and a
					// new one will be created
					documentVO.setDocumentId(null);
					// add the service order number
					documentVO.setSoId(serviceOrderId);
					documentVO.setCompanyId(securityContext.getCompanyId());
					ProcessResponse busResponse = documentBO.insertServiceOrderDocument(documentVO);
					processBusinessResponse(serviceOrderId, serviceResponse, busResponse);
				} else {
					processBusinessError(serviceResponse, null, "", "Document " + document.getTitle() + " can not be found.", true, WSConstants.WSProcessStatus.FAILURE);
				}
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
				processBusinessError(serviceResponse, ex, null, null, false, WSConstants.WSProcessStatus.FAILURE);
			}
		}
	}

	public String insertEventString(String request) {
		EventRequest eventRequest = StringToWSBeanConverter.convertToEventRequest(request);
		return insertEvent(eventRequest);
	}

	private String groupOrder(ServiceOrder so, String status) {
		String parentGroupId = null;
		try {
			IOrderGroupBO orderGrpBusinessBean = (IOrderGroupBO) getBusinessBeanFacility(GROUP_ORDER_BUSINESS_OBJECT);
			parentGroupId = orderGrpBusinessBean.getParentMatchForSO(so, status);
		} catch (com.newco.marketplace.exception.BusinessServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parentGroupId;
	}

	public ClientServiceOrderNoteResponse addClientServiceOrderNote(ClientServiceOrderNoteRequest request) {

		ClientServiceOrderNoteResponse serviceResponse = new ClientServiceOrderNoteResponse();
		serviceResponse.setProcessCode(WSConstants.WSProcessCodes.ADD_CLIENT_SO_NOTE);

		// WebService Authentication
		ABaseResponse securityResponse = doWSServiceAccessAuthenication(request);
		if (securityResponse.isError()) {
			return (ClientServiceOrderNoteResponse) processSecurityResponseError(serviceResponse);
		}

		IServiceOrderBO orderBusinessBean = (IServiceOrderBO) getBusinessBeanFacility(SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE);
		SecurityContext securityContext = createSecurityContext(request.getUserId(), request.getPassword());
		if (request.getBuyerId() != null) {
			ServiceOrderUtil.enrichSecurityContext(securityContext, request.getBuyerId());
		}

		try {
			ServiceOrder savedOrder = orderBusinessBean.getByCustomReferenceValue(request.getOrderIDString(), request.getBuyerId());
			if (null != savedOrder) {
				boolean isEmailToBeSent = false;
				ProcessResponse busResponse = orderBusinessBean.processAddNote(savedOrder.getBuyerResourceId(), request.getRoleId(), savedOrder.getSoId(), request.getSubject(), request.getNote(), OrderConstants.SO_NOTE_GENERAL_TYPE, request.getCreatedBy(), null, savedOrder.getBuyerResourceId(), request.getNoteType(), isEmailToBeSent, false, securityContext);

				processBusinessResponse(savedOrder.getSoId(), serviceResponse, busResponse);
			} else {
				processBusinessError(serviceResponse, null, WSConstants.WSErrors.Codes.ACN_SERVICEORDER_NOTFOUND, WSConstants.WSErrors.Messages.ACN_SERVICEORDER_NOTFOUND, false, WSConstants.WSProcessStatus.INVALID);
				serviceResponse.setSLServiceOrderId(WSConstants.FAILED_SERVICE_ORDER_NO);
			}
		} catch (BusinessServiceException bsEx) {
			logger.error("Error processing client service order note", bsEx);
			processBusinessError(serviceResponse, bsEx, null, null, false, WSConstants.WSProcessStatus.FAILURE);
			serviceResponse.setSLServiceOrderId(WSConstants.FAILED_SERVICE_ORDER_NO);
		}
		return serviceResponse;
	}

	public GetServiceOrderResponse getServiceOrder(String soId) {

		ServiceOrder serviceOrder = null;
		GetServiceOrderResponse response = new GetServiceOrderResponse();
		OrderStatus orderStatus = new OrderStatus();
		IServiceOrderBO orderBusinessBean = (IServiceOrderBO) getBusinessBeanFacility(SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE);
		try {
			serviceOrder = orderBusinessBean.getServiceOrder(soId);
		} catch (BusinessServiceException bsEx) {
			logger.error("Exception Occurred while retrieving the " + "service order" + soId + "+-->" + bsEx);
		}

		if (null != serviceOrder) {
			orderStatus.setSoId(serviceOrder.getSoId());
			orderStatus.setStatus(serviceOrder.getStatus());
			if (null != serviceOrder.getCreatedDate()) {
				orderStatus.setCreatedDate(serviceOrder.getCreatedDate().toString());
			} else {
				orderStatus.setCreatedDate("");
			}
			if (null != serviceOrder.getRoutedDate()) {
				orderStatus.setPostedDate(serviceOrder.getRoutedDate().toString());
			} else {
				orderStatus.setPostedDate("");
			}
			if (null != serviceOrder.getActivatedDate()) {
				orderStatus.setActiveDate(serviceOrder.getActivatedDate().toString());
			} else {
				orderStatus.setActiveDate("");
			}
			if (null != serviceOrder.getCompletedDate()) {
				orderStatus.setCompletedDate(serviceOrder.getCompletedDate().toString());
			} else {
				orderStatus.setCompletedDate("");
			}
			if (null != serviceOrder.getClosedDate()) {
				orderStatus.setClosedDate(serviceOrder.getClosedDate().toString());
			} else {
				orderStatus.setClosedDate("");
			}
		} else {
			orderStatus.setSoId(soId);
			orderStatus.setStatus("ServiceOrder does not exist");
			orderStatus.setCreatedDate("");
			orderStatus.setPostedDate("");
			orderStatus.setActiveDate("");
			orderStatus.setCompletedDate("");
			orderStatus.setClosedDate("");
		}

		response.setOrderStatus(orderStatus);

		return response;
	}

	public UpdateIncidentTrackingResponse updateIncidentTrackingWithAck(UpdateIncidentTrackingRequest request) {
		UpdateIncidentTrackingResponse serviceResponse = new UpdateIncidentTrackingResponse();
		serviceResponse.setProcessCode(WSConstants.WSProcessCodes.UPDATE_SO_INCIDENT_TRACKING);

		// WebService Authentication
		ABaseResponse securityResponse = doWSServiceAccessAuthenication(request);
		if (securityResponse.isError()) {
			return (UpdateIncidentTrackingResponse) processSecurityResponseError(serviceResponse);
		}

		IIncidentBO incidentBusinessBean = (IIncidentBO) getBusinessBeanFacility(INCIDENT_BUSINESS_OBJECT_REFERENCE);
		try {
			IncidentTrackingVO incidentTracking = incidentBusinessBean.getLastTrackingForIncidentAndSubstatus(request.getIncidentId(), request.getIncidentStatus());
			if (incidentTracking == null) {
				throw new com.newco.marketplace.exception.BusinessServiceException("There is not tracking record for " + request.getClientIncidentid() + " and status '" + request.getIncidentStatus() + "'");
			}
			incidentTracking.setIncidentAckId(request.getIncidentAckId());
			incidentBusinessBean.updateIncidentTrackingWithACKId(incidentTracking);
			serviceResponse.setProcessStatus(WSConstants.WSProcessStatus.SUCCESS);

		} catch (com.newco.marketplace.exception.BusinessServiceException bsEx) {
			logger.error("Error updating So_incident_tracking table with acknowldegement", bsEx);
			processBusinessError(serviceResponse, bsEx, null, null, false, WSConstants.WSProcessStatus.FAILURE);
		}
		return serviceResponse;
	}
	/**
	 * This operation has been added to get ServiceOrderId and Status Using CustomReference
	 * @param request
	 * @return GetSOStatusResponse
	 */
	public GetSOStatusResponse getSOStatus(GetSOStatusRequest request) {
		GetSOStatusResponse getSOStatusResponse=new GetSOStatusResponse();
		// WebService Authentication
		ABaseResponse securityResponse = doWSServiceAccessAuthenication(request);
		if (securityResponse.isError()) {
			return (GetSOStatusResponse) processSecurityResponseError(getSOStatusResponse);
		}
		ServiceOrder serviceOrder = null;
		IServiceOrderBO orderBusinessBean = (IServiceOrderBO) getBusinessBeanFacility(SERVICE_ORDER_BUSINESS_OBJECT_REFERENCE);
		try {
			serviceOrder = orderBusinessBean.getByCustomReferenceValue(request.getUniqueCustomReferenceValue(), request.getBuyerId());
		} catch (BusinessServiceException bsEx) {
			logger.error("Exception Occurred while retrieving the service order with reference value" + request.getUniqueCustomReferenceValue() + "+-->" , bsEx);
			processBusinessError(getSOStatusResponse, bsEx, null, null, false, WSConstants.WSProcessStatus.FAILURE);
		}
		if (null != serviceOrder) {
			getSOStatusResponse.setSLServiceOrderId(serviceOrder.getSoId());
			getSOStatusResponse.setSLServiceOrderStatus(serviceOrder.getStatus());
		}
		return getSOStatusResponse;
	}
}
