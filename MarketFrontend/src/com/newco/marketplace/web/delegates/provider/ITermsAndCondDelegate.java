package com.newco.marketplace.web.delegates.provider;

import com.newco.marketplace.vo.provider.TermsAndCondVO;
import com.newco.marketplace.web.dto.provider.TermsAndCondDto;

public interface ITermsAndCondDelegate {
	/**
	 * @param objTermsAndCondDto
	 * @return
	 * @throws Exception
	 */
	public boolean save(TermsAndCondDto objTermAndCondDto) throws Exception;
		
	/**
	 * @param termsAndCondVO
	 * @param termsAndCondDto
	 * @return
	 */
	public TermsAndCondDto converVoToDto(TermsAndCondVO termAndCondVO, TermsAndCondDto termAndCondDto);
	
	/**
	 * @param termsAndCondDto
	 * @param termsAndCondVO
	 * @return
	 */
	public TermsAndCondVO converDtoToVo(TermsAndCondDto termAndCondDto,TermsAndCondVO termsAndCondVO);
	
	public TermsAndCondDto getData(TermsAndCondDto objTermsAndCondDto) throws Exception;
	public Integer getData(Integer id) throws Exception;
	public void save(Integer id) throws Exception;
	//public TermsAndCondDto getTermsCond() throws DelegateException;
}

