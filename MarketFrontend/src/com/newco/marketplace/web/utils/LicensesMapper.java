/**
 * 
 */
package com.newco.marketplace.web.utils;

import com.newco.marketplace.vo.provider.LicensesVO;
import com.newco.marketplace.web.dto.provider.LicensesDto;

/**
 * @author MTedder
 *
 */
public class LicensesMapper extends ObjectMapper {

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertDTOtoVO(java.lang.Object, java.lang.Object)
	 */
	public LicensesVO convertDTOtoVO(Object objectDto, Object objectVO) {
		LicensesDto ldto = (LicensesDto) objectDto;
		LicensesVO lvo = (LicensesVO) objectVO;
		
		lvo.setVendorID(ldto.getVendorID());
		lvo.setAddCredentialToFile(ldto.getAddCredentialToFile());
		
		return lvo;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertVOtoDTO(java.lang.Object, java.lang.Object)
	 */
	public LicensesDto convertVOtoDTO(Object objectVO, Object objectDto) {
		LicensesDto ldto = (LicensesDto) objectDto;
		LicensesVO lvo = (LicensesVO) objectVO;
		
		ldto.setVendorID(lvo.getVendorID());
		ldto.setAddCredentialToFile(lvo.getAddCredentialToFile());
		
		return ldto;
	}

}
