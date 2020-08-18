package com.newco.marketplace.web.dto;

public class TimeOnSiteResultDTO extends SerializedBaseDTO{
	
	private static final long serialVersionUID = 1L;
	private Long tripNum;
	private String soId;
	private Long visitId;
	private Integer roleId;
	private String apptDateTimeEntry;
	private String arrivalEntry;
	private String departureEntry;
	private String departureReason;
	private boolean addDepartureInd = false;
	private String arrivalDate;
	
	public Long getTripNum() {
		return tripNum;
	}
	public void setTripNum(Long tripNum) {
		this.tripNum = tripNum;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getApptDateTimeEntry() {
		return apptDateTimeEntry;
	}
	public void setApptDateTimeEntry(String apptDateTimeEntry) {
		this.apptDateTimeEntry = apptDateTimeEntry;
	}
	public String getArrivalEntry() {
		return arrivalEntry;
	}
	public void setArrivalEntry(String arrivalEntry) {
		this.arrivalEntry = arrivalEntry;
	}
	public String getDepartureEntry() {
		return departureEntry;
	}
	public void setDepartureEntry(String departureEntry) {
		this.departureEntry = departureEntry;
	}
	public String getDepartureReason() {
		return departureReason;
	}
	public void setDepartureReason(String departureReason) {
		this.departureReason = departureReason;
	}
	public boolean isAddDepartureInd() {
		return addDepartureInd;
	}
	public void setAddDepartureInd(boolean addDepartureInd) {
		this.addDepartureInd = addDepartureInd;
	}
	public String getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public Long getVisitId() {
		return visitId;
	}
	public void setVisitId(Long visitId) {
		this.visitId = visitId;
	}
	
}