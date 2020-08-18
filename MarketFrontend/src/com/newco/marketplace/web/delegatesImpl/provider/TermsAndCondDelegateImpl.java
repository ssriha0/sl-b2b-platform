package com.newco.marketplace.web.delegatesImpl.provider;



import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.provider.ITermsAndCondBO;
import com.newco.marketplace.vo.provider.TermsAndCondVO;
import com.newco.marketplace.web.delegates.provider.ITermsAndCondDelegate;
import com.newco.marketplace.web.dto.provider.TermsAndCondDto;
import com.newco.marketplace.web.utils.TermsAndCondMapper;

public class TermsAndCondDelegateImpl implements ITermsAndCondDelegate {

	private ITermsAndCondBO iTermsAndCondBO;
	private TermsAndCondMapper termsAndCondMapper;
//	private TermsAndCondVO termsAndCondVO;
	private static final Logger localLogger = Logger
	.getLogger(TermsAndCondDelegateImpl.class.getName());
	
	/**
	 * @param businessinfoBO
	 */
	public TermsAndCondDelegateImpl(ITermsAndCondBO termsAndCondBO, TermsAndCondMapper termsAndCondMapper) {
		iTermsAndCondBO = termsAndCondBO;
		this.termsAndCondMapper = termsAndCondMapper;
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.delegates.IBusinessinfoDelegate#save(com.newco.marketplace.web.dto.Businessinfo)
	 */
	public boolean save(TermsAndCondDto objTermsAndCondDto) throws Exception {
		TermsAndCondVO termsAndCondVO = new TermsAndCondVO();
		termsAndCondVO = termsAndCondMapper.convertDTOtoVO(objTermsAndCondDto, termsAndCondVO);
		return iTermsAndCondBO.save(termsAndCondVO);
	}
		
	public TermsAndCondVO converDtoToVo(TermsAndCondDto businessinfoDto, TermsAndCondVO businessinfoVO) {
		return (TermsAndCondVO) termsAndCondMapper.convertDTOtoVO(businessinfoDto, businessinfoVO);
	}

	public TermsAndCondDto converVoToDto(TermsAndCondVO termAndCondVO,
			TermsAndCondDto termAndCondDto) {
		
		return (TermsAndCondDto) termsAndCondMapper.convertVOtoDTO(termAndCondVO, termAndCondDto);
	}

	public TermsAndCondDto getData(TermsAndCondDto objTermsAndCondDto) throws Exception {
		TermsAndCondVO termsAndCondVO = new TermsAndCondVO();
		termsAndCondVO =  termsAndCondMapper.convertDTOtoVO(objTermsAndCondDto, termsAndCondVO);
		return converVoToDto(iTermsAndCondBO.getData(termsAndCondVO),new TermsAndCondDto());
	}

	public Integer getData(Integer id) throws Exception {
		Integer ind = iTermsAndCondBO.getData(id);
		return ind;
	}

	public void save(Integer id) throws Exception {
		iTermsAndCondBO.save(id);
		
	}
	
}
