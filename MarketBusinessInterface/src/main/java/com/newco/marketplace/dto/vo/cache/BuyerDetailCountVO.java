package com.newco.marketplace.dto.vo.cache;

import java.io.Serializable;

@org.jboss.cache.pojo.annotation.Replicable
public class BuyerDetailCountVO  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6393725189548478764L;
	private Integer sentProviders =0;
	private Integer conditionalAccept =0;
	private Integer rejected = 0;
	private Double spendLimit =0.0;
	public Integer getSentProviders() {
		return sentProviders;
	}
	public void setSentProviders(Integer sentProviders) {
		this.sentProviders = sentProviders;
	}
	public Integer getConditionalAccept() {
		return conditionalAccept;
	}
	public void setConditionalAccept(Integer conditionalAccept) {
		this.conditionalAccept = conditionalAccept;
	}
	public Integer getRejected() {
		return rejected;
	}
	public void setRejected(Integer rejected) {
		this.rejected = rejected;
	}
	public Double getSpendLimit() {
		return spendLimit;
	}
	public void setSpendLimit(Double spendLimit) {
		this.spendLimit = spendLimit;
	}

}
