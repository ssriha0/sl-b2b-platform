package com.newco.marketplace.business.iBusiness.provider;

import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.vo.provider.TermsAndCondVO;

public interface ITermsAndCondBO {
	/**
	 * @param objBusinessinfoVO TODO
	 * @return
	 * @throws Exceptiona
	 */
	public boolean save(TermsAndCondVO objTermsAndCondVO) throws Exception;
	
	public TermsAndCondVO getData(TermsAndCondVO objTermsAndCondVO) throws Exception;
	
	public Integer getData(Integer id) throws Exception;
	
	public void save(Integer id) throws Exception;
	
	public TermsAndCondVO getTermsCond() throws BusinessServiceException;
	
	public TermsAndCondVO getBucksTermsCond() throws BusinessServiceException;
}
