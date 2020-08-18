package com.newco.marketplace.api.beans.b2c;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("downLoadResponse")
@XmlRootElement(name = "downLoadResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class DownLoadResponse{
	
	@XStreamAlias("results")
	@XmlElement(name="results")
	private DownloadResults results;

	public DownloadResults getResults() {
		return results;
	}

	public void setResults(DownloadResults results) {
		this.results = results;
	}

	
}