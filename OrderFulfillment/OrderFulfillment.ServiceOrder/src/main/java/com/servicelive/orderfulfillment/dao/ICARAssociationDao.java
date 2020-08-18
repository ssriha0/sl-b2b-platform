package com.servicelive.orderfulfillment.dao;

import com.servicelive.domain.so.SORoutingRuleAssoc;
import com.servicelive.marketplatform.common.vo.RoutingRuleHdrVO;

/**
 * User: Yunus Burhani
 * Date: Jul 21, 2010
 * Time: 3:41:41 PM
 */
public interface ICARAssociationDao {
    public SORoutingRuleAssoc getCARAssociation(String soId);
    public void save(SORoutingRuleAssoc soRoutingRuleAssoc);
    public void delete(SORoutingRuleAssoc soRoutingRuleAssoc);
    public SORoutingRuleAssoc getRuleId(String soId);
    //SL 15642
    public RoutingRuleHdrVO getProvidersListOfRule(Integer ruleId);
}
