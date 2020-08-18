package com.newco.marketplace.api.mobile.processor;

import java.io.File;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.GenericAPIResponse;
import com.newco.marketplace.api.mobile.beans.DownLoadResponse;
import com.newco.marketplace.api.mobile.beans.DownloadErrorResult;
import com.newco.marketplace.api.mobile.beans.GetEstimateDetailsResponse;
import com.newco.marketplace.api.mobile.beans.GetProviderSOListResponse;
import com.newco.marketplace.api.mobile.beans.MobileTimeOnSiteRequest;
import com.newco.marketplace.api.mobile.beans.MobileTimeOnSiteResponse;
import com.newco.marketplace.api.mobile.beans.addNotes.AddNoteRequest;
import com.newco.marketplace.api.mobile.beans.addNotes.AddNoteResponse;
import com.newco.marketplace.api.mobile.beans.authenticateUser.LoginProviderRequest;
import com.newco.marketplace.api.mobile.beans.authenticateUser.LoginProviderResponse;
import com.newco.marketplace.api.mobile.beans.createNewPassword.CreateNewPasswordRequest;
import com.newco.marketplace.api.mobile.beans.createNewPassword.CreateNewPasswordResponse;
import com.newco.marketplace.api.mobile.beans.depositioncode.GetDispositionCodeResponse;
import com.newco.marketplace.api.mobile.beans.getProviderProfile.ProviderProfileResponse;
import com.newco.marketplace.api.mobile.beans.getProviderProfileImage.ProviderProfileImageResponse;
import com.newco.marketplace.api.mobile.beans.provider.calendarEvent.AddCalendarProviderEventRequest;
import com.newco.marketplace.api.mobile.beans.provider.calendarEvent.AddCalendarProviderEventResponse;
import com.newco.marketplace.api.mobile.beans.provider.calendarEvent.DeleteCalendarProviderEventResponse;
import com.newco.marketplace.api.mobile.beans.provider.calendarEvent.GetCalendarProviderEventListResponse;
import com.newco.marketplace.api.mobile.beans.provider.calendarEvent.UpdateCalendarProviderEventRequest;
import com.newco.marketplace.api.mobile.beans.provider.calendarEvent.UpdateCalendarProviderEventResponse;
import com.newco.marketplace.api.mobile.beans.so.addEstimate.AddEstimateRequest;
import com.newco.marketplace.api.mobile.beans.so.addEstimate.AddEstimateResponse;
import com.newco.marketplace.api.mobile.beans.so.placeBid.MobilePlaceBidRequest;
import com.newco.marketplace.api.mobile.beans.so.placeBid.MobilePlaceBidResponse;
import com.newco.marketplace.api.mobile.beans.soDetails.v3_0.SOGetMobileDetailsResponse;
import com.newco.marketplace.api.mobile.beans.sodetails.SOGetMobileResponse;
import com.newco.marketplace.api.mobile.beans.updateApptTime.SOUpdateAppointmentTimeRequest;
import com.newco.marketplace.api.mobile.beans.updateApptTime.SOUpdateAppointmentTimeResponse;
import com.newco.marketplace.api.mobile.beans.updateApptTime.UpdateProviderProfileDetailsRequest;
import com.newco.marketplace.api.mobile.beans.uploadDocument.FileData;
import com.newco.marketplace.api.mobile.beans.uploadDocument.ProviderUploadDocumentResponse;
import com.newco.marketplace.api.mobile.beans.uploadProviderProfileImage.ProviderProfileImageUploadResponse;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.service.BaseService.RequestType;
import com.newco.marketplace.api.mobile.services.AddCalendarEventService;
import com.newco.marketplace.api.mobile.services.AddEstimateService;
import com.newco.marketplace.api.mobile.services.CreateNewPasswordForUserService;
import com.newco.marketplace.api.mobile.services.DeleteCalendarEventService;
import com.newco.marketplace.api.mobile.services.DocumentDownloadService;
import com.newco.marketplace.api.mobile.services.GetDispositionCodesService;
import com.newco.marketplace.api.mobile.services.GetEstimateDetailsService;
import com.newco.marketplace.api.mobile.services.GetProviderSOListService;
import com.newco.marketplace.api.mobile.services.LoginProviderService;
import com.newco.marketplace.api.mobile.services.MobilePlaceBidService;
import com.newco.marketplace.api.mobile.services.MobileTimeOnSiteService;
import com.newco.marketplace.api.mobile.services.ProviderAddNoteService;
import com.newco.marketplace.api.mobile.services.ProviderCalendarEventService;
import com.newco.marketplace.api.mobile.services.ProviderProfileImageService;
import com.newco.marketplace.api.mobile.services.ProviderProfileImageUploadService;
import com.newco.marketplace.api.mobile.services.ProviderProfileService;
import com.newco.marketplace.api.mobile.services.ProviderUploadDocumentService;
import com.newco.marketplace.api.mobile.services.SODetailsRetrieveService;
import com.newco.marketplace.api.mobile.services.SOUpdateAppointmentTimeService;
import com.newco.marketplace.api.mobile.services.UpdateCalendarEventService;
import com.newco.marketplace.api.mobile.services.UpdateProviderProfileService;
import com.newco.marketplace.api.mobile.services.UpdateSOCompletionService;
import com.newco.marketplace.api.server.MobileBaseRequestProcessor;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.utils.CryptoUtil;

@Path("v1.0")
public class MobileGenericRequestProcessor extends MobileBaseRequestProcessor{
	
	protected LoginProviderService loginProviderService;
	protected ProviderUploadDocumentService providerUploadDocumentService;
	protected CreateNewPasswordForUserService createNewPasswordForUserService;
	@Resource
	protected HttpServletRequest httpRequest;
	@Resource
	protected HttpServletResponse httpResponse;
	private Logger logger = Logger.getLogger(MobileGenericRequestProcessor.class);
     
	// service reference for Time on Site
	protected MobileTimeOnSiteService timeOnSiteService;
	protected SOUpdateAppointmentTimeService updateAppointmentTimeService;
	protected SODetailsRetrieveService soDetailsRetrieveService;
	protected DocumentDownloadService soDocDownloadService;
	protected GetProviderSOListService getProviderSOListService;
	protected ProviderAddNoteService providerAddNoteService;
	protected UpdateSOCompletionService updateSOCompletionService;
	// Service Reference for Adding Estimate for the service Order
	protected AddEstimateService addEstimateService;	
	//B2C: Service reference for fetching the estimate details of an SO
	protected GetEstimateDetailsService getEstimateService;
	// service reference for Place bid and Change bid APIs
	protected MobilePlaceBidService mobilePlaceBidService;
	// service reference to retrieve provider profile details
	protected ProviderProfileService providerProfileService;
	// service reference to update provider profile details
	protected UpdateProviderProfileService updateProviderProfileService;
	// service reference to retrieve provider profile image
	protected ProviderProfileImageService providerProfileImageService;
	// service reference to retrieve provider profile image
	protected ProviderProfileImageUploadService providerProfileImageUploadService;
	
	// Service Reference for Adding the provider calendar Events
	protected AddCalendarEventService addCalendarEventService;
	
	// Service Reference for Updating the provider calendar Events
	protected UpdateCalendarEventService updateCalendarEventService;
	
	// service reference for Fetching the  provider calendar Events
	protected ProviderCalendarEventService providerCalendarEventService;
	
	// service reference for removing the  provider calendar Events
		protected DeleteCalendarEventService deleteCalendarEventService;
		
	protected GetDispositionCodesService getDispositionCodesService;
	
	@POST
	@Path("/provider/authenticate")
	@Consumes( { "application/xml", "application/json", "text/xml" })
	@Produces( { "application/xml", "application/json", "text/xml" })
	public LoginProviderResponse getResponseForProviderLogin(
			LoginProviderRequest loginProviderRequest) {
		logger
				.info("Entering MobileGenericRequestProcessor.getResponseForProviderLogin()");
		Map<String, String> requestMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		LoginProviderResponse loginProviderResponse = new LoginProviderResponse();
		
		// Encrypting password before passing it down stream.
		loginProviderRequest.setPassword(CryptoUtil.generateHash(loginProviderRequest.getPassword()));
		
		String loginRequest = convertReqObjectToXMLString(
				loginProviderRequest, LoginProviderRequest.class); 
		// log the request
		// get resource Id from vendor_resource
		//R11.2 SL-19704
		if(StringUtils.isNotBlank(loginProviderRequest.getUsername())){
			loginProviderRequest.setUsername(loginProviderRequest.getUsername().trim());
		}
		Integer resourceId=loginProviderService.getResourceId(loginProviderRequest.getUsername());
		Integer loggingId=loginProviderService.logSOMobileHistory(loginRequest,PublicMobileAPIConstant.AUTHENTICATE_USER, resourceId,null,PublicMobileAPIConstant.HTTP_POST);
		
		apiVO.setRequestFromPostPut(loginRequest);
		apiVO.setRequestType(RequestType.Post);
		apiVO.addProperties(PublicAPIConstant.REQUEST_MAP, requestMap);
		String responseXML = loginProviderService.doSubmit(apiVO);
		loginProviderResponse = (LoginProviderResponse) convertXMLStringtoRespObject(
				responseXML, LoginProviderResponse.class); 
		logger
				.info("Leaving MobileGenericRequestProcessor.getResponseForProviderLogin()");
		// update response 
		loginProviderService.updateSOMobileResponse(loggingId, responseXML);
		
		return loginProviderResponse;
	}
	
	//code for document upload using multi-part form data
	@POST
    @Path("/provider/{providerId}/serviceorder/{SOID}/uploadDocument")
    @Consumes("multipart/form-data")
    @Produces( { "application/xml", "application/json", "text/xml" })
    public ProviderUploadDocumentResponse getResponseForProviderUploadDocument(
    		MultipartBody  request, @PathParam("SOID") String soId,
    		@PathParam("providerId") String providerId) {

		logger.info("Entering MobileGenericRequestProcessor.getResponseForProviderLogin()");
		ProviderUploadDocumentResponse uploadDocumentResponse=new ProviderUploadDocumentResponse();
		// log the request
		FileData fileData=new FileData();
		Integer resourceId=loginProviderService.validateResourceId(providerId);
		fileData=loginProviderService.getBytesFromFile(request);
		Integer loggingId=loginProviderService.logSOMobileHistory(fileData.getByteString(),PublicMobileAPIConstant.UPLOAD_DOCUMENT, resourceId,soId,PublicMobileAPIConstant.HTTP_POST);

		uploadDocumentResponse = providerUploadDocumentService.execute(request, soId, providerId,fileData.getBytes());
		logger.info("Leaving MobileGenericRequestProcessor.getResponseForProviderLogin()");
		// update response 
		String responseXml = convertReqObjectToXMLString(
				uploadDocumentResponse, ProviderUploadDocumentResponse.class);
	  loginProviderService.updateSOMobileResponse(loggingId, responseXml);
			return uploadDocumentResponse;
  
	}		

	/**
	 * This method returns a response for a Time On Site request. The request
	 * processor delegates the processing to the timeOnSiteService class - This
	 * is in case of Provider *
	 * 
	 */
	@POST
	@Path("/provider/{providerId}/serviceorder/{SOID}/timeonsite")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public MobileTimeOnSiteResponse addTimeOnSite(
			@PathParam("providerId") String providerId,
			@PathParam("SOID") String soId,
			MobileTimeOnSiteRequest timeOnSiteRequest) {
		if (logger.isInfoEnabled()) {
			logger.info("Entering MobileGenericRequestProcessor.addTimeOnSite");
		}
		long addTimeOnSiteStart = System.currentTimeMillis();		
		// declare the response variables
		String timeOnSiteResponse = null;
		MobileTimeOnSiteResponse mTimeOnSiteResponse = new MobileTimeOnSiteResponse();

		// convert the request object to XML string
		// uses XStream toXML() method
		String request = convertReqObjectToXMLString(timeOnSiteRequest,
				MobileTimeOnSiteRequest.class);

		// create the API request VO object for passing to doSubmit()
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());

		// assign soId from the request URL
		apiVO.setSOId(soId);
		if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}

		// assign the XML string
		apiVO.setRequestFromPostPut(request);
		// assign the Request method as POST
		apiVO.setRequestType(RequestType.Post);

		// log the request
		// get resource Id from vendor_resource
		Integer resourceId=timeOnSiteService.validateResourceId(providerId);
		Integer loggingId=timeOnSiteService.logSOMobileHistory(request,PublicMobileAPIConstant.TIME_ON_SITE, resourceId,soId,PublicMobileAPIConstant.HTTP_POST);

		// call the doSubmit method of the SOBaseService
		// this call would call the execute method of AdvancedTimeOnSiteService
		timeOnSiteResponse = timeOnSiteService.doSubmit(apiVO);

		// convert the XML string to object
		mTimeOnSiteResponse = (MobileTimeOnSiteResponse) convertXMLStringtoRespObject(
				timeOnSiteResponse, MobileTimeOnSiteResponse.class);

		mTimeOnSiteResponse.setSoId(soId);

		if (logger.isInfoEnabled()) {
			logger.info("Leaving MobileGenericRequestProcessor.addTimeOnSite");
		}
		// update response 
		timeOnSiteService.updateSOMobileResponse(loggingId, timeOnSiteResponse);
		long addTimeOnSiteEnd = System.currentTimeMillis();
		logger.info("Total time taken for timeonsite after logging in ms :::"+(addTimeOnSiteEnd-addTimeOnSiteStart));
		return mTimeOnSiteResponse;
	}

	@POST
	@Path("/provider/{provider_id}/serviceorder/{so_Id}/appointmentTime")
	@Consumes({ "application/xml", "application/json","text/xml"})
	@Produces({ "application/xml", "application/json","text/xml"})
	public SOUpdateAppointmentTimeResponse getResponseForUpdateAppointmentTime (@PathParam("provider_id") String resourceId, @PathParam("so_Id")
	String soId, SOUpdateAppointmentTimeRequest updateAppointmentTimeRequest) {
			logger.info("Entering MobileGenericRequestProcessor.getResponseForUpdateAppointmentTime()");
		String updateAppointmentTimeResponse = null;
		SOUpdateAppointmentTimeResponse soEditAppointmentTimeResponse = new SOUpdateAppointmentTimeResponse();
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		String request = convertReqObjectToXMLString(updateAppointmentTimeRequest,SOUpdateAppointmentTimeRequest.class);
		apiVO.setRequestFromPostPut(request);
		if(StringUtils.isNumeric(resourceId)){
			apiVO.setProviderResourceId(Integer.parseInt(resourceId));
		}else{
			apiVO.setProviderResourceId(0);	
		}
		
		apiVO.setSOId(soId);
		apiVO.setRequestType(RequestType.Post);
		
		// the indicator is set to exclude Add Note from the SOLevel validation in Base Service, the specific validation for will be handled in respective service class
		apiVO.setAPISOValidationExcludeInd(true);
		// log the request
		Integer providerId=updateAppointmentTimeService.validateResourceId(resourceId);
		Integer loggingId=updateAppointmentTimeService.logSOMobileHistory(request,PublicMobileAPIConstant.UPDATE_APPOINTMENT_TIME, providerId,soId,PublicMobileAPIConstant.HTTP_POST);

		updateAppointmentTimeResponse = updateAppointmentTimeService.doSubmit(apiVO);
		//updateAppointmentTimeResponse = PublicAPIConstant.XML_VERSION + updateAppointmentTimeResponse;
			logger.info("Leaving MobileGenericRequestProcessor.getResponseForUpdateAppointmentTime()");
			soEditAppointmentTimeResponse = (SOUpdateAppointmentTimeResponse) convertXMLStringtoRespObject(updateAppointmentTimeResponse,SOUpdateAppointmentTimeResponse.class);
		// update response 
			updateAppointmentTimeService.updateSOMobileResponse(loggingId, updateAppointmentTimeResponse);	
			return soEditAppointmentTimeResponse;
	}
	
	
	/**
	 * This method returns a response for the Adding note to ServiceOrder
	 * @param soId
	 * @param addNoteRequest
	 * @return addNoteResponse
	 */
	@POST
	@Path("/provider/{providerId}/serviceorder/{soId}/notes")
	@Consumes({ "application/xml", "application/json","text/xml"})
	@Produces({ "application/xml", "application/json","text/xml"})
	public AddNoteResponse getResponseForAddNote(@PathParam("providerId") String providerId,@PathParam("soId")
	String soId,AddNoteRequest addNoteRequest) {
		if (logger.isInfoEnabled())
			logger.info("Entering MobileGenericRequestProcessor."+ "getResponseForAddNote()");
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		AddNoteResponse addNoteResponse = new AddNoteResponse();
		String request=convertReqObjectToXMLString(addNoteRequest,AddNoteRequest.class);
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Post);
		if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		apiVO.setSOId(soId);
		// the indicator is set to exclude Add Note from the SOLevel validation in Base Service, the specific validation for Add Note API will be handled in respective service class
		apiVO.setAPISOValidationExcludeInd(true);

		// log the request
		Integer resourceId=providerAddNoteService.validateResourceId(providerId);
		Integer loggingId=providerAddNoteService.logSOMobileHistory(request,PublicMobileAPIConstant.ADD_NOTES, resourceId,soId,PublicMobileAPIConstant.HTTP_POST);

		response = providerAddNoteService.doSubmit(apiVO);
		addNoteResponse=(AddNoteResponse)convertXMLStringtoRespObject(response,AddNoteResponse.class);
		if (logger.isInfoEnabled())
			logger.info("Leaving MobileGenericRequestProcessor."+ "getResponseForProviderAddNote()");
		 //update response 
		providerAddNoteService.updateSOMobileResponse(loggingId, response);

		return addNoteResponse;
		
	}
	


	/**
	 * This method returns a response for a get / retrieve service order
	 * request.
	 * 
	 * @param String
	 *            buyerResourceId, String soId
	 * @return Response
	 */
	@SuppressWarnings("unchecked")
	@GET 
	@Path("/provider/{providerId}/serviceorders/{so_id}")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public SOGetMobileResponse getResponseForSoRetrieve(@PathParam("providerId") String resourceId, @PathParam("so_id")
	String soId) {
		if (logger.isInfoEnabled())
			logger.info("getResponseForProviderRetrieve method started");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest()
				.getParameterMap());
		apiVO.setRequestType(BaseService.RequestType.Get);
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setResourceId(resourceId);
		if(StringUtils.isNumeric(resourceId)){
			apiVO.setProviderResourceId(Integer.parseInt(resourceId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		apiVO.setSOId(soId);
		
		// log the request
		Integer providerId=soDetailsRetrieveService.validateResourceId(resourceId);
		Integer loggingId=soDetailsRetrieveService.logSOMobileHistory(reqMap.toString(),PublicMobileAPIConstant.GET_SO_DETAILS_PRO, providerId,soId,PublicMobileAPIConstant.HTTP_GET);
		String responseXML = soDetailsRetrieveService.doSubmit(apiVO);
		// update response 
		soDetailsRetrieveService.updateSOMobileResponse(loggingId, responseXML);
		return (SOGetMobileResponse) convertXMLStringtoRespObject(responseXML,
				SOGetMobileResponse.class);

	}
	
	
	@GET
	@Path("/provider/{providerId}/getProviderSOList")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public GetProviderSOListResponse getProviderSOList(
			
			@PathParam("providerId") String providerId) {
		if (logger.isInfoEnabled())
			logger.info("Entering MobileGenericRequestProcessor--> getProviderSOList()");
		GetProviderSOListResponse getProviderSOListResponse = new GetProviderSOListResponse();
		
		@SuppressWarnings("unchecked")
		Map<String, String> reqMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		//apiVO.setProviderId(providerId);
		
		if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setRequestType(BaseService.RequestType.Get);
		
		// log the request
		Integer resourceId=getProviderSOListService.validateResourceId(providerId);
		Integer loggingId=getProviderSOListService.logSOMobileHistory(reqMap.toString(),PublicMobileAPIConstant.GET_PROVIDER_SO_LIST,resourceId,null,PublicMobileAPIConstant.HTTP_GET);
		
		String responseXML = getProviderSOListService.doSubmit(apiVO);
		getProviderSOListResponse = (GetProviderSOListResponse) convertXMLStringtoRespObject(
				responseXML, GetProviderSOListResponse.class);
		if (logger.isInfoEnabled())
			logger.info("Leaving MobileGenericRequestProcessor--> getProviderSOList()");
		
		// update response 
		getProviderSOListService.updateSOMobileResponse(loggingId, responseXML);
		return getProviderSOListResponse;

	}
	 
	@GET
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	@Path("/provider/{providerId}/serviceorder/{soId}/downloadDocument/{documentId}")
	public Response getSODocuments(@PathParam("providerId") String providerId,@PathParam("soId") String soId,@PathParam("documentId") String documentId) {
		if (logger.isInfoEnabled()){
			logger.info("Entering getSODocuments--> getSODocuments()");
		}
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setRequestType(BaseService.RequestType.Get);
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setProviderId(providerId);
		apiVO.setSOId(soId);
		apiVO.setDocumentId(documentId);
		// log the request
		// get resource Id from vendor_resource
		Integer resourceId=loginProviderService.validateResourceId(providerId);
		Integer loggingId=loginProviderService.logSOMobileHistory(reqMap.toString(),PublicMobileAPIConstant.DOWNLOAD_DOCUMENT, resourceId,soId,PublicMobileAPIConstant.HTTP_GET);

		
		DownloadErrorResult result=soDocDownloadService.execute(apiVO);
		if (null != result && StringUtils.isNotBlank(result.getFilePath())) {
				File file = new File(result.getFilePath());
				MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
				String mimeType = mimeTypesMap.getContentType(file);
				ResponseBuilder response = Response.ok((Object) file);
				String fileName=file.getName().substring(file.getName().indexOf("_")+1,file.getName().length());
				response.header("Content-disposition", "attachment; filename="+ fileName);
				response.header("Content-Type", mimeType);
				// update response 
				//get bytes content of File
				String responseXml="";
				responseXml=loginProviderService.getBytesFromFile(file);
				loginProviderService.updateSOMobileResponse(loggingId,responseXml);
				return response.build();
		} else{
				DownLoadResponse errorResponse=createDownloadResponse(result.getCode(),result.getMessage());
				String resultString=convertXMLStringToJsonObject(errorResponse);
				Response response = Response.ok(resultString).build();
				// update response 
				 loginProviderService.updateSOMobileResponse(loggingId,resultString); 
				return response;
			
	        
		}

	}
	
	/*
	 Create new Password for User
	*/
   @POST
   @Path("/provider/{providerId}/createNewPassword")
   @Consumes( { "application/xml", "application/json", "text/xml" })
   @Produces( { "application/xml", "application/json", "text/xml" })
   public CreateNewPasswordResponse createNewPasswordForUser(
   		CreateNewPasswordRequest createNewPasswordRequest, @PathParam("providerId") String resourceId) {
		
		logger.info("Entering MobileGenericRequestProcessor.createNewPasswordForUser()");
		
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		CreateNewPasswordResponse createNewPasswordResponse = new CreateNewPasswordResponse();
		String request = convertReqObjectToXMLString(createNewPasswordRequest, CreateNewPasswordRequest.class);
		logger.info("Request : " + request); 
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Post);
		if(StringUtils.isNumeric(resourceId)){
			apiVO.setProviderResourceId(Integer.parseInt(resourceId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		
		// apiVO.setProviderResourceId(providerResourceId);
		// log the request
		Integer providerId=createNewPasswordForUserService.validateResourceId(resourceId);
		Integer loggingId=createNewPasswordForUserService.logSOMobileHistory(request,PublicMobileAPIConstant.CREATE_NEW_PASSWORD_FOR_USER, providerId,null,PublicMobileAPIConstant.HTTP_POST);
		response  = createNewPasswordForUserService.doSubmit(apiVO);
		
		// update response 
		createNewPasswordForUserService.updateSOMobileResponse(loggingId, response);
		logger.info("Response : " + response); 
		createNewPasswordResponse = (CreateNewPasswordResponse)convertXMLStringtoRespObject(response,CreateNewPasswordResponse.class);
		logger.info("Leaving MobileGenericRequestProcessor.createNewPasswordForUser()");
		return createNewPasswordResponse;
  
	}   
	/**
	 * This method add estimation for a service order 
	 * @param roleId
	 * @param firmId
	 * @param resourceId
	 * @param soId
	 * @param estimateId
	 * @return
	 */
	@POST
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/updateEstimate")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public AddEstimateResponse AddEstimate(@PathParam("roleId") Integer roleId,@PathParam("firmId") String firmId,
			@PathParam("providerId") Integer providerId,@PathParam("soId") String soId,AddEstimateRequest addEstimateRequest){
		String addEstimateResponseString = null;
		AddEstimateResponse addEstimateResponse = new AddEstimateResponse();
		String request = convertReqObjectToXMLString(addEstimateRequest,AddEstimateRequest.class);
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setProviderResourceId(providerId);
		apiVO.setProviderId(firmId);			
		apiVO.setSOId(soId);
		//setting the role as null to bypass the role level validations in baseservice
		apiVO.setRoleId(null);
        apiVO.setRequestFromPostPut(request);
	    apiVO.setRequestType(RequestType.Post);
	    Integer loggingId=addEstimateService.logSOMobileHistory(request,PublicMobileAPIConstant.ADD_ESTIMATE_SO,PublicMobileAPIConstant.HTTP_POST,apiVO);
		addEstimateResponseString = addEstimateService.doSubmit(apiVO);
		addEstimateResponse = (AddEstimateResponse) convertXMLStringtoRespObject(addEstimateResponseString, AddEstimateResponse.class);
		addEstimateService.updateSOMobileResponse(loggingId, addEstimateResponseString);
	    return addEstimateResponse;
	}
	/**
	 * This method retrieves estimate details of a service order 
	 * @param roleId
	 * @param firmId
	 * @param resourceId
	 * @param soId
	 * @param estimateId
	 * @return
	 */
	@GET
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/estimation/{estimateId}/estimationDetails")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public GetEstimateDetailsResponse getEstimateDetails(@PathParam("roleId") Integer roleId,@PathParam("firmId") String firmId, 
			@PathParam("providerId") String resourceId,@PathParam("soId")
	String soId,@PathParam("estimateId") Integer estimateId) {
		
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());		
		apiVO.setRequestType(BaseService.RequestType.Get);		
		apiVO.setProviderId(firmId);
		if(StringUtils.isNumeric(resourceId)){
			apiVO.setProviderResourceId(Integer.parseInt(resourceId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		apiVO.setSOId(soId);
		//setting the role as null to bypass the role level validations in baseservice
		apiVO.setRoleId(null);
		apiVO.setEstimateId(estimateId);
		Integer loggingId=getEstimateService.logSOMobileHistory("",PublicMobileAPIConstant.GET_ESTIMATE,PublicMobileAPIConstant.HTTP_GET,apiVO);		
		String responseXML = getEstimateService.doSubmit(apiVO);
		getEstimateService.updateSOMobileResponse(loggingId, responseXML);
		return (GetEstimateDetailsResponse) convertXMLStringtoRespObject(responseXML,
				GetEstimateDetailsResponse.class);

	}
	
	/**
	 * 
	 * @param roleId
	 * @param firmId
	 * @param resourceId
	 * @param soId
	 * @param mobilePlaceBidRequest
	 * @return
	 */
	@POST
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/placeBid")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public MobilePlaceBidResponse mobileSOPlaceBid(
			@PathParam("roleId") Integer roleId,
			@PathParam("firmId") String firmId,
			@PathParam("providerId") String resourceId,
			@PathParam("soId") String soId,
			MobilePlaceBidRequest mobilePlaceBidRequest) {
	
		// declare the response variables
		String mobilePlaceBidResponseString = null;
		MobilePlaceBidResponse mobilePlaceBidResponse = new MobilePlaceBidResponse();
		// convert the request object to XML string - uses XStream toXML() method
		String request = convertReqObjectToXMLString(mobilePlaceBidRequest,MobilePlaceBidRequest.class);
		// create the API request VO object for passing to doSubmit()
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		// assign soId from the request URL
		apiVO.setSOId(soId);
		if(StringUtils.isNumeric(firmId)){
			apiVO.setProviderId(firmId);
		} 
		if(StringUtils.isNumeric(resourceId)){
			apiVO.setProviderResourceId(Integer.parseInt(resourceId));
		} 
		apiVO.setRoleId(roleId);		
		// assign the XML string
		apiVO.setRequestFromPostPut(request);
		// assign the Request method as POST
		apiVO.setRequestType(RequestType.Post);
		// log the request
		// get resource Id from vendor_resource
		//Integer resourceId=mobileSOAcceptService.validateResourceId(providerId);
		Integer loggingId = mobilePlaceBidService.logSOMobileHistory(request, PublicMobileAPIConstant.PLACE_BID, PublicMobileAPIConstant.HTTP_POST, apiVO);
		// call the doSubmit method of the SOBaseService
		mobilePlaceBidResponseString = mobilePlaceBidService.doSubmit(apiVO);
		// convert the XML string to object
		mobilePlaceBidResponse = (MobilePlaceBidResponse) convertXMLStringtoRespObject(
				mobilePlaceBidResponseString, MobilePlaceBidResponse.class);
		// update response 
		mobilePlaceBidService.updateSOMobileResponse(loggingId, mobilePlaceBidResponseString);
		return mobilePlaceBidResponse;
	}
	
	/**
	 * 
	 * @param roleId
	 * @param firmId
	 * @param resourceId
	 * @param soId
	 * @param mobileChangeBidRequest
	 * @return
	 */
	@PUT
	@Path("/role/{roleId}/firm/{firmId}/provider/{providerId}/serviceorder/{soId}/changeBid")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public MobilePlaceBidResponse mobileSOChangeBid(
			@PathParam("roleId") Integer roleId,
			@PathParam("firmId") String firmId,
			@PathParam("providerId") String resourceId,
			@PathParam("soId") String soId,
			MobilePlaceBidRequest mobileChangeBidRequest) {
		
		// declare the response variables
		String mobileChangeBidResponseString = null;
		MobilePlaceBidResponse mobileChangeBidResponse = new MobilePlaceBidResponse();
		// convert the request object to XML string
		// uses XStream toXML() method
		String request = convertReqObjectToXMLString(mobileChangeBidRequest,MobilePlaceBidRequest.class);
		// create the API request VO object for passing to doSubmit()
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		// assign soId from the request URL
		apiVO.setSOId(soId);
		if(StringUtils.isNumeric(firmId)){
			apiVO.setProviderId(firmId);
		} 	
		if(StringUtils.isNumeric(resourceId)){
			apiVO.setProviderResourceId(Integer.parseInt(resourceId));
		} 
		apiVO.setRoleId(roleId);	
		// assign the XML string
		apiVO.setRequestFromPostPut(request);
		// assign the Request method as POST
		apiVO.setRequestType(RequestType.Put);
		// log the request
		// get resource Id from vendor_resource
		//Integer resourceId=mobileSOAcceptService.validateResourceId(providerId);
		Integer loggingId = mobilePlaceBidService.logSOMobileHistory(request, PublicMobileAPIConstant.CHANGE_BID, PublicMobileAPIConstant.HTTP_PUT, apiVO);
		// call the doSubmit method of the SOBaseService
		// this call would call the execute method of RevisitNeededService
		mobileChangeBidResponseString = mobilePlaceBidService.doSubmit(apiVO);
		// convert the XML string to object
		mobileChangeBidResponse = (MobilePlaceBidResponse) convertXMLStringtoRespObject(mobileChangeBidResponseString, MobilePlaceBidResponse.class);
		// update response 
		mobilePlaceBidService.updateSOMobileResponse(loggingId, mobileChangeBidResponseString);
		return mobileChangeBidResponse;
	}

	/**
	 * API to return provider profile dertails
	 * @param firmId
	 * @param resourceId
	 * @return
	 */
	@GET
	@Path("/provider/{providerId}/profileDetails")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public ProviderProfileResponse getProviderProfileDetails(
			@PathParam("providerId") String resourceId) {
		
		// declare the response variables
		String getProviderProfileResponseString = null;
		ProviderProfileResponse getProviderProfileResponse = new ProviderProfileResponse();
		// create the API request VO object for passing to doSubmit()
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());

		if(StringUtils.isNumeric(resourceId)){
			apiVO.setResourceId(resourceId);
		} 

		apiVO.setRequestType(RequestType.Get);
		// log the request
		Integer loggingId = providerProfileService.logSOMobileHistory("", PublicMobileAPIConstant.GET_PROVIDER_PROFILE, PublicMobileAPIConstant.HTTP_GET, apiVO);
		// call the doSubmit method of the SOBaseService
		// this call would call the execute method of RevisitNeededService
		getProviderProfileResponseString = providerProfileService.doSubmit(apiVO);
		// convert the XML string to object
		getProviderProfileResponse = (ProviderProfileResponse) convertXMLStringtoRespObject(getProviderProfileResponseString, ProviderProfileResponse.class);
		// update response 
		providerProfileService.updateSOMobileResponse(loggingId, getProviderProfileResponseString);
		return getProviderProfileResponse;
	}
	/**
	 * API to return provider profile image
	 * @param resourceId
	 * @return
	 */
	@GET
	@Path("/provider/{providerId}/profileImage")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public Response getProviderProfileImage(
			@PathParam("providerId") String resourceId) {

		// declare the response variables
		ProviderProfileResponse getProviderProfileResponse = new ProviderProfileResponse();
		// create the API request VO object for passing to doSubmit()
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());

		if (StringUtils.isNumeric(resourceId)) {
			apiVO.setResourceId(resourceId);
		}

		apiVO.setRequestType(RequestType.Get);
		Integer loggingId = loginProviderService.logSOMobileHistory("",
				PublicMobileAPIConstant.GET_PROVIDER_PROFILE_IMAGE,
				PublicMobileAPIConstant.HTTP_GET, apiVO);
		// call the doSubmit method of the SOBaseService
		// this call would call the execute method of RevisitNeededService
		ProviderProfileImageResponse serviceResponse = providerProfileImageService
				.execute(apiVO);
		if (serviceResponse.getProviderDocument() != null) {
			if(null == serviceResponse.getProviderDocument().getDocDetails() || null == serviceResponse.getProviderDocument().getDocDetails().getDocument()){
				String responseString = "Profile image not available";
				Response response = Response.ok(responseString).build();
				// update response
				loginProviderService
						.updateSOMobileResponse(loggingId, responseString);
				return response;
			}
			File file = serviceResponse.getProviderDocument().getDocDetails()
					.getDocument();
			MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
			String mimeType = mimeTypesMap.getContentType(file);
			ResponseBuilder response = Response.ok((Object) file);
			String fileName = serviceResponse.getProviderDocument()
					.getDocDetails().getFileName();
			response.header("Content-disposition", "attachment; filename="
					+ fileName);
			response.header("Content-Type", mimeType);
			// update response
			// get bytes content of File
			String responseXml = "";
			responseXml = loginProviderService.getBytesFromFile(file);
			loginProviderService.updateSOMobileResponse(loggingId, responseXml);
			return response.build();
		} else {
			DownLoadResponse errorResponse = createDownloadResponse(
					serviceResponse.getResults().getError().get(0).getCode(),
					serviceResponse.getResults().getError().get(0)
							.getMessage());
			String resultString = convertXMLStringToJsonObject(errorResponse);
			Response response = Response.ok(resultString).build();
			// update response
			loginProviderService
					.updateSOMobileResponse(loggingId, resultString);
			return response;

		}
	}

	/**
	 * API to upload provider profile image
	 * @param resourceId
	 * @return
	 */
	@POST
    @Path("/provider/{providerId}/profileImage")
    @Consumes("multipart/form-data")
    @Produces( { "application/xml", "application/json", "text/xml" })
    public ProviderProfileImageUploadResponse uploadProviderProfileImage(
    		MultipartBody  file,
    		@PathParam("providerId") String providerId) {

		logger.info("Entering MobileGenericRequestProcessor.uploadProviderProfileImage()");
		ProviderProfileImageUploadResponse uploadImageResponse=new ProviderProfileImageUploadResponse();
		// log the request
		FileData fileData = new FileData();
		Integer resourceId = loginProviderService.validateResourceId(providerId);
		fileData = loginProviderService.getBytesFromFile(file);
		Integer loggingId=loginProviderService.logSOMobileHistory(fileData.getByteString(),PublicMobileAPIConstant.UPLOAD_PROVIDER_PROFILE_IMAGE, resourceId, "",PublicMobileAPIConstant.HTTP_POST);

		uploadImageResponse = providerProfileImageUploadService.execute(file, providerId,fileData.getBytes());
		logger.info("Leaving MobileGenericRequestProcessor.uploadProviderProfileImage()");
		// update response 
		String responseXml = convertReqObjectToXMLString(
				uploadImageResponse, ProviderUploadDocumentResponse.class);
	  loginProviderService.updateSOMobileResponse(loggingId, responseXml);
			return uploadImageResponse;
	}
	
	@POST
    @Path("/provider/{providerId}/update")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
    public GenericAPIResponse updateProviderProfileDetails(
    		@PathParam("providerId") String providerId,
    		UpdateProviderProfileDetailsRequest updateProviderDetails) {
		
		// declare the response variables
		String updateProviderProfileResponseString = null;
		GenericAPIResponse updateProviderProfileResponse = new GenericAPIResponse();
		// create the API request VO object for passing to doSubmit()
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		
		// convert the request object to XML string
		// uses XStream toXML() method
		String request = convertReqObjectToXMLString(updateProviderDetails,UpdateProviderProfileDetailsRequest.class);

		if(StringUtils.isNumeric(providerId)){
			apiVO.setResourceId(providerId);
		} 
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Post);
		// log the request
		Integer loggingId = updateProviderProfileService.logSOMobileHistory("", PublicMobileAPIConstant.UPDATE_PROVIDER_PROFILE, PublicMobileAPIConstant.HTTP_POST, apiVO);
		// call the doSubmit method of the SOBaseService
		// this call would call the execute method of RevisitNeededService
		updateProviderProfileResponseString = updateProviderProfileService.doSubmit(apiVO);
		// convert the XML string to object
		updateProviderProfileResponse = (GenericAPIResponse) convertXMLStringtoRespObject(updateProviderProfileResponseString, GenericAPIResponse.class);
		// update response 
		updateProviderProfileService.updateSOMobileResponse(loggingId, updateProviderProfileResponseString);
		return updateProviderProfileResponse;
		
	}
	
	
	@POST
	@Path("/provider/{providerId}/createCalEvent")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public AddCalendarProviderEventResponse AddCalEvent(@PathParam("providerId") Integer providerId,AddCalendarProviderEventRequest addCalendarProviderEventRequest){
		String addCalendarResponseString = null;
		AddCalendarProviderEventResponse addCalendarProviderEventResponse = new AddCalendarProviderEventResponse();
		String request = convertReqObjectToXMLString(addCalendarProviderEventRequest,AddCalendarProviderEventRequest.class);
		
		
		
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setProviderResourceId(providerId);
		
        apiVO.setRequestFromPostPut(request);
	    apiVO.setRequestType(BaseService.RequestType.Post);
	    
	    addCalendarResponseString = addCalendarEventService.doSubmit(apiVO);
	    addCalendarProviderEventResponse = (AddCalendarProviderEventResponse) convertXMLStringtoRespObject(addCalendarResponseString, AddCalendarProviderEventResponse.class);
		
	    return addCalendarProviderEventResponse;
	}
	
	
	@PUT
	@Path("/provider/{providerId}/updateCalEvent")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public UpdateCalendarProviderEventResponse updateCalEvent(@PathParam("providerId") Integer providerId,UpdateCalendarProviderEventRequest updateCalendarProviderEventRequest){
		String updateCalendarResponseString = null;                                
		UpdateCalendarProviderEventResponse updateCalendarProviderEventResponse = new UpdateCalendarProviderEventResponse();
		String request = convertReqObjectToXMLString(updateCalendarProviderEventRequest,UpdateCalendarProviderEventRequest.class);
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setProviderResourceId(providerId);
		
        apiVO.setRequestFromPostPut(request);
	    apiVO.setRequestType(BaseService.RequestType.Put);
	    
	   updateCalendarResponseString = updateCalendarEventService.doSubmit(apiVO);
	    updateCalendarProviderEventResponse = (UpdateCalendarProviderEventResponse) convertXMLStringtoRespObject(updateCalendarResponseString, UpdateCalendarProviderEventResponse.class);
		
	    return updateCalendarProviderEventResponse;
	}
	
	

	
	
	@GET
	@Path("/provider/{providerId}/getCalendar")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public GetCalendarProviderEventListResponse getProviderCalendarEvents(@PathParam("providerId") Integer providerId,@QueryParam("startDate") String startDate,
            @QueryParam("startTime") String startTime,
            @QueryParam("endDate") String endDate,
            @QueryParam("endTime") String endTime){
		String getCalendarProviderEventListResponseString = null;                                
		GetCalendarProviderEventListResponse getCalendarProviderEventListResponse = new GetCalendarProviderEventListResponse();
		
		if (logger.isInfoEnabled())
			logger.info("getProviderCalendarEvents method started");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setProviderResourceId(providerId);
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setRequestType(BaseService.RequestType.Get);
		getCalendarProviderEventListResponseString = providerCalendarEventService.doSubmit(apiVO);
		//getCalendarProviderEventListResponse = providerCalendarEventService.execute(providerId,startDate, endDate,startTime,endTime);
		getCalendarProviderEventListResponse = (GetCalendarProviderEventListResponse) convertXMLStringtoRespObject(getCalendarProviderEventListResponseString, GetCalendarProviderEventListResponse.class);
		
	    return getCalendarProviderEventListResponse;
	}
	
	
		@DELETE
       @Path("/provider/{providerId}/calendar/{eventId}/deleteCalEvent")
	   @Produces({ "application/xml", "application/json", "text/xml" })
       public DeleteCalendarProviderEventResponse deleteProviderCalEvent(@PathParam("providerId") Integer providerId, @PathParam("eventId") String eventId){
	   
		   String deleteCalendarResponseString = null;                                
			DeleteCalendarProviderEventResponse deleteCalendarProviderEventResponse = new DeleteCalendarProviderEventResponse();
			Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest()
					.getParameterMap());
			APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
			apiVO.setProviderResourceId(providerId);
			apiVO.setEventId(eventId);
	        apiVO.setRequestFromGetDelete(reqMap);
		    apiVO.setRequestType(RequestType.Delete);
		    
		    
		    deleteCalendarResponseString=deleteCalendarEventService.doSubmit(apiVO);
		    
		     deleteCalendarProviderEventResponse =(DeleteCalendarProviderEventResponse) convertXMLStringtoRespObject(deleteCalendarResponseString, DeleteCalendarProviderEventResponse.class);
			return deleteCalendarProviderEventResponse; 
		 
       }
		
	/**
	 * Method to fetch all available disposition codes
	 * 
	 * @param roleId
	 * @param firmId
	 * @param providerId
	 * @param soId
	 * @return
	 */
	
	@GET
	@Path("/provider/{providerId}/getDispositionCodes")
	@Produces({ "application/xml", "application/json", "text/xml" })
	public GetDispositionCodeResponse getDespositionCodes(@PathParam("providerId") String providerId) {

		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());

		@SuppressWarnings("unchecked")
		Map<String, String> reqMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setRequestFromGetDelete(reqMap);
		if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}
	    apiVO.setRequestType(RequestType.Get);
		String responseXML = getDispositionCodesService.doSubmit(apiVO);
		return (GetDispositionCodeResponse) convertXMLStringtoRespObject(
				responseXML, GetDispositionCodeResponse.class);
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
	
	public LoginProviderService getLoginProviderService() {
		return loginProviderService;
	}
	public void setLoginProviderService(LoginProviderService loginProviderService) {
		this.loginProviderService = loginProviderService;
	}
	public ProviderUploadDocumentService getProviderUploadDocumentService() {
		return providerUploadDocumentService;
	}

	public void setProviderUploadDocumentService(
			ProviderUploadDocumentService providerUploadDocumentService) {
		this.providerUploadDocumentService = providerUploadDocumentService;
	}

	public MobileTimeOnSiteService getTimeOnSiteService() {
		return timeOnSiteService;
	}

	public void setTimeOnSiteService(MobileTimeOnSiteService timeOnSiteService) {
		this.timeOnSiteService = timeOnSiteService;
	}

	public SOUpdateAppointmentTimeService getUpdateAppointmentTimeService() {
		return updateAppointmentTimeService;
	}

	public void setUpdateAppointmentTimeService(
			SOUpdateAppointmentTimeService updateAppointmentTimeService) {
		this.updateAppointmentTimeService = updateAppointmentTimeService;
	}

	public SODetailsRetrieveService getSoDetailsRetrieveService() {
		return soDetailsRetrieveService;
	}

	public void setSoDetailsRetrieveService(
			SODetailsRetrieveService soDetailsRetrieveService) {
		this.soDetailsRetrieveService = soDetailsRetrieveService;
	}

	public DocumentDownloadService getSoDocDownloadService() {
		return soDocDownloadService;
	}

	public void setSoDocDownloadService(DocumentDownloadService soDocDownloadService) {
		this.soDocDownloadService = soDocDownloadService;
	}

	public GetProviderSOListService getGetProviderSOListService() {
		return getProviderSOListService;
	}

	public void setGetProviderSOListService(
			GetProviderSOListService getProviderSOListService) {
		this.getProviderSOListService = getProviderSOListService;
	}

	public ProviderAddNoteService getProviderAddNoteService() {
		return providerAddNoteService;
	}

	public void setProviderAddNoteService(
			ProviderAddNoteService providerAddNoteService) {
		this.providerAddNoteService = providerAddNoteService;
	}

	public UpdateSOCompletionService getUpdateSOCompletionService() {
		return updateSOCompletionService;
	}

	public void setUpdateSOCompletionService(
			UpdateSOCompletionService updateSOCompletionService) {
		this.updateSOCompletionService = updateSOCompletionService;
	}

	public CreateNewPasswordForUserService getCreateNewPasswordForUserService() {
		return createNewPasswordForUserService;
	}

	public void setCreateNewPasswordForUserService(
			CreateNewPasswordForUserService createNewPasswordForUserService) {
		this.createNewPasswordForUserService = createNewPasswordForUserService;
	}
	
	public AddEstimateService getAddEstimateService() {
		return addEstimateService;
	}

	public void setAddEstimateService(AddEstimateService addEstimateService) {
		this.addEstimateService = addEstimateService;
	}

	public GetEstimateDetailsService getGetEstimateService() {
		return getEstimateService;
	}

	public void setGetEstimateService(GetEstimateDetailsService getEstimateService) {
		this.getEstimateService = getEstimateService;
	}
	
	public MobilePlaceBidService getMobilePlaceBidService() {
		return mobilePlaceBidService;
	}

	public void setMobilePlaceBidService(MobilePlaceBidService mobilePlaceBidService) {
		this.mobilePlaceBidService = mobilePlaceBidService;
	}

	public ProviderProfileService getProviderProfileService() {
		return providerProfileService;
	}

	public void setProviderProfileService(
			ProviderProfileService providerProfileService) {
		this.providerProfileService = providerProfileService;
	}

	public ProviderProfileImageService getProviderProfileImageService() {
		return providerProfileImageService;
	}

	public void setProviderProfileImageService(
			ProviderProfileImageService providerProfileImageService) {
		this.providerProfileImageService = providerProfileImageService;
	}

	public ProviderProfileImageUploadService getProviderProfileImageUploadService() {
		return providerProfileImageUploadService;
	}

	public void setProviderProfileImageUploadService(
			ProviderProfileImageUploadService providerProfileImageUploadService) {
		this.providerProfileImageUploadService = providerProfileImageUploadService;
	}

	public UpdateProviderProfileService getUpdateProviderProfileService() {
		return updateProviderProfileService;
	}

	public void setUpdateProviderProfileService(
			UpdateProviderProfileService updateProviderProfileService) {
		this.updateProviderProfileService = updateProviderProfileService;
	}

	/**
	 * @return the addCalendarEventService
	 */
	public AddCalendarEventService getAddCalendarEventService() {
		return addCalendarEventService;
	}

	/**
	 * @param addCalendarEventService the addCalendarEventService to set
	 */
	public void setAddCalendarEventService(
			AddCalendarEventService addCalendarEventService) {
		this.addCalendarEventService = addCalendarEventService;
	}

	/**
	 * @return the updateCalendarEventService
	 */
	public UpdateCalendarEventService getUpdateCalendarEventService() {
		return updateCalendarEventService;
	}

	/**
	 * @param updateCalendarEventService the updateCalendarEventService to set
	 */
	public void setUpdateCalendarEventService(
			UpdateCalendarEventService updateCalendarEventService) {
		this.updateCalendarEventService = updateCalendarEventService;
	}

	/**
	 * @return the providerCalendarEventService
	 */
	public ProviderCalendarEventService getProviderCalendarEventService() {
		return providerCalendarEventService;
	}

	/**
	 * @param providerCalendarEventService the providerCalendarEventService to set
	 */
	public void setProviderCalendarEventService(
			ProviderCalendarEventService providerCalendarEventService) {
		this.providerCalendarEventService = providerCalendarEventService;
	}

	/**
	 * @return the deleteCalendarEventService
	 */
	public DeleteCalendarEventService getDeleteCalendarEventService() {
		return deleteCalendarEventService;
	}

	/**
	 * @param deleteCalendarEventService the deleteCalendarEventService to set
	 */
	public void setDeleteCalendarEventService(
			DeleteCalendarEventService deleteCalendarEventService) {
		this.deleteCalendarEventService = deleteCalendarEventService;
	}

	public GetDispositionCodesService getGetDispositionCodesService() {
		return getDispositionCodesService;
	}

	public void setGetDispositionCodesService(
			GetDispositionCodesService getDispositionCodesService) {
		this.getDispositionCodesService = getDispositionCodesService;
	}
	
}
