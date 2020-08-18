package com.newco.marketplace.persistence.iDao.alert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.newco.marketplace.business.businessImpl.alert.AlertBuyerEmail;
import com.newco.marketplace.business.businessImpl.alert.AlertDisposition;
import com.newco.marketplace.business.businessImpl.alert.AlertProviderEmail;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.business.businessImpl.alert.BuyerCallbackNotification;
import com.newco.marketplace.business.businessImpl.vibePostAPI.BuyerCallbackNotificationStatusVo;
import com.newco.marketplace.business.businessImpl.vibePostAPI.PushAlertStatusVo;
import com.newco.marketplace.business.businessImpl.vibePostAPI.VibeAlertStatusVO;
import com.newco.marketplace.dto.vo.buyerCallbackEvent.BuyerCallbackNotificationVO;
import com.newco.marketplace.dto.vo.serviceorder.BuyerDetail;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.dto.vo.serviceorder.ProviderDetail;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderNote;
import com.newco.marketplace.exception.core.DataServiceException;

public interface AlertDao {
	public boolean addAlertToQueue(AlertTask alertTask) throws DataServiceException;
	public boolean addAlertListToQueue(List<AlertTask> alertTaskList) throws DataServiceException;
	public Long addAlertTask(AlertTask alertTask) throws DataServiceException;
	public AlertDisposition getAlertDisposition (String alertName, String aopActionId) throws DataServiceException ;
	public ProviderDetail getProviderDetailForNotes(String soId) throws DataServiceException ;
	public ServiceOrderNote getLatestNoteDetail(String soId) throws DataServiceException ;
	public ArrayList<BuyerDetail> getAllBuyerDetail() throws DataServiceException ;
	public ArrayList<ProviderDetail> retrieveAllVendors() 	throws DataServiceException;
	public ArrayList<Contact> retrieveAllAdmins() throws DataServiceException;
	public ArrayList<AlertTask> retrieveAlertTasks(String priority, String alertTypeId) throws DataServiceException;
	public ArrayList<AlertTask> retrieveAlertTypes() throws DataServiceException;
	public void updateAlertStatus(List<Long> alertTaskIds) throws DataServiceException;
	public ArrayList<AlertProviderEmail> getProviderAndResourceEmails(String soId, Integer tierId)throws DataServiceException;
	public ArrayList<AlertProviderEmail> getProviderAdminEmails(String soId)throws DataServiceException;
	public ArrayList<AlertProviderEmail> getProviderAdminEmailForRejectedSO(String soId)throws DataServiceException;
	public ArrayList<AlertProviderEmail> getProviderAdminEmailsForAddNotes(String soId)throws DataServiceException;
	public ArrayList<AlertProviderEmail> getProviderAdminEmailsForAddNotesAccepted(String soId)throws DataServiceException;
	public ArrayList<AlertProviderEmail> getProviderAdminEmailsForReleasedSO(String soId) throws DataServiceException;
	public AlertBuyerEmail getBuyerAdminEmailAddr( String soId)throws DataServiceException;
	public AlertBuyerEmail getSOCreatorEmailAddr( String soId)throws DataServiceException;
	public  ArrayList<AlertProviderEmail> getAllRoutedProvidersExceptAccepted(String soId) throws DataServiceException;
	public String getLoggedInProvider(Integer vid) throws DataServiceException;
	public boolean resendWelcomeMail(String userName, String emailAddress);
	public void optOutSMS(String smsNo)throws DataServiceException;
	public ArrayList<String> getEmailFromSmsNo(String smsNo)throws DataServiceException;
	/*
	 * This method retrieves the Provider Admin Email
	 * parameters String soId
	 * return String
	 * throws DataServiceException
	 */
	public String getProvAdminEmailAddrForChangeOfScopeSO(String soId) throws DataServiceException;
	//SL-18979 vibe
	public ArrayList<AlertTask> retrieveAlertTasksForVibe(int noOfRecords) throws DataServiceException;
	public Map <String, String> getBuyerCallbackNotificationConstants() throws DataServiceException;
	public ArrayList<AlertTask> retrieveAlertTasksForPushNotification(int noOfRecords) throws DataServiceException;
	public ArrayList<BuyerCallbackNotification> retrieveBuyerCallbackNotification(int noOfRecords) throws DataServiceException;
	//PUSH Notification changes
	public Map <String, String> getNotificationConstants() throws DataServiceException;
	public String getTemplateData(int templateId) throws DataServiceException;
	public void insertPushAlertStatus(List<PushAlertStatusVo> pushAlertStatusVOList) throws DataServiceException;
	
	//R16_1: SL-18979: Fetching constant values from application constants
	public Map <String, String> getVibeConstants() throws DataServiceException;
	//R16_1: SL-18979: insert into sms_alert_status
	public void insertSMSAlertStatus(List<VibeAlertStatusVO> smsAlertStatusVOList) throws DataServiceException;
	
    public void updateAlertStatusPushNotification(Long alertTaskId) throws DataServiceException;
    
	public void updateAlertStatus(Long alertTaskId) throws DataServiceException;
	public void updateCallbackNotificationStatus(BuyerCallbackNotificationStatusVo notificationVo) throws DataServiceException;

}
 