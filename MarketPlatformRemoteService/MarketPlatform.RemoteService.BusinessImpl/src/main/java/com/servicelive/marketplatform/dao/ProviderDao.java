package com.servicelive.marketplatform.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.provider.ProviderFirm;
import com.servicelive.domain.provider.ServiceProvider;

import com.newco.marketplace.dto.vo.providerSearch.ProviderDetailsVO;


public class ProviderDao extends BaseEntityDao implements IProviderDao {
	protected final Logger logger = Logger.getLogger(getClass());

    public ServiceProvider findProviderResourceById(long providerRsrcId) {
        return entityMgr.find(ServiceProvider.class, (int)providerRsrcId);
    }

    public ProviderFirm findProviderById(long providerId) {
        return entityMgr.find(ProviderFirm.class, (int)providerId);
    }
    
    public ServiceProvider  findProviderAdminResource(long providerId) {
    	 Query q = entityMgr.createQuery("SELECT provAdmn FROM ServiceProvider provAdmn WHERE provAdmn.providerFirm.id = :providerId and provAdmn.primaryInd = true");
         q.setParameter("providerId", (int)providerId);
         List result = q.getResultList();
         if (result.size() > 0) {
             return (ServiceProvider) result.get(0);
}
         return null;
    }

	public List<ServiceProvider> findProviderResourcesByIds(
			List<Integer> resourceIds) {
		logger.info("Inside ProviderDao fetching prov details");
		 Query q = entityMgr.createQuery("SELECT prov FROM ServiceProvider prov WHERE prov.id IN (:providerIds)");
         q.setParameter("providerIds",resourceIds);
 		logger.info(q.toString());
		return (List<ServiceProvider>) q.getResultList();
	}
	
	
	
	public List<ProviderDetailsVO> findProviderResourcesDetailsByIds(List<Integer> resourceIds) {
		
		List<ProviderDetailsVO> list = new ArrayList<ProviderDetailsVO>();
		StringBuffer resIdList = new StringBuffer("");
		
		int count = 0;
		
		for(Integer id : resourceIds){
			resIdList = resIdList.append(id);
			count++;
			if (count<resourceIds.size()){
				resIdList = resIdList.append(", ");
			}
		}
		String resourceIdList = resIdList.toString();
			String hql = "SELECT resource.resource_id,resource.vendor_id,vendor.business_name,contact.first_name," +
					"contact.last_name FROM vendor_resource resource JOIN vendor_hdr vendor ON (resource.vendor_id=vendor.vendor_id) "+
					"JOIN contact contact ON (resource.contact_id = contact.contact_id )WHERE resource_id IN ("+ resourceIdList +")";
			Query query = entityMgr.createNativeQuery(hql);
				logger.info("Generated provider fetch query1"+hql);
			try {
				@SuppressWarnings("rawtypes")
				Iterator iterator = query.getResultList().iterator();
				if(iterator!=null) {
					 logger.info("not null");
					 while(iterator.hasNext()) {
						 ProviderDetailsVO vo = new ProviderDetailsVO();
						 Object[] tuple = (Object[]) iterator.next();
						
												 vo.setResourceId((Integer)tuple[0]);
						 	vo.setVendorId((Integer)tuple[1]);
					 						 vo.setBusinessName((String)tuple[2]);
						 vo.setFirstName((String)tuple[3]);
						 vo.setLastName((String)tuple[4]);
						 list.add(vo);					 
					 }
				 }
				
			} catch (NoResultException e) {
				logger.info("Exception occured in findProviderResourcesDetailsByIds"+e);
			}
			return list;
	}
}