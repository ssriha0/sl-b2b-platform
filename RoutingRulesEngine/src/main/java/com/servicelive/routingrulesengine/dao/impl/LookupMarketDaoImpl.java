package com.servicelive.routingrulesengine.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.servicelive.domain.lookup.LookupMarket;
import com.servicelive.routingrulesengine.dao.LookupMarketDao;

/**
 * 
 * @author svanloon
 *
 */
public class LookupMarketDaoImpl extends AbstractBaseDao implements LookupMarketDao {

	private Logger logger = Logger.getLogger(LookupMarketDaoImpl.class);
	@SuppressWarnings("unchecked")
	public List<LookupMarket> findActive(){
		String hql = "from " + LookupMarket.class.getSimpleName();
		Query query = this.getEntityManager().createQuery(hql);
		return query.getResultList();
	}
	
	public Map<String,String> getMaprkets(List<String> markets){
		logger.info("Inside getMaprkets method ***");
		System.out.println("Inside getMaprkets method ***");
		Map<String,String> marketMap = new HashMap<String, String>();
		String sql = "select lookupMarket from LookupMarket lookupMarket where lookupMarket.id IN (:markets)";	
		Query query = this.getEntityManager().createQuery(sql);
		/*
		 * SLT-3884
		*/
		//		List<Integer> intMarkets = markets.stream().map(Integer::parseInt).collect(Collectors.toList());
			 List<Integer> intMarkets =new ArrayList<>();
			 Iterator<String> mIterator = markets.iterator(); 
			 while(mIterator.hasNext()) {
			 intMarkets.add(Integer.parseInt(mIterator.next()));
			 }
		query.setParameter("markets", intMarkets);
		try{			
			@SuppressWarnings("unchecked")
			List<LookupMarket> lookupList = query.getResultList();
			for(LookupMarket market: lookupList){
				String id = market.getId().toString();
				String value = market.getDescription();
				marketMap.put(id, value);
			}			
		 }catch(Exception e) {
			 logger.info("Exception in getMaprkets:"+e.getMessage());
			 e.printStackTrace();
			 return null;
		 }
		return marketMap;
	}
}
