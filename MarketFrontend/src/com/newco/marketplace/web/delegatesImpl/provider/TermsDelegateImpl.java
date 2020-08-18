package com.newco.marketplace.web.delegatesImpl.provider;

import com.newco.marketplace.business.iBusiness.provider.ITermsBO;
import com.newco.marketplace.vo.provider.TermsVO;
import com.newco.marketplace.web.delegates.provider.ITermsDelegate;
import com.newco.marketplace.web.dto.provider.TermsDto;
import com.newco.marketplace.web.utils.TermsMapper;

public class TermsDelegateImpl implements ITermsDelegate {

	private ITermsBO iTermsBO;
	private TermsMapper termsMapper;
	//private TermsVO termsVO;
	
	/**
	 * @param businessinfoBO
	 */
	public TermsDelegateImpl(ITermsBO termsBO, TermsMapper termsMapper) {
		iTermsBO = termsBO;
		this.termsMapper = termsMapper;
		//this.termsVO = termsVO;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.delegates.IBusinessinfoDelegate#save(com.newco.marketplace.web.dto.Businessinfo)
	 */
	public boolean save(TermsDto objTermsDto) throws Exception {
		TermsVO termsVO = new TermsVO();
		termsVO = termsMapper.convertDTOtoVO(objTermsDto, termsVO);
		return iTermsBO.save(termsVO);
	}
		
	public TermsVO converDtoToVo(TermsDto businessinfoDto, TermsVO businessinfoVO) {
		return (TermsVO) termsMapper.convertDTOtoVO(businessinfoDto, businessinfoVO);
	}

	public TermsDto converVoToDto(TermsVO termVO,
			TermsDto termDto) {
		// TODO Auto-generated method stub
		return (TermsDto) termsMapper.convertVOtoDTO(termVO, termDto);
	}

	public TermsDto getData(TermsDto objTermsDto) throws Exception {
		TermsVO termsVO = new TermsVO();
		termsVO =  termsMapper.convertDTOtoVO(objTermsDto, termsVO);
		return converVoToDto(iTermsBO.getData(termsVO),new TermsDto());
	}
	
	public TermsDto sendEmailForNewUser(TermsDto objTermsDto, String provUserName) throws Exception
	{
		TermsVO termsVO = new TermsVO();
		termsVO =  termsMapper.convertDTOtoVO(objTermsDto, termsVO);
		termsVO = iTermsBO.sendEmailForNewUser(termsVO, provUserName);
		
		if (termsVO != null)
			return converVoToDto(termsVO, new TermsDto());
		else
			return objTermsDto;
	}

}
