package com.servicelive.routingrulesengine.dao.impl;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupRoutingAlertType;
import com.servicelive.routingrulesengine.dao.LookupRoutingAlertTypeDao;

/**
 * 
 *
 */
public class LookupRoutingAlertTypeDaoImpl extends AbstractBaseDao implements LookupRoutingAlertTypeDao {

	/**
	 * 
	 */
	public LookupRoutingAlertTypeDaoImpl() {
		super();
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.SUPPORTS)
	public LookupRoutingAlertType findById(Integer id) {
		try {
			List<LookupRoutingAlertType> list = (List<LookupRoutingAlertType>) super.findByProperty(LookupRoutingAlertType.class.getName(), "id", id);
			if(list == null || list.isEmpty()) {
				return null;
			}
			return list.iterator().next();

		} catch (Exception re) {
			logger.error("Exception thought impossible", re);
			return null;
		}
	}

	
	@Transactional(propagation=Propagation.SUPPORTS)
	public void insert(LookupRoutingAlertType entity) throws Exception {
		 super.save(entity);
	}

	@Transactional(propagation=Propagation.SUPPORTS)
	public void delete(LookupRoutingAlertType entity) throws Exception {
		 super.delete(entity);
	}

}