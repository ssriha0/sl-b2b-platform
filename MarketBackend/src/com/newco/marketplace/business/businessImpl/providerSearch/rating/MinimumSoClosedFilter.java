package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import java.util.ArrayList;

import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;

/**
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.2 $ $Author: glacy $ $Date: 2008/05/02 21:23:39 $
 */

/*
 * Maintenance History: See bottom of file.
 */
public class MinimumSoClosedFilter extends RatingCalculator {
	
	public MinimumSoClosedFilter(){
		super(null);
	}
	
	public MinimumSoClosedFilter(RatingCalculator calc){
		super(calc);
	}
	
	@Override
	public void getFilteredList(RatingParameterBean bean,
			ArrayList<ProviderResultVO> providers) {
		
		if (bean instanceof MinimumSoClosedParameterBean) {
			MinimumSoClosedParameterBean minSOClosedBean = (MinimumSoClosedParameterBean) bean;
			
			ProviderResultVO providerResultVO = null;
			int listSize = providers.size();
			int j = 0;
			if (null != minSOClosedBean.getMinSoClosed()) {
				for (int i = 0; i < listSize; i++) {
					providerResultVO = providers.get(j);
					Integer soClosed = providerResultVO.getTotalSOCompleted();
					if (null == soClosed || soClosed.intValue() < minSOClosedBean.getMinSoClosed().intValue()) {
						providers.remove(j);
						j--;
					}
					j++;
				}
			}
			
		} else {
			if (getChain() != null){
				getChain().getFilteredList(bean, providers);
			}
		}
	}

}

/*
 * Maintenance History:
 * $Log: MinimumSoClosedFilter.java,v $
 * Revision 1.2  2008/05/02 21:23:39  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.1.2.2  2008/04/25 14:46:08  mhaye05
 * removed useless logging
 *
 * Revision 1.1.2.1  2008/04/18 00:23:52  mhaye05
 * added logic for inviting service pros to spn
 *
 */