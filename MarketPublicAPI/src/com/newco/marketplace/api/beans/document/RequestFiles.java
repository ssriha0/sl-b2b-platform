package com.newco.marketplace.api.beans.document;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("files")
public class RequestFiles {


	@XStreamImplicit(itemFieldName="file")
	private List<RequestFile> file;

	public List<RequestFile> getFile() {
		return file;
	}

	public void setFile(List<RequestFile> file) {
		this.file = file;
	}

	
	
}
