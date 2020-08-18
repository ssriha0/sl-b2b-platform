package com.servicelive.wallet.batch;

import java.sql.Timestamp;

import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.wallet.alert.IAlertBO;
import com.servicelive.wallet.alert.IEmailTemplateBO;

// TODO: Auto-generated Javadoc
/**
 * The Class BaseProcessor.
 */
public abstract class BaseProcessor implements IProcessor {

	/**
	 * Gets the current timestamp.
	 * 
	 * @return the current timestamp
	 */
	public static Timestamp getCurrentTimestamp() {

		return new Timestamp(System.currentTimeMillis());
	}

	/** The alert bo. */
	protected IAlertBO alertBO;

	/** The application properties. */
	protected IApplicationProperties applicationProperties;

	/** The email template bo. */
	protected IEmailTemplateBO emailTemplateBO;

	/**
	 * Sets the alert bo.
	 * 
	 * @param alertBO the new alert bo
	 */
	public void setAlertBO(IAlertBO alertBO) {

		this.alertBO = alertBO;
	}

	/**
	 * Sets the application properties.
	 * 
	 * @param applicationProperties the new application properties
	 */
	public void setApplicationProperties(IApplicationProperties applicationProperties) {

		this.applicationProperties = applicationProperties;
	}

	/**
	 * Sets the email template bo.
	 * 
	 * @param emailTemplateBO the new email template bo
	 */
	public void setEmailTemplateBO(IEmailTemplateBO emailTemplateBO) {

		this.emailTemplateBO = emailTemplateBO;
	}
}
