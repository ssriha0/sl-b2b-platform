package com.newco.marketplace.persistence.iDao.provider;

import com.newco.marketplace.vo.provider.TermsAndCondVO;


public interface ITermsAndCondDao {

	/**
	 * @param objBusinessinfoVO
	 * @return
	 */
	public TermsAndCondVO save(TermsAndCondVO objTermsAndCondVO);
	
	public TermsAndCondVO getData(TermsAndCondVO objTermsAndCondVO);
	
	public Integer getData(Integer id) throws Exception;
	
	public void save(Integer id) throws Exception;
}
