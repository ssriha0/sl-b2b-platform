package com.newco.marketplace.vo.audit;

import java.util.Date;

import com.sears.os.vo.SerializableBaseVO;


public class AuditTimeVO extends SerializableBaseVO{

	private static final long serialVersionUID = 4503416570584017404L;
	
	private int auditTimeLoggingId = -1;

	private int auditTaskId = -1;
   
    private Integer agentId = null;
    
    private Date startTime;
    
    private Date endTime;
    
    private Integer updatedInd;
    
    private String agentName;
    
    private Integer credId;
    private String startAction;
    private String endAction;

    
    
	public Integer getCredId() {
		return credId;
	}

	public void setCredId(Integer credId) {
		this.credId = credId;
	}

	public String getStartAction() {
		return startAction;
	}

	public void setStartAction(String startAction) {
		this.startAction = startAction;
	}

	public String getEndAction() {
		return endAction;
	}

	public void setEndAction(String endAction) {
		this.endAction = endAction;
	}

	public int getAuditTimeLoggingId() {
		return auditTimeLoggingId;
	}

	public void setAuditTimeLoggingId(int auditTimeLoggingId) {
		this.auditTimeLoggingId = auditTimeLoggingId;
	}

	public int getAuditTaskId() {
		return auditTaskId;
	}

	public void setAuditTaskId(int auditTaskId) {
		this.auditTaskId = auditTaskId;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getUpdatedInd() {
		return updatedInd;
	}

	public void setUpdatedInd(Integer updatedInd) {
		this.updatedInd = updatedInd;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
  
	
   
}