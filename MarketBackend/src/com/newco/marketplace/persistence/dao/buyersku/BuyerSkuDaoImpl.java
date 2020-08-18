package com.newco.marketplace.persistence.dao.buyersku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.newco.marketplace.interfaces.OrderConstants;
import com.servicelive.autoclose.dao.impl.AbstractBaseDao;
import com.servicelive.domain.so.BuyerOrderSku;
import com.servicelive.domain.lookup.LookupServiceType;

public class BuyerSkuDaoImpl extends AbstractBaseDao implements BuyerSkuDao {
	
	public List<Map<Integer, String>> findSKUsForManageScope(String buyerID) {
		String hql = "select new map (b.skuId as skuId, b.sku as sku) from BuyerOrderSku b where b.manageScopeInd = 1 and b.buyerId = :buyerID";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("buyerID", new Long(buyerID));
			
		
		try {			
			@SuppressWarnings("unchecked")
			List<Map<Integer, String>> resultDao = query.getResultList();
			return resultDao;
		}
		catch (NoResultException e) {
			logger.info("findSKUsForManageScope NoResultException");
			return null;
		}
	}
	
	public List<Map<Integer, String>> findSKUsForCategory(final String buyerID,final Integer soMainCatId){
		try{
		String hql ="SELECT sku_id,sku FROM buyer_sku WHERE sku_id IN(SELECT sku_id FROM buyer_sku_task WHERE category_node_id"
		 +" IN(SELECT node_id FROM skill_tree WHERE root_node_id = :soMainCatId))AND manage_scope_ind = 1 AND buyer_id = :buyerId";
		 
			Query query = getEntityManager().createNativeQuery(hql);
			query.setParameter("buyerId", buyerID);
			query.setParameter("soMainCatId", soMainCatId);
			Iterator hdrIterator = query.getResultList().iterator();
			List<Map<Integer, String>> skuList= new ArrayList<Map<Integer, String>>();
			while ( hdrIterator.hasNext() ) {
				Map<Integer, String> skuMap = new HashMap<Integer, String>();
				Object[] tuple = (Object[]) hdrIterator.next();
				Integer skuId = (Integer) tuple[0];
				String sku = (String) tuple[1];
				skuMap.put(skuId, sku);
				skuList.add(skuMap);
	}
			return skuList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	public String getSkuDivisionException(final String primarySku)
	{
		try{
			String hql ="SELECT division FROM buyer_sku_division_exception WHERE sku = :primarySku";
		
				Query query = getEntityManager().createNativeQuery(hql);
				query.setParameter("primarySku", primarySku);
				String division= (String) query.getSingleResult();
				return division;
			}catch(Exception e){				
				e.printStackTrace();
				return null;
			}
	}
	
	public List<Map<Integer, String>> findSKUsForDivision(final String division,final String buyerID){
		
		try{
			String hql ="SELECT bs.sku_id, bs.sku FROM buyer_sku bs JOIN buyer_sku_division_map bsd ON bsd.sku = bs.sku "
			 +"WHERE bsd.division=:division AND bs.manage_scope_ind=1 AND bs.buyer_id=:buyerId";
			 
				Query query = getEntityManager().createNativeQuery(hql);
				query.setParameter("buyerId", buyerID);
				query.setParameter("division", division);
				Iterator hdrIterator = query.getResultList().iterator();
				List<Map<Integer, String>> skuList= new ArrayList<Map<Integer, String>>();
				while ( hdrIterator.hasNext() ) {
					Map<Integer, String> skuMap = new HashMap<Integer, String>();
					Object[] tuple = (Object[]) hdrIterator.next();
					Integer skuId = (Integer) tuple[0];
					String sku = (String) tuple[1];
					skuMap.put(skuId, sku);
					skuList.add(skuMap);
				}
				return skuList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public BuyerOrderSku findSkuBySkuId(Integer skuId) {
		String hql = "select b from BuyerOrderSku b where b.skuId = :skuID";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("skuID", new Long(skuId));
			
		
		try {			
			@SuppressWarnings("unchecked")
			BuyerOrderSku result = (BuyerOrderSku)query.getSingleResult();
			return result;
		}
		catch (NoResultException e) {
			logger.info("findSKUsForManageScope NoResultException");
			return null;
		}
	}
	
	public List<LookupServiceType> fetchSkills(Integer node) {
		String hql = "SELECT root_node_id  FROM skill_tree	WHERE node_id = :mainCategory";
		Query query = getEntityManager().createNativeQuery(hql);
		query.setParameter("mainCategory", node);		
		Integer mainCat =null;
		try {
			mainCat = (Integer)query.getSingleResult();				
	
		} catch (NoResultException e) {
			logger.error("ManageReasonCodeDaoImpl.checkInSO NoResultException");
			return null;
		}		
		hql = "select serviceType from LookupServiceType serviceType where serviceType.skillNodeId.id = :mainCategory order by serviceType.description";
		query = getEntityManager().createQuery(hql);
		query.setParameter("mainCategory",mainCat);
		
		try {			
			@SuppressWarnings("unchecked")
			List<LookupServiceType> result = (List<LookupServiceType>)query.getResultList();
			return result;
		}
		catch (NoResultException e) {
			logger.info("fetchSkills NoResultException");
			return null;
		}
	}
	public Integer getSoLevelFundingTypeIdForBuyer(String soId) {
		logger.info("Querying so_hdr  table for funding type id");
		Integer fundingTypeId = 0;
		String hql = "SELECT funding_type_id FROM so_hdr WHERE so_id = :soId";
		try {
			Query q = getEntityManager().createNativeQuery(hql);
			q.setParameter("soId",soId);
			fundingTypeId = (Integer) q.getSingleResult();
		} catch (NoResultException e) {
			logger.info("Exception in getting funding type id for so_id" + soId + "because of" + e.getMessage());
			return fundingTypeId;
		}
		return fundingTypeId;
	}
}