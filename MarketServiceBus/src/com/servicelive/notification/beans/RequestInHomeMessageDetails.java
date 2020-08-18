package com.servicelive.notification.beans;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * This is a bean class for storing request information for 
 * the In Home Out bound Notification Service
 * @author Infosys
 */
@XStreamAlias("SendMessageRequest")
public class RequestInHomeMessageDetails {
	
	@XStreamAlias("CorrelationId")
	private String correlationId;
	
	@XStreamAlias("orderType")
	private String orderType;
	
	@XStreamAlias("orderNum")
	private String orderNum;
	
	@XStreamAlias("unitNum")
	private String unitNum;
	
    @XStreamAlias("fromFunction")
	private String fromFunction;
	
	@XStreamAlias("toFunction")
	private String toFunction;
	
	@XStreamAlias("empid")
	private String empId;
	
	@XStreamAlias("sendMessage")
	private String Message;

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
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

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	public String getUnitNum() {
		return unitNum;
	}

	public void setUnitNum(String unitNum) {
		this.unitNum = unitNum;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}


}
