package com.newco.marketplace.api.server.v1_2;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.common.ResultsCode;
import com.newco.marketplace.api.services.BaseService.RequestType;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.api.utils.validators.ResponseValidator;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CGetFirmDetailsResponse;
import com.newco.marketplace.beans.d2c.d2cproviderportal.D2CPortalAPIVORequest;
import com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDateTimeSlots;
import com.newco.marketplace.dto.vo.dateTimeSlots.ServiceDatetimeSlot;
import com.newco.marketplace.vo.provider.TimeSlotDTO;



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
@Path("v1.2")
public class SearchRequestProcessor extends com.newco.marketplace.api.server.v1_1.SearchRequestProcessor{
	private Logger logger = Logger.getLogger(SearchRequestProcessor.class);
	private ResponseValidator responseValidator;
	
	// Required for retrieving attributes from Get URL
	@Resource
	private HttpServletRequest httpRequest;
	
	private static final String JSON_MEDIA_TYPE = "application/json";
	
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
		super.setHttpRequest(httpRequest);
		logger.info("Exiting SearchRequestProcessor.getResponseForSkillTreeSearch()");
		return(super.getProvidersBySkillTree());
		
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
	 * 
	 * URL Example  : providers/zip?zip=95050&maxdistance=10&PageSize=25&pageNumber=1&language=English&resultMode=Minimum&&favoriteList=19847-21952
	 * Response XSD : providerResults.xsd
	 * Service Class : SearchProviderByZipCodeService
	 */
	
	@GET
	@Path("/providers/zip")
	public Response getProvidersByZipCode() {
		logger.info("Entering SearchRequestProcessor.getProviderByZipCode() Method");
		super.setHttpRequest(httpRequest);
		return(super.getProvidersByZipCode());
		
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
		super.setHttpRequest(httpRequest);
		return(super.getProvidersByProfileId());
	}

	/**
	 * This method returns a list of mock Providers
	 * @return Response
	 * URL: /training/marketplace/categories?keyword=paint
	 */
	@GET
	@Path("/training/marketplace/categories")
	public Response getMockProvidersBySkillTree() {
		super.setHttpRequest(httpRequest);
		return(super.getMockProvidersBySkillTree());
		
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
		Map<String, String> zipMap = CommonUtility.getRequestMap(httpRequest.getParameterMap());		
		return Response.ok("providerResponse").build();
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
		Map<String, String> reqMap = CommonUtility.getRequestMap(httpRequest.getParameterMap());
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
		return Response.ok(providerResponse).build();
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
		super.setHttpRequest(httpRequest);
		return(super.getProviderReviews(providerResourceId));
		
	}
	@GET
	@Path("/buyers/{buyerId}/search/templates")
	public Response getResponseForSearchTemplate(@PathParam("buyerId") Integer buyerId) {		
		logger.info("Entering SearchRequestProcessor.getResponseForSearchTemplate()");
		super.setHttpRequest(httpRequest);
		return(super.getResponseForSearchTemplate(buyerId));
		
	}

	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
	
	// --------------------------------------------------------------------
	
	private TimeSlotDTO validatePrefDateTime(String prefStartDateTime1, String prefEndDateTime1) throws ParseException {
		
		// (CommonUtility.sdfToDate) = "yyyy-MM-dd'T'HH:mm:ss";
		try {
			(CommonUtility.sdfToDate).parse(prefStartDateTime1);
		} catch (ParseException e) {
			throw new ParseException("WRONG START DateTime format.", e.getErrorOffset());
		}
		
		try {
			(CommonUtility.sdfToDate).parse(prefEndDateTime1);
		} catch (ParseException e) {
			throw new ParseException("WRONG END DateTime format.", e.getErrorOffset());
		}
		
		return (new TimeSlotDTO((CommonUtility.sdfToDate).parse(prefStartDateTime1), (CommonUtility.sdfToDate).parse(prefEndDateTime1)));
	}
	
	private D2CGetFirmDetailsResponse getError(String msg, String code) {
		D2CGetFirmDetailsResponse firmDetailsXML = new D2CGetFirmDetailsResponse();
		Results results = new Results();
		results = Results.getError(msg, code);
		firmDetailsXML.setResults(results);
		return firmDetailsXML;
	}
	
	@POST
	@Path("/buyers/{buyer_id}/skuId/{skuId}/zipCode/{zipCode}")
	@Produces({ "application/json" })
	public D2CGetFirmDetailsResponse getFirmsWithRetailPriceBySKUGenericBuyer(
			@PathParam("buyer_id") String buyerId,
			@PathParam("skuId") String skuId,
			@PathParam("zipCode") String zipCode,
			@QueryParam("date") String date,
			@QueryParam("firmId") String firmId,
			@QueryParam("count") Integer count,
			@QueryParam("marketIndexFlag") Boolean marketIndexFlag,
			String serviceDatetimeSlotsString) {
		
		logger.info("Inside getFirmsWithRetailPriceBySKUGenericBuyer()");
		
		// logger.debug("Content type is : " + content);

		/*if (!StringUtils.isEmpty(getHttpRequest().getContentType())) {
			String content = getHttpRequest().getContentType().split(";")[0];
			if (!JSON_MEDIA_TYPE.equalsIgnoreCase(content)) {
				logger.error("Exiting due to wrong content format");
				return (getError(
						ResultsCode.ONLY_JSON_FORMAT_IS_ACCEPTED.getMessage(),
						ResultsCode.ONLY_JSON_FORMAT_IS_ACCEPTED.getCode()));
			}
		} else {
			logger.error(" D2CProviderFirmRequestProcessor.getFirmsWithRetailPriceBySKU content type is : null");
			return (getError(
					ResultsCode.CONTENT_TYPE_CAN_NOT_BE_BLANK.getMessage(),
					ResultsCode.CONTENT_TYPE_CAN_NOT_BE_BLANK.getCode()));
		}*/

		D2CPortalAPIVORequest d2CPortalAPIVO = new D2CPortalAPIVORequest();
		d2CPortalAPIVO.setBuyerId(buyerId);
		d2CPortalAPIVO.setSkuId(skuId);
		d2CPortalAPIVO.setZipCode(zipCode);

		if (!StringUtils.isEmpty(date)) {
			d2CPortalAPIVO.setDate(date);
		}

		if (!StringUtils.isEmpty(firmId)) {
			d2CPortalAPIVO.setFirmId(firmId);
		}

		if (null != count) {
			d2CPortalAPIVO.setCount(count);
		} else {
			d2CPortalAPIVO.setCount(5);
		}

		if (null != marketIndexFlag) {
			d2CPortalAPIVO.setMarketIndexFlag(marketIndexFlag);
		} else {
			d2CPortalAPIVO.setMarketIndexFlag(true);
		}
		
		logger.info("Passed date time pref. slot and Ind. serviceDatetimeSlotsString: " + serviceDatetimeSlotsString);
		if (!StringUtils.isEmpty(serviceDatetimeSlotsString)) {
			try {
				// USED in Calculating and saving MATCHING_PERCENTAGE
				Map<Integer, TimeSlotDTO> dateTimePrefAndStartEndSlotMap = bindPrefDateTimeToSearchMatchRankApiRequestBean(serviceDatetimeSlotsString);
				logger.info("dateTimePrefAndStartEndSlotMap: " + dateTimePrefAndStartEndSlotMap);
				// setting it to main object
				d2CPortalAPIVO.setPrefIdAndStartEndDateTimeSlotMap(dateTimePrefAndStartEndSlotMap);
			} catch (ParseException e) {
				return (getError(e.getMessage(), ""));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Map<String, String> reqMap = CommonUtility.getRequestMap(getHttpRequest().getParameterMap());
		d2CPortalAPIVO.setRequestFromPost(reqMap);

		logger.debug("after setting reqMap: " + d2CPortalAPIVO);

		APIRequestVO apiVO = new APIRequestVO(getHttpRequest());
		apiVO.setRequestType(RequestType.Post);
		apiVO.addProperties(PublicAPIConstant.REQUEST_MAP, reqMap);

		logger.info("-- START getFirmsWithRetailPriceBySKUGenericBuyer.getFirms():: d2CFirmDetailsServiceAPI = " + d2CFirmDetailsServiceAPI);
		D2CGetFirmDetailsResponse firmDetailsXML = d2CFirmDetailsServiceAPI.getFirms(d2CPortalAPIVO, apiVO);
		
		// TESTING_PURPOSE
		// D2CGetFirmDetailsResponse firmDetailsXML = null;
		logger.info("-- END getFirmsWithRetailPriceBySKUGenericBuyer.getFirms() --");

		return firmDetailsXML;
	}
	
	private Map<Integer, TimeSlotDTO> bindPrefDateTimeToSearchMatchRankApiRequestBean(String serviceDatetimeSlotsString) throws ParseException {
 		
 		Map<Integer, TimeSlotDTO> dateTimePrefAndStartEndSlotMap = null;
 		
 		// if null return null
 		// convert the string to Object
 		ServiceDateTimeSlots serviceDatetimeSlotsObj = null;
 		try {
 			serviceDatetimeSlotsObj = (ServiceDateTimeSlots) convertXMLStringtoRespObject(serviceDatetimeSlotsString, ServiceDateTimeSlots.class);
		} catch (Exception e) {
			e.printStackTrace();
			serviceDatetimeSlotsObj = null;
		}
		
		if (null == serviceDatetimeSlotsObj) {
			return dateTimePrefAndStartEndSlotMap;
		}
		
		// check for null and size
		List<ServiceDatetimeSlot> serviceDatetimeSlotList = serviceDatetimeSlotsObj.getServiceDatetimeSlot();
		if ((null == serviceDatetimeSlotList) || (serviceDatetimeSlotList.size() == 0)) {
			return dateTimePrefAndStartEndSlotMap;
		}
		
		dateTimePrefAndStartEndSlotMap = new LinkedHashMap<Integer, TimeSlotDTO>();
		
		for (ServiceDatetimeSlot serviceDatetimeSlot : serviceDatetimeSlotList) {
			// indicator
			System.out.println(serviceDatetimeSlot.getPreferenceInd());
			// start_end: date_time(both)
			System.out.println(serviceDatetimeSlot.getServiceStartDate());
			System.out.println(serviceDatetimeSlot.getServiceEndDate());
			
			if (null != serviceDatetimeSlot) {
				if (! StringUtils.isEmpty(serviceDatetimeSlot.getPreferenceInd())) {

					// Key : 1 Value : TimeSlotDTO [startTime=Thu Oct 26 12:00:00 IST 2017, endTime=Thu Oct 26 17:00:00 IST 2017, hourInd24=false, matchedSoPrefDateTime=false]
					if (!StringUtils.isEmpty(serviceDatetimeSlot.getServiceStartDate())) {
						if (!StringUtils.isEmpty(serviceDatetimeSlot.getServiceEndDate())) {
							try {
								// eg.: "2017-10-26T12:00:00Z"
								TimeSlotDTO ts = validatePrefDateTime(serviceDatetimeSlot.getServiceStartDate(), serviceDatetimeSlot.getServiceEndDate());
								
								if (serviceDatetimeSlot.getPreferenceInd().trim().equals("1")) {
									dateTimePrefAndStartEndSlotMap.put(1, ts);
								} else if (serviceDatetimeSlot.getPreferenceInd().trim().equals("2")) {
									dateTimePrefAndStartEndSlotMap.put(2, ts);
								} else if (serviceDatetimeSlot.getPreferenceInd().trim().equals("3")) {
									dateTimePrefAndStartEndSlotMap.put(3, ts);
								}
								
							} catch (ParseException e) {
								// "For Pref.Indi.: 1 WRONG START DateTime format. Accepted date format is: yyyy-MM-dd'T'HH:mm:ss"
								logger.error("For Pref.Indi.: " + (serviceDatetimeSlot.getPreferenceInd()) + " " + e.getMessage() + ". " + "Accepted date format is: yyyy-MM-dd'T'HH:mm:ss");
								throw new ParseException(("For Pref.Indi.: " + (serviceDatetimeSlot.getPreferenceInd()) + " " + e.getMessage() + ". " + "Accepted date format is: yyyy-MM-dd'T'HH:mm:ss"), e.getErrorOffset());
							}
						}
					}
				}
			}
		}
		
		return dateTimePrefAndStartEndSlotMap;
 	}

}
