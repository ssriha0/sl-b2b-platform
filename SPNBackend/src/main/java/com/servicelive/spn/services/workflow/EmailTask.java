/**
 * 
 */
package com.servicelive.spn.services.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupEmailTemplate;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.spn.detached.Email;
import com.servicelive.spn.services.email.EmailTemplateService;

/**
 * @author hoza
 * 
 */
public class EmailTask {

	private EmailTemplateService emailTemplateServices;

	/**
	 * Set Parameter map and Local resources to all of the template available in the DB
	 * 
	 * @param stateToUpdate
	 * @param model
	 * @return List
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<Email> getTask(String stateToUpdate, Map<String, String> model, String action)	throws Exception {
		List<Email> emails = getEmailListWithTemplates(stateToUpdate, action);
		for (Email email : emails) {
			email.setParams(model);
		}
		return emails;
	}

	/**
	 * 
	 * @param stateToUpdate
	 * @return List
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	private List<Email> getEmailListWithTemplates(String stateToUpdate, String action) throws Exception {
		List<Email> emails = new ArrayList<Email>();

		List<LookupEmailTemplate> templates = emailTemplateServices.findEmailTemplatesByLookupSPNWorkflowState(new LookupSPNWorkflowState(stateToUpdate), action);
		for (LookupEmailTemplate template : templates) {
			Email email = new Email();
			email.setTemplate(template);
			emails.add(email);
		}

		return emails;
	}

	/**
	 * @param emailTemplateServices the emailTemplateServices to set
	 */
	public void setEmailTemplateServices(EmailTemplateService emailTemplateServices) {
		this.emailTemplateServices = emailTemplateServices;
	}
}
