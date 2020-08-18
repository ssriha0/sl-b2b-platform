package com.servicelive.orderfulfillment.orderprep.processor.fileupload;

import org.apache.commons.lang.StringUtils;

import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.so.BuyerOrderTemplate;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformBuyerBO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformDocumentServiceBO;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;
import com.servicelive.orderfulfillment.orderprep.buyer.BuyerOrderTemplateLoader;
import com.servicelive.orderfulfillment.orderprep.processor.common.AbstractOrderHdrEnhancer;

public class FileUploadOrderHdrEnhancer extends AbstractOrderHdrEnhancer {
	private IMarketPlatformBuyerBO marketPlatformBuyerBO;
	private IMarketPlatformDocumentServiceBO marketPlatformDocumentServiceBO;
	private BuyerOrderTemplateLoader buyerOrderTemplateLoader;

	@Override
	public void enhanceOrder(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext){
		logger.info(" inside enhanceOrder FileUploadOrderHdrEnhancer");
		Long buyerResourceId = serviceOrder.getBuyerResourceId();
		BuyerResource resource = marketPlatformBuyerBO.getBuyerResource(buyerResourceId);
		serviceOrder.setBuyerId(resource.getBuyer().getBuyerId().longValue());
		logger.debug("Buyer id in the service order " + serviceOrder.getBuyerId());
		serviceOrder.setBuyerContactId(resource.getBuyer().getContact().getContactId());
		Integer templateId = serviceOrder.getTemplateId();
		if(templateId != null){
			logger.info("SO ID: "+serviceOrder.getSoId());
			logger.info("TemplateId ID: "+templateId);
			this.setInfoFromTemplate(serviceOrder, buyerOrderTemplateLoader.getBuyerOrderTemplate(templateId));
		} else if(StringUtils.isNotBlank(serviceOrder.getLogoDocumentTitle())){
			Integer logoId = marketPlatformDocumentServiceBO.retrieveBuyerDocumentIdByTitleAndOwnerId(serviceOrder.getLogoDocumentTitle(), serviceOrder.getBuyerId().intValue());
			serviceOrder.setLogoDocumentId(logoId);
		}
		Long primarySkillCatId = 0L;
		if(StringUtils.isNotBlank(serviceOrder.getPrimarySkillCategory())){
			primarySkillCatId = quickLookupCollection.getSkillTreeLookup().getMainCategoryId(serviceOrder.getPrimarySkillCategory());
		}
		serviceOrder.setPrimarySkillCatId(primarySkillCatId.intValue());
		setBuyerInfo(serviceOrder, resource.getBuyer());
		initializeTotalDocSize(serviceOrder);
	}
	
	@Override
	protected void setMiscOrderHdrInfo(ServiceOrder serviceOrder, BuyerOrderTemplate buyerOrderTemplate) {
		//nothing to do here
	}

	public void setMarketPlatformBuyerBO(IMarketPlatformBuyerBO marketPlatformBuyerBO) {
		this.marketPlatformBuyerBO = marketPlatformBuyerBO;
	}

	public void setBuyerOrderTemplateLoader(BuyerOrderTemplateLoader buyerOrderTemplateLoader) {
		this.buyerOrderTemplateLoader = buyerOrderTemplateLoader;
	}

	public void setMarketPlatformDocumentServiceBO(
			IMarketPlatformDocumentServiceBO marketPlatformDocumentServiceBO) {
		this.marketPlatformDocumentServiceBO = marketPlatformDocumentServiceBO;
	}
}
