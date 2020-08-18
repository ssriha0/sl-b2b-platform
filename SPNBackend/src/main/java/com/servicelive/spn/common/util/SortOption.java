package com.servicelive.spn.common.util;

/**
 * Options for sorting.
 */
public enum SortOption {
	
	/** ascending */
	ASCENDING(1, false),
	/** ascending ignore case */
	ASCENDING_IGNORE_CASE(1, true),
	/** descending */
	DESCENDING(-1, false),
	/** descending ignore case*/
	DESCENDING_IGNORE_CASE(-1, true);
	
	private int _option;
	private boolean _ignoreCase;
	
	private SortOption(int option, boolean ignoreCase) {
		_option = option;
		_ignoreCase = ignoreCase;
	}
	
	/**
	 * 
	 * @return sort option value
	 */
	public int getSortOptionValue() {
		return _option;
	}
	
	/**
	 * 
	 * @return whether case is ignored
	 */
	public boolean getIgnoreCase() {
		return _ignoreCase; 
	}
}