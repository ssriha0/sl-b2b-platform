package com.servicelive.spn.services.campaign;

import static com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_PF_INVITED_TO_SPN;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.business.EmailAlertBO;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.servicelive.domain.lookup.LookupEmailTemplate;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.spn.detached.Email;
import com.servicelive.domain.spn.detached.ProviderFirmVO;
import com.servicelive.spn.common.SPNBackendConstants;
import com.servicelive.spn.common.detached.CampaignInvitationVO;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.common.util.UiUtil;
import com.servicelive.spn.services.BaseServices;
import com.servicelive.spn.services.email.EmailTemplateService;
import com.servicelive.spn.services.email.MailQueryStringFormatter;
import com.servicelive.spn.services.providermatch.ProviderMatchForApprovalServices;
/**
 * 
 * @author svanloo
 *
 */
public class CampaignInvitationServices extends BaseServices {

	private String dialect = "mysql";
	private EmailTemplateService emailTemplateServices;
	private ProviderMatchForApprovalServices providerMatchForApprovalServices;
	private MailQueryStringFormatter mailQueryStringFormatter;
	private EmailAlertBO emailAlertBO;
	@SuppressWarnings("unused")
	@Override
	protected void handleDates(Object entity) {/* do nothing */}

	/**
	 * This is the main entry point for this class.
	 * @throws Exception 
	 */
	public void inviteProviders() throws Exception {
		List<CampaignInvitationVO> campaigns = this.listActiveCampaigns();
		for(CampaignInvitationVO campaign:campaigns) {
			inviteProvidersForCampaign(campaign);
		}
	}

	@SuppressWarnings("unchecked")
	private List<CampaignInvitationVO> listActiveCampaigns() throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("now", CalendarUtil.getTodayAtMidnight());
		return  getSqlMapClient().queryForList("campaign.invitation.getcampaigns", map);
	}

	protected void inviteProvidersForCampaign(CampaignInvitationVO campaign) throws Exception {
		List<ProviderFirmVO> providerFirmList = new ArrayList<ProviderFirmVO>();
		Integer campaignId = campaign.getCampaignId();
		Integer spnId = campaign.getSpnId();
		if(campaign.getIsSpecificFirmCampaign() != null && (campaign.getIsSpecificFirmCampaign().booleanValue() == true)){
			providerFirmList = providerMatchForApprovalServices.getListOfAdminsForSpecificInviteFirmInvitation(spnId, campaignId);
		}else {
			providerFirmList = providerMatchForApprovalServices.getListOfAdminsForCampaignInvitation(campaign);
		}
		for(ProviderFirmVO providerFirm:providerFirmList) {
			logger.info("sending out email for campaignId[" + campaignId + "], spnId["+spnId+"]");
			sendEmail(providerFirm, campaign);
		}
	}

	private void insertProviderFirmForInvitedState(ProviderFirmVO providerFirm, CampaignInvitationVO campaign) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("spnId", campaign.getSpnId());
		params.put("providerFirmId", providerFirm.getProviderFirmId());
		params.put("now", CalendarUtil.getNow());
		params.put("providerWfState", SPNBackendConstants.WF_STATUS_PF_INVITED_TO_SPN);
		getSqlMapClient().insert("campaign.invitation.insertProviderFirmForInvitedState", params);		
	}

	/**
	 * 
	 * @param providerFirmId
	 * @param spnId
	 * @return Date
	 * @throws SQLException
	 */
	public Date findProviderFirmInvitedDate(Integer providerFirmId, Integer spnId) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("spnId", spnId);
		params.put("providerFirmId", providerFirmId);
		List<?> resultList = getSqlMapClient().queryForList("campaign.invitation.findProviderFirmInvitedDate", params);
		return (Date) resultList.iterator().next();
	}
	
	
	protected void sendEmail(ProviderFirmVO providerFirm, CampaignInvitationVO campaign) {
		LookupSPNWorkflowState state = new LookupSPNWorkflowState();
		state.setId(WF_STATUS_PF_INVITED_TO_SPN);

		for(LookupEmailTemplate template:emailTemplateServices.findEmailTemplatesByLookupSPNWorkflowState(state, SPNBackendConstants.EMAIL_ACTION_SPN_JOIN)) {

			// need to add audit
			try {
				// send email
				AlertTask alertTask=null;
				Email email = new Email();
				email.addTo(providerFirm.getEmailAddress());
				email.setFrom(campaign.getSpnContactEmail());
				email.setTemplate(template);
				email.addParam("buyerId", String.valueOf(campaign.getBuyerId()));
				email.addParam("spnCompanyName", campaign.getSpnCompanyName());
				email.addParam("spnName", campaign.getSpnName());
				email.addParam("spnContactName", campaign.getSpnContactName());
				email.addParam("spnContactPhone", UiUtil.formatPhone(campaign.getSpnContactPhone()));
				email.addParam("spnContactEmail", campaign.getSpnContactEmail());
				//TODO email.addParam("serviceLiveUrl", "http://www.servicelive.com/");
				email.addParam("campaignEndDate", CalendarUtil.formatDateForDisplay(campaign.getEndDate()));
				email.addParam("campaignStartDate", CalendarUtil.formatDateForDisplay(campaign.getStartDate()));
				email.addParam("providerFirmAdminName", providerFirm.getProviderFirmAdminName());
				alertTask=mailQueryStringFormatter.insertAlert(email);
				
				//check the eligibility of provider firm before routing the invitation
				String insertUpdateFlag = checkSPNInvitationEligibility(providerFirm,campaign);
				if(insertUpdateFlag.equals("insert")){
					emailAlertBO.insertToAlertTask(alertTask);
					insertSpnetCampaignInvitation(providerFirm, campaign, template);
					insertProviderFirmForInvitedState(providerFirm, campaign);
				}else if(insertUpdateFlag.equals("true")){
					emailAlertBO.insertToAlertTask(alertTask);
					insertSpnetCampaignInvitation(providerFirm, campaign, template);
					updateProviderFirmForInvitedState(providerFirm, campaign);
				}
				
				
			} catch(RuntimeException e) {
				logger.info("couldn't send email for the following reason", e);
				//e.printStackTrace();
			} catch (SQLException e) {
				logger.warn("couldn't store in database that email was already sent to " + providerFirm.getEmailAddress(), e);
				//e.printStackTrace();
			}catch (Exception e){
				logger.info("couldn't send email for the following reason", e);
			}
		}
	}


	@Transactional(propagation=Propagation.REQUIRED)
	private void insertSpnetCampaignInvitation(ProviderFirmVO providerFirm, CampaignInvitationVO campaign, LookupEmailTemplate template) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("spnId", campaign.getSpnId()); 
		params.put("campaignId", campaign.getCampaignId());
		params.put("providerFirmId", providerFirm.getProviderFirmId());
		params.put("providerFirmAdminId", providerFirm.getProviderFirmAdminId());
		params.put("emailAddress", providerFirm.getEmailAddress());
		Object invitationIdObject = getSqlMapClient().insert("campaign.invitation.insertspnetcampaigninvitation"+dialect, params);
		if(invitationIdObject != null) {
			Integer invitationId = (Integer) invitationIdObject;
			//System.out.println("##################invitationId=" + invitationId);
			Map<String, Object> params2 = new HashMap<String, Object>();
			params2.put("invitationId", invitationId);
			params2.put("emailTemplateId", template.getTemplateId());
			params2.put("emailSentTime", CalendarUtil.getNow());
			getSqlMapClient().insert("campaign.invitation.insertspnetcampaigninvitationemail", params2);
			//System.out.println("#############Successfully inserted into spnetcampaignInvitationEmail");
		}
	}
	
	private String checkSPNInvitationEligibility(ProviderFirmVO providerFirm, CampaignInvitationVO campaign) throws SQLException {
		String insertUpdateFlag = SPNBackendConstants.TRUE;
		int spnCampaignListSize = SPNBackendConstants.ZERO;
		List<CampaignInvitationVO> spnetCampaignList = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("spnId", campaign.getSpnId());
		params.put("providerFirmId", providerFirm.getProviderFirmId());
		spnetCampaignList = getSqlMapClient().queryForList("campaign.invitation.spnetInviatationEligibility", params);
		if(null!=spnetCampaignList){
			spnCampaignListSize = spnetCampaignList.size();
		}
		
		if(spnCampaignListSize == SPNBackendConstants.ZERO){
			insertUpdateFlag = SPNBackendConstants.INSERT;
		}else{
			for(CampaignInvitationVO campaignVO : spnetCampaignList){
				if(campaignVO.getState().equals(SPNBackendConstants.WF_STATUS_CAMPAIGN_ACTIVE)){
					insertUpdateFlag = SPNBackendConstants.FALSE;
					break;
				}
			}
		}
		
		
		return insertUpdateFlag;
		
	}
	
	private void updateProviderFirmForInvitedState(ProviderFirmVO providerFirm, CampaignInvitationVO campaign) throws SQLException {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("spnId", campaign.getSpnId());
		params.put("providerFirmId", providerFirm.getProviderFirmId());
		params.put("now", CalendarUtil.getNow());
		params.put("providerWfState", SPNBackendConstants.WF_STATUS_PF_INVITED_TO_SPN);
		getSqlMapClient().update("campaign.invitation.updateProviderFirmForInvitedState", params);		
	}
	
	/**
	 * 
	 * @param providerFirmId
	 * @param spnId
	 * @return Date
	 * @throws SQLException
	 */
	public String checkProvFirmCampaignStatus(Integer providerFirmId, Integer spnId){
		String status = "true";
		ProviderFirmVO providerFirm = new ProviderFirmVO();
		CampaignInvitationVO campaign = new CampaignInvitationVO();
		providerFirm.setProviderFirmId(providerFirmId);
		campaign.setSpnId(spnId);
		try{
			status = checkSPNInvitationEligibility(providerFirm,campaign);
		}catch(SQLException ex){
			logger.info("Error occurred in checkProvFirmCampaignStatus of CampaignInvitationServices: "+ex);
		}
		return status;
	}
	
	/**
	 * @param emailTemplateServices the emailTemplateServices to set
	 */
	public void setEmailTemplateServices(EmailTemplateService emailTemplateServices) {
		this.emailTemplateServices = emailTemplateServices;
	}

	/**
	 * @param providerMatchForApprovalServices the providerMatchForApprovalServices to set
	 */
	public void setProviderMatchService(ProviderMatchForApprovalServices providerMatchForApprovalServices) {
		this.providerMatchForApprovalServices = providerMatchForApprovalServices;
	}

	/**
	 * @param dialect the dialect to set
	 */
	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public MailQueryStringFormatter getMailQueryStringFormatter() {
		return mailQueryStringFormatter;
	}

	public void setMailQueryStringFormatter(
			MailQueryStringFormatter mailQueryStringFormatter) {
		this.mailQueryStringFormatter = mailQueryStringFormatter;
	}

	public EmailAlertBO getEmailAlertBO() {
		return emailAlertBO;
	}

	public void setEmailAlertBO(EmailAlertBO emailAlertBO) {
		this.emailAlertBO = emailAlertBO;
	}
}