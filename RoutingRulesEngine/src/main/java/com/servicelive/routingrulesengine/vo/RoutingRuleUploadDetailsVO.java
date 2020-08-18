package com.servicelive.routingrulesengine.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * This class is used to display rulename,zipcodes,jobcodes and pickup locations of conflict of
 * car rule.
 * 
 */
public class RoutingRuleUploadDetailsVO implements Serializable {
	private static final long serialVersionUID = 4209518599795591722L;
	
	private String fileName;
	private String uploadAction;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUploadAction() {
		return uploadAction;
	}
	public void setUploadAction(String uploadAction) {
		this.uploadAction = uploadAction;
	}
	
	
}
