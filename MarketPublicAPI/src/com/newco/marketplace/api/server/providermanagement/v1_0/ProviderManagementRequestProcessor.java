package com.newco.marketplace.api.server.providermanagement.v1_0;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.account.provider.CreateProviderAccountRequest;
import com.newco.marketplace.api.beans.account.provider.CreateProviderAccountResponse;
import com.newco.marketplace.api.beans.search.provider.SearchProviderRequest;
import com.newco.marketplace.api.beans.search.provider.SearchProviderResponse;
import com.newco.marketplace.api.beans.search.provider.SearchProviderService;
import com.newco.marketplace.api.services.account.provider.CreateIPRProviderAccountService;
import com.newco.marketplace.api.services.account.provider.CreateProviderAccountService;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;


@Path("v1.0")
public class ProviderManagementRequestProcessor {
	
	private Logger logger = Logger.getLogger(ProviderManagementRequestProcessor.class);
	protected CreateProviderAccountService createProviderAccountService;
	//SL 16934 Service class for Provider Registration using IPR
	protected CreateIPRProviderAccountService createIPRProviderAccountService;
	protected SearchProviderService searchProviderService;	
	
		// Required for retrieving attributes from Get URL
	@Resource
	protected HttpServletRequest httpRequest;

	 /**
	 * @param createProviderAccountRequest
	 * 
	 * method for provider registration
	 * @return
	 */
	@POST
	  @Path("/provider/register")
	 // @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	 // @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	 @Consumes({ "application/xml", "application/json","text/xml"})
	 @Produces({ "application/xml", "application/json","text/xml"})
	  public CreateProviderAccountResponse getResponseForProvideRegister(CreateProviderAccountRequest createProviderAccountRequest) {
             
		    logger.info(createProviderAccountRequest);
			CreateProviderAccountResponse response = (CreateProviderAccountResponse)createProviderAccountService.execute(createProviderAccountRequest);	
			logger.info(response.toString());
			return response;
		}
		//SL 16934 API for Provider Registration using IPR
	 	 /**
		 * @param createProviderAccountRequest
		 * 
		 * method for provider registration using IPR
		 * @return
		 */
		@POST
		@Path("/provider/IPR/register")
		 @Consumes({ "application/xml", "application/json","text/xml"})
		 @Produces({ "application/xml", "application/json","text/xml"})
		  public CreateProviderAccountResponse getResponseForIPRProvideRegister(CreateProviderAccountRequest createProviderAccountRequest) {
	             
			    logger.info(createProviderAccountRequest);
				CreateProviderAccountResponse response = (CreateProviderAccountResponse)createIPRProviderAccountService.execute(createProviderAccountRequest);
				logger.info(response.toString());
				return response;
			}
		
	/**Web Service API for search provider/firm details
	 * @param createProviderAccountRequest
	 * 
	 * method for provider registration
	 * @return
	 */
	@POST
	@Path("/provider/search")
	//@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML })
	//@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
	@Consumes({ "application/xml", "application/json","text/xml"})
	@Produces({ "application/xml", "application/json","text/xml"})
	public SearchProviderResponse getResponseForProviderSearch(SearchProviderRequest searchProviderRequest) {
		
		logger.info(searchProviderRequest);
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		String responseFilter = reqMap.get("responsefilter");
		SearchProviderResponse response = searchProviderService.execute(searchProviderRequest, responseFilter);
		logger.info(response);
		return response;
	}
	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
	
	public SearchProviderService getSearchProviderService() {
		return searchProviderService;
	}

	public void setSearchProviderService(SearchProviderService searchProviderService) {
		this.searchProviderService = searchProviderService;
	}

	public CreateProviderAccountService getCreateProviderAccountService() {
		return createProviderAccountService;
	}

	public CreateIPRProviderAccountService getCreateIPRProviderAccountService() {
		return createIPRProviderAccountService;
	}

	public void setCreateIPRProviderAccountService(
			CreateIPRProviderAccountService createIPRProviderAccountService) {
		this.createIPRProviderAccountService = createIPRProviderAccountService;
	}

	public void setCreateProviderAccountService(
		CreateProviderAccountService createProviderAccountService) {
		this.createProviderAccountService = createProviderAccountService;
	}

	/**
		 * @param request
		 * @param classz
		 * @return
		 */
		private String getResponse(Object request, Class<?> classz){
			XStream xstream = this.getXstream(classz);		
			//String requestXml  = xstream.toXML(request).toString();
			return xstream.toXML(request).toString();
		}
		private XStream getXstream(Class<?> classz) {
			XStream xstream = new XStream(new DomDriver());
			xstream.registerConverter(new ResultConvertor());
			xstream.registerConverter(new ErrorConvertor());
			xstream.registerConverter(new DateConverter("yyyy-MM-dd", new String[0]));
			xstream.addDefaultImplementation(java.sql.Date.class, java.util.Date.class);
			xstream.processAnnotations(classz);
			return xstream;
		}
	
}