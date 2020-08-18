package com.servicelive.orderfulfillment.vo;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This is a bean class for storing request information for the In Home Out
 * bound Notification Service
 * 
 * @author Infosys
 */
@XStreamAlias("sendMsgData")
public class SendMsgData {

	@XStreamAlias("fromFunction")
	private String fromFunction;

	@XStreamAlias("toFunction")
	private String toFunction;

	@XStreamAlias("empID")
	private String empID;

	@XStreamAlias("Message")
	private String Message;

	public String getFromFunction() {
		return fromFunction;
	}

	public void setFromFunction(String fromFunction) {
		this.fromFunction = fromFunction;
	}

	public String getToFunction() {
		return toFunction;
	}

	public void setToFunction(String toFunction) {
		this.toFunction = toFunction;
	}

	public String getEmpID() {
		return empID;
	}

	public void setEmpID(String empID) {
		this.empID = empID;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}
}
