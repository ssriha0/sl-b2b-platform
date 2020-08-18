/*
 * @(#)CampaignApprovalCriteria.java
 *
 * Copyright 2008 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 */
package com.servicelive.domain.spn.campaign;

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

import com.servicelive.domain.ApprovalCriteria;
import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.lookup.LookupSPNApprovalCriteria;

/**
 * @author Mahmud Khair
 *
 */
@Entity
@Table ( name = "spnet_campaign_invitation_criteria")
public class CampaignApprovalCriteria extends LoggableBaseDomain implements ApprovalCriteria {

	private static final long serialVersionUID = -1858899074542882212L;

	@Id @GeneratedValue(strategy=IDENTITY)
	@Column (name = "id", unique = true, nullable = false, insertable = true, updatable = true)
	private Integer id ;

	@ManyToOne
    @JoinColumn(name="campaign_id", insertable=false, updatable=false)
    @ForeignKey(name="FK_CAMP_APP_CRITERIA_CAMPID")
	private CampaignHeader campaignId;

	@ManyToOne(cascade = {}, fetch = FetchType.EAGER)
	@JoinColumn(name = "criteria_id", unique = false, nullable = true, insertable = true, updatable = true)
	@ForeignKey(name="FK_CAMP_APP_CRITERIA_CRITERIAID2")
	private LookupSPNApprovalCriteria criteriaId;

	@Column ( name = "value" , unique = false, nullable = true, insertable = true, updatable = true, length = 1000)
	private String value;
	
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
	 * @return the campaign_Id
	 */
	public CampaignHeader getCampaignId() {
		return campaignId;
	}


	/**
	 * @param campaign_Id the campaign_Id to set
	 */
	public void setCampaignId(CampaignHeader campaign_Id) {
		this.campaignId = campaign_Id;
	}


	/**
	 * @return the criteriaId
	 */
	public LookupSPNApprovalCriteria getCriteriaId() {
		return criteriaId;
	}


	/**
	 * @param criteriaId the criteriaId to set
	 */
	public void setCriteriaId(LookupSPNApprovalCriteria criteriaId) {
		this.criteriaId = criteriaId;
	}


	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
