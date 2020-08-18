package com.servicelive.orderfulfillment.command.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map; 
import java.util.Set;

import org.apache.log4j.Logger;

import com.servicelive.domain.common.BuyerFeatureSetEnum;
import com.servicelive.domain.so.SORoutingRuleAssoc;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.marketplatform.common.vo.ProviderIdVO;
import com.servicelive.marketplatform.common.vo.ProviderListCriteriaForAutoRoutingVO;
import com.servicelive.marketplatform.common.vo.RoutingRuleHdrVO;
import com.servicelive.marketplatform.common.vo.RoutingRuleVendorVO;
import com.servicelive.marketplatform.common.vo.SPNetHdrVO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformProviderBO;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.dao.ICARAssociationDao;
import com.servicelive.orderfulfillment.dao.IServiceOrderDao;
import com.servicelive.orderfulfillment.domain.*;
import com.servicelive.orderfulfillment.domain.type.ProviderResponseType;
import com.servicelive.orderfulfillment.domain.type.RoleType;
import com.servicelive.orderfulfillment.lookup.BuyerFeatureLookup;
import com.servicelive.orderfulfillment.lookup.QuickLookupCollection;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;

public class AutoRouteHelper {
	private static final Logger logger = Logger.getLogger(AutoRouteHelper.class);
	IMarketPlatformProviderBO marketPlatformProviderBO;
	//IMarketPlatformProviderBO mktPlatformProviderBo;
	IServiceOrderDao serviceOrderDao;
    ICARAssociationDao carAssociationDao;
    TierRouteUtil tierRouteUtil;
    QuickLookupCollection quickLookupCollection;
	
	public List<ProviderIdVO> getProviders(ServiceOrder serviceOrder, Map<String, Object> processVariables){
		logger.info("Entered AutoRouteHelper.getProviders()....1"+ serviceOrder.getSoId());
		long start = System.currentTimeMillis();
        ProviderListCriteriaForAutoRoutingVO listCriteriaForAutoRoutingVO = new ProviderListCriteriaForAutoRoutingVO();
        Integer tier = getTierParameter(processVariables);
        logger.info("TIER ID::"+tier);
        listCriteriaForAutoRoutingVO.setBuyerId(serviceOrder.getBuyerId());
        String templateName = getBuyerCustomRefTemplateName(serviceOrder);
        logger.info("templateName1"+templateName);
        String isSaveandAutoPost = (String)processVariables.get(ProcessVariableUtil.AUTO_POST_IND);
        String isAutoPost = (String)processVariables.get(ProcessVariableUtil.SAVE_AND_AUTOPOST);
        logger.info("Inside getProviders>>"+isSaveandAutoPost);
        logger.info("Inside getProviders>>"+isAutoPost);
        Long buyerId =0L;
        //Implemented for AT&T buyer 
        if(templateName==null || templateName.equals("")){
        	templateName = (String)processVariables.get(ProcessVariableUtil.TEMPLATE_NAME);
        }
        logger.info("templateName2"+templateName);
        BuyerFeatureLookup buyerFeatureLookup = quickLookupCollection.getBuyerFeatureLookup();
        if(null!=serviceOrder.getBuyerId()){
        	buyerId = serviceOrder.getBuyerId();
        }
        if (!buyerFeatureLookup.isInitialized()) {
            throw new ServiceOrderException("Unable to lookup buyer feature . BuyerFeatureLookup not initialized.");
        }
        if (buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.MARKETPLACE_PROVIDER_SEARCH_OFF, buyerId)){
        	listCriteriaForAutoRoutingVO.setMarketProviderSearchOff(true);
        }
        if(null!=tier){
        listCriteriaForAutoRoutingVO.setTierId(tier);
        }
        listCriteriaForAutoRoutingVO.setBuyerTemplateName(templateName);
        listCriteriaForAutoRoutingVO.setPrimarySkillCategoryId(serviceOrder.getPrimarySkillCatId());
        listCriteriaForAutoRoutingVO.setServiceLocationZip(serviceOrder.getServiceLocation().getZip());
        listCriteriaForAutoRoutingVO.setServiceOrderId(serviceOrder.getSoId());
        logger.info("serviceOrder.getSpnId()>>"+serviceOrder.getSpnId());
        listCriteriaForAutoRoutingVO.setSpnId(serviceOrder.getSpnId());
        listCriteriaForAutoRoutingVO.setSaveAndAutoPost(isAutoPost);
        logger.info("serviceOrder.isSaveandAutoPost>>"+isSaveandAutoPost);
        //listCriteriaForAutoRoutingVO.setConditionalAutoRoute(false);
        addAllTasksSkillNodeAndServiceType(serviceOrder, listCriteriaForAutoRoutingVO);
        if(null != tier && !tier.equals(9999)){ //make sure that we get the providers - if we do not get than movze to next tier
        	logger.info("AutoRouteHelper.getProviders()....routing through tier"+ serviceOrder.getSoId());
        	long start1 = System.currentTimeMillis();
    		List<ProviderIdVO> idVOs = findProviderForTierRouting(serviceOrder, processVariables, listCriteriaForAutoRoutingVO, tier);
    		long end1 = System.currentTimeMillis();
        	logger.info("AutoRouteHelper.getProviders()....routing through tier time taken:"+ (end1-start1)+"SOID:"+ serviceOrder.getSoId()+"listsize>>"+idVOs.size());
        	return idVOs;
        }else {
        	logger.info("AutoRouteHelper.getProviders()....routing through marketplace"+ serviceOrder.getSoId());
        	//reseting the overflow tier, if present so that while reposting tier routing is enabled
        	processVariables.put(ProcessVariableUtil.AUTO_ROUTING_TIER, null);
        	processVariables.put(ProcessVariableUtil.OVERFLOW_TIER_IND, "true");
    		long start1 = System.currentTimeMillis();
    		List<ProviderIdVO> idVOs = marketPlatformProviderBO.findProvidersForAutoRouting(listCriteriaForAutoRoutingVO);
    		long end1 = System.currentTimeMillis();
        	logger.info("AutoRouteHelper.getProviders()....routing through marketplace time taken:"+ (end1-start1));
        	return idVOs;
	}
	}
	/**@Description : This method will fetch the eligible resources for the firm which is to be routed and accepted for warranty Order
	 * @param serviceOrder
	 * @param processVariables
	 * @return
	 */
	public List<ProviderIdVO> getProvidersForInhomeAutoPost(ServiceOrder serviceOrder, Map<String, Object> processVariables) {
		logger.info("Entered AutoRouteHelper.getProvidersForInhomeAutoPost()"+ serviceOrder.getSoId());
		Long buyerId = 0L;
        ProviderListCriteriaForAutoRoutingVO listCriteriaForAutoPostVO = new ProviderListCriteriaForAutoRoutingVO();
        listCriteriaForAutoPostVO.setBuyerId(serviceOrder.getBuyerId());
        String templateName = getBuyerCustomRefTemplateName(serviceOrder);
        logger.info("templateName"+templateName);
        BuyerFeatureLookup buyerFeatureLookup = quickLookupCollection.getBuyerFeatureLookup();
        if(null!=serviceOrder.getBuyerId()){
        	buyerId = serviceOrder.getBuyerId();
        }
        if (!buyerFeatureLookup.isInitialized()) {
            throw new ServiceOrderException("Unable to lookup buyer feature . BuyerFeatureLookup not initialized.");
        }
        if (buyerFeatureLookup.isActiveFeatureAssociatedWithBuyer(BuyerFeatureSetEnum.MARKETPLACE_PROVIDER_SEARCH_OFF, buyerId)){
        	listCriteriaForAutoPostVO.setMarketProviderSearchOff(true);
        }
        listCriteriaForAutoPostVO.setBuyerTemplateName(templateName);
        listCriteriaForAutoPostVO.setPrimarySkillCategoryId(serviceOrder.getPrimarySkillCatId());
        listCriteriaForAutoPostVO.setServiceLocationZip(serviceOrder.getServiceLocation().getZip());
        listCriteriaForAutoPostVO.setServiceOrderId(serviceOrder.getSoId());
        logger.info("serviceOrder.getSpnId()>>"+serviceOrder.getSpnId());
        listCriteriaForAutoPostVO.setSpnId(serviceOrder.getSpnId());
        addAllTasksSkillNodeAndServiceType(serviceOrder, listCriteriaForAutoPostVO);
        processVariables.put(ProcessVariableUtil.OVERFLOW_TIER_IND, "true");
        long start1 = System.currentTimeMillis();
		List<ProviderIdVO> idVOs = marketPlatformProviderBO.findProvidersForAutoRouting(listCriteriaForAutoPostVO);
		
		// filter out providers which are not the resource under the accepted firm of the recalled order.
		List<ProviderIdVO> providerList=new ArrayList<ProviderIdVO>();
		Integer vendorId = serviceOrder.getInhomeAcceptedFirm() != null ? serviceOrder.getInhomeAcceptedFirm().intValue() : null;
	    logger.info("accepted Inhome vendor Id "+vendorId);
		if(null!= vendorId && null!=idVOs){
		    	for(ProviderIdVO provider:idVOs){
		    		if(null!= provider.getVendorId() && vendorId.equals(provider.getVendorId())){
		    		    logger.info("added the provider in inhome list");		
		    			providerList.add(provider);
		    		}
		    	}
		}
	
		long end1 = System.currentTimeMillis();
    	logger.info("AutoRouteHelper.getProvidersForInhomeAutoPost()....routing through marketplace time taken:"+ (end1-start1));
    	return providerList;
    	
	}
    private void addAllTasksSkillNodeAndServiceType(ServiceOrder serviceOrder, ProviderListCriteriaForAutoRoutingVO listCriteriaForAutoRoutingVO){
        if(null != serviceOrder.getSoGroup()){
            for(ServiceOrder so : serviceOrder.getSoGroup().getServiceOrders()){
                addTasksSkillNodeAndServiceType(so, listCriteriaForAutoRoutingVO);
            }
        } else {
            addTasksSkillNodeAndServiceType(serviceOrder, listCriteriaForAutoRoutingVO);
        }
    }
    
    private void addTasksSkillNodeAndServiceType(ServiceOrder serviceOrder, ProviderListCriteriaForAutoRoutingVO listCriteriaForAutoRoutingVO){
        if(null != serviceOrder.getTasks()){
            for (SOTask soTask : serviceOrder.getActiveTasks()) {
                if (soTask.getSkillNodeId() != null) listCriteriaForAutoRoutingVO.addSkillNodeId(soTask.getSkillNodeId());
                if (soTask.getServiceTypeId() != null) listCriteriaForAutoRoutingVO.addSkillServiceType(soTask.getServiceTypeId());
            }
        }
    }

    private List<ProviderIdVO> findProviderForTierRouting(ServiceOrder serviceOrder, Map<String, Object> processVariables, ProviderListCriteriaForAutoRoutingVO listCriteriaForAutoRoutingVO, Integer tier) {
    	logger.info("INSIDE:::findProviderForTierRouting() for soid>>"+serviceOrder.getSoId());
    	long start = System.currentTimeMillis();
    	List<ProviderIdVO> providersIds = new ArrayList<ProviderIdVO>();
    	List<ServiceOrder> fetchChildOrders =  new ArrayList<ServiceOrder>();
        List<Integer> perfScores = new ArrayList<Integer>();
        String tierRouteInd = (String)processVariables.get(ProcessVariableUtil.ROUTING_PRIORITY_IND);
        Integer noOfProvInCurentTier = (Integer)processVariables.get(ProcessVariableUtil.PROVIDERS_IN_CURRENT_TIER);
        Integer noOfProvInPreviousTiers = (Integer)processVariables.get(ProcessVariableUtil.PROVIDERS_IN_PREVIOUS_TIERS);
        String perfCriteriaLevel = (String)processVariables.get(ProcessVariableUtil.PERF_CRITERIA_LEVEL);
        String postFromFrontend = (String)processVariables.get(ProcessVariableUtil.FE_POST_ORDER);
        String isRePost = (String)processVariables.get(ProcessVariableUtil.REPOST);
        String isOfEligible = (String)processVariables.get(ProcessVariableUtil.OVERFLOW_ELIGIBLE);
        String isSaveandAutoPost = (String)processVariables.get(ProcessVariableUtil.AUTO_POST_IND);
        String isAutoPost = (String)processVariables.get(ProcessVariableUtil.SAVE_AND_AUTOPOST);
        String groupId = (String) processVariables.get(ProcessVariableUtil.PVKEY_GROUP_ID);
        String fePost = (String) processVariables.get(OrderfulfillmentConstants.POST_FROM_FRONTEND_ACTION);
        String rejectedByAllInd =(String)processVariables.get(ProcessVariableUtil.REJECTED_BY_ALL_PROV);
        String counterOfferByAllInd =(String)processVariables.get(ProcessVariableUtil.COUNTER_OFFER_BY_ALL_PROV);
        boolean duplicateTierId = false;
        logger.info("CURRENT TIER:::"+tier);
        logger.info("FE POST:::"+fePost);
        logger.info("PERF CRITERIA LEVEL:::"+perfCriteriaLevel);
        logger.info("PROVIDERS_IN_CURRENT_TIER:::"+noOfProvInCurentTier);
        logger.info("PROVIDERS_IN_PREVIOUS_TIERS:::"+noOfProvInPreviousTiers);
        logger.info("TIER ROUTE IND:::"+tierRouteInd);
        logger.info("FE_POST_ORDER:::"+postFromFrontend);
        logger.info("BUYER_ID"+serviceOrder.getBuyerId());
        logger.info("OVERFLOW_ELIGIBLE"+isOfEligible);
        logger.info("groupId"+groupId);
        logger.info("isAutoPost"+isAutoPost);
        logger.info("isSaveandAutoPost"+isSaveandAutoPost);
        logger.info("rejectedByAllInd"+rejectedByAllInd);
        logger.info("counterOfferByAllInd"+counterOfferByAllInd);
        int foundProviders = 0;
        Integer oldTier = null;
        Integer tierId = getTierParameter(processVariables);
        if(null != groupId || null != serviceOrder.getSoGroup()){
    		logger.info("Grpd order>>Abt to fetch child orders");
    		//fetch child orders
    		fetchChildOrders = serviceOrderDao.fetchChildOrders(groupId);
        }
            listCriteriaForAutoRoutingVO.setTierId(tier);
            logger.info("Before fetch providers");
            //if through injection fetch eligible providers
            //providers need to be fetched only for following scenarios
            //1. Order injection - for starting tier routing(tier_id =1) + no providers configured for tier 1(serviceOrder.getTierRoutedResources().size()==0)
            //2. Frontend posting - providers need to be fetched for 'Save and Auto Post' only
            List<TierRouteProviders> tierProviders = serviceOrder.getTierRoutedResources();
            logger.info("tier eligible provider size in db:"+serviceOrder.getTierRoutedResources().size());
           // SL-19141  1)fetch only criteria level from spn id and use marketplatform to fetch the data
            SPNetHdrVO hdr = serviceOrderDao.fetchSpnInfo(serviceOrder.getSpnId());
            if((postFromFrontend == null && (null!=tierId && tierId.equals(1) 
            		|| serviceOrder.getTierRoutedResources().size()==0)) 
            		||((null!=isAutoPost && isAutoPost.equals("true")|| null!=isSaveandAutoPost && isSaveandAutoPost.equals("isAutoPost")) && noOfProvInPreviousTiers == 0)){
            	
            	logger.info("Abt to fetch providers");
            		long start1 = System.currentTimeMillis();
            		providersIds = marketPlatformProviderBO.findProvidersForAutoRouting(listCriteriaForAutoRoutingVO);
            	long end1 = System.currentTimeMillis();
        		logger.info("Time take inside auto route helper to fetch providers to be tier routed:"+(end1-start1)+"SOID::"+serviceOrder.getSoId());
        		logger.info("Providers for save and autopost::"+providersIds.size());
            	if(null !=tierProviders && tierProviders.size()>0 && null==isRePost && null!=isSaveandAutoPost){
            		for(TierRouteProviders trp:tierProviders){
            			ProviderIdVO vo = new ProviderIdVO();
            			vo.setVendorId(trp.getVendorId());
            			vo.setResourceId(trp.getProviderResourceId());
            			vo.setRoutingTimePerfScore(trp.getPerformanceScore());
            			vo.setRoutingTimeFirmPerfScore(trp.getFirmPerformanceScore());
            			providersIds.add(vo);
            		}
            	}
            	//delete the existing entries in so_routed_providers & tier_route_eligilble_providers for handling the following scenario
            	//->order moved to draft status since all providers rejected the order->save and auto post->order should routed to providers after deleting the existing list
            	
            	logger.info("Final List size>>"+providersIds.size());
            	
            	if(null!=serviceOrder.getRoutedResources() && serviceOrder.getRoutedResources().size()>0 &&
            			((null!=isAutoPost && isAutoPost.equals("true")|| null!=isSaveandAutoPost && isSaveandAutoPost.equals("isAutoPost")) && noOfProvInPreviousTiers == 0)){
            		logger.info(" for reject ");
            	for(RoutedProvider provider:serviceOrder.getRoutedResources()){    
            		logger.info(" provider.getProviderResponse() "+provider.getProviderResponse());
            		if(null!=provider.getProviderResponse() && provider.getProviderResponse().equals(ProviderResponseType.REJECTED)){
                		logger.info(" entered rejected ");
                		serviceOrderDao.deleteSoRoutedProviders(serviceOrder.getSoId());
                		serviceOrder.setRoutedResources(new ArrayList<RoutedProvider>());
                		break;
            		}
            	}
            	}
            }
            logger.info("After fetch providers");
            //determine the provider ranks eligible for current tier based on no of providers configured for current tier 
        	
            if(null==perfCriteriaLevel){
            	perfCriteriaLevel = hdr.getPerfCriteriaLevel();
        	}
            if(null != perfCriteriaLevel && perfCriteriaLevel.equals("FIRM")){
            	long start3 = System.currentTimeMillis();
            	Map<Integer, String> uniqueFirms = new HashMap<Integer, String>();
            	Map<Integer, Integer> uniqueRanks = new HashMap<Integer, Integer>();
            	
            	//to find the number of already routed unique vendors from so_routed_providers
            	if(serviceOrder.getRoutedResources().size()>0 && null!= tier && !tier.equals(1)){
            	for(RoutedProvider rp:serviceOrder.getRoutedResources()){
            		uniqueFirms.put(rp.getVendorId(),rp.getServiceOrder().getSoId() );
            	}}
            	noOfProvInPreviousTiers =  uniqueFirms.size();//to be given as input to fetch next set of providers
            	logger.info("noOfProvInPreviousTiers>>"+noOfProvInPreviousTiers);
            	//perfScores = serviceOrderDao.findRanks(serviceOrder.getSoId(), noOfProvInCurentTier, noOfProvInPreviousTiers);
            	
            	
            	//'IF' block will be executed only while order creation through injection/api and save and autopost for tier 1
            	//'ELSE' block will be executed if posted from frontend(where providers are already persited through EditSignal) and for routing through second tier onwards while injection
        			logger.info("serviceOrder.getTierRoutedResources() IS EMPTY for firm level>>"+providersIds.size());
        			List<ProviderIdVO> routedProviderList = providersIds;
        			if(null!=providersIds && providersIds.size()>0){//need to proceed with the below logic only is there are eligible providers
                	if(null != groupId || null != serviceOrder.getSoGroup()){
                		logger.info("Grpd order>>Abt to fetch child orders");
                		//fetch child orders
                    	long start4 = System.currentTimeMillis();

                		for(ServiceOrder order:fetchChildOrders){
                			routedProviderList = saveTierEligibleProviders(providersIds,order,perfCriteriaLevel,isSaveandAutoPost,isRePost,postFromFrontend,hdr);
                		}
                    	long end4 = System.currentTimeMillis();
                		logger.info("Time take inside auto route helper to save tier elgigble providers to be tier routed for group:"+(end4-start4)+"SOID::"+serviceOrder.getSoId());

                	}else{
                		logger.info("Not a grpd order");
                    	long start2 = System.currentTimeMillis();
                    	routedProviderList = saveTierEligibleProviders(providersIds,serviceOrder,perfCriteriaLevel,isSaveandAutoPost,isRePost,postFromFrontend,hdr);
                    	long end2 = System.currentTimeMillis();
                		logger.info("Time take inside auto route helper to save tier elgigble providers to be tier routed for non-group:"+(end2-start2));

                	}
                	if(routedProviderList.size()==0){
                		routedProviderList = providersIds;
                	}
                	
                	//find the unique ranks from recentlt sorted list
                	for(ProviderIdVO vo :routedProviderList){
                		uniqueRanks.put(vo.getRank(), vo.getVendorId());
                	}
                	logger.info("uniqueRanks.size()>> "+uniqueRanks.size());
                	//set the unique ranks to a list for sorting
                	Iterator i = uniqueRanks.entrySet().iterator();
                  	 while(i.hasNext()) {
                  		 Map.Entry pairs = (Map.Entry)i.next();
                        System.out.println(pairs.getKey() + " = " + pairs.getValue());
                        perfScores.add((Integer)pairs.getKey());
                  		  }
                  	System.out.println("list1>>unsorted>>"+perfScores);
                  	 Collections.sort(perfScores);
                  	System.out.println("list2>>sorted>>"+perfScores);
                  //fetch sublist based on the priority settings for the spn
                  	if(noOfProvInCurentTier>perfScores.size()){
                  		perfScores = perfScores.subList(noOfProvInPreviousTiers, perfScores.size());
                  	}else{
                  		perfScores = perfScores.subList(noOfProvInPreviousTiers, noOfProvInCurentTier);
                  	}
                  	System.out.println("list3>>"+perfScores);
                	 logger.info("perfScores.size()=="+perfScores.size());
                	long end3 = System.currentTimeMillis();
            		logger.info("Time take inside auto route helper to get perf score:"+(end3-start3)+"SOID::"+serviceOrder.getSoId());
            		//***to be removed if everything works fine***start//
            		if(null!= perfScores && perfScores.size()==0){
                		logger.info("perfScores.size()==0");
                		perfScores.add(0);
                	}else if(null!= perfScores && perfScores.size()>0){
                		logger.info("perfScores.size()>0");
                		for(Integer d:perfScores){
                        	logger.info("Values"+d);
                        		}
                	}
            		//***to be removed if everything works fine***end//
        			providersIds = new ArrayList<ProviderIdVO>(); 
        			for(ProviderIdVO idVo:routedProviderList){
        			for(Integer rank : perfScores){
        				if(idVo.getRank().equals(rank)){
        					logger.info("Resouceids>>"+idVo.getResourceId());
        					providersIds.add(idVo);
        				}
        			}
        		}
        		}else{
        			if(null!= serviceOrder.getTierRoutedResources() && serviceOrder.getTierRoutedResources().size()>0){
            			logger.info("serviceOrder.getTierRoutedResources() IS NOT NULL for firm level"+serviceOrder.getTierRoutedResources().size());
                		//***to be removed if everything works fine***start//
            			//tierProviders = serviceOrder.getTierRoutedResources().subList(noOfProvInPreviousTiers, noOfProvInCurentTier);
            			//***to be removed if everything works fine***end//
            			
            			//find the unique ranks in db
            			for(TierRouteProviders vo :serviceOrder.getTierRoutedResources()){
                    		uniqueRanks.put(vo.getRank(), vo.getVendorId());
                    	}
                    	logger.info("uniqueRanks.size()>> "+uniqueRanks.size());
                    	//set the unique ranks to a list for sorting
                    	Iterator i = uniqueRanks.entrySet().iterator();
                     	 while(i.hasNext()) {
                     		 Map.Entry pairs = (Map.Entry)i.next();
                           System.out.println(pairs.getKey() + " = " + pairs.getValue());
                           perfScores.add((Integer)pairs.getKey());
                     		  }
                     	System.out.println("list1>>unsorted>>"+perfScores);
                     	 Collections.sort(perfScores);
                     	System.out.println("list2>>sortedList>>"+perfScores);
                     	//fetch sublist based on the priority settings for the spn
                     	if(null!=tier && !tier.equals(4) && ((noOfProvInCurentTier+noOfProvInPreviousTiers)<perfScores.size())){
                     		logger.info("@inside if@");
                     		perfScores = perfScores.subList(noOfProvInPreviousTiers, noOfProvInCurentTier+noOfProvInPreviousTiers);
                     	}else{
                     		logger.info("@inside else@");
                     		perfScores = perfScores.subList(noOfProvInPreviousTiers, perfScores.size());
                     	}
                     	System.out.println("list3>>sublist>>"+perfScores);
                    	long end3 = System.currentTimeMillis();
                		logger.info("Time take inside auto route helper to get perf score:"+(end3-start3)+"SOID::"+serviceOrder.getSoId());
                		//***to be removed if everything works fine***start//
                		if(null!= perfScores && perfScores.size()==0){
                    		logger.info("perfScores.size()==0");
                    		perfScores.add(0);
                    	}else if(null!= perfScores && perfScores.size()>0){
                    		logger.info("perfScores.size()>0");
                    		for(Integer d:perfScores){
                            	logger.info("Values"+d);
                            		}
                    	}
                		//***to be removed if everything works fine***end//
                		//iterate over the list and filter the providers based on rank
            			for(TierRouteProviders trp:tierProviders){
                			for(Integer rank : perfScores){
                				if(trp.getRank().equals(rank)){
                			ProviderIdVO vo = new ProviderIdVO();
                			vo.setVendorId(trp.getVendorId());
                			vo.setResourceId(trp.getProviderResourceId());
                			logger.info("RES ID::"+trp.getProviderResourceId());
                			vo.setRoutingTimePerfScore(trp.getPerformanceScore());
                			vo.setRoutingTimeFirmPerfScore(trp.getFirmPerformanceScore());
                			providersIds.add(vo);
                			}
                			}
                		}
            		}
        		}
            }else{
            	//for provider level
            	//'IF' block will be executed only while order creation through injection/api and save and autopost for tier 1
            	//'ELSE' block will be executed if posted from frontend(where providers are already persisted through EditSignal) and for routing through second tier onwards while injection
        			//***&&&PROBLEM BLOCK&&&***//
        			long start4 = System.currentTimeMillis();
        			logger.info("serviceOrder.getTierRoutedResources() IS EMPTY for provider level"+providersIds.size());
        			List<ProviderIdVO> routedProviderList = providersIds;
        			if(null!=providersIds && providersIds.size()>0){//need to proceed with the below logic only is there are eligible providers
                	if(null != groupId || null != serviceOrder.getSoGroup()){
                		logger.info("Grpd order>>Abt to fetch child orders");
                		//fetch child orders

                		for(ServiceOrder order:fetchChildOrders){
                			routedProviderList = saveTierEligibleProviders(providersIds,order,perfCriteriaLevel,isSaveandAutoPost,isRePost,postFromFrontend,hdr);
                			//***to be removed if everything works fine***start//
                			//saveTierRouteEligibleProviders(providersIds,order,perfCriteriaLevel,isSaveandAutoPost,isRePost,postFromFrontend,hdr);
                			//***to be removed if everything works fine***end//
                		}
                    	long end4 = System.currentTimeMillis();
                		logger.info("Time take inside auto route helper to save tier elgigble providers to be tier routed for group:"+(end4-start4)+"SOID::"+serviceOrder.getSoId()+"routedProviderList.size>>"+routedProviderList.size());

                	}else{
                		logger.info("Not a grpd order");
                    	long start2 = System.currentTimeMillis();                    	
                    	routedProviderList = saveTierEligibleProviders(providersIds,serviceOrder,perfCriteriaLevel,isSaveandAutoPost,isRePost,postFromFrontend,hdr);
                    	//***to be removed if everything works fine***start//
                    	//saveTierRouteEligibleProviders(providersIds,serviceOrder,perfCriteriaLevel,isSaveandAutoPost,isRePost,postFromFrontend,hdr);
                    	//***to be removed if everything works fine***end//
                    	long end2 = System.currentTimeMillis();
                		logger.info("Time take inside auto route helper to save tier elgigble providers to be tier routed for non-group:"+(end2-start2)+"SOID::"+serviceOrder.getSoId()+"routedProviderList.size>>"+routedProviderList.size());
                	}
                	//***to be removed if everything works fine***start//
                	
                	//providersIds = fetchTierEligibleProviders(serviceOrder.getSoId(),noOfProvInCurentTier,noOfProvInPreviousTiers,null);
                	//***to be removed if everything works fine***end//
                	
                	//fetch the list of providers to be routed in current tier based on rank
                	logger.info("noOfProvInPreviousTiers::"+noOfProvInPreviousTiers);
                	logger.info("noOfProvInCurentTier::"+noOfProvInCurentTier);
                	
                	// TODO : PROBLEM IDENTIFIED AT THIS AREA
                	// While taking a sublist, data is not getting committed since the reference of providersIds is getting changed at this point
                	// Solution : What is the purpose of modifying the list? Identify this and come up with a differnt logic if required.
                	logger.info("providersIds::size>>before init"+providersIds.size());
                	if(routedProviderList.size()==0){
                		routedProviderList = providersIds;
                	}
                	//providersIds = routedProviderList.subList(noOfProvInPreviousTiers, noOfProvInCurentTier);
                	List<Integer> ranks = new ArrayList<Integer>();
        			for(ProviderIdVO idVo:routedProviderList){
        				ranks.add(idVo.getRank());
        			}
        			logger.info("rankList before sort"+ranks);
        			Collections.sort(ranks);
        			logger.info("rankList after sort"+ranks);
        			providersIds = new ArrayList<ProviderIdVO>(); 
        			logger.info("providersIds::size>>after init"+providersIds.size());
        			//filter ranks 
        			//fetch sublist based on the priority settings for the spn
        			if(noOfProvInCurentTier>ranks.size()){
        				ranks = ranks.subList(noOfProvInPreviousTiers,ranks.size());
        			}else{
        				ranks = ranks.subList(noOfProvInPreviousTiers,noOfProvInCurentTier);
        			}
        			logger.info("providersIds::size>>after sublist"+ranks.size());
        			//filter providers based on rank
        			for(ProviderIdVO idVo:routedProviderList){
        			for(Integer rank : ranks){
        				if(idVo.getRank().equals(rank)){
        					logger.info("Resouceids>>provider level>>tier 1"+idVo.getResourceId());
        					providersIds.add(idVo);
        				}
        			}
        		}
        			  logger.info("routedProviderList at the end::"+routedProviderList.size());
        			  logger.info("providersIds after sublisting::"+providersIds.size());
        		}else{
        			if(null!= serviceOrder.getTierRoutedResources() && serviceOrder.getTierRoutedResources().size()>0){
        			logger.info("serviceOrder.getTierRoutedResources() IS NOT NULL for provider level"+serviceOrder.getTierRoutedResources().size());
        			
            		tierProviders = serviceOrder.getTierRoutedResources();
            		for(TierRouteProviders trp:tierProviders){
            			ProviderIdVO vo = new ProviderIdVO();
            			vo.setVendorId(trp.getVendorId());
            			vo.setResourceId(trp.getProviderResourceId());
            			logger.info("RES ID::"+trp.getProviderResourceId());
            			vo.setRoutingTimePerfScore(trp.getPerformanceScore());
            			vo.setRoutingTimeFirmPerfScore(trp.getFirmPerformanceScore());
            			vo.setRank(trp.getRank());
            			providersIds.add(vo);
            		}
            		Comparator<ProviderIdVO> compare = null;
            		//sort the list from db based on rank >> can be removed after ensuring that the order is retained in by the obtained list
        			compare = ProviderIdVO.getComparator(ProviderIdVO.SortParameter.RANK);
        			Collections.sort(providersIds, compare);
        			//fetch sublist based on the priority settings for the spn
        			if(null!=tier && !tier.equals(4) && ((noOfProvInCurentTier+noOfProvInPreviousTiers)<providersIds.size())){
        				logger.info("@inside if@");
        				providersIds = providersIds.subList(noOfProvInPreviousTiers, noOfProvInCurentTier+noOfProvInPreviousTiers);
        			}else{
        				logger.info("@inside else@");
        				providersIds = providersIds.subList(noOfProvInPreviousTiers, providersIds.size());
        			}
        			logger.info("provider ids using sublist"+providersIds);
        		}
        		}
        		}
            foundProviders = providersIds.size();
            logger.info("foundProviders"+foundProviders);
            List<ProviderIdVO> providerIdVOList = new ArrayList<ProviderIdVO>();
            if(null==fePost || (null!=isAutoPost && isAutoPost.equals("true")|| null!=isSaveandAutoPost && isSaveandAutoPost.equals("isAutoPost"))){
            if(foundProviders>0){
            	// check for duplicate providers since there is a chance of duplicate providers getting inserted when order grouping is ON
            		providerIdVOList = findNewRoutedProviders(serviceOrder, providersIds);
            }
            logger.info("providerIdVOList.size()"+providerIdVOList.size());
            //prevent entires being made for duplicate tier id #issue when grouping is ON
            duplicateTierId = findDuplicateTierId(serviceOrder,tier);
            }
            logger.info("duplicateTierId>>"+duplicateTierId);
            //log tier routing entries in so_logging
            if((providerIdVOList.size()>0 && !duplicateTierId)||(null!=fePost && fePost.equals("true"))
            		||(serviceOrder.getTierRoutedResources().size()>0 && serviceOrder.getTierRoutedResources().size()==serviceOrder.getRoutedResources().size())){
            	logger.info("Abt to log...");
            	 performLoggingForTierRoute(serviceOrder, oldTier, tier, foundProviders,hdr.getSpnName(), rejectedByAllInd, counterOfferByAllInd);
            	 processVariables.put(ProcessVariableUtil.REJECTED_BY_ALL_PROV, "false");
            	 processVariables.put(ProcessVariableUtil.COUNTER_OFFER_BY_ALL_PROV, "false");
            }
            if(foundProviders == 0 ){
                //moving to next tier since we did not find providers
                /*oldTier = tier;
                */
                //tier = getTierParameter(processVariables);
            	if(null!=tier && tier.equals(4)){
            		tierRouteUtil.disableTierTimer(processVariables);
                	if(null!= isOfEligible && isOfEligible.equals("true")){
                		logger.info("Routing to marketplace");
                		processVariables.put(ProcessVariableUtil.AUTO_ROUTING_TIER, 9999);
                		providersIds = getProviders(serviceOrder,processVariables);
                		logger.info("MarketPlace Providers count"+providersIds.size());
                	}else{
                		processVariables.put(ProcessVariableUtil.AUTO_ROUTING_TIER, null);
                		processVariables.put(OrderfulfillmentConstants.POST_FROM_FRONTEND_ACTION, null);
                	}
            	}
            	else{
            	tierRouteUtil.setupTierRoutingForCurrentProcess(processVariables, serviceOrder);
            	providersIds = getProviders(serviceOrder,processVariables);
            	}
            }
        	long end = System.currentTimeMillis();
            logger.info("AutoRouteHelper findProviderForTierRouting time taken for "+serviceOrder.getSoId()+" "+(end-start));
          
            logger.info("providersIds at the end::"+providersIds.size());
            if(!duplicateTierId){
            	return providersIds;
            }else{
            	return null;
            }
    }   

    //save the eligible providers to tier_route_eligible_providers 
    private List<ProviderIdVO> saveTierEligibleProviders(List<ProviderIdVO> providersIds,
    		ServiceOrder serviceOrder,
    		String perfCriteriaLevel,
    		String isSaveandAutoPost, 
    		String isRepost, 
    		String postFromFrontend,
    		SPNetHdrVO hdr){
    	logger.info("AutoRouteHelper inside saveTierEligibleProviders>>");
    	List<ProviderIdVO> routedProviderList = new ArrayList<ProviderIdVO>();
    	if(null==perfCriteriaLevel){
    		perfCriteriaLevel = hdr.getPerfCriteriaLevel();
    	}
    	List<TierRouteProviders> routedProviders = new ArrayList<TierRouteProviders>();
    	List<ProviderIdVO> returnList = new ArrayList<ProviderIdVO>();
    	List<TierRouteProviders> routedProvidersTemp = serviceOrder.getTierRoutedResources();
    	logger.info("inside saveTierEligibleProviders>>isSaveandAutoPost>>"+ isSaveandAutoPost +"serviceOrder.getTierRoutedResources()>>"+ serviceOrder.getTierRoutedResources().size());
    	//existing entries in tier_route_eligilble_providers to be deleted based on certain conditions
    	//1. REPOST scenario - to be deleted
    	//2. Save & AutoPost frm frontent - DO NOT DELETE
    	if(postFromFrontend == null && ((null==isSaveandAutoPost) && (serviceOrder.getTierRoutedResources().size()>0) || (null!=isRepost && isRepost.equals("true")))){
    		logger.info("DELETING>>>>");
    		long start = System.currentTimeMillis();
    		serviceOrderDao.deleteTierRoutedProviders(serviceOrder.getSoId());
    		long end = System.currentTimeMillis();
    		logger.info("Time taken to delete providers:"+(end-start));
    		serviceOrder.setTierRoutedResources(routedProviders);
    	}
    	//check for any duplicate providers
    	List<ProviderIdVO> routedProList = findNewTierEligibleProviders(serviceOrder, providersIds);
    	logger.info("after removing dupli>>"+routedProList.size());
    	long start = System.currentTimeMillis();
    	//rank members based on performance score, completed date,name
    	routedProviderList = rankMembers(routedProList,perfCriteriaLevel,serviceOrder.getBuyerId());
    	long end = System.currentTimeMillis();
    		logger.info("Time taken to rank members:"+(end-start)+"SOID::"+serviceOrder.getSoId());
    	
    	logger.info("After delete >> "+serviceOrder.getTierRoutedResources().size());
    	//save the ranked and sorted list to tier_route_eligilble_providers
    	logger.info("done with ranking"+routedProviderList.size());
    	for (ProviderIdVO providerIdVO : routedProviderList) {
    		TierRouteProviders routedProvider = new TierRouteProviders();
            routedProvider.setProviderResourceId(providerIdVO.getResourceId());
            routedProvider.setPerformanceScore(providerIdVO.getRoutingTimePerfScore());
            routedProvider.setFirmPerformanceScore(providerIdVO.getRoutingTimeFirmPerfScore());
            routedProvider.setVendorId(providerIdVO.getVendorId());
            routedProvider.setSpnId(serviceOrder.getSpnId());
            routedProvider.setRank(providerIdVO.getRank());
            routedProvider.setServiceOrder(serviceOrder);
            routedProviders.add(routedProvider);

        }
    	logger.info("done with looping"+routedProviders.size());
    	serviceOrder.setTierRoutedResources(routedProviders); 
    	try{
    		serviceOrderDao.update(serviceOrder);
    	}catch(Exception e){
    		logger.info("EXCEPTION OCCURED WHILE SAVING!!!!"+e);
    	}
    	if(routedProviderList.size()>0){//if there are any new providers other than those selected from frontend
    		logger.info("inside IF>>routedProviderList.size()>0");
    		returnList = routedProviderList;
    	}else if(routedProvidersTemp.size()>0){//if all the available providers in spn has been selected from frontend,return all those ranked providers from db
    		logger.info("inside ELSE IF>>routedProviderList.size()>0");
    		for (TierRouteProviders providerIdVO : routedProvidersTemp) {
    			ProviderIdVO routedProvider = new ProviderIdVO();
    			routedProvider.setResourceId(providerIdVO.getProviderResourceId());
    			routedProvider.setRoutingTimePerfScore(providerIdVO.getPerformanceScore());
    			routedProvider.setRoutingTimeFirmPerfScore(providerIdVO.getFirmPerformanceScore());
    			routedProvider.setVendorId(providerIdVO.getVendorId());
    			routedProvider.setRank(providerIdVO.getRank());
    			returnList.add(routedProvider);
            }
    	}
    	logger.info("returnList"+returnList.size());
    	return returnList;
    }
    
    //method to rank members based on score,completed date or name
private List<ProviderIdVO> rankMembers(List<ProviderIdVO> srcRoutedProviderList, String criteriaLevel, Long buyerId) {
		long start = System.currentTimeMillis();
		logger.info("Inside rank members");
		//to get the elements with the same scores
		List<Integer> duplicateList =new ArrayList<Integer>();
		try
		{
			long startTime = System.currentTimeMillis();
			duplicateList=getDuplicatesProviders(srcRoutedProviderList,criteriaLevel);
			long endTime = System.currentTimeMillis();
			logger.info("Inside rank members>>getDuplicatesProviders()>>total time taken:"+(endTime-startTime));
			logger.info("Inside duplicateCodeNew");
		}
		catch(Exception e)
		{
		//***to be removed - start
		long startTime = System.currentTimeMillis();
		 duplicateList = getDuplicates(srcRoutedProviderList,criteriaLevel);
		 long endTime = System.currentTimeMillis();
		logger.info("Inside rank members>>getDuplicates()>>total time taken:"+(endTime-startTime));
		 logger.info("Inside duplicateCodePrevious" );
		 logger.info("exception is"+e );
		//***to be removed - end
		}
		//get the date of last completed order for prov/firm with same score
		try{
			if(null != duplicateList && duplicateList.size() > 0){
				long start1= System.currentTimeMillis();
				List<ProviderIdVO> dateList ;
				try
				{
					long startTime = System.currentTimeMillis();
					dateList=serviceOrderDao.getCompletedDateForSo(duplicateList, criteriaLevel, buyerId.intValue());
					long endTime = System.currentTimeMillis();
					logger.info("Inside rank members>>getCompletedDateForSo()>>total time taken:"+(endTime-startTime));
					logger.info("New getCompletedDateForSO is working");
				}
				catch(Exception e)
				{
					//***to be removed - start
					long startTime = System.currentTimeMillis();
					dateList=serviceOrderDao.getCompletedDate(duplicateList, criteriaLevel, buyerId.intValue());
					long endTime = System.currentTimeMillis();
					logger.info("Inside rank members>>getCompletedDate()>>total time taken:"+(endTime-startTime));
					logger.info("New getCompletedDateForSO is not working"+e);
					//***to be removed - end
				}
				long end1= System.currentTimeMillis();
				logger.info("time taken to fetch completed date :"+(end1-start1));
				if(null != dateList){
				logger.info("dateList size:::"+dateList.size());
				}
				if(null != dateList && dateList.size()!=0){
					logger.info("Date List is not null!!");
					long start2= System.currentTimeMillis();
					for(ProviderIdVO provider : srcRoutedProviderList){
						for(ProviderIdVO dto : dateList){
							if(null != criteriaLevel && criteriaLevel.equals("PROVIDER")){
							if(provider.getResourceId().intValue()== dto.getResourceId().intValue()){
								provider.setCompletedDate(dto.getCompletedDate());
							}
							}else{
								if(provider.getVendorId().intValue()== dto.getVendorId()){
									provider.setCompletedDate(dto.getCompletedDate());
								}
							}
						}
					}
					long end2= System.currentTimeMillis();
					logger.info("time taken to loop and set completed date :"+(end2-start2));
				}
			}
			//sort the list based on score, date and name
			long start3= System.currentTimeMillis();
			Comparator<ProviderIdVO> compare = null;
			if(null != criteriaLevel && criteriaLevel.equals("FIRM")){
				logger.info("getcomparator for Firm Level");
				if(null != duplicateList && duplicateList.size() > 0){
				compare = ProviderIdVO.getComparator(ProviderIdVO.SortParameter.FIRM_SCORE_DESCENDING,
						ProviderIdVO.SortParameter.DATE_ASCENDING, ProviderIdVO.SortParameter.FIRM_NAME_ASCENDING);
				}else if(null != duplicateList && duplicateList.size() == 0){
					logger.info("getcomparator for Firm Level w/o duplicates");
					compare = ProviderIdVO.getComparator(ProviderIdVO.SortParameter.FIRM_SCORE_DESCENDING);
				}
			}
			else{
				logger.info("getcomparator for Provider Level");
				if(null != duplicateList && duplicateList.size() > 0){
				compare = ProviderIdVO.getComparator(ProviderIdVO.SortParameter.SCORE_DESCENDING,
						ProviderIdVO.SortParameter.DATE_ASCENDING, ProviderIdVO.SortParameter.PROVIDER_NAME_ASCENDING);
				}else if(null != duplicateList && duplicateList.size() == 0){
					logger.info("getcomparator for Provider Level w/o duplicates");
					compare = ProviderIdVO.getComparator(ProviderIdVO.SortParameter.SCORE_DESCENDING);
				}
			}
			try{
				long startSort= System.currentTimeMillis();
			Collections.sort(srcRoutedProviderList, compare);
			long endSort= System.currentTimeMillis();
			logger.info("time taken for comparator sort"+(endSort-startSort));
			}catch(Exception e){
				logger.info("Exception while SORT"+e);
			}
			long end3= System.currentTimeMillis();
			logger.info("time taken to loop and sort"+(end3-start3));

			//set the rank
			long start4= System.currentTimeMillis();
			List <Integer> vendorIds = new ArrayList<Integer>();
			for(ProviderIdVO sortedList : srcRoutedProviderList){
				if(!vendorIds.contains(sortedList.getVendorId())){
				vendorIds.add(sortedList.getVendorId());
				}
			}
			long endVendorList= System.currentTimeMillis();
			logger.info("time taken to extractVendorIds"+(endVendorList-start4));
			//set the rank
			long start5= System.currentTimeMillis();
			if(null != criteriaLevel && criteriaLevel.equals("FIRM")){
				logger.info("Setting rank for firm");
				if(null != srcRoutedProviderList && srcRoutedProviderList.size()>0){
					Integer rank = 0;
					for(Integer id: vendorIds){
						rank = rank + 1;
							for(ProviderIdVO pro : srcRoutedProviderList){	
								if(null != pro && id.equals(pro.getVendorId())){
									pro.setRank(rank);
							}
						}
					}
				}
			}else{
				logger.info("Setting rank for prov");
				if(null != srcRoutedProviderList && srcRoutedProviderList.size()>0){
					Integer rank = 0;
					for(ProviderIdVO coverage : srcRoutedProviderList){
						if(null != coverage){
							rank = rank + 1;
							coverage.setRank(rank);
						}
					}
				}
			}
			long end4= System.currentTimeMillis();
			logger.info("time taken to loop and set rank"+(end4-start5));

		}
		catch(Exception e){
			logger.info("Exception in OFServiceOrderMapper rankMembers() due to "+ e);
		}
		long end = System.currentTimeMillis();
		logger.info("Inside rank members total time taken:"+(end-start));

		return srcRoutedProviderList;
	}
	
//to get the elements with the same scores for providers
private List<Integer> getDuplicatesProviders(List<ProviderIdVO> coveragelist,String criteriaLevel){	
	
		if(null != coveragelist && coveragelist.size() > 0){
			List<Integer> duplicateList=new ArrayList<Integer>();
			if(null != criteriaLevel && criteriaLevel.equals("FIRM")){

				//sort the list based on score
			Comparator<ProviderIdVO> compare = null;
		compare = ProviderIdVO.getComparator(ProviderIdVO.SortParameter.FIRM_SCORE_DESCENDING);
		Collections.sort(coveragelist, compare);
		
		Double perfScoreKey=null;
		for(ProviderIdVO list : coveragelist){
			perfScoreKey=list.getRoutingTimeFirmPerfScore();
			break;
		}
		HashMap<Double,List<Integer>> coverageMap=new HashMap<Double,List<Integer>>();
		List<Integer> providerList=new ArrayList<Integer>();
		for(ProviderIdVO list : coveragelist){
			if(list.getRoutingTimeFirmPerfScore().equals(perfScoreKey))
			{
				providerList.add(list.getVendorId());
			}
			else
			{
				coverageMap.put(perfScoreKey,providerList);
				providerList=new ArrayList<Integer>();
				providerList.add(list.getVendorId());
			}
			perfScoreKey=list.getRoutingTimeFirmPerfScore();
		}
		coverageMap.put(perfScoreKey,providerList);
		if(coverageMap.size()>0){
			Set<Double> keys = coverageMap.keySet();
			for (Iterator i = keys.iterator(); i.hasNext();) {
				Double key = (Double) i.next();
				List<Integer> keyValue= coverageMap.get(key);
				if(keyValue.size()>1){
					duplicateList.addAll(keyValue);
				}
			}
		}
			}
			else if(null != criteriaLevel && criteriaLevel.equals("PROVIDER")){
		//sort the list based on score
				Comparator<ProviderIdVO> compare = null;
			compare = ProviderIdVO.getComparator(ProviderIdVO.SortParameter.SCORE_DESCENDING);
			Collections.sort(coveragelist, compare);
			
			Double perfScoreKey=null;
			for(ProviderIdVO list : coveragelist){
				perfScoreKey=list.getRoutingTimePerfScore();
				break;
			}
			HashMap<Double,List<Integer>> coverageMap=new HashMap<Double,List<Integer>>();
			List<Integer> providerList=new ArrayList<Integer>();
			for(ProviderIdVO list : coveragelist){
				if(list.getRoutingTimePerfScore().equals(perfScoreKey))
				{
					providerList.add(list.getResourceId());
				}
				else
				{
					coverageMap.put(perfScoreKey,providerList);
					providerList=new ArrayList<Integer>();
					providerList.add(list.getResourceId());
				}
				perfScoreKey=list.getRoutingTimePerfScore();
			}
			coverageMap.put(perfScoreKey,providerList);

			if(coverageMap.size()>0){
				
				Set<Double> keys = coverageMap.keySet();
				for (Iterator i = keys.iterator(); i.hasNext();) {
					Double key = (Double) i.next();
					List<Integer> keyValue= coverageMap.get(key);
					
					if(keyValue.size()>1){
						duplicateList.addAll(keyValue);
					}
				}
			}
				
			}
			if(null!=duplicateList){
		logger.info("duplicateList size "+duplicateList.size());	
			}
		return duplicateList;	
		}		
	return null;
}

	//to get the elements with the same scores
	private List<Integer> getDuplicates(List<ProviderIdVO> coveragelist,String criteriaLevel){		
		long start = System.currentTimeMillis();
		List<Integer> duplicateList = new ArrayList<Integer>();
		for(ProviderIdVO list : coveragelist){
			int count = 0;
			for(ProviderIdVO provider : coveragelist){
				if(null != criteriaLevel && criteriaLevel.equals("PROVIDER")){
				if(null != list.getRoutingTimePerfScore() && null != provider.getRoutingTimePerfScore() && list.getRoutingTimePerfScore().doubleValue() == provider.getRoutingTimePerfScore().doubleValue()){
					count = count + 1;
				}
				}else{
					if(null != list.getRoutingTimeFirmPerfScore() && null != provider.getRoutingTimeFirmPerfScore() && list.getRoutingTimeFirmPerfScore().doubleValue() == provider.getRoutingTimeFirmPerfScore().doubleValue()){
						count = count + 1;
					}
				}
			}
			if(count > 1){
				for(ProviderIdVO provider : coveragelist){
					if(null != criteriaLevel && criteriaLevel.equals("PROVIDER")){
					if(null != list.getRoutingTimePerfScore() && null != provider.getRoutingTimePerfScore() && list.getRoutingTimePerfScore().doubleValue() == provider.getRoutingTimePerfScore().doubleValue()){
						if(!duplicateList.contains(provider.getResourceId())){
							duplicateList.add(provider.getResourceId());
						}
					}}
					else{
						if(null != list.getRoutingTimeFirmPerfScore() && null != provider.getRoutingTimeFirmPerfScore() && list.getRoutingTimeFirmPerfScore().doubleValue() == provider.getRoutingTimeFirmPerfScore().doubleValue()){
							if(!duplicateList.contains(provider.getVendorId())){
								duplicateList.add(provider.getVendorId());
							}
						}	
					}
				}
			}
		}
		long end = System.currentTimeMillis();
		logger.info("For finding duplicates time taken:"+(end-start));
		return duplicateList;		
	}
    
	private List<ProviderIdVO> fetchTierEligibleProviders(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers,List<Integer> perfScores){
    	List<ProviderIdVO> providers = new ArrayList<ProviderIdVO>();
    	List<TierRouteProviders> tierProviders = serviceOrderDao.findProvidersForTierRouting(soId,noOfProvInCurentTier,noOfProvInPreviousTiers,perfScores);
    	if(null !=providers){
    		for(TierRouteProviders trp:tierProviders){
    			ProviderIdVO vo = new ProviderIdVO();
    			vo.setVendorId(trp.getVendorId());
    			vo.setResourceId(trp.getProviderResourceId());
    			vo.setRoutingTimePerfScore(trp.getPerformanceScore());
    			vo.setRoutingTimeFirmPerfScore(trp.getFirmPerformanceScore());
    			providers.add(vo);
    		}
    	}
    	return providers;
    }
    
    private void performLoggingForTierRoute(ServiceOrder serviceOrder, Integer oldTier, Integer tier, Integer numberOfProviders, String spnNetwork, String rejectedByAllInd, String counterOfferByAllInd){
        logger.info("Logging tier route details..");
    	SOLogging logging = null;
        if((tier.intValue() == 1 && numberOfProviders >0) || (serviceOrder.getRoutedResources().size()==0)){
        	logger.info("Logging starting tier details..");
        	logging = getLogMessage(spnNetwork, serviceOrder.getBuyerId(), tier, "Start Tier Routing.", numberOfProviders);
        } else if(numberOfProviders == 0){
            logging = getLogMessage(spnNetwork, serviceOrder.getBuyerId(), tier, "No providers in current Tier.", numberOfProviders);
        } else if(null!=rejectedByAllInd && rejectedByAllInd.equals("true")){
        	logging = getLogMessage(spnNetwork, serviceOrder.getBuyerId(), tier, "The order moved to next tier as all providers in the previous tier rejected the order.", numberOfProviders);
        }else if(null!= counterOfferByAllInd && counterOfferByAllInd.equals("true")){
        	logging = getLogMessage(spnNetwork, serviceOrder.getBuyerId(), tier, "The order moved to next tier as all providers in the previous tier placed counter offer.", numberOfProviders);
        }else {
            logging = getLogMessage(spnNetwork, serviceOrder.getBuyerId(), tier, "Time Elapsed in previous Tier.", numberOfProviders);
        }

        //check if the order is part of the group order
        if(null != serviceOrder.getSoGroup()){
            for(ServiceOrder so : serviceOrder.getSoGroup().getServiceOrders()){
                SOLogging log = logging.copy();
                log.setServiceOrder(so);
                serviceOrderDao.save(log);
            }
        } else {
        	logger.info("so logging "+logging.getChgComment());
            logging.setServiceOrder(serviceOrder);
            serviceOrderDao.save(logging);
        }
    }

    private SOLogging getLogMessage(String spnName, Long buyerId, Integer tier, String reason, Integer providerCount) {
        String message = String.format("Service Order is ROUTED to %1$d providers<br/>SPN:%2$s<br/>Priority:%3$d<br/>Reason:"
                ,providerCount, spnName, tier) + reason;
        SOLogging soLogging = new SOLogging();
		soLogging.setEntityId(buyerId);
		soLogging.setModifiedBy(RoleType.System.name());
		soLogging.setCreatedByName(RoleType.System.name());
		soLogging.setRoleId(RoleType.System.getId());
        soLogging.setActionId(3);//Routed to Provider
		soLogging.setChgComment(message);
        return soLogging;
    }

    private Integer getTierParameter(Map<String, Object> processVariables) {
        return (Integer)processVariables.get(ProcessVariableUtil.AUTO_ROUTING_TIER);
    }

	public List<ProviderIdVO> getProviders(ServiceOrder serviceOrder){
		logger.info("Entered AutoRouteHelper.getProviders()....2");
        ProviderListCriteriaForAutoRoutingVO listCriteriaForAutoRoutingVO = new ProviderListCriteriaForAutoRoutingVO();
        listCriteriaForAutoRoutingVO.setBuyerId(serviceOrder.getBuyerId());
        listCriteriaForAutoRoutingVO.setBuyerTemplateName(getBuyerCustomRefTemplateName(serviceOrder));
        listCriteriaForAutoRoutingVO.setServiceOrderId(serviceOrder.getSoId());
        SORoutingRuleAssoc assoc = carAssociationDao.getCARAssociation(serviceOrder.getSoId());
        listCriteriaForAutoRoutingVO.setConditionalRuleId(assoc.getRoutingRuleHdrId());
        listCriteriaForAutoRoutingVO.setConditionalAutoRoute(true);
        return marketPlatformProviderBO.findProvidersForAutoRouting(listCriteriaForAutoRoutingVO);
	}
	
    private String getBuyerCustomRefTemplateName(ServiceOrder serviceOrder) {
        List<SOCustomReference> customReferences = serviceOrder.getCustomReferences();
        if (customReferences != null && !customReferences.isEmpty()) {
            for (SOCustomReference customRef : customReferences) {
                if (customRef.getBuyerRefTypeName() != null && customRef.getBuyerRefTypeName().equals(SOCustomReference.CREF_TEMPLATE_NAME)) {
                    return customRef.getBuyerRefValue();
                }
            }
        }
        return null;
    }

    public void saveRoutedProviders(List<ProviderIdVO> providerIdVOList, ServiceOrder serviceOrder, Integer tier, Integer nextTier, String isRepost, String autoPost, String perfCriteriaLevel, Integer provInPrevTiers){
    	long start = System.currentTimeMillis();
    	logger.info("Inside saveRoutedProviders()>>TIER ID>>"+tier);
    	logger.info("Inside saveRoutedProviders()>>ISREPOST>>"+isRepost);
    	logger.info("Inside saveRoutedProviders()>>AUTOPOST>>"+autoPost);
    	logger.info("Inside saveRoutedProviders()>>provInPrevTiers>>"+provInPrevTiers);
    	if(null==perfCriteriaLevel && null!= serviceOrder.getSpnId()){
    		SPNetHdrVO hdr = serviceOrderDao.fetchSpnInfo(serviceOrder.getSpnId());
    		perfCriteriaLevel = hdr.getPerfCriteriaLevel();
    	}
    	//provInPrevTiers == 0 ==> starting tier
    	logger.info("inside saveRoutedProviders>>perfCriteriaLevel>>"+perfCriteriaLevel);
    	List<ProviderIdVO> providerList = new ArrayList<ProviderIdVO>();
    	Boolean tierRouteInd = serviceOrder.getSOWorkflowControls().getTierRouteInd();
    	logger.info("Inside saveRoutedProviders()>>tierRouteInd>>"+tierRouteInd);
    	List<RoutedProvider> routedProviders = serviceOrder.getRoutedResources();
    	if(null!=serviceOrder.getRoutedResources() && serviceOrder.getRoutedResources().size()>0 && autoPost==null){
    	if(null!=isRepost && isRepost.equals("true")&&(null!= provInPrevTiers && provInPrevTiers == 0 || !(tierRouteInd))){
    		logger.info("Deleting routed resources>>count>>"+serviceOrder.getRoutedResources().size());
    		serviceOrderDao.deleteSoRoutedProviders(serviceOrder.getSoId());
    		routedProviders = new ArrayList<RoutedProvider>();
    		serviceOrder.setRoutedResources(routedProviders);
    	}
    	}
    	logger.info("Inside saveRoutedProviders()>>abt to enter findNewRoutedProviders");
    	
    	// get so routed providers 
    	
    	List<RoutedProvider> routedProvidersRejectList = new ArrayList<RoutedProvider>();
    	List <RoutedProvider> routedProviderNewList=serviceOrder.getRoutedResources();
    	
    	//commented the code for 19283
    	/*if(null!=routedProviderNewList && routedProviderNewList.size()>0 && (null!=autoPost && autoPost.equals("isAutoPost") && provInPrevTiers == 0)){
    		logger.info(" for reject ");
    	for(RoutedProvider provider:routedProviderNewList){    
    		logger.info(" provider.getProviderResponse() "+provider.getProviderResponse());
    		if(null!=provider.getProviderResponse() && provider.getProviderResponse().equals(ProviderResponseType.REJECTED)){
        		logger.info(" entered rejected ");
        		serviceOrderDao.deleteSoRoutedProviders(serviceOrder.getSoId());
        		serviceOrder.setRoutedResources(routedProvidersRejectList);
        		routedProviders=new ArrayList<RoutedProvider>();           
        		break;
    		}
	
    	}
    	}*/           

    	providerList = findNewRoutedProviders(serviceOrder, providerIdVOList);
    	logger.info("Final list for routed prov save>>"+providerList.size());
    	logger.info("Inside saveRoutedProviders()>>perfCriteriaLevel"+perfCriteriaLevel);
      for (ProviderIdVO providerIdVO : providerList) {
            RoutedProvider routedProvider = new RoutedProvider();
            routedProvider.setRoutedDate(new Date());
            routedProvider.setProviderResourceId(new Long(providerIdVO.getResourceId()));
            if(null != perfCriteriaLevel && perfCriteriaLevel.equals("PROVIDER")){
            	routedProvider.setPerfScore(providerIdVO.getRoutingTimePerfScore());
            }else if(null != perfCriteriaLevel && perfCriteriaLevel.equals("FIRM")){
                routedProvider.setFirmPerfScore(providerIdVO.getRoutingTimeFirmPerfScore());
            }
            routedProvider.setVendorId(providerIdVO.getVendorId());
            routedProvider.setSpnId(serviceOrder.getSpnId());
            if (tier != null) routedProvider.setTierId(tier);
            routedProvider.setPriceModel(serviceOrder.getPriceModel());
            routedProvider.setServiceOrder(serviceOrder);
            routedProviders.add(routedProvider);
        }
      serviceOrder.setRoutedResources(routedProviders);
      SOWorkflowControls soWorkflowControls = serviceOrder.getSOWorkflowControls();
      //soWorkflowControls.setServiceOrder(serviceOrder);
      soWorkflowControls.setCurrentTier(tier);
      soWorkflowControls.setNextTier(nextTier);
      soWorkflowControls.setCurrentTierRoutedDate(new Date());
      serviceOrder.setSOWorkflowControls(soWorkflowControls);
      serviceOrderDao.update(serviceOrder);
  	long end = System.currentTimeMillis();
  	logger.info("serviceOrder.getRoutedResources().size()>>"+serviceOrder.getRoutedResources().size());
  	logger.info("Inside auto rote helper for saving routed providers time taken:"+(end-start));

    }
    
    private List<ProviderIdVO> findNewTierEligibleProviders(ServiceOrder serviceOrder, List<ProviderIdVO> providerIdVOList){
    	long start = System.currentTimeMillis();
    	List<ProviderIdVO> retVal = new ArrayList<ProviderIdVO>(); 
    	List<TierRouteProviders> routedProviders = serviceOrder.getTierRoutedResources();
    	logger.info("provs in db>>"+routedProviders.size());
        for(ProviderIdVO proId : providerIdVOList){
        	boolean found = false;
	    	for(TierRouteProviders routedProvider : routedProviders){
	    		if(null != routedProvider.getProviderResourceId() && null!= proId.getResourceId()){
	            if(proId.getResourceId().intValue() == routedProvider.getProviderResourceId().intValue()){
	            	found = true;
	            	break;
	            }
	    		}
	        }
	    	if(!found){
	    		retVal.add(proId);
	    	}
        }
        logger.info("final list after removing dupli>>"+retVal.size());
    	long end = System.currentTimeMillis();
    	logger.info("Inside auto rote helper for findNewTierEligibleProviders time taken:"+(end-start));
      	return retVal;

    }
    
    private boolean findDuplicateTierId(ServiceOrder serviceOrder,Integer tier){
    	List<RoutedProvider> routedProviders = serviceOrder.getRoutedResources();
    	List<Integer> tierIds =  new ArrayList<Integer>();
    	for (RoutedProvider rps: routedProviders){
    		tierIds.add(rps.getTierId());
    	}
    	if(tierIds.contains(tier)){
    		return true;
    	}
    	return false;
    }
    
    private List<ProviderIdVO> findNewRoutedProviders(ServiceOrder serviceOrder, List<ProviderIdVO> providerIdVOList){
    	logger.info("Entered findNewRoutedProviders");
    	long start = System.currentTimeMillis();
    	List<ProviderIdVO> retVal = new ArrayList<ProviderIdVO>(); 
    	List<RoutedProvider> routedProviders = serviceOrder.getRoutedResources();
    	logger.info("routed provs in db>>"+routedProviders.size());
        for(ProviderIdVO proId : providerIdVOList){
        	//logger.info("Entered findNewRoutedProviders>>1");
        	boolean found = false;
	    	for(RoutedProvider routedProvider : routedProviders){
	    		//logger.info("Entered findNewRoutedProviders>>2");
	            if(proId.getResourceId().longValue() == routedProvider.getProviderResourceId().longValue()){
	            	found = true;
	            	break;
	            }
	        }
	    	if(!found){
	    		retVal.add(proId);
	    	}
        }
        logger.info("final routed pro list after removing dupli>>"+retVal.size());
    	long end = System.currentTimeMillis();
    	logger.info("Inside auto rote helper for findNewRoutedProviders time taken:"+(end-start));
    	return retVal;
    }
 
    public RoutingRuleHdrVO getProvidersListOfRule(Integer ruleId)
    {
    	logger.info("Inside provider list of rule");
    	RoutingRuleHdrVO rrhList=carAssociationDao.getProvidersListOfRule(ruleId);
    	
    	return rrhList;
    }
    
    public List<ProviderIdVO> getProviderForAutoAcceptance(
			ServiceOrder serviceOrder) {
		RoutingRuleHdrVO routingRuleHdrVO = getProvidersListOfRule(serviceOrder
				.getCondAutoRouteRuleId());
		List<ProviderIdVO> pvo = new ArrayList<ProviderIdVO>();

		if (null != routingRuleHdrVO.getRoutingRuleVendor()
				&& routingRuleHdrVO.getRoutingRuleVendor().size() == 1) {
			logger.info("Eligible firm id for auto acceptanceddd:"
					+ routingRuleHdrVO.getRoutingRuleVendor().get(0)
							.getRoutingRuleVendorId());
			Integer eligibleFirmId = routingRuleHdrVO.getRoutingRuleVendor()
					.get(0).getRoutingRuleVendorId();
			pvo = marketPlatformProviderBO
					.getProviderForAutoAcceptance(eligibleFirmId,serviceOrder.getSpnId());
			logger.info("total privder list size" + pvo.size());

		}

		if (null != pvo) {
			logger.info("Size of resultant provider list" + pvo.size());
		}
		return pvo;

	}

    public void UpdateProvidersForConditionalAutoRouting(
            ServiceOrder serviceOrder) {
      List<ProviderIdVO> providerList = new ArrayList<ProviderIdVO>();
      List<RoutedProvider> routedProviders = serviceOrder
                  .getRoutedResources();
      HashMap<Long, String> existingProvidersMap = new HashMap<Long, String>();
      boolean existingProv=false;
      if(null!=routedProviders){
    	  logger.info("Entering new code:create map");
            for(RoutedProvider routedProv :routedProviders){
                  existingProvidersMap.put(routedProv.getProviderResourceId(), "ExistingProvider");
            }
            existingProv=true;
      }
      logger
                  .info("Inside UpdateProvidersForConditionalAutoRouting of auto helper:-->");
      RoutingRuleHdrVO routingRuleHdrVO = getProvidersListOfRule(serviceOrder
                  .getCondAutoRouteRuleId());
      List<ProviderIdVO> prvUnderAFirm = new ArrayList<ProviderIdVO>();

      for (RoutingRuleVendorVO rrv : routingRuleHdrVO.getRoutingRuleVendor()) {
            logger.info("Eligible firm id for auto acceptanceddd:"
                        + rrv.getRoutingRuleVendorId());
            //SL-19021 Passing the spn id with provider firm id to fix the JIRA Issue
            logger.info("SPN ID fetched from serivce order object"+serviceOrder.getSpnId());
            prvUnderAFirm = marketPlatformProviderBO
                        .getProviderForAutoAcceptance(rrv.getRoutingRuleVendorId(),serviceOrder.getSpnId());
            logger
                        .info("prvUnderAFirm object contains the provider id who have wf state id 6");
            logger.info("total privder list size" + prvUnderAFirm.size());
            providerList.addAll(prvUnderAFirm);
      }
      logger.info("Total providers for the so are :--->"
                  + providerList.size());

      for (ProviderIdVO providerIdVO : providerList) {
            RoutedProvider routedProvider = new RoutedProvider();
            // This need not be set as the setRoutedDate method in
            // ServiceOrder.java handles this also
            // routedProvider.setRoutedDate(new Date());
            routedProvider.setProviderResourceId(new Long(providerIdVO
                        .getResourceId()));
            logger
                        .info("Inside setting the routed provider to the service order object:-->");
            logger
                        .info("Resource id and vendor id  before setting to the routed provider object"
                                    + providerIdVO.getResourceId()
                                    + ":"
                                    + providerIdVO.getVendorId());
            routedProvider.setVendorId(providerIdVO.getVendorId());
            if (null != serviceOrder.getSpnId()) {
                  routedProvider.setSpnId(serviceOrder.getSpnId());
            } else {
                  routedProvider.setSpnId(null);
            }
            routedProvider.setTierId(null);
            routedProvider.setPriceModel(serviceOrder.getPriceModel());
            routedProvider.setServiceOrder(serviceOrder);
            if(existingProv){
            	logger.info("Entering new code:duplicate check");
                  if(!existingProvidersMap.containsKey(routedProvider.getProviderResourceId())){
                        //adding to the list if it is not a duplicate value
                        routedProviders.add(routedProvider);
                  }
            }else{
                  routedProviders.add(routedProvider);
            }                
      }
      Date routedDate = new Date();
      if(routedProviders.size()>0){
      if(null == serviceOrder.getInitialRoutedDate()){
            serviceOrder.setInitialRoutedDate(routedDate);
      }
      serviceOrder.setRoutedDate(routedDate);
      }
      logger.info("Size of routed providers for the so"
                  + routedProviders.size());
      serviceOrderDao.update(serviceOrder);
      logger.info("After updateing the so routed providers table");
}
    
	public void setMarketPlatformProviderBO(
			IMarketPlatformProviderBO marketPlatformProviderBO) {
		this.marketPlatformProviderBO = marketPlatformProviderBO;
	}

	public void setServiceOrderDao(IServiceOrderDao serviceOrderDao) {
		this.serviceOrderDao = serviceOrderDao;
	}

    public void setCarAssociationDao(ICARAssociationDao carAssociationDao) {
        this.carAssociationDao = carAssociationDao;
    }

    public void setTierRouteUtil(TierRouteUtil tierRouteUtil) {
        this.tierRouteUtil = tierRouteUtil;
    }

    public void setQuickLookupCollection(QuickLookupCollection quickLookupCollection) {
        this.quickLookupCollection = quickLookupCollection;
    }

	
}
