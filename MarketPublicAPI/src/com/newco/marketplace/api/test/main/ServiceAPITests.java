package com.newco.marketplace.api.test.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.FileRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.ErrorConvertor;
import com.newco.marketplace.api.beans.ErrorResult;
import com.newco.marketplace.api.beans.Result;
import com.newco.marketplace.api.beans.ResultConvertor;
import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.Attachments;
import com.newco.marketplace.api.beans.so.Contact;
import com.newco.marketplace.api.beans.so.Contacts;
import com.newco.marketplace.api.beans.so.CustomReference;
import com.newco.marketplace.api.beans.so.CustomReferences;
import com.newco.marketplace.api.beans.so.Dimensions;
import com.newco.marketplace.api.beans.so.GeneralSection;
import com.newco.marketplace.api.beans.so.Identification;
import com.newco.marketplace.api.beans.so.Location;
import com.newco.marketplace.api.beans.so.OrderStatus;
import com.newco.marketplace.api.beans.so.Part;
import com.newco.marketplace.api.beans.so.Parts;
import com.newco.marketplace.api.beans.so.Phone;
import com.newco.marketplace.api.beans.so.Pricing;
import com.newco.marketplace.api.beans.so.Schedule;
import com.newco.marketplace.api.beans.so.ScopeOfWork;
import com.newco.marketplace.api.beans.so.Task;
import com.newco.marketplace.api.beans.so.Tasks;
import com.newco.marketplace.api.beans.so.addNote.SOAddNoteResponse;
import com.newco.marketplace.api.beans.so.cancel.SOCancelResponse;
import com.newco.marketplace.api.beans.so.create.SOCreateRequest;
import com.newco.marketplace.api.beans.so.create.SOCreateResponse;
import com.newco.marketplace.api.beans.so.create.ServiceOrderBean;
import com.newco.marketplace.api.beans.so.post.SOPostResponse;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleRequest;
import com.newco.marketplace.api.beans.so.reschedule.SORescheduleResponse;
import com.newco.marketplace.api.beans.so.retrieve.SORetrieveRequest;
import com.newco.marketplace.api.beans.so.retrieve.SORetrieveResponse;
import com.newco.marketplace.api.beans.so.search.SOSearchResponse;
import com.newco.marketplace.api.beans.so.search.SearchResults;
import com.newco.marketplace.api.utils.xstream.XStreamUtility;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ServiceAPITests{

	private Logger logger = Logger.getLogger(ServiceAPITests.class);
	private PostMethod post;
	private File inputFile;
	private String inputFileName;
	private HttpClient httpclient = new HttpClient();
	private RequestEntity entity;
	private int testCount = 1;
	private String fromxml = null;
	private XStreamUtility xstreamUtility;

	private SOCreateRequest createRequest = new SOCreateRequest();
	private SORescheduleRequest rescheduleRequest = new SORescheduleRequest();

	private String todayDate;
	private StringBuffer compareResult = new StringBuffer();
	private HttpServletRequest request;

	/**
	 * This method is the test method for testing Create ServiceOrder.
	 * 
	 * @return void
	 */
	public StringBuffer testMain(HttpServletRequest request) {
		Long testStartTime = System.currentTimeMillis();
		startTable();
		setRequest(request);
		logger.info("Entering ServiceAPITest.testMain");
		try {

			// creating an order with sku data
			startTableHeaderRow();
			SOCreateResponse createResponse = testCreate("Sku");
			endTableHeaderRow();

			// validating the create response
			startTableRow();
			validateCreateResponse(createResponse, "Sku");
			endTableRow();

			String soId = createResponse.getOrderstatus().getSoId();

			// getting the service order just created
			startTableHeaderRow();
			SORetrieveResponse retrieveResponse = testRetrieve(soId);
			endTableHeaderRow();

			// validating the retrieve response
			startTableRow();
			validateRetrieveResponse(createRequest, retrieveResponse);
			endTableRow();

			// canceling the order just created. Order status is draft
			startTableHeaderRow();
			SOCancelResponse cancelResponse = testCancel(soId);
			endTableHeaderRow();

			// validating the cancel response
			startTableRow();
			validateCancelResponse(soId, cancelResponse, createResponse, 
					retrieveResponse.getOrderstatus().getStatus());
			endTableRow();

			// creating an order with Task data
			startTableHeaderRow();
			createResponse = testCreate("Task");
			endTableHeaderRow();

			// validating the create response
			startTableRow();
			validateCreateResponse(createResponse, "Task");
			endTableRow();

			soId = createResponse.getOrderstatus().getSoId();

			// getting the service order just created
			startTableHeaderRow();
			retrieveResponse = testRetrieve(soId);
			endTableHeaderRow();

			// validating the retrieve response
			startTableRow();
			validateRetrieveResponse(createRequest, retrieveResponse);
			endTableRow();

			// adding a note to the just created service order
			startTableHeaderRow();
			SOAddNoteResponse addNoteResponse = testAddNote(soId);
			endTableHeaderRow();

			// validating the add note response
			startTableRow();
			validateAddNoteResponse(addNoteResponse);
			endTableRow();

			// rescheduling the just created service. Order status is draft
			startTableHeaderRow();
			SORescheduleResponse rescheduleResponse = testReschedule(soId);
			endTableHeaderRow();

			// validating the rescheduled service
			startTableRow();
			validateRescheduleResponse(createResponse, rescheduleResponse);
			endTableRow();

			// getting the service order after rescheduling the service order
			startTableHeaderRow();
			SORetrieveResponse retrieveResponseAfterReschedule = testRetrieve(soId);
			endTableHeaderRow();

			// validating the retrieve response for the rescheduled service order
			startTableRow();
			validateRetrieveAfterReschedule(soId, retrieveResponseAfterReschedule);
			endTableRow();

			// search service order
			startTableHeaderRow();
			SOSearchResponse searchResponse = testSearch();
			endTableHeaderRow();

			// validating the search response.
			startTableRow();
			validateSearchResponse(soId, searchResponse, "Draft");
			endTableRow();

			// posting the service order
			startTableHeaderRow();
			SOPostResponse postResponse = testPost(soId);
			endTableHeaderRow();

			// validating the post response.
			startTableRow();
			validatePostResponse(soId, postResponse);
			endTableRow();
			
			// getting the service order after rescheduling the service order
			startTableHeaderRow();
			SORetrieveResponse retrieveResponseAfterPost = testRetrieve(soId);
			endTableHeaderRow();

			// validating retrieve response after posting the request
			startTableRow();
			validateRetrieveAfterPost(soId, retrieveResponseAfterPost);
			endTableRow();
			
			// canceling the order just created. Order status is draft
			startTableHeaderRow();
			cancelResponse = testCancel(soId);
			endTableHeaderRow();

			// validating the cancel response
			startTableRow();
			validateCancelResponse(soId, cancelResponse, createResponse,
					retrieveResponseAfterPost.getOrderstatus().getStatus());
			endTableRow();
			
			logger.info("Testing completed");
			insertRows();
			startFinalSummaryRow();
			decorateHeader("Test Completed succesfully. ");
			endFinalSummaryRow();

		}
		// catching a generic exception, since there is no recovery mechanism
		// designed / required for this automated test.
		catch (Exception e) {
			startFinalSummaryRow();
			logger.info("Testing interrupted due to exception: " + e);
			decorateHeader("Test interrupted due to exception. Please check the log files for details");
			endFinalSummaryRow();
		} finally {
			Long testEndTime = System.currentTimeMillis();
			Double time = (testEndTime.doubleValue() - testStartTime.doubleValue()) / 1000;
			startFinalSummaryRow();
			decorateHeader("Total time taken for the test is <span style=\"font-weight: 700; background-color: #FF0000\">  "
					+ time + " seconds. </span>");
			endFinalSummaryRow();
			endTable();
			post.releaseConnection();
		}
		logger.info("Leaving ServiceAPITest.testMain");
		return compareResult;
	}

	/**
	 * Utility method to retrieve the file contents as a XML string
	 * 
	 * @param inputFile
	 * @return String
	 * @throws IOException
	 */
	private String readFileContents(File inputFile) throws IOException {
		StringBuffer contents = new StringBuffer(1000);
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			contents.append(readData);
			buf = new char[1024];
		}
		reader.close();
		return contents.toString();
	}

	/**
	 * Method to test the create service
	 * 
	 * @param type   indicates SKU / Task
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public SOCreateResponse testCreate(String type) throws HttpException, IOException {
		Long startTime = System.currentTimeMillis();
		logger.info("Entering ServiceAPITest.testCreate()");

		if (type.equals("Sku"))
			inputFileName = this.getClass().getResource(
					"/com/newco/marketplace/test/xmlData/CreateSO_SKUs.xml").getFile();
		else if (type.equals("Task"))
			inputFileName = this.getClass().getResource(
					"/com/newco/marketplace/test/xmlData/CreateSO_Tasks.xml").getFile();

		// hack to resolve file naming convention issues.
		inputFileName = inputFileName.replaceAll("%20", " ");
		inputFile = new File(inputFileName);

		String fileContents = readFileContents(inputFile);
		xstreamUtility = new XStreamUtility();
		createRequest = xstreamUtility.getCreateRequestObject(fileContents);

		post = new PostMethod(getBaseUrl() + "/MarketPublicAPI/serviceorder/create");
		post.addRequestHeader("Accept", "text/xml");

		entity = new FileRequestEntity(inputFile,"text/xml; charset=ISO-8859-1");
		post.setRequestEntity(entity);

		httpclient.executeMethod(post);
		fromxml = post.getResponseBodyAsString();
		logger.info("The response from create service is: " + fromxml);

		SOCreateResponse createResponse = getCreateResponseObject(fromxml);

		logger.info("Leaving ServiceAPITest.testCreate()");
		Long endTime = System.currentTimeMillis();
		Double time = (endTime.doubleValue() - startTime.doubleValue()) / 1000;
		insertExecutionTimeStamp("Create (with " + type + ")", time);
		return createResponse;
	}

	/**
	 * Method to Validate the create response
	 * 
	 * @param createResponse
	 * @param type
	 */
	private void validateCreateResponse(SOCreateResponse createResponse,
			String type) {
		Long startTime = System.currentTimeMillis();
		logger.info("Entering ServiceAPITest.validateCreateResponse()");
		if (!createResponse.getResults().getResult().get(0).getCode().equals("0"))
			insertSuccessMessage("Create (with " + type + ") ");
		else
			insertFailMessage("Create (with " + type + ") ");

		todayDate = createResponse.getOrderstatus().getCreatedDate(); 
		decorateHeader("Validating <u>Create with (" + type + ") Response</u>");
		compare(createResponse.getResults().getResult().get(0).getMessage(),
				"Service Order created successfully",
				"Success message (Service Order created Successfully)");
		compare(createResponse.getOrderstatus().getStatus(), "Draft", "Order Status");
		compareDate(createResponse.getOrderstatus().getCreatedDate(), todayDate, "Created Date");
		insertRows();
		Long endTime = System.currentTimeMillis();
		Double time = (endTime.doubleValue() - startTime.doubleValue()) / 1000;
		decorateHeader("Validation of Create Service order with ("+ type + ") completed. Total " +
				"time taken for validation of this service is  " + time + " second(s).");
		logger.info("Leaving ServiceAPITest.validateCreateResponse()");
	}

	/**
	 * Method to test the retrieve service
	 * 
	 * @param soId
	 * @return
	 * @throws HttpException
	 * @throws IOException
	 */
	public SORetrieveResponse testRetrieve(String soId) throws HttpException, IOException {
		Long startTime = System.currentTimeMillis();
		logger.info("Entering ServiceAPITest.testRetrieve()");
		post = new PostMethod(getBaseUrl() + "/MarketPublicAPI/serviceorder/get/" + soId);
		post.addRequestHeader("Accept", "text/xml");

		inputFileName = this.getClass().getResource("/com/newco/marketplace/test/xmlData/GetSO.xml").getFile();
		inputFileName = inputFileName.replaceAll("%20", " ");
		inputFile = new File(inputFileName);

		entity = new FileRequestEntity(inputFile, "text/xml; charset=ISO-8859-1");
		post.setRequestEntity(entity);
		fromxml = null;
		httpclient.executeMethod(post);
		fromxml = post.getResponseBodyAsString();
		SORetrieveResponse retrieveResponse = getRetrieveResponseObject(fromxml);

		logger.info("The response from retrieve test for soId " + soId + "is..." + fromxml);
		logger.info("Leaving ServiceAPITest.testRetrieve()");
		Long endTime = System.currentTimeMillis();

		Double time = (endTime.doubleValue() - startTime.doubleValue()) / 1000;
		insertExecutionTimeStamp("Retrieve", time);
		return retrieveResponse;
	}

	/**
	 * Method to validate the retrieve response
	 * 
	 * @param createRequest
	 * @param retrieveResponse
	 */
	private void validateRetrieveResponse(SOCreateRequest createRequest,
			SORetrieveResponse retrieveResponse) {
		Long startTime = System.currentTimeMillis();
		logger.info("Entering ServiceAPITest.validateRetrieveResponse()");
		if (!retrieveResponse.getResults().getResult().get(0).getCode().equals("0"))
			insertSuccessMessage("Retrieve");
		else
			insertFailMessage("Retrieve");
		decorateHeader("Validating <u>Retrieve Response.</u> ");
		ServiceOrderBean serviceOrder = createRequest.getServiceOrder();
		compare(retrieveResponse.getResults().getResult().get(0).getMessage(),
				"Retrieve Successful for ServiceOrder",
				"Success message (Retrieve Successful for ServiceOrder)");
		if (serviceOrder.getAttachments() != null
				&& retrieveResponse.getAttachments() != null) {
			decorateRows(compareResult, "Starting Attachment Match");
			int size = serviceOrder.getAttachments().getFilenameList().size();
			for (int counter = 0; counter < size; counter++) {
				compare(serviceOrder.getAttachments().getFilenameList().get(
						counter), retrieveResponse.getAttachments()
						.getFilenameList(), "Attachments");
			}
			decorateRows(compareResult, "Attachement Match Completed.");
		}

		if (serviceOrder.getGeneralSection() != null
				&& retrieveResponse.getSectionGeneral() != null) {
			decorateRows(compareResult, "Starting General Section Match");
			compare(serviceOrder.getGeneralSection().getBuyerTerms(),
					retrieveResponse.getSectionGeneral().getBuyerTerms(),
					"Section General - Buyer Terms");
			compare(serviceOrder.getGeneralSection().getOverview(),
					retrieveResponse.getSectionGeneral().getOverview(),
					"Section General - Overview");
			compare(serviceOrder.getGeneralSection().getSpecialInstructions(),
					retrieveResponse.getSectionGeneral()
							.getSpecialInstructions(),
					"Section General - Special Instructions");
			compare(serviceOrder.getGeneralSection().getTitle(),
					retrieveResponse.getSectionGeneral().getTitle(),
					"Section General - Title");
			decorateRows(compareResult, "General Section Match Completed.");
		}

		if (serviceOrder.getParts().getPartList() != null
				&& retrieveResponse.getParts().getPartList() != null) {
			int size = serviceOrder.getParts().getPartList().size();
			decorateRows(compareResult, "Starting Parts Match for " + size
					+ " part(s)");
			for (int counter = 0; counter < size; counter++) {
				Part requestPart = serviceOrder.getParts().getPartList().get(
						counter);
				Part responsePart = retrieveResponse.getParts().getPartList()
						.get(counter);
				compare(requestPart.getDescription(), responsePart
						.getDescription(), "Part - " + counter + 1
						+ " Description");
				compare(requestPart.getDimensions().getHeight(), responsePart
						.getDimensions().getHeight(), "Part - " + counter + 1
						+ " Height Dimension");
				compare(requestPart.getDimensions().getLength(), responsePart
						.getDimensions().getLength(), "Part - " + counter + 1
						+ " Length Dimension");
				compare(requestPart.getDimensions().getMeasurementType(),
						responsePart.getDimensions().getMeasurementType(),
						"Part - " + counter + 1 + " Measurement Type");
				compare(requestPart.getDimensions().getWeight(), responsePart
						.getDimensions().getWeight(), "Part - " + counter + 1
						+ " Weight Dimension");
				compare(requestPart.getDimensions().getWidth(), responsePart
						.getDimensions().getWidth(), "Part - " + counter + 1
						+ " Width Dimension");
			}
			decorateRows(compareResult, "Parts Match Completed for " + size
					+ " part(s).");
		}

		if (serviceOrder.getPricing() != null
				&& retrieveResponse.getPricing() != null) {
			decorateRows(compareResult, "Starting Pricing Match");
			roundOffAndCompare(serviceOrder.getPricing().getLaborSpendLimit(),
					retrieveResponse.getPricing().getLaborSpendLimit(),
					"Pricing -  Labor Spend Limit");
			roundOffAndCompare(serviceOrder.getPricing().getPartsSpendLimit(),
					retrieveResponse.getPricing().getPartsSpendLimit(),
					"Pricing -  Parts Spend Limit");
			decorateRows(compareResult, "Pricing  Match Completed.");
		}

		if (serviceOrder.getSchedule() != null
				&& retrieveResponse.getSchedule() != null) {
			decorateRows(compareResult, "Starting Schedule Match");
			Schedule requestSchedule = serviceOrder.getSchedule();
			Schedule responseSchedule = retrieveResponse.getSchedule();
			compare(requestSchedule.getConfirmWithCustomer(), responseSchedule
					.getConfirmWithCustomer(),
					"Schedule -  Confirm with Customer");
			compare(requestSchedule.getScheduleType(), responseSchedule
					.getScheduleType(), "Schedule -  Schedule Type");
			compareDate(requestSchedule.getServiceDateTime1(), responseSchedule
					.getServiceDateTime1(), "Schedule -  Service Date Time 1");
			compareDate(requestSchedule.getServiceDateTime2(), responseSchedule
					.getServiceDateTime2(), "Schedule -  Service Date Time 2");
			decorateRows(compareResult, "Schedule Match Completed.");
		}

		if (serviceOrder.getScopeOfWork() != null
				&& retrieveResponse.getScopeOfWork() != null) {
			ScopeOfWork requestSOW = serviceOrder.getScopeOfWork();
			ScopeOfWork responseSOW = retrieveResponse.getScopeOfWork();
			decorateRows(compareResult, "Starting Scope of Work Match");
			compare(requestSOW.getMainCategoryID(), responseSOW
					.getMainCategoryID(), "Scope Of Work -  Main Category ID");
			
			Tasks taskList = requestSOW.getTasks(); 
			
			if(taskList != null){ 
				int size = taskList.getTaskList().size();
				decorateRows(compareResult, "Starting Tasks Match for " + size + " Task(s)");
				for (int counter = 0;counter < size; counter++){ 
					Task requestTask = taskList.getTaskList().get(counter); 
					Task responseTask =responseSOW.getTasks().getTaskList().get(counter);
					compare(requestTask.getTaskName(), responseTask.getTaskName(), "Scope of Work - Task - Task Name");
					compare(requestTask.getCategoryID(), responseTask.getCategoryID(), "Scope of Work - Task - CategoryID"); 
					compare(requestTask.getServiceType(), responseTask.getServiceType(), "Scope of Work - Task - Service Type"); 
					compare(requestTask.getTaskComment(), responseTask.getTaskComment(), "Scope of Work - Task - Task Comment");
				}
				decorateRows(compareResult, "Task Match Completed for " + size + " tasks.");
			}
					
			decorateRows(compareResult, "Scope of Work Match Completed.");
		}

		if (serviceOrder.getServiceLocation() != null
				&& retrieveResponse.getServiceLocation() != null) {
			Location requestLocation = serviceOrder.getServiceLocation();
			Location responseLocation = retrieveResponse.getServiceLocation();
			decorateRows(compareResult, "Starting Service Location Match");
			compare(requestLocation.getAddress1(), responseLocation
					.getAddress1(), "Service Location -  Address 1");
			compare(requestLocation.getAddress2(), responseLocation
					.getAddress2(), "Service Location -  Address 2");
			compare(requestLocation.getCity(), responseLocation.getCity(),
					"Service Location -  City");
			compare(requestLocation.getLocationName(), responseLocation
					.getLocationName(), "Service Location -  Location Name");
			compare(requestLocation.getLocationType(), responseLocation
					.getLocationType(), "Service Location -  Location Type");
			compare(requestLocation.getNote(), responseLocation.getNote(),
					"Service Location -  Note");
			compare(requestLocation.getState(), responseLocation.getState(),
					"Service Location -  State");
			compare(requestLocation.getZip(), responseLocation.getZip(),
					"Service Location -  Zip");
			decorateRows(compareResult, "Service Location Match Completed.");
		}
		insertRows();
		Long endTime = System.currentTimeMillis();
		Double time = (endTime.doubleValue() - startTime.doubleValue()) / 1000;
		decorateHeader("Retrieve validation Completed. Total time taken for validation of this service is  "
				+ time + " second(s).");
		logger.info("Leaving ServiceAPITest.validateRetrieveResponse()");
	}

	/**
	 * Method to test the cancel service
	 */
	public SOCancelResponse testCancel(String soId) throws HttpException,
			IOException {
		Long startTime = System.currentTimeMillis();
		logger.info("Entering ServiceAPITest.testCancel()");
		post = new PostMethod(
				getBaseUrl() + "/MarketPublicAPI/serviceorder/cancel/" + soId);
		post.addRequestHeader("Accept", "text/xml");
		inputFileName = this.getClass().getResource(
				"/com/newco/marketplace/test/xmlData/CancelSO.xml").getFile();
		inputFileName = inputFileName.replaceAll("%20", " ");
		inputFile = new File(inputFileName);

		entity = new FileRequestEntity(inputFile,
				"text/xml; charset=ISO-8859-1");
		post.setRequestEntity(entity);
		fromxml = null;
		httpclient.executeMethod(post);
		fromxml = post.getResponseBodyAsString();
		logger.info("The response from cancel test for soId " + soId + "is..."
				+ fromxml);
		SOCancelResponse cancelResponse = getCancelResponseObject(fromxml);
		logger.info("Leaving ServiceAPITest.testCancel()");
		Long endTime = System.currentTimeMillis();
		Double time = (endTime.doubleValue() - startTime.doubleValue()) / 1000;
		insertExecutionTimeStamp("Cancel", time);
		return cancelResponse;
	}

	/**
	 * Method to validate the cancel response
	 */
	private void validateCancelResponse(String soId,
			SOCancelResponse cancelResponse, SOCreateResponse createResponse,
			String status) {
		Long startTime = System.currentTimeMillis();
		logger.info("Entering ServiceAPITest.validateCancelResponse()");
		if (!cancelResponse.getResults().getResult().get(0).getCode().equals(
				"0"))
			insertSuccessMessage("Cancel");
		else
			insertFailMessage("Cancel");
		decorateHeader("Validating <u>Cancel Response for " + status+ " status.</u> ");
		String cancelStatusMessage = "";
		if (status.equals("Draft"))
			cancelStatusMessage = "Draft is sucessfully deleted";
		else if (status.equals("Routed"))
			cancelStatusMessage = "Service Order voided successfully.";

		compare(cancelResponse.getResults().getResult().get(0).getMessage(),
				cancelStatusMessage,
				"Success message (" + cancelStatusMessage + ")");
		compare(soId, cancelResponse.getOrderstatus().getSoId(), "SoId");
		
		compare("Voided", cancelResponse.getOrderstatus().getStatus(),
				"Status");
		compareDate(createResponse.getOrderstatus().getCreatedDate(),
				cancelResponse.getOrderstatus().getCreatedDate(),
				"Created date");
		insertRows();
		Long endTime = System.currentTimeMillis();
		Double time = (endTime.doubleValue() - startTime.doubleValue()) / 1000;
		decorateHeader("Cancel validation Completed. Total time taken for validation of this service is  "
				+ time + " second(s).");
		logger.info("Leaving ServiceAPITest.validateCancelResponse()");
	}

	/**
	 * Method to test the add note service
	 */
	public SOAddNoteResponse testAddNote(String soId) throws HttpException,
			IOException {
		Long startTime = System.currentTimeMillis();
		logger.info("Entering ServiceAPITest.testAddNote()");
		post = new PostMethod(
				getBaseUrl() + "/MarketPublicAPI/serviceorder/addNote/" + soId);
		post.addRequestHeader("Accept", "text/xml");
		inputFileName = this.getClass().getResource(
				"/com/newco/marketplace/test/xmlData/AddNote.xml").getFile();
		inputFileName = inputFileName.replaceAll("%20", " ");
		inputFile = new File(inputFileName);

		entity = new FileRequestEntity(inputFile,
				"text/xml; charset=ISO-8859-1");
		post.setRequestEntity(entity);
		fromxml = null;
		httpclient.executeMethod(post);
		fromxml = post.getResponseBodyAsString();
		logger.info("The response from Add Note test for soId " + soId
				+ "is..." + fromxml);
		SOAddNoteResponse addNoteResponse = getAddNoteResponseObject(fromxml);
		logger.info("Leaving ServiceAPITest.testAddNote()");
		Long endTime = System.currentTimeMillis();
		Double time = (endTime.doubleValue() - startTime.doubleValue()) / 1000;
		insertExecutionTimeStamp("AddNote", time);
		return addNoteResponse;
	}

	/**
	 * Method to validate the add note response
	 */
	private void validateAddNoteResponse(SOAddNoteResponse addNoteResponse) {
		Long startTime = System.currentTimeMillis();
		logger.info("Entering ServiceAPITest.validateAddNoteResponse()");
		if (!addNoteResponse.getResults().getResult().get(0).getCode().equals(
				"0"))
			insertSuccessMessage("Add Note ");
		else
			insertFailMessage("Add Note ");
		decorateHeader("Validating <u>Add Note Response.</u>");
		compare(addNoteResponse.getResults().getResult().get(0).getMessage(),
				"Note Added", "Success message (Note Added)");
		insertRows();
		Long endTime = System.currentTimeMillis();
		Double time = (endTime.doubleValue() - startTime.doubleValue()) / 1000;
		decorateHeader("Add Note validation Completed. Total time taken for validation of this service is  "
				+ time + " second(s).");
		logger.info("Leaving ServiceAPITest.validateAddNoteResponse()");
	}

	/**
	 * Method to test the reschedule service
	 */
	public SORescheduleResponse testReschedule(String soId)
			throws HttpException, IOException {
		Long startTime = System.currentTimeMillis();
		logger.info("Entering ServiceAPITest.testReschedule()");

		inputFileName = this.getClass().getResource(
				"/com/newco/marketplace/test/xmlData/RescheduleSO.xml")
				.getFile();
		inputFileName = inputFileName.replaceAll("%20", " ");
		inputFile = new File(inputFileName);

		String fileContents = readFileContents(inputFile);

		xstreamUtility = new XStreamUtility();
		rescheduleRequest = xstreamUtility
				.getRescheduleRequestObject(fileContents);

		post = new PostMethod(
				getBaseUrl() + "/MarketPublicAPI/serviceorder/reschedule/"
						+ soId);
		post.addRequestHeader("Accept", "text/xml");

		entity = new FileRequestEntity(inputFile,
				"text/xml; charset=ISO-8859-1");
		post.setRequestEntity(entity);
		fromxml = null;
		httpclient.executeMethod(post);
		fromxml = post.getResponseBodyAsString();
		SORescheduleResponse rescheduleResponse = getRescheduleResponseObject(fromxml);
		logger.info("The response from reschedule test for soId " + soId
				+ "is..." + fromxml);
		logger.info("Leaving ServiceAPITest.testReschedule()");
		Long endTime = System.currentTimeMillis();
		Double time = (endTime.doubleValue() - startTime.doubleValue()) / 1000;
		insertExecutionTimeStamp("Reschedule", time);
		return rescheduleResponse;
	}

	/**
	 * Method to validate the reschedule response
	 */
	private void validateRescheduleResponse(SOCreateResponse createResponse,
			SORescheduleResponse rescheduleResponse) {
		Long startTime = System.currentTimeMillis();
		logger.info("Entering ServiceAPITest.validateRescheduleResponse()");
		if (!rescheduleResponse.getResults().getResult().get(0).getCode()
				.equals("0"))
			insertSuccessMessage("Reschedule");
		else
			insertFailMessage("Reschedule");
		decorateHeader("Validating <u>Reschedule Response.</u> ");
		compare(
				rescheduleResponse.getResults().getResult().get(0).getMessage(),
				"Service Order rescheduled request was submitted",
				"Success message (Service Order rescheduled request was submitted)");
		compareDate(createResponse.getOrderstatus().getCreatedDate(),
				rescheduleResponse.getOrderstatus().getCreatedDate(),
				"Created date");
		compare(createResponse.getOrderstatus().getStatus(), rescheduleResponse
				.getOrderstatus().getStatus(), "Order Status");
		compare(createResponse.getOrderstatus().getSoId(), rescheduleResponse
				.getOrderstatus().getSoId(), "SO ID");
		insertRows();
		Long endTime = System.currentTimeMillis();
		Double time = (endTime.doubleValue() - startTime.doubleValue()) / 1000;
		decorateHeader("Reschedule validation Completed. Total time taken for validation of this service is  "
				+ time + " second(s).");
		logger.info("Leaving ServiceAPITest.validateRescheduleResponse()");

	}

	/**
	 * Method to validate the retrieve response after a reschedule
	 */
	private void validateRetrieveAfterReschedule(String soId, SORetrieveResponse retrieveResponse) {
		Long startTime = System.currentTimeMillis();
		logger.info("Entering ServiceAPITest.validateRetrieveAfterReschedule()");
		if (!retrieveResponse.getResults().getResult().get(0).getCode().equals(
				"0"))
			insertSuccessMessage("Retrieve After Reschedule");
		else
			insertFailMessage("Retrieve After Reschedule");
		decorateHeader("Validating <u>Retrieve Response after Reschedule.</u> ");
		
		compare(soId, retrieveResponse.getOrderstatus().getSoId(), "SoId");
		compare(rescheduleRequest.getSoRescheduleInfo().getScheduleType(),
				retrieveResponse.getSchedule().getScheduleType(),
				"Schedule Type");
		if (retrieveResponse.getSchedule().getScheduleType().equalsIgnoreCase(
				"Range")) {
			compareDate(rescheduleRequest.getSoRescheduleInfo()
					.getServiceDateTime1(), retrieveResponse.getSchedule()
					.getServiceDateTime1(), "Service Date Time 1");
			compareDate(rescheduleRequest.getSoRescheduleInfo()
					.getServiceDateTime2(), retrieveResponse.getSchedule()
					.getServiceDateTime2(), "Service Date Time 2");
		} else
			compareDate(rescheduleRequest.getSoRescheduleInfo()
					.getServiceDateTime1(), retrieveResponse.getSchedule()
					.getServiceDateTime1(), "Service Date Time 1");
		insertRows();
		Long endTime = System.currentTimeMillis();
		Double time = (endTime.doubleValue() - startTime.doubleValue()) / 1000;
		decorateHeader("Retrieve validation after Reschedule Completed. Total time taken for validation of this service is  "
				+ time + " second(s).");
		logger.info("Leaving ServiceAPITest.validateRetrieveAfterReschedule()");
	}

	
	
	/**
	 * Method to test the post service
	 */
	public SOPostResponse testPost(String soId) throws HttpException,
			IOException {
		Long startTime = System.currentTimeMillis();
		logger.info("Entering ServiceAPITest.testPost()");
		post = new PostMethod(
				getBaseUrl() + "/MarketPublicAPI/serviceorder/post/" + soId);
		post.addRequestHeader("Accept", "text/xml");

		inputFileName = this.getClass().getResource(
				"/com/newco/marketplace/test/xmlData/PostSO.xml").getFile();
		inputFileName = inputFileName.replaceAll("%20", " ");
		inputFile = new File(inputFileName);

		entity = new FileRequestEntity(inputFile,
				"text/xml; charset=ISO-8859-1");
		post.setRequestEntity(entity);
		fromxml = null;
		httpclient.executeMethod(post);
		fromxml = post.getResponseBodyAsString();
		SOPostResponse postResponse = getPostResponseObject(fromxml);
		logger.info("The response from Post test for soId " + soId + "is..."
				+ fromxml);
		logger.info("Leaving ServiceAPITest.testPost()");
		Long endTime = System.currentTimeMillis();
		Double time = (endTime.doubleValue() - startTime.doubleValue()) / 1000;
		insertExecutionTimeStamp("Post", time);
		return postResponse;
	}

	/**
	 * Method to validate the post response
	 */
	private void validatePostResponse(String soId, SOPostResponse postResponse) {
		Long startTime = System.currentTimeMillis();
		logger.info("Entering ServiceAPITest.validatePostResponse()");
		if (!postResponse.getResults().getResult().get(0).getCode().equals("0"))
			insertSuccessMessage("Post");
		else
			insertFailMessage("Post");
		decorateHeader("Validating <u>Post Response.</u>");
		compare(soId, postResponse.getOrderstatus().getSoId(), "SoId");
		compare("Routed", postResponse.getOrderstatus().getStatus(), "Status");
		insertRows();
		Long endTime = System.currentTimeMillis();
		Double time = (endTime.doubleValue() - startTime.doubleValue()) / 1000;
		decorateHeader("Post validation Completed. Total time taken for validation of this service is  "
				+ time + " second(s).");
		logger.info("Leaving ServiceAPITest.validatePostResponse()");
	}

	
	/**
	 * Method to validate the retrieve response after a Post
	 */
	private void validateRetrieveAfterPost(
			String soId, SORetrieveResponse retrieveResponse) {
		Long startTime = System.currentTimeMillis();
		logger.info("Entering ServiceAPITest.validateRetrieveAfterPost()");
		if (!retrieveResponse.getResults().getResult().get(0).getCode().equals(
				"0"))
			insertSuccessMessage("Retrieve After Post");
		else
			insertFailMessage("Retrieve After Post");
		decorateHeader("Validating <u>Retrieve Response after Post.</u> ");
		compare(soId, retrieveResponse.getOrderstatus().getSoId(), "SoId");
		compare("Routed", retrieveResponse.getOrderstatus().getStatus(), "Status (Routed)");
		compareDate(todayDate, retrieveResponse.getOrderstatus().getCreatedDate(),"Created Date");
		
		insertRows();
		Long endTime = System.currentTimeMillis();
		Double time = (endTime.doubleValue() - startTime.doubleValue()) / 1000;
		decorateHeader("Retrieve validation after Post Completed. Total time taken for validation of this service is  "
				+ time + " second(s).");
		logger.info("Leaving ServiceAPITest.validateRetrieveAfterPost()");
	}
	
	/**
	 * Method to test the search service
	 */
	public SOSearchResponse testSearch() throws HttpException, IOException {
		Long startTime = System.currentTimeMillis();
		logger.info("Entering ServiceAPITest.testSearch()");

		post = new PostMethod(
				getBaseUrl() + "/MarketPublicAPI/serviceorder/search/");
		post.addRequestHeader("Accept", "text/xml");
		inputFileName = this.getClass().getResource(
				"/com/newco/marketplace/test/xmlData/SearchSO.xml").getFile();
		inputFileName = inputFileName.replaceAll("%20", " ");
		inputFile = new File(inputFileName);

		entity = new FileRequestEntity(inputFile,
				"text/xml; charset=ISO-8859-1");
		post.setRequestEntity(entity);
		fromxml = null;
		httpclient.executeMethod(post);
		fromxml = post.getResponseBodyAsString();
		SOSearchResponse searchResponse = getSearchResponseObject(fromxml);
		logger.info("The response from Search test for soId is..." + fromxml);
		logger.info("Leaving ServiceAPITest.testSearch()");
		Long endTime = System.currentTimeMillis();
		Double time = (endTime.doubleValue() - startTime.doubleValue()) / 1000;
		insertExecutionTimeStamp("Search", time);
		return searchResponse;
	}

	/**
	 * Method to validate the search response
	 */
	private void validateSearchResponse(String soId,
			SOSearchResponse searchResponse, String type) {
		Long startTime = System.currentTimeMillis();
		logger.info("Entering ServiceAPITest.validateSearchResponse()");
		if (!searchResponse.getResults().getResult().get(0).getCode().equals(
				"0"))
			insertSuccessMessage("Search");
		else
			insertFailMessage("Search");
		decorateHeader("Validating <u>Search Response.</u>");

		if (searchResponse.getSearchResults() != null) {
			SearchResults searchResults = searchResponse.getSearchResults();
			List<OrderStatus> orderStatusList = searchResults.getOrderstatus();
			for (OrderStatus orderstatus : orderStatusList) {
				if (orderstatus.getSoId().equals(soId)) {
					compare(soId, orderstatus.getSoId(), "SoId");
					compareDate(todayDate, orderstatus.getCreatedDate(),
							"Created Date");
					compare(type, orderstatus.getStatus(), "Status");
				}
			}
		}
		insertRows();
		Long endTime = System.currentTimeMillis();
		Double time = (endTime.doubleValue() - startTime.doubleValue()) / 1000;
		decorateHeader("Search validation Completed. Total time taken for validation of this service is  "
				+ time + " second(s).");
		logger.info("Leaving ServiceAPITest.validateSearchResponse()");
	}

	/**
	 * XStream Utility methods to convert XML String into object and vice versa
	 */

	// This method generates a SOCreateResponse object from the given XML string
	public SOCreateResponse getCreateResponseObject(String soXml) {
		logger.info("Entering ServiceAPITest.getCreateRequestObject()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] createClasses = new Class[] { Phone.class,
				Attachments.class, Contact.class, Contacts.class,
				CustomReferences.class, CustomReference.class,
				Dimensions.class, GeneralSection.class, Identification.class,
				Parts.class, Part.class, Pricing.class, Schedule.class,
				ScopeOfWork.class, Location.class, ServiceOrderBean.class,
				SOCreateResponse.class, Tasks.class, Task.class, Results.class,
				Result.class, ErrorResult.class };
		xstream.processAnnotations(createClasses);

		SOCreateResponse soCreateResponse = (SOCreateResponse) xstream
				.fromXML(soXml);
		logger.info("Leaving ServiceAPITest.getCreateRequestObject()");
		return soCreateResponse;
	}

	// This method generates a SORetrieveResponse object from the given XML string
	public SORetrieveResponse getRetrieveResponseObject(String soXml) {
		logger.info("Entering ServiceAPITest.getRetrieveResponseObject()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] createClasses = new Class[] { Phone.class,
				Attachments.class, Contact.class, Contacts.class,
				CustomReferences.class, CustomReference.class,
				Dimensions.class, GeneralSection.class, Identification.class,
				Parts.class, Part.class, Pricing.class, Schedule.class,
				ScopeOfWork.class, Location.class, ServiceOrderBean.class,
				SORetrieveResponse.class, Tasks.class, Task.class };
		xstream.processAnnotations(createClasses);
		SORetrieveResponse retrieveResponse = (SORetrieveResponse) xstream
				.fromXML(soXml);
		logger.info("Leaving ServiceAPITest.getRetrieveResponseObject()");
		return retrieveResponse;
	}

	// This method generates a SORescheduleResponse object from the given XML string
	public SORescheduleResponse getRescheduleResponseObject(String soXml) {
		logger.info("Entering ServiceAPITest.getRescheduleResponseObject()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] createClasses = new Class[] { Phone.class,
				Attachments.class, Contact.class, Contacts.class,
				CustomReferences.class, CustomReference.class,
				Dimensions.class, GeneralSection.class, Identification.class,
				Parts.class, Part.class, Pricing.class, Schedule.class,
				ScopeOfWork.class, Location.class, ServiceOrderBean.class,
				SORescheduleResponse.class, Tasks.class, Task.class };
		xstream.processAnnotations(createClasses);
		SORescheduleResponse rescheduleResponse = (SORescheduleResponse) xstream
				.fromXML(soXml);
		logger.info("Leaving ServiceAPITest.getRescheduleResponseObject()");
		return rescheduleResponse;
	}

	// This method generates a SOPostResponse object from the given XML string
	public SOPostResponse getPostResponseObject(String soXml) {
		logger.info("Entering ServiceAPITest.getPostResponseObject()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] createClasses = new Class[] { Phone.class,
				Attachments.class, Contact.class, Contacts.class,
				CustomReferences.class, CustomReference.class,
				Dimensions.class, GeneralSection.class, Identification.class,
				Parts.class, Part.class, Pricing.class, Schedule.class,
				ScopeOfWork.class, Location.class, ServiceOrderBean.class,
				SOPostResponse.class, Tasks.class, Task.class, Results.class,
				Result.class, ErrorResult.class };
		xstream.processAnnotations(createClasses);
		SOPostResponse postResponse = (SOPostResponse) xstream.fromXML(soXml);
		logger.info("Leaving ServiceAPITest.getPostResponseObject()");
		return postResponse;
	}

	// This method generates a SOSearchResponse object from the given XML string
	public SOSearchResponse getSearchResponseObject(String soXml) {
		logger.info("Entering ServiceAPITest.getsearchResponseObject()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] createClasses = new Class[] { Phone.class,
				Attachments.class, Contact.class, Contacts.class,
				CustomReferences.class, CustomReference.class,
				Dimensions.class, GeneralSection.class, Identification.class,
				Parts.class, Part.class, Pricing.class, Schedule.class,
				ScopeOfWork.class, Location.class, ServiceOrderBean.class,
				SOSearchResponse.class, Tasks.class, Task.class, Results.class,
				Result.class, ErrorResult.class };
		xstream.processAnnotations(createClasses);
		SOSearchResponse searchResponse = (SOSearchResponse) xstream
				.fromXML(soXml);
		logger.info("Leaving ServiceAPITest.getsearchResponseObject()");
		return searchResponse;
	}

	// This method generates a SOCancelResponse object from the given XML string
	public SOCancelResponse getCancelResponseObject(String soXml) {
		logger.info("Entering ServiceAPITest.getCancelResponseObject()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] createClasses = new Class[] { Phone.class,
				Attachments.class, Contact.class, Contacts.class,
				CustomReferences.class, CustomReference.class,
				Dimensions.class, GeneralSection.class, Identification.class,
				Parts.class, Part.class, Pricing.class, Schedule.class,
				ScopeOfWork.class, Location.class, ServiceOrderBean.class,
				SOCancelResponse.class, Tasks.class, Task.class, Results.class,
				Result.class, ErrorResult.class };
		xstream.processAnnotations(createClasses);
		SOCancelResponse cancelResponse = (SOCancelResponse) xstream
				.fromXML(soXml);
		logger.info("Leaving ServiceAPITest.getCancelResponseObject()");
		return cancelResponse;
	}

	// This method generates a SOAddNoteResponse object from the given XML string
	public SOAddNoteResponse getAddNoteResponseObject(String soXml) {
		logger.info("Entering ServiceAPITest.getAddNoteResponseObject()");
		XStream xstream = new XStream(new DomDriver());
		xstream.registerConverter(new ResultConvertor());
		xstream.registerConverter(new ErrorConvertor());
		Class<?>[] createClasses = new Class[] { Phone.class,
				Attachments.class, Contact.class, Contacts.class,
				CustomReferences.class, CustomReference.class,
				Dimensions.class, GeneralSection.class, Identification.class,
				Parts.class, Part.class, Pricing.class, Schedule.class,
				ScopeOfWork.class, Location.class, ServiceOrderBean.class,
				SOAddNoteResponse.class, Tasks.class, Task.class,
				Results.class, Result.class, ErrorResult.class };
		xstream.processAnnotations(createClasses);
		SOAddNoteResponse addNoteResponse = (SOAddNoteResponse) xstream
				.fromXML(soXml);
		logger.info("Leaving ServiceAPITest.getAddNoteResponseObject()");
		return addNoteResponse;
	}

	// This method converts the SORetrieveRequest into a XML String
	public String getRetrieveRequestXML(SORetrieveRequest retrieveRequest) {
		logger.info("Entering ServiceAPITest.getRetrieveRequestXML()");
		XStream xstream = new XStream(new DomDriver());
		Class<?>[] createClasses = new Class[] { Phone.class,
				Attachments.class, Contact.class, Contacts.class,
				CustomReferences.class, CustomReference.class,
				Dimensions.class, GeneralSection.class, Identification.class,
				Parts.class, Part.class, Pricing.class, Schedule.class,
				ScopeOfWork.class, Location.class, ServiceOrderBean.class,
				SOCreateResponse.class, Tasks.class, Task.class };
		xstream.processAnnotations(createClasses);
		String retrieveRequestXml = (String) xstream.toXML(retrieveRequest);
		logger.info("Leaving ServiceAPITest.getRetrieveRequestXML()");
		return retrieveRequestXml;
	}

	// This method converts the SOCreateRequest into a XML String
	public String getCreateRequestXML(SOCreateRequest createRequest) {
		logger.info("Entering ServiceAPITest.getCreateRequestXML()");
		XStream xstream = new XStream(new DomDriver());
		Class<?>[] createClasses = new Class[] { Phone.class,
				Attachments.class, Contact.class, Contacts.class,
				CustomReferences.class, CustomReference.class,
				Dimensions.class, GeneralSection.class, Identification.class,
				Parts.class, Part.class, Pricing.class, Schedule.class,
				ScopeOfWork.class, Location.class, ServiceOrderBean.class,
				SOCreateResponse.class, Tasks.class, Task.class };
		xstream.processAnnotations(createClasses);
		String createRequestXml = (String) xstream.toXML(createRequest);
		logger.info("Leaving ServiceAPITest.getCreateRequestXML()");
		return createRequestXml;
	}

	/***************************************************************************
	 * Method to compare two given objects
	 * 
	 * @param objectOne
	 * @param objectTwo
	 * @param attributeValue
	 */
	private void compare(Object objectOne, Object objectTwo,
			String attributeValue) {
		if (objectOne.equals(objectTwo))
			compareResult
					.append("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
							"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "
							+ "<font color=green> Retrieved "
							+ attributeValue
							+ " value matches expected value. </font> ");
		else
			compareResult
					.append("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
							"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "
							+ "<font color=red>	Retrieved "
							+ attributeValue
							+ " value <u> <b>"
							+ objectTwo.toString()
							+ " </b> </u>  does not match expected value "
							+ objectOne.toString() + " </font> ");
	}

	/**
	 * Method to Round of integer objects
	 * 
	 * @param objectOne
	 * @param objectTwo
	 * @param attributeValue
	 */
	private void roundOffAndCompare(String objectOne, String objectTwo,
			String attributeValue) {
		String roundedObjectOne[] = objectOne.split("\\.");
		String roundedObjectTwo[] = objectTwo.split("\\.");
		compare(roundedObjectOne[0], roundedObjectTwo[0], attributeValue);
	}

	/**
	 * Method to compare two date objects
	 * 
	 * @param dateOne
	 * @param dateTwo
	 * @param attributeValue
	 */
	private void compareDate(Object dateOne, Object dateTwo,
			String attributeValue) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		Date convertedDateOne = null;
		Date convertedDateTwo = null;
		try {
			convertedDateOne = sdf.parse(dateOne.toString());
			convertedDateTwo = sdf.parse(dateTwo.toString());
		} catch (ParseException e) {
			logger
					.error("Exception while converting date: Check if the date format is correct? "
							+ e);
		}
		compare(convertedDateOne, convertedDateTwo, attributeValue);
	}

	/**
	 * 
	 * Utility methods to decorate the HTML table, row, header etc.
	 * 
	 */
	private void decorateHeader(String content) {
		compareResult
				.append("<b>&nbsp;&nbsp; <font color = \"#990000\"> "
						+ content + "</font></b>");
	}

	private void insertRows() {
		compareResult.append("<br>");
	}

	private StringBuffer decorateRows(StringBuffer buffer, String content) {
		return buffer.append("<br> <font color = =\"#990000\"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + content + "</font>");
	}

	private void insertFailMessage(String content) {
		compareResult.append("<br> <b> <font color = red> &nbsp;&nbsp; Invocation of <u>"
				+ content + "</u> Service Failed </b> </font> <br>");
	}

	private void insertSuccessMessage(String content) {
		compareResult.append("<br> <font color = \"#990000\"> &nbsp;&nbsp; Invocation of  "
				+ content + " Service Succeeded  </font> <br>");
	}

	private void insertExecutionTimeStamp(String serviceName, Double time) {
		compareResult
				.append("<br>  <b> <font color =\"#990000\"> (Test #" + testCount + "): "
						+ serviceName + " service took " + time + " second(s) </b>");
		testCount++;
	}

	private void startTableRow() {
		compareResult
				.append("<tr><td bgcolor=\"#FFE4CA\"> <div style=\"border:1px GREEN solid; height:190px; overflow:scroll;\">");
	}

	private void endTableRow() {
		compareResult.append("</div></td></tr>");
	}

	private void startTableHeaderRow() {
		compareResult.append("<td bgcolor=\"#FFE4CA\">");
	}

	private void endTableHeaderRow() {
		compareResult.append("</td></tr>");
	}

	private void startFinalSummaryRow() {
		compareResult.append("<tr><td bgcolor=\"#FF9E3E\">");
	}

	private void endFinalSummaryRow() {
		compareResult.append("</td></tr>");
	}

	private void startTable() {
		compareResult
				.append("<div align=\"center\"><table border=\"3\" cellpadding=\"5\" width=\"90%\" " +
						"bordercolorlight=\"#000000\" style=\"border-collapse: collapse\" bordercolordark=\"#000000\">");
	}

	private void endTable() {
		compareResult.append("</table></div>");
	}

	private String getBaseUrl(){ // get base url... get host name + get port/// logger.debugstatement
		String baseUrl = "http://" + getRequest().getServerName() + ":" + getRequest().getLocalPort() + "";
		logger.debug("Inside ServiceAPITests.getBaseUrl(). Retrieved Base URL = " +  baseUrl);
		return baseUrl;
	}
	
	private HttpServletRequest getRequest() {
		return request;
		
	}

	private void setRequest(HttpServletRequest request) {
		this.request = request;
	}

}
