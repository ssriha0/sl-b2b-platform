package com.servicelive.routingrulesengine.dao.impl;

import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.servicelive.domain.routingrules.RoutingRuleBuyerAssoc;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.RoutingRuleHdrHist;
import com.servicelive.domain.routingrules.RoutingRuleSwitches;
import com.servicelive.routingrulesengine.dao.RoutingRuleBuyerAssocDao;
import com.servicelive.routingrulesengine.services.RoutingRulesPaginationService;
import com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesSearchVO;

public class RoutingRuleBuyerAssocDaoImpl extends AbstractBaseDao implements RoutingRuleBuyerAssocDao {
	private static final Logger logger = Logger.getLogger(RoutingRuleBuyerAssocDaoImpl.class);
	@Transactional(propagation = Propagation.SUPPORTS)
	public RoutingRuleBuyerAssoc findByBuyerId(Integer buyerId) {
		String hql = "FROM RoutingRuleBuyerAssoc a WHERE a.buyer.buyerId = :buyerId";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		
		try {
			return (RoutingRuleBuyerAssoc) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public Integer findRoutingRuleBuyerAssocId(Integer buyerId){
		String hql = "SELECT a.Id FROM RoutingRuleBuyerAssoc a WHERE a.buyer.buyerId = :buyerId";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		
		try {
			return  (Integer) query.getSingleResult();	
		}
		catch (NoResultException e) {
			return null;
		}
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public String findMarketId(String zip){
		String hql = "SELECT market_id FROM lu_zip_market WHERE zip='"+zip+"'";
		Query query = getEntityManager().createNativeQuery(hql);

		try {
			Integer retValue =(Integer)query.getSingleResult();
			return  retValue.toString();	
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
			return null;
		}
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public String findIfUpdate(String soId){
		String hql = "SELECT routing_rule_hdr_id FROM so_routing_rule_assoc WHERE so_id='"+soId+"'";
		Query query = getEntityManager().createNativeQuery(hql);

		try {
			Integer retValue =(Integer)query.getSingleResult();
			return  retValue.toString();	
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<RoutingRuleSwitches> findSortOrder(){
		String hql = "SELECT rrs.* FROM routing_rule_switches rrs";
		Query query = getEntityManager().createNativeQuery(hql,RoutingRuleSwitches.class);

		try {
			List<RoutingRuleSwitches> resultList=query.getResultList();
			return resultList;
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
			return null;
		}
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public Integer findWfStateIdforSO(String soId){
		String hql = "SELECT hdr.wf_state_id FROM so_hdr hdr WHERE hdr.so_id='"+soId+"'";
		Query query = getEntityManager().createNativeQuery(hql);

		try {
			Integer wfStateId=(Integer) query.getSingleResult();
			return wfStateId;
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<String> findMandatoryCustomRefs(Integer buyerId){
		String hql = "SELECT rrbc.customref_name FROM routing_rule_buyer_customrefs rrbc WHERE rrbc.buyer_id='"+buyerId+"'";
		Query query = getEntityManager().createNativeQuery(hql);

		try {
			List<String> mandatoyCustomRefs=query.getResultList();;
			return mandatoyCustomRefs;
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
			return null;
		}
	}
	@Transactional(propagation=Propagation.SUPPORTS)
	public RoutingRuleHdr findRuleHrIdForSO(String soId){
		Query query = null;
		
	   try {
		
		String hql = "SELECT rh.* FROM routing_rule_hdr rh, so_routing_rule_assoc assoc WHERE assoc.routing_rule_hdr_id = rh.routing_rule_hdr_id AND assoc.so_id ='"+soId+"'";
		
		query = getEntityManager().createNativeQuery(hql,RoutingRuleHdr.class);	
			
	    }catch(Exception ee) {
	    	return null;
	    }
		try{
			@SuppressWarnings("unchecked")
			RoutingRuleHdr routingRuleHdr = (RoutingRuleHdr) query.getSingleResult();
            return routingRuleHdr;
		} catch (NoResultException e) {
			logger.info(e.getMessage());
            return null;	
	    }
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<RoutingRuleHdr> findRoutingRuleHdrId(Integer routingRulebuyerAssocId,String jobcode,String zip,String marketId,String sortOrder){
		Query query = null; 
		
	   try {
		
		String hql = "SELECT rh.* FROM routing_rule_hdr rh,routing_rule_criteria  rrc, "+
		 " routing_rule_price rrp "+ 		
         " WHERE rh.routing_rule_buyer_assoc_id="+routingRulebuyerAssocId+ 
         " AND rh.rule_status='ACTIVE' "+
         " AND (rrc.routing_rule_hdr_id = rh.routing_rule_hdr_id)"+
         " AND (rrp.routing_rule_hdr_id = rh.routing_rule_hdr_id)"+
         " AND ((rrc.criteria_name = 'ZIP' AND rrc.criteria_value='"+zip+"')||(rrc.criteria_name = 'MARKET' AND rrc.criteria_value='"+marketId+"'))"+ 
         " AND (rrp.jobcode='"+jobcode+"')"+
         " GROUP BY rh.routing_rule_hdr_id"+
         " ORDER BY rh.created_date "+sortOrder+"";
		
		query = getEntityManager().createNativeQuery(hql,RoutingRuleHdr.class);	
			
	    }catch(Exception ee) {
	    	return null;
	    }
		try{
			@SuppressWarnings("unchecked")
			List<RoutingRuleHdr> routingRuleHdrList = query.getResultList();
            return routingRuleHdrList;
		} catch (NoResultException e) {
			logger.info(e.getMessage());
            return null;	
	    }
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public List<RoutingRuleHdr> findRoutingRuleHdrIdList(Integer routingRuleBuyerAssocId,String jobcode,String zip,String marketId,List<ServiceOrderCustomRefVO> customRefList,String sortOrder){
		
		Query query = null;

		   try {
			   
				StringBuffer customRefs = new StringBuffer(" OR (");
				if(!customRefList.isEmpty()){
					for(ServiceOrderCustomRefVO serviceOrderCustomRefVO:customRefList) {
						customRefs.append("(rrc.criteria_name ='"+serviceOrderCustomRefVO.getRefType()+"' AND rrc.criteria_value='"+serviceOrderCustomRefVO.getRefValue()+"') OR ");
					}
					customRefs.replace(customRefs.length()-3,customRefs.length(),")");
				}else{
					customRefs.append("0)");
				}
			   
				String hql = "SELECT rh.* FROM routing_rule_hdr rh"+
				" LEFT OUTER JOIN routing_rule_price rrp ON (rrp.routing_rule_hdr_id = rh.routing_rule_hdr_id)"+
				" WHERE rh.routing_rule_buyer_assoc_id="+routingRuleBuyerAssocId+ 
		        " AND rh.rule_status='ACTIVE' "+
		        " AND (rrp.jobcode = '"+jobcode+"')"+
		        " GROUP BY rh.routing_rule_hdr_id"+
		        " UNION ALL"+
		        " SELECT rh.* FROM routing_rule_hdr rh"+
		        " LEFT OUTER JOIN routing_rule_criteria  rrc ON (rrc.routing_rule_hdr_id = rh.routing_rule_hdr_id)"+
		        " WHERE rh.routing_rule_buyer_assoc_id="+routingRuleBuyerAssocId+ 
		        " AND rh.rule_status='ACTIVE' "+
		        " AND (((rrc.criteria_name = 'ZIP' AND rrc.criteria_value='"+zip+"')||(rrc.criteria_name = 'MARKET' AND rrc.criteria_value='"+marketId+"'))"+ 		         
				" "+customRefs.toString()+")"+
				" GROUP BY rh.routing_rule_hdr_id"+
		        " ORDER BY created_date "+sortOrder+"";
								
				logger.info(hql);
				query = getEntityManager().createNativeQuery(hql,RoutingRuleHdr.class);	
					
			    }catch(Exception ee) {
			    	return null;
			    }
				try{
					@SuppressWarnings("unchecked")
					List<RoutingRuleHdr> routingRuleHdrList = query.getResultList();
		            return routingRuleHdrList;
				} catch (NoResultException e) {
					logger.info(e.getMessage());
		            return null;	
			    }
		
	}
	
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<RoutingRuleHdr> findRoutingRuleHdrIdListRefactored(Integer routingRuleBuyerAssocId,String jobcode,String zip,String marketId,
			List<ServiceOrderCustomRefVO> customRefList,String sortOrder){
		
		Query query = null;

		   try {
			   
				StringBuffer customRefs = new StringBuffer();
				if(!customRefList.isEmpty()){
					for(ServiceOrderCustomRefVO serviceOrderCustomRefVO:customRefList) {
						customRefs.append("(rrc.criteria_name ='"+serviceOrderCustomRefVO.getRefType()+"' AND rrc.criteria_value='"+serviceOrderCustomRefVO.getRefValue()+"') OR ");
					}
					customRefs.replace(customRefs.length()-3,customRefs.length(),")");
				}else{
					customRefs.append("0)");
				}
			   
				String hql = "SELECT rh.* FROM routing_rule_hdr rh"+
				" LEFT OUTER JOIN routing_rule_price rrp ON (rrp.routing_rule_hdr_id = rh.routing_rule_hdr_id)"+
				" LEFT OUTER JOIN routing_rule_criteria rrc ON (rrc.routing_rule_hdr_id = rh.routing_rule_hdr_id AND rrc.criteria_name IN ('ZIP','MARKET'))"+
				" WHERE rh.routing_rule_buyer_assoc_id="+routingRuleBuyerAssocId+ 
		        " AND rh.rule_status='ACTIVE' "+
		        " AND (rrp.jobcode = '"+jobcode+"')"+
		        " AND rrc.criteria_value IS NULL"+
		        " GROUP BY rh.routing_rule_hdr_id"+
		        " UNION ALL"+
		        " SELECT rh.* FROM routing_rule_hdr rh"+
		        " LEFT OUTER JOIN routing_rule_criteria  rrc ON (rrc.routing_rule_hdr_id = rh.routing_rule_hdr_id)"+
		        " WHERE rh.routing_rule_buyer_assoc_id="+routingRuleBuyerAssocId+ 
		        " AND rh.rule_status='ACTIVE' "+
		        " AND ("+ 		         
				" "+customRefs.toString()+
				" GROUP BY rh.routing_rule_hdr_id"+
				" UNION ALL"+
				" SELECT rh.* FROM routing_rule_hdr rh"+
		        " LEFT OUTER JOIN routing_rule_criteria  rrc ON (rrc.routing_rule_hdr_id = rh.routing_rule_hdr_id)"+
		        " LEFT OUTER JOIN routing_rule_price rrp ON (rrp.routing_rule_hdr_id = rh.routing_rule_hdr_id)"+
		        " WHERE rh.routing_rule_buyer_assoc_id="+routingRuleBuyerAssocId+ 
		        " AND rh.rule_status='ACTIVE' "+
		        " AND ((rrc.criteria_name = 'ZIP' AND rrc.criteria_value='"+zip+"')||(rrc.criteria_name = 'MARKET' AND rrc.criteria_value='"+marketId+"'))"+
		        " AND rrp.jobcode IS NULL"+
		        " GROUP BY rh.routing_rule_hdr_id"+
		        " ORDER BY created_date "+sortOrder+"";
								
				logger.info(hql);
				query = getEntityManager().createNativeQuery(hql,RoutingRuleHdr.class);	
					
			    }catch(Exception ee) {
			    	return null;
			    }
				try{
					@SuppressWarnings("unchecked")
					List<RoutingRuleHdr> routingRuleHdrList = query.getResultList();
		            return routingRuleHdrList;
				} catch (NoResultException e) {
					logger.info(e.getMessage());
		            return null;	
			    }
		
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public RoutingRuleBuyerAssoc save(RoutingRuleBuyerAssoc routingRuleBuyerAssoc) throws Exception {
		getEntityManager().persist(routingRuleBuyerAssoc);
		flush();
		return routingRuleBuyerAssoc;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)
	public List<RoutingRuleHdr> getRoutingRulesHeaderForRuleName(
			RoutingRulesSearchVO routingRulesSearchVo,
			RoutingRulesPaginationVO pagingCriteria, boolean archiveIndicator) 
			{
		// Handle Sorting
		String hql = "";
		Query query = null;
		logger.debug("Getting sort col :"+pagingCriteria.getSortCol());
		logger.debug("Getting sort order :"+pagingCriteria.getSortOrd());
		String sortColumn = "";
		switch (pagingCriteria.getSortCol()) {
		case 0:
			sortColumn = "a.modifiedDate";
			break; // modifiedDate
		case 1:
			sortColumn = "a.ruleName";
			break; // Name
		case 2:
			sortColumn = "a.contact.firstName";
			break; // Contact
		case 3:
			sortColumn = "";
			break; // Action
		case 4:
			sortColumn = "a.ruleStatus";
			break; // Status
		case 5: 
			sortColumn = "true"; 
			break; // sort alerts on top	
		}
		String sortOrder = "";
		switch (pagingCriteria.getSortOrd()) {
		case 0:
			sortOrder = " DESC";
			break;
		case 1:
			sortOrder = " ASC";
			break;
		}
		
		if(sortColumn.equalsIgnoreCase("true") && archiveIndicator){
			logger.debug("SORT ALERTS AND ARCHIVE HQL=========");
				int buyerId = routingRulesSearchVo.getBuyerId().intValue();
				String ruleName = routingRulesSearchVo.getRuleName();
				ruleName = "%" + getSQLStringAfterPercentEscaping(ruleName) + "%";
				
				hql = "SELECT rrh.* FROM  routing_rule_hdr rrh  INNER JOIN routing_rule_buyer_assoc rrba"+ 
					  " ON rrh.routing_rule_buyer_assoc_id = rrba.routing_rule_buyer_assoc_id INNER JOIN buyer br"+
					  " ON  rrba.buyer_id = br.buyer_id  AND br.buyer_id = "+buyerId+" WHERE rrh.rule_name LIKE '"+ruleName+"'" +
					  " GROUP BY rrh.routing_rule_hdr_id ORDER BY"+
					  " (IF(rrh.rule_status != 'ARCHIVED' AND (rrh.routing_rule_hdr_id IN(SELECT rra.routing_rule_hdr_id FROM routing_rule_alert rra WHERE rra.alert_status = 'ACTIVE')"+
					  " || rrh.routing_rule_hdr_id IN(SELECT rre.routing_rule_hdr_id FROM routing_rule_error rre)), 1,0)) DESC,rrh.modified_date DESC";
				
				if(! hql.equalsIgnoreCase(null) && hql != "null" && hql != ""){	
				    query = getEntityManager().createNativeQuery(hql, RoutingRuleHdr.class);
			}			
		}
		else if(sortColumn.equalsIgnoreCase("true"))
		{
			logger.debug("SORT ALERTS ONLY HQL=========");			
				int buyerId = routingRulesSearchVo.getBuyerId().intValue();
				String ruleName = routingRulesSearchVo.getRuleName();
				ruleName = "%" + getSQLStringAfterPercentEscaping(ruleName) + "%";
				
				hql = "SELECT rrh.* FROM  routing_rule_hdr rrh  INNER JOIN routing_rule_buyer_assoc rrba"+ 
					  " ON rrh.routing_rule_buyer_assoc_id = rrba.routing_rule_buyer_assoc_id INNER JOIN buyer br"+
					  " ON  rrba.buyer_id = br.buyer_id  AND br.buyer_id = "+buyerId+" WHERE rrh.rule_name LIKE '"+ruleName+"'" +
					  " AND rrh.rule_status != 'ARCHIVED'" +
					  " GROUP BY rrh.routing_rule_hdr_id ORDER BY"+
					  " (IF(rrh.rule_status != 'ARCHIVED' AND (rrh.routing_rule_hdr_id IN(SELECT rra.routing_rule_hdr_id FROM routing_rule_alert rra WHERE rra.alert_status = 'ACTIVE')"+
					  " || rrh.routing_rule_hdr_id IN(SELECT rre.routing_rule_hdr_id FROM routing_rule_error rre)), 1,0)) DESC,rrh.modified_date DESC";
				
				if(! hql.equalsIgnoreCase(null) && hql != "null" && hql != ""){	
					    query = getEntityManager().createNativeQuery(hql, RoutingRuleHdr.class);
					}							
		}

		else if (archiveIndicator) {
			hql = "FROM RoutingRuleHdr a WHERE a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId AND " +
					"a.ruleName LIKE :rule";

		}

		else {
			hql = "FROM RoutingRuleHdr a WHERE a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId AND " +
					"a.ruleName LIKE :rule "
					+ "and a.ruleStatus != 'ARCHIVED'";
		}

		if (!sortColumn.equalsIgnoreCase("true")) 
		{
				if (StringUtils.isNotBlank(sortColumn)
						&& StringUtils.isNotBlank(sortOrder)) {
					hql += " ORDER BY " + sortColumn + sortOrder;
				}
				if (!hql.equalsIgnoreCase(null) && hql != "null" && hql != "") {
					query = getEntityManager().createQuery(hql);
				}
			
			if(!query.equals(null))
			{
				query.setParameter("buyerId", routingRulesSearchVo.getBuyerId());
				String ruleName = routingRulesSearchVo.getRuleName();
				ruleName = "%" + getSQLStringAfterPercentEscaping(ruleName) + "%";
				query.setParameter("rule", ruleName);
			}
		}
		Integer pageRequested = pagingCriteria.getPageRequested();
		Integer pageIndex = (pageRequested - 1)
				* RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE;
		query.setFirstResult(pageIndex);
		query
				.setMaxResults(RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE);

		try {
			@SuppressWarnings("unchecked")
			List<RoutingRuleHdr> routingRuleHdrList = query.getResultList();
			return routingRuleHdrList;
		} catch (NoResultException e) {
			return null;
		}
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public List<RoutingRuleHdr> getRoutingRulesHeaderForFirmName(
			RoutingRulesSearchVO routingRulesSearchVo,
			RoutingRulesPaginationVO pagingCriteria, boolean archiveIndicator) {
		logger.debug("====getRoutingRulesHeaderForFirmName====START");
		logger.debug("====getRoutingRulesHeaderForFirmName sortcol===="+pagingCriteria.getSortCol());
		logger.debug("====getRoutingRulesHeaderForFirmName sortOrder===="+pagingCriteria.getSortOrd());	
		String hql = "";
		Query query = null;		
		
		// Handle Sorting
		String sortColumn = "";
		switch (pagingCriteria.getSortCol()) {
		case 0:
			sortColumn = "a.routingRuleHdr.modifiedDate";
			break; // modifiedDate
		case 1:
			sortColumn = "a.routingRuleHdr.ruleName";
			break; // Name
		case 2:
			sortColumn = "a.routingRuleHdr.contact.firstName";
			break; // Contact
		case 3:
			sortColumn = "";
			break; // Action
		case 4:
			sortColumn = "a.routingRuleHdr.ruleStatus";
			break; // Status
		case 5: 
			sortColumn = "true"; 
			break; // Sort Alerts to Top						
		}
		String sortOrder = "";
		switch (pagingCriteria.getSortOrd()) {
		case 0:
			sortOrder = " DESC";
			break;
		case 1:
			sortOrder = " ASC";
			break;
		}


		
		if(sortColumn.equalsIgnoreCase("true") && archiveIndicator){
			logger.debug("ForFirmName-SORT ALERTS AND ARCHIVE HQL=========");
			int buyerId = routingRulesSearchVo.getBuyerId().intValue();
			String businessName = routingRulesSearchVo.getProviderFirmName();
			businessName = "%" + getSQLStringAfterPercentEscaping(businessName)
					+ "%";
			
			hql = "SELECT rrh.* FROM  routing_rule_hdr rrh INNER JOIN routing_rule_vendor rrv ON rrh.routing_rule_hdr_id = rrv.routing_rule_hdr_id"+                 
				  " INNER JOIN vendor_hdr vh ON rrv.vendor_id = vh.vendor_id INNER JOIN routing_rule_buyer_assoc rrba"+ 
				  " ON rrh.routing_rule_buyer_assoc_id = rrba.routing_rule_buyer_assoc_id INNER JOIN buyer br"+
				  " ON  rrba.buyer_id = br.buyer_id INNER JOIN contact ct ON rrh.contact_id = ct.contact_id"+
				  " WHERE vh.business_name LIKE '"+businessName+"' AND br.buyer_id = "+buyerId+" GROUP BY rrh.routing_rule_hdr_id ORDER BY"+
				  " (IF(rrh.rule_status != 'ARCHIVED' AND (rrh.routing_rule_hdr_id IN(SELECT rra.routing_rule_hdr_id FROM routing_rule_alert rra WHERE rra.alert_status = 'ACTIVE')"+
				  " || rrh.routing_rule_hdr_id IN(SELECT rre.routing_rule_hdr_id FROM routing_rule_error rre)), 1,0)) DESC,rrh.modified_date DESC";
			
			if(! hql.equalsIgnoreCase(null) && hql != "null" && hql != ""){	
			    query = getEntityManager().createNativeQuery(hql, RoutingRuleHdr.class);
			}			
		}
		else if(sortColumn.equalsIgnoreCase("true"))
		{
			logger.debug("ForFirmName-SORT ALERTS ONLY HQL=========");			
			int buyerId = routingRulesSearchVo.getBuyerId().intValue();
			String businessName = routingRulesSearchVo.getProviderFirmName();
			businessName = "%" + getSQLStringAfterPercentEscaping(businessName)
					+ "%";
			
			hql = "SELECT rrh.* FROM  routing_rule_hdr rrh INNER JOIN routing_rule_vendor rrv ON rrh.routing_rule_hdr_id = rrv.routing_rule_hdr_id"+                 
				  " INNER JOIN vendor_hdr vh ON rrv.vendor_id = vh.vendor_id INNER JOIN routing_rule_buyer_assoc rrba"+ 
				  " ON rrh.routing_rule_buyer_assoc_id = rrba.routing_rule_buyer_assoc_id INNER JOIN buyer br"+
				  " ON  rrba.buyer_id = br.buyer_id INNER JOIN contact ct ON rrh.contact_id = ct.contact_id"+
				  " WHERE vh.business_name LIKE '"+businessName+"' AND br.buyer_id = "+buyerId+" AND rrh.rule_status != 'ARCHIVED'" +
				  " GROUP BY rrh.routing_rule_hdr_id ORDER BY"+
				  " (IF(rrh.rule_status != 'ARCHIVED' AND (rrh.routing_rule_hdr_id IN(SELECT rra.routing_rule_hdr_id FROM routing_rule_alert rra WHERE rra.alert_status = 'ACTIVE')"+
				  " || rrh.routing_rule_hdr_id IN(SELECT rre.routing_rule_hdr_id FROM routing_rule_error rre)), 1,0)) DESC,rrh.modified_date DESC";
			
			if(! hql.equalsIgnoreCase(null) && hql != "null" && hql != ""){	
			    query = getEntityManager().createNativeQuery(hql, RoutingRuleHdr.class);
			}							
		}		
		else if (archiveIndicator) {
			logger.debug("ForFirmName-ARCHIVED");
			hql = "SELECT DISTINCT a.routingRuleHdr FROM RoutingRuleVendor a WHERE a.vendor.businessName LIKE :businessName " +
			"AND a.routingRuleHdr.routingRuleBuyerAssoc.buyer.buyerId = :buyerId";			
		}	
		else {
			logger.debug("ForFirmName-NORMAL CASE");
			hql = "SELECT DISTINCT a.routingRuleHdr FROM RoutingRuleVendor a WHERE a.vendor.businessName LIKE :businessName " +
			"AND a.routingRuleHdr.routingRuleBuyerAssoc.buyer.buyerId = :buyerId "
			+ "and a.routingRuleHdr.ruleStatus != 'ARCHIVED'";
		}		
		
		if (!sortColumn.equalsIgnoreCase("true")) 
		{
		if (StringUtils.isNotBlank(sortColumn) && StringUtils.isNotBlank(sortOrder)) {
			hql += " ORDER BY " + sortColumn + sortOrder;
		}
		if(!hql.equalsIgnoreCase(null) && hql != "null" && hql != ""){
	        query = getEntityManager().createQuery(hql);
		}
		if(!query.equals(null)){
			query.setParameter("buyerId", routingRulesSearchVo.getBuyerId());
			String businessName = routingRulesSearchVo.getProviderFirmName();
			businessName = "%" + getSQLStringAfterPercentEscaping(businessName)
					+ "%";
			query.setParameter("businessName", businessName);
		}
		}
		
		Integer pageRequested = pagingCriteria.getPageRequested();
		Integer pageIndex = (pageRequested - 1)
				* RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE;
		query.setFirstResult(pageIndex);
		query
				.setMaxResults(RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE);

		try {
			@SuppressWarnings("unchecked")
			List<RoutingRuleHdr> routingRuleHdrList = query.getResultList();
			logger.debug("====getRoutingRulesHeaderForFirmName====EXIT");
			return routingRuleHdrList;
		} catch (NoResultException e) {
			return null;
		}		

	}
	@Transactional(propagation = Propagation.REQUIRED)
	public List<RoutingRuleHdr> getRoutingRulesHeaderForFirmId(
			RoutingRulesSearchVO routingRulesSearchVo,
			RoutingRulesPaginationVO pagingCriteria, boolean archiveIndicator) {
		String hql = "";
		Query query = null;
		logger.debug("Getting sort col :"+pagingCriteria.getSortCol());
		logger.debug("Getting sort order :"+pagingCriteria.getSortOrd());
				
		// Handle Sorting
		String sortColumn = "";
		switch (pagingCriteria.getSortCol()) {
		case 0:
			sortColumn = "a.routingRuleHdr.modifiedDate";
			break; // modifiedDate
		case 1:
			sortColumn = "a.routingRuleHdr.ruleName";
			break; // Name
		case 2:
			sortColumn = "a.routingRuleHdr.contact.firstName";
			break; // Contact
		case 3:
			sortColumn = "";
			break; // Action
		case 4:
			sortColumn = "a.routingRuleHdr.ruleStatus";
			break; // Status
		case 5: 
			sortColumn = "true"; 
			break; // Sort Alerts to Top
			
		}
		String sortOrder = "";
		switch (pagingCriteria.getSortOrd()) {
			case 0:
				sortOrder = " DESC";
				break;
			case 1:
				sortOrder = " ASC";
				break;
		}
		if(sortColumn.equalsIgnoreCase("true") && archiveIndicator){
			logger.debug("SORT ALERTS AND ARCHIVE HQL=========");
			int buyerId = routingRulesSearchVo.getBuyerId().intValue();
			int vendorId = routingRulesSearchVo.getProviderFirmId().intValue();
			
			hql = "SELECT rrh.* FROM  routing_rule_hdr rrh INNER JOIN routing_rule_vendor rrv ON rrh.routing_rule_hdr_id = rrv.routing_rule_hdr_id"+                 
				  " INNER JOIN vendor_hdr vh ON rrv.vendor_id = vh.vendor_id INNER JOIN routing_rule_buyer_assoc rrba"+ 
				  " ON rrh.routing_rule_buyer_assoc_id = rrba.routing_rule_buyer_assoc_id INNER JOIN buyer br"+
				  " ON  rrba.buyer_id = br.buyer_id INNER JOIN contact ct ON rrh.contact_id = ct.contact_id"+
				  " WHERE vh.vendor_id = "+vendorId+" AND br.buyer_id = "+buyerId+" GROUP BY rrh.routing_rule_hdr_id ORDER BY"+
				  " (IF(rrh.rule_status != 'ARCHIVED' AND (rrh.routing_rule_hdr_id IN(SELECT rra.routing_rule_hdr_id FROM routing_rule_alert rra WHERE rra.alert_status = 'ACTIVE')"+
				  " || rrh.routing_rule_hdr_id IN(SELECT rre.routing_rule_hdr_id FROM routing_rule_error rre)), 1,0)) DESC,rrh.modified_date DESC";
			
			if(! hql.equalsIgnoreCase(null) && hql != "null" && hql != ""){	
			    query = getEntityManager().createNativeQuery(hql, RoutingRuleHdr.class);
			}			
		}
		else if(sortColumn.equalsIgnoreCase("true"))
		{
			logger.debug("SORT ALERTS ONLY HQL=========");			
			int buyerId = routingRulesSearchVo.getBuyerId().intValue();
			int vendorId = routingRulesSearchVo.getProviderFirmId().intValue();
			
			hql = "SELECT rrh.* FROM  routing_rule_hdr rrh INNER JOIN routing_rule_vendor rrv ON rrh.routing_rule_hdr_id = rrv.routing_rule_hdr_id"+                 
				  " INNER JOIN vendor_hdr vh ON rrv.vendor_id = vh.vendor_id INNER JOIN routing_rule_buyer_assoc rrba"+ 
				  " ON rrh.routing_rule_buyer_assoc_id = rrba.routing_rule_buyer_assoc_id INNER JOIN buyer br"+
				  " ON  rrba.buyer_id = br.buyer_id INNER JOIN contact ct ON rrh.contact_id = ct.contact_id"+
				  " WHERE vh.vendor_id = "+vendorId+" AND br.buyer_id = "+buyerId+" AND rrh.rule_status != 'ARCHIVED'" +
				  " GROUP BY rrh.routing_rule_hdr_id ORDER BY"+
				  " (IF(rrh.rule_status != 'ARCHIVED' AND (rrh.routing_rule_hdr_id IN(SELECT rra.routing_rule_hdr_id FROM routing_rule_alert rra WHERE rra.alert_status = 'ACTIVE')"+
				  " || rrh.routing_rule_hdr_id IN(SELECT rre.routing_rule_hdr_id FROM routing_rule_error rre)), 1,0)) DESC,rrh.modified_date DESC";
			
			if(! hql.equalsIgnoreCase(null) && hql != "null" && hql != ""){	
			    query = getEntityManager().createNativeQuery(hql, RoutingRuleHdr.class);
			}							
		}		
		else if (archiveIndicator) {
			logger.debug("ARCHIVED");
			hql = "SELECT a.routingRuleHdr FROM RoutingRuleVendor a WHERE a.vendor.id = :vendorId " +
					"AND a.routingRuleHdr.routingRuleBuyerAssoc.buyer.buyerId = :buyerId "
					+ "GROUP BY a.routingRuleHdr.routingRuleHdrId";
			if (StringUtils.isNotBlank(sortColumn) && StringUtils.isNotBlank(sortOrder)) {
				hql += " ORDER BY " + sortColumn + sortOrder;
			}
			if(!hql.equalsIgnoreCase(null) && hql != "null" && hql != ""){
		        query = getEntityManager().createQuery(hql);
			}
			if(!query.equals(null)){
			    query.setParameter("buyerId", routingRulesSearchVo.getBuyerId());
			    int vendorId = routingRulesSearchVo.getProviderFirmId().intValue();
			    query.setParameter("vendorId", vendorId);
			}			
		}	
		else {
			logger.debug("NORMAL CASE");
			hql = "SELECT a.routingRuleHdr FROM RoutingRuleVendor a WHERE a.vendor.id = :vendorId " +
					"AND a.routingRuleHdr.routingRuleBuyerAssoc.buyer.buyerId = :buyerId " +
					"and a.routingRuleHdr.ruleStatus != 'ARCHIVED' "
					+ "GROUP BY a.routingRuleHdr.routingRuleHdrId ";

			if (StringUtils.isNotBlank(sortColumn) && StringUtils.isNotBlank(sortOrder)) {
				hql += " ORDER BY " + sortColumn + sortOrder;
			}
			if(!hql.equalsIgnoreCase(null) && hql != "null" && hql != ""){
		        query = getEntityManager().createQuery(hql);
			}
			if(!query.equals(null)){
			    query.setParameter("buyerId", routingRulesSearchVo.getBuyerId());
			    int vendorId = routingRulesSearchVo.getProviderFirmId().intValue();
			    query.setParameter("vendorId", vendorId);
			}
		}

		Integer pageRequested = pagingCriteria.getPageRequested();
		Integer pageIndex = (pageRequested - 1)
				* RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE;
		query.setFirstResult(pageIndex);
		query
				.setMaxResults(RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE);

		try {
			@SuppressWarnings("unchecked")
			List<RoutingRuleHdr> routingRuleHdrList = query.getResultList();
			return routingRuleHdrList;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * This method is used to check if Upload File Name is used  
	 * as the criteria for searching
	 * 
	 * @param routingRulesSearchVo
	 * @param pagingCriteria
	 * @param archiveIndicator
	 * @return routingRuleHdrList
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public List<RoutingRuleHdr> getRoutingRulesHeaderForUploadFileName(
			RoutingRulesSearchVO routingRulesSearchVo,
			RoutingRulesPaginationVO pagingCriteria, boolean archiveIndicator) {

		String hql = "";
		Query query = null;
		logger.debug("Getting sort col :" + pagingCriteria.getSortCol());
		logger.debug("Getting sort order :" + pagingCriteria.getSortOrd());

		String sortColumn = "";
		switch (pagingCriteria.getSortCol()) {
		case 0:
			sortColumn = "a.modifiedDate";
			break; // modifiedDate
		case 1:
			sortColumn = "a.ruleName";
			break; // Name
		case 2:
			sortColumn = "a.contact.firstName";
			break; // Contact
		case 3:
			sortColumn = "";
			break; // Action
		case 4:
			sortColumn = "a.ruleStatus";
			break; // Status
		case 5:
			sortColumn = "true";
			break; // sort alerts on top
		}
		String sortOrder = "";
		switch (pagingCriteria.getSortOrd()) {
			case 0:
				sortOrder = " DESC";
				break;
			case 1:
				sortOrder = " ASC";
				break;
		}
		String uploadNameforalert1 = routingRulesSearchVo.getUploadedFileName();
		String uploadNameforalert2 = routingRulesSearchVo.getUploadedFileName();
		if (routingRulesSearchVo.getExactSearch()) {
			uploadNameforalert1 = getSQLStringAfterPercentEscaping(uploadNameforalert1)
					+ ".xls";
			uploadNameforalert2 = getSQLStringAfterPercentEscaping(uploadNameforalert2)
					+ ".xlsx";
		} else {
			uploadNameforalert1 = "%"
					+ getSQLStringAfterPercentEscaping(uploadNameforalert1)
					+ "%.xls";
			uploadNameforalert2 = "%"
					+ getSQLStringAfterPercentEscaping(uploadNameforalert2)
					+ "%.xlsx";
		}
		if (sortColumn.equalsIgnoreCase("true") && archiveIndicator) {
			logger.debug("SORT ALERTS AND ARCHIVE HQL=========");
			int buyerId = routingRulesSearchVo.getBuyerId().intValue();

			hql = "SELECT rrh.* FROM  routing_rule_hdr rrh  INNER JOIN routing_rule_buyer_assoc rrba"
					+ " ON rrh.routing_rule_buyer_assoc_id = rrba.routing_rule_buyer_assoc_id INNER JOIN buyer br"
					+ " ON  rrba.buyer_id = br.buyer_id AND br.buyer_id = "
					+ buyerId
					+ " INNER JOIN routing_rule_upload_rule rrur ON rrur.routing_rule_hdr_id = rrh.routing_rule_hdr_id"
					+ " INNER JOIN routing_rule_file_hdr rrfh ON rrfh.routing_rule_file_hdr_id = rrur.routing_rule_file_hdr_id"
					+ " WHERE (rrfh.routing_rule_file_name LIKE '"
					+ uploadNameforalert1
					+ "' OR rrfh.routing_rule_file_name LIKE '"
					+ uploadNameforalert2
					+ "')"
					+ " GROUP BY rrh.routing_rule_hdr_id ORDER BY"
					+ " (IF(rrh.rule_status != 'ARCHIVED' AND (rrh.routing_rule_hdr_id IN(SELECT rra.routing_rule_hdr_id FROM routing_rule_alert rra WHERE rra.alert_status = 'ACTIVE')"
					+ " || rrh.routing_rule_hdr_id IN(SELECT rre.routing_rule_hdr_id FROM routing_rule_error rre)), 1,0)) DESC,rrh.modified_date DESC";

			if (!hql.equalsIgnoreCase(null) && hql != "null" && hql != "") {
				query = getEntityManager().createNativeQuery(hql,
						RoutingRuleHdr.class);
			}
		} else if (sortColumn.equalsIgnoreCase("true")) {
			logger.debug("SORT ALERTS ONLY HQL=========");
			int buyerId = routingRulesSearchVo.getBuyerId().intValue();
			
			hql = "SELECT rrh.* FROM  routing_rule_hdr rrh  INNER JOIN routing_rule_buyer_assoc rrba"
					+ " ON rrh.routing_rule_buyer_assoc_id = rrba.routing_rule_buyer_assoc_id INNER JOIN buyer br"
					+ " ON  rrba.buyer_id = br.buyer_id AND br.buyer_id = "
					+ buyerId
					+ " INNER JOIN routing_rule_upload_rule rrur ON rrur.routing_rule_hdr_id = rrh.routing_rule_hdr_id"
					+ " INNER JOIN routing_rule_file_hdr rrfh ON rrfh.routing_rule_file_hdr_id = rrur.routing_rule_file_hdr_id"
					+ " AND (rrfh.routing_rule_file_name LIKE '"
					+ uploadNameforalert1
					+ "' OR rrfh.routing_rule_file_name LIKE '"
					+ uploadNameforalert2
					+ "')"
					+ " WHERE rrh.rule_status != 'ARCHIVED'"
					+ " GROUP BY rrh.routing_rule_hdr_id ORDER BY"
					+ " (IF(rrh.rule_status != 'ARCHIVED' AND (rrh.routing_rule_hdr_id IN(SELECT rra.routing_rule_hdr_id FROM routing_rule_alert rra WHERE rra.alert_status = 'ACTIVE')"
					+ " || rrh.routing_rule_hdr_id IN(SELECT rre.routing_rule_hdr_id FROM routing_rule_error rre)), 1,0)) DESC,rrh.modified_date DESC";

			if (!hql.equalsIgnoreCase(null) && hql != "null" && hql != "") {
				query = getEntityManager().createNativeQuery(hql,
						RoutingRuleHdr.class);
			}
		}

		else if (archiveIndicator) {
			hql = "FROM RoutingRuleHdr a WHERE a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId AND a.routingRuleHdrId in "
					+ "(SELECT rru.routingRuleHdr.routingRuleHdrId from RoutingRuleUploadRule rru WHERE rru.routingRuleFileHdr.routingRuleFileHdrId in  "
					+ "(SELECT rrfh.routingRuleFileHdrId from RoutingRuleFileHdr rrfh WHERE (rrfh.routingRuleFileName LIKE :uploadName1 OR rrfh.routingRuleFileName LIKE :uploadName2)))";
		}

		else {
			hql = "FROM RoutingRuleHdr a WHERE a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId AND a.ruleStatus != 'ARCHIVED' AND a.routingRuleHdrId in "
					+ "(SELECT rru.routingRuleHdr.routingRuleHdrId from RoutingRuleUploadRule rru WHERE rru.routingRuleFileHdr.routingRuleFileHdrId in  "
					+ "(SELECT rrfh.routingRuleFileHdrId from RoutingRuleFileHdr rrfh WHERE (rrfh.routingRuleFileName LIKE :uploadName1 OR rrfh.routingRuleFileName LIKE :uploadName2)))";
		}
		if (!sortColumn.equalsIgnoreCase("true")) {
			if (StringUtils.isNotBlank(sortColumn)
					&& StringUtils.isNotBlank(sortOrder)) {
				hql += " ORDER BY " + sortColumn + sortOrder;
			}
			if (!hql.equalsIgnoreCase(null) && hql != "null" && hql != "") {
				query = getEntityManager().createQuery(hql);
			}
		
		if (!query.equals(null)) {
			query.setParameter("buyerId", routingRulesSearchVo.getBuyerId());
			query.setParameter("uploadName1", uploadNameforalert1);
			query.setParameter("uploadName2", uploadNameforalert2);
		}
		}
		Integer pageRequested = pagingCriteria.getPageRequested();
		Integer pageIndex = (pageRequested - 1)
				* RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE;
		query.setFirstResult(pageIndex);
		query
				.setMaxResults(RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE);

		try {
			@SuppressWarnings("unchecked")
			List<RoutingRuleHdr> routingRuleHdrList = query.getResultList();
			for(RoutingRuleHdr rule:routingRuleHdrList)
			{
				Query queryName =null;
				int ruleId= rule.getRoutingRuleHdrId().intValue();
				//pass the id to a query ,
				
				String hqlName= "";
				hqlName =" SELECT DISTINCT(a.routing_rule_file_name) FROM routing_rule_file_hdr a, routing_rule_upload_rule b, routing_rule_hdr c WHERE a.routing_rule_file_hdr_id = b.routing_rule_file_hdr_id"
						+ " AND  b.routing_rule_hdr_id = c.routing_rule_hdr_id AND c.routing_rule_hdr_id  = "+ruleId+" AND (a.routing_rule_file_name LIKE '"+uploadNameforalert1+"' OR a.routing_rule_file_name LIKE '"+uploadNameforalert2+"')";
				
				if(! hqlName.equalsIgnoreCase(null) && hqlName != "null" && hqlName != ""){	
				    queryName = getEntityManager().createNativeQuery(hqlName);
				}
				try {
					@SuppressWarnings("unchecked")
					List<String> fileNames = queryName.getResultList();
					if(fileNames!=null)
					{
					rule.setFileNames(fileNames);
					}
				
				} catch (NoResultException e) {
					return null;
				}
				
				}
			return routingRuleHdrList;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * This method is used to check if Rule Id is used  
	 * as the criteria for searching
	 * 
	 * @param routingRulesSearchVo
	 * @param pagingCriteria
	 * @param archiveIndicator
	 * @return routingRuleHdrList
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public List<RoutingRuleHdr> getRoutingRulesHeaderForRuleId(
			RoutingRulesSearchVO routingRulesSearchVo,
			RoutingRulesPaginationVO pagingCriteria, boolean archiveIndicator) 
			{
		String hql = "";
		Query query = null;
		logger.debug("Getting sort col :"+pagingCriteria.getSortCol());
		logger.debug("Getting sort order :"+pagingCriteria.getSortOrd());
		String sortColumn = "";		
		switch (pagingCriteria.getSortCol()) {
		case 0:
			sortColumn = "a.modifiedDate";
			break; // modifiedDate
		case 1:
			sortColumn = "a.ruleName";
			break; // Name
		case 2:
			sortColumn = "a.contact.firstName";
			break; // Contact
		case 3:
			sortColumn = "";
			break; // Action
		case 4:
			sortColumn = "a.ruleStatus";
			break; // Status
		case 5: 
			sortColumn = "true"; 
			break; // sort alerts on top	
		}
		String sortOrder = "";
		switch (pagingCriteria.getSortOrd()) {
			case 0:
				sortOrder = " DESC";
				break;
			case 1:
				sortOrder = " ASC";
				break;
		}
		
		if(sortColumn.equalsIgnoreCase("true") && archiveIndicator){
			logger.debug("SORT ALERTS AND ARCHIVE HQL=========");
			int buyerId = routingRulesSearchVo.getBuyerId().intValue();
			int ruleId = routingRulesSearchVo.getRuleId().intValue();
			hql = "SELECT rrh.* FROM  routing_rule_hdr rrh  INNER JOIN routing_rule_buyer_assoc rrba"+ 
				  " ON rrh.routing_rule_buyer_assoc_id = rrba.routing_rule_buyer_assoc_id INNER JOIN buyer br"+
				  " ON  rrba.buyer_id = br.buyer_id AND br.buyer_id = "+buyerId+ " WHERE rrh.routing_rule_hdr_id = "+ruleId+"" +
				   " GROUP BY rrh.routing_rule_hdr_id ORDER BY"+
				  " (IF(rrh.rule_status != 'ARCHIVED' AND (rrh.routing_rule_hdr_id IN(SELECT rra.routing_rule_hdr_id FROM routing_rule_alert rra WHERE rra.alert_status = 'ACTIVE')"+
				  " || rrh.routing_rule_hdr_id IN(SELECT rre.routing_rule_hdr_id FROM routing_rule_error rre)), 1,0)) DESC,rrh.modified_date DESC";
			
			if(! hql.equalsIgnoreCase(null) && hql != "null" && hql != ""){	
			    query = getEntityManager().createNativeQuery(hql, RoutingRuleHdr.class);
			}			
		}
		else if(sortColumn.equalsIgnoreCase("true"))
		{
			logger.debug("SORT ALERTS ONLY HQL=========");			
			int buyerId = routingRulesSearchVo.getBuyerId().intValue();
			int ruleId = routingRulesSearchVo.getRuleId().intValue();
			hql = "SELECT rrh.* FROM  routing_rule_hdr rrh  INNER JOIN routing_rule_buyer_assoc rrba"+ 
				  " ON rrh.routing_rule_buyer_assoc_id = rrba.routing_rule_buyer_assoc_id INNER JOIN buyer br"+
				  " ON  rrba.buyer_id = br.buyer_id AND br.buyer_id = "+buyerId+"  AND rrh.routing_rule_hdr_id = "+ruleId+"" +
				  " WHERE rrh.rule_status != 'ARCHIVED'" +
				  " GROUP BY rrh.routing_rule_hdr_id ORDER BY"+
				  " (IF(rrh.rule_status != 'ARCHIVED' AND (rrh.routing_rule_hdr_id IN(SELECT rra.routing_rule_hdr_id FROM routing_rule_alert rra WHERE rra.alert_status = 'ACTIVE')"+
				  " || rrh.routing_rule_hdr_id IN(SELECT rre.routing_rule_hdr_id FROM routing_rule_error rre)), 1,0)) DESC,rrh.modified_date DESC";
			
			if(! hql.equalsIgnoreCase(null) && hql != "null" && hql != ""){	
			    query = getEntityManager().createNativeQuery(hql, RoutingRuleHdr.class);
			}							
		}

		else if (archiveIndicator) {
			hql = "FROM RoutingRuleHdr a WHERE a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId " +
					"AND a.routingRuleHdrId LIKE :ruleId";

		}

		else {
			hql = "FROM RoutingRuleHdr a WHERE a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId " +
					"AND a.routingRuleHdrId LIKE :ruleId "
					+ "and a.ruleStatus != 'ARCHIVED'";
		}

		if (!sortColumn.equalsIgnoreCase("true")) 
		{
			if (StringUtils.isNotBlank(sortColumn)
					&& StringUtils.isNotBlank(sortOrder)) {
				hql += " ORDER BY " + sortColumn + sortOrder;
			}
			if (!hql.equalsIgnoreCase(null) && hql != "null" && hql != "") {
				query = getEntityManager().createQuery(hql);
			}
			if(!query.equals(null))
			{
				query.setParameter("buyerId", routingRulesSearchVo.getBuyerId());
				int ruleId = routingRulesSearchVo.getRuleId().intValue();
				query.setParameter("ruleId", ruleId);
			}
		}
		Integer pageRequested = pagingCriteria.getPageRequested();
		Integer pageIndex = (pageRequested - 1)
				* RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE;
		query.setFirstResult(pageIndex);
		query
				.setMaxResults(RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE);

		try {
			@SuppressWarnings("unchecked")
			List<RoutingRuleHdr> routingRuleHdrList = query.getResultList();
			return routingRuleHdrList;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * This method is used to check if Process Id is used  
	 * as the criteria for searching
	 * 
	 * @param routingRulesSearchVo
	 * @param pagingCriteria
	 * @param archiveIndicator
	 * @return routingRuleHdrList
	 */

	@Transactional(propagation = Propagation.REQUIRED)
	public List<RoutingRuleHdr> getRoutingRulesHeaderForProcessId(
			RoutingRulesSearchVO routingRulesSearchVo,
			RoutingRulesPaginationVO pagingCriteria, boolean archiveIndicator) 
			{
		String hql = "";
		Query query = null;
		logger.debug("Getting sort col :"+pagingCriteria.getSortCol());
		logger.debug("Getting sort order :"+pagingCriteria.getSortOrd());
		String sortColumn = "";
		switch (pagingCriteria.getSortCol()) {
		case 0:
			sortColumn = "a.modifiedDate";
			break; // modifiedDate
		case 1:
			sortColumn = "a.ruleName";
			break; // Name
		case 2:
			sortColumn = "a.contact.firstName";
			break; // Contact
		case 3:
			sortColumn = "";
			break; // Action
		case 4:
			sortColumn = "a.ruleStatus";
			break; // Status
		case 5: 
			sortColumn = "true"; 
			break; // sort alerts on top	
		}
		String sortOrder = "";
		switch (pagingCriteria.getSortOrd()) {
			case 0:
				sortOrder = " DESC";
				break;
			case 1:
				sortOrder = " ASC";
				break;
		}
		
		if(sortColumn.equalsIgnoreCase("true") && archiveIndicator){
			logger.debug("SORT ALERTS AND ARCHIVE HQL=========");
			int buyerId = routingRulesSearchVo.getBuyerId().intValue();
			String processId = routingRulesSearchVo.getProcessId();
			processId = "%" + getSQLStringAfterPercentEscaping(processId) + "%";
			String name ="PROCESSID";
			
			hql = "SELECT rrh.* FROM  routing_rule_hdr rrh  INNER JOIN routing_rule_buyer_assoc rrba"+ 
				  " ON rrh.routing_rule_buyer_assoc_id = rrba.routing_rule_buyer_assoc_id INNER JOIN buyer br"+
				  " ON  rrba.buyer_id = br.buyer_id AND br.buyer_id = '"+buyerId+"' INNER JOIN routing_rule_criteria rrc " +
				  " ON rrc.routing_rule_hdr_id = rrh.routing_rule_hdr_id"+
				  " AND rrc.criteria_name LIKE '"+name+"' WHERE rrc.criteria_value LIKE '"+processId+"' " +
				  " GROUP BY rrh.routing_rule_hdr_id ORDER BY"+
				  " (IF(rrh.rule_status != 'ARCHIVED' AND (rrh.routing_rule_hdr_id IN(SELECT rra.routing_rule_hdr_id FROM routing_rule_alert rra WHERE rra.alert_status = 'ACTIVE')"+
				  " || rrh.routing_rule_hdr_id IN(SELECT rre.routing_rule_hdr_id FROM routing_rule_error rre)), 1,0)) DESC,rrh.modified_date DESC";
			
			if(! hql.equalsIgnoreCase(null) && hql != "null" && hql != ""){	
			    query = getEntityManager().createNativeQuery(hql, RoutingRuleHdr.class);
			}			
		}
		else if(sortColumn.equalsIgnoreCase("true"))
		{
			logger.debug("SORT ALERTS ONLY HQL=========");			
			int buyerId = routingRulesSearchVo.getBuyerId().intValue();
			String processId = routingRulesSearchVo.getProcessId();
			processId = "%" + getSQLStringAfterPercentEscaping(processId) + "%";
			String name ="PROCESSID";
			
			hql = "SELECT rrh.* FROM  routing_rule_hdr rrh  INNER JOIN routing_rule_buyer_assoc rrba"+ 
				  " ON rrh.routing_rule_buyer_assoc_id = rrba.routing_rule_buyer_assoc_id INNER JOIN buyer br"+
				  " ON  rrba.buyer_id = br.buyer_id AND br.buyer_id = "+buyerId+" INNER JOIN routing_rule_criteria rrc " +
				  " ON rrc.routing_rule_hdr_id = rrh.routing_rule_hdr_id"+
				  " AND rrc.criteria_name = '"+name+"' AND rrc.criteria_value LIKE '"+processId+"' " +
				  "  WHERE rrh.rule_status != 'ARCHIVED'" +
				  " GROUP BY rrh.routing_rule_hdr_id ORDER BY"+
				  " (IF(rrh.rule_status != 'ARCHIVED' AND (rrh.routing_rule_hdr_id IN(SELECT rra.routing_rule_hdr_id FROM routing_rule_alert rra WHERE rra.alert_status = 'ACTIVE')"+
				  " || rrh.routing_rule_hdr_id IN(SELECT rre.routing_rule_hdr_id FROM routing_rule_error rre)), 1,0)) DESC,rrh.modified_date DESC";
			
			if(! hql.equalsIgnoreCase(null) && hql != "null" && hql != ""){	
			    query = getEntityManager().createNativeQuery(hql, RoutingRuleHdr.class);
			}							
		}

		else if (archiveIndicator) {
			hql = "FROM RoutingRuleHdr a WHERE a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId and "
					+ "a.routingRuleHdrId in (select criteria.routingRuleHdr.routingRuleHdrId from RoutingRuleCriteria "
					+ "criteria where criteria.criteriaName= :name and criteria.criteriaValue LIKE :processId)";
		}

		else {
			hql = "FROM RoutingRuleHdr a WHERE a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId and "
					+ "a.routingRuleHdrId in (select criteria.routingRuleHdr.routingRuleHdrId from RoutingRuleCriteria "
					+ "criteria where criteria.criteriaName= :name and criteria.criteriaValue LIKE :processId) "
					+ "and a.ruleStatus != 'ARCHIVED'";
		}

		if (!sortColumn.equalsIgnoreCase("true")) {
			if (StringUtils.isNotBlank(sortColumn)
					&& StringUtils.isNotBlank(sortOrder)) {
				hql += " ORDER BY " + sortColumn + sortOrder;
			}
			if (!hql.equalsIgnoreCase(null) && hql != "null" && hql != "") {
				query = getEntityManager().createQuery(hql);
			}
			if(!query.equals(null))
			{
				query.setParameter("buyerId", routingRulesSearchVo.getBuyerId());
				String name = routingRulesSearchVo.getProcessId();
				name = "%" + getSQLStringAfterPercentEscaping(name) + "%";
				query.setParameter("name", "PROCESSID");
				query.setParameter("processId", name);
			}
		}
		Integer pageRequested = pagingCriteria.getPageRequested();
		Integer pageIndex = (pageRequested - 1)
				* RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE;
		query.setFirstResult(pageIndex);
		query.setMaxResults(RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE);

		try {
			@SuppressWarnings("unchecked")
			List<RoutingRuleHdr> routingRuleHdrList = query.getResultList();
			return routingRuleHdrList;
		} catch (NoResultException e) {
			return null;
		}
	}

	public Integer searchResultCountForRuleName(
			RoutingRulesSearchVO routingRulesSearchVo, boolean archiveIndicator) 
	{
		String hql = "";

		if (archiveIndicator) {
			 hql = "SELECT COUNT(a.routingRuleHdrId) FROM RoutingRuleHdr a " +
			 		"WHERE a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId AND a.ruleName LIKE :rule";

		}

		else {
			 hql = "SELECT COUNT(a.routingRuleHdrId) FROM RoutingRuleHdr a " +
			 		"WHERE a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId AND a.ruleName LIKE :rule "
					+ "and a.ruleStatus != 'ARCHIVED'";
		}
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", routingRulesSearchVo.getBuyerId());
		String ruleName = routingRulesSearchVo.getRuleName();
		ruleName = "%" + getSQLStringAfterPercentEscaping(ruleName) + "%";
		query.setParameter("rule", ruleName);
		try {
			Number routingRuleHdrCount = (Number) query.getSingleResult();
			Integer count = (Integer) routingRuleHdrCount.intValue();
			return count;
		} catch (NoResultException e) {
			return null;
		}
	}

	public Integer searchResultCountForFirmName(
			RoutingRulesSearchVO routingRulesSearchVo, boolean archiveIndicator) 
	{
		String hql = "";

		if (archiveIndicator) {
			hql = "SELECT COUNT(DISTINCT a.routingRuleHdr) FROM RoutingRuleVendor a WHERE a.vendor.businessName LIKE :businessName " +
					"AND a.routingRuleHdr.routingRuleBuyerAssoc.buyer.buyerId = :buyerId";

		}

		else {
			hql = "SELECT COUNT(DISTINCT a.routingRuleHdr) FROM RoutingRuleVendor a WHERE a.vendor.businessName LIKE :businessName " +
					"AND a.routingRuleHdr.routingRuleBuyerAssoc.buyer.buyerId = :buyerId "
					+ "and a.routingRuleHdr.ruleStatus != 'ARCHIVED'";
		}
		//String hql = "SELECT COUNT(a) FROM RoutingRuleHdr a JOIN a.RoutingRuleVendor b JOIN b.vendor c WITH c.businessName LIKE :businessName WHERE a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", routingRulesSearchVo.getBuyerId());
		String businessName = routingRulesSearchVo.getProviderFirmName();
		businessName = "%" + getSQLStringAfterPercentEscaping(businessName)
				+ "%";
		query.setParameter("businessName", businessName);

		try {
			Number routingRuleHdrCount = (Number) query.getSingleResult();
			Integer count = (Integer) routingRuleHdrCount.intValue();
			return count;
		} catch (NoResultException e) {
			return null;
		}
	}

	public Integer searchResultCountForFirmId
	(RoutingRulesSearchVO routingRulesSearchVo, boolean archiveIndicator) 
	{
		String hql = "";

		if (archiveIndicator) {
			hql = "SELECT COUNT(DISTINCT a.routingRuleHdr) FROM RoutingRuleVendor a WHERE a.vendor.id = :vendorId " +
					"AND a.routingRuleHdr.routingRuleBuyerAssoc.buyer.buyerId = :buyerId ";
				
		}

		else {
			hql = "SELECT COUNT(DISTINCT a.routingRuleHdr) FROM RoutingRuleVendor  a WHERE a.vendor.id = :vendorId " +
					"AND a.routingRuleHdr.routingRuleBuyerAssoc.buyer.buyerId = :buyerId " +
					"AND a.routingRuleHdr.ruleStatus != 'ARCHIVED' ";
				
		}
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", routingRulesSearchVo.getBuyerId());
		int vendorId = routingRulesSearchVo.getProviderFirmId().intValue();
		query.setParameter("vendorId", vendorId);

		try {
			Number routingRuleHdrCount = (Number) query.getSingleResult();
			Integer count = (Integer) routingRuleHdrCount.intValue();
			return count;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * This method is used to check if Upload File Name is used  
	 * as the criteria for searching
	 * 
	 * @param routingRulesSearchVo
	 * @param pagingCriteria
	 * @param archiveIndicator
	 * @return routingRuleHdrList
	 */

	public Integer searchResultCountForUploadFileName(
			RoutingRulesSearchVO routingRulesSearchVo, boolean archiveIndicator) 
	{
		String hql = "";

		if (archiveIndicator) {
			hql ="SELECT COUNT(DISTINCT a.routingRuleHdrId) FROM RoutingRuleHdr a " +
			"WHERE a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId AND a.routingRuleHdrId in "
			+"(SELECT rru.routingRuleHdr.routingRuleHdrId from RoutingRuleUploadRule rru " +
			"WHERE rru.routingRuleFileHdr.routingRuleFileHdrId in  " 
		 	+"(SELECT rrfh.routingRuleFileHdrId from RoutingRuleFileHdr rrfh " +
		 	"WHERE (rrfh.routingRuleFileName LIKE :uploadName1 OR rrfh.routingRuleFileName LIKE :uploadName2)))";
		}

		else {
			hql = "SELECT COUNT(DISTINCT a.routingRuleHdrId) FROM RoutingRuleHdr a WHERE a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId " +
					"AND a.ruleStatus != 'ARCHIVED' AND a.routingRuleHdrId in "
				+"(SELECT rru.routingRuleHdr.routingRuleHdrId from RoutingRuleUploadRule rru WHERE rru.routingRuleFileHdr.routingRuleFileHdrId in  " 
			 	+"(SELECT rrfh.routingRuleFileHdrId from RoutingRuleFileHdr rrfh " +
			 	"WHERE (rrfh.routingRuleFileName LIKE :uploadName1 OR rrfh.routingRuleFileName LIKE :uploadName2)))";
		}
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", routingRulesSearchVo.getBuyerId());
		String uploadName1 = routingRulesSearchVo.getUploadedFileName();
		String uploadName2 = routingRulesSearchVo.getUploadedFileName();
		if (routingRulesSearchVo.getExactSearch()) {
			uploadName1 = getSQLStringAfterPercentEscaping(uploadName1)
					+ ".xls";
			uploadName2 = getSQLStringAfterPercentEscaping(uploadName2)
					+ ".xlsx";
		} else {
			uploadName1 = "%" + getSQLStringAfterPercentEscaping(uploadName1)
					+ "%.xls";
			uploadName2 = "%" + getSQLStringAfterPercentEscaping(uploadName2)
					+ "%.xlsx";
		}
		query.setParameter("uploadName1", uploadName1);	
		query.setParameter("uploadName2", uploadName2);

		try {
			Number routingRuleHdrCount = (Number) query.getSingleResult();
			Integer count = (Integer) routingRuleHdrCount.intValue();
			return count;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * This method is used to check if Rule Id is used  
	 * as the criteria for searching
	 * 
	 * @param routingRulesSearchVo
	 * @param pagingCriteria
	 * @param archiveIndicator
	 * @return routingRuleHdrList
	 */

	public Integer searchResultCountForRuleId(
			RoutingRulesSearchVO routingRulesSearchVo, boolean archiveIndicator) 
	{
		String hql = "";

		if (archiveIndicator) {
			hql = "SELECT COUNT(a.routingRuleHdrId) FROM RoutingRuleHdr a WHERE a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId " +
					"AND a.routingRuleHdrId =:ruleId";

		}

		else {
			hql = "SELECT COUNT(a.routingRuleHdrId) FROM RoutingRuleHdr a WHERE a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId " +
					"AND a.routingRuleHdrId =:ruleId "
					+ "and a.ruleStatus != 'ARCHIVED'";
		} 
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", routingRulesSearchVo.getBuyerId());
		int ruleId = routingRulesSearchVo.getRuleId().intValue();
		
		query.setParameter("ruleId", ruleId);
		try {
			Number routingRuleHdrCount = (Number) query.getSingleResult();
			Integer count = (Integer) routingRuleHdrCount.intValue();
			return count;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 * This method is used to check if Process Id is used  
	 * as the criteria for searching
	 * 
	 * @param routingRulesSearchVo
	 * @param pagingCriteria
	 * @param archiveIndicator
	 * @return routingRuleHdrList
	 */
	
	public Integer searchResultCountForProcessId(
			RoutingRulesSearchVO routingRulesSearchVo, boolean archiveIndicator) 
	{
		String hql = "";

		if (archiveIndicator) {
			hql = "SELECT COUNT(a.routingRuleHdrId) FROM RoutingRuleHdr a WHERE a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId and " +
			"a.routingRuleHdrId in (select criteria.routingRuleHdr.routingRuleHdrId from RoutingRuleCriteria " +
			"criteria where criteria.criteriaName= :name and criteria.criteriaValue LIKE :processId)";
		}

		else {
			hql = "SELECT COUNT(a.routingRuleHdrId) FROM RoutingRuleHdr a WHERE a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId and " +
			"a.routingRuleHdrId in (select criteria.routingRuleHdr.routingRuleHdrId from RoutingRuleCriteria " +
			"criteria where criteria.criteriaName= :name and criteria.criteriaValue LIKE :processId) "+ 
			"and a.ruleStatus != 'ARCHIVED'";
		}
		
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", routingRulesSearchVo.getBuyerId());
		String name = routingRulesSearchVo.getProcessId();
		name = "%" + getSQLStringAfterPercentEscaping(name) + "%";
		query.setParameter("name", "PROCESSID");
		query.setParameter("processId", name);
		try {
			Number routingRuleHdrCount = (Number) query.getSingleResult();
			Integer count = (Integer) routingRuleHdrCount.intValue();
			return count;
		} catch (NoResultException e) {
			return null;
		}
	}
	public Integer searchResultCountForAutoAcceptance(
			RoutingRulesSearchVO routingRulesSearchVo, boolean archiveIndicator)
	{
		String hql = "";

		if (archiveIndicator) {
			hql = "SELECT COUNT(DISTINCT a.routingRuleHdr) FROM RoutingRuleVendor a WHERE a.autoAcceptStatus LIKE :autoAcceptStateBuyer " +
					"AND a.routingRuleHdr.routingRuleBuyerAssoc.buyer.buyerId = :buyerId";

		}

		else {
			hql = "SELECT COUNT(DISTINCT a.routingRuleHdr) FROM RoutingRuleVendor a WHERE a.autoAcceptStatus LIKE :autoAcceptStateBuyer " +
					"AND a.routingRuleHdr.routingRuleBuyerAssoc.buyer.buyerId = :buyerId ";
		}
		
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", routingRulesSearchVo.getBuyerId());
		query.setParameter("autoAcceptStateBuyer", routingRulesSearchVo.getAutoAcceptSearchlabel());
		

		try {
			Number routingRuleHdrCount = (Number) query.getSingleResult();
			Integer count = (Integer) routingRuleHdrCount.intValue();
			return count;
		} catch (NoResultException e) {
			return null;
		}
	
	}
	//SL 15642 Method to fetch all rules with selected auto accept status
		@Transactional(propagation = Propagation.REQUIRED)
		public List<RoutingRuleHdr> getRoutingRulesHeaderForAutoAcceptanceStatus(RoutingRulesSearchVO routingRulesSearchVo,
				RoutingRulesPaginationVO pagingCriteria, boolean archiveIndicator)
		{
			// Handle Sorting
			String hql = "";
			Query query = null;
			logger.debug("Getting sort col :"+pagingCriteria.getSortCol());
			logger.debug("Getting sort order :"+pagingCriteria.getSortOrd());
			String sortColumn = "";
			// Handle Sorting
			
			switch (pagingCriteria.getSortCol()) {
			case 0:
				sortColumn = "a.routingRuleHdr.modifiedDate";
				break; // modifiedDate
			case 1:
				sortColumn = "a.routingRuleHdr.ruleName";
				break; // Name
			case 2:
				sortColumn = "a.vendor.businessName";
				break; // Provider Firm name
			case 3:
				sortColumn = "";
				break; // Action
			case 4:
				sortColumn = "a.routingRuleHdr.ruleStatus";
				break; // Status
			case 5: 
				sortColumn = "true"; 
				break; // Sort Alerts to Top
			case 7: 
				sortColumn = "a.autoAcceptStatus"; 
				break;//Sort by auto acceptance status
			}
			String sortOrder = "";
			switch (pagingCriteria.getSortOrd()) {
			case 0:
				sortOrder = " DESC";
				break;
			case 1:
				sortOrder = " ASC";
				break;
			}
			
			if(sortColumn.equalsIgnoreCase("true") && archiveIndicator){
				logger.debug("SORT ALERTS AND ARCHIVE HQL=========");
		//	String sortColumn = "";
			
			int buyerid=routingRulesSearchVo.getBuyerId();
			String ruleStatus=routingRulesSearchVo.getAutoAcceptSearchlabel();
				hql =" SELECT rrh.* FROM  routing_rule_hdr rrh INNER JOIN routing_rule_vendor rrv ON rrh.routing_rule_hdr_id = rrv.routing_rule_hdr_id"+                 
						  "  INNER JOIN vendor_hdr vh ON rrv.vendor_id = vh.vendor_id INNER JOIN routing_rule_buyer_assoc rrba "+
						  "ON rrh.routing_rule_buyer_assoc_id = rrba.routing_rule_buyer_assoc_id "+
						  "WHERE rrba.buyer_id = "+buyerid+" AND rrv.auto_accept_status ='"+ruleStatus+"'"+
						  "GROUP BY rrh.routing_rule_hdr_id ORDER BY "+
						  "  (IF(rrh.rule_status != 'ARCHIVED' AND (rrh.routing_rule_hdr_id IN(SELECT rra.routing_rule_hdr_id FROM routing_rule_alert rra WHERE rra.alert_status = 'ACTIVE')"+
						  " || rrh.routing_rule_hdr_id IN(SELECT rre.routing_rule_hdr_id FROM routing_rule_error rre)), 1,0)) DESC,rrh.modified_date DESC";
				
				if(! hql.equalsIgnoreCase(null) && hql != "null" && hql != ""){	
				    query = getEntityManager().createNativeQuery(hql, RoutingRuleHdr.class);
				}
			
			}
			else if(sortColumn.equalsIgnoreCase("true"))
			{
				int buyerid=routingRulesSearchVo.getBuyerId();
				String ruleStatus=routingRulesSearchVo.getAutoAcceptSearchlabel();
					hql =" SELECT rrh.* FROM  routing_rule_hdr rrh INNER JOIN routing_rule_vendor rrv ON rrh.routing_rule_hdr_id = rrv.routing_rule_hdr_id"+                 
							  "  INNER JOIN vendor_hdr vh ON rrv.vendor_id = vh.vendor_id INNER JOIN routing_rule_buyer_assoc rrba "+
							  "ON rrh.routing_rule_buyer_assoc_id = rrba.routing_rule_buyer_assoc_id "+
							  "WHERE rrba.buyer_id = "+buyerid+" AND rrv.auto_accept_status ='"+ruleStatus+"'"+" AND a.routingRuleHdr.ruleStatus != 'ARCHIVED'"+
							  "GROUP BY rrh.routing_rule_hdr_id ORDER BY "+
							  " (IF(rrh.rule_status != 'ARCHIVED' AND (rrh.routing_rule_hdr_id IN(SELECT rra.routing_rule_hdr_id FROM routing_rule_alert rra WHERE rra.alert_status = 'ACTIVE')"+
							  " || rrh.routing_rule_hdr_id IN(SELECT rre.routing_rule_hdr_id FROM routing_rule_error rre)), 1,0)) DESC,rrh.modified_date DESC";
					
					if(! hql.equalsIgnoreCase(null) && hql != "null" && hql != ""){	
					    query = getEntityManager().createNativeQuery(hql, RoutingRuleHdr.class);
					}
			}
			else if (archiveIndicator) {
				logger.debug("Search for Auto Status as "+routingRulesSearchVo.getAutoAcceptSearchlabel());
				hql =" SELECT a.routingRuleHdr FROM  RoutingRuleVendor a "+
						"WHERE a.routingRuleHdr.routingRuleBuyerAssoc.buyer.buyerId = :buyerId AND a.autoAcceptStatus =:ruleStatus"+
							  "  GROUP BY a.routingRuleHdr.routingRuleHdrId ";
					if (StringUtils.isNotBlank(sortColumn) && StringUtils.isNotBlank(sortOrder)) {
						hql += " ORDER BY " + sortColumn + sortOrder;
					}
					if(!hql.equalsIgnoreCase(null) && hql != "null" && hql != ""){
				        query = getEntityManager().createQuery(hql);
					}
					if(!query.equals(null)){
					    query.setParameter("buyerId", routingRulesSearchVo.getBuyerId());
					    query.setParameter("ruleStatus", routingRulesSearchVo.getAutoAcceptSearchlabel());
					}
				
			}
			else
			{
				hql =" SELECT a.routingRuleHdr FROM  RoutingRuleVendor a "+
						"WHERE a.routingRuleHdr.routingRuleBuyerAssoc.buyer.buyerId = :buyerId AND a.autoAcceptStatus =:ruleStatus AND a.routingRuleHdr.ruleStatus != 'ARCHIVED'"+
							  "  GROUP BY a.routingRuleHdr.routingRuleHdrId ";
				if (StringUtils.isNotBlank(sortColumn) && StringUtils.isNotBlank(sortOrder)) {
					hql += " ORDER BY " + sortColumn + sortOrder;
				}
				if(!hql.equalsIgnoreCase(null) && hql != "null" && hql != ""){
			        query = getEntityManager().createQuery(hql);
				}
				if(!query.equals(null)){
				    query.setParameter("buyerId", routingRulesSearchVo.getBuyerId());
				    query.setParameter("ruleStatus", routingRulesSearchVo.getAutoAcceptSearchlabel());
				}
			}
				
			// Handle Pagination
			Integer pageRequested = pagingCriteria.getPageRequested();
			Integer pageIndex = (pageRequested - 1)
					* RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE;
			query.setFirstResult(pageIndex);
			query
					.setMaxResults(RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE);

			try {
				@SuppressWarnings("unchecked")
				List<RoutingRuleHdr> routingRuleHdrList = query.getResultList();
				return routingRuleHdrList;
			} catch (NoResultException e) {
				return null;
			}
		
		}
		
		
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RoutingRuleHdrHist> getRoutingRulesHeadersHistforBuyer(
			Integer buyerId, RoutingRulesPaginationVO pagingCriteria) {
		Integer pageRequested = pagingCriteria.getPageRequested();
		Integer pageIndex = (pageRequested - 1)
				* RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE;

		String hql = "FROM RoutingRuleHdrHist rrhh WHERE rrhh.routingRuleBuyerAssoc.buyer.buyerId = :buyerId ORDER BY modifiedDate DESC";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		query.setFirstResult(pageIndex);
		query
				.setMaxResults(RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE);
		try {
			@SuppressWarnings("unchecked")
			List<RoutingRuleHdrHist> routingRuleHdrList = query.getResultList();
			return routingRuleHdrList;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public Integer getRulesHistoryCount(Integer buyerId, boolean archiveIndicator) 
	{
		String hql = "";
		
		if (archiveIndicator) {
			hql = "SELECT COUNT(a.routingRuleHdrHistId) FROM RoutingRuleHdrHist a WHERE a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId";

		}

		else {
			hql = "SELECT COUNT(a.routingRuleHdrHistId) FROM RoutingRuleHdrHist a WHERE a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId "
			+ "and a.routingRuleHdr.ruleStatus != 'ARCHIVED'";
		}
		
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		try {
			Number routingRuleHdrHistCount = (Number) query.getSingleResult();
			Integer count = (Integer) routingRuleHdrHistCount.intValue();
			return count;
		} catch (NoResultException e) {
			return null;
		}
	}

	private String getSQLStringAfterPercentEscaping(String str) {
		boolean endsWithPercent = str.endsWith("%");
		str = StringUtils.join(str.split("%"), "\\%");
		if (endsWithPercent)
			str += "\\%";
		return str;
	}
	
	//SL 15642 Method check auto accept feature set for a particular buyer during upload rule
	public Boolean validateFeature(Integer buyerID, String feature) {
		String hql = null;
		Query query = null;
		try{
			hql = "SELECT COUNT(feature) FROM buyer_feature_set WHERE buyer_id ='"+buyerID+"' AND feature ='"+feature+"' and active_ind =1";
			query = getEntityManager().createNativeQuery(hql);
			Number  retValue =(Number)query.getSingleResult();
			if(retValue!=null && retValue.intValue()==1)
			{
				return  true;	
			}
			else
			{
				return false;
			}
		}
			catch (NoResultException e) {
				logger.info(e.getMessage());
				return null;
			}
	}
	
	/**
	 * Priority 4 issue changes
	 * Retrieve all active CAR rules matching the SO's jobcode, 
	 * zipcode and ISP_ID for Inhome buyer
	 * @param routingRulebuyerAssocId
	 * @param jobcode
	 * @param zip
	 * @param marketId
	 * @param ispIds
	 * @param sortOrder
	 * @return List<RoutingRuleHdr>
	 */
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<RoutingRuleHdr> findRoutingRuleForInhome(Integer routingRulebuyerAssocId,String jobcode,String zip,String marketId, Set<String> ispIds, String sortOrder){
		Query query = null; 
		
	   try {
		   
		   String ispIdValues = "";
		   for(String ispId : ispIds){
			   if(StringUtils.isNotBlank(ispId)){
				   ispIdValues = ispIdValues + "'" + ispId + "' , ";
			   }
		   }
		   if(ispIdValues.length() > 0){
			   ispIdValues = ispIdValues.substring(0, ispIdValues.length()-2);
		   }
		
		   String hql = "SELECT a.* FROM " +
		   " (SELECT rh.*	FROM routing_rule_hdr rh, " +
		   " routing_rule_price rrp, routing_rule_criteria  rrc	" +
		   " WHERE rh.routing_rule_buyer_assoc_id = "+ routingRulebuyerAssocId + 
		   " AND rh.rule_status='ACTIVE' " +
		   " AND (rrc.routing_rule_hdr_id = rh.routing_rule_hdr_id) " +
		   " AND (rrp.routing_rule_hdr_id = rh.routing_rule_hdr_id) " +
		   " AND ((rrc.criteria_name = 'ZIP' AND rrc.criteria_value='" + zip + "') " +
           " || (rrc.criteria_name = 'MARKET' AND rrc.criteria_value='" + marketId + "')) " +
		   " AND (rrp.jobcode='" + jobcode + "')) AS a " +
		   " JOIN routing_rule_criteria rrct " +
		   " ON (a.routing_rule_hdr_id= rrct.routing_rule_hdr_id) " +
		   " WHERE rrct.criteria_name = 'ISP_ID' AND rrct.criteria_value IN (" + ispIdValues + ") " +
		   " GROUP BY rrct.routing_rule_hdr_id " +
		   " ORDER BY a.created_date " + sortOrder;
		
		   query = getEntityManager().createNativeQuery(hql,RoutingRuleHdr.class);	
			
	    }catch(Exception e) {
	    	logger.error("Exception in RoutingRuleBuyerAssocDaoImpl.findRoutingRuleForInhome() due to "+e);
	    }
		try{
			@SuppressWarnings("unchecked")
			List<RoutingRuleHdr> routingRuleHdrList = query.getResultList();
            return routingRuleHdrList;
		} catch (NoResultException e) {
			logger.error("Exception in RoutingRuleBuyerAssocDaoImpl.findRoutingRuleForInhome() due to "+e);
            return null;	
	    }
	}

}
