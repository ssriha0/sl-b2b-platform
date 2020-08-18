package com.newco.marketplace.api.mobile.utils.mappers;


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.common.APIRequestVO;
import com.newco.marketplace.api.mobile.beans.EstimateDetails;
import com.newco.marketplace.api.mobile.beans.EstimateHistory;
import com.newco.marketplace.api.mobile.beans.EstimateHistoryDetails;
import com.newco.marketplace.api.mobile.beans.EstimateItemsHistory;
import com.newco.marketplace.api.mobile.beans.EstimateOtherServiceHistory;
import com.newco.marketplace.api.mobile.beans.EstimateOtherServicesHistory;
import com.newco.marketplace.api.mobile.beans.EstimatePartHistory;
import com.newco.marketplace.api.mobile.beans.EstimatePartsHistory;
import com.newco.marketplace.api.mobile.beans.GetEstimateDetailsResponse;
import com.newco.marketplace.api.mobile.beans.LaborTask;
import com.newco.marketplace.api.mobile.beans.LaborTaskHistory;
import com.newco.marketplace.api.mobile.beans.LaborTasks;
import com.newco.marketplace.api.mobile.beans.LaborTasksHistory;
import com.newco.marketplace.api.mobile.beans.OtherService;
import com.newco.marketplace.api.mobile.beans.OtherServices;
import com.newco.marketplace.api.mobile.beans.PricingDetails;
import com.newco.marketplace.api.mobile.beans.so.addEstimate.AddEstimateRequest;
import com.newco.marketplace.api.mobile.beans.so.addEstimate.AddEstimateResponse;
import com.newco.marketplace.api.mobile.beans.so.addEstimate.EstimatePart;
import com.newco.marketplace.api.mobile.beans.so.addEstimate.EstimateTask;
import com.newco.marketplace.api.mobile.beans.so.addEstimate.OtherEstimateService;
import com.newco.marketplace.api.mobile.beans.so.addEstimate.Pricing;
import com.newco.marketplace.api.mobile.beans.sodetails.AddOn;
import com.newco.marketplace.api.mobile.beans.sodetails.Appointment;
import com.newco.marketplace.api.mobile.beans.sodetails.CompletionDocuments;
import com.newco.marketplace.api.mobile.beans.sodetails.Document;
import com.newco.marketplace.api.mobile.beans.sodetails.Documents;
import com.newco.marketplace.api.mobile.beans.sodetails.Note;
import com.newco.marketplace.api.mobile.beans.sodetails.Notes;
import com.newco.marketplace.api.mobile.beans.sodetails.PermitAddon;
import com.newco.marketplace.api.mobile.beans.sodetails.ServiceLocation;
import com.newco.marketplace.api.mobile.beans.sodetails.SignatureDocument;
import com.newco.marketplace.api.mobile.beans.sodetails.SignatureDocuments;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.LatestTripDetails;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.SOTrip;
import com.newco.marketplace.api.mobile.beans.sodetails.v2_0.SOTrips;
import com.newco.marketplace.api.mobile.common.ResultsCode;
import com.newco.marketplace.api.mobile.constants.PublicMobileAPIConstant;
import com.newco.marketplace.business.iBusiness.lookup.ILookupBO;
import com.newco.marketplace.dto.vo.EstimateHistoryTaskVO;
import com.newco.marketplace.dto.vo.EstimateHistoryVO;
import com.newco.marketplace.dto.vo.EstimateTaskVO;
import com.newco.marketplace.dto.vo.EstimateVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.mobile.constants.MPConstants;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.TimeUtils;



/**
 * This class would act as a Mapper class for Mapping Service Object to
 * SODetailsRetrieveResponse Object.
 * 
 * @author Infosys
 * @version 1.0
 */
public class SODetailsRetrieveMapper {
	private Logger LOGGER = Logger.getLogger(SODetailsRetrieveMapper.class);

	private ILookupBO lookupBO;
	public ILookupBO getLookupBO() {
		return lookupBO;
	}

	public void setLookupBO(ILookupBO lookupBO) {
		this.lookupBO = lookupBO;
	}

	/**
	 * This method is for mapping ServiceOrder Object to RetrieveSOResponse
	 * Object.
	 * 
	 * @param serviceOrder
	 *            ServiceOrder
	 * @throws DataException
	 * @return RetrieveServiceOrderMobile
	 */
	/*public RetrieveServiceOrderMobile mapRequest(ServiceOrder serviceOrder,
			String responseFilter) throws DataServiceException {

		RetrieveServiceOrderMobile retrieveSOResponse =null;		
		if (null != responseFilter) {

			if(PublicMobileAPIConstant.SO_DETAILS.equals(responseFilter)){
				retrieveSOResponse=mapSoDetails(serviceOrder);
			}
			else if(PublicMobileAPIConstant.COMPLETION_DETAILS.equals(responseFilter)){
				retrieveSOResponse=mapSoCompleteDetails(serviceOrder);
			}
		}

		return retrieveSOResponse;
	}

	private RetrieveServiceOrderMobile mapSoDetails(ServiceOrder serviceOrder){

		RetrieveServiceOrderMobile retrieveSOResponse =new RetrieveServiceOrderMobile();
		GeneralSection sectionGeneral = new GeneralSection();
		Appointment appointment = new Appointment();
		Buyer buyer=new Buyer();
		ServiceLocation serviceLocation=new ServiceLocation();
		AlternateServiceLocation altServiceLocation=new AlternateServiceLocation(); 
		Scope scope =new Scope();
		Parts parts= new Parts();
		//For mapping General Section
		mapGeneralSection(serviceOrder, sectionGeneral);
		//retrieveSOResponse.setOrderdetails(sectionGeneral);

		mapAppintmentDetails(serviceOrder,appointment);
	//	retrieveSOResponse.setAppointment(appointment);

		mapBuyerDetails(serviceOrder,buyer);
		//retrieveSOResponse.setBuyer(buyer);

		mapPrimaryServiceLocation(serviceOrder, serviceLocation);
		//retrieveSOResponse.setServicelocation(serviceLocation);

		mapAlternateServiceLocation(serviceOrder,altServiceLocation);
		//retrieveSOResponse.setAlternateServiceLocation(altServiceLocation);

		mapScopeOfOrder(serviceOrder,scope);
		//retrieveSOResponse.setScope(scope);

		mapServiceOrderParts(serviceOrder,parts);
		return retrieveSOResponse;
	}

    private RetrieveServiceOrderMobile mapSoCompleteDetails(ServiceOrder serviceOrder){

		RetrieveServiceOrderMobile retrieveSOResponse =null;

		return retrieveSOResponse;

	}
	private void mapGeneralSection(ServiceOrder serviceOrder,
			GeneralSection generalSection) {

		logger.info("Mapping: General Section --->Starts");

		generalSection.setBuyerTerms(StringUtils.isBlank(serviceOrder
				.getBuyerTermsCond()) ? "" : serviceOrder.getBuyerTermsCond());
		generalSection
				.setOverView(StringUtils.isEmpty(serviceOrder.getSowDs()) ? ""
						: serviceOrder.getSowDs());
		generalSection.setSpecialInstuctions(serviceOrder
				.getProviderInstructions());
		generalSection
				.setTitle(StringUtils.isEmpty(serviceOrder.getSowTitle()) ? ""
						: serviceOrder.getSowTitle());
		generalSection.setSoid(StringUtils.isEmpty(serviceOrder
				.getSoId()) ? "" : serviceOrder.getSoId());
		generalSection.setSoStatus(StringUtils.isEmpty(serviceOrder
				.getStatus()) ? "" : serviceOrder.getStatus());
		generalSection.setSubStatus(StringUtils.isEmpty(serviceOrder
				.getSubStatus()) ? "" : serviceOrder.getSubStatus());

	}

	private void mapAppintmentDetails(ServiceOrder serviceOrder,Appointment appointment){

		logger.info("Mapping: AppintmentDetails --->Starts");
		ServiceWindow serviceWindow=new ServiceWindow();		

		try{
		appointment.setScheduleStatus("");	
        appointment.setTimeZone(StringUtils.isEmpty(serviceOrder.getServiceLocationTimeZone()) ? ""	: serviceOrder.getServiceLocationTimeZone());
        //appointment.setServiceStartDate(StringUtils.isEmpty(serviceOrder.getServiceDate1().toString())?"":serviceOrder.getServiceDate1().toString())	;
        appointment.setServiceEndDate(StringUtils.isEmpty(serviceOrder.getServiceDate2().toString())?"":serviceOrder.getServiceDate2().toString());
		java.util.Date serviceDate1, serviceDate2;

		if (null != serviceOrder.getServiceDate1()) {
			serviceDate1 = (java.util.Date) TimeUtils.combineDateTime(
					serviceOrder.getServiceDate1(), serviceOrder
							.getServiceTimeStart());
			serviceWindow.setStartTime(CommonUtility.sdfToDate
					.format(new Timestamp(serviceDate1.getTime())));
		}
		if (null != serviceOrder.getServiceDate2()) {
			serviceDate2 = (java.util.Date) TimeUtils.combineDateTime(
					serviceOrder.getServiceDate2(), serviceOrder
							.getServiceTimeEnd());

			serviceWindow.setEndTime(CommonUtility.sdfToDate
					.format(new Timestamp(serviceDate2.getTime())));
		}
		//appointment.setServiceWindow(serviceWindow);
		appointment.setMaxTimeWindow(0);
		appointment.setMinTimeWindow(0);

		}			
	 catch (Exception e) {
		logger.error("Exception Occurred" + e.getMessage());
	}
	}

	 *//**
	 * This method is for mapping the Buyer Response
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param buyer
	 *            Buyer
	 * @return void
	 *//*
	private void mapBuyerDetails(ServiceOrder serviceOrder,Buyer buyer) {

		if ( null != serviceOrder.getBuyer() && null != serviceOrder.getBuyer().getBuyerId()) {
				buyer.setBuyerUserId( serviceOrder.getBuyerId());

			if(null != serviceOrder.getBuyer().getMaxTimeWindow()){
				//TODO: Map the buyer details once the query is finalized.Commenting the code for time being.

				buyer.setAlternatePhone("");
				//buyer.setBuyerCity("");
				buyer.setBuyerFirstName("");
				buyer.setBusinessName("");
				buyer.setBuyerLastName("");
				//buyer.setBuyerState("");
				//buyer.setBuyerZip("");
				buyer.setEmail("");
				buyer.setFax("");
				buyer.setStreetAddress1("");
				buyer.setPrimaryphone(""); 
				buyer.setStreetAddress2("");

			}
		}
	}	

	  *//**
	  * This method is for mapping the ServiceLocation Section of Response xml
	  * from Service Order.
	  *
	  * @param serviceOrder
	  *            ServiceOrder
	  * @param location
	  *            Location
	  * @return void
	  *//*

	private void mapPrimaryServiceLocation(ServiceOrder serviceOrder,
			ServiceLocation serviceLocation) {
		logger.info("Mapping: Service Location--->Starts");
		int locationTypeId = 0;
		SoLocation soLocation = serviceOrder.getServiceLocation();
		if (null != soLocation.getLocnClassId()) {
			locationTypeId = soLocation.getLocnClassId();
		}

		//TODO: Map the Primary Service Location details after the query is finalized.Commenting the code for time being.

		serviceLocation.setZip(StringUtils.isEmpty(soLocation.getZip()) ? ""
				: soLocation.getZip());

		serviceLocation.setCustAlternatePhone("");
		serviceLocation.setCity(StringUtils.isEmpty(soLocation.getCity()) ? ""
				: soLocation.getCity());
		serviceLocation.setCustEmail("");
		serviceLocation.setCustomerFirstName("");
		serviceLocation.setCustomerLastName("");
		serviceLocation.setCustPrimaryPhone("");
		serviceLocation.setState(StringUtils.isEmpty(soLocation.getState()) ? ""
				: soLocation.getState());
		serviceLocation.setStreetAddress1(StringUtils
				.isEmpty(soLocation.getStreet1()) ? "" : soLocation
				.getStreet1());
		serviceLocation.setStreetAddress2(StringUtils
				.isEmpty(soLocation.getStreet2()) ? "" : soLocation
				.getStreet2());
		serviceLocation.setZip(StringUtils.isEmpty(soLocation.getZip()) ? ""
				: soLocation.getZip());
		serviceLocation.setServiceLocationNotes(soLocation.getLocnNotes());

	}

	private void mapAlternateServiceLocation(ServiceOrder serviceOrder,
			AlternateServiceLocation altServiceLocation) {
		logger.info("Mapping: Alternate Service Location--->Starts");
		int locationTypeId = 0;
		SoLocation soLocation = serviceOrder.getServiceLocation();
		if (null != soLocation.getLocnClassId()) {
			locationTypeId = soLocation.getLocnClassId();
		}

		//TODO: Map the Alternate Service Location details after the query is finalized.Commenting the code for time being.


		altServiceLocation.setCustAlternatePhone("");
		altServiceLocation.setCity(StringUtils.isEmpty(soLocation.getCity()) ? ""
				: soLocation.getCity());
		altServiceLocation.setCustEmail("");
		altServiceLocation.setCustomerFirstName("");
		altServiceLocation.setCustomerLastName("");
		altServiceLocation.setCustPrimaryPhone("");
		altServiceLocation.setState(StringUtils.isEmpty(soLocation.getState()) ? ""
				: soLocation.getState());
		altServiceLocation.setStreetAddress1(StringUtils
				.isEmpty(soLocation.getStreet1()) ? "" : soLocation
				.getStreet1());
		altServiceLocation.setStreetAddress2(StringUtils
				.isEmpty(soLocation.getStreet2()) ? "" : soLocation
				.getStreet2());
		altServiceLocation.setZip(StringUtils.isEmpty(soLocation.getZip()) ? ""
				: soLocation.getZip());

	}
	private void mapScopeOfOrder(ServiceOrder serviceOrder,
			Scope scope) {

		logger.info("Mapping: Scope  --->Starts");

		scope.setMainServiceCategory("");
		List<Task> tasks = new ArrayList<Task>();
		Tasks allTasks = new Tasks();
		if (serviceOrder.getTasks() != null
				&& serviceOrder.getTasks().size() > 0) {
			Iterator<ServiceOrderTask> soTaskList = serviceOrder.getTasks()
					.iterator();
			while (soTaskList.hasNext()) {
				ServiceOrderTask soTask = (ServiceOrderTask) soTaskList.next();
				Task task = new Task();
				task.setTaskCategory(soTask.getCategoryName());
				task.setTaskComments(soTask.getTaskComments());
				task.setTaskName(StringUtils.isEmpty(soTask.getTaskName()) ? ""
						: soTask.getTaskName());
				task.setCustPrePaidAmount(0.0);	
				task.setTaskId(0)	;													

				task.setTaskSkill("");
				//clarification
				task.setTaskStatus("");
				task.setTasksubCategory(soTask.getSubCategoryName());

				// need to clarify whether it is integer or string
				task.setTaskType(StringUtils
						.isEmpty(soTask.getServiceType()) ? "" : soTask
								.getServiceType());			

				tasks.add(task);
			}
			allTasks.setTaskList(tasks);
			scope.setTasks(allTasks);
		}

	}

	   *//**
	   * This method is for mapping the Parts Section of Create Request for
	   * Service Order.
	   *
	   * @param appointment
	   *            ServiceOrder
	   * @param serviceLocation 
	   * @param Parts
	   *            parts
	   * @return void
	   * @throws ParseException 
	   *//*
	private void mapServiceOrderParts(ServiceOrder serviceOrder, Parts parts) {

		logger.info("Mapping: Merchandise/Parts --->Starts");
		List<com.newco.marketplace.api.mobile.beans.sodetails.Part> partListResponse = new ArrayList<com.newco.marketplace.api.mobile.beans.sodetails.Part>();
		if (null != serviceOrder.getParts()
				&& (!serviceOrder.getParts().isEmpty())) {
			Iterator<Part> soPartsList = serviceOrder.getParts().iterator();
			while (soPartsList.hasNext()) {
				com.newco.marketplace.api.mobile.beans.sodetails.Part partResponse = new com.newco.marketplace.api.mobile.beans.sodetails.Part();
				Part soPart = (Part) soPartsList.next();
				partResponse.setAdditionalPartInfo("");
				partResponse.setCoreReturnCarrier("");
				partResponse.setCoreReturnDate("");
				partResponse.setCoreReturnTrackingNumber(0);
				partResponse.setManufacturer(StringUtils.isEmpty(soPart
						.getManufacturer()) ? "" : soPart.getManufacturer());
			//	partResponse.setModelNumber((Integer) (StringUtils.isEmpty(soPart.getModelNumber()) ? ""
						//: Integer.parseInt(soPart.getModelNumber())));
				partResponse.setOemNumber(0);
				partResponse.setOrderNumber("");
				partResponse.setPartDescription("");
				partResponse.setPartName("");
				partResponse.setPartStatus("");
				partResponse.setPartType("");
				partResponse.setPurchaseOrderNumber("");
				partResponse.setQuantity("");
				partResponse.setSerialNumber(0);
				partResponse.setShipDate("");
				partResponse.setShippingCarrier("");
				partResponse.setShippingTrackingNumber(0);

				//partResponse.setVendorPartNumber(0);
				partResponse.setWeight(StringUtils.isEmpty(soPart.getWeight()) ? "0"
						: soPart.getWeight());

				partResponse
						.setQuantity(StringUtils.isEmpty(soPart.getQuantity()) ? "1"
								: soPart.getQuantity());

				// Dimension
				String size="";
				String length="";
				String width="";
				String height="";
				height=StringUtils.isEmpty(soPart.getHeight()) ? "0"
								: soPart.getHeight().toString();
				width=StringUtils.isEmpty(soPart.getLength()) ? "0"
								: soPart.getLength();
				length=StringUtils.isEmpty(soPart.getWidth()) ? "0"
								: soPart.getWidth();

				size=length+ "X" +width+ "X" +height;
				partResponse.setSize(size);

				if (null != soPart.getMeasurementStandard()
						&& OrderConstants.ENGLISH.equalsIgnoreCase((soPart
								.getMeasurementStandard().toString()))) {
					dimensions
							.setMeasurementType(PublicAPIConstant.STANDARD_ENGLISH);
				} else if (null != soPart.getMeasurementStandard()
						&& OrderConstants.METRIC.equalsIgnoreCase(soPart
								.getMeasurementStandard().toString())) {
					dimensions
							.setMeasurementType(PublicAPIConstant.STANDARD_METRIC);
				}


				Carrier shippingCarrier = soPart.getShippingCarrier();
				Carrier returnCarrier = soPart.getReturnCarrier();
				if (null != shippingCarrier) {
					partResponse.setShipCarrier(StringUtils.isEmpty(shippingCarrier
							.getCarrierName()) ? "" : shippingCarrier
							.getCarrierName());
					partResponse.setShipTracking(StringUtils.isEmpty(shippingCarrier
							.getTrackingNumber()) ? "" : shippingCarrier
							.getTrackingNumber());
				}
				if (null != returnCarrier) {
					partResponse.setReturnCarrier(StringUtils.isEmpty(returnCarrier
							.getCarrierName()) ? "" : returnCarrier
							.getCarrierName());
					partResponse.setReturnTracking(StringUtils.isEmpty(returnCarrier
							.getTrackingNumber()) ? "" : returnCarrier
							.getTrackingNumber());
				}
				if (null != soPart.isProviderBringPartInd()) {
					partResponse
							.setRequiresPickup(soPart.isProviderBringPartInd() ? "true"
									: "false");
				} else {
					partResponse.setRequiresPickup("false");
				}

				if (null != soPart.getPickupLocation()) {

					SoLocation soPickupLocation = soPart.getPickupLocation();
					PickUpLocation pickupLocation=new PickUpLocation(); 
					pickupLocation.setPickupLocationCity("");
					pickupLocation.setPickupLocationName(StringUtils
							.isEmpty(soPickupLocation.getLocnName()) ? ""
									: soPickupLocation.getLocnName());
					pickupLocation.setPickupLocationState(StringUtils
							.isEmpty(soPickupLocation.getState()) ? ""
									: soPickupLocation.getState());
					pickupLocation.setPickupLocationStreet1(StringUtils
							.isEmpty(soPickupLocation.getStreet1()) ? ""
									: soPickupLocation.getStreet1());
					pickupLocation.setPickupLocationStreet2(StringUtils
							.isEmpty(soPickupLocation.getStreet2()) ? ""
									: soPickupLocation.getStreet2());
					pickupLocation.setPickupLocationZip(StringUtils.isEmpty(soPickupLocation
							.getZip()) ? "" : soPickupLocation.getZip());
					int locationTypeId = 0;
					if (null != soPickupLocation.getLocnClassId()) {
						locationTypeId = soPickupLocation.getLocnClassId();
					}

					partResponse.setPickupLocation(pickupLocation);

				}
				partListResponse.add(partResponse);
			}
			parts.setPartList(partListResponse);

		}

	}
	    */

	public Notes GMTToGivenTimeZone(Appointment appointment, Notes notes, ServiceLocation serviceLocation) throws ParseException {
		LOGGER.info("Inside GMTToGivenTimeZone");
		if(appointment != null && appointment.getTimeZone() != null && notes!=null && notes.getNote() != null && notes.getNote().size() > 0){

			List<Note> noteslst =  notes.getNote();
			String startDate = null;
			String startDateMod = null;
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			Date dateObj = null;
			LOGGER.info("Inside GMTToGivenTimeZone - Appointment Timezone : " + appointment.getTimeZone());
			Timestamp createdDateTimeStamp = null;
			// convert DB date to GMT
			Timestamp gmtTimeStamp = null;
			for(Note noteObj : noteslst){
				if(noteObj.getCreatedDate() != null){
					startDate = noteObj.getCreatedDate();
					dateObj = formatter.parse(startDate);
					createdDateTimeStamp = new Timestamp(dateObj.getTime());
					gmtTimeStamp =	TimeUtils.convertToGMT(createdDateTimeStamp,"CDT"); // Server Time to GMT					 

					// GMT to service order local time zone
					startDateMod = TimeUtils.convertGMTtoTimezone(gmtTimeStamp, getStandardTimezone(appointment.getTimeZone()),sdf);
					noteObj.setCreatedDate(startDateMod);
				}
			}
			return notes;
		}
		return notes;
	}
	public Documents GMTToGivenTimeZone(Appointment appointment, Documents document) throws ParseException{

		if(null!=document.getDocument()){			
			List<Document> documentList=document.getDocument();
			String startDate = null;
			String startDateMod = null;
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			Date dateObj = null;
			LOGGER.info("Inside GMTToGivenTimeZone For Document Formatting- Appointment Timezone : " + appointment.getTimeZone());
			Timestamp createdDateTimeStamp = null;
			// convert DB date to GMT
			Timestamp gmtTimeStamp = null;
			for(Document docObj : documentList){
				if(docObj.getUploadDateTime()!= null){
					startDate = docObj.getUploadDateTime() ;
					dateObj = formatter.parse(startDate);
					createdDateTimeStamp = new Timestamp(dateObj.getTime());
					gmtTimeStamp =	TimeUtils.convertToGMT(createdDateTimeStamp,"CDT"); // Server Time to GMT

					// GMT to service order local time zone
					startDateMod = TimeUtils.convertGMTtoTimezone(gmtTimeStamp, getStandardTimezone(appointment.getTimeZone()),sdf);
					docObj.setUploadDateTime(startDateMod);
				}
			}
			return document;

		}
		return document;
	}
	public  Appointment GMTToGivenTimeZone(Appointment appointment, ServiceLocation serviceLocation) {
		if(appointment!=null){
			HashMap<String, Object> serviceDate1 = null;
			HashMap<String, Object> serviceDate2 = null;
			String startTime = null;
			String endTime = null;
			String widgetDateFormat = "yyyy-MM-dd"; // Needed for dojo widget.  Bug in dojo 0.9 allows only this format.

			String startDate= appointment.getServiceStartDate();

			startTime = appointment.getServiceWindowStartTime(); 
			endTime = appointment.getServiceWindowEndTime();
			if (appointment.getServiceStartDate() != null && startTime != null) {

				serviceDate1 = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(appointment.getServiceStartDate()), startTime, appointment.getTimeZone());
				if (serviceDate1 != null && !serviceDate1.isEmpty()) {
					appointment.setServiceStartDate(DateUtils.getFormatedDate((Timestamp)serviceDate1.get(OrderConstants.GMT_DATE),widgetDateFormat));
					appointment.setServiceWindowStartTime((String) serviceDate1.get(OrderConstants.GMT_TIME));
				}
			}
			if (appointment.getServiceEndDate() != null && endTime != null) {
				serviceDate2 = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(appointment.getServiceEndDate()), endTime, appointment.getTimeZone());
				if (serviceDate2 != null && !serviceDate2.isEmpty()) {
					appointment.setServiceEndDate(DateUtils.getFormatedDate((Timestamp)serviceDate2.get(OrderConstants.GMT_DATE),widgetDateFormat));
					appointment.setServiceWindowEndTime((String) serviceDate2.get(OrderConstants.GMT_TIME));
				}
			}
			String dlsFlag = "N";

			if (serviceLocation != null
					&& !StringUtils.isBlank(serviceLocation
							.getZip())) {
				String zip = serviceLocation
						.getZip();
				try {
					dlsFlag = lookupBO.getDaylightSavingsFlg(zip);
				} catch (DataServiceException e) {
					// TODO Auto-generated catch block
					LOGGER.error("Exception inside details mapper: "+e.getMessage());

				}
			}

			String newTimeZone;
			if ("Y".equals(dlsFlag)) {
				TimeZone tz = TimeZone.getTimeZone(appointment.getTimeZone());
				Timestamp timeStampDate = null;
				try {
					if (appointment.getServiceStartDate() != null 
							&& (StringUtils.isNotBlank(startTime))) {
						java.util.Date dt = (java.util.Date) TimeUtils
								.combineDateTime(
										Timestamp.valueOf(startDate),
										startTime);
						timeStampDate = new Timestamp(dt.getTime());
					}
				} catch (ParseException pe) {
					pe.printStackTrace();
				}
				
				if (null != timeStampDate) {
					boolean isDLSActive = tz.inDaylightTime(timeStampDate);

					if (isDLSActive) {
						newTimeZone = getDSTTimezone(appointment.getTimeZone());
						appointment.setTimeZone(newTimeZone);
					} else {
						newTimeZone = getNormalTimezone(appointment.getTimeZone());
						appointment.setTimeZone(newTimeZone);
					}
				}
			} 
			else {
				newTimeZone = getNormalTimezone(appointment.getTimeZone());
				appointment.setTimeZone(newTimeZone);
			}

		}
		return appointment;

	}
	/**
	 * 
	 * @param serviceLocation
	 * @param timeZone
	 * @param checkTime
	 * @return
	 */
	public String getTimeZone(ServiceLocation serviceLocation, String timeZone ,String checkTime){

		String dlsFlag = "N";
		String newTimezone = null;
		try {
			if (serviceLocation != null
					&& !StringUtils.isBlank(serviceLocation
							.getZip())) {
				String zip = serviceLocation
						.getZip();

				dlsFlag = lookupBO.getDaylightSavingsFlg(zip);		
			}

			if ("Y".equals(dlsFlag)) {
				TimeZone tz = TimeZone.getTimeZone(timeZone);
				Timestamp timeStampDate = null;
				try {
					if (StringUtils.isNotBlank(checkTime)) {

						DateFormat dfFull = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");							
						Date combinedDate = dfFull.parse(checkTime);															
						timeStampDate = new Timestamp(combinedDate.getTime());

					}
				} catch (ParseException pe) {
					pe.printStackTrace();
				}
				if (null != timeStampDate) {
					boolean isDLSActive = tz.inDaylightTime(timeStampDate);
					if (isDLSActive) {
						newTimezone = getDSTTimezone(timeZone);
					} else {
						newTimezone = getNormalTimezone(timeZone);
					}
				}
			} else {
				newTimezone = getNormalTimezone(timeZone);
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return newTimezone;

	}




	/**
	 * @param soTrip
	 * @param serviceLocation
	 * @param timeZone
	 * Time conversion based on so time zone for current appointment start/end date/time and 
	 * next appointment start/end date/time
	 * @return
	 */
	public  SOTrips GMTToGivenTimeZone(SOTrips soTrip, String timeZone,ServiceLocation serviceLocation) {

		if(null != soTrip){
			List<SOTrip> tripList = soTrip.getTrip();
			if(null != tripList){
				for(SOTrip trip: tripList){
					String widgetDateFormat = "yyyy-MM-dd";
					HashMap<String, Object> currentApptStartDateMap = null;
					HashMap<String, Object> currentApptEndDateMap = null;
					HashMap<String, Object> nextApptStartDateMap = null;
					HashMap<String, Object> nextApptEndDateMap = null;
					HashMap<String, Object> checkInTimeMap = null;
					HashMap<String, Object> checkOutTimeMap = null;

					//Current appointment start date/start time conversion to so time zone
					String currentApptStartDate = trip.getCurrentApptStartDate();
					String currentApptStartTime = trip.getCurrentApptStartTime();
					if(StringUtils.isNotEmpty(currentApptStartDate) && StringUtils.isNotEmpty(currentApptStartTime)){
						currentApptStartDateMap = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(currentApptStartDate), currentApptStartTime, timeZone);
						if (currentApptStartDateMap != null && !currentApptStartDateMap.isEmpty()) {
							trip.setCurrentApptStartDate((DateUtils.getFormatedDate((Timestamp)currentApptStartDateMap.get(OrderConstants.GMT_DATE),widgetDateFormat)));
							trip.setCurrentApptStartTime((String) currentApptStartDateMap.get(OrderConstants.GMT_TIME));
						}
					}
					//Current appointment end date/end time conversion to so time zone
					String currentApptEndDate = trip.getCurrentApptEndDate();
					String currentApptEndTime = trip.getCurrentApptEndTime();
					if(StringUtils.isNotEmpty(currentApptEndDate) && StringUtils.isNotEmpty(currentApptEndTime)){
						currentApptEndDateMap = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(currentApptEndDate), currentApptEndTime, timeZone);
						if (currentApptEndDateMap != null && !currentApptEndDateMap.isEmpty()) {
							trip.setCurrentApptEndDate((DateUtils.getFormatedDate((Timestamp)currentApptEndDateMap.get(OrderConstants.GMT_DATE),widgetDateFormat)));
							trip.setCurrentApptEndTime((String) currentApptEndDateMap.get(OrderConstants.GMT_TIME));
						}
					}
					//Next appointment end date/end time conversion to so time zone
					String nextApptStartDate = trip.getNextApptStartDate();
					String nextApptStartTime = trip.getNextApptStartTime();
					if(StringUtils.isNotEmpty(nextApptStartDate) && StringUtils.isNotEmpty(nextApptStartTime)){
						nextApptStartDateMap = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(nextApptStartDate), nextApptStartTime, timeZone);
						if (nextApptStartDateMap != null && !nextApptStartDateMap.isEmpty()) {
							trip.setNextApptStartDate((DateUtils.getFormatedDate((Timestamp)nextApptStartDateMap.get(OrderConstants.GMT_DATE),widgetDateFormat)));
							trip.setNextApptStartTime((String) nextApptStartDateMap.get(OrderConstants.GMT_TIME));
						}
					}
					//Next appointment end date/end time conversion to so time zone
					String nextApptEndDate = trip.getNextApptEndDate();
					String nextApptEndTime = trip.getNextApptEndTime();
					if(StringUtils.isNotEmpty(nextApptEndDate) && StringUtils.isNotEmpty(nextApptEndTime)){
						nextApptEndDateMap = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(nextApptEndDate), nextApptEndTime, timeZone);
						if (nextApptEndDateMap != null && !nextApptEndDateMap.isEmpty()) {
							trip.setNextApptEndDate((DateUtils.getFormatedDate((Timestamp)nextApptEndDateMap.get(OrderConstants.GMT_DATE),widgetDateFormat)));
							trip.setNextApptEndTime((String) nextApptEndDateMap.get(OrderConstants.GMT_TIME));
						}
					}
					//Check In time conversion to so time zone
					String checkInTime = trip.getCheckInTime(); //"10/11/2014 08:00:00 00";
					if(StringUtils.isNotEmpty(checkInTime)){
						checkInTimeMap = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(checkInTime),timeZone);
						if (checkInTimeMap != null && !checkInTimeMap.isEmpty()) {
							String newCheckInTimezone = getTimeZone(serviceLocation,timeZone,checkInTime);
							trip.setCheckInTime(checkInTimeMap.get(OrderConstants.GMT_TIME).toString().replaceAll("T", " ")+" "+newCheckInTimezone);
						}
					}
					//Check Out time conversion to so time zone
					String checkOutTime = trip.getCheckOutTime(); //"10/11/2014 08:00:00 00";
					if(StringUtils.isNotEmpty(checkOutTime)){
						checkOutTimeMap = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(checkOutTime),timeZone);
						if (checkOutTimeMap != null && !checkOutTimeMap.isEmpty()) {
							String newCheckOutTimezone = getTimeZone(serviceLocation,timeZone,checkOutTime);
							trip.setCheckOutTime(checkOutTimeMap.get(OrderConstants.GMT_TIME).toString().replaceAll("T", " ")+" "+newCheckOutTimezone);
						}
					}
				}
			}

		}
		return soTrip;
	}

	public List<AddOn> validateCoverage(List<AddOn> addonList) {
		if(addonList.size()!=0){
			for(AddOn addon:addonList){
				addon.setEditable(MPConstants.YES);
				if(StringUtils.equals(addon.getCoverageType(), MPConstants.COVERAGE_TYPE_CC)&&StringUtils.equals(addon.getMiscInd(),MPConstants.NO)){
					addon.setEditable(MPConstants.NO);	
				}
			}

		}
		return addonList;
	}

	private String getDSTTimezone(String timezone) {

		if ("EST5EDT".equals(timezone)) {
			return "EDT";				
		}
		if ("AST4ADT".equals(timezone)) {
			return "ADT";
		}
		if ("CST6CDT".equals(timezone)) {
			return "CDT";
		}
		if ("MST7MDT".equals(timezone)) {
			return "MDT";
		}
		if ("PST8PDT".equals(timezone)) {
			return "PDT";
		}
		if ("HST".equals(timezone)) {
			return "HADT";
		}
		if ("Etc/GMT+1".equals(timezone)) {
			return "CEDT";
		}
		if ("AST".equals(timezone)) {
			return "AKDT";
		}
		return timezone;			
	}

	private String getNormalTimezone(String timezone) {

		if ("EST5EDT".equals(timezone)) {
			return "EST";
		}
		if ("AST4ADT".equals(timezone)) {
			return "AST";
		}
		if ("CST6CDT".equals(timezone)) {
			return "CST";
		}
		if ("MST7MDT".equals(timezone)) {
			return "MST";
		}
		if ("PST8PDT".equals(timezone)) {
			return "PST";
		}
		if ("HST".equals(timezone)) {
			return "HAST";
		}
		if ("Etc/GMT+1".equals(timezone)) {
			return "CET";
		}
		if ("AST".equals(timezone)) {
			return "AKST";
		}
		if ("Etc/GMT-9".equals(timezone)) {
			return "PST-7";
		}
		if ("MIT".equals(timezone)) {
			return "PST-3";
		}
		if ("NST".equals(timezone)) {
			return "PST-4";
		}
		if ("Etc/GMT-10".equals(timezone)) {
			return "PST-6";
		}
		if ("Etc/GMT-11".equals(timezone)) {
			return "PST-5";
		}
		return timezone;

	}

	/**
	 * Convert to standard time zone
	 * @param incomingZone
	 * @return
	 */
	private String getStandardTimezone(String incomingZone) {
		String outgoingZone = "GMT"; //Default
		if(null!=incomingZone && !StringUtils.isEmpty(incomingZone)){		
			if ("EST".equals(incomingZone) || "EDT".equals(incomingZone)) {
				outgoingZone =  "EST5EDT";
			}
			if ("AST".equals(incomingZone) || "ADT".equals(incomingZone)) {
				outgoingZone =  "AST4ADT";
			}
			if ("CST".equals(incomingZone) || "CDT".equals(incomingZone)) {
				outgoingZone =  "CST6CDT";
			}
			if ("MST".equals(incomingZone) || "MDT".equals(incomingZone)) {
				outgoingZone =  "MST7MDT";
			}
			if ("PST".equals(incomingZone) || "PDT".equals(incomingZone)) {
				outgoingZone =  "PST8PDT";
			}
			if ("HADT".equals(incomingZone) || "HAST".equals(incomingZone)) {
				outgoingZone =  "HST";
			}
			if ("CET".equals(incomingZone) || "CEDT".equals(incomingZone)) {
				outgoingZone =  "Etc/GMT+1";
			}
			if ("AKST".equals(incomingZone) || "AKDT".equals(incomingZone)) {
				outgoingZone =  "AST";
			}		
			if ("PST-7".equals(incomingZone)) {
				outgoingZone =  "Etc/GMT-9";
			}
			if ("PST-3".equals(incomingZone)) {
				outgoingZone =  "MIT";
			}
			if ("PST-4".equals(incomingZone)) {
				outgoingZone =  "NST";
			}
			if ("PST-6".equals(incomingZone)) {
				outgoingZone =  "Etc/GMT-10";
			}
			if ("PST-5".equals(incomingZone)) {
				outgoingZone =  "Etc/GMT-11";
			}
		}
		return outgoingZone;
	}

	public CompletionDocuments GMTToGivenTimeZone(Appointment appointment, CompletionDocuments document) throws ParseException{

		if(null!=document.getDocument()){			
			List<Document> documentList=document.getDocument();
			String startDate = null;
			String startDateMod = null;
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			Date dateObj = null;
			LOGGER.info("Inside GMTToGivenTimeZone For Document Formatting- Appointment Timezone : " + appointment.getTimeZone());
			Timestamp createdDateTimeStamp = null;
			// convert DB date to GMT
			Timestamp gmtTimeStamp = null;
			for(Document docObj : documentList){
				if(docObj.getUploadDateTime()!= null){
					startDate = docObj.getUploadDateTime() ;
					dateObj = formatter.parse(startDate);
					createdDateTimeStamp = new Timestamp(dateObj.getTime());
					gmtTimeStamp =	TimeUtils.convertToGMT(createdDateTimeStamp,"CDT"); // Server Time to GMT

					// GMT to service order local time zone
					startDateMod = TimeUtils.convertGMTtoTimezone(gmtTimeStamp, getStandardTimezone(appointment.getTimeZone()),sdf);
					docObj.setUploadDateTime(startDateMod);
				}
			}
			return document;

		}
		return document;
	}


	public SignatureDocuments GMTToGivenTimeZone(Appointment appointment, SignatureDocuments document) throws ParseException{

		if(null!=document.getSignature()){			
			List<SignatureDocument> documentList=document.getSignature();
			String startDate = null;
			String startDateMod = null;
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			Date dateObj = null;
			LOGGER.info("Inside GMTToGivenTimeZone For Document Formatting- Appointment Timezone : " + appointment.getTimeZone());
			Timestamp createdDateTimeStamp = null;
			// convert DB date to GMT
			Timestamp gmtTimeStamp = null;
			for(SignatureDocument docObj : documentList){
				if(docObj.getUploadDateTime()!= null){
					startDate = docObj.getUploadDateTime() ;
					dateObj = formatter.parse(startDate);
					createdDateTimeStamp = new Timestamp(dateObj.getTime());
					gmtTimeStamp =	TimeUtils.convertToGMT(createdDateTimeStamp,"CDT"); // Server Time to GMT

					// GMT to service order local time zone
					startDateMod = TimeUtils.convertGMTtoTimezone(gmtTimeStamp, getStandardTimezone(appointment.getTimeZone()),sdf);
					docObj.setUploadDateTime(startDateMod);
				}
			}
			return document;

		}
		return document;
	}
	
	/**
	 * @param latestTrip
	 * @param serviceLocation
	 * @param timeZone
	 * Time conversion based on so time zone for current appointment start/end date/time and 
	 * next appointment start/end date/time
	 * @return
	 */
	public  LatestTripDetails GMTToGivenTimeZone(LatestTripDetails latestTrip, String timeZone,ServiceLocation serviceLocation) {
		
		if(null != latestTrip){
					HashMap<String, Object> checkInTimeMap = null;
					HashMap<String, Object> checkOutTimeMap = null;

					//Check In time conversion to so time zone
					String checkInTime = latestTrip.getCheckInTime(); //"10/11/2014 08:00:00 00";
					if(StringUtils.isNotEmpty(checkInTime)){
						checkInTimeMap = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(checkInTime),timeZone);
						if (checkInTimeMap != null && !checkInTimeMap.isEmpty()) {
							String newCheckInTimezone = getTimeZone(serviceLocation,timeZone,checkInTime);
							latestTrip.setCheckInTime(checkInTimeMap.get(OrderConstants.GMT_TIME).toString().replaceAll("T", " ")+" "+newCheckInTimezone);
						}
					}
					//Check Out time conversion to so time zone
					String checkOutTime = latestTrip.getCheckOutTime(); //"10/11/2014 08:00:00 00";
					if(StringUtils.isNotEmpty(checkOutTime)){
						checkOutTimeMap = TimeUtils.convertGMTToGivenTimeZone(Timestamp.valueOf(checkOutTime),timeZone);
						if (checkOutTimeMap != null && !checkOutTimeMap.isEmpty()) {
							String newCheckOutTimezone = getTimeZone(serviceLocation,timeZone,checkOutTime);
							latestTrip.setCheckOutTime(checkOutTimeMap.get(OrderConstants.GMT_TIME).toString().replaceAll("T", " ")+" "+newCheckOutTimezone);
						}
					}

		}
		return latestTrip;
	}
	/**
	 * Method to map the estimate details to the response
	 * @param estimateVO
	 * @return
	 */
	public GetEstimateDetailsResponse mapEstimateDetails(EstimateVO estimateVO) {

		GetEstimateDetailsResponse estimateDetailsResponse = new GetEstimateDetailsResponse();
		
		Double subTotal=0.0d;
		PricingDetails pricingDetails = new PricingDetails();
		//setting estimation response details
		estimateDetailsResponse.setEstimationId(estimateVO.getEstimationId());
		if(StringUtils.isNotBlank(estimateVO.getEstimationRefNo())){
		   estimateDetailsResponse.setEstimationRefNo(estimateVO.getEstimationRefNo());
		}
		if(null !=estimateVO.getEstimationDate()){
		   estimateDetailsResponse.setEstimateDate(DateUtils.formatDate(estimateVO.getEstimationDate()));
		}
		if(null !=estimateVO.getEstimationExpiryDate()){
			   estimateDetailsResponse.setEstimationExpiryDate(DateUtils.formatDate(estimateVO.getEstimationExpiryDate()));
		}
		estimateDetailsResponse.setResourceId(estimateVO.getResourceId());
		estimateDetailsResponse.setVendorId(estimateVO.getVendorId());
		/*estimateDetailsResponse.setDescription(estimateVO.getDescription());
		estimateDetailsResponse.setAcceptSource(estimateVO.getAcceptSource());
		estimateDetailsResponse.setTripCharge(estimateVO.getTripCharge());*/
		estimateDetailsResponse.setStatus(estimateVO.getStatus()); 
		/*estimateDetailsResponse.setRespondedCustomerName(estimateVO.getRespondedCustomerName());*/
		
		if(StringUtils.isNotBlank(estimateVO.getStatus()) &&
				(estimateVO.getStatus().equals(MPConstants.ACCEPTED_STATUS) || estimateVO.getStatus().equals(MPConstants.DECLINED_STATUS))){
			Date responseDate = estimateVO.getResponseDate();
			if(null!= responseDate && StringUtils.isNotBlank(estimateVO.getTimeZone())){
				//Convert response Date to service location timezone  and then to  yyyy-MM-dd'T'HH:mm:ss format
				Timestamp ts = new Timestamp(responseDate.getTime());
				responseDate =TimeUtils.convertTimeFromOneTimeZoneToOther(ts,MPConstants.SERVER_TIMEZONE,estimateVO.getTimeZone());
				estimateDetailsResponse.setResponseDate(DateUtils.formatDate(responseDate));
				LOGGER.info("Accepted/Declined Date"+ estimateDetailsResponse.getResponseDate());
			}
			
		}
		
		List<LaborTask> laborTaskList= new ArrayList<LaborTask>();
		List<com.newco.marketplace.api.mobile.beans.Part> partList = new ArrayList<com.newco.marketplace.api.mobile.beans.Part>();
		List<OtherService> otherServicelist=new ArrayList<OtherService>();
		//setting estimation tasks details
		if(null!=estimateVO.getEstimateTasks()){
			for(EstimateTaskVO estimateTask : estimateVO.getEstimateTasks()){
				LaborTask laborTask = new LaborTask();
				laborTask.setItemId(estimateTask.getItemId());
				laborTask.setAdditionalDetails(estimateTask.getAdditionalDetails());
				laborTask.setDescription(estimateTask.getDescription());
				laborTask.setQuantity(estimateTask.getQuantity());
				laborTask.setTaskName(estimateTask.getTaskName());
				laborTask.setTaskSeqNumber(estimateTask.getTaskSeqNumber());
				laborTask.setTotalPrice(estimateTask.getTotalPrice());
				laborTask.setUnitPrice(estimateTask.getUnitPrice());
				laborTaskList.add(laborTask);
			}			
		}
		//setting estimation parts details
		if(null!=estimateVO.getEstimateParts()){
			for(EstimateTaskVO estimatePart : estimateVO.getEstimateParts()){
				com.newco.marketplace.api.mobile.beans.Part part = new com.newco.marketplace.api.mobile.beans.Part();
				part.setItemId(estimatePart.getItemId());
				part.setAdditionalDetails(estimatePart.getAdditionalDetails());
				part.setDescription(estimatePart.getDescription());
				part.setQuantity(estimatePart.getQuantity());
				part.setPartName(estimatePart.getPartName());
				part.setPartSeqNumber(estimatePart.getPartSeqNumber());
				part.setPartNo(estimatePart.getPartNumber());
				part.setTotalPrice(estimatePart.getTotalPrice());
				part.setUnitPrice(estimatePart.getUnitPrice());
				partList.add(part);
			}		
			
		}
		
		//setting other estimate service details
		if(null!=estimateVO.getEstimateOtherEstimateServices()){
			for(EstimateTaskVO estimateOtherEstimateService : estimateVO.getEstimateOtherEstimateServices()){
				OtherService otherService = new OtherService();
				otherService.setItemId(estimateOtherEstimateService.getItemId());
				otherService.setAdditionalDetails(estimateOtherEstimateService.getAdditionalDetails());
				otherService.setDescription(estimateOtherEstimateService.getDescription());
				otherService.setQuantity(estimateOtherEstimateService.getQuantity());
				otherService.setOtherServiceName(estimateOtherEstimateService.getOtherServiceName());
				otherService.setOtherServiceSeqNumber(estimateOtherEstimateService.getOtherServiceSeqNumber());
				otherService.setOtherServiceType(estimateOtherEstimateService.getOtherServiceType());
				otherService.setTotalPrice(estimateOtherEstimateService.getTotalPrice());
				otherService.setUnitPrice(estimateOtherEstimateService.getUnitPrice());
				otherServicelist.add(otherService);
			}		
			
		}
		EstimateDetails estimateDetails = null;
		LaborTasks laborTasks =null;
		com.newco.marketplace.api.mobile.beans.Parts parts= null;
		OtherServices otherServices =null;
		
		if(!laborTaskList.isEmpty()){
			laborTasks = new LaborTasks();
			laborTasks.setLaborTask(laborTaskList);	
		}
		if(!partList.isEmpty()){
			parts = new com.newco.marketplace.api.mobile.beans.Parts();
			parts.setPart(partList);		
		}
		if(!otherServicelist.isEmpty()){
			otherServices = new OtherServices();
			otherServices.setOtherService(otherServicelist);		
		}
		if(!partList.isEmpty()||!laborTaskList.isEmpty()||!otherServicelist.isEmpty()){
			 estimateDetails = new EstimateDetails();
			 estimateDetails.setLaborTasks(laborTasks);
			 estimateDetails.setParts(parts);
			 estimateDetails.setOtherServices(otherServices);
		}
	
		estimateDetailsResponse.setEstimateDetails(estimateDetails);
		
		if(StringUtils.isNotBlank(estimateVO.getComments())){
		estimateDetailsResponse.setComments(estimateVO.getComments());
		}
		estimateDetailsResponse.setLogoDocumentId(estimateVO.getLogoDocumentId());
		//setting estimation pricing details
		pricingDetails.setDiscountedAmount(estimateVO.getDiscountedAmount());
		pricingDetails.setDiscountedPercentage(estimateVO.getDiscountedPercentage());
		pricingDetails.setDiscountType(estimateVO.getDiscountType());
		pricingDetails.setTotalLaborPrice(estimateVO.getTotalLaborPrice());
		pricingDetails.setTotalPartsPrice(estimateVO.getTotalPartsPrice());
		pricingDetails.setTotalOtherServicePrice(estimateVO.getTotalOtherServicePrice());
		
		DecimalFormat df = new DecimalFormat(MPConstants.ROUND_OFF_TWO_DECIMAL);
        if(null!=estimateVO.getTotalLaborPrice()){
        	subTotal=subTotal+estimateVO.getTotalLaborPrice().doubleValue();	
        }
        if(null!=estimateVO.getTotalPartsPrice()){
        	subTotal=subTotal+estimateVO.getTotalPartsPrice().doubleValue();	
        }
        if(null!=estimateVO.getTotalOtherServicePrice()){
        	subTotal=subTotal+estimateVO.getTotalOtherServicePrice().doubleValue();	
        }
        LOGGER.info("subtotal before formatting:"+subTotal);
        if(null !=subTotal){
		pricingDetails.setSubTotal(Double.valueOf(df.format(subTotal)));
        }
        LOGGER.info("subtotal after formatting:"+subTotal);
		 
		pricingDetails.setTaxPrice(estimateVO.getTaxPrice());
		pricingDetails.setTaxRate(estimateVO.getTaxRate());
		pricingDetails.setTaxType(estimateVO.getTaxType());
		pricingDetails.setTotalPrice(estimateVO.getTotalPrice());
		
		//SL-21934
		pricingDetails.setLaborDiscountType(estimateVO.getLaborDiscountType());
		pricingDetails.setLaborDiscountedPercentage(estimateVO.getLaborDiscountedPercentage());
		pricingDetails.setLaborDiscountedAmount(estimateVO.getLaborDiscountedAmount());
		pricingDetails.setLaborTaxRate(estimateVO.getLaborTaxRate());
		pricingDetails.setLaborTaxPrice(estimateVO.getLaborTaxPrice());
		pricingDetails.setPartsDiscountType(estimateVO.getPartsDiscountType());
		pricingDetails.setPartsDiscountedPercentage(estimateVO.getPartsDiscountedPercentage());
		pricingDetails.setPartsDiscountedAmount(estimateVO.getPartsDiscountedAmount());
		pricingDetails.setPartsTaxRate(estimateVO.getPartsTaxRate());
		pricingDetails.setPartsTaxPrice(estimateVO.getPartsTaxPrice());
		
		
		estimateDetailsResponse.setPricing(pricingDetails);	
		//mapping history details for the estimate
		estimateDetailsResponse.setEstimateHistoryDetails(mapHistoryDetails(estimateVO.getEstimateHistoryList(),estimateVO.getEstimateTasksHistory(),estimateVO.getEstimatePartsHistory(),estimateVO.getEstimateOtherServicesHistory(),estimateVO.getTimeZone()));
		return estimateDetailsResponse;
	}
	/**@Description : Mapping request details to VO for persistence
	 * @param addEstimateRequest
	 * @param estimationId
	 * @param apiVO
	 * @param userName 
	 * @return
	 */
	public EstimateVO mapAddEditEstimate(AddEstimateRequest addEstimateRequest,Integer estimationId, APIRequestVO apiVO, String userName) {
		EstimateVO estimateVO = new EstimateVO();
		mapEstimationDetails(estimateVO,addEstimateRequest,apiVO,estimationId,userName);
		mapTasksAndParts(estimateVO,addEstimateRequest,estimationId);
		//SL-21385-Adding Other Services as part of R16_2_1
		mapOtherEstimateSevices(estimateVO,addEstimateRequest,estimationId);
		mapEstimatePrice(estimateVO,addEstimateRequest);
		return estimateVO;
	}


	

	/**@Description : Mapping request details to Vo for persistence in so_estimation table
	 * @param estimateVO
	 * @param addEstimateRequest
	 * @param apiVO
	 * @param userName 
	 */
	private void mapEstimationDetails(EstimateVO estimateVO,AddEstimateRequest addEstimateRequest, APIRequestVO apiVO,Integer estimationId, String userName) {
		estimateVO.setSoId(apiVO.getSOId());
		estimateVO.setEstimationId(estimationId);
		estimateVO.setEstimationRefNo(addEstimateRequest.getEstimationRefNo());
		estimateVO.setLogoDocumentId(Integer.valueOf(addEstimateRequest.getLogoDocumentId()));
		estimateVO.setComments(addEstimateRequest.getComments());
		if(StringUtils.isNotBlank(apiVO.getProviderId())&& StringUtils.isNumeric(apiVO.getProviderId())){
		  estimateVO.setVendorId(Integer.parseInt(apiVO.getProviderId()));
		}
		estimateVO.setResourceId(apiVO.getProviderResourceId());
		if(null==estimationId){
			estimateVO.setCreatedBy(userName);
			//Setting status as NEW/DRAFT based on isDraftEstimateFlag
			if((StringUtils.isNotBlank(addEstimateRequest.getIsDraftEstimate()) 
					&& StringUtils.equalsIgnoreCase(PublicMobileAPIConstant.DRAFT_STATUS_YES, addEstimateRequest.getIsDraftEstimate()))){
				  estimateVO.setStatus(PublicMobileAPIConstant.DRAFT);
			  }else{
				  estimateVO.setStatus(PublicMobileAPIConstant.NEW);
			  }
		}
		estimateVO.setModifiedBy(userName);
		estimateVO.setIsDraftEstimate(addEstimateRequest.getIsDraftEstimate());
		Date estimateDate = DateUtils.parseDate(addEstimateRequest.getEstimationDate());
		LOGGER.info("Estimation Date after Formatting:"+ estimateDate);
		estimateVO.setEstimationDate(estimateDate);
		if(null!=addEstimateRequest.getEstimationExpiryDate()){
		Date estimateExpiryDate = DateUtils.parseDate(addEstimateRequest.getEstimationExpiryDate());
		estimateVO.setEstimationExpiryDate(estimateExpiryDate);
		}
		
	}


	/**@Description : Mapping tasks/parts details in estimation request to vo  for persistence in so_estimation_items table
	 * @param addEstimateRequest
	 * @param estimateVO
	 * @param estimationId
	 */
	private void mapTasksAndParts( EstimateVO estimateVO,AddEstimateRequest addEstimateRequest,Integer estimationId) {
		List<EstimateTaskVO> estimateTasks = null;
		List<EstimateTaskVO> estimateParts = null;
		//Mapping Task Details 
		if(null!= addEstimateRequest && null!= addEstimateRequest.getEstimateTasks() &&
				null!= addEstimateRequest.getEstimateTasks().getEstimateTask() 
				    && !addEstimateRequest.getEstimateTasks().getEstimateTask().isEmpty()){
			estimateTasks = new ArrayList<EstimateTaskVO>();
			for(EstimateTask requestTask : addEstimateRequest.getEstimateTasks().getEstimateTask()){
				if(null!= requestTask){
					EstimateTaskVO taskVO = new EstimateTaskVO();
					taskVO.setEstimationId(estimationId);
					if(StringUtils.isNotBlank(requestTask.getTaskSeqNumber())){
					  taskVO.setTaskSeqNumber(Integer.parseInt(requestTask.getTaskSeqNumber()));
					}
					taskVO.setTaskName(requestTask.getTaskName());
					taskVO.setTaskType(PublicMobileAPIConstant.LABOR_TASK);
					taskVO.setDescription(requestTask.getDescription());
					if(StringUtils.isNotBlank(requestTask.getUnitPrice())){
					   taskVO.setUnitPrice(Double.parseDouble(requestTask.getUnitPrice()));
					}
					if(StringUtils.isNotBlank(requestTask.getQuantity())){
					   taskVO.setQuantity(Integer.parseInt(requestTask.getQuantity()));
					}
					if(StringUtils.isNotBlank(requestTask.getTotalPrice())){
					   taskVO.setTotalPrice(Double.parseDouble(requestTask.getTotalPrice()));
					}
					taskVO.setAdditionalDetails(requestTask.getAdditionalDetails());
					estimateTasks.add(taskVO);
				}
			}
			
		}
		//Mapping Parts Details
		if(null!= addEstimateRequest && null!= addEstimateRequest.getEstimateParts() &&
				null!= addEstimateRequest.getEstimateParts().getEstimatePart()
				    && !addEstimateRequest.getEstimateParts().getEstimatePart().isEmpty()){
			estimateParts = new ArrayList<EstimateTaskVO>();
			for(EstimatePart requestPart : addEstimateRequest.getEstimateParts().getEstimatePart()){
				if(null!= requestPart){
					EstimateTaskVO taskVO = new EstimateTaskVO();
					taskVO.setEstimationId(estimationId);
					if(StringUtils.isNotBlank(requestPart.getPartSeqNumber())){
					  taskVO.setPartSeqNumber(Integer.parseInt(requestPart.getPartSeqNumber()));
					}
					taskVO.setPartNumber(requestPart.getPartNumber());
					taskVO.setPartName(requestPart.getPartName());
					taskVO.setTaskType(PublicMobileAPIConstant.PARTS_TASK);
					taskVO.setDescription(requestPart.getDescription());
					if(StringUtils.isNotBlank(requestPart.getUnitPrice())){
					  taskVO.setUnitPrice(Double.parseDouble(requestPart.getUnitPrice()));
					}
					if(StringUtils.isNotBlank(requestPart.getQuantity())){
					taskVO.setQuantity(Integer.parseInt(requestPart.getQuantity()));
					}
					if(StringUtils.isNotBlank(requestPart.getTotalPrice())){
					  taskVO.setTotalPrice(Double.parseDouble(requestPart.getTotalPrice()));
					}
					taskVO.setAdditionalDetails(requestPart.getAdditionalDetails());
					estimateParts.add(taskVO);
				}
			}
		 }
			estimateVO.setEstimateTasks(estimateTasks);
			estimateVO.setEstimateParts(estimateParts);
			
		}
	/**@Description: Mapping pricing details from request to VO
	 * @param estimateVO
	 * @param addEstimateRequest
	 */
	private void mapEstimatePrice(EstimateVO estimateVO,AddEstimateRequest addEstimateRequest) {
		Pricing requestPrice = addEstimateRequest.getPricing();
		if(null!=requestPrice){
			estimateVO.setTotalLaborPrice(Double.parseDouble(requestPrice.getTotalLaborPrice()));
			if(StringUtils.isNotBlank(requestPrice.getTotalPartsPrice())){
			   estimateVO.setTotalPartsPrice(Double.parseDouble(requestPrice.getTotalPartsPrice()));
			}else{
			   estimateVO.setTotalPartsPrice(Double.parseDouble(PublicMobileAPIConstant.ZERO_PRICE));
			}
			//SL-21385 :Adding Other Services as part of R16_2_1 -- start
			if(StringUtils.isNotBlank(requestPrice.getTotalOtherServicePrice())){
				estimateVO.setTotalOtherServicePrice(Double.parseDouble(requestPrice.getTotalOtherServicePrice()));	   
			}else{
				estimateVO.setTotalOtherServicePrice(Double.parseDouble(PublicMobileAPIConstant.ZERO_PRICE));
			}
			//SL-21385 -- end
			estimateVO.setDiscountType(requestPrice.getDiscountType());
			if(StringUtils.isNotBlank(requestPrice.getDiscountedPercentage())){
				estimateVO.setDiscountedPercentage(Double.parseDouble(requestPrice.getDiscountedPercentage()));
			}
			if(StringUtils.isNotBlank(requestPrice.getDiscountedAmount())){
			    estimateVO.setDiscountedAmount(Double.parseDouble(requestPrice.getDiscountedAmount()));
			}
			if(StringUtils.isNotBlank(requestPrice.getTaxRate())){
			    estimateVO.setTaxRate(Double.parseDouble(requestPrice.getTaxRate()));
			}
			estimateVO.setTaxType(requestPrice.getTaxType());
			if(StringUtils.isNotBlank(requestPrice.getTaxPrice())){
			    estimateVO.setTaxPrice(Double.parseDouble(requestPrice.getTaxPrice()));
			}
			if(StringUtils.isNotBlank(requestPrice.getTotalPrice())){
			    estimateVO.setTotalPrice(Double.parseDouble(requestPrice.getTotalPrice()));
			}
			//SL-21934
			if(StringUtils.isNotBlank(requestPrice.getLaborDiscountType())){
			    estimateVO.setLaborDiscountType(requestPrice.getLaborDiscountType());
			}
			if(StringUtils.isNotBlank(requestPrice.getLaborDiscountedPercentage())){
			    estimateVO.setLaborDiscountedPercentage(Double.parseDouble(requestPrice.getLaborDiscountedPercentage()));
			}else{
				 estimateVO.setLaborDiscountedPercentage(Double.parseDouble(PublicMobileAPIConstant.ZERO_PRICE));
			}
			if(StringUtils.isNotBlank(requestPrice.getLaborDiscountedAmount())){
			    estimateVO.setLaborDiscountedAmount(Double.parseDouble(requestPrice.getLaborDiscountedAmount()));
			}else{
				 estimateVO.setLaborDiscountedAmount(Double.parseDouble(PublicMobileAPIConstant.ZERO_PRICE));
			}
			if(StringUtils.isNotBlank(requestPrice.getLaborTaxRate())){
			    estimateVO.setLaborTaxRate(Double.parseDouble(requestPrice.getLaborTaxRate()));
			}else{
				 estimateVO.setLaborTaxRate(Double.parseDouble(PublicMobileAPIConstant.ZERO_PRICE));
			}
			if(StringUtils.isNotBlank(requestPrice.getLaborTaxPrice())){
			    estimateVO.setLaborTaxPrice(Double.parseDouble(requestPrice.getLaborTaxPrice()));
			}else{
				 estimateVO.setLaborTaxPrice(Double.parseDouble(PublicMobileAPIConstant.ZERO_PRICE));
			}
			if(StringUtils.isNotBlank(requestPrice.getPartsDiscountType())){
			    estimateVO.setPartsDiscountType(requestPrice.getPartsDiscountType());
			}
			if(StringUtils.isNotBlank(requestPrice.getPartsDiscountedPercentage())){
			    estimateVO.setPartsDiscountedPercentage(Double.parseDouble(requestPrice.getPartsDiscountedPercentage()));
			}else{
				 estimateVO.setPartsDiscountedPercentage(Double.parseDouble(PublicMobileAPIConstant.ZERO_PRICE));
			}
			if(StringUtils.isNotBlank(requestPrice.getPartsDiscountedAmount())){
			    estimateVO.setPartsDiscountedAmount(Double.parseDouble(requestPrice.getPartsDiscountedAmount()));
			}else{
				 estimateVO.setPartsDiscountedAmount(Double.parseDouble(PublicMobileAPIConstant.ZERO_PRICE));
			}
			if(StringUtils.isNotBlank(requestPrice.getPartsTaxRate())){
			    estimateVO.setPartsTaxRate(Double.parseDouble(requestPrice.getPartsTaxRate()));
			}else{
				 estimateVO.setPartsTaxRate(Double.parseDouble(PublicMobileAPIConstant.ZERO_PRICE));
			}
			if(StringUtils.isNotBlank(requestPrice.getPartsTaxPrice())){
			    estimateVO.setPartsTaxPrice(Double.parseDouble(requestPrice.getPartsTaxPrice()));
			}else{
				 estimateVO.setPartsTaxPrice(Double.parseDouble(PublicMobileAPIConstant.ZERO_PRICE));
			}
			
		}
		
	}


	/**@Description: Setting the Success Response for Add/Edit Estimate 
	 * @param addEditestimationId
	 * @return
	 */
	public AddEstimateResponse createSuccessResponse(Integer addEditestimationId) {
		AddEstimateResponse response = new AddEstimateResponse();
		response.setEstimationId(addEditestimationId);
		Results results = Results.getSuccess(ResultsCode.ADD_EDIT_ESTIMATE.getMessage());
		response.setResults(results);
		return response;
	}

	/**@Description: Setting the Error Response for Add/Edit Estimate 
	 * @param addEditestimationId
	 * @return
	 */
	public AddEstimateResponse createErrorResponse(String message,String code) {
		AddEstimateResponse response = new AddEstimateResponse();
		Results results = Results.getError(message, code);
		response.setResults(results);		
		return response;
	}
	public GetEstimateDetailsResponse setErrorResponse(String message,String code) {
		GetEstimateDetailsResponse response = new GetEstimateDetailsResponse();
		Results results = Results.getError(message, code);
		response.setResults(results);		
		return response;
	}

	/** mapping estimate history details
	 * @param estimateHistoryList
	 * @param estimatePartList 
	 * @param estimateTaskList 
	 * @param list 
	 * @param timeZone 
	 * @return
	 */
	private EstimateHistoryDetails mapHistoryDetails(
			List<EstimateHistoryVO> estimateHistoryList, List<EstimateHistoryTaskVO> estimateTaskList, List<EstimateHistoryTaskVO> estimatePartList, List<EstimateHistoryTaskVO> estimateOtherServiceHistoryList, String timeZone) {
		EstimateHistoryDetails estimateHistoryDetails = null;
		
		Map<Integer,List<EstimateHistoryTaskVO>> laborTaskMap=new HashMap<Integer,List<EstimateHistoryTaskVO>>();
		Map<Integer,List<EstimateHistoryTaskVO>> partTaskMap=new HashMap<Integer,List<EstimateHistoryTaskVO>>();
		Map<Integer,List<EstimateHistoryTaskVO>> otherTaskMap=new HashMap<Integer,List<EstimateHistoryTaskVO>>();

		if(null!=estimateTaskList && estimateTaskList.size()>0){
		for(EstimateHistoryTaskVO estimateHistoryTaskVO: estimateTaskList){		
			if(laborTaskMap.containsKey(estimateHistoryTaskVO.getEstimationHistoryId())){
				List<EstimateHistoryTaskVO> history=laborTaskMap.get(estimateHistoryTaskVO.getEstimationHistoryId());
				history.add(estimateHistoryTaskVO);
				laborTaskMap.put(estimateHistoryTaskVO.getEstimationHistoryId(), history);
			}else{
				List<EstimateHistoryTaskVO> history=new ArrayList<EstimateHistoryTaskVO>();
				history.add(estimateHistoryTaskVO);
				laborTaskMap.put(estimateHistoryTaskVO.getEstimationHistoryId(), history);

			}			
		}
		}
		
		if(null!=estimatePartList && estimatePartList.size()>0){
		for(EstimateHistoryTaskVO estimateHistoryTaskVO: estimatePartList){		
			if(partTaskMap.containsKey(estimateHistoryTaskVO.getEstimationHistoryId())){
				List<EstimateHistoryTaskVO> history=partTaskMap.get(estimateHistoryTaskVO.getEstimationHistoryId());
				history.add(estimateHistoryTaskVO);
				partTaskMap.put(estimateHistoryTaskVO.getEstimationHistoryId(), history);

			}else{
				List<EstimateHistoryTaskVO> history=new ArrayList<EstimateHistoryTaskVO>();
				history.add(estimateHistoryTaskVO);
				partTaskMap.put(estimateHistoryTaskVO.getEstimationHistoryId(), history);

			}			
		}
		}
		
		if(null!=estimateOtherServiceHistoryList && estimateOtherServiceHistoryList.size()>0){
		for(EstimateHistoryTaskVO estimateHistoryTaskVO: estimateOtherServiceHistoryList){		
			if(otherTaskMap.containsKey(estimateHistoryTaskVO.getEstimationHistoryId())){
				List<EstimateHistoryTaskVO> history=otherTaskMap.get(estimateHistoryTaskVO.getEstimationHistoryId());
				history.add(estimateHistoryTaskVO);
				otherTaskMap.put(estimateHistoryTaskVO.getEstimationHistoryId(), history);

			}else{
				List<EstimateHistoryTaskVO> history=new ArrayList<EstimateHistoryTaskVO>();
				history.add(estimateHistoryTaskVO);
				otherTaskMap.put(estimateHistoryTaskVO.getEstimationHistoryId(), history);

			}			
		}
		}
		
		
		
		if (null!=estimateHistoryList && !estimateHistoryList.isEmpty()){
			estimateHistoryDetails = new EstimateHistoryDetails();
			List<EstimateHistory> historyList = new ArrayList<EstimateHistory>();
			for (EstimateHistoryVO estimateHistoryVO:estimateHistoryList){
				if(null!=estimateHistoryVO){
					EstimateHistory estimateHistory = new EstimateHistory();
					estimateHistory.setEstimationHistoryId(estimateHistoryVO.getEstimationHistoryId());
					estimateHistory.setEstimationId(estimateHistoryVO.getEstimationId());
					estimateHistory.setEstimationRefNo(estimateHistoryVO.getEstimationRefNo());
					if(null!=estimateHistoryVO.getEstimationDate()){
						estimateHistory.setEstimateDate(DateUtils.formatDate(estimateHistoryVO.getEstimationDate()));
					}
					if(null !=estimateHistoryVO.getEstimationExpiryDate()){
						estimateHistory.setEstimationExpiryDate(DateUtils.formatDate(estimateHistoryVO.getEstimationExpiryDate()));
					} 
					                     
					//mapping vendor and provider details
					estimateHistory.setVendorId(estimateHistoryVO.getVendorId());
					estimateHistory.setResourceId(estimateHistoryVO.getResourceId());					
					estimateHistory.setStatus(estimateHistoryVO.getStatus());
					
					if(StringUtils.isNotBlank(estimateHistoryVO.getStatus()) &&
							(estimateHistoryVO.getStatus().equals(MPConstants.ACCEPTED_STATUS) || estimateHistoryVO.getStatus().equals(MPConstants.DECLINED_STATUS))){
						Date responseDate = estimateHistoryVO.getResponseDate();
						if(null!= responseDate && StringUtils.isNotBlank(timeZone)){
							//Convert response Date to service location timezone  and then to  yyyy-MM-dd'T'HH:mm:ss format
							Timestamp ts = new Timestamp(responseDate.getTime());
							responseDate =TimeUtils.convertTimeFromOneTimeZoneToOther(ts,MPConstants.SERVER_TIMEZONE,timeZone);
							estimateHistory.setResponseDate(DateUtils.formatDate(responseDate));
							LOGGER.info("Accepted/Declined Date in history"+ estimateHistory.getResponseDate());
						}
						
					}															
					//mapping pricing details
					estimateHistory.setPricing(mapPricingDetails(estimateHistoryVO));					
					estimateHistory.setComments(estimateHistoryVO.getComments());
					estimateHistory.setLogoDocumentId(estimateHistoryVO.getLogoDocumentId());
					estimateHistory.setAction(estimateHistoryVO.getAction());
                    // map history			
					mapEstimateItemsHistory(laborTaskMap.get(estimateHistoryVO.getEstimationHistoryId()),partTaskMap.get(estimateHistoryVO.getEstimationHistoryId()),
							otherTaskMap.get(estimateHistoryVO.getEstimationHistoryId()),estimateHistory);
					historyList.add(estimateHistory);																	
				}
			}
			estimateHistoryDetails.setEstimateHistory(historyList);
			//mapping Item details
			//estimateHistoryDetails.setEstimateItemsHistory(mapItemsHistory(estimateTaskList,estimatePartList,estimateOtherServiceHistoryList));
		}
		
		return estimateHistoryDetails;
	}
	
	/** mapping pricing details
	 * @param estimateHistoryVO
	 * @return
	 */
	private PricingDetails mapPricingDetails(
			EstimateHistoryVO estimateHistoryVO) {
		PricingDetails estimatePricingDetails = new PricingDetails();
		Double subTotal=0.0d;
		estimatePricingDetails.setTotalLaborPrice(estimateHistoryVO.getTotalLaborPrice());
		estimatePricingDetails.setTotalPartsPrice(estimateHistoryVO.getTotalPartsPrice());
		estimatePricingDetails.setTotalOtherServicePrice(estimateHistoryVO.getTotalOtherServicePrice());
		DecimalFormat df = new DecimalFormat(MPConstants.ROUND_OFF_TWO_DECIMAL);
        if(null!=estimateHistoryVO.getTotalLaborPrice()){
            subTotal=subTotal+estimateHistoryVO.getTotalLaborPrice().doubleValue();   
        }
        if(null!=estimateHistoryVO.getTotalPartsPrice()){
            subTotal=subTotal+estimateHistoryVO.getTotalPartsPrice().doubleValue();   
        }
        if(null!=estimateHistoryVO.getTotalOtherServicePrice()){
            subTotal=subTotal+estimateHistoryVO.getTotalOtherServicePrice().doubleValue();   
        }
        if(null !=subTotal){
        	estimatePricingDetails.setSubTotal(Double.valueOf(df.format(subTotal)));
        }
		estimatePricingDetails.setDiscountType(estimateHistoryVO.getDiscountType());
		estimatePricingDetails.setDiscountedPercentage(estimateHistoryVO.getDiscountedPercentage());
		estimatePricingDetails.setDiscountedAmount(estimateHistoryVO.getDiscountedAmount());
		estimatePricingDetails.setTaxRate(estimateHistoryVO.getTaxRate());
		estimatePricingDetails.setTaxType(estimateHistoryVO.getTaxType());
		estimatePricingDetails.setTaxPrice(estimateHistoryVO.getTaxPrice());
		estimatePricingDetails.setTotalPrice(estimateHistoryVO.getTotalPrice());
		
		//SL-21934
		estimatePricingDetails.setLaborDiscountType(estimateHistoryVO.getLaborDiscountType());
		estimatePricingDetails.setLaborDiscountedPercentage(estimateHistoryVO.getLaborDiscountedPercentage());
		estimatePricingDetails.setLaborDiscountedAmount(estimateHistoryVO.getLaborDiscountedAmount());
		estimatePricingDetails.setLaborTaxRate(estimateHistoryVO.getLaborTaxRate());
		estimatePricingDetails.setLaborTaxPrice(estimateHistoryVO.getLaborTaxPrice());
		estimatePricingDetails.setPartsDiscountType(estimateHistoryVO.getPartsDiscountType());
		estimatePricingDetails.setPartsDiscountedPercentage(estimateHistoryVO.getPartsDiscountedPercentage());
		estimatePricingDetails.setPartsDiscountedAmount(estimateHistoryVO.getPartsDiscountedAmount());
		estimatePricingDetails.setPartsTaxRate(estimateHistoryVO.getPartsTaxRate());
		estimatePricingDetails.setPartsTaxPrice(estimateHistoryVO.getPartsTaxPrice());
		return estimatePricingDetails;
	}
	
	/** mapping labor and part items
	 * @param estimateTaskList
	 * @param estimatePartList
	 * @param estimateOtherServiceHistoryList 
	 * @return
	 */
	private EstimateItemsHistory mapItemsHistory(
			List<EstimateHistoryTaskVO> estimateTaskList,
			List<EstimateHistoryTaskVO> estimatePartList,
			List<EstimateHistoryTaskVO> estimateOtherServiceHistoryList) {
		EstimateItemsHistory estimateItems = null;
		List<LaborTaskHistory> laborTaskList = null;
		List<EstimatePartHistory> partList = null;
	    List<EstimateOtherServiceHistory> otherServiceList=null;
		if((null!=estimateTaskList && !estimateTaskList.isEmpty()) || (null!=estimatePartList && !estimatePartList.isEmpty()) ||(null!=estimateOtherServiceHistoryList && !estimateOtherServiceHistoryList.isEmpty())){
			estimateItems = new EstimateItemsHistory();
			// mapping labor tasks
			if(null!=estimateTaskList && !(estimateTaskList.isEmpty())){
				laborTaskList = new ArrayList<LaborTaskHistory>();
				for (EstimateHistoryTaskVO estimateTaskVO : estimateTaskList) {
						
						if (null!= estimateTaskVO){
							LaborTaskHistory labortask = new LaborTaskHistory();
							labortask.setTaskSeqNumber(estimateTaskVO.getTaskSeqNumber());
							labortask.setTaskName(estimateTaskVO.getTaskName());
							labortask.setDescription(estimateTaskVO
									.getDescription());
							labortask.setUnitPrice(estimateTaskVO.getUnitPrice());
							labortask.setQuantity(estimateTaskVO.getQuantity());
							labortask.setTotalPrice(estimateTaskVO.getTotalPrice());
							labortask.setAdditionalDetails(estimateTaskVO
									.getAdditionalDetails());
							labortask.setAction(estimateTaskVO.getAction());
							laborTaskList.add(labortask);
						}
				}
			}
			
			//mapping part items
			if(null!=estimatePartList && !(estimatePartList.isEmpty())){
				partList = new ArrayList<EstimatePartHistory>();
				for(EstimateHistoryTaskVO estimateTaskVO : estimatePartList){
					
					if(null!= estimateTaskVO){
						EstimatePartHistory estimatePart = new EstimatePartHistory();
						estimatePart.setPartSeqNumber(estimateTaskVO
								.getPartSeqNumber());
						estimatePart.setPartNo(estimateTaskVO.getPartNumber());
						estimatePart.setPartName(estimateTaskVO.getPartName());
						estimatePart.setDescription(estimateTaskVO
								.getDescription());
						estimatePart
								.setUnitPrice(estimateTaskVO.getUnitPrice());
						estimatePart.setQuantity(estimateTaskVO.getQuantity());
						estimatePart.setTotalPrice(estimateTaskVO
								.getTotalPrice());
						estimatePart.setAdditionalDetails(estimateTaskVO
								.getAdditionalDetails());
						estimatePart.setAction(estimateTaskVO.getAction());
						partList.add(estimatePart);
					}	
				}
			}
			
			//mapping other estimate service details
			if(null!=estimateOtherServiceHistoryList && !(estimateOtherServiceHistoryList.isEmpty())){
				otherServiceList = new ArrayList<EstimateOtherServiceHistory>();
				for(EstimateHistoryTaskVO estimateTaskVO : estimateOtherServiceHistoryList){
					
					if(null!= estimateTaskVO){
						EstimateOtherServiceHistory estimateOtherService = new EstimateOtherServiceHistory();
						estimateOtherService.setOtherServiceSeqNumber(estimateTaskVO.getOtherServiceSeqNumber());
						estimateOtherService.setOtherServiceType(estimateTaskVO.getOtherServiceType());
						estimateOtherService.setOtherServiceName(estimateTaskVO.getOtherServiceName());
						estimateOtherService.setDescription(estimateTaskVO.getDescription());
						estimateOtherService.setUnitPrice(estimateTaskVO.getUnitPrice());
						estimateOtherService.setQuantity(estimateTaskVO.getQuantity());
						estimateOtherService.setTotalPrice(estimateTaskVO.getTotalPrice());
						estimateOtherService.setAdditionalDetails(estimateTaskVO.getAdditionalDetails());
						estimateOtherService.setAction(estimateTaskVO.getAction());
						otherServiceList.add(estimateOtherService);
					}	
				}
			}
			if(null!=laborTaskList && !(laborTaskList.isEmpty())){
				LaborTasksHistory laborTasks = new LaborTasksHistory();
				laborTasks.setLabortask(laborTaskList);
				estimateItems.setLaborTasks(laborTasks);	
			}	
			
			if(null!=estimatePartList && !(estimatePartList.isEmpty())){
				EstimatePartsHistory estimateParts = new EstimatePartsHistory();
				estimateParts.setPart(partList);
				estimateItems.setParts(estimateParts);
			}
			
			if(null!=otherServiceList && !(otherServiceList.isEmpty())){
				EstimateOtherServicesHistory estimateOtherServices = new EstimateOtherServicesHistory();
				estimateOtherServices.setOtherService(otherServiceList);
				estimateItems.setOtherServices(estimateOtherServices);
			}
		}
		return estimateItems;
	}
	
	
	/** mapping labor and part items
	 * @param estimateTaskList
	 * @param estimatePartList
	 * @param estimateOtherServiceHistoryList 
	 * @return
	 */
	private void mapEstimateItemsHistory(
			List<EstimateHistoryTaskVO> estimateTaskList,
			List<EstimateHistoryTaskVO> estimatePartList,
			List<EstimateHistoryTaskVO> estimateOtherServiceHistoryList,EstimateHistory estimateHistory) {	
		EstimateItemsHistory estimateItems = null;
		List<LaborTaskHistory> laborTaskList = null;
		List<EstimatePartHistory> partList = null;
	    List<EstimateOtherServiceHistory> otherServiceList=null;
		if((null!=estimateTaskList && !estimateTaskList.isEmpty()) || (null!=estimatePartList && !estimatePartList.isEmpty()) ||(null!=estimateOtherServiceHistoryList && !estimateOtherServiceHistoryList.isEmpty())){
			estimateItems = new EstimateItemsHistory();
			// mapping labor tasks
			if(null!=estimateTaskList && !(estimateTaskList.isEmpty())){
				laborTaskList = new ArrayList<LaborTaskHistory>();
				for (EstimateHistoryTaskVO estimateTaskVO : estimateTaskList) {						
						if (null!= estimateTaskVO){
							LaborTaskHistory labortask = new LaborTaskHistory();
							labortask.setItemId(estimateTaskVO.getItemId());
							labortask.setTaskSeqNumber(estimateTaskVO.getTaskSeqNumber());
							labortask.setTaskName(estimateTaskVO.getTaskName());
							labortask.setDescription(estimateTaskVO
									.getDescription());
							labortask.setUnitPrice(estimateTaskVO.getUnitPrice());
							labortask.setQuantity(estimateTaskVO.getQuantity());
							labortask.setTotalPrice(estimateTaskVO.getTotalPrice());
							labortask.setAdditionalDetails(estimateTaskVO
									.getAdditionalDetails());
							labortask.setAction(estimateTaskVO.getAction());
							laborTaskList.add(labortask);
						}
				}
			}
			
			//mapping part items
			if(null!=estimatePartList && !(estimatePartList.isEmpty())){
				partList = new ArrayList<EstimatePartHistory>();
				for(EstimateHistoryTaskVO estimateTaskVO : estimatePartList){
					
					if(null!= estimateTaskVO){ 
						EstimatePartHistory estimatePart = new EstimatePartHistory();
						estimatePart.setItemId(estimateTaskVO.getItemId());
						estimatePart.setPartSeqNumber(estimateTaskVO
								.getPartSeqNumber());
						estimatePart.setPartNo(estimateTaskVO.getPartNumber());
						estimatePart.setPartName(estimateTaskVO.getPartName());
						estimatePart.setDescription(estimateTaskVO
								.getDescription());
						estimatePart
								.setUnitPrice(estimateTaskVO.getUnitPrice());
						estimatePart.setQuantity(estimateTaskVO.getQuantity());
						estimatePart.setTotalPrice(estimateTaskVO
								.getTotalPrice());
						estimatePart.setAdditionalDetails(estimateTaskVO
								.getAdditionalDetails());
						estimatePart.setAction(estimateTaskVO.getAction());
						partList.add(estimatePart);
					}	
				}
			}
			
			//mapping other estimate service details
			if(null!=estimateOtherServiceHistoryList && !(estimateOtherServiceHistoryList.isEmpty())){
				otherServiceList = new ArrayList<EstimateOtherServiceHistory>();
				for(EstimateHistoryTaskVO estimateTaskVO : estimateOtherServiceHistoryList){
					
					if(null!= estimateTaskVO){
						EstimateOtherServiceHistory estimateOtherService = new EstimateOtherServiceHistory();
						estimateOtherService.setItemId(estimateTaskVO.getItemId());
						estimateOtherService.setOtherServiceSeqNumber(estimateTaskVO.getOtherServiceSeqNumber());
						estimateOtherService.setOtherServiceType(estimateTaskVO.getOtherServiceType());
						estimateOtherService.setOtherServiceName(estimateTaskVO.getOtherServiceName());
						estimateOtherService.setDescription(estimateTaskVO.getDescription());
						estimateOtherService.setUnitPrice(estimateTaskVO.getUnitPrice());
						estimateOtherService.setQuantity(estimateTaskVO.getQuantity());
						estimateOtherService.setTotalPrice(estimateTaskVO.getTotalPrice());
						estimateOtherService.setAdditionalDetails(estimateTaskVO.getAdditionalDetails());
						estimateOtherService.setAction(estimateTaskVO.getAction());
						otherServiceList.add(estimateOtherService);
					}	
				}
			}
			if(null!=laborTaskList && !(laborTaskList.isEmpty())){
				LaborTasksHistory laborTasksHis = new LaborTasksHistory();
				laborTasksHis.setLabortask(laborTaskList);
				estimateItems.setLaborTasks(laborTasksHis);	
			}	
			
			if(null!=estimatePartList && !(estimatePartList.isEmpty())){
				EstimatePartsHistory estimateParts = new EstimatePartsHistory();
				estimateParts.setPart(partList);
				estimateItems.setParts(estimateParts);
			}
			
			if(null!=otherServiceList && !(otherServiceList.isEmpty())){
				EstimateOtherServicesHistory estimateOtherServices = new EstimateOtherServicesHistory();
				estimateOtherServices.setOtherService(otherServiceList);
				estimateItems.setOtherServices(estimateOtherServices);
			}
			estimateHistory.setEstimateItemsHistory(estimateItems);						
		}
		//return estimateItems;
	}
	
	/**@Description :SL-21385- Mapping other services details in estimation request to vo  for persistence in so_estimation_items table
	 * @param addEstimateRequest
	 * @param estimateVO
	 * @param estimationId 
	 */
	private void mapOtherEstimateSevices(EstimateVO estimateVO,AddEstimateRequest addEstimateRequest, Integer estimationId) {
		List<EstimateTaskVO> otherEstimateServices=null;
		
		//Mapping Other Service Details 
		if(null!= addEstimateRequest && null!= addEstimateRequest.getOtherEstimateServices() &&
				null!= addEstimateRequest.getOtherEstimateServices().getOtherEstimateService()
				    && !addEstimateRequest.getOtherEstimateServices().getOtherEstimateService().isEmpty()){
			  
			otherEstimateServices = new ArrayList<EstimateTaskVO>();
			for(OtherEstimateService otherEstimateService : addEstimateRequest.getOtherEstimateServices().getOtherEstimateService()){
				if(null!= otherEstimateService){
					EstimateTaskVO taskVO = new EstimateTaskVO();
					taskVO.setEstimationId(estimationId);
					if(StringUtils.isNotBlank(otherEstimateService.getOtherServiceSeqNumber())){
					  taskVO.setOtherServiceSeqNumber(Integer.parseInt(otherEstimateService.getOtherServiceSeqNumber()));
					}
					taskVO.setOtherServiceName(otherEstimateService.getOtherServiceName());
					taskVO.setOtherServiceType(otherEstimateService.getOtherServiceType());
					taskVO.setDescription(otherEstimateService.getDescription());
					if(StringUtils.isNotBlank(otherEstimateService.getUnitPrice())){
					   taskVO.setUnitPrice(Double.parseDouble(otherEstimateService.getUnitPrice()));
					}
					if(StringUtils.isNotBlank(otherEstimateService.getQuantity())){
					   taskVO.setQuantity(Integer.parseInt(otherEstimateService.getQuantity()));
					}
					if(StringUtils.isNotBlank(otherEstimateService.getTotalPrice())){
					   taskVO.setTotalPrice(Double.parseDouble(otherEstimateService.getTotalPrice()));
					}
					taskVO.setAdditionalDetails(otherEstimateService.getAdditionalDetails());
					otherEstimateServices.add(taskVO);
				}
			}
			
		}
		estimateVO.setEstimateOtherEstimateServices(otherEstimateServices);
	}



}