package com.servicelive.domain.spn.detached;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sldev
 *
 */
public class SPNSummaryFilterVO implements Serializable{
	
	
	private static final long serialVersionUID = 200903301L;
	private String stateCd;
	private Integer marketId;
	private Integer buyerId;
	private Integer addJoinInd=0;
	private List<String> stateCds = new ArrayList<String>();
	private List<Integer> marketIds = new ArrayList<Integer>();
	
	public Integer getAddJoinInd() {
		return addJoinInd;
	}
	public void setAddJoinInd(Integer addJoinInd) {
		this.addJoinInd = addJoinInd;
	}
	/**
	 * @return the stateCd
	 */
	public String getStateCd() {
		return stateCd;
	}
	/**
	 * @param stateCd the stateCd to set
	 */
	public void setStateCd(String stateCd) {
		this.stateCd = stateCd;
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
	/**
	 * @return the buyerId
	 */
	public Integer getBuyerId() {
		return buyerId;
	}
	/**
	 * @param buyerId the buyerId to set
	 */
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public List<String> getStateCds() {
		return stateCds;
	}
	public void setStateCds(List<String> stateCds) {
		this.stateCds = stateCds;
	}
	public List<Integer> getMarketIds() {
		return marketIds;
	}
	public void setMarketIds(List<Integer> marketIds) {
		this.marketIds = marketIds;
	}
	
}
