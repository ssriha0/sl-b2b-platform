package com.servicelive.routingrulesengine.dao;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.RoutingRuleVendor;

/**
 * 
 * @author svanloon
 *
 */
/**
 * @author madhup_chand
 *
 */
public interface RoutingRuleVendorDao {

	/**
	 * 
	 * @param routingRuleVendor
	 * @return RoutingRuleVendor
	 * @throws Exception
	 */
	public RoutingRuleVendor save(RoutingRuleVendor routingRuleVendor) throws Exception;
	
	/**
	 * 
	 * @param vendor
	 * @throws Exception
	 */
	public void delete(RoutingRuleVendor vendor) throws Exception;
	//SL 15642 Method to fetch auto accept status of all vendors associated with a particular rule
   public Map<Integer,String> getProviderAutoAcceptanceStatus(List<Integer> joinedVendorIds,Integer ruleId) throws Exception;
   //SL 15642 Method to update auto accept status of all vendors associated with a particular rule who have auto acceptance status ON
   // and they are not the new vendors added to this rule
   public void updateAutoStatusForRule(Integer vendorId,Integer ruleId) throws Exception;
   
   //SL 15642 Method to delete all entry from auto accept history table
   public void deleteHistoryFromAutoAccpetHistory(Integer ruleId,Integer vendorId) throws Exception;
   
   //SL 15642 Method to insert entry into alert task for all providers who have auto accept status ON
   public void saveAutoAcceptChangeProviderMailInfo(AlertTask alertTask) throws Exception;
   
 //SL 15642 Method to get provider admin email id
   public String getProviderAdminEmailId(Integer vendorId) throws Exception;
   
   //SL 15642 Method to get provider detail to send email on auto acceptance status change
   public ServiceProvider getProviderDetail(Integer vendorId) throws Exception;
   
   //SL 15642 Method to update auto accept history on status change on uploading a rule
   public void updateAutoAcceptHistoryOnStatusChange(Integer ruleId,String onStatus,Integer vendorId ,String modifiedBy,Integer buyerId,String action,String adoptedBy);
}
