package com.newco.marketplace.persistence.iDao.provider;

import com.newco.marketplace.vo.provider.TermsVO;


public interface ITermsDao {

	/**
	 * @param objBusinessinfoVO
	 * @return
	 */
	public TermsVO save(TermsVO objTermsVO);
	
	public TermsVO getData(TermsVO objTermsVO);
	
}
