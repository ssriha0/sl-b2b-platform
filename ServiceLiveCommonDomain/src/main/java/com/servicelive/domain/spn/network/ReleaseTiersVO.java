package com.servicelive.domain.spn.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ReleaseTiersVO  implements Serializable, Comparable<ReleaseTiersVO>
{

	private static final long serialVersionUID = 5254692630787236707L;
	
	
	private Integer tierId;
	

	private SPNTierMinutes tierMinute;
	
	private List<Integer> tierPerformanceLevels = new ArrayList<Integer>();
	
	public List<Integer> getTierPerformanceLevels()
	{
		return tierPerformanceLevels;
	}
	public void setTierPerformanceLevels(List<Integer> tierPerformanceLevels)
	{
		this.tierPerformanceLevels = tierPerformanceLevels;
	}
	/**
	 * @return the tierMinute
	 */
	public SPNTierMinutes getTierMinute() {
		return tierMinute;
	}
	/**
	 * @param tierMinute the tierMinute to set
	 */
	public void setTierMinute(SPNTierMinutes tierMinute) {
		this.tierMinute = tierMinute;
	}

	
	
	public void addPerformanceLevel(Integer plevel) {
		this.getTierPerformanceLevels().add(plevel);
	}
	/**
	 * @return the tierId
	 */
	public Integer getTierId() {
		return tierId;
	}
	/**
	 * @param tierId the tierId to set
	 */
	public void setTierId(Integer tierId) {
		this.tierId = tierId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(ReleaseTiersVO o) {
		if(o.getTierId() != null  && this.getTierId() != null) {
			return (this.getTierId().intValue() -  o.getTierId().intValue() ) ;
		}
		return 0;
	}
	
	
	
}
