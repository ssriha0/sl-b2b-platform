package com.servicelive.wallet.valuelink.ejb;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.wallet.valuelink.IValueLinkRequestHandlerBO;
import com.servicelive.wallet.valuelink.dao.IValueLinkDao;
import com.servicelive.wallet.valuelink.sharp.socket.bo.SharpSocketBo;

public class ValueLinkQueueSingleton implements Runnable {
	private final Logger logger = Logger
			.getLogger(ValueLinkQueueSingleton.class);

	private IValueLinkRequestHandlerBO requestHandler;
	private IValueLinkDao valueLinkDao;
	private IApplicationProperties applicationProperties;
	private long timerBeanSleepInterval;

	private boolean useIPSocket;

	public void run() {
		logger.debug("ValueLinkQueueSingleton-->run()-->START");
		sendVLMessages();
	}

	public ValueLinkQueueSingleton()
	{
		try
		{
			String appTimerBeanSleepInterval = (String) this.applicationProperties
			.getPropertyValue(CommonConstants.TIMER_BEAN_SLEEP_INTERVAL);
			if (StringUtils.isNotBlank(appTimerBeanSleepInterval)) {
				this.timerBeanSleepInterval = Long.parseLong(appTimerBeanSleepInterval);
			}else
			{
				this.timerBeanSleepInterval = 5000;
			}
		}catch(Exception e)
		{
			logger.info("Could not find app property value for timer bean sleep interval defaulting to 5000ms");
			this.timerBeanSleepInterval = 5000;
		}
	}
	public void sendVLMessages() {
		while (true) {
			try {
				logger.info("Using IP Sockets for ValueLink Communication: "+useIPSocket);
				if (!this.valueLinkDao.isValueLinkUp()) {
					logger.info("ValueLink is down!");
					requestHandler.sendHeartbeatMessage(useIPSocket);
				} else {
					logger.info("Timer Bean enqueueing Value Link entries");
					requestHandler.createValueLinkMessages();
					logger.info("Timer Bean sending Value Link entries");
					requestHandler.sendValueLinkMessages(useIPSocket);
				}

				Thread.sleep(this.timerBeanSleepInterval);
			} catch (Exception e) {
				logger.error(
						"ValueLinkQueueSingleton-->invokeMessaging()-->EXCEPTION-->",
						e);
			}
		}
	}

	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
			this.applicationProperties = applicationProperties;
	}

	public IValueLinkRequestHandlerBO getRequestHandler() {
		return requestHandler;
	}

	public void setRequestHandler(IValueLinkRequestHandlerBO requestHandler) {
		this.requestHandler = requestHandler;
	}

	public IValueLinkDao getValueLinkDao() {
		return valueLinkDao;
	}

	public void setValueLinkDao(IValueLinkDao valueLinkDao) {
		this.valueLinkDao = valueLinkDao;
	}

	public boolean isUseIPSocket() {
		return useIPSocket;
	}

	public void setUseIPSocket(boolean useIPSocket) {
		this.useIPSocket = useIPSocket;
	}
}