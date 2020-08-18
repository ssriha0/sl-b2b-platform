package com.newco.marketplace.api.mobile.utils.mappers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.GetProviderSOListResponse;
import com.newco.marketplace.api.mobile.beans.ProviderSOList;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.api.mobile.utils.convertors.DateConvertor;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.vo.mobile.ProviderSOSearchVO;
import com.newco.marketplace.vo.mobile.ProviderSearchSO;

public class ProviderSearchSOMapper {
	private Logger logger = Logger.getLogger(ProviderSearchSOMapper.class);
	public static final String EMPTY_STR = "";
	public static final String SPACE = " ";
	public static final String YES = "Y";
	public static final String NO = "N";
	public final static String RANGE_DATE = "2";
	public final static String FIXED_DATE = "1";

	/**
	 * This method maps the search results received from the database to the
	 * response object.
	 * 
	 * @param providerSOList
	 * @param count
	 * @return getProviderSOListResponse
	 */
	public GetProviderSOListResponse mapProviderSOSearchResponse(
			List<ProviderSOSearchVO> providerSOList, Integer count,
			HashMap<String, Object> buyerLogoUrl) {
		logger.info("Entering ProviderSearchSOMapper.mapProviderSOSearchResponse()");
		GetProviderSOListResponse getProviderSOListResponse = new GetProviderSOListResponse();
		List<ProviderSearchSO> providerSearchSOList = new ArrayList<ProviderSearchSO>();

		if (providerSOList.size() > 0) {

			for (ProviderSOSearchVO result : providerSOList) {
				ProviderSearchSO providerSearchSO = new ProviderSearchSO();

				// Set SO Title
				if (StringUtils.isNotBlank(result.getSoTitle())) {
					providerSearchSO.setSoTitle(result.getSoTitle());
				} else {
					providerSearchSO.setSoTitle(EMPTY_STR);
				}
				// Set SO Id
				providerSearchSO.setSoId(result.getSoId());
				// Set SO Status
				if (StringUtils.isNotBlank(result.getSoStatus())) {
					providerSearchSO.setSoStatus(result.getSoStatus());
				} else {
					providerSearchSO.setSoStatus(EMPTY_STR);
				}

				// Set SO SubStatus
				if (StringUtils.isNotBlank(result.getSoSubStatus())) {
					providerSearchSO.setSoSubStatus(mapSubstatus(result.getSoSubStatus()));
				} else {
					providerSearchSO.setSoSubStatus(EMPTY_STR);
				}

				// Set Service Start Date and End Date
				// TODO: Check if the date displayed is converted correctly.
				// convert date to required format and display

				// Start Date
				if (null != result.getServiceStartDate()) {

					providerSearchSO.setServiceStartDate(result
							.getServiceStartDate());
				}
				// End Date
				if (result.getDateType().equals(Integer.parseInt(FIXED_DATE))) {
					providerSearchSO.setServiceEndDate(EMPTY_STR);

				} else {
					providerSearchSO.setServiceEndDate(result
							.getServiceEndDate());
				}
				// Set Service Start Time
				providerSearchSO.setServiceWindowStartTime(result
						.getServiceTime1());

				// Set Service End Time
				if (StringUtils.isBlank(result.getServiceTime2())) {
					providerSearchSO.setServiceWindowEndTime(EMPTY_STR);
					providerSearchSO.setServiceTime(providerSearchSO
							.getServiceWindowStartTime());
				} else {
					providerSearchSO.setServiceWindowEndTime(result
							.getServiceTime2());
					providerSearchSO.setServiceTime(providerSearchSO
							.getServiceWindowStartTime()
							+ " To "
							+ providerSearchSO.getServiceWindowEndTime());
				}

				// Set timeZone
				if (StringUtils.isNotBlank(result.getServiceLocationTimeZone())) {
					providerSearchSO.setTimeZone(result
							.getServiceLocationTimeZone());
				} else {
					providerSearchSO.setTimeZone(EMPTY_STR);
				}

				// Set the Customer First Name
				if (StringUtils.isBlank(result.getCustFirstName())) {
					providerSearchSO.setCustomerFirstName(EMPTY_STR);
				} else {
					providerSearchSO.setCustomerFirstName(result
							.getCustFirstName());
				}

				// Set the Customer Last Name
				if (StringUtils.isBlank(result.getCustLastName())) {
					providerSearchSO.setCustomerLastName(EMPTY_STR);
				} else {
					providerSearchSO.setCustomerLastName(result
							.getCustLastName());
				}

				// Set City
				if (StringUtils.isNotBlank(result.getCity())) {
					providerSearchSO.setCity(result.getCity());
				} else {
					providerSearchSO.setCity(EMPTY_STR);
				}
				// Set State
				if (StringUtils.isNotBlank(result.getState())) {
					providerSearchSO.setState(result.getState());
				} else {
					providerSearchSO.setState(EMPTY_STR);
				}

				// Set Zip
				if (StringUtils.isNotBlank(result.getZip())) {
					providerSearchSO.setZip(result.getZip());
				} else {
					providerSearchSO.setZip(EMPTY_STR);
				}

				// Set Street Address 1
				if (StringUtils.isNotBlank(result.getAddress1())) {
					providerSearchSO.setStreetAddress1(result.getAddress1());
				} else {
					providerSearchSO.setStreetAddress1(EMPTY_STR);
				}

				// Set Street Address 2
				if (StringUtils.isNotBlank(result.getAddress2())) {
					providerSearchSO.setStreetAddress2(result.getAddress2());
				} else {
					providerSearchSO.setStreetAddress2(EMPTY_STR);
				}

				// Set Buyer Id
				providerSearchSO.setBuyerId(result.getBuyerId());
				// Set Buyer Name
				if (StringUtils.isNotBlank(result.getBuyerName())) {
					providerSearchSO.setBuyerName(result.getBuyerName());
				} else {
					providerSearchSO.setBuyerName(EMPTY_STR);
				}

				// Set Schedule Status
				providerSearchSO.setScheduleStatus(result.getScheduleStatus());
				// Set Merchandise Status to Y if MerchStatus obtained is 40
				if (StringUtils.isBlank(result.getMerchStatus())) {
					providerSearchSO.setMerchStatus(NO);
				} else {
					providerSearchSO.setMerchStatus(YES);
				}
				// Set Server Date
				// Convert based on the timeZone.
				// ISO, specific date and time zone
				String serverDateTime = DateConvertor.toString(new Date(),
						"yyyy-MM-dd hh:mm:ss",OrderConstants.UTC_ZONE);
				if (null != serverDateTime) {
					providerSearchSO.setServerDateTime(serverDateTime);
				}

				// TODO: Need to set stopNo and service time in case required.
				// Waiting for clarification.
				// testing purpose
				String baseUrlString = (String) buyerLogoUrl
						.get(PublicMobileAPIConstant.AdvancedProviderSOManagement.BASE_URL);

				// Actual
				String pathUrlString = (String) buyerLogoUrl
						.get(PublicMobileAPIConstant.AdvancedProviderSOManagement.PATH_URL);

				if (StringUtils.isNotBlank(result.getBuyerLogo())) {
					String logoUrl = result.getBuyerLogo();
					String mainUrl = "";
					if (logoUrl.contains(pathUrlString)) {
						mainUrl = logoUrl.replace(pathUrlString, baseUrlString);
					} else if (logoUrl
							.contains(PublicMobileAPIConstant.AdvancedProviderSOManagement.PATH_URL_TESTING)) {
						mainUrl = logoUrl
								.replace(
										PublicMobileAPIConstant.AdvancedProviderSOManagement.PATH_URL_TESTING,
										baseUrlString);
					} else {
						mainUrl = result.getBuyerLogo();
					}
					providerSearchSO.setBuyerLogo(mainUrl);
				} else {
					providerSearchSO.setBuyerLogo(EMPTY_STR);
				}

				// Set the ETA
				if (StringUtils.isBlank(result.getEta())) {
					providerSearchSO.setEta(EMPTY_STR);
				} else {
					providerSearchSO.setEta(result.getEta());
				}

				// Add to the list
				providerSearchSOList.add(providerSearchSO);
			}

			Results results = Results.getSuccess("Success");
			getProviderSOListResponse.setResults(results);
			ProviderSOList providerSOs = new ProviderSOList();
			providerSOs.setProviderSearchSO(providerSearchSOList);
			providerSOs.setTotalSOcount(count);
			getProviderSOListResponse.setServiceOrderList(providerSOs);
			logger.info("Leaving ProviderSearchSOMapper.mapProviderSOSearchResponse()");
		}
		return getProviderSOListResponse;
	}

	/**
	 * This method maps the request parameters to HashMap which is passed to the
	 * Database.
	 * 
	 * @param request
	 * @param statusIds
	 * @param firmId
	 * @return params
	 */
	public HashMap<String, Object> mapProviderSOSearchRequest(
			APIRequestVO request, List<Integer> statusIds, Integer firmId) {
		logger.info("Entering ProviderSearchSOMapper.mapProviderSOSearchRequest()");
		HashMap<String, Object> params = new HashMap<String, Object>();
		Map<String, String> requestMap = request.getRequestFromGetDelete();

		String providerId = request.getProviderResourceId().toString();
		String pageNo = requestMap
				.get(PublicMobileAPIConstant.AdvancedProviderSOManagement.PAGE_NUMBER);
		String pageSize = requestMap
				.get(PublicMobileAPIConstant.AdvancedProviderSOManagement.PAGE_SIZE);

		int resourceID = Integer.parseInt(providerId);
		int firmID = (int) firmId;

		int pageSz = ((null != pageSize) ? new Integer(pageSize)
				: PublicMobileAPIConstant.AdvancedProviderSOManagement.DEFAULT_PAGE_SIZE);

		int pageNumber = ((null != pageNo) ? new Integer(pageNo) : 1);

		// If the page number received is 1, then make the start index 0, else
		// it will start the search from startIndex 1 which is incorrect.
		int startIndex = 0;
		if (pageNumber != 0) {
			startIndex = ((pageNumber != 1) ? ((pageNumber - 1) * pageSz) : 0);
		}

		params.put("vendorId", firmID);
		params.put("resourceId", resourceID);
		params.put("workFlowStatusIds", statusIds);
		params.put("sortFlag", "set");
		params.put("sortBy", "AppointmentDate");
		params.put("startIndex", startIndex);
		params.put("numberOfRecords", pageSz);

		logger.info("Leaving ProviderSearchSOMapper.mapProviderSOSearchRequest()");
		return params;
	}
	protected String mapSubstatus(String subStatus){
		if(subStatus.contains(MPConstants.REQUESTED)){
			subStatus=subStatus.replace(MPConstants.REQUESTED,MPConstants.REQUIRED);
		}
		return subStatus;
		
	}
}
