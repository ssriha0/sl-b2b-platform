/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.servicelive.routing.tiered.vo;

import java.util.Date;

/**
 *
 * @author hoza
 */
public class SPNTierEventInfoVO extends BaseTRVO {
    private static final long serialVersionUID = -8089480043714477573L;
    private String soId;
    private String groupOrderId;
    private Integer currentTierId;
    private Integer nextTierId;
    private Date currentFireTime;
    private Date nextFireTime;

    public Date getCurrentFireTime() {
        return currentFireTime;
    }

    public void setCurrentFireTime(Date currentFireTime) {
        this.currentFireTime = currentFireTime;
    }

   

    public String getGroupOrderId() {
        return groupOrderId;
    }

    public void setGroupOrderId(String groupOrderId) {
        this.groupOrderId = groupOrderId;
    }

    public Date getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public String getSoId() {
        return soId;
    }

    public void setSoId(String soId) {
        this.soId = soId;
    }

	public Integer getCurrentTierId() {
		return currentTierId;
	}

	public void setCurrentTierId(Integer currentTierId) {
		this.currentTierId = currentTierId;
	}

	public Integer getNextTierId() {
		return nextTierId;
	}

	public void setNextTierId(Integer nextTierId) {
		this.nextTierId = nextTierId;
	}
    
}
