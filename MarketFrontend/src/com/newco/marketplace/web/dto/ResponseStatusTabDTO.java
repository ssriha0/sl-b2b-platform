package com.newco.marketplace.web.dto;

import java.util.ArrayList;

public class ResponseStatusTabDTO extends SerializedBaseDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1455884361831391602L;
	private ArrayList<ResponseStatusDTO> responseStatusDtoList;
	private Integer postedCnt;
	private Integer conditionallyAcceptedCnt;
	private Integer rejectedCnt;
	private Integer wfStateId;
	private boolean taskLevelIsOn;
	private String routingCriteria;
	
	
	public ArrayList<ResponseStatusDTO> getResponseStatusDtoList() {
		return responseStatusDtoList;
	}
	public void setResponseStatusDtoList(
			ArrayList<ResponseStatusDTO> responseStatusDtoList) {
		this.responseStatusDtoList = responseStatusDtoList;
	}
	public Integer getPostedCnt() {
		return postedCnt;
	}
	public void setPostedCnt(Integer postedCnt) {
		this.postedCnt = postedCnt;
	}
	public Integer getConditionallyAcceptedCnt() {
		return conditionallyAcceptedCnt;
	}
	public void setConditionallyAcceptedCnt(Integer conditionallyAcceptedCnt) {
		this.conditionallyAcceptedCnt = conditionallyAcceptedCnt;
	}
	public Integer getRejectedCnt() {
		return rejectedCnt;
	}
	public void setRejectedCnt(Integer rejectedCnt) {
		this.rejectedCnt = rejectedCnt;
	}
	public Integer getWfStateId() {
		return wfStateId;
	}
	public void setWfStateId(Integer wfStateId) {
		this.wfStateId = wfStateId;
	}
	public boolean isTaskLevelIsOn() {
		return taskLevelIsOn;
	}
	public void setTaskLevelIsOn(boolean taskLevelIsOn) {
		this.taskLevelIsOn = taskLevelIsOn;
	}
	public String getRoutingCriteria() {
		return routingCriteria;
	}
	public void setRoutingCriteria(String routingCriteria) {
		this.routingCriteria = routingCriteria;
	}
	

}
