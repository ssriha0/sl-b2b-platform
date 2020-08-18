package com.newco.marketplace.business.iBusiness.provider;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.provider.TermsVO;

public interface ITermsBO {
	/**
	 * @param objBusinessinfoVO TODO
	 * @return
	 * @throws Exceptiona
	 */
	public boolean save(TermsVO objTermsVO) throws Exception;
	
	public TermsVO getData(TermsVO objTermsVO) throws Exception;
	
	public TermsVO sendEmailForNewUser(TermsVO objTermsVO, String provUserName) throws BusinessServiceException;
}
