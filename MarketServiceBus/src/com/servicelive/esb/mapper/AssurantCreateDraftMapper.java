package com.servicelive.esb.mapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.translator.dao.IncidentEvent;
import com.newco.marketplace.translator.dao.IncidentPart;
import com.newco.marketplace.translator.dao.WarrantyContract;
import com.newco.marketplace.webservices.dto.serviceorder.ContactRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreatePartRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateTaskRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CustomRef;
import com.newco.marketplace.webservices.dto.serviceorder.LocationRequest;
import com.newco.marketplace.webservices.dto.serviceorder.PhoneRequest;
import com.servicelive.esb.constant.MarketESBConstant;

public class AssurantCreateDraftMapper extends CreateDraftMapper implements	Mapper {

	private Logger logger = Logger.getLogger(AssurantCreateDraftMapper.class);
	private ResourceBundle resourceBundle = ResourceBundle.getBundle("servicelive_esb_" + System.getProperty("sl_app_lifecycle"));
	private SimpleDateFormat dfmt = new SimpleDateFormat("yyy-MM-dd HH:mm:ss z");
	
	public Object mapData(Object obj) throws Exception {
		return (Object) mapRequestData((IncidentEvent) obj);
	}
	
	private CreateDraftRequest mapRequestData(IncidentEvent incidentEvent) throws Exception {
		logger.info("Mapping incident to CreateDraftRequest object: " + incidentEvent.getIncident().getClientIncidentID());
		CreateDraftRequest createDraftReq = new CreateDraftRequest();
		try {
			if (incidentEvent != null) {
				this.mapMisc(incidentEvent, createDraftReq);
				this.mapCustomRefs(incidentEvent, createDraftReq);
				this.mapContacts(incidentEvent, createDraftReq);
				this.mapLocation(incidentEvent, createDraftReq);
				this.mapParts(incidentEvent, createDraftReq);
				this.mapStatus(incidentEvent, createDraftReq);
				this.mapMessages(createDraftReq, incidentEvent);
				this.mapTasks(incidentEvent, createDraftReq);
				this.mapNotes(incidentEvent, createDraftReq);
			}
		} catch (Exception e) {
			logger.error("----Error while mapping the values----", e);
		}

		return createDraftReq;
	}

	private void mapMessages(CreateDraftRequest createDraftReq, IncidentEvent incidentEvent) {
		
	}

	/**
	 * NEW, UPDATE, ACKNOWLEDGEMENT, INFO, CANCEL
	 * @param incidentEvent
	 * @param createDraftReq
	 */
	private void mapStatus(IncidentEvent incidentEvent, CreateDraftRequest createDraftReq) {
		if (incidentEvent.getStatus().equals(MarketESBConstant.ClientStatus.ASSURANT_NEW)) {
			createDraftReq.setClientStatus(MarketESBConstant.ClientStatus.SL_NEW);
		}
		else if (incidentEvent.getStatus().equals(MarketESBConstant.ClientStatus.ASSURANT_CANCELLED)) {
			createDraftReq.setClientStatus(MarketESBConstant.ClientStatus.SL_CANCELLED);
		}
		else if (incidentEvent.getStatus().equals(MarketESBConstant.ClientStatus.ASSURANT_UPDATE)) {
			createDraftReq.setClientStatus(MarketESBConstant.ClientStatus.SL_UPDATE);
		}
	}
	
	private void mapTasks(IncidentEvent incidentEvent, CreateDraftRequest createDraftReq) throws Exception {

		if (incidentEvent.getIncidentParts() != null && incidentEvent.getIncidentParts().size() > 0) {
			List<CreateTaskRequest> tasks = new ArrayList<CreateTaskRequest>();
			boolean defaultSet = false;
			for (IncidentPart incidentPart : incidentEvent.getIncidentParts()) {
				CreateTaskRequest task = new CreateTaskRequest();
				task.setTaskComments(incidentPart.getClassComments());
				task.setTaskName(incidentPart.getClassCode() + "|" + incidentPart.getClassComments());
				//First part becomes default
				if (!defaultSet) {
					task.setServiceTypeId(new Integer(1));
					defaultSet = true;
				}
				tasks.add(task);
			}
			CreateTaskRequest taskArray[] = new CreateTaskRequest[tasks.size()];
			int taskCount = 0;
			for (CreateTaskRequest task : tasks) {
				taskArray[taskCount] = task;
				taskCount++;
			}
			createDraftReq.setTasks(Arrays.asList(taskArray));
		}
	}

	private void mapParts(IncidentEvent incidentEvent, CreateDraftRequest createDraftReq) {
		List<CreatePartRequest> parts = new ArrayList<CreatePartRequest>();
		
		for (IncidentPart ipart : incidentEvent.getIncidentParts()) {
			CreatePartRequest part = new CreatePartRequest();
			//LocationRequest pickupLoc = new LocationRequest();
			//ContactRequest pickUpCont = new ContactRequest();
			StringBuilder sb = new StringBuilder();
			if (StringUtils.isNotBlank(ipart.getPartComments())) {
				sb.append("Comments:");
				sb.append(ipart.getPartComments());
				sb.append(" ! ");
			}
			if (StringUtils.isNotBlank(incidentEvent.getShippingMethod())) {
				sb.append("ShippingMethod:");
				sb.append(incidentEvent.getShippingMethod());
				sb.append(" ! ");
			}
			if (StringUtils.isNotBlank(ipart.getOEMNumber())) {
				part.setManufacturerPartNumber(ipart.getOEMNumber());
			}
			if (StringUtils.isNotBlank(ipart.getClassCode())) {
				part.setAltPartRef1(ipart.getClassCode());
			}
			if (StringUtils.isNotBlank(ipart.getClassComments())) {
				part.setAdditionalPartInfo(ipart.getClassComments());
			}

			if (StringUtils.isNotBlank(ipart.getPartNumber())) {
				part.setVendorPartNumber(ipart.getPartNumber());
			}
			if (StringUtils.isNotBlank(incidentEvent.getManufacturer())) {
				part.setManufacturer(incidentEvent.getManufacturer());
			}
			if (StringUtils.isNotBlank(incidentEvent.getModelNumber())) {
				part.setModelNumber(incidentEvent.getModelNumber());
			}
			if (StringUtils.isNotBlank(incidentEvent.getSerialNumber())) {
				part.setSerialNumber(incidentEvent.getSerialNumber());
			}
			part.setPartDesc(sb.toString());
			part.setQuantity(MarketESBConstant.QUANTITY);
			part.setUserId((String) resourceBundle.getObject(MarketESBConstant.Client.ASSURANT + "_USER_ID"));
			/*commenting it out as default is causing validation error on front end asking for tracking number */
			//part.setShippingCarrierId(MarketESBConstant.FEDEX); 
			parts.add(part);
		}
		CreatePartRequest[] partArray = new CreatePartRequest[parts.size()];
		int counter = 0;
		for (CreatePartRequest partRequest : parts) {
			partArray[counter] = partRequest;
			counter++;
		}
		createDraftReq.setParts(Arrays.asList(partArray));
	}

	/**
	 * Map the contact information from the file to Create Draft Request object
	 * 
	 * @param incidentEvent
	 * @param createDraftReq
	 */
	private void mapContacts(IncidentEvent incidentEvent, CreateDraftRequest createDraftReq) {
		int numberOfPhones = 0;
		ContactRequest contact = new ContactRequest();
		contact.setContactTypeId(MarketESBConstant.DEFAULT_CONTACT_TYPE_ID);
		contact.setFirstName(incidentEvent.getContact().getFirstName());
		contact.setLastName(incidentEvent.getContact().getLastName());
		contact.setHonorific("");
		contact.setEmail(incidentEvent.getContact().getEmail());
		mapPhoneNumbers(incidentEvent, numberOfPhones, contact);
		createDraftReq.setServiceContact(contact);
	}

	/**
	 * Map the phone numbers from the file to Service Contact
	 * 
	 * @param incidentEvent
	 * @param numberOfPhones
	 * @param contact
	 */
	private void mapPhoneNumbers(IncidentEvent incidentEvent,
			int numberOfPhones, ContactRequest contact) {
		PhoneRequest phone1 = new PhoneRequest();
		PhoneRequest phone2 = new PhoneRequest();
		
		//Primary Phone number
		if (StringUtils.isNotBlank(incidentEvent.getContact().getPhone1().trim())) {
			phone1.setPhoneNo(incidentEvent.getContact().getPhone1());
			phone1.setPhoneExt(incidentEvent.getContact().getPhone1Ext());
			phone1.setPhoneType(MarketESBConstant.PRIMARY_PHONE);
			phone1.setClassId(MarketESBConstant.HOME_PHONE_TYPE);
			numberOfPhones++;
		}
		//Alternate Phone number
		if (StringUtils.isNotBlank(incidentEvent.getContact().getPhone2().trim())) {
			phone2.setPhoneNo(incidentEvent.getContact().getPhone2());
			phone2.setPhoneExt(incidentEvent.getContact().getPhone2Ext());
			phone2.setPhoneType(MarketESBConstant.ALTERNATE_PHONE);
			phone2.setClassId(MarketESBConstant.OTHER_PHONE_TYPE);
			numberOfPhones++;
		}
		//If there is no phone number in the file, set a default phone number as 9999999999. 
		//OMS was implemented same way.
		if (numberOfPhones == 0) {
			phone1.setPhoneNo("9999999999");
			phone1.setPhoneType(MarketESBConstant.ALTERNATE_PHONE);
			phone1.setClassId(MarketESBConstant.OTHER_PHONE_TYPE);
			numberOfPhones++;
		}
		PhoneRequest[] phoneArray = new PhoneRequest[numberOfPhones];
		phoneArray[0] = phone1;
		if (numberOfPhones > 1) {
			phoneArray[1] = phone2;
		}
		contact.setPhones(Arrays.asList(phoneArray));
	}

	/**
	 * @param incidentEvent
	 * @param createDraftReq
	 */
	private void mapLocation(IncidentEvent incidentEvent,
			CreateDraftRequest createDraftReq) {
		LocationRequest location = new LocationRequest();
		location.setCity(incidentEvent.getContact().getCity());
		location.setState(incidentEvent.getContact().getState());
		location.setStreet1(incidentEvent.getContact().getStreet1());
		location.setStreet2(incidentEvent.getContact().getStreet2());
		location.setZip(incidentEvent.getContact().getZip());
		location.setZip4(incidentEvent.getContact().getZipExt());
		createDraftReq.setServiceLocation(location);
	}

	private void mapCustomRefs(IncidentEvent incidentEvent, CreateDraftRequest createDraftReq) {
		List<CustomRef> refsToAdd = new ArrayList<CustomRef>();
		Integer slIncidentId = incidentEvent.getIncident().getIncidentID();
		if (slIncidentId != null) {
			CustomRef custRefSLIncidentID = new CustomRef();
			custRefSLIncidentID.setKey(MarketESBConstant.CUSTOM_REF_SL_INCIDENT_ID);
			custRefSLIncidentID.setValue(slIncidentId.toString());
			refsToAdd.add(custRefSLIncidentID);
		}
		String soIdentifier = incidentEvent.getIncident().getClientIncidentID();
		SimpleDateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd");
		if (soIdentifier.length() > 0) {
			CustomRef soClientID = new CustomRef();
			soClientID.setKey(MarketESBConstant.CUSTOM_REF_INCIDENT_ID);
			soClientID.setValue(soIdentifier);
			refsToAdd.add(soClientID);
		}
		if (StringUtils.isNotBlank(incidentEvent.getAssociatedIncident())) {
			CustomRef soAssociatedIncidentID = new CustomRef();
			soAssociatedIncidentID.setKey(MarketESBConstant.CUSTOM_REF_ASSOCIATED_INCIDENT);
			soAssociatedIncidentID.setValue(incidentEvent.getAssociatedIncident());
			refsToAdd.add(soAssociatedIncidentID);
		}
		if (StringUtils.isNotBlank(incidentEvent.getPartLaborIndicator())) {
			CustomRef soRetailer = new CustomRef();
			soRetailer.setKey(MarketESBConstant.CUSTOM_REF_PARTSLABORFLAG);
			soRetailer.setValue(incidentEvent.getPartLaborIndicator());
			refsToAdd.add(soRetailer);
		}
		if (StringUtils.isNotBlank(incidentEvent.getRetailer())) {
			CustomRef soRetailer = new CustomRef();
			soRetailer.setKey(MarketESBConstant.CUSTOM_REF_RETAILER);
			soRetailer.setValue(incidentEvent.getRetailer());
			refsToAdd.add(soRetailer);
		}
		if (StringUtils.isNotBlank(incidentEvent.getIncident().getWarrantyContract().getContractNumber())) {
			CustomRef soContractNumber = new CustomRef();
			soContractNumber.setKey(MarketESBConstant.CUSTOM_REF_CONTRACTNUMBER);
			soContractNumber.setValue(incidentEvent.getIncident().getWarrantyContract().getContractNumber());
			refsToAdd.add(soContractNumber);
		}
		if (incidentEvent.getIncident().getWarrantyContract().getContractDate() != null) {
			CustomRef soContractDate = new CustomRef();
			soContractDate.setKey(MarketESBConstant.CUSTOM_REF_CONTRACTDATE);
			soContractDate.setValue(dfmt.format(incidentEvent.getIncident().getWarrantyContract().getContractDate()));
			refsToAdd.add(soContractDate);
		}
		if (incidentEvent.getIncidentParts() != null && incidentEvent.getIncidentParts().size() > 0) {
			String primaryClassCode = "";
			String primaryPartNumber = "";
			for (IncidentPart part : incidentEvent.getIncidentParts()) {
				primaryClassCode = part.getClassCode();
				primaryPartNumber = part.getPartNumber();
				break;
			}
			CustomRef soClassCode = new CustomRef();
			soClassCode.setKey(MarketESBConstant.CUSTOM_REF_CLASSCODE);
			soClassCode.setValue(primaryClassCode);
			refsToAdd.add(soClassCode);
			if (StringUtils.isNotBlank(primaryPartNumber)) {
				CustomRef soPartNumber = new CustomRef();
				soPartNumber.setKey(MarketESBConstant.CUSTOM_REF_PRIMARY_PARTNUMBER);
				soPartNumber.setValue(primaryPartNumber);
				refsToAdd.add(soPartNumber);
			}
		}
		//Checks whether the contact type code denotes Accidental Damage   
		String contactTypeCode = incidentEvent.getIncident().getWarrantyContract().getContractTypeCode();	
		if (StringUtils.isNotBlank(contactTypeCode)) {
			String accidentalDamageCodes[] = MarketESBConstant.ASSURANT_ACCIDENTAL_DAMAGE_CODES;		
			for(String accidentalDamageCode : accidentalDamageCodes){			
				if(accidentalDamageCode.equals(contactTypeCode.trim())){
					CustomRef soContractTypeCode = new CustomRef();
					soContractTypeCode.setKey(MarketESBConstant.CUSTOM_REF_CONTRACTTYPE);
					soContractTypeCode.setValue(MarketESBConstant.ASSURANT_ACCIDENTAL_DAMAGE);
					refsToAdd.add(soContractTypeCode);
					break;
				}
			}
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

	private void mapMisc(IncidentEvent incidentEvent, CreateDraftRequest createDraftReq) {
		createDraftReq.setPartsSuppliedBy(MarketESBConstant.PARTS_SUPPLIED_BY_BUYER);
		createDraftReq.setRetailCancellationFee(MarketESBConstant.ZERO);
		createDraftReq.setSpendLimitParts(MarketESBConstant.ZERO);
		createDraftReq.setUserId((String) resourceBundle.getObject(MarketESBConstant.Client.ASSURANT + "_USER_ID"));
		StringBuilder sb = new StringBuilder();
		
		if (StringUtils.isNotBlank(incidentEvent.getManufacturer())) {
			sb.append("Manufacturer:");
			sb.append(incidentEvent.getManufacturer());
			sb.append(" ! ");
		}
		if (StringUtils.isNotBlank(incidentEvent.getModelNumber())) {
			sb.append("Model#:");
			sb.append(incidentEvent.getModelNumber());
			sb.append(" ! ");
		}
		if (StringUtils.isNotBlank(incidentEvent.getSerialNumber())) {
			sb.append("Serial#:");
			sb.append(incidentEvent.getSerialNumber());
			sb.append(" ! ");
		}
		createDraftReq.setSowDs(sb.toString());
	}
	
	private void mapNotes(IncidentEvent incidentEvent, CreateDraftRequest request) {
		StringBuilder sb = new StringBuilder();
		WarrantyContract contract = incidentEvent.getIncident().getWarrantyContract();
		if (StringUtils.isNotBlank(incidentEvent.getIncidentComment())) {
			sb.append("Incident Comments:");
			sb.append(incidentEvent.getIncidentComment());
			sb.append(" ! ");
		}		
		if (StringUtils.isNotBlank(incidentEvent.getWarrentyStatus())) {
			sb.append(" WarrantyStatus:" + incidentEvent.getWarrentyStatus());
			sb.append(" ! ");
		}
		if (contract.getExpirationDate() != null) {
			sb.append(" WarrantyExpirationDate:" + dfmt.format(contract.getExpirationDate()));
			sb.append(" ! ");
		}
		if (StringUtils.isNotBlank(incidentEvent.getPartsWarrentySKU())) {
			sb.append(" PartsWarrentySKU:" + incidentEvent.getPartsWarrentySKU());
			sb.append(" ! ");
		}
		if (StringUtils.isNotBlank(incidentEvent.getServiceProviderLocation())) {
			sb.append(" ServiceProviderLocation:" + incidentEvent.getServiceProviderLocation());
			sb.append(" ! ");
		}
		if (StringUtils.isNotBlank(incidentEvent.getVendorClaimNumber())) {
			sb.append(" VendorClaim#:" + incidentEvent.getVendorClaimNumber());
			sb.append(" ! ");
		}
		if (StringUtils.isNotBlank(incidentEvent.getSupportGroup())) {
			sb.append(" SupportGroup:" + incidentEvent.getSupportGroup());
			sb.append(" ! ");
		}
		if (StringUtils.isNotBlank(incidentEvent.getServicerID())) {
			sb.append(" ServicerID:" + incidentEvent.getServicerID());
			sb.append(" ! ");
		}
		if (StringUtils.isNotBlank(contract.getContractTypeCode())) {
			sb.append(" ContractTypeCode:" + contract.getContractTypeCode());
			sb.append(" ! ");
		}
		request.setProviderInstructions(sb.toString());
	}

}
