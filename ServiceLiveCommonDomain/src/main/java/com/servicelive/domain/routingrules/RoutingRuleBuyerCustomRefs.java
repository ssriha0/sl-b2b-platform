package com.servicelive.domain.routingrules;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "routing_rule_buyer_customrefs")
public class RoutingRuleBuyerCustomRefs{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7985507066096406465L;

	/**
	 * 
	 */
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private String id;
	
	@Column(name = "buyer_id")
	private Integer buyerId;
	
	@Column(name = "customref_name",length=30)
	private String customRefName;

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public String getCustomRefName() {
		return customRefName;
	}

	public void setCustomRefName(String customRefName) {
		this.customRefName = customRefName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


}
