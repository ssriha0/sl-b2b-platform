package com.newco.marketplace.test;

import com.newco.marketplace.dto.vo.account.AccountProfile;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.test.SLBase;

public class AccountProfileTest extends SLBase {

	public void testDeleteServiceOrder() throws DataServiceException, BusinessServiceException {
		String username[]={"null","buyer0002","prov0002","auditor02","newRegister","bu%","BUYER0002"},returnValue=null;
		String pwd[]={"null","Passw111!","PASSW001!","auditor02",null,"bu%","PASSW111!"};
		int i=0;Object valueRetured;
		for(String uN:username){display("userName=".concat(uN));
			returnValue=_iSecurityDAO.getPassword(uN);
			display(returnValue);
			valueRetured=_iSecurityBO.authenticate(uN, pwd[i++]);
			display(valueRetured);			
			AccountProfile ap=_iSecurityDAO.getAccountProfile(uN);
			display(ap);	
			ap=_iSecurityBO.getAccountProfile(uN);
			display(ap);	
		}
	}
}
