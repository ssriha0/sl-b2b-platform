package com.newco.marketplace.dto.vo.ach;

import java.sql.Timestamp;

import com.sears.os.vo.SerializableBaseVO;

public class NachaProcessLogHistoryVO extends SerializableBaseVO{
	private long achProcessLogHistoryId;
	private long achProcessLogId; 
	private long achProcessStatusId; 
	private String comments; 
	private Timestamp updatedDate; 
	private String updatedBy; 
	public long getAchProcessLogHistoryId() {
		return achProcessLogHistoryId;
	}
	public void setAchProcessLogHistoryId(long achProcessLogHistoryId) {
		this.achProcessLogHistoryId = achProcessLogHistoryId;
	}
	public long getAchProcessLogId() {
		return achProcessLogId;
	}
	public void setAchProcessLogId(long achProcessLogId) {
		this.achProcessLogId = achProcessLogId;
	}
	public long getAchProcessStatusId() {
		return achProcessStatusId;
	}
	public void setAchProcessStatusId(long achProcessStatusId) {
		this.achProcessStatusId = achProcessStatusId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Timestamp getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
