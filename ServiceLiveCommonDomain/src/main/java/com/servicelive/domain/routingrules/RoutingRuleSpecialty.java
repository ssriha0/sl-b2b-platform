package com.servicelive.domain.routingrules;

import static javax.persistence.GenerationType.IDENTITY;

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

import org.hibernate.annotations.ForeignKey;

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.lookup.LookupRoutingRuleType;

/**
 * RoutingRuleSpecialty entity.
 * 
 */
@Entity
@Table(name = "routing_rule_speciality")
public class RoutingRuleSpecialty extends LoggableBaseDomain {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 20090820L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "routing_rule_speciality_id", unique = true, nullable = false)
	private Integer routingRuleSpecialtyId;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="routing_rule_hdr_id")
    @ForeignKey(name="FK_routing_rule_specialty_routing_rule_hdr_id")
	private RoutingRuleHdr routingRuleHdr;
	
	@Column(name = "speciality", length = 30)
	private String specialty;
	
	@Column(name = "auto_pulling", length = 30)
	private Boolean autoPulling;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="rule_type_id")
    @ForeignKey(name="FK_routing_rule_speicality_rule_type_id")
	private LookupRoutingRuleType ruleType;
	
	@OneToMany(fetch=FetchType.LAZY, cascade= { CascadeType.ALL})
	@JoinColumn(name="routing_rule_speciality_id")
	private List<RoutingRulePrice> RoutingRulePrice;

	/**
	 * @return the routingRuleSpecialtyId
	 */
	public Integer getRoutingRuleSpecialtyId() {
		return routingRuleSpecialtyId;
	}

	/**
	 * @param routingRuleSpecialtyId the routingRuleSpecialtyId to set
	 */
	public void setRoutingRuleSpecialtyId(Integer routingRuleSpecialtyId) {
		this.routingRuleSpecialtyId = routingRuleSpecialtyId;
	}

	/**
	 * @return the routingRuleHdr
	 */
	public RoutingRuleHdr getRoutingRuleHdr() {
		return routingRuleHdr;
	}

	/**
	 * @param routingRuleHdr the routingRuleHdr to set
	 */
	public void setRoutingRuleHdr(RoutingRuleHdr routingRuleHdr) {
		this.routingRuleHdr = routingRuleHdr;
	}

	/**
	 * @return the specialty
	 */
	public String getSpecialty() {
		return specialty;
	}

	/**
	 * @param specialty the specialty to set
	 */
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	/**
	 * @return the autoPulling
	 */
	public Boolean getAutoPulling() {
		return autoPulling;
	}

	/**
	 * @param autoPulling the autoPulling to set
	 */
	public void setAutoPulling(Boolean autoPulling) {
		this.autoPulling = autoPulling;
	}

	/**
	 * @return the ruleType
	 */
	public LookupRoutingRuleType getRuleType() {
		return ruleType;
	}

	/**
	 * @param ruleType the ruleType to set
	 */
	public void setRuleType(LookupRoutingRuleType ruleType) {
		this.ruleType = ruleType;
	}

	/**
	 * @return the routingRulePrice
	 */
	public List<RoutingRulePrice> getRoutingRulePrice() {
		return RoutingRulePrice;
	}

	/**
	 * @param routingRulePrice the routingRulePrice to set
	 */
	public void setRoutingRulePrice(List<RoutingRulePrice> routingRulePrice) {
		RoutingRulePrice = routingRulePrice;
	}


}