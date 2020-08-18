package com.newco.marketplace.business.businessImpl.alert;

public class AlertDisposition {
	private String actionName;
	private String alertFrom;
	private String alertTo;
	private String alertCc; 
	private String alertBcc;
	private String role;
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getAlertFrom() {
		return alertFrom;
	}
	public void setAlertFrom(String alertFrom) {
		this.alertFrom = alertFrom;
	}
	public String getAlertTo() {
		return alertTo;
	}
	public void setAlertTo(String alertTo) {
		this.alertTo = alertTo;
	}
	public String getAlertCc() {
		return alertCc;
	}
	public void setAlertCc(String alertCc) {
		this.alertCc = alertCc;
	}
	public String getAlertBcc() {
		return alertBcc;
	}
	public void setAlertBcc(String alertBcc) {
		this.alertBcc = alertBcc;
	}
	
@Override
public String toString(){
	StringBuffer sb = new StringBuffer();
	sb.append(actionName);
	sb.append("[ AlertFrom: "+alertFrom);
	sb.append(", AlertTo: "+alertTo);
	sb.append(", AlertCc: "+alertCc);
	sb.append(", AlertBcc: "+alertBcc);
	sb.append(" ]");
	return sb.toString();
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}

}
