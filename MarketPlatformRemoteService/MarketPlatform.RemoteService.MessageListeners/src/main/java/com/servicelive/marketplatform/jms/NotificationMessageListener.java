package com.servicelive.marketplatform.jms;

import com.servicelive.marketplatform.common.vo.ServiceOrderNotificationTask;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformNotificationBO;
import org.apache.log4j.Logger;

import javax.ejb.EJBException;
import javax.jms.JMSException;
import javax.jms.Message;

public class NotificationMessageListener extends AbstractMessageListener {
    private static final Logger logger = Logger.getLogger(NotificationMessageListener.class);
    IMarketPlatformNotificationBO marketPlatformNotificationBO;
    
    public void onMessage(Message message) {
        ServiceOrderNotificationTask svcOrderNotifyTask = extractNotificationTask(message);
        logger.debug("a new Message received " + svcOrderNotifyTask.toString());
        try{
            marketPlatformNotificationBO.queueNotificationTask(svcOrderNotifyTask);
        }catch(Exception e){
            throw new EJBException("Exception happened while save notification", e);
        }
        logger.debug("NotificationMessageListener completed queueing notification task.");
    }

    private ServiceOrderNotificationTask extractNotificationTask(Message message) throws EJBException {
        Object obj = extractObjectFromMessage(message);
        assertPayloadIsServiceOrderNotificationTask(obj);
        return (ServiceOrderNotificationTask) obj;
    }

    private void assertPayloadIsServiceOrderNotificationTask(Object obj) {
        String errMsg = "Unable to extract notification task from ObjectMessage.";
        if ( ! (obj instanceof ServiceOrderNotificationTask)) {
            logger.error(errMsg);
            throw new EJBException(errMsg);
        }
     }

    public void setMarketPlatformNotificationBO(IMarketPlatformNotificationBO marketPlatformNotificationBO) {
        this.marketPlatformNotificationBO = marketPlatformNotificationBO;
    }
}
