package com.servicelive.marketplatform.serviceinterface;

import com.newco.marketplace.dto.vo.providerSearch.ProviderResultVO;
import com.newco.marketplace.dto.vo.spn.SPNetHeaderVO;
import com.servicelive.marketplatform.common.vo.ProviderIdVO;
import com.servicelive.marketplatform.common.vo.ProviderListCriteriaForAutoRoutingVO;

import java.util.List;

public interface IAutoRoutingProviderSearchBO {
    List<ProviderResultVO> findProvidersForAutoRouting(ProviderListCriteriaForAutoRoutingVO providerListCriteriaVO);
    List<Integer> findProvidersForConditionalAutoRouting(ProviderListCriteriaForAutoRoutingVO providerListCriteriaVO);
    List<Integer> getProviderForAutoAcceptance(Integer eligibleFirmId,Integer spnId);
    SPNetHeaderVO fetchSpnDetails(Integer spnId);
    public List<Integer> findRanks(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers);
    public List<ProviderIdVO> findProvidersForTierRouting(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers,List<Integer> perfScores);
}
