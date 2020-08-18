/*
 * Created on Mar 12, 2007
 *
 * Author: dgold1
 * 
 * Revisions:
 * 
 */
package com.sears.os.event;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sears.os.vo.ABaseVO;

public class EventResponse extends ABaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String processId;
	String code;
	String subCode;
	String[] messages;

	public String toString() {
		return new ToStringBuilder(this)
			.append("processId", getProcessId())
			.append("code", getCode())
			.append("subCode", getSubCode())
			.append("messages", getMessages())
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
