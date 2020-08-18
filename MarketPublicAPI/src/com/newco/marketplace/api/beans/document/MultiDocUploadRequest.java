package com.newco.marketplace.api.beans.document;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("uploadRequest")
public class MultiDocUploadRequest {
		
	@XStreamAlias("files")
	private RequestFiles files;

	public RequestFiles getFiles() {
		return files;
	} 

	public void setFiles(RequestFiles files) {
		this.files = files;
	}	
				
}
