package com.servicelive.orderfulfillment.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.exception.BusinessServiceException;
import com.servicelive.domain.autoclose.AutoCloseRuleHdr;
import com.servicelive.domain.autoclose.SoAutoCloseAction;
import com.servicelive.domain.autoclose.SoAutoCloseRuleStatus;
import com.servicelive.domain.lookup.LookupServiceType;
import com.servicelive.domain.pendingcancel.PendingCancelHistory;
import com.servicelive.domain.so.BuyerOrderRetailPrice;
import com.servicelive.domain.so.BuyerOrderSpecialtyAddOn;
import com.servicelive.domain.so.BuyerSkuAddon;
import com.servicelive.domain.so.BuyerSkuGroups;
import com.servicelive.domain.so.SORoutingRuleAssoc;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.marketplatform.common.vo.ProviderIdVO;
import com.servicelive.marketplatform.common.vo.SPNetHdrVO;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.ClientInvoiceOrder;
import com.servicelive.orderfulfillment.domain.RoutedProvider;
import com.servicelive.orderfulfillment.domain.SOAdditionalPaymentHistory;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOGroup;
import com.servicelive.orderfulfillment.domain.SOLogging;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.SOOnSiteVisit;
import com.servicelive.orderfulfillment.domain.SOSchedule;
import com.servicelive.orderfulfillment.domain.SOLocation;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.TierRouteProviders;
import com.servicelive.orderfulfillment.domain.type.LocationType;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.vo.BuyerCallBackNotificationVO;
import com.servicelive.orderfulfillment.vo.InvoicePartsPricingVO;
import com.servicelive.orderfulfillment.vo.OutboundNotificationVO;

public class ServiceOrderDao implements IServiceOrderDao {

	protected final Logger logger = Logger.getLogger(getClass());

	@PersistenceContext()
	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	public ServiceOrder getServiceOrder(String soId){
		return em.find(ServiceOrder.class, soId);
	}

	public void save(SOElement element ) {
		em.persist(element);
	}

	public void update(SOElement element){
		logger.info("Before merging of info");
		logger.info("Before merger resource id");
		em.merge(element);
	}

	public void delete(SOElement element ) {
		em.remove(element);
	}

	//new
	public void deleteSoRoutedProviders(String soId){
		String hql = "DELETE srp.* FROM so_routed_providers srp WHERE srp.so_id='"+soId+"'";
		logger.info("Query for delete SRP"+hql);
		Query query = em.createNativeQuery(hql);

		try {
			query.executeUpdate();
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
		}
	}

	public void deleteSoCounterOffers(String soId){
		String hql = "DELETE soc.* FROM so_providers_counter_offer_reasons soc WHERE soc.so_id='"+soId+"'";
		logger.info("Query for delete SOC"+hql);
		Query query = em.createNativeQuery(hql);

		try {
			query.executeUpdate();
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
		}
	}

	public void deleteTierRoutedProviders(String soId){
		String hql = "DELETE srp.* FROM tier_route_eligilble_providers srp WHERE srp.so_id='"+soId+"'";
		logger.info("Query for delete"+hql);
		Query query = em.createNativeQuery(hql);

		try {
			query.executeUpdate();
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
		}
	}

	public void deleteNotEligibleProviders(Integer vendorId,String soId){
		String hql = "DELETE FROM so_routed_providers  WHERE so_id='"+soId+"'"+" AND vendor_id !="+vendorId+"";
		logger.info("Query for delete"+hql);
		Query query = em.createNativeQuery(hql);

		try {
			query.executeUpdate();
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
		}
	}

	public List<Integer> findRanks(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers){
		logger.info("Inside ServiceOrderDao..noOfProvInCurentTier="+noOfProvInCurentTier+" & noOfProvInPreviousTiers="+noOfProvInPreviousTiers+"SOID>>"+soId);
		long start = System.currentTimeMillis();
		String hql = "SELECT * FROM tier_route_eligilble_providers trp"
				+ " WHERE trp.so_id='"+soId+"'"
				+ " GROUP BY rank"
				+ " ORDER BY rank ASC"
				+ " LIMIT "+noOfProvInCurentTier+" OFFSET "+noOfProvInPreviousTiers;
		logger.info("Generated query string::"+hql);
		Query query = em.createNativeQuery(hql);
		try {
			Iterator iterator = query.getResultList().iterator();
			List<Integer> perfScores =  new ArrayList<Integer>();
			if(iterator!=null) {
				while(iterator.hasNext()) {
					Object[] tuple = (Object[]) iterator.next();
					perfScores.add(((Integer)tuple[10]));					 
				}
			}
			long end = System.currentTimeMillis();
			logger.info("Time taken to find ranks>>"+(end-start));
			return perfScores;
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<TierRouteProviders> findProvidersForTierRouting(String soId,Integer noOfProvInCurentTier,Integer noOfProvInPreviousTiers,List<Integer> perfScores){
		logger.info("Inside findProvidersForTierRouting for so>>"+soId);
		long start = System.currentTimeMillis();
		String hql = "";
		if(null == perfScores){
			logger.info("Inside IF");
			hql = "SELECT * FROM tier_route_eligilble_providers trp"
					+ " WHERE trp.so_id='"+soId+"'"
					+ " ORDER BY rank ASC"
					+ " LIMIT "+noOfProvInCurentTier+" OFFSET "+noOfProvInPreviousTiers;
		}else{
			logger.info("Inside ELSE");
			StringBuffer respIdList = new StringBuffer("");

			int count = 0;

			for(Integer id : perfScores){
				respIdList = respIdList.append(id);
				count++;
				if (count<perfScores.size()){
					respIdList = respIdList.append(", ");
				}
			}
			String perfScoresList = respIdList.toString();

			hql = "SELECT * FROM tier_route_eligilble_providers trp"
					+ " WHERE trp.so_id='"+soId+"'"
					+ " AND trp.rank "
					+ " IN ("+ perfScoresList +")";
		}

		logger.info("Generated query string::"+hql);
		Query query = em.createNativeQuery(hql);
		try {
			List<TierRouteProviders> providerList = new ArrayList<TierRouteProviders>();
			if(null!=query.getResultList()&& query.getResultList().size()>0){
				Iterator iterator = query.getResultList().iterator();
				if(iterator!=null) {
					logger.info("not null");
					while(iterator.hasNext()) {
						Object[] tuple = (Object[]) iterator.next();
						TierRouteProviders trp = new TierRouteProviders();
						trp.setProviderResourceId((Integer)tuple[1]);
						trp.setVendorId((Integer)tuple[2]);
						if(null!=tuple[5]){
							trp.setPerformanceScore(((BigDecimal)tuple[5]).doubleValue());
						}
						if(null!=tuple[6]){
							trp.setFirmPerformanceScore(((BigDecimal)tuple[6]).doubleValue());
						}
						providerList.add(trp);					 
					}
				}

			}
			long end = System.currentTimeMillis();
			logger.info("Time taken to findProvidersForTierRouting>>"+(end-start)+">>soId>>"+soId);
			return providerList;
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public List<ProviderIdVO> getCompletedDate(List<Integer> duplicateList, String criteriaLevel, Integer buyerId){
		List<ProviderIdVO> dateList = new ArrayList<ProviderIdVO>();
		//Date completedDate = null;
		Date date = new Date(2000, 01, 01);
		//String hql="";
		logger.info("Inside completed date::criteriaLevel"+criteriaLevel);
		try{
			if(null != criteriaLevel && criteriaLevel.equals("FIRM")){
				for(Integer firmId : duplicateList){
					/*hql = "SELECT * FROM so_hdr" 
						 +" WHERE accepted_vendor_id ="+firmId+" and buyer_id ="+buyerId+""
						 +" and wf_state_id >= 160"
						 +" order by completed_date desc limit 1";
					logger.info("Generated query string::"+hql);
					Query query = em.createNativeQuery(hql,ServiceOrder.class);
					ServiceOrder order = (ServiceOrder)query.getSingleResult();*/

					Query query = em.createQuery("FROM ServiceOrder so WHERE so.acceptedProviderId=? AND so.buyerId=? AND so.wfStateId >= 160 ORDER BY so.completedDate DESC");
					query.setParameter(1, firmId.longValue());
					query.setParameter(2, buyerId.longValue());
					query.setMaxResults(1);
					ServiceOrder hdr = (ServiceOrder) query.getSingleResult();
					logger.info("RESULT::ACCEPTED RESOURCE_"+hdr.getAcceptedResource());
					ProviderIdVO rp = new ProviderIdVO();
					rp.setVendorId(hdr.getAcceptedProviderId().intValue());
					if(null != hdr.getCompletedDate()){
						rp.setCompletedDate(hdr.getCompletedDate());
					}else{
						rp.setCompletedDate(date);
					}
					dateList.add(rp);
				}
			}
			else{
				for(Integer provId : duplicateList){
					/*hql = "SELECT completed_date" 
						 +" FROM so_hdr" 
						 +" WHERE accepted_resource_id ="+provId+" and buyer_id ="+buyerId+""
						 +" and wf_state_id >= 160"
						 +" order by completed_date desc limit 1";
					Query query = em.createNativeQuery(hql,ServiceOrder.class);
					ServiceOrder order = (ServiceOrder)query.getSingleResult();*/
					Query query = em.createQuery("FROM ServiceOrder so WHERE so.acceptedProviderResourceId=? AND so.buyerId=? AND so.wfStateId >= 160 ORDER BY so.completedDate DESC");
					query.setParameter(1, provId.longValue());
					query.setParameter(2, buyerId.longValue());
					query.setMaxResults(1);
					ServiceOrder hdr = (ServiceOrder) query.getSingleResult();
					logger.info("RESULT::ACCEPTED RESOURCE ID_"+hdr.getAcceptedProviderResourceId());

					ProviderIdVO rp = new ProviderIdVO();
					rp.setResourceId(hdr.getAcceptedProviderResourceId().intValue());
					if(null != hdr.getCompletedDate()){
						rp.setCompletedDate(hdr.getCompletedDate());
					}else{
						rp.setCompletedDate(date);
					}
					dateList.add(rp);
				}
			}
			for(ProviderIdVO dList:dateList){
				logger.info("resourceId :"+dList.getResourceId());
				logger.info("date :"+dList.getCompletedDate());
			}

		}catch(Exception e){
			logger.error("Exception in ServiceorderDaoImpl.getCompletedDate() due to "+ e);
		}
		return dateList;
	}

	// new method
	@SuppressWarnings("deprecation")
	public List<ProviderIdVO> getCompletedDateForSo(List<Integer> duplicateList, String criteriaLevel, Integer buyerId){
		List<ProviderIdVO> dateList = new ArrayList<ProviderIdVO>();
		//Date completedDate = null;
		Date date = new Date(2000, 01, 01);
		//String hql="";
		logger.info("Inside completed date::criteriaLevel"+criteriaLevel);
		try{
			if(null != criteriaLevel && criteriaLevel.equals("FIRM")){
				String hql ="SELECT accepted_vendor_id,MAX(completed_date)  FROM so_hdr WHERE accepted_vendor_id IN (:providerIds)"
						+" AND buyer_id= :buyerId AND wf_state_id>=160  GROUP BY accepted_vendor_id  ";
				Query query = em.createNativeQuery(hql);
				query.setParameter("buyerId", buyerId);
				query.setParameter("providerIds",duplicateList);
				Iterator hdrIterator = query.getResultList().iterator();
				List<Map<Integer, Date>> providerList= new ArrayList<Map<Integer, Date>>();
				while ( hdrIterator.hasNext() ) {
					Map<Integer, String> providerMap = new HashMap<Integer, String>();
					Object[] tuple = (Object[]) hdrIterator.next();
					Integer vendorId = (Integer) tuple[0];

					ProviderIdVO provider=new ProviderIdVO();
					provider.setVendorId(vendorId);
					if(null!=tuple[1]){
						Date  completionDate = (Date) tuple[1];
						provider.setCompletedDate(completionDate);
					}
					else
					{
						provider.setCompletedDate(date);
					}
					dateList.add(provider);
				}
			}	
			else{
				String hql ="SELECT accepted_resource_id,MAX(completed_date)  FROM so_hdr WHERE accepted_resource_id IN (:providerIds)"
						+" AND buyer_id= :buyerId AND wf_state_id>=160  GROUP BY accepted_resource_id  ";
				Query query = em.createNativeQuery(hql);
				query.setParameter("buyerId", buyerId);
				query.setParameter("providerIds",duplicateList);
				Iterator hdrIterator = query.getResultList().iterator();
				List<Map<Integer, Date>> providerList= new ArrayList<Map<Integer, Date>>();
				while ( hdrIterator.hasNext() ) {
					Map<Integer, String> providerMap = new HashMap<Integer, String>();
					Object[] tuple = (Object[]) hdrIterator.next();
					Integer resourceId = (Integer) tuple[0];

					ProviderIdVO provider=new ProviderIdVO();
					provider.setResourceId(resourceId);
					if(null!=tuple[1]){
						Date  completionDate = (Date) tuple[1];
						provider.setCompletedDate(completionDate);
					}
					else
					{
						provider.setCompletedDate(date);

					}
					dateList.add(provider);
				}
			}
			for(ProviderIdVO dList:dateList){
				logger.info("resourceId :"+dList.getResourceId());
				logger.info("date :"+dList.getCompletedDate());
			}

		}
		catch(Exception e){
			logger.error("Exception in ServiceorderDaoImpl.getCompletedDateForSo() due to "+ e);
		}
		return dateList;
	}

	public SPNHeader fetchSpnDetails(Integer spnId){
		logger.info("Inside fetchSpnDetails>>"+spnId);
		long start = System.currentTimeMillis();
		Query query = em.createQuery("FROM SPNHeader spn WHERE spn.spnId=?");
		query.setParameter(1, spnId);
		SPNHeader hdr = (SPNHeader) query.getSingleResult();
		long end = System.currentTimeMillis();
		logger.info("Time taken to fetchSpnDetails"+(end -start));
		return hdr;
	}

	public SPNetHdrVO fetchSpnInfo(Integer spnId){
		logger.info("Inside fetchSpnInfo>>"+spnId);
		long start = System.currentTimeMillis();
		SPNetHdrVO hdr = new SPNetHdrVO();
		/*String hql1 ="SELECT accepted_vendor_id,MAX(completed_date)  FROM so_hdr WHERE accepted_vendor_id IN (:providerIds)"
			 +" AND buyer_id= :buyerId AND wf_state_id>=160  GROUP BY accepted_vendor_id  ";*/
		String hql = "SELECT spn_name, CAST(performance_criteria_level AS CHAR(100)), mp_overflow, CAST(routing_priority_status AS CHAR(100)) FROM spnet_hdr WHERE spn_id = :spnId";
		logger.info("Generated string for fetchSpnInfo>> "+hql);
		Query query = em.createNativeQuery(hql);
		query.setParameter("spnId", spnId);
		query.setMaxResults(1);
		Object[] obj = (Object[]) query.getSingleResult();
		if(null!=obj){
			logger.info("spn name"+obj[0]);
			logger.info("criteria level"+obj[1]);
			logger.info("mp of"+obj[2]);
			logger.info("status"+obj[3]);

			hdr.setSpnName((String)obj[0]);
			hdr.setPerfCriteriaLevel((String)obj[1]);
			Integer mapOverflow=(Integer)obj[2];
			if(mapOverflow.intValue()==1){
				hdr.setMpOverFlow(true);	
			}
			else
			{
				hdr.setMpOverFlow(false);	

			}
			hdr.setPriorityStatus((String)obj[3]);
		}
		return hdr;
	}

	@SuppressWarnings("unchecked")
	public List<ServiceOrder> fetchChildOrders(String groupId){
		Query query = em.createQuery("FROM ServiceOrder so WHERE so.soGroup.groupId=?");
		query.setParameter(1, groupId);
		List<ServiceOrder> childOrderList = (List<ServiceOrder>) query.getResultList();
		logger.info("Inside ServiceOrderDao>>childOrderList>>"+childOrderList.size());
		return childOrderList;
	}

	public void refresh(SOElement element){
		em.refresh(element);
	}
	public SOLogging getRescheduleRequestLog(ServiceOrder so) {
		Query querySOLog = em.createQuery("FROM SOLogging log WHERE log.serviceOrder.soId=? and actionId = 36 ORDER BY createdDate DESC");
		querySOLog.setParameter(1, so.getSoId());
		return (SOLogging) querySOLog.getResultList().get(0);
	}

	public List<SOLogging> getServiceOrderLog(String soId) {
		Query querySOLog = em.createQuery("FROM SOLogging log WHERE log.serviceOrder.soId=?");
		querySOLog.setParameter(1, soId);
		@SuppressWarnings("unchecked")
		List qryResult = querySOLog.getResultList();
		if (qryResult == null) return null;
		List<SOLogging> soLogList = new ArrayList<SOLogging>();
		for (Object soLog : qryResult) {
			soLogList.add((SOLogging) soLog);
		}
		return soLogList;
	}

	public <T extends SOElement> T getElement(Class<T> clazz, Object primaryKey){
		return em.find(clazz, primaryKey);
	}

	public SOGroup getSOGroup (String groupId){
		return em.find(SOGroup.class, groupId);
	}

	public List<BuyerOrderSpecialtyAddOn> getSpecialtyAddOnListBySpecialtyAndDivision(final String specialtyCode, final String divisionCode) {
		String queryString =
				"SELECT specialtyAddOn FROM BuyerOrderSpecialtyAddOn specialtyAddOn "
						+ "WHERE"
						+ "("
						+ " (specialtyAddOn.specialtyCode = :specialtyCode AND classificationCode = 'A') "
						+ " OR (specialtyAddOn.specialtyCode = :specialtyCode AND classificationCode = 'M' AND stockNumber LIKE :divisionCode)"
						+ ")"
						+ "AND specialtyAddOn.coverage != 'CC' ";
		Query q = em.createQuery(queryString);
		q.setParameter("specialtyCode", specialtyCode);
		q.setParameter("divisionCode", "%"+divisionCode);

		@SuppressWarnings("unchecked")
		List<BuyerOrderSpecialtyAddOn> resultList = (List<BuyerOrderSpecialtyAddOn>) q.getResultList();
		return resultList;

	}

	public BuyerOrderSpecialtyAddOn getCallCollectSpecialtyAddOn(final String specialtyCode, final String stockNumber) {
		final String queryString =
				"SELECT specialtyAddOn FROM BuyerOrderSpecialtyAddOn specialtyAddOn "
						+ "WHERE specialtyAddOn.specialtyCode = :specialtyCode "
						+ "AND stockNumber = :stockNumber AND coverage =  'CC' ";
		Query q = em.createQuery(queryString);
		q.setParameter("specialtyCode", specialtyCode);
		q.setParameter("stockNumber", stockNumber);

		@SuppressWarnings("unchecked")
		List<BuyerOrderSpecialtyAddOn> resultList = (List<BuyerOrderSpecialtyAddOn>) q.getResultList();
		if (resultList==null || resultList.size()==0) return null;
		return resultList.get(0);
	}


	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.dao.IBuyerRetailPriceDAO#findByStoreNoAndSKU(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public BuyerOrderRetailPrice findByStoreNoAndSKU(final String storeNo, final String sku, final Long buyerID,final String defaultStroreNo) throws Exception{
		try {
			final String hql = "select model from BuyerOrderRetailPrice model where model.sku = :sku and model.storeNumberString in (:storeNo,:defaultStroreNo) and model.buyerId = :buyerID";


			Query query = em.createQuery(hql);
			query.setParameter("sku", sku);
			query.setParameter("storeNo", storeNo);
			query.setParameter("defaultStroreNo", defaultStroreNo);
			query.setParameter("buyerID", buyerID);
			//There might be two records in the following order, 1. result with storeNo passed in 2. result with default store no.Always get the first result. 
			return (BuyerOrderRetailPrice)query.getResultList().get(0);	


		} catch (Exception re) {
			logger.error("findByStoreNoAndSKU failed SKU: "+sku+" store: "+storeNo+" buyerID: "+buyerID);
			throw re;
		}
	}


	public List<Integer> autoCloseProviderExclusionList(final Long buyerId)
	{
		
		final String queryString = "SELECT provider_id FROM auto_close_rule_provider_assoc WHERE  active_ind = :indicator AND auto_close_rule_hdr_id =(SELECT auto_close_rule_hdr_id FROM auto_close_rule_hdr WHERE buyer_id=:buyerId AND auto_close_rule_id =:id);";
		

		Query q = em.createNativeQuery(queryString);
		q.setParameter("buyerId", buyerId);
		q.setParameter("id", new Integer(6));
		boolean indicator=false;
		q.setParameter("indicator",indicator);
		@SuppressWarnings("unchecked")
		List<Integer> resultList = (List<Integer>) q.getResultList();
		if (resultList==null || resultList.size()==0) return null;
		return resultList;
	}

	
	
	public List<String> autoCloseCriteriaValue(final Long buyerId)
	{
		final String queryString = "select criteria_value from auto_close_rule_criteria where auto_close_rule_hdr_id =(select auto_close_rule_hdr_id from auto_close_rule_hdr where buyer_id=:buyerId and auto_close_rule_id =:id);";
		Query q = em.createNativeQuery(queryString);
		q.setParameter("buyerId", buyerId);
		q.setParameter("id", new Integer(4));
		@SuppressWarnings("unchecked")
		List<String> resultList = (List<String>) q.getResultList();
		if (resultList==null || resultList.size()==0) return null;
		return resultList;
	}

	public List<Integer> autoCloseFirmExclusionList(final Long buyerId)
	{
		
		final String queryString = "SELECT firm_id FROM auto_close_rule_firm_assoc WHERE  active_ind = :indicator AND auto_close_rule_hdr_id =(SELECT auto_close_rule_hdr_id FROM auto_close_rule_hdr WHERE buyer_id=:buyerId AND auto_close_rule_id =:id);";
		

		Query q = em.createNativeQuery(queryString);
		q.setParameter("buyerId", buyerId);
		q.setParameter("id", new Integer(5));
		boolean indicator=false;
		q.setParameter("indicator",indicator);

		@SuppressWarnings("unchecked")
		List<Integer> resultList = (List<Integer>) q.getResultList();
		if (resultList==null || resultList.size()==0) return null;
		return resultList;
	}


	public void autoCloseUpdation(String soId,String autoCloseStatus)
	{
		SoAutoCloseAction soAutoCloseAction=new SoAutoCloseAction();
		soAutoCloseAction.setSoId(soId);
		soAutoCloseAction.setAutoCloseStatus(autoCloseStatus);
		soAutoCloseAction.setAutoCloseDate(null);

		em.merge(soAutoCloseAction);


	}



	public void withdrawPendingCancel(String soId,String userType){

		String hql="UPDATE pending_cancel_history  SET withdraw_flag=1 WHERE user_type='"+userType+"' AND so_id='"+soId+"' ORDER BY created_date DESC LIMIT 1";
		Query query = em.createNativeQuery(hql);

		try {
			query.executeUpdate();
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
		}
	}

	// update the provider invoice parts prices during completion
	public void updateProviderInvoicePartsPricing(InvoicePartsPricingVO invoicePartsPricingVO){

		String hql="UPDATE so_price  SET invoice_parts_retail_price="+invoicePartsPricingVO.getRetailPrice()+",invoice_parts_retail_reimbursement="+
				invoicePartsPricingVO.getRetailReimbursement()+",invoice_parts_retail_sl_gross_up="+invoicePartsPricingVO.getRetailSlGrossUp()+
				",invoice_parts_final_price="+invoicePartsPricingVO.getFinalPartsPrice()+" WHERE so_id='"+invoicePartsPricingVO.getSoId()+"'";

		Query query = em.createNativeQuery(hql);

		try {
			query.executeUpdate();
		}
		catch (Exception e) {
			logger.info("error in updating the provider invoice parts prices during completion "+e);
		}
	}



	public void pendingCancelUpdation(PendingCancelHistory pendingCancelHistory)

	{
		em.merge(pendingCancelHistory);
	}

	public PendingCancelHistory getPendingCancelCriteria(Integer roleId,String soId){
		final String queryString = "SELECT pendingCancelHistory FROM PendingCancelHistory pendingCancelHistory "
				+ "WHERE pendingCancelHistory.soId = :soId "
				+ "AND pendingCancelHistory.roleId = :roleId ORDER BY pendingCancelHistory.pendingCancelHistoryId DESC ";
		Query q = em.createQuery(queryString);
		q.setParameter("soId", soId);
		q.setParameter("roleId", roleId);
		q.setFirstResult(1);
		q.setMaxResults(1);
		PendingCancelHistory pendingCancelHistory = null;
		try {
			pendingCancelHistory = (PendingCancelHistory) q.getSingleResult();
		} catch (NoResultException e) {

		}
		return pendingCancelHistory;

	}

	public PendingCancelHistory getPendingCancelHistory(Integer roleId,String soId){
		final String queryString = "SELECT pendingCancelHistory FROM PendingCancelHistory pendingCancelHistory "
				+ "WHERE pendingCancelHistory.soId = :soId "
				+ "AND pendingCancelHistory.roleId = :roleId ORDER BY pendingCancelHistory.pendingCancelHistoryId DESC ";
		Query q = em.createQuery(queryString);
		q.setParameter("soId", soId);
		q.setParameter("roleId", roleId);
		q.setFirstResult(0);
		q.setMaxResults(1);
		PendingCancelHistory pendingCancelHistory = null;
		try {
			pendingCancelHistory = (PendingCancelHistory) q.getSingleResult();
		} catch (NoResultException e) {

		}
		return pendingCancelHistory;

	}

	public void clearPendingCancelHist(String soId) {
		final String queryString = "DELETE FROM PendingCancelHistory p WHERE p.soId = :soId";
		Query query = em.createQuery(queryString);
		query.setParameter("soId", soId);
		try {
			query.executeUpdate();
		} catch (NoResultException e) {}

	}


	
	
	public void autoCloseRuleUpdation(Integer autoCloseId,List<String> autoCloseRuleStatus,String ruleValue,final Long  buyerId)
	{

		SoAutoCloseAction soAutoCloseAction=em.find(SoAutoCloseAction.class, autoCloseId);
		for(int k=0;k<autoCloseRuleStatus.size();k++)
		{
			SoAutoCloseRuleStatus soAutoCloseRuleStatus=new SoAutoCloseRuleStatus();
			soAutoCloseRuleStatus.setSoAutoCloseAction(soAutoCloseAction);
			if (buyerId.toString().equals("1953")){
				
				soAutoCloseRuleStatus.setAutoCloseRuleHdrId(new Integer(k+10));
				soAutoCloseRuleStatus.setAutoCloseRuleStatus(autoCloseRuleStatus.get(k));
				if(k==2)
				{
					soAutoCloseRuleStatus.setAutoCloseRuleValue(ruleValue);	
				}
				else
				{
					soAutoCloseRuleStatus.setAutoCloseRuleValue("0");
				}
			}else{
				
			
			soAutoCloseRuleStatus.setAutoCloseRuleHdrId(new Integer(k+1));
			soAutoCloseRuleStatus.setAutoCloseRuleStatus(autoCloseRuleStatus.get(k));
			if(k==3)
			{
				soAutoCloseRuleStatus.setAutoCloseRuleValue(ruleValue);	
			}
			else
			{
				soAutoCloseRuleStatus.setAutoCloseRuleValue("0");
			}
			
			}
			em.merge(soAutoCloseRuleStatus);
		}

	}
	
	public Integer getAutoCloseId(String soId)
	{
		final String queryString = "SELECT MAX(autoClose.autoCloseId) FROM SoAutoCloseAction autoClose "
				+ "WHERE autoClose.soId = :soId ";

		Query q = em.createQuery(queryString);
		q.setParameter("soId", soId);
		Integer autoCloseId=null;

		try {
			autoCloseId = (Integer) q.getSingleResult();
		} catch (NoResultException e) {}

		return autoCloseId;

	}

	public BuyerOrderSpecialtyAddOn getSpecialtyAddOn(final String specialtyCode, final String stockNumber) {
		final String queryString = "SELECT specialtyAddOn FROM BuyerOrderSpecialtyAddOn specialtyAddOn "
				+ "WHERE specialtyAddOn.stockNumber = :stockNumber "
				+ "AND specialtyAddOn.specialtyCode = :specialtyCode ";
		Query q = em.createQuery(queryString);
		q.setParameter("stockNumber", stockNumber);
		q.setParameter("specialtyCode", specialtyCode);
		BuyerOrderSpecialtyAddOn buyerOrderSpecialtyAddOn = null;
		try {
			buyerOrderSpecialtyAddOn = (BuyerOrderSpecialtyAddOn) q.getSingleResult();
		} catch (NoResultException e) {}
		return buyerOrderSpecialtyAddOn;
	}



	public LookupServiceType getServiceTypeTemplate(Integer serviceTypeTemplateId) {
		return em.find(LookupServiceType.class, serviceTypeTemplateId);
	}

	public LookupServiceType getServiceTypeTemplate(Integer primaryNodeId, String description) {
		final String queryString = "SELECT lookupServiceType FROM LookupServiceType lookupServiceType "
				+ "WHERE lookupServiceType.skillNodeId = :primaryNodeId "
				+ "AND lookupServiceType.description = :description ";
		Query q = em.createQuery(queryString);
		q.setParameter("primaryNodeId", primaryNodeId);
		q.setParameter("description", description);
		return (LookupServiceType) q.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<LookupServiceType> getServiceTypeTemplates() {
		Query q = em.createQuery("SELECT lkupSvcTyp FROM LookupServiceType lkupSvcTyp");
		return (List<LookupServiceType>) q.getResultList();
	}

	public SOOnSiteVisit getIVRDetails(String soId) {
		Query q = em.createNativeQuery("SELECT visit_id FROM so_onsite_visit WHERE so_id='"+soId+"'").setFirstResult(0).setMaxResults(1);

		Long visitId = null;
		try{
			visitId = ((BigInteger)q.getSingleResult()).longValue();   	
		}catch(NoResultException e){}

		if(null != visitId){
			return em.find(SOOnSiteVisit.class, visitId.longValue());
		}
		else{
			return null;
		}
	}    

	public List<ServiceOrder> getPendingServiceOrders() {

		Query q = em.createQuery("FROM ServiceOrder s WHERE s.wfSubStatusId = 64 AND s.serviceOrderProcess.jmsMessageId IS NOT NULL");

		@SuppressWarnings("unchecked")
		List<ServiceOrder>resultList = (List<ServiceOrder>)q.getResultList();
		return resultList;
	}

	public ServiceOrder findServiceOrderByCustomReference(Long buyerId, Integer customRefTypeId, String customRefValue, String soId){
		Query q = em.createQuery("Select s FROM ServiceOrder s join s.customReferences cr where s.soId <> :soId and s.buyerId = :buyerId " +
				"and cr.buyerRefTypeId = :customRefTypeId and cr.buyerRefValue = :customRefValue Order by s.createdDate desc");
		q.setParameter("soId", soId);
		q.setParameter("buyerId", buyerId);
		q.setParameter("customRefTypeId", customRefTypeId);
		q.setParameter("customRefValue", customRefValue);
		@SuppressWarnings("unchecked")
		List<ServiceOrder> serviceOrders = q.getResultList();
		if(null != serviceOrders && serviceOrders.size() > 0)
			return serviceOrders.get(0);
		else
			return null;
	}

	public void saveSOLocationGIS(Integer locationId) {
		Query q = em.createNativeQuery("call sp_createSoLocationGIS(:locationId)");
		q.setParameter("locationId", locationId);
		q.executeUpdate();        
	}

	public void saveClientInvoice(ClientInvoiceOrder invoice ) {
		em.persist(invoice);
	}  


	public ClientInvoiceOrder getClientInvoiceForIncident(String incidentId, Integer skuId)
	{
		Query q = em.createQuery("Select c from ClientInvoiceOrder c where c.clientIncidentId  = :incidentId and c.skuId = :skuId Order by c.createdDate desc LIMIT 1");
		q.setParameter("incidentId", incidentId);
		q.setParameter("skuId", skuId);
		ClientInvoiceOrder clientInvoiceOrder = (ClientInvoiceOrder)q.getSingleResult();
		return clientInvoiceOrder;
	}

	/**
	 * Retrieves template id for a sku
	 */
	public Integer getTemplateId(String sku,Integer buyerId){

		Query q = em.createQuery("Select bos.templateId from BuyerOrderSku bos where bos.buyerId = :buyer and bos.sku = :skuId ");
		q.setParameter("buyer", buyerId);
		q.setParameter("skuId", sku);
		Integer templateId = 0;
		try{
			templateId = (Integer) q.getSingleResult();
		}catch(NoResultException e){
			logger.error("ServiceOrderDao.getTemplateId:error while retrieving template id");
		}
		return templateId;
	}

	/**
	 * Get template data using template name
	 */
	public String getTemplateData(String templateName,Integer buyerId){		
		Query q = em.createQuery("Select bos.templateXmlData from BuyerOrderTemplateRecord bos where bos.templateName =:templateName and bos.buyerID =:buyer ");
		q.setParameter("templateId", templateName);
		q.setParameter("buyer", buyerId);
		String xmlData = "";
		try{
			xmlData = (String) q.getSingleResult();
		}catch(NoResultException e){
			logger.error("ServiceOrderDao.getTemplateData:error while retrieving template data");
		}
		return xmlData;
	}

	/**
	 * Retrieve template name from template id
	 */
	public String getTemplateNameFromTemplateId(Integer templateId){
		Query q = em.createQuery("Select bot.templateName from BuyerOrderTemplateRecord bot where bot.templateID =:templateId");
		q.setParameter("templateId", templateId);
		String templateName = "";
		try{
			templateName = (String) q.getSingleResult();
		}catch(NoResultException e){
			logger.error("ServiceOrderDao.getTemplateNameFromTemplateId:error while retrieving template name");
		}
		return templateName;
	}

	@SuppressWarnings("unchecked")
	public List<SONote> getNote(ServiceOrder serviceOrder, String note) {

		Query q = em.createQuery("Select s from SONote s where s.serviceOrder = :order and s.note = :note");
		q.setParameter("order", serviceOrder);
		q.setParameter("note", note);
		List<SONote> soNote = (List<SONote>)q.getResultList();
		return soNote;
	}
	@SuppressWarnings("unchecked")
	public List<SOLogging> getLog(ServiceOrder serviceOrder, String log) {

		Query q = em.createQuery("Select s from SOLogging s where s.serviceOrder = :order and s.chgComment = :comment");
		q.setParameter("order", serviceOrder);
		q.setParameter("comment", log);
		List<SOLogging> soLog = (List<SOLogging>)q.getResultList();
		return soLog;
	}

	@SuppressWarnings("unchecked")
	public Double getCancelAmount(Integer buyerId) {

		String hql = "SELECT cancellation_fee FROM buyer WHERE buyer_id='"+buyerId+"'";
		Query q = em.createNativeQuery(hql);		
		Double cancelFee =(Double)q.getSingleResult();
		return cancelFee;
	}

	//SL 15642 Method to get conditional Routing feature of the buyer
	@SuppressWarnings("unchecked")
	public boolean getCarFeatureOfBuyer(Integer buyerId)
	{
		try{
			String	hql = "SELECT COUNT(feature) FROM buyer_feature_set WHERE buyer_id ='"+buyerId+"' AND feature ='CONDITIONAL_ROUTE' and active_ind =1";
			Query q = em.createNativeQuery(hql);	
			Number  retValue =(Number)q.getSingleResult();
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
			return false;
		}
	}

	//SL 15642 Method to get auto Accept feature of the buyer
	public boolean getAutoAcceptFeatureOfBuyer(Integer buyerId)
	{
		try{
			String	hql = "SELECT COUNT(feature) FROM buyer_feature_set WHERE buyer_id ='"+buyerId+"' AND feature ='AUTO_ACCEPTANCE' and active_ind =1";
			Query q = em.createNativeQuery(hql);	
			Number  retValue =(Number)q.getSingleResult();
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
			return false;
		}
	}

	//SL 15642 Method to enter type of acceptance in so_workflow_controls table on associating a rule id with so
	@Transactional(propagation=Propagation.SUPPORTS)
	public void setMethodOfAcceptanceOfSo(String soId,String methodOfAcceptance)
	{
		logger.info("Inside setMethodOfAcceptanceOfSo with so id and methodOfAcceptance"+soId+methodOfAcceptance);
		String hql = null;
		Query query = null;
		try{
			hql = "SELECT COUNT(so_id) FROM so_workflow_controls WHERE so_id ='"+soId+"'";
			logger.info("Hql query for method of acceptance"+hql);
			query = em.createNativeQuery(hql);
			BigInteger  retValue =(BigInteger)query.getSingleResult();
			logger.info("Total number of entry in wfm table"+retValue);
			if(retValue.intValue()==0)
			{
				//Inserting a new entry in  so_workflow_controls for a particular so id 
				logger.info("inside the value with 0"+retValue.intValue());
				hql = "INSERT INTO so_workflow_controls(so_id,method_of_acceptance) VALUES(:soId,:methodOfAcceptance)";
				logger.info("Insert query ret value=0 "+hql);
				query = em.createNativeQuery(hql);
				query.setParameter("soId", soId);
				query.setParameter("methodOfAcceptance",methodOfAcceptance);
				query.executeUpdate();
				logger.info("insertion into wfm with zero entry");
			}
			else
			{
				//Updating a already existing entry in  so_workflow_controls for a particular so id 
				logger.info("inside the value with not 0"+retValue.intValue());
				hql = "UPDATE  so_workflow_controls SET  method_of_acceptance=:methodOfAcceptance WHERE so_id = :soId";
				logger.info("Insert query ret value zero"+hql);
				query = em.createNativeQuery(hql);
				query.setParameter("methodOfAcceptance",methodOfAcceptance);
				query.setParameter("soId", soId);
				query.executeUpdate();
				logger.info("insertion into wfm with more than zero entry");
			}
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
		}
	}


	//SL 15642 Method to enter type of routing in so_workflow_controls table on associating a rule id with so
	@Transactional(propagation=Propagation.SUPPORTS)
	public void setMethodOfRoutingOfSo(String soId,String methodOfRouting)
	{
		logger.info("Inside setMethodOfAcceptanceOfSo with so id and methodOfRouting"+soId+methodOfRouting);
		String hql = null;
		Query query = null;
		try{
			hql = "SELECT COUNT(so_id) FROM so_workflow_controls WHERE so_id ='"+soId+"'";
			query = em.createNativeQuery(hql);
			BigInteger  retValue =(BigInteger)query.getSingleResult();
			logger.info("Total number of entry in wfm table"+retValue);
			if(retValue.intValue()==0)
			{
				logger.info("inside the value with 0"+retValue.intValue());
				//Inserting a new entry in  so_workflow_controls for a particular so id 
				hql = "INSERT INTO so_workflow_controls(so_id,method_of_routing) VALUES(:soId,:methodOfRouting)";
				logger.info("Insert query ret value=0 "+hql);
				query = em.createNativeQuery(hql);
				query.setParameter("soId", soId);
				query.setParameter("methodOfRouting",methodOfRouting);
				query.executeUpdate();
				logger.info("insertion into wfm with zero entry");

			}
			else
			{
				//Updating a already existing entry in  so_workflow_controls for a particular so id 
				logger.info("inside the value with not 0"+retValue.intValue());
				hql = "UPDATE  so_workflow_controls SET  method_of_routing=:methodOfRouting WHERE so_id = :soId";
				logger.info("Insert query ret value not 0 "+hql);
				query = em.createNativeQuery(hql);
				query.setParameter("methodOfRouting",methodOfRouting);
				query.setParameter("soId", soId);
				query.executeUpdate();
				logger.info("insertion into wfm with updated  entry");

			}
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
		}
	}
	//SL 15642 Start Method to update entry into so routing rule buyer association table on finding a suitable rule
	@Transactional(propagation=Propagation.SUPPORTS)
	public void updateRuleIdForSo(String soId,Integer ruleId)
	{
		logger.info("Inside setMethodOfAcceptanceOfSo with so id and methodOfRouting"+soId+ruleId);
		String hql = null;
		Query query = null;
		String modifiedBy="System";
		try{
			hql = "SELECT COUNT(so_id) FROM so_routing_rule_assoc WHERE so_id ='"+soId+"'";
			query = em.createNativeQuery(hql);
			BigInteger  retValue =(BigInteger)query.getSingleResult();
			logger.info("Total number of entry in wfm table"+retValue);
			if(retValue.intValue()==0)
			{
				logger.info("inside the so_routing_rule_assoc value with 0"+retValue.intValue());
				//Inserting a new entry in  so_routing_rule_assoc for a particular so id 
				hql = "INSERT INTO so_routing_rule_assoc(so_id,routing_rule_hdr_id,modified_by) VALUES(:soId,:ruleId,:modifiedBy)";
				logger.info("Insert query ret value=0 "+hql);
				query = em.createNativeQuery(hql);
				query.setParameter("soId", soId);
				query.setParameter("ruleId",ruleId);
				query.setParameter("modifiedBy",modifiedBy);
				query.executeUpdate();
				logger.info("insertion into wfm with zero entry");

			}
			else
			{
				//Updating a already existing entry in  so_routing_rule_assoc for a particular so id 
				logger.info("inside the  so_routing_rule_assocvalue with not 0"+retValue.intValue());
				hql = "UPDATE  so_routing_rule_assoc SET  routing_rule_hdr_id=:ruleId WHERE so_id = :soId";
				logger.info("Insert query ret value not 0 "+hql);
				query = em.createNativeQuery(hql);
				query.setParameter("ruleId",ruleId);
				query.setParameter("soId", soId);
				query.executeUpdate();
				logger.info("insertion into wfm with updated  entry");

			}
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
		}
	}

	//SL-17781:method to fetch mandatory custom ref
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Integer> getMandatoryCustRef(Integer buyerId) {
		String hql = null;
		Query query = null;
		List<Integer>custrefList=null;
		try{
			logger.debug("Inside Dao,querying buyer_ref_type_id and buyer id is "+ buyerId);
			hql="Select buyer_ref_type_id From buyer_reference_type WHERE buyer_id ="+buyerId+" AND required=1 AND active_ind=1 " +
					"AND ref_type NOT IN('Manual_Review','RepeatRepair') AND buyer_input=1";
			query=em.createNativeQuery(hql);
			custrefList=(List<Integer>)query.getResultList();
			logger.debug("Size of mandatory Custom Ref List is"+ custrefList.size());
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
		}
		return custrefList;

	}

	public String getDefaultTemplate(String key){
		String hql = "select app_value from application_properties where app_key = :key";
		Query q = em.createNativeQuery(hql);
		q.setParameter("key", key);
		logger.info("hql::"+hql);
		String template = (String)q.getSingleResult();
		return template;
	}

	// SL-18007 to set the provider response reason while placing counter offer in the reason comment field while persisting so price details history
	@SuppressWarnings("unchecked")
	public List<String> fetchCounterOfferProviderResponseReason(List<Long> counterOfferRespIdList){

		StringBuffer respIdList = new StringBuffer("");

		int count = 0;

		for(Long id : counterOfferRespIdList){
			respIdList = respIdList.append(id);
			count++;
			if (count<counterOfferRespIdList.size()){
				respIdList = respIdList.append(", ");
			}
		}
		String cntrOffrRespIdList = respIdList.toString();
		String hql = "SELECT counter_offer_reason_desc FROM lu_so_counter_offer_reasons WHERE counter_offer_reason_id IN ( "+ cntrOffrRespIdList +" )";
		Query q = em.createNativeQuery(hql);
		List<String> counterOfferRespReasonList = (List<String>)q.getResultList();

		return counterOfferRespReasonList;
	}

	/*	SL-18162:To fetch fundingType Id from DB for Buyer instead of getting it from Cache
	 * */
	public Integer getFundingTypeIdForBuyer(Integer buyerId) {
		logger.info("Querying Buyer table for funding type id");
		Integer fundingTypeId = 0;
		String hql = "SELECT funding_type_id FROM buyer WHERE buyer_id = :buyerId";
		try {
			Query q = em.createNativeQuery(hql);
			q.setParameter("buyerId", buyerId);
			fundingTypeId = (Integer) q.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Exception in getting funding type id for buyer"
					+ buyerId + "because of" + e.getMessage());
		}
		return fundingTypeId;
	}

	public String getCorrelationId() {
		Integer seqNo = 0;
		String hql = "SELECT MAX(notification_id) FROM buyer_outbound_notification";
		try {
			Query q = em.createNativeQuery(hql);
			seqNo = (Integer) q.getSingleResult();
		} catch (NoResultException e) {
			logger.error("Exception ingetting seq No" + e);
		}
		if (null == seqNo) {
			seqNo = 0;
		}
		seqNo++;
		return seqNo.toString();
	}

	public void insertInHomeOutBoundNotification(OutboundNotificationVO notificationVO) {
		String hql ="INSERT INTO buyer_outbound_notification"
				+ " (seq_no, so_id, service_id, xml, no_of_retries, active_ind, created_date, modified_date, status, exception)"
				+ " VALUES "
				+ " (:seqNo, :soId, :serviceId, :xml, :noOfRetries, :activeInd, NOW(), NOW(), :status, :exception)";
		try{
			Query q = em.createNativeQuery(hql);
			q.setParameter("soId", notificationVO.getSoId());
			q.setParameter("serviceId", notificationVO.getServiceId().toString());
			q.setParameter("xml", notificationVO.getXml());
			q.setParameter("noOfRetries", notificationVO.getNoOfRetries().toString());
			q.setParameter("activeInd", notificationVO.getActiveInd().toString());
			q.setParameter("status", notificationVO.getStatus());
			q.setParameter("exception", notificationVO.getException());
			q.setParameter("seqNo", notificationVO.getSeqNo());
			q.executeUpdate();
		}catch (Exception e) {
			logger.error("Exception in inserting details into notification table"+ e);
		}
	}

	// Method to fetch a message for a wf_state_id and wf_substatus_id from lu_inhome_outbound_notification_messages table.
	public String getNotificationMessage(Integer wfStateId, Integer wfSubStatusId) {
		String message = null;
		String hql = "SELECT message FROM lu_inhome_outbound_notification_messages" 
				+ " WHERE status_id =:statusId  AND substatus_id =:subStatusId";
		try {
			Query q = em.createNativeQuery(hql);
			q.setParameter("statusId", wfStateId);
			q.setParameter("subStatusId", wfSubStatusId);
			message = (String) q.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Exception in getting notification message" + e);
		}
		return message;
	}

	//to get the custom reference values for the SO
	public HashMap<String,String> getCustomRefValues(String soId){
		logger.info("Inside getCustomRefValues");
		List<Object[]> references = null;
		HashMap<String,String> refValues = new HashMap<String,String>();
		String hql = "SELECT rt.ref_type, buyer_ref_value FROM so_custom_reference sr " +
				"JOIN buyer_reference_type rt " +
				"ON sr.buyer_ref_type_id = rt.buyer_ref_type_id " +
				"WHERE so_id = :soId AND buyer_id = 3000 " +
				"AND ref_type IN ('UnitNumber','OrderNumber','ISP_ID')";
		try {
			Query q = em.createNativeQuery(hql);
			q.setParameter("soId", soId);
			references = q.getResultList();
			if(null != references){
				for(Object[] object : references){
					String ref = (String)object[0];
					logger.info("getCustomRefValues.ref:"+ref);
					String value = (String)object[1];
					logger.info("getCustomRefValues.value:"+value);
					refValues.put(ref, value);
				}
			}
		} catch (NoResultException e) {
			logger.error("Exception in getting notification message" + e);
		}
		return refValues;
	}

	// Code Added for Jira SL-19728
	// For Sending emails specific to NON FUNDED buyer
	// As per new requirements
	/**
	 * Method added to query the email template specific to NON FUNDED BUYER
	 * 
	 * @param buyerId
	 *            ,templateId
	 * @return
	 * @throws
	 */

	public Integer getEmailTemplateSpecificToBuyer(Integer buyerId,Integer templateId) {
		Integer tempId = 0;
		String hql = "SELECT original_template_id FROM buyer_email_mapping where buyer_id=:buyerId and template_id=:templateId";
		try {
			Query q = em.createNativeQuery(hql);
			q.setParameter(OrderfulfillmentConstants.BUYER_ID_NONFUNDED, buyerId);
			q.setParameter(OrderfulfillmentConstants.TEMPLATE_ID, templateId);
			tempId = (Integer) q.getSingleResult();
			return tempId;
		} catch (NoResultException e) {
			logger.error("Exception in getEmailTemplateSpecificToBuyer" + e);
			return tempId;
		}


	}

	//Jira SL-18825
	//To fetch custom reference with display_no_value indicator equal to 1
	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Integer> getCustRefwithEmptyValue(Integer buyerId) {
		String hql = null;
		Query query = null;
		List<Integer>custrefList=null;
		try{
			logger.debug("Inside Dao,getCustRefwithEmptyValue method and buyer id is "+ buyerId);
			hql="Select buyer_ref_type_id From buyer_reference_type WHERE buyer_id ="+buyerId+" AND display_no_value=1";
			query=em.createNativeQuery(hql);
			custrefList=(List<Integer>)query.getResultList();
			logger.debug("Size of Cust Ref List with Empty Value is"+ custrefList.size());
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
		}
		return custrefList;

	}


	//JIRA SL-17042
	@Transactional(propagation=Propagation.SUPPORTS)
	public void updateCompletedSOCount(Long buyerId, Long resourceId) {
		String hql = "";
		Query query = null;

		try {
			// increment the completed orders count in vendor_resource
			hql = "UPDATE vendor_resource SET total_so_completed = (COALESCE(total_so_completed,0)+1),modified_date = NOW() WHERE resource_id = :resourceId";
			query = em.createNativeQuery(hql);
			query.setParameter("resourceId", resourceId);
			query.executeUpdate();

			// check if the buyer has already rated the provider
			hql = "SELECT COUNT(*) FROM survey_response_summary WHERE buyer_id = :buyerID AND vendor_resource_id = :vendorResourceId";
			query = em.createNativeQuery(hql);
			query.setParameter("buyerID", buyerId);
			query.setParameter("vendorResourceId", resourceId);
			Number count = (Number) query.getSingleResult();
			if (null != count && count.intValue() == 1) {
				// update the completed orders count in survey_response_summary
				hql = "UPDATE survey_response_summary SET total_so_completed = (COALESCE(total_so_completed,0)+1),modified_date = NOW(),modified_by = :buyerID	WHERE buyer_id = :buyerID AND vendor_resource_id = :vendorResourceId";
				query = em.createNativeQuery(hql);
				query.setParameter("buyerID", buyerId);
				query.setParameter("vendorResourceId", resourceId);
				query.executeUpdate();
			} else {
				// insert a new row for buyer-provider combination in
				// survey_response_summary
				hql = "INSERT INTO survey_response_summary (buyer_id, vendor_resource_id,total_so_completed,created_date,modified_date,modified_by)VALUES (:buyerID, :vendorResourceId,1,NOW(),NOW(),:buyerID)";
				query = em.createNativeQuery(hql);
				query.setParameter("buyerID", buyerId);
				query.setParameter("vendorResourceId", resourceId);
				query.executeUpdate();
			}
		} catch (Exception e) {
			logger.error("Exception in method updateCompletedSOCount()" + e);
		}

	}

	// R12.0 Sprint 2 : Update so_trip current appointment details with service date while rescheduling.
	public void updateSOTripForReschedule(SOSchedule reschedule, String soId) {
		String hql = "";
		Query query = null;
		try {
			hql = "SELECT trip_no FROM so_trip WHERE so_id = :soId AND trip_status = 'OPEN' ORDER BY trip_no DESC";
			query = em.createNativeQuery(hql);
			query.setParameter("soId", soId);
			List<Integer> tripNo = (List<Integer>)query.getResultList();
			if(null != tripNo && !tripNo.isEmpty()){
				hql = "UPDATE so_trip SET current_appt_start_date = :startDate, current_appt_end_date = :endDate, current_appt_start_time = :startTime, " +
						"current_appt_end_time = :endTime, modified_date = NOW() WHERE so_id = :soId AND trip_no = :tripNum";
				query = em.createNativeQuery(hql);
				query.setParameter("startDate", reschedule.getServiceDateGMT1());
				query.setParameter("endDate", reschedule.getServiceDateGMT2());
				query.setParameter("startTime", reschedule.getServiceTimeStartGMT());
				query.setParameter("endTime", reschedule.getServiceTimeEndGMT());
				query.setParameter("soId", soId);
				query.setParameter("tripNum", tripNo.get(0));
				query.executeUpdate();
			}
		}catch (Exception e) {
			logger.error("Exception in method updateSOTripForReschedule()" + e);
		}
	}
	// R12_1 To insert/update for HSR autoclose table
	public void insertSOInhomeAutoclose(String soId,String status, Integer emailInd, Integer noOfRetries, Date processAfterDate, String subStatus, Integer activeInd) {
		String hql = "";
		Query query = null;
		try{
			// check if a record exists for the soId
			hql = "SELECT COUNT(*) FROM so_inhome_auto_close WHERE so_id = :soId";
			query = em.createNativeQuery(hql);
			query.setParameter("soId", soId);
			Number count = (Number) query.getSingleResult();
			if (null != count && count.intValue() >= 1) {
				hql = "UPDATE so_inhome_auto_close SET active_ind = 0,modified_date = NOW() where so_id = :soId and active_ind = 1";
				query = em.createNativeQuery(hql);
				query.setParameter("soId", soId);
				query.executeUpdate();
			}
			hql = "INSERT INTO so_inhome_auto_close(so_id,status,no_of_retries,email_ind,process_after_date,created_date,modified_date,active_ind,so_substatus)VALUES(:soId,:status,:noOfRetries,:emailInd,:processAfterDate,NOW(),NOW(),:activeInd,:subStatus)";
			query = em.createNativeQuery(hql);
			query.setParameter("soId", soId);
			query.setParameter("status", status);
			query.setParameter("noOfRetries", noOfRetries);
			query.setParameter("emailInd", emailInd);
			query.setParameter("processAfterDate", processAfterDate);
			query.setParameter("activeInd", activeInd);
			query.setParameter("subStatus", subStatus);
			query.executeUpdate();

		}
		catch (Exception e) {
			logger.error("Exception in method insertSOInhomeAutoclose()" + e);
		}

	}

	//R12_1 To get time interval from application_properties
	public Integer getInhomeAutoCloseTimeInterval(String timeInterval) {
		String hql = "";
		Query query = null;
		String temp=null;
		Integer intervalValue = 0;
		try{
			hql = "select app_constant_value from application_constants where app_constant_key = :timeInterval";
			query = em.createNativeQuery(hql);
			query.setParameter("timeInterval", timeInterval);
			logger.info("hql::"+hql);
			temp = (String)query.getSingleResult();
			logger.info("String value ::"+temp);
			intervalValue =Integer.parseInt(temp);
			logger.info("Integer value::"+intervalValue);
		}
		catch (Exception e) {
			logger.error("Exception in method getInhomeAutoCloseTimeInterval()" + e);
		}

		return intervalValue;
	}

	/**@Descriptrion: Soft delete invoice parts for the order.
	 * @param partsInvoiceId
	 */
	/*public void deleleInvoiceParts(Integer partsInvoiceId) {
		String hql="UPDATE so_provider_invoice_parts SET delete_ind = 1,modified_date = NOW()  WHERE so_provider_invoice_parts_id = :partsInvoiceId";
		Query query = em.createNativeQuery(hql);
		query.setParameter("partsInvoiceId", partsInvoiceId);
		try {
			query.executeUpdate();
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
		}

	}*/
	/*	Commenting code SL-20167 : updating purchase amount for canceled tasks 
	 * public void updatePurchaseAmount(String sku, BigDecimal purchaseAmount,String soId){
		logger.info("Inside ServiceOrderDao.updatePurchaseAmount SOID:"+soId+" sku:"+sku+" purchaseAmount:"+purchaseAmount);

		String hql = "";
		Query query = null;

		try {
			hql = "UPDATE so_tasks SET purchase_amount = :purchaseAmount,modified_date = NOW() WHERE sku = :sku and so_id = :soId";
			query = em.createNativeQuery(hql);
			query.setParameter("purchaseAmount", purchaseAmount);
			query.setParameter("sku", sku);
			query.setParameter("soId", soId);
			query.executeUpdate();
			logger.info("Inside ServiceOrderDao.updatePurchaseAmount after updating "+hql);

		} catch (Exception e) {
			logger.error("Exception in method updatePurchaseAmount()" + e);
		}
	}
	 */

	public Map<String, String> getAdjudicationConstants() {
		String hql = "SELECT app_constant_key AS app_constant_key,app_constant_value AS app_constant_value FROM application_constants WHERE app_constant_key "+
				"IN('auto_adjudication_default_reimbursement_rate','adjudication_tolerance','auto_adjudication_grossup_value','adjudication_commercial_discount_percentage','adjudication_final_price_constant')";
		Query query = em.createNativeQuery(hql);
		Map<String, String> adjudicationConstants = new HashMap<String, String>();

		try {
			Iterator hdrIterator = query.getResultList().iterator();

			while (hdrIterator.hasNext()) {

				Object[] tuple = (Object[]) hdrIterator.next();
				String key = (String) tuple[0];
				String value = (String) tuple[1];
				adjudicationConstants.put(key, value);
			}

			return adjudicationConstants;

		} catch (Exception e) {
			logger.info("getAdjudicationConstants" + e);
			return adjudicationConstants;
		}
	} 


	public Map<String, String> getReimbursementModelConstants(){
		String hql = "SELECT app_constant_key AS app_constant_key,app_constant_value AS app_constant_value FROM application_constants WHERE app_constant_key "+
				"IN('reimbursement_model_grossup_value','reimbursement_model_reimbursement_rate')";
		Query query = em.createNativeQuery(hql);
		Map<String, String> reimbursemntModelConstants = new HashMap<String, String>();

		try {
			Iterator hdrIterator = query.getResultList().iterator();

			while (hdrIterator.hasNext()) {

				Object[] tuple = (Object[]) hdrIterator.next();
				String key = (String) tuple[0];
				String value = (String) tuple[1];
				reimbursemntModelConstants.put(key, value);
			}

			return reimbursemntModelConstants;

		} catch (Exception e) {
			logger.info("getReimbursemntModelConstants" + e);
			return reimbursemntModelConstants;
		}



	}




	public Double getReimbursementRate(Integer vendorId){
		String hql = "";
		Query query = null;
		Double reimbursementRate=null;

		try{
			hql = "SELECT reimbursement_rate FROM inhome_auto_close_rule_firm_assoc WHERE active_ind=1 AND firm_id = :firmId";
			query = em.createNativeQuery(hql);
			query.setParameter("firmId", vendorId);
			logger.info("hql::"+hql);
			reimbursementRate = (Double)query.getSingleResult();
			logger.info("String value ::"+reimbursementRate);

		}
		catch (Exception e) {
			logger.error("Exception in method getReimbursementRate()" + e);
		}

		return reimbursementRate;

	}





	public Map<String, String> getPartsBuyerPriceCalcValues() {
		String hql = "SELECT CONCAT(c.part_coverage_type,'-',s.part_sourcing_type)AS identifer,CONCAT(CAST(b.reimbursement_rate AS CHAR),'-',b.sl_gross_up_val) "+
				"AS constants FROM buyer_parts_price_calc_values b "+
				"JOIN lu_part_coverage_type c ON c.part_coverage_type_id=b.part_coverage_type_id "+
				"JOIN lu_part_sourcing_level s ON s.part_sourcing_level_id=b.part_sourcing_level_id";

		Query query = em.createNativeQuery(hql);
		Map<String, String> constants = new HashMap<String, String>();

		try {
			Iterator hdrIterator = query.getResultList().iterator();

			while (hdrIterator.hasNext()) {

				Object[] tuple = (Object[]) hdrIterator.next();
				String key = (String) tuple[0];
				String value = (String) tuple[1];
				constants.put(key, value);
			}
			logger.info("constants"+constants);
			return constants;

		} catch (Exception e) {
			logger.info("getPartsBuyerPriceCalcValues" + e);
			return constants;
		}
	}
	//R12_1 SL-20647
	public void insertSoWorkflowControl(String value,String soId) {
		// TODO Auto-generated method stubString hql = "";
		Query query = null;
		String hql = "";
		try{
			hql = "UPDATE so_workflow_controls SET method_of_closure = :value where so_id = :soId";
			query = em.createNativeQuery(hql);
			query.setParameter("soId", soId);
			query.setParameter("value",value);
			query.executeUpdate();

		}
		catch (Exception e) {
			logger.error("Exception in method insertSoWorkflowControl()" + e);
		}

	} 

	//SL-20436
	public SORoutingRuleAssoc getCARAssociation(String soId) {
		Query query = em.createQuery("From SORoutingRuleAssoc rra where rra.soId = ?");
		query.setParameter(1, soId);
		return (SORoutingRuleAssoc) query.setMaxResults(1).getSingleResult();  
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public List<Long> getVendorIdsToExclude(String ruleId){
		logger.info("ruleId in getVendorIdsToExclude():"+ruleId);
		List<Long> firmIdsToExclude = null;
		List<Integer> vendorIdToExcludeListInteger = null;
		String hql = "SELECT routingRuleVendor.vendor_id FROM routing_rule_vendor routingRuleVendor"+
				" WHERE routingRuleVendor.routing_rule_hdr_id='"+ruleId+"'"+
				" AND routingRuleVendor.auto_accept_status= 'ON' "+  
				" AND routingRuleVendor.opportunity_email_ind= 0 ";

		Query query = em.createNativeQuery(hql);
		logger.info("getVendorIdsToExclude fetch query:"+hql);
		try {
			vendorIdToExcludeListInteger = query.getResultList();
		}
		catch (Exception e) {
			logger.error("Severe Exception occured in getVendorIdsToExclude"+e);
			e.printStackTrace();
		}
		if(null != vendorIdToExcludeListInteger && vendorIdToExcludeListInteger.size()>0){
			firmIdsToExclude = new ArrayList<Long>();
			for (Integer vendorId: vendorIdToExcludeListInteger){
				firmIdsToExclude.add((long) vendorId);
			}
		}
		logger.info("End method call getVendorIdsToExclude()");
		return firmIdsToExclude;


	}
	//PCT Vault II Changes
	public void updateHistory(SOAdditionalPaymentHistory sOAdditionalPaymentHistory){
		logger.info("inside updateHistory persis");

		try{
			if(null!= sOAdditionalPaymentHistory){
				logger.info("Before persisting of info"+sOAdditionalPaymentHistory.getSoId());
				logger.info("Before merger resource id");
				em.persist(sOAdditionalPaymentHistory);
			}else{
				logger.info("inside updateHistory persis: History is Null");
			}
		}catch(Exception e){
			logger.error("inside updateHistory error");
			e.printStackTrace();

		}
	}

	/**SL-21126
	 * Description :To fetch the value for an app key from the application_constants table
	 * @param appConstantKey
	 * @return
	 */
	public String getApplicationConstantValue(String appConstantKey){
		logger.info("Inside PriceReductionCmd ServiceDao :getApplicationConstantValue");
		String hql = "";
		Query query = null;
		String appValue=null;
		try{
			hql = "select app_constant_value from application_constants where app_constant_key = :appConstantKey";
			query = em.createNativeQuery(hql);
			query.setParameter("appConstantKey", appConstantKey);
			appValue =  (String) query.getSingleResult();

			logger.info("Inside PriceReductionCmd ServiceDao :getApplicationConstantValue: hql"+hql);
			logger.info("Inside PriceReductionCmd ServiceDao :getApplicationConstantValue: appValue"+appValue);
		}
		catch (Exception e) {
			logger.error("Exception in method getApplicationConstantValue()" + e);
		}
		return appValue;
	}


	public String getApplicationFlagValue(String appConstantKey){
		String hql = "";
		Query query = null;
		String appValue=null;
		try{
			hql = "SELECT app_value FROM application_flags WHERE app_key = :appConstantKey";
			query = em.createNativeQuery(hql);
			query.setParameter("appConstantKey", appConstantKey);
			appValue =  (String) query.getSingleResult();

		}
		catch (Exception e) {
			logger.error("Exception in method getApplicationFlagValue()" + e);
		}
		return appValue;
	}
	/**SL-21126
	 * Description :To fetch primary so for inhome priority 11
	 * @param so
	 * @param vendorId
	 * @return String
	 */

	public String getPrimarySO(ServiceOrder so,Integer vendorId){
		logger.info("PriceReductionCmd-  Inside ServiceDao Class - getPrimsarySO() method :");
		String primarySOId = null;
		String hql = "";
		Query query = null;
		try{
			hql =   "SELECT DISTINCT sh.so_id "+
					"FROM so_hdr sh JOIN so_location sl ON sh.so_id = sl.so_id "+
					"JOIN so_workflow_controls sw ON sh.so_id = sw.so_id "+
					"WHERE "+
					"sh.wf_state_id IN (110,150,155) AND "+
					"sh.so_id != :soId AND "+
					"sh.buyer_id = :buyerId AND "+
					"sw.method_of_routing = 'EXCLUSIVE' AND "+
					"sl.so_locn_type_id = 10 AND "+
					"sh.service_date1 = :serviceDate1 AND ";
			logger.info("hql1::"+hql);

			if(null!=so.getSchedule() && null!=so.getSchedule().getServiceDateGMT2()){
				hql = hql+"sh.service_date2 = :serviceDate2 AND ";
				logger.info("hql2::"+hql);
			}
			hql = hql +"sl.street_1 = :address1 AND "+
					"sl.street_2 = :address2 AND "+
					"sl.apt_no = :apptNo AND "+
					"sl.city = :city AND "+
					"sl.state_cd = :state AND "+
					"sl.zip = :zip AND "+
					"((sh.accepted_vendor_id = :vendorId AND sh.wf_state_id IN (150,155)) OR "+
					"( :vendorId IN(SELECT sr1.vendor_id FROM so_routed_providers sr1 WHERE sr1.so_id = sh.so_id AND sh.wf_state_id = 110 GROUP BY sr1.so_id HAVING COUNT( DISTINCT sr1.vendor_id) = 1 AND sr1.vendor_id = :vendorId ))) "+
					"ORDER BY sh.spend_limit_labor DESC ,sh.created_date DESC LIMIT 1; ";
			logger.info("hql3::"+hql);

			query = em.createNativeQuery(hql);
			query.setParameter("serviceDate1", so.getSchedule().getServiceDateGMT1());
			if(null!=so.getSchedule() && null!=so.getSchedule().getServiceDateGMT2()){
				query.setParameter("serviceDate2", so.getSchedule().getServiceDateGMT2());
			}
			if(null!=so.getLocations()){
				for(SOLocation soLocation: so.getLocations()){
					if(null != soLocation && null!=soLocation.getSoLocationTypeId() && 
							LocationType.SERVICE == soLocation.getSoLocationTypeId()){//need to check only service address
						query.setParameter("address1", soLocation.getStreet1());
						query.setParameter("address2", soLocation.getStreet2());
						query.setParameter("apptNo", soLocation.getAptNumber());
						query.setParameter("city", soLocation.getCity());
						query.setParameter("state", soLocation.getState());
						query.setParameter("zip", soLocation.getZip());
					}
				}
			}
			query.setParameter("soId", so.getSoId());
			query.setParameter("buyerId", so.getBuyerId());
			query.setParameter("vendorId", vendorId);
			logger.info("hql::"+hql);

			List<String> resultList = (List<String>) query.getResultList();
			if(null!=resultList && !resultList.isEmpty()){
				primarySOId =resultList.get(0);
			}
			logger.info("String value primarySOId ::"+primarySOId);

		}
		catch (NoResultException e) {
			logger.info("No primary SO found for the order:" + so.getSoId());
		}
		catch (Exception e) {
			logger.error("Exception in method getPrimarySO()" + e);
		}

		return primarySOId;
	}

	/**SL-21126
	 * Description :To fetch the SO with same location, service date 
	 * @param so
	 * @param vendorId
	 * @return String
	 */
	public List<ServiceOrder> getServiceOrdersForPrimarySo(ServiceOrder so,String validWfStates){
		logger.info("entered getServiceOrdersForPrimarySo()");
		Query query = null;
		List<ServiceOrder> seriveOrderList = new ArrayList<ServiceOrder>();
		StringBuilder hql = new StringBuilder();
		SOLocation soLocationObj = null;
		try{

			if(null!= so && null!=so.getLocations()){
				for(SOLocation soLocation: so.getLocations()){
					if(null != soLocation && null!=soLocation.getSoLocationTypeId() && 
							LocationType.SERVICE == soLocation.getSoLocationTypeId()){//need to check only service address
						soLocationObj = soLocation;

					}
				}
			}
			if(null!=soLocationObj && null!=so.getSchedule()){
				hql.append("SELECT sh.so_id, sh.wf_state_id,sh.accepted_vendor_id,sh.created_date "+
						"FROM so_hdr sh JOIN so_location sl ON sh.so_id = sl.so_id "+ 
						"JOIN so_workflow_controls sw ON sh.so_id = sw.so_id "+
						"WHERE " +
						"sh.wf_state_id IN ("+validWfStates+") AND "+
						"sh.service_date1 = :serviceDate1 AND ");
				logger.info("hql1::"+hql);

				if(null!=so.getSchedule().getServiceDateGMT2()){
					hql.append("sh.service_date2 = :serviceDate2 AND ");
					logger.info("hql2::"+hql);
				}else{
					hql.append("(sl.service_date2 IS NULL OR sl.service_date2 = '' OR sh.service_date2 = :serviceDate1 ) AND ");
				}
				hql.append("sl.street_1 = :address1  AND ");
				if( StringUtils.isNotBlank(soLocationObj.getStreet2())){
					hql.append("sl.street_2 = :address2 AND ");
				}else{
					hql.append("(sl.street_2 IS NULL OR sl.street_2 = '') AND ");
				}
				if( StringUtils.isNotBlank(soLocationObj.getAptNumber())){
					hql.append("sl.apt_no = :apptNo AND ");
				}else{
					hql.append("(sl.apt_no  IS NULL OR sl.apt_no = '') AND  ");
				}
				hql.append("sl.city = :city AND "+
						"sl.state_cd = :state AND "+
						"sl.zip = :zip AND "+
						"sw.method_of_routing = 'EXCLUSIVE' AND "+
						"sl.so_locn_type_id = 10  AND "+
						"sh.so_id != :soId AND "+
						"sh.buyer_id = :buyerId  ORDER BY sh.created_date ASC ");

				logger.info("hql3::"+hql);

				query = em.createNativeQuery(hql.toString());

				query.setParameter("serviceDate1", so.getSchedule().getServiceDateGMT1());
				if(null!=so.getSchedule().getServiceDateGMT2()){
					query.setParameter("serviceDate2", so.getSchedule().getServiceDateGMT2());
				}
				query.setParameter("address1", soLocationObj.getStreet1());
				if(StringUtils.isNotBlank(soLocationObj.getStreet2())){
					query.setParameter("address2", soLocationObj.getStreet2());
				}
				if(StringUtils.isNotBlank(soLocationObj.getAptNumber())){
					query.setParameter("apptNo", soLocationObj.getAptNumber());
				}
				query.setParameter("city", soLocationObj.getCity());
				query.setParameter("state", soLocationObj.getState());
				query.setParameter("zip", soLocationObj.getZip());

				query.setParameter("soId", so.getSoId());
				query.setParameter("buyerId", so.getBuyerId());
				logger.info("hql::"+hql);
				//
				List<Object[]> resultList = (List<Object[]>) query.getResultList();
				if(null!=resultList && !resultList.isEmpty()){
					Iterator iterator = resultList.iterator();
					if(iterator!=null) {
						while(iterator.hasNext()) {
							Object[] tuple = (Object[]) iterator.next();
							ServiceOrder soObj = new ServiceOrder();
							soObj.setSoId((String) tuple[0]);
							soObj.setWfStateId((Integer)tuple[1]);
							Integer temp = (Integer)tuple[2];
							if(null!=temp){
								soObj.setAcceptedProviderId(Long.parseLong(temp.toString()));
							}
							soObj.setCreatedDate((Date)tuple[3]);
							seriveOrderList.add(soObj);
						}
					}
				}
			}
		}catch (NoResultException e) {
			logger.info("No primary SO found for the order:" + so.getSoId());
		}
		catch (Exception e) {
			logger.error("Exception in method getServiceOrdersForPrimarySo()" + e);
		}  
		logger.info("leaving getServiceOrdersForPrimarySo()");
		return seriveOrderList;
	}

	/**SL-21126
	 * Description :To fetch the unique vendorId for posted orders
	 * @param so
	 * @param vendorId
	 * @return String
	 */
	public Integer getVendorIdForRoutedSo(String soId, Integer secondaryVendorId){
		logger.info("entered getVendorIdForRoutedSo()");
		String hql ="";
		Query query = null;
		Integer vendorId = null;
		try{

			if(null!=soId && null!= secondaryVendorId){
				hql = "SELECT sr1.vendor_id FROM so_routed_providers sr1 WHERE sr1.so_id = :soId "+
						"GROUP BY sr1.so_id HAVING COUNT( DISTINCT sr1.vendor_id) = 1 AND sr1.vendor_id = :secondaryVendorId ";
				query = em.createNativeQuery(hql);
				query.setParameter("soId", soId);
				query.setParameter("secondaryVendorId", secondaryVendorId);
				List<Integer> resultList = (List<Integer>) query.getResultList();
				if(null!=resultList && !resultList.isEmpty()){
					vendorId = (Integer)resultList.get(0);
				}
			}
		} catch (NoResultException e) {
			logger.info("No VendorId Found for RoutedSo :" + soId);
		}
		catch (Exception e) {
			logger.error("Exception in method getVendorIdForRoutedSo()" + e);
		}
		logger.info("leaving getVendorIdForRoutedSo()");
		return vendorId;
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public Integer getRoutingRuleVendorId(Integer ruleId){
		logger.info("ruleId in getRoutingRuleVendorId():"+ruleId);
		Integer vendorId= null;
		List<Integer> vendorIdToInclude = null;
		String hql = "SELECT routingRuleVendor.vendor_id FROM routing_rule_vendor routingRuleVendor"+
				" WHERE routingRuleVendor.routing_rule_hdr_id='"+ruleId+"'";

		Query query = em.createNativeQuery(hql);
		logger.info("getRoutingRuleVendorId fetch query:"+hql);
		try {
			vendorIdToInclude = query.getResultList();
		}
		catch (Exception e) {
			logger.error("Severe Exception occured in getRoutingRuleVendorId"+e);
		}
		if(null != vendorIdToInclude && vendorIdToInclude.size() == 1){
			vendorId = vendorIdToInclude.get(0);		
		}
		logger.info("End method call getRoutingRuleVendorId()");
		return vendorId;
	}

	/**@Description: Getting the warranty Order associated with the service order
	 * @param originalOrderNumber
	 * @param originalUnitNumber
	 * @return
	 */
	public String getInhomeWarrantyOrder(String originalOrderNumber,String originalUnitNumber) throws ServiceOrderException {
		String originalSo=null;
		Query query = null;
		String hql = null;
		try{
			hql =  "SELECT sc.so_id FROM buyer_reference_type br JOIN so_custom_reference sc "+
					"ON (br.buyer_ref_type_id=sc.buyer_ref_type_id AND br.ref_type='UnitNumber' AND sc.buyer_ref_value=:unitNumber) "+
					"JOIN so_custom_reference sct JOIN buyer_reference_type brt  ON (brt.buyer_ref_type_id=sct.buyer_ref_type_id  " +
					"AND brt.ref_type='OrderNumber' AND sct.buyer_ref_value=:orderNumber) AND sc.so_id = sct.so_id AND br.buyer_id=3000 AND brt.buyer_id=3000";
			query=em.createNativeQuery(hql);
			logger.info("Query"+ hql);
			query.setParameter("unitNumber", originalUnitNumber);
			query.setParameter("orderNumber", originalOrderNumber);
			originalSo = (String) query.getSingleResult();
		}catch (NoResultException nre) {
			logger.info("No service order is found to be associated with the warranty Order"+ nre.getMessage());
		}catch (Exception e) {
			logger.error("Exception in fetching the Warranty Order associated with the service Order"+ e);
			throw new ServiceOrderException("Exception in fetching the Warranty Order associated with the service Order",e);
		}
		return originalSo;
	}

	/**@Description: Getting the firm Name from vendor_hdr
	 * @param acceptedFirm
	 * @return
	 * @throws ServiceOrderException
	 */
	public String getProviderFirmName(Long acceptedFirm)throws ServiceOrderException {
		String firmName=null;
		Query query = null;
		String hql = null;
		try{
			hql = "SELECT vh.business_name FROM vendor_hdr vh WHERE vh.vendor_id=:firmId";
			query=em.createNativeQuery(hql);
			query.setParameter("firmId", acceptedFirm);
			firmName = (String) query.getSingleResult();
		}catch (NoResultException nre) {
			logger.info("Business Name is not saved for the firm in DB"+ nre.getMessage());
		}
		catch (Exception e) {
			logger.error("Exception in fetching the business name for the firm"+ e);
			throw new ServiceOrderException("Exception in fetching the business name for the firm",e);
		}
		return firmName;
	}
	/**@Description: validating firm status in (3,26,34)
	 * @param inhomeAcceptedProviderFirm
	 * @return
	 * @throws ServiceOrderException
	 */
	public boolean validateFirmStatus(Long inhomeAcceptedProviderFirm)throws ServiceOrderException {
		boolean isValid= false;
		Query query = null;
		String hql = null;
		try{
			hql = "SELECT vh.wf_state_id FROM vendor_hdr vh WHERE vh.vendor_id=:firmId AND vh.wf_state_id IN (3,26,34)";
			query=em.createNativeQuery(hql);
			query.setParameter("firmId", inhomeAcceptedProviderFirm);
			Integer wfState = (Integer) query.getSingleResult();
			if(null!= wfState){
				isValid=true;
			}
		}catch (NoResultException nre) {
			logger.info("Provider firm is not compliant in ServiceLive"+ nre.getMessage());
		}
		catch (Exception e) {
			logger.error("Exception validating the Firm Status"+ e);
			throw new ServiceOrderException("Exception validating the Firm Status",e);
		}
		return isValid;
	}
	/**
	 * @param spnId
	 * @param inhomeAcceptedFirm
	 * @return
	 * @throws ServiceOrderException
	 */
	public boolean validateFirmSpnCompliance(Integer spnId,Long inhomeAcceptedFirm) throws ServiceOrderException {
		boolean isValid= false;
		Query query = null;
		String hql = null;
		try{
			hql = "SELECT spfs.provider_wf_state FROM spnet_provider_firm_state spfs WHERE spfs.provider_firm_id=:firmId AND spfs.spn_id=:spnId " +
					" AND spfs.provider_wf_state=:spnStatus";
			query=em.createNativeQuery(hql);
			query.setParameter("firmId", inhomeAcceptedFirm);
			query.setParameter("spnId", spnId);
			query.setParameter("spnStatus", "PF SPN MEMBER");
			String  spnStatus = (String) query.getSingleResult();
			if(StringUtils.isNotBlank(spnStatus)){
				isValid =true;
			}
		}catch (NoResultException nre) {
			logger.info("Provider firm is not compliant in SPN"+ nre.getMessage());
		}
		catch (Exception e) {
			logger.error("Exception in validateFirmSpnCompliance for the firm"+ e);
			throw new ServiceOrderException("Exception in validateFirmSpnCompliance for the firm",e);
		}
		return isValid;
	}

	/**@Description: check for logs exits for No Matching CAR rule and Recall provider Not Available
	 * @param soId
	 * @return
	 * @throws ServiceOrderException
	 */
	public boolean checkNoMatchCarRuleOrRecallProviderExists(String soId) throws ServiceOrderException {
		boolean isValid=false;
		Query query = null;
		String hql = null;
		try{
			hql = "SELECT l.action_id FROM so_logging l WHERE l.so_id=:soId AND l.action_id IN (279)";
			query=em.createNativeQuery(hql);
			query.setParameter("soId", soId);
			Integer wfState = (Integer) query.getSingleResult();
			if(null!= wfState){
				isValid=true;
			}
		}catch (NoResultException nre) {
			logger.info("No Logging exists for No Matching Car Rule or Recall provider Not available"+ nre.getMessage());
		}
		catch (Exception e) {
			logger.error("Exception validating the Drfat Logging with No Matching Car Rule or Recall provider Not available "+ e);
			throw new ServiceOrderException("Exception validating the Drfat Logging with No Matching Car Rule or Recall provider Not available",e);
		}
		return isValid;
	}

	/**@Description: check for logs exits Normal Draft Order Creation
	 * @param soId
	 * @return
	 * @throws ServiceOrderException
	 */
	public boolean checkDraftLogExists(String soId) throws ServiceOrderException {
		boolean isValid=false;
		Query query = null;
		String hql = null;
		try{
			hql = "SELECT l.action_id FROM so_logging l WHERE l.so_id=:soId AND l.action_id = 21";
			query=em.createNativeQuery(hql);
			query.setParameter("soId", soId);
			Integer wfState = (Integer) query.getSingleResult();
			if(null!= wfState){
				isValid=true;
			}
		}catch (NoResultException nre) {
			logger.info("No Logging exists for Draft Order Creation"+ nre.getMessage());
		}
		catch (Exception e) {
			logger.error("Exception validating the Drfat Logging "+ e);
			throw new ServiceOrderException("Exception validating the Drfat Logging",e);
		}
		return isValid;
	}

	/**@Description: Removing Normal Draft Logging from the Order. 
	 * @param soId
	 * @throws ServiceOrderException
	 */
	public void deleteDraftLogging(String soId) throws ServiceOrderException {
		String hql = "DELETE l.* FROM so_logging l WHERE  l.so_id=:soId AND l.action_id = 21";
		logger.info("Query for delete"+hql);
		Query query = em.createNativeQuery(hql);
		query.setParameter("soId", soId);
		try {
			query.executeUpdate();
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
		}

	}

	public boolean validateRecallOrder(ServiceOrder serviceOrder)throws ServiceOrderException {
		String repeatRepairOrderNumber=null;
		String repeatRepairUnitNumber=null;
		String warrantyOrder=null;
		ServiceOrder so = null;
		Integer wfState = null;
		try{
			repeatRepairOrderNumber = serviceOrder.getCustomRefValue(OFConstants.REPEAT_REPAIR_ORDER_NUMBER);
			logger.info("REPEAT_REPAIR_ORDER_NUMBER"+ repeatRepairOrderNumber);
			repeatRepairUnitNumber = serviceOrder.getCustomRefValue(OFConstants.REPEAT_REPAIR_UNIT_NUMBER);
			logger.info("REPEAT_REPAIR_UNIT_NUMBER"+ repeatRepairUnitNumber);
			if(StringUtils.isNotBlank(repeatRepairOrderNumber) && StringUtils.isNotBlank(repeatRepairUnitNumber)){
				warrantyOrder = getInhomeWarrantyOrder(repeatRepairOrderNumber,repeatRepairUnitNumber);
				wfState = getComplatedClosedWfStatus(warrantyOrder);
				if(null!= wfState && StringUtils.isNotBlank(warrantyOrder)){
					return true;
				}
			}

		}catch (Exception e) {
			logger.error("Exception validating Recall Order "+ e);
			throw new ServiceOrderException("Exception validating Recall Order",e);
		}
		return false;
	}

	private Integer getComplatedClosedWfStatus(String warrantyOrder) {
		Integer wfState = null;
		Query query = null;
		String hql = null;
		try{
			if(StringUtils.isNotBlank(warrantyOrder)){
				hql = "SELECT wf_state_id FROM so_hdr WHERE so_id=:soId";
				logger.info("Query for delete"+hql);
				query = em.createNativeQuery(hql);
				query.setParameter("soId", warrantyOrder);
				wfState = (Integer) query.getSingleResult();
				if(null!=wfState && (wfState.equals(OFConstants.SO_COMPLETED)||
						wfState.equals(OFConstants.SO_CLOSED))){
					return wfState;
				}
			}

		}catch (NoResultException nre) {
			logger.info("Wf state id for the order is null"+ nre.getMessage());
		}
		catch (Exception e) {
			logger.error("Exception validating Recall Order from getComplatedClosedWfStatus() "+ e);
			throw new ServiceOrderException("Exception validating Recall Order",e);
		}
		return null;
	}
	/**
	 * @param providerIds
	 * @return
	 * SL-18979 AddrFetcher for SMSchanges
	 */
	public List<Long> getProviderIdsForSMS(List<Long> providerIds)  throws BusinessServiceException{
		List<Long> proIds = null;
		List<Integer> proIdsInteger = null;

		StringBuffer proIdsList = new StringBuffer("");

		int count = 0;

		for(Long id : providerIds){
			proIdsList = proIdsList.append(id);
			count++;
			if (count<providerIds.size()){
				proIdsList = proIdsList.append(", ");
			}
		}
		String hql = "SELECT vResSubScr.resource_id FROM vendor_resource_sms_subscription vResSubScr"+
				" WHERE vResSubScr.resource_id IN ("+proIdsList+")"+
				" AND (vResSubScr.status NOT IN ('DELETED','INACTIVE'))";

		Query query = em.createNativeQuery(hql);
		try {
			proIdsInteger = query.getResultList();
			if(null != proIdsInteger && !proIdsInteger.isEmpty()){
				proIds = new ArrayList<Long>();
				for (Integer proId: proIdsInteger){
					proIds.add((long) proId);
				}
			}
		}
		catch (Exception e) {
			logger.error("Severe Exception occured in getProviderIdsForSMS"+e);
			throw new BusinessServiceException(e);
		}

		return proIds;


	}

	/**
	 * @param providerFirmIds
	 * @return
	 *  SL-18979 AddrFetcher for SMSchanges
	 */
	public List<Long> getFirmIdsForSMS(List<Long> providerFirmIds)  throws BusinessServiceException{
		List<Long> firmIds = null;
		StringBuffer firmIdsList = new StringBuffer("");
		List<Integer> firmIdsInteger = null;

		int count = 0;

		for(Long id : providerFirmIds){
			firmIdsList = firmIdsList.append(id);
			count++;
			if (count<providerFirmIds.size()){
				firmIdsList = firmIdsList.append(", ");
			}
		}
		String hql = "SELECT vRes.vendor_id FROM vendor_resource_sms_subscription vResSubScr JOIN vendor_resource vRes on vRes.resource_id = vResSubScr.resource_id and vRes.primary_ind=1"+
				" WHERE vRes.vendor_id IN ("+firmIdsList+")"+
				" AND (vResSubScr.status NOT IN ('DELETED','INACTIVE'))";

		Query query = em.createNativeQuery(hql);
		try {
			firmIdsInteger = query.getResultList();
			if(null != firmIdsInteger && !firmIdsInteger.isEmpty()){
				firmIds = new ArrayList<Long>();
				for (Integer firmId: firmIdsInteger){
					firmIds.add((long) firmId);
				}
			}
		}
		catch (Exception e) {
			logger.error("Severe Exception occured in getFirmIdsForSMS"+e);
			throw new BusinessServiceException(e);
		}

		return firmIds;


	}
	/**Owner  : Infosys
	 * module : Inhome Service Operation Notificatios
	 * purpose: Set the Job code from lu table if exists for the main service category in notification xml
	 * Jira : SL-21241
	 * @description: Set the Job code from lu table if exists for the main service category
	 * @param primarySkillCatId
	 * @param job
	 */
	public String getJobCodeForNodeId(Integer primarySkillCatId) throws BusinessServiceException {
		String jobCode=null;
		try{
			String hql ="SELECT job_code FROM lu_jobcode WHERE primary_skill_category_id=:primarySkillCatId";
			Query query = em.createNativeQuery(hql);
			query.setParameter("primarySkillCatId", primarySkillCatId);
			jobCode = (String) query.getSingleResult();

		}catch (Exception e) {
			logger.error("Exception in getting the job code for the main service category id"+e);
			throw new BusinessServiceException(e);
		}
		return jobCode;
	}

	/**Owner  : Infosys
	 * module : Relay Services Notification logging
	 * purpose: logging notifications to the relay services 
	 * Jira : 
	 * @description: logging notifications to the relay services 
	 * @param soId
	 * @param request
	 * @param responseCode
	 */
	public void loggingRelayServicesNotification(String soId, String request,
			int responseCode) throws BusinessServiceException {
		String hql = "";
		Query query = null;
		if (null == soId){
			soId = "";
		}
		try{
			hql = "INSERT INTO relay_services_notification_logging(so_id,request,response,created_date)VALUES(:soId,:request,:response,NOW())";
			query = em.createNativeQuery(hql);
			query.setParameter("soId", soId);
			query.setParameter("request", request);
			query.setParameter("response", responseCode);
			query.executeUpdate();
		}
		catch (Exception e) {
			logger.error("Exception in method loggingRelayServicesNotification()" + e);
		}

	}
	// SL-21455 to check whether the order is release by firm
	public boolean isReleaseByFirm(String soId) {
		boolean releaseByFirm = false;
		String request = null;
		try {

			String release="%release%";
			String repost="%repost%";
			String hql = "SELECT CAST(request AS CHAR(100)) AS request FROM relay_services_notification_logging WHERE so_id='"+soId+"'"+" AND "
					+ "request LIKE '"+release+"'"+" OR request LIKE '"+repost+"'"+" ORDER BY created_date DESC";
			logger.info("hql: "+hql);
			Query query = em.createNativeQuery(hql);
			query.setMaxResults(1);
			request = (String) query.getSingleResult();
			logger.info("request "+ request);
			if (null != request && request.contains("order_released_by_firm")) {
				releaseByFirm = true;
			}

		} catch (Exception e) {
			logger.error("Exception in method isReleaseByFirm"
					+ e);

		}
		return releaseByFirm;
	}

	public List<Integer> getFirmIdList(String soId)
	{
		try{
			String queryString =
					"SELECT DISTINCT vendor_id FROM so_routed_providers WHERE so_id='"+soId+"'";                 
			Query q = em.createNativeQuery(queryString);
			logger.info("queryString"+queryString);

			List<Integer> resultList = (List<Integer>) q.getResultList();
			if (resultList==null || resultList.size()==0) return null;
			return resultList;
		}
		catch(Exception e){
			logger.info("Exception in getFirmIdList"+e);
			return null;
		}
	}

	public List<Integer> getFirmIdNotReleaseSO(String soId) {
		try {
			String queryString = "SELECT DISTINCT vendor_id FROM so_routed_providers WHERE so_id='" + soId + "' AND provider_resp_id != 7";
			Query q = em.createNativeQuery(queryString);
			logger.info("queryString" + queryString);

			List<Integer> resultList = (List<Integer>) q.getResultList();
			if (resultList == null || resultList.size() == 0)
				return null;
			return resultList;
		} catch (Exception e) {
			logger.error("Exception in getFirmIdNotReleaseSO" + e);
			return null;
		}
	}

	public String getVendorBNameList(List<Integer> uniqueNewVendorsList) {
		logger.info("start getVendorBNameList @ serviceOrderDao");
		try {
			if (null == uniqueNewVendorsList || uniqueNewVendorsList.size() < 1) {
				logger.warn("vendor list is empty.");
				return null;
			}

			int count = 0;
			StringBuffer vendorIdsList = new StringBuffer();

			for (Integer id : uniqueNewVendorsList) {
				vendorIdsList.append(id);
				count++;
				if (count < uniqueNewVendorsList.size()) {
					vendorIdsList.append(", ");
				}
			}

			logger.info("vendorIdsList  size : " + count);

			String queryString = "SELECT vendor_id, business_name FROM vendor_hdr WHERE vendor_id in (" + vendorIdsList.toString() + ")";
			Query query = em.createNativeQuery(queryString);

			logger.debug("queryString: " + queryString);

			List<Object[]> vendorDataList = query.getResultList();

			if (null == vendorDataList || vendorDataList.size() < 1) {
				return null;
			}

			logger.info("vendorIdsList  size from DB : " + vendorDataList.size());

			JSONArray jsonArray = new JSONArray();

			for (int i = 0; i < vendorDataList.size(); i++) {
				Object[] vendorData = vendorDataList.get(i);

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("firmid", (Integer) vendorData[0]);
				jsonObject.put("firmname", URLEncoder.encode(((String) vendorData[1]),"UTF-8"));
				jsonArray.put(jsonObject);
			}

			logger.debug("result list : " + jsonArray);
			return jsonArray.toString();
		} catch (Exception e) {
			logger.error("Exception in getFirmIdList" + e);
			return null;
		}
	}
	public boolean isEstimationSO(String soId) {
		logger.info("isEstimationSO");
		String queryString = "SELECT count(*) from so_custom_reference socust "
				+ "inner join buyer_reference_type reftype on reftype.buyer_ref_type_id=socust.buyer_ref_type_id and reftype.ref_type='Order_Type'"
				+ " WHERE socust.so_id='"+soId+"'  and socust.buyer_ref_value='ESTIMATION'";		
		Query query = em.createNativeQuery(queryString);
		logger.debug("queryString: " + queryString);

		BigInteger count = (BigInteger) query.getSingleResult();
		if(count.intValue() <= 0) {
			return false;
		}		
		return true;		
	}
	
	public List<BuyerSkuAddon> getBuyerSkuAddOnList( Long CatId,String primarySku, Long buyerId) throws BusinessServiceException{
		try{
		String queryString =
				"SELECT buyerSkuAddon FROM BuyerSkuAddon buyerSkuAddon "
						+ "WHERE"
						+ " buyerSkuAddon.skuCategoryId = :CatId AND (buyerSkuAddon.groupNo IS NULL OR buyerSkuAddon.groupNo =:primarySku) "
						+ "AND buyerSkuAddon.buyerId =:buyerId ";
		Query q = em.createQuery(queryString);
		q.setParameter("CatId", CatId.longValue());
		q.setParameter("primarySku", primarySku);
		q.setParameter("buyerId", buyerId);

		@SuppressWarnings("unchecked")
		List<BuyerSkuAddon> resultList = (List<BuyerSkuAddon>) q.getResultList();
		return resultList;
		} catch (Exception e) {
			logger.error("Relay Exception in getBuyerSkuAddOnList" + e);
			return null;
		}
	}
		
		public List<BuyerSkuAddon> getBuyerSkuAddOnList( String addon, Long buyerId) throws BusinessServiceException{
			try{
			String queryString =
					"SELECT buyerSkuAddon FROM BuyerSkuAddon buyerSkuAddon "
							+ "WHERE"
							+ " buyerSkuAddon.type = :addon "
							+ "AND buyerSkuAddon.buyerId =:buyerId ";
			Query q = em.createQuery(queryString);
			q.setParameter("addon", addon);
			q.setParameter("buyerId", buyerId);

			@SuppressWarnings("unchecked")
			List<BuyerSkuAddon> resultList = (List<BuyerSkuAddon>) q.getResultList();
			return resultList;
			} catch (Exception e) {
				logger.error("Exception in getBuyerSkuAddOnList" + e);
				return null;
			}
	}
		
		
			public BuyerSkuGroups getBuyerSkuGroups( Long skuId) throws BusinessServiceException{
				try{
				String queryString =
						"SELECT buyerSkuGroups FROM BuyerSkuGroups buyerSkuGroups "
							+ " WHERE buyerSkuGroups.skuId =:skuId ";
				Query q = em.createQuery(queryString);
				q.setParameter("skuId", skuId);

				@SuppressWarnings("unchecked")
				BuyerSkuGroups result = (BuyerSkuGroups) q.getSingleResult();
				return result;
				} catch (Exception e) {
					logger.error("Exception in getBuyerSkuAddOnList" + e);
					return null;
				}
	}
			
			public void insertCallBackNotification(BuyerCallBackNotificationVO buyerCallBackNotificationVO) {
				
				logger.info("Inserting the data in buyer_callback_notification table");
				
				String hql ="INSERT INTO buyer_callback_notification"
						+ " (seq_no, so_id, event_id,  no_of_retries,  created_date, modified_date, status)"
						+ " VALUES "
						+ " (:seqNo, :soId, :serviceId,  :noOfRetries, NOW(), NOW(), :status )";
				try{
					Query q = em.createNativeQuery(hql);
					q.setParameter("soId", buyerCallBackNotificationVO.getSoId());
					q.setParameter("serviceId", buyerCallBackNotificationVO.getEventId().toString());
					
					q.setParameter("noOfRetries", buyerCallBackNotificationVO.getNoOfRetries().toString());
					q.setParameter("status", buyerCallBackNotificationVO.getStatus());
					q.setParameter("seqNo", buyerCallBackNotificationVO.getSeqNo());
					q.executeUpdate();
				}catch (Exception e) {
					logger.error("Exception in inserting details into Buyer_callback_notification table"+ e);
				}
			}
			
			public String getSequenceId() {
				Integer seqNo = 0;
				String hql = "SELECT MAX(notification_id) FROM buyer_callback_notification";
				try {
					Query q = em.createNativeQuery(hql);
					seqNo = (Integer) q.getSingleResult();
				} catch (NoResultException e) {
					logger.error("Exception ingetting seq No" + e);
				}
				if (null == seqNo) {
					seqNo = 0;
				}
				seqNo++;
				return seqNo.toString();
			}
			
			
	public boolean checkBuyerCallbackExist(Long buyerId) throws ServiceOrderException {
		logger.info("Inside checkBuyerCallbackExist>>"+buyerId);
				boolean isValid=false;
				Query query = null;
				String hql = null;
				try{
					hql = "SELECT bcs.callback_flag FROM buyer_callback_service bcs WHERE bcs.buyer_id=:buyerId";
					query=em.createNativeQuery(hql);
					query.setParameter("buyerId", buyerId.intValue());
					String flag = String.valueOf(query.getSingleResult());
					if(null!= flag && flag.equals("Y")){
						isValid=true;
					}
				}catch (NoResultException nre) {
					logger.info("Callback flag is not found"+ nre.getMessage());
				}
				catch (Exception e) {
					
					throw new ServiceOrderException("Exception validating the flag",e);
				}
				return isValid;
			}
	
	public void updateSohdrAndSlotSelectedValues(String soId,int preferenceInd) throws ServiceOrderException {
		updateSelctedSeviceDateTimeSlot(soId, preferenceInd);
		updateSelectedSlotValue(soId, preferenceInd);
	}
	
	private void updateSelctedSeviceDateTimeSlot(String soId,int preferenceInd) throws ServiceOrderException {
		logger.info("Inside updateSelctedSeviceDateTimeSlot>>"+soId);
		String hql="UPDATE so_hdr so INNER JOIN so_service_datetime_slot sdt ON so.so_id = sdt.so_id " 
				+"SET so.service_date1 = sdt.service_start_date,so.service_time_start = sdt.service_start_time," 
				+"so.service_date2 = sdt.service_end_date,so.service_time_end = sdt.service_end_time " 
				+"where so.so_id=:soId and  sdt.so_id=:soId and sdt.preference_ind=:preferenceInd";
		Query query = em.createNativeQuery(hql);
		query.setParameter("soId", soId);
		query.setParameter("preferenceInd", preferenceInd);
		try {
			query.executeUpdate();
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
		}
		catch (Exception e) {
			logger.info(e.getMessage());
			throw new ServiceOrderException("Exception occured while updating the serviceslot date so_hdr",e);
		}

	}
	
	private void updateSelectedSlotValue(String soId,int preferenceInd) throws ServiceOrderException{
		logger.info("Inside updateSelectedSlotValue>>"+soId);

		String hql="UPDATE so_service_datetime_slot SET slot_seleted_ind=1 WHERE so_id=:soId AND preference_ind=:preferenceInd";
		Query query = em.createNativeQuery(hql);
		query.setParameter("soId", soId);
		query.setParameter("preferenceInd", preferenceInd);
		try {
			query.executeUpdate();
		}
		catch (NoResultException e) {
			logger.info(e.getMessage());
		}
		catch (Exception e) {
			logger.info(e.getMessage());
			throw new ServiceOrderException("Exception occured while updating the serviceslot",e);
		}
	}
	
	public Double getRefundPostingFee(ServiceOrder serviceOrder, long businessTransactionId) {
		
		Double postingFee = 0.0;
		String effectiveDate;
		logger.info("inside getRefundPostingFee >>");
		if (serviceOrder.getBuyerId() != null) {
			logger.info("buyer_id >>" + serviceOrder.getBuyerId());
			logger.info("funding_type_id >>" + serviceOrder.getFundingTypeId());
			logger.info("bus_trans_id >>" + businessTransactionId);
			logger.info("Routed Date >>" + serviceOrder.getRoutedDate());
			String hql = "SELECT refund_posting_fee FROM supplier_prod.buyer_refund_posting_fee WHERE buyer_id=? AND bus_trans_id=? "
					+ "AND funding_type_id=? AND effective_date<=? order by effective_date desc";
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
				if(serviceOrder.getRoutedDate()==null){
					//for buyer 3000 warrenty case routed date was null due to this using created date.
					effectiveDate=dateFormat.format(serviceOrder.getCreatedDate());
				}else{
					effectiveDate=dateFormat.format(serviceOrder.getRoutedDate());;
				}
				logger.info("effective Date >>"+effectiveDate);
				Query query = em.createNativeQuery(hql);
				query.setParameter(1, serviceOrder.getBuyerId());
				query.setParameter(2, businessTransactionId);
				query.setParameter(3, serviceOrder.getFundingTypeId());
				query.setParameter(4, effectiveDate);
				query.setMaxResults(1);
				postingFee = (Double) query.getSingleResult();
				if (postingFee != 0.0) {
					if (postingFee > serviceOrder.getPostingFee().doubleValue()) {
						postingFee = serviceOrder.getPostingFee().doubleValue();
					}
				}
			} catch (NoResultException e) {
				logger.error("Exception in getting query data" + e);
				// postingFee=serviceOrder.getPostingFee().doubleValue();
				logger.info("inside NoResultExceptions >>" + postingFee);
			}
		}
		logger.info("ended getRefundPostingFee>>");
		return postingFee;
	}
	
	//SLT-3835 Getting event_id from action
	public String getCorrespondingActionId(String stateName) throws ServiceOrderException {
		logger.info("Inside getCorrespondingActionId>>"+stateName);
				String eventId = null;
				Query query = null;
				String hql = null;
				try{
					hql = "SELECT lem.event_id FROM lu_event_master lem WHERE lem.event_name=:stateName AND lem.event_type='CALLBACK'";
					query=em.createNativeQuery(hql);
					query.setParameter("stateName", stateName);
					eventId = String.valueOf(query.getSingleResult());
					
				}catch (NoResultException nre) {
					logger.info("Event is not found"+ nre.getMessage());
				}
				catch (Exception e) {
					
					throw new ServiceOrderException("Exception fetching the event id",e);
				}
				return eventId;
			}
}
