package com.newco.marketplace.dto.vo.ledger;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author zizrale
 *
 */
public class ReceivedAmountVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1000692984038824137L;
	private double totalReceived;
	private int vendorId;
	private int stateId;
	
	/** @return int */
	public int getStateId() {
		return stateId;
	}
	/** @param stateId */
	public void setStateId(int stateId) {
		this.stateId = stateId;
	}
	/** @return double */
	public double getTotalReceived() {
		return totalReceived;
	}
	/** @param totalReceived **/
	public void setTotalReceived(double totalReceived) {
		this.totalReceived = totalReceived;
	}
	/** @return int */
	public int getVendorId() {
		return vendorId;
	}
	/** @param vendorId **/
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	
}
