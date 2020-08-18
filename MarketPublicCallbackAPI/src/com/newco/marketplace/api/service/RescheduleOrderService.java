package com.newco.marketplace.api.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.newco.marketplace.api.beans.CustomReference;
import com.newco.marketplace.api.beans.EventCallbackSoRequest;
import com.newco.marketplace.api.beans.JobCode;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.RetrieveServiceOrder;
import com.newco.marketplace.api.client.SOMClient;
import com.newco.marketplace.interfaces.CloseOrderConstants;
import com.newco.marketplace.interfaces.EventCallbackConstants;
import com.newco.marketplace.interfaces.RescheduleOrderConstants;
import com.newco.marketplace.util.TimeUtils;
import com.newco.marketplace.vo.AttemptReschedule;
import com.newco.marketplace.vo.Brand;
import com.newco.marketplace.vo.Coverage;
import com.newco.marketplace.vo.JobCodes;
import com.newco.marketplace.vo.Merchandise;
import com.newco.marketplace.vo.Remittance;
import com.newco.marketplace.vo.RescheduleRequest;
import com.newco.marketplace.vo.SOMResponse;
import com.newco.marketplace.vo.ServiceInfoReschedule;
import com.newco.marketplace.vo.ServiceOrderReschedule;
import com.newco.marketplace.vo.Technician;

public class RescheduleOrderService {

	private Logger logger = Logger.getLogger(RescheduleOrderService.class);
	private SOMClient somClient;
	public static long expiryTime;
	public static String authToken;

	public Result execute(EventCallbackSoRequest eventCallbackSoRequest, Map<String, String> eventCallbackMap)
			throws Exception {
		Result result = new Result();
		// Mapping get SO response to Attempt API request
		RescheduleRequest closeOrderAPIRequest = mapRescheduleApiRequest(eventCallbackSoRequest);

		if (closeOrderAPIRequest != null && closeOrderAPIRequest.getServiceOrder() != null) {
			ServiceOrderReschedule so = closeOrderAPIRequest.getServiceOrder();
			String unitNumber = so.getServiceUnit();
			String orderNumber = so.getOrderNumber();

			if (unitNumber == null || unitNumber.isEmpty()) {
				logger.info("Cust Ref for Unit number is not present");
				unitNumber = RescheduleOrderConstants.BLANK;
			}
			if (orderNumber == null || orderNumber.isEmpty()) {
				logger.info("Cust Ref for Order number is not present");
				orderNumber = RescheduleOrderConstants.BLANK;
			}

			String authUrl = eventCallbackMap.get(RescheduleOrderConstants.TECHHUB_AUTH_URL);
			String authCodeForAuthAPI = RescheduleOrderConstants.TH_AUTH_TOKEN_HEADER
					+ RescheduleOrderConstants.SPACE_STRING
					+ eventCallbackMap.get(RescheduleOrderConstants.TECHHUB_AUTH_TOKEN);
			boolean isAuthorized = getAuthToken(authCodeForAuthAPI, authUrl);
			if (isAuthorized) {

				String orderAttemptUrl = eventCallbackMap.get(RescheduleOrderConstants.TECHHUB_RESCHEDULE_URL);
				String orderAttemptReqParam = unitNumber + RescheduleOrderConstants.HYPHEN_STRING + orderNumber;
				String mappedUrl = orderAttemptUrl.replaceAll(RescheduleOrderConstants.SO_ID_PARAM,
						orderAttemptReqParam);
				String authCodeForCCAPI = RescheduleOrderConstants.TH_API_TOKEN_HEADER
						+ RescheduleOrderConstants.SPACE_STRING + authToken;
				String orderAttemptReqJSON = convertObjectToJSON(closeOrderAPIRequest);
				SOMResponse orderAttemptResponse = somClient.invokeTechHubAPI(mappedUrl, authCodeForCCAPI,
						orderAttemptReqJSON);
				result = populateEventResponse(orderAttemptResponse);
			} else {
				result = populateResponse(EventCallbackConstants.RESPONSE_AUTHENTICATION_ERROR_CODE,
						EventCallbackConstants.RESPONSE_AUTHENTICATION_ERROR);
			}
		} else {
			result = populateResponse(RescheduleOrderConstants.ATTEMPT_REQUEST_MAPPING_ERROR_CD,
					RescheduleOrderConstants.ATTEMPT_REQUEST_MAPPING_ERROR);
		}
		return result;
	}

	public RescheduleRequest mapRescheduleApiRequest(EventCallbackSoRequest soDetails) throws Exception {
		logger.info("Inside RescheduleServiceOrder.mapRescheduleApiRequest");
		RescheduleRequest rescheduleApiRequest = new RescheduleRequest();
		try {
			Map<String, String> custRefMap = new HashMap<>();
			List<CustomReference> custRefList = null;
			RetrieveServiceOrder so = null;
			if (null != soDetails && null != soDetails.getServiceOrders()) {
				if (null != soDetails.getServiceOrders().getServiceorderList()) {
					so = soDetails.getServiceOrders().getServiceorderList().get(0);
					if (null != so) {
						if (null != so.getOrderstatus()) {
							String soId = soDetails.getServiceOrders().getServiceorderList().get(0).getOrderstatus()
									.getSoId();
							logger.info("Service order being processed: " + soId);
						}
						if (null != so.getCustomReferences()) {
							custRefList = soDetails.getServiceOrders().getServiceorderList().get(0)
									.getCustomReferences().getCustomRefList();
						}

						// corelationId is a random Value
						Random random = new Random();
						rescheduleApiRequest.setCorrelationId(random.nextInt(RescheduleOrderConstants.RAND_VAL));
						if (null != custRefList) {
							for (int i = 0; i < custRefList.size(); i++) {
								custRefMap.put(custRefList.get(i).getName().toLowerCase(),
										custRefList.get(i).getValue());
							}
						}

						// Map data to ServiceInfoReschedule
						ServiceInfoReschedule serviceInfo = mapServiceInfo(custRefMap, so);

						// Coverage
						Coverage coverage = new Coverage();
						coverage.setCoverageCode(
								custRefMap.get(RescheduleOrderConstants.CUST_REF_COVERAGE_LABOR.toLowerCase()));
						String coverageCode = coverage.getCoverageCode();

						// remittance
						if (null != coverageCode && coverageCode.equals(RescheduleOrderConstants.PT)) {
							Remittance remittance = new Remittance();
							remittance.setTransferToStore(RescheduleOrderConstants.REMITTENCE_CODE);
							remittance.setSubAccount(RescheduleOrderConstants.REMITTENCE_SUB_ACCNT);
							serviceInfo.setRemittance(remittance);
						}

						// jobcodes
						List<JobCode> jobCodeReqList = null;
						if (null != so.getJobCodes()) {
							jobCodeReqList = so.getJobCodes().getJobCode();
						}
						List<JobCodes> jobCodeList = new ArrayList<>();
						if (null != jobCodeReqList) {
							for (int i = 0; i < jobCodeReqList.size(); i++) {
								JobCodes jobCodes = new JobCodes();
								JobCode jobcode = jobCodeReqList.get(i);
								jobCodes.setChargeAmount(RescheduleOrderConstants.CHARGE_AMOUNT);
								jobCodes.setCoverage(coverage);
								jobCodes.setJobCode(jobcode.getJobCodeId());
								jobCodes.setChargeCode(RescheduleOrderConstants.Y);
								jobCodes.setSequence(RescheduleOrderConstants.SEQUENCE);
								jobCodes.setRelatedFl(RescheduleOrderConstants.RELATED_FLAG);
								jobCodeList.add(jobCodes);
							}
						}
						// Brand
						Brand brand = new Brand();
						String brandName = custRefMap.get(RescheduleOrderConstants.CUST_REF_BRAND.toLowerCase());
						if (null != brandName && brandName.length() > EventCallbackConstants.BRAND_NAME_LIMIT) {
							brandName = brandName.substring(0, EventCallbackConstants.BRAND_NAME_LIMIT);
						}
						brand.setBrandName(brandName);

						// Merchandise
						Merchandise merchandise = new Merchandise();
						String serialNum = custRefMap.get(CloseOrderConstants.CUST_REF_SERIAL_NUMBER.toLowerCase());
						if (serialNum != null && serialNum.length() > EventCallbackConstants.SERIAL_NUMBER_LIMIT) {
							serialNum = serialNum.substring(0, EventCallbackConstants.SERIAL_NUMBER_LIMIT);
						}
						merchandise.setSerialNumber(serialNum);
						merchandise.setBrand(brand);
						String modelNum = custRefMap.get(RescheduleOrderConstants.CUST_REF_MODEL.toLowerCase());
						if (modelNum != null && modelNum.length() > EventCallbackConstants.MODEL_NUMBER_LIMIT) {
							modelNum = modelNum.substring(0, EventCallbackConstants.MODEL_NUMBER_LIMIT);
						}
						merchandise.setModelNumber(modelNum);
						ServiceOrderReschedule serviceOrder = new ServiceOrderReschedule();
						serviceOrder.setJobCodes(jobCodeList);
						serviceOrder.setMerchandise(merchandise);
						serviceOrder.setServiceInfo(serviceInfo);
						serviceOrder.setServiceUnit(
								custRefMap.get(RescheduleOrderConstants.CUST_REF_UNIT_NUMBER.toLowerCase()));
						serviceOrder.setOrderNumber(
								custRefMap.get(RescheduleOrderConstants.CUST_REF_ORDER_NUMBER.toLowerCase()));
						rescheduleApiRequest.setServiceOrder(serviceOrder);
						// so end
					}

				}
			}

			return rescheduleApiRequest;

		} catch (Exception e) {
			logger.error("Exception occurred while converting get so response to Reschedule request:", e);
			throw e;
		}
	}

	private ServiceInfoReschedule mapServiceInfo(Map<String, String> custRefMap, RetrieveServiceOrder so)
			throws Exception {
		try {
			// Attempt
			AttemptReschedule attempt = new AttemptReschedule();
			attempt.setArriveTime(RescheduleOrderConstants.DEFAULT_START_TIME);
			attempt.setEndTime(RescheduleOrderConstants.DEFAULT_END_TIME);
			attempt.setTransitTime(RescheduleOrderConstants.DEFAULT_TIME_DIFF);

			String techComment = so.getSchedule().getReschedule().getComment();
			if (null != techComment && techComment.length() > EventCallbackConstants.TECH_COMMENT_REAL_LIMIT) {
				techComment = techComment.substring(0, EventCallbackConstants.TECH_COMMENT_CONTENT_LIMIT) + "...";
			}
			attempt.setTechComments(techComment);

			attempt.setCallCode(RescheduleOrderConstants.CALL_CODE);
			attempt.setReasonCode(RescheduleOrderConstants.REASON_CODE);
			// Technician
			Technician technicianServiceInfo = new Technician();
			String ispId = custRefMap.get(RescheduleOrderConstants.CUST_REF_ISP_ID.toLowerCase());
			if (null != ispId && !StringUtils.isEmpty(ispId)) {
				String id = ispId.substring(ispId.length() - RescheduleOrderConstants.DIGIT);
				technicianServiceInfo.setId(id);
			}
			Technician technicianAttempt = new Technician();
			technicianAttempt.setUserId(EventCallbackConstants.TH_API_HEADER_CLIENT_ID_VALUE);
			attempt.setTechnician(technicianAttempt);
			
			// serviceinfo
			ServiceInfoReschedule serviceInfo = new ServiceInfoReschedule();
			serviceInfo.setAttempt(attempt);
			serviceInfo.setTechnician(technicianServiceInfo);
			serviceInfo.setRescheduleFlag(RescheduleOrderConstants.RESCHEDULE_FLAG);
			serviceInfo.setAttemptCount(RescheduleOrderConstants.ATTEMPT_COUNT);

			if (null != so.getSchedule().getServiceDateTime1()) {
				String serviceDateTime1Str = so.getSchedule().getServiceDateTime1();
				Timestamp serviceDateTimestamp1 = TimeUtils.stringToTimestamp(
						serviceDateTime1Str.substring(0, 10) + " " + serviceDateTime1Str.substring(11, 19));
				String serviceDateTime1 = TimeUtils.convertGMTToGivenTimeZone(serviceDateTimestamp1,
						so.getSchedule().getServiceLocationTimezone());
				String formattedserviceDate1 = TimeUtils.formatDateString(serviceDateTime1,
						RescheduleOrderConstants.DATE_FORMAT);
				String formattedserviceDateStartTime = TimeUtils.formatDateString(serviceDateTime1,
						RescheduleOrderConstants.TIME_FORMAT);

				serviceInfo.setRequestedDate(formattedserviceDate1);
				serviceInfo.setRequestedStartTime(formattedserviceDateStartTime);
				if (null != so.getSchedule().getServiceDateTime2()) {
					String serviceDateTime2 = so.getSchedule().getServiceDateTime2();
					Timestamp serviceDateTimestamp2 = TimeUtils.stringToTimestamp(
							serviceDateTime2.substring(0, 10) + " " + serviceDateTime2.substring(11, 19));
					String promisedServiceDateTimestamp2 = TimeUtils.convertGMTToGivenTimeZone(serviceDateTimestamp2,
							so.getSchedule().getServiceLocationTimezone());
					String formattedserviceDateEndTime = TimeUtils.formatDateString(promisedServiceDateTimestamp2,
							RescheduleOrderConstants.TIME_FORMAT);
					serviceInfo.setRequestedEndTime(formattedserviceDateEndTime);
				} else {
					serviceInfo.setRequestedEndTime(RescheduleOrderConstants.DEFAULT_END_TIME);
				}
				if (null != so.getSchedule().getReschedule().getRescheduleCreatedDateTime()) {
					String rescheduleCreatedDateTimeStr = so.getSchedule().getReschedule()
							.getRescheduleCreatedDateTime();
					String formattedpromisedDate = TimeUtils.formatDateString(rescheduleCreatedDateTimeStr,
							RescheduleOrderConstants.DATE_FORMAT);
					serviceInfo.setPromiseDate(formattedpromisedDate);
				}
			}
			return serviceInfo;
		} catch (Exception e) {
			logger.error("Exception occurred while converting get so response to Reschedule request:" + e.getMessage());
			throw e;
		}
	}

	public String convertObjectToJSON(RescheduleRequest reqObj) {
		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		String jsonStr = gson.toJson(reqObj);
		// jsonStr = StringEscapeUtils.unescapeJava(jsonStr);
		logger.debug(jsonStr);
		return jsonStr;
	}

	public boolean getAuthToken(String authCodeForAuthAPI, String authUrl) {
		long currentTime = System.currentTimeMillis();

		try {
			if (expiryTime == 0 || currentTime >= expiryTime) {

				// Invoking Tech Hub Authorization API
				long startTime = System.currentTimeMillis();
				String authResponse = somClient.invokeTechHubAPI(authUrl, authCodeForAuthAPI);
				JSONObject authJson = new JSONObject(authResponse);
				authToken = authJson.getString("token");
				String tokenLife = authJson.getString("tokenLife");
				long tokenlifelong = Long.parseLong(tokenLife);
				long tokenlifeMilli = tokenlifelong * 1000;
				expiryTime = startTime + tokenlifeMilli;
				logger.debug("token value is:" + authToken);
				logger.info("Successfully invoked Tech Hub Auth Service");
			}
		} catch (Exception e) {
			logger.error("Severe exception occured while calling Tech Hub Auth Service", e);
			return false;
		}
		return true;
	}

	public Result populateEventResponse(SOMResponse response) {

		if (response != null) {
			String successMessage = response.getResponseMessage();
			if (successMessage != null && (successMessage.equals(EventCallbackConstants.SUCCESS_MESSAGE)
					|| response.getResponseCode().equals(EventCallbackConstants.ATTEMPT_RESP_SUCCESS_CODE))) {
				return populateResponse(EventCallbackConstants.SUCCESS_CODE, response.getResponseMessage());
			} else {
				return populateResponse(response.getStatus(), response.getMessage());
			}
		}
		return null;
	}

	public Result populateResponse(String code, String message) {
		Result eventResponse = new Result();
		eventResponse.setCode(code);
		eventResponse.setMessage(message);
		return eventResponse;
	}

	public SOMClient getSomClient() {
		return somClient;
	}

	public void setSomClient(SOMClient somClient) {
		this.somClient = somClient;
	}

}
