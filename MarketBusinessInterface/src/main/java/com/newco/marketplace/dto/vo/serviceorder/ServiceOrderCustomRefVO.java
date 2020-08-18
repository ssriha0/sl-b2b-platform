package com.newco.marketplace.dto.vo.serviceorder;

import java.sql.Date;

import com.sears.os.vo.SerializableBaseVO;


public class ServiceOrderCustomRefVO extends SerializableBaseVO implements Comparable<ServiceOrderCustomRefVO> {

	private static final long serialVersionUID = -3633307763004253050L;
	private String soId;
	private Integer soStateId;
	private Integer refTypeId;
	private String refValue;
	private String refType;
	private Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	private String pdfRefInd;
	private boolean privateInd;
	private Integer editable;
	private String requiredInd;
	
	public String getRequiredInd() {
		return requiredInd;
	}

	public void setRequiredInd(String requiredInd) {
		this.requiredInd = requiredInd;
	}

	public Integer getEditable() {
		return editable;
	}

	public void setEditable(Integer editable) {
		this.editable = editable;
	}

	public boolean isPrivateInd() {
		return privateInd;
	}

	public void setPrivateInd(boolean privateInd) {
		this.privateInd = privateInd;
	}

	public String getRefType() {
		return refType;
	}

	public void setRefType(String refType) {
		this.refType = refType;
	}

	public String getsoId() {
		return soId;
	}

	public void setsoId(String soId) {
		this.soId = soId;
	}
	public Integer getRefTypeId() {
		return refTypeId;
	}

	public void setRefTypeId(Integer refTypeId) {
		this.refTypeId = refTypeId;
	}

	public String getRefValue() {
		if (refValue != null) {
			refValue = refValue.trim();
		}
		return refValue;
	}

	public void setRefValue(String refValue) {
		this.refValue = refValue;
	}

	public int compareTo(ServiceOrderCustomRefVO o) {
		if (!o.getComparableString().equals(this.getComparableString())) {
			return 1;
		}
		return 0;
	}
	
	public String getComparableString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getRefType() != null ? getRefType() : "");
		sb.append(getRefTypeId() != null ? Integer.toString(getRefTypeId().intValue()) : "");
		sb.append(getRefValue() != null ? getRefValue() : "");
		return sb.toString();
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Integer getSoStateId() {
		return soStateId;
	}

	public void setSoStateId(Integer soStateId) {
		this.soStateId = soStateId;
	}

	public String getPdfRefInd() {
		return pdfRefInd;
	}

	public void setPdfRefInd(String pdfRefInd) {
		this.pdfRefInd = pdfRefInd;
	}


}
