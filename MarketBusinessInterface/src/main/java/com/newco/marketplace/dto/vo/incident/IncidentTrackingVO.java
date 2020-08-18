package com.newco.marketplace.dto.vo.incident;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.sears.os.vo.SerializableBaseVO;

public class IncidentTrackingVO extends SerializableBaseVO {
	
	private static final long serialVersionUID = -8672193243368359754L;
	
	private Integer id;
	private String soId;
	private Integer incidentId;
	private Integer buyerSubstatusAssocId;
	private String buyerSubstatusDesc;
	private String outputFile;
	private Timestamp responseSentDate;
	private Integer incidentAckId;
	private Integer clientId;
	private String clientIncidentId;
	
	public Integer getIncidentAckId() {
		return incidentAckId;
	}
	public void setIncidentAckId(Integer incidentAckId) {
		this.incidentAckId = incidentAckId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public Integer getIncidentId() {
		return incidentId;
	}
	public void setIncidentId(Integer incidentId) {
		this.incidentId = incidentId;
	}
	public Integer getBuyerSubstatusAssocId() {
		return buyerSubstatusAssocId;
	}
	public void setBuyerSubstatusAssocId(Integer buyerSubstatusAssocId) {
		this.buyerSubstatusAssocId = buyerSubstatusAssocId;
	}
	public String getOutputFile() {
		return outputFile;
	}
	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}
	public Timestamp getResponseSentDate() {
		return responseSentDate;
	}
	public void setResponseSentDate(Timestamp responseSentDate) {
		this.responseSentDate = responseSentDate;
	}
	public String getBuyerSubstatusDesc() {
		return buyerSubstatusDesc;
	}
	public void setBuyerSubstatusDesc(String buyerSubstatusDesc) {
		this.buyerSubstatusDesc = buyerSubstatusDesc;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}
	public Integer getClientId() {
		return clientId;
	}
	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}
	public String getClientIncidentId() {
		return clientIncidentId;
	}
	public void setClientIncidentId(String clientIncidentId) {
		this.clientIncidentId = clientIncidentId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
