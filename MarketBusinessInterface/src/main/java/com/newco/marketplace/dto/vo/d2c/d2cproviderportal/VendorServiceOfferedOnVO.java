package com.newco.marketplace.dto.vo.d2c.d2cproviderportal;

import java.util.List;

public class VendorServiceOfferedOnVO {

	private Integer id;
	private List<LuOffersOnVO> luOffersOnVO;
	private LuOffersOnVO luOffersOnVOforUpdate;
	private String primaryIndustry;
	private String vendorId;
	private boolean offersOnFlag;
	//private VendorCategoryMap vendorCategoryMap;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public List<LuOffersOnVO> getLuOffersOnVO() {
		return luOffersOnVO;
	}
	public void setLuOffersOnVO(List<LuOffersOnVO> luOffersOnVO) {
		this.luOffersOnVO = luOffersOnVO;
	}
	public String getPrimaryIndustry() {
		return primaryIndustry;
	}
	public void setPrimaryIndustry(String primaryIndustry) {
		this.primaryIndustry = primaryIndustry;
	}
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	/*public VendorCategoryMap getVendorCategoryMap() {
		return vendorCategoryMap;
	}
	public void setVendorCategoryMap(VendorCategoryMap vendorCategoryMap) {
		this.vendorCategoryMap = vendorCategoryMap;
	}*/
	public boolean isOffersOnFlag() {
		return offersOnFlag;
	}
	public void setOffersOnFlag(boolean offersOnFlag) {
		this.offersOnFlag = offersOnFlag;
	}
	public LuOffersOnVO getLuOffersOnVOforUpdate() {
		return luOffersOnVOforUpdate;
	}
	public void setLuOffersOnVOforUpdate(LuOffersOnVO luOffersOnVOforUpdate) {
		this.luOffersOnVOforUpdate = luOffersOnVOforUpdate;
	}
	
}
