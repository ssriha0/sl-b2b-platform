/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.servicelive.routing.tiered.schedulermanager;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import static com.servicelive.routing.tiered.constants.SPNTieredRoutingConstants.KEY_SO_ID;
import static com.servicelive.routing.tiered.constants.SPNTieredRoutingConstants.KEY_TIER_ID;
import static com.servicelive.routing.tiered.constants.SPNTieredRoutingConstants.KEY_IS_GROUPED;
import static com.servicelive.routing.tiered.constants.SPNTieredRoutingConstants.KEY_TR_REASON_CODE;
/**
 *
 * @author hoza
 */
public class SPNTieredRoutingJobDetails extends JobDetail {
    private Integer tierId ;
    private String orderId;
    private Boolean isGrouped;
    private Integer reasonCode;

    public Boolean getIsGrouped() {
        return isGrouped;
    }

    public void setIsGrouped(Boolean isGrouped) {
        this.isGrouped = isGrouped;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getTierId() {
        return tierId;
    }

    public void setTierId(Integer tierId) {
        this.tierId = tierId;
    }

    public String getSoId() {
        return orderId;
    }

    public void setSoId(String soId) {
        this.orderId = soId;
    }

    public SPNTieredRoutingJobDetails(Integer tierId,String orderId, Boolean isGrouped,Integer reasonId) throws Exception{
        this.tierId = tierId;
        this.orderId = orderId;
        this.isGrouped = isGrouped;
        this.reasonCode = reasonId;
        setUp();
    }


    public Integer getSpnId() {
        return tierId;
    }

    public void setSpnId(Integer spnId) {
        this.tierId = spnId;
    }

    private void setUp() throws Exception{
        this.setName(SPNJobNameUtil.getJOBName( orderId));
        this.setGroup(SPNJobNameUtil.getJOBGroupName(tierId, orderId));
        this.setJobClass(SPNTieredRoutingJob.class);
        JobDataMap map = new JobDataMap();
        map.put(KEY_TIER_ID, tierId);
        map.put(KEY_SO_ID, orderId);
        map.put(KEY_IS_GROUPED, isGrouped);
        map.put(KEY_TR_REASON_CODE, reasonCode);
        this.setJobDataMap(map);
    }

	/**
	 * @return the reasonCode
	 */
	public Integer getReasonCode() {
		return reasonCode;
	}

	/**
	 * @param reasonCode the reasonCode to set
	 */
	public void setReasonCode(Integer reasonCode) {
		this.reasonCode = reasonCode;
	}

}
