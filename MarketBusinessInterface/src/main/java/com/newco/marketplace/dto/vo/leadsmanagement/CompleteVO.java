package com.newco.marketplace.dto.vo.leadsmanagement;

import java.util.Date;

public class CompleteVO {
private Integer firmId;
private String leadId;
private Date completedDate;
private int noOfCompletedDate;

public Integer getFirmId() {
	return firmId;
}
public void setFirmId(Integer firmId) {
	this.firmId = firmId;
}
public String getLeadId() {
	return leadId;
}
public void setLeadId(String leadId) {
	this.leadId = leadId;
}
public Date getCompletedDate() {
	return completedDate;
}
public void setCompletedDate(Date completedDate) {
	this.completedDate = completedDate;
}
public int getNoOfCompletedDate() {
	return noOfCompletedDate;
}
public void setNoOfCompletedDate(int noOfCompletedDate) {
	this.noOfCompletedDate = noOfCompletedDate;
}
}
