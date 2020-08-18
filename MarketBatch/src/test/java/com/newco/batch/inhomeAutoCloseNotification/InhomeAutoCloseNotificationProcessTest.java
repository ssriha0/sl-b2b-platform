package com.newco.batch.inhomeAutoCloseNotification;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.newco.marketplace.business.businessImpl.inhomeAutoCloseNotification.InhomeAutoCloseBOImpl;
import com.newco.marketplace.inhomeautoclosenotification.IInhomeAutoCloseBO;
import com.newco.marketplace.inhomeautoclosenotification.vo.InHomeAutoCloseVO;

public class InhomeAutoCloseNotificationProcessTest {
	
	private IInhomeAutoCloseBO inhomeautoCloseBO;
	private InhomeAutoCloseNotificationProcess process;
	public static final String AUTOCLOSE_FAILURE_NOTIFICATION_RECIPIENT_MAILS = "auto_close_failure_notification_recipient";
	
	@Test
	public void processTest(){
		String recipientId=null;
		List<InHomeAutoCloseVO> notificationList = new ArrayList<InHomeAutoCloseVO>();
		InHomeAutoCloseVO notifInfo= new InHomeAutoCloseVO();
		notifInfo.setAutoCloseId(1);
		notifInfo.setSoId("571-0466-5997-41");
		notificationList.add(notifInfo);
		
	
		try {
		process= new InhomeAutoCloseNotificationProcess();
		inhomeautoCloseBO = mock(InhomeAutoCloseBOImpl.class);
		process.setInhomeautoCloseBO(inhomeautoCloseBO);
		
		when(inhomeautoCloseBO.getRecipientIdFromDB(AUTOCLOSE_FAILURE_NOTIFICATION_RECIPIENT_MAILS)).thenReturn("nvijay0@searshc.com");
		recipientId=inhomeautoCloseBO.getRecipientIdFromDB(AUTOCLOSE_FAILURE_NOTIFICATION_RECIPIENT_MAILS);
		Assert.assertNotNull(recipientId);
		
		
		when(inhomeautoCloseBO.fetchRecords()).thenReturn(notificationList);
		notificationList = inhomeautoCloseBO.fetchRecords();
		Assert.assertNotNull(notificationList);
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
