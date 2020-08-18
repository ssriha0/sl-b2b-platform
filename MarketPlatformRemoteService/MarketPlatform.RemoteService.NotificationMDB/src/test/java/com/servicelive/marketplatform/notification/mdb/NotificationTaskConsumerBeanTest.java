package com.servicelive.marketplatform.notification.mdb;

import java.security.Identity;
import java.security.Principal;
import java.util.Properties;

import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.ejb.MessageDrivenContext;
import javax.ejb.TimerService;
import javax.transaction.UserTransaction;

import org.junit.Test;

public class NotificationTaskConsumerBeanTest{
	private NotificationTaskConsumerBean bean;
	
	@Test
	public void testSetMessageDrivenContext(){
		bean = new NotificationTaskConsumerBean();
		
		MessageDrivenContext messageDrivenContext = new MessageDrivenContext() {
			
			public void setRollbackOnly() throws IllegalStateException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean isCallerInRole(Identity arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			public boolean isCallerInRole(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}
			
			public UserTransaction getUserTransaction() throws IllegalStateException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public TimerService getTimerService() throws IllegalStateException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public boolean getRollbackOnly() throws IllegalStateException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public Properties getEnvironment() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public EJBLocalHome getEJBLocalHome() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public EJBHome getEJBHome() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Principal getCallerPrincipal() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Identity getCallerIdentity() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		bean.setMessageDrivenContext(messageDrivenContext);
		
	}
	
	
}
