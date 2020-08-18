package com.newco.marketplace.api.mobile.beans;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("results")
public class DownloadResults {
	
	@XStreamAlias("error")
	@XStreamImplicit(itemFieldName="error")
	private List<DownloadErrorResult> error;
	
	public List<DownloadErrorResult> getError() {
		return error;
	}
    public void setError(List<DownloadErrorResult> error) {
		this.error = error;
	}
    
    public static DownloadResults getError(String message, String code) {
		DownloadResults results = new DownloadResults();		
		List<DownloadErrorResult> resultList = new ArrayList<DownloadErrorResult> ();
		DownloadErrorResult result = new DownloadErrorResult();
		result.setCode(code);
		result.setMessage(message);
		resultList.add(result);
		results.setError(resultList);
		return results;
	}



	
}
