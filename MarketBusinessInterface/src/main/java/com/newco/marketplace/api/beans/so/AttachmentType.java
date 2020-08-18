package com.newco.marketplace.api.beans.so;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * This is a generic bean class for storing attachment information.
 * @author Infosys
 *
 */
@XStreamAlias("attachments")
public class AttachmentType {
	
	
	@XStreamImplicit(itemFieldName="fileNames")
	private List<FileNames> fileNames;

	public List<FileNames> getFileNames() {
		return fileNames;
	}

	public void setFileNames(List<FileNames> fileNames) {
		this.fileNames = fileNames;
	}


}
