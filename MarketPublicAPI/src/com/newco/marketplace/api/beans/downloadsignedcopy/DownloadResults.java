package com.newco.marketplace.api.beans.downloadsignedcopy;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("results")
public class DownloadResults {
	
	@XStreamAlias("error")
	@XStreamImplicit(itemFieldName="error")
	private List<DownloadResult> results;
	
	@XStreamOmitField
	private List<String> soIdList;
   
	@XStreamOmitField
	private boolean isEligibleForDownLoad = false;
	
	@XStreamOmitField
	private boolean isDocumentAvailable = false;
	
	@XStreamOmitField
	private boolean otherErrorMessage = false;
	
	public List<DownloadResult> getResults() {
		return results;
	}
	public void setResults(List<DownloadResult> results) {
		this.results = results;
	}
	
	public static DownloadResults getError(String message, String code) {
		DownloadResults results = new DownloadResults();		
		List<DownloadResult> resultList = new ArrayList<DownloadResult> ();
		DownloadResult result = new DownloadResult();
		result.setCode(code);
		result.setMessage(message);
		resultList.add(result);
		results.setResults(resultList);
		return results;
	}
	public static DownloadResults getAllError(List<DownloadResult> resultList){
		DownloadResults results = new DownloadResults();		
		List<DownloadResult> list = new ArrayList<DownloadResult> ();
		list.addAll(resultList);
		results.setResults(list);
		return results;
	}
	public static DownloadResult getErrorResult(String message, String code){
		DownloadResult result = new DownloadResult();
		result.setCode(code);
		result.setMessage(message);
		return result;
	}
	public List<String> getSoIdList() {
		return soIdList;
	}
	public void setSoIdList(List<String> soIdList) {
		this.soIdList = soIdList;
	}
	public boolean isEligibleForDownLoad() {
		return isEligibleForDownLoad;
	}
	public void setEligibleForDownLoad(boolean isEligibleForDownLoad) {
		this.isEligibleForDownLoad = isEligibleForDownLoad;
	}
	public boolean isOtherErrorMessage() {
		return otherErrorMessage;
	}
	public void setOtherErrorMessage(boolean otherErrorMessage) {
		this.otherErrorMessage = otherErrorMessage;
	}
	public boolean isDocumentAvailable() {
		return isDocumentAvailable;
	}
	public void setDocumentAvailable(boolean isDocumentAvailable) {
		this.isDocumentAvailable = isDocumentAvailable;
	}
	
}
