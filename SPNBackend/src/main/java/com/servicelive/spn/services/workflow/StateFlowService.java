package com.servicelive.spn.services.workflow;

import static com.servicelive.spn.common.SPNBackendConstants.DOC_STATE_INCOMPLETE;
import static com.servicelive.spn.common.SPNBackendConstants.DOC_STATE_PENDING_NEED_MORE_INFO;
import static com.servicelive.spn.common.SPNBackendConstants.EMAIL_ACTION_SPN_AUDITOR_MODIFIED;
import static com.servicelive.spn.common.SPNBackendConstants.STATE_FLOW_RESP_EMAIL_NOT_SENT;
import static com.servicelive.spn.common.SPNBackendConstants.STATE_FLOW_RESP_SUCCESS;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_APPLICANT_INCOMPLETE;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_SPN_DECLINED;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_SPN_MEMBER;
import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_SPN_MEMBERSHIP_UNDER_REVIEW;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.business.EmailAlertBO;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.exception.core.DataServiceException;
import com.servicelive.domain.buyer.BuyerFeatureSet;
import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.domain.lookup.LookupEmailTemplate;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.domain.spn.detached.Email;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.domain.spn.network.SPNUploadedDocumentState;
import com.servicelive.spn.dao.network.SPNHeaderDao;
import com.servicelive.spn.dao.provider.ServiceProviderDao;
import com.servicelive.spn.services.BaseServices;
import com.servicelive.spn.services.auditor.UploadedDocumentStateService;
import com.servicelive.spn.services.email.EmailTemplateService;
import com.servicelive.spn.services.email.MailQueryStringFormatter;
import com.servicelive.spn.services.interfaces.IProviderFirmStateService;

/**
 * 
 * @author svanloon
 * 
 */
public class StateFlowService extends BaseServices {

	private IProviderFirmStateService providerFirmStateService;
	private EmailTemplateService emailTemplateServices;
	private SPNHeaderDao spnHeaderDao;
	private ServiceProviderDao serviceProviderDao;
	private UploadedDocumentStateService uploadedDocumentStateService;
	private EmailAlertBO emailAlertBO;
	private MailQueryStringFormatter mailQueryStringFormatter;

	@SuppressWarnings("unused")
	@Override
	protected void handleDates(Object entity) {
		// do nothing
	}

	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @param modifiedBy
	 * @return true if the task can be persisted and not if the task was
	 *         successful.
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String signalApproval(Integer spnId, Integer providerFirmId,
			String modifiedBy) throws Exception {
		SPNProviderFirmTask task = new ApproveTask(spnId, providerFirmId,
				modifiedBy, WF_STATUS_PF_SPN_MEMBER);
		return signal(task);
	}
	
	// Code Added for Jira SL-19384
	//Method to make a SPN applicant to 'Membership Under Review' status
	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @param modifiedBy
	 * @return true if the task can be persisted and not if the task was
	 *         successful.
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String signalMemberShipUnderReview(Integer spnId, Integer providerFirmId,
			String modifiedBy) throws Exception {
		SPNProviderFirmTask task = new ApproveTask(spnId, providerFirmId,
				modifiedBy, WF_STATUS_PF_SPN_MEMBERSHIP_UNDER_REVIEW);
		providerFirmStateService.update(task.getSpnId(),
				task.getProviderFirmId(), task.getLookupSPNWorkflowState(),
				task.getModifiedBy(), task.getComment(),
				task.getLookupSPNStatusActionMapper());
		providerFirmStateService.unlock(task.getSpnId(),
				task.getProviderFirmId());
		return STATE_FLOW_RESP_SUCCESS;
	}

	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @param modifiedBy
	 * @param comment
	 * @return true if the task can be persisted and not if the task was
	 *         successful.
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String signalDeclined(Integer spnId, Integer providerFirmId,
			String modifiedBy, String comment) throws Exception {
		SPNProviderFirmTask task = new DeclineSubmitTask(spnId, providerFirmId,
				modifiedBy, WF_STATUS_PF_SPN_DECLINED, comment);
		return signal(task);
	}

	/**
	 * 
	 * @param spnId
	 * @param providerFirmId
	 * @param modifiedBy
	 * @param comment
	 * @return true if the task can be persisted and not if the task was
	 *         successful.
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public String signalSendNotification(Integer spnId, Integer providerFirmId,
			String modifiedBy, String comment) throws Exception {
		SPNProviderFirmTask task = new SendNotificationTask(spnId,
				providerFirmId, modifiedBy, WF_STATUS_PF_APPLICANT_INCOMPLETE,
				comment);
		return signal(task);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private String signal(SPNProviderFirmTask task) throws Exception {
		providerFirmStateService.update(task.getSpnId(),
				task.getProviderFirmId(), task.getLookupSPNWorkflowState(),
				task.getModifiedBy(), task.getComment(),
				task.getLookupSPNStatusActionMapper());
		providerFirmStateService.unlock(task.getSpnId(),
				task.getProviderFirmId());
		// do email

		boolean response = sendEmail(task);
		if (response == false) {
			return STATE_FLOW_RESP_EMAIL_NOT_SENT;
		}
		return STATE_FLOW_RESP_SUCCESS;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private boolean sendEmail(SPNProviderFirmTask providerFirmTask)
			throws Exception {
		System.out
				.println(providerFirmTask.getLookupSPNWorkflowState().getId());
		boolean result = true;
		Integer providerFirmId = providerFirmTask.getProviderFirmId();
		Integer spnId = providerFirmTask.getSpnId();
		SPNHeader spn = spnHeaderDao.findById(spnId);
		List<ServiceProvider> serviceProviders = serviceProviderDao
				.findAdmin(providerFirmId);
		List<SPNUploadedDocumentState> spnUploadedDocumentStates = uploadedDocumentStateService
				.findBy(spnId, providerFirmId);

		List<LookupEmailTemplate> templates = emailTemplateServices
				.findEmailTemplatesByLookupSPNWorkflowState(
						providerFirmTask.getLookupSPNWorkflowState(),
						EMAIL_ACTION_SPN_AUDITOR_MODIFIED);
		for (LookupEmailTemplate template : templates) {
			for (ServiceProvider serviceProvider : serviceProviders) {
				AlertTask alertTask = null;
				Email email = new Email();
				String emailAddress = serviceProvider.getContact().getEmail();
				email.addTo(emailAddress);
				email.setTemplate(template);

				email.addParam(
						"buyerId",
						String.valueOf(spn.getBuyer().iterator().next()
								.getBuyerId().getBuyerId()));
				email.addParam("providerFirmContactName", serviceProvider
						.getContact().getFirstName()
						+ " "
						+ serviceProvider.getContact().getLastName());
				email.addParam("spnName", spn.getSpnName());
				email.addParam("documentList",
						createDocumentList(spnUploadedDocumentStates));
				email.addParam("spnContactName", spn.getContactName());
				email.addParam("spnContactPhone", spn.getContactPhone());
				email.addParam("buyerCompanyName", spn.getBuyer().iterator()
						.next().getBuyerId().getBusinessName());
				email.addParam("spnContactEmail", spn.getContactEmail());
				email.addParam("auditComment", providerFirmTask.getComment());
				alertTask = mailQueryStringFormatter.insertAlert(email);

				// Code Added for Jira SL-19728
				// For Sending emails specific to NON FUNDED buyer when a
				// provider is approved in a SPN
				// As per new requirements

				if (providerFirmTask.getLookupSPNWorkflowState().getId()
						.equals(WF_STATUS_PF_SPN_MEMBER)) {
					List<BuyerFeatureSet> buyerFeatureSet = spn.getBuyer()
							.iterator().next().getBuyerId()
							.getBuyerfeatureSet();
					if (null != buyerFeatureSet
							&& null != spn.getBuyer().iterator().next()
									.getBuyerId().getBuyerId()) {

						Integer tempId;

						for (BuyerFeatureSet buyerFeatureSetList : buyerFeatureSet) {
							if (buyerFeatureSetList.getFeatureSet().equals(
									BuyerFeatureSetEnum.NON_FUNDED.name())) {
								tempId = 0;
								try {
									tempId = serviceProviderDao
											.getEmailTemplateSpecificToBuyer(
													spn.getBuyer().iterator()
															.next()
															.getBuyerId()
															.getBuyerId(),
													alertTask.getTemplateId());

									if (null != tempId
											&& tempId.intValue() != 0) {
										alertTask.setTemplateId(tempId);
									}
									break;
								} catch (DataServiceException e) {
									logger.error("Exception in sendEmail() method of StateFlowService : "
											+ e.getMessage());
								}
							}

						}
					}
				}
				emailAlertBO.insertToAlertTask(alertTask);
				// result &= emailService.sendEmail(email);
			}
		}

		return result;
	}

	private String createDocumentList(
			List<SPNUploadedDocumentState> spnUploadedDocumentStates) {
		StringBuilder sb = new StringBuilder();

		for (SPNUploadedDocumentState doc : spnUploadedDocumentStates) {
			String docStateId = (String) doc.getSpnDocumentState().getId();
			if (DOC_STATE_INCOMPLETE.equals(docStateId)
					|| DOC_STATE_PENDING_NEED_MORE_INFO.equals(docStateId)) {
				sb.append("<li>");
				sb.append(doc.getSpnUploadedDocumentStatePk()
						.getProviderFirmDocument().getTitle());
				if (StringUtils.isNotBlank(doc.getComments())) {
					sb.append(" - ");
					sb.append(doc.getComments());
				}
				sb.append("</li>");
			}
		}
		if (sb.length() != 0) {
			sb.insert(0, "<ul>");
			sb.append("</ul>");
		}
		return sb.toString();
	}

	/**
	 * @param providerFirmStateService
	 *            the providerFirmStateService to set
	 */
	public void setProviderFirmStateService(
			IProviderFirmStateService providerFirmStateService) {
		this.providerFirmStateService = providerFirmStateService;
	}

	/**
	 * @param emailTemplateServices
	 *            the emailTemplateServices to set
	 */
	public void setEmailTemplateServices(
			EmailTemplateService emailTemplateServices) {
		this.emailTemplateServices = emailTemplateServices;
	}

	/**
	 * @param spnHeaderDao
	 *            the spnHeaderDao to set
	 */
	public void setSpnheaderDao(SPNHeaderDao spnHeaderDao) {
		this.spnHeaderDao = spnHeaderDao;
	}

	/**
	 * @param uploadedDocumentStateService
	 *            the uploadedDocumentStateService to set
	 */
	public void setUploadedDocumentStateService(
			UploadedDocumentStateService uploadedDocumentStateService) {
		this.uploadedDocumentStateService = uploadedDocumentStateService;
	}

	public ServiceProviderDao getServiceProviderDao() {
		return serviceProviderDao;
	}

	public void setServiceProviderDao(ServiceProviderDao serviceProviderDao) {
		this.serviceProviderDao = serviceProviderDao;
	}

	public EmailAlertBO getEmailAlertBO() {
		return emailAlertBO;
	}

	public void setEmailAlertBO(EmailAlertBO emailAlertBO) {
		this.emailAlertBO = emailAlertBO;
	}

	public MailQueryStringFormatter getMailQueryStringFormatter() {
		return mailQueryStringFormatter;
	}

	public void setMailQueryStringFormatter(
			MailQueryStringFormatter mailQueryStringFormatter) {
		this.mailQueryStringFormatter = mailQueryStringFormatter;
	}

}
