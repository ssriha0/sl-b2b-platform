package com.servicelive.routingrulesengine.vo;

public class CustomReferenceVO {
	
	String customRefName;
	String customRefValue;
	
	public CustomReferenceVO(String customRefName, String customRefValue) {
		super();
		this.customRefName = customRefName;
		this.customRefValue = customRefValue;
	}
	
	public String getCustomRefName() {
		return customRefName;
	}
	public void setCustomRefName(String customRefName) {
		this.customRefName = customRefName;
	}
	public String getCustomRefValue() {
		return customRefValue;
	}
	public void setCustomRefValue(String customRefValue) {
		this.customRefValue = customRefValue;
	}

}
