/**
 *
 */
package com.newco.marketplace.vo.searchportal;

import org.apache.commons.lang.StringUtils;

/**
 * @author hoza
 *
 */
public class SearchPortalBuyerVO extends BaseSearchPortalVO {

	/**
	 *
	 */
	private static final long serialVersionUID = 58662088452308913L;
	//I could have moved these two into the base one.. but then I am afraid that the bseVO might be too heavy loaded
	private SearchPortalUserVO user;
	private SearchPortalLocationVO location;
	private Boolean isChildResultNeeded = Boolean.FALSE;
	private String sortOrder;
    private String sortKey;
	/**
	 * @return the user
	 */
	public SearchPortalUserVO getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(SearchPortalUserVO user) {
		this.user = user;
	}
	/**
	 * @return the location
	 */
	public SearchPortalLocationVO getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(SearchPortalLocationVO location) {
		this.location = location;
	}
	/**
	 * @return the isChildResultNeeded
	 */
	public Boolean getIsChildResultNeeded() {
		return isChildResultNeeded;
	}
	/**
	 * @param isChildResultNeeded the isChildResultNeeded to set
	 */
	public void setIsChildResultNeeded(Boolean isChildResultNeeded) {
		this.isChildResultNeeded = isChildResultNeeded;
	}

	public boolean isFilterEmpty() {
		boolean result = false;
		if(
				( user.isFilterEmpty() )
				&&
				(location.isFilterEmpty())
				 &&
				( StringUtils.isBlank(getSoId()))
				){
			result = true;

		}
		return result;
	}
	@Override
	public String getSortOrder() {
		return sortOrder;
	}
	@Override
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getSortKey() {
		return sortKey;
	}
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}


}
