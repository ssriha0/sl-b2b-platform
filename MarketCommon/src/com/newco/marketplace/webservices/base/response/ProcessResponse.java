package com.newco.marketplace.webservices.base.response;

import java.util.ArrayList;
import java.util.List;

import com.sears.os.service.ServiceConstants;

public class ProcessResponse extends ABaseResponse implements ServiceConstants {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6607620175191601386L;
	private Object obj;
	
	public ProcessResponse() {
		super();
	}
	
	public ProcessResponse(String code, String subCode, ArrayList<String> messages, List<String> warnings) {
		super(code, subCode, messages, warnings);
	}
	
	public ProcessResponse(String code, String subCode) {
		super(code, subCode);
	}
	
	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
}
