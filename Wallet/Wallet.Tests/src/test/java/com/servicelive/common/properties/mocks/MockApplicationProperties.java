package com.servicelive.common.properties.mocks;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.properties.IApplicationProperties;

/**
 * Class MockApplicationProperties.
 */
public class MockApplicationProperties implements IApplicationProperties {

	/** applicationProperties. */
	private IApplicationProperties applicationProperties;
	
	/* (non-Javadoc)
	 * @see com.servicelive.common.properties.IApplicationProperties#getFMPropertyValue(java.lang.String)
	 */
	public String getFMPropertyValue(String key) throws DataNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.common.properties.IApplicationProperties#getPropertyValue(java.lang.String)
	 */
	public String getPropertyValue(String key) throws DataNotFoundException {
		if (key == "ptd_file_directory"){
			return "C:\\projects\\sears\\ptdFiles\\";
		}
		else if (key == CommonConstants.TRANS_FILE_DIRECTORY){
			return "C:\\projects\\sears\\testFiles\\trans\\";
		}
		else if (key == "daily_reconciliation_email_body"){
			return "this is a test message";
		}
		else if(key == "ptd_alert_to_address" 
			|| key == "ack_failure_notification_pager"
			|| key == "email_admin_servicelive"
			|| key == "org_failure_notification_pager"
			|| key == "daily_reconciliation_email_to"){
			return "slbuyeradmin@gmail.com";
		}
		else if (key == "acknowledgement_file_directory"){
			return "C:\\projects\\sears\\testFiles\\ackFiles\\";
		}
		else if (key == CommonConstants.ORIGINATION_FILE_DIRECTORY){
			return "C:\\projects\\sears\\testFiles\\orgFiles\\";
		}
		else if (key == CommonConstants.ORIGINATION_FILE_ARCHIVE_DIRECTORY){
			return "C:\\projects\\sears\\testFiles\\archive\\orgFiles\\";
		}
		else if (key == CommonConstants.RETURNS_FILE_DIRECTORY){
			return "C:\\projects\\sears\\testFiles\\retFiles\\";
		}
		else if (key == CommonConstants.RETURNS_FILE_ARCHIVE_DIRECTORY){
			return "C:\\projects\\sears\\testFiles\\archive\\retFiles\\";
		}
		else if (key == "daily_reconciliation_file_directory"){
			return "C:\\projects\\sears\\testFiles\\reports\\";
		}
		return applicationProperties.getPropertyValue(key);
	}

	/**
	 * setApplicationProperties.
	 * 
	 * @param applicationProperties 
	 * 
	 * @return void
	 */
	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	public String getPropertyFromDB(String key) throws DataNotFoundException {
		return getPropertyValue(key);
	}

}
