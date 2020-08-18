package com.newco.marketplace.persistence.iDao.leadprofile;

import com.newco.marketplace.vo.leadprofile.LeadProfileBillingRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileBillingResponseVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationRequestVO;
import com.newco.marketplace.vo.leadprofile.LeadProfileCreationResponseVO;

public interface ILeadSLDao {
	
	public LeadProfileCreationResponseVO createLeadProfile(LeadProfileCreationRequestVO leadProfileCreationRequestVO) throws Exception ;
	public LeadProfileBillingResponseVO createLeadProfileBilling(LeadProfileBillingRequestVO leadProfileBillingRequestVO) throws Exception ;	

}
