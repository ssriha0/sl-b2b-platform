package com.newco.marketplace.api.beans.document;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
@XStreamAlias("files")
public class ResponseFiles{
	
	@XStreamImplicit(itemFieldName="file")
	private List<ResponseFile> file;

	public List<ResponseFile> getFile() {
		return file;
	}

	public void setFile(List<ResponseFile> file) {
		this.file = file;
	}
	 	
	
}
