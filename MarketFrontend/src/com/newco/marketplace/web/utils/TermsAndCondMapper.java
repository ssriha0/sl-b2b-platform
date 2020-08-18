package com.newco.marketplace.web.utils;

import com.newco.marketplace.vo.provider.TermsAndCondVO;
import com.newco.marketplace.web.dto.provider.TermsAndCondDto;

public class TermsAndCondMapper extends ObjectMapper{

	public TermsAndCondVO convertDTOtoVO(Object objectDto, Object objectVO) {
		TermsAndCondDto termsAndCondDto = (TermsAndCondDto) objectDto;
		TermsAndCondVO termsAndCondVO = (TermsAndCondVO) objectVO;

		termsAndCondVO.setVendorId(termsAndCondDto.getVendorId());
		termsAndCondVO.setAcceptTerms(termsAndCondDto.getAcceptTerms().booleanValue());
		termsAndCondVO.setId(termsAndCondDto.getId());
		termsAndCondVO.setTermsContent(termsAndCondDto.getTermsContent());
		termsAndCondVO.setSlBucksText(termsAndCondDto.getSlBucksText());			
		termsAndCondVO.setAcceptBucksTerms(termsAndCondDto.getAcceptBucksTerms().booleanValue());	
		termsAndCondVO.setAcceptTermsInd(termsAndCondDto.getAcceptTermsInd());
		termsAndCondVO.setAcceptBucksTermsInd(termsAndCondDto.getAcceptBucksTermsInd());
		//SLT-2236
		termsAndCondVO.setAcceptNewBucksTermsInd(termsAndCondDto.getAcceptNewBucksTermsInd());
		termsAndCondVO.setAcceptNewBucksTerms(termsAndCondDto.getAcceptNewBucksTerms());
		return termsAndCondVO;
		}

		public TermsAndCondDto convertVOtoDTO(Object objectVO, Object objectDto) {
			TermsAndCondDto termsAndCondDto = (TermsAndCondDto) objectDto;
			TermsAndCondVO termsAndCondVO = (TermsAndCondVO) objectVO;

			termsAndCondDto.setVendorId(termsAndCondVO.getVendorId());
			termsAndCondDto.setAcceptTerms(termsAndCondVO.getAcceptTerms());
			termsAndCondDto.setId(termsAndCondVO.getId());
			termsAndCondDto.setTermsContent(termsAndCondVO.getTermsContent());
			termsAndCondDto.setAcceptBucksTerms(termsAndCondVO.getAcceptBucksTerms());
			termsAndCondDto.setSlBucksText(termsAndCondVO.getSlBucksText());
			termsAndCondDto.setAcceptTermsInd(termsAndCondVO.getAcceptTermsInd());
			termsAndCondDto.setAcceptBucksTermsInd(termsAndCondVO.getAcceptBucksTermsInd());
			//SLT-2236
			termsAndCondDto.setAcceptNewBucksTerms(termsAndCondVO.isAcceptNewBucksTerms());
			termsAndCondDto.setAcceptNewBucksTermsInd(termsAndCondVO.getAcceptNewBucksTermsInd());
			return termsAndCondDto;
		}
		
}
