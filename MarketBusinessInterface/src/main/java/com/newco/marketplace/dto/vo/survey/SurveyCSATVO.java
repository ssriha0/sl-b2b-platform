package com.newco.marketplace.dto.vo.survey;

import com.sears.os.vo.SerializableBaseVO;

public class SurveyCSATVO  extends SerializableBaseVO{
	private static final long serialVersionUID = 1L;
	
	private String serviceOrderID;
	private String takenDate;
	private String q8result;
	public String getServiceOrderID() {
		return serviceOrderID;
	}
	public void setServiceOrderID(String serviceOrderID) {
		this.serviceOrderID = serviceOrderID;
	}
	public String getTakenDate() {
		return takenDate;
	}
	public void setTakenDate(String takenDate) {
		this.takenDate = takenDate;
	}
	public String getQ8result() {
		return q8result;
	}
	public void setQ8result(String q8result) {
		this.q8result = q8result;
	}
}
