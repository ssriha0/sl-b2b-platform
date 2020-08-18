package com.newco.marketplace.vo.calendarPortal;

import com.sears.os.vo.SerializableBaseVO;

public class CalendarProviderVO extends SerializableBaseVO {

	private static final long serialVersionUID = 1L;
	
	private Integer personId;
	private String personFirstName;
	private String personLastName;
	private Integer personPrimaryInd;
	private Integer personAdminInd;
	private Integer personWFStateId;
	private String personWFState;
	
	public String getPersonFirstName() {
		return personFirstName;
	}

	public void setPersonFirstName(String personFirstName) {
		this.personFirstName = personFirstName;
	}

	public String getPersonLastName() {
		return personLastName;
	}

	public void setPersonLastName(String personLastName) {
		this.personLastName = personLastName;
	}

	public Integer getPersonPrimaryInd() {
		return personPrimaryInd;
	}

	public void setPersonPrimaryInd(Integer personPrimaryInd) {
		this.personPrimaryInd = personPrimaryInd;
	}

	public Integer getPersonAdminInd() {
		return personAdminInd;
	}

	public void setPersonAdminInd(Integer personAdminInd) {
		this.personAdminInd = personAdminInd;
	}

	public Integer getPersonWFStateId() {
		return personWFStateId;
	}

	public void setPersonWFStateId(Integer personWFStateId) {
		this.personWFStateId = personWFStateId;
	}

	public String getPersonWFState() {
		return personWFState;
	}

	public void setPersonWFState(String personWFState) {
		this.personWFState = personWFState;
	}

	public Integer getPersonId() {
		return personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}
}
