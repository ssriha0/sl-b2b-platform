package com.newco.marketplace.persistence.iDao.providerBackground;

import java.util.List;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.vo.provider.ExpiryNotificationVO;
import com.newco.marketplace.vo.provider.ProviderBackgroundCheckVO;
import com.newco.marketplace.vo.provider.VendorNotesVO;


public interface ProviderBackgroundCheckDao 
{
	//adding entry in vendor_notes
    public void insertVendorNotes(List<VendorNotesVO> voList) throws DataServiceException;
	//adding entry in audit_cred_expiry_notification for thirty days
    public void insertExpiryNotificationForThirtyDays(List<ExpiryNotificationVO> expiryNotificationList)throws DataServiceException;
    //adding entry in audit_cred_expiry_notification for seven days
    public void insertExpiryNotificationForSevenDays(List<ExpiryNotificationVO> expiryNotificationList)throws DataServiceException;
    //adding entry in audit_cred_expiry_notification for zero days
    public void insertExpiryNotificationForZeroDays(List<ExpiryNotificationVO> expiryNotificationList)throws DataServiceException;
	//adding entry in alert_task
    public boolean addAlertToQueue(List<AlertTask> alertTaskList) throws DataServiceException;
    // get background check notification data for all providers
    public List<ProviderBackgroundCheckVO> getProviderInfoForNotification() throws DataServiceException;
    //to update notification type of 7 
	public void updateSevenDaysExpiryNotification(List<Integer> notificationIdsList)throws DataServiceException;
	//to update notification type of 0
	public void updateZeroDaysExpiryNotification(List<Integer> notificationIdsList)throws DataServiceException;
	//to update thirty day with new date and clear 0 and 7 days column to null
	public void updateAndClearExpiryNotification(List<Integer> notificationIdsList)throws DataServiceException;
	
	//R11_2
	//SL-20437
	// get background check notification data for all providers
    public List<ProviderBackgroundCheckVO> getProvInfoForNotification() throws DataServiceException;
    //adding entry in audit_cred_expiry_notification
    public void insertExpiryNotification(List<ExpiryNotificationVO> expiryNotificationList)throws DataServiceException;
}
 