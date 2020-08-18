package com.newco.marketplace.criteria;

public class SortCriteria implements ICriteria{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2907372929742068623L;
	private String sortColumnName;
	private String sortOrder;
	
	public String getSortColumnName() {
		return sortColumnName;
	}

	public void setSortColumnName(String sortColumnName) {
		this.sortColumnName = sortColumnName;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean isSet() {
		// TODO Auto-generated method stub
		return false;
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}