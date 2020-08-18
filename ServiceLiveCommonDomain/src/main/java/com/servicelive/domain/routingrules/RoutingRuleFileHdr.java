package com.servicelive.domain.routingrules;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.buyer.BuyerResource;
/**
 * RoutingRuleFileHdr entity.
 * 
 */
@Entity
@Table(name = "routing_rule_file_hdr")
public class RoutingRuleFileHdr extends LoggableBaseDomain {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7556509743080029380L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "routing_rule_file_hdr_id", unique = true, nullable = false)
	private Integer routingRuleFileHdrId;
	
	@Column(name = "routing_rule_file_name")
	private String routingRuleFileName;
	
	@Column(name = "file_path")
	private String filePath;
	
	@Column(name = "file_status")
	private String fileStatus;
	
	@Column(name = "process_completed_time")
	private Date processCompletedTime;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="uploaded_by", referencedColumnName="resource_id")
	private BuyerResource uploadedBy;
	
	@Column(name = "num_new_rules")
	private Integer numNewRules = 0;
	
	@Column(name = "num_update_rules")
	private Integer numUpdateRules = 0;
	
	@Column(name = "num_errors")
	private Integer numErrors = 0;
	
	@Column(name = "num_conflicts")
	private Integer numConflicts = 0;
	
	@OneToMany(fetch=FetchType.LAZY, cascade= {CascadeType.ALL})
	@JoinColumn(name="routing_rule_file_hdr_id")
	private List<RoutingRuleFileError> routingRuleFileError;
	
	@OneToMany(fetch=FetchType.LAZY, cascade= {CascadeType.ALL})
	@JoinColumn(name="routing_rule_file_hdr_id")
	private List<RoutingRuleUploadRule> routingRuleUploadRule;
	
	public Integer getRoutingRuleFileHdrId() {
		return routingRuleFileHdrId;
	}

	public void setRoutingRuleFileHdrId(Integer routingRuleFileHdrId) {
		this.routingRuleFileHdrId = routingRuleFileHdrId;
	}

	public String getRoutingRuleFileName() {
		return routingRuleFileName;
	}

	public void setRoutingRuleFileName(String routingRuleFileName) {
		this.routingRuleFileName = routingRuleFileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileStatus() {
		return fileStatus;
	}

	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
	}

	public List<RoutingRuleFileError> getRoutingRuleFileError() {
		return routingRuleFileError;
	}

	public void setRoutingRuleFileError(
			List<RoutingRuleFileError> routingRuleFileError) {
		this.routingRuleFileError = routingRuleFileError;
	}

	public List<RoutingRuleUploadRule> getRoutingRuleUploadRule() {
		return routingRuleUploadRule;
	}

	public void setRoutingRuleUploadRule(
			List<RoutingRuleUploadRule> routingRuleUploadRule) {
		this.routingRuleUploadRule = routingRuleUploadRule;
	}

	public Integer getNumNewRules() {
		return numNewRules;
	}

	public void setNumNewRules(Integer numNewRules) {
		this.numNewRules = numNewRules;
	}

	public Integer getNumUpdateRules() {
		return numUpdateRules;
	}

	public void setNumUpdateRules(Integer numUpdateRules) {
		this.numUpdateRules = numUpdateRules;
	}

	public Integer getNumErrors() {
		return numErrors;
	}

	public void setNumErrors(Integer numErrors) {
		this.numErrors = numErrors;
	}

	public Integer getNumConflicts() {
		return numConflicts;
	}

	public void setNumConflicts(Integer numConflicts) {
		this.numConflicts = numConflicts;
	}

	public BuyerResource getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(BuyerResource uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	public Date getProcessCompletedTime() {
		return processCompletedTime;
	}

	public void setProcessCompletedTime(Date processCompletedTime) {
		this.processCompletedTime = processCompletedTime;
	}

}
