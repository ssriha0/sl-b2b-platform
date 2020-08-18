package com.newco.marketplace.dto.vo.provider;

import com.sears.os.vo.SerializableBaseVO;

public class TimeZoneLookupVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6806854210117455125L;
	private int id;
	private String descr;
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
