package com.servicelive.marketplatform.service;

import com.newco.marketplace.business.businessImpl.providerSearch.rating.ZipParameterBean;
import com.newco.marketplace.business.businessImpl.routingDistance.BuyerRoutingDistanceCache;
import com.newco.marketplace.business.iBusiness.buyer.IBuyerBO;
import com.newco.marketplace.business.iBusiness.providersearch.IMasterCalculatorBO;
import com.newco.marketplace.business.iBusiness.providersearch.IProviderSearchBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IBuyerSOTemplateBO;
import com.newco.marketplace.dto.BuyerSOTemplateDTO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderSearchCriteriaVO;
import com.newco.marketplace.dto.vo.providerSearch.RatingParameterBean;
import com.newco.marketplace.dto.vo.serviceorder.TierRoutedProvider;
import com.newco.marketplace.dto.vo.spn.SPNetHeaderVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.marketplatform.serviceinterface.IAutoRoutingProviderSearchBO;
import com.servicelive.marketplatform.common.vo.ProviderIdVO;
import com.servicelive.marketplatform.common.vo.ProviderListCriteriaForAutoRoutingVO;
import com.servicelive.routingrulesengine.services.OrderProcessingService;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public class AutoRoutingProviderSearchBO implements IAutoRoutingProviderSearchBO {
    protected final Logger logger = Logger.getLogger(getClass());

    IProviderSearchBO providerSearchBO;
    IMasterCalculatorBO masterCalculatorBO;
    IBuyerSOTemplateBO buyerSOTemplateBO;
    OrderProcessingService orderProcessingService;
    BuyerRoutingDistanceCache buyerRoutingDistanceCache;

    public List<ProviderResultVO> findProvidersForAutoRouting(ProviderListCriteriaForAutoRoutingVO providerListCriteriaVO) {
        logProviderListCriteria(providerListCriteriaVO);
        logger.info("Inside AutoRoutingProviderSearchBO.findProvidersForAutoRouting method");
        long startTime = System.currentTimeMillis();
        BuyerSOTemplateDTO buyerSOTemplate = getBuyerSOTemplate(providerListCriteriaVO.getBuyerId().intValue(), providerListCriteriaVO.getBuyerTemplateName());
        ProviderSearchCriteriaVO searchBOCriteria = createSearchBOCriteria(providerListCriteriaVO, buyerSOTemplate);
        long start = System.currentTimeMillis();
        ArrayList<ProviderResultVO> providerSearchResults = providerSearchBO.getProviderList(searchBOCriteria);
        long end = System.currentTimeMillis();
        logger.info("inside AutoRoutingProviderSearchBO findProvidersForAutoRouting time taken to fetch :"+(end-start));
        ArrayList<ProviderResultVO> trimmedProviderSearchResults = new ArrayList<ProviderResultVO>();
		if(null!=providerSearchResults && providerSearchResults.size()>0){
			// Commented trim logic for SLT-2549
			//Changes for SLT-272
			//Integer routingRadius = buyerRoutingDistanceCache.getBuyerRoutingDistance(providerListCriteriaVO.getBuyerId().intValue());
			//trimmedProviderSearchResults = trimProviderListByPrimarySkillAndLocation(providerSearchResults, providerListCriteriaVO.getPrimarySkillCategoryId(), providerListCriteriaVO.getServiceLocationZip(), routingRadius);
			 logger.info("Order routed to Prividers : "+ providerSearchResults);
			if (null!=buyerSOTemplate && buyerSOTemplate.getSpnId() != null) {
	            trimmedProviderSearchResults = trimProviderListByMinSpnPercentMatch(providerSearchResults, buyerSOTemplate.getSpnPercentageMatch());
	        }
		}
        logger.info(String.format("Finished with AutoRoutingProviderSearchBO.findProvidersForAutoRouting method. Time taken is %1$d ms and found %2$d", System.currentTimeMillis() - startTime, trimmedProviderSearchResults.size()));
        return trimmedProviderSearchResults;
    }

    private void logProviderListCriteria(ProviderListCriteriaForAutoRoutingVO criteria) {
        if (logger.isDebugEnabled()){ 
        StringBuilder sb = new StringBuilder("\nCriteria for auto route provider search");
        sb.append("\nbuyerId               : ").append(criteria.getBuyerId());
        sb.append("\nserviceOrderId        : ").append(criteria.getServiceOrderId());
        sb.append("\nbuyerTemplateName     : ").append(criteria.getBuyerTemplateName());
        sb.append("\nserviceLocationZip    : ").append(criteria.getServiceLocationZip());
        sb.append("\nprimarySkillCategoryId: ").append(criteria.getPrimarySkillCategoryId());
        sb.append("\ntierId                : ").append(criteria.getTierId());
        sb.append("\nconditionalAutoRoute  : ").append(criteria.isConditionalAutoRoute());
        if (criteria.getSkillNodeIds() != null) {
            sb.append("\nskillNodeIds          : ");
            for (Integer skillNodeId : criteria.getSkillNodeIds()) {
                sb.append(skillNodeId).append(", ");
            }
        }
        if (criteria.getSkillServiceTypes() != null) {
            sb.append("\nskillServiceTypes     : ");
            for (Integer skillServiceTypes : criteria.getSkillServiceTypes()) {
                sb.append(skillServiceTypes).append(", ");
            }
        }
            logger.debug(sb.toString());
    }
    }

    public List<Integer> findProvidersForConditionalAutoRouting(ProviderListCriteriaForAutoRoutingVO providerListCriteriaVO) {
        BuyerSOTemplateDTO buyerSOTemplate = getBuyerSOTemplate(providerListCriteriaVO.getBuyerId().intValue(), providerListCriteriaVO.getBuyerTemplateName());
        Integer spnId = null;
        Boolean isNewSpn = false;
        if (buyerSOTemplate != null) {
        	logger.info("SPN id:"+buyerSOTemplate.getSpnId());
            spnId = buyerSOTemplate.getSpnId();
            isNewSpn = buyerSOTemplate.getIsNewSpn();
        }

        return orderProcessingService.getApprovedProviderList(providerListCriteriaVO.getConditionalRuleId(), spnId, isNewSpn);
    }

    @Transactional
    public List<Integer> getProviderForAutoAcceptance(Integer eligibleFirmId,Integer spnId)
    {
    	List<Integer> resourceIdList=new ArrayList<Integer>();
    	//SL-19021 Passing the spn id with provider firm id to fix the JIRA Issue
    	resourceIdList=orderProcessingService.getProviderForAutoAcceptance(eligibleFirmId,spnId);
    	logger.info("Total size of resource id"+resourceIdList.size());
    	return resourceIdList;
    	
    }
    protected BuyerSOTemplateDTO getBuyerSOTemplate(Integer buyerId, String templateName) {
        BuyerSOTemplateDTO buyerSOTemplate = buyerSOTemplateBO.loadBuyerSOTemplate(buyerId, templateName);
        return buyerSOTemplate;
    }

    protected ProviderSearchCriteriaVO createSearchBOCriteria(ProviderListCriteriaForAutoRoutingVO providerListCriteriaVO, BuyerSOTemplateDTO buyerSOTemplate) {
        ProviderSearchCriteriaVO searchBOCriteria = new ProviderSearchCriteriaVO();
        searchBOCriteria.setTierId(providerListCriteriaVO.getTierId());

        LocationVO serviceLocation = new LocationVO();
        serviceLocation.setZip(providerListCriteriaVO.getServiceLocationZip());
        searchBOCriteria.setServiceLocation(serviceLocation);

        if (isNotOverflowTier(providerListCriteriaVO.getTierId())) {
            searchBOCriteria.setTierId(providerListCriteriaVO.getTierId());
            logger.info("spnId in AutoRoutingProviderSearchBO>>"+providerListCriteriaVO.getSpnId());
            Integer tempLateSPN = 0;
            Integer entitySPN = 0;
            if(null!=buyerSOTemplate){
            	tempLateSPN = buyerSOTemplate.getSpnId();
            	logger.info("SPN ID FROM TEMPLATE"+tempLateSPN);
            }
            	entitySPN = providerListCriteriaVO.getSpnId();
            	logger.info("entitySPN"+entitySPN); 
            	searchBOCriteria.setSpnID(entitySPN);
           if(null!=buyerSOTemplate){
            	logger.info("Inside if..");
            	if(null==entitySPN){
            	searchBOCriteria.setSpnID(tempLateSPN);
            	}
                searchBOCriteria.setIsNewSpn(buyerSOTemplate.getIsNewSpn());
            }
           logger.info("final spn:"+searchBOCriteria.getSpnID());
        }

        ArrayList<Integer> skillNodes = new ArrayList<Integer>();
        if (providerListCriteriaVO.getSkillNodeIds() != null) {
            skillNodes.addAll(providerListCriteriaVO.getSkillNodeIds());
        }
        searchBOCriteria.setSkillNodeIds(skillNodes);
        searchBOCriteria.setMarketProviderSearchOff(providerListCriteriaVO.isMarketProviderSearchOff());
        searchBOCriteria.setSkillServiceTypeId(providerListCriteriaVO.getSkillServiceTypes());

        return searchBOCriteria;
    }

    protected boolean isNotOverflowTier(Integer tierId) {
    	logger.info("Inside AutoRoutingProviderSearchBO.isNotOverflowTier()>>TierId>>"+tierId);
        return
                (tierId == null) ||
                (tierId != null && !tierId.equals(OrderConstants.OVERFLOW));
    }

    protected ArrayList<ProviderResultVO> trimProviderListByPrimarySkillAndLocation(ArrayList<ProviderResultVO> providerSearchResults, Integer primarySkillCatId, String zipCode, Integer radius) {
    	logger.info("Inside AutoRoutingProviderSearchBO.trimProviderListByPrimarySkillAndLocation()");
    	ArrayList<RatingParameterBean> ratingParameterBeans = new ArrayList<RatingParameterBean>();
        ZipParameterBean zipBean = new ZipParameterBean();
      //  zipBean.setRadius(OrderConstants.SO_ROUTE_CRITERIA_DIST);
        zipBean.setRadius(radius);
        zipBean.setZipcode(zipCode);
        zipBean.setCredentialId(primarySkillCatId);

        ratingParameterBeans.add(zipBean);

        return masterCalculatorBO.getFilteredProviderList(ratingParameterBeans, providerSearchResults);
    }

    protected ArrayList<ProviderResultVO> trimProviderListByMinSpnPercentMatch(ArrayList<ProviderResultVO> providerSearchResults, Integer minSpnMatchValue) {
        ArrayList<ProviderResultVO> providerList = new ArrayList<ProviderResultVO>();
        for (ProviderResultVO vo : providerSearchResults) {
            if (vo.getPercentageMatch()!=null && vo.getPercentageMatch() >= minSpnMatchValue) {
                providerList.add(vo);
            }
        }
        return providerList;
    }
    
    public SPNetHeaderVO fetchSpnDetails(Integer spnId){
    	return (SPNetHeaderVO)providerSearchBO.fetchSpnDetails(spnId);
    }
    
    public List<Integer> findRanks(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers){
    	return (List<Integer>)providerSearchBO.findRanks(soId, noOfProvInCurentTier, noOfProvInPreviousTiers);
    }
    
    public List<ProviderIdVO> findProvidersForTierRouting(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers,List<Integer> perfScores){
    	List<TierRoutedProvider> prov = new ArrayList<TierRoutedProvider>();
    	List<ProviderIdVO> provList = new ArrayList<ProviderIdVO>();
    	prov = providerSearchBO.findProvidersForTierRouting(soId, noOfProvInCurentTier, noOfProvInPreviousTiers, perfScores);
    	logger.info("Inside AutoRoutingProviderSearchBO.findProvidersForTierRouting()@@provListSize>> "+prov.size());
    	if(null!=prov && prov.size()>0){
    		for(TierRoutedProvider trp:prov){
    			ProviderIdVO vo = new ProviderIdVO();
    			vo.setVendorId(trp.getVendorId());
    			vo.setResourceId(trp.getResourceId());
    			vo.setRoutingTimePerfScore(trp.getPerfScore());
    			vo.setRoutingTimeFirmPerfScore(trp.getFirmPerfScore());
    			provList.add(vo);
    		}
    	}
    	return provList;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////
    public void setProviderSearchBO(IProviderSearchBO providerSearchBO) {
        this.providerSearchBO = providerSearchBO;
    }

    public void setMasterCalculatorBO(IMasterCalculatorBO masterCalculatorBO) {
        this.masterCalculatorBO = masterCalculatorBO;
    }

    public void setBuyerSOTemplateBO(IBuyerSOTemplateBO buyerSOTemplateBO) {
        this.buyerSOTemplateBO = buyerSOTemplateBO;
    }

    public void setOrderProcessingService(OrderProcessingService orderProcessingService) {
        this.orderProcessingService = orderProcessingService;
    }

    public void setBuyerRoutingDistanceCache(
			BuyerRoutingDistanceCache buyerRoutingDistanceCache) {
		this.buyerRoutingDistanceCache = buyerRoutingDistanceCache;
	}
    
 }
