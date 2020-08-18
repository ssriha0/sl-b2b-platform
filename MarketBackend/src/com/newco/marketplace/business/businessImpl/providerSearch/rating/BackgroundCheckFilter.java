package com.newco.marketplace.business.businessImpl.providerSearch.rating;

import java.util.ArrayList;

import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;
import com.newco.marketplace.interfaces.ProviderConstants;

public class BackgroundCheckFilter extends RatingCalculator{

	public BackgroundCheckFilter(){
		super(null);
	}
	
	public BackgroundCheckFilter(RatingCalculator calc){
		super(calc);
	}
	
	@Override
	public void getFilteredList(RatingParameterBean bean, ArrayList<ProviderResultVO> providers) {
		 if (bean instanceof BackgroundCheckParameterBean) {			 
			 BackgroundCheckParameterBean backgroundCheckParameterBean = (BackgroundCheckParameterBean) bean;			 			 
			 Integer backgroundCheck = backgroundCheckParameterBean.getBackgroundCheck();
			 		 
			 int j = 0;
			 int size = providers.size();
			 for(int i=0;i<size;i++){
				 Integer vendorResourceBackgroundStatus = providers.get(j).getBackgroundCheckStatus();
				 
				 switch(backgroundCheck.intValue())
				 {
				 case 0://When Not Applicable. Do Nothing
					 	break;
				 case 1:// When Yes: Remove all the resources who are not yet cleared by service live.
					 	if(vendorResourceBackgroundStatus.intValue() != ProviderConstants.TEAM_MEMBER_BACKGROUND_CHECK_STATE_CLEAR.intValue()){
					 		providers.remove(j);
							j--;
					 	}					 		
					 	break;
				 case 2: // When No 
					 	if(vendorResourceBackgroundStatus.intValue() != ProviderConstants.TEAM_MEMBER_BACKGROUND_CHECK_STATE_NOT_STARTED.intValue() 
					 			&& vendorResourceBackgroundStatus.intValue() != ProviderConstants.TEAM_MEMBER_BACKGROUND_CHECK_STATE_PENDING_SUBMISSION.intValue()
					 			&& vendorResourceBackgroundStatus.intValue() != ProviderConstants.TEAM_MEMBER_BACKGROUND_CHECK_STATE_INPROCESS.intValue()){
					 		providers.remove(j);
							j--;
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
