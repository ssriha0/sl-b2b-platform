package com.servicelive.orderfulfillment.orderprep.processor.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.buyer.BuyerFeatureSet;
import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.domain.so.BuyerOrderTemplate;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.marketplatform.common.vo.SPNetHdrVO;
import com.servicelive.marketplatform.provider.domain.SkillNode;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.domain.SOCustomReference;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.PartSupplierType;
import com.servicelive.orderfulfillment.domain.type.ProblemType;
import com.servicelive.orderfulfillment.domain.type.SOPriceType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.orderprep.OrderEnhancementContext;
import org.apache.commons.lang.StringUtils;

public abstract class AbstractOrderHdrEnhancer extends AbstractOrderEnhancer {

    protected QuickLookupCollection quickLookupCollection;
    protected IServiceOrderDao serviceOrderDao;
  
	public void enhanceOrder(ServiceOrder serviceOrder, OrderEnhancementContext orderEnhancementContext) {
        String templateName = serviceOrder.getCustomRefValue(SOCustomReference.CREF_TEMPLATE_NAME);
        logger.info("Template custom reference:"+ templateName);
        if(serviceOrder.isCreateWithOutTasks()){
        	logger.info("inside AbstractOrderHdrEnhancer::before buyer check");
        	if(orderEnhancementContext.getOrderBuyer().getBuyerId().equals(OFConstants.SEARS_BUYER)){
        		logger.info("inside sears ::before calling application prop::" +orderEnhancementContext.getOrderBuyer().getBuyerId());
        		templateName = serviceOrderDao.getDefaultTemplate(OFConstants.DEFAULT_TEMPLATE_FOR_SEARS);
        		logger.info("inside sears ::after calling application prop::" +templateName);
        	}
        	else if(orderEnhancementContext.getOrderBuyer().getBuyerId().equals(OFConstants.ASSURANT_BUYER)){
        		logger.info("inside assurant ::before calling application prop::" +orderEnhancementContext.getOrderBuyer().getBuyerId());
        		templateName = serviceOrderDao.getDefaultTemplate(OFConstants.DEFAULT_TEMPLATE_FOR_ASSURANT);
        		logger.info("inside assurant ::before calling application prop::" +templateName);
        	}
        }
        BuyerOrderTemplate buyerOrderTemplate = null; 
        if (templateName != null) {
            buyerOrderTemplate = orderEnhancementContext.getOrderBuyer().getTemplateMap().getTemplate(templateName);
            validationUtil.addErrorsIfNull(buyerOrderTemplate, serviceOrder.getValidationHolder(), ProblemType.MissingTemplate);
            setInfoFromTemplate(serviceOrder, buyerOrderTemplate);
        }else{
        	SOWorkflowControls soWorkflowControls = serviceOrder.getSOWorkflowControls() == null?new SOWorkflowControls():serviceOrder.getSOWorkflowControls();
        	soWorkflowControls.setPerformanceScore(0.00);
        	soWorkflowControls.setTierRouteInd(false);
        	serviceOrder.setSOWorkflowControls(soWorkflowControls);
        }
        setPrimarySkillId(serviceOrder);
        setBuyerInfo(serviceOrder, orderEnhancementContext.getOrderBuyer().getBuyerInfo());
        initializeTotalDocSize(serviceOrder);

        setMiscOrderHdrInfo(serviceOrder, buyerOrderTemplate);
        
        // set invoicePartsInd="NO_PARTS_ADDED" for In home
        SOWorkflowControls soWorkflowControlsForNoPartsInd = serviceOrder.getSOWorkflowControls() == null?new SOWorkflowControls():serviceOrder.getSOWorkflowControls();
        logger.info("Buyer ID from context:"+ orderEnhancementContext.getOrderBuyer().getBuyerId());
    	logger.info("OFConstants.HSR_BUYER_ID"+ OFConstants.HSR_BUYER_ID);
    	 if(orderEnhancementContext.getOrderBuyer().getBuyerId().equals(OFConstants.HSR_BUYER_ID)){
    		 soWorkflowControlsForNoPartsInd.setInvoicePartsInd("NO_PARTS_ADDED");
         	serviceOrder.setSOWorkflowControls(soWorkflowControlsForNoPartsInd);
         }
        
    }

    protected abstract void setMiscOrderHdrInfo(ServiceOrder serviceOrder, BuyerOrderTemplate buyerOrderTemplate);

    private void setPrimarySkillId(ServiceOrder serviceOrder) {
    	logger.info("Inside setting primary SkillId");
    	if(null != serviceOrder.getPrimaryTask()){
    		logger.info("Primary Task:"+ serviceOrder.getPrimaryTask());
    		Integer primaryTaskNodeId = serviceOrder.getPrimaryTask().getSkillNodeId();
    		logger.info("Primary Task Id" + primaryTaskNodeId);
	        if (primaryTaskNodeId != null) {
	            SkillNode primaryTaskNode = quickLookupCollection.getSkillTreeLookup().getSkillNodeById(primaryTaskNodeId.longValue());
	            serviceOrder.setPrimarySkillCatId(primaryTaskNode.getRootNodeId().intValue());
	        }
	       
    	}
    }

    protected void setInfoFromTemplate(ServiceOrder serviceOrder, BuyerOrderTemplate buyerOrderTemplate) {
    	logger.info("inside setInfoFromTemplate.");
     if(null!=serviceOrder.getBuyerId()){
    	if(OFConstants.ATT_BUYER==serviceOrder.getBuyerId().intValue() || OFConstants.FLUSHMATE_BUYER==serviceOrder.getBuyerId().intValue()){	
    	 logger.info(" an AT&T and Flushmate buyer");
    	 // If the overview is not set from the API layer, set the data from the template.
    	 if(null==serviceOrder.getSowDs() || ("").equals(serviceOrder.getSowDs().trim())){
    		 // serviceOrder.setSowDs(fixStringIfNull(serviceOrder.getSowDs()) + fixStringIfNull(buyerOrderTemplate.getOverview()));   
    		 serviceOrder.setSowDs(fixStringIfNull(buyerOrderTemplate.getOverview()));   
    	 }
         // If the parts supplied by is not set from the API layer, set the data from the template.
    	 if(null==serviceOrder.getPartsSupplier()){
    		 if(buyerOrderTemplate.getPartsSuppliedBy() !=null)
    			 serviceOrder.setPartsSupplier(PartSupplierType.fromId(buyerOrderTemplate.getPartsSuppliedBy()));
    	 }
    	 // If the buyer terms and conditions is not set from the API layer, set the data from the template.
    	 if(null==serviceOrder.getBuyerTermsCond() ||  ("").equals(serviceOrder.getBuyerTermsCond().trim())){
    		 serviceOrder.setBuyerTermsCond(buyerOrderTemplate.getTerms());
    	 }
    	 // If the instructions is not set from the API layer, set the data from the template.
    	 if(null==serviceOrder.getProviderInstructions() || ("").equals(serviceOrder.getProviderInstructions().trim())){
    		 serviceOrder.setProviderInstructions(buyerOrderTemplate.getSpecialInstructions());
    	 }
    	 // If the call to confirm is not set from the API layer, set the data from the template.
    	 if(null==serviceOrder.getProviderServiceConfirmInd()){
    		 serviceOrder.setProviderServiceConfirmInd(buyerOrderTemplate.getConfirmServiceTime()); 
    	 }
    	 
     }else{  
    	 logger.info("not an AT&T buyer");
    	 String str = ( StringUtils.isBlank(serviceOrder.getSowTitle()) ) ?  "" : " ";
    	 //SL- 20888     		
    	 // for SL_Direct buyer set title as title from API request only
    	 //SL-21243 : For buyer 513590, set the title from request only
    	 if(OFConstants.SL_DIRECT_BUYER.intValue() == serviceOrder.getBuyerId().intValue() ||
    			 OFConstants.AMERICAN_BUYER.intValue() == serviceOrder.getBuyerId().intValue() ||
    			 OFConstants.NEST_BUYER.intValue() == serviceOrder.getBuyerId().intValue() ||
    			 OFConstants.FLUSHMATE_BUYER.intValue() == serviceOrder.getBuyerId().intValue()){
    		 logger.info("SL-20888:SL Direct Buyer:");
    		 serviceOrder.setSowTitle(fixStringIfNull(serviceOrder.getSowTitle()));
    	 }
    	 else{
    		 logger.info("SL-20888:Not SL Direct Buyer:");
    		 serviceOrder.setSowTitle(fixStringIfNull(serviceOrder.getSowTitle()) + str + fixStringIfNull(buyerOrderTemplate.getTitle()));
    	 }
         serviceOrder.setSowDs(fixStringIfNull(serviceOrder.getSowDs()) + fixStringIfNull(buyerOrderTemplate.getOverview()));
         if(buyerOrderTemplate.getPartsSuppliedBy() !=null)
         	serviceOrder.setPartsSupplier(PartSupplierType.fromId(buyerOrderTemplate.getPartsSuppliedBy()));
         //R12_3:SL-20664
         //setting provider instructions & buyer terms for At&t road runner
         //R15_4:SL-20975
         //setting provider instructions & buyer terms for buyer 513561 from both template and API request
         //R16_0_1:SL-21238
         //setting provider instructions & buyer terms for buyer 513590 from both template and API request
         if(OFConstants.SL_DIRECT_BUYER.intValue() != serviceOrder.getBuyerId().intValue() 
        		 && OFConstants.EPM_BUYER.intValue() != serviceOrder.getBuyerId().intValue()
        		 && OFConstants.AMERICAN_BUYER.intValue() != serviceOrder.getBuyerId().intValue()){
        	 serviceOrder.setBuyerTermsCond(buyerOrderTemplate.getTerms());        	 
         }else{
        	 serviceOrder.setBuyerTermsCond(fixStringIfNull(serviceOrder.getBuyerTermsCond()) + fixStringIfNull(buyerOrderTemplate.getTerms()));
        	 serviceOrder.setProviderInstructions(fixStringIfNull(serviceOrder.getProviderInstructions()) + fixStringIfNull(buyerOrderTemplate.getSpecialInstructions()));
         }
         serviceOrder.setProviderServiceConfirmInd(buyerOrderTemplate.getConfirmServiceTime()); 
         
       //SL-18361 For orders in Draft/Jobcode Mismatch status, main category should be marked as "Unclassified" from the "Jobcode mismatch" template
    	 if(null==serviceOrder.getPrimaryTask()){
    		 logger.info("inside abstract order hdr enhancer - set info frm template for primaryskill category id for all non AT&T buyers");
    		 serviceOrder.setPrimarySkillCatId(Integer.parseInt(buyerOrderTemplate.getMainServiceCategory()));
    	 }
     }
    }
        serviceOrder.setSpnId(buyerOrderTemplate.getSpnId());

        // SL-16834 Do not override the value if it is already set via API
        boolean isCreatedViaAPI = false;
        isCreatedViaAPI = serviceOrder.isCreatedViaAPI();
        if(!isCreatedViaAPI){
        	 serviceOrder.setBuyerContactId(buyerOrderTemplate.getAltBuyerContactId());
        }
        if(!isCreatedViaAPI){
        	 serviceOrder.setBuyerResourceId(buyerOrderTemplate.getBuyerResourceId().longValue());
        }
        serviceOrder.setLogoDocumentId(buyerOrderTemplate.getDocumentLogoId());
        logger.info("about to auto accept check.");
        SOWorkflowControls soWorkflowControls = serviceOrder.getSOWorkflowControls() == null?new SOWorkflowControls():serviceOrder.getSOWorkflowControls();
        if(buyerOrderTemplate.getAutoAccept() != null){
        	soWorkflowControls.setAutoAcceptRescheduleRequestIndicator(buyerOrderTemplate.getAutoAccept().equals(1)?new Boolean(true):new Boolean(false));
        	soWorkflowControls.setAutoAcceptRescheduleRequestDays(buyerOrderTemplate.getAutoAcceptDays());
        	soWorkflowControls.setAutoAcceptRescheduleRequestCount(buyerOrderTemplate.getAutoAcceptTimes());
        }
        if(null!=buyerOrderTemplate.getSpnId()){
        	SPNetHdrVO spnHdr = serviceOrderDao.fetchSpnInfo(buyerOrderTemplate.getSpnId());
        	if(null != spnHdr && null!=spnHdr.getPriorityStatus() && !spnHdr.getPriorityStatus().equals("ACTIVE")){
        		serviceOrder.setRoutingPriorityInd("false");
        		soWorkflowControls.setTierRouteInd(false);
        		soWorkflowControls.setMpOverFlow(false);
        	}else{
        		serviceOrder.setRoutingPriorityInd("true");
        		serviceOrder.setPerfCriteriaLevel(spnHdr.getPerfCriteriaLevel());
        		soWorkflowControls.setTierRouteInd(true);
        		soWorkflowControls.setPerformanceScore(0.00);
        		boolean mpEligible = quickLookupCollection.getBuyerFeatureLookup().isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.AUTO_ROUTE, serviceOrder.getBuyerId());
        		logger.info("mpEligible in AbstractOrderHdrEnhancer>>"+mpEligible);
        		if(mpEligible){//should be set true only for buyers with AUTO_ROUTE = 1
        			soWorkflowControls.setMpOverFlow(spnHdr.getMpOverFlow());
        		}else{
        			soWorkflowControls.setMpOverFlow(false);
        		}
        	}
        }
        serviceOrder.setSOWorkflowControls(soWorkflowControls);
    	logger.info("soWorkflowControls is set.");
    }

    private String fixStringIfNull(String str) {
        return (str == null) ?  "" : str;
    }

    protected void setBuyerInfo(ServiceOrder serviceOrder, Buyer buyer) {
        serviceOrder.setCreatorUserName(buyer.getUser().getUsername());
        /*This has to be done to update auto funding type id in so_hdr
         * while order creation by querying buyer table*/
        logger.info("getting funding type id from buyer table");
        Integer fundingTypeId=serviceOrderDao.getFundingTypeIdForBuyer(buyer.getBuyerId());
        logger.info("Funding Type id from DB is"+ fundingTypeId);
        serviceOrder.setFundingTypeId(fundingTypeId);
        serviceOrder.setPostingFee(buyer.getPostingFee());
        serviceOrder.setCancellationFee(buyer.getCancellationFee());
        serviceOrder.setRetailCancellationFee(PricingUtil.ZERO);
        serviceOrder.setBuyerState(buyer.getLocationByPriLocnId().getLookupStates().getId());
        List<BuyerFeatureSet> featureList = buyer.getBuyerfeatureSet();
        if(null != featureList){
        	for(BuyerFeatureSet feature :featureList){
        		if(feature.getFeatureSet().equals(BuyerFeatureSetEnum.TASK_LEVEL.name()) && feature.getIsActive()){
        			serviceOrder.setPriceType(SOPriceType.TASK_LEVEL.name());
        		}
        	}
        }
    }

    protected void initializeTotalDocSize(ServiceOrder serviceOrder) {
        serviceOrder.setDocSizeTotal(0L);
    }

     ////////////////////////////////////////////////////////////////////////////
     // SETTERS FOR SPRING INJECTION
     ////////////////////////////////////////////////////////////////////////////

    public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
        this.quickLookupCollection = quickLookupCollection;
    }
    
    public IServiceOrderDao getServiceOrderDao() {
  		return serviceOrderDao;
  	}

  	public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
  		this.serviceOrderDao = serviceOrderDao;
  	}

}
