/**
 *
 */
package com.newco.marketplace.aop;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.incident.IncidentTrackingVO;
import com.newco.marketplace.dto.vo.logging.SoLoggingVo;
import com.newco.marketplace.dto.vo.ordergroup.ChildServiceOrderVO;
import com.newco.marketplace.dto.vo.ordergroup.OrderGroupVO;
import com.newco.marketplace.dto.vo.serviceorder.Buyer;
import com.newco.marketplace.dto.vo.serviceorder.Carrier;
import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.RoutedProvider;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.survey.SurveyVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.interfaces.BuyerFeatureConstants;
import com.newco.marketplace.interfaces.ClientConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.util.so.ServiceOrderUtil;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.buyer.BuyerRegistrationVO;
import com.newco.marketplace.vo.provider.Contact;
import com.newco.marketplace.vo.provider.LostUsernameVO;
import com.newco.marketplace.vo.provider.ProviderRegistrationVO;
/**
 * @author schavda
 *
 */
public class AOPMapper extends BaseAOPMapper {

	private static final Logger logger = Logger.getLogger(AOPMapper.class.getName());

	public AOPMapper() {
		super();
	}

	public AOPMapper(Object[] params){
		super(params);
	}

	/**
	 * This method is called from a Completion Record - EditCompletion
	 * till buyer has not closed it to set it back to Active.
	 */
	public HashMap<String, Object> editCompletionRecordForSo(){
		logger.debug("AOPMapper.editCompletionRecordForSo()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		String strUserName = "";
		SecurityContext ctx = (SecurityContext)params[1];
		if (ctx != null){
			strUserName = ctx.getUsername();
		}
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_USER_NAME, strUserName);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, PROVIDER_ROLEID);
		return hmParams;
	}

	/**
	 * SO Close
	 * */
	public HashMap<String, Object> processCloseSO(){
		logger.debug("AOPMapper.processCloseSO()");
		double finalTotal = 0.0;
		double partsFinalPrice = 0.0;
		double laborFinalPrice = 0.0;

		HashMap<String, Object> hmParams = new AOPHashMap();
		hmParams.put(AOPConstants.AOP_BUYER_ID, params[0]);
		hmParams.put(AOPConstants.AOP_SO_ID, params[1]);

		partsFinalPrice = (params[2] != null)?Double.parseDouble(params[2].toString()):0.0;
		laborFinalPrice = (params[3] != null)?Double.parseDouble(params[3].toString()):0.0;

		hmParams.put(AOPConstants.AOP_PARTS_FINALPRICE, UIUtils.formatDollarAmount(partsFinalPrice));
		hmParams.put(AOPConstants.AOP_LABOR_FINALPRICE, UIUtils.formatDollarAmount(laborFinalPrice));
		finalTotal = partsFinalPrice + laborFinalPrice;
		hmParams.put(AOPConstants.AOP_FINALPRICE_TOTAL, UIUtils.formatDollarAmount(finalTotal));
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, BUYER_ROLEID);
		Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        DateFormat formatter = new SimpleDateFormat( "MM/dd/yyyy" );
        String strDate = formatter.format(date);
        hmParams.put(AOPConstants.AOP_SO_CLOSE_DATE,strDate);
        
		return hmParams;
	}

	public Map<String, Object> processCloseSOPostSOInjection(Map<String, Object> hmParams){
		return updatePostSOInjection(hmParams, null, null);
	}

	/**
	 * SO Cancel in Accepted State
	 * */
	public HashMap<String, Object> processCancelSOInAccepted(){
		logger.debug("AOPMapper.processCancelSOInAccepted()");
		HashMap<String, Object> hmParams = new AOPHashMap();

		hmParams.put(AOPConstants.AOP_BUYER_ID, params[0]);
		hmParams.put(AOPConstants.AOP_SO_ID, params[1]);
		hmParams.put(AOPConstants.AOP_COMMENTS, params[2]);
		hmParams.put(AOPConstants.AOP_USER_NAME, params[3]);
		Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        DateFormat formatter = new SimpleDateFormat( "MM/dd/yyyy" );
        String strDate = formatter.format(date);
        hmParams.put(AOPConstants.AOP_SO_CANCEL_DATE,strDate);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, BUYER_ROLEID);

		return hmParams;
	}

	public Map<String, Object> processCancelSOInAcceptedPostSOInjection(Map<String, Object> hmParams){
		return updatePostSOInjection(hmParams, null, null);
	}

	public HashMap<String, Object> updateSOSubStatus() {
		logger.debug("AOPMapper.updateSOSubStatus()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_SUBSTATUS_ID, params[1]);
		hmParams.put(AOPConstants.AOP_LOGGING_NEWVAL, params[1]);
		SecurityContext ctx = (SecurityContext)params[2];
		int roleType =0;
		if (ctx != null){
			roleType = ctx.getRoleId();
		}
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, roleType);
		return hmParams;
	}

	public Map<String, Object> updateSOSubStatusPostSOInjection(Map<String, Object> hmParams) {
		if (hmParams.containsKey(AOPConstants.AOP_SERVICE_ORDER)) {
			ServiceOrder so = (ServiceOrder) hmParams.get(AOPConstants.AOP_SERVICE_ORDER);
			if (!buyerFeatureSetBO.validateFeature(so.getBuyer().getBuyerId(), BuyerFeatureConstants.SUBSTATUS_ALERTS)) {
				hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_YES);
				hmParams.put(AOPConstants.SKIP_FTP_ALERT, OrderConstants.FLAG_YES);
			}
		}
		return updatePostSOInjection(hmParams, null, null);
	}




	public HashMap<String, Object> assurantAlertForNeedsAttentionSubStatus(){
		logger.debug("AOPMapper.assurantAlertForNeedAttentionSubStatus()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_SUBSTATUS_ID, params[1]);
		hmParams.put(AOPConstants.AOP_LOGGING_NEWVAL, params[1]);

		return hmParams;
	}

	public Map<String, Object> assurantAlertForNeedsAttentionSubStatusPostSOInjection(Map<String, Object> hmParams) {
		if (hmParams.containsKey(AOPConstants.AOP_SERVICE_ORDER)) {
			ServiceOrder so = (ServiceOrder) hmParams.get(AOPConstants.AOP_SERVICE_ORDER);
			if (!buyerFeatureSetBO.validateFeature(so.getBuyer().getBuyerId(), BuyerFeatureConstants.SUBSTATUS_ALERTS)) {
				hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_YES);
			}
		}
		return populateAssurantFieldsForEmailAlert(hmParams);
	}

	private Map<String, Object> populateAssurantFieldsForEmailAlert(Map<String, Object> hmParams) {
		logger.debug("AOPMapper.updateSOSubStatusPostSO()");
		String subStatus = "";
		String status = "";
		String incidentId = "";

		try {
			if (hmParams.containsKey(AOPConstants.AOP_SERVICE_ORDER)) {
				ServiceOrder so = (ServiceOrder) hmParams.get(AOPConstants.AOP_SERVICE_ORDER);
				if (so != null) {
					subStatus = so.getSubStatus();
					status = so.getStatus();
					hmParams.put(AOPConstants.AOP_SUBSTATUS_DESC, subStatus);
					hmParams.put(AOPConstants.AOP_STATUS_DESC, status);
					if (hmParams.get(AOPConstants.AOP_BUYER_ID) == null) {
						hmParams.put(AOPConstants.AOP_BUYER_ID, so.getBuyer().getBuyerId());
					}

					ServiceOrderCustomRefVO customeRefObj =  serviceOrderDao.getCustomReferenceObject(OrderConstants.INCIDENT_REFERNECE_KEY, so.getSoId());
					if(customeRefObj!= null){
						incidentId = customeRefObj.getRefValue();
					}
					hmParams.put(OrderConstants.INCIDENT_REFERNECE_KEY, incidentId);
					if (buyerFeatureSetBO.validateFeature(so.getBuyer().getBuyerId(), BuyerFeatureConstants.ASSURANT_ALERTS)) {
						//populateAssurantFieldsInMap(hmParams, so);

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
	 * SO Cancel in Active State - Send Alert
	 * */
	public HashMap<String, Object> processCancelRequestInActive(){
		logger.debug("AOPMapper.processCancelRequestInActive()");
		HashMap<String, Object> hmParams = new AOPHashMap();

		hmParams.put(AOPConstants.AOP_BUYER_ID, params[0]);
		hmParams.put(AOPConstants.AOP_SO_ID, params[1]);
		double cancelAmt = (params[2] != null)?Double.parseDouble(params[2].toString()):0.00;
		hmParams.put(AOPConstants.AOP_CANCEL_AMT, UIUtils.formatDollarAmount(cancelAmt));
		hmParams.put(AOPConstants.AOP_USER_NAME, params[3]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, BUYER_ROLEID);

		return hmParams;
	}

	public Map<String, Object> processCancelRequestInActivePostSOInjection(Map<String, Object> hmParams){
		return updatePostSOInjection(hmParams, null, null);
	}

	/**
	 * SO Route
	 * */
	public HashMap<String, Object> processRouteSO(){
		logger.debug("AOPMapper.processRouteSO()");
		HashMap<String, Object> hmParams = new AOPHashMap();

		hmParams.put(AOPConstants.AOP_BUYER_ID, params[0]);
		hmParams.put(AOPConstants.AOP_SO_ID, params[1]);
		hmParams.put(AOPConstants.AOP_VENDOR_RESOURCE_LIST, params[2]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, BUYER_ROLEID);
	    Calendar calendar = new GregorianCalendar();
	    Date date = calendar.getTime();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        String strDate = formatter.format(date);
        hmParams.put(AOPConstants.AOP_CURRENT_DATE, strDate);
		return hmParams;
	}

	public Map<String, Object> processRouteSOPostSOInjection(Map<String, Object> hmParams){
		return updatePostSOInjection(hmParams, null, null);
	}

	/**
	 * SO Void
	 * */
	public HashMap<String, Object> processVoidSO(){
		logger.debug("AOPMapper.processVoidSO()");
		HashMap<String, Object> hmParams = new AOPHashMap();

		hmParams.put(AOPConstants.AOP_BUYER_ID, params[0]);
		hmParams.put(AOPConstants.AOP_SO_ID, params[1]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, BUYER_ROLEID);

		return hmParams;
	}

	public Map<String, Object> processVoidSOPostSOInjection(Map<String, Object> hmParams){
		return hmParams;
	}

	/**
	 * SO Void
	 * */
	public HashMap<String, Object> processVoidSOForWS(){
		logger.debug("AOPMapper.processVoidSOForWS()");
		HashMap<String, Object> hmParams = new AOPHashMap();

		hmParams.put(AOPConstants.AOP_BUYER_ID, params[0]);
		hmParams.put(AOPConstants.AOP_SO_ID, params[1]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, BUYER_ROLEID);

		return hmParams;
	}

	public Map<String, Object> processVoidSOForWSPostSOInjection(Map<String, Object> hmParams){
		return updatePostSOInjection(hmParams, null, null);
	}

	/**
	 * SO Add Note
	 * */

	public HashMap<String, Object> processCompleteSO(){
		logger.debug("AOPMapper.processCompleteSO()");
		HashMap<String, Object> hmParams = new AOPHashMap();

		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_RES_DESCR, params[1]);
		hmParams.put(AOPConstants.AOP_PROVIDER_ID, params[2]);

		Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        DateFormat formatter = new SimpleDateFormat( "MM/dd/yyyy" );
        String strDate = formatter.format(date);
        hmParams.put(AOPConstants.AOP_COMPLETION_DATE,strDate);
        hmParams.put(AOPConstants.AOP_ROLE_TYPE, PROVIDER_ROLEID);

		return hmParams;
	}

	public Map<String, Object> processCompleteSOPostSOInjection(Map<String, Object> hmParams){
		return updatePostSOInjection(hmParams, null, null);
	}

	public HashMap<String, Object> processAddNote(){
		logger.debug("AOPMapper.processAddNote()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		if(OrderConstants.BUYER_ROLEID == ((Integer)params[1]).intValue()){
			hmParams.put(AOPConstants.AOP_USER_ID, params[0]);
		}
		else if(OrderConstants.PROVIDER_ROLEID == ((Integer)params[1]).intValue()){
			hmParams.put(AOPConstants.AOP_USER_ID, params[0]);
		}
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, params[1]);
		hmParams.put(AOPConstants.AOP_SO_ID, params[2]);
		hmParams.put(AOPConstants.AOP_NOTE_SUBJECT, params[3]);
		hmParams.put(AOPConstants.AOP_COMMENTS, params[4]);
		hmParams.put(AOPConstants.AOP_NOTE, params[4]);
		hmParams.put(AOPConstants.AOP_NOTE_TYPE, params[5]);
		hmParams.put(AOPConstants.AOP_NOTE_PRIV, params[9]);

		if(OrderConstants.BUYER_ROLEID == ((Integer)params[1]).intValue()){
			hmParams.put(AOPConstants.AOP_ROLE, OrderConstants.BUYER_ROLE);
		} else if(OrderConstants.PROVIDER_ROLEID == ((Integer)params[1]).intValue()) {
			hmParams.put(AOPConstants.AOP_ROLE, OrderConstants.PROVIDER_ROLE);
		} else if(OrderConstants.SIMPLE_BUYER_ROLEID == ((Integer)params[1]).intValue()) {
			hmParams.put(AOPConstants.AOP_ROLE, OrderConstants.SIMPLE_BUYER_ROLE);
		} else if(OrderConstants.NEWCO_ADMIN_ROLEID == ((Integer)params[1]).intValue()) {
			hmParams.put(AOPConstants.AOP_ROLE, OrderConstants.NEWCO_ADMIN_ROLE);
		}

		Date currentDate = new Date();
        String strDate = DateUtils.getFormatedDate(currentDate, "MMM dd, yyyy HH:mm a (z)");
        hmParams.put(AOPConstants.AOP_SUPPORT_NOTE_DATE,strDate);
        SecurityContext sc = (SecurityContext) params[params.length-1];
        if(OrderConstants.PRIVATE_IND_VAL.equals(params[9])
			|| OrderConstants.NEWCO_ADMIN_ROLEID == sc.getRoles().getRoleId()
			|| !"true".equals(params[10].toString())) {
			hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_YES);
			hmParams.put(AOPConstants.SKIP_LOGGING, OrderConstants.FLAG_YES);
		} else {
			hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_NO);
			hmParams.put(AOPConstants.SKIP_LOGGING, OrderConstants.FLAG_NO);
		}
        if(!(OrderConstants.NEWCO_ADMIN_ROLEID == sc.getRoles().getRoleId()) && 
        		OrderConstants.SEND_EMAIL_ALERT.equals(params[10].toString())){
        	hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_NO);
        }
		return hmParams;
	}

	public Map<String, Object> processAddNotePostSOInjection(Map<String, Object> hmParams) {
		if (hmParams.containsKey(AOPConstants.AOP_SERVICE_ORDER)) {
			ServiceOrder so = (ServiceOrder) hmParams.get(AOPConstants.AOP_SERVICE_ORDER);
			int orderStatus = so.getWfStateId();
			if (orderStatus != OrderConstants.ACCEPTED_STATUS && orderStatus != OrderConstants.ACTIVE_STATUS &&
					orderStatus != OrderConstants.COMPLETED_STATUS && orderStatus != OrderConstants.CLOSED_STATUS &&
					orderStatus != OrderConstants.PROBLEM_STATUS) {
				// SL-6096 - Skip Add Note Email, if SO is in these states: (Accepted, Active, Completed, Closed, Problem)
				hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_YES);
			}
		}
		return hmParams;
	}

	public HashMap<String, Object> processSupportAddNote() {
		logger.debug("AOPMapper.processSupportAddNote()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		hmParams.put(AOPConstants.AOP_USER_ID, params[0]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, params[1]);
		hmParams.put(AOPConstants.AOP_SO_ID, params[2]);
		hmParams.put(AOPConstants.AOP_NOTE_SUBJECT, params[3]);
		hmParams.put(AOPConstants.AOP_COMMENTS, params[4]);
		hmParams.put(AOPConstants.AOP_NOTE, params[4]);
		hmParams.put(AOPConstants.AOP_NOTE_TYPE, params[5]);
		hmParams.put(AOPConstants.AOP_NOTE_PRIV, params[9]);

		if(OrderConstants.BUYER_ROLEID == ((Integer)params[1]).intValue()){
			hmParams.put(AOPConstants.AOP_ROLE, OrderConstants.BUYER_ROLE);
		} else if(OrderConstants.PROVIDER_ROLEID == ((Integer)params[1]).intValue()) {
			hmParams.put(AOPConstants.AOP_ROLE, OrderConstants.PROVIDER_ROLE);
		} else if(OrderConstants.SIMPLE_BUYER_ROLEID == ((Integer)params[1]).intValue()) {
			hmParams.put(AOPConstants.AOP_ROLE, OrderConstants.SIMPLE_BUYER_ROLE);
		} else if(OrderConstants.NEWCO_ADMIN_ROLEID == ((Integer)params[1]).intValue()) {
			hmParams.put(AOPConstants.AOP_ROLE, OrderConstants.NEWCO_ADMIN_ROLE);
		}
		Date currentDate = new Date();
        String strDate = DateUtils.getFormatedDate(currentDate, "MMM dd, yyyy HH:mm a (z)");
        hmParams.put(AOPConstants.AOP_SUPPORT_NOTE_DATE,strDate);
		hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_NO);
		hmParams.put(AOPConstants.SKIP_LOGGING, OrderConstants.FLAG_NO);
        return hmParams;
	}


	/**
	 * SO Reschedule
	 * */
	public HashMap<String, Object> updateSOSchedule(){
		logger.debug("AOPMapper.updateSOSchedule()");
		HashMap<String, Object> hmParams = new AOPHashMap();

		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_SCHEDULE_TO, params[1]);
		hmParams.put(AOPConstants.AOP_BUYER_ID, params[2]);

		hmParams.put(AOPConstants.AOP_ROLE_TYPE, BUYER_ROLEID);

		return hmParams;
	}

	public HashMap<String, Object> updateServiceLocation(){
		logger.debug("AOPMapper.updateServiceLocation()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		ServiceOrder so = (ServiceOrder) params[1];
		hmParams.put(AOPConstants.AOP_SO_ID, so.getSoId());
		hmParams.put(AOPConstants.AOP_BUYER_ID, so.getBuyer().getBuyerId());

		hmParams.put(AOPConstants.AOP_ROLE_TYPE, BUYER_ROLEID);

		return hmParams;
	}

	public HashMap<String, Object> processChangePartPickupLocation(){
		logger.debug("AOPMapper.updateServiceLocation()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		ServiceOrder so = (ServiceOrder) params[1];
		hmParams.put(AOPConstants.AOP_SO_ID, so.getSoId());
		hmParams.put(AOPConstants.AOP_BUYER_ID, so.getBuyer().getBuyerId());

		hmParams.put(AOPConstants.AOP_ROLE_TYPE, BUYER_ROLEID);

		return hmParams;
	}

	public HashMap<String, Object> updateServiceContact(){
		logger.debug("AOPMapper.updateServiceContact()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		ServiceOrder so = (ServiceOrder) params[1];
		hmParams.put(AOPConstants.AOP_SO_ID, so.getSoId());
		hmParams.put(AOPConstants.AOP_BUYER_ID, so.getBuyer().getBuyerId());

		hmParams.put(AOPConstants.AOP_ROLE_TYPE, BUYER_ROLEID);

		return hmParams;
	}


	public HashMap<String, Object> updateDescription(){
		logger.debug("AOPMapper.updateDescription()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		ServiceOrder so = (ServiceOrder) params[0];
		hmParams.put(AOPConstants.AOP_SO_ID, so.getSoId());
		hmParams.put(AOPConstants.AOP_BUYER_ID, so.getBuyer().getBuyerId());

		hmParams.put(AOPConstants.AOP_ROLE_TYPE, BUYER_ROLEID);

		return hmParams;
	}

	public HashMap<String, Object> updateSowTitle(){
		logger.debug("AOPMapper.updateSowTitle()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		ServiceOrder so = (ServiceOrder) params[0];
		hmParams.put(AOPConstants.AOP_SO_ID, so.getSoId());
		hmParams.put(AOPConstants.AOP_BUYER_ID, so.getBuyer().getBuyerId());

		hmParams.put(AOPConstants.AOP_ROLE_TYPE, BUYER_ROLEID);

		return hmParams;
	}


	public HashMap<String, Object> updateCustomRef(){
		logger.debug("AOPMapper.updateCustomRef()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		ServiceOrder so = (ServiceOrder) params[1];
		hmParams.put(AOPConstants.AOP_SO_ID, so.getSoId());
		hmParams.put(AOPConstants.AOP_BUYER_ID, so.getBuyer().getBuyerId());

		hmParams.put(AOPConstants.AOP_ROLE_TYPE, BUYER_ROLEID);

		return hmParams;
	}

	public HashMap<String, Object> processChangeOfScope(){
		logger.debug("AOPMapper.updateCustomRef()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		ServiceOrder so = (ServiceOrder)params[0];
		try {
			so = serviceOrderDao.getServiceOrder(so.getSoId());
		} catch (DataServiceException e) {
			logger.error("AOPMapper.processChangeOfScope()" + e.getMessage());
		}
		hmParams.put(AOPConstants.AOP_SO_ID, so.getSoId());
		hmParams.put(AOPConstants.AOP_BUYER_ID, so.getBuyer().getBuyerId());
		hmParams.put(AOPConstants.AOP_STATUS_DESC, so.getStatus());
		hmParams.put(AOPConstants.AOP_SUBSTATUS_DESC, so.getSubStatus());
		hmParams.put(AOPConstants.AOP_SO_SERVICE_LOCATION_TIMEZONE, so.getServiceLocationTimeZone().substring(0, 3));
		hmParams.put(AOPConstants.AOP_SPEND_LIMIT_TO_LABOR, so.getSpendLimitLabor());
		hmParams.put(AOPConstants.AOP_SPEND_LIMIT_TO_PARTS, so.getSpendLimitParts());
		hmParams.put(AOPConstants.AOP_SO_TITLE, so.getSowTitle());
		
		String strZone = so.getServiceLocationTimeZone();
		HashMap<String, Object> serviceDateTime1 = TimeUtils.convertGMTToGivenTimeZone(so.getServiceDate1(), so.getServiceTimeStart(), strZone);		
		HashMap<String, Object> serviceDateTime2 = TimeUtils.convertGMTToGivenTimeZone(so.getServiceDate2(), so.getServiceTimeEnd(), strZone);
        
		hmParams.put(AOPConstants.AOP_SERVICE_DATE1, DateUtils.getFormatedDate((Timestamp) serviceDateTime1.get(OrderConstants.GMT_DATE),"M/d/yyyy"));		
		hmParams.put(AOPConstants.AOP_SERVICE_START_TIME, serviceDateTime1.get(OrderConstants.GMT_TIME));
		hmParams.put(AOPConstants.AOP_SERVICE_DATE2, DateUtils.getFormatedDate((Timestamp) serviceDateTime2.get(OrderConstants.GMT_DATE),"M/d/yyyy"));		
		hmParams.put(AOPConstants.AOP_SERVICE_END_TIME, serviceDateTime2.get(OrderConstants.GMT_TIME));
		

		return hmParams;
	}

	public HashMap<String, Object> updateProviderInstructions(){
		logger.debug("AOPMapper.updateProviderInstructions()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		ServiceOrder so = (ServiceOrder) params[0];
		hmParams.put(AOPConstants.AOP_SO_ID, so.getSoId());
		hmParams.put(AOPConstants.AOP_BUYER_ID, so.getBuyer().getBuyerId());

		hmParams.put(AOPConstants.AOP_ROLE_TYPE, BUYER_ROLEID);

		return hmParams;
	}

	/**
	 * SO Increase Spend Limit
	 * */

	public HashMap<String, Object> updateSOSpendLimit(){
		logger.debug("AOPMapper.updateSOSpendLimit()");
		HashMap<String, Object> hmParams = new AOPHashMap();

		double totalSLLabor = params[1] != null?Double.parseDouble(String.valueOf(params[1])):0.0;
		double totalSLParts = params[2] != null?Double.parseDouble(String.valueOf(params[2])):0.0;
		double totalSL = totalSLLabor + totalSLParts;

		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_SPEND_LIMIT_TO_LABOR, UIUtils.formatDollarAmount(totalSLLabor));
		hmParams.put(AOPConstants.AOP_SPEND_LIMIT_TO_PARTS, UIUtils.formatDollarAmount(totalSLParts));
		if(StringUtils.isNotBlank(params[3].toString())){
			StringBuilder reason = new StringBuilder();
			reason.append("Reason: ");
			reason.append(params[3]);
			hmParams.put(AOPConstants.AOP_SPEND_LIMIT_REASON,reason.toString());
		}else
			hmParams.put(AOPConstants.AOP_SPEND_LIMIT_REASON,AOPConstants.SO_UPDATE_SPEND_LIMIT_NO_REASON);
		
		hmParams.put(AOPConstants.AOP_BUYER_ID, params[4]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, BUYER_ROLEID);
		hmParams.put(AOPConstants.AOP_TOTAL_SPEND_LIMIT, UIUtils.formatDollarAmount(totalSL));
		if(params[6]!=null ){
			//For OMS update separate logging is implemented.Hence skip.
			boolean isOMSUpdate=((Boolean)params[6]).booleanValue();
			if(isOMSUpdate){
				hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_YES);
				hmParams.put(AOPConstants.SKIP_LOGGING, OrderConstants.FLAG_YES);
			}
		}

		return hmParams;
	}

	/**
	 * SO Reject
	 * */
	public HashMap<String, Object> rejectServiceOrder(){
		logger.debug("AOPMapper.rejectServiceOrder()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[1]);
		
		hmParams.put(AOPConstants.AOP_REJECT_REASON_ID, params[2]);
		hmParams.put(AOPConstants.AOP_REJECT_RESPONSE_ID, params[3]);
		hmParams.put(AOPConstants.AOP_REJECT_REASON_DESC, params[5]);

		
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, PROVIDER_ROLEID);

		return hmParams;
	}

	public HashMap<String, Object> sendAllProviderRejectAlert(){
		logger.debug("AOPMapper.sendAllProviderRejectAlert()");
		HashMap<String, Object> hmParams = new AOPHashMap();

		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);

		return hmParams;
	}

	/**
	 * SO Accept
	 * */
	public HashMap<String, Object> processAcceptServiceOrder(){
		logger.debug("AOPMapper.processAcceptServiceOrder()");
		
		ServiceOrder serviceOrder = null; 
		HashMap<String, Object> hmParams = new AOPHashMap();
		
		try {
			serviceOrder = serviceOrderDao.getServiceOrder(params[0].toString());
		} catch (DataServiceException e) {
			logger.error("AOPMapper.processAcceptServiceOrder()" + e.getMessage());
		}
		
		if(serviceOrder.getWfStateId().equals(OrderConstants.EXPIRED_STATUS)){
			hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_YES);
		}

		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_VENDOR_RESOURCE_ID, params[1]);
		hmParams.put(AOPConstants.AOP_PROVIDER_ID, params[2]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE,PROVIDER_ROLEID);

		String strZone = serviceOrder.getServiceLocationTimeZone();
		HashMap<String, Object> serviceDateTime = TimeUtils.convertGMTToGivenTimeZone(serviceOrder.getServiceDate1(), serviceOrder.getServiceTimeStart(), strZone);		
        
		hmParams.put(AOPConstants.AOP_DATE, DateUtils.getFormatedDate((Timestamp) serviceDateTime.get(OrderConstants.GMT_DATE),"M/d/yyyy"));		
		hmParams.put(AOPConstants.AOP_TIME, (String) serviceDateTime.get(OrderConstants.GMT_TIME)+" ("+DateUtils.getFormatedDate(serviceOrder.getServiceDate1(), "z")+")");
		return hmParams;
	}

	public Map<String, Object> processAcceptServiceOrderPostSOInjection(Map<String, Object> hmParams){
		return updatePostSOInjection(hmParams, null, null);
	}

	public HashMap<String, Object> reportProblem(){
		logger.debug("AOPMapper.reportProblem()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_SUBSTATUS_ID, params[1]);
		hmParams.put(AOPConstants.AOP_COMMENT, params[2]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, params[4]);

		if(OrderConstants.BUYER_ROLEID == ((Integer)params[4]).intValue()){
			hmParams.put(AOPConstants.ALERT_TO, AlertConstants.ALERT_ROLE_ACCEPTED_VENDOR_RESOURCE);
		}
		else if(OrderConstants.PROVIDER_ROLEID == ((Integer)params[4]).intValue()){
			hmParams.put(AOPConstants.ALERT_TO, AlertConstants.ALERT_ROLE_BUYER_RESOURCE);
		}

		hmParams.put(AOPConstants.AOP_PROBLEM_DESC, params[5]);
		hmParams.put(AOPConstants.AOP_USER_NAME, params[6]);
		Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        DateFormat formatter = new SimpleDateFormat( "MM/dd/yyyy" );
        String strDate = formatter.format(date);
        hmParams.put(AOPConstants.AOP_PROBLEM_REPORT_DATE,strDate);
        if(params[7]!=null ){
			//For OMS update separate alerting is implemented.Hence skip.
			boolean isOMSUpdate=((Boolean)params[7]).booleanValue();
			if(isOMSUpdate){
				hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_YES);
			}
		}
        return hmParams;
	}

	public Map<String, Object> reportProblemPostSOInjection(Map<String, Object> hmParams){
		return updatePostSOInjection(hmParams, null, null);
	}

	public HashMap<String, Object> reportResolution(){
		logger.debug("AOPMapp.->reportResolution()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_SUBSTATUS_ID, params[1]);
		hmParams.put(AOPConstants.AOP_COMMENT, params[2]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, params[4]);

		if(OrderConstants.BUYER_ROLEID == ((Integer)params[4]).intValue()){
			hmParams.put(AOPConstants.ALERT_TO, AlertConstants.ALERT_ROLE_ACCEPTED_VENDOR_RESOURCE);
		}
		else if(OrderConstants.PROVIDER_ROLEID == ((Integer)params[4]).intValue()){
			hmParams.put(AOPConstants.ALERT_TO, AlertConstants.ALERT_ROLE_BUYER_RESOURCE);
		}

		hmParams.put(AOPConstants.AOP_PROBLEM_DESC, params[5]);
		hmParams.put(AOPConstants.AOP_PROBLEM_DETAILS, params[6]);
		hmParams.put(AOPConstants.AOP_USER_NAME, params[7]);
		return hmParams;
	}

	public HashMap<String, Object> requestRescheduleSO(){
		logger.debug("AOPMapper.requestRescheduleSO()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_NEW_START_DATE, params[1]);
		hmParams.put(AOPConstants.AOP_NEW_END_DATE, params[2]);
		hmParams.put(AOPConstants.AOP_NEW_START_TIME, params[3]);
		hmParams.put(AOPConstants.AOP_NEW_END_TIME, params[4]);
		hmParams.put(AOPConstants.AOP_SUBSTATUS_ID, params[5]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, params[6]);

		if(OrderConstants.BUYER_ROLEID == ((Integer)params[6]).intValue()){
			hmParams.put(AOPConstants.AOP_ROLE, "BUYER");
			hmParams.put(AOPConstants.ALERT_TO, AlertConstants.ALERT_ROLE_ACCEPTED_VENDOR_RESOURCE);
		}
		else if(OrderConstants.PROVIDER_ROLEID == ((Integer)params[6]).intValue()){
			hmParams.put(AOPConstants.AOP_ROLE, "PROVIDER");
			hmParams.put(AOPConstants.ALERT_TO, AlertConstants.ALERT_ROLE_BUYER_RESOURCE);
		}
		hmParams.put(AOPConstants.AOP_COMPANY_ID, params[7]);
		return hmParams;
	}

	public HashMap<String, Object> rescheduleSOComments(){
		logger.debug("AOPMapper.requestRescheduleSO()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_NEW_START_DATE, params[1]);
		hmParams.put(AOPConstants.AOP_NEW_END_DATE, params[2]);
		hmParams.put(AOPConstants.AOP_NEW_START_TIME, params[3]);
		hmParams.put(AOPConstants.AOP_NEW_END_TIME, params[4]);
		hmParams.put(AOPConstants.AOP_SUBSTATUS_ID, params[5]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, params[6]);

		if(OrderConstants.BUYER_ROLEID == ((Integer)params[6]).intValue()){
			hmParams.put(AOPConstants.AOP_ROLE, "BUYER");
			hmParams.put(AOPConstants.ALERT_TO, AlertConstants.ALERT_ROLE_ACCEPTED_VENDOR_RESOURCE);
		}
		else if(OrderConstants.PROVIDER_ROLEID == ((Integer)params[6]).intValue()){
			hmParams.put(AOPConstants.AOP_ROLE, "PROVIDER");
			hmParams.put(AOPConstants.ALERT_TO, AlertConstants.ALERT_ROLE_BUYER_RESOURCE);
		}
		hmParams.put(AOPConstants.AOP_COMPANY_ID, params[7]);
		if(params[9]!=null){
			hmParams.put(AOPConstants.AOP_COMMENT, params[9]);
		}
		return hmParams;
	}

	public HashMap<String, Object> respondToRescheduleRequest(){
		logger.debug("AOPMapper.respondToRescheduleRequest()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		if(true == ((Boolean)params[1]).booleanValue()){
			hmParams.put(AOPConstants.AOP_RESPONSE_TO_RESCHEDULE, "ACCEPTED");
		}else{
			hmParams.put(AOPConstants.AOP_RESPONSE_TO_RESCHEDULE, "DECLINED");
		}
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, params[2]);

		if(OrderConstants.BUYER_ROLEID == ((Integer)params[2]).intValue()){
			hmParams.put(AOPConstants.AOP_ROLE, "BUYER");
			hmParams.put(AOPConstants.ALERT_TO, AlertConstants.ALERT_ROLE_ACCEPTED_VENDOR_RESOURCE);
		}
		else if(OrderConstants.PROVIDER_ROLEID == ((Integer)params[2]).intValue()){
			hmParams.put(AOPConstants.AOP_ROLE, "PROVIDER");
			hmParams.put(AOPConstants.ALERT_TO, AlertConstants.ALERT_ROLE_BUYER_RESOURCE);
		}

		hmParams.put(AOPConstants.AOP_COMPANY_ID, params[3]);
		return hmParams;
	}

	//splitting the methods for accept or reject reschedule..start

	public HashMap<String, Object> acceptRescheduleRequest(){
		logger.debug("AOPMapper.acceptRescheduleRequest()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);

			hmParams.put(AOPConstants.AOP_RESPONSE_TO_RESCHEDULE, "ACCEPTED");

		hmParams.put(AOPConstants.AOP_ROLE_TYPE, params[2]);

		if(OrderConstants.BUYER_ROLEID == ((Integer)params[2]).intValue()){
			hmParams.put(AOPConstants.AOP_ROLE, "BUYER");
			hmParams.put(AOPConstants.ALERT_TO, AlertConstants.ALERT_ROLE_ACCEPTED_VENDOR_RESOURCE);
		}
		else if(OrderConstants.PROVIDER_ROLEID == ((Integer)params[2]).intValue()){
			hmParams.put(AOPConstants.AOP_ROLE, "PROVIDER");
			hmParams.put(AOPConstants.ALERT_TO, AlertConstants.ALERT_ROLE_BUYER_RESOURCE);
		}

		hmParams.put(AOPConstants.AOP_COMPANY_ID, params[3]);
		
		Contact contact1;
		try {
			String soId = (String) params[0];
			ServiceOrder serviceOrder1 = serviceOrderDao.getServiceOrder(soId);
			if (serviceOrder1 != null) {
				contact1 = contactDao.getByBuyerId(((Buyer) serviceOrder1
						.getBuyer()).getBuyerId());
				if (contact1 != null) {
					hmParams.put(AOPConstants.AOP_USERNAME, contact1
							.getFirstName()
							+ " " + contact1.getLastName());
				}
			}
		} catch (DBException e) {
			e.printStackTrace();
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		
		return hmParams;
	}

	public HashMap<String, Object> rejectRescheduleRequest(){
		logger.debug("AOPMapper.rejectRescheduleRequest()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);

			hmParams.put(AOPConstants.AOP_RESPONSE_TO_RESCHEDULE, "DECLINED");

		hmParams.put(AOPConstants.AOP_ROLE_TYPE, params[2]);

		if(OrderConstants.BUYER_ROLEID == ((Integer)params[2]).intValue()){
			hmParams.put(AOPConstants.AOP_ROLE, "BUYER");
			hmParams.put(AOPConstants.ALERT_TO, AlertConstants.ALERT_ROLE_ACCEPTED_VENDOR_RESOURCE);
		}
		else if(OrderConstants.PROVIDER_ROLEID == ((Integer)params[2]).intValue()){
			hmParams.put(AOPConstants.AOP_ROLE, "PROVIDER");
			hmParams.put(AOPConstants.ALERT_TO, AlertConstants.ALERT_ROLE_BUYER_RESOURCE);
		}

		hmParams.put(AOPConstants.AOP_COMPANY_ID, params[3]);
		
		Contact contact1;
		try {
			String soId = (String) params[0];
			ServiceOrder serviceOrder1 = serviceOrderDao.getServiceOrder(soId);
			if (serviceOrder1 != null) {
				contact1 = contactDao.getByBuyerId(((Buyer) serviceOrder1
						.getBuyer()).getBuyerId());
				if (contact1 != null) {
					hmParams.put(AOPConstants.AOP_USERNAME, contact1
							.getFirstName()
							+ " " + contact1.getLastName());
				}
			}
		} catch (DBException e) {
			e.printStackTrace();
		} catch (DataServiceException e) {
			e.printStackTrace();
		}
		
		return hmParams;
	}

	public HashMap<String, Object> cancelRescheduleRequest(){
		logger.debug("AOPMapper.cancelRescheduleRequest()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, params[1]);

		if(OrderConstants.BUYER_ROLEID == ((Integer)params[1]).intValue()){
			hmParams.put(AOPConstants.AOP_ROLE, "BUYER");
			hmParams.put(AOPConstants.ALERT_TO, AlertConstants.ALERT_ROLE_ACCEPTED_VENDOR_RESOURCE);
		}
		else if(OrderConstants.PROVIDER_ROLEID == ((Integer)params[1]).intValue()){
			hmParams.put(AOPConstants.AOP_ROLE, "PROVIDER");
			hmParams.put(AOPConstants.ALERT_TO, AlertConstants.ALERT_ROLE_BUYER_RESOURCE);
		}

		hmParams.put(AOPConstants.AOP_COMPANY_ID, params[2]);
		return hmParams;
	}

	public HashMap<String, Object> processCreateConditionalOffer(){
		logger.debug("AOPMapper.processCreateConditionalOffer()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_VENDOR_RESOURCE_ID, params[1]);
		hmParams.put(AOPConstants.AOP_PROVIDER_ID, params[2]);
		hmParams.put(AOPConstants.AOP_SCHEDULE_FROM, params[3]);
		hmParams.put(AOPConstants.AOP_SCHEDULE_TO, params[4]);
		hmParams.put(AOPConstants.AOP_CONDITIONAL_CHANGE_START_TIME, params[5]);
		hmParams.put(AOPConstants.AOP_CONDITIONAL_CHANGE_END_TIME, params[6]);
		hmParams.put(AOPConstants.AOP_CONDITIONAL_EXPIRATION_DATE, params[7]);
		double finalLaborSL = (params[8] != null)?Double.parseDouble(params[8].toString()):0.00;
		hmParams.put(AOPConstants.AOP_SPEND_LIMIT_TO_LABOR, UIUtils.formatDollarAmount(finalLaborSL));
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, OrderConstants.PROVIDER_ROLEID);
		hmParams.put(AOPConstants.AOP_COUNTER_OFFER_DETAILS, OrderConstants.PROVIDER_ROLEID);

		if(params[3]==null&&params[4]==null)
        {
			hmParams.put(AOPConstants.AOP_COUNTER_OFFER_DETAILS,"<br>"+"PROPOSED LABOR SPEND LIMIT: "+UIUtils.formatDollarAmount(finalLaborSL));	
        }else
        	if(finalLaborSL==0&&params[6]!=null)
            {
    			hmParams.put(AOPConstants.AOP_COUNTER_OFFER_DETAILS,"<br>"+"PROPOSED SCHEDULE DATE: "+params[3]
    					+"-"+params[4]+"<br>"+
    			"PROPOSED SCHEDULE TIME: "+params[5]
    		  +"-"+params[6]);
    			
            }
        	else 
        		if(finalLaborSL==0&&params[6]==null)
        	{
        		hmParams.put(AOPConstants.AOP_COUNTER_OFFER_DETAILS,"<br>"+"PROPOSED SCHEDULE DATE: "+params[3]
    					+"<br>"+
    			"PROPOSED SCHEDULE TIME: "+params[5]
    		          		);
        		
        	}
		else if(finalLaborSL!=0&&params[6]==null)
    	{
    		hmParams.put(AOPConstants.AOP_COUNTER_OFFER_DETAILS,"<br>"+"PROPOSED SCHEDULE DATE: "+params[3]
					+"-"+params[4]+"<br>"+
			"PROPOSED SCHEDULE TIME: "+params[5]
		  +
		  "<br>"+"PROPOSED LABOR SPEND LIMIT: "+UIUtils.formatDollarAmount(finalLaborSL)
    		);
    		
    	}
		else
    	{
    		hmParams.put(AOPConstants.AOP_COUNTER_OFFER_DETAILS,"<br>"+"PROPOSED SCHEDULE DATE: "+params[3]
					+"-"+params[4]+"<br>"+
			"PROPOSED SCHEDULE TIME: "+params[5]
		  +"-"+params[6]+
		  "<br>"+"PROPOSED LABOR SPEND LIMIT: "+UIUtils.formatDollarAmount(finalLaborSL)
    		);
    		
    	}
		return hmParams;
	}

	public HashMap<String, Object> acceptConditionalOffer(){

		logger.debug("AOPMapper.acceptConditionalOffer()");
		ServiceOrder serviceOrder=null;
		HashMap<String, Object> hmParams = new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);

		try {
			serviceOrder = serviceOrderDao.getServiceOrder(params[0].toString());
		} catch (DataServiceException e) {
			logger.error("AOPMapper.acceptConditionalOffer()" + e.getMessage());
		}
		String bidComment="";
		if (null != serviceOrder.getRoutedResources()
				&& (!serviceOrder.getRoutedResources().isEmpty())) {
			Iterator<RoutedProvider> soRouteList = serviceOrder
					.getRoutedResources().iterator();
			while (soRouteList.hasNext()) {
				
				RoutedProvider soRoutedProvider = soRouteList.next();
				if(null!=soRoutedProvider.getResourceId()&&soRoutedProvider.getResourceId().equals(params[1])&&
						soRoutedProvider.getProviderRespId()!=null&&soRoutedProvider.getProviderRespId().equals(2)){
					bidComment=StringUtils.isEmpty(soRoutedProvider
							.getProviderRespComment()) ? "" : soRoutedProvider
									.getProviderRespComment();
				}
			}
		}
		hmParams.put(AOPConstants.AOP_BID_COMMENT, bidComment);

		hmParams.put(AOPConstants.AOP_VENDOR_RESOURCE_ID, params[1]);
		hmParams.put(AOPConstants.AOP_PROVIDER_ID, params[2]);
		hmParams.put(AOPConstants.AOP_RESPONSE_REASON_ID, params[3]);
		if(params[4] != null
				&& params[5] != null){
			hmParams.put(AOPConstants.AOP_RESCHEDULE_SERVICE_DATE,
					DateUtils.getFormatedDate((Date)params[4],"MMM d, yyyy") +" - "+DateUtils.getFormatedDate((Date)params[5],"MMM d, yyyy"));
		}else if(params[4] != null){
			hmParams.put(AOPConstants.AOP_RESCHEDULE_SERVICE_DATE, DateUtils.getFormatedDate((Date)params[4],"MMM d, yyyy"));
		}else if(params[4] == null                                                                                                                   //For SL-9215
				&& params[5] == null){
			hmParams.put(AOPConstants.AOP_RESCHEDULE_SERVICE_DATE,
					DateUtils.getFormatedDate((Date)serviceOrder.getServiceDate1(),"MMM d, yyyy") +" - "
					+DateUtils.getFormatedDate((Date)serviceOrder.getServiceDate2(),"MMM d, yyyy"));
		}else if(params[4] == null){
			hmParams.put(AOPConstants.AOP_RESCHEDULE_SERVICE_DATE, 
								DateUtils.getFormatedDate((Date)serviceOrder.getServiceDate1(),"MMM d, yyyy") );
		}/*else{
			hmParams.put(AOPConstants.AOP_RESCHEDULE_SERVICE_DATE, AOPConstants.AOP_NOT_APPLICABLE);
		}*/
		if(params[6] != null
				&& StringUtils.isNotEmpty((String)params[6])
				&& params[7] != null
				&& StringUtils.isNotEmpty((String)params[7])){
			hmParams.put(AOPConstants.AOP_RESCHEDULE_SERVICE_TIME, params[6] + " - "+params[7]);
		}else if(params[6] != null
				&& StringUtils.isNotEmpty((String)params[6])){
			hmParams.put(AOPConstants.AOP_RESCHEDULE_SERVICE_TIME, params[6]);
		}else if(params[6] == null                                                  																//For SL-9215                                                                          
				&& StringUtils.isEmpty((String)params[6])
				&& params[7] == null
				&& StringUtils.isEmpty((String)params[7])){
			hmParams.put(AOPConstants.AOP_RESCHEDULE_SERVICE_TIME, 
					serviceOrder.getServiceTimeStart() + " - "+serviceOrder.getServiceTimeEnd());
		}else if(params[6] == null
				&& StringUtils.isEmpty((String)params[6])){
			hmParams.put(AOPConstants.AOP_RESCHEDULE_SERVICE_TIME, 
					serviceOrder.getServiceTimeStart());
		}/*else{
			hmParams.put(AOPConstants.AOP_NEW_START_TIME, AOPConstants.AOP_NOT_APPLICABLE);
		}*/
		if(params[8] != null){
			hmParams.put(AOPConstants.AOP_SPEND_LIMIT_TO_LABOR, UIUtils.formatDollarAmount(Double.parseDouble(params[8].toString())));
		}else if(params[8] == null){
			hmParams.put(AOPConstants.AOP_SPEND_LIMIT_TO_LABOR,serviceOrder.getLaborFinalPrice() + 
					serviceOrder.getPartsFinalPrice());
		}/*else{
			hmParams.put(AOPConstants.AOP_SPEND_LIMIT_TO_LABOR, AOPConstants.AOP_NOT_APPLICABLE);
		}*/
		hmParams.put(AOPConstants.AOP_BUYER_ID, params[9]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, 3);

		return hmParams;

	}

	public Map<String, Object> acceptConditionalOfferPostSOInjection(Map<String, Object> hmParams){
		return updatePostSOInjection(hmParams, null, null);
	}

	public HashMap<String, Object> withdrawConditionalAcceptance(){
		logger.debug("AOPMapper.withdrawConditionalAcceptance()");
		HashMap<String, Object> hmParams=new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_PROVIDER_ID, params[1]);
		hmParams.put(AOPConstants.AOP_PROVIDER_RESP_ID, params[2]);
        hmParams.put(AOPConstants.AOP_ROLE_TYPE, 1);
       	return hmParams;

	}

	public HashMap<String, Object> processCreateDraftSO(){
			logger.debug("AOPMapper.processCreateDraftSO()");
			HashMap<String, Object> hmParams = new AOPHashMap();
			if(params[0] != null)
				hmParams.put(AOPConstants.AOP_SO_ID, ((ServiceOrder)params[0]).getSoId());
			if(((ServiceOrder)params[0]).getBuyer() != null)
				hmParams.put(AOPConstants.AOP_BUYER_ID, ((ServiceOrder)params[0]).getBuyer().getBuyerId());

			hmParams.put(AOPConstants.AOP_ROLE_TYPE, OrderConstants.BUYER_ROLEID);

			return hmParams;
	}
	
	public HashMap<String, Object> processCreateBid() {
		logger.debug("AOPMapper-->processCreateBid()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		if(params[0] != null)
			hmParams.put(AOPConstants.AOP_SO_ID, params[0]);

		hmParams.put(AOPConstants.AOP_ROLE_TYPE, OrderConstants.PROVIDER_ROLEID);
		return hmParams;
	}

	public Map<String, Object> processCreateDraftSOPostSOInjection(Map<String, Object> hmParams){
		return updatePostSOInjection(hmParams, null, null);
	}

	public HashMap<String, Object> releaseServiceOrderInActive(){
		logger.debug("AOPMapper.releaseServiceOrder()");
		HashMap<String, Object> hmParams=new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_REASON_CODE, params[1]);
		hmParams.put(AOPConstants.AOP_PROVIDER_COMMENT, params[2]);
		hmParams.put(AOPConstants.AOP_PROVIDER_ID, params[3]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, 1);
		Date currentDate = new Date();
		String strDate = DateUtils.getFormatedDate(currentDate, "MMM dd, yyyy");	 	
                hmParams.put(AOPConstants.AOP_SO_RELEASE_DATE,strDate);
		return hmParams;
	}

	public HashMap<String, Object> releaseServiceOrderInAccepted(){
		logger.debug("AOPMapper.releaseServiceOrder()");
		HashMap<String, Object> hmParams=new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_REASON_CODE, params[1]);
		hmParams.put(AOPConstants.AOP_PROVIDER_COMMENT, params[2]);
		hmParams.put(AOPConstants.AOP_PROVIDER_ID, params[3]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, 1);
		Date currentDate = new Date();
		String strDate = DateUtils.getFormatedDate(currentDate, "MMM dd, yyyy");
        hmParams.put(AOPConstants.AOP_SO_RELEASE_DATE,strDate);
		return hmParams;
	}
	public HashMap<String, Object> releaseServiceOrderInProblem(){
		logger.debug("AOPMapper.releaseServiceOrder()");
		HashMap<String, Object> hmParams=new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_REASON_CODE, params[1]);
		hmParams.put(AOPConstants.AOP_PROVIDER_COMMENT, params[2]);
		hmParams.put(AOPConstants.AOP_PROVIDER_ID, params[3]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, 1);
		Date currentDate = new Date();
		String strDate = DateUtils.getFormatedDate(currentDate, "MMM dd, yyyy");
        hmParams.put(AOPConstants.AOP_SO_RELEASE_DATE,strDate);
		return hmParams;
	}

	public HashMap<String, Object> releaseSOProviderAlert(){
		logger.debug("AOPMapper-->releaseServiceOrder()");
		HashMap<String, Object> hmParams=new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, OrderConstants.NEWCO_ADMIN_ROLEID);
		hmParams.put(AOPConstants.AOP_CREATED_BY_NAME, "SYSTEM");

		return hmParams;
	}

	public HashMap<String, Object> deleteDraftSO(){
		logger.debug("AOPMapper.deleteDraftSO()");
		HashMap<String, Object> hmParams=new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, BUYER_ROLEID);
		return hmParams;
	}

	public Map<String, Object> deleteDraftSOPostSOInjection(Map<String, Object> hmParams) {
		return updatePostSOInjection(hmParams, null, null);
	}

	public HashMap<String, Object> activateAcceptedSO(){
		logger.debug("AOPMapper.activateAcceptedSO()");
		HashMap<String, Object> hmParams=new AOPHashMap();
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, OrderConstants.NEWCO_ADMIN_ROLEID);
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
	    hmParams.put(AOPConstants.AOP_CREATED_BY_NAME, "SYSTEM");

		return hmParams;
	}

	public HashMap<String, Object> expirePostedSO(){
		logger.debug("AOPMapper.expirePostedSO()");
		HashMap<String, Object> hmParams=new AOPHashMap();
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, OrderConstants.NEWCO_ADMIN_ROLEID);
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
	    hmParams.put(AOPConstants.AOP_CREATED_BY_NAME, "SYSTEM");
	    Calendar calendar = new GregorianCalendar();
	    Date date = calendar.getTime();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        String strDate = formatter.format(date);
	    hmParams.put(AOPConstants.AOP_SO_EXPIRED_DATE, strDate);
		return hmParams;
	}

	public HashMap<String, Object> expireConditionalOffer(){
		logger.debug("AOPMapper.expireConditionalOffer()");
		HashMap<String, Object> hmParams=new AOPHashMap();
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, OrderConstants.NEWCO_ADMIN_ROLEID);
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
	    hmParams.put(AOPConstants.AOP_CREATED_BY_NAME, "SYSTEM");
	    Calendar calendar = new GregorianCalendar();
	    Date date = calendar.getTime();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        String strDate = formatter.format(date);
	    hmParams.put(AOPConstants.AOP_CONDITIONAL_OFFER_EXPIRED_DATE, strDate);
		return hmParams;
	}

	public HashMap<String, Object> processUpdateDraftSO(){
		logger.debug("AOPMapper.processUpdateDraftSO()");
		HashMap<String, Object> hmParams=new AOPHashMap();
		ServiceOrder serviceOrder = (ServiceOrder)params[0];
		if(serviceOrder != null){
			hmParams.put(AOPConstants.AOP_SO_ID, serviceOrder.getSoId());
		}
		if(serviceOrder.getBuyer() != null){
			hmParams.put(AOPConstants.AOP_BUYER_ID, serviceOrder.getBuyer().getBuyerId());
		}
		if(serviceOrder != null
				&& serviceOrder.getIsEditMode() == Boolean.TRUE)
			hmParams.put(AOPConstants.AOP_ACTION_NAME, AOPConstants.AOP_SO_EDITED);
		else
			hmParams.put(AOPConstants.AOP_ACTION_NAME, AOPConstants.AOP_SO_CREATED);

		 hmParams.put(AOPConstants.AOP_ROLE_TYPE, OrderConstants.BUYER_ROLEID);

		 return hmParams;
	}

	public Map<String, Object> processUpdateDraftSOPostSOInjection(Map<String, Object> aopParams) {
		String clientIncidentId = null;
		try {
			ServiceOrder so = (ServiceOrder) aopParams.get(AOPConstants.AOP_SERVICE_ORDER);
			clientIncidentId = ServiceOrderUtil.getCustomReferenceValueByType(so, OrderConstants.INCIDENT_REFERNECE_KEY);
			SecurityContext securityContext = (SecurityContext)aopParams.get(AOPConstants.AOP_SECURITY_CONTEXT);
			if (StringUtils.isNotBlank(clientIncidentId)) {
				Integer slIncidentId = getSLIncidentId(clientIncidentId, securityContext.getClientId(), so.getSoId(), so.getBuyer().getBuyerId());
				IncidentTrackingVO lastTracking = incidentBO.getLastTrackingForIncident(slIncidentId, securityContext);
				aopParams.put(OrderConstants.SL_INCIDENT_REFERNECE_KEY, slIncidentId);
				//Existing incident, send closed,SP Reopen and Parts Pending / Sp Reopen and parts peding outfile
				aopParams = updateParamsForSpReOpen(aopParams, lastTracking, securityContext);
			}
		} 
		catch (BusinessServiceException e) {
			logger.error("Error finding last tracking information for incident ID# " + clientIncidentId, e);
		}
		catch (Exception e) {
			logger.error("Error in processUpdateDraftSOPostSOInjection()", e);
		}
		return aopParams;
	}

	
	public HashMap<String, Object> processReRouteSO(){
		logger.debug("AOPMapper.processReRouteSO()");
		HashMap<String, Object> hmParams=new AOPHashMap();
		hmParams.put(AOPConstants.AOP_BUYER_ID, params[0]);
		hmParams.put(AOPConstants.AOP_SO_ID, params[1]);
		//Date currentDate = new Date();
        //String strDate = DateUtils.getFormatedDate(currentDate, "MMM dd, yyyy");
	    Calendar calendar = new GregorianCalendar();
	    Date date = calendar.getTime();
        DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        String strDate = formatter.format(date);
        hmParams.put(AOPConstants.AOP_CURRENT_DATE, strDate);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, OrderConstants.BUYER_ROLEID);
		logger.info("Inside processReRouteSO-> before params[2]: " + params[2]); 
        if(params[2]!=null ){
 			//For OMS update separate logging is implemented.Hence skip.
			boolean isOMSUpdate=((Boolean)params[2]).booleanValue();
			if(isOMSUpdate){
				hmParams.put(AOPConstants.SKIP_LOGGING, OrderConstants.FLAG_YES);
			}
		}
		return hmParams;
	}

	public Map<String, Object> processReRouteSOPostSOInjection(Map<String, Object> hmParams){
		//Adds the Dispatch out file after posting
		updatePostSOInjection(hmParams, null, null);
		//Adds Parts Shipped out file after posting if there is tracking number for any parts
		if (hmParams.containsKey(AOPConstants.AOP_SERVICE_ORDER)) {
			ServiceOrder so = (ServiceOrder) hmParams.get(AOPConstants.AOP_SERVICE_ORDER);
			if (so != null && so.getParts().size() > 0) {
				List<Part> parts = so.getParts();
				for (Part part : parts) {
					Carrier shippingCarrier = part.getShippingCarrier();
					if (shippingCarrier != null && StringUtils.isNotBlank(shippingCarrier.getTrackingNumber())) {
						updatePostSOInjection(hmParams, ClientConstants.PARTS_SHIPPED_STATUS, ClientConstants.PARTS_SHIPPED_COMMENTS);
					}
					break;
				}
			}
		}
		return hmParams;
	}

	public HashMap<String, Object> saveResponse(){
		logger.debug("AOPMapper.saveResponse()");
		HashMap<String, Object> hmParams=new AOPHashMap();

		if(params[0] != null)
			hmParams.put(AOPConstants.AOP_SO_ID, ((SurveyVO)params[0]).getServiceOrderID());

		if(params[0] != null)
			hmParams.put(AOPConstants.AOP_SURVEY_RATING_SCORE, ((SurveyVO)params[0]).getOverallScore());

		if(params[0] != null)
			hmParams.put(AOPConstants.AOP_SURVEY_RATING_COMMENT, ((SurveyVO)params[0]).getSurveyComments());
		if(params[0] != null){
			if(((SurveyVO)params[0]).getEntityType() == OrderConstants.BUYER_ROLE){
				hmParams.put(AOPConstants.AOP_ROLE, OrderConstants.PROVIDER_ROLE);
				hmParams.put(AOPConstants.AOP_ROLE_TYPE, OrderConstants.BUYER_ROLEID);
			}
			else if(((SurveyVO)params[0]).getEntityType() == OrderConstants.PROVIDER_ROLE){
				hmParams.put(AOPConstants.AOP_ROLE, OrderConstants.BUYER_ROLE);
				hmParams.put(AOPConstants.AOP_ROLE_TYPE, OrderConstants.PROVIDER_ROLEID);
			}
		}

		return hmParams;
	}

	/**
	 * SO Reassign
	 * */
	public HashMap<String, Object> saveReassignSO(){
		logger.debug("AOPMapper.saveReassignSO()");
		HashMap<String, Object> hmParams = new AOPHashMap();

		hmParams.put(AOPConstants.AOP_SO_ID,((SoLoggingVo)params[0]).getServiceOrderNo());
		hmParams.put(AOPConstants.AOP_USER_NAME, ((SoLoggingVo)params[0]).getCreatedByName());
		hmParams.put(AOPConstants.AOP_VENDOR_RESOURCE_ID, ((SoLoggingVo)params[0]).getNewValue());
		hmParams.put(AOPConstants.AOP_PROVIDER_ID,((SoLoggingVo)params[0]).getEntityId());
		hmParams.put(AOPConstants.AOP_SO_REASON,params[2]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE,PROVIDER_ROLEID);
		return hmParams;
	}

	public HashMap<String, Object> sendallProviderResponseExceptAccepted(){
		logger.debug("AOPMapper.sendallProviderResponseExceptAccepted()");
		
		ServiceOrder serviceOrder = null;
		HashMap<String, Object> hmParams=new AOPHashMap();
		
		try {
			serviceOrder = serviceOrderDao.getServiceOrder(params[0].toString());
		} catch (DataServiceException e) {
			logger.error("AOPMapper.sendallProviderResponseExceptAccepted()" + e.getMessage());
		}
		
		if(serviceOrder.getWfStateId().equals(OrderConstants.EXPIRED_STATUS)){
			hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_YES);
		}

		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);

		return hmParams;

	}

	public HashMap<String, Object> processRouteOrderGroup(){
		logger.debug("AOPMapper.processRouteOrderGroup()");
		HashMap<String, Object> hmParams=new AOPHashMap();
		@SuppressWarnings("unchecked")
		List<ServiceOrderSearchResultsVO> soList = (List<ServiceOrderSearchResultsVO>) params[0];
		String soGroupId =  soList.get(0).getParentGroupId();
		hmParams.put(AOPConstants.AOP_SO_GROUP_ID, soGroupId);
		//hmParams.put("serviceOrderList", params[0]);
		hmParams.put("orderType", params[1]);

		return hmParams;
	}

	public HashMap<String, Object> routeGroupToSelectedProviders(){
		logger.debug("AOPMapper.routeGroupToSelectedProviders()");
		HashMap<String, Object> hmParams=new AOPHashMap();
		//List<ServiceOrderSearchResultsVO> soList  =  (List<ServiceOrderSearchResultsVO>) params[0];
		String soGroupId = (String) params[2];
		hmParams.put(AOPConstants.AOP_SO_GROUP_ID, soGroupId);
		//hmParams.put("serviceOrderList", params[0]);
		//hmParams.put("orderType", params[1]);

		return hmParams;
	}



	public HashMap<String, Object> sendallProviderResponseExceptAcceptedForGroup(){
		logger.debug("AOPMapper.sendallProviderResponseExceptAcceptedForGroup()");
		HashMap<String, Object> hmParams=new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_GROUP_ID, params[0]);


		return hmParams;

	}

	/**
	 * Populate Service Order Object
	 * */
	public Map<String, Object> mapGroupOrder(Map<String, Object> hmParams, OrderGroupVO orderGroup,SecurityContext securityContext){
		logger.debug("AOPMapper.mapGroupOrder()");

		List<ServiceOrderSearchResultsVO> soChildList = new ArrayList<ServiceOrderSearchResultsVO>();
		String groupId = orderGroup.getGroupId();
		try{

			hmParams.put(AOPConstants.AOP_SO_GROUP_ID, groupId);
			hmParams.put(AOPConstants.AOP_SO_ID, groupId);
			soChildList = orderGroupDAO.getServiceOrdersForGroup(groupId);

			//soChildList = (List<ServiceOrderSearchResultsVO>)hmParams.get("serviceOrderList");
			String childSoId = soChildList.get(0).getSoId();
			ServiceOrder firstchildSO = serviceOrderDao.getServiceOrder(childSoId);
			if(firstchildSO != null ){
				if(firstchildSO.getRoutedDate()!= null){
						//hmParams.put(AOPConstants.AOP_SO_GROUP_ROUTED_DATE, TimeUtils.convertToTimezone(firstchildSO.getRoutedDate(),OrderConstants.SERVICELIVE_ZONE));
						hmParams.put(AOPConstants.AOP_SO_ROUTED_DATE, TimeUtils.convertToTimezone(firstchildSO.getRoutedDate(),OrderConstants.SERVICELIVE_ZONE));
				}
				if(firstchildSO.getSkill() != null){
						//hmParams.put(AOPConstants.AOP_SO_GROUP_MAIN_SERVICE_CATEGORY, firstchildSO.getSkill().getNodeName());
						hmParams.put(AOPConstants.AOP_SO_MAIN_SERVICE_CATEGORY, firstchildSO.getSkill().getNodeName());
				}
				if(firstchildSO.getServiceLocation() != null){
						//hmParams.put(AOPConstants.AOP_GROUP_SO_SERVICE_CITY, firstchildSO.getServiceLocation().getCity());
						hmParams.put(AOPConstants.AOP_SO_SERVICE_CITY, firstchildSO.getServiceLocation().getCity());
						//hmParams.put(AOPConstants.AOP_GROUP_SO_SERVICE_STATE, firstchildSO.getServiceLocation().getState());
						hmParams.put(AOPConstants.AOP_SO_SERVICE_STATE, firstchildSO.getServiceLocation().getState());
						//hmParams.put(AOPConstants.AOP_GROUP_SO_SERVICE_ZIP, firstchildSO.getServiceLocation().getZip());
						hmParams.put(AOPConstants.AOP_SO_SERVICE_ZIP, firstchildSO.getServiceLocation().getZip());
				}
				//hmParams.put(AOPConstants.AOP_GROUP_SO_SERVICE_LOCATION_TIMEZONE, firstchildSO.getServiceLocationTimeZone());
				hmParams.put(AOPConstants.AOP_SO_SERVICE_LOCATION_TIMEZONE, firstchildSO.getServiceLocationTimeZone());
				
				if (hmParams.get(AOPConstants.AOP_BUYER_ID) == null) {
					hmParams.put(AOPConstants.AOP_BUYER_ID, firstchildSO.getBuyer().getBuyerId());
				}

			}

			Map<String, Object> dateTimeMap = orderGroupBO.getGroupOrderServiceDateTime(groupId);

			if(dateTimeMap.get(GROUP_SERVICE_DATE1) != null &&
					dateTimeMap.get(GROUP_SERVICE_START_TIME) != null){
				/*HashMap<String, Object> estServiceDateTime = TimeUtils.convertGMTToGivenTimeZone((Timestamp)dateTimeMap.get(GROUP_SERVICE_DATE1),
																	dateTimeMap.get(GROUP_SERVICE_START_TIME).toString(), firstchildSO.getServiceLocationTimeZone());*/


				hmParams.put(AOPConstants.AOP_SERVICE_DATE1, DateUtils.getFormatedDate((Timestamp) dateTimeMap.get(GROUP_SERVICE_DATE1),"M/d/yyyy"));
				hmParams.put(AOPConstants.AOP_SERVICE_START_TIME, dateTimeMap.get(GROUP_SERVICE_START_TIME));
			}else{
				hmParams.put(AOPConstants.AOP_SERVICE_DATE1,"");
				hmParams.put(AOPConstants.AOP_SERVICE_START_TIME,"");
			}

			if(dateTimeMap.get(GROUP_SERVICE_DATE2) != null &&
					dateTimeMap.get(GROUP_SERVICE_END_TIME)!= null){
	/*			HashMap<String, Object> estServiceDateTime = TimeUtils.convertGMTToGivenTimeZone((Timestamp)dateTimeMap.get(GROUP_SERVICE_DATE2),
						dateTimeMap.get(GROUP_SERVICE_END_TIME).toString(), firstchildSO.getServiceLocationTimeZone());
*/
				hmParams.put(AOPConstants.AOP_SERVICE_DATE2, DateUtils.getFormatedDate((Timestamp) dateTimeMap.get(GROUP_SERVICE_DATE2),"M/d/yyyy"));
				hmParams.put(AOPConstants.AOP_SERVICE_END_TIME, dateTimeMap.get(GROUP_SERVICE_END_TIME));

			}else{
				hmParams.put(AOPConstants.AOP_SERVICE_DATE2,"");
				hmParams.put(AOPConstants.AOP_SERVICE_END_TIME,"");
			}
/*

			if(dateTimeMap.get(GROUP_SERVICE_DATE1)!= null){
				hmParams.put(AOPConstants.AOP_GROUP_SERVICE_DATE1, dateTimeMap.get(GROUP_SERVICE_DATE1));
			}
			if(dateTimeMap.get(GROUP_SERVICE_DATE2)!= null){
				hmParams.put(AOPConstants.AOP_GROUP_SERVICE_DATE2, dateTimeMap.get(GROUP_SERVICE_DATE2));
			}
			if(dateTimeMap.get(GROUP_SERVICE_START_TIME)!= null){
				hmParams.put(AOPConstants.AOP_GROUP_SERVICE_START_TIME, dateTimeMap.get(GROUP_SERVICE_START_TIME));
			}
			if(dateTimeMap.get(GROUP_SERVICE_END_TIME)!= null){
				hmParams.put(AOPConstants.AOP_GROUP_SERVICE_END_TIME, dateTimeMap.get(GROUP_SERVICE_END_TIME));
			}*/


		}catch(Exception e){
			logger.error("Error occured while getting routedDate for child of group" + e.getMessage());
		}


		// go thru children of grouped order get List of SOs
		//List<ChildServiceOrderVO> serviceOrderChildList = getListOfChildSO(groupId);


		//hmParams.put(AOPConstants.AOP_SO_GROUP_CHILDREN, serviceOrderChildList);

		//hmParams.put(AOPConstants.AOP_GROUP_SPEND_LIMIT_TO_LABOR, orderGroup.getFinalSpendLimitLabor());
		hmParams.put(AOPConstants.AOP_SPEND_LIMIT_LABOR, UIUtils.formatDollarAmount(orderGroup.getFinalSpendLimitLabor()));
		//hmParams.put(AOPConstants.AOP_GROUP_SPEND_LIMIT_TO_PARTS, orderGroup.getFinalSpendLimitParts());
		hmParams.put(AOPConstants.AOP_SPEND_LIMIT_PARTS, UIUtils.formatDollarAmount(orderGroup.getFinalSpendLimitParts()));

		hmParams.put(AOPConstants.AOP_TOTAL_SPEND_LIMIT, UIUtils.formatDollarAmount(orderGroup.getFinalSpendLimitLabor() + orderGroup.getFinalSpendLimitParts()));

		hmParams.put(AOPConstants.AOP_SO_TITLE, orderGroup.getTitle());
		hmParams.put(AOPConstants.AOP_SO_DESC,"");
		hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_NO);
		hmParams.put(AOPConstants.SKIP_FTP_ALERT, OrderConstants.FLAG_NO);


		return hmParams;

	}

	public List<ChildServiceOrderVO> getListOfChildSO(String groupId) {

		List<ChildServiceOrderVO> soChildAOPList = new ArrayList<ChildServiceOrderVO>();

		try{
			List<ServiceOrderSearchResultsVO>soChildResults = orderGroupDAO.getServiceOrdersForGroup(groupId);

			for(ServiceOrderSearchResultsVO eachChild : soChildResults){
				String childSoId = eachChild.getSoId();
				ServiceOrder firstchildSO = serviceOrderDao.getServiceOrder(childSoId);
				ChildServiceOrderVO childSOAOP = new ChildServiceOrderVO();
				if(firstchildSO != null){
					childSOAOP.setSoId(firstchildSO.getSoId());
					childSOAOP.setTasks(firstchildSO.getTasks());
				}
				soChildAOPList.add(childSOAOP);
			}

		}catch(Exception e){
			logger.error("Error while getting children for groupId: " + groupId + " in AOPMapper.getListOfChildSO()");
		}
		return soChildAOPList;
	}

	public HashMap<String, Object> updatePartsShippingInfo() {
		logger.debug("AOPMapper.updatePartsShippingInfo()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);

		return hmParams;
	}

	public Map<String, Object> updatePartsShippingInfoPostSOInjection(Map<String, Object> hmParams) {
		//If Order is in completed status don't send parts shipped file SL-7478
		if (((Integer)hmParams.get(AOPConstants.AOP_STATUS_ID)).intValue() != OrderConstants.COMPLETED_STATUS) {
			return updatePostSOInjection(hmParams, ClientConstants.PARTS_SHIPPED_STATUS, ClientConstants.PARTS_SHIPPED_COMMENTS);
		}
		return hmParams;		
	}

	public Map<String, Object> processIncidentReopen() {
		logger.debug("IncidentAOPMapper.processIncidentReopen()");
		Map<String, Object> aopParams = new AOPHashMap();
		ServiceOrder so = (ServiceOrder)params[0];
		if(so != null) {
			aopParams.put(AOPConstants.AOP_SO_ID, so.getSoId());
			Buyer buyer = so.getBuyer();
			if(buyer != null) {
				aopParams.put(AOPConstants.AOP_BUYER_ID, buyer.getBuyerId());
			}
		}
		aopParams.put(AOPConstants.AOP_ROLE_TYPE, OrderConstants.BUYER_ROLEID);
		return aopParams;
	}

	public Map<String, Object> processIncidentReopenPostSOInjection(Map<String, Object> aopParams) {
		aopParams = updatePostSOInjection(aopParams, ClientConstants.SP_REOPEN_STATUS, null);
		return aopParams;
	}

	/**
	 * Populates the arguments to HashMap for Provider registration
	 * @return
	 */
	public HashMap<String, Object> saveRegistration(){
		logger.debug("AOPMapper.saveRegistration()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		ProviderRegistrationVO providerRegVO = (ProviderRegistrationVO) params[0];
		hmParams.put(AOPConstants.AOP_USER_NAME, providerRegVO.getUserName());
		hmParams.put(AOPConstants.AOP_USER_FIRST_NAME, providerRegVO.getFirstName());
		hmParams.put(AOPConstants.AOP_USER_LAST_NAME, providerRegVO.getLastName());
		hmParams.put(AOPConstants.AOP_USER_EMAIL, providerRegVO.getEmail());
		hmParams.put(AOPConstants.AOP_ALT_USER_EMAIL, providerRegVO.getAltEmail());
		hmParams.put(AOPConstants.AOP_USER_PASSWORD, providerRegVO.getPassword());
		Date currentDate = new Date();
		String strDate = DateUtils.getFormatedDate(currentDate, "MMM dd, yyyy");
        hmParams.put(AOPConstants.AOP_REGISTRATION_DATE, strDate);
		hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_NO);
        return hmParams;
	}


	/**
	 * Populates the arguments to HashMap for Forgot user name
	 * @return
	 */
	public HashMap<String, Object> sendForgotUsernameMail(){
		logger.debug("AOPMapper.saveRegistration()");
		HashMap<String, Object> hmParams = new AOPHashMap();
	
		LostUsernameVO lostUserNameVO = (LostUsernameVO) params[0];		
		hmParams.put(AOPConstants.AOP_USERNAME, lostUserNameVO.getUserName());
		hmParams.put(AOPConstants.AOP_USER_EMAIL,lostUserNameVO.getEmailAddress());
		hmParams.put(AOPConstants.AOP_ROLE, lostUserNameVO.getUserId());
		hmParams.put(AOPConstants.AOP_DEEPLINK, lostUserNameVO.getDeepLinkUrl());
		
		Date currentDate = new Date();
		String strDate = DateUtils.getFormatedDate(currentDate, "MMM dd, yyyy");
        hmParams.put(AOPConstants.AOP_REGISTRATION_DATE, strDate);
		hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_NO);
        return hmParams;
	}


	/**
	 * Populates the arguments to HashMap for forgot password
	 * @return
	 * @throws BusinessServiceException 
	 */
	public HashMap<String, Object> validateAns() throws BusinessServiceException {
		logger.debug("AOPMapper.validateAns()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		LostUsernameVO lostUserNameVO = (LostUsernameVO) params[0];
		if(!lostUserNameVO.getSuccessAns()) {
			throw new BusinessServiceException("The answer for the secret question is wrong");
		}
		hmParams.put(AOPConstants.AOP_USERNAME, lostUserNameVO.getUserName());
		hmParams.put(AOPConstants.AOP_USER_EMAIL, lostUserNameVO.getEmailAddress());
		hmParams.put(AOPConstants.AOP_USER_PASSWORD, lostUserNameVO.getPassword());
		hmParams.put(AOPConstants.AOP_DEEPLINK, lostUserNameVO.getDeepLinkUrl());
		
		Date currentDate = new Date();
                String strDate = DateUtils.getFormatedDate(currentDate, "MMM dd, yyyy");
                hmParams.put(AOPConstants.AOP_RESET_DATE, strDate);
		if(!lostUserNameVO.getSuccessAns()) {
			hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_YES);
			hmParams.put(AOPConstants.SKIP_LOGGING, OrderConstants.FLAG_YES);
		} else {
		hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_NO);
			hmParams.put(AOPConstants.SKIP_LOGGING, OrderConstants.FLAG_NO);
		
		}
        return hmParams;
	}
	
	
	public HashMap<String, Object> resetPassword() throws BusinessServiceException {
		logger.debug("AOPMapper.resetPassword()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		LostUsernameVO lostUserNameVO = (LostUsernameVO) params[0];		
		hmParams.put(AOPConstants.AOP_USERNAME, lostUserNameVO.getUserName());
		hmParams.put(AOPConstants.AOP_USER_EMAIL, lostUserNameVO.getEmailAddress());
		hmParams.put(AOPConstants.AOP_USER_PASSWORD, lostUserNameVO.getPassword());
		hmParams.put(AOPConstants.AOP_DEEPLINK, lostUserNameVO.getDeepLinkUrl());
		
		Date currentDate = new Date();
                String strDate = DateUtils.getFormatedDate(currentDate, "MMM dd, yyyy");
                hmParams.put(AOPConstants.AOP_RESET_DATE, strDate);
		
		hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_NO);
		hmParams.put(AOPConstants.SKIP_LOGGING, OrderConstants.FLAG_NO);
        return hmParams;
	}
	
	public HashMap<String, Object> saveProfBuyerRegistration() {
		logger.debug("AOPMapper.saveProfBuyerRegistration()");
		return saveSimpleBuyerRegistration();
	}

	/**
	 * Populates the arguments to HashMap for buyer registration
	 * @return
	 */
	public HashMap<String, Object> saveSimpleBuyerRegistration(){
		logger.debug("AOPMapper.saveSimpleBuyerRegistration()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		BuyerRegistrationVO buyerRegistrationVO = (BuyerRegistrationVO) params[0];
		hmParams.put(AOPConstants.AOP_USERNAME, buyerRegistrationVO.getUserName());
		hmParams.put(AOPConstants.AOP_BUYERUSERNAME,
				buyerRegistrationVO.getFirstName() + " " + buyerRegistrationVO.getLastName());
		hmParams.put(AOPConstants.AOP_BUYERFIRSTNAME, buyerRegistrationVO.getFirstName());
		hmParams.put(AOPConstants.AOP_BUYERLASTNAME, buyerRegistrationVO.getLastName());
		hmParams.put(AOPConstants.AOP_USER_EMAIL, buyerRegistrationVO.getEmail());
		hmParams.put(AOPConstants.AOP_ALT_USER_EMAIL, buyerRegistrationVO.getAltEmail());
		hmParams.put(AOPConstants.AOP_USER_PASSWORD, buyerRegistrationVO.getPassword());
		Date currentDate = new Date();
		String strDate = DateUtils.getFormatedDate(currentDate, "MMM dd, yyyy");
        hmParams.put(AOPConstants.AOP_REGISTRATION_DATE, strDate);
		hmParams.put(AOPConstants.SKIP_ALERT, OrderConstants.FLAG_NO);
		String waivePostingFee = PropertiesUtils.getPropertyValue(Constants.AppPropConstants.WAIVE_POSTING_FEE);
		hmParams.put(AOPConstants.AOP_PROMO_END_DT, waivePostingFee);
        return hmParams;
	}
	/**
	 * This method populates the arguments to HashMao for processUpdateSpendLimitforWS
	 * @return hmParams
	 */
	public HashMap<String, Object> processUpdateSpendLimitforWS(){
		logger.debug("AOPMapper.processUpdateSpendLimitforWS()");		
		HashMap<String, Object> hmParams = new AOPHashMap();
		ServiceOrder so = (ServiceOrder) params[0];
		hmParams.put(AOPConstants.AOP_SO_ID, so.getSoId());
		return hmParams;
	}
	/**
	 * This method populates the arguments to HashMap for processReRouteSOForWS
	 * @return hmParams
	 */
	public HashMap<String, Object> processReRouteSOForWS(){
		logger.debug("AOPMapper.processReRouteSOForWS()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		ServiceOrder so = (ServiceOrder) params[0];
		hmParams.put(AOPConstants.AOP_SO_ID, so.getSoId());
		return hmParams;
	}
	/**
	 * Populates the arguments to HashMap for buyer processUpdateTask
	 * @return
	 */
	public HashMap<String, Object> processUpdateTask(){
		logger.debug("AOPMapper.processUpdateTask()");
		HashMap<String, Object> hmParams = new AOPHashMap();
		ServiceOrder so = (ServiceOrder) params[0];
		hmParams.put(AOPConstants.AOP_SO_ID, so.getSoId());
		return hmParams;
	}
	/**
	 * Populates the arguments to HashMap for processCancelSOInAcceptedForWS
	 * @return
	 */
	public HashMap<String, Object> processCancelSOInAcceptedForWS(){
		logger.debug("AOPMapper.processCancelSOInAcceptedForWS()");
		ServiceOrder so = null;
		String soId = (String)params[0];
		try {
			so = serviceOrderDao.getServiceOrder(soId);
		} catch (DataServiceException dse) {
			logger.error("AOPMapper.processCancelSOInAcceptedForWS()", dse );
		}
		HashMap<String, Object> hmParams = new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_BUYER_ID, so.getBuyer().getBuyerId());
		hmParams.put(AOPConstants.AOP_USER_NAME, AOPConstants.AOP_SYSTEM);
		hmParams.put(AOPConstants.AOP_COMMENTS, AOPConstants.AOP_CANCEL_COMMENT_IN_ACCEPTED);
		Calendar calendar = new GregorianCalendar();
        Date date = calendar.getTime();
        DateFormat formatter = new SimpleDateFormat( "MM/dd/yyyy" );
        String strDate = formatter.format(date);
        hmParams.put(AOPConstants.AOP_SO_CANCEL_DATE,strDate);
		
		return hmParams;
	}
	public Map<String, Object> processCancelSOInAcceptedForWSPostSOInjection(Map<String, Object> hmParams){
		return updatePostSOInjection(hmParams, null, null);
	}
	/**
	 * Populates the arguments to HashMap for processCancelSOInActiveForWS
	 * @return
	 */
	public HashMap<String, Object> processCancelSOInActiveForWS(){
		logger.debug("AOPMapper.processCancelSOInActiveForWS()");
		ServiceOrder so = null;
		double cancellationAmt = AOPConstants.AOP_ZERO_CANCELLATION_AMT;
		String soId = (String)params[0];
		try {
			so = serviceOrderDao.getServiceOrder(soId);
		} catch (DataServiceException dse) {
			logger.error("AOPMapper.processCancelSOInActiveForWS()", dse );
		}
		HashMap<String, Object> hmParams = new AOPHashMap();
		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_BUYER_ID, so.getBuyer().getBuyerId());
		hmParams.put(AOPConstants.AOP_USER_NAME, AOPConstants.AOP_SYSTEM);
		hmParams.put(AOPConstants.AOP_CANCEL_AMT, UIUtils.formatDollarAmount(cancellationAmt));
		
		return hmParams;
	}
	public Map<String, Object> processCancelSOInActiveForWSPostSOInjection(Map<String, Object> hmParams){
		return updatePostSOInjection(hmParams, null, null);
	}
	
	/**
	 * SO Update Custom Reference
	 * */

	public HashMap<String, Object> updateSOCustomReference(){
		logger.debug("AOPMapper.updateSOCustomReference()");
		HashMap<String, Object> hmParams = new AOPHashMap();

		hmParams.put(AOPConstants.AOP_SO_ID, params[0]);
		hmParams.put(AOPConstants.AOP_CUSTOM_REFERENCE_TYPE, params[1]);
		hmParams.put(AOPConstants.AOP_CUSTOM_REFERENCE_VALUE_NEW, params[2]);
		hmParams.put(AOPConstants.AOP_CUSTOM_REFERENCE_VALUE_OLD, params[3]);
		hmParams.put(AOPConstants.AOP_ROLE_TYPE, BUYER_ROLEID);
		SecurityContext sc = (SecurityContext) params[params.length-1];
		hmParams.put(AOPConstants.AOP_SECURITY_CONTEXT, sc);
		return hmParams;
	}
}
