package com.servicelive.esb.mapper;

import com.servicelive.esb.constant.HSRFieldNameConstants;
import com.servicelive.esb.dto.Address;
import com.servicelive.esb.dto.HSRMerchandise;
import com.servicelive.esb.dto.HSRProtectionAgreement;
import com.servicelive.esb.dto.HSRRepairLocation;
import com.servicelive.esb.dto.HSRServiceOrder;
import com.servicelive.esb.dto.HSRSoCustomer;
import com.servicelive.esb.dto.HSRSoDatesAndTimes;

public class HSRNewFileDataMapper extends HSRDataMapper implements Mapper{ 
	
	public Object mapData(Object dataObj){
		HSRServiceOrder hsrServiceOrder = null;
		
		if(dataObj!= null){
			String fileData  = (String) dataObj;
			String[] fields = fileData.split("\\|");
			/* the New file is supposed to have 65 fields, if it doesn't have do not process the file*/
			if(fields!= null && fields.length < 69){
				return hsrServiceOrder;
			}
			
			hsrServiceOrder = new HSRServiceOrder();
			
			hsrServiceOrder.setInputFragment(fileData);
			
			hsrServiceOrder.setServiceOrderNumber(fields[HSRFieldNameConstants.SERVICE_ORDER_NUMBER]);
			hsrServiceOrder.setServiceUnitNumber(fields[HSRFieldNameConstants.SERVICE_UNIT_NUMBER]);
			hsrServiceOrder.setRepairTagBarCodeNumber(fields[HSRFieldNameConstants.REPAIR_TAG_BAR_CODE]);
			hsrServiceOrder.setPaymentMethodInd(fields[HSRFieldNameConstants.PAYMENT_METHOD_INDICATOR]);
			hsrServiceOrder.setCustomerChargeAcctNumber(fields[HSRFieldNameConstants.CUSTOMER_CHARGE_ACCOUNT_NUMBER]);
			hsrServiceOrder.setCustomerChargeAcctExp(fields[HSRFieldNameConstants.CUSTOMER_CHARGE_ACCOUNT_EXPIRATION]);
			hsrServiceOrder.setServiceRequested(fields[HSRFieldNameConstants.SERVICE_REQUESTED]);
			hsrServiceOrder.setServiceOrderStatusCode(fields[HSRFieldNameConstants.SERVICE_ORDER_STATUS_CODE]);
			
			hsrServiceOrder.setSpecialInstructions1(fields[HSRFieldNameConstants.SPECIAL_INSTRUCTIONS1]);
			hsrServiceOrder.setSpecialInstructions2(fields[HSRFieldNameConstants.SPECIAL_INSTRUCTIONS2]);
			hsrServiceOrder.setPermanentInstructions(fields[HSRFieldNameConstants.PERMANENT_INSTRUCTIONS]);
			hsrServiceOrder.setPriorityInd(fields[HSRFieldNameConstants.PRIORITY_INDICATOR]);
			hsrServiceOrder.setLastModifiedByEmpId(fields[HSRFieldNameConstants.LAST_MODIFIED_BY_EMPLOYEE_ID]);
			hsrServiceOrder.setCoverageTypeLabor(fields[HSRFieldNameConstants.COVERAGE_TYPE_LABOR]);
			hsrServiceOrder.setCoverageTypeParts(fields[HSRFieldNameConstants.COVERAGE_TYPE_PARTS]);
			hsrServiceOrder.setExcepPartWarrExpDate(fields[HSRFieldNameConstants.EXCEPTION_PART_WARRANTY_EXPIRATION_DATE]);
			
			hsrServiceOrder.setPromotionInd(fields[HSRFieldNameConstants.PROMOTION_INDICATOR]);
			hsrServiceOrder.setPartWarrExpDate(fields[HSRFieldNameConstants.PART_WARRANTY_EXPIRATION_DATE]);
			hsrServiceOrder.setLaborWarrExpDate(fields[HSRFieldNameConstants.LABOR_WARRANTY_EXPIRAION_DATE]);
			hsrServiceOrder.setExcepPartWarrExpDate2(fields[HSRFieldNameConstants.EXCEPTION_PART_WARRANTY_EXPIRATION_DATE2]);
			hsrServiceOrder.setLaborWarranty(fields[HSRFieldNameConstants.LABOR_WARRANTY]);
			hsrServiceOrder.setRepairInstructions(fields[HSRFieldNameConstants.REPAIR_INSTRUCTIONS]);
			
			hsrServiceOrder.setSoCreatorEmpNumber(fields[HSRFieldNameConstants.EMPLOYEE_NO_CREATED_SO]);
			hsrServiceOrder.setSolicitPAInd(fields[HSRFieldNameConstants.SOLICIT_PA_INDICATOR]);
			hsrServiceOrder.setSoCreationUnitNumber(fields[HSRFieldNameConstants.SO_CREATION_UNIT_NUMBER]);
			hsrServiceOrder.setProcId(fields[HSRFieldNameConstants.PROC_ID]);
			hsrServiceOrder.setContractNumber(fields[HSRFieldNameConstants.CONTRACT_NUMBER]);
			hsrServiceOrder.setContractExpDate(fields[HSRFieldNameConstants.CONTRACT_EXP_DATE]);
			hsrServiceOrder.setAuthorizationNumber(fields[HSRFieldNameConstants.AUTHORIZATION_NUMBER]);
			
			mapSODatesAndTimes(fields,hsrServiceOrder);
			mapSOCustomer(fields,hsrServiceOrder);
			mapSORepairLocation(fields,hsrServiceOrder);
			mapSOProtectionAgreementInfo(fields,hsrServiceOrder);
			mapSOMerchandise(fields,hsrServiceOrder);
		}
		return hsrServiceOrder;
	}

	/* map Merchandise info*/
	private void mapSOMerchandise(String[] fields,
			HSRServiceOrder hsrServiceOrder) {
		HSRMerchandise merchandise = new HSRMerchandise();
		merchandise.setCode(fields[HSRFieldNameConstants.MERCHANDISE_CODE]);
		merchandise.setDescription(fields[HSRFieldNameConstants.MERCHANDISE_DESCRIPTION]);
		merchandise.setBrand(fields[HSRFieldNameConstants.BRAND]);
		merchandise.setModel(fields[HSRFieldNameConstants.MODEL]);
		merchandise.setSerialNumber(fields[HSRFieldNameConstants.SERIAL_NUMBER]);
		merchandise.setSearsSoldCode(fields[HSRFieldNameConstants.SEARS_SOLD_CODE]);
		merchandise.setSystemItemSuffix(fields[HSRFieldNameConstants.SYSTEM_ITEM_SUFFIX]);
		merchandise.setItemNumber(fields[HSRFieldNameConstants.ITEM_NUMBER]);
		merchandise.setSearsStoreNumber(fields[HSRFieldNameConstants.SEARS_STORE_NUMBER]);
		merchandise.setDivision(fields[HSRFieldNameConstants.DIVISION]);
		merchandise.setResidentialOrCommercialUsage(fields[HSRFieldNameConstants.RESIDENTIAL_OR_COMMERCIAL_USAGE]);
		
		hsrServiceOrder.setMerchandise(merchandise);
	}

	/* map protection agreement plan*/
	private void mapSOProtectionAgreementInfo(String[] fields,
			HSRServiceOrder hsrServiceOrder) {
		
		HSRProtectionAgreement protectionAgreement = new HSRProtectionAgreement();
		protectionAgreement.setAgreementNumber(fields[HSRFieldNameConstants.PA_AGGREMENT_NUMBER]);
		protectionAgreement.setAgreementType(fields[HSRFieldNameConstants.PROTECTION_AGREEMENT_TYPE]);
		protectionAgreement.setAgreementPlanType(fields[HSRFieldNameConstants.PROTECTION_AGREEMENT_PLAN_TYPE]);
		protectionAgreement.setAgreementExpDate(fields[HSRFieldNameConstants.PA_LATEST_EXPIRATION_DATE]);
		
		hsrServiceOrder.setProtectionAgreement(protectionAgreement);
	}

	/* map the repair Location  info*/
	private void mapSORepairLocation(String[] fields,
			HSRServiceOrder hsrServiceOrder) {
		HSRRepairLocation repairLocation = new HSRRepairLocation();
		Address  address = new Address();
		address.setCity(fields[HSRFieldNameConstants.CITY]);
		address.setState(fields[HSRFieldNameConstants.STATE]);
		address.setStreetAddress1(fields[HSRFieldNameConstants.REPAIR_ADDRESS1]);
		address.setStreetAddress2(fields[HSRFieldNameConstants.REPAIR_ADDRESS2]);
		address.setZipCode(fields[HSRFieldNameConstants.POSTAL_CODE]);
		repairLocation.setAddress(address);
		
		repairLocation.setBusinessAddressInd(fields[HSRFieldNameConstants.BUSINESS_ADDRESS_INDICATOR]);
		repairLocation.setContactName(fields[HSRFieldNameConstants.CONTACT_NAME]);
		repairLocation.setServiceLocationCode(fields[HSRFieldNameConstants.SERVICE_LOCATION_CODE]);
		
		hsrServiceOrder.setRepairLocation(repairLocation);
	}

	/* map the Customer info*/
	private void mapSOCustomer(String[] fields, HSRServiceOrder hsrServiceOrder) {
		
		HSRSoCustomer soCustomer = new HSRSoCustomer();
		soCustomer.setCustomerFirstName(fields[HSRFieldNameConstants.CUSTOMER_FIRST_NAME]);
		soCustomer.setCustomerNumber(fields[HSRFieldNameConstants.CUSTOMER_NUMBER]);
		soCustomer.setCustomerLastName(fields[HSRFieldNameConstants.CUSTOMER_LAST_NAME]);
		soCustomer.setCustomerPhone(fields[HSRFieldNameConstants.CUSTOMER_PHONE]);
		soCustomer.setCustomerAltPhone(fields[HSRFieldNameConstants.CUSTOMER_ALT_PHONE]);
		soCustomer.setCustomerPrefLang(fields[HSRFieldNameConstants.CUSTOMER_PREFFERED_LANGUAGE]);
		soCustomer.setCustomerType(fields[HSRFieldNameConstants.CUSTOMER_TYPE]);
		
		hsrServiceOrder.setCustomer(soCustomer);
	}

	/* map all the dates and times required for the order*/
	private void mapSODatesAndTimes(String[] fields,
			HSRServiceOrder hsrServiceOrder) {
		
		HSRSoDatesAndTimes datesAndTimes = new HSRSoDatesAndTimes();
		datesAndTimes.setOrderTakenTime(fields[HSRFieldNameConstants.ORDER_TAKEN_TIME]);
		datesAndTimes.setOrderTakenDate(fields[HSRFieldNameConstants.ORDER_TAKEN_DATE]);
		datesAndTimes.setPromisedDate(fields[HSRFieldNameConstants.PROMISED_DATE]);
		datesAndTimes.setPromisedTimeFrom(fields[HSRFieldNameConstants.PROMISED_TIME_FROM]);
		datesAndTimes.setPromisedTimeTo(fields[HSRFieldNameConstants.PROMISED_TIME_TO]);
		datesAndTimes.setOriginalSchedDate(fields[HSRFieldNameConstants.ORIGINAL_SCHEDULED_DATE]);
		datesAndTimes.setOriginalDeliveryDate(fields[HSRFieldNameConstants.ORIGINAL_DELIVERY_DATE]);
		datesAndTimes.setOriginalTimeFrom(fields[HSRFieldNameConstants.ORIGINAL_TIME_FROM]);
		datesAndTimes.setOriginalTimeTo(fields[HSRFieldNameConstants.ORIGINAL_TIME_TO]);
		datesAndTimes.setPurchaseDate(fields[HSRFieldNameConstants.PURCHASE_DATE]);
		hsrServiceOrder.setDatesAndTimes(datesAndTimes);
	}

}
