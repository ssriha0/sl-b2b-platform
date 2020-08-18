package com.newco.marketplace.util.scheduler;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.business.iBusiness.BuyerCallbackNotification.IBuyerCallbackNotificationBO;
import com.newco.marketplace.buyercallbacknotificationscheduler.vo.BuyerCallBackNotificationSchedulerVO;
import com.newco.marketplace.interfaces.AlertConstants;

public class BuyerCallbackNotificationProcessScheduler extends ABaseScheduler implements StatefulJob
{
	
	private static final Logger logger= Logger.getLogger(BuyerCallbackNotificationProcessScheduler.class);
	
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		long startTime =System.currentTimeMillis();
		logger.info("BuyerCallbackNotiificationProcessScheduler start-->"+ new Date());
		try {
			ApplicationContext applicationContext = getApplicationContext(context); 
			IBuyerCallbackNotificationBO buyerCallbackNotificationBO = 
					(IBuyerCallbackNotificationBO) applicationContext.getBean("buyerCallbackNotificationBOImpl");
			List<BuyerCallBackNotificationSchedulerVO> buyerCallBackNotificationList= buyerCallbackNotificationBO.getNotificationServiceOrderDetails();
			
			if(buyerCallBackNotificationList!=null&& buyerCallBackNotificationList.size()>0){
				logger.info("BuyerCallbackNotiificationProcessScheduler processing records count -->"+ buyerCallBackNotificationList.size());
				Iterator<BuyerCallBackNotificationSchedulerVO> buyerCallBackNotificationListItr=buyerCallBackNotificationList.iterator();
				while(buyerCallBackNotificationListItr.hasNext()){
					BuyerCallBackNotificationSchedulerVO buyerCallBackNotification=buyerCallBackNotificationListItr.next();
					String responseFilter=buyerCallbackNotificationBO.getBuyerCallbackFilters(buyerCallBackNotification.getBuyerId(), buyerCallBackNotification.getActionId());
					if(responseFilter!=null){
						logger.info("BuyerCallbackNotiificationProcessScheduler fetched filters successfully through API call");
						String response=buyerCallbackNotificationBO.getServiceOrderDetails(buyerCallBackNotification.getBuyerId(), 
								buyerCallBackNotification.getSoId(), responseFilter);
						if(isGetSoResponseSuccess(response)){
							logger.info("BuyerCallbackNotiificationProcessScheduler fetched getSO details successfully through API call");
							buyerCallbackNotificationBO.updateBuyerCallbackNotification(buyerCallBackNotification.getNotificationId(), response, AlertConstants.UPDATE_NOTIFICATION_STATUS_SUCCESS);
						} else {
							logger.info("BuyerCallbackNotiificationProcessScheduler failed to fetch getSO details through API call");
							buyerCallbackNotificationBO.updateBuyerCallbackNotificationFailure(buyerCallBackNotification.getNotificationId(), buyerCallBackNotification.getNoOfRetries()+1, AlertConstants.UPDATE_NOTIFICATION_STATUS_FAILURE);
						}
					}
					else{
						logger.info("BuyerCallbackNotiificationProcessScheduler failed to fetch filters through API call");
						buyerCallbackNotificationBO.updateBuyerCallbackNotificationFailure(buyerCallBackNotification.getNotificationId(), buyerCallBackNotification.getNoOfRetries()+1, AlertConstants.UPDATE_NOTIFICATION_STATUS_FAILURE);
					}
				}
			}
			logger.info("BuyerCallbackNotiificationProcessScheduler end-->"+ new Date());
			long endTime =System.currentTimeMillis()-startTime;
			logger.info("BuyerCallbackNotiificationProcessScheduler execution time : "+endTime+" milli secs");
			
		}catch(Exception e){
			logger.error("Error occured while running the BuyerCallbackNotiificationProcessScheduler process",e);
			throw new JobExecutionException("Error occured while running the BuyerCallbackNotiificationProcessScheduler process",e);
		}
	}
	
	private boolean isGetSoResponseSuccess(String response){
		if(null != response && response.contains("<result code=\"0000\">"))
			return true;
		return false;
	}

}
