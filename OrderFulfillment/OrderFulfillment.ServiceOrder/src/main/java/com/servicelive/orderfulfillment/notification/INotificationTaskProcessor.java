package com.servicelive.orderfulfillment.notification;

import java.util.Map;

public interface INotificationTaskProcessor {
    void processNotificationTask(String taskBldrCtxNm, Map<String,Object> processVariables);
    public void processNotificationTaskForNonFunded(String taskBldrCtxNm, Map<String,Object> processVariables,Integer buyerId);
    
}
