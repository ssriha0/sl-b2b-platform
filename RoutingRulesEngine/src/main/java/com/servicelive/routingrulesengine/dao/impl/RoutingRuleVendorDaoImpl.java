package com.servicelive.routingrulesengine.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.servicelive.domain.lookup.LookupMarket;
import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.RoutingRuleVendor;
import com.servicelive.routingrulesengine.dao.RoutingRuleVendorDao;


/**
 * 
 * @author svanloon
 * 
 */
public class RoutingRuleVendorDaoImpl
	extends AbstractBaseDao
	implements RoutingRuleVendorDao
{

	/**
	 * 
	 * @param routingRuleVendor
	 * @return RoutingRuleVendor
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	public RoutingRuleVendor save(RoutingRuleVendor routingRuleVendor)
		throws Exception
	{
		this.getEntityManager().persist(routingRuleVendor);
		return routingRuleVendor;
	}


	/**
	 * 
	 * @param vendor
	 * @throws Exception
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	public void delete(RoutingRuleVendor vendor)
		throws Exception
	{
		super.delete(vendor);		
	}
	
	//SL 15642 Method to get auto acceptance status of all providers belonging to this rule and who are not new to this 
	//particular rule after edit of rule
	public Map<Integer,String> getProviderAutoAcceptanceStatus(List<Integer> joinedVendorIds,Integer ruleId) throws Exception
	{
		Map<Integer,String> listAutoAcceptVendor = new TreeMap<Integer,String>();
		String sql = "select rrv.vendor.id,rrv.autoAcceptStatus from RoutingRuleVendor rrv where rrv.vendor.id IN (:joinedVendorId) and rrv.routingRuleHdr.routingRuleHdrId=:ruleId";			
		Query query = this.getEntityManager().createQuery(sql);
		query.setParameter("joinedVendorId", joinedVendorIds);
		query.setParameter("ruleId", ruleId);
		try{			
			@SuppressWarnings("unchecked")
			List<Object[]>  autoAcceptList = query.getResultList();
			for(Object[] autoAcceptStatusList: autoAcceptList){
				Integer vendorId=(Integer)autoAcceptStatusList[0];
				String autoAcceptStatus = (String)autoAcceptStatusList[1];
				listAutoAcceptVendor.put(vendorId, autoAcceptStatus);
			}			
		 }catch(Exception e) {
			 logger.info("Exception in getMaprkets:"+e.getMessage());
			 e.printStackTrace();
			 return null;
		 }
		return listAutoAcceptVendor;
		
	}
	
	//SL 15642 Method to update auto acceptance status of providers as PENDING if they are not new to this rule and 
	//they have auto acceptance status as ON
	@Transactional(propagation = Propagation.REQUIRED)
	 public void updateAutoStatusForRule(Integer vendorId,Integer ruleId)  throws Exception
	 {
		String hql = null;
		Query query = null;
		//Updating routing rule vendor for particular vendor belonging to a particular rule
					hql = "UPDATE  routing_rule_vendor SET  auto_accept_status=:autoAcceptStatusChange, opportunity_email_ind=:indicator WHERE vendor_id = :vendorId and routing_rule_hdr_id=:ruleId";
							 query = getEntityManager().createNativeQuery(hql);
							 query.setParameter("ruleId", ruleId);
							 query.setParameter("vendorId", vendorId);
							 query.setParameter("autoAcceptStatusChange", "PENDING");
							 //SL-20436: Resetting the opportunity email indicator while editing the rule
							 query.setParameter("indicator", '1');
							 query.executeUpdate();
		
	 }
	
	//SL 15642 Method to delete all entry from auto accept history table for the vendor who no longer belong to this rule
	@Transactional(propagation = Propagation.REQUIRED)
	   public void deleteHistoryFromAutoAccpetHistory(Integer ruleId,Integer vendorId) throws Exception
	   {
		String hql = null;
		Query query = null;
		//Updating routing rule vendor for particular vendor belonging to a particular rule
					hql = "DELETE from auto_accept_history  WHERE vendor_id = :vendorId and routing_rule_hdr_id=:ruleId";
							 query = getEntityManager().createNativeQuery(hql);
							 query.setParameter("ruleId", ruleId);
							 query.setParameter("vendorId", vendorId);
							 query.executeUpdate();
		
	   }
	
	//SL 15642 Method to get provider admin email id
	   public String getProviderAdminEmailId(Integer vendorId) 
	   {
		   String hql = null;
			Query query = null;
			try{
						hql = "SELECT vcon.email FROM vendor_resource r JOIN vendor_hdr v ON r.vendor_id=v.vendor_id JOIN contact vcon ON vcon.contact_id=r.contact_id WHERE  r.vendor_id=:vendorId AND r.primary_ind=1";
								query = getEntityManager().createNativeQuery(hql);
								query.setParameter("vendorId", vendorId);
								String providerEmailId =(String)query.getSingleResult();
									return providerEmailId;
								
								}
									catch (NoResultException e) {
										logger.info(e.getMessage());
										return null;
									}
	   }
	   
	   //SL 15642 Method to insert entry into alert task for all providers who have auto accept status ON
	   @Transactional(propagation = Propagation.REQUIRED)
	   public void saveAutoAcceptChangeProviderMailInfo(AlertTask alertTask)
	   {
		   String hql = null;
			Query query = null;
			//Updating routing rule vendor for particular vendor belonging to a particular rule
						hql = "INSERT INTO alert_task(alerted_timestamp,"+
								"completion_indicator,alert_type_id,template_id,priority,alert_from,alert_to,"+
								"alert_cc,alert_bcc,created_date,modified_date,modified_by,template_input_value) VALUES (:alertedTimestamp,"+
								":completionIndicator,:alertTypeId,:templateId,:priority,:alertFrom,:alertTo,:alertCc,"+
								":alertBcc,:createdDate,:modifiedDate,:modifiedBy,:templateInputValue)";
								 query = getEntityManager().createNativeQuery(hql);
								 query.setParameter("alertedTimestamp", alertTask.getAlertedTimestamp());
								 query.setParameter("createdDate", alertTask.getCreatedDate());
								 query.setParameter("modifiedDate", alertTask.getCreatedDate());
								 query.setParameter("modifiedBy", alertTask.getModifiedBy());
								 query.setParameter("alertTypeId", alertTask.getAlertTypeId());
								 query.setParameter("templateId", alertTask.getTemplateId());
								 query.setParameter("alertTo", alertTask.getAlertTo());
								 query.setParameter("alertFrom", alertTask.getAlertFrom());
								 query.setParameter("alertCc", alertTask.getAlertCc());
								 query.setParameter("alertBcc", alertTask.getAlertBcc());
								 query.setParameter("priority", alertTask.getPriority());
								 query.setParameter("completionIndicator", alertTask.getCompletionIndicator());
								 query.setParameter("templateInputValue", alertTask.getTemplateInputValue());
								 query.executeUpdate();
	   }
	   
	   //SL 15642 Method to get provider detail to send email on auto acceptance status change
	   public ServiceProvider getProviderDetail(Integer vendorId) 
	   {
		   Query query=null;
		   try
		   {
		   String hql = "SELECT vh.* FROM vendor_resource vh WHERE vendor_id ='"+vendorId+"' AND primary_ind=1";
		   query = getEntityManager().createNativeQuery(hql,ServiceProvider.class);	
			}
		   catch(Exception ee) {
			   logger.info("Exception while fetching first and last name of provider");
		    	return null;
		    }
			try{
				ServiceProvider providerFirm = (ServiceProvider) query.getSingleResult();
	            return providerFirm;
			} catch (NoResultException e) {
				logger.info(e.getMessage());
	            return null;	
		    }
	   }
	   
	   //SL 15642 Method to update auto accept history on status change on uploading a rule
	   @Transactional(propagation = Propagation.REQUIRED)
	   public void updateAutoAcceptHistoryOnStatusChange(Integer ruleId,String onStatus,Integer vendorId ,String modifiedBy,Integer roleId,String action,String adoptedBy)
	   {
		   String hql = null;
			Query query = null;
			//Updating auto accept history on uploading a rule if auto accept history ON
			hql = "INSERT INTO auto_accept_history(routing_rule_hdr_id,auto_accept_status,action,role_id,vendor_id,modified_by,created_date,adopted_by)"+
					"VALUES(:ruleId,:onStatus,:action,:roleId,:vendorId,:modifiedBy,NOW(),:adoptedBy)";
			query = getEntityManager().createNativeQuery(hql);
			 query.setParameter("ruleId", ruleId);
			 query.setParameter("onStatus",onStatus);
			 query.setParameter("vendorId", vendorId);
			 query.setParameter("modifiedBy", modifiedBy);
			 query.setParameter("action",action);
			 query.setParameter("roleId",roleId);
			 query.setParameter("adoptedBy",adoptedBy);
			 query.executeUpdate();
			
	   }
}
