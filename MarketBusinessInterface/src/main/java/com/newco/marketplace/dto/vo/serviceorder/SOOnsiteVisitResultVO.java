/**
 * 
 */
package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Timestamp;
import java.util.Date;

import com.newco.marketplace.webservices.base.CommonVO;

/**
 * @author Infosys
 *
 */
public class SOOnsiteVisitResultVO extends CommonVO{
	
	private static final long serialVersionUID = 1L;
	
	public SOOnsiteVisitResultVO() {}

	private Long tripNum;
	private String soId;
	private Integer roleId;
	private Timestamp apptStartDate;
	private Timestamp apptEndDate;
	private String apptStartTime;
	private String apptEndTime;
	private Date arrivalDateTime;
	private String tripStartSource;
	private Integer arrivalResourceId;
	private Date departureDateTime;
	private String tripEndSource;
	private Integer departureResourceId;
	private String departureReason;
	private String resName;
	private Long checkInVisitId;
	private Date createdDate;

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
	public Timestamp getApptStartDate() {
		return apptStartDate;
	}
	public void setApptStartDate(Timestamp apptStartDate) {
		this.apptStartDate = apptStartDate;
	}
	public Timestamp getApptEndDate() {
		return apptEndDate;
	}
	public void setApptEndDate(Timestamp apptEndDate) {
		this.apptEndDate = apptEndDate;
	}
	public String getApptStartTime() {
		return apptStartTime;
	}
	public void setApptStartTime(String apptStartTime) {
		this.apptStartTime = apptStartTime;
	}
	public String getApptEndTime() {
		return apptEndTime;
	}
	public void setApptEndTime(String apptEndTime) {
		this.apptEndTime = apptEndTime;
	}
	public Date getArrivalDateTime() {
		return arrivalDateTime;
	}
	public void setArrivalDateTime(Date arrivalDateTime) {
		this.arrivalDateTime = arrivalDateTime;
	}
	public String getTripStartSource() {
		return tripStartSource;
	}
	public void setTripStartSource(String tripStartSource) {
		this.tripStartSource = tripStartSource;
	}
	public Integer getArrivalResourceId() {
		return arrivalResourceId;
	}
	public void setArrivalResourceId(Integer arrivalResourceId) {
		this.arrivalResourceId = arrivalResourceId;
	}
	public Date getDepartureDateTime() {
		return departureDateTime;
	}
	public void setDepartureDateTime(Date departureDateTime) {
		this.departureDateTime = departureDateTime;
	}
	public String getTripEndSource() {
		return tripEndSource;
	}
	public void setTripEndSource(String tripEndSource) {
		this.tripEndSource = tripEndSource;
	}
	public Integer getDepartureResourceId() {
		return departureResourceId;
	}
	public void setDepartureResourceId(Integer departureResourceId) {
		this.departureResourceId = departureResourceId;
	}
	public String getDepartureReason() {
		return departureReason;
	}
	public void setDepartureReason(String departureReason) {
		this.departureReason = departureReason;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public Long getCheckInVisitId() {
		return checkInVisitId;
	}
	public void setCheckInVisitId(Long checkInVisitId) {
		this.checkInVisitId = checkInVisitId;
	}
	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
}
