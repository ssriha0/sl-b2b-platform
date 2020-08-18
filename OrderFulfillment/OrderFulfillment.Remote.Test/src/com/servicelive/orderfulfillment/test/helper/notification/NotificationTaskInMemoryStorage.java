package com.servicelive.orderfulfillment.test.helper.notification;

import com.servicelive.marketplatform.common.vo.ServiceOrderNotificationTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationTaskInMemoryStorage {

    private static Map<String, List<ServiceOrderNotificationTask>> notifyTaskMap;

    static {
        notifyTaskMap = Collections.synchronizedMap(new HashMap<String, List<ServiceOrderNotificationTask>>());
    }

    public static void saveNotificationTask(ServiceOrderNotificationTask svcOrderNotifyTask) {
        String serviceOrderId = svcOrderNotifyTask.getServiceOrderId();
        List<ServiceOrderNotificationTask> soNotifyTaskList;
        if (notifyTaskMap.containsKey(serviceOrderId)) {
            soNotifyTaskList = notifyTaskMap.get(serviceOrderId);
            soNotifyTaskList.add(svcOrderNotifyTask);
        } else {
            soNotifyTaskList = new ArrayList<ServiceOrderNotificationTask>();
            soNotifyTaskList.add(svcOrderNotifyTask);
            notifyTaskMap.put(serviceOrderId, soNotifyTaskList);
        }
    }

    public static Map<String, List<ServiceOrderNotificationTask>> getNotifyTaskMapCopy() {
        return new HashMap<String, List<ServiceOrderNotificationTask>>(notifyTaskMap);
    }
}
