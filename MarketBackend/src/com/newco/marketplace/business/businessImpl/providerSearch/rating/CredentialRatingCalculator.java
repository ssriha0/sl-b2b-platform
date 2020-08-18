package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import java.util.ArrayList;

import com.newco.marketplace.dto.vo.providerSearch.ProviderCredentialResultsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;

public class CredentialRatingCalculator extends RatingCalculator{
	public CredentialRatingCalculator(){
		super(null);
	}
	
	public CredentialRatingCalculator(RatingCalculator calc){
		super(calc);
	}
	
	@Override
	public void getFilteredList(RatingParameterBean bean, ArrayList<ProviderResultVO> providers){
		if (bean instanceof CredentialParameterBean) {
    		CredentialParameterBean credBean = (CredentialParameterBean)bean;

			Integer credentialCriteria = credBean.getCredentialId();
			Integer credentialType = credBean.getCredentialTypeId();
			
			int providerListSize = providers.size();
			int counter = 0;
			// remove all providers without the given credential
			for (int i = 0; i < providerListSize; i++){
				ProviderResultVO provider = providers.get(counter);
				// loop through the array of creds for each provider - 
				// if come across on, add that provider to the filteredProviders list
				ArrayList<ProviderCredentialResultsVO> providerCredentials = provider.getCredentials();
				boolean hasCredential = false;
				for (int j = 0; j < providerCredentials.size(); j++){
					ProviderCredentialResultsVO credential = providerCredentials.get(j);
					if(credentialType != null && credentialCriteria != null && credentialCriteria.intValue() == 0){
						if(credentialType.intValue() == credential.getProviderCredentialTypeId())
							hasCredential = true;
					}else if(credentialCriteria != null && credentialCriteria.intValue() != 0
							&& credential.getProviderCredentialId() == credentialCriteria.intValue()){
						hasCredential = true;
					}
				}
				if(!hasCredential){
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
