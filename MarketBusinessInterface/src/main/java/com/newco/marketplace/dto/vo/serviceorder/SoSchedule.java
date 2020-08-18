package com.newco.marketplace.dto.vo.serviceorder;

import com.sears.os.vo.SerializableBaseVO;

public class SoSchedule extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5658069740402316295L;
	private String scheduleStatus ;
	private Integer confirmInd;
	//SL SL-18698 	R6.0 UAT Order Management  Display ETA in SOD
	private String eta;
	/**
	 * @return the eta
	 */
	public String getEta() {
		return eta;
	}
	/**
	 * @param eta the eta to set
	 */
	public void setEta(String eta) {
		this.eta = eta;
	}
	public String getScheduleStatus() {
		return scheduleStatus;
	}
	public void setScheduleStatus(String scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}
	public Integer getConfirmInd() {
		return confirmInd;
	}
	public void setConfirmInd(Integer confirmInd) {
		this.confirmInd = confirmInd;
	}
	
}
