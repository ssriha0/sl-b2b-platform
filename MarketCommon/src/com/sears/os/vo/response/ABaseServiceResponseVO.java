package com.sears.os.vo.response;

import org.apache.commons.lang.builder.ToStringBuilder;
import com.sears.os.service.ServiceConstants;
import com.sears.os.vo.ABaseVO;

public abstract class ABaseServiceResponseVO extends ABaseVO implements ServiceConstants {

	String processId;
	String code = VALID_RC;
	String subCode = VALID_RC;
	String[] messages;
	
	public String toString() {
		return new ToStringBuilder(this)
			.append("processId", processId)
			.append("code", code)
			.append("subCode", subCode)
			.append("messages", messages)
			.toString();
	}

	/**
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return
	 */
	public String[] getMessages() {
		return messages;
	}

	/**
	 * @return
	 */
	public String getProcessId() {
		return processId;
	}

	/**
	 * @return
	 */
	public String getSubCode() {
		return subCode;
	}

	/**
	 * @param string
	 */
	public void setCode(String string) {
		code = string;
	}

	/**
	 * @param strings
	 */
	public void setMessages(String[] strings) {
		messages = strings;
	}

	/**
	 * @param string
	 */
	public void setProcessId(String string) {
		processId = string;
	}

	/**
	 * @param string
	 */
	public void setSubCode(String string) {
		subCode = string;
	}
}
