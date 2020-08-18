package com.servicelive.orderfulfillment.notification;

import java.util.HashMap;
import java.util.Map;
import org.dozer.DozerBeanMapper;

public class NotificationValuesMapBuilder implements INotificationValuesMapBuilder {
    String mappingId;
    DozerBeanMapper beanMapper;
    boolean checkMapKeyset = true;

    @SuppressWarnings("unchecked")
	public Map<String, String> buildMap(NotificationInfo notificationInfo) {
        HashMap<String, String> map = new HashMap<String, String>();
        if (mappingId!=null) {
            copyMapValues(map, beanMapper.map(notificationInfo, map.getClass(), mappingId));
        } else {
            copyMapValues(map, beanMapper.map(notificationInfo, map.getClass()));
        }
        return map;
    }

    private void copyMapValues(Map<String, String> destMap, Map<String, String> sourceMap) {
        destMap.putAll(sourceMap);
    }

    public void setMappingId(String mappingId) {
        this.mappingId = mappingId;
    }

    public void setBeanMapper(DozerBeanMapper beanMapper) {
        this.beanMapper = beanMapper;
    }

    public void setCheckMapKeyset(boolean checkMapKeyset) {
        this.checkMapKeyset = checkMapKeyset;
    }
}
