package com.servicelive.domain.routingrules;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * RoutingRuleErrorCause entity.
 * 
 */
@Entity
@Table(name = "lu_rule_error_cause")
public class RoutingRuleErrorCause {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6352961918123510099L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "error_cause_id", unique = true, nullable = false)
	private Integer routingRuleErrorCauseId;

	@Column(name = "error_cause_desc")
	private String errorCauseDesc;

	public Integer getRoutingRuleErrorCauseId() {
		return routingRuleErrorCauseId;
	}

	public void setRoutingRuleErrorCauseId(Integer routingRuleErrorCauseId) {
		this.routingRuleErrorCauseId = routingRuleErrorCauseId;
	}

	public String getErrorCauseDesc() {
		return errorCauseDesc;
	}

	public void setErrorCauseDesc(String errorCauseDesc) {
		this.errorCauseDesc = errorCauseDesc;
	}


}
