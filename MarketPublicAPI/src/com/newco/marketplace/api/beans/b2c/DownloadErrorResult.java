package com.newco.marketplace.api.beans.b2c;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * This is a generic bean class for storing Result information.
 * @author Infosys
 *
 */
@XStreamAlias("error")
public class DownloadErrorResult {

	@XStreamAlias("code")
	private String code;

	@XStreamAlias("message")
	private String message;
	
	@XStreamOmitField
	private String filePath;

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

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
