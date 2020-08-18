package com.newco.marketplace.web.utils;

import com.newco.marketplace.vo.provider.TermsVO;
import com.newco.marketplace.web.dto.provider.TermsDto;

public class TermsMapper extends ObjectMapper{

	public TermsVO convertDTOtoVO(Object objectDto, Object objectVO) {
			TermsDto termsDto = (TermsDto) objectDto;
			TermsVO termsVO = (TermsVO) objectVO;
			termsVO.setVendorId(termsDto.getVendorId());
			termsVO.setAcceptTerms(termsDto.getAcceptTerms());
			termsVO.setResourceId(termsDto.getResourceId());			
			termsVO.setTermsContent(termsDto.getTermsContent());	
			return termsVO;
		}

		public TermsDto convertVOtoDTO(Object objectVO, Object objectDto) {
			TermsDto termsDto = (TermsDto) objectDto;
			TermsVO termsVO = (TermsVO) objectVO;

			termsDto.setVendorId(termsVO.getVendorId());
			termsDto.setAcceptTerms(termsVO.getAcceptTerms());
			termsDto.setResourceId(termsVO.getResourceId());
			termsDto.setTermsContent(termsVO.getTermsContent());	
			
			return termsDto;
		}
		
}
