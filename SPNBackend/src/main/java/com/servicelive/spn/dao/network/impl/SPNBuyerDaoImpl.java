/**
 * 
 */
package com.servicelive.spn.dao.network.impl;

import java.util.List;

import com.servicelive.domain.spn.network.SPNBuyer;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.network.SPNBuyerDao;
import javax.persistence.Query;

/**
 * @author hoza
 *
 */
public class SPNBuyerDaoImpl extends AbstractBaseDao implements SPNBuyerDao {

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.network.SPNBuyerDao#delete(com.servicelive.domain.spn.network.SPNBuyer)
	 */
	public void delete(SPNBuyer entity) throws Exception {
		super.delete(entity);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.network.SPNBuyerDao#findAll(int[])
	 */
	@SuppressWarnings("unchecked")
	public List<SPNBuyer> findAll(int... rowStartIdxAndCount) throws Exception {
		return (List<SPNBuyer>) super.findAll("SPNBuyer", rowStartIdxAndCount);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.network.SPNBuyerDao#findById(java.lang.Integer)
	 */
	public SPNBuyer findById(Integer id) throws Exception {
		return (SPNBuyer) super.findById(SPNBuyer.class, id) ;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.network.SPNBuyerDao#findByProperty(java.lang.String, java.lang.Object, int[])
	 */
	@SuppressWarnings("unchecked")
	public List<SPNBuyer> findByProperty(String propertyName, Object value,
			int... rowStartIdxAndCount) throws Exception {
		return (List<SPNBuyer>) super.findByProperty("SPNBuyer", propertyName, value,rowStartIdxAndCount);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.network.SPNBuyerDao#save(com.servicelive.domain.spn.network.SPNBuyer)
	 */
	public void save(SPNBuyer entity) throws Exception {
		super.save(entity);
		
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.network.SPNBuyerDao#update(com.servicelive.domain.spn.network.SPNBuyer)
	 */
	public SPNBuyer update(SPNBuyer entity) throws Exception {
		return (SPNBuyer) super.update(entity);
	}
	/**
	 * R11_0 SL_19387
	 * This method returns the value of that feature set.
	 * @param buyerId
	 * @param feature
	 */
	public String getBuyerFeatureSetValue(Integer buyerId, String feature)	throws Exception {
		String value = null;
		String hql="SELECT CAST(feature AS CHAR(100)) FROM buyer_feature_set WHERE buyer_id = :buyerId AND feature = :feature AND active_ind=1";

		Query query = getEntityManager().createNativeQuery(hql);
		query.setParameter("buyerId", buyerId);	
		query.setParameter("feature", feature);

		try {
			value = (String) query.getSingleResult();				
			return value;
		} catch (Exception e) {
			return null;
		}
		}

	}
