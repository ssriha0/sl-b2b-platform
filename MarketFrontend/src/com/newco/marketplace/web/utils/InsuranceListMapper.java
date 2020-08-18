/**
 * 
 */
package com.newco.marketplace.web.utils;

import com.newco.marketplace.web.dto.provider.InsuranceInfoDto;
import com.newco.marketplace.vo.provider.InsuranceTypesVO;


/**
 * @author KSudhanshu
 *
 */
public class InsuranceListMapper extends ObjectMapper {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertDTOtoVO(java.lang.Object,
	 *      java.lang.Object)
	 */
	public InsuranceTypesVO convertDTOtoVO(Object objectDto, Object objectVO) {
		InsuranceInfoDto insuranceInfoDto = (InsuranceInfoDto) objectDto;
		InsuranceTypesVO insuranceTypesVO = (InsuranceTypesVO) objectVO;
		if (objectDto != null && objectVO!=null) {
			insuranceTypesVO.setVendorId(insuranceInfoDto.getVendorId());
		
		}
		return insuranceTypesVO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertVOtoDTO(java.lang.Object,
	 *      java.lang.Object)
	 */
	public InsuranceInfoDto convertVOtoDTO(Object objectVo, Object objectDto) {
		InsuranceInfoDto insuranceInfoDto = (InsuranceInfoDto) objectDto;
		InsuranceTypesVO insuranceTypesVO = (InsuranceTypesVO) objectVo;
		
		if (objectVo != null && objectDto!= null) {
			insuranceInfoDto.setInsuranceList(insuranceTypesVO.getInsuranceList());
			insuranceInfoDto.setAddPolicy(insuranceTypesVO.isAddPolicy());
		}
		return insuranceInfoDto;
	}

}
