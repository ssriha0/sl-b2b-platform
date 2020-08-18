package com.newco.marketplace.api.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.GeneralSection;
import com.newco.marketplace.api.beans.so.Location;
import com.newco.marketplace.api.beans.so.OrderStatus;
import com.newco.marketplace.api.beans.so.Schedule;
import com.newco.marketplace.api.beans.so.addNote.SOAddNoteResponse;
import com.newco.marketplace.api.beans.so.cancel.SOCancelResponse;
import com.newco.marketplace.api.beans.so.create.SOCreateResponse;
import com.newco.marketplace.api.beans.so.post.SOPostResponse;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleResponse;
import com.newco.marketplace.api.beans.so.retrieve.SORetrieveResponse;
import com.newco.marketplace.api.beans.so.search.SOSearchResponse;
import com.newco.marketplace.api.beans.so.search.SearchResults;
import com.newco.marketplace.api.utils.constants.PublicAPIConstant;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.sears.os.service.ServiceConstants;

/**
 * The purpose of this class is to return mock responses for the available 
 * services. The below services are available 
 * 1. Create
 * 2. Search
 * 3. Retrieve
 * 4. Cancel
 * 5. Reschedule
 * 6. Post
 * 
 */

public class SOMockResponse {
	private static Logger logger = Logger.getLogger(SOMockResponse.class);
	private static XStreamUtility conversionUtility = new XStreamUtility();

	/**
	 * This method returns a mock response for the create service order
	 * @return String stringResponse
	 */
	public static String getMockResponseForCreate() {
		logger.info("Entering SOMockResponse.getMockResponseForCreate()");
		SOCreateResponse soCreateResponse = new SOCreateResponse();
		List<Result> resultList = new ArrayList<Result>();
		List<ErrorResult> errorList = new ArrayList<ErrorResult>();
		Results results = new Results();
		Result result = new Result();
		ErrorResult errorResult = new ErrorResult();
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setSoId("154-5448-6303-33");
		orderStatus.setStatus(PublicAPIConstant.DRAFT);
		orderStatus.setSubstatus("");
		orderStatus.setCreatedDate(CommonUtility.sdfToDate.format(new Date()));
		result.setMessage(CommonUtility.getMessage(
								PublicAPIConstant.DRAFT_CREATED_RESULT_CODE));
		result.setCode(PublicAPIConstant.ONE);
		errorResult.setCode(PublicAPIConstant.ZERO);
		errorResult.setMessage("");
		resultList.add(result);
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		soCreateResponse.setOrderstatus(orderStatus);
		soCreateResponse.setResults(results);
		soCreateResponse.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		soCreateResponse
				.setSchemaLocation(PublicAPIConstant.SORESPONSE_SCHEMALOCATION);
		soCreateResponse.setNamespace(PublicAPIConstant.SORESPONSE_NAMESPACE);
		soCreateResponse.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		String stringResponse = conversionUtility
				.getCreateResponseXML(soCreateResponse);
		logger.info("Leaving SOMockResponse.getMockResponseForCreate()");
		return stringResponse;
	}

	/**
	 * This method returns a mock response for the search service order
	 * @return String stringResponse
	 */
	public static String getMockResponseForSearch() {
		logger.info("Entering SOMockResponse.getMockResponseForSearch()");
		SOSearchResponse soSearchResponse = new SOSearchResponse();
		ArrayList<OrderStatus> orderstatusList = new ArrayList<OrderStatus>();
		OrderStatus orderStatus1 = new OrderStatus();
		OrderStatus orderStatus2 = new OrderStatus();
		SearchResults searchResults = new SearchResults();
		List<Result> resultList = new ArrayList<Result>();
		List<ErrorResult> errorList = new ArrayList<ErrorResult>();
		Results results = new Results();
		Result result = new Result();
		ErrorResult errorResult = new ErrorResult();
		result.setCode(PublicAPIConstant.ONE);
		result.setMessage("2 result(s) returned");
		errorResult.setCode(PublicAPIConstant.ZERO);
		errorResult.setMessage("");
		resultList.add(result);
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		orderStatus1.setCreatedDate("2005-12-17T09:30:47Z");
		orderStatus1.setSoId("199-234-45-55");
		orderStatus1.setStatus("Completed");
		orderStatus1.setSubstatus("Documentation Required");
		orderStatus2.setCreatedDate("2001-12-17T09:30:47Z");
		orderStatus2.setSoId("123-566-25-56");
		orderStatus2.setStatus("Closed");
		orderStatus2.setSubstatus("Documentation Required");
		orderstatusList.add(orderStatus1);
		orderstatusList.add(orderStatus2);
		searchResults.setOrderstatus(orderstatusList);
		soSearchResponse.setResults(results);
		soSearchResponse.setSearchResults(searchResults);
		soSearchResponse.setVersion(PublicAPIConstant.SEARCH_VERSION);
		soSearchResponse
				.setSchemaLocation(PublicAPIConstant.SEARCH_SCHEMALOCATION);
		soSearchResponse
				.setNamespace(PublicAPIConstant.SEARCHRESPONSE_NAMESPACE);
		soSearchResponse.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		String stringResponse = null;
		stringResponse = conversionUtility
				.getSearchResponseXML(soSearchResponse);
		logger.info("Leaving SOMockResponse.getMockResponseForSearch()");
		return stringResponse;
	}

	/**
	 * This method returns a mock response for the retrieve service order
	 * @return String stringResponse
	 */
	public static String getMockResponseForRetrieve() {
		logger.info("Entering SOMockResponse.getMockResponseForRetrieve()");
		SORetrieveResponse soRetrieveResponse = new SORetrieveResponse();
		List<Result> resultList = new ArrayList<Result>();
		List<ErrorResult> errorList = new ArrayList<ErrorResult>();
		Results results = new Results();
		OrderStatus orderStatus = new OrderStatus();
		GeneralSection sectionGeneral = new GeneralSection();
		Location serviceLocation = new Location();
		Schedule schedule = new Schedule();
		ErrorResult error = new ErrorResult();
		List<String> responseFilter = getResponseFilter();

		Result result = new Result();
		error.setCode(PublicAPIConstant.ZERO);
		error.setMessage("");
		result.setCode(PublicAPIConstant.ZERO);
		result.setMessage("");
		resultList.add(result);
		errorList.add(error);
		results.setResult(resultList);
		results.setError(errorList);
		orderStatus.setCreatedDate("2001-12-17T01:30:47Z");
		orderStatus.setSoId("154-5448-6303-33");
		orderStatus.setStatus("Draft");
		orderStatus.setSubstatus("");
		sectionGeneral
				.setBuyerTerms("This Service Order is subject to, and " 
						+"incorporates by reference, that certain Authorized");
		sectionGeneral.setOverview("Install Plasma TV at customer's residence");
		sectionGeneral.setSpecialInstructions("State you are" 
											+" arriving on behalf of Company X");
		sectionGeneral.setTitle("Install a 42inch flat screen television");
		serviceLocation.setAddress1("123 Main Street");
		serviceLocation.setAddress2("123 Main Street 2");
		serviceLocation.setCity("Cary");
		serviceLocation.setLocationName("Home");
		serviceLocation.setLocationType("Commercial");
		serviceLocation.setNote("Cary");
		serviceLocation.setState("IL");
		serviceLocation.setZip("60661");
		schedule.setConfirmWithCustomer("false");
		schedule.setScheduleType("range");
		schedule.setServiceDateTime1("2001-12-17T09:30:47Z");
		schedule.setServiceDateTime2("2001-12-17T09:30:47Z");
		soRetrieveResponse.setOrderstatus(orderStatus);
		soRetrieveResponse.setResults(results);
		soRetrieveResponse.setSchedule(schedule);
		soRetrieveResponse.setSectionGeneral(sectionGeneral);
		soRetrieveResponse.setServiceLocation(serviceLocation);
		soRetrieveResponse.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		soRetrieveResponse
				.setSchemaLocation(PublicAPIConstant.SORESPONSE_SCHEMALOCATION);
		soRetrieveResponse.setNamespace(PublicAPIConstant.SORESPONSE_NAMESPACE);
		soRetrieveResponse.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		String stringResponse = conversionUtility
				.getRetrieveResponseXML(soRetrieveResponse, responseFilter);
		logger.info("Leaving SOMockResponse.getMockResponseForRetrieve()");
		return stringResponse;
	}

	/**
	 * This method returns a mock response for the cancel service order
	 * @return String stringResponse
	 */
	public static String getMockResponseForCancel() {
		logger.info("Entering SOMockResponse.getMockResponseForCancel()");
		SOCancelResponse soCancelResponse = new SOCancelResponse();
		List<Result> resultList = new ArrayList<Result>();
		List<ErrorResult> errorList = new ArrayList<ErrorResult>();
		Results results = new Results();
		Result result = new Result();
		ErrorResult errorResult = new ErrorResult();
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setSoId("154-5448-6303-33");
		orderStatus.setStatus(PublicAPIConstant.COMPLETED);
		orderStatus.setSubstatus("");
		orderStatus.setCreatedDate(CommonUtility.sdfToDate.format(new Date()));
		orderStatus.setPostedDate(CommonUtility.sdfToDate.format(new Date()));
		orderStatus.setAcceptedDate(CommonUtility.sdfToDate.format(new Date()));
		orderStatus
				.setCompletedDate(CommonUtility.sdfToDate.format(new Date()));

		result.setMessage("");
		result.setCode(PublicAPIConstant.ZERO);
		errorResult.setCode(ServiceConstants.USER_ERROR_RC);
		errorResult
				.setMessage("Service order is not in a state that can be canceled");
		resultList.add(result);
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		soCancelResponse.setOrderstatus(orderStatus);
		soCancelResponse.setResults(results);
		soCancelResponse.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		soCancelResponse
				.setSchemaLocation(PublicAPIConstant.SORESPONSE_SCHEMALOCATION);
		soCancelResponse.setNamespace(PublicAPIConstant.SORESPONSE_NAMESPACE);
		soCancelResponse.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		String stringResponse = conversionUtility
				.getCancelResponseXML(soCancelResponse);
		logger.info("Leaving SOMockResponse.getMockResponseForCancel()");
		return stringResponse;

	}

	/**
	 * This method returns a mock response for the reschedule service order
	 * @return String stringResponse
	 */
	public static String getMockResponseForReschedule() {
		logger.info("Entering SOMockResponse.getMockResponseForReschedule()");
		SORescheduleResponse soRescheduleResponse = new SORescheduleResponse();
		List<Result> resultList = new ArrayList<Result>();
		List<ErrorResult> errorList = new ArrayList<ErrorResult>();
		Results results = new Results();
		Result result = new Result();
		ErrorResult errorResult = new ErrorResult();
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setSoId("154-5448-6303-33");
		orderStatus.setStatus(PublicAPIConstant.ACCEPTED);
		orderStatus.setSubstatus("");
		orderStatus.setCreatedDate(CommonUtility.sdfToDate.format(new Date()));
		orderStatus.setPostedDate(CommonUtility.sdfToDate.format(new Date()));
		orderStatus.setAcceptedDate(CommonUtility.sdfToDate.format(new Date()));
		result.setMessage(PublicAPIConstant.RESCHEDULE_REQUEST);
		result.setCode(PublicAPIConstant.ONE);
		errorResult.setCode(PublicAPIConstant.ZERO);
		errorResult.setMessage("");
		resultList.add(result);
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		soRescheduleResponse.setOrderstatus(orderStatus);
		soRescheduleResponse.setResults(results);
		soRescheduleResponse.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		soRescheduleResponse
				.setSchemaLocation(PublicAPIConstant.SORESPONSE_SCHEMALOCATION);
		soRescheduleResponse
				.setNamespace(PublicAPIConstant.SORESPONSE_NAMESPACE);
		soRescheduleResponse
				.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		String stringResponse = conversionUtility
				.getRescheduleResponseXML(soRescheduleResponse);
		logger.info("Leaving SOMockResponse.getMockResponseForReschedule()");
		return stringResponse;

	}

	/**
	 * This method returns a mock response for the post service order
	 * @return String stringResponse
	 */
	public static String getMockResponseForPost() {
		logger.info("Entering SOMockResponse.getMockResponseForPost()");
		SOPostResponse soPostResponse = new SOPostResponse();
		List<Result> resultList = new ArrayList<Result>();
		List<ErrorResult> errorList = new ArrayList<ErrorResult>();
		Results results = new Results();
		Result result = new Result();
		ErrorResult errorResult = new ErrorResult();
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setSoId("154-5448-6303-33");
		orderStatus.setStatus(PublicAPIConstant.POSTED);
		orderStatus.setSubstatus("");
		orderStatus.setCreatedDate(CommonUtility.sdfToDate.format(new Date()));
		orderStatus.setPostedDate(CommonUtility.sdfToDate.format(new Date()));
		result.setMessage(PublicAPIConstant.POST_REQUEST);
		result.setCode(PublicAPIConstant.ONE);
		errorResult.setCode(PublicAPIConstant.ZERO);
		errorResult.setMessage("");
		resultList.add(result);
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		soPostResponse.setOrderstatus(orderStatus);
		soPostResponse.setResults(results);
		soPostResponse.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		soPostResponse
				.setSchemaLocation(PublicAPIConstant.SORESPONSE_SCHEMALOCATION);
		soPostResponse.setNamespace(PublicAPIConstant.SORESPONSE_NAMESPACE);
		soPostResponse.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		String stringResponse = conversionUtility
				.getPostResponseXML(soPostResponse);
		logger.info("Leaving SOMockResponse.getMockResponseForPost()");
		return stringResponse;
	}
	/**
	 * This method returns a mock response for the Add Note
	 * @return String stringResponse
	 */
	public static String getMockResponseForAddNote() {
		logger.info("Entering SOMockResponse.getMockResponseForAddNote()");
		SOAddNoteResponse addNoteResponse = new SOAddNoteResponse();
		List<Result> resultList = new ArrayList<Result>();
		List<ErrorResult> errorList = new ArrayList<ErrorResult>();
		Results results = new Results();
		Result result = new Result();
		ErrorResult errorResult = new ErrorResult();
		result.setMessage(PublicAPIConstant.ADD_NOTE_RESULT);
		result.setCode(PublicAPIConstant.ONE);
		errorResult.setCode(PublicAPIConstant.ZERO);
		errorResult.setMessage("");
		resultList.add(result);
		errorList.add(errorResult);
		results.setResult(resultList);
		results.setError(errorList);
		addNoteResponse.setResults(results);
		addNoteResponse.setVersion(PublicAPIConstant.SORESPONSE_VERSION);
		addNoteResponse
				.setSchemaLocation(PublicAPIConstant.SORESPONSE_SCHEMALOCATION);
		addNoteResponse.setNamespace(PublicAPIConstant.SORESPONSE_NAMESPACE);
		addNoteResponse.setSchemaInstance(PublicAPIConstant.SCHEMA_INSTANCE);
		String stringResponse = conversionUtility
				.getAddNoteResponseXML(addNoteResponse);
		logger.info("Leaving SOMockResponse.getMockResponseForAddNote()");
		return stringResponse;
	}

	private static List<String> getResponseFilter() {
		List<String> responseFilter = new ArrayList<String>();
		responseFilter.add("General");
		responseFilter.add("Service Location");
		responseFilter.add("Schedule");

		return responseFilter;
	}
}
