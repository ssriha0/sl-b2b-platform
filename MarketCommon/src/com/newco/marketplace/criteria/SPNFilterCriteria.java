package com.newco.marketplace.criteria;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.2 $ $Author: glacy $ $Date: 2008/05/02 21:24:45 $
 */

public class SPNFilterCriteria extends FilterCriteria {

	public SPNFilterCriteria() {
	}
	
	public SPNFilterCriteria(Integer[] status, String subStatus) {
		super(status, subStatus,null,null, null);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = -5737673644803745885L;
	private Integer marketId;
	private boolean allMarkets; 
	private boolean isSet = false;
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.criteria.ICriteria#isSet()
	 */
	public boolean isSet() {
		return isSet;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.criteria.ICriteria#reset()
	 */
	public void reset() {
		marketId = null;
		super.reset();
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
		isSet = true;
	}

	/**
	 * @return the allMarkets
	 */
	public boolean isAllMarkets() {
		return allMarkets;
	}

	/**
	 * @param allMarkets the allMarkets to set
	 */
	public void setAllMarkets(boolean allMarkets) {
		this.allMarkets = allMarkets;
		isSet = true;
	}

}
