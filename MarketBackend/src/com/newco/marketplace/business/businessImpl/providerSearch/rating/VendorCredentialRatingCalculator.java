package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.provider.VendorCredentialsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision: 1.2 $ $Author: glacy $ $Date: 2008/05/02 21:23:37 $
 */

/*
 * Maintenance History: See bottom of file.
 */
public class VendorCredentialRatingCalculator extends RatingCalculator {

	
	public VendorCredentialRatingCalculator(){
		super(null);
	}
	
	public VendorCredentialRatingCalculator(RatingCalculator calc){
		super(calc);
	}
	
	/* (non-Javadoc)
	 * @see com.newco.marketplace.business.businessImpl.providerSearch.rating.RatingCalculator#getFilteredList(com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean, java.util.ArrayList)
	 */
	@Override
	public void getFilteredList(RatingParameterBean bean,
			ArrayList<ProviderResultVO> providers) {

		if (bean instanceof VendorCredentialParameterBean) {
			VendorCredentialParameterBean credBean = (VendorCredentialParameterBean)bean;

			Integer credentialCategory = credBean.getCredentialCategoryId();
			Integer credentialType = credBean.getCredentialTypeId();
			
			if (null != credentialType) {
				int providerListSize = providers.size();
				int counter = 0;
				// remove all providers without the given credential
				for (int i = 0; i < providerListSize; i++){
					ProviderResultVO provider = providers.get(counter);

					List<VendorCredentialsVO> vendorCredentials = provider.getVendorCredentials();
					boolean hasCredential = false;
					for (int j = 0; j < vendorCredentials.size(); j++){
						VendorCredentialsVO credential = vendorCredentials.get(j);
						if ( (credential.getTypeId() == credentialType.intValue() && 
							credentialCategory != null &&
							credentialCategory.intValue() == 0) || 
							(credential.getTypeId() == credentialType.intValue() && 
							 credentialCategory != null &&
							 credentialCategory.intValue() == credential.getCategoryId())) {
							hasCredential = true;
						}
					}
					if(!hasCredential){
						providers.remove(counter);
						counter--;
					}
					counter++;
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
 * $Log: VendorCredentialRatingCalculator.java,v $
 * Revision 1.2  2008/05/02 21:23:37  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.1.2.2  2008/04/25 14:46:08  mhaye05
 * removed useless logging
 *
 * Revision 1.1.2.1  2008/04/23 14:01:00  mhaye05
 * backend for spn camaign batch job
 *
 */