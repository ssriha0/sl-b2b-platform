package com.newco.batch.documentExpiration;

import java.util.Date;
import java.util.List;


import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.DetailsVO;
import com.newco.marketplace.dto.vo.ExpiryDetailsVO;
import com.newco.marketplace.dto.vo.audit.AuditHistoryVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.iDao.alert.AlertDao;
import com.newco.marketplace.persistence.iDao.documentExpiry.DocumentExpiryDao;
import com.newco.marketplace.exception.core.BusinessServiceException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import com.newco.marketplace.utils.DateUtils;
import com.servicelive.common.properties.IApplicationProperties;

/**
 * This class processes firm/provider credentials and sends 7/30 days expiry notification mails to the firm admin.
 */
public  class DocumentExpirationService{
	private static final Logger logger = Logger.getLogger(DocumentExpirationService.class);
	private static final Integer FIRM_CRED_OUT_OF_COMPLIANT = 25;
	private static final Integer PROVIDER_CRED_OUT_OF_COMPLIANT = 24;
	private static final String COMPANY_CREDENTIAL = "Company Credential";
	private static final String PROVIDER_CREDENTIAL = "Team Member Credential";
	private static final Integer REASON_CODE_PROVIDER = 58;
	private static final Integer REASON_CODE_FIRM = 28; 
	private static final String AUDITOR ="System";
	private static final String STATUS_OUT_OF_COMPLIANT = "Out of Compliance";
	private static final String REASON_CODE ="REASON_CODE";
	private static final String NOTIFICATION_TYPE_SEVEN = "7";
	private static final String NOTIFICATION_TYPE_THIRTY = "30";
	private static final Integer PROVIDER_AUDIT_LINK_ID =4;
	private static final Integer FIRM_AUDIT_LINK_ID =3;
	private static final Integer TYPE_INSURANCE_ID=6;
	private static final String RESOURCE_CRED_TABLE ="resource_credential";
	private static final String RESOURCE_CRED_COLUMN_NAME ="resource_cred_id";
	private static final String VENDOR_CRED_TABLE ="vendor_credential";
	private static final String VENDOR_CRED_COLUMN_NAME ="vendor_cred_id";
	private static final Integer ENTITY_COMPANY_CREDENTIAL = 1;
	private static final Integer ENTITY_COMPANY_CREDENTIALS_INSURANCE =2 ;
	private static final Integer ENTITY_PROVIDER_CREDENTIAL = 3;
	private static final String ENTITY_COMPANY_CREDENTIALS ="Company Credentials" ;
	private static final String ENTITY_PROVIDER_CREDENTIALS ="Provider Credentials" ;
	public static final String FIRM_NAME = "FIRM_NAME";
	public static final String CREDENTIAL_NUMBER = "CREDENTIAL_NUMBER";
	public static final String CREDENTIAL_TYPE ="CREDENTIAL_TYPE";
	public static final String STATUS ="STATUS";
	public static final String PROVIDER_NAME ="PROVIDER_NAME";
	public static final String CREDENTIAL_CATEGORY= "CREDENTIAL_CATEGORY";
	public static final String EXPIRATION_DATE = "EXPIRATION_DATE";
	public static final String NOTIFICATION_TYPE = "NOTIFICATION_TYPE";
	public static final String NOT_APPLICABLE = "N/A";
	public static final Integer TEMPLATE_INSURANCE_EXPIRY =297;
	public static final Integer TEMPLATE_FIRM_LICENSE_EXPIRY =298;
	public static final Integer TEMPLATE_PROVIDER_CRED_EXPIRY =299;
	public static final Integer TEMPLATE_COMPANY_CRED_OUT_OF_COMPLIANCE = 300;
	public static final Integer TEMPLATE_PROVIDER_CRED_OUT_OF_COMPLIANCE =301;
	public static final Integer ALERT_TYPE_ID=1;
	public static final String PRIORITY="1";
	public static final String INCOMPLETE_INDICATOR = "1";
	public static final String SERVICE_LIVE_MAILID = "SLCompliance@servicelive.com";
	public static final String PROVIDER_FIRST_NAME = "PROVIDER_FIRST_NAME";
	public static final String PROVIDER_LAST_NAME = "PROVIDER_LAST_NAME";
	public static final String AUDIT_NOTE_TEXT1 =" days (";
	public static final String AUDIT_NOTE_TEXT2  =") expiration notice sent to Provider Firm";
	public static final String AUDIT_NOTE_TEXT3 = ") expiration notice sent to PRO ID # ";
	public static final String SUBJECT ="SUBJECT";
	public static final String SUBJECT_FIRM_INSURANCE_SEVEN_DAYS = "Final Expiration Notice - Insurance will expire within 7 days";
	public static final String SUBJECT_FIRM_INSURANCE_THIRTY_DAYS = "Expiration Notice - Insurance will expire within 30 days";
	public static final String SUBJECT_FIRM_LICENSE_SEVEN_DAYS = "Final Expiration Notice - License will expire within 7 days";
	public static final String SUBJECT_FIRM_LICENSE_THIRTY_DAYS = "Expiration Notice - License will expire within 30 days";
	public static final String SUBJECT_PROVIDER_CRED_SEVEN_DAYS = "Final Expiration Notice - Service Provider License will expire within 7 days";
	public static final String SUBJECT_PROVIDER_CRED_THIRTY_DAYS = "Expiration Notice - Service Provider License will expire within 30 days";
	public static final String CREDENTIALNUMBER = "CREDENTIALNUMBER";
	public static final String PRIMECONTACT = "PRIMECONTACT";
	public static final String CREDENTIALTYPE ="CREDENTIALTYPE";
	public static final String CREDENTIALNAME ="CREDENTIALNAME";
	public static final String REASONDESCRIPTION ="REASONDESCRIPTION";
	public static final String TARGETSTATENAME ="TARGETSTATENAME";
	public static final String DOCUMENT_EXPIRED ="Document Expired";
	
	private Map<String,List<ExpiryDetailsVO>> notificationMap=null;
	private AlertDao alertDao;
	private DocumentExpiryDao documentExpiryDao;
	private IApplicationProperties applicationProperties;

	private List<AlertTask> alertTaskList = new ArrayList<AlertTask>();
	/**
	 * This method processes firm/provider credentials and sends 7/30 days expiry notification mail to the firm admin
	 * @throws BusinessServiceException
	 */
	public void processDocumentExpiration() throws BusinessServiceException {
		notificationMap=new HashMap<String,List<ExpiryDetailsVO>>();
		long startTime = System.currentTimeMillis();
		processFirmCredentials();
		logger.info("Time taken to processFirmCredentials()"+(System.currentTimeMillis()-startTime));
		processProviderCredentials();
		logger.info("Time taken to processProviderCredentials()"+(System.currentTimeMillis()-startTime));
			thirtyDaysNotification();
			sevenDaysNotification();
			outOfComplainceNotification();
		
	}
	
	/**
	 * This method processes firm credentials and sends 7/30 days expiry notification mail to the firm admin
	 * @throws BusinessServiceException
	 */
			
	private void processFirmCredentials() throws BusinessServiceException {
		List<ExpiryDetailsVO> firmExpiryDetails = new ArrayList<ExpiryDetailsVO>();
		List<ExpiryDetailsVO> outOfComplainceFirmNonInsuranceList=new ArrayList<ExpiryDetailsVO>();
		List<ExpiryDetailsVO> outOfComplainceFirmInsuranceList=new ArrayList<ExpiryDetailsVO>();
		List<ExpiryDetailsVO> sevenDaysFirmNonInsuranceList=new ArrayList<ExpiryDetailsVO>();
		List<ExpiryDetailsVO> sevenDaysFirmInsuranceList=new ArrayList<ExpiryDetailsVO>();
		List<ExpiryDetailsVO> thirtDaysFirmNonInsuranceList=new ArrayList<ExpiryDetailsVO>();
		List<ExpiryDetailsVO> thirtDaysFirmInsuranceList=new ArrayList<ExpiryDetailsVO>();
		List<ExpiryDetailsVO> sevenDaysFirmNonInsuranceListForUpdate=new ArrayList<ExpiryDetailsVO>();
		List<ExpiryDetailsVO> sevenDaysFirmInsuranceListForUpdate=new ArrayList<ExpiryDetailsVO>();
		firmExpiryDetails = fetchFirmsCredentialDetails();
		if (null != firmExpiryDetails) {
			for (ExpiryDetailsVO firmDetail : firmExpiryDetails) {
				if (null != firmDetail) {
						//check if document is expired
						if(checkIfExpired(firmDetail.getCredentialExpirationDate())){
							if(!(checkIfFirmDocOutOfcompliant(firmDetail.getCredentialStatus()))){
								//For those credential which expires on Current date, this is to be displayed in front end.
								//Set necessary details.
								setDataForFirmCredentialNotification(firmDetail,NOTIFICATION_TYPE_SEVEN);
								if(!(isTypeInsurance(firmDetail.getCredentialTypeId()))){	
									outOfComplainceFirmNonInsuranceList.add(firmDetail);
								}
								else{
									outOfComplainceFirmInsuranceList.add(firmDetail); 
								}
								createNotificationDataForOutOfCompliantCreds(firmDetail, ENTITY_COMPANY_CREDENTIAL);
								updateAuditHistory(COMPANY_CREDENTIAL,FIRM_CRED_OUT_OF_COMPLIANT,REASON_CODE_FIRM,firmDetail.getFirmId(),firmDetail.getProviderId(),firmDetail.getDocumentId(),FIRM_AUDIT_LINK_ID,firmDetail.getCredentialId(),VENDOR_CRED_TABLE,VENDOR_CRED_COLUMN_NAME,firmDetail.getFirmName(),firmDetail.getAuditTaskId());
							}
						}
						//check if document expiry within 7 days
						else if (checkIfDocumentExpiryWithin7Days(firmDetail.getCredentialExpirationDate())){
							if(!(checkIfnotificationAlreadySent(firmDetail.getAuditInd(),NOTIFICATION_TYPE_SEVEN))){
								setDataForFirmCredentialNotification(firmDetail,NOTIFICATION_TYPE_SEVEN);
								if(checkIfnotificationAlreadySent(firmDetail.getAuditInd(),NOTIFICATION_TYPE_THIRTY)){
									if(!(isTypeInsurance(firmDetail.getCredentialTypeId()))){
										createNotificationDataForFirmCredentials(firmDetail);
										sevenDaysFirmNonInsuranceListForUpdate.add(firmDetail);
									}else{
										createNotificationDataForFirmInsurance(firmDetail);
										sevenDaysFirmInsuranceListForUpdate.add(firmDetail);
									}
								}else{
									if(!(isTypeInsurance(firmDetail.getCredentialTypeId()))){
										createNotificationDataForFirmCredentials(firmDetail);
										sevenDaysFirmNonInsuranceList.add(firmDetail);
									}else{
										createNotificationDataForFirmInsurance(firmDetail);
										sevenDaysFirmInsuranceList.add(firmDetail);
									}
								}
							}
						}
						//check if document expiry within 30 days
						else if(checkIfDocumentExpiryWithin30Days(firmDetail.getCredentialExpirationDate())){
							if(!(checkIfnotificationAlreadySent(firmDetail.getAuditInd(),NOTIFICATION_TYPE_THIRTY))){
								setDataForFirmCredentialNotification(firmDetail,NOTIFICATION_TYPE_THIRTY);
								if(!(isTypeInsurance(firmDetail.getCredentialTypeId()))){
									createNotificationDataForFirmCredentials(firmDetail);
									thirtDaysFirmNonInsuranceList.add(firmDetail);
								}else{
									createNotificationDataForFirmInsurance(firmDetail);
									thirtDaysFirmInsuranceList.add(firmDetail);
								}
							}
						}
				}
			}
			logger.info("outOfComplainceFirmInsuranceList size "+outOfComplainceFirmInsuranceList.size());
			logger.info("outOfComplainceFirmNonInsuranceList size "+outOfComplainceFirmNonInsuranceList.size());
			logger.info("sevenDaysFirmInsuranceList size "+sevenDaysFirmInsuranceList.size());
			logger.info("sevenDaysFirmNonInsuranceList size "+sevenDaysFirmNonInsuranceList.size());
			logger.info("thirtDaysFirmInsuranceList size "+thirtDaysFirmInsuranceList.size());
			logger.info("thirtDaysFirmNonInsuranceList size "+thirtDaysFirmNonInsuranceList.size());
			logger.info("sevenDaysFirmNonInsuranceListForUpdate size "+sevenDaysFirmNonInsuranceListForUpdate.size());
			logger.info("sevenDaysFirmInsuranceListForUpdate size "+sevenDaysFirmInsuranceListForUpdate.size());

			notificationMap.put(MPConstants.OUT_OF_COMPLAINCE_FIRM_INSURANCE,
					outOfComplainceFirmInsuranceList);
			notificationMap.put(MPConstants.OUT_OF_COMPLAINCE_FIRM_NONINSURANCE,
					outOfComplainceFirmNonInsuranceList);
			notificationMap.put(MPConstants.SEVEN_DAYS_FIRM_INSURANCE,
					sevenDaysFirmInsuranceList);
			notificationMap.put(MPConstants.SEVEN_DAYS_FIRM_NONINSURANCE,
					sevenDaysFirmNonInsuranceList);
			notificationMap.put(MPConstants.THIRTY_DAYS_FIRM_INSURANCE,
					thirtDaysFirmInsuranceList);
			notificationMap.put(MPConstants.THIRTY_DAYS_FIRM_NONINSURANCE,
					thirtDaysFirmNonInsuranceList);
			notificationMap.put(MPConstants.SEVEN_DAYS_FIRM_NONINSURANCE_FOR_UPDATE, sevenDaysFirmNonInsuranceListForUpdate);
			notificationMap.put(MPConstants.SEVEN_DAYS_FIRM_INSURANCE_FOR_UPDATE, sevenDaysFirmInsuranceListForUpdate);
			//update tables for expired credentials
			makeFirmCredentialOutOfCompliant();
			sendNotification();
			addAuditNotesForFirms();
			addAuditNoticeForFirms();
			//update notification table with 7 days notification data for those rows whose current notification type is 30.
			updateNotificationType(ENTITY_COMPANY_CREDENTIAL);
		}
	}
	
	/**
	 * This method processes provider credentials and sends 7/30 days expiry notification mail to the firm admin
	 * @throws BusinessServiceException
	 */
	
	private void processProviderCredentials() throws BusinessServiceException{
		alertTaskList = new ArrayList<AlertTask>();
		List<ExpiryDetailsVO> providerCredDetailsList = new ArrayList<ExpiryDetailsVO>();
		String auditNote ="" ;
		providerCredDetailsList = getProviderCredentialDetails();
		List<ExpiryDetailsVO> outOfComplainceProviderList=new ArrayList<ExpiryDetailsVO>();
		List<ExpiryDetailsVO> sevenDaysProviderList=new ArrayList<ExpiryDetailsVO>();
		List<ExpiryDetailsVO> thirtDaysProviderList=new ArrayList<ExpiryDetailsVO>();
		List<ExpiryDetailsVO> sevenDaysProviderListForUpdate=new ArrayList<ExpiryDetailsVO>();
		if(null!=providerCredDetailsList){
		for(ExpiryDetailsVO providerCredDetails:providerCredDetailsList){
			if(null != providerCredDetails){
				//check if document is expired
				if(checkIfExpired(providerCredDetails.getCredentialExpirationDate())){
					if(!(checkIfProviderDocOutOfcompliant(providerCredDetails.getCredentialStatus()))){
						setProviderCredDetails(providerCredDetails, NOTIFICATION_TYPE_SEVEN);
						createNotificationDataForOutOfCompliantCreds(providerCredDetails, ENTITY_PROVIDER_CREDENTIAL);
						updateAuditHistory(PROVIDER_CREDENTIAL,PROVIDER_CRED_OUT_OF_COMPLIANT,REASON_CODE_PROVIDER,providerCredDetails.getFirmId(),providerCredDetails.getProviderId(),providerCredDetails.getDocumentId(),PROVIDER_AUDIT_LINK_ID,providerCredDetails.getCredentialId(),RESOURCE_CRED_TABLE,RESOURCE_CRED_COLUMN_NAME,providerCredDetails.getFirmName(),providerCredDetails.getAuditTaskId());
						outOfComplainceProviderList.add(providerCredDetails);
					}
				}else{
						//check if document expiry within 7 days
						if (checkIfDocumentExpiryWithin7Days(providerCredDetails.getCredentialExpirationDate())){
							if(!(checkIfnotificationAlreadySent(providerCredDetails.getAuditInd(),NOTIFICATION_TYPE_SEVEN))){
								setProviderCredDetails(providerCredDetails, NOTIFICATION_TYPE_SEVEN);
								createNotificationDataForProviderCredential(providerCredDetails);
								if(null != providerCredDetails.getProviderId()){
									auditNote = providerCredDetails.getNotificationType() + AUDIT_NOTE_TEXT1+ ENTITY_PROVIDER_CREDENTIALS +" - " +providerCredDetails.getCredentialType()+ " - "+providerCredDetails.getCredentialCategory()+AUDIT_NOTE_TEXT3+ providerCredDetails.getProviderId().toString();
									providerCredDetails.setAuditNotes(auditNote);
								}
								if(checkIfnotificationAlreadySent(providerCredDetails.getAuditInd(),NOTIFICATION_TYPE_THIRTY)){
									sevenDaysProviderListForUpdate.add(providerCredDetails);
								}else{
									sevenDaysProviderList.add(providerCredDetails);
								}
							}
						}
						//check if document expire within 30 days
						else if(checkIfDocumentExpiryWithin30Days(providerCredDetails.getCredentialExpirationDate())){
							if(!(checkIfnotificationAlreadySent(providerCredDetails.getAuditInd(),NOTIFICATION_TYPE_THIRTY))){
								setProviderCredDetails(providerCredDetails, NOTIFICATION_TYPE_THIRTY);
								createNotificationDataForProviderCredential(providerCredDetails);
								if(null != providerCredDetails.getProviderId()){
									auditNote = providerCredDetails.getNotificationType() + AUDIT_NOTE_TEXT1+ ENTITY_PROVIDER_CREDENTIALS +" - " +providerCredDetails.getCredentialType()+ " - "+providerCredDetails.getCredentialCategory()+AUDIT_NOTE_TEXT3+ providerCredDetails.getProviderId().toString();
									providerCredDetails.setAuditNotes(auditNote);
								}
								thirtDaysProviderList.add(providerCredDetails);
							}
						}
				}
			}
		}
	}
		logger.info("outOfComplainceProviderList size "+outOfComplainceProviderList.size());
		logger.info("sevenDaysProviderList size "+sevenDaysProviderList.size());
		logger.info("thirtDaysProviderList size "+thirtDaysProviderList.size());
		logger.info("sevenDaysProviderListForUpdate size "+sevenDaysProviderListForUpdate.size());

		notificationMap.put(MPConstants.OUT_OF_COMPLAINCE_PROVIDER, outOfComplainceProviderList);
		notificationMap.put(MPConstants.SEVEN_DAYS_PROVIDER_LIST, sevenDaysProviderList);
		notificationMap.put(MPConstants.THIRTY_DAYS_PROVIDER_LIST, thirtDaysProviderList);
		notificationMap.put(MPConstants.SEVEN_DAYS_PROVIDER_LIST_FOR_UPDATE, sevenDaysProviderListForUpdate);
		makeProviderCredOutOfcompliant();
		sendNotification();
		addAuditNotesForProviders();
		addAuditNoticeForProviders();
		updateNotificationType(ENTITY_PROVIDER_CREDENTIAL);
	}
	
	/**
	 * This method sets Provider credential details like notification type, credential type etc..
	 * @param providerCredDetails {@link ExpiryDetailsVO}
	 * @param notificationTypeSeven notification type like 7 days, 30 days..
	 * */
	private void setProviderCredDetails(ExpiryDetailsVO providerCredDetails,
			String notificationTypeSeven) {
		providerCredDetails.setEntityType(ENTITY_PROVIDER_CREDENTIAL);
		providerCredDetails.setCredentialName(setCredentialName(ENTITY_PROVIDER_CREDENTIAL,providerCredDetails.getCredentialType(),providerCredDetails.getCredentialCategory(),providerCredDetails.getProviderId()));
		providerCredDetails.setAuditor(AUDITOR);
		providerCredDetails.setNotificationType(notificationTypeSeven);
	}

	private void updateNotificationType(Integer entityType)throws BusinessServiceException{
		List<ExpiryDetailsVO> credDetailsList = new ArrayList<ExpiryDetailsVO>();
		if(entityType.equals(ENTITY_COMPANY_CREDENTIAL)){
			credDetailsList =new ArrayList<ExpiryDetailsVO>(notificationMap.get(MPConstants.SEVEN_DAYS_FIRM_NONINSURANCE_FOR_UPDATE));
			if(null != credDetailsList){
				credDetailsList.addAll(notificationMap.get(MPConstants.SEVEN_DAYS_FIRM_INSURANCE_FOR_UPDATE));
			}
		}else if (entityType.equals(ENTITY_PROVIDER_CREDENTIAL)){
			credDetailsList = notificationMap.get(MPConstants.SEVEN_DAYS_PROVIDER_LIST_FOR_UPDATE);
		}
		List<Integer> notificationIds = getNotificationIds(credDetailsList);
		try{
			if(null != notificationIds && notificationIds.size() > 0 ){
			documentExpiryDao.updateNotificationType(notificationIds);
			}
		}catch(DataServiceException exception){
			logger.error("eror in updateNotificationType() of DocumentExpirationService.java due to " + exception.getMessage());
			throw new BusinessServiceException(exception.getMessage(),exception);
		}
	}
	
	private void setDataForFirmCredentialNotification(ExpiryDetailsVO firmDetail,String notificationType){
		String auditNote ="" ;
		firmDetail.setNotificationType(notificationType);
		firmDetail.setAuditor(AUDITOR);
		if(!(isTypeInsurance(firmDetail.getCredentialTypeId()))){
			firmDetail.setEntityType(ENTITY_COMPANY_CREDENTIAL);
			auditNote = firmDetail.getNotificationType() +AUDIT_NOTE_TEXT1+ ENTITY_COMPANY_CREDENTIALS +" - "+firmDetail.getCredentialType()+ " - " +firmDetail.getCredentialCategory()+AUDIT_NOTE_TEXT2 ;
			firmDetail.setAuditNotes(auditNote);
			firmDetail.setCredentialName(setCredentialName(ENTITY_COMPANY_CREDENTIAL,firmDetail.getCredentialType(),firmDetail.getCredentialCategory(),firmDetail.getProviderId()));
		}
		else{
			firmDetail.setEntityType(ENTITY_COMPANY_CREDENTIALS_INSURANCE);
			auditNote = firmDetail.getNotificationType() +AUDIT_NOTE_TEXT1 + firmDetail.getCredentialType()+ " - "+firmDetail.getCredentialCategory() +AUDIT_NOTE_TEXT2;
			firmDetail.setAuditNotes(auditNote);
			firmDetail.setCredentialName(setCredentialName(ENTITY_COMPANY_CREDENTIALS_INSURANCE,firmDetail.getCredentialType(),firmDetail.getCredentialCategory(),firmDetail.getProviderId()));
		}
	}
	
	/**
	 * This method checks if the document type is insurance.
	 * @param credentialTypeId
	 * @return boolean
	 */
	public boolean isTypeInsurance(Integer credentialTypeId){
		if(null != credentialTypeId && credentialTypeId.equals(TYPE_INSURANCE_ID)){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * This method fetches details of provider credentials.
	 * @return List<ExpiryDetailsVO>
	 * @throws BusinessServiceException
	 */
	private List<ExpiryDetailsVO> getProviderCredentialDetails()throws BusinessServiceException{
		List<ExpiryDetailsVO> providerCredDetailsList = new ArrayList<ExpiryDetailsVO>();
		try{
			providerCredDetailsList = documentExpiryDao.getProviderCredentialDetails();
		}catch(DataServiceException exception){
			logger.error("eror in getProviderCredentialDetails() of DocumentExpirationService.java due to " + exception.getMessage());
			throw new BusinessServiceException(exception.getMessage(),exception);
		}
		return providerCredDetailsList;
	}
	
	/**
	 * This method makes expired provider credentials in 'out of compliant' status
	 * @throws BusinessServiceException
	 */
	private void makeProviderCredOutOfcompliant()throws BusinessServiceException{
		try{
		List<ExpiryDetailsVO> providerCredDetailsList = new ArrayList<ExpiryDetailsVO>();
		providerCredDetailsList = notificationMap.get("outOfComplainceProviderList");
		List<Integer> credentialIds = getcredentialIds(providerCredDetailsList);
		if(null != credentialIds && credentialIds.size() > 0){
		documentExpiryDao.makeProviderCredOutOfcompliant(credentialIds);
		}
		}catch(DataServiceException exception){
			logger.error("eror in makeProviderCredOutOfcompliant() of DocumentExpirationService.java due to " + exception.getMessage());
			throw new BusinessServiceException(exception.getMessage(),exception);
		}
	}
	
	public List<Integer> getcredentialIds(List<ExpiryDetailsVO> credentialList){
    	List<Integer> credentialIds = new ArrayList<Integer>();
		if (null != credentialList) {
			for (ExpiryDetailsVO credential : credentialList) {
				if(null != credential)
					credentialIds.add(credential.getCredentialId());
			}
		}
		return credentialIds;
    }
    
	
	/**
	 * This method checks if the provider credential is already in 'out of compliant' status
	 * @param status
	 * @return boolean
	 */
	
	private boolean checkIfProviderDocOutOfcompliant(int status){
		if(PROVIDER_CRED_OUT_OF_COMPLIANT.equals(status)){
			return true;
		}else
			return false;
	}
	
	/**
	 * This method creates the credential name for display in 'audit notes' screen.
	 * @param credentialInd
	 * @param credentialType
	 * @param credentialCategory
	 * @param providerId
	 * @return credentialName
	 */
	private String setCredentialName(Integer credentialInd,String credentialType,String credentialCategory,Integer providerId) {
		String credentialName ="";
		if(null!=credentialInd){
			if(credentialInd.equals(ENTITY_COMPANY_CREDENTIAL)){
				credentialName = "CompanyCredentials - "+ credentialType +" - "+ credentialCategory;
			}else if(credentialInd.equals(ENTITY_COMPANY_CREDENTIALS_INSURANCE)){
				credentialName = "Insurance" + " - " + credentialCategory;
			}else if(null !=providerId && credentialInd.equals(ENTITY_PROVIDER_CREDENTIAL)){
				credentialName = "PRO ID # " + providerId.toString() +" - "+credentialType +" - "+ credentialCategory;
			}
		}
		return credentialName;
	}
	
	/**
	 * This method fetches the details of firm credentials.
	 * @return List<ExpiryDetailsVO>
	 * @throws BusinessServiceException
	 */

	private List<ExpiryDetailsVO> fetchFirmsCredentialDetails()throws BusinessServiceException{
		try{
		return documentExpiryDao.processFirmsCredentials();
		}catch(DataServiceException exception){
			logger.error("eror in processFirmsCredentials() of DocumentExpirationService.java due to " + exception.getMessage());
			throw new BusinessServiceException(exception.getMessage(),exception);
		}
	}

	public void createNotificationDataForFirmCredentials(ExpiryDetailsVO expiryDetail) {
		Map<String, String> alertMap = new HashMap<String, String>();
		if(null != expiryDetail){
		alertMap.put(FIRM_NAME,expiryDetail.getFirmName());
		alertMap.put(CREDENTIAL_CATEGORY, expiryDetail.getCredentialCategory());
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		alertMap.put(EXPIRATION_DATE, dateFormat.format(expiryDetail.
				getCredentialExpirationDate()));
		if(expiryDetail.getNotificationType().equals(NOTIFICATION_TYPE_SEVEN)){
			alertMap.put(SUBJECT,SUBJECT_FIRM_LICENSE_SEVEN_DAYS);
		}else{
			alertMap.put(SUBJECT,SUBJECT_FIRM_LICENSE_THIRTY_DAYS);
		}
		AlertTask alertTask = new AlertTask();
		Date currentdate = new Date();
		alertTask.setAlertedTimestamp(null);
		alertTask.setAlertTypeId(ALERT_TYPE_ID);
		alertTask.setCreatedDate(currentdate);
		alertTask.setModifiedDate(currentdate);
		alertTask.setTemplateId(TEMPLATE_FIRM_LICENSE_EXPIRY);
		alertTask.setAlertFrom(SERVICE_LIVE_MAILID);
		alertTask.setAlertTo(expiryDetail.getFirmEmail());
		alertTask.setAlertBcc("");
		alertTask.setAlertCc("");
		alertTask.setCompletionIndicator(INCOMPLETE_INDICATOR);
		alertTask.setPriority(PRIORITY);
		alertTask.setTemplateInputValue(createMailMergeValueStringFromMap(alertMap));
		alertTaskList.add(alertTask);
		}
	}
	public void createNotificationDataForFirmInsurance(ExpiryDetailsVO expiryDetail) {
	
		Map<String, String> alertMap = new HashMap<String, String>();
		if(null != expiryDetail){
		alertMap.put(FIRM_NAME,expiryDetail.getFirmName());
		alertMap.put(CREDENTIAL_CATEGORY, expiryDetail.getCredentialCategory());
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		alertMap.put(EXPIRATION_DATE, dateFormat.format(expiryDetail.
				getCredentialExpirationDate()));
		if(expiryDetail.getNotificationType().equals(NOTIFICATION_TYPE_SEVEN)){
			alertMap.put(SUBJECT,SUBJECT_FIRM_INSURANCE_SEVEN_DAYS);
		}else{
			alertMap.put(SUBJECT,SUBJECT_FIRM_INSURANCE_THIRTY_DAYS);
		}
		AlertTask alertTask = new AlertTask();
		Date currentdate = new Date();
		alertTask.setAlertedTimestamp(null);
		alertTask.setAlertTypeId(ALERT_TYPE_ID);
		alertTask.setCreatedDate(currentdate);
		alertTask.setModifiedDate(currentdate);
		alertTask.setTemplateId(TEMPLATE_INSURANCE_EXPIRY);
		alertTask.setAlertFrom(SERVICE_LIVE_MAILID);
		alertTask.setAlertTo(expiryDetail.getFirmEmail());
		alertTask.setAlertBcc("");
		alertTask.setAlertCc("");
		alertTask.setCompletionIndicator(INCOMPLETE_INDICATOR);
		alertTask.setPriority(PRIORITY);
		alertTask.setTemplateInputValue(createMailMergeValueStringFromMap(alertMap));
		alertTaskList.add(alertTask);
		}
	}
	
	
	
	public void createNotificationDataForProviderCredential(ExpiryDetailsVO expiryDetail) {
		Map<String, String> alertMap = new HashMap<String, String>();
		if(null != expiryDetail){
		alertMap.put(FIRM_NAME,expiryDetail.getFirmName());
		alertMap.put(PROVIDER_FIRST_NAME,expiryDetail.getProviderFirstName());
		alertMap.put(PROVIDER_LAST_NAME,expiryDetail.getProviderLastName());
		alertMap.put(CREDENTIAL_CATEGORY, expiryDetail.getCredentialCategory());
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		alertMap.put(EXPIRATION_DATE, dateFormat.format(expiryDetail.
				getCredentialExpirationDate()));
		if(expiryDetail.getNotificationType().equals(NOTIFICATION_TYPE_SEVEN)){
			alertMap.put(SUBJECT,SUBJECT_PROVIDER_CRED_SEVEN_DAYS);
		}else{
			alertMap.put(SUBJECT,SUBJECT_PROVIDER_CRED_THIRTY_DAYS);
		}
		AlertTask alertTask = new AlertTask();
		Date currentdate = new Date();
		alertTask.setAlertedTimestamp(null);
		alertTask.setAlertTypeId(ALERT_TYPE_ID);
		alertTask.setCreatedDate(currentdate);
		alertTask.setModifiedDate(currentdate);
		alertTask.setTemplateId(TEMPLATE_PROVIDER_CRED_EXPIRY);
		alertTask.setAlertFrom(SERVICE_LIVE_MAILID);
		alertTask.setAlertTo(expiryDetail.getFirmEmail());
		alertTask.setAlertBcc("");
		alertTask.setAlertCc("");
		alertTask.setCompletionIndicator(INCOMPLETE_INDICATOR);
		alertTask.setPriority(PRIORITY);
		alertTask.setTemplateInputValue(createMailMergeValueStringFromMap(alertMap));
		alertTaskList.add(alertTask);
		}
	}
	
	public void createNotificationDataForOutOfCompliantCreds(ExpiryDetailsVO expiryDetail,Integer entityType) {
        Map<String, String> alertMap = new HashMap<String, String>();
        if(null != expiryDetail){
        if(StringUtils.isNotBlank(expiryDetail.getCredNumber())){
        alertMap.put(CREDENTIAL_NUMBER,expiryDetail.getCredNumber());
        }else{
        alertMap.put(CREDENTIAL_NUMBER,NOT_APPLICABLE);
        }
        alertMap.put(FIRM_NAME,expiryDetail.getFirmName());
        alertMap.put(CREDENTIAL_TYPE, expiryDetail.getCredentialType());
        if(StringUtils.isNotBlank(expiryDetail.getCredName())){
        	alertMap.put(CREDENTIAL_CATEGORY,expiryDetail.getCredName());
        }else{
        	alertMap.put(CREDENTIAL_CATEGORY,NOT_APPLICABLE);
        }
        alertMap.put(REASON_CODE,DOCUMENT_EXPIRED);
        alertMap.put(STATUS,STATUS_OUT_OF_COMPLIANT);
        AlertTask alertTask = new AlertTask();
        Date currentdate = new Date();
        alertTask.setAlertedTimestamp(null);
        alertTask.setAlertTypeId(ALERT_TYPE_ID);
        alertTask.setCreatedDate(currentdate);
        alertTask.setModifiedDate(currentdate);
        if(entityType.equals(ENTITY_COMPANY_CREDENTIAL)){
        	 alertTask.setTemplateId(TEMPLATE_COMPANY_CRED_OUT_OF_COMPLIANCE);
        }else{
        	 alertMap.put(PROVIDER_NAME, setProviderName(expiryDetail.getProviderFirstName(),expiryDetail.getProviderLastName()));
        	 alertTask.setTemplateId(TEMPLATE_PROVIDER_CRED_OUT_OF_COMPLIANCE);
        }
        alertTask.setAlertFrom(SERVICE_LIVE_MAILID);
        alertTask.setAlertTo(expiryDetail.getFirmEmail());
        alertTask.setAlertBcc("");
        alertTask.setAlertCc("");
        alertTask.setCompletionIndicator(INCOMPLETE_INDICATOR);
        alertTask.setPriority(PRIORITY);
        alertTask.setTemplateInputValue(createMailMergeValueStringFromMap(alertMap));
        alertTaskList.add(alertTask);
        }
}
	
	private String setProviderName(String firstName,String lastName){
		return lastName + ',' + firstName;
	}
	
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
	
	/**
	 * This method updates the audit history with the credential details whenever system makes a credential out of compliant.
	 * @param type
	 * @param status
	 * @param reasoncode
	 * @param firmId
	 * @param providerId
	 * @param documentId
	 * @param auditLinkId
	 * @param auditKeyId
	 * @param tableName
	 * @param keyName
	 * @param firmName
	 * @param auditTaskId
	 * @throws BusinessServiceException
	 */
	
	private void updateAuditHistory(String type,Integer status,Integer reasoncode,Integer firmId,Integer providerId,Integer documentId,
			Integer auditLinkId,Integer auditKeyId,String tableName,String keyName,String firmName,Integer auditTaskId) throws BusinessServiceException{

		AuditHistoryVO auditHistoryVO = new AuditHistoryVO();
		auditHistoryVO.setType(type);
		auditHistoryVO.setStatus(STATUS_OUT_OF_COMPLIANT);
		auditHistoryVO.setStatusId(status);
		auditHistoryVO.setAuditor(AUDITOR);
		auditHistoryVO.setVendorId(firmId);
		auditHistoryVO.setResourceId(providerId);
		auditHistoryVO.setDocumentId(documentId);
		auditHistoryVO.setAuditLinkId(auditLinkId);
		auditHistoryVO.setAuditKeyId(auditKeyId);
		auditHistoryVO.setTableName(tableName);
		auditHistoryVO.setKeyName(keyName);
		auditHistoryVO.setDocumentId(documentId);
		auditHistoryVO.setFirmName(firmName);
		auditHistoryVO.setReasonCodeId(reasoncode);
		auditHistoryVO.setReasonCode(REASON_CODE);
		auditHistoryVO.setAuditTaskId(auditTaskId);
		try{
			documentExpiryDao.updateAuditHistory(auditHistoryVO);
		}catch(DataServiceException exception){
			logger.error("eror in updateAuditHistory() of DocumentExpirationService.java due to " + exception.getMessage());
			throw new BusinessServiceException(exception.getMessage(),exception);
		}
	}

	/**
	 * This method updates the audit_cred_expiry_notification table, whenever system sends 7/30 days notification mail to firm admin
	 * @throws BusinessServiceException
	 */
	private void addAuditNoticeForFirms()throws BusinessServiceException{
		 List <ExpiryDetailsVO> expiryDetails = new ArrayList<ExpiryDetailsVO>(notificationMap.get(MPConstants.SEVEN_DAYS_FIRM_INSURANCE));
		 List <ExpiryDetailsVO> outOfComplainceFirmInsuranceList =  new ArrayList<ExpiryDetailsVO>(notificationMap.get(MPConstants.OUT_OF_COMPLAINCE_FIRM_INSURANCE));
		 List <ExpiryDetailsVO> outOfComplainceFirmNonInsuranceList =  new ArrayList<ExpiryDetailsVO>(notificationMap.get(MPConstants.OUT_OF_COMPLAINCE_FIRM_NONINSURANCE));
		 if(null != expiryDetails){
			expiryDetails.addAll(notificationMap.get("sevenDaysFirmNonInsuranceList"));
			expiryDetails.addAll(notificationMap.get("thirtDaysFirmInsuranceList"));
			expiryDetails.addAll(notificationMap.get("thirtDaysFirmNonInsuranceList"));
			//To display in front end, entries which are in 'reached expiration' status 
			addAuditNoticeForReachedExpiration(outOfComplainceFirmInsuranceList, expiryDetails);
			addAuditNoticeForReachedExpiration(outOfComplainceFirmNonInsuranceList, expiryDetails);
		}
		try{
			documentExpiryDao.addAuditNotice(expiryDetails);
		}catch(DataServiceException exception){
			logger.error("eror in addAuditNoticeForFirms() of DocumentExpirationService.java due to " + exception.getMessage());
			throw new BusinessServiceException(exception.getMessage(),exception);
		}
	}
	/**
	 * This method will add out of compliance  entries with
	 * current date as expiration date to expiryDetails list, which will
	 * be persisted to display in front end.
	 * @param outOfComplainceList : This can be out of compliance list of FirmInsurance List, Firm Non-InsuranceList
	 * and  Provider List.
	 * @param expiryDetails : Notifications to be displayed in front end.
	 * */
	private void addAuditNoticeForReachedExpiration(List<ExpiryDetailsVO> outOfComplainceList, List<ExpiryDetailsVO> expiryDetails ){
		if(null != outOfComplainceList){
			Date toDay = getCurrentDateWithoutTimeStamp();
			for(ExpiryDetailsVO detailsVO : outOfComplainceList){
				//Add the list entry only when expiration date is the current date.
				//ie, the credential which expires 'today'
				Date expirationDate = setTimeStampToZero(detailsVO.getCredentialExpirationDate());
				if(toDay.compareTo(expirationDate)==0){
					expiryDetails.add(detailsVO);
				}
			}
		}else{
			return;
		}
	}
	
	/**
	 * This method updates the audit_cred_expiry_notification table, whenever system sends 7/30 days notification mail to firm admin
	 * @throws BusinessServiceException
	 */
	
	private void addAuditNoticeForProviders()throws BusinessServiceException{
		List <ExpiryDetailsVO> expiryDetails = new ArrayList<ExpiryDetailsVO>();
		expiryDetails.addAll(notificationMap.get(MPConstants.SEVEN_DAYS_PROVIDER_LIST));
		List <ExpiryDetailsVO> outOfComplainceProviderList = new ArrayList<ExpiryDetailsVO>(notificationMap.get(MPConstants.OUT_OF_COMPLAINCE_PROVIDER));
		//To display Thirty Days list in front end
		if(null != expiryDetails){
			expiryDetails.addAll(notificationMap.get(MPConstants.THIRTY_DAYS_PROVIDER_LIST));
			//To display entries which are in 'reached expiration' status in front end
			addAuditNoticeForReachedExpiration(outOfComplainceProviderList, expiryDetails);
		}
		
		try{
			documentExpiryDao.addAuditNotice(expiryDetails);
		}catch(DataServiceException exception){
			logger.error("eror in addAuditNotice() of DocumentExpirationService.java due to " + exception.getMessage());
			throw new BusinessServiceException(exception.getMessage(),exception);
		}
	}

	/**
	 * This method updates the audit notes with the details of firm credentials.
	 * @throws BusinessServiceException
	 */

	private void addAuditNotesForFirms()throws BusinessServiceException{
		List <ExpiryDetailsVO> expiryDetails =new ArrayList<ExpiryDetailsVO>(notificationMap.get("sevenDaysFirmInsuranceList"));
		if(null != expiryDetails){
			expiryDetails.addAll(notificationMap.get("sevenDaysFirmNonInsuranceList"));
			expiryDetails.addAll(notificationMap.get("thirtDaysFirmInsuranceList"));
			expiryDetails.addAll(notificationMap.get("thirtDaysFirmNonInsuranceList"));
			expiryDetails.addAll(notificationMap.get(MPConstants.SEVEN_DAYS_FIRM_NONINSURANCE_FOR_UPDATE));
			expiryDetails.addAll(notificationMap.get(MPConstants.SEVEN_DAYS_FIRM_INSURANCE_FOR_UPDATE));
		}
		
		try {
			documentExpiryDao.addVendorAuditNotes(expiryDetails);
			} catch (DataServiceException exception) {
			logger.error("error in addVendorAuditNotes() of DocumentExpirationService.java due to "
					+ exception.getMessage());
			throw new BusinessServiceException(exception.getMessage(),
					exception);
		}
	}

	/**
	 * This method updates the audit notes with the details of provider credentials.
	 * @throws BusinessServiceException
	 */
	private void addAuditNotesForProviders()throws BusinessServiceException{
		List <ExpiryDetailsVO> expiryDetails = new ArrayList<ExpiryDetailsVO>();
		expiryDetails = new ArrayList<ExpiryDetailsVO>(notificationMap.get("sevenDaysProviderList"));
		if(null != expiryDetails){
		expiryDetails.addAll(notificationMap.get("thirtDaysProviderList"));
		expiryDetails.addAll(notificationMap.get(MPConstants.SEVEN_DAYS_PROVIDER_LIST_FOR_UPDATE));
		}
		try {
			documentExpiryDao.addVendorAuditNotes(expiryDetails);
			} catch (DataServiceException exception) {
			logger.error("error in addVendorAuditNotes() of DocumentExpirationService.java due to "
					+ exception.getMessage());
			throw new BusinessServiceException(exception.getMessage(),
					exception);
			}
	}

	
	private void sendNotification() {
		try {
			alertDao.addAlertListToQueue(alertTaskList);
		} catch (Exception e) {
			logger.info("error in sending email" + e);
		}
		logger.info("emailsinserted");
	}
	/**
	 * This method checks if the 7/30 days notification is already sent.
	 * @param credentialId
	 * @param notificationType
	 * @return
	 * @throws BusinessServiceException
	 */

	private boolean  checkIfnotificationAlreadySent(Integer auditInd,String notificationType)throws BusinessServiceException{
			if(null != auditInd && notificationType.equals(auditInd.toString())){
				return true;
			}else{
				return false;
			}
	}
	
	
	/**
	 * This method checks if the credential is expired.
	 * <p>Credential is expired when expirationDate <= CurrentDate
	 * @param credentialExpirationDate
	 * @return boolean
	 */
	public boolean checkIfExpired(Date credentialExpirationDate) {
		Date currentDate = getCurrentDateWithoutTimeStamp();
		Date expirationDate = setTimeStampToZero(credentialExpirationDate);
		if (null != credentialExpirationDate && currentDate.compareTo(expirationDate)>=0) {
			return true;
		}else {
			return false;
		}
	}
	
	/** This is a utility method which returns current Date.<p>
	 * The date format contains time stamp 00:00:00
	 * @return Current Date in the format mm dd yyyy 00:00:00
	 * */
	private Date getCurrentDateWithoutTimeStamp(){
		Date today = new Date();
		today = setTimeStampToZero(today);
		return today;
	}
	
	/**
	 * This is a utility method which returns Date in a format with time stamp 00:00:00
	 * @return Date in the format mm dd yyyy 00:00:00
	 * */
	private Date setTimeStampToZero(Date date){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date dateZeroTimeStamp = date;
		try{
			dateZeroTimeStamp = dateFormat.parse(dateFormat.format(date));
		}catch(ParseException e){
			logger.error(e.getMessage());
		}
		return dateZeroTimeStamp;
	}
	/**
	 * This method checks if the firm credential is 'out of compliant'
	 * @param credentialStatus
	 * @return
	 */
    public boolean checkIfFirmDocOutOfcompliant(Integer credentialStatus)
    {
    	if (FIRM_CRED_OUT_OF_COMPLIANT.equals(credentialStatus)){
    		return true;}
    	else{
    		return false;}
    }
    
    /**
     * This method makes a frim credential 'out of compliant'
     * @throws BusinessServiceException
     */
	public void makeFirmCredentialOutOfCompliant()
			throws BusinessServiceException {
		try {
			List <ExpiryDetailsVO> credentialList = new ArrayList<ExpiryDetailsVO>(notificationMap.get("outOfComplainceFirmInsuranceList"));
			if (null != credentialList) {
				credentialList.addAll(notificationMap
						.get("outOfComplainceFirmNonInsuranceList"));
			}
			List<Integer> credentialIds = getcredentialIds(credentialList);
			if (null != credentialIds && credentialIds.size() > 0) {
				documentExpiryDao.makeOutOfCompliant(credentialIds);
			}
		} catch (DataServiceException exception) {
			logger.error("eror in makeOutOfCompliant() of DocumentExpirationService.java due to "
					+ exception.getMessage());
			throw new BusinessServiceException(exception.getMessage(),
					exception);
		}
	}
    
    public List<Integer> getNotificationIds(List<ExpiryDetailsVO> credentialList){
    	List<Integer> notificationIds = new ArrayList<Integer>();
		if (null != credentialList) {
			for (ExpiryDetailsVO credential : credentialList) {
				if(null != credential)
					notificationIds.add(credential.getNotificationId());
			}
		}
		return notificationIds;
    }
    
    /**
     * This method checks if the document expiry is with in 7 days
     * @param credentialExpirationDate
     * @return
     */
	public boolean checkIfDocumentExpiryWithin7Days(
			Date credentialExpirationDate) {
		Date currentDate = new Date();
		long days = 0L;
		if (null != credentialExpirationDate) {
		days = DateUtils.getExactDaysBetweenDates(currentDate,credentialExpirationDate) + 1L;
			if (days > 0 && days <= 7) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	/**
	 * This method checks if the document expiry is with in 30 days
	 * @param credentialExpirationDate
	 * @return
	 */
	 public boolean checkIfDocumentExpiryWithin30Days(
				Date credentialExpirationDate) {
			Date currentDate = new Date();
			long days = 0L;
			days = DateUtils.getExactDaysBetweenDates(currentDate,credentialExpirationDate)+ 1L;			
			if (null != credentialExpirationDate) {
				if (days > 0 && days <= 30) {
					return true;
				} else {
					return false;
				}
			}
			return false;
		}
    
    
	 
	    
	    
	    
	public  List<DetailsVO> convertVoForExcel(String listType) {

			List<DetailsVO> detailsList = new ArrayList<DetailsVO>();
			List<ExpiryDetailsVO> expiryDetailsList = new ArrayList<ExpiryDetailsVO>(notificationMap.get(listType));
		
		
		if (listType.equals(MPConstants.SEVEN_DAYS_FIRM_NONINSURANCE)) {
			
			expiryDetailsList.addAll(notificationMap
					.get(MPConstants.SEVEN_DAYS_FIRM_NONINSURANCE_FOR_UPDATE));
		} else if (listType.equals(MPConstants.SEVEN_DAYS_FIRM_INSURANCE)) {
			expiryDetailsList.addAll(notificationMap
					.get(MPConstants.SEVEN_DAYS_FIRM_INSURANCE_FOR_UPDATE));
		} else if (listType.equals(MPConstants.SEVEN_DAYS_PROVIDER_LIST)) {
			expiryDetailsList.addAll(notificationMap
					.get(MPConstants.SEVEN_DAYS_PROVIDER_LIST_FOR_UPDATE));
		}
		
		
		logger.info("listType - "+listType+" and the size is "+expiryDetailsList.size());
		    if(MPConstants.SEVEN_DAYS_FIRM_INSURANCE.equals(listType)
		    		|| MPConstants.THIRTY_DAYS_FIRM_INSURANCE.equals(listType)
		    		|| MPConstants.OUT_OF_COMPLAINCE_FIRM_INSURANCE.equals(listType))
		    	
		    {
		    	if (null != expiryDetailsList && expiryDetailsList.size()>0) {
					for (ExpiryDetailsVO expiryDetailsVO : expiryDetailsList) {

						DetailsVO details = new DetailsVO();
						if (null != expiryDetailsVO.getCredentialCategory()) {
							details.setCredentailType(expiryDetailsVO
									.getCredentialCategory());
						} else {
							details.setCredentailType(MPConstants.EMPTY_STRING);
						}
						if (null != expiryDetailsVO.getFirmId()) {
							details.setEntityId(Integer.toString(expiryDetailsVO
									.getFirmId()));
						} else {
							details.setEntityId(MPConstants.EMPTY_STRING);
						}
						if (null != expiryDetailsVO.getFirmName()) {
							details.setEntityName(expiryDetailsVO.getFirmName());
						} else {
							details.setEntityName(MPConstants.EMPTY_STRING);
						}
						DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						if (null != expiryDetailsVO.getCredentialExpirationDate()) {
							details.setExpirationDate(dateFormat
									.format(expiryDetailsVO
											.getCredentialExpirationDate()));
						} else {
							details.setExpirationDate(MPConstants.EMPTY_STRING);
						}

						details.setNotificationDate(dateFormat.format(new Date()));
						detailsList.add(details);

					}
				}
			}
		    
		    
		    if(MPConstants.SEVEN_DAYS_PROVIDER_LIST.equals(listType)
		    		|| MPConstants.THIRTY_DAYS_PROVIDER_LIST.equals(listType)
		    		|| MPConstants.OUT_OF_COMPLAINCE_PROVIDER.equals(listType))
		    	
		    {
		    	if (null != expiryDetailsList && expiryDetailsList.size()>0) {
					for (ExpiryDetailsVO expiryDetailsVO : expiryDetailsList) {

						DetailsVO details = new DetailsVO();
						if (null != expiryDetailsVO.getCredentialType()
								&& null != expiryDetailsVO.getCredentialCategory()) {
							details.setCredentailType(expiryDetailsVO
									.getCredentialType()
									+ MPConstants.CONNECTION_STRING
									+ expiryDetailsVO.getCredentialCategory());
						} else {
							details.setCredentailType(MPConstants.EMPTY_STRING);
						}
						if (null != expiryDetailsVO.getProviderId()) {
							details.setEntityId(Integer.toString(expiryDetailsVO
									.getProviderId()));
						} else {
							details.setEntityId(MPConstants.EMPTY_STRING);
						}
						if (null != expiryDetailsVO.getProviderFirstName()
								&& null != expiryDetailsVO.getProviderLastName()) {
							details.setEntityName(expiryDetailsVO
									.getProviderFirstName()
									+ MPConstants.SPACE_STRING
									+ expiryDetailsVO.getProviderLastName());
						} else {
							details.setEntityName(MPConstants.EMPTY_STRING);
						}
						DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						if (null != expiryDetailsVO.getCredentialExpirationDate()) {
							details.setExpirationDate(dateFormat
									.format(expiryDetailsVO
											.getCredentialExpirationDate()));
						} else {
							details.setExpirationDate(MPConstants.EMPTY_STRING);
						}

						details.setNotificationDate(dateFormat.format(new Date()));
						detailsList.add(details);

					}
				}
		    	
		    }
		    
		    
		    if(MPConstants.SEVEN_DAYS_FIRM_NONINSURANCE.equals(listType)
		    		|| MPConstants.THIRTY_DAYS_FIRM_NONINSURANCE.equals(listType)
		    		|| MPConstants.OUT_OF_COMPLAINCE_FIRM_NONINSURANCE.equals(listType))
		    	
		    {
		    	if (null != expiryDetailsList && expiryDetailsList.size()>0) {
					for (ExpiryDetailsVO expiryDetailsVO : expiryDetailsList) {

						DetailsVO details = new DetailsVO();
						if (null != expiryDetailsVO.getCredentialType()
								&& null != expiryDetailsVO.getCredentialCategory()) {
							details.setCredentailType(expiryDetailsVO
									.getCredentialType()
									+ MPConstants.CONNECTION_STRING
									+ expiryDetailsVO.getCredentialCategory());
						} else {
							details.setCredentailType(MPConstants.EMPTY_STRING);
						}
						if (null != expiryDetailsVO.getFirmId()) {
							details.setEntityId(Integer.toString(expiryDetailsVO
									.getFirmId()));
						} else {
							details.setEntityId(MPConstants.EMPTY_STRING);
						}
						if (null != expiryDetailsVO.getFirmName()) {
							details.setEntityName(expiryDetailsVO.getFirmName());
						} else {
							details.setEntityName(MPConstants.EMPTY_STRING);
						}
						DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						if (null != expiryDetailsVO.getCredentialExpirationDate()) {
							details.setExpirationDate(dateFormat
									.format(expiryDetailsVO
											.getCredentialExpirationDate()));
						} else {
							details.setExpirationDate(MPConstants.EMPTY_STRING);
						}

						details.setNotificationDate(dateFormat.format(new Date()));
						detailsList.add(details);

					}
				}
			}
			logger.info("listType - "+listType+" and the size of detailsList is "+detailsList.size());

			return detailsList;

		}
		public  void thirtyDaysNotification() throws BusinessServiceException {
			FileOutputStream fos=null;
			String fileDate ="";
				try
				{
			 boolean sevenDaysInsurance=false;
	            boolean sevenDaysFirm=false;
	           
				ByteArrayOutputStream outFinal = new ByteArrayOutputStream();
				  //create an excel
				WritableWorkbook workbook = Workbook.createWorkbook(outFinal);
				WritableSheet sheet = workbook.createSheet(MPConstants.FIRST_SHEET,
						MPConstants.EXCEL_POSITION_ONE);
				
				//set column size for individual column
				sheet.setColumnView(MPConstants.EXCEL_POSITION_ZERO, MPConstants.EXCEL_POSITION_TEN);
				sheet.setColumnView(MPConstants.EXCEL_POSITION_ONE, MPConstants.EXCEL_COLUMN_SIZE);
				sheet.setColumnView(MPConstants.EXCEL_POSITION_TWO, MPConstants.EXCEL_COLUMN_SIZE_THIRTY);
				sheet.setColumnView(MPConstants.EXCEL_POSITION_THREE, MPConstants.EXCEL_COLUMN_SIZE_THIRTY);
				sheet.setColumnView(MPConstants.EXCEL_POSITION_FOUR, MPConstants.EXCEL_COLUMN_SIZE);
				sheet.setColumnView(MPConstants.EXCEL_POSITION_FIVE, MPConstants.EXCEL_COLUMN_SIZE);
		        
				//set cell format for gray shade
				WritableFont arial10font = new WritableFont(WritableFont.ARIAL,
						MPConstants.EXCEL_POSITION_TEN, WritableFont.BOLD);
				WritableCellFormat arial10format = new WritableCellFormat(arial10font);
				arial10format.setAlignment(Alignment.LEFT);
				arial10format.setBackground(Colour.GRAY_25);
				arial10format.setBorder(Border.ALL, BorderLineStyle.THIN);
				arial10format.setIndentation(1);
				arial10format.setWrap(true);
				
				//set cell format for green shade
				WritableFont arial11font = new WritableFont(WritableFont.ARIAL,
						MPConstants.EXCEL_POSITION_TEN, WritableFont.BOLD);
				WritableCellFormat arial11format = new WritableCellFormat(arial10font);
				arial11format.setAlignment(Alignment.LEFT);
				arial11format.setBackground(Colour.LIGHT_GREEN);
				arial11format.setBorder(Border.ALL, BorderLineStyle.THIN);
				arial11format.setIndentation(1);
				arial11format.setWrap(true);
				
				WritableCellFormat cellformat = new WritableCellFormat();
				cellformat.setAlignment(Alignment.LEFT);
				cellformat.setIndentation(1);
				cellformat.setWrap(true);
				cellformat.setBorder(Border.ALL, BorderLineStyle.THIN);

				// Create cell font and format
			    WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
			    cellFont.setBoldStyle(WritableFont.BOLD);
			    
			    WritableCellFormat cellBoldFormat = new WritableCellFormat(cellFont);
			    cellBoldFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				
				List<DetailsVO> insuranceList = convertVoForExcel( MPConstants.THIRTY_DAYS_FIRM_INSURANCE);
				int insuranceListSize = MPConstants.EXCEL_POSITION_ZERO;
				if (null != insuranceList) {
					insuranceListSize = insuranceList.size();
				}
				
				if(insuranceListSize!=MPConstants.EXCEL_POSITION_ZERO)
				{
					
				// add cell for 30 days Insurance Heading
				Label insurance = new Label(MPConstants.EXCEL_POSITION_ZERO,
						MPConstants.EXCEL_POSITION_ZERO,
						MPConstants.THIRTY_DAYS_INSURANCE
								+ dateFormat.format(new Date()) + ")", arial10format);

				sheet.addCell(insurance);

				Label insuranceDelivered = new Label(MPConstants.EXCEL_POSITION_ZERO,
						MPConstants.EXCEL_POSITION_ONE,
						MPConstants.DELIVERED_SUCCESSFULLY, arial11format);

				sheet.addCell(insuranceDelivered);
				

				int insuranceCredRow = MPConstants.EXCEL_POSITION_TWO;
				
				// add table heading for 30 days Insurance
				Label slNoInsurance = new Label(MPConstants.EXCEL_POSITION_ZERO,
						insuranceCredRow, MPConstants.SERIAL_NO, cellformat);

				sheet.addCell(slNoInsurance);

				Label firmIdInsurance = new Label(MPConstants.EXCEL_POSITION_ONE,
						insuranceCredRow, MPConstants.FIRM_ID, cellformat);

				sheet.addCell(firmIdInsurance);

				Label firmNameInsurance = new Label(MPConstants.EXCEL_POSITION_TWO,
						insuranceCredRow, MPConstants.FIRM_NAME, cellformat);

				sheet.addCell(firmNameInsurance);

				Label credentialTypeInsurance = new Label(
						MPConstants.EXCEL_POSITION_THREE, insuranceCredRow,
						MPConstants.INSURANCE_TYPE, cellformat);

				sheet.addCell(credentialTypeInsurance);
				Label expirationDateInsurance = new Label(
						MPConstants.EXCEL_POSITION_FOUR, insuranceCredRow,
						MPConstants.EXPIRATION_DATE, cellformat);

				sheet.addCell(expirationDateInsurance);

				Label notificationDateInsurance = new Label(
						MPConstants.EXCEL_POSITION_FIVE, insuranceCredRow,
						MPConstants.NOTICE_SENT_ON, cellformat);

				sheet.addCell(notificationDateInsurance);

				}
				else
				{
					sevenDaysInsurance=true;
					Label insurance = new Label(MPConstants.EXCEL_POSITION_ZERO,
							MPConstants.EXCEL_POSITION_ZERO,MPConstants.NO_THIRTY_DAYS_INSURANCE
									+ dateFormat.format(new Date()) + ")", arial10format);	
					sheet.addCell(insurance);
					
				}

				
				
				// add data for 30 days Insurance
				for (int columnCount = MPConstants.EXCEL_POSITION_ZERO; columnCount < insuranceListSize; columnCount++) {

					DetailsVO details = insuranceList.get(columnCount);
					Number seriallNo = new Number(MPConstants.EXCEL_POSITION_ZERO,
							(MPConstants.EXCEL_POSITION_THREE + columnCount),
							columnCount + MPConstants.EXCEL_POSITION_ONE, cellformat);
					Label entityId = new Label(MPConstants.EXCEL_POSITION_ONE,
							(MPConstants.EXCEL_POSITION_THREE + columnCount), details
									.getEntityId(), cellformat);
					Label entityName = new Label(MPConstants.EXCEL_POSITION_TWO,
							(MPConstants.EXCEL_POSITION_THREE + columnCount), details
									.getEntityName(), cellformat);
					Label credentailType = new Label(MPConstants.EXCEL_POSITION_THREE,
							(MPConstants.EXCEL_POSITION_THREE + columnCount), details
									.getCredentailType(), cellformat);
					Label expirationDateValue = new Label(
							MPConstants.EXCEL_POSITION_FOUR,
							(MPConstants.EXCEL_POSITION_THREE + columnCount), details
									.getExpirationDate(), cellBoldFormat);
					Label notificationDateValue = new Label(
							MPConstants.EXCEL_POSITION_FIVE,
							(MPConstants.EXCEL_POSITION_THREE + columnCount), details
									.getNotificationDate(), cellBoldFormat);

					sheet.addCell(seriallNo);
					sheet.addCell(entityId);
					sheet.addCell(entityName);
					sheet.addCell(credentailType);
					sheet.addCell(expirationDateValue);
					sheet.addCell(notificationDateValue);

				}

				// add cell for 30 days Company Credential Heading 
				int companyCredential = MPConstants.EXCEL_POSITION_FOUR
						+ insuranceListSize;
				int companyCredentialDelivered = MPConstants.EXCEL_POSITION_FIVE
						+ insuranceListSize;
				List<DetailsVO> companyCredentialList = convertVoForExcel(MPConstants.THIRTY_DAYS_FIRM_NONINSURANCE);

				int companyCredentialListSize = MPConstants.EXCEL_POSITION_ZERO;
				if (null != companyCredentialList) {
					companyCredentialListSize = companyCredentialList.size();
				}
				
				if(companyCredentialListSize != MPConstants.EXCEL_POSITION_ZERO)
				{
					
				
				Label companyCred = new Label(MPConstants.EXCEL_POSITION_ZERO,
						companyCredential,

						MPConstants.THIRTY_DAYS_COMPANY + dateFormat.format(new Date())
								+ ")", arial10format);
				sheet.addCell(companyCred);

				Label companyCredDelivered = new Label(MPConstants.EXCEL_POSITION_ZERO,
						companyCredentialDelivered, MPConstants.DELIVERED_SUCCESSFULLY,
						arial11format);

				sheet.addCell(companyCredDelivered);

				int companyCredRow = MPConstants.EXCEL_POSITION_SIX + insuranceListSize;

				Label slNo = new Label(MPConstants.EXCEL_POSITION_ZERO, companyCredRow,
						MPConstants.SERIAL_NO, cellformat);

				sheet.addCell(slNo);
				
				// add table heading for 30 days Company Credential
				Label firmId = new Label(MPConstants.EXCEL_POSITION_ONE,
						companyCredRow, MPConstants.FIRM_ID, cellformat);

				sheet.addCell(firmId);

				Label firmName = new Label(MPConstants.EXCEL_POSITION_TWO,
						companyCredRow, MPConstants.FIRM_NAME, cellformat);

				sheet.addCell(firmName);

				Label credentialType = new Label(MPConstants.EXCEL_POSITION_THREE,
						companyCredRow, MPConstants.CREDENTIAL_TYPE, cellformat);

				sheet.addCell(credentialType);
				Label expirationDate = new Label(MPConstants.EXCEL_POSITION_FOUR,
						companyCredRow, MPConstants.EXPIRATION_DATE, cellformat);

				sheet.addCell(expirationDate);

				Label notificationDate = new Label(MPConstants.EXCEL_POSITION_FIVE,
						companyCredRow, MPConstants.NOTICE_SENT_ON, cellformat);

				sheet.addCell(notificationDate);

				}
				else
				{
					sevenDaysFirm=true;
					Label companyCred = new Label(MPConstants.EXCEL_POSITION_ZERO,
							companyCredential,

							MPConstants.NO_THIRTY_DAYS_COMPANY + dateFormat.format(new Date())
									+ ")", arial10format);
					sheet.addCell(companyCred);
		
				}
				
				// add data for 30 days Company Credential table
				for (int columnCount = MPConstants.EXCEL_POSITION_ZERO; columnCount < companyCredentialListSize; columnCount++) {
					DetailsVO details = companyCredentialList.get(columnCount);
					Number seriallNo = new Number(MPConstants.EXCEL_POSITION_ZERO,
							(insuranceListSize
									+ MPConstants.EXCEL_POSITION_SEVEN + columnCount),
							columnCount + 1, cellformat);
					Label entityId = new Label(MPConstants.EXCEL_POSITION_ONE,
							(insuranceListSize
									+ MPConstants.EXCEL_POSITION_SEVEN + columnCount),
							details.getEntityId(), cellformat);
					Label entityName = new Label(MPConstants.EXCEL_POSITION_TWO,
							(insuranceListSize
									+ MPConstants.EXCEL_POSITION_SEVEN + columnCount),
							details.getEntityName(), cellformat);
					Label credentailType = new Label(MPConstants.EXCEL_POSITION_THREE,
							(insuranceListSize
									+ MPConstants.EXCEL_POSITION_SEVEN + columnCount),
							details.getCredentailType(), cellformat);
					Label expirationDateValue = new Label(
							MPConstants.EXCEL_POSITION_FOUR, (insuranceListSize
									+ MPConstants.EXCEL_POSITION_SEVEN + columnCount),
							details.getExpirationDate(), cellBoldFormat);
					Label notificationDateValue = new Label(
							MPConstants.EXCEL_POSITION_FIVE, (insuranceListSize
									+ MPConstants.EXCEL_POSITION_SEVEN + columnCount),
							details.getNotificationDate(), cellBoldFormat);

					sheet.addCell(seriallNo);
					sheet.addCell(entityId);
					sheet.addCell(entityName);
					sheet.addCell(credentailType);
					sheet.addCell(expirationDateValue);
					sheet.addCell(notificationDateValue);

				}

				int providerCredFirstrow = MPConstants.EXCEL_POSITION_NINE
						+ companyCredentialListSize + insuranceListSize;

				int providerCredSecondrow = MPConstants.EXCEL_POSITION_TEN
						+ companyCredentialListSize + insuranceListSize;

				// add cell for 30 days Provider Credential Heading
				
				List<DetailsVO> providerCredentialList = convertVoForExcel(MPConstants.THIRTY_DAYS_PROVIDER_LIST);

				int providerCredentialListSize = MPConstants.EXCEL_POSITION_ZERO;
				if (null != providerCredentialList) {
					providerCredentialListSize = providerCredentialList.size();
				}
				
				if(providerCredentialListSize != MPConstants.EXCEL_POSITION_ZERO)
				{
					
				
				Label providerCred = new Label(MPConstants.EXCEL_POSITION_ZERO,
						providerCredFirstrow,

						MPConstants.THIRTY_DAYS_PROVIDER + dateFormat.format(new Date())
								+ ")", arial10format);
				sheet.addCell(providerCred);

				Label providerCredDelivered = new Label(
						MPConstants.EXCEL_POSITION_ZERO, providerCredSecondrow,
						MPConstants.DELIVERED_SUCCESSFULLY, arial11format);

				sheet.addCell(providerCredDelivered);
				

				int providerCredrow = MPConstants.EXCEL_POSITION_ELEVEN
						+ companyCredentialListSize + insuranceListSize;
				
				// add table heading for 30 days Provider Credential
				Label slNoProvider = new Label(MPConstants.EXCEL_POSITION_ZERO,
						providerCredrow, MPConstants.SERIAL_NO, cellformat);

				sheet.addCell(slNoProvider);

				Label providerId = new Label(MPConstants.EXCEL_POSITION_ONE,
						providerCredrow, MPConstants.PROVIDER_ID, cellformat);

				sheet.addCell(providerId);

				Label providerName = new Label(MPConstants.EXCEL_POSITION_TWO,
						providerCredrow, MPConstants.PROVIDER_NAME, cellformat);

				sheet.addCell(providerName);

				Label providerCredentialType = new Label(
						MPConstants.EXCEL_POSITION_THREE, providerCredrow,
						MPConstants.CREDENTIAL_TYPE, cellformat);

				sheet.addCell(providerCredentialType);
				Label providerExpirationDate = new Label(
						MPConstants.EXCEL_POSITION_FOUR, providerCredrow,
						MPConstants.EXPIRATION_DATE, cellformat);

				sheet.addCell(providerExpirationDate);

				Label providerNotificationDate = new Label(
						MPConstants.EXCEL_POSITION_FIVE, providerCredrow,
						MPConstants.NOTICE_SENT_ON, cellformat);

				sheet.addCell(providerNotificationDate);
				}
				else
				{
					Label providerCred = new Label(MPConstants.EXCEL_POSITION_ZERO,
							providerCredFirstrow,

							MPConstants.NO_THIRTY_DAYS_PROVIDER + dateFormat.format(new Date())
									+ ")", arial10format);
					sheet.addCell(providerCred);	
				}
				
				
				// add data for 30 days Provider Credential table
				for (int columnCount = MPConstants.EXCEL_POSITION_ZERO; columnCount < providerCredentialListSize; columnCount++) {

					DetailsVO details = providerCredentialList.get(columnCount);
					Number seriallNo = new Number(MPConstants.EXCEL_POSITION_ZERO,
							(companyCredentialListSize + insuranceListSize
									+ MPConstants.EXCEL_POSITION_TWELVE + columnCount),
							columnCount + MPConstants.EXCEL_POSITION_ONE, cellformat);
					Label entityId = new Label(MPConstants.EXCEL_POSITION_ONE,
							(companyCredentialListSize + insuranceListSize
									+ MPConstants.EXCEL_POSITION_TWELVE + columnCount),
							details.getEntityId(), cellformat);
					Label entityName = new Label(MPConstants.EXCEL_POSITION_TWO,
							(companyCredentialListSize + insuranceListSize
									+ MPConstants.EXCEL_POSITION_TWELVE + columnCount),
							details.getEntityName(), cellformat);
					Label credentailType = new Label(MPConstants.EXCEL_POSITION_THREE,
							(companyCredentialListSize + insuranceListSize
									+ MPConstants.EXCEL_POSITION_TWELVE + columnCount),
							details.getCredentailType(), cellformat);
					Label expirationDateValue = new Label(
							MPConstants.EXCEL_POSITION_FOUR, (companyCredentialListSize
									+ insuranceListSize
									+ MPConstants.EXCEL_POSITION_TWELVE + columnCount),
							details.getExpirationDate(), cellBoldFormat);
					Label notificationDateValue = new Label(
							MPConstants.EXCEL_POSITION_FIVE, (companyCredentialListSize
									+ insuranceListSize
									+ MPConstants.EXCEL_POSITION_TWELVE + columnCount),
							details.getNotificationDate(), cellBoldFormat);

					sheet.addCell(seriallNo);
					sheet.addCell(entityId);
					sheet.addCell(entityName);
					sheet.addCell(credentailType);
					sheet.addCell(expirationDateValue);
					sheet.addCell(notificationDateValue);

				}
				//merge the cells for headings.
				sheet.mergeCells(MPConstants.EXCEL_POSITION_ZERO,
						MPConstants.EXCEL_POSITION_ZERO,
						MPConstants.EXCEL_POSITION_FIVE,
						MPConstants.EXCEL_POSITION_ZERO);
				sheet
						.mergeCells(MPConstants.EXCEL_POSITION_ZERO,
								MPConstants.EXCEL_POSITION_ONE,
								MPConstants.EXCEL_POSITION_FIVE,
								MPConstants.EXCEL_POSITION_ONE);

				sheet.mergeCells(MPConstants.EXCEL_POSITION_ZERO, companyCredential,
						MPConstants.EXCEL_POSITION_FIVE, companyCredential);
				sheet.mergeCells(MPConstants.EXCEL_POSITION_ZERO,
						companyCredentialDelivered, MPConstants.EXCEL_POSITION_FIVE,
						companyCredentialDelivered);
				sheet.mergeCells(MPConstants.EXCEL_POSITION_ZERO, providerCredFirstrow,
						MPConstants.EXCEL_POSITION_FIVE, providerCredFirstrow);
				sheet.mergeCells(MPConstants.EXCEL_POSITION_ZERO,
						providerCredSecondrow, MPConstants.EXCEL_POSITION_FIVE,
						providerCredSecondrow);

				sheet.findLabelCell("No"+
						MPConstants.SEVEN_DAYS_INSURANCE);
				if(sevenDaysInsurance)
				{
				sheet.removeRow(MPConstants.EXCEL_POSITION_ONE);
				sheet.removeRow(MPConstants.EXCEL_POSITION_TWO);
				}
				if(sevenDaysFirm)
				{
					
					sheet.removeRow(MPConstants.EXCEL_POSITION_THREE+insuranceListSize+companyCredentialListSize);
					sheet.removeRow(MPConstants.EXCEL_POSITION_FOUR+insuranceListSize+companyCredentialListSize);
				}
				sheet.insertRow(MPConstants.EXCEL_POSITION_ZERO);
				WritableCellFormat arialheadingformat = new WritableCellFormat(arial10font);
				arialheadingformat.setAlignment(Alignment.CENTRE);
				//arialheadingformat.setBackground(Colour.LIGHT_GREEN);
				arialheadingformat.setBorder(Border.ALL, BorderLineStyle.THIN);
			
				Label heading = new Label(
						MPConstants.EXCEL_POSITION_ZERO, MPConstants.EXCEL_POSITION_ZERO,
						"30 days Notification status", arialheadingformat);
				
				sheet.addCell(heading);
				sheet.mergeCells(MPConstants.EXCEL_POSITION_ZERO,
						MPConstants.EXCEL_POSITION_ZERO,
						MPConstants.EXCEL_POSITION_FIVE,
						MPConstants.EXCEL_POSITION_ZERO);
				workbook.write();
				workbook.close();
				outFinal.close();

			//saving excel in a folder
			String fileDir = applicationProperties.getPropertyFromDB(MPConstants.ADMIN_NOTIFICATION_DIRECTORY);
			new File(fileDir).mkdir();
			SimpleDateFormat sdf = new SimpleDateFormat ( "yyyyMMddHHmmss" ); 
			 fileDate = sdf.format(new Date());
			fos = new FileOutputStream(fileDir
					+ MPConstants.THIRTY_DAYS_NOTIFICATION_XLS+fileDate+MPConstants.XLS_FORMAT);

			fos.write(outFinal.toByteArray());
			fos.flush();
			
		}
		catch (IOException e) {
			throw new BusinessServiceException("IO Exception "+e);
		}
		catch (WriteException e) {
			throw new BusinessServiceException("Write Exception "+e);
		}
		catch (Exception e) {
			throw new BusinessServiceException("General Exception "+e);
		}
		finally
		{
			if(fos!=null)
			{
				try {
					fos.close();
					
				} catch (IOException e) {
					throw new BusinessServiceException("exception in finally"+e);

				}
			}
		}
		sendSLAdminMessage(
				MPConstants.THIRTY_DAYS_NOTIFICATION_XLS+fileDate+MPConstants.XLS_FORMAT,MPConstants.THIRTY_DAYS_EMAIL_SUBJECT);
		logger.info("30 days notification sent to SLAdmin");
		}

		public  void sevenDaysNotification() throws BusinessServiceException {
			FileOutputStream  fos=null;
			String fileDate="";
				try
				{
					   boolean sevenDaysInsurance=false;
					   boolean sevenDaysFirm=false;
  
			ByteArrayOutputStream outFinal = new ByteArrayOutputStream();
			  //create an excel
			WritableWorkbook workbook = Workbook.createWorkbook(outFinal);
			WritableSheet sheet = workbook.createSheet(MPConstants.FIRST_SHEET,
					MPConstants.EXCEL_POSITION_ONE);
			
			//set column size for individual column
			sheet.setColumnView(MPConstants.EXCEL_POSITION_ZERO, MPConstants.EXCEL_POSITION_TEN);
			sheet.setColumnView(MPConstants.EXCEL_POSITION_ONE, MPConstants.EXCEL_COLUMN_SIZE);
			sheet.setColumnView(MPConstants.EXCEL_POSITION_TWO, MPConstants.EXCEL_COLUMN_SIZE_THIRTY);
			sheet.setColumnView(MPConstants.EXCEL_POSITION_THREE, MPConstants.EXCEL_COLUMN_SIZE_THIRTY);
			sheet.setColumnView(MPConstants.EXCEL_POSITION_FOUR, MPConstants.EXCEL_COLUMN_SIZE);
			sheet.setColumnView(MPConstants.EXCEL_POSITION_FIVE, MPConstants.EXCEL_COLUMN_SIZE);
	        
			//set cell format for gray shade
			WritableFont arial10font = new WritableFont(WritableFont.ARIAL,
					MPConstants.EXCEL_POSITION_TEN, WritableFont.BOLD);
			WritableCellFormat arial10format = new WritableCellFormat(arial10font);
			arial10format.setAlignment(Alignment.LEFT);
			arial10format.setBackground(Colour.GRAY_25);
			arial10format.setBorder(Border.ALL, BorderLineStyle.THIN);
			arial10format.setIndentation(1);
			arial10format.setWrap(true);
			
			//set cell format for green shade
			WritableFont arial11font = new WritableFont(WritableFont.ARIAL,
					MPConstants.EXCEL_POSITION_TEN, WritableFont.BOLD);
			WritableCellFormat arial11format = new WritableCellFormat(arial10font);
			arial11format.setAlignment(Alignment.LEFT);
			arial11format.setBackground(Colour.LIGHT_GREEN);
			arial11format.setBorder(Border.ALL, BorderLineStyle.THIN);
			arial11format.setIndentation(1);
			arial11format.setWrap(true);
			
			WritableCellFormat cellformat = new WritableCellFormat();
			cellformat.setAlignment(Alignment.LEFT);
			cellformat.setIndentation(1);
			cellformat.setWrap(true);
			cellformat.setBorder(Border.ALL, BorderLineStyle.THIN);

			// Create cell font and format
		    WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
		    cellFont.setBoldStyle(WritableFont.BOLD);
		    
		    WritableCellFormat cellBoldFormat = new WritableCellFormat(cellFont);
		    cellBoldFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		    
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			
			List<DetailsVO> insuranceList = convertVoForExcel( MPConstants.SEVEN_DAYS_FIRM_INSURANCE);
			int insuranceListSize = MPConstants.EXCEL_POSITION_ZERO;
			if (null != insuranceList) {
				insuranceListSize = insuranceList.size();
			}
			
			if(insuranceListSize!=MPConstants.EXCEL_POSITION_ZERO)
			{
				
			// add cell for 7 days Insurance Heading
			Label insurance = new Label(MPConstants.EXCEL_POSITION_ZERO,
					MPConstants.EXCEL_POSITION_ZERO,
					MPConstants.SEVEN_DAYS_INSURANCE
							+ dateFormat.format(new Date()) + ")", arial10format);

			sheet.addCell(insurance);

			Label insuranceDelivered = new Label(MPConstants.EXCEL_POSITION_ZERO,
					MPConstants.EXCEL_POSITION_ONE,
					MPConstants.DELIVERED_SUCCESSFULLY, arial11format);

			sheet.addCell(insuranceDelivered);
			

			int insuranceCredRow = MPConstants.EXCEL_POSITION_TWO;
			
			// add table heading for 7 days Insurance
			Label slNoInsurance = new Label(MPConstants.EXCEL_POSITION_ZERO,
					insuranceCredRow, MPConstants.SERIAL_NO, cellformat);

			sheet.addCell(slNoInsurance);

			Label firmIdInsurance = new Label(MPConstants.EXCEL_POSITION_ONE,
					insuranceCredRow, MPConstants.FIRM_ID, cellformat);

			sheet.addCell(firmIdInsurance);

			Label firmNameInsurance = new Label(MPConstants.EXCEL_POSITION_TWO,
					insuranceCredRow, MPConstants.FIRM_NAME, cellformat);

			sheet.addCell(firmNameInsurance);

			Label credentialTypeInsurance = new Label(
					MPConstants.EXCEL_POSITION_THREE, insuranceCredRow,
					MPConstants.INSURANCE_TYPE, cellformat);

			sheet.addCell(credentialTypeInsurance);
			Label expirationDateInsurance = new Label(
					MPConstants.EXCEL_POSITION_FOUR, insuranceCredRow,
					MPConstants.EXPIRATION_DATE, cellformat);

			sheet.addCell(expirationDateInsurance);

			Label notificationDateInsurance = new Label(
					MPConstants.EXCEL_POSITION_FIVE, insuranceCredRow,
					MPConstants.NOTICE_SENT_ON, cellformat);

			sheet.addCell(notificationDateInsurance);

			}
			else
			{
				sevenDaysInsurance=true;
				Label insurance = new Label(MPConstants.EXCEL_POSITION_ZERO,
						MPConstants.EXCEL_POSITION_ZERO,
						MPConstants.NO_SEVEN_DAYS_INSURANCE
								+ dateFormat.format(new Date()) + ")", arial10format);	
				sheet.addCell(insurance);
				
			}

			
			
			// add data for 7 days Insurance
			for (int columnCount = MPConstants.EXCEL_POSITION_ZERO; columnCount < insuranceListSize; columnCount++) {

				DetailsVO details = insuranceList.get(columnCount);
				Number seriallNo = new Number(MPConstants.EXCEL_POSITION_ZERO,
						(MPConstants.EXCEL_POSITION_THREE + columnCount),
						columnCount + MPConstants.EXCEL_POSITION_ONE, cellformat);
				Label entityId = new Label(MPConstants.EXCEL_POSITION_ONE,
						(MPConstants.EXCEL_POSITION_THREE + columnCount), details
								.getEntityId(), cellformat);
				Label entityName = new Label(MPConstants.EXCEL_POSITION_TWO,
						(MPConstants.EXCEL_POSITION_THREE + columnCount), details
								.getEntityName(), cellformat);
				Label credentailType = new Label(MPConstants.EXCEL_POSITION_THREE,
						(MPConstants.EXCEL_POSITION_THREE + columnCount), details
								.getCredentailType(), cellformat);
				Label expirationDateValue = new Label(
						MPConstants.EXCEL_POSITION_FOUR,
						(MPConstants.EXCEL_POSITION_THREE + columnCount), details
								.getExpirationDate(), cellBoldFormat);
				Label notificationDateValue = new Label(
						MPConstants.EXCEL_POSITION_FIVE,
						(MPConstants.EXCEL_POSITION_THREE + columnCount), details
								.getNotificationDate(), cellBoldFormat);

				sheet.addCell(seriallNo);
				sheet.addCell(entityId);
				sheet.addCell(entityName);
				sheet.addCell(credentailType);
				sheet.addCell(expirationDateValue);
				sheet.addCell(notificationDateValue);

			}

			// add cell for 7 days Company Credential Heading 
			int companyCredential = MPConstants.EXCEL_POSITION_FOUR
					+ insuranceListSize;
			int companyCredentialDelivered = MPConstants.EXCEL_POSITION_FIVE
					+ insuranceListSize;
			List<DetailsVO> companyCredentialList = convertVoForExcel(MPConstants.SEVEN_DAYS_FIRM_NONINSURANCE);

			int companyCredentialListSize = MPConstants.EXCEL_POSITION_ZERO;
			if (null != companyCredentialList) {
				companyCredentialListSize = companyCredentialList.size();
			}
			
			if(companyCredentialListSize != MPConstants.EXCEL_POSITION_ZERO)
			{
				
			
			Label companyCred = new Label(MPConstants.EXCEL_POSITION_ZERO,
					companyCredential,

					MPConstants.SEVEN_DAYS_COMPANY + dateFormat.format(new Date())
							+ ")", arial10format);
			sheet.addCell(companyCred);

			Label companyCredDelivered = new Label(MPConstants.EXCEL_POSITION_ZERO,
					companyCredentialDelivered, MPConstants.DELIVERED_SUCCESSFULLY,
					arial11format);

			sheet.addCell(companyCredDelivered);

			int companyCredRow = MPConstants.EXCEL_POSITION_SIX + insuranceListSize;

			Label slNo = new Label(MPConstants.EXCEL_POSITION_ZERO, companyCredRow,
					MPConstants.SERIAL_NO, cellformat);

			sheet.addCell(slNo);
			
			// add table heading for 7 days Company Credential
			Label firmId = new Label(MPConstants.EXCEL_POSITION_ONE,
					companyCredRow, MPConstants.FIRM_ID, cellformat);

			sheet.addCell(firmId);

			Label firmName = new Label(MPConstants.EXCEL_POSITION_TWO,
					companyCredRow, MPConstants.FIRM_NAME, cellformat);

			sheet.addCell(firmName);

			Label credentialType = new Label(MPConstants.EXCEL_POSITION_THREE,
					companyCredRow, MPConstants.CREDENTIAL_TYPE, cellformat);

			sheet.addCell(credentialType);
			Label expirationDate = new Label(MPConstants.EXCEL_POSITION_FOUR,
					companyCredRow, MPConstants.EXPIRATION_DATE, cellformat);

			sheet.addCell(expirationDate);

			Label notificationDate = new Label(MPConstants.EXCEL_POSITION_FIVE,
					companyCredRow, MPConstants.NOTICE_SENT_ON, cellformat);

			sheet.addCell(notificationDate);

			}
			else
			{
				sevenDaysFirm=true;
				Label companyCred = new Label(MPConstants.EXCEL_POSITION_ZERO,
						companyCredential,

						MPConstants.NO_SEVEN_DAYS_COMPANY + dateFormat.format(new Date())
								+ ")", arial10format);
				sheet.addCell(companyCred);
	
			}
			
			// add data for 7 days Company Credential table
			for (int columnCount = MPConstants.EXCEL_POSITION_ZERO; columnCount < companyCredentialListSize; columnCount++) {
				DetailsVO details = companyCredentialList.get(columnCount);
				Number seriallNo = new Number(MPConstants.EXCEL_POSITION_ZERO,
						(insuranceListSize
								+ MPConstants.EXCEL_POSITION_SEVEN + columnCount),
						columnCount + 1, cellformat);
				Label entityId = new Label(MPConstants.EXCEL_POSITION_ONE,
						(insuranceListSize
								+ MPConstants.EXCEL_POSITION_SEVEN + columnCount),
						details.getEntityId(), cellformat);
				Label entityName = new Label(MPConstants.EXCEL_POSITION_TWO,
						(insuranceListSize
								+ MPConstants.EXCEL_POSITION_SEVEN + columnCount),
						details.getEntityName(), cellformat);
				Label credentailType = new Label(MPConstants.EXCEL_POSITION_THREE,
						(insuranceListSize
								+ MPConstants.EXCEL_POSITION_SEVEN + columnCount),
						details.getCredentailType(), cellformat);
				Label expirationDateValue = new Label(
						MPConstants.EXCEL_POSITION_FOUR, (insuranceListSize
								+ MPConstants.EXCEL_POSITION_SEVEN + columnCount),
						details.getExpirationDate(), cellBoldFormat);
				Label notificationDateValue = new Label(
						MPConstants.EXCEL_POSITION_FIVE, (insuranceListSize
								+ MPConstants.EXCEL_POSITION_SEVEN + columnCount),
						details.getNotificationDate(), cellBoldFormat);

				sheet.addCell(seriallNo);
				sheet.addCell(entityId);
				sheet.addCell(entityName);
				sheet.addCell(credentailType);
				sheet.addCell(expirationDateValue);
				sheet.addCell(notificationDateValue);

			}

			int providerCredFirstrow = MPConstants.EXCEL_POSITION_NINE
					+ companyCredentialListSize + insuranceListSize;

			int providerCredSecondrow = MPConstants.EXCEL_POSITION_TEN
					+ companyCredentialListSize + insuranceListSize;

			// add cell for 7 days Provider Credential Heading
			
			List<DetailsVO> providerCredentialList = convertVoForExcel(MPConstants.SEVEN_DAYS_PROVIDER_LIST);

			int providerCredentialListSize = MPConstants.EXCEL_POSITION_ZERO;
			if (null != providerCredentialList) {
				providerCredentialListSize = providerCredentialList.size();
			}
			
			if(providerCredentialListSize != MPConstants.EXCEL_POSITION_ZERO)
			{
				
			
			Label providerCred = new Label(MPConstants.EXCEL_POSITION_ZERO,
					providerCredFirstrow,

					MPConstants.SEVEN_DAYS_PROVIDER + dateFormat.format(new Date())
							+ ")", arial10format);
			sheet.addCell(providerCred);

			Label providerCredDelivered = new Label(
					MPConstants.EXCEL_POSITION_ZERO, providerCredSecondrow,
					MPConstants.DELIVERED_SUCCESSFULLY, arial11format);

			sheet.addCell(providerCredDelivered);
			

			int providerCredrow = MPConstants.EXCEL_POSITION_ELEVEN
					+ companyCredentialListSize + insuranceListSize;
			
			// add table heading for 7 days Provider Credential
			Label slNoProvider = new Label(MPConstants.EXCEL_POSITION_ZERO,
					providerCredrow, MPConstants.SERIAL_NO, cellformat);

			sheet.addCell(slNoProvider);

			Label providerId = new Label(MPConstants.EXCEL_POSITION_ONE,
					providerCredrow, MPConstants.PROVIDER_ID, cellformat);

			sheet.addCell(providerId);

			Label providerName = new Label(MPConstants.EXCEL_POSITION_TWO,
					providerCredrow, MPConstants.PROVIDER_NAME, cellformat);

			sheet.addCell(providerName);

			Label providerCredentialType = new Label(
					MPConstants.EXCEL_POSITION_THREE, providerCredrow,
					MPConstants.CREDENTIAL_TYPE, cellformat);

			sheet.addCell(providerCredentialType);
			Label providerExpirationDate = new Label(
					MPConstants.EXCEL_POSITION_FOUR, providerCredrow,
					MPConstants.EXPIRATION_DATE, cellformat);

			sheet.addCell(providerExpirationDate);

			Label providerNotificationDate = new Label(
					MPConstants.EXCEL_POSITION_FIVE, providerCredrow,
					MPConstants.NOTICE_SENT_ON, cellformat);

			sheet.addCell(providerNotificationDate);
			}
			else
			{
				Label providerCred = new Label(MPConstants.EXCEL_POSITION_ZERO,
						providerCredFirstrow,

						MPConstants.NO_SEVEN_DAYS_PROVIDER + dateFormat.format(new Date())
								+ ")", arial10format);
				sheet.addCell(providerCred);	
			}
			
			
			// add data for 7 days Provider Credential table
			for (int columnCount = MPConstants.EXCEL_POSITION_ZERO; columnCount < providerCredentialListSize; columnCount++) {

				DetailsVO details = providerCredentialList.get(columnCount);
				Number seriallNo = new Number(MPConstants.EXCEL_POSITION_ZERO,
						(companyCredentialListSize + insuranceListSize
								+ MPConstants.EXCEL_POSITION_TWELVE + columnCount),
						columnCount + MPConstants.EXCEL_POSITION_ONE, cellformat);
				Label entityId = new Label(MPConstants.EXCEL_POSITION_ONE,
						(companyCredentialListSize + insuranceListSize
								+ MPConstants.EXCEL_POSITION_TWELVE + columnCount),
						details.getEntityId(), cellformat);
				Label entityName = new Label(MPConstants.EXCEL_POSITION_TWO,
						(companyCredentialListSize + insuranceListSize
								+ MPConstants.EXCEL_POSITION_TWELVE + columnCount),
						details.getEntityName(), cellformat);
				Label credentailType = new Label(MPConstants.EXCEL_POSITION_THREE,
						(companyCredentialListSize + insuranceListSize
								+ MPConstants.EXCEL_POSITION_TWELVE + columnCount),
						details.getCredentailType(), cellformat);
				Label expirationDateValue = new Label(
						MPConstants.EXCEL_POSITION_FOUR, (companyCredentialListSize
								+ insuranceListSize
								+ MPConstants.EXCEL_POSITION_TWELVE + columnCount),
						details.getExpirationDate(), cellBoldFormat);
				Label notificationDateValue = new Label(
						MPConstants.EXCEL_POSITION_FIVE, (companyCredentialListSize
								+ insuranceListSize
								+ MPConstants.EXCEL_POSITION_TWELVE + columnCount),
						details.getNotificationDate(), cellBoldFormat);

				sheet.addCell(seriallNo);
				sheet.addCell(entityId);
				sheet.addCell(entityName);
				sheet.addCell(credentailType);
				sheet.addCell(expirationDateValue);
				sheet.addCell(notificationDateValue);

			}
			//merge the cells for headings.
			sheet.mergeCells(MPConstants.EXCEL_POSITION_ZERO,
					MPConstants.EXCEL_POSITION_ZERO,
					MPConstants.EXCEL_POSITION_FIVE,
					MPConstants.EXCEL_POSITION_ZERO);
			sheet
					.mergeCells(MPConstants.EXCEL_POSITION_ZERO,
							MPConstants.EXCEL_POSITION_ONE,
							MPConstants.EXCEL_POSITION_FIVE,
							MPConstants.EXCEL_POSITION_ONE);

			sheet.mergeCells(MPConstants.EXCEL_POSITION_ZERO, companyCredential,
					MPConstants.EXCEL_POSITION_FIVE, companyCredential);
			sheet.mergeCells(MPConstants.EXCEL_POSITION_ZERO,
					companyCredentialDelivered, MPConstants.EXCEL_POSITION_FIVE,
					companyCredentialDelivered);
			sheet.mergeCells(MPConstants.EXCEL_POSITION_ZERO, providerCredFirstrow,
					MPConstants.EXCEL_POSITION_FIVE, providerCredFirstrow);
			sheet.mergeCells(MPConstants.EXCEL_POSITION_ZERO,
					providerCredSecondrow, MPConstants.EXCEL_POSITION_FIVE,
					providerCredSecondrow);

			sheet.findLabelCell(
					MPConstants.NO_SEVEN_DAYS_INSURANCE);
			if(sevenDaysInsurance)
			{
			sheet.removeRow(MPConstants.EXCEL_POSITION_ONE);
			sheet.removeRow(MPConstants.EXCEL_POSITION_TWO);
			}
			if(sevenDaysFirm)
			{
				
				sheet.removeRow(MPConstants.EXCEL_POSITION_THREE+insuranceListSize+companyCredentialListSize);
				sheet.removeRow(MPConstants.EXCEL_POSITION_FOUR+insuranceListSize+companyCredentialListSize);
			}
			sheet.insertRow(MPConstants.EXCEL_POSITION_ZERO);
			WritableCellFormat arialheadingformat = new WritableCellFormat(arial10font);
			arialheadingformat.setAlignment(Alignment.CENTRE);
			//arialheadingformat.setBackground(Colour.LIGHT_GREEN);
			arialheadingformat.setBorder(Border.ALL, BorderLineStyle.THIN);
			Label heading = new Label(
					MPConstants.EXCEL_POSITION_ZERO, MPConstants.EXCEL_POSITION_ZERO,
					"7 days Notification status", arialheadingformat);
			
			sheet.addCell(heading);
			sheet.mergeCells(MPConstants.EXCEL_POSITION_ZERO, MPConstants.EXCEL_POSITION_ZERO,
					MPConstants.EXCEL_POSITION_FIVE, MPConstants.EXCEL_POSITION_ZERO);
			workbook.write();
			workbook.close();
			outFinal.close();
			//saving excel in a folder
			String fileDir = applicationProperties.getPropertyFromDB(MPConstants.ADMIN_NOTIFICATION_DIRECTORY);
			new File(fileDir).mkdir();
			SimpleDateFormat sdf = new SimpleDateFormat ( "yyyyMMddHHmmss" ); 
			fileDate = sdf.format(new Date());
			fos = new FileOutputStream(fileDir
					+ MPConstants.SEVEN_DAYS_NOTIFICATION_XLS+fileDate+MPConstants.XLS_FORMAT);

			fos.write(outFinal.toByteArray());
			fos.flush();
			
				}
			catch (IOException e) {
				throw new BusinessServiceException("IO Exception "+e);
			}
			catch (WriteException e) {
				throw new BusinessServiceException("Write Exception "+e);
			}
			catch (Exception e) {
				throw new BusinessServiceException("General Exception "+e);
			}
			finally
			{
				if(fos!=null)
				{
					try {
						fos.close();
						
					} catch (IOException e) {
						throw new BusinessServiceException("exception in finally"+e);

					}
				}
			}
			sendSLAdminMessage(
					MPConstants.SEVEN_DAYS_NOTIFICATION_XLS+fileDate+MPConstants.XLS_FORMAT,MPConstants.SEVEN_DAYS_EMAIL_SUBJECT);
			logger.info("7 days notification sent to SLAdmin");
			
		}

		public  void outOfComplainceNotification() throws BusinessServiceException {
			FileOutputStream fos =null;
			String fileDate="";
			try
			{
			ByteArrayOutputStream outFinal = new ByteArrayOutputStream();
			
			//create an excel
			WritableWorkbook workbook = Workbook.createWorkbook(outFinal);
			WritableSheet sheet = workbook.createSheet(MPConstants.FIRST_SHEET,
					MPConstants.EXCEL_POSITION_ONE);
			//set column size for individual column
			sheet.setColumnView(MPConstants.EXCEL_POSITION_ZERO, MPConstants.EXCEL_POSITION_TEN);
			sheet.setColumnView(MPConstants.EXCEL_POSITION_ONE, MPConstants.EXCEL_COLUMN_SIZE);
			sheet.setColumnView(MPConstants.EXCEL_POSITION_TWO, MPConstants.EXCEL_COLUMN_SIZE_THIRTY);
			sheet.setColumnView(MPConstants.EXCEL_POSITION_THREE, MPConstants.EXCEL_COLUMN_SIZE_THIRTY);
			sheet.setColumnView(MPConstants.EXCEL_POSITION_FOUR, MPConstants.EXCEL_COLUMN_SIZE);
			sheet.setColumnView(MPConstants.EXCEL_POSITION_FIVE, MPConstants.EXCEL_COLUMN_SIZE);
			
			//set cell format for yellow shade
			WritableFont arial10font = new WritableFont(WritableFont.ARIAL,
					MPConstants.EXCEL_POSITION_TEN, WritableFont.BOLD);
			WritableCellFormat arial10format = new WritableCellFormat(arial10font);
			arial10format.setAlignment(Alignment.LEFT);			
			arial10format.setBackground(Colour.GOLD);
			arial10format.setBorder(Border.ALL, BorderLineStyle.THIN);
			arial10format.setIndentation(1);
			arial10format.setWrap(true);
			
			WritableFont arial11font = new WritableFont(WritableFont.ARIAL,
					MPConstants.EXCEL_POSITION_TEN, WritableFont.BOLD);
			WritableCellFormat arial11format = new WritableCellFormat(arial10font);
			arial11format.setAlignment(Alignment.LEFT);
			arial11format.setBackground(Colour.LIGHT_GREEN);
			arial11format.setBorder(Border.ALL, BorderLineStyle.THIN);
			arial11format.setIndentation(1);
			arial11format.setWrap(true);
			
			WritableCellFormat cellformat = new WritableCellFormat();
			cellformat.setAlignment(Alignment.LEFT);
			cellformat.setIndentation(1);
			cellformat.setWrap(true);
			cellformat.setBorder(Border.ALL, BorderLineStyle.THIN);

			// Create cell font and format
		    WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 10);
		    cellFont.setBoldStyle(WritableFont.BOLD);
		    
		    WritableCellFormat cellBoldFormat = new WritableCellFormat(cellFont);
		    cellBoldFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		    
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			List<DetailsVO> insuranceList = convertVoForExcel(MPConstants.OUT_OF_COMPLAINCE_FIRM_INSURANCE);

			int insuranceListSize = MPConstants.EXCEL_POSITION_ZERO;
			if (null != insuranceList) {
				insuranceListSize = insuranceList.size();
			}
			
			if(insuranceListSize != MPConstants.EXCEL_POSITION_ZERO)
			{
				
		
			// add cell for Out of compliance-Insurance Heading
			Label insurance = new Label(MPConstants.EXCEL_POSITION_ZERO,
					MPConstants.EXCEL_POSITION_ZERO,
					MPConstants.OUT_OF_COMPLIANCE_INSURANCE
							+ dateFormat.format(new Date()) + ")", arial10format);

			sheet.addCell(insurance);

			int insuranceCredRow = MPConstants.EXCEL_POSITION_ONE;

			Label slNoInsurance = new Label(MPConstants.EXCEL_POSITION_ZERO,
					insuranceCredRow, MPConstants.SERIAL_NO, cellformat);

			sheet.addCell(slNoInsurance);
			// add table heading for  Out of compliance-Insurance
			Label firmIdInsurance = new Label(MPConstants.EXCEL_POSITION_ONE,
					insuranceCredRow, MPConstants.FIRM_ID, cellformat);

			sheet.addCell(firmIdInsurance);

			Label firmNameInsurance = new Label(MPConstants.EXCEL_POSITION_TWO,
					insuranceCredRow, MPConstants.FIRM_NAME, cellformat);

			sheet.addCell(firmNameInsurance);

			Label credentialTypeInsurance = new Label(
					MPConstants.EXCEL_POSITION_THREE, insuranceCredRow,
					MPConstants.INSURANCE_TYPE, cellformat);

			sheet.addCell(credentialTypeInsurance);
			Label expirationDateInsurance = new Label(
					MPConstants.EXCEL_POSITION_FOUR, insuranceCredRow,
					MPConstants.EXPIRATION_DATE, cellformat);

			sheet.addCell(expirationDateInsurance);

			Label notificationDateInsurance = new Label(
					MPConstants.EXCEL_POSITION_FIVE, insuranceCredRow,
					MPConstants.OUT_OF_COMPLIANCE_STATUS_CHANGE, cellformat);

			sheet.addCell(notificationDateInsurance);
		}
		else
		{
			Label insurance = new Label(MPConstants.EXCEL_POSITION_ZERO,
					MPConstants.EXCEL_POSITION_ZERO,
					MPConstants.NO_OUT_OF_COMPLIANCE_INSURANCE
							+ dateFormat.format(new Date()) + ")", arial10format);

			sheet.addCell(insurance);
		}
			
			// add data for Out of compliance-Insurance table
			for (int columnCount = MPConstants.EXCEL_POSITION_ZERO; columnCount < insuranceListSize; columnCount++) {

				DetailsVO details = insuranceList.get(columnCount);
				Number seriallNo = new Number(MPConstants.EXCEL_POSITION_ZERO,
						(MPConstants.EXCEL_POSITION_TWO + columnCount), columnCount
								+ MPConstants.EXCEL_POSITION_ONE, cellformat);
				Label entityId = new Label(MPConstants.EXCEL_POSITION_ONE,
						(MPConstants.EXCEL_POSITION_TWO + columnCount), details
								.getEntityId(), cellformat);
				Label entityName = new Label(MPConstants.EXCEL_POSITION_TWO,
						(MPConstants.EXCEL_POSITION_TWO + columnCount), details
								.getEntityName(), cellformat);
				Label credentailType = new Label(MPConstants.EXCEL_POSITION_THREE,
						(MPConstants.EXCEL_POSITION_TWO + columnCount), details
								.getCredentailType(), cellformat);
				Label expirationDateValue = new Label(
						MPConstants.EXCEL_POSITION_FOUR,
						(MPConstants.EXCEL_POSITION_TWO + columnCount), details
								.getExpirationDate(), cellBoldFormat);
				Label notificationDateValue = new Label(
						MPConstants.EXCEL_POSITION_FIVE,
						(MPConstants.EXCEL_POSITION_TWO + columnCount), details
								.getNotificationDate(), cellBoldFormat);

				sheet.addCell(seriallNo);
				sheet.addCell(entityId);
				sheet.addCell(entityName);
				sheet.addCell(credentailType);
				sheet.addCell(expirationDateValue);
				sheet.addCell(notificationDateValue);

			}

			List<DetailsVO> companyCredentialList = convertVoForExcel(MPConstants.OUT_OF_COMPLAINCE_FIRM_NONINSURANCE);

			int companyCredentialListSize = MPConstants.EXCEL_POSITION_ZERO;
			if (null != companyCredentialList) {
				companyCredentialListSize = companyCredentialList.size();
			}
			
			int companyCredential = insuranceListSize
					+ MPConstants.EXCEL_POSITION_THREE;
			
			if(companyCredentialListSize != MPConstants.EXCEL_POSITION_ZERO)
			{
			
			// add cell for Out of compliance-Company Heading
			Label companyCred = new Label(0, companyCredential,
					MPConstants.OUT_OF_COMPLIANCE_COMPANY
							+ dateFormat.format(new Date()) + ")", arial10format);
			sheet.addCell(companyCred);

			int companyCredRow = insuranceListSize
					+ MPConstants.EXCEL_POSITION_FOUR;

			
			
			Label slNo = new Label(MPConstants.EXCEL_POSITION_ZERO, companyCredRow,
					MPConstants.SERIAL_NO, cellformat);

			sheet.addCell(slNo);

			Label firmId = new Label(MPConstants.EXCEL_POSITION_ONE,
					companyCredRow, MPConstants.FIRM_ID, cellformat);

			sheet.addCell(firmId);

			Label firmName = new Label(MPConstants.EXCEL_POSITION_TWO,
					companyCredRow, MPConstants.FIRM_NAME, cellformat);

			sheet.addCell(firmName);

			Label credentialType = new Label(MPConstants.EXCEL_POSITION_THREE,
					companyCredRow, MPConstants.CREDENTIAL_TYPE, cellformat);

			sheet.addCell(credentialType);
			Label expirationDate = new Label(MPConstants.EXCEL_POSITION_FOUR,
					companyCredRow, MPConstants.EXPIRATION_DATE, cellformat);

			sheet.addCell(expirationDate);

			Label notificationDate = new Label(MPConstants.EXCEL_POSITION_FIVE,
					companyCredRow, MPConstants.OUT_OF_COMPLIANCE_STATUS_CHANGE,
					cellformat);

			sheet.addCell(notificationDate);
		}
			else
			{
				Label companyCred = new Label(0, companyCredential,
						MPConstants.NO_OUT_OF_COMPLIANCE_COMPANY
								+ dateFormat.format(new Date()) + ")", arial10format);
				sheet.addCell(companyCred);	
			}
			// add data for Out of compliance-Company table
			for (int columnCount = MPConstants.EXCEL_POSITION_ZERO; columnCount < companyCredentialListSize; columnCount++) {
				DetailsVO details = companyCredentialList.get(columnCount);
				Number seriallNo = new Number(MPConstants.EXCEL_POSITION_ZERO,
						(insuranceListSize
								+ MPConstants.EXCEL_POSITION_FIVE + columnCount),
						columnCount + MPConstants.EXCEL_POSITION_ONE, cellformat);
				Label entityId = new Label(MPConstants.EXCEL_POSITION_ONE,
						(insuranceListSize
								+ MPConstants.EXCEL_POSITION_FIVE + columnCount),
						details.getEntityId(), cellformat);
				Label entityName = new Label(MPConstants.EXCEL_POSITION_TWO,
						(insuranceListSize
								+ MPConstants.EXCEL_POSITION_FIVE + columnCount),
						details.getEntityName(), cellformat);
				Label credentailType = new Label(MPConstants.EXCEL_POSITION_THREE,
						(insuranceListSize
								+ MPConstants.EXCEL_POSITION_FIVE + columnCount),
						details.getCredentailType(), cellformat);
				Label expirationDateValue = new Label(
						MPConstants.EXCEL_POSITION_FOUR, (insuranceListSize
								+ MPConstants.EXCEL_POSITION_FIVE + columnCount),
						details.getExpirationDate(), cellBoldFormat);
				Label notificationDateValue = new Label(
						MPConstants.EXCEL_POSITION_FIVE, (insuranceListSize
								+ MPConstants.EXCEL_POSITION_FIVE + columnCount),
						details.getNotificationDate(), cellBoldFormat);

				sheet.addCell(seriallNo);
				sheet.addCell(entityId);
				sheet.addCell(entityName);
				sheet.addCell(credentailType);
				sheet.addCell(expirationDateValue);
				sheet.addCell(notificationDateValue);

			}

			int providerCredFirstrow = companyCredentialListSize
					+ insuranceListSize + MPConstants.EXCEL_POSITION_SIX;
			
			List<DetailsVO> providerCredentialList = convertVoForExcel(MPConstants.OUT_OF_COMPLAINCE_PROVIDER);

			int providerCredentialListSize = MPConstants.EXCEL_POSITION_ZERO;
			if (null != providerCredentialList) {
				providerCredentialListSize = providerCredentialList.size();
			}
			if(providerCredentialListSize != MPConstants.EXCEL_POSITION_ZERO)
			{
				
			
			
			// add cell for Out of compliance-Provider Heading
			Label providerCred = new Label(0, providerCredFirstrow,
					MPConstants.OUT_OF_COMPLIANCE_PROVIDER
							+ dateFormat.format(new Date()) + ")", arial10format);
			sheet.addCell(providerCred);

			

			int providerCredrow = companyCredentialListSize + insuranceListSize
					+ MPConstants.EXCEL_POSITION_SEVEN;
			// add table heading for  Out of compliance-Provider
			Label slNoProvider = new Label(MPConstants.EXCEL_POSITION_ZERO,
					providerCredrow, MPConstants.SERIAL_NO, cellformat);

			sheet.addCell(slNoProvider);

			Label providerId = new Label(MPConstants.EXCEL_POSITION_ONE,
					providerCredrow, MPConstants.PROVIDER_ID, cellformat);

			sheet.addCell(providerId);

			Label providerName = new Label(MPConstants.EXCEL_POSITION_TWO,
					providerCredrow, MPConstants.PROVIDER_NAME, cellformat);

			sheet.addCell(providerName);

			Label providerCredentialType = new Label(
					MPConstants.EXCEL_POSITION_THREE, providerCredrow,
					MPConstants.CREDENTIAL_TYPE, cellformat);

			sheet.addCell(providerCredentialType);
			Label providerExpirationDate = new Label(
					MPConstants.EXCEL_POSITION_FOUR, providerCredrow,
					MPConstants.EXPIRATION_DATE, cellformat);

			sheet.addCell(providerExpirationDate);

			Label providerNotificationDate = new Label(
					MPConstants.EXCEL_POSITION_FIVE, providerCredrow,
					MPConstants.OUT_OF_COMPLIANCE_STATUS_CHANGE, cellformat);

			sheet.addCell(providerNotificationDate);
			}
			else
			{
				Label providerCred = new Label(0, providerCredFirstrow,
						MPConstants.NO_OUT_OF_COMPLIANCE_PROVIDER
								+ dateFormat.format(new Date()) + ")", arial10format);
				sheet.addCell(providerCred);	
			}
			
			// add data for Out of compliance-Provider table
			for (int columnCount = MPConstants.EXCEL_POSITION_ZERO; columnCount < providerCredentialListSize; columnCount++) {

				DetailsVO details = providerCredentialList.get(columnCount);
				Number seriallNo = new Number(MPConstants.EXCEL_POSITION_ZERO,
						(companyCredentialListSize + insuranceListSize
								+ MPConstants.EXCEL_POSITION_EIGHT + columnCount),
						columnCount + MPConstants.EXCEL_POSITION_ONE, cellformat);
				Label entityId = new Label(MPConstants.EXCEL_POSITION_ONE,
						(companyCredentialListSize + insuranceListSize
								+ MPConstants.EXCEL_POSITION_EIGHT + columnCount),
						details.getEntityId(), cellformat);
				Label entityName = new Label(MPConstants.EXCEL_POSITION_TWO,
						(companyCredentialListSize + insuranceListSize
								+ MPConstants.EXCEL_POSITION_EIGHT + columnCount),
						details.getEntityName(), cellformat);
				Label credentailType = new Label(MPConstants.EXCEL_POSITION_THREE,
						(companyCredentialListSize + insuranceListSize
								+ MPConstants.EXCEL_POSITION_EIGHT + columnCount),
						details.getCredentailType(), cellformat);
				Label expirationDateValue = new Label(
						MPConstants.EXCEL_POSITION_FOUR, (companyCredentialListSize
								+ insuranceListSize
								+ MPConstants.EXCEL_POSITION_EIGHT + columnCount),
						details.getExpirationDate(), cellBoldFormat);
				Label notificationDateValue = new Label(
						MPConstants.EXCEL_POSITION_FIVE, (companyCredentialListSize
								+ insuranceListSize
								+ MPConstants.EXCEL_POSITION_EIGHT + columnCount),
						details.getNotificationDate(), cellBoldFormat);

				sheet.addCell(seriallNo);
				sheet.addCell(entityId);
				sheet.addCell(entityName);
				sheet.addCell(credentailType);
				sheet.addCell(expirationDateValue);
				sheet.addCell(notificationDateValue);

			}

			
	      // merger cells for headings
			sheet.mergeCells(MPConstants.EXCEL_POSITION_ZERO,
					MPConstants.EXCEL_POSITION_ZERO,
					MPConstants.EXCEL_POSITION_FIVE,
					MPConstants.EXCEL_POSITION_ZERO);
			sheet.mergeCells(MPConstants.EXCEL_POSITION_ZERO, companyCredential,
					MPConstants.EXCEL_POSITION_FIVE, companyCredential);
			sheet.mergeCells(MPConstants.EXCEL_POSITION_ZERO, providerCredFirstrow,
					MPConstants.EXCEL_POSITION_FIVE, providerCredFirstrow);
			
			if(insuranceListSize==MPConstants.EXCEL_POSITION_ZERO)
			{
				sheet.removeRow(MPConstants.EXCEL_POSITION_ONE);	
				
			}
			if(companyCredentialListSize==MPConstants.EXCEL_POSITION_ZERO)
			{
				sheet.removeRow(MPConstants.EXCEL_POSITION_FOUR+insuranceListSize+companyCredentialListSize);	
				
			}
			
			sheet.insertRow(MPConstants.EXCEL_POSITION_ZERO);
			WritableCellFormat arialheadingformat = new WritableCellFormat(arial10font);
			arialheadingformat.setAlignment(Alignment.CENTRE);
			//arialheadingformat.setBackground(Colour.LIGHT_GREEN);
			arialheadingformat.setBorder(Border.ALL, BorderLineStyle.THIN);
			Label heading = new Label(
					MPConstants.EXCEL_POSITION_ZERO, MPConstants.EXCEL_POSITION_ZERO,
					"Document (Insurance/credential) status changed to \"Out of compliance\"", arialheadingformat);
			
			sheet.addCell(heading);
			sheet.mergeCells(MPConstants.EXCEL_POSITION_ZERO, MPConstants.EXCEL_POSITION_ZERO,
					MPConstants.EXCEL_POSITION_FIVE, MPConstants.EXCEL_POSITION_ZERO);
			workbook.write();
			workbook.close();
			outFinal.close();
			//saving file in excel
			String fileDir = applicationProperties.getPropertyFromDB(MPConstants.ADMIN_NOTIFICATION_DIRECTORY);
			new File(fileDir).mkdir();
			SimpleDateFormat sdf = new SimpleDateFormat ( "yyyyMMddHHmmss" ); 
			fileDate = sdf.format(new Date());
			
			fos=new FileOutputStream(fileDir
					+ MPConstants.OUT_OF_COMPLAINCE_XLS+fileDate+MPConstants.XLS_FORMAT);

			fos.write(outFinal.toByteArray());
			fos.flush();
			
			}
			catch (IOException e) {
				throw new BusinessServiceException("IO Exception "+e);
			}
			catch (WriteException e) {
				throw new BusinessServiceException("Write Exception "+e);
			}
			catch (Exception e) {
				throw new BusinessServiceException("General Exception "+e);
			}
			finally
			{
				if(fos!=null)
				{
					try {
						fos.close();
						
					} catch (IOException e) {
						throw new BusinessServiceException("exception in finally"+e);

					}
				}
			}
			sendSLAdminMessage(
					MPConstants.OUT_OF_COMPLAINCE_XLS+fileDate+MPConstants.XLS_FORMAT,MPConstants.OUT_COMPLAINCE_EMAIL_SUBJECT);
			logger.info("out of complaince notification sent to SLAdmin");

		}

	// send email to SLAdmin
	

	public  void sendSLAdminMessage( 
			String nameOfFile, String sub
			) {
		try {
		logger.info("inJavaMail");
		String toAddresses=applicationProperties.getPropertyFromDB(MPConstants.ADMIN_EMAIL_GROUP);
		String toRecipients[]=toAddresses.split(";");
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(MPConstants.MAIL_HOST);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper mimeHelper;
	
			mimeHelper = new MimeMessageHelper(mimeMessage, true);
			
			mimeHelper.setSubject(sub);
			mimeHelper.setTo(toRecipients);
			mimeHelper.setFrom(MPConstants.EMAIL_FROM);
			
		
			
			//reading the excel file.
			String fileDir = applicationProperties.getPropertyFromDB(MPConstants.ADMIN_NOTIFICATION_DIRECTORY);
			logger.info("beforeattaching");
			mimeHelper.setText("Please find the attached files.");
			logger.info("setText");
			File file = new File(fileDir + nameOfFile);
			//adding excel to the mail.
			if (file.exists()) {
				mimeHelper.addAttachment(nameOfFile, file);
			}
			logger.info("afterattaching");
			mailSender.send(mimeMessage);
			logger.info(sub + " email sent to "+toAddresses);
		} catch (MessagingException e) {
			logger.error("MessagingException while sending email to SLAdmin"
					+ e);
		} catch (Exception e) {
			logger.error("Exception while sending email to SLAdmin" + e);
		}
		

	}
	
	
	//update tables for expired credentials
			private String setExpirationDataForProviders(List<ExpiryDetailsVO> credentialDetailsList) throws  BusinessServiceException
			{
			String expirationData = Constants.TABLE_HEADER;
			Integer providerId = 0;
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

			for (ExpiryDetailsVO expiryDetails :credentialDetailsList ) {

				if (null != expiryDetails.getProviderId()
						&& !expiryDetails.getProviderId()
								.equals(providerId)) {
					
					expirationData = expirationData
							+ expiryDetails.getProviderFirstName() + ","
							+ expiryDetails.getProviderLastName();
					
//				expirationData =Constants.TABLE_BODY_NAME+expiryDetails.getProviderFirstName() + ","
//				+expiryDetails.getProviderLastName()+Constants.TABLE_BODY_ID+expiryDetails.getProviderId().toString()
//				+Constants.TABLE_BODY_DATE+dateFormat.format(expiryDetails.getCredentialExpirationDate());
				}

			}
			return expirationData;
		}
	public DocumentExpiryDao getDocumentExpiryDao() {
		return documentExpiryDao;
	}

	public void setDocumentExpiryDao(DocumentExpiryDao documentExpiryDao) {
		this.documentExpiryDao = documentExpiryDao;
	}
	public AlertDao getAlertDao() {
		return alertDao;
	}

	public void setAlertDao(AlertDao alertDao) {
		this.alertDao = alertDao;
	}

	public IApplicationProperties getApplicationProperties() {
		return applicationProperties;
	}

	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	
}
