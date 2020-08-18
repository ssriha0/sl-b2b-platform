package com.newco.marketplace.dto.vo.buyerUploadScheduler;

import com.sears.os.vo.SerializableBaseVO;

public class LocationClassVO extends SerializableBaseVO{

	int so_locn_class_id;
	String descr;
	/**
	 * @return the so_locn_class_id
	 */
	public int getSo_locn_class_id() {
		return so_locn_class_id;
	}
	/**
	 * @param so_locn_class_id the so_locn_class_id to set
	 */
	public void setSo_locn_class_id(int so_locn_class_id) {
		this.so_locn_class_id = so_locn_class_id;
	}
	/**
	 * @return the descr
	 */
	public String getDescr() {
		return descr;
	}
	/**
	 * @param descr the descr to set
	 */
	public void setDescr(String descr) {
		this.descr = descr;
	}
}
