package com.servicelive.domain.so;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.servicelive.domain.LoggableBaseDomain;

@Entity
@Table(name = "so_routing_rule_assoc")
public class SORoutingRuleAssoc extends LoggableBaseDomain {

	private static final long serialVersionUID = -20090823;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "routing_rule_so_assoc_Id")
	private Integer routingRuleSoAssocId;

	@Column(name = "so_id")
	@ForeignKey(name = "FK_so_routing_rule_assoc_so_id")
	private String soId;

	@Column(name = "routing_rule_hdr_id")
	@ForeignKey(name = "FK_so_routing_rule_assoc_routing_rule_hdr_id")
	private Integer routingRuleHdrId;

	/**
	 * @return the routingRuleSoAssocId
	 */
	public Integer getRoutingRuleSoAssocId() {
		return routingRuleSoAssocId;
	}

	/**
	 * @param routingRuleSoAssocId the routingRuleSoAssocId to set
	 */
	public void setRoutingRuleSoAssocId(Integer routingRuleSoAssocId) {
		this.routingRuleSoAssocId = routingRuleSoAssocId;
	}

	/**
	 * @return the soId
	 */
	public String getSoId() {
		return soId;
	}

	/**
	 * @param soId the soId to set
	 */
	public void setSoId(String soId) {
		this.soId = soId;
	}

	/**
	 * @return the routingRuleHdrId
	 */
	public Integer getRoutingRuleHdrId() {
		return routingRuleHdrId;
	}

	/**
	 * @param routingRuleHdrId the routingRuleHdrId to set
	 */
	public void setRoutingRuleHdrId(Integer routingRuleHdrId) {
		this.routingRuleHdrId = routingRuleHdrId;
	}

}
