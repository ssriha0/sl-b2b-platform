package com.newco.marketplace.api.mobile.processor.v2_0;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.ext.multipart.MultipartBody;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.addinvoicesoproviderpart.AddInvoiceSOProviderPartRequest;
import com.newco.marketplace.api.mobile.beans.addinvoicesoproviderpart.AddInvoiceSOProviderPartResponse;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.AddSOProviderPartRequest;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.AddSOProviderPartResponse;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.FindSupplierRequest;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.FindSupplierResponse;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.UpdateSOProviderPartRequest;
import com.newco.marketplace.api.mobile.beans.addsoproviderpart.UpdateSOProviderPartResponse;
import com.newco.marketplace.api.mobile.beans.authenticateMobileVersionCheck.v2_0.AuthenticateMobileVersionRequest;
import com.newco.marketplace.api.mobile.beans.authenticateMobileVersionCheck.v2_0.AuthenticateMobileVersionResponse;
import com.newco.marketplace.api.mobile.beans.authenticateUser.v2_0.LoginProviderRequest;
import com.newco.marketplace.api.mobile.beans.authenticateUser.v2_0.LoginProviderResponse;
import com.newco.marketplace.api.mobile.beans.feedback.FeedbackRequest;
import com.newco.marketplace.api.mobile.beans.feedback.FeedbackResponse;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.SOGetMobileCompletionDetailsResponse;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.SOGetMobileDetailsResponse;
import com.newco.marketplace.api.mobile.beans.updateMobileAppVersion.v2_0.UpdateMobileAppVersionRequest;
import com.newco.marketplace.api.mobile.beans.updateMobileAppVersion.v2_0.UpdateMobileAppVersionResponse;

import com.newco.marketplace.api.mobile.beans.uploadDocument.FileData;
import com.newco.marketplace.api.mobile.beans.uploadDocument.ProviderUploadDocumentResponse;
import com.newco.marketplace.api.mobile.beans.v2_0.DeleteSOProviderPartRequest;
import com.newco.marketplace.api.mobile.beans.v2_0.DeleteSOProviderPartResponse;
import com.newco.marketplace.api.mobile.beans.v2_0.MobileTimeOnSiteRequest;
import com.newco.marketplace.api.mobile.beans.v2_0.MobileTimeOnSiteResponse;
import com.newco.marketplace.api.mobile.beans.v2_0.SORevisitNeededRequest;
import com.newco.marketplace.api.mobile.beans.v2_0.SORevisitNeededResponse;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.service.BaseService;
import com.newco.marketplace.api.mobile.service.BaseService.RequestType;
import com.newco.marketplace.api.mobile.services.v2_0.AddInvoiceSOProviderPartService;
import com.newco.marketplace.api.mobile.services.v2_0.AddSOProviderPartService;
import com.newco.marketplace.api.mobile.services.v2_0.AuthenticateMobileVersionService;
import com.newco.marketplace.api.mobile.services.v2_0.DeleteSOProviderPartService;
import com.newco.marketplace.api.mobile.services.v2_0.FeedbackService;
import com.newco.marketplace.api.mobile.services.v2_0.FindSupplierService;
import com.newco.marketplace.api.mobile.services.v2_0.LoginProviderService;
import com.newco.marketplace.api.mobile.services.v2_0.MobileTimeOnSiteService;
import com.newco.marketplace.api.mobile.services.v2_0.PartDirectWrapperService;
import com.newco.marketplace.api.mobile.services.v2_0.ProviderUploadDocumentService;
import com.newco.marketplace.api.mobile.services.v2_0.SOCompletionDetailsRetrieveService;
import com.newco.marketplace.api.mobile.services.v2_0.SODetailsRetrieveService;
import com.newco.marketplace.api.mobile.services.v2_0.SORevisitNeededService;
import com.newco.marketplace.api.mobile.services.v2_0.UpdateMobileAppVersionService;

import com.newco.marketplace.api.mobile.services.v2_0.UpdateSOProviderPartService;
import com.newco.marketplace.api.server.MobileBaseRequestProcessor;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.mobile.PartDetailsDTO;
import com.newco.marketplace.vo.mobile.v2_0.MobileDeviceData;
import com.newco.marketplace.vo.mobile.v2_0.MobileDeviceDataList;

@Path("v2.0")
public class MobileGenericRequestProcessor extends MobileBaseRequestProcessor{

	@Resource
	protected HttpServletRequest httpRequest;
	@Resource
	protected HttpServletResponse httpResponse;
	private Logger logger = Logger.getLogger(MobileGenericRequestProcessor.class);
     
	// service reference for Time on Site
	protected MobileTimeOnSiteService timeOnSiteService;
	protected SORevisitNeededService revisitNeededService;
	
	protected SOCompletionDetailsRetrieveService soCompletionDetailsRetrieveService;
	protected SODetailsRetrieveService soDetailsRetrieveService;	
	protected ProviderUploadDocumentService providerUploadDocumentService;
	protected LoginProviderService loginProviderService;
	//service class for adding invoice provider part
	protected AddSOProviderPartService addSOProviderPartService;
	//service class for updating supplier location details and part status
	protected UpdateSOProviderPartService updateSOProviderPartService;
	// service reference for Part Invoice  Info
	protected AddInvoiceSOProviderPartService addInvoiceSOProviderPartService;
	protected DeleteSOProviderPartService deleteSOProviderPartService;
	// Service class to invoke LPN Web Service
	protected FindSupplierService findSupplierService;
	// service to submit feedback
	protected FeedbackService feedbackService;
	protected AuthenticateMobileVersionService authenticateMobileVersionService;
	protected UpdateMobileAppVersionService updateMobileAppVersionService;
	// Service class to invoke LIS Web Service
	protected PartDirectWrapperService partDirectWrapperService;	

	/**
	 * This method returns a response for a get / retrieve service order
	 * details for completion request .
	 * 
	 * @param String
	 *            buyerResourceId, String soId
	 * @return Response
	 */
	@SuppressWarnings("unchecked")
	@GET 
	@Path("/provider/{providerId}/serviceorders/{soId}/getSOCompletionDetails")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public SOGetMobileCompletionDetailsResponse getSOCompletionDetailsPro(@PathParam("providerId") String resourceId, @PathParam("soId")
	String soId) {
		if (logger.isInfoEnabled())
			logger.info("getResponseForProviderRetrieve method started");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());		
		apiVO.setRequestType(BaseService.RequestType.Get);		
		apiVO.setResourceId(resourceId);
		if(StringUtils.isNumeric(resourceId)){
			apiVO.setProviderResourceId(Integer.parseInt(resourceId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		apiVO.setSOId(soId);
		// the indicator is set to exclude GetSOCompletionDetails from the SOLevel validation in Base Service, the specific validation for Complete API will be handled in respective service class
		apiVO.setAPISOValidationExcludeInd(true);
		// log the request
		Integer providerId=soCompletionDetailsRetrieveService.validateResourceId(resourceId);
		Integer loggingId=soCompletionDetailsRetrieveService.logSOMobileHistory("",PublicMobileAPIConstant.GET_SO_COMP_DETAILS_PRO_V2, 
			providerId,soId,PublicMobileAPIConstant.HTTP_GET);		
		String responseXML = soCompletionDetailsRetrieveService.doSubmit(apiVO);
		soCompletionDetailsRetrieveService.updateSOMobileResponse(loggingId, responseXML);
		return (SOGetMobileCompletionDetailsResponse) convertXMLStringtoRespObject(responseXML,
				SOGetMobileCompletionDetailsResponse.class);

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
	@Path("/provider/{providerId}/serviceorders/{soId}/getSODetailsPro")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public SOGetMobileDetailsResponse getResponseForSoRetrieve(@PathParam("providerId") String resourceId, @PathParam("soId")
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
		Integer loggingId=soDetailsRetrieveService.logSOMobileHistory(reqMap.toString(),PublicMobileAPIConstant.GET_SO_DETAILS_PRO_V2, 
			providerId,soId,PublicMobileAPIConstant.HTTP_GET);
		String responseXML = soDetailsRetrieveService.doSubmit(apiVO);
		// update response 
		soDetailsRetrieveService.updateSOMobileResponse(loggingId, responseXML);
		return (SOGetMobileDetailsResponse) convertXMLStringtoRespObject(responseXML,
				SOGetMobileDetailsResponse.class);

	}
	
	
	/**
	 * This method returns a response for a Time On Site request. The request
	 * processor delegates the processing to the timeOnSiteService class - This
	 * is in case of Provider *
	 * 
	 */
	@POST
	@Path("/provider/{providerId}/serviceorder/{soId}/timeonsite")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public MobileTimeOnSiteResponse addTimeOnSite(
			@PathParam("providerId") String providerId,
			@PathParam("soId") String soId,
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
		Integer loggingId=timeOnSiteService.logSOMobileHistory(request,PublicMobileAPIConstant.TIME_ON_SITE_V2, resourceId,soId,PublicMobileAPIConstant.HTTP_POST);

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
	
	/**
	 * @param providerId
	 * @param soId
	 * @param soRevisitNeededRequest
	 * @return
	 * This method returns a response for a SO Revisit Needed request. The request
	 * processor delegates the processing to the soRevisitNeededService class - This
	 * is in case of Provider
	 */
	@POST
	@Path("/provider/{providerId}/serviceorder/{soId}/submitSORevisit")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public SORevisitNeededResponse soRevisitNeeded(
			@PathParam("providerId") String providerId,
			@PathParam("soId") String soId,
			SORevisitNeededRequest soRevisitNeededRequest) {
		if (logger.isInfoEnabled()) {
			logger.info("Entering MobileGenericRequestProcessor.soRevisitNeeded");
		}
		long soRevisitNeededStart = System.currentTimeMillis();		
		// declare the response variables
		String soRevisitNeededResponseString = null;
		SORevisitNeededResponse soRevisitNeededResponse = new SORevisitNeededResponse();

		// convert the request object to XML string
		// uses XStream toXML() method
		String request = convertReqObjectToXMLString(soRevisitNeededRequest,
				SORevisitNeededRequest.class);

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
		Integer resourceId=revisitNeededService.validateResourceId(providerId);
		Integer loggingId=revisitNeededService.logSOMobileHistory(request,PublicMobileAPIConstant.SO_REVISIT_NEEDED_V2, resourceId,soId,PublicMobileAPIConstant.HTTP_POST);

		// call the doSubmit method of the SOBaseService
		// this call would call the execute method of RevisitNeededService
		soRevisitNeededResponseString = revisitNeededService.doSubmit(apiVO);

		// convert the XML string to object
		soRevisitNeededResponse = (SORevisitNeededResponse) convertXMLStringtoRespObject(
				soRevisitNeededResponseString, SORevisitNeededResponse.class);

		soRevisitNeededResponse.setSoId(soId);

		if (logger.isInfoEnabled()) {
			logger.info("Leaving MobileGenericRequestProcessor.addTimeOnSite");
		}
		// update response 
		revisitNeededService.updateSOMobileResponse(loggingId, soRevisitNeededResponseString);
		long soRevisitNeededEnd = System.currentTimeMillis();
		logger.info("Total time taken for soRevisitNeeded after logging in ms :::"+(soRevisitNeededEnd-soRevisitNeededStart));
		return soRevisitNeededResponse;
	}
	
	
	//code for document upload using multi-part form data
	//added new param tripNo for document upload corresponding to each trip
	@POST
    @Path("/provider/{providerId}/serviceorder/{soId}/uploadDocument")
    @Consumes("multipart/form-data")
    @Produces( { "application/xml", "application/json", "text/xml" })
    public ProviderUploadDocumentResponse getResponseForProviderUploadDocument(
    		MultipartBody  request, @PathParam("soId") String soId,
    		@PathParam("providerId") String providerId) {

		logger.info("Entering MobileGenericRequestProcessor.getResponseForProviderLogin()");
		ProviderUploadDocumentResponse uploadDocumentResponse=new ProviderUploadDocumentResponse();
		// log the request
		FileData fileData=new FileData();
		Integer resourceId=loginProviderService.validateResourceId(providerId);
		fileData=loginProviderService.getBytesFromFile(request);
		Integer loggingId=loginProviderService.logSOMobileHistory(fileData.getByteString(),PublicMobileAPIConstant.UPLOAD_DOCUMENT_V2, resourceId,soId,PublicMobileAPIConstant.HTTP_POST);
		
		SecurityContext securityContext = loginProviderService.getSecurityContextForVendor(resourceId);
		String createdBy = "";
		if(securityContext!=null){
			LoginCredentialVO lvRoles = securityContext.getRoles();
			createdBy = lvRoles.getFirstName() +" "+lvRoles.getLastName();
		}

		uploadDocumentResponse = providerUploadDocumentService.execute(request, soId, providerId,fileData.getBytes(),createdBy);
		logger.info("Leaving MobileGenericRequestProcessor.getResponseForProviderLogin()");
		// update response 
		String responseXml = convertReqObjectToXMLString(
				uploadDocumentResponse, ProviderUploadDocumentResponse.class);
		loginProviderService.updateSOMobileResponse(loggingId, responseXml);
		return uploadDocumentResponse;
	}		
	
	  
		
		/**
		 * This method returns a response for the Adding invoice part details to a part
		 * @param soId
		 * @param providerId
		 * @return addInvoiceSOProviderPartResponse
		 */
		@PUT
		@Path("/provider/{providerId}/serviceorder/{soId}/addInvoiceSOProviderPart")
		@Consumes({ "application/xml", "application/json", "text/xml" })
		@Produces({ "application/xml", "application/json", "text/xml" })
		public AddInvoiceSOProviderPartResponse getResponseForaddInvoiceSOProviderPart(
				@PathParam("providerId") String providerId,
				@PathParam("soId") String soId,AddInvoiceSOProviderPartRequest addInvoiceSOProviderPartRequest) {
			if (logger.isInfoEnabled()) {
				logger.info("Entering AddInvoicePartRequestProcessor.addInvoiceSOProviderPartService");
			}		
			// declare the response variables
			String partInvoiceInfoResponse = null;
			AddInvoiceSOProviderPartResponse addInvoiceSOProviderPartResponse = new AddInvoiceSOProviderPartResponse();

			// convert the request object to XML string
			// uses XStream toXML() method
			String request = convertReqObjectToXMLString(addInvoiceSOProviderPartRequest,
					AddInvoiceSOProviderPartRequest.class);

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
			Integer resourceId=addInvoiceSOProviderPartService.validateResourceId(providerId);
			Integer loggingId=addInvoiceSOProviderPartService.logSOMobileHistory(request,PublicMobileAPIConstant.ADD_PART_INVOICE_INFO, resourceId,soId,PublicMobileAPIConstant.HTTP_POST);

			// call the doSubmit method of the SOBaseService
		
			partInvoiceInfoResponse = addInvoiceSOProviderPartService.doSubmit(apiVO);

			// convert the XML string to object
			addInvoiceSOProviderPartResponse = (AddInvoiceSOProviderPartResponse) convertXMLStringtoRespObject(
					partInvoiceInfoResponse, AddInvoiceSOProviderPartResponse.class);

			addInvoiceSOProviderPartResponse.setSoId(soId);

			if (logger.isInfoEnabled()) {
				logger.info("Leaving AddInvoicePartRequestProcessor.addInvoiceSOProviderPartService");
			}
			// update response 
			addInvoiceSOProviderPartService.updateSOMobileResponse(loggingId, partInvoiceInfoResponse);
			return addInvoiceSOProviderPartResponse;
		}
		
   
	/**
	 * This method returns a response for the Adding invoice part to service order
	 * @param soId
	 * @param providerId
	 * @return addProviderInvoicePartResponse
	 */
	@POST
	@Path("/provider/{providerId}/serviceorder/{soId}/addSOProviderPart")
	@Consumes({ "application/xml", "application/json","text/xml"})
	@Produces({ "application/xml", "application/json","text/xml"})
	public AddSOProviderPartResponse getResponseForAddSOProviderPart(@PathParam("providerId") String providerId,@PathParam("soId")
	String soId,AddSOProviderPartRequest  addSOProviderPartRequest) {
		if (logger.isInfoEnabled())
			logger.info("Entering MobileGenericRequestProcessor."+ "getResponseForAddSOProviderPart()");
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		AddSOProviderPartResponse addSOProviderPartResponse = new AddSOProviderPartResponse();
		String request=convertReqObjectToXMLString(addSOProviderPartRequest,AddSOProviderPartRequest.class);
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Post);
		if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		apiVO.setSOId(soId);
		// log the request
		// get resource Id from vendor_resource	
		Integer resourceId=addSOProviderPartService.validateResourceId(providerId);
		Integer loggingId=addSOProviderPartService.logSOMobileHistory(request,PublicMobileAPIConstant.ADD_PROVIDER_SO_PART, resourceId,soId,PublicMobileAPIConstant.HTTP_POST);
        response = addSOProviderPartService.doSubmit(apiVO);
        addSOProviderPartResponse=(AddSOProviderPartResponse)convertXMLStringtoRespObject(response,AddSOProviderPartResponse.class);
	    if (logger.isInfoEnabled()){			
			
			logger.info("Leaving MobileGenericRequestProcessor"+ "getResponseForAddProviderInvoicePart()");
	     }
		// update response 
		addSOProviderPartService.updateSOMobileResponse(loggingId,response);
	    return addSOProviderPartResponse;
	}

	/**
	 * This method returns a response for the Adding supplier location details and part status
	 * @param soId
	 * @param providerId
	 * @return addProviderInvoicePartResponse
	 */
	
	@PUT
	@Path("/provider/{providerId}/serviceorder/{soId}/updateSOProviderPart")
	@Consumes({ "application/xml", "application/json","text/xml"})
	@Produces({ "application/xml", "application/json","text/xml"})
	public UpdateSOProviderPartResponse getResponseForUpdateProviderPart(@PathParam("providerId") String providerId,
			@PathParam("soId") String soId,
			UpdateSOProviderPartRequest updateSOProviderPart){
		if (logger.isInfoEnabled())
			logger.info("Entering MobileGenericRequestProcessor."+ "getResponseForUpdateProviderPart()");
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		UpdateSOProviderPartResponse updateSOProviderPartResponse = new UpdateSOProviderPartResponse();
		String request=convertReqObjectToXMLString(updateSOProviderPart,UpdateSOProviderPartRequest.class);
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Put);
		if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		apiVO.setSOId(soId);
		// log the request
		// get resource Id from vendor_resource	
		Integer resourceId=updateSOProviderPartService.validateResourceId(providerId);
		Integer loggingId=updateSOProviderPartService.logSOMobileHistory(request,PublicMobileAPIConstant.UPDATE_PROVIDER_PART, resourceId,soId,PublicMobileAPIConstant.HTTP_PUT);
        response = updateSOProviderPartService.doSubmit(apiVO);
        updateSOProviderPartResponse=(UpdateSOProviderPartResponse)convertXMLStringtoRespObject(response,UpdateSOProviderPartResponse.class);
	    if (logger.isInfoEnabled()){			
			logger.info("Leaving MobileGenericRequestProcessor"+ "getResponseForUpdateProviderPart()");
	     }
		// update response 
	    updateSOProviderPartService.updateSOMobileResponse(loggingId,response);
	    return updateSOProviderPartResponse;
	}
	

	/**
	 * This method returns a response for a get / retrieve service order
	 * details for completion request .
	 * 
	 * @param String providerId
	 * @param String soId
	 * @return Response
	 */
	@SuppressWarnings("unchecked")
	@PUT
	@Path("/provider/{providerId}/serviceorder/{soId}/deleteSOProviderPart")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public DeleteSOProviderPartResponse getDeleteSOProviderPartResponse(@PathParam("providerId") String providerId, @PathParam("soId")
	String soId,DeleteSOProviderPartRequest deleteSOProviderPartRequest) {

		if (logger.isInfoEnabled())
			logger.info("Entering MobileGenericRequestProcessor."+ "getDeleteSOProviderPartResponse()");
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		DeleteSOProviderPartResponse deleteSOProviderPartResponse = new DeleteSOProviderPartResponse();
		String request=convertReqObjectToXMLString(deleteSOProviderPartRequest,DeleteSOProviderPartRequest.class);
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Put);
		if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		apiVO.setSOId(soId);
		// log the request
		// get resource Id from vendor_resource	
		Integer resourceId=deleteSOProviderPartService.validateResourceId(providerId);
		Integer loggingId=deleteSOProviderPartService.logSOMobileHistory(request,PublicMobileAPIConstant.DELETE_PART_INVOICE_INFO, resourceId,soId,PublicMobileAPIConstant.HTTP_PUT);
        response = deleteSOProviderPartService.doSubmit(apiVO);
        deleteSOProviderPartResponse=(DeleteSOProviderPartResponse)convertXMLStringtoRespObject(response,DeleteSOProviderPartResponse.class);
	    if (logger.isInfoEnabled()){			
			logger.info("Leaving MobileGenericRequestProcessor"+ "getDeleteSOProviderPartResponse()");
	     }
		// update response 
	    deleteSOProviderPartService.updateSOMobileResponse(loggingId,response);
	    return deleteSOProviderPartResponse;

	}
	
	@POST
	@Path("/provider/{providerId}/serviceorder/{soId}/findSupplierPart")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public FindSupplierResponse getFindSupplierResponse(@PathParam("providerId") String providerId, @PathParam("soId")
	String soId,FindSupplierRequest findSupplierRequest) {
		if (logger.isInfoEnabled())
			logger.info("Entering MobileGenericRequestProcessor."+ "getDeleteSOProviderPartResponse()");
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		FindSupplierResponse findSupplierResponse = new FindSupplierResponse();
		String request=convertReqObjectToXMLString(findSupplierRequest,FindSupplierRequest.class);
		apiVO.setRequestFromPostPut(request);
		apiVO.setRequestType(RequestType.Post);
		if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		apiVO.setSOId(soId);
		// log the request
		// get resource Id from vendor_resource	
		Integer resourceId=findSupplierService.validateResourceId(providerId);
		Integer loggingId=findSupplierService.logSOMobileHistory(request,PublicMobileAPIConstant.FIND_PART_SUPPLIER, resourceId,soId,PublicMobileAPIConstant.HTTP_POST);
		response = findSupplierService.doSubmit(apiVO);
		findSupplierResponse=(FindSupplierResponse)convertXMLStringtoRespObject(response,FindSupplierResponse.class);
		    if (logger.isInfoEnabled()){			
				logger.info("Leaving MobileGenericRequestProcessor"+ "getFindSupplierResponse()");
		     }
		//update response
		findSupplierService.updateSOMobileResponse(loggingId, response);
		return findSupplierResponse;
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/provider/{providerId}/serviceorder/{soId}/itemSearch")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public String getPartsDetailResponse(@PathParam("providerId") String providerId, @PathParam("soId")
	String soId) {
		if (logger.isInfoEnabled())
			logger.info("Entering MobileGenericRequestProcessor."+ "getPartsDetailResponse()");
		String response = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest()
				.getParameterMap());
		apiVO.setRequestType(BaseService.RequestType.Get);
		apiVO.setRequestFromGetDelete(reqMap);		
		apiVO.setResourceId(providerId);
		if(StringUtils.isNumeric(providerId)){
			apiVO.setProviderResourceId(Integer.parseInt(providerId));
		} else {
			apiVO.setProviderResourceId(0);
		}
		apiVO.setSOId(soId);

		// Accept Type
		if (getHttpRequest().getHeader("Accept") != null){
			apiVO.setAcceptHeader(getHttpRequest().getHeader("Accept"));
		}
		// log the request
		// get resource Id from vendor_resource	
		Integer resourceId=partDirectWrapperService.validateResourceId(providerId);
		Integer loggingId=partDirectWrapperService.logSOMobileHistory(reqMap.toString(),PublicMobileAPIConstant.PART_SEARCH_SERVICE, 
				resourceId,soId,PublicMobileAPIConstant.HTTP_GET);
		
		response = partDirectWrapperService.execute(apiVO);
		
	    if (logger.isInfoEnabled()){			
			logger.info("Leaving MobileGenericRequestProcessor"+ "getPartsDetailResponse()");
	     }
		//update response
		partDirectWrapperService.updateSOMobileResponse(loggingId, response);
		return response;
	}
	
	
	/**
	 * V2.0 of login API
	 * @param loginProviderRequest
	 * @return
	 */
	
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
		Integer resourceId=loginProviderService.getResourceId(loginProviderRequest.getUsername());
		Integer loggingId=loginProviderService.logSOMobileHistory
			(loginRequest,PublicMobileAPIConstant.AUTHENTICATE_USER_V2, resourceId,null,PublicMobileAPIConstant.HTTP_POST);
		
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
	
	/**
	 * API for checking mobile app version is valid or not
	 * @param AuthenticateMobileVersionRequest
	 * @return
	 */
	
	@POST
	@Path("/provider/validateMobileVersion")
	@Consumes( { "application/xml", "application/json", "text/xml" })
	@Produces( { "application/xml", "application/json", "text/xml" })
	public AuthenticateMobileVersionResponse getResponseForMobileVersionCheck(
			AuthenticateMobileVersionRequest authenticateMobileVersionRequest) {
		logger
				.info("Entering MobileGenericRequestProcessor.getResponseForMobileVersionCheck()");
		Map<String, String> requestMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		AuthenticateMobileVersionResponse authenticateMobileVersionResponse = new AuthenticateMobileVersionResponse();
		String authMobileVerRequest = convertReqObjectToXMLString(
				authenticateMobileVersionRequest, AuthenticateMobileVersionRequest.class);
		
		if(StringUtils.isBlank(authenticateMobileVersionRequest.getDeviceOS())){
			logger.warn("Mobile app DeviceOS is blank");
		}
		
		// SLT-4728 - iOS app  is sending the deviceId in request pay load but  android app  is sending the deviceName
		String deviceId;
		if (authenticateMobileVersionRequest.getDeviceName()!=null){
			deviceId=authenticateMobileVersionRequest.getDeviceName();
		}
		else{
			deviceId=authenticateMobileVersionRequest.getDeviceId();
		}
		
		MobileDeviceDataList mobileDeviceDataList = authenticateMobileVersionService.fetchMobileDevices(deviceId);
		// get resource Id from vendor_resource
		Integer resourceId=authenticateMobileVersionService.getResourceId(authenticateMobileVersionRequest.getUsername());
		// log the request
		Integer loggingId=authenticateMobileVersionService.logSOMobileHistory
			(authMobileVerRequest,PublicMobileAPIConstant.AUTHENTICATE_MOBILE_VERSION_CHECK, resourceId,null,PublicMobileAPIConstant.HTTP_POST);
		
		apiVO.setRequestFromPostPut(authMobileVerRequest);
		apiVO.setRequestType(RequestType.Post);
		apiVO.addProperties(PublicAPIConstant.REQUEST_MAP, requestMap);
		String responseXML = authenticateMobileVersionService.doSubmit(apiVO);
		authenticateMobileVersionResponse = (AuthenticateMobileVersionResponse) convertXMLStringtoRespObject(
				responseXML, AuthenticateMobileVersionResponse.class); 
		logger.info("Leaving MobileGenericRequestProcessor.getResponseForMobileVersionCheck()");
		// update response 
		authenticateMobileVersionService.updateSOMobileResponse(loggingId, responseXML);
	//	MobileDeviceDataList updateMobileAppVerReq = new MobileDeviceDataList();
		if (mobileDeviceDataList!=null){
		for (MobileDeviceData mobileDeviceData : mobileDeviceDataList.getMobileDeviceDataList()){
			if(mobileDeviceData.getDeviceId().equalsIgnoreCase(deviceId)){
				
				mobileDeviceData.setCurrentAppVersion(authenticateMobileVersionRequest.getCurrentAppVersion());
				mobileDeviceData.setDeviceOS(authenticateMobileVersionRequest.getDeviceOS());
			}
		}
		
		authenticateMobileVersionService.updateMobileDeviceAppVersion(mobileDeviceDataList);
		}
		return authenticateMobileVersionResponse;
	}
	
	@POST
	@Path("/provider/updateMobileAppVersion")
	@Consumes( { "application/xml", "application/json", "text/xml" })
	@Produces( { "application/xml", "application/json", "text/xml" })
	public UpdateMobileAppVersionResponse getResponseForMobileAPPVersionUpdate(
			UpdateMobileAppVersionRequest updateMobileAppVersionRequest) {
		logger
				.info("Entering MobileGenericRequestProcessor.getResponseForMobileAPPVersionUpdate()");
		Map<String, String> requestMap = CommonUtility
				.getRequestMap(getHttpRequest().getParameterMap());
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		UpdateMobileAppVersionResponse updateMobileAppVersionResponse = new UpdateMobileAppVersionResponse();
		String authMobileVerRequest = convertReqObjectToXMLString(
				updateMobileAppVersionRequest, UpdateMobileAppVersionRequest.class);		
		// get resource Id from vendor_resource
		Integer resourceId=updateMobileAppVersionService.getResourceId(updateMobileAppVersionRequest.getUsername());
		// log the request
		Integer loggingId=updateMobileAppVersionService.logSOMobileHistory
			(authMobileVerRequest,PublicMobileAPIConstant.UPDATE_MOBILE_VERSION_CHECK, resourceId,null,PublicMobileAPIConstant.HTTP_POST);
		
		apiVO.setRequestFromPostPut(authMobileVerRequest);
		apiVO.setRequestType(RequestType.Post);
		apiVO.addProperties(PublicAPIConstant.REQUEST_MAP, requestMap);
		String responseXML = updateMobileAppVersionService.doSubmit(apiVO);
		updateMobileAppVersionResponse = (UpdateMobileAppVersionResponse) convertXMLStringtoRespObject(
				responseXML, UpdateMobileAppVersionResponse.class); 
		logger.info("Leaving MobileGenericRequestProcessor.getResponseForMobileAPPVersionUpdate()");
		// update response 
		updateMobileAppVersionService.updateSOMobileResponse(loggingId, responseXML);
		
		return updateMobileAppVersionResponse;
	}
	
	/**
	 * @param firmId
	 * @param providerId
	 * @param feedbackRequest
	 * @return
	 * 
	 * API to log feedback from mobile
	 * 
	 */
	@POST
	@Path("/firm/{firmId}/provider/{providerId}/feedback")
	@Consumes( { "application/xml", "application/json", "text/xml" })
	@Produces( { "application/xml", "application/json", "text/xml" })
	public FeedbackResponse getResponseForProviderFeedback(
			@PathParam("firmId")
			String firmId,
			@PathParam("providerId") String providerId, 
			FeedbackRequest feedbackRequest) {
		
		logger.info("Entering MobileGenericRequestProcessor.getResponseForProviderFeedback()");
		APIRequestVO apiVO = null;
		FeedbackResponse feedbackResponse = null;
		String response =null;
		String request=null;
		Integer resourceId = null;
		Integer loggingId = null;
		try{
			
			apiVO = new APIRequestVO(getHttpRequest());
			feedbackResponse = new FeedbackResponse();
			request = convertReqObjectToXMLString(feedbackRequest,FeedbackRequest.class);
			apiVO.setRequestFromPostPut(request);
			apiVO.setRequestType(RequestType.Post);
			
			if(StringUtils.isNumeric(providerId)){
				apiVO.setProviderResourceId(Integer.parseInt(providerId));
			} else {
				apiVO.setProviderResourceId(0);
			}
			// Check for numeric should go here
			apiVO.setProviderId(firmId);
			
			// log the request
			// get resource Id from vendor_resource	
			resourceId = feedbackService.validateResourceId(providerId);
			loggingId = feedbackService.logSOMobileHistory(request,PublicMobileAPIConstant.APP_FEEDBACK, resourceId,null,PublicMobileAPIConstant.HTTP_POST);
			response = feedbackService.doSubmit(apiVO);
			if (logger.isInfoEnabled()){			
					logger.info("Leaving MobileGenericRequestProcessor"+ "getResponseForProviderFeedback()");
			     }
			feedbackResponse=(FeedbackResponse)convertXMLStringtoRespObject(response,FeedbackResponse.class);
			//update response
			feedbackService.updateSOMobileResponse(loggingId, response);
			
		}catch(Exception e){
			// Exception handling code should go here with error setting in response. 
			feedbackResponse.setResults(Results.getError(
					ResultsCode.INTERNAL_SERVER_ERROR.getMessage(),
					ResultsCode.INTERNAL_SERVER_ERROR.getCode()));
			logger.info("Exception in getResponseForProviderFeedback():"+e.getMessage());
		}
		return feedbackResponse;
	}
	
	
	public ProviderUploadDocumentService getProviderUploadDocumentService() {
		return providerUploadDocumentService;
	}

	public void setProviderUploadDocumentService(
			ProviderUploadDocumentService providerUploadDocumentService) {
		this.providerUploadDocumentService = providerUploadDocumentService;
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
	
	public MobileTimeOnSiteService getTimeOnSiteService() {
		return timeOnSiteService;
	}

	public void setTimeOnSiteService(MobileTimeOnSiteService timeOnSiteService) {
		this.timeOnSiteService = timeOnSiteService;
	}

	public SORevisitNeededService getRevisitNeededService() {
		return revisitNeededService;
	}

	public void setRevisitNeededService(SORevisitNeededService revisitNeededService) {
		this.revisitNeededService = revisitNeededService;
	}
	

	
	public SOCompletionDetailsRetrieveService getSoCompletionDetailsRetrieveService() {
		return soCompletionDetailsRetrieveService;
	}

	public void setSoCompletionDetailsRetrieveService(
			SOCompletionDetailsRetrieveService soCompletionDetailsRetrieveService) {
		this.soCompletionDetailsRetrieveService = soCompletionDetailsRetrieveService;
	}
	
	public LoginProviderService getLoginProviderService() {
		return loginProviderService;
	}
	
	
	
	/**
	 * @return the soDetailsRetrieveService
	 */
	public SODetailsRetrieveService getSoDetailsRetrieveService() {
		return soDetailsRetrieveService;
	}


	/**
	 * @param soDetailsRetrieveService the soDetailsRetrieveService to set
	 */
	public void setSoDetailsRetrieveService(
			SODetailsRetrieveService soDetailsRetrieveService) {
		this.soDetailsRetrieveService = soDetailsRetrieveService;
	}


	public void setLoginProviderService(LoginProviderService loginProviderService) {
		this.loginProviderService = loginProviderService;
	}


	public AddSOProviderPartService getAddSOProviderPartService() {
		return addSOProviderPartService;
	}


	public void setAddSOProviderPartService(
			AddSOProviderPartService addSOProviderPartService) {
		this.addSOProviderPartService = addSOProviderPartService;
	}


	public AddInvoiceSOProviderPartService getAddInvoiceSOProviderPartService() {
		return addInvoiceSOProviderPartService;
	}


	public void setAddInvoiceSOProviderPartService(
			AddInvoiceSOProviderPartService addInvoiceSOProviderPartService) {
		this.addInvoiceSOProviderPartService = addInvoiceSOProviderPartService;
	}


	public DeleteSOProviderPartService getDeleteSOProviderPartService() {
		return deleteSOProviderPartService;
	}


	public void setDeleteSOProviderPartService(
			DeleteSOProviderPartService deleteSOProviderPartService) {
		this.deleteSOProviderPartService = deleteSOProviderPartService;
	}


	public UpdateSOProviderPartService getUpdateSOProviderPartService() {
		return updateSOProviderPartService;
	}


	public void setUpdateSOProviderPartService(
			UpdateSOProviderPartService updateSOProviderPartService) {
		this.updateSOProviderPartService = updateSOProviderPartService;
	}


	public FindSupplierService getFindSupplierService() {
		return findSupplierService;
	}


	public void setFindSupplierService(FindSupplierService findSupplierService) {
		this.findSupplierService = findSupplierService;
	}


	public FeedbackService getFeedbackService() {
		return feedbackService;
	}


	public void setFeedbackService(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}


	public AuthenticateMobileVersionService getAuthenticateMobileVersionService() {
		return authenticateMobileVersionService;
	}


	public void setAuthenticateMobileVersionService(
			AuthenticateMobileVersionService authenticateMobileVersionService) {
		this.authenticateMobileVersionService = authenticateMobileVersionService;
	}


	public UpdateMobileAppVersionService getUpdateMobileAppVersionService() {
		return updateMobileAppVersionService;
	}


	public void setUpdateMobileAppVersionService(
			UpdateMobileAppVersionService updateMobileAppVersionService) {
		this.updateMobileAppVersionService = updateMobileAppVersionService;
	}


	public PartDirectWrapperService getPartDirectWrapperService() {
		return partDirectWrapperService;
	}


	public void setPartDirectWrapperService(
			PartDirectWrapperService partDirectWrapperService) {
		this.partDirectWrapperService = partDirectWrapperService;
	}

	


}
