package com.newco.marketplace.dto.vo.skillTree;

import com.sears.os.vo.SerializableBaseVO;

/**
 * @author zizrale
 */
public class SearchExpressionVO extends SerializableBaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5387321101661243833L;
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
