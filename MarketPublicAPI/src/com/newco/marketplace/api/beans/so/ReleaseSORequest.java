/**
 * 
 */
package com.newco.marketplace.api.beans.so;

import com.newco.marketplace.api.annotation.XSD;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @author ndixit
 *
 */
@XSD(name="releaseSORequest.xsd", path="/resources/schemas/so/")
@XStreamAlias("releaseSORequest")
public class ReleaseSORequest {
	
	@XStreamAlias("statusId")
	private Integer statusId;
	
	@XStreamAlias("comment")
	private String comment;
	
	@XStreamAlias("soId")
	private Integer soId;

	@XStreamAlias("reasonCode")
	private Integer reasonCode;
	
	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getSoId() {
		return soId;
	}

	public void setSoId(Integer soId) {
		this.soId = soId;
	}

	public Integer getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(Integer reasonCode) {
		this.reasonCode = reasonCode;
	}

}
