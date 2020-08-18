package com.servicelive.esb.mapper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.translator.util.DateUtil;
import com.newco.marketplace.webservices.dto.serviceorder.ContactRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreatePartRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateTaskRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CustomRef;
import com.newco.marketplace.webservices.dto.serviceorder.LocationRequest;
import com.newco.marketplace.webservices.dto.serviceorder.NoteRequest;
import com.newco.marketplace.webservices.dto.serviceorder.PhoneRequest;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.dto.Address;
import com.servicelive.esb.dto.JobCode;
import com.servicelive.esb.dto.Message;
import com.servicelive.esb.dto.Messages;
import com.servicelive.esb.dto.SalesCheckItem;
import com.servicelive.esb.dto.ServiceOrder;
import com.servicelive.esb.integration.IIntegrationServiceCoordinator;

public class OMSCreateDraftMapper extends CreateDraftMapper implements Mapper {

	private Logger logger = Logger.getLogger(OMSCreateDraftMapper.class);

	private ResourceBundle resourceBundle = ResourceBundle.getBundle("servicelive_esb_" + System.getProperty("sl_app_lifecycle"));
	
	private String dateCalMethod;
	private Integer buyer_id=1000;
	
	public OMSCreateDraftMapper(){
		super();
	}

	public Object mapData(Object obj) 
		throws Exception 
	{
		return (Object) mapRequestData((ServiceOrder) obj);
	}
	
	private CreateDraftRequest mapRequestData(ServiceOrder so) 
		throws Exception 
	{
		logger.debug("Processing mapping for the service order :" + so);
		CreateDraftRequest createDraftReq = new CreateDraftRequest();
			if (so != null) {
				this.mapMisc(so, createDraftReq);				
				this.mapLocationsAndContacts(so, createDraftReq);
				this.mapParts(so, createDraftReq);
				boolean nextDay = this.mapTasks(so, createDraftReq);
				this.mapDates(so, createDraftReq, nextDay);
				this.mapStatus(so, createDraftReq);
				this.mapCustomRefs(so, createDraftReq);
				this.mapMessages(createDraftReq, so.getMessages());
			}
		return createDraftReq;
	}
	
	private void mapMessages(	CreateDraftRequest request, 
								Messages messages) 
		throws Exception 
	{
		if (messages != null && messages.getMessages() != null && messages.getMessages().size() > 0) {
			List<NoteRequest> notes = new ArrayList<NoteRequest>();
			for (Message message : messages.getMessages()) {
				NoteRequest note = new NoteRequest();
				String noteText = message.getDate() + " " + message.getTime() + " " + message.getText();
				note.setNote(noteText);
				note.setSubject("OMS Update Message");
				notes.add(note);
			}
			/*NoteRequest[] noteRequestArray = new NoteRequest[notes.size()];
			int index = 0;
			for (NoteRequest noteRequest : notes) {
				noteRequestArray[index] = noteRequest;
				index++;
			}*/
			request.setNotes(notes);
		}
	}

	private void mapStatus(ServiceOrder so, CreateDraftRequest createDraftReq) 
		throws Exception 
	{
		if (so.getServiceOrderStatusCode() != null && so.getServiceOrderStatusCode().length() > 0) {
			String clientStatus = so.getServiceOrderStatusCode();
			if (clientStatus.equals(MarketESBConstant.ClientStatus.OMS_CANCELLED)) {
				clientStatus = MarketESBConstant.ClientStatus.SL_CANCELLED;
			}
			else if (clientStatus.equals(MarketESBConstant.ClientStatus.OMS_ASSIGNED_TECH)) {
				clientStatus =  MarketESBConstant.ClientStatus.SL_ASSIGNED_TECH;
			}
			else if (clientStatus.equals(MarketESBConstant.ClientStatus.OMS_WAITING_SERVICE)) {
				clientStatus =  MarketESBConstant.ClientStatus.SL_WAITING_SERVICE;
			}
			else if (clientStatus.equals(MarketESBConstant.ClientStatus.OMS_CLOSED)) {
				clientStatus =  MarketESBConstant.ClientStatus.SL_CLOSED;
			}
			else if (clientStatus.equals(MarketESBConstant.ClientStatus.OMS_EDITED)) {
				clientStatus =  MarketESBConstant.ClientStatus.SL_EDITED;
			}
			else if (clientStatus.equals(MarketESBConstant.ClientStatus.OMS_VOIDED)) {
				clientStatus =  MarketESBConstant.ClientStatus.SL_VOIDED;
			}
			createDraftReq.setClientStatus(clientStatus);
		}
	}

	private void mapMisc(	ServiceOrder so, 
							CreateDraftRequest createDraftReq) 
		throws Exception 
	{
		if (so.getSalesCheck() != null) {
			StringBuilder sbf = new StringBuilder();

			if (StringUtils.isNotBlank(so.getLogistics().getLogisticOrder()
					.getItemInstructions())) {
				sbf.append(so.getLogistics().getLogisticOrder().getItemInstructions()+ StringUtils.EMPTY);				
			}

			if (StringUtils.isNotBlank(so.getLogistics().getLogisticOrder()
					.getSpecialInstructions())) {
				sbf.append(so.getLogistics().getLogisticOrder() .getSpecialInstructions()+ StringUtils.EMPTY);
			}
			
			sbf.append(MarketESBConstant.SALES_CHECK_NUM);
			sbf.append(so.getSalesCheck().getNumber());

			sbf.append(MarketESBConstant.DATE_LABEL);
			sbf.append(so.getSalesCheck().getDate());

			for (SalesCheckItem salesCheck : so.getSalesCheck().getSalesCheckItems().getSalesCheckItemList()) {

				sbf.append(MarketESBConstant.DIVISION_LABEL);
				sbf.append(salesCheck.getDivision());

				sbf.append(MarketESBConstant.ITEM_NUMBER_LABEL);
				sbf.append(salesCheck.getItemNumber());

				sbf.append(MarketESBConstant.DESC_LABEL);
				sbf.append(salesCheck.getDescription());

				sbf.append(MarketESBConstant.QUANTITY_LABEL);
				sbf.append(salesCheck.getQuantity());

				sbf.append(MarketESBConstant.GIFT_FLAG_LABEL);
				sbf.append(salesCheck.getGiftFlag());

				sbf.append(MarketESBConstant.GIFT_DATE_LABEL);
				sbf.append(salesCheck.getGiftDate());
				
				//Add a newline character
				sbf.append(MarketESBConstant.NEWLINE_CHAR);

			}
			createDraftReq.setProviderInstructions(sbf.toString());
		}
		createDraftReq.setPartsSuppliedBy(MarketESBConstant.PARTS_SUPPLIED_BY_BUYER);
		createDraftReq.setRetailCancellationFee(MarketESBConstant.ZERO);
		createDraftReq.setSpendLimitParts(MarketESBConstant.ZERO);
		
		// SL-21931
		if (this.getIntegrationServiceCoordinator() == null) {
			this.setIntegrationServiceCoordinator((IIntegrationServiceCoordinator) this.getBeanFactory().getBean("integrationServiceCoordinator"));
		}
		String userName=this.getIntegrationServiceCoordinator().getIntegrationBO().getBuyerUserNameByBuyerId(buyer_id);
		createDraftReq.setUserId(userName);
		
	}

	private void mapDates(	ServiceOrder so, 
							CreateDraftRequest createDraftReq, 
							boolean nextDay) 
	throws Exception 
{
		SimpleDateFormat sdfSL = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss z");
		SimpleDateFormat sdfOMS = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm a");
		SimpleDateFormat sdfOMSDeliveryDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		SimpleDateFormat sdfNoTime = new SimpleDateFormat("yyyy-MM-dd");
		
		if (nextDay) {
			Calendar cal = Calendar.getInstance();
			cal.setTime( new Date() );
			cal.add(Calendar.DAY_OF_YEAR, 1);
			Date nextDate = cal.getTime();
			while( ! DateUtil.isBusinessDay(nextDate) )
			{
				cal.add(Calendar.DAY_OF_YEAR, 1);
				nextDate = cal.getTime();
			}
			dateCalMethod = MarketESBConstant.KEY_NEXT_DATE;
			Date promiseStartDate = sdfOMS.parse(sdfNoTime.format(nextDate) + "T08:00 AM");
			createDraftReq.setAppointmentStartDate(MarketESBConstant.KEY_PROMISED_DATE + sdfSL.format(promiseStartDate));
			Date promiseEndDate = sdfOMS.parse(sdfNoTime.format(nextDate) + "T05:00 PM");
			createDraftReq.setAppointmentEndDate(sdfSL.format(promiseEndDate));
		}
		else if (isValidDate(so.getPromisedDate())) {
			Date promiseStartDate = null;
			Date promiseEndDate = null;
			try {
				if (so.getPromisedTimeFrom() != null && so.getPromisedTimeFrom().length() > 0) {
					promiseStartDate = sdfOMS.parse(so.getPromisedDate() + "T" + so.getPromisedTimeFrom());
				}
				else {
					promiseStartDate = sdfOMS.parse(so.getPromisedDate() + "T08:00 AM");
				}
				if (so.getPromisedTimeTo() != null && so.getPromisedTimeTo().length() > 0) {
					promiseEndDate = sdfOMS.parse(so.getPromisedDate() + "T" + so.getPromisedTimeTo());
				}
				else {
					promiseEndDate = sdfOMS.parse(so.getPromisedDate() + "T05:00 PM");
				}
			}
			catch (Exception e) {
				promiseStartDate = new Date();
				logger.error( "Exception caught promiseStartDate set to " 
					+ promiseStartDate.toString(), e );
			}
			dateCalMethod = MarketESBConstant.KEY_PROMISED_DATE;
			createDraftReq.setAppointmentStartDate(MarketESBConstant.KEY_PROMISED_DATE + sdfSL.format(promiseStartDate));
			createDraftReq.setAppointmentEndDate(sdfSL.format(promiseEndDate));
		} else if (so.getLogistics() != null
				&& so.getLogistics().getLogisticOrder() != null
				&& so.getLogistics().getLogisticOrder().getDeliveryDate() != null
				&& isValidDate(so.getLogistics().getLogisticOrder().getDeliveryDate())) {
			Date deliveryStartDate = null;
			try {
				deliveryStartDate = sdfOMSDeliveryDate.parse(so.getLogistics().getLogisticOrder().getDeliveryDate() + "T" + so.getOrderTakenTime());
			}
			catch (Exception e) {
				deliveryStartDate = new Date();
				logger.error( "Exception caught deliveryStartDate set to " 
					+ deliveryStartDate.toString(), e );
			}
			dateCalMethod = MarketESBConstant.KEY_DELIVERY_DATE;
			createDraftReq.setAppointmentStartDate(MarketESBConstant.KEY_DELIVERY_DATE + sdfSL.format(deliveryStartDate));
		} else {
			// Business rule: Take current system date as appointment start date in this scenario
			Calendar cal = Calendar.getInstance();
			cal.setTime( new Date() );
			cal.add(Calendar.DAY_OF_YEAR, 1);
			Date nextDate = cal.getTime();
			while( ! DateUtil.isBusinessDay(nextDate) )
			{
				cal.add(Calendar.DAY_OF_YEAR, 1);
				nextDate = cal.getTime();
			}
			dateCalMethod = MarketESBConstant.KEY_CURRENT_DATE;
			Date promiseStartDate = sdfOMS.parse(sdfNoTime.format(nextDate) + "T08:00 AM");
			createDraftReq.setAppointmentStartDate(MarketESBConstant.KEY_DELIVERY_DATE + sdfSL.format(promiseStartDate));
			Date promiseEndDate = sdfOMS.parse(sdfNoTime.format(nextDate) + "T05:00 PM");
			createDraftReq.setAppointmentEndDate(sdfSL.format(promiseEndDate));
		}
	}
	
	private LocationRequest mapLocation(Address address) 
		throws Exception 
	{
		LocationRequest locReq = new LocationRequest();
		locReq.setStreet1(address.getStreetAddress1());
		locReq.setStreet2(address.getStreetAddress2());
		locReq.setAptNo(MarketESBConstant.EMPTY_STR);
		locReq.setCity(address.getCity());
		locReq.setState(address.getState());
		locReq.setCountry(MarketESBConstant.COUNTRY);
		locReq.setCreatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		if (address.getZipCode().length() == MarketESBConstant.ZIP_LONG) {
			locReq.setZip(address.getZipCode().substring(0, MarketESBConstant.ZIP_SHORT));
			locReq.setZip4(address.getZipCode().substring(MarketESBConstant.ZIP_SHORT, address.getZipCode().length()));
		} else {
			locReq.setZip(address.getZipCode());
		}
		
		return locReq;
	}
	
	private void mapParts(	ServiceOrder so, 
							CreateDraftRequest createDraftReq) 
		throws Exception 
	{
		//currently no multiple parts will occur in an import
		if (so.getMerchandise() != null) {
			//using a list to provide easier port if multiple merchandise nodes were present
			List<CreatePartRequest> parts = new ArrayList<CreatePartRequest>();
			
			CreatePartRequest part = new CreatePartRequest();
			LocationRequest pickupLoc = new LocationRequest();
			ContactRequest pickUpCont = new ContactRequest();
			
			part.setQuantity(MarketESBConstant.QUANTITY);

			if (so.getLogistics() != null
					&& so.getLogistics().getLogisticOrder() != null
					&& so.getLogistics().getLogisticOrder().getPickupLocationCode() != null
					&& !MarketESBConstant.EMPTY_STR.equals(so.getLogistics().getLogisticOrder().getPickupLocationCode())
					&& so.getLogistics().getLogisticOrder().getAddress() != null) {
				
				pickupLoc = mapLocation(so.getLogistics().getLogisticOrder().getAddress());
				part.setPickupLocation(pickupLoc);
				pickUpCont.setContactTypeId(MarketESBConstant.DEFAULT_CONTACT_TYPE_ID);
				//CAR Story 16 - this should not be harded to Sears Store
				// Sears does not currently send values for these fields.
				pickUpCont.setFirstName(null);
				pickUpCont.setLastName(null);
				part.setPickupContact(pickUpCont);
			}
			
			// Sl-21931
			String userName=this.getIntegrationServiceCoordinator().getIntegrationBO().getBuyerUserNameByBuyerId(buyer_id);
			part.setUserId(userName);
			
			
			StringBuilder sbPartDescription = new StringBuilder();
			sbPartDescription.append(so.getMerchandise().getDescription());
			if (so.getLogistics().getLogisticOrder().getWarehouseNumber() != null) {
				sbPartDescription.append(" WAREHOUSE:" + so.getLogistics().getLogisticOrder().getWarehouseNumber());
			}
			if (so.getLogistics().getLogisticOrder().getLastMaintenanceDate() != null) {
				sbPartDescription.append(" LAST MAINTENANCE DATE:" + so.getLogistics().getLogisticOrder().getLastMaintenanceDate());
			}
			if (so.getLogistics().getLogisticOrder().getDeliveryStatus() != null) {
				sbPartDescription.append(" DELIVERY STAUTS:" + so.getLogistics().getLogisticOrder().getDeliveryStatus());
			}
			if (so.getLogistics().getLogisticOrder().getDeliveryDescription() != null) {
				sbPartDescription.append(" DELIVERY DESCRIPTION:" + so.getLogistics().getLogisticOrder().getDeliveryDescription());
			}
			if (so.getLogistics().getLogisticOrder().getHoldCode() != null) {
				sbPartDescription.append(" HOLD CODE:" + so.getLogistics().getLogisticOrder().getHoldCode());
			}
			if (so.getLogistics().getLogisticOrder().getHoldDescription() != null) {
				sbPartDescription.append(" HOLD DESCRIPTION:" + so.getLogistics().getLogisticOrder().getHoldDescription());
			}
			if (so.getLogistics().getLogisticOrder().getPendCode() != null) {
				sbPartDescription.append(" PEND CODE:" + so.getLogistics().getLogisticOrder().getPendCode());
			}
			if (so.getLogistics().getLogisticOrder().getPendDescription() != null) {
				sbPartDescription.append(" PEND DESCRIPTION:" + so.getLogistics().getLogisticOrder().getPendDescription());
			}
			if (so.getLogistics().getLogisticOrder().getShipmentMethodCode() != null) {
				sbPartDescription.append(" SHIPMENT METHOD CODE:" + so.getLogistics().getLogisticOrder().getShipmentMethodCode());
			}
			if (so.getLogistics().getLogisticOrder().getPickupLocationCode() != null) {
				sbPartDescription.append(" PICKUP LOCATION CODE:" + so.getLogistics().getLogisticOrder().getPickupLocationCode());
			}
			if (so.getLogistics().getLogisticOrder().getShipmentMethodDescription() != null) {
				sbPartDescription.append(" SHIPMENT METHOD DESCRIPTION:" + so.getLogistics().getLogisticOrder().getShipmentMethodDescription());
			}
			if (so.getLogistics().getLogisticOrder().getDeliveryTimeCode() != null) {
				sbPartDescription.append(" DELIVERY TIME CODE:" + so.getLogistics().getLogisticOrder().getDeliveryTimeCode());
			}
			if (so.getLogistics().getLogisticOrder().getDeliveryTimeDescription() != null) {
				sbPartDescription.append(" DELIVERY TIME DESCRIPTION:" + so.getLogistics().getLogisticOrder().getDeliveryTimeDescription());
			}
			part.setPartDesc(sbPartDescription.toString());
			part.setModelNumber(so.getMerchandise().getModel());
			part.setManufacturer(so.getMerchandise().getBrand());
			String partReference = "";
			if (so.getMerchandise().getDivision() != null) {
				partReference = so.getMerchandise().getDivision();
			}
			if (so.getMerchandise().getItemNumber() != null) {
				partReference += so.getMerchandise().getItemNumber();
			}
			part.setReferencePartId(partReference);
			parts.add(part);
			CreatePartRequest[] partArray = new CreatePartRequest[parts.size()];
			int counter = 0;
			for (CreatePartRequest partRequest : parts) {
				partArray[counter] = partRequest;
				counter++;
			}
			createDraftReq.setParts(Arrays.asList(partArray));
		}

	}

	private void mapLocationsAndContacts(	ServiceOrder so, 
											CreateDraftRequest createDraftReq) 
		throws Exception 
	{
		LocationRequest locReq = new LocationRequest();
		PhoneRequest phone1 = new PhoneRequest();
		PhoneRequest phone2 = new PhoneRequest();
		int classId = MarketESBConstant.HOME_PHONE_TYPE;// By default a "Home" number
		ContactRequest serviceContactReq = new ContactRequest();
		serviceContactReq.setContactTypeId(MarketESBConstant.DEFAULT_CONTACT_TYPE_ID);
		
		//Customer information should always come from the customer object
		if (so.getCustomer() != null) {

			serviceContactReq.setHonorific(MarketESBConstant.EMPTY_STR);
			serviceContactReq.setMi(MarketESBConstant.EMPTY_STR);
			serviceContactReq.setFirstName(so.getCustomer().getFirstName());
			serviceContactReq.setLastName(so.getCustomer().getLastName());
			String type = so.getCustomer().getType();
			if (type.equals(MarketESBConstant.KEY_WORK_PHONE_TYPE)) {
				classId = MarketESBConstant.WORK_PHONE_TYPE;
			}
		}
		phone1.setClassId(classId);
		phone2.setClassId(classId);
		
		int numberOfPhones = 0;
		//PHONES
		//phone is always primary
		//use cell for alternate if present
		//use alternate for alternate if cell not present
		//use all 9's if no phones present
		if (so.getRepairLocation() != null 
				&& so.getRepairLocation().getPhone() != null
				&& !so.getRepairLocation().getPhone().trim().equals(MarketESBConstant.EMPTY_STR)) {
				//PRIMARY
				phone1.setPhoneNo(so.getRepairLocation().getPhone());
				phone1.setPhoneType(MarketESBConstant.PRIMARY_PHONE);
				phone1.setCreatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				numberOfPhones++;
				//SECONDAY
				if (so.getRepairLocation().getAltPhone() != null 
						&& !so.getRepairLocation().getAltPhone().trim().equals(MarketESBConstant.EMPTY_STR)) {
					
					phone2.setPhoneNo(so.getRepairLocation().getAltPhone());
					phone2.setPhoneType(MarketESBConstant.ALTERNATE_PHONE);
					phone2.setCreatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					phone2.setClassId(MarketESBConstant.WORK_PHONE_TYPE);
					numberOfPhones++;
				}
		}
		else if (so.getCustomer() != null 
				&& so.getCustomer().getPhone() != null 
				&& !so.getCustomer().getPhone().trim().equals(MarketESBConstant.EMPTY_STR)) {
			
			phone1.setPhoneNo(so.getCustomer().getPhone());
			phone1.setPhoneType(MarketESBConstant.PRIMARY_PHONE);
			phone1.setCreatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			numberOfPhones++;
			if (so.getCustomer() != null 
					&& so.getCustomer().getCellPhoneNumber() != null 
					&& !so.getCustomer().getCellPhoneNumber().trim().equals(MarketESBConstant.EMPTY_STR)) {
				
				phone2.setPhoneNo(so.getCustomer().getCellPhoneNumber());
				phone2.setPhoneType(MarketESBConstant.ALTERNATE_PHONE);
				phone2.setCreatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				phone2.setClassId(MarketESBConstant.CELL_PHONE_TYPE);
				numberOfPhones++;
			}
			else if (so.getCustomer() != null 
					&& so.getCustomer().getAltPhone() != null 
					&& !so.getCustomer().getAltPhone().trim().equals(MarketESBConstant.EMPTY_STR)) {
				
				phone2.setPhoneNo(so.getCustomer().getAltPhone());
				phone2.setPhoneType(MarketESBConstant.ALTERNATE_PHONE);
				phone2.setCreatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				phone2.setClassId(MarketESBConstant.WORK_PHONE_TYPE);
				numberOfPhones++;
			}
		}
		else {
			phone1.setPhoneNo("9999999999");
			phone1.setPhoneType(MarketESBConstant.PRIMARY_PHONE);
			phone1.setCreatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			numberOfPhones++;
		}
			
		PhoneRequest[] phoneArray = new PhoneRequest[numberOfPhones];
		phoneArray[0] = phone1;
		if (numberOfPhones > 1) {
			phoneArray[1] = phone2;
		}
		serviceContactReq.setPhones(Arrays.asList(phoneArray));
		createDraftReq.setServiceContact(serviceContactReq);
		
		//LOCATION
		if (so.getRepairLocation() != null 
				&& so.getRepairLocation().getAddress() != null 
				&& so.getRepairLocation().getAddress().getZipCode() != null 
				&& so.getRepairLocation().getAddress().getZipCode().trim().length() > 0) {
			
			locReq = mapLocation(so.getRepairLocation().getAddress());

		} else if (so.getCustomer() != null
				&& so.getCustomer().getAddress() != null
				&& so.getCustomer().getAddress().getZipCode() != null
				&& so.getCustomer().getAddress().getZipCode().trim().length() > 0) {
			
			locReq = mapLocation(so.getCustomer().getAddress());
			locReq.setLocName((so.getCustomer().getType() != null ? so.getCustomer().getType() : ""));
		}
		
		String locnNotes = "";
		if (so.getCustomer() != null && so.getCustomer().getSupplementalCustomerInformation() != null && so.getCustomer().getSupplementalCustomerInformation().length() > 0) {
			locnNotes += so.getCustomer().getSupplementalCustomerInformation() + "\n";
		}
		if (so.getSpecialInstructions1() != null && so.getSpecialInstructions1().length() > 0) {
			locnNotes += so.getSpecialInstructions1() + "\n";
		}
		if (so.getSpecialInstructions2() != null && so.getSpecialInstructions2().length() > 0) {
			locnNotes += so.getSpecialInstructions2() + "\n";
		}
		if (so.getPermanentInstructions() != null && so.getPermanentInstructions().length() > 0) {
			locnNotes += so.getPermanentInstructions() + "\n";
		}
		locReq.setLocnNotes(locnNotes);
		createDraftReq.setServiceLocation(locReq);
	}

	private void mapCustomRefs(ServiceOrder so,	CreateDraftRequest createDraftReq) 
		throws Exception
		{
		List<CustomRef> refsToAdd = new ArrayList<CustomRef>();
		String soIdentifier = "";
		
		if (so.getServiceUnitNumber() != null && so.getServiceUnitNumber().length() > 0) {
			CustomRef unitNumber = new CustomRef();
			unitNumber.setKey(MarketESBConstant.CUSTOM_REF_UNIT_NUM);
			unitNumber.setValue(so.getServiceUnitNumber());
			soIdentifier += so.getServiceUnitNumber();
			refsToAdd.add(unitNumber);
			createDraftReq.setUnitNumber(so.getServiceUnitNumber());
		}

		if (so.getSalesCheck() != null && so.getSalesCheck().getNumber() != null && so.getSalesCheck().getNumber().length() > 0) {
			CustomRef salesCheckNum = new CustomRef();
			salesCheckNum.setKey(MarketESBConstant.CUSTOM_REF_SALES_CHECK_NUM);
			salesCheckNum.setValue(so.getSalesCheck().getNumber());
			refsToAdd.add(salesCheckNum);
		}
		
		if (so.getServiceOrderNumber() != null && so.getServiceOrderNumber().length() > 0) {
			CustomRef serviceOrderNum = new CustomRef();
			serviceOrderNum.setKey(MarketESBConstant.CUSTOM_REF_ORDER_NUM);
			serviceOrderNum.setValue(so.getServiceOrderNumber());
			soIdentifier += so.getServiceOrderNumber();
			refsToAdd.add(serviceOrderNum);
			createDraftReq.setOrderNumber(so.getServiceOrderNumber());
		}

		if (so.getOrderTakenDate() != null && so.getOrderTakenDate().length() > 0) {
			CustomRef orderTakenDate = new CustomRef();
			orderTakenDate.setKey(MarketESBConstant.CUSTOM_REF_SALES_CHECK_DATE);
			orderTakenDate.setValue(so.getSalesCheck().getDate());
			refsToAdd.add(orderTakenDate);
		}
		
		if (so.getOrderTakenTime() != null && so.getOrderTakenTime().length() > 0) {
			CustomRef orderTakenTime = new CustomRef();
			orderTakenTime.setKey(MarketESBConstant.CUSTOM_REF_SALES_CHECK_TIME);
			orderTakenTime.setValue(so.getSalesCheck().getTime());
			refsToAdd.add(orderTakenTime);
		}


		if (so.getCustomer() != null && so.getCustomer().getPreferredLanguage() != null && so.getCustomer().getPreferredLanguage().length() > 0) {
			CustomRef prefLang = new CustomRef();
			prefLang.setKey(MarketESBConstant.CUSTOM_REF_PREF_LANG);
			prefLang.setValue(so.getCustomer().getPreferredLanguage());
			refsToAdd.add(prefLang);
		}
		
		if (so.getRequestedService() != null && so.getRequestedService().length() > 0) {
			CustomRef serviceRequested = new CustomRef();
			serviceRequested.setKey(MarketESBConstant.CUSTOM_REF_SERVICE_REQUESTED);
			serviceRequested.setValue(so.getRequestedService());
			refsToAdd.add(serviceRequested);
		}
		
		if (so.getSalesCheck().getSellingAssociate() != null && so.getSalesCheck().getSellingAssociate().length() > 0) {
			CustomRef salesAssoc = new CustomRef();
			salesAssoc.setKey(MarketESBConstant.CUSTOM_REF_SELLING_ASSOC);
			salesAssoc.setValue(so.getSalesCheck().getSellingAssociate());
			refsToAdd.add(salesAssoc);
		}

		if (so.getSalesCheck().getNumber() != null && so.getSalesCheck().getNumber().length() > 5) {
			CustomRef storeNumber = new CustomRef();
			storeNumber.setKey(MarketESBConstant.CUSTOM_REF_STORE_NUMBER);
			storeNumber.setValue(so.getSalesCheck().getNumber().substring(0, 5));
			refsToAdd.add(storeNumber);
		}
		
		if (so.getLogistics().getLogisticsMerchandise() != null && so.getLogistics().getLogisticsMerchandise().getScimHandlingCode() != null) {
			CustomRef scimCode = new CustomRef();
			scimCode.setKey("SCIMH Handlng Code");
			scimCode.setValue(so.getLogistics().getLogisticsMerchandise().getScimHandlingCode());
			refsToAdd.add(scimCode);
		}
		
		if (so.getLogistics().getLogisticsMerchandise() != null && so.getLogistics().getLogisticsMerchandise().getScimHandlingDescription() != null) {
			CustomRef scimCode = new CustomRef();
			scimCode.setKey("SCIMH Handlng Description");
			scimCode.setValue(so.getLogistics().getLogisticsMerchandise().getScimHandlingDescription());
			refsToAdd.add(scimCode);
		}
		
		if (so.getLogistics().getLogisticsMerchandise() != null && so.getLogistics().getLogisticsMerchandise().getLastMaintenanceDate() != null) {
			CustomRef lastMaint = new CustomRef();
			lastMaint.setKey("Last Maintenance Date");
			lastMaint.setValue(so.getLogistics().getLogisticsMerchandise().getLastMaintenanceDate());
			refsToAdd.add(lastMaint);
		}
		
		if (so.getLogistics().getLogisticOrder() != null && so.getLogistics().getLogisticOrder().getDeliveryDate() != null) {
			CustomRef lastMaint = new CustomRef();
			lastMaint.setKey(MarketESBConstant.CUSTOM_REF_MERCHANDISE_AVAILABILITY_DATE);
			lastMaint.setValue(so.getLogistics().getLogisticOrder().getDeliveryDate());
			refsToAdd.add(lastMaint);
		}
		
		if (so.getLogistics().getLogisticOrder() != null && so.getLogistics().getLogisticOrder().getPickupLocationCode() != null) {
			CustomRef pickupLoc = new CustomRef();
			pickupLoc.setKey(MarketESBConstant.CUSTOM_REF_PICKUP_LOCATION_CODE);
			pickupLoc.setValue(so.getLogistics().getLogisticOrder().getPickupLocationCode());
			refsToAdd.add(pickupLoc);
		}
		
		if(so.getMerchandise() != null && so.getMerchandise().getSpecialty() != null) {
			CustomRef specCode = new CustomRef();
			specCode.setKey(MarketESBConstant.CUSTOM_REF_SPECIALTY_CODE);
			specCode.setValue(so.getMerchandise().getSpecialty());
			refsToAdd.add(specCode);
		}
		
		if(so.getMerchandise() != null && so.getMerchandise().getCode() != null) {
			CustomRef merchandiseCode = new CustomRef();
			merchandiseCode.setKey(MarketESBConstant.CUSTOM_REF_MERCHANDISE_CODE);
			merchandiseCode.setValue(so.getMerchandise().getCode());
			refsToAdd.add(merchandiseCode);
		}
		
		if(so.getMerchandise() != null && so.getMerchandise().getBrand() != null) {
			CustomRef brand = new CustomRef();
			brand.setKey(MarketESBConstant.CUSTOM_REF_BRAND);
			brand.setValue(so.getMerchandise().getBrand());
			refsToAdd.add(brand);
		}
		
		if (soIdentifier.length() > 0) {
			CustomRef soClientID = new CustomRef();
			soClientID.setKey(MarketESBConstant.CUSTOM_REF_ORDERID_STRING);
			soClientID.setValue(soIdentifier);
			refsToAdd.add(soClientID);
		}
		
		
		if (MarketESBConstant.KEY_DELIVERY_DATE.equals(dateCalMethod)) {
			CustomRef dateCalMethodRef = new CustomRef();
			dateCalMethodRef.setKey(MarketESBConstant.CUSTOM_REF_DATE_CALCULATION_METHOD);
			dateCalMethodRef.setValue(MarketESBConstant.KEY_DELIVERY_DATE);
			refsToAdd.add(dateCalMethodRef);
		}
		
		if (MarketESBConstant.KEY_PROMISED_DATE.equals(dateCalMethod)) {
			CustomRef dateCalMethodRef = new CustomRef();
			dateCalMethodRef.setKey(MarketESBConstant.CUSTOM_REF_DATE_CALCULATION_METHOD);
			dateCalMethodRef.setValue(MarketESBConstant.KEY_PROMISED_DATE);
			refsToAdd.add(dateCalMethodRef);
		}
		
		
		if (MarketESBConstant.KEY_NEXT_DATE.equals(dateCalMethod)) {
			CustomRef dateCalMethodRef = new CustomRef();
			dateCalMethodRef.setKey(MarketESBConstant.CUSTOM_REF_DATE_CALCULATION_METHOD);
			dateCalMethodRef.setValue(MarketESBConstant.KEY_NEXT_DATE);
			refsToAdd.add(dateCalMethodRef);
		}
		
		if (MarketESBConstant.KEY_CURRENT_DATE.equals(dateCalMethod)) {
			CustomRef dateCalMethodRef = new CustomRef();
			dateCalMethodRef.setKey(MarketESBConstant.CUSTOM_REF_DATE_CALCULATION_METHOD);
			dateCalMethodRef.setValue(MarketESBConstant.KEY_CURRENT_DATE);
			refsToAdd.add(dateCalMethodRef);
		}
		
		// add custom ref to hold Template name for service Order
		CustomRef templateRef = new CustomRef();
		templateRef.setKey(MarketESBConstant.CUSTOM_REF_TEMPLATE_NAME);
		// value set in Webservice while creating draft
		templateRef.setValue(StringUtils.EMPTY);
		refsToAdd.add(templateRef);
		
		//Adding custom reference to hold main job code
		if (so.getJobCodes() != null && so.getJobCodes().getJobCodeList() != null
				&& !so.getJobCodes().getJobCodeList().isEmpty()) {
			for (JobCode jobCode : so.getJobCodes().getJobCodeList()) {
				if (jobCode.getType().equals(MarketESBConstant.JOB_CODE_PRIMARY)) {
					CustomRef mainJobCode = new CustomRef();
					mainJobCode.setKey(MarketESBConstant.CUSTOM_REF_MAIN_SKU);
					mainJobCode.setValue(jobCode.getNumber());
					refsToAdd.add(mainJobCode);
				}
			}
		}
				
		//Adding custom reference to hold process id
		if(StringUtils.isNotBlank(so.getProcessId())){
			CustomRef processId = new CustomRef();
			processId.setKey(MarketESBConstant.CUSTOM_REF_PROCESS_ID);
			processId.setValue(so.getProcessId());
			refsToAdd.add(processId);
		}
	
		if (refsToAdd.size() > 0) {
			CustomRef[] customRefArray = new CustomRef[refsToAdd.size()];
			int index = 0;
			for (CustomRef ref : refsToAdd) {
				customRefArray[index] = ref;
				index++;
			}
			createDraftReq.setCustomRef(Arrays.asList(customRefArray));
		}
	}

	private boolean mapTasks(ServiceOrder so, CreateDraftRequest createDraftReq)
			throws Exception 
	{
		if (so.getJobCodes() != null && so.getJobCodes().getJobCodeList().size() > 0) {
			List<CreateTaskRequest> tasks = new ArrayList<CreateTaskRequest>();
			boolean nextDay = false;
		
			for (int i = 0; i < so.getJobCodes().getJobCodeList().size(); i++) {
				CreateTaskRequest taskObj = new CreateTaskRequest();
				JobCode jobCode = so.getJobCodes().getJobCodeList().get(i);				
				if (jobCode.getType().equals(MarketESBConstant.JOB_CODE_PRIMARY)) {
					taskObj.setServiceTypeId(MarketESBConstant.PRIMARY_JOB_CODE);
				} else {
					taskObj.setServiceTypeId(MarketESBConstant.ALTERNAME_JOB_CODE);
				}	
				
				//(Stock Number)+"|"+(Job Code Description) from SST WS's for every Job Code (Translator removes the pipe)
				taskObj.setTaskName(jobCode.getJobCodeInfo().getStockNumber()
						+ "|"+ jobCode.getDescription());
				
				//(OMS feed's Store#)+SST WS's (Inclusion Description) for every Job Code
				taskObj.setTaskComments("Store No:" + so.getSalesCheck().getNumber().substring(0, 5)+ "-" 
						+ jobCode.getJobCodeInfo().getInclusionDesc());
					
				
				if (jobCode.getNumber().equals(MarketESBConstant.NEXT_DAY_TASK)) {
					nextDay = true;
				}
				else{
					tasks.add(taskObj);
				}
					
			}
			if (nextDay && tasks != null) {
				for (CreateTaskRequest task : tasks) {
					task.setTaskComments(MarketESBConstant.NEXT_DAY_TASK_COMMENTS + task.getTaskComments());					
				}
			}
			CreateTaskRequest taskArray[] = new CreateTaskRequest[tasks.size()];
			int taskCount = 0;
			for (CreateTaskRequest task : tasks) {
				taskArray[taskCount] = task;
				taskCount++;
			}
			createDraftReq.setTasks(Arrays.asList(taskArray));
			return nextDay;
		}
		return false;
	}

	private boolean isValidDate(String date) 
		throws Exception {
		//see if it is the default date
		if (date == null || date.length() == 0 || date.equals(MarketESBConstant.DEFAULT_DATE)) {
			return false;
		}
		else {
			return true;
		}
	}
}
