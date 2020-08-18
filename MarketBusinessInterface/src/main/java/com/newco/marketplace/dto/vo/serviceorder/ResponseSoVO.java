package com.newco.marketplace.dto.vo.serviceorder;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sears.os.vo.request.ABaseServiceRequestVO;
public class ResponseSoVO extends ABaseServiceRequestVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7431951436182769530L;
	private int responseId = 0;
	private int reasonId = 0;
	private int resourceId = 0;
	private String soId = "";
	private String modifiedBy = "";
	private Date responseDate = null;
	private String reasonDesc = "";
	
	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public int getResponseId() {
		return responseId;
	}

	public void setResponseId(int responseId) {
		this.responseId = responseId;
	}

	public int getReasonId() {
		return reasonId;
	}

	public void setReasonId(int reasonId) {
		this.reasonId = reasonId;
	}

	public int getResourceId() {
		return resourceId;
	}

	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public String getReasonDesc() {
		return reasonDesc;
	}

	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
			.append("responseId", getResponseId())
			.append("reasonId", getReasonId())
			.append("soId", getSoId())
			.append("resourceId", getResourceId())
			.append("modifiedBy", getModifiedBy())
			.append("reasonDesc", getReasonDesc())
			.toString();
	}

}
