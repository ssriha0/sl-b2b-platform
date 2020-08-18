package com.servicelive.routingrulesengine.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * This class is used to display rulename,zipcodes,jobcodes and pickup locations of conflict of
 * car rule.
 * 
 */
public class RoutingRuleErrorVO implements Serializable {
	private static final long serialVersionUID = 4209518599795591722L;
	
	private String errorMessage;
	private String errorType;
	private String conflictRuleName;
	private String conflictInd;
	private List<RuleErrorVO> ruleErrorVo;
	private Integer uploadFileHdrId;
	private Integer routingRuleHdrId;
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getConflictRuleName() {
		return conflictRuleName;
	}
	public void setConflictRuleName(String conflictRuleName) {
		this.conflictRuleName = conflictRuleName;
	}
	public String getConflictInd() {
		return conflictInd;
	}
	public void setConflictInd(String conflictInd) {
		this.conflictInd = conflictInd;
	}
	
	public List<RuleErrorVO> getRuleErrorVo() {
		return ruleErrorVo;
	}
	public void setRuleErrorVo(List<RuleErrorVO> ruleErrorVo) {
		this.ruleErrorVo = ruleErrorVo;
	}
	public Integer getUploadFileHdrId() {
		return uploadFileHdrId;
	}
	public void setUploadFileHdrId(Integer uploadFileHdrId) {
		this.uploadFileHdrId = uploadFileHdrId;
	}
	public Integer getRoutingRuleHdrId() {
		return routingRuleHdrId;
	}
	public void setRoutingRuleHdrId(Integer routingRuleHdrId) {
		this.routingRuleHdrId = routingRuleHdrId;
	}
	
	
	
}
