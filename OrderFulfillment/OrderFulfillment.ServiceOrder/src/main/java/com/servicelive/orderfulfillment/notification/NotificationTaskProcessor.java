package com.servicelive.orderfulfillment.notification;

import com.servicelive.marketplatform.common.vo.ServiceOrderNotificationTask;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformNotificationBO;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;

import java.util.Map;

import org.apache.log4j.Logger;

public class NotificationTaskProcessor implements INotificationTaskProcessor {
	protected INotificationTaskBuilder notificationTaskBuilder;
	protected TaskBuilderContextResolver taskBuilderContextResolver;
	protected IMarketPlatformNotificationBO notificationBO;
	protected IServiceOrderDao serviceOrderDao;
	private static final Logger logger = Logger
			.getLogger("NotificationTaskProcessor");

	public void processNotificationTask(String taskBldrCtxNm,
			Map<String, Object> processVariables) {
		NotificationTaskBuilderContext taskBldrCtx = taskBuilderContextResolver
				.resolve(taskBldrCtxNm);

		if (null == taskBldrCtx)
			throw new ServiceOrderException(
					"Did not find the email configuration for " + taskBldrCtxNm);

		if (taskBldrCtx.getDataMapValues() != null
				&& "N".equals(taskBldrCtx.getDataMapValues().get("EMAIL_IND")))
			logger.info("Email alert is blocked,EMAIL_IND value is N");
		else {
			taskBldrCtx.setProcessVariablesCopy(processVariables);
			ServiceOrderNotificationTask notificationTask = notificationTaskBuilder
					.buildNotificationTask(taskBldrCtx);

			// Use different template for estimation SO routing
			boolean isEstimationSO  = serviceOrderDao.isEstimationSO(notificationTask.getServiceOrderId());
			logger.info("processNotificationTask: isEstimationSO : "+isEstimationSO);
			if(isEstimationSO) {
				logger.info("processNotificationTask: Estimation template="+taskBldrCtx.getEstimationTemplateId());
				notificationTask.setTemplateId(taskBldrCtx.getEstimationTemplateId());
			}

			notificationBO.queueNotificationTask(notificationTask);
		}
	}

	// Code Added for Jira SL-19728
	// For Sending emails specific to NON FUNDED buyer
	// As per new requirements
	/**
	 * Method added to send emails for NON FUNDED BUYER
	 * 
	 * @param taskBldrCtxNm
	 *            ,buyerId
	 * @return
	 * @throws
	 */
	public void processNotificationTaskForNonFunded(String taskBldrCtxNm,
			Map<String, Object> processVariables, Integer buyerId) {
		NotificationTaskBuilderContext taskBldrCtx = taskBuilderContextResolver
				.resolve(taskBldrCtxNm);

		if (null == taskBldrCtx)
			throw new ServiceOrderException(
					"Did not find the email configuration for " + taskBldrCtxNm);

		if (taskBldrCtx.getDataMapValues() != null
				&& "N".equals(taskBldrCtx.getDataMapValues().get("EMAIL_IND")))
			logger.info("Email alert is blocked,EMAIL_IND value is N");
		else {
			taskBldrCtx.setProcessVariablesCopy(processVariables);
			ServiceOrderNotificationTask notificationTask = notificationTaskBuilder
					.buildNotificationTask(taskBldrCtx);

			Integer templateId = serviceOrderDao
					.getEmailTemplateSpecificToBuyer(buyerId, notificationTask
							.getTemplateId().intValue());
			if (null != templateId && templateId.intValue() != 0) {
				notificationTask.setTemplateId(templateId.longValue());
			}
			notificationBO.queueNotificationTask(notificationTask);
		}
	}

	public void setNotificationTaskBuilder(
			INotificationTaskBuilder notificationTaskBuilder) {
		this.notificationTaskBuilder = notificationTaskBuilder;
	}

	public void setTaskBuilderContextResolver(
			TaskBuilderContextResolver taskBuilderContextResolver) {
		this.taskBuilderContextResolver = taskBuilderContextResolver;
	}

	public void setNotificationBO(IMarketPlatformNotificationBO notificationBO) {
		this.notificationBO = notificationBO;
	}

	public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}
}
