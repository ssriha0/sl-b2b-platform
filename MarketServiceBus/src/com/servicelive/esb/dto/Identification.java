package com.servicelive.esb.dto;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("identification")
public class Identification implements Serializable {
	
	/** generated serialVersionUID */
	private static final long serialVersionUID = -3243491249308485331L;

	@XStreamAlias("accesskey")
	private String accessKey;
	
	@XStreamAlias("password")
	private String passWord;
	
	@XStreamAlias("apiVersion")
	private String apiVersion;

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}
	
	public String toString() {
		return "accessKey:" + accessKey + " :apiVersion" + apiVersion;
	}
}
