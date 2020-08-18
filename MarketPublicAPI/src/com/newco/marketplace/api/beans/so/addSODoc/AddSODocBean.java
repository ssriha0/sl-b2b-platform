package com.newco.marketplace.api.beans.so.addSODoc;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class AddSODocBean {
	
	@XStreamAlias("fileName")
	private String fileName;
	
	@XStreamAlias("description")
	private String description;
	
	@XStreamAlias("video")
	private boolean video;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isVideo() {
		return video;
	}

	public void setVideo(boolean video) {
		this.video = video;
	}

	
}
