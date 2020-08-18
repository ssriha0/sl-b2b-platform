package com.servicelive.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;

import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;
import com.newco.marketplace.webservices.dto.serviceorder.CustomRef;
import com.servicelive.esb.constant.MarketESBConstant;

public class MarketESBUtil { 

	public static final Map<String,String> MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT = new HashMap<String,String>();
	public static final Map<String,String> MAP_ESB_CUSTOMER_ORDER_TO_STAGE_CUSTOMER_ORDER_OBJECT = new HashMap<String,String>();
	public static final Map<String,String> MAP_ESB_MERCH_ORDER_TO_STAGE_MERCH_ORDER_OBJECT = new HashMap<String,String>();
	public static final Map<String,String> MAP_ESB_SCHEDULE_ORDER_TO_STAGE_SCHEDULE_ORDER_OBJECT = new HashMap<String,String>();
	
	static {
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("serviceUnitNumber","unitNumber");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("serviceOrderNumber","orderNumber");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("repairTagBarCodeNumber","repairTagBarCode");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("paymentMethodInd","paymentMethodInd");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("customerChargeAcctNumber","customerChargeAcctNumber");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("customerChargeAcctExp","customerChargeAcctExpDate");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("serviceRequested","serviceRequested");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("serviceOrderStatusCode","orderStatusCode");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("specialInstructions1","specialInstruction1");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("specialInstructions2","specialInstruction2");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("permanentInstructions","permanentInstruction");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("priorityInd","priorityInd");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("lastModifiedByEmpId","lastModifiedEmpId");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("coverageTypeLabor","coverageLaborType");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("coverageTypeParts","coveragePartsType");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("excepPartWarrExpDate","exceptionPartWarrExpDate");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("promotionInd","promotionInd");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("partWarrExpDate","partWarrExpDate");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("laborWarrExpDate","laborWarrExpDate");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("excepPartWarrExpDate2","exceptionPartExpirationDate");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("laborWarranty","laborWarrenty");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("repairInstructions","repairInstruction");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("soCreatorEmpNumber","orderCreatorEmpId");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("solicitPAInd","solicitProtectionAgreementInd");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("soCreationUnitNumber","orderCreationUnitNumber");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("procId","procId");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("contractNumber","contractNumber");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("contractExpDate","contractExpirationdate");
		MAP_ESB_ORDER_TO_STAGE_ORDER_OBJECT.put("authorizationNumber","authNumber");
		
		MAP_ESB_CUSTOMER_ORDER_TO_STAGE_CUSTOMER_ORDER_OBJECT.put("customerNumber","customerNo");
		MAP_ESB_CUSTOMER_ORDER_TO_STAGE_CUSTOMER_ORDER_OBJECT.put("customerType","customerType");
		MAP_ESB_CUSTOMER_ORDER_TO_STAGE_CUSTOMER_ORDER_OBJECT.put("customerFirstName","customerFirstName");
		MAP_ESB_CUSTOMER_ORDER_TO_STAGE_CUSTOMER_ORDER_OBJECT.put("customerLastName","customerLastName");
		MAP_ESB_CUSTOMER_ORDER_TO_STAGE_CUSTOMER_ORDER_OBJECT.put("customerPhone","customerPhone");
		MAP_ESB_CUSTOMER_ORDER_TO_STAGE_CUSTOMER_ORDER_OBJECT.put("customerAltPhone","customerAltPhone");
		MAP_ESB_CUSTOMER_ORDER_TO_STAGE_CUSTOMER_ORDER_OBJECT.put("customerPrefLang","customerPrefLanguage");
		
		MAP_ESB_MERCH_ORDER_TO_STAGE_MERCH_ORDER_OBJECT.put("code","merchandiseCode");
		MAP_ESB_MERCH_ORDER_TO_STAGE_MERCH_ORDER_OBJECT.put("description","merchandiseDescription");
		MAP_ESB_MERCH_ORDER_TO_STAGE_MERCH_ORDER_OBJECT.put("brand","brand");
		MAP_ESB_MERCH_ORDER_TO_STAGE_MERCH_ORDER_OBJECT.put("itemNumber","itemNumber");
		MAP_ESB_MERCH_ORDER_TO_STAGE_MERCH_ORDER_OBJECT.put("model","model");
		MAP_ESB_MERCH_ORDER_TO_STAGE_MERCH_ORDER_OBJECT.put("residentialOrCommercialUsage","typeOfUsage");
		MAP_ESB_MERCH_ORDER_TO_STAGE_MERCH_ORDER_OBJECT.put("serialNumber","serialNumber");
		MAP_ESB_MERCH_ORDER_TO_STAGE_MERCH_ORDER_OBJECT.put("searsSoldCode","searsSoldCode");
		MAP_ESB_MERCH_ORDER_TO_STAGE_MERCH_ORDER_OBJECT.put("searsStoreNumber","searsStoreNumber");
		MAP_ESB_MERCH_ORDER_TO_STAGE_MERCH_ORDER_OBJECT.put("systemItemSuffix","systemItemSuffix");
		MAP_ESB_MERCH_ORDER_TO_STAGE_MERCH_ORDER_OBJECT.put("division","division");
		
		MAP_ESB_SCHEDULE_ORDER_TO_STAGE_SCHEDULE_ORDER_OBJECT.put("orderTakenTime","orderTakenTime");
		MAP_ESB_SCHEDULE_ORDER_TO_STAGE_SCHEDULE_ORDER_OBJECT.put("orderTakenDate","orderTakenDate");
		MAP_ESB_SCHEDULE_ORDER_TO_STAGE_SCHEDULE_ORDER_OBJECT.put("promisedDate","promisedDate");
		MAP_ESB_SCHEDULE_ORDER_TO_STAGE_SCHEDULE_ORDER_OBJECT.put("promisedTimeFrom","promisedTimeFrom");
		MAP_ESB_SCHEDULE_ORDER_TO_STAGE_SCHEDULE_ORDER_OBJECT.put("promisedTimeTo","promisedTimeTo");
		MAP_ESB_SCHEDULE_ORDER_TO_STAGE_SCHEDULE_ORDER_OBJECT.put("originalSchedDate","originalScheduleDate");
		MAP_ESB_SCHEDULE_ORDER_TO_STAGE_SCHEDULE_ORDER_OBJECT.put("originalTimeFrom","originalScheduleTimeFrom");
		MAP_ESB_SCHEDULE_ORDER_TO_STAGE_SCHEDULE_ORDER_OBJECT.put("originalTimeTo","originalScheduleTimeTo");
			
	}
	
	
	/**
	 * Method to get the order number from Reference fields
	 * @param CreateDraftRequest
	 * @return String orderNo
	 */
	public static  String getOMSOrderNumberFromReferenceFields(CreateDraftRequest createDraftReq){
		String orderNo ="";
		CustomRef[] cusRefs = createDraftReq.getCustomRef().toArray(new CustomRef[0]);
		for(CustomRef ref : cusRefs){
			if (MarketESBConstant.CUSTOM_REF_ORDER_NUM.equals(ref.getKey())) {
				orderNo = ref.getValue();
			}
		}
		return orderNo;
	}
	
	/**
	 * Method to get the unit number from Reference fields
	 * @param CreateDraftRequest
	 * @return String unitNo
	 */
	public static  String getOMSUnitNumberFromReferenceFields(CreateDraftRequest createDraftReq){
		String unitNo ="";
		CustomRef[] cusRefs = createDraftReq.getCustomRef().toArray(new CustomRef[0]);
		for(CustomRef ref : cusRefs){
			if (MarketESBConstant.CUSTOM_REF_UNIT_NUM.equals(ref.getKey())) {
				unitNo = ref.getValue();
			}
		}
		return unitNo;
	}

	/**
	 * Method should be used by anyone who is struggling with remembering whether OrderId is 
	 * unitNumber + orderNumber or vice versa.    Use it and stop caring!
	 * @param orderNumber
	 * @param unitNumber
	 * @return orderId
	 */
	public static String constructOrderIdUtil (String orderNumber, String unitNumber) {
		return unitNumber+orderNumber;
	}
	
	public static  void mapByReflection(Map<String,String> maptoUse, Object from, Object to) {
		Set<String> keys = maptoUse.keySet();
		for(String key : keys) {
			try{
				PropertyUtils.setSimpleProperty(to, maptoUse.get(key),PropertyUtils.getSimpleProperty(from, key));
			}catch(Exception e){
				//TODO fix this 
				e.printStackTrace();
			}
		}
		
		
		//BeanUtils.getProperty(arg0, arg1)
	}
	
	// Note: workSuffix should not include a '.' character
	public static String renameInputFileToWorkFile(String fileName, String workSuffix) throws Exception {
		File fileObject = null;
    	if (fileName == null) {
    		throw new Exception("File name cannot be null.");
    	}
    	
    	String appendSuffix = workSuffix;
    	if (appendSuffix.indexOf(".") != 0) {
    		appendSuffix = "." + appendSuffix;
    	}
		
    	String workFileName = fileName + appendSuffix;
		fileObject = new File(fileName);
		File workFileObject = new File(workFileName);
		fileObject.renameTo(workFileObject); 
		
		return workFileName;
	}
}
