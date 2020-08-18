package com.servicelive.orderfulfillment.orderprep.processor.fileupload;

import java.util.List;

import com.servicelive.domain.so.BuyerReferenceType;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;
import com.servicelive.orderfulfillment.orderprep.buyer.IOrderBuyer;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderCustomRefEnhancer;

public class FileUploadCustomRefEnhancer extends AbstractOrderCustomRefEnhancer {

	private IMarketPlatformBuyerBO marketPlatformBuyerBO;
	
	public void enhanceOrder(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext) {
		//get buyer custom references and apply the id to the custom refs
		List<BuyerReferenceType> buyerRefTypes = marketPlatformBuyerBO.getBuyerReferenceTypes(serviceOrder.getBuyerId());
		int index = 0;
		for(SOCustomReference cus : serviceOrder.getCustomReferences()){
			BuyerReferenceType buyerRefType = buyerRefTypes.get(index);
			cus.setBuyerRefTypeId(buyerRefType.getBuyerRefTypeId());
			index++;
		}
	}
	
	@Override
	protected void addCustomReferences(ServiceOrder serviceOrder, IOrderBuyer orderBuyer) {
		//nothing to do
	}

	public void setMarketPlatformBuyerBO(IMarketPlatformBuyerBO marketPlatformBuyerBO) {
		this.marketPlatformBuyerBO = marketPlatformBuyerBO;
	}
	
	
}
