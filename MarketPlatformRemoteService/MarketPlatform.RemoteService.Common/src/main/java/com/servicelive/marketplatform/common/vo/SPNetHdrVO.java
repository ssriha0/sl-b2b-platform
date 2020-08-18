package com.servicelive.marketplatform.common.vo;

import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

@XmlRootElement()
public class SPNetHdrVO implements Serializable{

    /**
	 *
	 */
	private static final long serialVersionUID = 4891774862616124095L;
    
    private String spnName;
    private String perfCriteriaLevel;
    private Boolean mpOverFlow;
    private String priorityStatus;
    
	public String getSpnName() {
		return spnName;
	}
	public void setSpnName(String spnName) {
		this.spnName = spnName;
	}
	public String getPerfCriteriaLevel() {
		return perfCriteriaLevel;
	}
	public void setPerfCriteriaLevel(String perfCriteriaLevel) {
		this.perfCriteriaLevel = perfCriteriaLevel;
	}
	public Boolean getMpOverFlow() {
		return mpOverFlow;
	}
	public void setMpOverFlow(Boolean mpOverFlow) {
		this.mpOverFlow = mpOverFlow;
	}
	public String getPriorityStatus() {
		return priorityStatus;
	}
	public void setPriorityStatus(String priorityStatus) {
		this.priorityStatus = priorityStatus;
	}


}
