package com.newco.marketplace.dto.vo.serviceorder;

import com.sears.os.vo.SerializableBaseVO;



public class ServiceOrderContact extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4471935724522367326L;
	int soLocationId;
	int soContactId;
	String soId;
	
	public int getSoContactId() {
		return soContactId;
	}
	public void setSoContactId(int soContactId) {
		this.soContactId = soContactId;
	}
	public String getSoId() {
		return soId;
	}
	public void setSoId(String soId) {
		this.soId = soId;
	}
	public int getSoLocationId() {
		return soLocationId;
	}
	public void setSoLocationId(int soLocationId) {
		this.soLocationId = soLocationId;
	}
	
}
