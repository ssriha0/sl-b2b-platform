package com.newco.marketplace.business.iBusiness.provider;

import java.util.HashMap;

import com.newco.marketplace.vo.provider.WarrantyVO;



public interface IWarrantyBO {
	public int saveWarrantyData(WarrantyVO objWarrantyVO);
	public WarrantyVO getWarrantyData(WarrantyVO objWarrantyVO);
	public void deleteWarrantyData(WarrantyVO objWarrantyVO);
	public void updateWarrantyData(WarrantyVO objWarrantyVO);
	public WarrantyVO loadPage(WarrantyVO objWarrantyVO);
	public HashMap getMapLuWarrantyPeriods();
}
