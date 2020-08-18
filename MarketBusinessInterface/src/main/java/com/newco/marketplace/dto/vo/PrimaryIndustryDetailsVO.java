package com.newco.marketplace.dto.vo;

import java.util.List;

import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.LuOffersOnVO;
import com.newco.marketplace.dto.vo.d2c.d2cproviderportal.VendorServiceOfferedOnVO;
import com.sears.os.vo.SerializableBaseVO;

/**
 * 
 * @author rranja1
 *
 */
public class PrimaryIndustryDetailsVO  extends SerializableBaseVO{

	private static final long serialVersionUID = 1L;
	
	private int primaryIndustryId;
	private String primaryIndustryName;
	private String primaryIndustryType;
	private boolean rateCardAdded;
	private int servicesOfferedCount;
	private int servicesOptedCount;
	//Added For D2c
	private VendorServiceOfferedOnVO vendorServiceOfferedOnVO;
	
	private List<LuOffersOnVO> luOffersOnVO;
	
	public int getPrimaryIndustryId() {
		return primaryIndustryId;
	}
	public void setPrimaryIndustryId(int primaryIndustryId) {
		this.primaryIndustryId = primaryIndustryId;
	}
	public String getPrimaryIndustryName() {
		return primaryIndustryName;
	}
	public void setPrimaryIndustryName(String primaryIndustryName) {
		this.primaryIndustryName = primaryIndustryName;
	}
	public boolean isRateCardAdded() {
		return rateCardAdded;
	}
	public void setRateCardAdded(boolean rateCardAdded) {
		this.rateCardAdded = rateCardAdded;
	}
	public int getServicesOfferedCount() {
		return servicesOfferedCount;
	}
	public void setServicesOfferedCount(int servicesOfferedCount) {
		this.servicesOfferedCount = servicesOfferedCount;
	}
	public int getServicesOptedCount() {
		return servicesOptedCount;
	}
	public void setServicesOptedCount(int servicesOptedCount) {
		this.servicesOptedCount = servicesOptedCount;
	}
	public String getPrimaryIndustryType() {
		return primaryIndustryType;
	}
	public void setPrimaryIndustryType(String primaryIndustryType) {
		this.primaryIndustryType = primaryIndustryType;
	}
	public VendorServiceOfferedOnVO getVendorServiceOfferedOnVO() {
		return vendorServiceOfferedOnVO;
	}
	public void setVendorServiceOfferedOnVO(
			VendorServiceOfferedOnVO vendorServiceOfferedOnVO) {
		this.vendorServiceOfferedOnVO = vendorServiceOfferedOnVO;
	}
	public List<LuOffersOnVO> getLuOffersOnVO() {
		return luOffersOnVO;
	}
	public void setLuOffersOnVO(List<LuOffersOnVO> luOffersOnVO) {
		this.luOffersOnVO = luOffersOnVO;
	}
	

}
