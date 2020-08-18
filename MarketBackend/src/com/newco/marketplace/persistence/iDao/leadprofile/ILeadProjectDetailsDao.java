package com.newco.marketplace.persistence.iDao.leadprofile;

import com.newco.marketplace.vo.leadprofile.GetLeadProjectTypeRequestVO;
import com.newco.marketplace.vo.leadprofile.GetLeadProjectTypeResponseVO;


public interface ILeadProjectDetailsDao {
	
	public GetLeadProjectTypeResponseVO getleadProjectTypeAndRates(GetLeadProjectTypeRequestVO getLeadProjectTypeRequestVO) throws Exception ;

}
