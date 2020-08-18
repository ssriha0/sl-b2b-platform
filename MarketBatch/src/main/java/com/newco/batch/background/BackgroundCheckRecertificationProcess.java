package com.newco.batch.background;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AlertConstants;
import com.newco.marketplace.persistence.iDao.providerBackground.ProviderBackgroundCheckDao;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.utils.DateUtils;
import com.newco.marketplace.vo.provider.ExpiryNotificationVO;
import com.newco.marketplace.vo.provider.ProviderBackgroundCheckVO;
import com.newco.marketplace.vo.provider.VendorNotesVO;

//Provider background check notification
public class BackgroundCheckRecertificationProcess {

	private static final Logger logger = Logger
			.getLogger(BackgroundCheckRecertificationProcess.class);

	private List<VendorNotesVO> vendorNotesList = null;
	private ProviderBackgroundCheckDao providerBackgroundCheckDao;
	private List<ExpiryNotificationVO> expiryNotificationListThirty = null;
	private List<ExpiryNotificationVO> expiryNotificationListSeven = null;
	private List<ExpiryNotificationVO> expiryNotificationListZero = null;
	private List<Integer> notificationIdsListSeven = null;
	private List<Integer> notificationIdsListZero = null;
	private List<Integer> notificationIdsListThirty = null;
	private List<ProviderBackgroundCheckVO> sevenDaysListUpdate = null;
	private List<ProviderBackgroundCheckVO> zeroDaysListUpdate = null;
	private List<ProviderBackgroundCheckVO> thirtyDaysListUpdate = null;
	private List<AlertTask> alertTaskList = null;
//R11_0 Background check notification email
	public void process() {

		logger.info("start of process method in BackgroundCheckRecertificationProcess");

		try {
			List<ProviderBackgroundCheckVO> backgroundCheckList = providerBackgroundCheckDao
					.getProviderInfoForNotification();

			// creating new map with key as Firm Id with details of all
			// resources under the firm.
			Map<Integer, List<ProviderBackgroundCheckVO>> backgroundCheckMap = new LinkedHashMap<Integer, List<ProviderBackgroundCheckVO>>();

			if (null != backgroundCheckList && !backgroundCheckList.isEmpty()) {
				logger.info("Inside of grouping resources under Vendor method");
				Integer vendorId = backgroundCheckList.get(Constants.INT_ZERO)
						.getVendorId();

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
			logger.info("backgroundChecckMAp created:" + backgroundCheckMap);
			// Creating a new map with key as ProviderId and value as another
			// map with key as notification type and value list of VO.
			Map<Integer, Map<Integer, List<ProviderBackgroundCheckVO>>> groupedMap = new LinkedHashMap<Integer, Map<Integer, List<ProviderBackgroundCheckVO>>>();
			logger.info("inside of grouping method");
			Set<Integer> keys = backgroundCheckMap.keySet();
			for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
				Integer vendorId = (Integer) iterator.next();

				List<ProviderBackgroundCheckVO> zeroDaysList = new ArrayList<ProviderBackgroundCheckVO>();
				List<ProviderBackgroundCheckVO> sevenDaysList = new ArrayList<ProviderBackgroundCheckVO>();
				List<ProviderBackgroundCheckVO> thirtyDaysList = new ArrayList<ProviderBackgroundCheckVO>();
				List<ProviderBackgroundCheckVO> pastDueList = new ArrayList<ProviderBackgroundCheckVO>();
				Map<Integer, List<ProviderBackgroundCheckVO>> notificationMap = new LinkedHashMap<Integer, List<ProviderBackgroundCheckVO>>();

				List<ProviderBackgroundCheckVO> backgroundCheckVoList = backgroundCheckMap
						.get(vendorId);

				for (ProviderBackgroundCheckVO pVO : backgroundCheckVoList) {
					if (checkFor30Days(pVO.getReVerficationDate())) {
						thirtyDaysList.add(pVO);
					} else if (checkFor7Days(pVO.getReVerficationDate())) {
						sevenDaysList.add(pVO);
					} else if (checkForToday(pVO.getReVerficationDate())) {
						zeroDaysList.add(pVO);
					} else if (checkForPastDue(pVO.getReVerficationDate())) {
						pastDueList.add(pVO);
					}
					notificationMap.put(Constants.NOTIFICATION_ZERO,
							zeroDaysList);
					notificationMap.put(Constants.NOTIFICATION_SEVEN,
							sevenDaysList);
					notificationMap.put(Constants.NOTIFICATION_THIRTY,
							thirtyDaysList);
					notificationMap.put(Constants.PAST_DUE, pastDueList);
				}
				groupedMap.put(vendorId, notificationMap);
			}
			logger.info("Groupedd MAp created:" + groupedMap);
			// Iterating through each firm's data and setting notification data
			for (Entry<Integer, Map<Integer, List<ProviderBackgroundCheckVO>>> entry : groupedMap
					.entrySet()) {
				Integer providerId = entry.getKey();
				Map<Integer, List<ProviderBackgroundCheckVO>> groupMap = groupedMap
						.get(providerId);

				vendorNotesList = new ArrayList<VendorNotesVO>();
				expiryNotificationListThirty = new ArrayList<ExpiryNotificationVO>();
				expiryNotificationListSeven = new ArrayList<ExpiryNotificationVO>();
				expiryNotificationListZero = new ArrayList<ExpiryNotificationVO>();
				alertTaskList = new ArrayList<AlertTask>();
				notificationIdsListSeven = new ArrayList<Integer>();
				notificationIdsListZero = new ArrayList<Integer>();
				notificationIdsListThirty = new ArrayList<Integer>();
				sevenDaysListUpdate = new ArrayList<ProviderBackgroundCheckVO>();
				zeroDaysListUpdate = new ArrayList<ProviderBackgroundCheckVO>();
				thirtyDaysListUpdate = new ArrayList<ProviderBackgroundCheckVO>();
				
				List<ProviderBackgroundCheckVO> pastDueList = new ArrayList<ProviderBackgroundCheckVO>();
				String vendorEmail = null;
				String firmName = null;
				StringBuilder notificationData = new StringBuilder();
				Integer slNo = Constants.INTEGER_ONE;
				pastDueList = groupMap.get(-1);
				for (Entry<Integer, List<ProviderBackgroundCheckVO>> groupMapEntry : groupMap
						.entrySet()) {
					Integer notificationType = groupMapEntry.getKey();
					List<ProviderBackgroundCheckVO> pVOList = groupMap
							.get(notificationType);

					// Sending mail depending upon the notification type
					if (pVOList != null & !pVOList.isEmpty()) {
						vendorEmail = pVOList.get(Constants.INT_ZERO)
								.getVendorEmail();
						firmName = pVOList.get(Constants.INT_ZERO)
								.getVendorFirstName();

						if (notificationType.intValue() == Constants.NOTIFICATION_ZERO
								.intValue()) {
							logger.info("inside of Notification Zero");
							Integer resourceId = Constants.INTEGER_ZERO;

							for (ProviderBackgroundCheckVO provider : pVOList) {

								if (null != provider.getResourceId()
										&& !provider.getResourceId().equals(
												resourceId)) {
									// add audit notes.
									insertAuditNotes(provider, notificationType);
									// creating notification Data with all
									// resources under a firm
									notificationData.append(slNo.toString());
									setNotificationData(notificationData,
											provider, notificationType);
									slNo++;
								}
								if (null!= provider.getNotificationType()) {
									zeroDaysListUpdate.add(provider);
									// updating audit entry of 7/30 to 0
									updateAuditEntry(zeroDaysListUpdate,
											notificationType);
								} else {
									// inserting audit entry
									insertAuditEntryForZeroDays(provider,
											notificationType);
								}
								resourceId = provider.getResourceId();
							}

						} else if (notificationType.intValue() == Constants.NOTIFICATION_SEVEN
								.intValue()) {
							logger.info("inside of Notification Seven");
							Integer resourceId = Constants.INTEGER_ZERO;

							for (ProviderBackgroundCheckVO provider : pVOList) {

								if (null != provider.getResourceId()
										&& !provider.getResourceId().equals(
												resourceId)) {
									// add audit notes.
									insertAuditNotes(provider, notificationType);
									// creating notification Data with all
									// resources under a firm
									notificationData.append(slNo.toString());
									setNotificationData(notificationData,
											provider, notificationType);
									slNo++;
								}
								if (null!= provider.getNotificationType()) {
									sevenDaysListUpdate.add(provider);
									// updating audit entry of 30 to 7
									updateAuditEntry(sevenDaysListUpdate,
											notificationType);
								} else {
									// inserting audit entry
									insertAuditEntryForSevenDays(provider,
											notificationType);
								}
								resourceId = provider.getResourceId();
							}

						} else if (notificationType.intValue() == Constants.NOTIFICATION_THIRTY
								.intValue()) {
							logger.info("inside of Notification Thirty");

							Integer resourceId = Constants.INTEGER_ZERO;

							for (ProviderBackgroundCheckVO provider : pVOList) {
								if (null != provider.getResourceId()
										&& !provider.getResourceId().equals(
												resourceId)) {
									// add audit notes.
									insertAuditNotes(provider, notificationType);
									// creating notification Data with all
									// resources under a firm
									notificationData.append(slNo.toString());
									setNotificationData(notificationData,
											provider, notificationType);
									slNo++;
								}
								// clearing the data if the recertification date
								// is changed
								if (null!= provider.getNotificationType()) {
									thirtyDaysListUpdate.add(provider);
									updateAuditEntry(thirtyDaysListUpdate,
											notificationType);
								} else {
									insertAuditEntryForThirtyDays(provider,
											notificationType);
								}
								resourceId = provider.getResourceId();
							}
						}

					}

				}
				// appending past due list
				if (null != notificationData
						&& !notificationData.toString().equals("")) {
					Integer resourceId = 0;
					if (pastDueList != null && !pastDueList.isEmpty()) {
						for (ProviderBackgroundCheckVO provider : pastDueList) {
							if (null != provider.getResourceId()
									&& !provider.getResourceId().equals(
											resourceId)) {
								notificationData.append(slNo.toString());
								setPastDueNotificationData(notificationData,
										provider);
								slNo++;
							}
							resourceId = provider.getResourceId();
						}
					}
			// Sending email
				sendEmailtoFirm(notificationData.toString(), vendorEmail,
						firmName);
				}
				
				// Data Insertion and Updation
				if (null != expiryNotificationListThirty
						&& !expiryNotificationListThirty.isEmpty()) {
					providerBackgroundCheckDao
							.insertExpiryNotificationForThirtyDays(expiryNotificationListThirty);
				}
				if (null != notificationIdsListSeven
						&& !notificationIdsListSeven.isEmpty()) {
					providerBackgroundCheckDao
							.updateSevenDaysExpiryNotification(notificationIdsListSeven);
				}
				if (null != expiryNotificationListSeven
						&& !expiryNotificationListSeven.isEmpty()) {
					providerBackgroundCheckDao
							.insertExpiryNotificationForSevenDays(expiryNotificationListSeven);
				}
				if (null != notificationIdsListZero
						&& !notificationIdsListZero.isEmpty()) {
					providerBackgroundCheckDao
							.updateZeroDaysExpiryNotification(notificationIdsListZero);
				}
				if (null != notificationIdsListThirty
						&& !notificationIdsListThirty.isEmpty()) {
					providerBackgroundCheckDao
							.updateAndClearExpiryNotification(notificationIdsListThirty);
				}
				if (null != expiryNotificationListZero
						&& !expiryNotificationListZero.isEmpty()) {
					providerBackgroundCheckDao
							.insertExpiryNotificationForZeroDays(expiryNotificationListZero);
				}
				if (null != alertTaskList && !alertTaskList.isEmpty()) {
					providerBackgroundCheckDao.addAlertToQueue(alertTaskList);
				}
				if (null != vendorNotesList && !vendorNotesList.isEmpty()) {
					providerBackgroundCheckDao
							.insertVendorNotes(vendorNotesList);
				}

			}
		} catch (DataServiceException e) {
			logger.error("Error in process method of BackgroundCheckRecertificationProcess" + e);

		} catch (Exception e) {
			logger.error("Error in process method of BackgroundCheckRecertificationProcess" + e);

		}

	}

	private void setPastDueNotificationData(StringBuilder notificationData,
			ProviderBackgroundCheckVO provider) {

		
		//R11_1
		//Jira SL-20434
		String parm3 = null;

        if(provider.getSsnLastFour() != null && provider.getSsnLastFour().trim().length() > 0 && provider.getResourceId() != null){

               String plusOne= provider.getResourceId().toString().trim()+provider.getSsnLastFour().trim();

               parm3 = CryptoUtil.encryptKeyForSSNAndPlusOne(plusOne);
               parm3=URLEncoder.encode(parm3);

        }
		
		DateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy");
		notificationData.append(Constants.DOT_STRING).append(
				Constants.SPACE_STRING).append(Constants.BOLDSTART_STRING)
				.append(provider.getSpnName()).append(Constants.BOLDEND_STRING)
				.append(Constants.SPACE_STRING);
		if (provider.getSpnIdCount() > 1) {
			notificationData.append(Constants.MORE_STRING);
		}
		notificationData.append(Constants.NEXTLINE_STRING).append(
				Constants.TABSPACE_STRING).append(
						provider.getResourceFirstName()).append(
						Constants.SPACE_STRING).append(
						provider.getResourceLastName())
				.append(Constants.SPACE_STRING).append(Constants.OPEN_STRING)
				.append(Constants.USERID_STRING).append(Constants.SPACE_STRING)
				.append(provider.getResourceId())
				.append(Constants.CLOSE_STRING).append(
						Constants.NEXTLINE_STRING).append(
						Constants.TABSPACE_STRING).append(
						Constants.RECERTIFICATIONEDATE_STRING).append(
						Constants.SEMICOLON_STRING).append(
						Constants.SPACE_STRING).append(
						dateFormat.format(provider.getReVerficationDate()))
				.append(Constants.SPACE_STRING).append(Constants.OPEN_STRING)
				.append(Constants.REDSTART_STRING).append(
						Constants.PASTDUE_STRING).append(
						Constants.COLOREND_STRING).append(
						Constants.CLOSE_STRING)
						.append(Constants.SPACE_STRING)
						.append(Constants.ANCHORSTART_STRING)
						.append(Constants.PLUSONE_URL)
						.append(provider.getResourceId())
						.append(Constants.PARAM3)
						.append(parm3)
						.append(Constants.PARAM4)
						.append(Constants.RECERTIFICATION_IND)
						.append(Constants.QUOTEEND_STRING)
						.append(Constants.RECT_STRING)
						.append(Constants.ANCHOREND_STRING)
						.append(Constants.NEXTLINE_STRING)
						.append(Constants.NEXTLINE_STRING);
	}

	private void setNotificationData(StringBuilder notificationData,
			ProviderBackgroundCheckVO provider, Integer notificationType) {

		//R11_1
		//Jira SL-20434
		String parm3 = null;

        if(provider.getSsnLastFour() != null && provider.getSsnLastFour().trim().length() > 0 && provider.getResourceId() != null){

               String plusOne= provider.getResourceId().toString().trim()+provider.getSsnLastFour().trim();

               parm3 = CryptoUtil.encryptKeyForSSNAndPlusOne(plusOne);
               parm3=URLEncoder.encode(parm3);

        }
		
		DateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy");
		notificationData.append(Constants.DOT_STRING).append(
				Constants.SPACE_STRING).append(Constants.BOLDSTART_STRING)
				.append(provider.getSpnName()).append(Constants.BOLDEND_STRING)
				.append(Constants.SPACE_STRING);
		if (provider.getSpnIdCount() > 1) {
			notificationData.append(Constants.MORE_STRING);
		}
		notificationData.append(Constants.NEXTLINE_STRING).append(
				Constants.TABSPACE_STRING).append(
						provider.getResourceFirstName()).append(
						Constants.SPACE_STRING).append(
						provider.getResourceLastName())
				.append(Constants.SPACE_STRING).append(Constants.OPEN_STRING)
				.append(Constants.USERID_STRING).append(Constants.SPACE_STRING)
				.append(provider.getResourceId())
				.append(Constants.CLOSE_STRING).append(
						Constants.NEXTLINE_STRING).append(
						Constants.TABSPACE_STRING).append(
						Constants.RECERTIFICATIONEDATE_STRING).append(
						Constants.SEMICOLON_STRING).append(
						Constants.SPACE_STRING).append(
						dateFormat.format(provider.getReVerficationDate()))
				.append(Constants.SPACE_STRING).append(Constants.OPEN_STRING)
				.append(Constants.REDSTART_STRING);
		if (notificationType.intValue() == 0) {
			notificationData.append(Constants.DUE_TODAY);
		} else {
			notificationData.append(Constants.DUE_STRING).append(
					Constants.SPACE_STRING).append(notificationType).append(
					Constants.SPACE_STRING).append(Constants.DAYS_STRING);
		}
		notificationData.append(Constants.COLOREND_STRING).append(
				Constants.CLOSE_STRING).append(Constants.SPACE_STRING)
				.append(Constants.ANCHORSTART_STRING)
				.append(Constants.PLUSONE_URL)
				.append(provider.getResourceId())
				.append(Constants.PARAM3)
				.append(parm3)
				.append(Constants.PARAM4)
				.append(Constants.RECERTIFICATION_IND)
				.append(Constants.QUOTEEND_STRING)
				.append(Constants.RECT_STRING)
				.append(Constants.ANCHOREND_STRING)
				.append(Constants.NEXTLINE_STRING)
				.append(Constants.NEXTLINE_STRING);

	}

	// adding entry in vendor_notes
	public void insertAuditNotes(ProviderBackgroundCheckVO providerCheck,
			Integer notificationType) {
		VendorNotesVO vendorNotes = new VendorNotesVO();
		vendorNotes.setVendorId(providerCheck.getVendorId());
		vendorNotes.setModifiedBy(Constants.SYSTEM);
		String note = Constants.EMPTY_STRING;
		// add note with background check description with resource Id
		if (null != providerCheck.getResourceId()) {
			if (notificationType.equals(Constants.NOTIFICATION_THIRTY)) {
				note = Constants.BACKGROUND_CHECK_DESCRIPTION_THIRTY
						+ providerCheck.getResourceId().toString();
				
			} else if (notificationType.equals(Constants.NOTIFICATION_SEVEN)) {
				note = Constants.BACKGROUND_CHECK_DESCRIPTION_SEVEN
						+ providerCheck.getResourceId().toString();
				
			} else if (notificationType.equals(Constants.NOTIFICATION_ZERO)) {
				note = Constants.BACKGROUND_CHECK_DESCRIPTION_ZERO
						+ providerCheck.getResourceId().toString();
				
			}
			
			vendorNotes.setNote(note);
			vendorNotesList.add(vendorNotes);
		}
		

	}

	// adding entry in alert_task
	public void sendEmailtoFirm(String notificationData, String vendorEmail,
			String firmName) {
		Map<String, String> alertMap = new HashMap<String, String>();
		
			alertMap.put(Constants.NOTIFICATION_DATA, notificationData);
			alertMap.put(Constants.FIRM_NAME, firmName);
			// creating new entry for alert_task
			AlertTask alertTask = new AlertTask();
			Date currentdate = new Date();
			alertTask.setAlertedTimestamp(null);
			alertTask.setAlertTypeId(AlertConstants.ALERT_TYPE_ID);
			alertTask.setCreatedDate(currentdate);
			alertTask.setModifiedDate(currentdate);
			// setting template_id
			alertTask
					.setTemplateId(AlertConstants.TEMPLATE_PROVIDER_BACKGROUNDCHECK_EMAIL);
			alertTask
					.setAlertFrom(AlertConstants.SERVICE_LIVE_COMPLAINCE_MAILID);
			alertTask.setAlertTo(vendorEmail);
			alertTask.setAlertBcc(Constants.EMPTY_STRING);
			alertTask.setAlertCc(Constants.EMPTY_STRING);
			alertTask
					.setCompletionIndicator(AlertConstants.INCOMPLETE_INDICATOR);
			alertTask.setPriority(AlertConstants.PRIORITY);
			// generating template_input_value from Map
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

	// adding entry in audit_cred_expiry_notification for thirtydays
	public void insertAuditEntryForThirtyDays(
			ProviderBackgroundCheckVO providerCheck, Integer notificationType) {

		ExpiryNotificationVO expiryNotification = new ExpiryNotificationVO();
		expiryNotification.setVendorId(providerCheck.getVendorId());
		expiryNotification.setResourceId(providerCheck.getResourceId());
		// R11.0
		expiryNotification.setNotificationType(Constants.NOTIFICATION_THIRTY);

		expiryNotification.setExpirationDate(providerCheck
				.getReVerficationDate());
		// set credential indicator as 4
		expiryNotification
				.setCredentialIndicator(Constants.BACKGROUND_CREDENTIAL_IND);
		expiryNotification.setCredentialName(Constants.SPN_PRO_ID
				+ providerCheck.getResourceId().toString()
				+ Constants.SPN_REQUIREMENT_TYPE);
		expiryNotificationListThirty.add(expiryNotification);

	}

	// adding entry in audit_cred_expiry_notification for sevendays
	public void insertAuditEntryForSevenDays(
			ProviderBackgroundCheckVO providerCheck, Integer notificationType) {

		ExpiryNotificationVO expiryNotification = new ExpiryNotificationVO();
		expiryNotification.setVendorId(providerCheck.getVendorId());
		expiryNotification.setResourceId(providerCheck.getResourceId());
		// R11.0
		expiryNotification.setNotificationType(Constants.NOTIFICATION_SEVEN);

		expiryNotification.setExpirationDate(providerCheck
				.getReVerficationDate());
		// set credential indicator as 4
		expiryNotification
				.setCredentialIndicator(Constants.BACKGROUND_CREDENTIAL_IND);
		expiryNotification.setCredentialName(Constants.SPN_PRO_ID
				+ providerCheck.getResourceId().toString()
				+ Constants.SPN_REQUIREMENT_TYPE);
		expiryNotificationListSeven.add(expiryNotification);

	}

	// adding entry in audit_cred_expiry_notification for zerodays
	public void insertAuditEntryForZeroDays(
			ProviderBackgroundCheckVO providerCheck, Integer notificationType) {

		ExpiryNotificationVO expiryNotification = new ExpiryNotificationVO();
		expiryNotification.setVendorId(providerCheck.getVendorId());
		expiryNotification.setResourceId(providerCheck.getResourceId());
		// R11.0
		expiryNotification.setNotificationType(Constants.NOTIFICATION_ZERO);

		expiryNotification.setExpirationDate(providerCheck
				.getReVerficationDate());
		// set credential indicator as 4
		expiryNotification
				.setCredentialIndicator(Constants.BACKGROUND_CREDENTIAL_IND);
		expiryNotification.setCredentialName(Constants.SPN_PRO_ID
				+ providerCheck.getResourceId().toString()
				+ Constants.SPN_REQUIREMENT_TYPE);
		expiryNotificationListZero.add(expiryNotification);

	}

	/**
	 * This method checks if the reverficationDate is with in 30 days
	 * 
	 * @param reverficationDate
	 * @return
	 */
	public boolean checkFor30Days(Date reverficationDate) {
		Date currentDate = new Date();
		long days = 0L;
		if (null != reverficationDate) {
		days = DateUtils.getExactDaysBetweenDates(currentDate,
				reverficationDate) + 1L;
			if (days == 30) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * This method checks if the reverficationDate is with in 7 days
	 * 
	 * @param reverficationDate
	 * @return
	 */
	public boolean checkFor7Days(Date reverficationDate) {
		Date currentDate = new Date();
		long days = 0L;
		if (null != reverficationDate) {
			days = DateUtils.getExactDaysBetweenDates(currentDate,
					reverficationDate) + 1L;
			if (days == 7) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	/**
	 * This method checks if the reverficationDate is today.
	 * 
	 * @param reverficationDate
	 * @return boolean
	 */
	public boolean checkForToday(Date reverficationDate) {
		Date currentDate = getCurrentDateWithoutTimeStamp();
		if(null!= reverficationDate){
			Date expirationDate = setTimeStampToZero(reverficationDate);
		if (currentDate.compareTo(expirationDate) == 0) {
			return true;
		} else {
			return false;
		}
		}
		return false;
	}

	/**
	 * This method checks if the reverficationDate is past.
	 * 
	 * @param reverficationDate
	 * @return boolean
	 */
	public boolean checkForPastDue(Date reverficationDate) {
		Date currentDate = getCurrentDateWithoutTimeStamp();
		if(null!= reverficationDate){
			Date expirationDate = setTimeStampToZero(reverficationDate);
		if (expirationDate.compareTo(currentDate) < 0) {
			return true;
		} else {
			return false;
		}
	}
		return false;
	}

	/**
	 * This is a utility method which returns current Date.
	 * <p>
	 * The date format contains time stamp 00:00:00
	 * 
	 * @return Current Date in the format mm dd yyyy 00:00:00
	 */
	private Date getCurrentDateWithoutTimeStamp() {
		Date today = new Date();
		today = setTimeStampToZero(today);
		return today;
	}

	/**
	 * This is a utility method which returns Date in a format with time stamp
	 * 00:00:00
	 * 
	 * @return Date in the format mm dd yyyy 00:00:00
	 */
	private Date setTimeStampToZero(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date dateZeroTimeStamp = date;
		try {
			dateZeroTimeStamp = dateFormat.parse(dateFormat.format(date));
		} catch (ParseException e) {
			logger.error(e.getMessage());
		}
		return dateZeroTimeStamp;
	}

	/**
	 * This method checks if the 7/30 days notification is already sent.
	 * 
	 * @param credentialId
	 * @param notificationType
	 * @return
	 * @throws BusinessServiceException
	 */

	private boolean checkIfNotificationAlreadySent(Integer notificationInd,
			Integer notificationType) throws BusinessServiceException {
		if (null != notificationInd
				&& (notificationType.intValue() == notificationInd.intValue())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * This method checks if the 7/30 days notification is already sent when it is due today.
	 * 
	 * @param credentialId
	 * @param notificationType
	 * @return
	 * @throws BusinessServiceException
	 */

	private boolean zeroCheckIfNotificationAlreadySent(Integer notificationType) throws BusinessServiceException {
		if (null != notificationType &&(notificationType.intValue() == Constants.NOTIFICATION_SEVEN || notificationType.intValue() == Constants.NOTIFICATION_THIRTY)) {
			return true;
		} else {
			return false;
		}
	}

	// update entry in audit_cred_expiry_notification
	public void updateAuditEntry(List<ProviderBackgroundCheckVO> updateList,
			Integer notificationType) {
		if (null != updateList && !updateList.isEmpty()) {
			for (ProviderBackgroundCheckVO check : updateList) {
				if (null != check) {
					if (notificationType.intValue() == Constants.NOTIFICATION_SEVEN) {
						notificationIdsListSeven.add(check.getNotificationId());
					} else if (notificationType.intValue() == Constants.NOTIFICATION_ZERO) {
						notificationIdsListZero.add(check.getNotificationId());
					} else if (notificationType.intValue() == Constants.NOTIFICATION_THIRTY) {
						notificationIdsListThirty
								.add(check.getNotificationId());
					}
				}

			}

		}

	}

	public ProviderBackgroundCheckDao getProviderBackgroundCheckDao() {
		return providerBackgroundCheckDao;
	}

	public void setProviderBackgroundCheckDao(
			ProviderBackgroundCheckDao providerBackgroundCheckDao) {
		this.providerBackgroundCheckDao = providerBackgroundCheckDao;
	}

}
