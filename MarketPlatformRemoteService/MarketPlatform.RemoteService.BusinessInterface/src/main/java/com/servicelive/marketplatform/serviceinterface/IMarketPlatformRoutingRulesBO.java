package com.servicelive.marketplatform.serviceinterface;

import com.servicelive.marketplatform.common.vo.ItemsForCondAutoRouteRepriceVO;
import com.servicelive.marketplatform.common.vo.CondRoutingRuleVO;
import com.servicelive.marketplatform.common.vo.RoutingRuleHdrVO;
import com.servicelive.marketplatform.common.vo.TierReleaseInfoVO;

import java.util.List;

public interface IMarketPlatformRoutingRulesBO {
    CondRoutingRuleVO getConditionalRoutingRuleId(CondRoutingRuleVO condRoutingRuleVO);
    ItemsForCondAutoRouteRepriceVO repriceItems(ItemsForCondAutoRouteRepriceVO itemsForReprice);
    TierReleaseInfoVO retrieveTierReleaseInfo(Integer spnId, Integer currentTier);
    List<TierReleaseInfoVO> retrieveAllAvailableTierReleaseInfo();
     //SL 15642 Start Method to enter type of acceptance in so_workflow_controls table on associating a rule id with so
   public void setMethodOfAcceptanceOfSo(String soId,String methodOfAcceptance);
    public void setMethodOfRoutingOfSo(String soId,String methodOfRouting);
    RoutingRuleHdrVO getProvidersListOfRule(Integer ruleId);
    //SL 15642 Start Method to enter type of acceptance in so_workflow_controls table on associating a rule id with so
}
