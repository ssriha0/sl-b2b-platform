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

import com.servicelive.domain.LoggableBaseDomain;

/**
 * RoutingRuleUploadRule entity.
 * 
 */
@Entity
@Table(name = "routing_rule_upload_rule")
public class RoutingRuleUploadRule extends LoggableBaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6352961918123510044L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "routing_rule_upload_rule_id", unique = true, nullable = false)
	private Integer routingRuleUploadRuleId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "routing_rule_file_hdr_id")
	@ForeignKey(name = "FK_routing_rule_upload_rule")
	private RoutingRuleFileHdr routingRuleFileHdr;

	@Column(name = "action")
	private String action;

	@Column(name = "status")
	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "routing_rule_hdr_id")
	@ForeignKey(name = "FK_routing_rule_upload_rule_routing_rule_hdr")
	private RoutingRuleHdr routingRuleHdr;

	public Integer getRoutingRuleUploadRuleId() {
		return routingRuleUploadRuleId;
	}

	public void setRoutingRuleUploadRuleId(Integer routingRuleUploadRuleId) {
		this.routingRuleUploadRuleId = routingRuleUploadRuleId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public RoutingRuleHdr getRoutingRuleHdr() {
		return routingRuleHdr;
	}

	public void setRoutingRuleHdr(RoutingRuleHdr routingRuleHdr) {
		this.routingRuleHdr = routingRuleHdr;
	}
	public RoutingRuleUploadRule(){
		
	}

	public RoutingRuleUploadRule(RoutingRuleFileHdr routingRuleFileHdr,
			String action, String status, RoutingRuleHdr routingRuleHdr) {
		super();
		this.routingRuleFileHdr = routingRuleFileHdr;
		this.action = action;
		this.status = status;
		this.routingRuleHdr = routingRuleHdr;
	}

}
