package com.servicelive.marketplatform.service;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.marketplatform.common.exception.MarketPlatformException;
import com.servicelive.marketplatform.common.vo.ServiceOrderNotificationTask;
import com.servicelive.marketplatform.dao.INotificationEntityDao;
import com.servicelive.marketplatform.dao.INotificationTemplateDao;
import com.servicelive.marketplatform.notification.domain.NotificationTask;
import com.servicelive.marketplatform.notification.domain.NotificationTemplate;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformNotificationBO;

public class MarketPlatformNotificationBO implements IMarketPlatformNotificationBO {
    INotificationEntityDao notificationTaskDao;
    INotificationTemplateDao notificationTemplateDao;

    Mapper mapper;

    private Logger logger = Logger.getLogger(MarketPlatformNotificationBO.class);

    @Transactional
    public void queueNotificationTask(ServiceOrderNotificationTask serviceOrderNotificationTask) {
        NotificationTask notificationTask = createNotificationTaskFromSOTask(serviceOrderNotificationTask);

        populateNotificationTaskWithTemplateValues(serviceOrderNotificationTask.getTemplateId(), notificationTask);

        // set other properties
        Date now = new Date();
        notificationTask.setCreatedDate(now);
        notificationTask.setLastModifiedDate(now);
        notificationTask.setTaskNotYetCompleted();
        notificationTask.setLastModifiedBy("NotificationService");
        if (StringUtils.isBlank(notificationTask.getTaskRecipient()) && 
        		StringUtils.isBlank(notificationTask.getTaskCCRecipient()) &&
        		StringUtils.isBlank(notificationTask.getTaskBCCRecipient()) 
        		){
        	logger.info("MarketPlatformNotificationBO.queueNotificationTask() --- no recipient (TO, CC & BCC) defined for " + notificationTask.getId());
        	notificationTask.setCompletionIndicator((short) 2);        	
        }
        notificationTaskDao.save(notificationTask);
    }

    public NotificationTemplate retrieveNotificationTemplate(Long templateId) {
        return notificationTemplateDao.findById(templateId);
    }

    private void populateNotificationTaskWithTemplateValues(Long templateId, NotificationTask notificationTask) {
        NotificationTemplate template = notificationTemplateDao.findById(templateId);
        if (template==null) {
            String errMsg = "Unable to find template for template id : " + templateId;
            logger.error(errMsg);
            throw new MarketPlatformException(errMsg);
        }
        notificationTask.setPriority(template.getPriority().doubleValue());
        notificationTask.setAlertTypeId(template.getTypeId());
    }

    NotificationTask createNotificationTaskFromSOTask(ServiceOrderNotificationTask serviceOrderNotificationTask) {
    	NotificationTask notificationTask = mapper.map(serviceOrderNotificationTask, NotificationTask.class);
    	
    	notificationTask.setMailMergeValues(createMailMergeValueStringFromMap(serviceOrderNotificationTask.getTemplateMergeValueMap()));
    	
        return notificationTask;
    }
    
    String createMailMergeValueStringFromMap(Map<String, String> templateMergeValueMap) {
    	StringBuilder stringBuilder = new StringBuilder("");
    	boolean isFirstKey = true;
    	if (templateMergeValueMap != null) {
	    	Set<String> keySet = templateMergeValueMap.keySet(); 
	    	for (String key: keySet) {
	    		if (isFirstKey) {
	    			isFirstKey = !isFirstKey;
	    		} else {
	    			stringBuilder.append("|");
	    		}

	    		stringBuilder.append(key)
	    		             .append("=")
	    		             .append(templateMergeValueMap.get(key));
	    	}
    	}
    	return stringBuilder.toString();
    }


    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////
    public void setNotificationTaskDao(INotificationEntityDao notificationTaskDao) {
        this.notificationTaskDao = notificationTaskDao;
    }

    public void setNotificationTemplateDao(INotificationTemplateDao notificationTemplateDao) {
        this.notificationTemplateDao = notificationTemplateDao;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }
}
