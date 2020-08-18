package com.newco.marketplace.business.businessImpl.vibePostAPI;

import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.newco.marketplace.aop.AOPHashMap;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AOPConstants;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.persistence.iDao.alert.AlertDao;
import com.newco.marketplace.persistence.iDao.provider.ITemplateDao;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.utils.UIUtils;
import com.newco.marketplace.vo.provider.TemplateVo;

public class PushNotificationAlertTask {
	private static final Logger logger = Logger
			.getLogger(PushNotificationAlertTask.class.getName());
	
	private ITemplateDao templateDao = null;
	private AlertDao alertDao;
	private ServiceOrderDao serviceOrderDao;

	public void AddAlert(ServiceOrder order, String event) {
		try {			
			HashMap<String, Object> vContext = new AOPHashMap();
			vContext.put(AlertConstants.VIBES_SO_SERVICE_DATE1, order.getServiceDate1());
			vContext.put(AlertConstants.VIBES_SO_SERVICE_START_TIME, order.getServiceTimeStart());
			vContext.put(AlertConstants.VIBES_SO_SERVICE_ZIP, order.getServiceLocation().getZip());
			vContext.put(AlertConstants.VIBES_SO_SERVICE_LOCN_TIMEZONE, order.getActualServiceLocationTimeZone());
			vContext.put(AlertConstants.VIBES_SO_ID, order.getSoId());
			vContext.put(AlertConstants.EVENT_TYPE, event);
			vContext.put(AlertConstants.VENDOR_RESOURCE_ID, order.getAcceptedResourceId());
			vContext.put(AlertConstants.VENDOR_ID, order.getAcceptedVendorId());
			vContext.put(AlertConstants.DATE, order.getCreatedDate());
			vContext.put(AOPConstants.AOP_SO_SERVICE_STATE, order.getServiceLocation().getState());
			vContext.put(AOPConstants.AOP_SO_ROUTED_DATE, order.getRoutedDate());
			vContext.put(AOPConstants.AOP_TOTAL_SPEND_LIMIT, UIUtils.formatDollarAmount(order.getSpendLimitLabor() + order.getSpendLimitParts()));					
			vContext.put(AlertConstants.VIBES_SO_SERVICE_CITY, order.getServiceLocation().getCity());
			if ((event.equals(OrderConstants.ORDER_CANCEL_PUSH_NOTIFICATION_TEMPLATE)
					|| event.equals(OrderConstants.BUYER_CANCELLATION_PUSH_NOTIFICATION_TEMPLATE) || event.equals(OrderConstants.PROVIDER_CANCELLATION_PUSH_NOTIFICATION_TEMPLATE))
					&& null != order.getAcceptedVendorId())
				vContext.put(AlertConstants.PRIMARY_RESOURCE_ID, getPrimaryResourceId(order.getAcceptedVendorId()));
			alertDao.addAlertTask(getAlertTask(event, vContext));
		} catch (DataServiceException e) {
			logger.error("erro occured while add alert for push Notification: "+e);
			//throw new BusinessServiceException(e);
		}
	}
	
	public void addAlert(ServiceOrder order, String event) {
		try {			
			HashMap<String, Object> vContext = new AOPHashMap();

			vContext.put(AlertConstants.VIBES_SO_ID, order.getSoId());
			vContext.put(AlertConstants.EVENT_TYPE, event);
			vContext.put(AlertConstants.VENDOR_RESOURCE_ID, order.getAcceptedResourceId());
			vContext.put(AlertConstants.VENDOR_ID, order.getAcceptedVendorId());
			vContext.put(AlertConstants.DATE, order.getCreatedDate());
			alertDao.addAlertTask(getAlertTaskForPushNotifcn(event, vContext));
		} catch (DataServiceException e) {
			logger.error("error occured while add alert for push Notification: "+e);
			//throw new BusinessServiceException(e);
		}
	}
	
	
	public void AddAlert(String soId, String event){
		try {
			AddAlert(serviceOrderDao.getServiceOrder(soId),event);
		} catch (DataServiceException e) {
			logger.error("erro occured while add alert for push Notification: "+e);
			//throw new BusinessServiceException(e);
		}
	}

	public AlertTask getAlertTask(String event, HashMap vContext) {
		logger.info("PushNotificationAlertTask.getAlertTask() Event --> " +event);
		TemplateVo template = null;
		try {
			template = getTemplateDao().getTemplate(event);
		} catch (DBException e) {
			new BusinessServiceException(e);
		}

		AlertTask alertTask = new AlertTask();
		Date date = new Date();
		alertTask.setAlertedTimestamp(null);
		alertTask.setAlertTypeId(template.getTemplateTypeId());
		alertTask.setCreatedDate(date);
		alertTask.setModifiedDate(date);
		// alertTask.setModifiedBy((String)aopHashMap.get(AOPConstants.AOP_MODIFIED_BY));
		alertTask.setTemplateId(template.getTemplateId());
		if (template.getTemplateFrom() != null)
			alertTask.setAlertFrom(template.getTemplateFrom());// AlertConstants.SERVICE_LIVE_MAILID);
		else
			alertTask.setAlertFrom(AlertConstants.SERVICE_LIVE_MAILID);
		if (event.equals(OrderConstants.ORDER_CANCEL_PUSH_NOTIFICATION_TEMPLATE)
				|| event.equals(OrderConstants.BUYER_CANCELLATION_PUSH_NOTIFICATION_TEMPLATE)|| event.equals(OrderConstants.PROVIDER_CANCELLATION_PUSH_NOTIFICATION_TEMPLATE))
			alertTask.setAlertTo(vContext.get(AlertConstants.PRIMARY_RESOURCE_ID).toString());
		else
		alertTask.setAlertTo(vContext.get(AlertConstants.VENDOR_RESOURCE_ID).toString());
		alertTask.setAlertBcc("");
		alertTask.setAlertCc("");
		alertTask.setCompletionIndicator(AlertConstants.INCOMPLETE_INDICATOR);
		alertTask.setPriority(template.getPriority());
		alertTask.setTemplateInputValue(vContext.toString());
		return alertTask;
	}

	public AlertTask getAlertTaskForPushNotifcn(String event, HashMap vContext) {
		logger.info("PushNotificationAlertTask.getAlertTask() Event --> " + event);
		
		TemplateVo template = null;
		try {
			template = getTemplateDao().getTemplate(event);
		} catch (DBException e) {
			new BusinessServiceException(e);
		}

		AlertTask alertTask = new AlertTask();
		Date date = new Date();
		alertTask.setAlertedTimestamp(null);
		alertTask.setAlertTypeId(template.getTemplateTypeId());
		alertTask.setCreatedDate(date);
		alertTask.setModifiedDate(date);
		// alertTask.setModifiedBy((String)aopHashMap.get(AOPConstants.AOP_MODIFIED_BY));
		alertTask.setTemplateId(template.getTemplateId());
		if (template.getTemplateFrom() != null)
			alertTask.setAlertFrom(template.getTemplateFrom());// AlertConstants.SERVICE_LIVE_MAILID);
		else
			alertTask.setAlertFrom(AlertConstants.SERVICE_LIVE_MAILID);

		alertTask.setAlertTo(vContext.get(AlertConstants.VENDOR_RESOURCE_ID).toString());
		alertTask.setAlertBcc("");
		alertTask.setAlertCc("");
		alertTask.setCompletionIndicator(AlertConstants.INCOMPLETE_INDICATOR);
		alertTask.setPriority(template.getPriority());
		alertTask.setTemplateInputValue(vContext.toString());
		return alertTask;
	}
	private Integer getPrimaryResourceId(Integer acceptedVendorId){
		try {
			logger.info("PushNotificationAlertTask.getPrimaryResourceId() AcceptedVendorId --> " +acceptedVendorId);
			return getServiceOrderDao().getPrimaryResourceId(acceptedVendorId);
		} catch (Exception e) {
			logger.error("Exception Occurred while fetching the primaary resource id of the vendor " +acceptedVendorId+ "in PushNotificationAlertTask");
		}
		return null;
	}
	public AlertDao getAlertDao() {
		return alertDao;
	}

	public void setAlertDao(AlertDao alertDao) {
		this.alertDao = alertDao;
	}

	public ITemplateDao getTemplateDao() {
		return templateDao;
	}

	public void setTemplateDao(ITemplateDao templateDao) {
		this.templateDao = templateDao;
	}

	public ServiceOrderDao getServiceOrderDao() {
		return serviceOrderDao;
	}

	public void setServiceOrderDao(ServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}

}
