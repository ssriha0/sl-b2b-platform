package com.newco.marketplace.webservices.base.response;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.webservices.base.CommonVO;
import com.sears.os.service.ServiceConstants;

public abstract class ABaseResponse extends CommonVO implements ServiceConstants {
	
	private String code;
	private String subCode;
	private List<String> messages;
	private List<String> warnings;

	public ABaseResponse() {
	}

	public ABaseResponse(String code, String subCode, List<String> messages, List<String> warnings) {
		setCode(code);
		setSubCode(subCode);
		setMessages(messages);
		setWarnings(warnings);
	}
	
	public ABaseResponse(String code, String subCode) {
		setCode(code);
		setSubCode(subCode);
		setMessages(new ArrayList<String>());
		setWarnings(new ArrayList<String>());
	}
	
	public String getCode() {
		if(code == null)
			code = "";
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<String> getMessages() {
		if(messages == null) {
			messages = new ArrayList<String>();
		}
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public String getMessage(){
        StringBuffer sb = new StringBuffer();
        for(String m : messages)
            sb.append(m).append("\n");
        return sb.toString();
    }

    public void setMessage(String message) {
		getMessages().add(message);
	}
	
	public void appendMessages(List<String> messages)
	{
		getMessages().addAll(messages);
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}
	
	public boolean isSuccess() {
		return	(getCode().equals(ServiceConstants.VALID_RC) ? true : false);
	}
	
	public boolean isError() {
		return	!isSuccess();
	}

	public List<String> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<String> warnings) {
		this.warnings = warnings;
	}
	
	
}
