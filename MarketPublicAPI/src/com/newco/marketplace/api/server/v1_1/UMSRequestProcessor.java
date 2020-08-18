/*
 *	Date        Project       Author         Version
 * -----------  --------- 	-----------  	---------
 * 25-Sept-2009	pgangra   SHC				1.0
 * 
 * 
 */

package com.newco.marketplace.api.server.v1_1;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.abdera.util.XmlUtil;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.account.provider.CreateProviderAccountRequest;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.services.BaseService;
import com.newco.marketplace.api.services.account.buyer.CreateBuyerAccountService;
import com.newco.marketplace.api.services.account.buyer.GetBuyerAccountService;
import com.newco.marketplace.api.services.account.buyer.ModifyBuyerAccountService;
import com.newco.marketplace.api.services.account.provider.CreateProviderAccountService;
import com.newco.marketplace.api.services.login.LoginService;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.api.utils.validators.RequestValidator;
import com.newco.marketplace.api.utils.validators.ResponseValidator;
import com.sears.os.service.ServiceConstants;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * This class would act as an intercepter for all the user-management APIs in
 * the application. This class will be used for creating, modifying BuyerAccounts.
 */
@Path("v1.1")
public class UMSRequestProcessor {

	private Logger logger = Logger.getLogger(UMSRequestProcessor.class);
	protected RequestValidator requestValidator;
	protected ResponseValidator responseValidator;

	protected CommonUtility commonUtility;
	protected CreateBuyerAccountService createBuyerAccountService;
	protected ModifyBuyerAccountService modifyBuyerAccountService;
	protected GetBuyerAccountService getBuyerAccountService;
	protected LoginService loginService;
	// Required for retrieving attributes from Get URL
	@Resource
	protected HttpServletRequest httpRequest;
	//protected MediaType mediaType = MediaType.parse("text/xml");
	final protected String mediaType = MediaType.TEXT_XML;
	/**
	 * This method creates a buyer account
	 * 
	 * @param String buyerAcc
	 * @return Response URL: /buyers
	 */
	@POST
	@Path("/buyers")
	public Response createBuyer(String requestXML) {

		if (logger.isInfoEnabled()) {
			logger.info("CreateBuyerAccount method started");
		}
				APIRequestVO apiVO = new APIRequestVO(httpRequest);
		apiVO.setRequestFromPostPut(requestXML);
		apiVO.setRequestType(BaseService.RequestType.Post);
		String responseXML = createBuyerAccountService.doSubmit(apiVO);
		return Response.ok(responseXML, mediaType).build();
	}

/*	
	 * This method creates a provider account
	 * Code Added 643272 date 03-09-2013 Start
	 * @param String providerAcc
	 * @return Response URL: /providers
	 *
	@POST
	@Path("/providercreate")
	public Response createProvider(String requestXML) {

		if (logger.isInfoEnabled()) {
			logger.info("CreateproviderAccount method started");
		}
			APIRequestVO apiVO = new APIRequestVO(httpRequest);				
		apiVO.setRequestFromPostPut(requestXML);
		apiVO.setRequestType(BaseService.RequestType.Post);		
		String responseXML = createProviderAccountService.doSubmit(apiVO);		
		return Response.ok(responseXML, mediaType).build();
	}
	
	
	Code Added 643272 date 03-09-2013 End*/
	
	/**
	 * This method calls the login method
	 * 
	 * @param String logindetails
	 * @return Response 
	 */
	@POST
	@Path("/users/authentication")
	public Response getResponseForLogin(String requestXML) {
		System.out.println("login");
		if (logger.isInfoEnabled()) {
			logger.info("API Login");
		}
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(requestXML);
		apiVO.setRequestType(BaseService.RequestType.Post);
		String responseXML = loginService.doSubmit(apiVO);
		return Response.ok(responseXML, mediaType).build();
	}
	
	
	
	/**
	 * This method modifies buyer account
	 * 
	 * @param String buyerAcc
	 * @return Response URL: /buyers/{buyerId}/buyerresources/{buyerResourceId}
	 */
	@PUT
	@Path("/buyers/{buyerId}/buyerresources/{buyerResourceId}")
	public Response modifyBuyerAccount(String requestXML, @PathParam("buyerId") String buyerId,
			@PathParam("buyerResourceId") String buyerResourceId) { 
		
		if (logger.isInfoEnabled()) {
			logger.info("CreateBuyerAccount method started");
		}
			APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromPostPut(requestXML);
		apiVO.setRequestType(BaseService.RequestType.Put);
		apiVO.setBuyerId(buyerId);
		apiVO.addProperties(APIRequestVO.BUYER_RESOURCE_ID, buyerResourceId);
		String responseXML = modifyBuyerAccountService.doSubmit(apiVO);
		return Response.ok(responseXML, mediaType).build();
	}
/**
	 * 
	 * This method returns the buyer account details
	 * 
	 * @return Response URL: /buyers/<buyerid>/buyerresources/{buyerResourceId}
	 */
	@GET
	@Path("/buyers/{buyer_id}/buyerresources/{buyer_resource_id}")
	public Response getBuyerAccount(@PathParam("buyer_id") String buyerId, @PathParam("buyer_resource_id") String buyerResourceId) {

		if (logger.isInfoEnabled()) {
			logger.info("getBuyerAccount method started");
		}
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(BaseService.RequestType.Get);
		apiVO.addProperties(APIRequestVO.BUYER_RESOURCE_ID, buyerResourceId);
		apiVO.setBuyerId(buyerId);
		String responseXML = getBuyerAccountService.doSubmit(apiVO);
		return Response.ok(responseXML, mediaType).build();
	}

	public RequestValidator getRequestValidator() {
		return requestValidator;
	}

	public void setRequestValidator(RequestValidator requestValidator) {
		this.requestValidator = requestValidator;
	}

	public ResponseValidator getResponseValidator() {
		return responseValidator;
	}

	public void setResponseValidator(ResponseValidator responseValidator) {
		this.responseValidator = responseValidator;
	}

	public CommonUtility getCommonUtility() {
		return commonUtility;
	}

	public void setCommonUtility(CommonUtility commonUtility) {
		this.commonUtility = commonUtility;
	}

	public CreateBuyerAccountService getCreateBuyerAccountService() {
		return createBuyerAccountService;
	}

	public void setCreateBuyerAccountService(
			CreateBuyerAccountService createBuyerAccountService) {
		this.createBuyerAccountService = createBuyerAccountService;
	}

	public ModifyBuyerAccountService getModifyBuyerAccountService() {
		return modifyBuyerAccountService;
	}

	public void setModifyBuyerAccountService(
			ModifyBuyerAccountService modifyBuyerAccountService) {
		this.modifyBuyerAccountService = modifyBuyerAccountService;
	}

	public GetBuyerAccountService getGetBuyerAccountService() {
		return getBuyerAccountService;
	}

	public void setGetBuyerAccountService(
			GetBuyerAccountService getBuyerAccountService) {
		this.getBuyerAccountService = getBuyerAccountService;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
	
}
