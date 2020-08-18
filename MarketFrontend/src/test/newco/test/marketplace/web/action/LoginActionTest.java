package test.newco.test.marketplace.web.action;

import java.util.HashMap;

import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.web.action.login.LoginAction;
import com.newco.marketplace.web.dto.provider.LoginDto;

public class LoginActionTest extends BaseStrutsTestCase {
	
	 public void testInterceptorsBySettingDomainObjects() throws Exception {   
		LoginAction action = (LoginAction)createAction(com.newco.marketplace.web.action.login.LoginAction.class,"/security","doLogin");
		String userName = "scarpe5@searshc.com";
		LoginDto loginDto = new LoginDto(); 
		loginDto.setUsername(userName);
		loginDto.setPassword("Test123!");
		HashMap<String,SecurityContext> hm = new HashMap<String,SecurityContext>();
		SecurityContext securityCntxt = new SecurityContext(userName);
		hm.put("SecurityContext", securityCntxt);
		action.setSession(hm);
		String result = proxy.execute();   
		assertEquals(result, "buyerdashboard");   
	 }    
}
