package com.servicelive.marketplatform.serviceinterface;

import java.util.List;

import com.servicelive.domain.common.Contact;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.marketplatform.common.vo.ProviderIdVO;
import com.servicelive.marketplatform.common.vo.ProviderListCriteriaForAutoRoutingVO;
import com.servicelive.marketplatform.provider.domain.SkillNode;


public interface IMarketPlatformProviderBO {
    public Contact retrieveProviderResourceContactInfo(Long providerRsrcId);
    public Contact retrieveProviderPrimaryResourceContactInfo(Long providerId);
    public List<Contact> retrieveProviderResourceContactInfoList(List<Long> providerRsrcIdList);
    public List<SkillNode> retrieveAllAvailableProviderSkills();
    public SkillNode getSkillNodeById(Long skillNodeId);
    public List<ProviderIdVO> findProvidersForAutoRouting(ProviderListCriteriaForAutoRoutingVO providerListCriteria);
    public List<Contact>  retrieveProviderAdminContactInfoList(List<Long> providerFirmIdList);
    public List<ProviderIdVO> getProviderForAutoAcceptance(Integer eligibleFirmId,Integer spnId);
    public SPNHeader fetchSpnDetails(Integer spnId);
    public List<Integer> findRanks(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers);
    public List<ProviderIdVO> findProvidersForTierRouting(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers,List<Integer> perfScores);
}
