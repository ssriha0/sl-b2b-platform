package com.newco.marketplace.api.server.v1_1;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.search.firms.SearchFirmsRequest;
import com.newco.marketplace.api.beans.search.firms.SearchFirmsResults;
import com.newco.marketplace.api.beans.search.providerProfile.ProviderFirmResults;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.server.BaseRequestProcessor;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.interfaces.OrderConstants;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;



/**
 * This class would act as an intercepter for all the Search APIs in the
 * application. It handles following APIS:
 * /marketplace/categories
 * /providers/zip
 * /providers?idlist
 * /serviceproviders/{providerId}/reviews
 * /resources/{resourceId}/serviceproviders/reviews
 *
 * Spring Bean Name : This is not a spring bean. It is call by CFX framework using reflection.
 *
 * @author Infosys & Shekhar Nirkhe
 * @since 08/25/2009
 * @version 1.0
 */

//
@Path("v1.1")
public class SearchRequestProcessor extends BaseRequestProcessor{
	private Logger logger = Logger.getLogger(SearchRequestProcessor.class);
	//final protected MediaType mediaType = MediaType.parse("text/xml");
	final protected String mediaType = MediaType.TEXT_XML;
	
	@Resource
	private HttpServletRequest httpRequest;

	/**
	 * This method returns a list of Providers based on the skillTree search.
	 * URL: /marketplace/categories?keyword=XXXX
	 *	 keyword = < any key word >
	 *
	 * URL Example:  /marketplace/categories?keyword=paint
	 * Response XSD : skillTreeResponse.xsd
	 * Service Class : SearchProviderByskillTreeService.java
	 *
	 *  @return Response : The response will either return list of matched categories or will return an
	 * empty list with result code 0000.
	 *
	 *
	 *
	 */
	@GET
	@Path("/marketplace/categories")
	public Response getProvidersBySkillTree() {
		logger.info("Entering SearchRequestProcessor.getResponseForSkillTreeSearch()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());

		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setRequestType(RequestType.Get);

		String skillTreeResponse = (String)providerByskillTreeService.doSubmit(apiVO);


		logger.info("Exiting SearchRequestProcessor.getResponseForSkillTreeSearch()");

		return Response.ok(	skillTreeResponse, mediaType).build();

	}

	/**
	 * This method returns a list of Providers based on the ZipCode search
	 * @return Response
	 * URL: /providers/zip?zip=x&idlist=123-456&categoryid=x&servicetype=z&language=x&maxdistance=x&minrating=x&resultMode=x&pageSize=x&pageNumber=x&sortby=rating&sortorder=asc&companyId=XXX&&favoriteList=XXXX
	 *
	 * sortby = distance or rating (default distance)
	 * sortorder = asc or desc (default desc)
	 * pageNumber: Integer (starting from 1)
	 * servicetype: String (No checks so far, Valid values are Delivery, Installation, Repair, Maintenance, Estimates, Training)
	 * language: String
	 * maxdistance: Integer
	 * resultMode: (Minimum, Medium,  Large, All)
	 * pageSize: Integer starting form 1
	 * sortBy : rating, distance
	 * SortOrder : Asc, Desc
	 * city
	 * state
	 *
	 * URL Example  : providers/zip?zip=95050&maxdistance=10&PageSize=25&pageNumber=1&language=English&resultMode=Minimum&&favoriteList=19847-21952
	 * Response XSD : providerResults.xsd
	 * Service Class : SearchProviderByZipCodeService
	 */

	@GET
	@Path("/providers/zip")
	public Response getProvidersByZipCode() {
		logger.info("Entering SearchRequestProcessor.getProviderByZipCode() Method");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> zipMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());

		apiVO.setRequestFromGetDelete(zipMap);
		apiVO.setRequestType(RequestType.Get);

		String providerResponse = (String)zipCodeService.doSubmit(apiVO);

		logger.info("Exiting SearchRequestProcessor.getProviderByZipCode()");
		return Response.ok(providerResponse, mediaType).build();
	}

	/**
	 * This method returns a list of Providers based on the ProfileId search
	 * @return Response
	 * URL : /providers?idlist=123-456&zip=x
	 * idlist :  Provider ids separated by -. e.g. 1123-5465-3562
	 * zip : ZIP of the user to calculate distance. Remember if the provider id does not fall in
	 *       max radius(500 miles) of the zip it won't return the provider.
	 *
	 * URL Example  : /providers?idlist=19500-19501&zip=95050
	 * Response XSD : providerResults.xsd
	 * Service Class : SearchProviderByIdService
	 *
	 */

	@GET
	@Path("/providers")
	public Response getProvidersByProfileId() {
		logger.info("Entering getProviderProfileById Method");
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		return getProviders(reqMap);
	}

	/** due to bug in CXF I have to parse path myself.
	 *
	 * @param state
	 * @return
	 */
	@GET
	@Path(value="/providers/us/{partUrl}") 
	public Response getProvidersByLocation(@PathParam("partUrl")String partUrl) {
		String state = null,  city = null, skill = null;
		logger.info("Entering getProvidersByLocation Method");
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
	
		if (partUrl != null) {
			String []urlArr = partUrl.split("/");		
			
			state = getState(urlArr, true);
			city = getCity(urlArr, true);
			skill = getSkill(urlArr, true);
			String lang = getLang(urlArr, true);
			if (lang != null)
				reqMap.put("language", lang);
			String rr = getRating(urlArr, true);
			if (rr != null) {
				String t [] = rr.split("-");
				reqMap.put("minrating", t[0]);
				if (t.length > 1)
					reqMap.put("maxrating", t[1]);
			}
		}
		
		reqMap.put("state", state);
		reqMap.put("city", city);
		String providerResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setRequestType(RequestType.Get);
		
		if (skill != null) {
			reqMap.put("categoryName", skill);
			providerResponse = (String)zipCodeService.doSubmit(apiVO);
		} else {			
			providerResponse = (String)providerCountService.doSubmit(apiVO);
		}

		logger.info("Exiting SearchRequestProcessor.getProvidersByLocation()");
		return Response.ok(providerResponse, mediaType).build();
	}
	
	private String getState(String []urlArr, boolean byLocation) {
		int index = 1;
		if (byLocation)
			index = 0;
		
		if (urlArr.length > index) {
			return format(urlArr[index]);
		}
		
		return  null;	
	}
	
	private String getCity(String []urlArr, boolean byLocation) {
		int index = 2;
		if (byLocation)
			index = 1;
		
		if (urlArr.length > index)
			return format(urlArr[index]);
		return  null;
		
	}
	
	private String getSkill(String []urlArr, boolean byLocation) {
		int index = 0;
		if (byLocation)
			index = 2;
	
		if (urlArr.length > index)
			return format(urlArr[index]);
		return  null;
	}
	

	private String format(String param) {
		if (param != null) {
			param = param.replaceAll("-", " ");
		}
		return param;		
	}
	
	private String getLang(String []urlArr, boolean byLocation) {
		int index = 4;
		
		if (urlArr.length > index) {
			String param =  urlArr[index]; 
			String temp = urlArr[index -1]; //no break
			if (temp.equalsIgnoreCase("language") && param != null) {
				return param;
			} 
		}
		
		if (urlArr.length > index + 2) {
			String param =  urlArr[index + 2]; 
			String temp = urlArr[index + 1]; //no break
			if (temp.equalsIgnoreCase("language") && param != null) {
				return param;
			} 
		}
		return null;
	}
	
	private String getRating(String []urlArr, boolean byLocation) {
		int index = 4;
		if (urlArr.length > index) {
			String param =  urlArr[index]; 
			String temp = urlArr[index - 1]; //no break
			if (temp.equalsIgnoreCase("rating") && param != null) {
				return param;
			}			
		}
		
		if (urlArr.length > index + 2) {
			String param =  urlArr[index + 2]; 
			String temp = urlArr[index + 1]; //no break
			if (temp.equalsIgnoreCase("rating") && param != null) {
				return param;
			}			
		}
		return null;
	}
	

	/** due to bug in CXF I have to parse path myself.
	 *
	 * @param state
	 * @return
	 */
	@GET
	@Path(value="/providers/category/{partUrl}")
	public Response getProvidersByCategory(@PathParam("partUrl")String partUrl) {
		String state = null,  city = null, skill = null;
		logger.info("Entering getProvidersByCategory Method");
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		
		if (partUrl != null) {
			String []urlArr = partUrl.split("/");		
			
			state = getState(urlArr, false);
			city = getCity(urlArr, false);
			skill = getSkill(urlArr, false);
			String lang = getLang(urlArr, false);
			if (lang != null)
				reqMap.put("language", lang);
			String rr = getRating(urlArr, false);
			if (rr != null) {
				String t [] = rr.split("-");
				reqMap.put("minrating", t[0]);
				if (t.length > 1)
					reqMap.put("maxrating", t[1]);
			}
		}
		
		
		reqMap.put("state", state);
		reqMap.put("city", city);
		reqMap.put("categoryName", skill);
		String providerResponse = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setRequestType(RequestType.Get);
		
		if (city != null) {
			providerResponse = (String)zipCodeService.doSubmit(apiVO);
		} else {			
			providerResponse = (String)providerCountService.doSubmit(apiVO);
		}

		logger.info("Exiting SearchRequestProcessor.getProvidersByLocation()");
		return Response.ok(providerResponse, mediaType).build();
	}
   /*
	@GET
	@Path("providers/us1/{state}/{city}")
	public Response getProvidersByLocation2(@PathParam("state") String state, @PathParam("city") String city) {
		logger.info("Entering getProvidersByLocation Method");
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		reqMap.put("state", state);
		reqMap.put("city", city);

		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setRequestType(RequestType.Get);
		String providerResponse = (String)providerCountService.doSubmit(apiVO);

		logger.info("Exiting SearchRequestProcessor.getProvidersByLocation()");
		return Response.ok(providerResponse, mediaType).build();
	}  */

	private Response getProviders(Map<String, String> reqMap) {
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setRequestType(RequestType.Get);

		String providerResponse= null;

		providerResponse = (String)providerByIdService.doSubmit(apiVO);

		logger.info("Exiting SearchRequestProcessor.getProviderProfileById()");
		return Response.ok(providerResponse, mediaType).build();
	}

	/**
	 * This method returns a list of mock Providers
	 * @return Response
	 * URL: /training/marketplace/categories?keyword=paint
	 */
	@GET
	@Path("/training/marketplace/categories")
	public Response getMockProvidersBySkillTree() {
		logger.info("Entering SearchRequestProcessor.getResponseForSkillTreeSearch()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		String keyword = (String) reqMap.get(PublicAPIConstant.KEYWORD);

		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setRequestType(RequestType.Get);

		String skillTreeResponse = mockProviderByskillTreeService.doSubmit(apiVO);
		logger.debug("Skill Tree Response : \n" + skillTreeResponse);
		responseValidator.validateResponseXML(skillTreeResponse,
									PublicAPIConstant.SEARCH_RESOURCES_SCHEMAS,
									PublicAPIConstant.SKILLTREE_XSD);
		logger.info("Exiting SearchRequestProcessor.getResponseForSkillTreeSearch()");
		return Response.ok(skillTreeResponse, mediaType).build();
	}

	/**
	 * This method returns a list of mock Providers
	 * @return Response
	 * URL: /training/providers/zip?zip=x&categoryid=x&servicetype=z&language=x&maxdistance=x&minrating=x&resultMode=x&pageSize=x&pageNumber=x
	 */
	@GET
	@Path("/training/providers/zip")
	public Response getMockProvidersByZipCode() {
		logger.info("Entering SearchRequestProcessor.getProviderByZipCode() Method");
		Map<String, String> zipMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		return Response.ok("providerResponse", mediaType).build();
	}



	/**
	 * This method returns a list of mock Providers
	 * @return Response
	 * URL: /providers?idlist=123-456
	 */
	@GET
	@Path("/training/providers")
	public Response getMockProvidersByProfileId() {
		logger.info("Entering getProviderProfileById Method");
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		Object idValue = reqMap.get(PublicAPIConstant.IDLIST);
		String[] profileIdArray = (String[]) idValue;
		String profileIds = null;
		String providerResponse = null;
		if (profileIdArray.length > 0) {
			profileIds = profileIdArray[0];
		}
		if (profileIds != null) {
		} else {
			providerResponse =
				responseValidator.getFailureResponse(ResultsCode.INVALIDXML_ERROR_CODE.getMessage() + ": providerIds",
						ResultsCode.INVALIDXML_ERROR_CODE.getCode());
		}
		logger.debug("Provider Response By ProfleId: \n" + providerResponse);
		responseValidator.validateResponseXML(providerResponse,
				PublicAPIConstant.SEARCH_RESOURCES_SCHEMAS,
				PublicAPIConstant.PROVIDERRESULT_XSD);
		logger.info("Exiting SearchRequestProcessor.getProviderProfileById()");
		return Response.ok(providerResponse, mediaType).build();
	}

	/**
	 * This method returns a list of mock Providers
	 * @return Response
	 * URL: /serviceproviders/<providerid>/reviews?beginDate=<mmddyyyy>&endDate=<mmddyyyy>&sort=<rating, date>&order=<asc, desc>&pageSize=<size>&pageNum=<num>
	 *
	 * providerId : providerId. This method wont validate providerId. The service class should add
	 * 	addRequiredURLParam method in the constructor to validate the URL parameters.
	 *
	 * URL Example:  /providers/10065/reviews?sort=rating&order=asc
	 *
	 * Response XSD  : providerReviewsResponse.xsd
	 * Service Class : ProviderReviewsService
	 *
	 */
	@GET
	@Path("/providers/{resource_id}/reviews")
	public Response getProviderReviews(@PathParam("resource_id") String providerResourceId) {
		logger.info("Entering getProviderReviews Method");
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.addProperties(APIRequestVO.PROVIDER_RESOURCE_ID, providerResourceId);
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setRequestType(RequestType.Get);
		String responseXML = providerReviewsService.doSubmit(apiVO);
		return Response.ok(responseXML, mediaType).build();
	}

	/**
	 * This method add provider reviews
	 * @param
	 * @return Response
	 * URL: /resources/{resourceId}/serviceproviders/reviews
	 *
	 */
	@POST
	@Path("buyers/{buyer_id}/serviceorders/{soid}/reviews/")
	public Response addProviderReview(@PathParam("buyer_id") String buyerId, String requestXML) {
		logger.info("Entering SearchRequestProcessor.addProviderReview()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.addProperties("RoleType", OrderConstants.SIMPLE_BUYER_ROLEID);
		apiVO.setRequestFromPostPut(requestXML);
		apiVO.setRequestType(RequestType.Post);
		apiVO.setBuyerId(buyerId);
		String responseXML = addProviderReviewService.doSubmit(apiVO);
		return Response.ok(responseXML, mediaType).build();
	}

	/**
	 * This method add buyer reviews
	 * @param
	 * @return Response
	 * URL: /resources/{resourceId}/serviceproviders/reviews
	 *
	 */
	@POST
	@Path("/providers/{providerId}/serviceorders/{soId}/reviews")
	public Response addBuyerReview(@PathParam("providerId") String providerId, String requestXML) {
		logger.info("Entering SearchRequestProcessor.addProviderReview()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.addProperties("RoleType", OrderConstants.PROVIDER_ROLEID);
		apiVO.setRequestFromPostPut(requestXML);
		apiVO.setRequestType(RequestType.Post);
		apiVO.setProviderId(providerId);
		String responseXML = addProviderReviewService.doSubmit(apiVO);
		return Response.ok(responseXML, mediaType).build();
	}

	/**
	 * This method for search template
	 * @param
	 * @return Response
	 * URL: /resources/{resourceId}/serviceproviders/reviews
	 *
	 */
	@GET
	@Path("/buyers/{buyerId}/search/templates")
	public Response getResponseForSearchTemplate(@PathParam("buyerId") Integer buyerId) {
		logger.info("Entering SearchRequestProcessor.getResponseForSearchTemplate()");
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(RequestType.Get);
		apiVO.setBuyerId(buyerId.toString());
		String responseXML = soSearchTemplateService.doSubmit(apiVO);
		return Response.ok(responseXML, mediaType).build();
	}
	
	/**
	 * Search providers firms for standard service offerings
	 * @param
	 * @return Response
	 * URL: /buyers/{buyerId}/search/firms
	 *
	 */
	@SuppressWarnings("unchecked")
	@POST
	@Path("/buyers/{buyerId}/searchfirms")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public SearchFirmsResults getResponseForSearchFirms(@PathParam("buyerId") Integer buyerId, SearchFirmsRequest getSearchFirmsRequest) {
		String firmDetailsXML = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		String searchFirmsRequest = convertReqObjectToXMLString(getSearchFirmsRequest, SearchFirmsRequest.class); 
		apiVO.setRequestFromPostPut(searchFirmsRequest);
		apiVO.setRequestType(RequestType.Post);
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.addProperties(PublicAPIConstant.REQUEST_MAP, reqMap);
		apiVO.setBuyerId(buyerId.toString());
		firmDetailsXML = searchFirmsService.doSubmit(apiVO);
		firmDetailsXML = PublicAPIConstant.XML_VERSION + firmDetailsXML;

		return (SearchFirmsResults) convertXMLStringtoRespObject(firmDetailsXML,
				SearchFirmsResults.class);
	}
	
	/** R 16_2_0_1: SL-21376- Search provider firms by zip and category and skill, SKU
	 * @param
	 * @return Response
	 * URL: /buyers/{buyerId}/searchproviders
	 *
	 */
	@SuppressWarnings("unchecked")
	@GET
	@Path("/buyers/{buyerId}/searchfirms")
	@Consumes({ "application/xml", "application/json", "text/xml" })
	@Produces({ "application/xml", "application/json", "text/xml" })
	public ProviderFirmResults getResponseForSearchProviders(@PathParam("buyerId") Integer buyerId) {
		String providerDetailsXML = null;
		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		apiVO.setRequestFromGetDelete(reqMap);
		apiVO.setRequestType(RequestType.Get);
		apiVO.addProperties(PublicAPIConstant.REQUEST_MAP, reqMap);
		apiVO.setBuyerId(buyerId.toString());
		providerDetailsXML = searchProvidersService.doSubmit(apiVO);
		providerDetailsXML = PublicAPIConstant.XML_VERSION + providerDetailsXML;

		return (ProviderFirmResults) convertXMLStringtoRespObject(providerDetailsXML,
				ProviderFirmResults.class);
		
	}
	
	//converting XML to Object and vice versa
	protected String convertReqObjectToXMLString(Object request, Class<?> classz) {
			XStream xstream = this.getXstream(classz);
			return xstream.toXML(request).toString();
	}
		
	protected Object convertXMLStringtoRespObject(String request,
			Class<?> classz) {
		XStream xstream = this.getXstream(classz);
		return (Object) xstream.fromXML(request);
	}

	protected XStream getXstream(Class<?> classz) {
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		xstream.registerConverter(new DateConverter(
				PublicAPIConstant.DATE_FORMAT_YYYY_MM_DD, new String[0]));
		xstream.addDefaultImplementation(java.sql.Date.class,
				java.util.Date.class);
		xstream.processAnnotations(classz);
		return xstream;
	}
	


	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}

}
