/*
 * @(#)CampaignSPN.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.servicelive.domain.spn.campaign;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.spn.network.SPNHeader;

/**
 * @author Mahmud Khair
 *
 */
@Entity
@Table (name = "spnet_campaign_network")
public class CampaignSPN extends LoggableBaseDomain {
	private static final long serialVersionUID = 5767882539814588234L;

	@Id @GeneratedValue(strategy=IDENTITY)
	@Column ( name = "id" , unique= true,nullable = false, insertable = true, updatable = true )
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "spn_id", unique = false, nullable = false, insertable = true, updatable = true)
	@ForeignKey (name = "FK_CAMP_NETWORK_SPN_ID")
	private SPNHeader  spn;
	
	@ManyToOne
    @JoinColumn(name="campaign_id", insertable=false, updatable=false)
    @ForeignKey (name = "FK_CAMP_NETWORK_CAMP_ID")
	private CampaignHeader campaign;

	

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the spn
	 */
	public SPNHeader getSpn() {
		return spn;
	}

	/**
	 * @param spn the spn to set
	 */
	public void setSpn(SPNHeader spn) {
		this.spn = spn;
	}
	
	/**
	 * @return the campaign
	 */
	public CampaignHeader getCampaign() {
		return campaign;
	}

	/**
	 * @param campaign the campaign to set
	 */
	public void setCampaign(CampaignHeader campaign) {
		this.campaign = campaign;
	}

	
}//End of CampaignSPN.java
