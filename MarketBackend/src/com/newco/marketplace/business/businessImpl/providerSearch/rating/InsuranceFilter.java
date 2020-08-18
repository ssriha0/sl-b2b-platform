package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.providerSearch.InsuranceResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;

public class InsuranceFilter extends RatingCalculator{
	
	public InsuranceFilter(){
		super(null);
	}
	
	public InsuranceFilter(RatingCalculator calc){
		super(calc);
	}
	
	@Override
	public void getFilteredList(RatingParameterBean bean, ArrayList<ProviderResultVO> providers) {
		if (bean instanceof InsuranceParameterBean) {			 
				   		
			 InsuranceParameterBean insuranceBean = (InsuranceParameterBean) bean;			 
			 			 
			 Integer backgroundCheck = insuranceBean.getInsuranceStatus();
			 List<Integer> searchInsuranceList = insuranceBean.getInsuranceTypes();
			 boolean isVerified = insuranceBean.getVerifiedByServiceLive().booleanValue();
			 int j = 0;
			 int size = providers.size();
			 for(int i=0;i<size;i++){
				 List<InsuranceResultVO> vendorInsuranceTypes = providers.get(j).getVendorInsuranceTypes();
				
				 boolean found = false;				
				 switch(backgroundCheck.intValue())
				 {
				 case 0:// When Not Applicable. Do Nothing
					 	break;
				 case 1:// When Yes: Remove all the resources who are not yet cleared.
					 	for(int k=0;k<searchInsuranceList.size();k++){
					 		found = false; 
					 		for(int l=0;l<vendorInsuranceTypes.size();l++){
					 			if(vendorInsuranceTypes.get(l) != null){
					 				 Integer vendorInsuranceType = vendorInsuranceTypes.get(l).getVendorInsuranceTypes();
									 boolean verifiedBySL = vendorInsuranceTypes.get(l).getVerifiedByServiceLive();
						 			if(vendorInsuranceType != null && searchInsuranceList.get(k).intValue() == vendorInsuranceType.intValue()){
							 			if(isVerified ? verifiedBySL : true){
							 				found = true;
							 				break;
							 			}
						 			}
					 			}					 			
					 		}
					 		if(!found){
					 			providers.remove(j);
								j--;	
								break;
					 		}
					 	}					 						 		
					 	break;
				 case 2:// When No. Remove the provider who have the specified insurance
					 	for(int k=0;k<searchInsuranceList.size();k++){
					 		found = false; 
					 		for(int l=0;l<vendorInsuranceTypes.size();l++){
					 			if(vendorInsuranceTypes.get(l) != null){
					 				 Integer vendorInsuranceType = vendorInsuranceTypes.get(l).getVendorInsuranceTypes();									 
						 			if(vendorInsuranceType != null && searchInsuranceList.get(k).intValue() == vendorInsuranceType.intValue()){
						 				found = true;
						 				break;
						 			}
					 			}					 			
					 		}
					 		if(found){
					 			providers.remove(j);
								j--;	
								break;
					 		}
					 	}		
					 	break;
				}
				j++;
			 }
		 } 
		 else{
			if (getChain() != null){
				getChain().getFilteredList(bean, providers);
			}
		 }
	}
}
