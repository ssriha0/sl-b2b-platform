package com.newco.marketplace.business.iBusiness.providersearch;

import java.util.ArrayList;

import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;

public interface IMasterCalculatorBO {
	public ArrayList<ProviderResultVO> getFilteredProviderList(ArrayList<RatingParameterBean> criteriaBeans, ArrayList<ProviderResultVO> providers);

}
