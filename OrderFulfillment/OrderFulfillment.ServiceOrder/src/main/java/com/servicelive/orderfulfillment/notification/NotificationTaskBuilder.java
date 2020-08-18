package com.servicelive.orderfulfillment.notification;

import java.util.Map;

import com.servicelive.marketplatform.common.vo.ServiceOrderNotificationTask;
import com.servicelive.marketplatform.common.vo.ServiceOrderNotificationTaskAddresses;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;

public class NotificationTaskBuilder implements INotificationTaskBuilder {
    private INotificationInfoCollector notificationInfoCollector;
    private INotificationTaskAddressesResolver recipientsResolver;
    private INotificationValuesMapBuilder notificationValuesMapBuilder;

    public ServiceOrderNotificationTask buildNotificationTask(NotificationTaskBuilderContext taskBuilderContext) {
        NotificationInfo notificationInfo = notificationInfoCollector.collectNotificationInfo(taskBuilderContext.getTemplateId(), taskBuilderContext.getProcessVariablesCopy());
        ServiceOrderNotificationTaskAddresses taskAddresses = recipientsResolver.resolveAddresses(taskBuilderContext.getAddressCodeSet(), notificationInfo);
        Map<String, String> valueMap = notificationValuesMapBuilder.buildMap(notificationInfo);

        if (taskBuilderContext.getDataMapValues() != null) {
            valueMap.putAll(taskBuilderContext.getDataMapValues());
        }

        ServiceOrderNotificationTask notificationTask = new ServiceOrderNotificationTask();
        notificationTask.setServiceOrderId(ProcessVariableUtil.extractServiceOrderId(taskBuilderContext.getProcessVariablesCopy()));
        notificationTask.setTemplateId(taskBuilderContext.getTemplateId());
        notificationTask.setAddresses(taskAddresses);
        notificationTask.setTemplateMergeValueMap(valueMap);

        return notificationTask;
    }

    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////
    public void setNotificationInfoCollector(INotificationInfoCollector notificationInfoCollector) {
        this.notificationInfoCollector = notificationInfoCollector;
    }

    public void setRecipientsResolver(INotificationTaskAddressesResolver recipientsResolver) {
        this.recipientsResolver = recipientsResolver;
    }

    public void setNotificationValuesMapper(INotificationValuesMapBuilder notificationValuesMapBuilder) {
        this.notificationValuesMapBuilder = notificationValuesMapBuilder;
    }
}
