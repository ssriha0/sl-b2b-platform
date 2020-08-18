package com.servicelive.marketplatform.notification;

import java.util.HashMap;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.log4j.Logger;

import com.servicelive.domain.common.Contact;
import com.servicelive.marketplatform.common.vo.ServiceOrderNotificationTask;
import com.servicelive.marketplatform.common.vo.ServiceOrderNotificationTaskAddresses;
import com.servicelive.marketplatform.service.MarketPlatformBuyerBO;
import com.servicelive.marketplatform.service.MarketPlatformNotificationBO;
import com.servicelive.marketplatform.service.MarketPlatformProviderBO;
import com.servicelive.marketplatform.service.MarketPlatformServiceOrderBO;
import com.servicelive.marketplatform.serviceorder.domain.ServiceOrder;
import com.servicelive.marketplatform.serviceorder.domain.ServiceOrderRoutedProvider;

public class ActivityNotificationListener implements MessageListener {

	private MarketPlatformServiceOrderBO serviceOrderBO;
	private MarketPlatformBuyerBO buyerBO;
	private MarketPlatformProviderBO providerBO;
	private MarketPlatformNotificationBO notificationBO;
	
	private static final Logger logger = Logger.getLogger(ActivityNotificationListener.class);
	
	public void onMessage(Message message) {
		final String methodName = "onMessage";
		logger.debug(String.format("entering %s", methodName));
		ServiceOrder so;
		try {
			so = serviceOrderBO.retrieveServiceOrder(message.getStringProperty("orderId"));
			StringBuffer sb = new StringBuffer();
			for (ServiceOrderRoutedProvider routedProvider : so.getRoutedProviders()) {
				Contact ci = providerBO.retrieveProviderResourceContactInfo(routedProvider.getResourceId());
				sb.append(ci.getEmail());
				sb.append(";");
			}
			
			ServiceOrderNotificationTaskAddresses taskAddresses;
			taskAddresses = new ServiceOrderNotificationTaskAddresses();
			taskAddresses.setFrom("noreply@servicelive.com");
			taskAddresses.setTo(sb.toString());
			
			ServiceOrderNotificationTask serviceOrderNotificationTask = new ServiceOrderNotificationTask();
			serviceOrderNotificationTask.setTemplateId(50L);
			serviceOrderNotificationTask.setAddresses(taskAddresses);
			serviceOrderNotificationTask.setTemplateMergeValueMap(new HashMap<String,String>());
			
			notificationBO.queueNotificationTask(serviceOrderNotificationTask);
			
		} catch (Exception e) {
			logger.error("Unhandled exception occurred while processing message", e);
		}
		logger.debug(String.format("exiting %s", methodName));
	}

	public void setServiceOrderBO(MarketPlatformServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}

	public MarketPlatformServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setBuyerBO(MarketPlatformBuyerBO buyerBO) {
		this.buyerBO = buyerBO;
	}

	public MarketPlatformBuyerBO getBuyerBO() {
		return buyerBO;
	}

	public void setProviderBO(MarketPlatformProviderBO providerBO) {
		this.providerBO = providerBO;
	}

	public MarketPlatformProviderBO getProviderBO() {
		return providerBO;
	}

	public void setNotificationBO(MarketPlatformNotificationBO notificationBO) {
		this.notificationBO = notificationBO;
	}

	public MarketPlatformNotificationBO getNotificationBO() {
		return notificationBO;
	}

}
