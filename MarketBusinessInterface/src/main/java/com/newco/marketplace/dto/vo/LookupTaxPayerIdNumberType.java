package com.newco.marketplace.dto.vo;

import com.sears.os.vo.SerializableBaseVO;

public class LookupTaxPayerIdNumberType extends SerializableBaseVO{
    /**
	 * 
	 */
	private static final long serialVersionUID = 20090428L;
	private int id;
	private String description;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
}
