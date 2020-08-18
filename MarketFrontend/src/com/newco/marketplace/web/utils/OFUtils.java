/**
 * 
 */
package com.newco.marketplace.web.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.dto.vo.ManageTaskVO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.dto.vo.serviceorder.SOPartLaborPriceReasonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.interfaces.CreditCardConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.tokenize.TokenizeResponse;
import com.newco.marketplace.web.dto.AddonServiceRowDTO;
import com.newco.marketplace.web.dto.AddonServicesDTO;
import com.newco.marketplace.web.dto.ConditionalOfferDTO;
import com.newco.marketplace.web.dto.IncreaseSpendLimitDTO;
import com.newco.marketplace.web.dto.ReleaseServiceOrderDTO;
import com.newco.marketplace.web.dto.RescheduleDTO;
import com.newco.marketplace.web.dto.SOCancelDTO;
import com.newco.marketplace.web.dto.SOCompleteCloseDTO;
import com.newco.marketplace.web.dto.SOCompleteHSRPartsPanelDTO;
import com.newco.marketplace.web.dto.SOCompleteHSRPartsPanelServicesDTO;
import com.newco.marketplace.web.dto.SOPartsDTO;
import com.newco.marketplace.web.dto.SOPendingCancelDTO;
import com.newco.marketplace.web.dto.SOTaskDTO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;
import com.sears.os.service.ServiceConstants;
import com.servicelive.orderfulfillment.domain.LeadElement;
import com.servicelive.orderfulfillment.domain.LeadHdr;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOAdditionalPayment;
import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.SOContact;
import com.servicelive.orderfulfillment.domain.SOCounterOfferReason;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.SOPart;
import com.servicelive.orderfulfillment.domain.SOPartLaborPriceReason;
import com.servicelive.orderfulfillment.domain.SOPhone;
import com.servicelive.orderfulfillment.domain.SOPrice;
import com.servicelive.orderfulfillment.domain.SOProviderInvoiceParts;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.SOScheduleDate;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.CarrierType;
import com.servicelive.orderfulfillment.domain.type.ContactLocationType;
import com.servicelive.orderfulfillment.domain.type.ContactType;
import com.servicelive.orderfulfillment.domain.type.LocationType;
import com.servicelive.orderfulfillment.domain.type.PaymentCardType;
import com.servicelive.orderfulfillment.domain.type.PaymentType;
import com.servicelive.orderfulfillment.domain.type.PhoneClassification;
import com.servicelive.orderfulfillment.domain.type.PhoneType;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;
import com.servicelive.orderfulfillment.domain.type.SOScheduleType;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentLeadRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentRequest;
import com.servicelive.orderfulfillment.serviceinterface.vo.OrderFulfillmentResponse;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification.EntityType;

/**
 * @author Mustafa Motiwala
 *
 */
public class OFUtils {
	private static final Logger logger = Logger.getLogger(OFUtils.class);

	public static Identification createOFIdentityFromSecurityContext(SecurityContext securityContext) {
		if(null == securityContext){
			return null;
		}
		int loginRoleId = securityContext.getRoles().getRoleId();
		switch (loginRoleId) {
		case OrderConstants.BUYER_ROLEID :
		case OrderConstants.SIMPLE_BUYER_ROLEID :
			return getIdentification(securityContext, EntityType.BUYER);

		case OrderConstants.PROVIDER_ROLEID :
			return getIdentification(securityContext, EntityType.PROVIDER);

		case OrderConstants.NEWCO_ADMIN_ROLEID :
			return getIdentification(securityContext, EntityType.SLADMIN);
		}
		return null;
	}

	
	public static Identification getIdentificationIfAdopted(SecurityContext securityContext,Integer resourceId){
		Identification id = new Identification(); 
		id.setEntityType(EntityType.SLADMIN);
		id.setCompanyId((long)securityContext.getCompanyId()); 
		if(null!=resourceId){
		id.setResourceId((long)resourceId);
		}
		id.setUsername(securityContext.getUsername());
		id.setFullname(securityContext.getRoles().getFirstName()+" "+securityContext.getRoles().getLastName());
		id.setRoleId(securityContext.getRoleId());
		return id;
	}
	
	private static Identification getIdentification(SecurityContext securityContext, EntityType entityType){
		Identification id = new Identification();
		id.setEntityType(entityType);
		id.setCompanyId((long)securityContext.getCompanyId());
		id.setResourceId((long)securityContext.getVendBuyerResId());
		id.setUsername(securityContext.getUsername());
		id.setFullname(securityContext.getRoles().getFirstName()+" "+securityContext.getRoles().getLastName());
		id.setRoleId(securityContext.getRoleId());
		return id;
	}

	public static OrderFulfillmentRequest createRequestForCancel(SOCancelDTO soCancelDTO, SecurityContext securityContext){
		OrderFulfillmentRequest ofRequest = new OrderFulfillmentRequest();
		NumberFormat formatter = new DecimalFormat("#0.00");
		Identification identification = OFUtils.createOFIdentityFromSecurityContext(securityContext);
		String name = identification.getFullname();
		String resCode ="";
		Integer reasonCode = soCancelDTO.getReasonCode();
		String reason = soCancelDTO.getReason();
		String comment = soCancelDTO.getComments();
		if(null!=reasonCode){
			resCode = reasonCode.toString();
		}
		double cancelAmt = soCancelDTO.getCancelAmt();
		String tripCharge = String.valueOf(soCancelDTO.isTripChargeOverride());
		String providerApproveIndicator = String.valueOf(soCancelDTO.isProviderApproveIndicator());
		String providerPaymentCheck = String.valueOf(soCancelDTO.isProviderPaymentCheck());
		String taskLevel = soCancelDTO.getSoPricing();
		Calendar calendar = new GregorianCalendar();
		Date date = calendar.getTime();
		DateFormat formatterDate = new SimpleDateFormat("MMM d, yyyy");
		String strDate = formatterDate.format(date);
		ofRequest.setIdentification(identification);
		//ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_NAME,name);
		ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_PENDING_CANCEL_STATUS,"Cancel");
		ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_PENDING_CANCEL_STATE,"Request");
		ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_CANCEL_DATE,strDate);
		if (!StringUtils.isBlank(resCode)){
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_CANCELLATION_REASON_CODE,resCode);
		}
		if (!StringUtils.isBlank(tripCharge)){
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_TRIP_CHARGE_OVERRIDE_FEATURE,tripCharge);
		}
		if (!StringUtils.isBlank(providerApproveIndicator)){
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_CANCELLATION_PROVIDER_ACKNOWLEDGEMENT_IND,providerApproveIndicator);
		}
		if (!StringUtils.isBlank(providerPaymentCheck)){
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_PAY_PROVIDER_IND,providerPaymentCheck);
		}
		if (!StringUtils.isBlank(taskLevel)){
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_SO_PRICING_METHOD,taskLevel);
		}
		if (!StringUtils.isBlank(reason)){
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_CANCELLATION_REASON,reason.trim());
		}
		if (!StringUtils.isBlank(comment)) {
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_CANCELLATION_COMMENT, comment.trim());
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_CANCEL_COMMENT, comment.trim());
		}
		ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT,formatter.format(cancelAmt));
		// for pending cancel state.

		if(null!=soCancelDTO.getAdminUserName())
		{
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_ADMIN_USER_NAME,soCancelDTO.getAdminUserName());

		}
		if(null!=soCancelDTO.getAdminResourecId())
		{
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_ADMIN_USER_ID,soCancelDTO.getAdminResourecId());
		}
		if(null!=soCancelDTO.getUserName())
		{
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_USER_NAME,soCancelDTO.getUserName());
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_NAME,soCancelDTO.getUserName());
		}
		if(null!=soCancelDTO.getUserId())
		{
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_ID,soCancelDTO.getUserId());
		}


		HashMap<Integer, ManageTaskVO> scopeChangeTasks = soCancelDTO.getScopeChangeTasks();
		SOElementCollection collection = new SOElementCollection();
		StringBuilder skuList = new StringBuilder();
		if(null != scopeChangeTasks && !scopeChangeTasks.isEmpty()){
			for(ManageTaskVO task : scopeChangeTasks.values()){
				SOTask soTask = new SOTask();
				soTask.setTaskName(task.getTaskName());
				soTask.setTaskComments(task.getTaskComments());
				soTask.setServiceTypeId(task.getServiceTypeTemplateId().intValue());
				soTask.setExternalSku(task.getSku());
				soTask.setFinalPrice(task.getFinalPrice());
				soTask.setPrice(task.getFinalPrice());
				if(null != task.getSkillNodeId())
					soTask.setSkillNodeId(task.getSkillNodeId().intValue());
				soTask.setTaskStatus(OrderConstants.ACTIVE_TASK);
				soTask.setTaskType(OrderConstants.PRIMARY_TASK);
				soTask.setAutoInjectedInd(0);
				 if(null!=task.getSkuId() && !("").equals(task.getSkuId()))
					{
					 soTask.setSkuId(Integer.parseInt(task.getSkuId())); 
					}

				String sku = "";
				if (null != task.getSku()){
					if ("0".equals(task.getSku())){
						sku  = "NA";
					}else{
						sku = task.getSku();
					}
				}	    		
				skuList.append(sku).append(",");
				// TO-D0 : Include task type
				collection.addElement(soTask);
			}
		}
		ofRequest.setElement(collection);
		if(null != soCancelDTO.getScopeChangeCancelReasonCode()){
			ofRequest.addMiscParameter("REASON_CODE_ID",soCancelDTO.getScopeChangeCancelReasonCode().toString());
			ofRequest.addMiscParameter("SO_MAX_PRICE", formatter.format(cancelAmt));
			skuList.deleteCharAt(skuList.lastIndexOf(","));
			ofRequest.addMiscParameter("SKU_LIST", skuList.toString());
		}
		if(null != soCancelDTO.getScopeChangeCancelReason()){
			ofRequest.addMiscParameter("REASON_CODE", soCancelDTO.getScopeChangeCancelReason().trim());
		}
		if(null != soCancelDTO.getScopeChangeCancelReason()){
			ofRequest.addMiscParameter("REASON_COMMENTS", soCancelDTO.getScopeChangeCancelComments().trim());
		}
		return ofRequest;
	}

	public static OrderFulfillmentRequest createRequestForPendingCancel(SOPendingCancelDTO  soPendingCancelDto, SecurityContext securityContext){
		OrderFulfillmentRequest ofRequest = new OrderFulfillmentRequest();
		ofRequest.setIdentification(OFUtils.createOFIdentityFromSecurityContext(securityContext));

		if (soPendingCancelDto.getCancelComment()!=null) {
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_CANCELLATION_COMMENT, soPendingCancelDto.getCancelComment());
		}
		if (soPendingCancelDto.getCancelAmount() != 0) {
			NumberFormat formatter = new DecimalFormat("#0.00");
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT,formatter.format(soPendingCancelDto.getCancelAmount()));
		}
		if(soPendingCancelDto.getAction().equalsIgnoreCase(OrderfulfillmentConstants.BUYER_DISAGREE_AMOUNT))
		{
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_PENDING_CANCEL_STATE,OrderfulfillmentConstants.PENDING_CANCEL_RESPONSE);
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_PENDING_CANCEL_STATUS,OrderfulfillmentConstants.PENDING_CANCEL_RESPONSE);
		}
		Calendar calendar = new GregorianCalendar();
		Date date = calendar.getTime();
		DateFormat formatterDate = new SimpleDateFormat("MMM d, yyyy");
		String strDate = formatterDate.format(date);
		ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_CANCEL_DATE,strDate);
		if(null!=soPendingCancelDto.getAdminUserName())
		{
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_ADMIN_USER_NAME,soPendingCancelDto.getAdminUserName());
		}
		if(null!=soPendingCancelDto.getAdminResourecId())
		{
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_ADMIN_USER_ID,soPendingCancelDto.getAdminResourecId());
		}
		if(null!=soPendingCancelDto.getUserName())
		{
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_NAME,soPendingCancelDto.getUserName());
		}
		if(null!=soPendingCancelDto.getUserId())
		{
			ofRequest.addMiscParameter(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_ID,soPendingCancelDto.getUserId());
		}

		return ofRequest;
	}


	public static OrderFulfillmentRequest createRequestForCancelPendingTransaction(SecurityContext securityContext){
		OrderFulfillmentRequest ofRequest = new OrderFulfillmentRequest();
		ofRequest.setIdentification(OFUtils.createOFIdentityFromSecurityContext(securityContext));
		return ofRequest;
	}

	public static ProcessResponse mapProcessResponse(OrderFulfillmentResponse response){
		return mapProcessResponse(response, false);
	}

	public static ProcessResponse mapProcessResponse(OrderFulfillmentResponse response, boolean isSOInEditMode){
		ProcessResponse returnVal = new ProcessResponse();
		if(!response.isSignalAvailable() && isSOInEditMode){
			returnVal.setCode(ServiceConstants.USER_ERROR_RC);
			returnVal.setMessage(OrderConstants.ORDER_BEING_EDITED);
		}
		else if(!response.getErrors().isEmpty()){
			returnVal.setCode(ServiceConstants.USER_ERROR_RC);
			returnVal.setMessage(response.getErrorMessage());
		}else{
			returnVal.setCode(ServiceConstants.VALID_RC);
			returnVal.setMessage("Request Successfully executed.");
		}
		return returnVal;
	}
	public static ProcessResponse mapCancelProcessResponse(OrderFulfillmentResponse response,SOCancelDTO soCancelDto){
		ProcessResponse returnVal = new ProcessResponse();
		String message = null;
		int wfStateId =soCancelDto.getWfStateId();
		if(!response.getErrors().isEmpty()){
			returnVal.setCode(ServiceConstants.USER_ERROR_RC);
			returnVal.setMessage(response.getErrorMessage());
		}else{
			returnVal.setCode(ServiceConstants.VALID_RC);
			if(wfStateId==OrderConstants.DRAFT_STATUS){
				message = OrderConstants.CANCELSO_DELETE_SUCCESS;
			}else if(wfStateId==OrderConstants.ROUTED_STATUS||wfStateId==OrderConstants.EXPIRED_STATUS){
				message = OrderConstants.CANCELSO_VOID_SUCCESS;
			}else if(soCancelDto.isNonFunded()){
				message = OrderConstants.CANCELSO_SUCCESS;
			}else {
				message = getCancelMessage(soCancelDto);
			}
			returnVal.setMessage(message);
		}

		return returnVal;
	}

	private static String getCancelMessage(SOCancelDTO soCancelDto){
		String message = null;
		NumberFormat formatter = new DecimalFormat("#0.00");
		if(soCancelDto.isTripChargeOverride()){
			if(soCancelDto.isProviderPaymentCheck()){
				if(soCancelDto.getSoPricing()!=null && soCancelDto.getSoPricing().equals("SO_LEVEL")){
					message = OrderConstants.PENDING_CANCELSO_SUCCESS1 + formatter.format(soCancelDto.getCancelAmt())+ OrderConstants.PENDING_CANCELSO_SUCCESS2 ;
				}else{
					if(soCancelDto.isProviderApproveIndicator()){
						message = OrderConstants.CANCELSO_SUCCESS;
						message = message+ OrderConstants.CANCELSO_AMOUNT_SUCCESS + formatter.format(soCancelDto.getCancelAmt());
					}else{
						message = OrderConstants.PENDING_CANCELSO_SUCCESS1 + formatter.format(soCancelDto.getCancelAmt())+ OrderConstants.PENDING_CANCELSO_SUCCESS2 ;
					}
				}
			}else{
				message = OrderConstants.CANCELSO_SUCCESS;
			}
		}else if(soCancelDto.getWfStateId()!=OrderConstants.ACCEPTED_STATUS){
			if(soCancelDto.getSoPricing()!=null && soCancelDto.getSoPricing().equals("SO_LEVEL")){
				message = OrderConstants.PENDING_CANCELSO_SUCCESS1 + formatter.format(soCancelDto.getCancelAmt())+ OrderConstants.PENDING_CANCELSO_SUCCESS2 ;

			}else{
				if(soCancelDto.isProviderApproveIndicator()){
					message = OrderConstants.CANCELSO_SUCCESS;
					message = message+ OrderConstants.CANCELSO_AMOUNT_SUCCESS + formatter.format(soCancelDto.getCancelAmt());
				}else{
					message = OrderConstants.PENDING_CANCELSO_SUCCESS1 + formatter.format(soCancelDto.getCancelAmt())+ OrderConstants.PENDING_CANCELSO_SUCCESS2 ;
				}

			}

		}else{
			if(soCancelDto.isWithin24Hours()){
				message = OrderConstants.CANCELSO_SUCCESS;
				message = message+ OrderConstants.CANCELSO_AMOUNT_SUCCESS + formatter.format(soCancelDto.getCancelAmt());
			}else{
				message = OrderConstants.CANCELSO_SUCCESS;
			}
		}
		return message;
	}
	public static List<RoutedProvider> createConditionalOffer(ConditionalOfferDTO conditionalOfferDTO) {
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");

		Date startDate = null;
		Date endDate = null;
		BigDecimal increaseLimit = null;
		Date expirationDate = null;
		try{
			expirationDate = formatDate.parse(conditionalOfferDTO.getConditionalExpirationDate());
		}catch(ParseException e){
			//This should never have happened.
		}
		if(BooleanUtils.isTrue(conditionalOfferDTO.getRescheduleServiceDate())){ //Null safe operation.
			String changeEndDate = conditionalOfferDTO.getConditionalChangeDate2();
			String changeStartDate = conditionalOfferDTO.getConditionalChangeDate1();
			try {
				if(StringUtils.isNotBlank(changeStartDate)){
					startDate = formatDate.parse(changeStartDate);
				}            
				if(StringUtils.isNotBlank(changeEndDate)) {
					endDate = formatDate.parse(changeEndDate);
				}

			} catch (ParseException e) {
				//This should never have happened.
			}
		}   
		if(BooleanUtils.isTrue(conditionalOfferDTO.getIncreaseMaxPrice())){//Null safe operation.
			increaseLimit = new BigDecimal(conditionalOfferDTO.getConditionalSpendLimit());
		}
		List<Integer> counterOfferedResourceIds=conditionalOfferDTO.getCounterOfferedResources();
		if(counterOfferedResourceIds==null||counterOfferedResourceIds.isEmpty()){
			counterOfferedResourceIds.add(conditionalOfferDTO.getResourceId());
		}
		List<RoutedProvider> returnVal= new ArrayList<RoutedProvider>();
		for(Integer firmResourceId:counterOfferedResourceIds){
			RoutedProvider routedProvider = createConditionalOffer(firmResourceId,
					conditionalOfferDTO.getVendorOrBuyerID(),
					expirationDate,
					conditionalOfferDTO.getConditionalExpirationTime(),
					startDate, conditionalOfferDTO.getConditionalStartTime(),
					endDate, conditionalOfferDTO.getConditionalEndTime(),
					null, null, null, "",
					increaseLimit
					);

			for(Integer reason:conditionalOfferDTO.getSelectedCounterOfferReasonsList()){
				SOCounterOfferReason counterOfferReason = new SOCounterOfferReason();
				counterOfferReason.setResponseReasonId((long)reason);
				counterOfferReason.setProviderResponse(routedProvider.getProviderResponse());
				counterOfferReason.setProviderResponseReasonId(routedProvider.getProviderRespReasonId());
				counterOfferReason.setRoutedProvider(routedProvider);
				routedProvider.getCounterOffers().add(counterOfferReason);
			}
			returnVal.add(routedProvider);
		}
		return returnVal;
	}

	public static RoutedProvider createConditionalOffer(Integer resourceId, Integer vendorId, Date expirationDate, String conditionalExpirationTime,
			Date startDate, String startTime, Date endDate, String endTime,
			BigDecimal totalLabor, BigDecimal totalHours, BigDecimal partsMaterials,
			String comment, BigDecimal totalLaborParts) {

		SimpleDateFormat formatDateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		RoutedProvider returnVal = new RoutedProvider();
		returnVal.setProviderResourceId((long)resourceId);
		returnVal.setVendorId(vendorId);
		returnVal.setProviderResponse(ProviderResponseType.CONDITIONAL_OFFER);

		try{
			returnVal.setOfferExpirationDate( formatDateTime.parse(formatDate.format(expirationDate) + " " + conditionalExpirationTime));
		}catch(ParseException e){
			//Should not happen.
		}

		if(startDate != null){
			returnVal.setSchedule(new SOScheduleDate());
			returnVal.getSchedule().setServiceDate1(startDate);
			returnVal.getSchedule().setServiceTimeStart(startTime);
			//should not be setting end date without start date
			if(endDate != null){
				returnVal.getSchedule().setServiceDate2(endDate);
				returnVal.getSchedule().setServiceTimeEnd(endTime);
			}
		}
		if(null != totalHours) returnVal.setTotalHours(totalHours.doubleValue());
		if(null != totalLabor) returnVal.setTotalLabor(totalLabor);
		if(null != partsMaterials) returnVal.setPartsMaterials(partsMaterials);
		if(null != totalLaborParts) returnVal.setIncreaseSpendLimit(totalLaborParts);
		returnVal.setProviderRespComment(comment);
		if (startDate != null && totalLaborParts != null)
			returnVal.setProviderRespReasonId(OrderConstants.RESCHEDULE_SERVICE_DATE_AND_SPEND_LIMIT);
		else if (totalLaborParts != null)
			returnVal.setProviderRespReasonId(OrderConstants.SPEND_LIMIT);
		else if (startDate != null)
			returnVal.setProviderRespReasonId(OrderConstants.RESCHEDULE_SERVICE_DATE);

		return returnVal;
	}

	public static RoutedProvider createReleaseElement(Long resourceId, String providerComment,Integer reasonCode){
		RoutedProvider routedProvider = new RoutedProvider();
		routedProvider.setProviderResourceId(resourceId);
		routedProvider.setProviderResponse(ProviderResponseType.RELEASED);
		if(reasonCode.equals(-2)){
			routedProvider.setProviderRespReasonId(null);
		}else{
		routedProvider.setProviderRespReasonId(reasonCode);
		}
		routedProvider.setProviderRespComment(providerComment);
		Timestamp providerRespDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
		routedProvider.setProviderRespDate(providerRespDate);

		return routedProvider;
	}
	
	public static SOElementCollection createSOElementForProviderList(List<Integer> providerList,ReleaseServiceOrderDTO release){
		SOElementCollection soElementCollection = new SOElementCollection();
		for(Integer providerId :providerList){
			Long id = (long)providerId;
			RoutedProvider routedProvider = new RoutedProvider();
			routedProvider.setProviderResourceId(id);
			routedProvider.setProviderResponse(ProviderResponseType.RELEASED_BY_FIRM);
			if(release.getReleaseReasonCode().equals(-2)){
				routedProvider.setProviderRespReasonId(null);
			}else{
			routedProvider.setProviderRespReasonId(release.getReleaseReasonCode());
			}
			routedProvider.setProviderRespComment(release.getComment());
			Timestamp providerRespDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
			routedProvider.setProviderRespDate(providerRespDate);
			soElementCollection.addElement(routedProvider);
		}
		return soElementCollection;
	}

	public static List<SOAddon> mapAddOns(SOCompleteCloseDTO soCompDto) {

		List<SOAddon> soAddOns = new ArrayList<SOAddon>();
		AddonServicesDTO addonDto = soCompDto.getAddonServicesDTO();

		List<AddonServiceRowDTO> addOns = addonDto.getAddonServicesList();

		for (AddonServiceRowDTO addOn : addOns) {
			SOAddon soAddOn = new SOAddon();
			soAddOn.setSku(addOn.getSku());
			soAddOn.setAddonId(addOn.getAddonId());
			soAddOn.setQuantity(addOn.getQuantity());

			if (addOn.getEndCustomerCharge() != null) {
				soAddOn.setRetailPrice(BigDecimal.valueOf(addOn.getEndCustomerCharge()));
			}
			if (addOn.getDescription() != null) {
				soAddOn.setDescription(addOn.getDescription());
			}
			if (addOn.getMargin() != null) {
				soAddOn.setMargin(addOn.getMargin());
			}
			soAddOns.add(soAddOn);
		}
		if(soCompDto.isTaskLevelPricing() && soCompDto.getPermitTaskList()!=null && soCompDto.getPermitTaskList().size()>0){
			for (SOTaskDTO permitTask : soCompDto.getPermitTaskList()) {
				if(null!=permitTask.getFinalPrice() && null!=permitTask.getSellingPrice() && permitTask.getSellingPrice()<permitTask.getFinalPrice()){
					double amountDiff = permitTask.getFinalPrice()- permitTask.getSellingPrice();
					SOAddon soAddOn = new SOAddon();
					soAddOn.setSku("99888");
					soAddOn.setQuantity(1);
					soAddOn.setMargin(0.0);
					soAddOn.setAutoGenInd(true);
					soAddOn.setRetailPrice(BigDecimal.valueOf(amountDiff));
					soAddOn.setDescription(permitTask.getPermitTaskDesc());
					soAddOn.setCoverage("CC");
					soAddOns.add(soAddOn);
				}
			}
		}

		return soAddOns;
	}

	public static List<SOProviderInvoiceParts> mapPartsInvoice(SOCompleteCloseDTO soCompDto, ServiceOrder serviceOrder) {

		List<SOProviderInvoiceParts> soProviderInvoiceParts = new ArrayList<SOProviderInvoiceParts>();  
		List<ProviderInvoicePartsVO> invoicePartsList = soCompDto.getInvoiceParts();
		for (ProviderInvoicePartsVO providerInvoicePartsVO : invoicePartsList) {
			SOProviderInvoiceParts soProviderInvoicePart = new SOProviderInvoiceParts();
			if(providerInvoicePartsVO != null){
					soProviderInvoicePart.setServiceOrder(serviceOrder);
					soProviderInvoicePart.setPartCoverage(providerInvoicePartsVO.getPartCoverage());
					soProviderInvoicePart.setSource(providerInvoicePartsVO.getSource());
					soProviderInvoicePart.setPartNo(providerInvoicePartsVO.getPartNo());
					soProviderInvoicePart.setDivisionNumber(providerInvoicePartsVO.getDivisionNumber());
					soProviderInvoicePart.setSourceNumber(providerInvoicePartsVO.getSourceNumber());
					
					if (StringUtils.isBlank(providerInvoicePartsVO.getPartsUrl())) {
						soProviderInvoicePart.setPartsUrl(null);
					} else {
						soProviderInvoicePart.setPartsUrl(providerInvoicePartsVO.getPartsUrl());
					}
					//This primary key is retained to set invoice child related items such as location and document objects
					if (null!=providerInvoicePartsVO.getInvoicePartId()) {
						soProviderInvoicePart.setPartsInvoiceId(providerInvoicePartsVO.getInvoicePartId());
					}
					soProviderInvoicePart.setPartStatus(providerInvoicePartsVO.getPartStatus());
					soProviderInvoicePart.setCategory(providerInvoicePartsVO.getCategory());
				    soProviderInvoicePart.setDescription(providerInvoicePartsVO.getDescription());
					soProviderInvoicePart.setInvoiceNo(providerInvoicePartsVO.getInvoiceNo());
					soProviderInvoicePart.setUnitCost(providerInvoicePartsVO.getUnitCost()); 
					soProviderInvoicePart.setRetailPrice(providerInvoicePartsVO.getRetailPrice());
					soProviderInvoicePart.setQty(providerInvoicePartsVO.getQty());
					soProviderInvoicePart.setEstProviderPartsPpayment(providerInvoicePartsVO.getEstProviderPartsPayment());
					soProviderInvoicePart.setFinalPrice(providerInvoicePartsVO.getFinalPrice());
					soProviderInvoicePart.setSourceNonSears(providerInvoicePartsVO.getNonSearsSource());
					soProviderInvoicePart.setRetailCostToInventory(providerInvoicePartsVO.getRetailCostToInventory());
					soProviderInvoicePart.setRetailReimbursement(providerInvoicePartsVO.getRetailReimbursement());
					soProviderInvoicePart.setRetailPriceSLGrossUp(providerInvoicePartsVO.getRetailPriceSLGrossUp());
					soProviderInvoicePart.setRetailSLGrossUp(providerInvoicePartsVO.getRetailSLGrossUp());
					soProviderInvoiceParts.add(soProviderInvoicePart);
			}
		}
		return soProviderInvoiceParts;
	}    

	public static List<SOPartLaborPriceReason> mapPartLaborPriceReason(SOCompleteCloseDTO soCompDto, ServiceOrder serviceOrder) {

		List<SOPartLaborPriceReason> soPartLaborPriceReason = new ArrayList<SOPartLaborPriceReason>();
		List<SOPartLaborPriceReasonVO> reasonList = soCompDto.getSoPartLaborPriceReason();
		for (SOPartLaborPriceReasonVO soPartLaborPriceReasonVO : reasonList) {
			SOPartLaborPriceReason soReason = new SOPartLaborPriceReason();
			soReason.setReasonId(soPartLaborPriceReasonVO.getReasonId());
			soReason.setServiceOrder(serviceOrder);
			soReason.setPriceType(soPartLaborPriceReasonVO.getPriceType());
			soReason.setReasonCodeId(soPartLaborPriceReasonVO.getReasonCodeId());
			soReason.setReasonComments(soPartLaborPriceReasonVO.getReasonComments());
			soPartLaborPriceReason.add(soReason);
		}
		return soPartLaborPriceReason;
	}


	public static List<SOCustomReference> mapCustomRefs(List<BuyerReferenceVO> buyerRefs) {
		List<SOCustomReference> customRefs = new ArrayList<SOCustomReference>();

		for (BuyerReferenceVO buyerRef : buyerRefs) {
			SOCustomReference customRef = new SOCustomReference();
			customRef.setBuyerRefTypeId(buyerRef.getBuyerRefTypeId());
			customRef.setBuyerRefValue(buyerRef.getReferenceValue());
			customRefs.add(customRef);
		}

		return customRefs;
	}

	public static List<SOPart> mapParts(ArrayList<SOPartsDTO> partList) {
		List<SOPart> parts = new ArrayList<SOPart>();

		for (SOPartsDTO partListPart : partList) {
			SOPart part = new SOPart();
			part.setPartId(partListPart.getPartId());
			if (partListPart.getCoreReturnCarrierId() != -1) {
				part.setCoreReturnCarrierId(partListPart.getCoreReturnCarrierId());
				part.setReturnCarrierId(CarrierType.fromId(partListPart.getCoreReturnCarrierId()));
			}
			part.setCoreReturnTrackNo(partListPart.getCoreReturnTrackingNumber());
			part.setReturnTrackNo(partListPart.getCoreReturnTrackingNumber());
			parts.add(part);
		}

		return parts;
	}

	public static SOSchedule mapRescheduleRequest(RescheduleDTO reschedule) throws ParseException{
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		SOSchedule returnVal = new SOSchedule();
		returnVal.setServiceDate1(dateFormatter.parse(reschedule.getNewStartDate()));
		returnVal.setServiceTimeStart(reschedule.getNewStartTime());
		returnVal.setReason(reschedule.getReasonCode());
		if(StringUtils.equals("1", reschedule.getRangeOfDates())){
			returnVal.setServiceDateTypeId(SOScheduleType.DATERANGE);
			returnVal.setServiceDate2(dateFormatter.parse(reschedule.getNewEndDate()));
			returnVal.setServiceTimeEnd(reschedule.getNewEndTime());
		}else{
			returnVal.setServiceDateTypeId(SOScheduleType.SINGLEDAY);
		}
		return returnVal;
	}

	public static OrderFulfillmentRequest mapSONote(ServiceOrderNote source, boolean isEmailTobeSent){
		OrderFulfillmentRequest request = new OrderFulfillmentRequest();
		//request.addMiscParameter(OrderfulfillmentConstants.PVKEY_SEND_EMAIL, String.valueOf(isEmailTobeSent));

		SONote note = new SONote();
		note.setCreatedByName(source.getCreatedByName());
		note.setCreatedDate(source.getCreatedDate());
		note.setDeletedDate(source.getDeletedDate());
		if(null != source.getEntityId())
			note.setEntityId(source.getEntityId().longValue());
		note.setModifiedBy(source.getModifiedBy());
		note.setModifiedDate(source.getModifiedDate());
		note.setNote(source.getNote());
		note.setNoteTypeId(source.getNoteTypeId());
		if(source.getPrivateId()>0) note.setPrivate(true);
		else note.setPrivate(false);
		note.setRoleId(source.getRoleId());
		note.setSubject(source.getSubject());
		note.setSendEmail(isEmailTobeSent);
		
		//SL-19050
		//Setting read_ind for so notes
		note.setReadInd(source.getReadInd());


		request.setElement(note);

		return request;
	}

	//public static

	public static SOContact mapContact(Contact src){
		SOContact returnVal = new SOContact();
		SOPhone phone = new SOPhone();
		/*Setup the primary object.*/
		returnVal.setBusinessName(src.getBusinessName());
		returnVal.setContactTypeId(ContactType.PRIMARY);
		returnVal.setEmail(src.getEmail());
		returnVal.setFirstName(src.getFirstName());
		returnVal.setLastName(src.getLastName());
		returnVal.setHonorific(src.getHonorific());
		returnVal.setMi(src.getMi());
		returnVal.setSuffix(src.getSuffix());
		returnVal.setEntityId(src.getEntityId());
		returnVal.setEntityType(com.servicelive.orderfulfillment.domain.type.EntityType.PROVIDER);

		/*Set Phone info:*/
		Set<SOPhone> phones = new HashSet<SOPhone>();
		if(StringUtils.isNotBlank( src.getPhoneNo())){
			phone.setPhoneClass(PhoneClassification.WORK);
			phone.setPhoneType(PhoneType.PRIMARY);
			phone.setPhoneNo(src.getPhoneNo());
			phones.add(phone);
		}
		if(StringUtils.isNotBlank(src.getFaxNo())){
			phone = new SOPhone();
			phone.setPhoneClass(PhoneClassification.FAX);
			phone.setPhoneType(PhoneType.FAX);
			phone.setPhoneNo(src.getFaxNo());
			phones.add(phone);
		}
		if(StringUtils.isNotBlank(src.getCellNo())){
			phone = new SOPhone();
			phone.setPhoneClass(PhoneClassification.MOBILE);
			phone.setPhoneType(PhoneType.PRIMARY);
			phone.setPhoneNo(src.getCellNo());
			phones.add(phone);
		}
		returnVal.setPhones(phones);
		returnVal.addContactLocation(ContactLocationType.PROVIDER);

		return returnVal;
	}

	public static SOLocation mapLocation(Contact src) {
		SOLocation location = new SOLocation();
		location.setAptNumber(src.getAptNo());
		location.setCity(src.getCity());
		location.setStreet1(src.getStreet_1());
		location.setStreet2(src.getStreet_2());
		location.setState(src.getStateCd());
		location.setCountry(src.getCountry());
		location.setZip(src.getZip());
		location.setSoLocationTypeId(LocationType.PROVIDER);
		return location;
	}

	public static OrderFulfillmentRequest newPostOrderRequest(SecurityContext securityContext){
		OrderFulfillmentRequest returnVal = new OrderFulfillmentRequest();
		returnVal.setIdentification(createOFIdentityFromSecurityContext(securityContext));
		returnVal.setElement(new SOElementCollection());
		return returnVal;
	}

	public static OrderFulfillmentRequest createAddFundsRequest(IncreaseSpendLimitDTO soIncSpendLimitDto, SecurityContext securityContext, String modifiedByName) {
		OrderFulfillmentRequest returnVal = new OrderFulfillmentRequest();
		returnVal.setIdentification(createOFIdentityFromSecurityContext(securityContext));
		ServiceOrder tmpServiceOrder = new ServiceOrder();
		returnVal.setElement(tmpServiceOrder);
		BigDecimal spendLimitLabor = null; 
		BigDecimal spendLimitParts = null;
        String modifiedUserId = "";
		if(null != soIncSpendLimitDto.getTotalSpendLimit()){
			spendLimitLabor =  new BigDecimal(soIncSpendLimitDto.getTotalSpendLimit()).setScale(2, RoundingMode.HALF_EVEN);
			tmpServiceOrder.setSpendLimitLabor(spendLimitLabor);
		}
		if(null != soIncSpendLimitDto.getTotalSpendLimitParts()){
			spendLimitParts = new BigDecimal(soIncSpendLimitDto.getTotalSpendLimitParts()).setScale(2, RoundingMode.HALF_EVEN);
			tmpServiceOrder.setSpendLimitParts(spendLimitParts);
		}
		if(StringUtils.isNotBlank(soIncSpendLimitDto.getIncreasedSpendLimitReason())){
			if(StringUtils.equals(soIncSpendLimitDto.getIncreasedSpendLimitReason(),OrderfulfillmentConstants.OTHER)){
				tmpServiceOrder.setSpendLimitIncrComment(soIncSpendLimitDto.getIncreasedSpendLimitComment());
				returnVal.addMiscParameter("spendLimitReason","Reason                        ====>"+soIncSpendLimitDto.getIncreasedSpendLimitComment());
				returnVal.addMiscParameter(OrderfulfillmentConstants.PVKEY_SPEND_LIMIT_REASON,"");
				//SL-18007
				returnVal.addMiscParameter("spendLmtReasonForPriceHistory", "Other - "+soIncSpendLimitDto.getIncreasedSpendLimitComment());
			}
			else{
				tmpServiceOrder.setSpendLimitIncrComment(soIncSpendLimitDto.getIncreasedSpendLimitReason());
				returnVal.addMiscParameter("spendLimitReason","Reason                        ====>"+soIncSpendLimitDto.getIncreasedSpendLimitReason());
				returnVal.addMiscParameter(OrderfulfillmentConstants.PVKEY_SPEND_LIMIT_REASON,soIncSpendLimitDto.getIncreasedSpendLimitReasonId());
				//SL-18007
				returnVal.addMiscParameter("spendLmtReasonForPriceHistory", "Reason - "+soIncSpendLimitDto.getIncreasedSpendLimitReason());
			}
		}
		else if(StringUtils.isNotBlank(soIncSpendLimitDto.getIncreasedSpendLimitComment())){
			tmpServiceOrder.setSpendLimitIncrComment(soIncSpendLimitDto.getIncreasedSpendLimitComment());
			returnVal.addMiscParameter("spendLimitReason","Reason                        ====>"+soIncSpendLimitDto.getIncreasedSpendLimitComment());
			returnVal.addMiscParameter(OrderfulfillmentConstants.PVKEY_SPEND_LIMIT_REASON,"");
		}


		tmpServiceOrder.setPrice(mapPricing(spendLimitLabor, spendLimitParts, tmpServiceOrder));

		if(soIncSpendLimitDto.isTaskLevelPriceInd()){
			tmpServiceOrder.setTasks(mapTask(soIncSpendLimitDto.getTaskList(),securityContext));
		}
		modifiedUserId = Integer.toString(securityContext.getVendBuyerResId());
		tmpServiceOrder.setModifiedByName(modifiedByName);
		returnVal.addMiscParameter(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_NAME,modifiedByName);
		tmpServiceOrder.setModifiedBy(modifiedUserId);
		returnVal.addMiscParameter(OrderfulfillmentConstants.PVKEY_MODIFIED_USER_ID,modifiedUserId);


		tmpServiceOrder.setModifiedDate(new Date());

		tmpServiceOrder.setCreatedDate(new Date());

		tmpServiceOrder.setSoId(soIncSpendLimitDto.getSelectedSO());
		tmpServiceOrder.setUpdate(false);
		return returnVal;
	}

	private static List<SOTask> mapTask(ArrayList<SOTaskDTO> taskList,SecurityContext securityContext) {

		List<SOTask> tasks = new ArrayList<SOTask>();

		for (SOTaskDTO soTask : taskList) {
			SOTask task = new SOTask();
			task.setSequenceNumber(soTask.getSequenceNumber());
			task.setTaskId(soTask.getTaskId());
			if(soTask.getRetailPrice()!=null){
				task.setRetailPrice(new BigDecimal(soTask.getRetailPrice()));
			}
			task.setExternalSku(soTask.getSku());
			if(soTask.getFinalPrice()!=null){
				task.setFinalPrice(new BigDecimal(soTask.getFinalPrice()));
			}
			tasks.add(task);
		}
		return tasks;
	}

	/**
	 * Helper method to converting the pricing object to SOPricing for OF.
	 * @param pricing
	 * @return SOPricing object for the OF Domain.
	 */
	 private static SOPrice mapPricing(BigDecimal spendLimitLabor, BigDecimal spendLimitParts, ServiceOrder so) {
		 SOPrice returnVal = new SOPrice();
		 returnVal.setServiceOrder(so);
		 returnVal.setOrigSpendLimitLabor(spendLimitLabor);
		 returnVal.setOrigSpendLimitParts(spendLimitParts);
		 returnVal.setDiscountedSpendLimitLabor(spendLimitLabor);
		 returnVal.setDiscountedSpendLimitParts(spendLimitParts);
		 return returnVal;
	 }

	 public static OrderFulfillmentRequest newOrderFulfillmentRequest(SOElement element, SecurityContext context){
		 OrderFulfillmentRequest returnVal = new OrderFulfillmentRequest();
		 returnVal.setElement(element);
		 returnVal.setIdentification(createOFIdentityFromSecurityContext(context));
		 return returnVal;
	 }
	 public static RoutedProvider getRejectedProvider(int resourceId, int reasonId, int responseId,String providerRespComment, String modifiedBy) {
		 RoutedProvider routedProvider = new RoutedProvider();
		 routedProvider.setProviderResourceId((long)resourceId);
		 routedProvider.setProviderRespReasonId(reasonId);
		 routedProvider.setProviderResponse(ProviderResponseType.fromId(responseId));
		 routedProvider.setModifiedBy(modifiedBy);
		 Date now = Calendar.getInstance().getTime();
		 routedProvider.setProviderRespDate(now);
		 routedProvider.setModifiedDate(now);
		 routedProvider.setProviderRespComment(providerRespComment);
		 return routedProvider;
	 }
	 public static List<RoutedProvider> getRejectedProviders(List<Integer> checkedResourceID , int reasonId, int responseId, String providerRespComment, String modifiedBy) {
		 List<RoutedProvider> rejectedResources=new ArrayList<RoutedProvider>();
		 for(int resId: checkedResourceID){
			 RoutedProvider routedProvider = new RoutedProvider();
			 routedProvider.setProviderResourceId((long)resId);
			 routedProvider.setProviderRespReasonId(reasonId);
			 routedProvider.setProviderRespComment(providerRespComment);
			 routedProvider.setProviderResponse(ProviderResponseType.fromId(responseId));
			 routedProvider.setModifiedBy(modifiedBy);
			 Date now = Calendar.getInstance().getTime();
			 routedProvider.setProviderRespDate(now);
			 routedProvider.setModifiedDate(now);
			 rejectedResources.add(routedProvider);
		 }

		 return rejectedResources;
	 }

	 public static SOAdditionalPayment mapAdditionalPayment(AddonServicesDTO addonDto) {
		 SOAdditionalPayment addPayment = new SOAdditionalPayment();

		 if (addonDto.getPaymentRadioSelection().equals(OrderConstants.UPSELL_PAYMENT_TYPE_CHECK)) {
			 addPayment.setCheckNumber(addonDto.getCheckNumber().toString());
			 addPayment.setPaymentAmount(BigDecimal.valueOf(addonDto.getCheckAmount()));
			 addPayment.setPaymentType(PaymentType.CHECK);
		 } else if (addonDto.getPaymentRadioSelection().equals(OrderConstants.UPSELL_PAYMENT_TYPE_CREDIT)) {
			 addPayment.setExpirationDateMonth(addonDto.getSelectedMonth());
			 addPayment.setExpirationDateYear(addonDto.getSelectedYear());
			 addPayment.setCreditCardNumber(addonDto.getCreditCardNumber());
			 //Added to identify history logging is needed or not
			 addPayment.setEditOrCancel(addonDto.getEditOrCancel());
			 if (addonDto.getSelectedCreditCardType() != null)
				 addPayment.setCardType(PaymentCardType.fromId(addonDto.getSelectedCreditCardType()));
			 if (addonDto.getAmtAuthorized() != null)
				 addPayment.setPaymentAmount(BigDecimal.valueOf(addonDto.getAmtAuthorized()));
			 addPayment.setAuthorizationNumber(addonDto.getPreAuthNumber());
			 if (addonDto.getSelectedCreditCardType() != null)
				 addPayment.setPaymentType(PaymentType.fromShortName(getCreditCardCode(addonDto.getSelectedCreditCardType())));
		 }

		 return addPayment;

	 }

	 private static String getCreditCardCode(int intCode) {
		 switch (intCode) {
		 /*SLT-2591 and SLT-2592: Disable Amex
		  * case CreditCardConstants.CARD_ID_AMEX:
			 return CreditCardConstants.CARD_ID_AMEX_STR;*/
		 case CreditCardConstants.CARD_ID_DISCOVER:
			 return CreditCardConstants.CARD_ID_DISCOVER_STR;
		 case CreditCardConstants.CARD_ID_VISA:
			 return CreditCardConstants.CARD_ID_VISA_STR;
		 case CreditCardConstants.CARD_ID_MASTERCARD:
		 case CreditCardConstants.CARD_ID_SEARS_MASTERCARD:
			 return CreditCardConstants.CARD_ID_MASTERCARD_STR;
		 case CreditCardConstants.CARD_ID_SEARS:
			 return CreditCardConstants.CARD_ID_SEARS_CHARGE_STR;
		 case CreditCardConstants.CARD_ID_SEARS_COMMERCIAL:
			 return CreditCardConstants.CARD_ID_COMMERCIAL_ONE_STR;
		 case CreditCardConstants.CARD_ID_SEARS_PLUS:
			 return CreditCardConstants.CARD_ID_SEARS_CHARGE_PLUS_STR;
		 }

		 return "";
	 }

	 public static OrderFulfillmentRequest createPartsShippedRequest(
			 String serviceOrderId, List<SOPartsDTO> partShippingInfo,
			 SecurityContext securityContext) {

		 List<SOPart> soPartsList = mapLegacySoPartsDtoToOfSoParts(serviceOrderId, partShippingInfo);
		 OrderFulfillmentRequest ofRequest = new OrderFulfillmentRequest();
		 ofRequest.setIdentification(createOFIdentityFromSecurityContext(securityContext));
		 ofRequest.setElement(mapSoElementsListToSoElementCollection(soPartsList));

		 return ofRequest;
	 }


	 private static SOElement mapSoElementsListToSoElementCollection(List<? extends SOElement> soElementsList) {
		 SOElementCollection soElementCollection = new SOElementCollection();
		 soElementCollection.addAllElements(soElementsList);
		 return soElementCollection;
	 }

	 private static List<SOPart> mapLegacySoPartsDtoToOfSoParts(String soId,
			 List<SOPartsDTO> soPartsDtos) {

		 List<SOPart> soParts = new ArrayList<SOPart>();
		 for (SOPartsDTO soPartsDto : soPartsDtos) {
			 if (soPartsDto != null) {
				 SOPart soPart = new SOPart();
				 soPart.setPartId(soPartsDto.getPartId());

				 if (soPartsDto.getShippingCarrierId() != null) {
					 soPart.setShipCarrierId(CarrierType.fromId(soPartsDto.getShippingCarrierId()));
					 soPart.setShipTrackNo(soPartsDto.getShippingTrackingNumber());
					 String shippingDateString = soPartsDto.getShipDate();
					 if (shippingDateString != null && !"".equalsIgnoreCase(shippingDateString)) {

						 Date shipDate = null;
						 String shippingDateFormat = "yyyy-MM-dd";
						 try {
							 DateFormat sdf = new SimpleDateFormat(shippingDateFormat);
							 shipDate = sdf.parse(shippingDateString);
						 }
						 catch (ParseException e) {
							 // ignore the error
							 logger.info(String.format("Ignoring error while parsing shiping date '%s' with format '%s'", shippingDateString, shippingDateFormat), e);
						 }

						 soPart.setShipDate(shipDate);
					 }
				 }

				 if (soPartsDto.getCoreReturnCarrierId() != null) {
					 soPart.setReturnCarrierId(CarrierType.fromId(soPartsDto.getCoreReturnCarrierId()));
					 soPart.setReturnTrackNo(soPartsDto.getCoreReturnTrackingNumber());
				 }

				 soParts.add(soPart);
			 }
		 }
		 return soParts;
	 }

	 public static OrderFulfillmentRequest createAddOrdersToGroupRequest(List<String> soIdList, SecurityContext securityContext) {
		 OrderFulfillmentRequest request = new OrderFulfillmentRequest();
		 request.setIdentification(createOFIdentityFromSecurityContext(securityContext));
		 SOElementCollection collection = new SOElementCollection();
		 for (String soId : soIdList){
			 ServiceOrder so = new ServiceOrder();
			 so.setSoId(soId);
			 collection.addElement(so);
		 }
		 request.setElement(collection);
		 return request;  
	 }

	 public static OrderFulfillmentRequest createManageScopeOFRequest(HashMap<Integer, ManageTaskVO> tasks,Integer reasonId,String reason, String comment,SecurityContext securityContext) {
		 OrderFulfillmentRequest request = new OrderFulfillmentRequest();
		 request.setIdentification(createOFIdentityFromSecurityContext(securityContext));
		 SOElementCollection collection = new SOElementCollection();
		 for(ManageTaskVO task : tasks.values()){
			 SOTask soTask = new SOTask();
			 soTask.setTaskName(task.getTaskName());
			 soTask.setTaskComments(task.getTaskComments());
			 if(null != task.getServiceTypeTemplateId())
				 soTask.setServiceTypeId(task.getServiceTypeTemplateId().intValue());
			 soTask.setExternalSku(task.getSku());
			 soTask.setFinalPrice(task.getFinalPrice());
			 soTask.setPrice(task.getFinalPrice());
			 if(null != task.getSkillNodeId())
				 soTask.setSkillNodeId(task.getSkillNodeId().intValue());
			 soTask.setTaskStatus(OrderConstants.ACTIVE_TASK);
			 soTask.setTaskType(OrderConstants.PRIMARY_TASK);
			 soTask.setAutoInjectedInd(0);
			 if(null!=task.getSkuId() && !("").equals(task.getSkuId()))
				{
				 soTask.setSkuId(Integer.parseInt(task.getSkuId())); 
				}
				
			 // TO-D0 : Include task type
			 collection.addElement(soTask);
		 }
		 request.setElement(collection);
		 request.addMiscParameter("REASON_CODE_ID", reasonId.toString());
		 request.addMiscParameter("REASON_CODE", reason);
		 request.addMiscParameter("REASON_COMMENTS", comment);
		 return request;
	 }
	 
	 public static OrderFulfillmentLeadRequest createFetchProvidersLeadRequest(String leadId,SecurityContext securityContext) {
		 OrderFulfillmentLeadRequest request = new OrderFulfillmentLeadRequest();
		 LeadHdr hdr = new LeadHdr();
		 hdr.setLeadId(leadId);
		 request.setElement(hdr);
		 request.setIdentification(createOFIdentityFromSecurityContext(securityContext));		 
		
		 return request;
	 }
}
