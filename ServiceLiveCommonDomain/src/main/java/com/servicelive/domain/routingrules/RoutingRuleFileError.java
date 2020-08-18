package com.servicelive.domain.routingrules;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
/**
 * RoutingRuleFileError entity.
 * 
 */

@Entity
@Table(name = "routing_rule_file_error")
public class RoutingRuleFileError {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "routing_rule_file_error_id", unique = true, nullable = false)
	private Integer routingRuleFileErrorId;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="routing_rule_file_hdr_id", nullable = false)
    @ForeignKey(name="FK_routing_rule_file_error")
	private RoutingRuleFileHdr routingRuleFileHdr;
	
	@Column(name = "error_desc", unique = false)
	private String errorDesc;
	
	@Column(name = "error_type")
	private Integer errorType;
	
	@Column(name = "file_action")
	private String action;
	
	public Integer getErrorType() {
		return errorType;
	}

	public void setErrorType(Integer errorType) {
		this.errorType = errorType;
	}

	public RoutingRuleFileError() {
	
	}

	public RoutingRuleFileError(RoutingRuleFileHdr routingRuleFileHdr, Integer errorType,
			String errorDesc, String action) {
		this.routingRuleFileHdr = routingRuleFileHdr;
		this.errorDesc = errorDesc;
		this.errorType = errorType;
		this.action = action;
	}

	public Integer getRoutingRuleFileErrorId() {
		return routingRuleFileErrorId;
	}

	public void setRoutingRuleFileErrorId(Integer routingRuleFileErrorId) {
		this.routingRuleFileErrorId = routingRuleFileErrorId;
	}

	public String getErrorDesc() {
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	public RoutingRuleFileHdr getRoutingRuleFileHdr() {
		return routingRuleFileHdr;
	}

	public void setRoutingRuleFileHdr(RoutingRuleFileHdr routingRuleFileHdr) {
		this.routingRuleFileHdr = routingRuleFileHdr;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	
}
