/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.servicelive.routing.tiered.vo;

import java.util.List;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;

/**
 *
 * @author hoza
 */
public class TierRouteServiceOrderVO extends BaseTRVO {
    
	public TierRouteServiceOrderVO() {
		super();
	}

	public TierRouteServiceOrderVO(String orderId, Boolean grouped, Integer reasonId) {
		super();
		this.orderId = orderId;
		this.grouped = grouped;
		this.reasonId = reasonId;
	}

	private static final long serialVersionUID = -1300646405829724256L;
    private String orderId;
    private Integer spnId;
    private Integer buyerId;
    private Integer statusId;
    private Integer currentTierId;
    private Integer tierToBeRouted;
    private Integer reasonId;
    private Boolean grouped;
    private List<ServiceOrderSearchResultsVO> groupedOrders;

    public Integer getCurrentTierId() {
        return currentTierId;
    }

    public Integer getReasonId() {
        return reasonId;
    }

    public void setReasonId(Integer reasonId) {
        this.reasonId = reasonId;
    }

    public void setCurrentTierId(Integer currentTierId) {
        this.currentTierId = currentTierId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    public Integer getSpnId() {
        return spnId;
    }

    public void setSpnId(Integer spnId) {
        this.spnId = spnId;
    }

	public Integer getTierToBeRouted() {
		return tierToBeRouted;
	}

	public void setTierToBeRouted(Integer tierToBeRouted) {
		this.tierToBeRouted = tierToBeRouted;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Boolean isGrouped() {
		return grouped;
	}

	public void setGrouped(Boolean grouped) {
		this.grouped = grouped;
	}

	public List<ServiceOrderSearchResultsVO> getGroupedOrders() {
		return groupedOrders;
	}

	public void setGroupedOrders(List<ServiceOrderSearchResultsVO> groupedOrders) {
		this.groupedOrders = groupedOrders;
	}

}
