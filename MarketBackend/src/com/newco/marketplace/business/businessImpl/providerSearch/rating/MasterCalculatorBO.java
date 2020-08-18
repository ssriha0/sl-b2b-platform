package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import java.util.ArrayList;

import com.newco.marketplace.business.iBusiness.providersearch.IMasterCalculatorBO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;

/**
 * $Revision: 1.8 $ $Author: glacy $ $Date: 2008/05/02 21:23:37 $
 */

/*
 * Maintenance History: See bottom of file.
 */
public class MasterCalculatorBO implements IMasterCalculatorBO{

	private RatingCalculator ratingCalculator;

	public void setRatingCalculator(RatingCalculator ratingCalculator) {
		this.ratingCalculator = ratingCalculator;
	}
	
	public ArrayList<ProviderResultVO> getFilteredProviderList(ArrayList<RatingParameterBean> criteriaBeans, ArrayList<ProviderResultVO> providers){
		
		for (RatingParameterBean criteriaBean : criteriaBeans) {
			ratingCalculator.getFilteredList(criteriaBean, providers);
		}
		return providers;
	}
}
/*
 * Maintenance History:
 * $Log: MasterCalculatorBO.java,v $
 * Revision 1.8  2008/05/02 21:23:37  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.6.46.1  2008/04/18 00:23:52  mhaye05
 * added logic for inviting service pros to spn
 *
 */
