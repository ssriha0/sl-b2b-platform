package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import java.util.ArrayList;

import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;

/**
 * @author zizrale
 * 
 */
public abstract class RatingCalculator {

	private RatingCalculator chain;

	protected RatingCalculator() {

	}

	protected RatingCalculator(RatingCalculator calc) {
		this.chain = calc;
	}

	protected RatingCalculator getChain() {
		return chain;
	}

	/**
	 * @param bean
	 * @param providers
	 */
	public abstract void getFilteredList(RatingParameterBean bean,
			ArrayList<ProviderResultVO> providers);

}
