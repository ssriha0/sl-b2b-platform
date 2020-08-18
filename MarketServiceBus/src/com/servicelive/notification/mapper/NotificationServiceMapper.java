package com.servicelive.notification.mapper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.inhomeoutbound.constants.InHomeNPSConstants;
import com.newco.marketplace.buyeroutboundnotification.constatns.BuyerOutBoundConstants;
import com.servicelive.esb.integration.domain.Customer;
import com.servicelive.esb.integration.domain.InhomePart;
import com.servicelive.notification.beans.CustomerAddressData;
import com.servicelive.notification.beans.CustomerData;
import com.servicelive.notification.beans.CustomerNameData;
import com.servicelive.notification.beans.CustomerPhoneData;
import com.servicelive.notification.beans.JobCodeData;
import com.servicelive.notification.beans.MerchandiseData;
import com.servicelive.notification.beans.OrderUpdateRequest;
import com.servicelive.notification.beans.PartsDatas;
import com.servicelive.notification.beans.RequestInHomeMessageDetails;
import com.servicelive.notification.beans.SendMsgData;
import com.servicelive.notification.beans.UserDefFields;
import com.servicelive.notification.bo.INotificationBO;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * 
 * @author Infosys
 * Used for mapping In Home out-bound notification requests
 * for status changes and Closure  
 */
public class NotificationServiceMapper{

	private static final Logger LOGGER = Logger.getLogger(NotificationServiceMapper.class);
	private INotificationBO notificationBO;
	
	
	/**Description:map the request fields for closure notification
	 * @param inHomeDetails
	 * @param serviceOrder
	 * @return
	 */
	public OrderUpdateRequest mapInHomeDetails(OrderUpdateRequest inHomeDetails, ServiceOrder serviceOrder,String serialNumberFlag) {
		
		LOGGER.info("Entering mapInHomeDetails");
		try{
			inHomeDetails.setSoId(serviceOrder.getSoId());
			//mandatory fields
			//TODO Clarification on mandatory fields
			inHomeDetails.setCorrelationId(getCorrelationId());
			inHomeDetails.setOrderType(InHomeNPSConstants.IN_HOME_ORDER_TYPE);
			if(null != serviceOrder.getCustomRefValue(InHomeNPSConstants.UNIT_NUMBER)){
				inHomeDetails.setUnitNum(serviceOrder.getCustomRefValue(InHomeNPSConstants.UNIT_NUMBER));
			}else{
				inHomeDetails.setUnitNum(InHomeNPSConstants.NO_DATA);
			}
			if(null != serviceOrder.getCustomRefValue(InHomeNPSConstants.ORDER_NUMBER)){
				inHomeDetails.setOrderNum(serviceOrder.getCustomRefValue(InHomeNPSConstants.ORDER_NUMBER));
			}else{
				inHomeDetails.setOrderNum(InHomeNPSConstants.NO_DATA);
			}
			inHomeDetails.setRouteDate(formatDate(serviceOrder.getCompletedDate()));
			if(null != serviceOrder.getCustomRefValue(InHomeNPSConstants.TECH_ID)){
				inHomeDetails.setTechId(serviceOrder.getCustomRefValue(InHomeNPSConstants.TECH_ID));
			}else{
				inHomeDetails.setTechId(InHomeNPSConstants.NO_DATA);
			}
			if(null != serviceOrder.getCustomRefValue(InHomeNPSConstants.COVERAGE_TYPE_LABOR) &&
					StringUtils.isNotBlank(InHomeNPSConstants.callCodes().get(serviceOrder.getCustomRefValue(InHomeNPSConstants.COVERAGE_TYPE_LABOR)))){
				inHomeDetails.setCallCd(InHomeNPSConstants.callCodes().get(serviceOrder.getCustomRefValue(InHomeNPSConstants.COVERAGE_TYPE_LABOR)));
			}else{
				inHomeDetails.setCallCd(InHomeNPSConstants.NO_DATA);
			}
			inHomeDetails.setServiceFromTime(InHomeNPSConstants.DEFAULT_START_TIME);			
			inHomeDetails.setServiceToTime(InHomeNPSConstants.DEFAULT_END_TIME);
			if(StringUtils.isNotBlank(serviceOrder.getCustomRefValue(InHomeNPSConstants.BRAND))){
				inHomeDetails.setApplBrand(serviceOrder.getCustomRefValue(InHomeNPSConstants.BRAND));
			}else{
				inHomeDetails.setApplBrand(InHomeNPSConstants.NO_DATA);
			}
			if(StringUtils.isNotBlank(serviceOrder.getCustomRefValue(InHomeNPSConstants.MODEL))){
				inHomeDetails.setModelNum(serviceOrder.getCustomRefValue(InHomeNPSConstants.MODEL));
			}else{
				inHomeDetails.setModelNum(InHomeNPSConstants.NO_DATA);
			}
			
			if (StringUtils.isNotBlank(serialNumberFlag) && InHomeNPSConstants.ON.equalsIgnoreCase(serialNumberFlag))
			{
				//SL-21009 :Setting Serial Number for Call Close API
				if(StringUtils.isNotBlank(serviceOrder.getCustomRefValue(InHomeNPSConstants.SERIAL_NUMBER))){
					inHomeDetails.setSerialNum((serviceOrder.getCustomRefValue(InHomeNPSConstants.SERIAL_NUMBER)));
				}else{
					inHomeDetails.setSerialNum(InHomeNPSConstants.NO_DATA);
				}
			}
			
			if(StringUtils.isNotBlank(serviceOrder.getResolutionDs())){
				String techComments=replaceSpecialCharacters(serviceOrder.getResolutionDs());
				if(techComments.length()>120){
					inHomeDetails.setTechComments(techComments.substring(0, 120));
				}else{
					inHomeDetails.setTechComments(techComments);
				}
			}
			/*if(serviceOrder.getResolutionDs().length() > 120){
				inHomeDetails.setTechComments(replaceSpecialCharacters(serviceOrder.getResolutionDs()).substring(0, 120));
			}else{
				inHomeDetails.setTechComments(replaceSpecialCharacters(serviceOrder.getResolutionDs()));
			}*/
			//job code details
			inHomeDetails.setJobCodeData(mapJobCodeData(serviceOrder));			
			//parts details
			inHomeDetails.setPartsDatas(mapPartData(serviceOrder));
			
			//non-mandatory fields			
			/*inHomeDetails.setChargeCd(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setChargeStoreNum(InHomeNPSConstants.NO_DATA);*/
			inHomeDetails.setTransit(InHomeNPSConstants.INHOME_TRANSIT_VALUE);
			/*inHomeDetails.setReschdDate(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setReschdFromTime(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setReschdToTime(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setEstMinAmt(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setEstMaxAmt(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setPrimaryAmountCollected(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setSecondaryAmountCollected(InHomeNPSConstants.NO_DATA);	
			inHomeDetails.setLaborAssociateDiscount(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setLaborPromotionDiscount(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setLaborCouponDiscount(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setLaborDiscountReasonCode1(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setLaborDiscountReasonCode2(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setPartDiscount(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setPartPromotionDiscount(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setPartCouponDiscount(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setPartDiscountReasonCode1(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setPartDiscountReasonCode2(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setAgreeDiscount(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setAgreePromotionDiscount(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setAgreeCouponDiscount(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setAgreeDiscountReasonCode1(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setAgreeDiscountReasonCode2(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setPaymentMethodIndicator(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setPaymentAccountNumber(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setPaymentExpirationDate(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setAuthorizationNumber(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setSecondaryPaymentMethodIndicator(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setSecondaryPaymentAccountNumber(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setSecondaryPaymentExpirationDate(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setSecondaryAuthorizationNumber(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setLaborPpdAmt(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setRefNum(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setTaxExmptNum(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setSywrMemberId(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setSrvPrdSold(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setAltServiceLocationFlag(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setAltServiceLocationNumber(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setRepairTagBarcode(InHomeNPSConstants.NO_DATA);
			inHomeDetails.setReasonCode(InHomeNPSConstants.NO_DATA);			
			//customer details
			inHomeDetails.setUpdateCustomerFl(InHomeNPSConstants.NO_DATA);			
			if(InHomeNPSConstants.Y.equalsIgnoreCase(inHomeDetails.getUpdateCustomerFl())){
				inHomeDetails.setCustomerData(mapCustomerData(serviceOrder.getSoId()));
			}			
			//merchandise details
			inHomeDetails.setUpdateMerchandiseFl(InHomeNPSConstants.NO_DATA);
			if(InHomeNPSConstants.Y.equalsIgnoreCase(inHomeDetails.getUpdateMerchandiseFl())){
				inHomeDetails.setMerchandiseData(mapMerchandiseData(serviceOrder));
			}			
			//user fields
			inHomeDetails.setUpdateUdfFl(InHomeNPSConstants.NO_DATA);
			if(InHomeNPSConstants.Y.equalsIgnoreCase(inHomeDetails.getUpdateUdfFl())){
				inHomeDetails.setUserDefFields(mapUserDefFields());
			}			
			//send message data
			inHomeDetails.setSendMessageFl(InHomeNPSConstants.NO_DATA);
			if(InHomeNPSConstants.Y.equalsIgnoreCase(inHomeDetails.getSendMessageFl())){
				inHomeDetails.setSendMsgData(mapSendMsgData());
			}	
			
			LOGGER.info("Non-mandatory fields mapped");*/

		}catch (Exception e){
			LOGGER.error("Exception in NotificationServiceMapper.mapInHomeDetails() " + e);
		}
		LOGGER.info("Leaving mapInHomeDetails");
		return inHomeDetails;
	}
	
	/**construct jobCodeData object
	 * @param serviceOrder
	 * @return
	 */
	private JobCodeData mapJobCodeData(ServiceOrder serviceOrder) {
		
		LOGGER.info("Mapping Job fields");
		try{
			//Assuming that there will be only one task for InHome order
			JobCodeData job = new JobCodeData();
			/*BigDecimal price = new BigDecimal("0.9");
			BigDecimal invoicePrice = new BigDecimal("0.00");
			if(null != serviceOrder.getSoProviderInvoiceParts() && !serviceOrder.getSoProviderInvoiceParts().isEmpty()){
				for(SOProviderInvoiceParts parts : serviceOrder.getSoProviderInvoiceParts()){
					if(null != parts && null != parts.getFinalPrice()){
						invoicePrice = invoicePrice.add(parts.getFinalPrice());
					}
				}
			}
			if(null != serviceOrder.getFinalPriceTotal()){
				//90%(total final price + total invoice parts final price)
				price = price.multiply(serviceOrder.getFinalPriceTotal().add(invoicePrice)).setScale(2, RoundingMode.HALF_DOWN);
			}else{
				price = new BigDecimal("0.00");
			}*/
			job.setJobCalcPrice(InHomeNPSConstants.JOB_CALC_PRICE);
			if(null != serviceOrder.getCustomRefValue(InHomeNPSConstants.COVERAGE_TYPE_LABOR)){
				job.setJobCoverageCd(serviceOrder.getCustomRefValue(InHomeNPSConstants.COVERAGE_TYPE_LABOR));
			}else{
				job.setJobCoverageCd(InHomeNPSConstants.NO_DATA);
			}
			//SL-21241:Setting the jobCode based on main category
			if(null != serviceOrder.getPrimarySkillCatId()){
				String jobCode = notificationBO.getJobCodeForMainCategory(serviceOrder.getPrimarySkillCatId());
				if(StringUtils.isNotBlank(jobCode)){
					job.setJobCode(jobCode);
				}
			}
			return job;
		}
		catch(Exception e){
			LOGGER.error("Exception in NotificationServiceMapper.mapJobCodeData() due to "+e);
		}
		return null;
	}


	/**construct list of partData object
	 * @param serviceOrder
	 * @return
	 */
	private List<PartsDatas> mapPartData(ServiceOrder serviceOrder) {
		
		LOGGER.info("Mapping part fields");
		try{
			List<InhomePart> partDetails = notificationBO.getPartDetails(serviceOrder.getSoId());
			if(null != partDetails && !partDetails.isEmpty()){
				List<PartsDatas> parts = new ArrayList<PartsDatas>();
				for(InhomePart part : partDetails){
					if(null != part){
						PartsDatas newPart = new PartsDatas();
						if(null != part.getPartDivNo() && part.getPartDivNo().length() > 3){
							newPart.setPartDivNo(part.getPartDivNo().substring(part.getPartDivNo().length()-3));
						}else{
							newPart.setPartDivNo(part.getPartDivNo());
						}
						if(null != part.getPartPlsNo() && part.getPartPlsNo().length() > 3){
							newPart.setPartPlsNo(part.getPartPlsNo().substring(part.getPartPlsNo().length()-3));
						}else{
							newPart.setPartPlsNo(part.getPartPlsNo());
						}
						if(null != part.getPartPartNo() && part.getPartPartNo().length() > 24){
							newPart.setPartPartNo(part.getPartPartNo().substring(part.getPartPartNo().length()-24));
						}else{
							newPart.setPartPartNo(part.getPartPartNo());
						}
						//newPart.setPartOrderQty(part.getPartOrderQty());
						newPart.setPartInstallQty(part.getPartInstallQty());
						newPart.setPartLocation(InHomeNPSConstants.X);
						if(null != part.getPartCoverageCode() && 
								StringUtils.isNotBlank(InHomeNPSConstants.coverageCodes().get((part.getPartCoverageCode())))){
							newPart.setPartCoverageCode(InHomeNPSConstants.coverageCodes().get((part.getPartCoverageCode())));
						}else{
							newPart.setPartCoverageCode(InHomeNPSConstants.NO_DATA);
						}
						//As per the API spec partPrice need formatting ($50.25 =  format 0005025) 
						//newPart.setPartPrice(formatPrice(part.getPartPrice()));
						newPart.setPartPrice(part.getPartPrice());
						parts.add(newPart);
					}
				}
				return parts;
			}
		}catch(Exception e){
			LOGGER.error("Exception in NotificationServiceMapper.mapPartData() due to "+e);
		}
		return null;
	}
	
	//Formatting the given price($50.25 =  format 0005025)  
	/*private String formatPrice(String partPrice) {
		float price = Float.parseFloat(partPrice);
		Integer formattedPrice = (int) (price * 100);
		return StringUtils.leftPad(formattedPrice.toString(), 7, "0");
	}*/

	/**return customer data
	 * @param soId
	 * @return
	 */
	private CustomerData mapCustomerData(String soId) {
		
		LOGGER.info("Mapping Customer fields");
		try{
			Customer custDetails = notificationBO.getCustomerDetails(soId);
			if(null != custDetails){
				CustomerData customer = new CustomerData();
				//customer name data
				CustomerNameData custName = new CustomerNameData();
				custName.setFirstName(custDetails.getFirstName());
				custName.setLastName(custDetails.getLastName());
				custName.setNamePrefix(custDetails.getNamePrefix());
				custName.setNameSuffix(custDetails.getNameSuffix());
				custName.setSecondName(custDetails.getSecondName());
				
				//customer address data
				CustomerAddressData custAddress = new CustomerAddressData();
				custAddress.setAddressLineOne(custDetails.getAddressLine1());
				custAddress.setAddressLineTwo(custDetails.getAddressLine2());
				custAddress.setAptNum(custDetails.getAptNum());
				custAddress.setCity(custDetails.getCity());
				custAddress.setState(custDetails.getState());
				custAddress.setZipCode(custDetails.getZipCode());
				custAddress.setZipCodeExtension(custDetails.getZipCodeExtension());
				
				//customer phone data
				CustomerPhoneData custPhone = new CustomerPhoneData();	
				custPhone.setCustPhoneNum(custDetails.getCustPhoneNum());
				
				customer.setCustKey(custDetails.getCustKey());
				customer.setCustomerNameData(custName);
				customer.setCustomerAddressData(custAddress);
				customer.setCustomerPhoneData(custPhone);
				customer.setPreferredPrimaryCntFl(custDetails.getPreferredPrimaryCntFl());
				customer.setEmailAddress(custDetails.getEmailAddress());
				
				return customer;
			}
		}catch(Exception e){
			LOGGER.error("Exception in NotificationServiceMapper.mapCustomerData() due to "+e);
		}
		return null;
	}

	
	/**
	 * @param serviceOrder
	 * @return MerchandiseData object
	 */
	private MerchandiseData mapMerchandiseData(ServiceOrder serviceOrder) {
		
		LOGGER.info("Mapping Merchendise fields");
		try{
			MerchandiseData merchandise = new MerchandiseData();
			merchandise.setDelReceiveDate(InHomeNPSConstants.NO_DATA);
			merchandise.setItemSuffix(InHomeNPSConstants.NO_DATA);
			merchandise.setPurchaseDate(InHomeNPSConstants.NO_DATA);
			merchandise.setStoreNumber(InHomeNPSConstants.NO_DATA);
			merchandise.setUsageType(InHomeNPSConstants.NO_DATA);
			merchandise.setWhereBought(InHomeNPSConstants.NO_DATA);
			merchandise.setSerialNum(serviceOrder.getCustomRefValue(InHomeNPSConstants.SERIAL_NUMBER));
			merchandise.setModelNum(serviceOrder.getCustomRefValue(InHomeNPSConstants.MODEL));
			merchandise.setApplBrand(serviceOrder.getCustomRefValue(InHomeNPSConstants.BRAND));
			return merchandise;
			
		}catch(Exception e){
			LOGGER.error("Exception in NotificationServiceMapper.mapMerchandiseData() due to "+e);
		}
		return null;
	}
	
	/**
	 * @return UserDefFields object
	 */
	private UserDefFields mapUserDefFields() {
		
		LOGGER.info("MappingUser fields");
		UserDefFields user = new UserDefFields();
		user.setFieldIDNum(InHomeNPSConstants.NO_DATA);
		user.setFacCatCd(InHomeNPSConstants.NO_DATA);
		user.setFieldLnItmXref(InHomeNPSConstants.NO_DATA);
		user.setFieldLnItmId(InHomeNPSConstants.NO_DATA);
		user.setRuleId(InHomeNPSConstants.NO_DATA);
		user.setProfileFl(InHomeNPSConstants.NO_DATA);
		user.setFieldName(InHomeNPSConstants.NO_DATA);
		user.setFieldValue(InHomeNPSConstants.NO_DATA);
		return user;
	}

	/**
	 * @return SendMsgData object
	 */
	private SendMsgData mapSendMsgData() {
		
		LOGGER.info("Mapping Send message fields");
		SendMsgData sendMsgData = new SendMsgData();
		sendMsgData.setFromFunction(InHomeNPSConstants.NO_DATA);
		sendMsgData.setToFunction(InHomeNPSConstants.NO_DATA);
		sendMsgData.setEmpID(InHomeNPSConstants.NO_DATA);
		sendMsgData.setMessage(InHomeNPSConstants.NO_DATA);		
		return sendMsgData;
	}
	
	/**
	/** Description:Map the details for send message API
	 * @param message
	 * @param serviceOrder
	 * @return
	 */
	public RequestInHomeMessageDetails mapInHomeDetails(String message,ServiceOrder serviceOrder) {
		RequestInHomeMessageDetails requestDetails=new RequestInHomeMessageDetails();
		/*if(null!=serviceOrder.getAcceptedProviderId()){
		requestDetails.setEmpId(serviceOrder.getAcceptedProviderId().toString());
		}*/
		String empId = serviceOrder.getCustomRefValue(InHomeNPSConstants.INHOME_EMP_ID);
		requestDetails.setEmpId(empId);
		requestDetails.setFromFunction(InHomeNPSConstants.INHOME_FROM_FUNCTION);
		requestDetails.setToFunction(InHomeNPSConstants.INHOME_TO_FUNCTION);
		requestDetails.setOrderType(InHomeNPSConstants.INHOME_ORDER_TYPE);
		String unitNo = serviceOrder.getCustomRefValue(InHomeNPSConstants.INHOME_UNIT_NUMBER);
		LOGGER.info("UnitNumber CustomReference value:"+unitNo);
		String orderNum =serviceOrder.getCustomRefValue(InHomeNPSConstants.INHOME_ORDER_NUMBER);
		LOGGER.info("OrderNumber CustomReference value:"+orderNum);
		if(StringUtils.isNotBlank(unitNo)){
			requestDetails.setUnitNum(unitNo);
		}else{
			requestDetails.setUnitNum(null);
		}
		if(StringUtils.isNotBlank(orderNum)){
			requestDetails.setOrderNum(orderNum);
		}else{
			requestDetails.setOrderNum(null);
		}
		requestDetails.setMessage(addSubStatus(message,serviceOrder));
		return requestDetails;
	}

	
   private String addSubStatus(String message, ServiceOrder serviceOrder) {
		String newMessage = message;
		String toReplace=InHomeNPSConstants.SUB_STATUS_MESSAGE;
		String replaceSubStatus="";
		LOGGER.info("ServiceOrder Id:"+ serviceOrder.getSoId());
		LOGGER.info("Service order sub status:"+serviceOrder.getWfSubStatus());
		LOGGER.info("Service order wf substatus:"+serviceOrder.getWfSubStatusId());
		if(StringUtils.isNotBlank(serviceOrder.getWfSubStatus())){
			replaceSubStatus = serviceOrder.getWfSubStatus()== null? "":serviceOrder.getWfSubStatus()+" ";
			LOGGER.info("SubStaus Of order: "+ replaceSubStatus);
			newMessage = newMessage.replaceFirst(toReplace, replaceSubStatus);
			LOGGER.info("Message to be returned:"+newMessage);
		}else{
			newMessage = newMessage.replaceFirst(toReplace,"");
		}
		LOGGER.info("Message returning from addSubStatus():"+newMessage );
		return newMessage;
	}

/**append 0's to make an 8 digit correlationID
    * 
    * @return
    */
   private String getCorrelationId() {
		String seqNo = "";
		try{
			seqNo = notificationBO.getCorrelationId();
			int length = 8 - seqNo.length();
			for (int i = 0; i < length; i++) {
				seqNo = BuyerOutBoundConstants.SEQ_ZERO + seqNo;
			}
			
		}catch (Exception e){
			LOGGER.error("Exception in NotificationServiceCoordinator.getSequenceNo() "+ e.getMessage(), e);
		}
		return seqNo;
	}

	
	/**
	 * Format the date as MMddyyyy
	 * @param format
	 * @param date
	 * @return
	 */
	private String formatDate(Date date){
		DateFormat formatter = new SimpleDateFormat(InHomeNPSConstants.TIME_FORMAT);
		String formattedDate = InHomeNPSConstants.NO_DATA;
		try {
			if(null != date){
				formattedDate = formatter.format(date);
				//to get the format as MMddyyyy
				formattedDate = formattedDate.substring(0,formattedDate.indexOf(","));
				String dates[] = formattedDate.split("-");
				formattedDate = dates[1] + dates[2] + dates[0];
			}
		} catch (Exception e) {
			LOGGER.error("Exception in NotificationServiceCoordinator.formatDate() "+ e.getMessage(), e);
			formattedDate = InHomeNPSConstants.NO_DATA;
		}
		return formattedDate;
	}
	
	// Replace < &lt; > &gt;  & &amp; " &quot; ' &apos;
	private String replaceSpecialCharacters(String value) {
		if(StringUtils.isNotBlank(value)){
			value = value.replaceAll("&","&amp;");
			value = value.replaceAll("<","&lt;");
			value = value.replaceAll(">","&gt;");
			value = value.replaceAll("\"","&quot;");
			value = value.replaceAll("'", "&apos;");
		}
		return value;
	}
	 
	public INotificationBO getNotificationBO() {
		return notificationBO;
	}

	public void setNotificationBO(INotificationBO notificationBO) {
		this.notificationBO = notificationBO;
	}

}