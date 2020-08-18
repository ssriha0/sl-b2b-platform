package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.providerSearch.ProviderLanguageResultsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;

public class LanguageFilter extends RatingCalculator{

	public LanguageFilter(){
		super(null);
	}
	
	public LanguageFilter(RatingCalculator calc){
		super(calc);
	}
	
	@Override
	public void getFilteredList(RatingParameterBean bean, ArrayList<ProviderResultVO> providers){
		if (bean instanceof LanguageParameterBean) {
			LanguageParameterBean langBean = (LanguageParameterBean)bean;
	
			List<Integer> languageCriteria = langBean.getSelectedLangs();
			
			int providerListSize = providers.size();
			int counter = 0;
			// remove all providers without the given language
			for (int i = 0; i < providerListSize; i++){
				ProviderResultVO provider = providers.get(counter);
				// loop through the array of languages for each provider - 
				ArrayList<ProviderLanguageResultsVO> providerLanguages = provider.getLanguages();
				boolean hasLanguage = false;
				for(int k=0;k<languageCriteria.size();k++){					
					for (int j = 0; j < providerLanguages.size(); j++){
						hasLanguage = false;
						ProviderLanguageResultsVO language = providerLanguages.get(j);
						if (language.getProviderLanguageId() == languageCriteria.get(k).intValue()){
							hasLanguage = true;
							break;
						}
					}
					if(!hasLanguage){
						break;
					}
				}
				if(!hasLanguage){
					providers.remove(counter);
					counter--;
				}
				counter++;
			}

			//return filteredProviders;
		} else {
			if (getChain() != null){
				getChain().getFilteredList(bean, providers);
			}
		}
	}
}
