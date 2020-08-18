package com.servicelive.wallet.alert;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;


import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.alert.vo.AlertTaskVO;
import com.servicelive.wallet.alert.vo.ContactVO;
import com.servicelive.wallet.alert.vo.TemplateVO;

// TODO: Auto-generated Javadoc
/**
 * Class AlertBO.
 */
public class AlertBO extends BaseAlert implements IAlertBO {

	/** logger. */
	private static final Logger logger = Logger.getLogger(AlertBO.class.getName());

	/**
	 * getEmailAlertTask.
	 * 
	 * @param inputValue 
	 * @param templateId 
	 * @param alertTo 
	 * 
	 * @return AlertTaskVO
	 * 
	 * @throws DataServiceException 
	 */
	private AlertTaskVO getEmailAlertTask(String inputValue, int templateId, String alertTo) throws DataServiceException {

		TemplateVO template = templateDao.getTemplateById(templateId);
		AlertTaskVO alertTask = new AlertTaskVO();
		Date date = new Date();
		alertTask.setAlertedTimestamp(null);
		alertTask.setAlertTypeId(template.getTemplateTypeId());
		alertTask.setCreatedDate(date);
		alertTask.setModifiedDate(date);
		alertTask.setTemplateId(templateId);
		if (template.getTemplateFrom() != null) alertTask.setAlertFrom(template.getTemplateFrom());// AlertConstants.SERVICE_LIVE_MAILID);
		else alertTask.setAlertFrom(CommonConstants.SERVICE_LIVE_MAILID);
		alertTask.setAlertTo(alertTo);
		alertTask.setAlertBcc("");
		alertTask.setAlertCc("");
		alertTask.setCompletionIndicator("1");
		alertTask.setPriority(template.getPriority());
		alertTask.setTemplateInputValue(inputValue);
		return alertTask;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.alert.IAlertBO#sendACHFailureAlertForProvider(long, java.lang.String, java.lang.Double, java.lang.String, int)
	 */
	public void sendACHFailureAlertForProvider(long providerId, String transId, Double amount, String returnDesc, int templateId) {

		try {
			List<ContactVO> contacts = contactDao.getProviderInformation(providerId);

			for (ContactVO contact : contacts) {
				try {
					Calendar cal = Calendar.getInstance();
					Date date = cal.getTime();
					HashMap<String, Object> vContext = new AlertHashMap();
					vContext.put("FNAME", contact.getFirstName());
					vContext.put("LNAME", contact.getLastName());
					vContext.put("USERNAME", contact.getFirstName() + " " + contact.getLastName());
					vContext.put("TRANSACTION_ID", transId);
					vContext.put("TRANS_AMOUNT", amount.toString());
					vContext.put("RETURN_CODE_DESC", returnDesc);
					vContext.put("CURRENT_DATE", date.toString());
					vContext.put("SERVICE_URL", "http://" + getSiteUrl() + "/termsAndConditions_displayBucksAgreement.action");
					try{
						vContext.put("SERVICE_URL", getSiteUrlForRole(CommonConstants.SL_BUCKS_PROFESSIONAL));
					}catch(Exception e){
						logger.error("Exception occured while getting value from appl prop");
					}
					
					if (contact.getEmailAddress() != null){
						AlertTaskVO alertTask = getEmailAlertTask(vContext.toString(), templateId, contact.getEmailAddress());
						alertDao.addAlertToQueue(alertTask);
					}else{
						logger.error("Provider id " + providerId + " does not have an email on file");
					}
				} catch (DataServiceException e) {
					logger.error("sendACHFailureAlertForProvider-->DataServiceException--> inner loop continuing with other contacts", e);
				}
			}
		} catch (DataServiceException e) {
			logger.error("sendACHFailureAlertForProvider-->DataServiceException-->", e);
		}
	}

	/**
	 * sendSettlementConfirmation.
	 * 
	 * @param contact 
	 * @param ledgerId 
	 * @param amount 
	 * @param templateId 
	 * 
	 * @return void
	 */
	private void sendSettlementConfirmation(ContactVO contact, String ledgerId, Double amount, int templateId, String roleType) {

		try {
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			HashMap<String, Object> vContext = new AlertHashMap();
			vContext.put("FNAME", contact.getFirstName());
			vContext.put("LNAME", contact.getLastName());
			vContext.put("LEDGER_ID", ledgerId);// constant says ledger_id but it is actually ledgerTransactionId
			vContext.put("TRANS_AMOUNT", "$" + amount.toString());
			vContext.put("CURRENT_DATE", date.toString());
			vContext.put("SERVICE_URL", "http://" + getSiteUrl() + "/termsAndConditions_displayBucksAgreement.action");
			//Code for getting SERVICE_URL based on role
			try{
				if(null != roleType){
					if("buyer".equalsIgnoreCase(roleType)||
							"provider".equalsIgnoreCase(roleType)){
						vContext.put("SERVICE_URL",getSiteUrlForRole("sl_bucks_professional"));
					}else{
						vContext.put("SERVICE_URL",getSiteUrlForRole("sl_bucks_simple"));
					}	
				}
			}catch(Exception e){
				logger.error("Exception occured while getting value from appl prop");
			}	
			AlertTaskVO alert = getEmailAlertTask(vContext.toString(), templateId, contact.getEmailAddress());
			alertDao.addAlertToQueue(alert);
			logger.debug("email SENT************************");
		} catch (DataServiceException e) {
			logger.error("sendSettlementConfirmation-->DataServiceException-->", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.alert.IAlertBO#sendSettlementConfirmationToBuyer(long, java.lang.String, java.lang.Double, int)
	 */
	public void sendSettlementConfirmationToBuyer(long buyerId, String ledgerId, Double amount, int templateId, String roleType) {

		try {
			List<ContactVO> contacts = contactDao.getBuyerInformation(buyerId);
			for (ContactVO contact : contacts) {
				sendSettlementConfirmation(contact, ledgerId, amount, templateId,roleType);
			}

		} catch (DataServiceException e) {
			logger.error("sendSettlementConfirmationToBuyer-->DataServiceException-->", e);
		}
	}

	// To send a mail if origination file is not present when the scheduler job runs
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.alert.IAlertBO#sendSettlementConfirmationToProvider(long, java.lang.String, java.lang.Double, int)
	 */
	public void sendSettlementConfirmationToProvider(long providerId, String ledgerId, Double amount, int templateId) {

		try {
			List<ContactVO> contacts = contactDao.getProviderInformation(providerId);
			for (ContactVO contact : contacts) {
				sendSettlementConfirmation(contact, ledgerId, amount, templateId,CommonConstants.PROVIDER_ROLE);
			}

		} catch (DataServiceException e) {
			logger.error("sendSettlementConfirmationToProvider-->DataServiceException-->", e);
		}
	}


	/* (non-Javadoc)
	 * @see com.servicelive.wallet.alert.IAlertBO#sendEmailVLMessageFailure(long, java.lang.String, java.lang.String, java.lang.String, long, long, java.sql.Timestamp, java.lang.String, long, java.lang.String, double, java.lang.String, int)
	 */
	public void sendEmailVLMessageFailure(long ledgerEntityId, String role, String actionCode, String actionCodeDesc,
		long fullfillmentGroupId, long fullfillmentEntryId, Timestamp entryDate,
		String messageIdentifier, long primaryAccountNumber, String entityTypeDesc, 
		double transactionAmount, String serviceOrderId, int templateId) {

		try {
			
			List<ContactVO> contacts = null;
			if( role != null ) {
				if(templateId == CommonConstants.EMAIL_TEMPLATE_VL_RESPONSE_FAILURE) {
					contacts = new ArrayList<ContactVO>();
					ContactVO contactVo = new ContactVO();
					contactVo.setEmailAddress(CommonConstants.SERVICE_LIVE_MAILID_SO_SUPPORT);
					contacts.add(contactVo);	
				}else if( role.equals("BUYER")) {
					contacts = contactDao.getBuyerInformation(ledgerEntityId);	
				} else if(role.equals("PROVIDER")) {
					contacts = contactDao.getProviderInformation(ledgerEntityId);	
				} else if(role.equals("SERVICELIVE")) {
					
				}
			}
			if( contacts != null ) {
				for (ContactVO contact : contacts) {
					this.sendEmailVLMessageFailure(contact, actionCode, actionCodeDesc, fullfillmentGroupId, fullfillmentEntryId, 
						entryDate, ledgerEntityId, messageIdentifier, primaryAccountNumber, entityTypeDesc, transactionAmount, 
						serviceOrderId, templateId);
				}
			}
		} catch (DataServiceException e) {
			logger.error("sendEmailVLMessageFailure-->DataServiceException-->", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.alert.IAlertBO#sendACHFailureAlertForProvider(long, java.lang.String, java.lang.Double, java.lang.String, int)
	 */
	public void sendEscheatmentACHFailuretoProdSupp(String transId, Double amount, String returnDesc, int templateId) {

		  try {
				    HashMap<String, Object> vContext = new AlertHashMap();
					vContext.put("TRANSACTION_ID", transId);
					vContext.put("TRANS_AMOUNT", amount.toString());
					vContext.put("RETURN_CODE_DESC", returnDesc);
					
					AlertTaskVO alertTask = getEmailAlertTask(vContext.toString(), templateId,CommonConstants.SERVICE_LIVE_MAILID_PROD_SUPPORT);
					alertDao.addAlertToQueue(alertTask);
					
				} catch (DataServiceException e) {
					logger.error("sendEscheatmentACHFailuretoProdSupp-->DataServiceException--> inner loop continuing with other contacts", e);
				}
	}
	

	/**
	 * sendEmailVLMessageFailure.
	 * 
	 * @param contact 
	 * @param actionCode 
	 * @param actionCodeDesc 
	 * @param fullfillmentGroupId 
	 * @param fullfillmentEntryId 
	 * @param entryDate 
	 * @param ledgerEntityId 
	 * @param messageIdentifier 
	 * @param primaryAccountNumber 
	 * @param entityTypeDesc 
	 * @param transactionAmount 
	 * @param serviceOrderId 
	 * @param templateId 
	 * 
	 * @return void
	 */
	private void sendEmailVLMessageFailure(ContactVO contact, String actionCode, String actionCodeDesc,
		long fullfillmentGroupId, long fullfillmentEntryId, Date entryDate, long ledgerEntityId, 
		String messageIdentifier, long primaryAccountNumber, String entityTypeDesc, 
		double transactionAmount, String serviceOrderId, int templateId) {

    	try {
			logger.debug("sendEmailVLResponseFailure-->START");
			HashMap<String, Object> vContext = new AlertHashMap();
			vContext.put("ACTION_CODE", actionCode);
			vContext.put("ACTION_CODE_DESC", actionCodeDesc);
			vContext.put("FULLFILLMENT_GROUP_ID", fullfillmentGroupId);
			vContext.put("FULLFILLMENT_ENTRY_ID", fullfillmentEntryId);
			vContext.put("ENTRY_DATE", entryDate);
			vContext.put("ENTITY_INFO", entityTypeDesc + " - " + ledgerEntityId);
			vContext.put("MESSAGE_TYPE", messageIdentifier);
			vContext.put("PRIMARY_ACCOUNT_NUMBER", primaryAccountNumber);
			vContext.put("TRANS_AMOUNT", transactionAmount);
			vContext.put("SO_ID", serviceOrderId);

			AlertTaskVO alert = getEmailAlertTask(vContext.toString(), templateId, contact.getEmailAddress());
			alertDao.addAlertToQueue(alert);
		} catch (DataServiceException e) {
			logger.error("sendEmailVLMessageFailure-->DataServiceException-->", e);
		}
	}	
}
