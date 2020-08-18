package com.servicelive.esb.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.webservices.dto.serviceorder.ContactRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CreateTaskRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CustomRef;
import com.newco.marketplace.webservices.dto.serviceorder.LocationRequest;
import com.newco.marketplace.webservices.dto.serviceorder.PhoneRequest;
import com.servicelive.esb.constant.MarketESBConstant;
import com.servicelive.esb.dto.Address;
import com.servicelive.esb.dto.HSRServiceOrder;

public class HSRCreateDraftMapper extends CreateDraftMapper implements Mapper {

	private Logger logger = Logger.getLogger(HSRCreateDraftMapper.class);

	public Object mapData(Object obj) throws Exception {
		return (Object) mapRequestData((HSRServiceOrder) obj);
	}
	
	private CreateDraftRequest mapRequestData(HSRServiceOrder so)
			throws Exception {
		logger.debug("Processing mapping for the service order :" + so);
		CreateDraftRequest createDraftReq = new CreateDraftRequest();
		if (so != null) {
			
			if(so.getIsCameInUpdateFile()!= null && so.getIsCameInUpdateFile()){
				this.mapParts(so, createDraftReq);
				this.mapStatus(so, createDraftReq);
				this.mapCustomRefs(so, createDraftReq);
				this.mapMisc(so, createDraftReq);
			}else{
				this.mapMisc(so, createDraftReq);
				this.mapLocationsAndContacts(so, createDraftReq);
				//this.mapParts(so, createDraftReq);
				this.mapTasks(so, createDraftReq);
				this.mapDates(so, createDraftReq);
				this.mapStatus(so, createDraftReq);
				this.mapCustomRefs(so, createDraftReq);
		    }
		}
		return createDraftReq;
	}
	
	private void mapStatus(HSRServiceOrder so, CreateDraftRequest createDraftReq) 
		throws Exception {

		if (so.getServiceOrderStatusCode() != null && so.getServiceOrderStatusCode().length() > 0) {
			String clientStatus = so.getServiceOrderStatusCode();
	        String slClientStatus = MarketESBConstant.clientToSLStatus.get(clientStatus);
	       	createDraftReq.setClientStatus(slClientStatus);
		}

	}

	private void mapMisc(	HSRServiceOrder so, 
							CreateDraftRequest createDraftReq) 
		throws Exception 
	{
		String providerInstructions = "";
		if (so.getSpecialInstructions1() != null && so.getSpecialInstructions1().length() > 0) {
			providerInstructions += so.getSpecialInstructions1() + "\n";
		}
		if (so.getSpecialInstructions2() != null && so.getSpecialInstructions2().length() > 0) {
			providerInstructions += so.getSpecialInstructions2() + "\n";
		}
		if (so.getPermanentInstructions() != null && so.getPermanentInstructions().length() > 0) {
			providerInstructions += so.getPermanentInstructions() + "\n";
		}
		if (so.getRepairInstructions() != null && so.getRepairInstructions().length() > 0) {
			providerInstructions += so.getRepairInstructions() + "\n";
		}
		createDraftReq.setProviderInstructions(providerInstructions);
		//createDraftReq.setPartsSuppliedBy(MarketESBConstant.PARTS_SUPPLIED_BY_BUYER);
		createDraftReq.setRetailCancellationFee(MarketESBConstant.ZERO);
		createDraftReq.setSpendLimitParts(MarketESBConstant.ZERO);
		
		String clientStatusCode = so.getServiceOrderStatusCode();
		Map<String, String> buyerSpecificFields = new HashMap<String, String>();
		if(so.getDatesAndTimes()!= null){
			buyerSpecificFields.put(MarketESBConstant.HSR_ORDER_TAKEN_TIME, so.getDatesAndTimes().getOrderTakenTime());
			buyerSpecificFields.put(MarketESBConstant.HSR_ORDER_TAKEN_DATE, so.getDatesAndTimes().getOrderTakenDate());
			buyerSpecificFields.put(MarketESBConstant.HSR_ORG_DELIVERY_DATE, so.getDatesAndTimes().getOriginalDeliveryDate());
    	}
		if(so.getProtectionAgreement()!= null){
			buyerSpecificFields.put(MarketESBConstant.HSR_PROT_AGR_TYPE, so.getProtectionAgreement().getAgreementType());
			buyerSpecificFields.put(MarketESBConstant.HSR_PROT_AGR_PLAN_TYPE, so.getProtectionAgreement().getAgreementPlanType());
			buyerSpecificFields.put(MarketESBConstant.HSR_PROT_AGR_EXP_DATE, so.getProtectionAgreement().getAgreementExpDate());
		}
		buyerSpecificFields.put(MarketESBConstant.HSR_SERVICE_REQUESTED, so.getServiceRequested());
		buyerSpecificFields.put(MarketESBConstant.HSR_CLIENT_STATUS_CODE, clientStatusCode);
		buyerSpecificFields.put(MarketESBConstant.HSR_MODIFYING_UNIT_ID, so.getModifyingUnitId());
		createDraftReq.setBuyerSpecificFields(buyerSpecificFields);

	}

	private void mapDates(HSRServiceOrder so, CreateDraftRequest createDraftReq)
			throws Exception {

	}
	
	
	
	private void mapParts(HSRServiceOrder so, CreateDraftRequest createDraftReq)
			throws Exception {

	}

	private void mapLocationsAndContacts(	HSRServiceOrder so, 
											CreateDraftRequest createDraftReq) 
		throws Exception 
	{
		LocationRequest locReq = new LocationRequest();
		PhoneRequest phone1 = new PhoneRequest();
		PhoneRequest phone2 = new PhoneRequest();
		ContactRequest serviceContactReq = new ContactRequest();
		serviceContactReq.setContactTypeId(MarketESBConstant.DEFAULT_CONTACT_TYPE_ID);
		
		//Customer information should always come from the customer object
		if (so.getCustomer() != null) {

			serviceContactReq.setHonorific(MarketESBConstant.EMPTY_STR);
			serviceContactReq.setMi(MarketESBConstant.EMPTY_STR);
			serviceContactReq.setFirstName(so.getCustomer().getCustomerFirstName());
			serviceContactReq.setLastName(so.getCustomer().getCustomerLastName());
		}
				
		int numberOfPhones = 0;
		//PHONES
		//Map home phone
		if (so.getCustomer() != null 
				&& so.getCustomer().getCustomerPhone() != null 
				&& !so.getCustomer().getCustomerPhone().trim().equals(MarketESBConstant.EMPTY_STR)) {
			phone1.setPhoneNo(so.getCustomer().getCustomerPhone());
			phone1.setPhoneType(MarketESBConstant.PRIMARY_PHONE);
			phone1.setCreatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			phone1.setClassId(MarketESBConstant.HOME_PHONE_TYPE);
			numberOfPhones++;
			
		}
		//Map work phone
		if (so.getCustomer() != null 
				&& so.getCustomer().getCustomerAltPhone() != null 
				&& !so.getCustomer().getCustomerAltPhone().trim().equals(MarketESBConstant.EMPTY_STR)) {
			
			phone2.setPhoneNo(so.getCustomer().getCustomerAltPhone());
			if (numberOfPhones == 0 ) {
				phone2.setPhoneType(MarketESBConstant.PRIMARY_PHONE);
			}
			else
				phone2.setPhoneType(MarketESBConstant.ALTERNATE_PHONE);
			
			phone2.setCreatedDate(new Timestamp(Calendar.getInstance().getTimeInMillis()));
			phone2.setClassId(MarketESBConstant.WORK_PHONE_TYPE);
			numberOfPhones++;
			
		}
		if (numberOfPhones == 0) {
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
			if (so.getRepairLocation().getBusinessAddressInd().equalsIgnoreCase("Y")) {
				locReq.setLocnClassId(MarketESBConstant.COMMERCIAL_LOCATION);
			}
			else
				locReq.setLocnClassId(MarketESBConstant.RESIDENTIAL_LOCATION);
		} 
		
		String locnNotes = "";
		if (so.getRepairLocation()!= null && so.getRepairLocation().getContactName() != null && so.getRepairLocation().getContactName().length() > 0) {
			locnNotes += so.getRepairLocation().getContactName() + "\n";
		}
		
		locReq.setLocnNotes(locnNotes);
		createDraftReq.setServiceLocation(locReq);
	}
	
	private LocationRequest mapLocation(Address address) throws Exception {
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
			locReq.setZip4(address.getZipCode().substring(
							MarketESBConstant.ZIP_SHORT, address.getZipCode().length()));
		} else {
			locReq.setZip(address.getZipCode());
		}

		return locReq;
	}

	private void mapCustomRefs(HSRServiceOrder so,	CreateDraftRequest createDraftReq) 
		throws Exception
		{
		List<CustomRef> refsToAdd = new ArrayList<CustomRef>();
		String soIdentifier = "";
		
		if (so.getServiceUnitNumber() != null && so.getServiceUnitNumber().length() > 0) {
			CustomRef unitNumber = new CustomRef();
			unitNumber.setKey(MarketESBConstant.CUSTOM_REF_UNIT_NUM_HSR);
			unitNumber.setValue(so.getServiceUnitNumber());
			soIdentifier += so.getServiceUnitNumber();
			refsToAdd.add(unitNumber);
			createDraftReq.setUnitNumber(so.getServiceUnitNumber());
		}
		
		if (so.getServiceOrderNumber() != null && so.getServiceOrderNumber().length() > 0) {
			CustomRef serviceOrderNum = new CustomRef();
			serviceOrderNum.setKey(MarketESBConstant.CUSTOM_REF_ORDER_NUM_HSR);
			serviceOrderNum.setValue(so.getServiceOrderNumber());
			soIdentifier += so.getServiceOrderNumber();
			refsToAdd.add(serviceOrderNum);
			createDraftReq.setOrderNumber(so.getServiceOrderNumber());
		}
		
		if (so.getCustomer() != null && so.getCustomer().getCustomerNumber() != null) {
			CustomRef customerNumber = new CustomRef();
			customerNumber.setKey(MarketESBConstant.CUSTOM_REF_CUSTOMER_NUMBER);
			customerNumber.setValue(so.getCustomer().getCustomerNumber());
			refsToAdd.add(customerNumber);
		}
		
		if (so.getPaymentMethodInd() != null ) {
			CustomRef paymentMethodInd = new CustomRef();
			paymentMethodInd.setKey(MarketESBConstant.CUSTOM_REF_PAYMENT_METHOD_IND);
			paymentMethodInd.setValue(so.getPaymentMethodInd());
			refsToAdd.add(paymentMethodInd);
		}
	
		// need not be in Custom ref, requirements changed
/*		if (so.getCustomerChargeAcctNumber() != null ) {
			CustomRef customerChargeAcctNumber = new CustomRef();
			customerChargeAcctNumber.setKey(MarketESBConstant.CUSTOM_REF_CHARGE_ACCT_NO);
			customerChargeAcctNumber.setValue(so.getCustomerChargeAcctNumber());
			refsToAdd.add(customerChargeAcctNumber);
		}
		
		if (so.getCustomerChargeAcctExp() != null ) {
			CustomRef customerChargeAcctExp = new CustomRef();
			customerChargeAcctExp.setKey(MarketESBConstant.CUSTOM_REF_CHARGE_ACCT_EXP);
			customerChargeAcctExp.setValue(so.getCustomerChargeAcctExp());
			refsToAdd.add(customerChargeAcctExp);
		}*/
		
		if (so.getPriorityInd() != null ) {
			CustomRef priorityInd = new CustomRef();
			priorityInd.setKey(MarketESBConstant.CUSTOM_REF_PRIORITY_IND);
			priorityInd.setValue(so.getPriorityInd());
			refsToAdd.add(priorityInd);
		}
		
		if (so.getCoverageTypeLabor() != null ) {
			CustomRef coverageTypeLabor = new CustomRef();
			coverageTypeLabor.setKey(MarketESBConstant.CUSTOM_REF_COVERAGE_TYPE_LABOR_HSR);
			coverageTypeLabor.setValue(so.getCoverageTypeLabor());
			refsToAdd.add(coverageTypeLabor);
		}
		
		if (so.getCoverageTypeParts() != null ) {
			CustomRef coverageTypeParts = new CustomRef();
			coverageTypeParts.setKey(MarketESBConstant.CUSTOM_REF_COVERAGE_TYPE_PARTS_HSR);
			coverageTypeParts.setValue(so.getCoverageTypeParts());
			refsToAdd.add(coverageTypeParts);
		}
		
		if (so.getMerchandise() != null ) {
			
			if (so.getMerchandise().getDivision() != null ) {
				CustomRef division = new CustomRef();
				division.setKey(MarketESBConstant.CUSTOM_REF_DIVISION);
				division.setValue(so.getMerchandise().getDivision());
				refsToAdd.add(division);
			}
			
			if (so.getMerchandise().getCode() != null ) {
				CustomRef code = new CustomRef();
				code.setKey(MarketESBConstant.CUSTOM_REF_MERCHANDISE_CODE);
				code.setValue(so.getMerchandise().getCode());
				refsToAdd.add(code);
			}
			
			if (so.getMerchandise().getBrand() != null ) {
				CustomRef brand = new CustomRef();
				brand.setKey(MarketESBConstant.CUSTOM_REF_BRAND);
				brand.setValue(so.getMerchandise().getBrand());
				refsToAdd.add(brand);
			}
			
			if (so.getMerchandise().getModel() != null ) {
				CustomRef model = new CustomRef();
				model.setKey(MarketESBConstant.CUSTOM_REF_MODEL);
				model.setValue(so.getMerchandise().getModel());
				refsToAdd.add(model);
			}
			
			if (so.getMerchandise().getSerialNumber() != null ) {
				CustomRef serialNumber = new CustomRef();
				serialNumber.setKey(MarketESBConstant.CUSTOM_REF_SERIAL_NUMBER);
				serialNumber.setValue(so.getMerchandise().getSerialNumber());
				refsToAdd.add(serialNumber);
			}
			
			if (so.getMerchandise().getSearsStoreNumber() != null ) {
				CustomRef storeNumber = new CustomRef();
				storeNumber.setKey(MarketESBConstant.CUSTOM_REF_STORE_NUMBER);
				storeNumber.setValue(so.getMerchandise().getSearsStoreNumber());
				refsToAdd.add(storeNumber);
			}
		}
		
		if (so.getDatesAndTimes() != null && so.getDatesAndTimes().getPurchaseDate() != null) {
			CustomRef purchaseDate = new CustomRef();
			purchaseDate.setKey(MarketESBConstant.CUSTOM_REF_PURCHASE_DATE);
			purchaseDate.setValue(so.getDatesAndTimes().getPurchaseDate());
			refsToAdd.add(purchaseDate);
		}
		
		if (so.getPromotionInd() != null) {
			CustomRef promotionInd = new CustomRef();
			promotionInd.setKey(MarketESBConstant.CUSTOM_REF_PROMOTION_IND);
			promotionInd.setValue(so.getPromotionInd());
			refsToAdd.add(promotionInd);
		}
		
		if (so.getProcId() != null) {
			CustomRef procId = new CustomRef();
			procId.setKey(MarketESBConstant.CUSTOM_REF_PROC_ID);
			procId.setValue(so.getProcId());
			refsToAdd.add(procId);
		}
		
		if (so.getContractNumber() != null) {
			CustomRef contractNumber = new CustomRef();
			contractNumber.setKey(MarketESBConstant.CUSTOM_REF_CONTRACT_NUMBER);
			contractNumber.setValue(so.getContractNumber());
			refsToAdd.add(contractNumber);
		}
		
		if (so.getContractExpDate() != null) {
			CustomRef contractExpDate = new CustomRef();
			contractExpDate.setKey(MarketESBConstant.CUSTOM_REF_CONTRACT_EXP);
			contractExpDate.setValue(so.getContractExpDate());
			refsToAdd.add(contractExpDate);
		}
		
		if (so.getAuthorizationNumber() != null) {
			CustomRef authNum = new CustomRef();
			authNum.setKey(MarketESBConstant.CUSTOM_REF_AUTH_NUMBER);
			authNum.setValue(so.getAuthorizationNumber());
			refsToAdd.add(authNum);
		}
		
		if (soIdentifier.length() > 0) {
			CustomRef soClientID = new CustomRef();
			soClientID.setKey(MarketESBConstant.CUSTOM_REF_ORDERID_STRING);
			soClientID.setValue(soIdentifier);
			refsToAdd.add(soClientID);
		}
				
		// add custom ref to hold Template name for service Order
		CustomRef templateRef = new CustomRef();
		templateRef.setKey(MarketESBConstant.CUSTOM_REF_TEMPLATE_NAME);
		// value set in Webservice while creating draft
		templateRef.setValue(StringUtils.EMPTY);
		refsToAdd.add(templateRef);
		
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

	private boolean mapTasks(HSRServiceOrder so, CreateDraftRequest createDraftReq)
			throws Exception 
	{
		List<CreateTaskRequest> tasks = new ArrayList<CreateTaskRequest>();
		CreateTaskRequest taskObj = new CreateTaskRequest();
		taskObj.setJobCode(so.getMerchandise().getCode());
		String taskName = so.getMerchandise().getCode() + "-" + so.getMerchandise().getDescription(); 
		taskObj.setTaskName(taskName);
		
		tasks.add(taskObj);
		createDraftReq.setTasks(tasks);
		return true;
	}
}
