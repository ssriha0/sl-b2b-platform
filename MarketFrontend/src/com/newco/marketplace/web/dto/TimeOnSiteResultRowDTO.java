package com.newco.marketplace.web.dto;

import java.util.Date;



public class TimeOnSiteResultRowDTO extends SerializedBaseDTO{

	private static final long serialVersionUID = -7681921019443444183L;
	private Long visitId;
	private String soId;
	private Integer roleId;
	private String arrivalEnteredType;
	private String departureEnteredType;
	private String arrivalDate;
	private String departureDate;
	private String arrivalTime;
	private String departureTime;
	private Date arrivalDateCal;
	private Date departureDateCal;
	
	
	
	
	
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalEnteredType() {
		return arrivalEnteredType;
	}
	public void setArrivalEnteredType(String arrivalEnteredType) {
		this.arrivalEnteredType = arrivalEnteredType;
	}
	public String getDepartureEnteredType() {
		return departureEnteredType;
	}
	public void setDepartureEnteredType(String departureEnteredType) {
		this.departureEnteredType = departureEnteredType;
	}
	public String getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getDepartureDate() {
		return departureDate;
	}
	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}
	
	public Date getArrivalDateCal() {
		return arrivalDateCal;
	}
	public void setArrivalDateCal(Date arrivalDateCal) {
		this.arrivalDateCal = arrivalDateCal;
	}
	public Date getDepartureDateCal() {
		return departureDateCal;
	}
	public void setDepartureDateCal(Date departureDateCal) {
		this.departureDateCal = departureDateCal;
	}
	public Long getVisitId() {
		return visitId;
	}
	public void setVisitId(Long visitId) {
		this.visitId = visitId;
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
	
	
}