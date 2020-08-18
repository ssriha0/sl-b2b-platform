package com.newco.batch.routingrules;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import com.newco.batch.ABatchProcess;
import com.newco.marketplace.business.businessImpl.provider.EmailTemplateBOImpl;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.servicelive.domain.routingrules.detached.RoutingRuleEmailVO;
import com.servicelive.routingrulesengine.services.RoutingRuleAlertService;
import com.servicelive.routingrulesengine.services.RoutingRulesService;

public class RoutingRulesEmailNotificationProcess extends ABatchProcess {
	
	private RoutingRulesService routingRulesService;
	private RoutingRuleAlertService routingRuleAlertService;
	private EmailTemplateBOImpl emailTemplateBean;
	private static final Logger logger = Logger.getLogger(RoutingRulesEmailNotificationProcess.class);
	
	@Override
	public int execute() throws BusinessServiceException {
		try {
			List<RoutingRuleEmailVO> emailVOs = routingRuleAlertService.processActiveRulesForEmail();
			if (emailVOs != null && emailVOs.size() > 0) {
				sendEmail(emailVOs);
			}
		} catch (Exception ex) {
			logger.error("Unexpected exception occurred while sending active rules alert notifications!", ex);
		}
		return 0;
	}

	private void sendEmail(List<RoutingRuleEmailVO> emailVOs) {
		// Send Notification
			for (RoutingRuleEmailVO emailVO : emailVOs) {
				try {
					emailTemplateBean.sendBuyerRoutingRulesNotificationEmail(emailVO);
				} catch (MessagingException e) {
					logger.error("Exception while processing routing rule: " + emailVO.getCarRuleName(), e );
				} catch (IOException e) {
					logger.error("Exception while processing routing rule: " + emailVO.getCarRuleName(), e );
				}
		}
	}

	@Override
	public void setArgs(String[] args) {
		// NOOP
	}

	public RoutingRulesService getRoutingRulesService() {
		return routingRulesService;
	}

	public void setRoutingRulesService(RoutingRulesService routingRulesService) {
		this.routingRulesService = routingRulesService;
	}

	public RoutingRuleAlertService getRoutingRuleAlertService() {
		return routingRuleAlertService;
	}

	public void setRoutingRuleAlertService(
			RoutingRuleAlertService routingRuleAlertService) {
		this.routingRuleAlertService = routingRuleAlertService;
	}

	public EmailTemplateBOImpl getEmailTemplateBean() {
		return emailTemplateBean;
	}

	public void setEmailTemplateBean(EmailTemplateBOImpl emailTemplateBean) {
		this.emailTemplateBean = emailTemplateBean;
	}

}
