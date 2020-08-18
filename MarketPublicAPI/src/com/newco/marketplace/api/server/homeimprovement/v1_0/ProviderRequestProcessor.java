package com.newco.marketplace.api.server.homeimprovement.v1_0;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.hi.account.firm.approve.ApproveFirmsRequest;
import com.newco.marketplace.api.beans.hi.account.firm.approve.ApproveFirmsResponse;
import com.newco.marketplace.api.beans.hi.account.firm.approve.ApproveProvidersRequest;
import com.newco.marketplace.api.beans.hi.account.firm.approve.ApproveProvidersResponse;
import com.newco.marketplace.api.beans.hi.account.firm.create.v1_0.CreateFirmAccountRequest;
import com.newco.marketplace.api.beans.hi.account.firm.create.v1_0.CreateFirmAccountResponse;
import com.newco.marketplace.api.beans.hi.account.firm.addproviderskill.AddProviderSkillRequest;
import com.newco.marketplace.api.beans.hi.account.firm.addproviderskill.AddProviderSkillResponse;
import com.newco.marketplace.api.beans.hi.account.firm.removeproviderskill.RemoveProviderSkillRequest;
import com.newco.marketplace.api.beans.hi.account.firm.removeproviderskill.RemoveProviderSkillResponse;
import com.newco.marketplace.api.beans.hi.account.firm.update.v1_0.UpdateFirmAccountRequest;
import com.newco.marketplace.api.beans.hi.account.firm.update.v1_0.UpdateFirmAccountResponse;
import com.newco.marketplace.api.beans.hi.account.provider.create.ProviderRegistrationRequest;
import com.newco.marketplace.api.beans.hi.account.provider.create.ProviderRegistrationResponse;
import com.newco.marketplace.api.beans.hi.account.provider.update.UpdateProviderRegistrationRequest;
import com.newco.marketplace.api.beans.hi.account.provider.update.UpdateProviderRegistrationResponse;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.services.hi.ApproveFirmsService;
import com.newco.marketplace.api.services.hi.ApproveProviderService;
import com.newco.marketplace.api.services.hi.CreateFirmAccountService;
import com.newco.marketplace.api.services.hi.CreateHIProviderAccountService;
import com.newco.marketplace.api.services.hi.RemoveProviderSkillService;
import com.newco.marketplace.api.services.hi.UpdateFirmAccountService;
import com.newco.marketplace.api.services.hi.UpdateProviderRegistrationService;
import com.newco.marketplace.api.services.hi.AddProviderSkillService;
import com.newco.marketplace.interfaces.ProviderConstants;

@Path("v1.0")
public class ProviderRequestProcessor extends BaseRequestProcessor{

	@Resource
	protected HttpServletRequest httpRequest;
	@Resource
	protected HttpServletResponse httpResponse;
	private Logger logger = Logger.getLogger(ProviderRequestProcessor.class);
     
	protected UpdateFirmAccountService updateFirmAccountService;
	protected CreateFirmAccountService createFirmAccountService;
	//service reference for approving firm 
	protected ApproveFirmsService approveFirmService;
	//service reference for creating provider account.
	protected CreateHIProviderAccountService createHiProviderAccountService;
	
	//service reference for updating provider
	UpdateProviderRegistrationService updateProviderRegistrationService;
	//service reference for adding provider skills
	protected AddProviderSkillService addProviderSkillService;
	//service reference for removing provider skills
	protected RemoveProviderSkillService removeProviderSkillService;
	
	//service reference for approving provider
	protected ApproveProviderService approveProviderService;
	
	@POST
	@Path("/firm/providerFirmregister")
	@Consumes( { "application/xml", "application/json", "text/xml" })
	@Produces( { "application/xml", "application/json", "text/xml" })
	public CreateFirmAccountResponse  getResponseForCreateFirmAccount(CreateFirmAccountRequest createFirmAccountRequest) {
		logger.info("Entering MobileGenericRequestProcessor.getResponseForCreateFirmAccount()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		CreateFirmAccountResponse createFirmAccountResponse = new CreateFirmAccountResponse();
		String createFirmRequest = convertReqObjectToXMLString(createFirmAccountRequest, CreateFirmAccountRequest.class); 
		apiVO.setRequestFromPostPut(createFirmRequest);
		apiVO.setRequestType(RequestType.Post);		
		String responseXML = createFirmAccountService.doSubmit(apiVO);
		createFirmAccountResponse = (CreateFirmAccountResponse) convertXMLStringtoRespObject(responseXML, CreateFirmAccountResponse.class); 
		
		//Logging Request and response
		String apiLoggingSwitch=createFirmAccountService.apiLoggingSwitch();
		if(ProviderConstants.ALL.equalsIgnoreCase(apiLoggingSwitch)){
			String status = createFirmAccountService.logStatus(createFirmAccountResponse.getResults()); 
			createFirmAccountService.logAPIHistory(createFirmRequest,ProviderConstants.POST,ProviderConstants.CREATE_FIRM,apiVO,responseXML,ProviderConstants.ALL,status);  		
		}else if(ProviderConstants.ERROR_STRING.equalsIgnoreCase(apiLoggingSwitch)){
			String status = createFirmAccountService.logStatus(createFirmAccountResponse.getResults()); 
			if(ProviderConstants.ERROR.equalsIgnoreCase(status)){
				createFirmAccountService.logAPIHistory(createFirmRequest,ProviderConstants.POST,ProviderConstants.CREATE_FIRM,apiVO,responseXML,ProviderConstants.ERROR_STRING,status);  
			}	
		}			
		logger.info("Leaving MobileGenericRequestProcessor.getResponseForProviderLogin()");
		return createFirmAccountResponse;
	}

	
	/**
	 * V1.0 of Update Firm Account API
	 * @param updateFirmAccountRequest 
	 * @return updateFirmAccountResponse
	 */
	
	@PUT
	@Path("/updateFirm/firmId/{firmId}")
	@Consumes( { "application/xml", "application/json", "text/xml" })
	@Produces( { "application/xml", "application/json", "text/xml" })
	public UpdateFirmAccountResponse  getResponseForUpdateFirmAccount(@PathParam("firmId")
	String firmId,UpdateFirmAccountRequest updateFirmAccountRequest) {
		logger.info("Entering ProviderRequestProcessor.getResponseForUpdateFirmAccount()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		UpdateFirmAccountResponse updateFirmAccountResponse = new UpdateFirmAccountResponse();
		String updateFirmRequest = convertReqObjectToXMLString(updateFirmAccountRequest, UpdateFirmAccountRequest.class); 
		apiVO.setProviderFirmId(firmId);
		apiVO.setRequestFromPostPut(updateFirmRequest);
		apiVO.setRequestType(RequestType.Put);		
		String responseXML = updateFirmAccountService.doSubmit(apiVO);
		updateFirmAccountResponse = (UpdateFirmAccountResponse) convertXMLStringtoRespObject(responseXML, UpdateFirmAccountResponse.class); 
			
		//Logging Request and response
		String apiLoggingSwitch=updateFirmAccountService.apiLoggingSwitch();
		if(ProviderConstants.ALL.equalsIgnoreCase(apiLoggingSwitch)){
			String status = updateFirmAccountService.logStatus(updateFirmAccountResponse.getResults());
			updateFirmAccountService.logAPIHistory(updateFirmRequest,ProviderConstants.PUT,ProviderConstants.UPDATE_FIRM,apiVO,responseXML,ProviderConstants.ALL,status);  		
		}else if(ProviderConstants.ERROR_STRING.equalsIgnoreCase(apiLoggingSwitch)){
			String status = updateFirmAccountService.logStatus(updateFirmAccountResponse.getResults());
			if(ProviderConstants.ERROR.equalsIgnoreCase(status)){
				updateFirmAccountService.logAPIHistory(updateFirmRequest,ProviderConstants.PUT,ProviderConstants.UPDATE_FIRM,apiVO,responseXML,ProviderConstants.ERROR_STRING,status);  
			}	
		}		
		logger.info("Leaving ProviderRequestProcessor.getResponseForUpdateFirmAccount()");
		return updateFirmAccountResponse;
	}
	
	@POST
	@Path("firm/approveFirms")
	@Consumes( { "application/xml", "application/json", "text/xml" })
	@Produces( { "application/xml", "application/json", "text/xml" })
	public ApproveFirmsResponse  getResponseForApproveFirm(ApproveFirmsRequest approveFirmsRequest) {
		logger.info("Entering ProviderRequestProcessor.getResponseForApproveFirm()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		ApproveFirmsResponse approveFirmsResponse = new ApproveFirmsResponse();
		String approveFirmsRequestString = convertReqObjectToXMLString(approveFirmsRequest, ApproveFirmsRequest.class); 
		apiVO.setRequestFromPostPut(approveFirmsRequestString);
		apiVO.setRequestType(RequestType.Post);			
		String responseXML = approveFirmService.doSubmit(apiVO);
		approveFirmsResponse = (ApproveFirmsResponse) convertXMLStringtoRespObject(responseXML, ApproveFirmsResponse.class); 
		
		//Logging Request and response
		String apiLoggingSwitch=approveFirmService.apiLoggingSwitch();	
		if(ProviderConstants.ALL.equalsIgnoreCase(apiLoggingSwitch)){
			String status = approveFirmService.logStatus(approveFirmsResponse.getResults());
			approveFirmService.logAPIHistory(approveFirmsRequestString,ProviderConstants.POST,ProviderConstants.APPROVE_FIRM,apiVO,responseXML,ProviderConstants.ALL,status);  		
		}else if(ProviderConstants.ERROR_STRING.equalsIgnoreCase(apiLoggingSwitch)){
			String status = approveFirmService.logStatus(approveFirmsResponse.getResults());
			if(ProviderConstants.ERROR.equalsIgnoreCase(status)){
			approveFirmService.logAPIHistory(approveFirmsRequestString,ProviderConstants.POST,ProviderConstants.APPROVE_FIRM,apiVO,responseXML,ProviderConstants.ERROR_STRING,status);  
			}	
		}		
		logger.info("Leaving ProviderRequestProcessor.getResponseForApproveFirm()");
		return approveFirmsResponse;
	}
	@POST
	@Path("/provider/createProvider")
	@Consumes( { "application/xml", "application/json", "text/xml" })
	@Produces( { "application/xml", "application/json", "text/xml" })
	public ProviderRegistrationResponse  getResponseForCreateProviderAccount(ProviderRegistrationRequest providerRegistrationRequest ) {
		logger.info("Entering MobileGenericRequestProcessor.getResponseForCreateProviderAccount()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		ProviderRegistrationResponse providerRegistrationResponse = new ProviderRegistrationResponse();
		String createProviderRequest = convertReqObjectToXMLString(providerRegistrationRequest, ProviderRegistrationRequest.class); 
		apiVO.setRequestFromPostPut(createProviderRequest);
		apiVO.setRequestType(RequestType.Post);
		String apiLoggingSwitch=createHiProviderAccountService.apiLoggingSwitch();
		String responseXML = createHiProviderAccountService.doSubmit(apiVO);
		providerRegistrationResponse = (ProviderRegistrationResponse) convertXMLStringtoRespObject(responseXML, ProviderRegistrationResponse.class); 
		//Logging Response
		if(ProviderConstants.ALL.equals(apiLoggingSwitch)){
			String status = createHiProviderAccountService.logStatus(providerRegistrationResponse.getResults()); 
			createHiProviderAccountService.logAPIHistory(createProviderRequest,ProviderConstants.POST,ProviderConstants.CREATE_PROVIDER,apiVO,responseXML,ProviderConstants.ALL,status);  		
		}else if(ProviderConstants.ERROR_STRING.equals(apiLoggingSwitch)){
			String status = createHiProviderAccountService.logStatus(providerRegistrationResponse.getResults()); 
			if(ProviderConstants.ERROR.equals(status)){
				createHiProviderAccountService.logAPIHistory(createProviderRequest,ProviderConstants.POST,ProviderConstants.CREATE_PROVIDER,apiVO,responseXML,ProviderConstants.ERROR_STRING,status);  
			}	
		}
		logger.info("Leaving ProviderRequestProcessor.getResponseForCreateProviderAccount()");
		return providerRegistrationResponse;
	}
	
	
	/**
	 * V1.0 of Update Provider Registration API
	 * @param updateProviderRegistrationRequest 
	 * @return updateProviderRegistrationResponse
	 */
	
	@PUT
	@Path("/providers/{providerId}/updateProvider")
	@Consumes( { "application/xml", "application/json", "text/xml" })
	@Produces( { "application/xml", "application/json", "text/xml" })
	public UpdateProviderRegistrationResponse  getResponseForUpdateProviderRegistration(@PathParam("providerId")
	String providerId,UpdateProviderRegistrationRequest updateProviderRegistrationRequest) {
		logger.info("Entering ProviderRequestProcessor.getResponseForUpdateProviderRegistration()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		UpdateProviderRegistrationResponse updateProviderRegistrationResponse = new UpdateProviderRegistrationResponse();
		String updateProviderRequest = convertReqObjectToXMLString(updateProviderRegistrationRequest, UpdateProviderRegistrationRequest.class); 
		Integer proId = checkProviderId(providerId);
		apiVO.setProviderResourceId(proId);
		apiVO.setRequestFromPostPut(updateProviderRequest);
		apiVO.setRequestType(RequestType.Put);
		String apiLoggingSwitch=updateProviderRegistrationService.apiLoggingSwitch();
		String responseXML = updateProviderRegistrationService.doSubmit(apiVO);
		updateProviderRegistrationResponse = (UpdateProviderRegistrationResponse) convertXMLStringtoRespObject(responseXML, UpdateProviderRegistrationResponse.class); 
		//Logging Response
		if(ProviderConstants.ALL.equals(apiLoggingSwitch)){
			String status = updateProviderRegistrationService.logStatus(updateProviderRegistrationResponse.getResults()); 
			updateProviderRegistrationService.logAPIHistory(updateProviderRequest,ProviderConstants.PUT,ProviderConstants.UPDATE_PROVIDER,apiVO,responseXML,ProviderConstants.ALL,status);  		
			}else if(ProviderConstants.ERROR_STRING.equals(apiLoggingSwitch)){
			String status = updateProviderRegistrationService.logStatus(updateProviderRegistrationResponse.getResults()); 
			if(ProviderConstants.ERROR.equals(status)){
				createHiProviderAccountService.logAPIHistory(updateProviderRequest,ProviderConstants.PUT,ProviderConstants.UPDATE_PROVIDER,apiVO,responseXML,ProviderConstants.ERROR_STRING,status);    
				}	
		}
		
		logger.info("Leaving ProviderRequestProcessor.getResponseForUpdateProviderRegistration()");
		return updateProviderRegistrationResponse;
	}


	private Integer checkProviderId(String providerId) {
		Integer proId = null;
		try {
			if (null!=providerId) {
				proId =Integer.parseInt(providerId);
			}
		} catch (NumberFormatException e) {
			proId= -1;
		}catch (Exception e) {
			proId= -1;
		}
		return proId;
	}
	
	@POST
	@Path("/approveProviders")
	@Consumes( { "application/xml", "application/json", "text/xml" })
	@Produces( { "application/xml", "application/json", "text/xml" })
	public ApproveProvidersResponse  getResponseForApproveProvider(ApproveProvidersRequest approveProviderRequest) {
		logger.info("Entering ProviderRequestProcessor.getResponseForApproveProvider()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		ApproveProvidersResponse approveProviderResponse = new ApproveProvidersResponse();
		String approveProviderRequestString = convertReqObjectToXMLString(approveProviderRequest, ApproveProvidersRequest.class); 
		apiVO.setRequestFromPostPut(approveProviderRequestString);
		apiVO.setRequestType(RequestType.Post);
		//Logging Request
		//Integer logginId = approveProviderService.logAPIHistory(approveProviderRequestString,ProviderConstants.POST,ProviderConstants.APPROVE_PROVIDER,apiVO);
		
		String apiLoggingSwitch=approveProviderService.apiLoggingSwitch();

		
		String responseXML = approveProviderService.doSubmit(apiVO);
		approveProviderResponse = (ApproveProvidersResponse) convertXMLStringtoRespObject(responseXML, ApproveProvidersResponse.class); 
		//Logging Response
		
		if(ProviderConstants.ALL.equals(apiLoggingSwitch)){
			String status = approveProviderService.logStatus(approveProviderResponse.getResults());
			approveProviderService.logAPIHistory(approveProviderRequestString,ProviderConstants.POST,ProviderConstants.APPROVE_PROVIDER,apiVO,responseXML,ProviderConstants.ALL,status);  		
		}else if(ProviderConstants.ERROR_STRING.equals(apiLoggingSwitch)){
			String status = approveProviderService.logStatus(approveProviderResponse.getResults());
			if(ProviderConstants.ERROR.equals(status)){
			approveProviderService.logAPIHistory(approveProviderRequestString,ProviderConstants.POST,ProviderConstants.APPROVE_PROVIDER,apiVO,responseXML,ProviderConstants.ERROR_STRING,status);  
			}	
		}
		
		
		logger.info("Leaving ProviderRequestProcessor.getResponseForApproveProvider()");
		return approveProviderResponse;
	}
	
	/**
	 * @param addProviderSkillRequest
	 * @return addProviderSkillResponse
	 */
	@POST
	@Path("/addProviderSkills")
	@Consumes( { "application/xml", "application/json", "text/xml" })
	@Produces( { "application/xml", "application/json", "text/xml" })
	public AddProviderSkillResponse getResponseForAddProviderSkill(AddProviderSkillRequest addProviderSkillRequest){
		
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		AddProviderSkillResponse addProviderSkillResponse=new AddProviderSkillResponse();
		String addSkillRequest=convertReqObjectToXMLString(addProviderSkillRequest,AddProviderSkillRequest.class); 
		apiVO.setRequestFromPostPut(addSkillRequest);
		apiVO.setRequestType(RequestType.Post);
		String apiLoggingSwitch=addProviderSkillService.apiLoggingSwitch();
		String responseXML = addProviderSkillService.doSubmit(apiVO);
		addProviderSkillResponse=(AddProviderSkillResponse) convertXMLStringtoRespObject(responseXML, AddProviderSkillResponse.class);
		//Logging Response
		
		if(ProviderConstants.ALL.equals(apiLoggingSwitch)){
			String status = addProviderSkillService.logStatus(addProviderSkillResponse.getResults());
			addProviderSkillService.logAPIHistory(addSkillRequest,ProviderConstants.POST,ProviderConstants.ADD_PROVIDER_SKILL,apiVO,responseXML,ProviderConstants.ALL,status);  		
		}else if(ProviderConstants.ERROR_STRING.equals(apiLoggingSwitch)){
			String status = addProviderSkillService.logStatus(addProviderSkillResponse.getResults());
			if(ProviderConstants.ERROR.equals(status)){
				addProviderSkillService.logAPIHistory(addSkillRequest,ProviderConstants.POST,ProviderConstants.ADD_PROVIDER_SKILL,apiVO,responseXML,ProviderConstants.ERROR_STRING,status);  
			}	
		}
		return addProviderSkillResponse;
	}
	
	/**
	 * 
	 * @param removeProviderSkillRequest
	 * @return removeProviderSkillResponse
	 */
	@POST
	@Path("/removeProviderSkills")
	@Consumes( { "application/xml", "application/json", "text/xml" })
	@Produces( { "application/xml", "application/json", "text/xml" })
	public RemoveProviderSkillResponse getResponseForRemoveProviderSkill(RemoveProviderSkillRequest removeProviderSkillRequest){
	
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		RemoveProviderSkillResponse removeProviderSkillResponse=new RemoveProviderSkillResponse();
		String removeSkillRequest=convertReqObjectToXMLString(removeProviderSkillRequest,RemoveProviderSkillRequest.class); 
		apiVO.setRequestFromPostPut(removeSkillRequest);
		apiVO.setRequestType(RequestType.Post);
		String apiLoggingSwitch=removeProviderSkillService.apiLoggingSwitch();
		String responseXML = removeProviderSkillService.doSubmit(apiVO);
		removeProviderSkillResponse=(RemoveProviderSkillResponse) convertXMLStringtoRespObject(responseXML, RemoveProviderSkillResponse.class);
		//Logging Response
		
		if(ProviderConstants.ALL.equals(apiLoggingSwitch)){
			String status = removeProviderSkillService.logStatus(removeProviderSkillResponse.getResults());
			removeProviderSkillService.logAPIHistory(removeSkillRequest,ProviderConstants.POST,ProviderConstants.REMOVE_PROVIDER_SKILL,apiVO,responseXML,ProviderConstants.ALL,status);  		
		}else if(ProviderConstants.ERROR_STRING.equals(apiLoggingSwitch)){
			String status = removeProviderSkillService.logStatus(removeProviderSkillResponse.getResults());
			if(ProviderConstants.ERROR.equals(status)){
				removeProviderSkillService.logAPIHistory(removeSkillRequest,ProviderConstants.POST,ProviderConstants.REMOVE_PROVIDER_SKILL,apiVO,responseXML,ProviderConstants.ERROR_STRING,status);  
			}	
		}
		return removeProviderSkillResponse;
	}
	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

	public HttpServletResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HttpServletResponse httpResponse) {
		this.httpResponse = httpResponse;
	}

	public UpdateFirmAccountService getUpdateFirmAccountService() {
		return updateFirmAccountService;
	}

	public void setUpdateFirmAccountService(
			UpdateFirmAccountService updateFirmAccountService) {
		this.updateFirmAccountService = updateFirmAccountService;
	}


	public CreateFirmAccountService getCreateFirmAccountService() {
		return createFirmAccountService;
	}

	public void setCreateFirmAccountService(
			CreateFirmAccountService createFirmAccountService) {
		this.createFirmAccountService = createFirmAccountService;
	}

	public ApproveFirmsService getApproveFirmService() {
		return approveFirmService;
	}

	public void setApproveFirmService(ApproveFirmsService approveFirmService) {
		this.approveFirmService = approveFirmService;
	}


	public CreateHIProviderAccountService getCreateHiProviderAccountService() {
		return createHiProviderAccountService;
	}


	public void setCreateHiProviderAccountService(
			CreateHIProviderAccountService createHiProviderAccountService) {
		this.createHiProviderAccountService = createHiProviderAccountService;
	}


	public UpdateProviderRegistrationService getUpdateProviderRegistrationService() {
		return updateProviderRegistrationService;
	}


	public void setUpdateProviderRegistrationService(
			UpdateProviderRegistrationService updateProviderRegistrationService) {
		this.updateProviderRegistrationService = updateProviderRegistrationService;
	}
	
	public ApproveProviderService getApproveProviderService() {
		return approveProviderService;
	}


	public void setApproveProviderService(
			ApproveProviderService approveProviderService) {
		this.approveProviderService = approveProviderService;
	}
	
	
	
	public AddProviderSkillService getAddProviderSkillService() {
		return addProviderSkillService;
	}

	public void setAddProviderSkillService(
			AddProviderSkillService addProviderSkillService) {
		this.addProviderSkillService = addProviderSkillService;
	}
	
	public RemoveProviderSkillService getRemoveProviderSkillService() {
		return removeProviderSkillService;
	}


	public void setRemoveProviderSkillService(
			RemoveProviderSkillService removeProviderSkillService) {
		this.removeProviderSkillService = removeProviderSkillService;
	}
	
 }
