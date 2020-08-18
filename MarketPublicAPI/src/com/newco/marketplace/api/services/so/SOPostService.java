/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 11-Jun-2009	KMSTRSUP   Infosys				1.0
 * 
 * 
 */
package com.newco.marketplace.api.services.so;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.so.post.SOPostRequest;
import com.newco.marketplace.api.beans.so.post.SOPostResponse;
import com.newco.marketplace.api.security.SecurityProcess;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.SOPostMapper;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.LanguageParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.StarParameterBean;
import com.newco.marketplace.business.businessImpl.providerSearch.rating.ZipParameterBean;
import com.newco.marketplace.business.iBusiness.providersearch.IMasterCalculatorBO;
import com.newco.marketplace.business.iBusiness.providersearch.IProviderSearchBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IBuyerSOTemplateBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.sears.os.utils.DateValidationUtils;

/**
 * This class is a service class for Posting Service Orders
 * 
 * @author Infosys
 * @version 1.0
 */
public class SOPostService {
	private Logger logger = Logger.getLogger(SOPostService.class);
	private XStreamUtility conversionUtility;
	private SecurityProcess securityProcess;
	private IServiceOrderBO serviceOrderBO;
	private IProviderSearchBO provSearchObj;
	private IMasterCalculatorBO masterCalcObj;
	private IBuyerSOTemplateBO templateBO;
	private SOPostMapper postMapper;

	/**
	 * This method dispatches the post service order request.
	 * 
	 * @param String postOrderRequest
	 * @param String
	 *            soId
	 * @throws Exception
	 * @return String
	 */
	public String dispatchPostServiceRequest(String soPostRequest,
			String soId) throws Exception {
		logger.info("Entering SOPostService.dispatchPostServiceRequest()");
		SOPostRequest postRequest = new SOPostRequest();
		ProcessResponse postResponse = new ProcessResponse();
		List<String> arrMessage = new ArrayList<String>();
		String postResponseXML = null;
		List<Integer> routedResourceIds = new ArrayList<Integer>();
		postRequest = conversionUtility.getPostRequestObject(soPostRequest);
		SecurityContext securityContext = securityProcess.getSecurityContext(
				postRequest.getIdentification().getUsername(), postRequest
						.getIdentification().getPassWord());
		if (securityContext == null) {
			logger.error("SecurityContext is null");
			throw new BusinessServiceException("SecurityContext is null");

		} else {
			int buyerId = securityContext.getCompanyId();
			// Getting service order details
			ServiceOrder serviceOrder = serviceOrderBO.getServiceOrder(soId);
			if (null != serviceOrder) {
				// Validating the service order
				postResponse = validateServiceOrder(serviceOrder);
				if (postResponse.getCode().equalsIgnoreCase(
						PublicAPIConstant.ONE)) {
					// Setting the list of routed providers when it is specified
					// in the input xml
					if (postRequest.getProviderRouteInfo()
							.getSpecificProviders() != null
							&& postRequest.getProviderRouteInfo()
									.getSpecificProviders().getResourceId()
									.size() > 0) {
						routedResourceIds=postRequest
								.getProviderRouteInfo().getSpecificProviders()
								.getResourceId();

					} else {
						// Getting the list of routed providers based on the
						// filter criteria
						ArrayList<RatingParameterBean> ratingParamBeans = new ArrayList<RatingParameterBean>();

						// Getting filter condition for zip
						if (postRequest.getProviderRouteInfo().getMaxDistance() != null) {
							ZipParameterBean zipBean = new ZipParameterBean();
							zipBean.setRadius(Integer.parseInt(postRequest
									.getProviderRouteInfo().getMaxDistance()));
							zipBean.setZipcode(serviceOrder
									.getServiceLocation().getZip());
							zipBean.setCredentialId(serviceOrder
									.getPrimarySkillCatId());
							ratingParamBeans.add(zipBean);
						}

						// Getting filter condition for minimum rating
						if (postRequest.getProviderRouteInfo().getMinRating() != null
								&& Double.parseDouble(postRequest
										.getProviderRouteInfo().getMinRating()) >= 0) {
							StarParameterBean starBean = new StarParameterBean();
							starBean.setNumberOfStars(Double
									.parseDouble(postRequest
											.getProviderRouteInfo()
											.getMinRating()));
							ratingParamBeans.add(starBean);
						}
						// Getting filter condition for language
						if (postRequest.getProviderRouteInfo().getLanguage() != null) {
							LanguageParameterBean langBean = new LanguageParameterBean();
							List<Integer> languageIds = new ArrayList<Integer>();
							// Calling BO method for getting list of language
							// ids from language names
							languageIds = serviceOrderBO
									.getLanguageIds(postRequest
											.getProviderRouteInfo()
											.getLanguage().getLanguages());
							langBean.setSelectedLangs(languageIds);
							ratingParamBeans.add(langBean);
						}

						ProviderSearchCriteriaVO provSearchVO = new ProviderSearchCriteriaVO();
						provSearchVO.setBuyerID(buyerId);
						provSearchVO.setServiceLocation(serviceOrder
								.getServiceLocation());
						provSearchVO.setServiceOrderID(serviceOrder.getSoId());
						if (serviceOrder.getPrimarySkillCatId() != null) {
							ArrayList<Integer> skillNodeIds = new ArrayList<Integer>();
							List<Integer> skillTypes = new ArrayList<Integer>();
							if (null != serviceOrder.getTasks()
									&& serviceOrder.getTasks().size() > 0) {
								// If we have tasks then assign the
								// SubCategory/Category
								// to the list else select the main category
								for (ServiceOrderTask taskDto : serviceOrder
										.getTasks()) {
									if (taskDto.getSubCategoryName() != null) {
										skillNodeIds.add(taskDto
												.getSkillNodeId());
									} else if (taskDto.getCategoryName() != null) {
										skillNodeIds.add(taskDto
												.getSkillNodeId());
									} else {
										skillNodeIds.add(serviceOrder
												.getPrimarySkillCatId());
									}
									if (taskDto.getServiceTypeId() != null) {
										skillTypes.add(taskDto
												.getServiceTypeId());
									}
								}
								provSearchVO.setSkillNodeIds(skillNodeIds);
								provSearchVO.setSkillServiceTypeId(skillTypes);
							} else {
								skillNodeIds.add(serviceOrder
										.getPrimarySkillCatId());
								provSearchVO.setSkillNodeIds(skillNodeIds);
							}
						}
						// Add SPN id to provider search filters; if available
						// in
						// the buyer template

						BuyerSOTemplateDTO template = templateBO
								.loadBuyerSOTemplate(buyerId, postRequest
										.getProviderRouteInfo().getTemplate());
						if (template != null && template.getSpnId() != null) {
							provSearchVO.setSpnID(template.getSpnId());
						}

						// Do initial provider search
						ArrayList<ProviderResultVO> resultsAL = provSearchObj
								.getProviderList(provSearchVO);

						// Apply filters and get refined results
						List<ProviderResultVO> filteredResultsAL = masterCalcObj
								.getFilteredProviderList(ratingParamBeans,
										resultsAL);

						// Further filtering on percentage matching providers

						for (ProviderResultVO vo : filteredResultsAL) {
							if (template != null) {
								if (vo.getPercentageMatch().doubleValue() >= template
										.getSpnPercentageMatch().doubleValue()) {
									routedResourceIds.add(Integer.valueOf(vo
											.getResourceId()));
								}
							} else {
								routedResourceIds.add(Integer.valueOf(vo
										.getResourceId()));
							}
						}

					}
					if (routedResourceIds == null || routedResourceIds.size() == 0){
						logger.info("No Providers selected");
						arrMessage.add(CommonUtility.getMessage(PublicAPIConstant.NO_PROVIDERS_ERROR_CODE));
						postResponse.setMessages(arrMessage);
						postResponse.setCode(ServiceConstants.USER_ERROR_RC);

					} else {
						logger.info("Posting Service Order to "
								+ routedResourceIds.size() + " providers");
						
						//TODO: Pushkar - The following tierID value needs to be set to 
						//an appropriate value after checking with the CAR team.
						Integer tierId = null;
						postResponse = serviceOrderBO.processRouteSO(buyerId,
								serviceOrder.getSoId(), routedResourceIds, tierId,
								securityContext);
						if (postResponse.getCode().equalsIgnoreCase(
								ServiceConstants.VALID_RC)) {
							arrMessage.add(PublicAPIConstant.POST_RESULT1
									+ routedResourceIds.size()
									+ PublicAPIConstant.POST_RESULT2);
							postResponse.setMessages(arrMessage);
						}

					}
				}else{
					postResponse.setCode(ServiceConstants.USER_ERROR_RC);
				}
				ServiceOrder soFinal = serviceOrderBO.getServiceOrder(soId);
				SOPostResponse soPostResponse = new SOPostResponse();
				soPostResponse = postMapper.mapServiceOrder(postResponse, soFinal);
				postResponseXML = conversionUtility
						.getPostResponseXML(soPostResponse);

			} else {
				return null;
			}
		}
		logger.info("Leaving SOPostService.dispatchPostServiceRequest()");
		return postResponseXML;

	}
	/**
	 * This method validates the service order.Checks if the service dates and the labor price are valid
	 * @param ServiceOrder serviceOrder
	 * @return ProcessResponse
	 */
	public ProcessResponse validateServiceOrder(ServiceOrder serviceOrder) {

		ProcessResponse response = new ProcessResponse();
		response.setCode(PublicAPIConstant.ONE);
		List<String> message = new ArrayList<String>();
		logger.info("Checking if dates are valid");
		boolean isCurrentDate = DateValidationUtils
				.fromDateGreaterCurrentDate(serviceOrder.getServiceDate1()
						.toString());
		boolean isPastDateTime = TimeUtils.isPastCurrentTime(serviceOrder
				.getServiceDate1(), serviceOrder.getServiceTimeStart());
		if (isCurrentDate && isPastDateTime) {
			message.add(CommonUtility.getMessage(PublicAPIConstant.FROM_DATETIME_ERROR_CODE));
			response.setMessages(message);
			response.setCode(ServiceConstants.USER_ERROR_RC);
		} else if (!isCurrentDate) {
			message.add(CommonUtility.getMessage(PublicAPIConstant.FROM_DATE_ERROR_CODE));
			response.setMessages(message);
			response.setCode(ServiceConstants.USER_ERROR_RC);
		}

		if (serviceOrder.getServiceDateTypeId() != null
				&& serviceOrder.getServiceDateTypeId() != 1) {
			if (!DateValidationUtils.fromDateLesserToDate(serviceOrder
					.getServiceDate1().toString(), serviceOrder
					.getServiceDate2().toString())) {

				message.add(CommonUtility.getMessage(PublicAPIConstant.FROM_TODATE_ERROR_CODE));
				response.setMessages(message);
				response.setCode(ServiceConstants.USER_ERROR_RC);

			}else if(serviceOrder.getServiceDate1().toString().equalsIgnoreCase(serviceOrder
					.getServiceDate2().toString())){
				if(!DateValidationUtils.timeValidation(serviceOrder.getServiceTimeStart(),serviceOrder.getServiceTimeEnd())){
					logger.info("End time less than start time");
					message.add(CommonUtility.getMessage(PublicAPIConstant.TIME_ERROR_CODE));
					response.setMessages(message);
					response.setCode(ServiceConstants.USER_ERROR_RC);
				}
				
			}
		}
		
		return response;
	}

	public XStreamUtility getConversionUtility() {
		return conversionUtility;
	}

	public void setConversionUtility(XStreamUtility conversionUtility) {
		this.conversionUtility = conversionUtility;
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
	public SecurityProcess getSecurityProcess() {
		return securityProcess;
	}

	public void setSecurityProcess(SecurityProcess securityProcess) {
		this.securityProcess = securityProcess;
	}

}
