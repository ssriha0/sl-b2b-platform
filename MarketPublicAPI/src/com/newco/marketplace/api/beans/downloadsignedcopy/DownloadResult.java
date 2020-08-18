package com.newco.marketplace.api.beans.downloadsignedcopy;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * This is a generic bean class for storing Result information.
 * @author Infosys
 *
 */
@XStreamAlias("error")
public class DownloadResult {

	@XStreamAlias("code")
	private String code;

	@XStreamAlias("message")
	private String message;
	
	@XStreamOmitField
	private String soId;
	
    public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	
}
