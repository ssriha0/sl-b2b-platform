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
public class Attachments {
	
	@XStreamImplicit(itemFieldName="filename")
	private List<String> filenameList;

	public List<String> getFilenameList() {
		return filenameList;
	}

	public void setFilenameList(List<String> filenameList) {
		this.filenameList = filenameList;
	}





}
