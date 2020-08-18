package com.newco.marketplace.api.beans.downloadsignedcopy;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("downLoadResponse")
@XmlRootElement(name = "downLoadResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class DownloadSODocumentsResponse{
	
	@XStreamAlias("results")
	@XmlElement(name="results")
	private DownloadResults results;
    
	@XStreamOmitField
	private List<SODocumentsResult>soDocumentsResultList;
	
	@XStreamOmitField
	private String[] srcFiles;
	
	@XStreamOmitField
	private String soId;
	
	@XStreamOmitField
	private String validSoId;
	
	@XStreamOmitField
	private Map<String,List<SODocumentsResult>> resultMap;
	
	@XStreamOmitField
	private long totalFileSizeInKB;
	
	
	public List<SODocumentsResult> getSoDocumentsResultList() {
		return soDocumentsResultList;
	}

	public void setSoDocumentsResultList(
			List<SODocumentsResult> soDocumentsResultList) {
		this.soDocumentsResultList = soDocumentsResultList;
	}

	public void setSrcFiles(String[] srcFiles) {
		this.srcFiles = srcFiles;
	}
	
	public DownloadResults getResults() {
		return results;
	}

	public void setResults(DownloadResults results) {
		this.results = results;
	}
	
	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public Map<String, List<SODocumentsResult>> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, List<SODocumentsResult>> resultMap) {
		this.resultMap = resultMap;
	}
	
	
	public String getValidSoId() {
		return validSoId;
	}

	public void setValidSoId(String validSoId) {
		this.validSoId = validSoId;
	}

	public String[] getSrcFiles() {
		return srcFiles;
	}
	public long getTotalFileSizeInKB() {
		return totalFileSizeInKB;
	}
	public void setTotalFileSizeInKB(long totalFileSizeInKB) {
		this.totalFileSizeInKB = totalFileSizeInKB;
	}
	
}