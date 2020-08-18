package com.newco.marketplace.api.services.leadsmanagement;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.IAPIResponse;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.mappers.lead.LeadOFMapper;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.businessImpl.leadsmanagement.LeadProcessingBO;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CompleteLeadsRequest;
import com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement.CompleteLeadsResponse;
import com.newco.marketplace.dto.vo.leadsmanagement.CompleteLeadsRequestVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.NewServiceConstants;
import com.servicelive.orderfulfillment.client.OFHelper;
import com.servicelive.orderfulfillment.serviceinterface.vo.LeadResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentLeadRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.SignalType;

public class CompleteLeadsService extends BaseService {
	
	private static Logger logger = Logger.getLogger(CompleteLeadsService.class);
	private LeadProcessingBO leadProcessingBO;
	private LeadManagementValidator leadManagementValidator;
	private LeadOFMapper leadOFMapper;
	private OFHelper ofHelper;
	


	public CompleteLeadsService() {
		super(PublicAPIConstant.COMPLETES_LEADS_REQUEST_XSD,
				PublicAPIConstant.COMPLETES_LEADS_RESPONSE_XSD,
				PublicAPIConstant.NEW_SERVICES_NAMESPACE,
				PublicAPIConstant.NEW_SERVICES_SCHEMA,
				PublicAPIConstant.COMPLETES_LEADS_RESPONSE_SCHEMALOCATION,
				CompleteLeadsRequest.class, CompleteLeadsResponse.class);
	}

	@Override
	public IAPIResponse execute(APIRequestVO apiVO) {
		CompleteLeadsRequest completeLeadsRequest = (CompleteLeadsRequest) apiVO
				.getRequestFromPostPut();
		CompleteLeadsResponse response = new CompleteLeadsResponse();
		LeadResponse leadResponse=null;
		// Invoke Validator to validate the request
		try {
			completeLeadsRequest = leadManagementValidator
					.validate(completeLeadsRequest);
			if (ResultsCode.SUCCESS != completeLeadsRequest.getValidationCode()) {
				return createErrorResponse(completeLeadsRequest
						.getValidationCode().getMessage(), completeLeadsRequest
						.getValidationCode().getCode());
			}
		   } catch (Exception e) {
			return createErrorResponse(
					ResultsCode.UNABLE_TO_PROCESS.getMessage(),
					ResultsCode.UNABLE_TO_PROCESS.getCode());
		  }
		  try{
			   leadResponse = processLeadOFRequest(completeLeadsRequest);
			 }
		  catch (Exception e) {
			   logger.info("Exception occurred in processLeadOFRequest of CompleteLeadsService: "+e.getMessage());
			   return createErrorResponse(
						ResultsCode.UNABLE_TO_PROCESS.getMessage(),
						ResultsCode.UNABLE_TO_PROCESS.getCode());
			}
		    if(null!= leadResponse && StringUtils.isNotBlank(leadResponse.getLeadId())){
		        response.setLeadId(completeLeadsRequest.getLeadId());
			    response.setFirmId(completeLeadsRequest.getFirmId());
			    response.setResults(Results.getSuccess(ResultsCode.COMPLETE_LEADS.getMessage()));
			    sendCompleteMailToCustomer(completeLeadsRequest);
		    }
            return response;
	}
	/** Description :Method which accepts ServiceLive lead id and executes JBPM transition
	 * @param completeLeadsRequest 
	 * @param leadId
	 * @return LeadResponse
	 */
	private LeadResponse processLeadOFRequest(CompleteLeadsRequest completeLeadsRequest) {
		LeadResponse response = new LeadResponse();	
		SecurityContext securityContext=null;
		//creating security context
		if(StringUtils.isNotBlank(completeLeadsRequest.getResourceId())){
		   securityContext = getSecurityContextForVendor(Integer.parseInt(completeLeadsRequest.getResourceId()));
		}
		try{
			OrderFulfillmentLeadRequest leadRequest = leadOFMapper.createCompleteLeadRequest(completeLeadsRequest,securityContext);
			response = ofHelper.runLeadFulfillmentProcess(completeLeadsRequest.getLeadId(), SignalType.COMPLETE_LEAD, leadRequest);
		}catch(Exception e){
			logger.info("Exception occurred in processLeadOFRequest of CompleteLeadsService: "+e.getMessage());
		}
		
		return response;
	}

	private CompleteLeadsResponse createErrorResponse(String message,
			String code) {
		CompleteLeadsResponse createResponse = new CompleteLeadsResponse();
		Results results = Results.getError(message, code);
		createResponse.setResults(results);
		return createResponse;
	}
    private void sendCompleteMailToCustomer(CompleteLeadsRequest completeLeadsRequest){
    	AlertTask alertTask = new AlertTask();	
    	Map<String, String> mailMap=new HashMap<String, String>();
    	mailMap.put("leadid", completeLeadsRequest.getLeadId());
    	mailMap.put("firmid",completeLeadsRequest.getFirmId());
    	mailMap.put("resourceid",completeLeadsRequest.getResourceId());
    	CompleteLeadsRequestVO completeLeadsRequestVO=null;
    	Map<String, Object> alertMap = new HashMap<String, Object>();
    	try{
    		completeLeadsRequestVO=leadProcessingBO.getCompleteMailDetails(mailMap);  
    		if(null!=completeLeadsRequestVO)
    		{
    			alertMap.put(NewServiceConstants.LEAD_REFERENCE, completeLeadsRequestVO.getLeadId());
    			alertMap.put(NewServiceConstants.SERVICE, completeLeadsRequestVO.getService());
    			if(null!=completeLeadsRequestVO.getSkill() &&completeLeadsRequestVO.getSkill().equalsIgnoreCase("REPAIR")){
    				alertMap.put(NewServiceConstants.SKILL,"Repair");
    				}else if(null!=completeLeadsRequestVO.getSkill() && completeLeadsRequestVO.getSkill().equalsIgnoreCase("DELIVERY")){
    				alertMap.put(NewServiceConstants.SKILL,"Delivery");
    				}else if(null!=completeLeadsRequestVO.getSkill() &&completeLeadsRequestVO.getSkill().equalsIgnoreCase("INSTALL")){
    				alertMap.put(NewServiceConstants.SKILL,"Install");
    				}else{
    				alertMap.put(NewServiceConstants.SKILL,completeLeadsRequestVO.getSkill());
    			    }
    			alertMap.put(NewServiceConstants.LEAD_FIRST_NAME, completeLeadsRequestVO.getCustomerFirstName());    			
    			String custName="";
    			custName=completeLeadsRequestVO.getCustomerFirstName();
    			if(null!=completeLeadsRequestVO.getCustomerLastName()&&StringUtils.isNotBlank(completeLeadsRequestVO.getCustomerLastName())){
    			alertMap.put(NewServiceConstants.CUSTOMER_NAME,custName+" "+completeLeadsRequestVO.getCustomerLastName());	
    			}
    			else{
    			alertMap.put(NewServiceConstants.CUSTOMER_NAME,custName);		
    			}
    			alertMap.put(NewServiceConstants.COMPANY_NAME, completeLeadsRequestVO.getFirmName());
    			alertMap.put(NewServiceConstants.RESOURCE_NAME, completeLeadsRequestVO.getProviderName());
    			alertMap.put(NewServiceConstants.RESOURCE_LAST_NAME, completeLeadsRequestVO.getProviderLastName());
    			String completedDate=leadProcessingBO.getFormattedDateAsString(completeLeadsRequest.getCompletedDate());
    			alertMap.put(NewServiceConstants.COMPLETED_DATE,completedDate);    
    			alertMap.put(NewServiceConstants.COMPLETED_TIME,completeLeadsRequest.getCompletedTime());
    			String address=completeLeadsRequestVO.getStreet1();
    			String street2=completeLeadsRequestVO.getStreet2();
    			if(StringUtils.isNotBlank(street2))
    				    {
    					address=address+","+street2;
    					}
    			alertMap.put(NewServiceConstants.ADDRESS,address);
				
		     alertMap.put(NewServiceConstants.CITY,completeLeadsRequestVO.getServiceCity());				
		     alertMap.put(NewServiceConstants.ZIP,completeLeadsRequestVO.getServiceZip());
		     alertMap.put(NewServiceConstants.STATE,completeLeadsRequestVO.getServiceState());
		     if(StringUtils.isNotBlank(completeLeadsRequestVO.getSWYRID())){
			 alertMap.put(NewServiceConstants.SWYR_MEMBERSHIP_ID,completeLeadsRequestVO.getSWYRID());
				   	}
			  else
					{
			 alertMap.put(NewServiceConstants.SWYR_MEMBERSHIP_ID,"Not Available");
					}
		      String templateInputValue = leadProcessingBO.createKeyValueStringFromMap(alertMap);
			  alertTask.setTemplateInputValue(templateInputValue);
			  alertTask.setTemplateId(NewServiceConstants.TEMPLATE_SEND_COMPLETE_MAIL);
			  alertTask.setAlertTo(completeLeadsRequestVO.getCustomerEmail());	
			  logger.info("Sending mail details to lead processingBO");
			  leadProcessingBO.sendToDestination(alertTask);
    		}
    		
    	}
    	catch (Exception e) {
    		logger.info("Exception occurred in sendCompleteMailToCustomer of CompleteLeadsService: "+e.getMessage());
		}
    }
	public LeadProcessingBO getLeadProcessingBO() {
		return leadProcessingBO;
	}

	public void setLeadProcessingBO(LeadProcessingBO leadProcessingBO) {
		this.leadProcessingBO = leadProcessingBO;
	}

	public LeadManagementValidator getLeadManagementValidator() {
		return leadManagementValidator;
	}

	public void setLeadManagementValidator(
			LeadManagementValidator leadManagementValidator) {
		this.leadManagementValidator = leadManagementValidator;
	}

	public LeadOFMapper getLeadOFMapper() {
		return leadOFMapper;
	}

	public void setLeadOFMapper(LeadOFMapper leadOFMapper) {
		this.leadOFMapper = leadOFMapper;
	}

	public OFHelper getOfHelper() {
		return ofHelper;
	}

	public void setOfHelper(OFHelper ofHelper) {
		this.ofHelper = ofHelper;
	}
}
