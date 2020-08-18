package com.servicelive.orderfulfillment.notification;

import java.util.Map;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class MainServiceOrderNotificationInfoCollector extends AbstractNotificationInfoCollector {

	public NotificationInfo collectNotificationInfo(Long notificationTemplateId, Map<String,Object> processVariablesClone) {
		
        String serviceOrderId = ProcessVariableUtil.extractServiceOrderId(processVariablesClone);
        ServiceOrder serviceOrder = serviceOrderDao.getServiceOrder(serviceOrderId);
        
        NotificationInfo notificationInfo = getNotification(serviceOrder);
        notificationInfo.setProcessVariables(processVariablesClone);
        fetchSystemProperties(notificationInfo);
        setNotificationType(notificationTemplateId, notificationInfo);
        notificationInfo.setExternalOrderId(getExternalOrderId(serviceOrder));
        return notificationInfo;
    }

}
