package com.newco.marketplace.dto.vo.so.order;

import java.sql.Timestamp;
import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;
import com.thoughtworks.xstream.annotations.XStreamAlias;


/**
 * @author zizrale
 *
 */
public class ServiceOrderRescheduleVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2170983298487378635L;
	private String soId;
	private String scheduleType; //fixed vs range
	private Date oldDateStart;
	private Date newDateStart;
	private String oldTimeStart;
	private String newTimeStart;
	private Date oldDateEnd;
	private Date newDateEnd;
	private String oldTimeEnd;
	private String newTimeEnd;
	private Integer userRole; // buyer vs provider
	private Integer userId;
	private Integer wfStateId;
	private Integer wfSubStateId;
	private Integer lastWfStateId;
	private Timestamp lastStatusChange;
	//Added for SL-15642
	private String reasonCode;
	private String comments;
	
	public Timestamp getLastStatusChange() {
		return lastStatusChange;
	}
	public void setLastStatusChange(Timestamp lastStatusChange) {
		this.lastStatusChange = lastStatusChange;
	}
	public Integer getLastWfStateId() {
		return lastWfStateId;
	}
	public void setLastWfStateId(Integer lastWfStateId) {
		this.lastWfStateId = lastWfStateId;
	}
	public Integer getWfStateId() {
		return wfStateId;
	}
	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}
	/** @return Integer userId*/
	public Integer getUserId() {
		return userId;
	}
	/** @param usrId */
	public void setUserId(Integer usrId) {
		this.userId = usrId;
	}
	/** @return Integer userRole */
	public Integer getUserRole() {
		return userRole;
	}
	/** @param userRoleType */
	public void setUserRole(Integer userRoleType) {
		this.userRole = userRoleType;
	}
	/** @return Date newDateEnd */
	public Date getNewDateEnd() {
		return newDateEnd;
	}
	/** @param newDateStop */
	public void setNewDateEnd(Date newDateStop) {
		this.newDateEnd = newDateStop;
	}
	/** @return Date newDateStart */
	public Date getNewDateStart() {
		return newDateStart;
	}
	/** @param newDateBegin */
	public void setNewDateStart(Date newDateBegin) {
		this.newDateStart = newDateBegin;
	}
	/** @return String newTimeEnd */
	public String getNewTimeEnd() {
		return newTimeEnd;
	}
	/** @param newTimeStop */
	public void setNewTimeEnd(String newTimeStop) {
		this.newTimeEnd = newTimeStop;
	}
	/** @return String newTimeStart */
	public String getNewTimeStart() {
		return newTimeStart;
	}
	/** @param newTimeBegin */
	public void setNewTimeStart(String newTimeBegin) {
		this.newTimeStart = newTimeBegin;
	}
	/** @return Date oldDateEnd */
	public Date getOldDateEnd() {
		return oldDateEnd;
	}
	/** @param oldDateStop */
	public void setOldDateEnd(Date oldDateStop) {
		this.oldDateEnd = oldDateStop;
	}
	/** @return Date oldDateStart */
	public Date getOldDateStart() {
		return oldDateStart;
	}
	/** @param oldDateBegin */
	public void setOldDateStart(Date oldDateBegin) {
		this.oldDateStart = oldDateBegin;
	}
	/** @return String oldTimeEnd */
	public String getOldTimeEnd() {
		return oldTimeEnd;
	}
	/** @param oldTimeStop */
	public void setOldTimeEnd(String oldTimeStop) {
		this.oldTimeEnd = oldTimeStop;
	}
	/** @return String oldTimeStart */
	public String getOldTimeStart() {
		return oldTimeStart;
	}
	/** @param oldTimeBegin */
	public void setOldTimeStart(String oldTimeBegin) {
		this.oldTimeStart = oldTimeBegin;
	}
	/** @return String */
	public String getScheduleType() {
		return scheduleType;
	}
	/** @param schedType */
	public void setScheduleType(String schedType) {
		this.scheduleType = schedType;
	}
	/** @return String */
	public String getSoId() {
		return soId;
	}
	/** @param servOrdId */
	public void setSoId(String servOrdId) {
		this.soId = servOrdId;
	}
	public Integer getWfSubStateId() {
		return wfSubStateId;
	}
	public void setWfSubStateId(Integer wfSubStateId) {
		this.wfSubStateId = wfSubStateId;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
}
