package com.newco.marketplace.cheetahmail;

import java.util.Map;

import com.newco.marketplace.email.EmailService;

public class CheetahEmailServiceStub implements EmailService {

	private static final long serialVersionUID = 1L;

	public boolean  send(String eid, Map<String, String> parameters) {
        //purposely do nothing, since this is a stub
		return true;
    }

	public void addEmailProperty(String key, String value) {
		// TODO Auto-generated method stub
		
	}

}
