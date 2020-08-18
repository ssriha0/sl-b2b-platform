package com.servicelive.domain.routingrules;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.ForeignKey;
import com.servicelive.domain.LoggableBaseDomain;

/**
 * RoutingRuleError entity.
 * 
 */
@Entity
@Table(name = "routing_rule_error")
public class RoutingRuleError extends LoggableBaseDomain {

	private static final long serialVersionUID = 20091005L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "routing_rule_error_id", unique = true, nullable = false)
	private Integer routingRuleErrorId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "routing_rule_hdr_id", nullable = false)
	@ForeignKey(name = "FK_routing_rule_error")
	private RoutingRuleHdr routingRuleHdr;

	@Column(name = "error_type")
	private String errorType;

	@Column(name = "error_cause_id")
	private Integer errorCauseId;

	@Column(name = "error")
	private String error;

	@Column(name = "conflict_ind")
	private Boolean conflictInd = false;

	@Column(name = "conflicting_rule_id")
	private Integer conflictingRuleId;
	
	@Column(name = "routing_rule_upload_rule_id")
	private Integer routingRuleUploadRuleId;

	// Constructors

	/** default constructor */
	public RoutingRuleError() {
		super();
	}

	public RoutingRuleError(String errorType, Integer errorCauseId,
			String error, String modifiedBy) {
		super();
		this.setErrorType(errorType);
		this.setErrorCauseId(errorCauseId);
		this.setError(error);
		this.setModifiedBy(modifiedBy);
	}

	public Integer getRoutingRuleErrorId() {
		return routingRuleErrorId;
	}

	public void setRoutingRuleErrorId(Integer routingRuleErrorId) {
		this.routingRuleErrorId = routingRuleErrorId;
	}

	public RoutingRuleHdr getRoutingRuleHdr() {
		return routingRuleHdr;
	}

	public void setRoutingRuleHdr(RoutingRuleHdr routingRuleHdr) {
		this.routingRuleHdr = routingRuleHdr;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	public Boolean getConflictInd() {
		return conflictInd;
	}

	public void setConflictInd(Boolean conflictInd) {
		this.conflictInd = conflictInd;
	}

	public Integer getConflictingRuleId() {
		return conflictingRuleId;
	}

	public void setConflictingRuleId(Integer conflictingRuleId) {
		this.conflictingRuleId = conflictingRuleId;
	}

	public Integer getErrorCauseId() {
		return errorCauseId;
	}

	public void setErrorCauseId(Integer errorCauseId) {
		this.errorCauseId = errorCauseId;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Integer getRoutingRuleUploadRuleId() {
		return routingRuleUploadRuleId;
	}

	public void setRoutingRuleUploadRuleId(Integer routingRuleUploadRuleId) {
		this.routingRuleUploadRuleId = routingRuleUploadRuleId;
	}
}