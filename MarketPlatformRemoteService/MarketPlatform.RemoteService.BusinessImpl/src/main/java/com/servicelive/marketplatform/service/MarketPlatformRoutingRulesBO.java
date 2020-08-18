package com.servicelive.marketplatform.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.servicelive.domain.common.NameValuePair;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.RoutingRuleVendor;
import com.servicelive.domain.spn.network.SimpleSPNHeader;
import com.servicelive.domain.spn.network.SimpleSPNTierReleaseInfo;
import com.servicelive.marketplatform.common.exception.MarketPlatformException;
import com.servicelive.marketplatform.common.vo.CondRoutingRuleVO;
import com.servicelive.marketplatform.common.vo.ItemsForCondAutoRouteRepriceVO;
import com.servicelive.marketplatform.common.vo.RoutingRuleHdrVO;
import com.servicelive.marketplatform.common.vo.RoutingRuleVendorVO;
import com.servicelive.marketplatform.common.vo.TierReleaseInfoVO;
import com.servicelive.marketplatform.dao.ISPNDao;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformRoutingRulesBO;
import com.servicelive.routingrulesengine.services.OrderProcessingService;
import com.servicelive.routingrulesengine.services.impl.OrderProcessingServiceImpl;

public class MarketPlatformRoutingRulesBO implements IMarketPlatformRoutingRulesBO {
	private static final Logger logger = Logger.getLogger(MarketPlatformRoutingRulesBO.class);
    private OrderProcessingService orderProcessingService;
    private ISPNDao spnDao;

    public CondRoutingRuleVO getConditionalRoutingRuleId(CondRoutingRuleVO condRoutingRuleVO) {
    	
        String mainJobCode = condRoutingRuleVO.getMainJobCode();
        logger.info("Inside getConditionalRoutingRuleId method of MArketPlatforRoutingRulesBO class with following info"+
        "buyer Id"+condRoutingRuleVO.getBuyerId()+"zip code"+ condRoutingRuleVO.getServiceLocationZip()+"customreferpairs size"+
        condRoutingRuleVO.getCustomRefNameValuePairs().size()+"so id"+ condRoutingRuleVO.getSoId()+"isupdate status"+condRoutingRuleVO.isUpdate());
        ServiceOrder serviceOrder = createMktServiceOrderFromInput(
                condRoutingRuleVO.getBuyerId(),
                condRoutingRuleVO.getServiceLocationZip(),
                condRoutingRuleVO.getCustomRefNameValuePairs(),
                condRoutingRuleVO.getSoId(),
                condRoutingRuleVO.isUpdate());
        logger.info("SL 15642 Conditional-route : SO ID"+ condRoutingRuleVO.getSoId());
        RoutingRuleHdr routingRule;
        try {
        	 logger.info("SL 15642 Conditional-route : MAin Job Code"+ mainJobCode);
            routingRule = orderProcessingService.getConditionalRoutingRule(serviceOrder, mainJobCode);

            //create car association with service order
            if(serviceOrder.isUpdate()){
            	logger.info("Setting value for so routing rule associtation");
            orderProcessingService.updateRoutingRuleId(routingRule, serviceOrder);
            }
        } catch (Exception e) {
            throw new MarketPlatformException("unable to determine conditional autorouting eligibility", e);
        }

        if (routingRule == null) {
                return null;
        	}
        logger.info("SL 15642 Conditional-route : Rule Id "+ routingRule.getRoutingRuleHdrId());
        condRoutingRuleVO.setRuleId(routingRule.getRoutingRuleHdrId());
        condRoutingRuleVO.setRuleName(routingRule.getRuleName());
      
        return condRoutingRuleVO;
    }

    private ServiceOrder createMktServiceOrderFromInput(Integer buyerId, String serviceLocationZip, List<NameValuePair> customRefNameValuePairs, String soId,boolean isUpdate) {
        ServiceOrder serviceOrder = new ServiceOrder();

        com.newco.marketplace.dto.vo.serviceorder.Buyer buyer = new com.newco.marketplace.dto.vo.serviceorder.Buyer();
        buyer.setBuyerId(buyerId);
        serviceOrder.setBuyer(buyer);
        serviceOrder.setSoId(soId);
        serviceOrder.setUpdate(isUpdate);
        SoLocation serviceLocation = new SoLocation();
        serviceLocation.setZip(serviceLocationZip);
        serviceOrder.setServiceLocation(serviceLocation);
        
        List<ServiceOrderCustomRefVO> custRefList = new ArrayList<ServiceOrderCustomRefVO>();
        for (NameValuePair customRefNameValuePair : customRefNameValuePairs) {
            ServiceOrderCustomRefVO custRef = new ServiceOrderCustomRefVO();
            custRef.setRefType(customRefNameValuePair.getName());
            custRef.setRefValue(customRefNameValuePair.getValue());
            custRefList.add(custRef);
        }
        serviceOrder.setCustomRefs(custRefList);
        return serviceOrder;
    }

    public ItemsForCondAutoRouteRepriceVO repriceItems(ItemsForCondAutoRouteRepriceVO itemsForReprice) {
    	logger.info("Entering MarketPlatformRoutingRulesBO.repriceItems()");
    	long startTime = System.currentTimeMillis();
        Map<String, BigDecimal> repricedItems;
        try {
            repricedItems = orderProcessingService.applyConditionalPriceOverrides(
                    itemsForReprice.getSkus(), 
                    itemsForReprice.getCondAutoRouteRuleId(),
                    itemsForReprice.getSpecialtyCode());
        } catch (Exception e) {
            throw new MarketPlatformException("unable to apply conditional price overrides", e);
        }

        List<NameValuePair> skuPricePairList = new ArrayList<NameValuePair>();
        for (Map.Entry<String, BigDecimal> repricedItem : repricedItems.entrySet()) {
            skuPricePairList.add(new NameValuePair(repricedItem.getKey(), repricedItem.getValue().toPlainString()));
        }
        itemsForReprice.setSkuPricePairList(skuPricePairList);
        logger.info(String.format("Exiting MarketPlatformRoutingRulesBO.repriceItems()Time taken is %1$d ms", System.currentTimeMillis() - startTime));
        return itemsForReprice;
    }

    @Transactional(readOnly = true)
    public TierReleaseInfoVO retrieveTierReleaseInfo(Integer spnId, Integer currentTier) {
        List<SimpleSPNTierReleaseInfo> spnTierReleaseInfoList = spnDao.retrieveTierReleaseInfoWithSPNAndStartingTier(spnId, currentTier);
        if (spnTierReleaseInfoList == null || spnTierReleaseInfoList.size() == 0) {
            return null;
        }
        SimpleSPNTierReleaseInfo currSpnTierReleaseInfo = spnTierReleaseInfoList.get(0);
        SimpleSPNHeader currentSPNHeader = currSpnTierReleaseInfo.getSpnTierReleasePK().getSpnId();

        TierReleaseInfoVO tierReleaseInfoVO = new TierReleaseInfoVO(spnId, currentSPNHeader.getNetworkName(), currentTier);
        setNextTierInfo(tierReleaseInfoVO, spnTierReleaseInfoList, 0);
        setTierReleaseTimeInfo(tierReleaseInfoVO, spnTierReleaseInfoList.get(0));

        return tierReleaseInfoVO;
    }

    @Transactional(readOnly = true)
    public List<TierReleaseInfoVO> retrieveAllAvailableTierReleaseInfo() {
        List<SimpleSPNTierReleaseInfo> tierReleaseInfoList = spnDao.retrieveAllAvailableTierReleaseInfo();
        if (tierReleaseInfoList == null || tierReleaseInfoList.size() == 0) {
            return null;
        }

        List<TierReleaseInfoVO> tierReleaseInfoVOList = new ArrayList<TierReleaseInfoVO>();
        for (int idx=0; idx<tierReleaseInfoList.size(); idx++) {
            SimpleSPNTierReleaseInfo currSpnTierReleaseInfo = tierReleaseInfoList.get(idx);
            SimpleSPNHeader currentSPNHeader = currSpnTierReleaseInfo.getSpnTierReleasePK().getSpnId();
            TierReleaseInfoVO tierReleaseInfoVO = new TierReleaseInfoVO(currentSPNHeader.getSpnId(), currentSPNHeader.getNetworkName(),
                                                                        currSpnTierReleaseInfo.getSpnTierReleasePK().getTierId());
            tierReleaseInfoVO.setNumberOfProviders(currSpnTierReleaseInfo.getNumberOfProviders());
            tierReleaseInfoVO.setMarketOverflowIndicator(currentSPNHeader.hasMarketPlaceOverFlowIndicator());
            setNextTierInfo(tierReleaseInfoVO, tierReleaseInfoList, idx);
            setTierReleaseTimeInfo(tierReleaseInfoVO, currSpnTierReleaseInfo);
            tierReleaseInfoVOList.add(tierReleaseInfoVO);
        }
        return tierReleaseInfoVOList;
    }

    private void setNextTierInfo(TierReleaseInfoVO tierReleaseInfoVO, List<SimpleSPNTierReleaseInfo> spnTierReleaseInfoList, Integer currTierIdx) {
        if (currentTierHasNextTier(spnTierReleaseInfoList, currTierIdx)) {
            tierReleaseInfoVO.setNextTier(spnTierReleaseInfoList.get(currTierIdx + 1).getSpnTierReleasePK().getTierId());
        }
    }

    private boolean currentTierHasNextTier(List<SimpleSPNTierReleaseInfo> spnTierReleaseInfoList, Integer currTierId) {
        return spnTierReleaseInfoList.size() > (currTierId + 1) &&
                spnTierReleaseInfoList.get(currTierId).getSpnTierReleasePK().getSpnId().equals(
                        spnTierReleaseInfoList.get(currTierId + 1).getSpnTierReleasePK().getSpnId()
                );
    }

    private void setTierReleaseTimeInfo(TierReleaseInfoVO tierReleaseInfoVO, SimpleSPNTierReleaseInfo spnTierReleaseInfo) {
        Long minutes = spnTierReleaseInfo.getAdvancedMinutes() != null ? spnTierReleaseInfo.getAdvancedMinutes() : 0L;
        Long hours = spnTierReleaseInfo.getAdvancedHours() != null ? spnTierReleaseInfo.getAdvancedHours() : 0L;
        Long days = spnTierReleaseInfo.getAdvancedDays() != null ? spnTierReleaseInfo.getAdvancedDays() : 0L;
        Long totalMinutes = minutes + ((hours + (days * 24)) * 60);
        tierReleaseInfoVO.setMinutesUntilNextTier(totalMinutes);
    }
 //SL 15642 Start Method to enter type of acceptance in so_workflow_controls table on associating a rule id with so
    public void setMethodOfAcceptanceOfSo(String soId,String methodOfAcceptance)
    {
    	orderProcessingService.setMethodOfAcceptanceOfSo(soId, methodOfAcceptance);
    }
    public void setMethodOfRoutingOfSo(String soId,String methodOfRouting)
    {
    	orderProcessingService.setMethodOfRoutingOfSo(soId, methodOfRouting);
    }
    public RoutingRuleHdrVO getProvidersListOfRule(Integer ruleId)
    {
    	RoutingRuleHdr rrhList=orderProcessingService.getProvidersListOfRule(ruleId);
    	List<RoutingRuleVendorVO> rrvList=new ArrayList<RoutingRuleVendorVO>();
    	RoutingRuleHdrVO rrv=new RoutingRuleHdrVO();
    	rrv.setRoutingRuleHdrId(ruleId);
    	if(null!=rrhList.getRoutingRuleVendor())
    	{
    		RoutingRuleVendorVO rrvNew=new RoutingRuleVendorVO();
    		for(RoutingRuleVendor rrh:rrhList.getRoutingRuleVendor())
    		{
    			rrvNew.setAutoAcceptStatus(rrh.getAutoAcceptStatus());
    			rrvNew.setRoutingRuleVendorId(rrh.getRoutingRuleVendorId());
    			rrvList.add(rrvNew);
    		}
    		
    	}
    	rrv.setRoutingRuleVendor(rrvList);
    	return rrv;
    }
    //SL 15642 Start Method to enter type of acceptance in so_workflow_controls table on associating a rule id with so

    ////////////////////////////////////////////////////////////////////////////
    // SETTERS AND GETTERS
    ////////////////////////////////////////////////////////////////////////////

    public void setOrderProcessingService(OrderProcessingService orderProcessingService) {
        this.orderProcessingService = orderProcessingService;
    }

    public void setSpnDao(ISPNDao spnDao) {
        this.spnDao = spnDao;
    }
    }
