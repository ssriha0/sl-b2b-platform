package com.servicelive.orderfulfillment.notification;

import java.util.Map;

import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

public class GroupNotificationInfoCollector extends AbstractNotificationInfoCollector {

	public NotificationInfo collectNotificationInfo(Long notificationTemplateId, Map<String, Object> processVariablesClone) {

		NotificationInfo notificationInfo = null;
        if (processVariablesClone.get(ProcessVariableUtil.PVKEY_GROUP_ID) != null){
        	String groupId = (String) processVariablesClone.get(ProcessVariableUtil.PVKEY_GROUP_ID);
			SOGroup soGroup = serviceOrderDao.getSOGroup(groupId);
	        notificationInfo = getNotification(soGroup);
        } else {
			String soId = (String) processVariablesClone.get(ProcessVariableUtil.PVKEY_SERVICE_ORDER_IDS_FOR_GROUP_PROCESS);
			ServiceOrder so = serviceOrderDao.getServiceOrder(soId); 
			notificationInfo = getNotification(so);
        }        
        
        notificationInfo.setProcessVariables(processVariablesClone);
        fetchSystemProperties(notificationInfo);
        setNotificationType(notificationTemplateId, notificationInfo);

        return notificationInfo;
	}

	private NotificationInfo getNotification(SOGroup sog){
		ServiceOrder so = sog.getFirstServiceOrder();
		NotificationInfo notificationInfo = getNotification(so);

        notificationInfo.setOrderId(sog.getGroupId());
        notificationInfo.setLaborSpendLimit(sog.getGroupPrice().getFinalSpendLimitLabor());
        notificationInfo.setPartsSpendLimit(sog.getGroupPrice().getFinalSpendLimitParts());
		return notificationInfo;
	}

}
