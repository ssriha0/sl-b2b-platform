package com.newco.batch.background;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.persistence.iDao.providerBackground.ProviderBackgroundCheckDao;
import com.newco.marketplace.vo.provider.ExpiryNotificationVO;
import com.newco.marketplace.vo.provider.ProviderBackgroundCheckVO;
import com.newco.marketplace.vo.provider.VendorNotesVO;

//R11_2
//SL-20437
//  Provider background check notification
public class BackgroundChkRecertificationProcess {

	private static final Logger logger = Logger
			.getLogger(BackgroundChkRecertificationProcess.class);
	private List<VendorNotesVO> vendorNotesList = null;
	private List<ExpiryNotificationVO> expiryNotificationList = null;
	private List<AlertTask> alertTaskList = null;

	private ProviderBackgroundCheckDao providerBackgroundCheckDao;

	public void process() {
		vendorNotesList = new ArrayList<VendorNotesVO>();
		expiryNotificationList = new ArrayList<ExpiryNotificationVO>();
		alertTaskList = new ArrayList<AlertTask>();
		logger.info("start of process method in BackgroundChkRecertificationProcess");
		

		// get all providers whose expiration date is in next month.
		
		try {
			List<ProviderBackgroundCheckVO> backgroundCheckList = providerBackgroundCheckDao
					.getProvInfoForNotification();
			
			//creating new map with key as Firm Id with details of all resources under the firm.
			Map<Integer, List<ProviderBackgroundCheckVO>> backgroundCheckMap = new LinkedHashMap<Integer, List<ProviderBackgroundCheckVO>>();

			if (null != backgroundCheckList && !backgroundCheckList.isEmpty()) {
				Integer vendorId = backgroundCheckList.get(Constants.INT_ZERO).getVendorId();

				List<ProviderBackgroundCheckVO> providerBackgroundlist = new ArrayList<ProviderBackgroundCheckVO>();

				for (ProviderBackgroundCheckVO providerCheck : backgroundCheckList) {

					if (providerCheck.getVendorId().equals(vendorId)) {
						providerBackgroundlist.add(providerCheck);
					} else {
						providerBackgroundlist = new ArrayList<ProviderBackgroundCheckVO>();
						vendorId = providerCheck.getVendorId();
						providerBackgroundlist.add(providerCheck);

					}
					backgroundCheckMap.put(vendorId, providerBackgroundlist);

				}

				backgroundCheckMap.put(vendorId, providerBackgroundlist);
			}
			//iterate through each firm's notification data
			Set<Integer> keys = backgroundCheckMap.keySet();
			for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
				Integer vendorId = (Integer) iterator.next();

				List<ProviderBackgroundCheckVO> backgroundCheckVoList = backgroundCheckMap
						.get(vendorId);

				String vendorEmail = backgroundCheckVoList.get(Constants.INT_ZERO)
						.getVendorEmail();
				String firmName = backgroundCheckVoList.get(Constants.INT_ZERO)
						.getVendorFirstName();
				StringBuilder notificationData = new StringBuilder(Constants.TABLE_HEADER);
				Integer resourceId = Constants.INTEGER_ZERO;
				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				Integer slNo = Constants.INTEGER_ONE;
				for (ProviderBackgroundCheckVO providerCheck : backgroundCheckVoList) {

					if (null != providerCheck.getResourceId()
							&& !providerCheck.getResourceId()
									.equals(resourceId)) {
						// add audit notes.
						insertAuditNotes(providerCheck);
						// creating notification Data with all resources under a firm
						notificationData.append(Constants.TABLE_BODY_TR) 
								.append(Constants.TABLE_BODY_TD)
								.append( slNo.toString())
								.append( Constants.TABLE_BODY_TD_CLOSE)
								.append( Constants.TABLE_BODY_TD_NAME)
								.append( providerCheck.getResourceFirstName())
								.append( Constants.COMMA_STRING)
								.append(Constants.SPACE_STRING)
								.append(providerCheck.getResourceLastName())
								.append( Constants.TABLE_BODY_TD_CLOSE)
								.append( Constants.TABLE_BODY_TD)
								.append(providerCheck.getResourceId())
								.append(Constants.TABLE_BODY_TD_CLOSE)
								.append(Constants.TABLE_BODY_TD_DATE)
								.append(dateFormat.format(providerCheck
										.getExpirationDate()))
								.append(Constants.TABLE_BODY_TD_CLOSE)
								.append(Constants.TABLE_BODY_TR_CLOSE);

						slNo++;
					}

					// add entry for spn
					insertAuditEntry(providerCheck);
					resourceId = providerCheck.getResourceId();
				}
				notificationData.append(Constants.TABLE_FOOTER);
				
				
				// send email to the firm.
				sendEmailtoFirm(notificationData.toString(), vendorEmail, firmName);
			}

			providerBackgroundCheckDao
					.insertExpiryNotification(expiryNotificationList);
			providerBackgroundCheckDao.insertVendorNotes(vendorNotesList);
			providerBackgroundCheckDao.addAlertToQueue(alertTaskList);
			logger
					.info("end of process method in BackgroundChkRecertificationProcess");
		} catch (DataServiceException e) {
			logger.error("error while fecthing provider info" + e);

		} catch (Exception e) {
			logger.error("error while fecthing provider info" + e);

		}

	}

	// adding entry in vendor_notes
	public void insertAuditNotes(ProviderBackgroundCheckVO providerCheck) {
		VendorNotesVO vendorNotes = new VendorNotesVO();
		String note = Constants.EMPTY_STRING;
		// add note with background check description with resource Id
		if (null != providerCheck.getResourceId()) {
			note = Constants.BACKGROUND_CHECK_DESCRIPTION
					+ providerCheck.getResourceId().toString();
		}
		vendorNotes.setNote(note);
		vendorNotes.setVendorId(providerCheck.getVendorId());
		vendorNotes.setModifiedBy(Constants.SYSTEM);

		vendorNotesList.add(vendorNotes);
	}

	// adding entry in alert_task
	public void sendEmailtoFirm(String notificationData, String vendorEmail,
			String firmName) {
		Map<String, String> alertMap = new HashMap<String, String>();
		alertMap.put(Constants.NOTIFICATION_DATA, notificationData);
		alertMap.put(Constants.FIRM_NAME, firmName);
		//creating new entry for alert_task
		AlertTask alertTask = new AlertTask();
		Date currentdate = new Date();
		alertTask.setAlertedTimestamp(null);
		alertTask.setAlertTypeId(AlertConstants.ALERT_TYPE_ID);
		alertTask.setCreatedDate(currentdate);
		alertTask.setModifiedDate(currentdate);
		//setting template_id
		alertTask.setTemplateId(AlertConstants.TEMPLATE_PROVIDER_CHECK_EMAIL);
		alertTask.setAlertFrom(AlertConstants.SERVICE_LIVE_COMPLAINCE_MAILID);
		alertTask.setAlertTo(vendorEmail);
		alertTask.setAlertBcc(Constants.EMPTY_STRING);
		alertTask.setAlertCc(Constants.EMPTY_STRING);
		alertTask.setCompletionIndicator(AlertConstants.INCOMPLETE_INDICATOR);
		alertTask.setPriority(AlertConstants.PRIORITY);
		//generating template_input_value from Map
		alertTask
				.setTemplateInputValue(createMailMergeValueStringFromMap(alertMap));
		alertTaskList.add(alertTask);
	}

	// return the template input value for alert_task
	public String createMailMergeValueStringFromMap(
			Map<String, String> templateMergeValueMap) {
		StringBuilder stringBuilder = new StringBuilder("");
		boolean isFirstKey = true;
		if (templateMergeValueMap != null) {
			Set<String> keySet = templateMergeValueMap.keySet();
			for (String key : keySet) {
				if (isFirstKey) {
					isFirstKey = !isFirstKey;
				} else {
					stringBuilder.append("|");
				}

				stringBuilder.append(key).append("=").append(
						templateMergeValueMap.get(key));
			}
		}
		return stringBuilder.toString();
	}

	// adding entry in audit_cred_expiry_notification
	public void insertAuditEntry(ProviderBackgroundCheckVO providerCheck) {
		
			ExpiryNotificationVO expiryNotification = new ExpiryNotificationVO();
			expiryNotification.setVendorId(providerCheck.getVendorId());
			expiryNotification.setResourceId(providerCheck.getResourceId());
			//set notificationType as 30
			expiryNotification
					.setNotificationType(Constants.BACKGROUND_NOTIFICATION_TYPE);
			expiryNotification.setExpirationDate(providerCheck
					.getExpirationDate());
			//set credential indicator as 4
			expiryNotification
					.setCredentialIndicator(Constants.BACKGROUND_CREDENTIAL_IND);
			expiryNotification.setCredentialName(Constants.SPN_PRO_ID
					+ providerCheck.getResourceId().toString()
					+ Constants.SPN_REQUIREMENT_TYPE);
			expiryNotificationList.add(expiryNotification);
		
	}

	public ProviderBackgroundCheckDao getProviderBackgroundCheckDao() {
		return providerBackgroundCheckDao;
	}

	public void setProviderBackgroundCheckDao(
			ProviderBackgroundCheckDao providerBackgroundCheckDao) {
		this.providerBackgroundCheckDao = providerBackgroundCheckDao;
	}


}
