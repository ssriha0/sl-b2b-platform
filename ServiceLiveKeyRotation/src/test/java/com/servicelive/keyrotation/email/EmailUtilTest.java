package com.servicelive.keyrotation.email;

import org.junit.Test;

import com.servicelive.keyrotation.constants.KeyRotationConstants;
import com.servicelive.keyrotation.email.EmailUtil;

/**
 * @author Infosys: Jun, 2014 
 */
public class EmailUtilTest {
	@Test
	public void sendEmailTest(){
		try {
			KeyRotationConstants.EMAIL_NOTIFICATION = "ON";
			KeyRotationConstants.EMAIL_RECIPIENT_LIST_COMMA_SEPARATED = "user1,user2";
			KeyRotationConstants.EMAIL_SMTP = "smtp.sears.com";
			KeyRotationConstants.EMAIL_SENDER = "user@searshc.com";
			KeyRotationConstants.EMAIL_SUBJECT = "Subject: Encryption Key Rotation batch Notification";
			KeyRotationConstants.EMAIL_CONTENT = "Hi,\n\nEncryption Key rotation batch ran successfully!\n";
			KeyRotationConstants.EMAIL_DETAIL_1 = "Credit Card Encryption changed on : ";
			KeyRotationConstants.EMAIL_DETAIL_2 = ". The change was initiated by : ";
			KeyRotationConstants.EMAIL_SIGNATURE = "Sent by : ";
			
			EmailUtil.sendEmail("cjose2");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
