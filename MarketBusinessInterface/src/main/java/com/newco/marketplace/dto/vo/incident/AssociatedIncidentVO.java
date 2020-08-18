package com.newco.marketplace.dto.vo.incident;

import java.sql.Timestamp;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.sears.os.vo.SerializableBaseVO;

public class AssociatedIncidentVO extends SerializableBaseVO {
	
	private static final long serialVersionUID = 598674940772310759L;
	
	private String soId;
	private String incidentId;
	private Integer buyerId;
	private Integer buyerRefTypeId;
	private String refType;
	private Integer ageOfOrder;
	
	private String clientIncidentId;
	private Integer buyerSubstatusAssocId;
	private Timestamp responseSentDate;
	private String outputFile;
	
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public String getIncidentId() {
		return incidentId;
	}
	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getBuyerRefTypeId() {
		return buyerRefTypeId;
	}
	public void setBuyerRefTypeId(Integer buyerRefTypeId) {
		this.buyerRefTypeId = buyerRefTypeId;
	}
	public String getRefType() {
		return refType;
	}
	public void setRefType(String refType) {
		this.refType = refType;
	}
	public Integer getAgeOfOrder() {
		return ageOfOrder;
	}
	public void setAgeOfOrder(Integer ageOfOrder) {
		this.ageOfOrder = ageOfOrder;
	}
	public Integer getBuyerSubstatusAssocId() {
		return buyerSubstatusAssocId;
	}
	public void setBuyerSubstatusAssocId(Integer buyerSubstatusAssocId) {
		this.buyerSubstatusAssocId = buyerSubstatusAssocId;
	}
	public Timestamp getResponseSentDate() {
		return responseSentDate;
	}
	public void setResponseSentDate(Timestamp responseSentDate) {
		this.responseSentDate = responseSentDate;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}
	public String getClientIncidentId() {
		return clientIncidentId;
	}
	public void setClientIncidentId(String clientIncidentId) {
		this.clientIncidentId = clientIncidentId;
	}
	public String getOutputFile() {
		return outputFile;
	}
	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}
}
