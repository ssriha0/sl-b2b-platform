package com.newco.marketplace.web.delegates.provider;

import com.newco.marketplace.vo.provider.TermsVO;
import com.newco.marketplace.web.dto.provider.TermsDto;

public interface ITermsDelegate {
	/**
	 * @param objTermsDto
	 * @return
	 * @throws Exception
	 */
	public boolean save(TermsDto objTermDto) throws Exception;
		
	/**
	 * @param termsVO
	 * @param termsDto
	 * @return
	 */
	public TermsDto converVoToDto(TermsVO termVO, TermsDto termDto);
	
	/**
	 * @param termsDto
	 * @param termsVO
	 * @return
	 */
	public TermsVO converDtoToVo(TermsDto termDto,TermsVO termsVO);
	
	public TermsDto getData(TermsDto objTermsDto) throws Exception;
	
	/**
	 * 
	 * @param objTermsDto
	 * @return
	 * @throws Exception
	 */
	public TermsDto sendEmailForNewUser(TermsDto objTermsDto, String provUserName) throws Exception;
}

