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
import com.servicelive.domain.buyer.Buyer;

/**
 * RoutingRuleBuyerAssoc entity.
 * 
 * @author agupt02
 */
@Entity
@Table(name = "routing_rule_buyer_assoc")
public class RoutingRuleBuyerAssoc extends LoggableBaseDomain {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 7941837364224004885L;
	
    @Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "routing_rule_buyer_assoc_id", unique = true, nullable = false)
	private Integer Id;
	
    @ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinColumn(name="buyer_id")
    @ForeignKey(name="FK_routing_rule_buyer_assoc_buyer_id")
	private Buyer buyer;
    
    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="routing_rule_buyer_assoc_id")
    private List<RoutingRuleHdr> routingRuleHeaders;

	// Constructors

	/** default constructor */
	public RoutingRuleBuyerAssoc() {
		super();
	}

	// Property accessors
	
	public Integer getId() {
		return this.Id;
	}

	public void setId(Integer Id) {
		this.Id = Id;
	}

	public Buyer getBuyer() {
		return this.buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public List<RoutingRuleHdr> getRoutingRuleHeaders() {
		return routingRuleHeaders;
	}

	public void setRoutingRuleHeaders(List<RoutingRuleHdr> routingRuleHeaders) {
		this.routingRuleHeaders = routingRuleHeaders;
	}

}