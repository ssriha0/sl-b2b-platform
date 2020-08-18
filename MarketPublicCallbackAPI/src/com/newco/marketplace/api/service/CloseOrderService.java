package com.newco.marketplace.api.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.newco.marketplace.api.beans.PartsDatas;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.RetrieveServiceOrder;
import com.newco.marketplace.api.client.SOMClient;
import com.newco.marketplace.interfaces.CloseOrderConstants;
import com.newco.marketplace.interfaces.EventCallbackConstants;
import com.newco.marketplace.vo.Attempt;
import com.newco.marketplace.vo.Brand;
import com.newco.marketplace.vo.Coverage;
import com.newco.marketplace.vo.JobCodes;
import com.newco.marketplace.vo.Merchandise;
import com.newco.marketplace.vo.Obligor;
import com.newco.marketplace.vo.OrderAttemptAPIRequest;
import com.newco.marketplace.vo.Parts;
import com.newco.marketplace.vo.PaymentTotalInformation;
import com.newco.marketplace.vo.Remittance;
import com.newco.marketplace.vo.SOMResponse;
import com.newco.marketplace.vo.ServiceInfo;
import com.newco.marketplace.vo.ServiceOrder;
import com.newco.marketplace.vo.Technician;
import com.newco.marketplace.vo.ThirdPartyInfo;

public class CloseOrderService {

	private Logger logger = Logger.getLogger(CloseOrderService.class);
	private SOMClient somClient;
	public static long expiryTime;
	public static String authToken;

	public Result execute(EventCallbackSoRequest eventCallbackSoRequest, Map<String, String> eventCallbackMap)
			throws Exception {

		Result result = new Result();
		// Mapping get SO response to Attempt API request
		OrderAttemptAPIRequest closeOrderAPIRequest = mapAttemptAPIRequest(eventCallbackSoRequest);

		if (closeOrderAPIRequest != null && closeOrderAPIRequest.getServiceOrder() != null) {

			ServiceOrder so = closeOrderAPIRequest.getServiceOrder();
			String unitNumber = so.getServiceUnit();
			String orderNumber = so.getOrderNumber();

			if (unitNumber == null || unitNumber.isEmpty()) {
				logger.info("Cust Ref for Unit number is not present");
				unitNumber = CloseOrderConstants.BLANK;
			}
			if (orderNumber == null || orderNumber.isEmpty()) {
				logger.info("Cust Ref for Order number is not present");
				orderNumber = CloseOrderConstants.BLANK;
			}
			String authUrl = eventCallbackMap.get(CloseOrderConstants.TECHHUB_AUTH_URL);
			String authCodeForAuthAPI = CloseOrderConstants.TH_AUTH_TOKEN_HEADER + CloseOrderConstants.SPACE_STRING
					+ eventCallbackMap.get(CloseOrderConstants.TECHHUB_AUTH_TOKEN);
			boolean isAuthorized = getAuthToken(authCodeForAuthAPI, authUrl);
			if (isAuthorized) {

				String orderAttemptUrl = eventCallbackMap.get(CloseOrderConstants.TECHHUB_CALL_CLOSE_URL);
				String orderAttemptReqParam = unitNumber + CloseOrderConstants.HYPHEN_STRING + orderNumber;
				String mappedUrl = orderAttemptUrl.replaceAll(CloseOrderConstants.SO_ID_PARAM, orderAttemptReqParam);
				String authCodeForCCAPI = CloseOrderConstants.TH_API_TOKEN_HEADER + CloseOrderConstants.SPACE_STRING
						+ authToken;
				String orderAttemptReqJSON = convertObjectToJSON(closeOrderAPIRequest);

				SOMResponse orderAttemptResponse = somClient.invokeTechHubAPI(mappedUrl, authCodeForCCAPI,
						orderAttemptReqJSON);
				result = populateEventResponse(orderAttemptResponse);
			} else {
				result = populateResponse(EventCallbackConstants.RESPONSE_AUTHENTICATION_ERROR_CODE,
						EventCallbackConstants.RESPONSE_AUTHENTICATION_ERROR);
			}
		} else {
			result = populateResponse(CloseOrderConstants.ATTEMPT_REQUEST_MAPPING_ERROR_CD,
					CloseOrderConstants.ATTEMPT_REQUEST_MAPPING_ERROR);
		}
		return result;
	}

	// Unit number - Order number
	public OrderAttemptAPIRequest mapAttemptAPIRequest(EventCallbackSoRequest soDetails) throws Exception {

		OrderAttemptAPIRequest closeOrderAPIRequest = null;

		try {

			Map<String, String> custRefMap = new HashMap<>();
			List<CustomReference> custRefList = null;
			String completedDate = null;
			String techComment = null;

			if (null != soDetails && null != soDetails.getServiceOrders()) {
				if (null != soDetails.getServiceOrders().getServiceorderList()
						&& null != soDetails.getServiceOrders().getServiceorderList().get(0)
						&& null != soDetails.getServiceOrders().getServiceorderList().get(0).getOrderstatus()) {
					String soId = soDetails.getServiceOrders().getServiceorderList().get(0).getOrderstatus().getSoId();
					logger.info("Service order being processed: " + soId);

					techComment = soDetails.getServiceOrders().getServiceorderList().get(0).getOrderstatus()
							.getResolutionComments();
					if (null != techComment && techComment.length() > EventCallbackConstants.TECH_COMMENT_REAL_LIMIT) {
						techComment = techComment.substring(0, EventCallbackConstants.TECH_COMMENT_CONTENT_LIMIT)
								+ "...";
					}
					completedDate = soDetails.getServiceOrders().getServiceorderList().get(0).getOrderstatus()
							.getCompletedDate();
				}

				if (null != soDetails.getServiceOrders().getServiceorderList()
						&& null != soDetails.getServiceOrders().getServiceorderList().get(0)
						&& null != soDetails.getServiceOrders().getServiceorderList().get(0).getCustomReferences()) {
					custRefList = soDetails.getServiceOrders().getServiceorderList().get(0).getCustomReferences()
							.getCustomRefList();
				}
			}

			closeOrderAPIRequest = new OrderAttemptAPIRequest();
			// corelationId is a random Value
			Random random = new Random();
			closeOrderAPIRequest.setCorrelationId(random.nextInt(CloseOrderConstants.RAND_VAL));

			if (null != custRefList) {
				for (int i = 0; i < custRefList.size(); i++) {
					custRefMap.put(custRefList.get(i).getName().toLowerCase(), custRefList.get(i).getValue());
				}
			}

			// Coverage
			Coverage coverage = new Coverage();
			coverage.setCoverageCode(custRefMap.get(CloseOrderConstants.CUST_REF_COVERAGE_LABOR.toLowerCase()));

			String coverageCode = coverage.getCoverageCode();
			// Obligor
			Obligor obligor = new Obligor();
			obligor.setCoverage(coverage);
			String procID = custRefMap.get(CloseOrderConstants.CUST_REF_PROCESS_ID.toLowerCase());
			if (coverageCode.equals(CloseOrderConstants.IW)) {
				if (StringUtils.isBlank(procID)) {
					obligor.setObligorId(CloseOrderConstants.IW_PROC_ID);
				} else {
					obligor.setObligorId(procID);
				}
			} else {
				obligor.setObligorId(procID);
			}

			// Parts loop
			List<RetrieveServiceOrder> soList = null;
			List<PartsDatas> invoicePartsList = null;
			List<Parts> partsList = new ArrayList<>();

			if (null != soDetails && null != soDetails.getServiceOrders()) {
				soList = soDetails.getServiceOrders().getServiceorderList();
				if (null != soList && null != soList.get(0) && null != soList.get(0).getInvoiceParts()) {
					invoicePartsList = soList.get(0).getInvoiceParts().getPartsDatas();
				}
			}

			if (null != invoicePartsList) {
				for (int i = 0; i < invoicePartsList.size(); i++) {
					Parts parts = new Parts();
					parts.setQuantity(1);
					parts.setObligor(obligor);
					parts.setStatus(CloseOrderConstants.BLANK);
					parts.setNumber(invoicePartsList.get(i).getPartPartNo());
					parts.setSequence(1);
					parts.setLocation(CloseOrderConstants.X);
					parts.setPls(invoicePartsList.get(i).getPartPlsNo());
					String divNo = invoicePartsList.get(i).getPartDivNo();
					if (null != divNo && divNo.length() == 4) {
						divNo = divNo.substring(1);
					}
					parts.setDiv(divNo);
					Coverage coverageParts = new Coverage();
					coverageParts
							.setCoverageCode(custRefMap.get(CloseOrderConstants.CUST_REF_COVERAGE_PARTS.toLowerCase()));
					parts.setCoverage(coverageParts);
					parts.setPrice(invoicePartsList.get(i).getPartPrice());
					partsList.add(parts);
				}
			}

			// attempt
			Attempt attempt = new Attempt();
			attempt.setArriveTime(CloseOrderConstants.DEFAULT_START_TIME);
			attempt.setEndTime(CloseOrderConstants.DEFAULT_END_TIME);
			attempt.setTransitTime(CloseOrderConstants.DEFAULT_TIME_DIFF);
			attempt.setTechComments(techComment);

			ThirdPartyInfo thirdPartyInfo = new ThirdPartyInfo();
			if (coverageCode.equals(CloseOrderConstants.IW)) {
				if (StringUtils.isBlank(procID)) {
					thirdPartyInfo.setThirdPartyId(CloseOrderConstants.IW_PROC_ID);
				} else {
					thirdPartyInfo.setThirdPartyId(procID);
				}
			} else {
				thirdPartyInfo.setThirdPartyId(procID);
			}

			if (coverageCode != null) {

				attempt.setCallCode(coverageCode.equals(CloseOrderConstants.PT) ? CloseOrderConstants.PT_CODE
						: coverageCode.equals(CloseOrderConstants.SP) ? CloseOrderConstants.SP_CODE
								: coverageCode.equals(CloseOrderConstants.IW) ? CloseOrderConstants.IW_CODE
										: coverageCode.equals(CloseOrderConstants.CC) ? CloseOrderConstants.CC_CODE
												: null);
			}

			// Technician
			Technician technicianServiceInfo = new Technician();
			String ispId = custRefMap.get(CloseOrderConstants.CUST_REF_ISP_ID.toLowerCase());

			if (null != ispId && !StringUtils.isEmpty(ispId)) {
				String id = ispId.substring(ispId.length() - CloseOrderConstants.DIGIT);
				technicianServiceInfo.setId(id);
			}
			Technician technicianAttempt = new Technician();
			technicianAttempt.setUserId(EventCallbackConstants.TH_API_HEADER_CLIENT_ID_VALUE);
			attempt.setTechnician(technicianAttempt);

			// ServiceInfo
			ServiceInfo serviceInfo = new ServiceInfo();
			serviceInfo.setTechnician(technicianServiceInfo);
			serviceInfo.setAttempt(attempt);
			serviceInfo.setThirdPartyInfo(thirdPartyInfo);

			// remittance
			if (null != coverageCode && coverageCode.equals(CloseOrderConstants.PT)) {
				Remittance remittance = new Remittance();
				remittance.setTransferToStore(CloseOrderConstants.REMITTENCE_CODE);
				remittance.setSubAccount(CloseOrderConstants.REMITTENCE_SUB_ACCNT);
				serviceInfo.setRemittance(remittance);
			}

			if (null != completedDate) {

				DateFormat originalFormat = new SimpleDateFormat(CloseOrderConstants.SO_DATE_FORMAT);
				DateFormat targetFormat = new SimpleDateFormat(CloseOrderConstants.DATE_FORMAT);
				Date date = null;
				try {
					date = originalFormat.parse(completedDate);
				} catch (ParseException e) {
					logger.error("Incorrect completed date" + completedDate);
				}
				String formattedDate = targetFormat.format(date);
				if (null != date) {
					serviceInfo.setPromiseDate(formattedDate);
				}
			}

			serviceInfo.setAttemptCount(CloseOrderConstants.ATTEMPT_COUNT);

			// PaymentTotalInformation
			PaymentTotalInformation paymentTotalInformation = new PaymentTotalInformation();
			paymentTotalInformation.setPrimaryAmountCollected(CloseOrderConstants.PRIMARY_AMOUNT);
			paymentTotalInformation.setPrimaryPaymentMethod(CloseOrderConstants.BLANK);

			List<JobCode> jobCodeReqList = null;
			// JobCodes //add loop
			if (null != soDetails && null != soDetails.getServiceOrders()
					&& null != soDetails.getServiceOrders().getServiceorderList()
					&& null != soDetails.getServiceOrders().getServiceorderList().get(0)
					&& null != soDetails.getServiceOrders().getServiceorderList().get(0).getJobCodes()) {
				jobCodeReqList = soDetails.getServiceOrders().getServiceorderList().get(0).getJobCodes().getJobCode();
			}

			List<JobCodes> jobCodeList = new ArrayList<>();
			if (custRefMap.get(CloseOrderConstants.CUST_REF_TEMPLATE_NAME.toLowerCase()).equalsIgnoreCase(
					CloseOrderConstants.PMCHECK_TEMPLATE_NAME) && !coverageCode.equals(CloseOrderConstants.IW)) {
				JobCodes attemptJobCodes = new JobCodes();
				attemptJobCodes.setChargeAmount(CloseOrderConstants.PMCHECK_CHARGE_AMT);
				attemptJobCodes.setJobCode(CloseOrderConstants.PMCHECK_JOBCODE);
				attemptJobCodes.setCoverage(coverage);
				attemptJobCodes.setChargeCode(CloseOrderConstants.Y);
				attemptJobCodes.setSequence(CloseOrderConstants.SEQUENCE);
				attemptJobCodes.setRelatedFl(CloseOrderConstants.RELATED_FLAG);

				jobCodeList.add(attemptJobCodes);
			} else if (null != jobCodeReqList) {
				for (int i = 0; i < jobCodeReqList.size(); i++) {
					JobCodes attemptJobCodes = new JobCodes();
					JobCode jobcode = jobCodeReqList.get(i);
					attemptJobCodes.setChargeAmount(jobcode.getChargeAmount());
					attemptJobCodes.setJobCode(jobcode.getJobCodeId());
					attemptJobCodes.setCoverage(coverage);
					attemptJobCodes.setChargeCode(CloseOrderConstants.Y);
					attemptJobCodes.setSequence(jobcode.getSequence());
					attemptJobCodes.setRelatedFl(jobcode.getRelatedFlag());

					jobCodeList.add(attemptJobCodes);
				}
			}
			// Brand
			Brand brand = new Brand();
			String brandName = custRefMap.get(CloseOrderConstants.CUST_REF_BRAND.toLowerCase());
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
			String modelNum = custRefMap.get(CloseOrderConstants.CUST_REF_MODEL.toLowerCase());
			if (modelNum != null && modelNum.length() > EventCallbackConstants.MODEL_NUMBER_LIMIT) {
				modelNum = modelNum.substring(0, EventCallbackConstants.MODEL_NUMBER_LIMIT);
			}
			merchandise.setModelNumber(modelNum);

			// ServiceOrder
			ServiceOrder serviceOrder = new ServiceOrder();
			serviceOrder.setMerchandise(merchandise);
			serviceOrder.setOrderNumber(custRefMap.get(CloseOrderConstants.CUST_REF_ORDER_NUMBER.toLowerCase()));
			serviceOrder.setJobCodes(jobCodeList);
			serviceOrder.setPaymentTotalInformation(paymentTotalInformation);
			serviceOrder.setServiceInfo(serviceInfo);
			serviceOrder.setParts(partsList);
			serviceOrder.setServiceUnit(custRefMap.get(CloseOrderConstants.CUST_REF_UNIT_NUMBER.toLowerCase()));

			closeOrderAPIRequest.setServiceOrder(serviceOrder);
			return closeOrderAPIRequest;

		} catch (Exception e) {
			logger.error("Exception occurred while converting get so response to order attempt request:", e);
			throw e;
		}

	}

	public String convertObjectToJSON(OrderAttemptAPIRequest reqObj) {
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
			if (successMessage != null && successMessage.equals(EventCallbackConstants.SUCCESS_MESSAGE)) {
				return populateResponse(EventCallbackConstants.SUCCESS_CODE, response.getResponseMessage());
			} else {
				return populateResponse(response.getStatus(), response.getMessage());
			}
		}
		return null;
	}

	public Result populateResponse(String code, String message) {
		Result eventResponse = new Result();
		if (message.equalsIgnoreCase(EventCallbackConstants.CONNECTION_ISSUE_MESG)) {
			eventResponse.setCode(EventCallbackConstants.CONNECTION_ISSUE_CODE);
		} else {
			eventResponse.setCode(code);
		}
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
