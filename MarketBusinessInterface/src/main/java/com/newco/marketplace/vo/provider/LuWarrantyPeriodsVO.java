/**
 * 
 */
package com.newco.marketplace.vo.provider;

import java.util.HashMap;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author MTedder
 * <!--  VO for values for jsp page drop down list -->
 */
public class LuWarrantyPeriodsVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7295736795720799702L;
	private int id;
	private String type;
	private String descr;
	private int sortOrder;
	private HashMap warrantyPeriods;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
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
	/**
	 * @return the sortOrder
	 */
	public int getSortOrder() {
		return sortOrder;
	}
	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	/**
	 * @return the warrantyPeriods
	 */
	public HashMap getWarrantyPeriods() {
		return warrantyPeriods;
	}
	/**
	 * @param warrantyPeriods the warrantyPeriods to set
	 */
	public void setWarrantyPeriods(HashMap warrantyPeriods) {
		this.warrantyPeriods = warrantyPeriods;
	}
	
}
