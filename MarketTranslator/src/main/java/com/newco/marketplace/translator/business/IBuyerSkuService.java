package com.newco.marketplace.translator.business;

import java.util.Date;
import java.util.List;

import com.newco.marketplace.translator.dto.SkuPrice;
import com.newco.marketplace.translator.dto.Task;
import com.newco.marketplace.webservices.dto.serviceorder.CreateDraftRequest;

public interface IBuyerSkuService {

	public SkuPrice priceBuyerSku(SkuPrice sku, String zip, String storeCode, Integer buyerID) throws Exception;
	
	public void priceServiceOrder(	List<SkuPrice> skus, String storeCode, 
			CreateDraftRequest request) throws Exception;
	
	public Integer calculateLeadTime(List<SkuPrice> skus) throws Exception;
	
	public Date getServiceEndDate(List<SkuPrice> skus, Date serviceStartDate) throws Exception;
	
	public List<Task> translateSKU(Task sku) throws Exception;
	
	public Double calculateRetailPrice(String sku, String storeCode, Integer buyerID);
}
