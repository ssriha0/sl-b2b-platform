package com.newco.marketplace.api.beans.downloadsignedcopy;

import java.math.BigDecimal;
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
public class DownloadSignedCopyResponse{
	
	@XStreamAlias("results")
	@XmlElement(name="results")
	private DownloadResults results;
    
	@XStreamOmitField
	private List<SignedCopyResult>signedCopyResultList;
	
	@XStreamOmitField
	private String[] srcFiles;
	
	@XStreamOmitField
	private List<String> soIdList;
	
	@XStreamOmitField
	private List<String> validSoIdList;
	
	@XStreamOmitField
	private Map<String,List<SignedCopyResult>> resultMap;
	
	@XStreamOmitField
	private long totalFileSizeInKB;
	
	public List<SignedCopyResult> getSignedCopyResultList() {
		return signedCopyResultList;
	}
	public void setSignedCopyResultList(List<SignedCopyResult> signedCopyResultList) {
		this.signedCopyResultList = signedCopyResultList;
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
	public List<String> getSoIdList() {
		return soIdList;
	}
	public void setSoIdList(List<String> soIdList) {
		this.soIdList = soIdList;
	}
	public Map<String, List<SignedCopyResult>> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, List<SignedCopyResult>> resultMap) {
		this.resultMap = resultMap;
	}
	public List<String> getValidSoIdList() {
		return validSoIdList;
	}
	public void setValidSoIdList(List<String> validSoIdList) {
		this.validSoIdList = validSoIdList;
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