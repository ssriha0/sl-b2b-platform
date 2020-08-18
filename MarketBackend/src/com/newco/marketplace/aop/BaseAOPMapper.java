/**
 *
 */
package com.newco.marketplace.aop;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import com.newco.marketplace.constants.Constants;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerFeatureSetBO;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerSubstatusAssocBO;
import com.newco.marketplace.business.iBusiness.orderGroup.IOrderGroupBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IIncidentBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderOutFileTrackingBO;
import com.newco.marketplace.dto.vo.buyer.BuyerReferenceVO;
import com.newco.marketplace.dto.vo.buyer.BuyerSubstatusAssocVO;
import com.newco.marketplace.dto.vo.incident.IncidentTrackingVO;
import com.newco.marketplace.dto.vo.incident.IncidentVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderOutFileTrackingVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.interfaces.BuyerConstants;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.ClientConstants;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.orderGroup.IOrderGroupDao;
import com.newco.marketplace.persistence.iDao.provider.IContactDao;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.ServiceLiveStringUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.utils.TimezoneMapper;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.login.LoginCredentialVO;
import com.newco.marketplace.vo.provider.Contact;

public class BaseAOPMapper implements OrderConstants, ClientConstants {

	private static final Logger logger = Logger.getLogger(BaseAOPMapper.class.getName());
	
	Object[] params = null;
	
	IBuyerFeatureSetBO buyerFeatureSetBO = (IBuyerFeatureSetBO) MPSpringLoaderPlugIn.getCtx().getBean("buyerFeatureSetBO");
	IServiceOrderBO serviceOrderBO = (IServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderBOTarget");
	ServiceOrderDao serviceOrderDao = (ServiceOrderDao) MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderDao");
	IOrderGroupDao orderGroupDAO = (IOrderGroupDao) MPSpringLoaderPlugIn.getCtx().getBean("orderGroupDAO");
	IOrderGroupBO orderGroupBO = (IOrderGroupBO) MPSpringLoaderPlugIn.getCtx().getBean("OrderGroupBOTarget");
	IIncidentBO incidentBO = (IIncidentBO) MPSpringLoaderPlugIn.getCtx().getBean("incidentBOTarget");
	IBuyerBO buyerBO = (IBuyerBO) MPSpringLoaderPlugIn.getCtx().getBean("buyerBo");
	IContactDao contactDao = (IContactDao) MPSpringLoaderPlugIn.getCtx().getBean("fmcontactDaoImpl");
	public BaseAOPMapper(){
	}

	public BaseAOPMapper(Object[] params){
		this.params = params;
	}

	/**
	 * Populate Service Order Object
	 * @throws DBException 
	 * */
	public Map<String, Object> mapServiceOrder(Map<String, Object> hmParams, ServiceOrder serviceOrder,SecurityContext securityContext) throws DBException{
		logger.debug("AOPMapper-->mapServiceOrder()");

		if(securityContext != null){
			LoginCredentialVO lvo = securityContext.getRoles();
			
			// If the user is SL Admin get Admin details else get logged in user details
			if(securityContext.isSlAdminInd()){
				hmParams.put(AOPConstants.AOP_CREATED_BY_NAME, securityContext.getSlAdminFName() + " " + securityContext.getSlAdminLName());
				 
				hmParams.put(AOPConstants.AOP_RESOURCE_ID, MPConstants.SLADMIN_RESOURCE_ID);
				
				hmParams.put(AOPConstants.AOP_MODIFIED_BY, securityContext.getSlAdminUName());
				
				hmParams.put(AOPConstants.AOP_ROLE_TYPE, OrderConstants.NEWCO_ADMIN_ROLEID);
			}else{ 
				if(lvo != null){
					hmParams.put(AOPConstants.AOP_CREATED_BY_NAME, lvo.getFirstName() + " " + lvo.getLastName());
				}
				
				if(securityContext.getVendBuyerResId() != null){
					hmParams.put(AOPConstants.AOP_RESOURCE_ID, securityContext.getVendBuyerResId());
				}
				
				if(securityContext.getUsername() != null){
					hmParams.put(AOPConstants.AOP_MODIFIED_BY, securityContext.getUsername());
				}
			}			
		}

		hmParams.put(AOPConstants.AOP_SO_ID, serviceOrder.getSoId());
		hmParams.put(AOPConstants.AOP_STATUS_ID, serviceOrder.getWfStateId());
		hmParams.put(AOPConstants.AOP_BUYER_RESOURCE_ID, serviceOrder.getBuyerResourceId());
		hmParams.put(AOPConstants.AOP_BUYER_ID, serviceOrder.getBuyer().getBuyerId());
		if(serviceOrder.getAcceptedVendorId() != null){
			hmParams.put(AOPConstants.AOP_PROVIDER_ID, serviceOrder.getAcceptedVendorId());
		}
		if(serviceOrder.getAcceptedResource() != null){
			hmParams.put(AOPConstants.AOP_VENDOR_RESOURCE_ID, serviceOrder.getAcceptedResource().getResourceId());
		}
		
		if(serviceOrder.getRoutedResources() != null){
			hmParams.put(AOPConstants.AOP_PROVIDER_LIST, serviceOrder.getRoutedResources());
			hmParams.put(AOPConstants.AOP_PROVIDER_LIST_COUNT, serviceOrder.getRoutedResources().size());
		}
		if(serviceOrder.getRoutedDate()!= null)
		{
			hmParams.put(AOPConstants.AOP_SO_ROUTED_DATE, TimeUtils.convertToTimezone(serviceOrder.getRoutedDate(),OrderConstants.SERVICELIVE_ZONE));
		}
		// For Route Service Order
		if(OrderConstants.METHOD_REROUTE_SO.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))
				|| OrderConstants.METHOD_RELEASE_SO.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))
				|| OrderConstants.METHOD_ROUTE_SO.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))) {
			if(serviceOrder.getSkill() != null){
				hmParams.put(AOPConstants.AOP_SO_MAIN_SERVICE_CATEGORY, serviceOrder.getSkill().getNodeName());
			}
			if(serviceOrder.getSpendLimitLabor() != null){
				hmParams.put(AOPConstants.AOP_SPEND_LIMIT_LABOR, UIUtils.formatDollarAmount(serviceOrder.getSpendLimitLabor()));
			}
			if(serviceOrder.getSpendLimitParts() != null){
				hmParams.put(AOPConstants.AOP_SPEND_LIMIT_PARTS, UIUtils.formatDollarAmount(serviceOrder.getSpendLimitParts()));
			}
			if(serviceOrder.getSpendLimitLabor() != null &&
					serviceOrder.getSpendLimitParts() != null){
				hmParams.put(AOPConstants.AOP_TOTAL_SPEND_LIMIT, UIUtils.formatDollarAmount(serviceOrder.getSpendLimitLabor() + serviceOrder.getSpendLimitParts()));
			}			
			if(serviceOrder.getServiceDate1() != null &&
					serviceOrder.getServiceTimeStart() != null){
				HashMap<String, Object> estServiceDateTime = TimeUtils.convertGMTToGivenTimeZone(serviceOrder.getServiceDate1(),
																serviceOrder.getServiceTimeStart(), serviceOrder.getServiceLocationTimeZone());
				hmParams.put(AOPConstants.AOP_SERVICE_DATE1, DateUtils.getFormatedDate(
							(Timestamp) estServiceDateTime.get(OrderConstants.GMT_DATE),"M/d/yyyy"));
				hmParams.put(AOPConstants.AOP_SERVICE_START_TIME,
												estServiceDateTime.get(OrderConstants.GMT_TIME));
			}else{
				hmParams.put(AOPConstants.AOP_SERVICE_DATE1,"");
				hmParams.put(AOPConstants.AOP_SERVICE_START_TIME,"");
			}
			if(serviceOrder.getServiceDate2() != null &&
					serviceOrder.getServiceTimeEnd() != null){
				HashMap<String, Object> estServiceDateTime = TimeUtils.convertGMTToGivenTimeZone(serviceOrder.getServiceDate2(),
						serviceOrder.getServiceTimeEnd(), serviceOrder.getServiceLocationTimeZone());

				hmParams.put(AOPConstants.AOP_SERVICE_DATE2, DateUtils.getFormatedDate(
						(Timestamp) estServiceDateTime.get(OrderConstants.GMT_DATE),"M/d/yyyy"));
				hmParams.put(AOPConstants.AOP_SERVICE_END_TIME, estServiceDateTime.get(OrderConstants.GMT_TIME));
			
			}else{
				hmParams.put(AOPConstants.AOP_SERVICE_DATE2,"");
				hmParams.put(AOPConstants.AOP_SERVICE_END_TIME,"");
			}
			hmParams.put(AOPConstants.AOP_SO_SERVICE_LOCATION_TIMEZONE, TimezoneMapper.getStandardTimezone(serviceOrder.getServiceLocationTimeZone()));
			if(serviceOrder.getSowTitle() != null){
				hmParams.put(AOPConstants.AOP_SO_TITLE, serviceOrder.getSowTitle());
			}
		
			if(serviceOrder.getSowDs() != null){
				hmParams.put(AOPConstants.AOP_SO_DESC, serviceOrder.getSowDs());
			}
			if(serviceOrder.getServiceLocation() != null){
				hmParams.put(AOPConstants.AOP_SO_SERVICE_CITY, serviceOrder.getServiceLocation().getCity());
				hmParams.put(AOPConstants.AOP_SO_SERVICE_STATE, serviceOrder.getServiceLocation().getState());
				hmParams.put(AOPConstants.AOP_SO_SERVICE_ZIP, serviceOrder.getServiceLocation().getZip());
			}
			
			// skip route Email for grouped Order
			if(StringUtils.isNotBlank(serviceOrder.getGroupId())){
				hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_YES);
			}

		}

		//Adding the Title for the following method names. 
		if(OrderConstants.METHOD_REROUTE_SO.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))
				|| OrderConstants.METHOD_RELEASE_SO.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))
				|| OrderConstants.METHOD_ROUTE_SO.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))
				|| OrderConstants.METHOD_ACCEPT_SERVICE_ORDER.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))
				|| OrderConstants.METHOD_ACCEPT_CONDITIONAL_SERVICE_ORDER.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))
				|| OrderConstants.METHOD_COMPLETE_SERVICE_ORDER.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))
				|| OrderConstants.METHOD_PROCESS_CREATE_CONDITIONALOFFER.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))
				|| OrderConstants.METHOD_PROCESS_CREATE_BID.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME)				
				)){
			if(serviceOrder.getSowTitle() != null){
				hmParams.put(AOPConstants.AOP_SO_TITLE, serviceOrder.getSowTitle());
			}			
		}
		
		if(OrderConstants.METHOD_ACCEPT_SERVICE_ORDER.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME)) ||	
				OrderConstants.METHOD_REASSIGN_SERVICE_ORDER.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))){
			if(serviceOrder.getAcceptedResource() != null
					&& serviceOrder.getAcceptedResource().getResourceContact() != null){
				hmParams.put(AOPConstants.AOP_ACCEPTED_VENDOR_RESOURCE_FNAME,
						serviceOrder.getAcceptedResource().getResourceContact().getFirstName());
				hmParams.put(AOPConstants.AOP_ACCEPTED_VENDOR_RESOURCE_LNAME,
						serviceOrder.getAcceptedResource().getResourceContact().getLastName());
			}
			if(serviceOrder.getAcceptedDate() != null){
				//change in dateformat
				String strDate = DateUtils.getFormatedDate(serviceOrder.getAcceptedDate(), "MMM dd, yyyy HH:mm a (z)");
				hmParams.put(AOPConstants.AOP_SO_ACCEPTED_DATE,strDate);
				
			}
			if(serviceOrder.getModifiedDate() != null){
				Date date = DateUtils.getDateFromString(serviceOrder.getModifiedDate(), "yyyy-MM-dd");
				hmParams.put(AOPConstants.AOP_SO_MODIFIED_DATE, DateUtils.getFormatedDate(date, "MMM dd, yyyy"));
			}
			hmParams.put(AOPConstants.AOP_PROVIDER_FIRST_NAME, hmParams.get(AOPConstants.AOP_ACCEPTED_VENDOR_RESOURCE_FNAME));
			hmParams.put(AOPConstants.AOP_VENDOR_RESOURCE_ID,serviceOrder.getAcceptedVendorId());
			hmParams.put(AOPConstants.AOP_PROVIDER_LAST_NAME,hmParams.get(AOPConstants.AOP_ACCEPTED_VENDOR_RESOURCE_LNAME));
			hmParams.put(AOPConstants.AOP_DATE_OF_SERVICE, DateUtils.getFormatedDate(serviceOrder.getServiceDate1(), "MMM dd, yyyy"));
		}
		if(OrderConstants.METHOD_REPORT_A_PROBLEM.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))){
			hmParams.put(AOPConstants.AOP_FIRSTNAME,
					securityContext.getRoles().getFirstName());
			hmParams.put(AOPConstants.AOP_LASTNAME,
					securityContext.getRoles().getLastName());
			
		
		}
		if(OrderConstants.METHOD_REJECT_SERVICEORDER.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))){
			String providerLastname="";
			hmParams.put(AOPConstants.AOP_VENDOR_RESOURCE_ID,
					securityContext.getVendBuyerResId());
			hmParams.put(AOPConstants.AOP_PROVIDERFIRSTNAME,securityContext.getRoles().getFirstName());
			if(StringUtils.isNotBlank(securityContext.getRoles().getLastName())){
				providerLastname=securityContext.getRoles().getLastName().substring(0,1);}
			hmParams.put(AOPConstants.AOP_PROVIDERLASTNAME,providerLastname);
			hmParams.put(AOPConstants.AOP_USERNAME,serviceOrder.getBuyer().getBuyerContact().getFirstName()+" "+serviceOrder.getBuyer().getBuyerContact().getLastName());
			
		
		}
		if(OrderConstants.METHOD_PROCESS_CREATE_CONDITIONALOFFER.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))){
			hmParams.put(AOPConstants.AOP_VENDOR_RESOURCE_ID,
					securityContext.getVendBuyerResId());
			hmParams.put(AOPConstants.AOP_PROVIDERFIRSTNAME,securityContext.getRoles().getFirstName());
			
			if(securityContext.getRoles().getLastName() != null){
				hmParams.put(AOPConstants.AOP_PROVIDERLASTNAME,securityContext.getRoles().getLastName().substring(0,1));
			}
			
			hmParams.put(AOPConstants.AOP_USERNAME,serviceOrder.getBuyer().getBuyerContact().getFirstName()+" "+serviceOrder.getBuyer().getBuyerContact().getLastName());
					
		}
		
		//Special case for Update Spend Limit, Since Alert/Email needs to be sent only after Accepting SO
		if(OrderConstants.METHOD_UPDATE_SPEND_LIMIT.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))){
			Integer wfStateId = serviceOrder.getWfStateId();
			if(wfStateId != null &&
					wfStateId.intValue() == OrderConstants.ACTIVE_STATUS ||
					wfStateId.intValue() == OrderConstants.ACCEPTED_STATUS ||
					wfStateId.intValue() == OrderConstants.PROBLEM_STATUS ||
					wfStateId.intValue() == OrderConstants.ROUTED_STATUS){

				hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_NO);
				hmParams.put(AOPConstants.SKIP_FTP_ALERT, OrderConstants.FLAG_NO);
				// If Increase Spend Limit is done in Posted status then email is sent to all routed providers
				// if its in other status send it to accpted vendor resource
				if(wfStateId.intValue() == OrderConstants.ROUTED_STATUS){
					hmParams.put(AOPConstants.ALERT_TO, AlertConstants.ALERT_ROLE_ALL_ROUTED_RESOURCES);
				}else{
					hmParams.put(AOPConstants.ALERT_TO, AlertConstants.ALERT_ROLE_ACCEPTED_VENDOR_RESOURCE);
				}
			}
			else{
				hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_YES);
			}
		}
		else if(hmParams.get(AOPConstants.SKIP_ALERT) == null){
			hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_NO);
			hmParams.put(AOPConstants.SKIP_FTP_ALERT, OrderConstants.FLAG_NO);
		}
		if(OrderConstants.METHOD_DELETE_DRAFT_SO.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))){
			if(securityContext.getCompanyId().equals(BuyerConstants.ASSURANT_BUYER_ID) && 
					(!securityContext.getRole().equals(OrderConstants.SYSTEM_ROLE))){
				hmParams.put(AOPConstants.SKIP_FTP_ALERT, OrderConstants.FLAG_YES);
			}
		}
		if(OrderConstants.METHOD_RELEASE_SO.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))
			|| OrderConstants.METHOD_ROUTE_SO.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))){
			hmParams.put(AOPConstants.AOP_BUYER_ID, serviceOrder.getBuyer().getBuyerId());
			List<RoutedProvider> routedProvider = serviceOrder.getRoutedResources();
			if(routedProvider != null
					&& routedProvider.size() > 0){
				boolean skipAlert = true;
				for(RoutedProvider routedResource : routedProvider){
					if(routedResource.getProviderRespId() == null
							|| ( routedResource.getProviderRespId().intValue() != OrderConstants.REJECTED.intValue()
									&& routedResource.getProviderRespId().intValue() != OrderConstants.RELEASED.intValue())){
						skipAlert = false;
						break;
					}
				}
				if(skipAlert){
					hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_YES);
				}
			}
		}

//		 For Support Note
		if(OrderConstants.METHOD_ADD_NOTE.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))
			||OrderConstants.METHOD_PROCESS_SUPPORT_ADD_NOTE.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))) {
			if(hmParams.get(AOPConstants.AOP_ROLE) != null
					&& ((String)hmParams.get(AOPConstants.AOP_ROLE)).equalsIgnoreCase(OrderConstants.BUYER_ROLE)){
				// Checking for the null values
				if(serviceOrder.getBuyer().getBuyerId() != 0) {
					hmParams.put(AOPConstants.AOP_COMPANY_ID, serviceOrder.getBuyer().getBuyerId());
				} else {
					hmParams.put(AOPConstants.AOP_COMPANY_ID, "NA");
				}
				if(StringUtils.isNotBlank(serviceOrder.getBuyer().getBusinessName())) {
					hmParams.put(AOPConstants.AOP_COMPANY_NAME, serviceOrder.getBuyer().getBusinessName());
				} else {
					hmParams.put(AOPConstants.AOP_COMPANY_NAME, "NA");
				}
			} else if(hmParams.get(AOPConstants.AOP_ROLE) != null
					&& ((String)hmParams.get(AOPConstants.AOP_ROLE)).equalsIgnoreCase(OrderConstants.PROVIDER_ROLE)){
				if(serviceOrder.getAcceptedVendorId() != 0) {
					hmParams.put(AOPConstants.AOP_COMPANY_ID, serviceOrder.getAcceptedVendorId());
				} else {
					hmParams.put(AOPConstants.AOP_COMPANY_ID, "NA");
				}
				if(StringUtils.isNotBlank(serviceOrder.getAcceptedVendorName())) {
					hmParams.put(AOPConstants.AOP_COMPANY_NAME, serviceOrder.getAcceptedVendorName());
				} else {
					hmParams.put(AOPConstants.AOP_COMPANY_NAME, "NA");
				}
			} else if(hmParams.get(AOPConstants.AOP_ROLE) != null
					&& ((String)hmParams.get(AOPConstants.AOP_ROLE)).equalsIgnoreCase(OrderConstants.SIMPLE_BUYER_ROLE)){
				// Checking for the null values
				if(serviceOrder.getBuyer().getBuyerId() != 0) {
					hmParams.put(AOPConstants.AOP_COMPANY_ID, serviceOrder.getBuyer().getBuyerId());
				} else {
					hmParams.put(AOPConstants.AOP_COMPANY_ID, "NA");
				}
				if(StringUtils.isNotBlank(serviceOrder.getBuyer().getBusinessName())) {
					hmParams.put(AOPConstants.AOP_COMPANY_NAME, serviceOrder.getBuyer().getBusinessName());
				} else {
					hmParams.put(AOPConstants.AOP_COMPANY_NAME, "NA");
				}
			}
			// The first and last name are set instead actual user name 
			hmParams.put(AOPConstants.AOP_USERNAME, securityContext.getRoles().getFirstName() + " " + securityContext.getRoles().getLastName());
			hmParams.put(AOPConstants.AOP_USER_PHONE_NUMBER, securityContext.getRoles().getPhoneNo());
			hmParams.put(AOPConstants.AOP_USER_EMAIL, securityContext.getRoles().getEmail());
		}		

		if(OrderConstants.METHOD_RESCHEDULE_REQUEST.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))||
				OrderConstants.METHOD_REQUEST_RESCHEDULE_SO.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))) {					 
		 	Buyer buyer = serviceOrder.getBuyer();
			Contact contact = contactDao.getByBuyerId(buyer.getBuyerId());
			hmParams.put(AOPConstants.AOP_USERNAME, contact.getFirstName() + " " + contact.getLastName());
			serviceOrder = convertGMTToLocalTime(serviceOrder);
			String timeZone = "";
			if(StringUtils.isNotBlank(serviceOrder.getServiceLocationTimeZone())) {
				timeZone = "(" + serviceOrder.getServiceLocationTimeZone().substring(0, 3) + ")";
			}
			String date = DateUtils.getFormatedDate(serviceOrder.getRescheduleServiceDate1(), "MMM dd, yyyy")
				+ " " + StringUtils.trimToEmpty(serviceOrder.getRescheduleServiceTimeStart())
				+ " " + timeZone;
				
			if(serviceOrder.getRescheduleServiceDate2() != null) {
				date += " - " + DateUtils.getFormatedDate(serviceOrder.getRescheduleServiceDate2(), "MMM dd, yyyy")
					+ " " + StringUtils.trimToEmpty(serviceOrder.getRescheduleServiceTimeEnd())
					+ " " + timeZone;
			}
			hmParams.put(AOPConstants.AOP_DATE, date);
		}
		if(OrderConstants.METHOD_CANCEL_RESCHEDULE_REQUEST.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))) {					 
		 	Buyer buyer = serviceOrder.getBuyer();
			Contact contact = contactDao.getByBuyerId(buyer.getBuyerId());
			hmParams.put(AOPConstants.AOP_USERNAME, contact.getFirstName() + " " + contact.getLastName());
			serviceOrder = convertGMTToLocalTime(serviceOrder);
			String timeZone = "";
			if(StringUtils.isNotBlank(serviceOrder.getServiceLocationTimeZone())) {
				timeZone = "(" + serviceOrder.getServiceLocationTimeZone().substring(0, 3) + ")";
			}
			String date = DateUtils.getFormatedDate(serviceOrder.getServiceDate1(), "MMM dd, yyyy")
				+ " " + StringUtils.trimToEmpty(serviceOrder.getServiceTimeStart())
				+ " " + timeZone;
			if(serviceOrder.getServiceDate2() != null) {
				date += " - " + DateUtils.getFormatedDate(serviceOrder.getServiceDate2(), "MMM dd, yyyy")
					+ " " + StringUtils.trimToEmpty(serviceOrder.getServiceTimeEnd())
					+ " " + timeZone;
			}
			hmParams.put(AOPConstants.AOP_ORIGINAL_DATE, date);
		}
		
		// If SKIP_LOGGING is not yet set, then set it to no
		if(hmParams.get(AOPConstants.SKIP_LOGGING) == null){
			hmParams.put(AOPConstants.SKIP_LOGGING, OrderConstants.FLAG_NO);
		}
		
		//hmParams.put(AOPConstants.AOP_SO_GROUP_ID, serviceOrder.getGroupId());
		//Additional mappings for the new OMS methods in order to make system as user
		if(OrderConstants.METHOD_PROCESS_UPDATE_SPEND_LIMIT_FOR_WS.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))
				|| OrderConstants.METHOD_PROCESS_REROUTE_SO_FOR_WS.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))
				|| OrderConstants.METHOD_PROCESS_UPDATE_TASK.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))
				|| OrderConstants.METHOD_PROCESS_CHANGE_OF_SCOPE.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))
				|| OrderConstants.METHOD_PROCESS_CANCEL_SO_IN_ACCEPTED_FOR_WS.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))
				|| OrderConstants.METHOD_PROCESS_CANCEL_SO_IN_ACTIVE_FOR_WS.equals(hmParams.get(AOPConstants.AOP_METHOD_NAME))){
			
			hmParams.put(AOPConstants.AOP_ROLE_TYPE, AOPConstants.AOP_SYSTEM_ROLE_ID);
			hmParams.put(AOPConstants.AOP_CREATED_BY_NAME, AOPConstants.AOP_SYSTEM);
			hmParams.put(AOPConstants.AOP_MODIFIED_BY, AOPConstants.AOP_SYSTEM.toLowerCase());
		}
		//Setting the Flag for Consumer and Flag for Price Model
		if( null != serviceOrder.getBuyer()){
			if(null != serviceOrder.getBuyer().getRoleId() 
					&& serviceOrder.getBuyer().getRoleId() == 5){
				hmParams.put(AOPConstants.AOP_CONSUMER_FLAG, OrderConstants.FLAG_YES);
			}else{
				hmParams.put(AOPConstants.AOP_CONSUMER_FLAG, OrderConstants.FLAG_NO);
			}			
		}	
		if(null != serviceOrder.getPriceModel() 
				&& serviceOrder.getPriceModel().equals(Constants.PriceModel.NAME_PRICE)){
			hmParams.put(AOPConstants.AOP_BID_FLAG, Constants.PriceModel.NAME_YOUR_PRICE);
		}else{
			hmParams.put(AOPConstants.AOP_BID_FLAG, Constants.PriceModel.ZERO_PRICE);
		}
		
		return hmParams;
	}
	
	protected Map<String, Object> updatePostSOInjection(Map<String, Object> hmParams, String clientStatus, String comments) {
		logger.debug("AOPMapper-->updateSOSubStatusPostSO()");
		String subStatus = "";
		
		
		try {
			if (hmParams.containsKey(AOPConstants.AOP_SERVICE_ORDER)) {
				ServiceOrder so = (ServiceOrder) hmParams.get(AOPConstants.AOP_SERVICE_ORDER);
				if (so != null) {
					subStatus = so.getSubStatus();
					hmParams.put(AOPConstants.AOP_SUBSTATUS_DESC, subStatus);
					if (hmParams.get(AOPConstants.AOP_BUYER_ID) == null) {
						hmParams.put(AOPConstants.AOP_BUYER_ID, so.getBuyer().getBuyerId());
					}
					if (buyerFeatureSetBO.validateFeature(so.getBuyer().getBuyerId(), BuyerFeatureConstants.ASSURANT_ALERTS)) {
						populateAssurantFieldsInMap(hmParams, so, clientStatus, comments);
					}
					if (buyerFeatureSetBO.validateFeature(so.getBuyer().getBuyerId(), BuyerFeatureConstants.HSR_ALERTS)) {
						populateHSRFieldsInMap(hmParams, so);
					}					
					hmParams.put(AOPConstants.AOP_UPSOLD_TOTAL_PRICE, "");
					// Addon Sell amt is set only if Amt  > 0, assuming only for oms it would be > 0
					Double addOnSellAmt = serviceOrderBO.calcUpsellAmount(so);
					double addOnsAmt = (addOnSellAmt != null)?addOnSellAmt.doubleValue():0.0;
					if(addOnsAmt > 0){
						String upsoldTotalPriceDisplayStr = AOPConstants.AOP_UPSOLD_TOTAL_PRICE_DISPLAY + UIUtils.formatDollarAmount(addOnsAmt);
						hmParams.put(AOPConstants.AOP_UPSOLD_TOTAL_PRICE, upsoldTotalPriceDisplayStr);
						if(hmParams.get(AOPConstants.AOP_FINALPRICE_TOTAL)!= null){
							double finalTotal = so.getLaborFinalPrice() + so.getPartsFinalPrice();
							finalTotal = finalTotal + addOnsAmt;
							hmParams.put(AOPConstants.AOP_FINALPRICE_TOTAL, UIUtils.formatDollarAmount(finalTotal));
						}
						
						
						
					}
					
				}
			}
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		} 
		return hmParams;
	}

	/**
	 * @param hmParams
	 * @param so
	 * @throws ParseException 
	 */
	protected void populateAssurantFieldsInMap(Map<String, Object> hmParams,
			ServiceOrder so, String clientStatus, String comments) throws ParseException {
		populateBuyerSubstatusDescInMap(hmParams, so, clientStatus, comments);
		populateAssurantComments(hmParams, so);
		populatePartsInMap(hmParams, so);
		populateMiscFieldsInMap(hmParams, so);
	}
	
	
	/**
	 * Populate the value for template
	 * 
	 * @param hmParams
	 * @param so
	 */
	private void populateHSRFieldsInMap(Map<String, Object> hmParams, ServiceOrder so) {
		if(isFirstTimeAccepted(so)){
			String unitNumber = serviceOrderBO.getCustomRefValue(
					OrderConstants.CUSTOM_REF_UNIT_NUM, so.getSoId()).getRefValue();
			String orderNumber = serviceOrderBO.getCustomRefValue
					(OrderConstants.CUSTOM_REF_ORDER_NUM, so.getSoId()).getRefValue();
			
			hmParams.put(AOPConstants.AOP_SO_UNIT_NUMBER, unitNumber);
			hmParams.put(AOPConstants.AOP_SO_ORDER_NUMBER, orderNumber);
			hmParams.put(AOPConstants.AOP_HSR_TECH_ID, "0000003");
			// set file date for each alert  
			SimpleDateFormat sdfFileDate = new SimpleDateFormat("yyyyMMdd'.'HHmmss");
			hmParams.put(AOPConstants.AOP_FILE_NAME, AOPConstants.AOP_HSR_FILE_NAME + "." + sdfFileDate.format(new Date()));
		
			//Override the SO_ROUTED_DATE since its exisitng one is give Time information 
			String soRoutedDate =  (String) hmParams.get(AOPConstants.AOP_SO_ROUTED_DATE);
			String pattern[] = {"MM/dd/yyyy hh:mm a"};
			if(soRoutedDate != null) {
				
					try {
						Date soRdate = org.apache.commons.lang.time.DateUtils.parseDate(soRoutedDate, pattern);
						SimpleDateFormat sdfRoutedDate = new SimpleDateFormat("yyyy-MM-dd");
						hmParams.put(AOPConstants.AOP_SO_ROUTED_DATE, sdfRoutedDate.format(soRdate));
					} catch (ParseException e) {
						logger.error(" So Routed DATE parsing Exception " + e.getMessage(), e);
						//TODO I dont know what should be do ... I kept the existing date as it is in case of failure 
					}
				
			}
			
		}
		else{
			hmParams.put(AOPConstants.SKIP_FTP_ALERT, OrderConstants.FLAG_YES);
		}
	}

	/**
	 * Check if order is getting accepted for the first time
	 * 
	 * @param so
	 * @return
	 */
	public static boolean isFirstTimeAccepted(ServiceOrder so) {
		boolean firstTimeAccepted = false;
		int numOfAcceptence = 0;
		int numOfRelease = 0;
		boolean firstSoOutFiles = true;

		// To check whether there is entry in so_outfile_tracking table for the so 
		try {
			IServiceOrderOutFileTrackingBO serviceOrderOutFileTrackingBO = (IServiceOrderOutFileTrackingBO)  MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderOutFileTrackingBO");
			String soId = so.getSoId();
			ServiceOrderOutFileTrackingVO serviceOrderOutFileTrackingVO = serviceOrderOutFileTrackingBO.getSOOutFileTrackingInfo(soId);
			if(serviceOrderOutFileTrackingVO != null){
				firstSoOutFiles = false;
			}
		} 
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		} 
		
		List<RoutedProvider> routedResources = so.getRoutedResources();
		for (RoutedProvider routedProvider : routedResources) {
			if (routedProvider.getProviderRespId() != null && routedProvider.getProviderRespId().equals(OrderConstants.ACCEPTED)) {
				numOfAcceptence +=1;
			}
			if (routedProvider.getProviderRespId() != null && routedProvider.getProviderRespId().equals(OrderConstants.RELEASED)) {
				numOfRelease +=1;
			}
		}
		if (numOfAcceptence == 1 && numOfRelease == 0 && firstSoOutFiles) {
			firstTimeAccepted = true;;
		}
		return firstTimeAccepted;
	}
	
	/**
	 * @param hmParams
	 * @param so
	 */
	protected void populateBuyerSubstatusDescInMap(Map<String, Object> hmParams,
			ServiceOrder so, String clientStatus, String comments) {
		
		List<BuyerSubstatusAssocVO>  buyerAssocInfoList;
		IBuyerSubstatusAssocBO buyerSubstatusBO = (IBuyerSubstatusAssocBO) MPSpringLoaderPlugIn.getCtx().getBean("buyerSubstatusAssocBO");
		Integer buyerId = so.getBuyer().getBuyerId();
		Integer wfStateId = so.getWfStateId();
		Integer wfSubStatusId = so.getWfSubStatusId();
		String soId = so.getSoId();
		buyerAssocInfoList = buyerSubstatusBO.getBuyerSubstatusAssoc(buyerId, wfStateId, wfSubStatusId, clientStatus);
		
		if (StringUtils.isBlank(clientStatus)) {
			if (buyerAssocInfoList!= null && !buyerAssocInfoList.isEmpty()) {
				hmParams.put(AOPConstants.AOP_BUYER_SUBSTATUS_ASSOC, buyerAssocInfoList);
				// check if we need to see if its 2nd time or more for which we may need not send Output file
				if(buyerSubstatusBO.getSkipAlertFlag(buyerId, wfStateId, wfSubStatusId, soId)){
					hmParams.put(AOPConstants.SKIP_FTP_ALERT, OrderConstants.FLAG_YES);
				}
			}
			else{
				logger.warn(new StringBuilder("WARNING: Alert will not generated for SO = ").append(soId).append("; Since no status found for given buyerId = ")
						.append(buyerId).append(", SO Status = ").append(wfStateId).append(" and SO Substatus = ").append(wfSubStatusId).append("!").toString());
				hmParams.put(AOPConstants.SKIP_FTP_ALERT, OrderConstants.FLAG_YES);
			}
		}
		else{
			populateStaticCommentsInMap(hmParams, clientStatus, comments, buyerAssocInfoList);
		}
	}

	/**
	 * @param hmParams
	 * @param clientStatus
	 * @param comments
	 * @param dbBuyerAssocInfoList
	 */
	@SuppressWarnings("unchecked")
	private void populateStaticCommentsInMap(Map<String, Object> hmParams, String clientStatus, String comments, List<BuyerSubstatusAssocVO>  dbBuyerAssocInfoList) {
		BuyerSubstatusAssocVO buyerSubstatusAssocVO = new BuyerSubstatusAssocVO();
		buyerSubstatusAssocVO.setBuyerStatus(clientStatus);
		buyerSubstatusAssocVO.setComments(comments);
		if (dbBuyerAssocInfoList!= null && !dbBuyerAssocInfoList.isEmpty()) {
			buyerSubstatusAssocVO.setBuyerSubStatusAssocId(dbBuyerAssocInfoList.get(0).getBuyerSubStatusAssocId());
		}
		List<BuyerSubstatusAssocVO> buyerAssocInfoList = (List<BuyerSubstatusAssocVO>) hmParams.get(AOPConstants.AOP_BUYER_SUBSTATUS_ASSOC);
		if (buyerAssocInfoList == null) {
			buyerAssocInfoList = new ArrayList<BuyerSubstatusAssocVO>();
		}
		buyerAssocInfoList.add(buyerSubstatusAssocVO);
		hmParams.put(AOPConstants.AOP_BUYER_SUBSTATUS_ASSOC, buyerAssocInfoList);
	}

	/**
	 * @param hmParams
	 * @param so
	 * @throws ParseException 
	 */
	protected void populateMiscFieldsInMap(Map<String, Object> hmParams,
			ServiceOrder so) throws ParseException {
		Date partsETA = null;
		String shippingTracking = null;
		String returnTracking = null;
		String model = null;
		String manufacturer = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
		if (so.getParts().size() > 0) {
			
			if (partsETA == null && so.getParts().get(0).getShipDate() != null) {
				partsETA = so.getParts().get(0).getShipDate();
			}
			else if (partsETA != null && so.getParts().get(0).getShipDate() != null){
				if (partsETA.compareTo(so.getParts().get(0).getShipDate()) < 0) {
					partsETA = so.getParts().get(0).getShipDate();
				}
			}
			if (so.getParts().get(0).getShippingCarrier() != null) {
				shippingTracking = so.getParts().get(0).getShippingCarrier().getTrackingNumber();
			}							
			if (so.getParts().get(0).getReturnCarrier() != null) {
				returnTracking = so.getParts().get(0).getReturnCarrier().getTrackingNumber();
			}
			model = so.getParts().get(0).getModelNumber();
			manufacturer = so.getParts().get(0).getManufacturer();
		}
		populateEtaInMap(hmParams, so, partsETA);
		
		hmParams.put("REPLACEMENT_SN", "");
		for (ServiceOrderCustomRefVO customRef : so.getCustomRefs()) {
			hmParams.put(customRef.getRefType(), customRef.getRefValue());
		}
		hmParams.put("STATUS_DATE", sdf.format(new Date()));
		hmParams.put("CORE_RETURN_NUMBER", "");
		hmParams.put("SHIPPING_CHARGE", 0);
		hmParams.put("SALES_TAX", "0.00");		
		hmParams.put("SHIP_AIR_BILL", shippingTracking == null ? "" : shippingTracking);
		hmParams.put("CORE_RETURN_AIR_BILL", returnTracking == null ? "" : returnTracking);
		hmParams.put("REPLACEMENT_OEM", manufacturer == null ? "" : manufacturer);
		hmParams.put("REPLACEMENT_MODEL", model == null ? "" : model);
	}
	
	/**
	 * Populate the Estimate time of arrival (ETA) in the map
	 * 
	 * @param hmParams
	 * @param so
	 * @param partsETA
	 * @throws ParseException 
	 */
	private void populateEtaInMap(Map<String, Object> hmParams,	ServiceOrder so, Date partsETA) throws ParseException
			{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
		if (partsETA != null) {
			hmParams.put("ETA_DATE", sdf.format(partsETA)); //parts ship arrival time max ship date
		}
		else {
			Date etaDate;
			if (so.getServiceDateTypeId().equals(Integer.parseInt(FIXED_DATE))) {
				HashMap<String, Object> dateTimeMap = TimeUtils.convertGMTToGivenTimeZone(so.getServiceDate1(), 
						so.getServiceTimeStart(), so.getServiceLocationTimeZone());
				Date serviceDate = new Date(((Timestamp)dateTimeMap.get(GMT_DATE)).getTime());
				String serviceTime = dateTimeMap.get(GMT_TIME).toString();
				etaDate = TimeUtils.combineDateTime(serviceDate, serviceTime);
			}
			else{
				HashMap<String, Object> dateTimeMap = TimeUtils.convertGMTToGivenTimeZone(so.getServiceDate2(), 
						so.getServiceTimeEnd(), so.getServiceLocationTimeZone());
				Date serviceDate = new Date(((Timestamp)dateTimeMap.get(GMT_DATE)).getTime());
				String serviceTime = dateTimeMap.get(GMT_TIME).toString();
				etaDate = TimeUtils.combineDateTime(serviceDate, serviceTime);
			}
			hmParams.put("ETA_DATE", sdf.format(etaDate));
		}
	}
	
	/**
	 * Modify the comment for assurant mapping. This method first tries to get comment from
	 * service order attributes if the the comment is an attribute of the service order, otherwise
	 * it uses the configured comment. If the comment is null, this method uses service order
	 * description as comment.
	 * 
	 * @param hmParams
	 * @param so
	 */
	@SuppressWarnings("unchecked")
	protected void populateAssurantComments(Map<String, Object> hmParams, ServiceOrder so){
		List<BuyerSubstatusAssocVO>  buyerAssocInfoList = 
			(List<BuyerSubstatusAssocVO>)hmParams.get(AOPConstants.AOP_BUYER_SUBSTATUS_ASSOC);
		String outputComments = null;
		
		if (buyerAssocInfoList != null) {
			for (BuyerSubstatusAssocVO buyerSubstatusAssocVO : buyerAssocInfoList) {
				//If comment is configured as null, so service order description as comment
				if (StringUtils.isBlank(buyerSubstatusAssocVO.getComments())) {
					buyerSubstatusAssocVO.setComments("sowDs");
				}
				try {
					//Get the comment from service order attribute
					outputComments = (String) PropertyUtils.getSimpleProperty(so, buyerSubstatusAssocVO.getComments());
				} catch (IllegalAccessException e) {
					outputComments = processCommentsException(buyerSubstatusAssocVO, e);
				} catch (InvocationTargetException e) {
					outputComments = processCommentsException(buyerSubstatusAssocVO, e);
				}catch (IllegalArgumentException e) {
					outputComments = buyerSubstatusAssocVO.getComments();
				} catch (NoSuchMethodException e) {
					//Use the configured comment
					outputComments = buyerSubstatusAssocVO.getComments();
				}
				catch (Exception e) {
					outputComments = processCommentsException(buyerSubstatusAssocVO, e);
				} 
				outputComments = ServiceLiveStringUtils.replaceNewLineChars(outputComments, "!");
				outputComments = ServiceLiveStringUtils.scrubImageMicroReferences(outputComments);
				buyerSubstatusAssocVO.setComments(ServiceLiveStringUtils.replaceNewLineChars(outputComments, "!"));
			}
		}
	}



	/**
	 * Processes the exception occurred from processing comments
	 * 
	 * @param buyerSubstatusAssocVO
	 * @param e
	 * @return
	 */
	private String processCommentsException(BuyerSubstatusAssocVO buyerSubstatusAssocVO,Exception e) {
		String outputComments;
		outputComments = "";
		logger.error("Could not set the comment for output file. " +
				"The input comment is " +  buyerSubstatusAssocVO.getComments(), e);
		return outputComments;
	}

	/**
	 * @param hmParams
	 * @param so
	 */
	protected void populatePartsInMap(Map<String, Object> hmParams,
			ServiceOrder so) {
		String classCode = null;
		StringBuilder partsClasses = new StringBuilder();
		StringBuilder partsNumbers = new StringBuilder();
		StringBuilder partsDescriptions = new StringBuilder();
		StringBuilder partsQuantities = new StringBuilder();
		StringBuilder partsPrices = new StringBuilder();
		StringBuilder parts = new StringBuilder();
		int count = 1;
		for (Part part : so.getParts()) {
			classCode = part.getAltPartRef1();
			if (count > 1) {
				partsClasses.append("^");
			}
			//part number is null according to requirement
			partsClasses.append(classCode == null ? "" : classCode.trim());
			if (count > 1) {
				partsDescriptions.append("^");
			}
			//Replace any new line characters and truncate to 35 characters
			String partsDescription = ServiceLiveStringUtils.replaceNewLineChars(part.getPartDs(), " ");
			populatePartDescription(partsDescription,part);
			
			if (partsDescription.length() > PARTS_DESC_LENGTH) {
				partsDescription = partsDescription.substring(0,PARTS_DESC_LENGTH);
			}
			partsDescriptions.append(partsDescription);
			if (count > 1) {
				partsQuantities.append("^");
			}
			partsQuantities.append(part.getQuantity());
			if (count > 1) {
				partsPrices.append("^");
			}
			partsPrices.append(0);							
			count++;							
		}
		parts.append(partsClasses);
		parts.append("~");
		parts.append(partsNumbers);
		parts.append("~");
		parts.append(partsDescriptions);
		parts.append("~");
		parts.append(partsQuantities);
		parts.append("~");
		parts.append(partsPrices);
		parts.append("~");
		hmParams.put("PARTS", parts.toString()); //classes~numbers~descriptions~quantities~prices~
	}
	
	/**  get the information of part which was saved along with part desc earlier, now they are saved individually*/
	private void populatePartDescription(String partsDescription, Part part) {
		if(partsDescription!= null){
			@SuppressWarnings("unused")
			String comments = ServiceLiveStringUtils.getKeyVal(part.getPartDs(), "Comments", "!", ":");
			@SuppressWarnings("unused")
			String shippingMethod = ServiceLiveStringUtils.getKeyVal(part.getPartDs(), "ShippingMethod", "!", ":");
			String oemNumber = part.getManufacturerPartNumber();
			String classCode = part.getAltPartRef1();
			String classComments = part.getAdditionalPartInfo();
			String partNumber = part.getVendorPartNumber();
			String manufacturer = part.getManufacturer();
			String modelNumber = part.getModelNumber();
			String serialNumber = part.getSerialNumber();
			
			StringBuffer partDescSb = new StringBuffer();
			partDescSb.append(partsDescription);
			if (StringUtils.isNotBlank(oemNumber) ){
				partDescSb.append(OrderConstants.OEM + ":");
				partDescSb.append(oemNumber);
				partDescSb.append(" ! ");
			}
			if (StringUtils.isNotBlank(classCode) ){
				partDescSb.append(OrderConstants.CLASSCODE + ":");
				partDescSb.append(classCode);
				partDescSb.append(" ! ");
			}
			if (StringUtils.isNotBlank(classComments) ){
				partDescSb.append(OrderConstants.CLASSCOMMENTS + ":");
				partDescSb.append(classComments);
				partDescSb.append(" ! ");
			}
			if (StringUtils.isNotBlank(partNumber) ){
				partDescSb.append(OrderConstants.PARTNUMBER + ":");
				partDescSb.append(partNumber);
				partDescSb.append(" ! ");
			}
			if (StringUtils.isNotBlank(manufacturer) ){
				partDescSb.append(OrderConstants.MANUFACTURER + ":");
				partDescSb.append(manufacturer);
				partDescSb.append(" ! ");
			}
			if (StringUtils.isNotBlank(modelNumber) ){
				partDescSb.append(OrderConstants.MODELNUMBER + ":");
				partDescSb.append(modelNumber);
				partDescSb.append(" ! ");
			}
			if (StringUtils.isNotBlank(serialNumber) ){
				partDescSb.append(OrderConstants.SERIALNUMBER + ":");
				partDescSb.append(serialNumber);
				partDescSb.append(" ! ");
			}
			
		    partsDescription =  partDescSb.toString();	
			
		}
		
		
	}

	protected Integer getSLIncidentId(String clientIncidentId, Integer clientId, String soId, Integer buyerId) {
		// Get ServiceLive Incident Id based on Client Incident Id and Client Id
		IncidentVO incidentVO = new IncidentVO();
		incidentVO.setClientIncidentId(clientIncidentId);
		incidentVO.setClientId(clientId);
		List<IncidentVO> incidents = null;
		Integer slIncidentId = null;
		try {
			incidents = incidentBO.getIncidents(incidentVO);
			if (incidents != null && !incidents.isEmpty()) {
				slIncidentId = incidents.get(0).getIncidentId();
				updateSLIncidentCustomRef(soId, slIncidentId, buyerId);
			}
		} catch (BusinessServiceException e) {
			logger.error(e);
		}
		return slIncidentId;
	}
	
	protected void updateSLIncidentCustomRef(String soId, Integer slIncidentId, Integer buyerId) throws BusinessServiceException {

		int slIncidentIdCustomRefTypeId = -1;
		List<BuyerReferenceVO> refs;
		try {
			refs = buyerBO.getBuyerReferences(buyerId);
			//TODO Bad! A BO throwing DataServiceException; handle it for now
		} catch (DataServiceException dsEx) {
			throw new BusinessServiceException("Error while retrieving buyer reference types", dsEx);
		}
		for (BuyerReferenceVO ref : refs) {
			if (OrderConstants.SL_INCIDENT_REFERNECE_KEY.equals(ref.getReferenceType())) {
				slIncidentIdCustomRefTypeId = ref.getBuyerRefTypeId();
				break;
			}
		}

		if (slIncidentIdCustomRefTypeId != -1) {
	    	ServiceOrderCustomRefVO socrVo = new ServiceOrderCustomRefVO();
	    	socrVo.setsoId(soId);
	    	socrVo.setModifiedDate(new java.sql.Date(System.currentTimeMillis()));
	    	socrVo.setCreatedDate(new java.sql.Date(System.currentTimeMillis()));
	    	socrVo.setModifiedBy(buyerId.toString());
	    	socrVo.setRefType(OrderConstants.SL_INCIDENT_REFERNECE_KEY);
	    	socrVo.setRefTypeId(slIncidentIdCustomRefTypeId);
	    	socrVo.setRefValue(slIncidentId.toString());
	    	serviceOrderBO.insertSoCustomReference(socrVo); // Try update; if no records found, then insert
		} else {
			throw new BusinessServiceException("SERVICELIVE INCIDENT ID custom ref not defined in buyer_reference_types tables");
		}
	}
	
	/**
	 * If the last outfile status for the current incident is not Parts Pending, this method sends outfile with closed
	 * status when last status is not closed and then sends outfile for SP ReOpen status and Parts Pending status.
	 * 
	 * @param aopParams
	 * @param lastTracking
	 * @param sc
	 * @return aopParams i.e. a map of template parameters
	 */
	protected Map<String, Object> updateParamsForSpReOpen(
			Map<String, Object> aopParams, IncidentTrackingVO lastTracking, SecurityContext sc) {
		String lastIncidentUpdateStatus = null;
		try {
			if (lastTracking != null) {
				String currentSoId = (String)aopParams.get(AOPConstants.AOP_SO_ID);
				lastIncidentUpdateStatus = lastTracking.getBuyerSubstatusDesc();
				//If not same SoID then send the SP Reopen related outfiles, 
				if (StringUtils.isNotBlank(lastIncidentUpdateStatus) &&
						!currentSoId.equals(lastTracking.getSoId())) {
					//If last status is not closed and a close file has not been sent for that SO
					//then send a closed out file and add a note to the previous SO with same incident ID
					if (!lastIncidentUpdateStatus.equalsIgnoreCase(ClientConstants.CLOSED_STATUS)
							&& incidentBO.getLastTrackingForSoAndSubstatus(lastTracking.getSoId(), ClientConstants.CLOSED_STATUS) == null) {
						aopParams = updateParamsForClose(aopParams, lastTracking, sc);
					}
					//If one SpReopen file has been sent for this service order then send SpReopen and parts pending file
					IncidentTrackingVO lastTrackingForSO = incidentBO.getLastTrackingForSoAndSubstatus(currentSoId, ClientConstants.SP_REOPEN_STATUS);
					if (lastTrackingForSO == null) {
						aopParams = updatePostSOInjection(aopParams, ClientConstants.SP_REOPEN_STATUS, ClientConstants.SP_REOPEN_COMMENTS);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error in creating SP Reopen file", e);
		}
		return aopParams;
	}

	/**
	 * @param aopParams i.e. a map of template parameters
	 * @param lastTracking
	 * @param sc
	 * @return i.e. a map of template parameters
	 * @throws BusinessServiceException
	 */
	private Map<String, Object> updateParamsForClose(
			Map<String, Object> aopParams, IncidentTrackingVO lastTracking, SecurityContext sc)
			throws com.newco.marketplace.exception.core.BusinessServiceException {
		
		String currentSoId = (String)aopParams.get(AOPConstants.AOP_SO_ID);
		ServiceOrder originalSo = (ServiceOrder) aopParams.get(AOPConstants.AOP_SERVICE_ORDER);
		ServiceOrder tempSo = serviceOrderBO.getServiceOrder(lastTracking.getSoId());
		tempSo.setWfStateId(OrderConstants.COMPLETED_STATUS);
		tempSo.setWfSubStatusId(OrderConstants.AWAITING_PAYMENT_SUBSTATUS);
		aopParams.put(AOPConstants.AOP_SERVICE_ORDER, tempSo);
		//Update AopParams for sending a close file for the previous order
		aopParams = updatePostSOInjection(aopParams, ClientConstants.CLOSED_STATUS, ClientConstants.CLOSED_COMMENTS);
		aopParams.put(AOPConstants.AOP_SERVICE_ORDER, originalSo);
		//Set the SO ID of the previous order in the aop parameter map as closed SO ID, so that it gets printed
		//in the outfile and saved in so_incident_tracking
		aopParams.put(AOPConstants.AOP_CLOSED_SO_ID, lastTracking.getSoId());
		addNoteToPreviousSO(currentSoId, lastTracking.getSoId(), "incident.close", sc);
		return aopParams;
	}
	
	/**
	 * This method will convert the GMT time to the local time.
	 * @param serviceOrder
	 * @return
	 */
	private ServiceOrder convertGMTToLocalTime(ServiceOrder serviceOrder) {
		HashMap<String, Object> serviceDate1 = null;
		HashMap<String, Object> serviceDate2 = null;
		HashMap<String, Object> rescheduleDate1 = null;
		HashMap<String, Object> rescheduleDate2 = null;
		String startTime = null;
		String endTime = null;
		
		startTime = serviceOrder.getServiceTimeStart();
		endTime = serviceOrder.getServiceTimeEnd();
		if (serviceOrder.getServiceDate1() != null && startTime != null) {

			serviceDate1 = TimeUtils.convertGMTToGivenTimeZone(serviceOrder
					.getServiceDate1(), startTime, serviceOrder.getServiceLocationTimeZone());
			if (serviceDate1 != null && !serviceDate1.isEmpty()) {
				serviceOrder.setServiceDate1((Timestamp) serviceDate1
						.get(OrderConstants.GMT_DATE));
				serviceOrder.setServiceTimeStart((String) serviceDate1
						.get(OrderConstants.GMT_TIME));
			}
		}
		if (serviceOrder.getServiceDate2() != null && endTime != null) {
			serviceDate2 = TimeUtils.convertGMTToGivenTimeZone(serviceOrder
					.getServiceDate2(), endTime, serviceOrder.getServiceLocationTimeZone());
			if (serviceDate2 != null && !serviceDate2.isEmpty()) {
				serviceOrder.setServiceDate2((Timestamp) serviceDate2
						.get(OrderConstants.GMT_DATE));
				serviceOrder.setServiceTimeEnd((String) serviceDate2
						.get(OrderConstants.GMT_TIME));
			}
		}
		
		startTime = serviceOrder.getRescheduleServiceTimeStart();
		endTime = serviceOrder.getRescheduleServiceTimeEnd();
		if (serviceOrder.getRescheduleServiceDate1() != null
				&& startTime != null) {

			rescheduleDate1 = TimeUtils.convertGMTToGivenTimeZone(serviceOrder
					.getRescheduleServiceDate1(), startTime,
					serviceOrder.getServiceLocationTimeZone());
			if (rescheduleDate1 != null && !rescheduleDate1.isEmpty()) {
				serviceOrder
						.setRescheduleServiceDate1((Timestamp) rescheduleDate1
								.get(OrderConstants.GMT_DATE));
				serviceOrder
						.setRescheduleServiceTimeStart((String) rescheduleDate1
								.get(OrderConstants.GMT_TIME));
			}
		}
		if (serviceOrder.getRescheduleServiceDate2() != null && endTime != null) {
			rescheduleDate2 = TimeUtils.convertGMTToGivenTimeZone(serviceOrder
					.getRescheduleServiceDate2(), endTime,
					serviceOrder.getServiceLocationTimeZone());
			if (rescheduleDate2 != null && !rescheduleDate2.isEmpty()) {
				serviceOrder
						.setRescheduleServiceDate2((Timestamp) rescheduleDate2
								.get(OrderConstants.GMT_DATE));
				serviceOrder
						.setRescheduleServiceTimeEnd((String) rescheduleDate2
								.get(OrderConstants.GMT_TIME));
			}
		}
		return serviceOrder;
	}

	/**
	 * Add a note to the previous SO
	 * 
	 * @param newSoId
	 * @param oldSoId
	 * @param noteTypeInResourceBundle
	 * @param sc
	 */
	private void addNoteToPreviousSO(String newSoId,String oldSoId, String noteTypeInResourceBundle, SecurityContext sc) {
		try {
			ResourceBundle resBundle = ResourceBundle.getBundle("/resources/properties/servicelive_copy");
			String noteSubject = resBundle.getString("note.subject." + noteTypeInResourceBundle);
			String noteMessage = resBundle.getString("note.message." + noteTypeInResourceBundle);
			Object[] messageArguments = {newSoId};
			MessageFormat formatter = new MessageFormat(noteMessage, Locale.getDefault());
			noteMessage = formatter.format(messageArguments);
			serviceOrderBO.processAddNote(sc.getVendBuyerResId(), sc.getRoleId(), oldSoId,
					noteSubject, noteMessage, OrderConstants.SO_NOTE_GENERAL_TYPE, sc.getUsername(), 
					sc.getUsername(), sc.getCompanyId(), OrderConstants.SO_NOTE_PUBLIC_ACCESS, true, false, sc);
		} catch (Exception e) {
			logger.error("Error addin notes to exisitng service order# " + oldSoId, e);
		}
	}


	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
}
