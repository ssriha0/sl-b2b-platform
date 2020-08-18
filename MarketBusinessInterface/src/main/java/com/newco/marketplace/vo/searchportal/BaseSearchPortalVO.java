/**
 *
 */
package com.newco.marketplace.vo.searchportal;

import com.newco.marketplace.vo.MPBaseVO;

/**
 * @author hoza
 *
 */
public class BaseSearchPortalVO extends MPBaseVO {

	/**
	 *
	 */
	private static final long serialVersionUID = -6956987406067241451L;
	private String sortColumnName;
	private String sortOrder;
	private String soId;
	/* (non-Javadoc)
	 * @see com.sears.os.vo.ABaseVO#toString()
	 */
	@Override
	public String toString() {
		return "Sort Column" + this.getSortColumnName()+ " Sort Order" + this.getSortOrder();
	}
	/**
	 * @return the sortColumnName
	 */
	public String getSortColumnName() {
		return sortColumnName;
	}
	/**
	 * @param sortColumnName the sortColumnName to set
	 */
	public void setSortColumnName(String sortColumnName) {
		this.sortColumnName = sortColumnName;
	}
	/**
	 * @return the sortOrder
	 */
	public String getSortOrder() {
		return sortOrder;
	}
	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	/**
	 * @return the soId
	 */
	public String getSoId() {
		return soId;
	}
	/**
	 * @param soId the soId to set
	 */

	public void setSoId(String soId) {
		this.soId = soId;
	}



}
