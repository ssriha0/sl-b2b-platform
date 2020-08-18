package com.newco.marketplace.api.beans.so;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
@XStreamAlias("fileNames")
public class FileNames {
		
	@XStreamAlias("video")
	@XStreamAsAttribute() 
	private String video;
	
	@XStreamAlias("file")
	private String file;
	
	@XStreamAlias("videoUrl")
	private String videoUrl;

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	
	
}
