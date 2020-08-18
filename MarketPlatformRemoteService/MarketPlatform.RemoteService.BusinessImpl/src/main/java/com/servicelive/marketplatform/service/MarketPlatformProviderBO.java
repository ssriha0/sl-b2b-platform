package com.servicelive.marketplatform.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.dto.vo.providerSearch.ProviderDetailsVO;
import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.spn.SPNetHeaderVO;
import com.servicelive.domain.common.Contact;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.marketplatform.common.vo.ProviderIdVO;
import com.servicelive.marketplatform.common.vo.ProviderListCriteriaForAutoRoutingVO;
import com.servicelive.marketplatform.dao.IProviderDao;
import com.servicelive.marketplatform.dao.ISkillTreeDao;
import com.servicelive.marketplatform.provider.domain.SkillNode;
import com.servicelive.marketplatform.serviceinterface.IAutoRoutingProviderSearchBO;
import com.servicelive.marketplatform.serviceinterface.IMarketPlatformProviderBO;

public class MarketPlatformProviderBO implements IMarketPlatformProviderBO {
    protected final Logger logger = Logger.getLogger(getClass());

    private IProviderDao providerDao;
    private ISkillTreeDao skillTreeDao;
    private IAutoRoutingProviderSearchBO autoRoutingProviderSearchBO;

    @Transactional(readOnly = true)
    public Contact retrieveProviderResourceContactInfo(Long providerRsrcId) {
        ServiceProvider providerResource = providerDao.findProviderResourceById(providerRsrcId);
        if (providerResource == null) return null;

        return providerResource.getContact();
    }

    public Contact retrieveProviderPrimaryResourceContactInfo(Long providerId) {
        ProviderFirm provider = providerDao.findProviderById(providerId);
        if (provider == null || provider.getPrimaryServiceProvider() == null) return null;

        return provider.getPrimaryServiceProvider().getContact();
    }

    public List<Contact> retrieveProviderResourceContactInfoList(List<Long> providerRsrcIdList) {
        List<Contact> providerContactList = new ArrayList<Contact>();
        for (Long providerId : providerRsrcIdList) {
            providerContactList.add(retrieveProviderResourceContactInfo(providerId));
        }
        return providerContactList;
    }

    @Transactional(readOnly = true)
    public List<Contact>  retrieveProviderAdminContactInfoList(List<Long> providerFirmIdList) {
    	List<Contact> providerAdminContactList = new ArrayList<Contact>();
    	
    	for (Long providerFirmId : providerFirmIdList) {
        	ServiceProvider providerAdmin = providerDao.findProviderAdminResource(providerFirmId);
        	if (providerAdmin != null) {
        		providerAdminContactList.add(providerAdmin.getContact());
        	}
        }
        
        return providerAdminContactList;
    }

    
    @Transactional(readOnly = true)
    public List<SkillNode> retrieveAllAvailableProviderSkills() {
        return skillTreeDao.getAllAvailableProviderSkills();
    }

    public SkillNode getSkillNodeById(Long skillNodeId) {
        return skillTreeDao.findById(skillNodeId);
    }

    @Transactional(readOnly = true)
    public List<ProviderIdVO> findProvidersForAutoRouting(ProviderListCriteriaForAutoRoutingVO providerListCriteriaVO) {
    	logger.info("inside the marketplatfor bo clss");
        logProviderSearchCriteriaForAutoRouting(providerListCriteriaVO);
        List<Integer> providerRsrcIdList = new ArrayList<Integer>();
        if (providerListCriteriaVO.isConditionalAutoRoute()) {
            List<Integer> conditionalAutoRouteProviderList = autoRoutingProviderSearchBO.findProvidersForConditionalAutoRouting(providerListCriteriaVO);
            if (conditionalAutoRouteProviderList != null){
            	logger.info("size of the provider liast "+conditionalAutoRouteProviderList.size());
            	providerRsrcIdList = conditionalAutoRouteProviderList;
        } 
            return retrieveServiceProviderListForProviderResourcesForCAR(providerRsrcIdList);
        }else {
        	if(null!=providerListCriteriaVO.getTierId()&&!(providerListCriteriaVO.getTierId().equals(9999))){
        	logger.info("abt to fetch providers for tier::tierID"+providerListCriteriaVO.getTierId());
        	}else{
        		logger.info("abt to fetch providers for marketplace"+providerListCriteriaVO.getTierId());	
        	}
        	long start = System.currentTimeMillis();
            List<ProviderResultVO> providerList = autoRoutingProviderSearchBO.findProvidersForAutoRouting(providerListCriteriaVO);
        	long end = System.currentTimeMillis();
     	   logger.info("Time taken MktpltformBo findProvidersForAutoRouting time taken"+(end-start)+"SOID>>"+providerListCriteriaVO.getServiceOrderId());

        	   logger.info("PROVIDERS FOUND::"+providerList.size());
            return retrieveServiceProviderListForProviderResources(providerList);

        }
    }

    
    @Transactional
    public List<ProviderIdVO> getProviderForAutoAcceptance(Integer eligibleFirmId,Integer spnId)
    {
    	logger.info("inside getProviderForAutoAcceptance method market platform provider bo after going  inside  getProviderForAutoAcceptance method of MarketPlatformRemoteServiceClient");
    	 List<Integer> providerRsrcIdList = new ArrayList<Integer>();
    	 List<ProviderIdVO> serviceProviderIdList = new ArrayList<ProviderIdVO>();
    	//SL-19021 Passing the spn id with provider firm id to fix the JIRA Issue
    	 providerRsrcIdList=autoRoutingProviderSearchBO.getProviderForAutoAcceptance(eligibleFirmId,spnId);
    	 logger.info("Total size of provider resource"+providerRsrcIdList.size());
    	 serviceProviderIdList=retrieveServiceProviderListForProviderResourcesForCAR(providerRsrcIdList);
    	 return serviceProviderIdList;
    }
    
    private void logProviderSearchCriteriaForAutoRouting(ProviderListCriteriaForAutoRoutingVO criteria) {
        if (logger.isDebugEnabled()){
        StringBuilder sb = new StringBuilder("\nMarketPlatformProviderBO.findProvidersForAutoRouting() criteria:");
        sb.append("\nbuyerId                : ").append(criteria.getBuyerId());
        sb.append("\nserviceOrderId         : ").append(criteria.getServiceOrderId());
        sb.append("\nbuyerTemplateName      : ").append(criteria.getBuyerTemplateName());
        sb.append("\nserviceLocationZip     : ").append(criteria.getServiceLocationZip());
        sb.append("\nprimarySkillCategoryId : ").append(criteria.getPrimarySkillCategoryId());
        sb.append("\ntierId                 : ").append(criteria.getTierId());
        sb.append("\nconditionalAutoRoute   : ").append(criteria.isConditionalAutoRoute());
        sb.append("\nskillNodeIds           : ");
        if (criteria.getSkillNodeIds() != null) {
            for (Integer skillNodeId : criteria.getSkillNodeIds()) {
                sb.append(skillNodeId).append(", ");
            }
        }
        sb.append("\nskillServiceTypes      : ");
        if (criteria.getSkillServiceTypes() != null) {
            for (Integer skillSvcType : criteria.getSkillServiceTypes()) {
                sb.append(skillSvcType).append(", ");
            }
        }
            logger.debug(sb.toString());
    }
    }
    
    @Transactional
    private List<ProviderIdVO> retrieveServiceProviderListForProviderResources(List<ProviderResultVO> providerRsrcIdList) {
    	logger.info("Fetching the provider detail belonging to a firm>>provListsize>>"+providerRsrcIdList.size());
    	long start = System.currentTimeMillis();
        List<ProviderIdVO> serviceProviderIdList = new ArrayList<ProviderIdVO>();
    	List<Integer> resourceIds = new ArrayList<Integer>();
    	HashMap<Integer, ProviderResultVO> providerMap = new HashMap<Integer, ProviderResultVO>();
    	logger.info("Inside retrieveServiceProviderListForProviderResources starting>>");
    	if(null!=providerRsrcIdList && providerRsrcIdList.size()>0){
    		for (ProviderResultVO providerRsrc : providerRsrcIdList) {
    			if(null!=providerRsrc){
    				resourceIds.add(providerRsrc.getResourceId());
            		providerMap.put(providerRsrc.getResourceId(), providerRsrc);
    			}
            }
    		long start1 = System.currentTimeMillis();
    		logger.info("Inside retrieveServiceProviderListForProviderResources abt to fetch prov details");
    		List<ProviderDetailsVO> serviceProviders = providerDao.findProviderResourcesDetailsByIds(resourceIds);
    		long end1 = System.currentTimeMillis();
    		logger.info("Total time taken to fetch prov details>>"+(end1-start1)+">list size>>"+serviceProviders.size());
           /* for (ServiceProvider providerRsrc : serviceProviders) {
                ProviderIdVO providerIdVO = new ProviderIdVO();
                providerIdVO.setResourceId(providerRsrc.getId());
                providerIdVO.setVendorId(providerRsrc.getProviderFirm().getId());
                providerIdVO.setFirmName(providerRsrc.getProviderFirm().getBusinessName());
                providerIdVO.setProviderFirstName(providerRsrc.getContact().getFirstName());
                providerIdVO.setProviderLastName(providerRsrc.getContact().getLastName());
                providerIdVO.setRoutingTimeFirmPerfScore(providerMap.get(providerRsrc.getId()).getFirmPerformanceScore());
                providerIdVO.setRoutingTimePerfScore(providerMap.get(providerRsrc.getId()).getPerformanceScore());
                serviceProviderIdList.add(providerIdVO); 
            }*/
            for (ProviderDetailsVO providerRsrc : serviceProviders) {
                ProviderIdVO providerIdVO = new ProviderIdVO();
                providerIdVO.setResourceId(providerRsrc.getResourceId());
                providerIdVO.setVendorId(providerRsrc.getVendorId());
                providerIdVO.setFirmName(providerRsrc.getBusinessName());
                providerIdVO.setProviderFirstName(providerRsrc.getFirstName());
                providerIdVO.setProviderLastName(providerRsrc.getLastName());
                providerIdVO.setRoutingTimeFirmPerfScore(providerMap.get(providerRsrc.getResourceId()).getFirmPerformanceScore());
                providerIdVO.setRoutingTimePerfScore(providerMap.get(providerRsrc.getResourceId()).getPerformanceScore());
                serviceProviderIdList.add(providerIdVO); 
            }
            
            
    	}
    	
    	long end = System.currentTimeMillis();
        logger.info("total size of service provider list before returning list time taken: "+(end-start));
        return serviceProviderIdList;
    }
    
    @Transactional
    private List<ProviderIdVO> retrieveServiceProviderListForProviderResourcesForCAR(List<Integer> providerRsrcIdList) {
    	logger.info("Fetching the provider detail belonging to a firm");
        List<ProviderIdVO> serviceProviderIdList = new ArrayList<ProviderIdVO>();
        for (Integer providerRsrcId : providerRsrcIdList) {
            ServiceProvider providerResource = providerDao.findProviderResourceById(providerRsrcId);
            ProviderIdVO providerIdVO = new ProviderIdVO();
            providerIdVO.setResourceId(providerResource.getId());
            logger.info("Provider resource id"+providerResource.getId());
            providerIdVO.setVendorId(providerResource.getProviderFirm().getId());
            logger.info("Using the newly added variable to store the firm name for so logging cmd with firm name"+providerResource.getProviderFirm().getId());
            providerIdVO.setFirmName(providerResource.getProviderFirm().getBusinessName());
            logger.info("Provide vendor id"+providerResource.getProviderFirm().getId());
            providerIdVO.setProviderFirstName(providerResource.getContact().getFirstName());
            providerIdVO.setProviderLastName(providerResource.getContact().getLastName());
            serviceProviderIdList.add(providerIdVO); 
        }
        logger.info("total size of service provider list before returning list"+serviceProviderIdList.size());
        return serviceProviderIdList;
    }
    
    public SPNHeader fetchSpnDetails(Integer spnId){
    	logger.info("Fetching spn details in mktplatform");
    	SPNetHeaderVO vo = autoRoutingProviderSearchBO.fetchSpnDetails(spnId);
    	if(null!=vo){
    	logger.info("SPN NAME: "+vo.getSpnName());
    	logger.info("Critera Level: "+vo.getPerfCriteriaLevel());
    	}
    	SPNHeader hdr =  new SPNHeader();
    	hdr.setSpnName(vo.getSpnName());
    	hdr.setCriteriaLevel(vo.getPerfCriteriaLevel());
    	return hdr;
    }
    
    public List<Integer> findRanks(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers){
    	List<Integer> ranks = new ArrayList<Integer>();
    	ranks = autoRoutingProviderSearchBO.findRanks(soId, noOfProvInCurentTier, noOfProvInPreviousTiers);
    	logger.info("Inside find ranks in mktplatform"+ranks.size());
    	return ranks;
    }
    
    public List<ProviderIdVO> findProvidersForTierRouting(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers,List<Integer> perfScores){
    	List<ProviderIdVO> providerList = new ArrayList<ProviderIdVO>();
    	providerList = autoRoutingProviderSearchBO.findProvidersForTierRouting(soId, noOfProvInCurentTier, noOfProvInPreviousTiers, perfScores);
    	logger.info("Inside findProvidersForTierRouting in mktplatform"+providerList.size());
    	return providerList;
    }
    ////////////////////////////////////////////////////////////////////////////
    // SETTERS FOR SPRING INJECTION
    ////////////////////////////////////////////////////////////////////////////
    public void setProviderDao(IProviderDao providerDao) {
        this.providerDao = providerDao;
    }

    public void setSkillTreeDao(ISkillTreeDao skillTreeDao){
        this.skillTreeDao = skillTreeDao;
    }

    public void setAutoRoutingProviderSearchBO(IAutoRoutingProviderSearchBO autoRoutingProviderSearchBO) {
        this.autoRoutingProviderSearchBO = autoRoutingProviderSearchBO;
    }
}
