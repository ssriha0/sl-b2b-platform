package com.newco.marketplace.dto.vo.spn;


import com.sears.os.vo.SerializableBaseVO;

public class SPNetTierReleaseVO extends SerializableBaseVO{

	private static final long serialVersionUID = 7340651147575047883L;
	
	private Integer spnId;
	
	private Integer tierId;
	
    private Integer tierWaitMinutes;
    private Integer tierWaitDays;
    private Integer tierWaitHours;
	private Integer noOfMembers;
	private int perfomanceId;

	
	public int getPerfomanceId() {
		return perfomanceId;
	}
	public void setPerfomanceId(int perfomanceId) {
		this.perfomanceId = perfomanceId;
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
	public Integer getNoOfMembers() {
		return noOfMembers;
	}
	public void setNoOfMembers(Integer noOfMembers) {
		this.noOfMembers = noOfMembers;
	}

}
