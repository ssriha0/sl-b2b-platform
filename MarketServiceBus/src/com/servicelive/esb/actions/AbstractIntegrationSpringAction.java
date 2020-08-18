package com.servicelive.esb.actions;

import java.io.File;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;

import com.newco.marketplace.translator.util.EmailSender;
import com.servicelive.esb.integration.IIntegrationServiceCoordinator;
import com.servicelive.esb.integration.bo.IIntegrationBO;
import com.servicelive.notification.INotificationServiceCoordinator;
import com.servicelive.notification.bo.INotificationBO;

public abstract class AbstractIntegrationSpringAction extends
		AbstractEsbSpringAction {

	private static final Logger logger = Logger.getLogger(AbstractIntegrationSpringAction.class);

	private IIntegrationServiceCoordinator integrationServiceCoordinator;
	private INotificationServiceCoordinator notificationServiceCoordinator;
	private ResourceBundle propertiesResourceBundle;

	public AbstractIntegrationSpringAction() {
		super();
	}

	public AbstractIntegrationSpringAction(ConfigTree configTree) {
		super(configTree);
	}

	@Override
	public void initialise() throws ActionLifecycleException {
		final String methodName = "initialise";
		logger.info(String.format("Entered %s", methodName));

		super.initialise();
		if (this.getIntegrationServiceCoordinator() == null) {
			this.setIntegrationServiceCoordinator((IIntegrationServiceCoordinator) super.getBeanFactory().getBean("integrationServiceCoordinator"));
		}

		if (this.getIntegrationServiceCoordinator().getIntegrationBO() == null) {
			this.getIntegrationServiceCoordinator().setIntegrationBO((IIntegrationBO) super.getBeanFactory().getBean("integrationBO"));
		}
		
		if (this.getNotificationServiceCoordinator() == null) {
			this.setNotificationServiceCoordinator((INotificationServiceCoordinator) super.getBeanFactory().getBean("notificationServiceCoordinator"));
		}

		if (this.getNotificationServiceCoordinator().getNotificationBO() == null) {
			this.getNotificationServiceCoordinator().setNotificationBO((INotificationBO) super.getBeanFactory().getBean("notificationBO"));
		}

		if (this.propertiesResourceBundle == null) {
			propertiesResourceBundle = (ResourceBundle) super.getBeanFactory().getBean("esbPropertiesResourceBundle");
		}

		logger.info(String.format("Exiting %s", methodName));
	}

	@Override
	public void exceptionHandler(Message message, Throwable throwable) {
		// Email subject
		StringBuilder emailSubject = new StringBuilder("Error occurred in integration")
			.append(this.getIntegrationName(null));

		// Email body
		StringBuilder emailBody = new StringBuilder("Message:\r\n");
		emailBody.append(throwable.getMessage());
		emailBody.append("\r\n\r\nStack Trace:\r\n");
		emailBody.append(convertThrowableToString(throwable));

		// Send email
		logger.error(emailSubject, throwable);
		EmailSender.sendMessage(emailSubject, emailBody);
	}

	public IIntegrationServiceCoordinator getIntegrationServiceCoordinator() {
		return integrationServiceCoordinator;
	}

	public void setIntegrationServiceCoordinator(IIntegrationServiceCoordinator integrationServiceCoordinator) {
		this.integrationServiceCoordinator = integrationServiceCoordinator;
	}

	public INotificationServiceCoordinator getNotificationServiceCoordinator() {
		return notificationServiceCoordinator;
	}

	public void setNotificationServiceCoordinator(
			INotificationServiceCoordinator notificationServiceCoordinator) {
		this.notificationServiceCoordinator = notificationServiceCoordinator;
	}

	public ResourceBundle getPropertiesResourceBundle() {
		return propertiesResourceBundle;
	}

	public void setPropertiesResourceBundle(ResourceBundle propertiesResourceBundle) {
		this.propertiesResourceBundle = propertiesResourceBundle;
	}

	protected abstract Long getIntegrationId(String fileName);

	protected abstract String getIntegrationName(String fileName);

	protected String getProperty(String key) {
		return this.propertiesResourceBundle.getString(key);
	}

	protected String getProperty(String key, String defaultValue) {
		try {
			return this.propertiesResourceBundle.getString(key);
		} 
		catch (MissingResourceException e) {
			return defaultValue;
		}
	}

	protected <T> T getProperty(String key, Class<T> valueType) {
		@SuppressWarnings("unchecked")
		T value = (T) this.propertiesResourceBundle.getObject(key);
		return value;
	}

	protected <T> T getProperty(String key, T defaultValue) {
		try {
			@SuppressWarnings("unchecked")
			T value = (T) this.propertiesResourceBundle.getObject(key);
			return value;
		} 
		catch (MissingResourceException e) {
			return defaultValue;
		}
	}

	protected void recordBatchError(String batchFileName, Throwable throwable) {
		String errorMessage = convertThrowableToString(throwable);
		try {
			this.getIntegrationServiceCoordinator().createNewUnsuccessfulBatch(
					batchFileName, 
					getIntegrationId(batchFileName),errorMessage);
		} catch (Exception e) {
			throw new RuntimeException(String.format("Error occurred while trying to record error in processing inputFile '%s'",batchFileName), e);
		}
	}

	protected void recordBatchError(String batchFileName, Throwable throwable,Long batchId) {
		String errorMessage = convertThrowableToString(throwable);
		try {
			this.getIntegrationServiceCoordinator().recordBatchError(batchFileName, batchId, errorMessage);
		} catch (Exception e) {
			throw new RuntimeException(String.format("Error occurred while trying to record error for batch with batchId %d, inputFile '%s'",batchId, batchFileName), e);
		}
	}

	protected void issueErrorNotificationForFile(String fileName,Throwable throwable, 
			String errorFolderName, String errorFileSuffix) {
		String payload = "";
		try {
			payload = FileUtils.readFileToString(new File(fileName));
		} catch (IncompatibleClassChangeError ie) {
			payload = String.format(
					"Error occurred while trying to convert file '%s' to string due to error %s",
					fileName, convertThrowableToString(throwable));
		} catch (IOException e) {
			payload = String.format(
					"Unable to read payload from file '%s' due to error %s",
					fileName, convertThrowableToString(e));
		}
		EmailSender.sendMessage(this.getIntegrationName(fileName), fileName,
				convertThrowableToString(throwable), payload, errorFolderName,
				errorFileSuffix);
	}

}
