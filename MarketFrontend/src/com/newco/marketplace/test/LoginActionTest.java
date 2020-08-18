package com.newco.marketplace.test;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newco.marketplace.web.action.login.LoginAction;
import com.opensymphony.xwork2.Action;

public class LoginActionTest extends TestCase {

	private LoginAction action;
	
	protected void setUp() throws Exception {
	        action = new LoginAction();
	}

	public void testExecute() {
		try {
		//ContextValue.setContextFile("resources/spring/applicationContext.xml");
		//System.out.println("ServiceOrderRoleTest-->Loaded applicationContext.xml");
		ApplicationContext ctx;
		ctx = new ClassPathXmlApplicationContext("/resources/spring/applicationContext.xml");
		String result = action.execute();
	        assertEquals(Action.ERROR, result);
	        assertEquals(1, action.getActionErrors().size());
	        assertEquals("Invalid LoginAction.", action.getActionErrors().iterator().next());
	        assertNull(action.execute());

		} catch (Throwable t) {
			t.printStackTrace();
			System.out.println(t.getMessage());
		}	

	}
}
