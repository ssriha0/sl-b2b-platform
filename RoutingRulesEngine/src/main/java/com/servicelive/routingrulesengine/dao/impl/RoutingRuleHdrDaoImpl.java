package com.servicelive.routingrulesengine.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.routingrules.RoutingRuleError;
import com.servicelive.domain.routingrules.RoutingRuleHdr;
import com.servicelive.domain.routingrules.detached.RoutingRuleAutoAcceptHistoryVO;
import com.servicelive.domain.spn.detached.LabelValueBean;
import com.servicelive.routingrulesengine.RoutingRulesConstants;
import com.servicelive.routingrulesengine.dao.RoutingRuleHdrDao;
import com.servicelive.routingrulesengine.services.RoutingRulesPaginationService;
import com.servicelive.routingrulesengine.vo.RoutingRuleCacheStatusVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesConflictVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO;

/**
 * 
 * @author svanloon
 * 
 */
public class RoutingRuleHdrDaoImpl extends AbstractBaseDao implements RoutingRuleHdrDao {

	@Transactional(propagation = Propagation.SUPPORTS)
	public RoutingRuleHdr findBySoId(String soId) {
		String hql = "from RoutingRuleHdr as rrh where rrh.routingRuleHdrId in (select rra.routingRuleHdrId from SORoutingRuleAssoc as rra where rra.soId = :soId) ";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("soId", soId);
		RoutingRuleHdr result;
		try {
			result = (RoutingRuleHdr) query.getSingleResult();
		} catch (NoResultException e) {
			logger.info("RoutingRuleHdrDaoImpl.findBySoId while searching.  This is expected behavior when there isn't a SORoutingRuleAssoc for soId[" + soId + "]");
			result = null;
		}
		return result;
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public RoutingRuleHdr findByRoutingRuleHdrId(Integer routingRuleHdrId) {
		return (RoutingRuleHdr) this.findById(RoutingRuleHdr.class, routingRuleHdrId);
	}

	@Transactional(propagation = Propagation.SUPPORTS)
	public RoutingRuleHdr save(RoutingRuleHdr routingRuleHdr) throws Exception {
		getEntityManager().persist(routingRuleHdr);
		flush();
		return routingRuleHdr;
	}
	
	@Transactional(propagation = Propagation.SUPPORTS)
	public RoutingRuleError delete(RoutingRuleError routingRuleError) throws Exception {
		getEntityManager().remove(routingRuleError);
		flush();
		return routingRuleError;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public List <RoutingRuleHdr> findAll() {
		List <RoutingRuleHdr> result = new ArrayList<RoutingRuleHdr>(0);
		try {
			result = (List<RoutingRuleHdr>) super.findAll("RoutingRuleHdr", 0);
		} catch (Exception ex) {
			logger.error("RoutingRuleHdrDaoImpl.findAll.", ex);
			result = null;
		}
		return result;
	}
	
	@Transactional(propagation = Propagation.REQUIRED)                
	public List<RoutingRuleHdr> getRoutingRulesHeadersforBuyer(Integer buyerId, RoutingRulesPaginationVO pagingCriteria){
		// Handle Sorting
		String sortColumn = "";
		String hql="";
		Query query = null;
		switch (pagingCriteria.getSortCol()) {
			case 0: sortColumn = "a.routingRuleHdr.modifiedDate"; break; // modifiedDate
			case 1: sortColumn = "a.ruleName"; break; // Name
			case 2:  sortColumn = "a.vendor.businessName";break; // Provider Firm name
			case 3:  sortColumn = ""; break; // Action
			case 4:  sortColumn = "a.ruleStatus"; break; // Status
			case 5: sortColumn = "true"; break;
			case 7: sortColumn = "a.autoAcceptStatus"; break;//Sort by auto acceptance status
}
		String sortOrder = "";
		switch (pagingCriteria.getSortOrd()) {
			case 0: sortOrder = " desc"; break;
			case 1: sortOrder = " asc"; break;
		}
		
		if(sortColumn.equalsIgnoreCase("true"))
		{
			int buyerid=buyerId.intValue();
			hql = "SELECT rrh.* FROM  routing_rule_hdr rrh INNER JOIN routing_rule_vendor rrv ON rrh.routing_rule_hdr_id = rrv.routing_rule_hdr_id"+                 
				  " INNER JOIN vendor_hdr vh ON rrv.vendor_id = vh.vendor_id INNER JOIN routing_rule_buyer_assoc rrba"+ 
				  " ON rrh.routing_rule_buyer_assoc_id = rrba.routing_rule_buyer_assoc_id INNER JOIN buyer br"+
				  " ON  rrba.buyer_id = br.buyer_id INNER JOIN contact ct ON rrh.contact_id = ct.contact_id"+
				  " WHERE br.buyer_id = "+buyerid+" AND rrh.rule_status != 'ARCHIVED'" +
				  " GROUP BY rrh.routing_rule_hdr_id ORDER BY"+
				  " (IF(rrh.routing_rule_hdr_id IN(SELECT rra.routing_rule_hdr_id FROM routing_rule_alert rra WHERE rra.alert_status='ACTIVE')"+
				  " || rrh.routing_rule_hdr_id IN(SELECT rre.routing_rule_hdr_id FROM routing_rule_error rre), 1,0)) DESC,rrh.modified_date DESC";
			
			if(! hql.equalsIgnoreCase(null) && hql != "null" && hql != ""){	
			    query = getEntityManager().createNativeQuery(hql, RoutingRuleHdr.class);
			}
		}
		else
		{
			// Form JPA Query to load RoutingRuleHdr objects
			//SL 15642 Sorting for column 4 status and column 1 
			if(pagingCriteria.getSortCol()==4 || pagingCriteria.getSortCol()==1 )
			{
				 hql = "select a from RoutingRuleHdr a where a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId and a.ruleStatus != :ruleStatus";
					if (StringUtils.isNotBlank(sortColumn) && StringUtils.isNotBlank(sortOrder)) {
						hql += " order by " + sortColumn + sortOrder;
					}
			}
			//SL 15642 Sorting for other columns in manage tab of rule
			else
			{
		
		hql =" SELECT a.routingRuleHdr FROM  RoutingRuleVendor a "+
					"WHERE a.routingRuleHdr.routingRuleBuyerAssoc.buyer.buyerId = :buyerId AND a.routingRuleHdr.ruleStatus != :ruleStatus"+
						  "  GROUP BY a.routingRuleHdr.routingRuleHdrId ";
				if (StringUtils.isNotBlank(sortColumn) && StringUtils.isNotBlank(sortOrder)) {
					hql += " ORDER BY " + sortColumn + sortOrder;
				}
				if(!hql.equalsIgnoreCase(null) && hql != "null" && hql != ""){
			        query = getEntityManager().createQuery(hql);
				}
			}
		query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		query.setParameter("ruleStatus", RoutingRulesConstants.ROUTING_RULE_STATUS_ARCHIVED);
		}
		// Handle Pagination
		Integer pageRequested = pagingCriteria.getPageRequested();
		Integer pageIndex = (pageRequested-1)*RoutingRulesPaginationService.DEFAULT_RULES_PAGE_SIZE;
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

		@Transactional(propagation=Propagation.SUPPORTS)
		public Integer getRoutingRulesCount(Integer buyerId){
			
			String hql= "select count(a.routingRuleHdrId) from RoutingRuleHdr a  where a.routingRuleBuyerAssoc.buyer.buyerId = :buyerId and a.ruleStatus != :ruleStatus";
			Query query = getEntityManager().createQuery(hql);
			query.setParameter("buyerId", buyerId);
			query.setParameter("ruleStatus",RoutingRulesConstants.ROUTING_RULE_STATUS_ARCHIVED );	     
			try {
				Number routingRuleHdrCount=(Number)query.getSingleResult();
				Integer count=(Integer)routingRuleHdrCount.intValue();
				return count;
			} catch (NoResultException e) {
				return null;
			}
		}

		/**
		 * Method to fetch active rules whose jobcodes and zipcodes matches with
		 * the baserule
		 * 
		 * @param buyerId
		 * @param baseRuleVO
		 * @return List<RoutingRuleHdr>
		 */

	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RoutingRuleHdr> getActiveRoutingRulesforBuyer(Integer buyerId,
			RoutingRulesConflictVO baseRuleVO) {
		String pickupQuery = "";
		String hql = "";
		hql = "select hdr from RoutingRuleHdr as hdr where hdr.ruleStatus=:status and hdr.routingRuleBuyerAssoc.buyer.buyerId = :buyerId and hdr.routingRuleHdrId !=:rule "
				+ "and hdr.routingRuleHdrId in "
						+"(select price.routingRuleHdr.routingRuleHdrId from RoutingRulePrice as price where price.jobcode in (:jobcodes))"
				+ "and (hdr.routingRuleHdrId in " 
						+"(select criteria.routingRuleHdr.routingRuleHdrId from RoutingRuleCriteria as criteria where criteria.criteriaName= :name and "
						+"(criteria.criteriaValue in (:zips)or criteria.criteriaValue in"
								+ "(select marketlu.lookupZipMarketPk.zipGeocode.zip from LookupZipMarket marketlu where "
								+ "marketlu.lookupZipMarketPk.market.id in "
								+ "(select criteria.criteriaValue from RoutingRuleCriteria as criteria where criteria.routingRuleHdr.routingRuleHdrId =:rule and "
								+ "criteria.criteriaName =:marketCriteria)))))";

		if (baseRuleVO.getPickupLocation() != null
				&& baseRuleVO.getPickupLocation().size() > 0
				&& !baseRuleVO.getPickupLocation().isEmpty()) {
			pickupQuery = "and hdr.routingRuleHdrId in " 
								+"(select criteria.routingRuleHdr.routingRuleHdrId from RoutingRuleCriteria as criteria "
								+ "where criteria.criteriaName = :pickupcriteria and criteria.criteriaValue in (:pikupcodes))";
			hql = hql + pickupQuery;
		} else {
			pickupQuery = "and hdr.routingRuleHdrId not in "
								+"(select criteria.routingRuleHdr.routingRuleHdrId from RoutingRuleCriteria as criteria "
								+"where criteria.criteriaName = :pickupcriteria)";
			hql = hql + pickupQuery;
		}

		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerId", buyerId);
		query.setParameter("status", "ACTIVE");
		query.setParameter("name", "ZIP");
		query.setParameter("jobcodes", baseRuleVO.getJobCodes());
		query.setParameter("zips", baseRuleVO.getZipCodes());
		query.setParameter("rule", baseRuleVO.getRuleId());
		query.setParameter("marketCriteria", "MARKET");
		query.setParameter("pickupcriteria", "PICKUP LOCATION CODE");
		if (baseRuleVO.getPickupLocation() != null
				&& baseRuleVO.getPickupLocation().size() > 0
				&& !baseRuleVO.getPickupLocation().isEmpty()) {
			query.setParameter("pikupcodes", baseRuleVO.getPickupLocation());
		}
		try {
			@SuppressWarnings("unchecked")
			List<RoutingRuleHdr> routingRuleHdrList = query.getResultList();
			return routingRuleHdrList;
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 * Method to fetch zip codes of market to which the car rule is associated
	 * 
	 * @param ruleId
	 * @return List<String>
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<String> getzipsForMarket(Integer ruleId) {
		String hql = "";
		hql = "select marketlu.lookupZipMarketPk.zipGeocode.zip from LookupZipMarket marketlu "
			+ "where marketlu.lookupZipMarketPk.market.id in "
					+ "(select criteria.criteriaValue from RoutingRuleCriteria as criteria "
					+ "where criteria.routingRuleHdr.routingRuleHdrId =:rule and criteria.criteriaName =:marketCriteria)";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("rule", ruleId);
		query.setParameter("marketCriteria", "MARKET");
		try {
			@SuppressWarnings("unchecked")
			List<String> zips = query.getResultList();
			return zips;
		} catch (NoResultException e) {
			return null;
		}
	}

		/**
	 * Return all ruleHdr for the list of ruleId.
	 * 
	 * @param List
	 *            <Integer> ruleIds
	 * @return List<RoutingRuleHdr>
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RoutingRuleHdr> getRoutingRulesHeadersForRuleIds(
			List<Integer> routingRuleIds) {
		String hql = "select ruleHdr from RoutingRuleHdr ruleHdr where ruleHdr.routingRuleHdrId in (:ruleIds)";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("ruleIds", routingRuleIds);
		try {
			@SuppressWarnings("unchecked")
			List<RoutingRuleHdr> routingRuleHdrList = query.getResultList();
			return routingRuleHdrList;
		} catch (NoResultException e) {
			return null;
		}
	}

	/**
	 *  Return all ruleConflict for the rule 
	 * @param ruleId
	 * @return List<RoutingRuleError>
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<RoutingRuleError> getRoutingRulesConflictsForRuleId(
			Integer routingRuleId) {
		String hql = "select ruleError from RoutingRuleError ruleError where ruleError.conflictInd = :conflictInd and ruleError.routingRuleHdr.routingRuleHdrId = :routingRuleHdrId";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("routingRuleHdrId", routingRuleId);
		query.setParameter("conflictInd", true);
		try {
			@SuppressWarnings("unchecked")
			List<RoutingRuleError> routingRuleConflictList = query.getResultList();
			return routingRuleConflictList;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/**
	 *  Return all ruleError for the rule 
	 * @param ruleId
	 * @return List<RoutingRuleError>
	 */
	public List<RoutingRuleError> getRoutingRuleErrorForRuleId(Integer ruleId)
	{
		String hql = "select ruleError from RoutingRuleError ruleError where ruleError.routingRuleHdr.routingRuleHdrId = :routingRuleHdrId";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("routingRuleHdrId", ruleId);
		try {
			@SuppressWarnings("unchecked")
			List<RoutingRuleError> routingRuleConflictList = query.getResultList();
			return routingRuleConflictList;
		} catch (NoResultException e) {
			return null;
		}	
	}

	/* (non-Javadoc)
	 * @see com.servicelive.routingrulesengine.dao.RoutingRuleHdrDao#getRoutingRuleByName(java.lang.String, java.lang.Integer)
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public RoutingRuleHdr getRoutingRuleByName(String ruleName, Integer buyerId){
		String hql = "select ruleHdr from RoutingRuleHdr ruleHdr where ruleHdr.ruleName = :ruleName and ruleHdr.routingRuleBuyerAssoc.buyer.buyerId = :buyerId";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("ruleName", ruleName);
		query.setParameter("buyerId", buyerId);
		try {
			RoutingRuleHdr ruleHdr = (RoutingRuleHdr) query.getSingleResult();
			return ruleHdr;
		} catch (NoResultException e) {
			return null;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.routingrulesengine.dao.RoutingRuleHdrDao#update(com.servicelive.domain.routingrules.RoutingRuleHdr)
	 */
	@Transactional(propagation = Propagation.SUPPORTS)
	public RoutingRuleHdr update(RoutingRuleHdr routingRuleHdr) throws Exception {
		getEntityManager().merge(routingRuleHdr);
		flush();
		return routingRuleHdr;
	}
	
	public List<Integer> findConflict(Integer buyerId, RoutingRulesConflictVO baseRuleVO){
		try{
		String hql1 = "SELECT routing_rule_buyer_assoc_id from routing_rule_buyer_assoc where buyer_id = :buyerId";
		Query query1 = getEntityManager().createNativeQuery(hql1);
		query1.setParameter("buyerId", buyerId);
		Integer buyerAssocId = (Integer)query1.getSingleResult();
		String hql = "SELECT DISTINCT hdr.routing_rule_hdr_id FROM routing_rule_hdr hdr ";
		if (baseRuleVO.getJobCodes() != null
				&& !baseRuleVO.getJobCodes().isEmpty()) {
			hql += " JOIN routing_rule_price price ON hdr.routing_rule_hdr_id = price.routing_rule_hdr_id AND price.jobcode IN (:jobCodes)";
		} else {
			hql += " LEFT OUTER JOIN routing_rule_price price_nojobs ON price_nojobs.routing_rule_hdr_id = hdr.routing_rule_hdr_id ";
		}
		if(baseRuleVO.getPickupLocation() == null || baseRuleVO.getPickupLocation().isEmpty()){
			hql+=" LEFT OUTER JOIN routing_rule_criteria pickup_codes ON pickup_codes.routing_rule_hdr_id = hdr.routing_rule_hdr_id  AND pickup_codes.criteria_name = 'PICKUP LOCATION CODE'";
		}else{
			hql+=" JOIN routing_rule_criteria pickup_codes ON "
				+"hdr.routing_rule_hdr_id = pickup_codes.routing_rule_hdr_id AND "
				+"pickup_codes.criteria_value IN (:pickUpCodes) AND pickup_codes.criteria_name = 'PICKUP LOCATION CODE'";
		}
		if(baseRuleVO.getZipCodes() == null || baseRuleVO.getZipCodes().isEmpty()){
			hql+=" LEFT OUTER JOIN routing_rule_criteria zip_code ON "
				+"zip_code.routing_rule_hdr_id = hdr.routing_rule_hdr_id  AND zip_code.rule_type_id = 1";	
		}else{
			hql+=" JOIN routing_rule_criteria zip_code ON "
				+"hdr.routing_rule_hdr_id = zip_code.routing_rule_hdr_id AND zip_code.rule_type_id = 1 AND ("
				+"(zip_code.criteria_value IN (:zipCodes) AND zip_code.criteria_name = 'ZIP') OR "
				+"(zip_code.criteria_value IN (SELECT market_id FROM lu_zip_market WHERE zip IN (:zipCodes)) AND zip_code.criteria_name = 'MARKET' ))";
		}
		hql+=" WHERE hdr.rule_status = 'ACTIVE' AND hdr.routing_rule_buyer_assoc_id = :buyerAssocId AND hdr.routing_rule_hdr_id != :ruleId";
		if(baseRuleVO.getJobCodes() == null
				|| baseRuleVO.getJobCodes().isEmpty()){
			hql+=" AND  price_nojobs.routing_rule_hdr_id IS NULL";
		}
		if(baseRuleVO.getPickupLocation() == null || baseRuleVO.getPickupLocation().isEmpty()){
			hql+=" AND pickup_codes.routing_rule_hdr_id IS NULL";
		}
		if(baseRuleVO.getZipCodes() == null || baseRuleVO.getZipCodes().isEmpty()){
			hql+=" AND zip_code.routing_rule_hdr_id IS NULL";
		}
		Query query = getEntityManager().createNativeQuery(hql);
		query.setParameter("ruleId", baseRuleVO.getRuleId());
		query.setParameter("jobCodes", baseRuleVO.getJobCodes());
		query.setParameter("pickUpCodes", baseRuleVO.getPickupLocation());
		query.setParameter("zipCodes", baseRuleVO.getZipCodes());
		query.setParameter("buyerAssocId", buyerAssocId);
		@SuppressWarnings("unchecked")
		List<Integer> conflictingRuleIds = query.getResultList();
		return conflictingRuleIds;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
		
	}
	
	public List<String> findConflictingZips(Integer ruleId,Integer conflictingRuleId){
		try{
		String queryString = "SELECT criteriaConflict.criteria_value"
								+" FROM routing_rule_criteria criteriaConflict"
								+" JOIN routing_rule_criteria Criteriarule ON Criteriarule.criteria_name = 'ZIP'"
								+" AND criteriaConflict.criteria_value = Criteriarule.criteria_value AND Criteriarule.routing_rule_hdr_id =:ruleId" 
								+" AND criteriaConflict.criteria_name = 'ZIP'"
								+" AND criteriaConflict.routing_rule_hdr_id = :conflictingRuleId";
		Query query = getEntityManager().createNativeQuery(queryString);
		query.setParameter("ruleId", ruleId);
		query.setParameter("conflictingRuleId", conflictingRuleId);
		@SuppressWarnings("unchecked")
		List<String> conflictingZips = query.getResultList();
		return conflictingZips;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> findConflictingJobCodes(Integer ruleId,Integer conflictingRuleId){
		try{
		String queryString = "SELECT  DISTINCT priceConflict.jobcode"
								+" FROM routing_rule_price priceConflict"
								+" JOIN routing_rule_price priceRule ON priceRule.routing_rule_hdr_id = :ruleId"
								+" AND priceConflict.jobcode =priceRule.jobcode"
								+" AND priceConflict.routing_rule_hdr_id = :conflictingRuleId";
		Query query = getEntityManager().createNativeQuery(queryString);
		query.setParameter("ruleId", ruleId);
		query.setParameter("conflictingRuleId", conflictingRuleId);
		@SuppressWarnings("unchecked")
		List<String> conflictingZips = query.getResultList();
		return conflictingZips;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> findConflictingPickupCodes(Integer ruleId,Integer conflictingRuleId){
		try{
		String queryString = "SELECT criteriaConflict.criteria_value"
								+" FROM routing_rule_criteria criteriaConflict"
								+" JOIN routing_rule_criteria Criteriarule ON Criteriarule.criteria_name = 'PICKUP LOCATION CODE'"
								+" AND criteriaConflict.criteria_value = Criteriarule.criteria_value AND Criteriarule.routing_rule_hdr_id = :ruleId"
								+" AND criteriaConflict.criteria_name = 'PICKUP LOCATION CODE'"
								+" AND criteriaConflict.routing_rule_hdr_id = :conflictingRuleId";
		Query query = getEntityManager().createNativeQuery(queryString);
		query.setParameter("ruleId", ruleId);
		query.setParameter("conflictingRuleId", conflictingRuleId);
		@SuppressWarnings("unchecked")
		List<String> conflictingZips = query.getResultList();
		return conflictingZips;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public HashMap<Integer, List<String>>  getConflictingZips(List<String> zips,Integer buyerId){
		try{
		String hql = "SELECT criteria.routing_rule_hdr_id,criteria.criteria_value"  
						+" FROM routing_rule_criteria criteria,routing_rule_hdr hdr "
						+" WHERE hdr.routing_rule_hdr_id = criteria.routing_rule_hdr_id" 
						+" AND criteria.criteria_name='ZIP'" 
						+" AND criteria.criteria_value IN (:zipList)"
						+" AND hdr.rule_status='ACTIVE'" 
						+" AND routing_rule_buyer_assoc_id = :buyerId";
		
			Query query = getEntityManager().createNativeQuery(hql);
			query.setParameter("zipList", zips);
			query.setParameter("buyerId", buyerId);
			Iterator hdrIterator = query.getResultList().iterator();
			HashMap<Integer, List<String>> conflictingZips = new HashMap<Integer, List<String>>();
			while ( hdrIterator.hasNext() ) {
				Object[] tuple = (Object[]) hdrIterator.next();
				Integer routingHdrId = (Integer) tuple[0];
				String zip = (String) tuple[1];
				List<String> zipCodeList = new ArrayList<String>();
				if(conflictingZips.containsKey(routingHdrId)){
					List<String> newzipList = conflictingZips.get(routingHdrId);
					newzipList.add(zip);
					conflictingZips.put(routingHdrId,newzipList);
				}
				else{
					zipCodeList.add(zip);
					conflictingZips.put(routingHdrId, zipCodeList);
				}
		}
			return conflictingZips;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public HashMap<Integer, List<String>>  getConflictingPickups(List<String> pickups,Integer buyerId){
		try{
		String hql ="SELECT criteria.routing_rule_hdr_id,criteria.criteria_value FROM routing_rule_criteria criteria,routing_rule_hdr hdr"
		 +" WHERE  hdr.routing_rule_hdr_id = criteria.routing_rule_hdr_id"
		 +" AND hdr.rule_status='ACTIVE'" 
		 +" AND hdr.routing_rule_buyer_assoc_id = :buyerId"
		 +" AND criteria.criteria_name='PICKUP LOCATION CODE'" 
		 +" AND criteria.criteria_value IN (:pickupList)";
			Query query = getEntityManager().createNativeQuery(hql);
			query.setParameter("pickupList", pickups);
			query.setParameter("buyerId", buyerId);
			Iterator hdrIterator = query.getResultList().iterator();
			HashMap<Integer, List<String>> conflictingPickups = new HashMap<Integer, List<String>>();
			while ( hdrIterator.hasNext() ) {
				Object[] tuple = (Object[]) hdrIterator.next();
				Integer routingHdrId = (Integer) tuple[0];
				String pickup = (String) tuple[1];
				List<String> zipPickupList = new ArrayList<String>();
				if(conflictingPickups.containsKey(routingHdrId)){
					List<String> newPickupList = conflictingPickups.get(routingHdrId);
					newPickupList.add(pickup);
					conflictingPickups.put(routingHdrId,newPickupList);
				}
				else{
					zipPickupList.add(pickup);
					conflictingPickups.put(routingHdrId, zipPickupList);
				}
		}
			return conflictingPickups;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public HashMap<Integer, List<String>>  getConflictingJobcodes(List<String> jobcodes,Integer buyerId){
		try{
		String hql ="SELECT job.routing_rule_hdr_id,job.jobcode FROM routing_rule_price job,routing_rule_hdr hdr"  
					 +" WHERE hdr.routing_rule_hdr_id = job.routing_rule_hdr_id "
					 +" AND hdr.rule_status='ACTIVE'" 
					 +" AND hdr.routing_rule_buyer_assoc_id = :buyerId"
					 +" AND job.jobcode IN (:jobcodeList)";
			Query query = getEntityManager().createNativeQuery(hql);
			query.setParameter("jobcodeList", jobcodes);
			query.setParameter("buyerId", buyerId);
			Iterator hdrIterator = query.getResultList().iterator();
			HashMap<Integer, List<String>> conflictingJobcodes = new HashMap<Integer, List<String>>();
			while ( hdrIterator.hasNext() ) {
				Object[] tuple = (Object[]) hdrIterator.next();
				Integer routingHdrId = (Integer) tuple[0];
				String jobcode = (String) tuple[1];
				List<String> zipJobcodeList = new ArrayList<String>();
				if(conflictingJobcodes.containsKey(routingHdrId)){
					List<String> newJobcodeList = conflictingJobcodes.get(routingHdrId);
					newJobcodeList.add(jobcode);
					conflictingJobcodes.put(routingHdrId,newJobcodeList);
				}
				else{
					zipJobcodeList.add(jobcode);
					conflictingJobcodes.put(routingHdrId, zipJobcodeList);
				}
		}
			return conflictingJobcodes;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Integer getbuyerAssocId(Integer buyerId){
		try{
		String hql1 = "SELECT routing_rule_buyer_assoc_id from routing_rule_buyer_assoc where buyer_id = :buyerId";
		Query query1 = getEntityManager().createNativeQuery(hql1);
		query1.setParameter("buyerId", buyerId);
		Integer buyerAssocId = (Integer)query1.getSingleResult();
		return buyerAssocId;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public HashMap<Integer, List<Integer>>  getCriteriaCount(Set<Integer> routingRuleIds) {
		
		try{
			List<Integer> tempIds = new ArrayList<Integer>(routingRuleIds);
			long startTime = System.currentTimeMillis();
			String hql ="SELECT routing_rule_hdr_id,criteria_name,COUNT(1)"
					+" FROM routing_rule_criteria"
					+" WHERE (criteria_name='ZIP' OR criteria_name='PICKUP LOCATION CODE')"
					+" AND routing_rule_hdr_id IN (:routingRuleIds)"
					+" GROUP BY routing_rule_hdr_id,criteria_name";
				Query query = getEntityManager().createNativeQuery(hql);
				query.setParameter("routingRuleIds", tempIds);
				Iterator hdrIterator = query.getResultList().iterator();
				HashMap<Integer, List<Integer>> criteriaCountMap = new HashMap<Integer, List<Integer>>();
				
				HashMap<Integer, List<Integer>> tempCriteriaCountMap = new HashMap<Integer, List<Integer>>();
				logger.info("@@@Start of  zip/pickup iteration "+" Time:"+(System.currentTimeMillis()-startTime));
				while ( hdrIterator.hasNext() ) {
					logger.info("inside Iterator....");
					Object[] tuple = (Object[]) hdrIterator.next();
					Integer routingHdrId = (Integer) tuple[0];
					String criteria = (String) tuple[1];
					//Long count = (Long) tuple[2];
					logger.info("VALUES"+routingHdrId+"::"+criteria);
					List<Integer> tempValue = tempCriteriaCountMap.get(routingHdrId);
					if(tempValue==null) {
						List<Integer> tempList = new ArrayList<Integer>(3);
						tempList.add(0);
						tempList.add(0);
						tempList.add(0);
						tempCriteriaCountMap.put(routingHdrId, tempList);
					}
					logger.info("AFTER TEMP VALUE SET");
					if(criteria.equalsIgnoreCase("ZIP")) {
						List<Integer> values = tempCriteriaCountMap.get(routingHdrId);
						values.set(0, new Integer(1));	
						tempCriteriaCountMap.put(routingHdrId, values);											
					}
					logger.info("AFTER ZIP VALUE SET");
					if(criteria.equalsIgnoreCase("PICKUP LOCATION CODE")) {
						List<Integer> values = tempCriteriaCountMap.get(routingHdrId);
						values.set(1, new Integer(1));	
						tempCriteriaCountMap.put(routingHdrId, values);												
					}
					logger.info("AFTER PC VALUE SET");			
				}
				logger.info("@@@End of  zip/pickup iteration "+" Time:"+(System.currentTimeMillis()-startTime));
				String hql1 ="SELECT routing_rule_hdr_id,COUNT(jobcode)"
					+" FROM routing_rule_price"
					+" WHERE routing_rule_hdr_id IN (:routingRuleIds)"
					+" GROUP BY routing_rule_hdr_id";	
			Query query1 = getEntityManager().createNativeQuery(hql1);
			query1.setParameter("routingRuleIds", tempIds);
			Iterator hdrIterator1 = query1.getResultList().iterator();						
			while ( hdrIterator1.hasNext() ) {
				Object[] tuple = (Object[]) hdrIterator1.next();
				Integer routingHdrId = (Integer) tuple[0];
				//Integer count = (Integer) tuple[1];				
				List<Integer> values = tempCriteriaCountMap.get(routingHdrId);
				if(values==null) {
					List<Integer> tempList = new ArrayList<Integer>(3);
					tempList.add(0);
					tempList.add(0);
					tempList.add(0);
					tempCriteriaCountMap.put(routingHdrId, tempList);
				}else {
					values.set(2, 1);	
					tempCriteriaCountMap.put(routingHdrId, values);	
				}			
								
			}				
			logger.info("@@@End of  jobcode iteration "+" Time:"+(System.currentTimeMillis()-startTime));		
			for(Integer id:routingRuleIds){				
				List<Integer> criteriaExistList = tempCriteriaCountMap.get(id);
				if(criteriaExistList!=null) {
					criteriaCountMap.put(id, criteriaExistList);
				}else {
					List<Integer> tempList = new ArrayList<Integer>(3);
					tempList.add(0);
					tempList.add(0);
					tempList.add(0);					
					criteriaCountMap.put(id, tempList);
				}			
			}
			return criteriaCountMap;
		}catch(Exception e){
				e.printStackTrace();
				return null;
		}		
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public List<Integer> getHeaderList(Integer buyerId) {
		
		String hql = "SELECT routing_rule_hdr_id FROM routing_rule_hdr WHERE routing_rule_buyer_assoc_id = :buyerId AND rule_status = 'ACTIVE'";
		Query query = getEntityManager().createNativeQuery(hql);
		query.setParameter("buyerId", buyerId);		
		List<Integer> hdrList = query.getResultList();
		return hdrList;
	}
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> getZips(Integer hdrId) {
		try{
			String hql ="SELECT distinct criteria_value FROM routing_rule_criteria WHERE criteria_name='ZIP' AND routing_rule_hdr_id=:hdrId";
				Query query = getEntityManager().createNativeQuery(hql);
				query.setParameter("hdrId", hdrId);
				
				List<String> zips = query.getResultList();
				return zips;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}				
	}
	@Transactional(propagation = Propagation.REQUIRED)
    public List<String> getPickups(Integer hdrId) {
    	try{
			String hql ="SELECT distinct criteria_value FROM routing_rule_criteria WHERE criteria_name='PICKUP LOCATION CODE' AND routing_rule_hdr_id=:hdrId";
				Query query = getEntityManager().createNativeQuery(hql);
				query.setParameter("hdrId", hdrId);
				
				List<String> pls = query.getResultList();
				return pls;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	@Transactional(propagation = Propagation.REQUIRED)
    public List<String> getJobCodes(Integer hdrId) {
    	try{
			String hql ="SELECT distinct jobcode FROM routing_rule_price WHERE routing_rule_hdr_id=:hdrId";
				Query query = getEntityManager().createNativeQuery(hql);
				query.setParameter("hdrId", hdrId);
				
				List<String> jcs = query.getResultList();
				return jcs;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
    
    @Transactional(propagation = Propagation.REQUIRED)
    public  List<Integer> getCarBuyers(){
    	try{
			String hql ="SELECT DISTINCT routing_rule_buyer_assoc_id FROM routing_rule_hdr";
			Query query = getEntityManager().createNativeQuery(hql);			
				List<Integer> buyerIds = query.getResultList();
				return buyerIds;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}	
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public HashMap<Integer, HashMap<Integer, List<String>>> getMarketZips(Integer buyerId){
		try{
			 long startTime = System.currentTimeMillis();
			 HashMap<Integer, HashMap<Integer, List<String>>> marketZipsforHdrIdMap = new HashMap<Integer, HashMap<Integer, List<String>>>();
			 String hql = "SELECT DISTINCT cri.routing_rule_hdr_id FROM routing_rule_hdr hdr ,routing_rule_criteria cri "
					+ " WHERE hdr.routing_rule_hdr_id=cri.routing_rule_hdr_id "
					+ " AND hdr.rule_status='ACTIVE' "
					+ " AND hdr.routing_rule_buyer_assoc_id=:buyerId "
					+ " AND cri.criteria_name='MARKET'";
			 Query query = getEntityManager().createNativeQuery(hql);
			 query.setParameter("buyerId", buyerId);
			 List<Integer> hdrIdList = (List<Integer>)query.getResultList();
			 logger.info("after 1st query getMarketZipsquery"+(System.currentTimeMillis()-startTime)+"hdrIdList::"+hdrIdList);
			 for(Integer hdrId:hdrIdList){
				 String hql1 = "SELECT criteria_value FROM routing_rule_criteria WHERE routing_rule_hdr_id = :hdrId AND criteria_name = 'MARKET'";
				 Query query1 = getEntityManager().createNativeQuery(hql1);
				 query1.setParameter("hdrId", hdrId);
				 List<String> marketIds = query1.getResultList();
				 HashMap<Integer, List<String>> marketZipMap= new HashMap<Integer, List<String>>();				 
				 for(String marketId:marketIds) {
					 Integer mId = Integer.parseInt(marketId);
					 String hql2 = "SELECT DISTINCT market.zip  FROM  lu_zip_market market WHERE market.market_id =:marketId ";
					 Query query2 = getEntityManager().createNativeQuery(hql2);
					 query2.setParameter("marketId",mId );
					 List<String> zips = query2.getResultList();
					 marketZipMap.put(mId, zips);
				 }
				 marketZipsforHdrIdMap.put(hdrId, marketZipMap);			 						
			 }
			logger.info("end iteration getMarketZipsquery"+(System.currentTimeMillis()-startTime));
			return marketZipsforHdrIdMap;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
    
    
	public List<Integer> getAllRuleHdrIds(Integer buyerAssocId){
		String queryString = "SELECT routing_rule_hdr_id FROM routing_rule_hdr WHERE routing_rule_buyer_assoc_id = :buyerAssocId";
		Query query = getEntityManager().createNativeQuery(queryString);
		query.setParameter("buyerAssocId", buyerAssocId);
		@SuppressWarnings("unchecked")
		List<Integer> hdrIds = query.getResultList();
		return hdrIds;
	}
	
	public List<Integer> getAllRuleHdrIdsExceptActive(List<String> ruleIds){
		String queryString = "SELECT routing_rule_hdr_id FROM routing_rule_hdr WHERE routing_rule_hdr_id in (:ruleIds) AND rule_status IN ('ARCHIVED','INACTIVE')";
		Query query = getEntityManager().createNativeQuery(queryString);
		query.setParameter("ruleIds", ruleIds);
		@SuppressWarnings("unchecked")
		List<Integer> hdrIds = query.getResultList();
		return hdrIds;
	}
	
	public List<Integer> getAllRuleHdrIdsExceptArchive(List<String> ruleIds){
		String queryString = "SELECT routing_rule_hdr_id FROM routing_rule_hdr WHERE routing_rule_hdr_id in (:ruleIds) AND rule_status IN ('ACTIVE','INACTIVE')";
		Query query = getEntityManager().createNativeQuery(queryString);
		query.setParameter("ruleIds", ruleIds);
		@SuppressWarnings("unchecked")
		List<Integer> hdrIds = query.getResultList();
		return hdrIds;
	}
	
	public void updateRuleStatusAndComment(List<String> ruleIds, String status, String comment, String userName){
		String queryString = "UPDATE routing_rule_hdr SET rule_status = :status,rule_comment = :comment, modified_by = :userName WHERE routing_rule_hdr_id in (:ruleIds)";
		Query query = getEntityManager().createNativeQuery(queryString);
		query.setParameter("ruleIds", ruleIds);
		query.setParameter("status", status);
		query.setParameter("comment", comment);
		query.setParameter("userName", userName);
		query.executeUpdate();
		return;
	}
	
	 public List<String> getMarketIds(Integer hdrId){
		 try{
		 String queryString = "SELECT criteria_value FROM routing_rule_criteria WHERE criteria_name = 'MARKET' AND routing_rule_hdr_id = :ruleId";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("ruleId", hdrId);
			@SuppressWarnings("unchecked")
			List<String> marketIds = query.getResultList();
			return marketIds;
		 }catch(Exception e){
				e.printStackTrace();
				return null;
		 }
		}
	 public List<String>  findMarketZips(Integer marketId){
		 try{
		 String queryString = "SELECT zip FROM lu_zip_market WHERE market_id = :marketId";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("marketId", marketId);
			@SuppressWarnings("unchecked")
			List<String> zips = query.getResultList();
			return zips;
		 }catch(Exception e){
				e.printStackTrace();
				return null;
		 }
	 }
	 
	 public void updateCache(List<RoutingRuleHdr> ruleHdrs, String action,Integer buyerAssocId) {
		 try {
			long startTime = System.currentTimeMillis();			
			Query query = null;
			Query selectQuery = null;
			String hql = null;
			String selectSql = null;
			if(null != ruleHdrs && ruleHdrs.size()>0){
				for(RoutingRuleHdr ruleHdr:ruleHdrs) {
					 //1. Check if the rule id is already there in the table. If so, update the record , else insert a new record.
					 Integer ruleId = ruleHdr.getRoutingRuleHdrId();
					 logger.info("The updated time stamp"+ruleHdr.getModifiedDate());
					 Date statusUpdatedTime = ruleHdr.getModifiedDate();
					 selectSql = "SELECT update_action from routing_rule_active_cache_status " +
					 	" WHERE routing_rule_hdr_id = :routingruleId ";
					 selectQuery = getEntityManager().createNativeQuery(selectSql); 
					 selectQuery.setParameter("routingruleId", ruleId);				
					 String existingAction = null;				
					 try{
						 existingAction = (String)selectQuery.getSingleResult();
					 }catch(NoResultException exception){
						 existingAction = null;
					 }
					 if(null != existingAction && !StringUtils.isEmpty(existingAction)){					
						 // Update
						 if(RoutingRulesConstants.RULE_ACTION_INACTIVATE.equalsIgnoreCase(existingAction) 
								 && RoutingRulesConstants.RULE_ACTION_ACTIVATE.equalsIgnoreCase(action)){
							 // Already in-activated and now activating. Change to UPDATE action, so that the cache will be updated.
							 action = RoutingRulesConstants.RULE_ACTION_UPDATE;
						 }					
						 hql = "UPDATE routing_rule_active_cache_status SET update_action = :action, cache_updated_time = :updatedTime, " +
						 		"buyer_assoc_id = :buyerAssocId WHERE routing_rule_hdr_id = :ruleId";
						 query = getEntityManager().createNativeQuery(hql);	
						 
						 query.setParameter("action", action);
						 query.setParameter("updatedTime", statusUpdatedTime);
						 query.setParameter("buyerAssocId", buyerAssocId);
						 query.setParameter("ruleId", ruleId);
						 query.executeUpdate();
					 }else {					
						 // insert					
						 hql = "INSERT INTO routing_rule_active_cache_status(routing_rule_hdr_id,update_action,buyer_assoc_id,"+
		                   "is_cache_update_from_batch,is_cache_update_from_frontend,cache_updated_time) VALUES (:ruleId,:action,:buyerAssocId,1,1,:updatedTime)";
						 query = getEntityManager().createNativeQuery(hql);
						 query.setParameter("ruleId", ruleId);
						 query.setParameter("action", action);
						 query.setParameter("buyerAssocId", buyerAssocId);
						 query.setParameter("updatedTime", statusUpdatedTime);
						 query.executeUpdate();
					 }
					 selectSql = null;
					 selectQuery = null;
					 query = null;
					 hql = null;
				 }	
			}
			 
			 logger.info("updateCache.after insert:"+(System.currentTimeMillis()-startTime));
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		 		 
	 }
	 
	 public List<RoutingRuleCacheStatusVO> retrieveDirtyCache(Integer sourceOfRetrieve) {
		 try {
			 
			 String hql = null;
			 /*if(RoutingRulesConstants.UPDATE_FROM_BATCH .equals(sourceOfRetrieve)) {
				 hql = "SELECT routing_rule_hdr_id,update_action,buyer_assoc_id FROM routing_rule_active_cache_status WHERE is_cache_update_from_batch = 1";				
			 }else if(RoutingRulesConstants.UPDATE_FROM_FRONT .equals(sourceOfRetrieve)) {
			     hql = "SELECT routing_rule_hdr_id,update_action,buyer_assoc_id FROM routing_rule_active_cache_status WHERE is_cache_update_from_frontend = 1";
			 }*/
			 hql = "SELECT routing_rule_hdr_id,update_action,buyer_assoc_id,cache_updated_time FROM routing_rule_active_cache_status";
			 Query query = getEntityManager().createNativeQuery(hql);
			 Iterator iterator = query.getResultList().iterator();
			 List<RoutingRuleCacheStatusVO> cacheStatusVOs = new ArrayList<RoutingRuleCacheStatusVO>();
			 if(iterator!=null) {
				 while(iterator.hasNext()) {
					 Object[] tuple = (Object[]) iterator.next();
					 RoutingRuleCacheStatusVO statusVO = new RoutingRuleCacheStatusVO();
					 statusVO.setRoutingRuleHdrId((Integer)tuple[0]);
					 statusVO.setAction((String)tuple[1]);
					 statusVO.setBuyerAssocId((Integer)tuple[2]);
					 statusVO.setUpdateTime((Date)tuple[3]);
					 cacheStatusVOs.add(statusVO);					 
				 }
			 }
			/* hql = null;
			 if(RoutingRulesConstants.UPDATE_FROM_BATCH.equals(sourceOfRetrieve)) {
				 hql = "DELETE FROM routing_rule_active_cache_status where is_cache_update_from_batch = 1";
			 }else if(RoutingRulesConstants.UPDATE_FROM_FRONT.equals(sourceOfRetrieve)) {
				 hql = "DELETE FROM routing_rule_active_cache_status where is_cache_update_from_frontend = 1";
			 }
			 query = null;
			 query = getEntityManager().createNativeQuery(hql);
			 query.executeUpdate();*/
			 return cacheStatusVOs;
		 }catch(Exception e) {
			 logger.info("Exception in retrieveDirtyCache:"+e.getMessage());
			 e.printStackTrace();
			 return null;
		 }
		 		 
	 }
	 
	 public List<LabelValueBean> getRoutingRulesBuyer(Integer buyerId){
		 List<LabelValueBean> ruleNameIdList  = new ArrayList<LabelValueBean>();
		 try{
			 String sql = "SELECT hdr.rule_name,hdr.routing_rule_hdr_id FROM routing_rule_hdr hdr,routing_rule_buyer_assoc rrbuyer, buyer b"+
			 	" WHERE hdr.routing_rule_buyer_assoc_id = rrbuyer.routing_rule_buyer_assoc_id AND  rrbuyer.buyer_id = b.buyer_id"+
			 	" AND b.buyer_id ="+buyerId+" AND hdr.rule_status != 'ARCHIVED'";
			 
			 Query query = getEntityManager().createNativeQuery(sql);
			 Iterator iterator = query.getResultList().iterator();
			 if(iterator!=null) {
				 while(iterator.hasNext()) {
					 Object[] tuple = (Object[]) iterator.next();
					 LabelValueBean valueBean = new LabelValueBean((String)tuple[0], ((Integer)tuple[1]).toString());
					 ruleNameIdList.add(valueBean);
				 }
			}
		 }catch(Exception e) {
			 logger.info("Exception in getRoutingRulesBuyer:"+e.getMessage());
			 e.printStackTrace();
			 return null;
		 }
		 return ruleNameIdList;
	 }
	
	//SL-15642 Method to save or update the email id and email id required status for buyer from manage rule dashboard
		@Transactional(propagation = Propagation.REQUIRED)
		public void saveManageRuleBuyerEmailId(Integer indEmailNotifyRequire,String manageRuleBuyerEmailId,Integer buyerId)
		{
			
			String hql = null;
			Query query = null;
			//Buyer don't need email for status change update table with null email id and email required status as 0
			 if(indEmailNotifyRequire==0)
				    {
						hql = "UPDATE  routing_rule_buyer_assoc SET  modified_date=NOW(),auto_accept_email_required=:indEmailNotifyRequire,email_id=:buyerEmailId WHERE buyer_id = :buyerId";
								 query = getEntityManager().createNativeQuery(hql);
								 query.setParameter("indEmailNotifyRequire", indEmailNotifyRequire);	
								 query.setParameter("buyerEmailId", null);
								 query.setParameter("buyerId", buyerId);
								 query.executeUpdate();
					}
			//Buyer  need email for status change update table  with the entered  email id and email required status as 1
				    else
				    {
				    	hql = "UPDATE  routing_rule_buyer_assoc SET  modified_date=NOW(),auto_accept_email_required=:indEmailNotifyRequire,email_id=:buyerEmailId WHERE buyer_id = :buyerId";
						 query = getEntityManager().createNativeQuery(hql);
						 query.setParameter("indEmailNotifyRequire", indEmailNotifyRequire);
						 query.setParameter("buyerEmailId", manageRuleBuyerEmailId);
						 query.setParameter("buyerId", buyerId);
						 query.executeUpdate();
				    }
			
			
			
		}
		
		//SL-15642 Saving auto_accept_status in the routing_rule_vendor table during Creation of CAR rule
		@Transactional(propagation = Propagation.REQUIRED)
		public void  saveRoutingRuleVendor(String autoAcceptStatCarRuleCreation,Integer ruleId)
		{
			String hql = null;
			Query query = null;
			//If buyer has auto acceptance feature as on then set auto_accept_status as pending or if off set  auto_accept_status as NA
			 
						hql = "UPDATE  routing_rule_vendor SET  auto_accept_status=:autoAcceptStatCarRuleCreation WHERE routing_rule_hdr_id = :ruleId";
								 query = getEntityManager().createNativeQuery(hql);
								 query.setParameter("autoAcceptStatCarRuleCreation", autoAcceptStatCarRuleCreation);
								 query.setParameter("ruleId", ruleId);
								 query.executeUpdate();
				
		}
		
		//SL 15642 Method to get history of a rule for a buyer
		@Transactional(propagation = Propagation.SUPPORTS)
		public List<RoutingRuleAutoAcceptHistoryVO> getAutoAcceptHistoryBuyer(Integer ruleHdrId)
		{
			String sql = "SELECT autoHistBuyer.* FROM auto_accept_history autoHistBuyer WHERE autoHistBuyer.routing_rule_hdr_id = :ruleId ORDER BY autoHistBuyer.created_date DESC";
			Query query = getEntityManager().createNativeQuery(sql);
			query.setParameter("ruleId", ruleHdrId);
			try {
				Iterator iterator = query.getResultList().iterator();
				List<RoutingRuleAutoAcceptHistoryVO> rracHistory = new ArrayList<RoutingRuleAutoAcceptHistoryVO>();
				 if(iterator!=null) {
					 while(iterator.hasNext()) {
						 Object[] tuple = (Object[]) iterator.next();
						 RoutingRuleAutoAcceptHistoryVO autoAcceptHistory=new RoutingRuleAutoAcceptHistoryVO();
						 autoAcceptHistory.setAutoAcceptance((String)tuple[2]);
						 autoAcceptHistory.setUpdatedOn((Date)tuple[7]);
						 Integer roleId =(Integer)tuple[4];
						 String modifiedBy=(String)tuple[6];
						 String adoptedBy=(String)tuple[9];
						 String autoAcceptCurrentStatus=(String)tuple[2];
						 //Setting action as comment for buyer 
						 String userFullName="";
						 if(roleId!=null && roleId==3)
							{
							 	userFullName=getUserInformation(modifiedBy,roleId);
							 	//Show adopted by as updated by if adopted by sl admin otherwise show normal buyer admin
							 	if(null!=adoptedBy)
							 	{
							 		if(null!=userFullName)
									{
							 		autoAcceptHistory.setUpdatedBy(adoptedBy+userFullName);
									}
							 		else
							 		{
							 		autoAcceptHistory.setUpdatedBy(adoptedBy);
							 		}
							 				
							 	}
							 	else
							 	{
							 		if(null!=userFullName)
							 		{
							 		autoAcceptHistory.setUpdatedBy("Buyer"+userFullName);
							 		}
							 		else
							 		{
							 			autoAcceptHistory.setUpdatedBy("Buyer");	
							 		}
							 	}
							 	autoAcceptHistory.setComments((String)tuple[3]);
								//autoAcceptHistory.setAdoptedBy(adoptedBy+userFullName);
								
							}
							else
								if(roleId!=null &&roleId==1)
								{
									userFullName=getUserInformation(modifiedBy,roleId);
									//Show adopted by as updated by if adopted by sl admin otherwise show normal buyer admin
									if(null!=adoptedBy)
									{
										if(null!=userFullName)
										{
								 		autoAcceptHistory.setUpdatedBy(adoptedBy+userFullName);
										}
										else
										{
										autoAcceptHistory.setUpdatedBy(adoptedBy);
										}
								 	}
								 	else
								 	{
								 		autoAcceptHistory.setUpdatedBy("Provider"+userFullName);
								 	}
									//autoAcceptHistory.setAdoptedBy(adoptedBy+userFullName);
									//Setting turn off reason as comment if auto accept status is OFF for a provider
									if(autoAcceptCurrentStatus!=null && autoAcceptCurrentStatus.equalsIgnoreCase("OFF"))
									{
									autoAcceptHistory.setComments((String)tuple[8]);
									}
									//Setting action as comment if auto accept status is ON,PENDING or NA for a provider
									else
									{
									autoAcceptHistory.setComments((String)tuple[3]);	
									}
					
								}
								else if(null != roleId && 2 == roleId){
									autoAcceptHistory.setUpdatedBy("SYSTEM");
									autoAcceptHistory.setComments((String)tuple[3]);	
								}
						 rracHistory.add(autoAcceptHistory);
					 }
				}
				 return rracHistory;
				} 
			catch (NoResultException e) {
				return null;
			}
			
		}
		
		//SL 15642 Method to get history of a rule for provider
		public List<RoutingRuleAutoAcceptHistoryVO> getAutoAcceptHistoryProvider(Integer ruleHdrId, Integer vendorId){
			String hql1 = "select concat(first_name,' ',last_name) from contact c "+
							"join vendor_resource res "+
							"on c.contact_id = res.contact_id where res.user_name = hist.modified_by";
			String hql2 = "select concat(first_name,' ',last_name) from contact c "+
							"join buyer_resource b "+
							"on c.contact_id = b.contact_id where b.user_name = hist.modified_by";
			
			String sql = "select hist.*, " +
							"if(hist.role_id = 1, ("+hql1+"), if(hist.role_id = 3, ("+hql2+"), '')) as modifiedBy "+
							"from auto_accept_history hist " +
							"where hist.routing_rule_hdr_id = :ruleId " +
							"and (hist.vendor_id = :vendorId or hist.vendor_id is null) " +
							"order by hist.created_date desc";
			Query query = getEntityManager().createNativeQuery(sql);
			query.setParameter("ruleId", ruleHdrId);
			query.setParameter("vendorId", vendorId);
			
			try {
				Iterator iterator = query.getResultList().iterator();
				List<RoutingRuleAutoAcceptHistoryVO> rracHistory = new ArrayList<RoutingRuleAutoAcceptHistoryVO>();
				 if(iterator!=null) {
					 while(iterator.hasNext()) {
						 Object[] tuple = (Object[]) iterator.next();
						 RoutingRuleAutoAcceptHistoryVO autoAcceptHistory = new RoutingRuleAutoAcceptHistoryVO();
						 autoAcceptHistory.setAutoAcceptance((String)tuple[2]);
						 autoAcceptHistory.setUpdatedOn((Date)tuple[7]);
						 autoAcceptHistory.setUpdatedBy((String)tuple[11]);
						 autoAcceptHistory.setOpportunityEmailInd((Boolean)tuple[10]);
						 
						 Integer roleId = (Integer)tuple[4];
						 String adoptedBy = (String)tuple[9];
						 if(null != roleId && null == adoptedBy){
							 if(OrderConstants.PROVIDER_ROLEID == roleId){
								 autoAcceptHistory.setRole(OrderConstants.ROLE_PROVIDER);
								 autoAcceptHistory.setComments((String)tuple[8]);
							 }
							 else if(OrderConstants.BUYER_ROLEID == roleId){
								 autoAcceptHistory.setRole(OrderConstants.ROLE_BUYER);
								 autoAcceptHistory.setComments((String)tuple[3]);
							 }
							 else if(OrderConstants.NEWCO_ADMIN_ROLEID == roleId){
								 autoAcceptHistory.setRole(OrderConstants.SYSTEM_ROLE);
								 autoAcceptHistory.setComments((String)tuple[3]);
							 }
						 }	
						 else if(null != adoptedBy){
							 //if adopted, modified-by should be name of the admin
							 autoAcceptHistory.setRole(adoptedBy);
							 //Sl 18515 Bug fix for comments
							 autoAcceptHistory.setComments((String)tuple[3]);
						 }
						 rracHistory.add(autoAcceptHistory);
					 }
				}
				 return rracHistory;
			} 
			catch (NoResultException e) {
				return null;
			}
		}
		
		//SL -15642 Method to fetch saved email id for the buyer
		public String fetchSavedEmailId(Integer buyerId)
		{
			try{
				String hql1 = "SELECT email_id from routing_rule_buyer_assoc where buyer_id = :buyerId";
				Query query1 = getEntityManager().createNativeQuery(hql1);
				query1.setParameter("buyerId", buyerId);
				String buyerEmailId = (String)query1.getSingleResult();
				return buyerEmailId;
				}catch(Exception e){
					e.printStackTrace();
					return null;
				}
			
		}
		
		//SL 15642 Method to get user full name info 
		public String getUserInformation(String userName,Integer roleId)
		{
			String userFullName="";
			if(roleId==3)
			{
			String hql = "select buyer from BuyerResource buyer where buyer.user.username = :username";
			Query query = getEntityManager().createQuery(hql);
			
			query.setParameter("username", userName);
			try {
				BuyerResource buyer = (BuyerResource) query.getSingleResult();
				userFullName="("+buyer.getContact().getFirstName()+" "+buyer.getContact().getLastName()+")";
				
			} catch (NoResultException e) {
				return null;
			}
			}
			else if(roleId==1)
			{
				//Code commented to avoid Second order SQL injection
//				String hql = "SELECT c.first_name,c.last_name FROM vendor_resource vr INNER JOIN contact c ON c.contact_id=vr.contact_id WHERE vr.user_name='"+userName+"'";
//				//Query query = getEntityManager().createNativeQuery(hql);
//				Query query = getEntityManager().createNativeQuery(hql);
				
				//Code changes made to avoid Second order SQL injection
				String hql = "SELECT c.first_name,c.last_name FROM vendor_resource vr INNER JOIN contact c ON c.contact_id=vr.contact_id WHERE vr.user_name= :username";
				Query query = getEntityManager().createNativeQuery(hql);
				query.setParameter("username", userName);
				
				try {
					Object[] fullName = (Object[]) query.getSingleResult();
					String firstName=(String)fullName[0];
					String lastName=(String)fullName[1];
					userFullName="("+firstName+" "+lastName+")";
				}
					catch (NoResultException e) {
					return null;
				}
			}
			return userFullName;
		}
		
		//SL-20363 Need to Add UI for Forceful CAR Activation for buyer# 3000
		public Integer forceFulActiveRule(Map inputMap) throws Exception {

			Query q = getEntityManager()
					.createNativeQuery(
							"call sp_activateCarRule(:in_carRuleid,:in_ruleComment,:in_SL_userId)");
			//q.setParameter("in_ruleName", (String) inputMap.get("carName"));
			String carId=(String) inputMap.get("carId");
			q.setParameter("in_carRuleid", Integer.parseInt(carId)); 

			q.setParameter("in_ruleComment", (String) inputMap.get("ruleComment"));
			q.setParameter("in_SL_userId", (String) inputMap.get("modifiedBy"));
			List resultList = q.getResultList();
			logger.info("resultList is:"+resultList);
			logger.info("Sucessfully Activated Car# "
					+ (String) inputMap.get("carId"));
			return Integer.valueOf(1);
		}
		
		
		/**
		 * Check if the conflict finder is disabled for the buyer Id
		 */
		public boolean isConflictFinderDisabled(Integer buyerAssocId) throws Exception{
	    	 try{
				String hql ="SELECT COUNT(1) FROM buyer_feature_set featureSet,routing_rule_buyer_assoc assoc WHERE featureSet.buyer_id=assoc.buyer_id"+
	    	 " AND featureSet.feature='DISABLE_CONFLICT_FINDER' AND featureSet.active_ind=1 AND assoc.routing_rule_buyer_assoc_id=:buyerAssocId";
				Query query = getEntityManager().createNativeQuery(hql);
				query.setParameter("buyerAssocId", buyerAssocId);
				Number  count = (Number)query.getSingleResult();
				logger.info("count"+count);
				if(null!=count && count.intValue()>0){
					return true;
				}
				
			}catch (Exception e) {
				logger.error("Exception in the method isConflictFinderDisabled"+e);
				throw new Exception("Exception in the method isConflictFinderDisabled");
			}
		  return false;
		}
		
		
}