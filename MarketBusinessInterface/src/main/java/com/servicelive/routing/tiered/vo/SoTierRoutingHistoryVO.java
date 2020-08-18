/*
 * @(#)SoTierRoutinHistoryVO.java
 *
 * Copyright 2009 Sears Holdings Corporation, All rights reserved.
 * SHC PROPRIETARY/CONFIDENTIAL.
 *  
 */
package com.servicelive.routing.tiered.vo;

import java.util.Date;


/**
 * @author Mahmud Khair
 * @since 09/15/2009
 *
 */
public class SoTierRoutingHistoryVO {
	/**
	 * 
	 * This field corresponds to the database column so_tier_route_history.id
	 */
	private Integer soTierRoutingHistId;
	
	/**
     * This field corresponds to the database column so_tier_route_history.so_id
     */
    private String soId;

    /**
     * This field corresponds to the database column so_tier_route_history.tier_id
     */
    private Integer tierId;

    /**
     * This field corresponds to the database column so_tier_route_history.reason_code
     */
    private Integer reasonCode;
    
    /**
     * This field corresponds to the database column lu_tier_route_reason.descr
     */
    private String reasonDesc;

    /**
     * This field corresponds to the database column so_tier_route_history.route_status_ind
     */
    private Boolean routeStatusInd;

    /**
     * This field corresponds to the database column so_tier_route_history.created_date
     */
    private Date createdDate;

    /**
     * This field corresponds to the database column so_tier_route_history.modified_date
     */
    private Date modifiedDate;

    /**
     *
     * This method returns the value of the database column so_tier_route_history.so_id
     *
     * @return the value of so_tier_route_history.so_id
     *
     *
     */
    public String getSoId() {
        return soId;
    }

    /**
     *
     * This method sets the value of the database column so_tier_route_history.so_id
     *
     * @param soId the value for so_tier_route_history.so_id
     *
     *
     */
    public void setSoId(String soId) {
        this.soId = soId;
    }

    /**
     *
     * This method returns the value of the database column so_tier_route_history.tier_id
     *
     * @return the value of so_tier_route_history.tier_id
     *
     *
     */
    public Integer getTierId() {
        return tierId;
    }

    /**
     *
     * This method sets the value of the database column so_tier_route_history.tier_id
     *
     * @param tierId the value for so_tier_route_history.tier_id
     *
     *
     */
    public void setTierId(Integer tierId) {
        this.tierId = tierId;
    }

    /**
     *
     * This method returns the value of the database column so_tier_route_history.reason_code
     *
     * @return the value of so_tier_route_history.reason_code
     *
     *
     */
    public Integer getReasonCode() {
        return reasonCode;
    }

    /**
     *
     * This method sets the value of the database column so_tier_route_history.reason_code
     *
     * @param reasonCode the value for so_tier_route_history.reason_code
     *
     *
     */
    public void setReasonCode(Integer reasonCode) {
        this.reasonCode = reasonCode;
    }

    /**
	 * @return the reasonDesc
	 */
	public String getReasonDesc() {
		return reasonDesc;
	}

	/**
	 * @param reasonDesc the reasonDesc to set
	 */
	public void setReasonDesc(String reasonDesc) {
		this.reasonDesc = reasonDesc;
	}

	/**
     *
     * This method returns the value of the database column so_tier_route_history.route_status_ind
     *
     * @return the value of so_tier_route_history.route_status_ind
     *
     *
     */
    public Boolean getRouteStatusInd() {
        return routeStatusInd;
    }

    /**
     *
     * This method sets the value of the database column so_tier_route_history.route_status_ind
     *
     * @param routeStatusInd the value for so_tier_route_history.route_status_ind
     *
     *
     */
    public void setRouteStatusInd(Boolean routeStatusInd) {
        this.routeStatusInd = routeStatusInd;
    }

    /**
     *
     * This method returns the value of the database column so_tier_route_history.created_date
     *
     * @return the value of so_tier_route_history.created_date
     *
     *
     */
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     *
     * This method sets the value of the database column so_tier_route_history.created_date
     *
     * @param createdDate the value for so_tier_route_history.created_date
     *
     *
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    /**
     *
     * This method returns the value of the database column so_tier_route_history.modified_date
     *
     * @return the value of so_tier_route_history.modified_date
     *
     *
     */
    public Date getModifiedDate() {
        return modifiedDate;
    }

    /**
     *
     * This method sets the value of the database column so_tier_route_history.modified_date
     *
     * @param modifiedDate the value for so_tier_route_history.modified_date
     *
     *
     */
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

	/**
	 * @return the soTierRoutingHistId
	 */
	public Integer getSoTierRoutingHistId() {
		return soTierRoutingHistId;
	}

	/**
	 * @param soTierRoutingHistId the soTierRoutingHistId to set
	 */
	public void setSoTierRoutingHistId(Integer soTierRoutingHistId) {
		this.soTierRoutingHistId = soTierRoutingHistId;
	}
}