package com.newco.marketplace.dto.vo.spn;

import java.util.List;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */

public class SPNMemberSearchRequestVO extends SerializableBaseVO {

	private static final long serialVersionUID = -6716473773532820010L;
	private List<Integer> searchStatuses;
	private String sortColumnName;
	private String sortOrder;
	private Integer startIndex;
	private Integer numberOfRecords;
	private Integer spnId;
	private Integer marketId;
	
	/**
	 * @return the searchStatuses
	 */
	public List<Integer> getSearchStatuses() {
		return searchStatuses;
	}
	/**
	 * @param searchStatuses the searchStatuses to set
	 */
	public void setSearchStatuses(List<Integer> searchStatuses) {
		this.searchStatuses = searchStatuses;
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
	 * @return the startIndex
	 */
	public Integer getStartIndex() {
		return startIndex;
	}
	/**
	 * @param startIndex the startIndex to set
	 */
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	/**
	 * @return the numberOfRecords
	 */
	public Integer getNumberOfRecords() {
		return numberOfRecords;
	}
	/**
	 * @param numberOfRecords the numberOfRecords to set
	 */
	public void setNumberOfRecords(Integer numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}
	/**
	 * @return the spnId
	 */
	public Integer getSpnId() {
		return spnId;
	}
	/**
	 * @param spnId the spnId to set
	 */
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	/**
	 * @return the marketId
	 */
	public Integer getMarketId() {
		return marketId;
	}
	/**
	 * @param marketId the marketId to set
	 */
	public void setMarketId(Integer marketId) {
		this.marketId = marketId;
	}
	
	
}
