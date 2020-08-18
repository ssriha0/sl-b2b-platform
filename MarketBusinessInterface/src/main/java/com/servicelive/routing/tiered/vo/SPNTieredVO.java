/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.servicelive.routing.tiered.vo;

import java.util.List;

/**
 * This VO holds value for the Tiered Information
 * @author hoza
 */
public class SPNTieredVO extends BaseTRVO {
    private static final long serialVersionUID = 6944494105666009045L;
    private Integer spnId;
    private Integer tierId = new Integer(0); // default to zero so that we can compare this value all the time
    private Integer nextTierId;
    private Integer buyerId ;
    private Integer tierWaitMinutes;
    private Integer tierWaitDays;
    private Integer tierWaitHours;
    List<Integer> performaceLevels;

    public List<Integer> getPerformaceLevels() {
        return performaceLevels;
    }

    public void setPerformaceLevels(List<Integer> performaceLevels) {
        this.performaceLevels = performaceLevels;
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

    public Integer getTierId() {
        return tierId;
    }

    public void setTierId(Integer tierId) {
        this.tierId = tierId;
    }

	public Integer getTierWaitMinutes() {
		return tierWaitMinutes;
	}

	public void setTierWaitMinutes(Integer tierWaitMinutes) {
		this.tierWaitMinutes = tierWaitMinutes;
	}

	public Integer getTierWaitDays() {
		return tierWaitDays;
	}

	public void setTierWaitDays(Integer tierWaitDays) {
		this.tierWaitDays = tierWaitDays;
	}

	public Integer getTierWaitHours() {
		return tierWaitHours;
	}

	public void setTierWaitHours(Integer tierWaitHours) {
		this.tierWaitHours = tierWaitHours;
	}

	/**
	 * @return the nextTierId
	 */
	public Integer getNextTierId() {
		return nextTierId;
	}

	/**
	 * @param nextTierId the nextTierId to set
	 */
	public void setNextTierId(Integer nextTierId) {
		this.nextTierId = nextTierId;
	}


}
