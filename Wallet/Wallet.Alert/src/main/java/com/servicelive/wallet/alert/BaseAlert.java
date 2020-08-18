package com.servicelive.wallet.alert;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.wallet.alert.dao.IAlertDao;
import com.servicelive.wallet.alert.dao.IContactDao;
import com.servicelive.wallet.alert.dao.ITemplateDao;

// TODO: Auto-generated Javadoc
/**
 * Class BaseAlert.
 */
public class BaseAlert {

	/** alertDao. */
	protected IAlertDao alertDao;

	/** applicationProperties. */
	IApplicationProperties applicationProperties;

	/** contactDao. */
	protected IContactDao contactDao;

	/** mailSender. */
	protected JavaMailSender mailSender = null;

	// ClassPathResource emailImg = new ClassPathResource("resources/images/icon_logo.gif");
	/** ServiceLiveEmailImg. */
	ClassPathResource ServiceLiveEmailImg = new ClassPathResource("com/servicelive/wallet/alert/service_live_logo.gif");

	/** templateDao. */
	protected ITemplateDao templateDao;

	// ClassPathResource verifiedEmailImg = new ClassPathResource("resources/images/verified.gif");

	/** velocityEngine. */
	protected VelocityEngine velocityEngine = null;

	/**
	 * getServiceLiveEmailImg.
	 * 
	 * @return ClassPathResource
	 */
	public ClassPathResource getServiceLiveEmailImg() {

		return ServiceLiveEmailImg;
	}

	/**
	 * getSiteUrl.
	 * 
	 * @return String
	 * 
	 * @throws DataServiceException 
	 */
	public String getSiteUrl() throws DataServiceException {

		return applicationProperties.getPropertyValue(CommonConstants.SERVICE_URL);
	}
	/**
	 * getSiteUrlForProvider.
	 * 
	 * @return String
	 * 
	 * @throws DataServiceException 
	 */
	public String getSiteUrlForRole(String appkey) throws DataServiceException {

		return applicationProperties.getPropertyValue(appkey);
	}
	/**
	 * setAlertDao.
	 * 
	 * @param alertDao 
	 * 
	 * @return void
	 */
	public void setAlertDao(IAlertDao alertDao) {

		this.alertDao = alertDao;
	}

	/**
	 * setApplicationProperties.
	 * 
	 * @param applicationProperties 
	 * 
	 * @return void
	 */
	public void setApplicationProperties(IApplicationProperties applicationProperties) {

		this.applicationProperties = applicationProperties;
	}

	/**
	 * setContactDao.
	 * 
	 * @param contactDao 
	 * 
	 * @return void
	 */
	public void setContactDao(IContactDao contactDao) {

		this.contactDao = contactDao;
	}

	/**
	 * setMailSender.
	 * 
	 * @param mailSender 
	 * 
	 * @return void
	 */
	public void setMailSender(JavaMailSender mailSender) {

		this.mailSender = mailSender;
	}

	/**
	 * setServiceLiveEmailImg.
	 * 
	 * @param serviceLiveEmailImg 
	 * 
	 * @return void
	 */
	public void setServiceLiveEmailImg(ClassPathResource serviceLiveEmailImg) {

		ServiceLiveEmailImg = serviceLiveEmailImg;
	}

	/**
	 * setTemplateDao.
	 * 
	 * @param templateDao 
	 * 
	 * @return void
	 */
	public void setTemplateDao(ITemplateDao templateDao) {

		this.templateDao = templateDao;
	}

	/**
	 * setVelocityEngine.
	 * 
	 * @param velocityEngine 
	 * 
	 * @return void
	 */
	public void setVelocityEngine(VelocityEngine velocityEngine) {

		this.velocityEngine = velocityEngine;
	}

}
