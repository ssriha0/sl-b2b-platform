/*
 *    Date        Project       Author         Version
 * -----------  ---------     -----------      ---------
 * 22-June-2015    KMSRVLVE    Infosys                1.0
 *
 *
 */
package com.newco.marketplace.api.services.so;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.annotation.APIRequestClass;
import com.newco.marketplace.api.annotation.APIResponseClass;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.post.FirmList;
import com.newco.marketplace.api.beans.so.post.SOPostFirmRequest;
import com.newco.marketplace.api.beans.so.post.SOPostFirmResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFMapper;
import com.newco.marketplace.api.utils.mappers.so.v1_1.OFPostMapper;
import com.newco.marketplace.api.utils.mappers.so.v1_1.SOPostMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.providersearch.IProviderSearchBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.utils.DateValidationUtils;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;


/**
 * This is the service class for executing the Post Service Order to firm request
 *
 * @author Infosys
 * @version 1.0
 */
@APIRequestClass(SOPostFirmRequest.class)
@APIResponseClass(SOPostFirmResponse.class)
public class SOPostFirmService extends BaseService {
    private Logger logger = Logger.getLogger(SOPostFirmService.class);
    private IServiceOrderBO serviceOrderBO;
    private IProviderSearchBO provSearchObj;
    private SOPostMapper postMapper;
    private OFPostMapper ofPostMapper;
    private OFHelper ofHelper = new OFHelper();


    /**
     * public constructor
     */
    public SOPostFirmService() {
        super(PublicAPIConstant.POST_SO_TO_FIRM_XSD_REQ, PublicAPIConstant.POST_SO_TO_FIRM_XSD_RESP,
                PublicAPIConstant.SO_POSTFIRM_RESPONSE_NAMESPACE,
                PublicAPIConstant.SO_RESOURCES_SCHEMAS,
                PublicAPIConstant.SO_POSTFIRM_RESPONSE_SCHEMALOCATION,
                SOPostFirmRequest.class, SOPostFirmResponse.class);
    }
   

    @Override
    public IAPIResponse execute(APIRequestVO apiVO) {

    	logger.info("Entering SOPostFirmService.execute()");
        SOPostFirmRequest postToFirmRequest = (SOPostFirmRequest) apiVO.getRequestFromPostPut();
        SecurityContext securityContext = getSecurityContextForBuyerAdmin(apiVO.getBuyerIdInteger());
        SOElementCollection soec = new SOElementCollection();
        String soId = apiVO.getSOId();
        FirmList firmIdListFromReq = postToFirmRequest.getFirmIds();
        ServiceOrder soDetails = null;
        int substatus = 0;        
        Set<Integer> providerIdsToPost = new HashSet<Integer>(); 
        HashMap<Integer, List<Integer>> resourceIdsToPost = new HashMap<Integer, List<Integer>>();
        List<Integer> validProvidersFromRequest = null;
        SOPostFirmResponse postSOFirmResponse = new SOPostFirmResponse();
        ProcessResponse postResponse = new ProcessResponse();
        boolean isError=false;
		List<Integer> invalidProviderList = new ArrayList<Integer>();
		
		try {
			String corelationId = postToFirmRequest.getCorelationId();
			logger.info("corelationId = " + corelationId);
			// update corelationId with so_id in `vendor_ranking_weighted_score` and `vendor_so_ranking`
			if (null != corelationId) {
				if (!((corelationId.trim()).equals(""))) {
					if (!((corelationId.trim()).equalsIgnoreCase("null"))) {
						
						HashMap<String, Object> corelationIdAndSoidMap = new HashMap<String, Object>();
						corelationIdAndSoidMap.put("corelationId", corelationId);
						corelationIdAndSoidMap.put("soId", soId);
						corelationIdAndSoidMap.put("modifiedBy", PublicAPIConstant.MODIFIED_BY_PUBLIC_API);
						
						serviceOrderBO.updateCorelationIdWithSoId(corelationIdAndSoidMap);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("error while updating corelationId: ", e);
		}
		
        try {
            //Validation for Invalid/Non SL approved firmIds
        	validProvidersFromRequest = provSearchObj.getValidVendorList(firmIdListFromReq
                    .getFirmId());
           
            if(null != validProvidersFromRequest && !validProvidersFromRequest.isEmpty()){
                for (Integer firm : firmIdListFromReq.getFirmId()) {
                    if (!validProvidersFromRequest.contains(firm)) {
                    	invalidProviderList.add(firm);
                    }
                }
            }else{
            	invalidProviderList=firmIdListFromReq.getFirmId();
            }
           
            if (null == invalidProviderList || invalidProviderList.isEmpty()) {
                // 1. Fetch the so details.
                soDetails = serviceOrderBO.getServiceOrder(apiVO.getSOId());
                if (soDetails != null) {
                    if (soDetails.getWfSubStatusId() != null) {
                        substatus = soDetails.getWfSubStatusId().intValue();
                    }
                    // Checking if the so status is routed/expired/draft and sub-status is not missing information
                    if (null!=soDetails.getWfStateId() && soDetails.getWfStateId() == OrderConstants.ROUTED_STATUS
                            || (soDetails.getWfStateId() == OrderConstants.DRAFT_STATUS && OrderConstants.MISSING_INFORMATION_SUBSTATUS != substatus)
                            || soDetails.getWfStateId() == OrderConstants.EXPIRED_STATUS) {
                       
                        //Date validations of SO
                        postResponse = validateServiceOrder(soDetails,apiVO.getBuyerIdInteger());
                        if (PublicAPIConstant.ONE.equalsIgnoreCase(postResponse.getCode())) {
                            // 2. Get Eligible providers for the given firm to whom
                            // the SO can be routed
                        	providerIdsToPost = findEligibleProviders(apiVO, 
                                    soDetails, validProvidersFromRequest, resourceIdsToPost);
                            
                            
                    		// From the list of resources obtained which match the skill and
                    		// location,
                    		// Select only those resources which come under the provided firmIds.
                            if (null != providerIdsToPost && !providerIdsToPost.isEmpty()) {
                            	for (Integer providerId : validProvidersFromRequest) {
									if(!providerIdsToPost.contains(providerId)){
										invalidProviderList.add(providerId);
									}
								}
                            	if(null != invalidProviderList && !invalidProviderList.isEmpty()){
                            		String errormessage = ResultsCode.POSTFIRM_NOTELIGIBLE_FIRM.getMessage();
	                				errormessage = errormessage
	                						+ " ["
	                						+ StringUtils.join(invalidProviderList.toArray(), ',')
	                						+ "].";
	                				postSOFirmResponse = createErrorResponse(errormessage,
	                						ResultsCode.POSTFIRM_NOTELIGIBLE_FIRM.getCode());
	            					isError = true;
                            	}else if(null != providerIdsToPost && !providerIdsToPost.isEmpty()){
                            		for (Integer providerId : providerIdsToPost) {
                            			for (Integer resourceId : resourceIdsToPost.get(providerId)) {
                            				RoutedProvider rp = new RoutedProvider();
                                			rp.setProviderResourceId(resourceId.longValue());
                                			rp.setVendorId(providerId);
                                			soec.addElement(rp);
										}
									}
                            	}                               
                            }else{
                            	postSOFirmResponse = createErrorResponse(
										ResultsCode.POSTFIRM_NO_ELIGIBLE_PROVIDERS
												.getMessage(),
										ResultsCode.POSTFIRM_NO_ELIGIBLE_PROVIDERS
												.getCode());
								isError = true;
                            }
                        }
                        else{
                        	postSOFirmResponse=createErrorResponse(postResponse.getMessage(),postResponse.getCode());
                        	isError=true;
                        }
                } else {
                	postSOFirmResponse = createErrorResponse(
                                ResultsCode.POSTFIRM_INVALID_SO_STATE.getMessage(),
                                ResultsCode.POSTFIRM_INVALID_SO_STATE.getCode());
                	isError=true;
                    }

                }
            } else {
            	String errormessage = ResultsCode.POSTFIRM_INVALID_FIRM.getMessage();
				errormessage = errormessage
						+ " ["
						+ StringUtils.join(invalidProviderList.toArray(), ',')
						+ "].";
				postSOFirmResponse = createErrorResponse(errormessage,
						ResultsCode.POSTFIRM_INVALID_FIRM.getCode());
            	isError=true;
            }
        }
        catch (Exception e) {
            logger.error("SOPostFirmService-->execute():" + e);
            postSOFirmResponse = createErrorResponse(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),
                    ResultsCode.INTERNAL_SERVER_ERROR.getCode());
            isError=true;
        }
        
        if(!isError){
            // 3. Validate Buyer Transaction Limit
            if (securityContext.getRoleId().intValue() != 5) {
                try {

                    Double buyerMaxTransactionLimit = serviceOrderBO
                            .getBuyerMaxTransactionLimit(
                                    securityContext.getVendBuyerResId(),
                                    apiVO.getBuyerIdInteger());
                    securityContext.setMaxSpendLimitPerSO(buyerMaxTransactionLimit
                            .doubleValue());
                    if (isOrderPriceAboveMaxTransactionLimit(soDetails,
                            securityContext, true)) {
                    	postSOFirmResponse= createErrorResponse(
                                ResultsCode.POSTFIRM_BUYER_UPFUND_ERROR
                                        .getMessage(),
                                ResultsCode.POSTFIRM_BUYER_UPFUND_ERROR
                                        .getCode());
                    	isError=true;
                    }

                } catch (Exception e) {
                    logger.error(
                            "SOPostFirmService.execute(): Exception occurred while "
                                    + "accessing max transaction limit with Buyer id: ",
                            e);
                    postSOFirmResponse= createErrorResponse(ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),
                            ResultsCode.INTERNAL_SERVER_ERROR.getCode());
                    isError=true;
                }
            }
        }
       
        if(!isError){
        	// 4. Call the POST signal which posts the order to eligible providers
            Identification identity = OFMapper
                    .createBuyerIdentFromSecCtx(securityContext);
            OrderFulfillmentResponse ofResponse = ofHelper
                    .runOrderFulfillmentProcess(soId, SignalType.POST_ORDER, soec,
                            identity);
            logger.info("Leaving SOPostFirmService.execute()");

            // 5. Mapping the response to SOPostFirmResponse and returning the
            // result
            postSOFirmResponse= ofPostMapper.mapPostServiceOrderToFirm(
                    ofHelper.getServiceOrder(soId), ofResponse,providerIdsToPost);	
        }
        return postSOFirmResponse;

    }
    
    private void removeRejectedResources(List<Integer> resourceIdsToPost, String soID) throws BusinessServiceException {
    	List<Integer> rejectedReleasedResourceIds = new ArrayList<Integer>();
    	if (null != resourceIdsToPost && !resourceIdsToPost.isEmpty()) {
			rejectedReleasedResourceIds = provSearchObj
					.getResourcesBasedOnProviderResponse(
							soID, resourceIdsToPost);
		}
		if(null != rejectedReleasedResourceIds && !rejectedReleasedResourceIds.isEmpty()){
			for (Integer integer : rejectedReleasedResourceIds) {
				resourceIdsToPost.remove(integer);
			}
		}
	}
   
    /**
     * @param message
     * @param code
     * @return
     */
    private SOPostFirmResponse createErrorResponse(String message, String code){
        SOPostFirmResponse postResponse = new SOPostFirmResponse();
        Results results = Results.getError(message, code);
        postResponse.setResults(results);
        return postResponse;
    }

    /**
     * This method validates the service order.Checks if the service dates are valid
     * @param integer 
     *
     * @param ServiceOrder
     *            serviceOrder
     * @return ProcessResponse
     */
    public ProcessResponse validateServiceOrder(ServiceOrder serviceOrder, int buyerID) {

        ProcessResponse response = new ProcessResponse();
        response.setCode(PublicAPIConstant.ONE);
        List<String> message = new ArrayList<String>();
        logger.info("Checking if dates are valid");
        //Check whether the from date and time is greater than or equal to current time
        boolean isCurrentDate = DateValidationUtils
                .fromDateGreaterCurrentDate(serviceOrder.getServiceDate1()
                        .toString());
        boolean isPastDateTime = false;
        if(buyerID == PublicAPIConstant.TECHTALK_SERVICES_BUYER_ID){
            isPastDateTime = TimeUtils.isPastCurrentUTCTime(serviceOrder
                    .getServiceDateGMT1(), serviceOrder.getServiceTimeStartGMT());
        }else{
            isPastDateTime = TimeUtils.isPastCurrentTime(serviceOrder
                    .getServiceDate1(), serviceOrder.getServiceTimeStart());
        }
        if (isCurrentDate && isPastDateTime) {
            message.add(ResultsCode.POSTFIRM_FROM_DATETIME_ERROR_CODE.getMessage());
            response.setMessages(message);
            response.setCode(ResultsCode.POSTFIRM_FROM_DATETIME_ERROR_CODE.getCode());
        } else if (!isCurrentDate) {
            message.add(ResultsCode.POSTFIRM_FROM_DATE_ERROR_CODE.getMessage());
            response.setMessages(message);
            response.setCode(ResultsCode.POSTFIRM_FROM_DATE_ERROR_CODE.getCode());
        }

        if (serviceOrder.getServiceDateTypeId() != null
                && serviceOrder.getServiceDateTypeId() != 1) {
            //Check whether from date is less than to date
            if (!DateValidationUtils.fromDateLesserToDate(serviceOrder
                    .getServiceDate1().toString(), serviceOrder
                    .getServiceDate2().toString())) {

                message.add(ResultsCode.POSTFIRM_FROM_TODATE_ERROR_CODE.getMessage());
                response.setMessages(message);
                response.setCode(ResultsCode.POSTFIRM_FROM_TODATE_ERROR_CODE.getCode());

            //Check whether from time is less than to time
            } else if (serviceOrder
                    .getServiceDate1()
                    .toString()
                    .equalsIgnoreCase(serviceOrder.getServiceDate2().toString())) {
                if (!DateValidationUtils.timeValidation(serviceOrder
                        .getServiceTimeStart(), serviceOrder
                        .getServiceTimeEnd())) {
                    logger.info("End time less than start time");
                    message.add(ResultsCode.POSTFIRM_TIME_ERROR_CODE.getMessage());
                    response.setMessages(message);
                    response.setCode(ResultsCode.POSTFIRM_TIME_ERROR_CODE.getCode());
                }

            }
        }

        return response;
    }
   
    /**
     * This method is used to get a list of eligible providers based on skill, location
     * @param apiVO
     * @param serviceOrder
     * @param validFirmsFromRequest
     * @return Eligible providerIds
     */
    public Set<Integer> findEligibleProviders(APIRequestVO apiVO,
			ServiceOrder serviceOrder, List<Integer> validFirmsFromRequest, HashMap<Integer, List<Integer>> resourceIdsToPost)
			throws BusinessServiceException {
		Set<Integer> providerIdsToPost = new HashSet<Integer>();
		List<Integer> resourceIds = new ArrayList<>();
		int buyerId = apiVO.getBuyerIdInteger();
		ProviderSearchCriteriaVO provSearchVO = new ProviderSearchCriteriaVO();

		generateProviderSearchCriteria(provSearchVO, validFirmsFromRequest, serviceOrder, buyerId);

		// Do initial provider search
		ArrayList<ProviderResultVO> resultsAL = provSearchObj
				.getProviderList(provSearchVO);
		
		for (ProviderResultVO providerResultVO : resultsAL) {
			resourceIds.add(providerResultVO.getResourceId());
		}
		// If the order is in routed/expired state, filter out providers which have the
		// following responseIds:
		// 1) Released by provider
		// 2) Firm level release
		// 3) Rejected
		if (serviceOrder.getWfStateId() == OrderConstants.ROUTED_STATUS||serviceOrder.getWfStateId() == OrderConstants.EXPIRED_STATUS) {
			removeRejectedResources(resourceIds, serviceOrder.getSoId());
		}
		
		for (ProviderResultVO providerResultVO : resultsAL) {
			if(resourceIds.contains(providerResultVO.getResourceId())){
				providerIdsToPost.add(providerResultVO.getVendorID());
				List<Integer> resourcesforVendor = resourceIdsToPost.get(providerResultVO.getVendorID()) != null ? resourceIdsToPost.get(providerResultVO.getVendorID()) : new ArrayList<>();
				resourcesforVendor.add(providerResultVO.getResourceId());
				resourceIdsToPost.put(providerResultVO.getVendorID(), resourcesforVendor);
			}
		}
		return providerIdsToPost;
	}
    
    
    /**
     * @param provSearchVO
     * @param serviceOrder
     * @param buyerId
     * @param template
     */
    private void generateProviderSearchCriteria(
            ProviderSearchCriteriaVO provSearchVO, List<Integer> firmIds, ServiceOrder serviceOrder,
            int buyerId) {
        provSearchVO.setBuyerID(buyerId);
        provSearchVO.setServiceLocation(serviceOrder.getServiceLocation());
        provSearchVO.setServiceOrderID(serviceOrder.getSoId());
        provSearchVO.setFirmId(firmIds);
        
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


    public SOPostMapper getPostMapper() {
        return postMapper;
    }


    public void setPostMapper(SOPostMapper postMapper) {
        this.postMapper = postMapper;
    }


    public OFPostMapper getOfPostMapper() {
        return ofPostMapper;
    }


    public void setOfPostMapper(OFPostMapper ofPostMapper) {
        this.ofPostMapper = ofPostMapper;
    }


    public OFHelper getOfHelper() {
        return ofHelper;
    }


    public void setOfHelper(OFHelper ofHelper) {
        this.ofHelper = ofHelper;
    }
       
   
}