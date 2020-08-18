package com.newco.marketplace.api.beans.apiUserProfileAuth;

import static org.mockito.Mockito.mock;
import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.newco.marketplace.api.security.AuthenticationProcess;
import com.newco.marketplace.business.businessImpl.provider.LoginBOImpl;
import com.newco.marketplace.business.iBusiness.provider.ILoginBO;

public class AuthenticationProcessTest {
	
   private static Logger  logger = Logger.getLogger(AuthenticationProcessTest.class);
   private ILoginBO loginBOImpl;
   private AuthenticationProcess process;
   
   @Test
   public void validateUsername(){
	   String userName = "athakkar123";
	   String password = "Test123!";
	   boolean isExists = false;
	   try{
		   loginBOImpl = mock(LoginBOImpl.class);
		   process = new AuthenticationProcess();
		   process.setLoginBOImpl(loginBOImpl);
		   isExists = process.authenticate(userName, password);
		   Assert.assertTrue("User Does not exists",isExists);
	    }catch(Exception e){
		   logger.error("Exception in validating user credentials: "+ e.getMessage());
	   }
   }
   
}
