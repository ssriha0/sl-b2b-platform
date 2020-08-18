package com.newco.marketplace.web.delegates.provider;

import java.util.HashMap;

import com.newco.marketplace.web.dto.provider.WarrantyDto;

public interface IWarrantyDelegate {

	public abstract int saveWarrantyInfo(WarrantyDto wdto);

	public abstract WarrantyDto getWarrantyData(WarrantyDto wdto);
	
	public abstract void deleteWarrantyInfo(WarrantyDto wdto);
	
	public abstract void updateWarrantyData(WarrantyDto wdto);
	
	public abstract WarrantyDto loadPage(WarrantyDto wdto);
	
	public abstract HashMap getMapLuWarrantyPeriods();	
}