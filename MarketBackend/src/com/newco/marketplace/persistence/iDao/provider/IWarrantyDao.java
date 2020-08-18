package com.newco.marketplace.persistence.iDao.provider;

import java.util.HashMap;

import com.newco.marketplace.vo.provider.WarrantyVO;

public interface IWarrantyDao {
	public WarrantyVO query(WarrantyVO objWarrantyVO);
	public void insert(WarrantyVO objWarrantyVO);
	public void delete(WarrantyVO objWarrantyVO);
	public void update(WarrantyVO objWarrantyVO);
	public void updateWarrantyPartialData(WarrantyVO objWarrantyVO);
	public WarrantyVO loadPage(WarrantyVO objWarrantyVO);
	public HashMap getMapLuWarrantyPeriods();
}
