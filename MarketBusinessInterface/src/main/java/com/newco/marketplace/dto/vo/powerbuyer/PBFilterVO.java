package com.newco.marketplace.dto.vo.powerbuyer;

import com.sears.os.vo.SerializableBaseVO;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */

/*
 * Maintenance History: See bottom of file
 */
public class PBFilterVO extends SerializableBaseVO{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7483633071752890489L;
	private Integer filterId;
	private String filterName;
	private String filterDesc;
	private String destinationTab;
	private String destinationSubTab;
	private String filterOpt;
	private String dbQueryRoot;
	private String sortByColumnName;
	private String sortOrder;
	private Integer buyerId;
	private Integer buyerRefTypeId;
	private String buyerRefValue;
	private String searchBuyerId;
	
	public String getSearchBuyerId() {
		return searchBuyerId;
	}
	public void setSearchBuyerId(String searchBuyerId) {
		this.searchBuyerId = searchBuyerId;
	}
	public String getDestinationTab() {
		return destinationTab;
	}
	public void setDestinationTab(String destinationTab) {
		this.destinationTab = destinationTab;
	}
	
	public String getDestinationSubTab() {
		return destinationSubTab;
	}
	public void setDestinationSubTab(String destinationSubTab) {
		this.destinationSubTab = destinationSubTab;
	}
	public Integer getFilterId() {
		return filterId;
	}
	public void setFilterId(Integer filterId) {
		this.filterId = filterId;
	}
	public String getFilterName() {
		return filterName;
	}
	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}
	public String getFilterDesc() {
		return filterDesc;
	}
	public void setFilterDesc(String filterDesc) {
		this.filterDesc = filterDesc;
	}
	public String getFilterOpt() {
		return filterOpt;
	}
	public void setFilterOpt(String filterOpt) {
		this.filterOpt = filterOpt;
	}
	public String getDbQueryRoot() {
		return dbQueryRoot;
	}
	public void setDbQueryRoot(String dbQueryRoot) {
		this.dbQueryRoot = dbQueryRoot;
	}
	
	public String getSortByColumnName() {
		return sortByColumnName;
	}
	public void setSortByColumnName(String sortByColumnName) {
		this.sortByColumnName = sortByColumnName;
	}
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	
	public Integer getBuyerRefTypeId() {
		return buyerRefTypeId;
	}
	public void setBuyerRefTypeId(Integer buyerRefTypeId) {
		this.buyerRefTypeId = buyerRefTypeId;
	}
	public String getBuyerRefValue() {
		return buyerRefValue;
	}
	public void setBuyerRefValue(String buyerRefValue) {
		this.buyerRefValue = buyerRefValue;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		builder.append("filterId: " + filterId + "\n");
		builder.append("filterName: " + filterName + "\n");
		builder.append("filterDesc: " + filterDesc + "\n");
		builder.append("dbQueryRoot: " + dbQueryRoot + "\n");
		builder.append("sortByColumnName: " + sortByColumnName + "\n");
		builder.append("sortOrder: " + sortOrder + "\n");
		builder.append("buyerId: " + buyerId + "\n");
		
		return builder.toString();
	}
}
/*
 * Maintenance History
 * $Log: PBFilterVO.java,v $
 * Revision 1.7  2008/04/26 00:40:06  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.5.12.1  2008/04/23 11:41:13  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.6  2008/04/23 05:17:01  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.5  2008/02/14 23:44:24  mhaye05
 * Merged Feb4_release branch into head
 *
 * Revision 1.4.6.1  2008/02/08 02:31:52  spate05
 * serializing for session replication or updating serialuid
 *
 * Revision 1.4  2008/01/18 22:39:27  mhaye05
 * updated to make sure we use the buyer id when claiming the next so
 *
 * Revision 1.3  2008/01/15 20:48:05  mhaye05
 * added sort and filter attributes
 *
 * Revision 1.2  2008/01/14 20:36:50  mhaye05
 * added toString()
 *
 * Revision 1.1  2008/01/11 18:42:14  mhaye05
 * Initial check in
 *
 */