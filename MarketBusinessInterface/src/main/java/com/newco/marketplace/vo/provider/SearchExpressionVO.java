package com.newco.marketplace.vo.provider;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author zizrale
 */
public class SearchExpressionVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1915436605919115498L;
	private String searchExpression;

	/** @return String searchExpression */
	public String getSearchExpression() {
		return searchExpression;
	}

	/** @param searchExpression */
	public void setSearchExpression(String searchExpression) {
		this.searchExpression = searchExpression;
	}
	
}
