package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import java.util.ArrayList;

import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;

/**
 * $Revision: 1.8 $ $Author: glacy $ $Date: 2008/05/02 21:23:38 $
 */

public class StarRatingCalculator extends RatingCalculator{

	/**
	 * 
	 */
	public StarRatingCalculator(){
		super(null);
	}
	
	/**
	 * @param calc
	 */
	public StarRatingCalculator(RatingCalculator calc){
		super(calc);
	}

	@Override
	public void getFilteredList(RatingParameterBean bean, ArrayList<ProviderResultVO> providers){
		if (bean instanceof StarParameterBean) {
			StarParameterBean starBean = (StarParameterBean)bean;

			Double starCriteria = starBean.getNumberOfStars();
			
			int providerListSize = providers.size();
			int j = 0;
			// remove all providers with < given rating
			for (int i = 0; i < providerListSize; i++){
				ProviderResultVO provider = providers.get(j);
				if (provider.getProviderStarRating() != null && 
						provider.getProviderStarRating().getHistoricalRating() != null && 
						provider.getProviderStarRating().getHistoricalRating().doubleValue() < starCriteria.doubleValue()){
					providers.remove(j);
					j--;
				}
				else if((provider.getProviderStarRating() == null || provider.getProviderStarRating().getHistoricalRating() == null) 
						&& !starBean.isIncludeNonRated()){
					providers.remove(j);
					j--;
				}
				j++;
			}
		} else {
			if (getChain() != null){
				getChain().getFilteredList(bean, providers);
			}
		}
	}
}
