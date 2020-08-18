package com.newco.marketplace.dto.vo.d2c.d2cproviderportal;

import java.util.List;

import com.newco.marketplace.dto.vo.PrimaryIndustryDetailsVO;

public class PrimaryIndustryOffersOnDetailsVO {

	private List<PrimaryIndustryDetailsVO> primaryIndustryDetailsVO;
    private List<LuOffersOnVO> luOffersOnVO;
    
	
	public List<PrimaryIndustryDetailsVO> getPrimaryIndustryDetailsVO() {
		return primaryIndustryDetailsVO;
	}
	public void setPrimaryIndustryDetailsVO(
			List<PrimaryIndustryDetailsVO> primaryIndustryDetailsVO) {
		this.primaryIndustryDetailsVO = primaryIndustryDetailsVO;
	}
	public List<LuOffersOnVO> getLuOffersOnVO() {
		return luOffersOnVO;
	}
	public void setLuOffersOnVO(List<LuOffersOnVO> luOffersOnVO) {
		this.luOffersOnVO = luOffersOnVO;
	}
    
}
