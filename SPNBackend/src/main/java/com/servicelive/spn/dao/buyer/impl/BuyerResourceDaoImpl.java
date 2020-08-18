/**
 * 
 */
package com.servicelive.spn.dao.buyer.impl;

import java.util.List;

import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.buyer.BuyerResourceDao;

/**
 * @author cgarcia
 *
 */
public class BuyerResourceDaoImpl extends AbstractBaseDao implements BuyerResourceDao {


	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.buyer.BuyerDao#findById(java.lang.String)
	 */
	public BuyerResource findById(Integer id) throws Exception {
		return (BuyerResource) super.findById(BuyerResource.class, id);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.buyer.BuyerDao#findByProperty(java.lang.String, java.lang.Object, int[])
	 */
	@SuppressWarnings("unchecked")
	public List<BuyerResource> findByProperty(String propertyName, Object value, int... rowStartIdxAndCount) throws Exception {
		return ( List<BuyerResource> )super.findByProperty("BuyerResource", propertyName, value, rowStartIdxAndCount);
	}

	@SuppressWarnings("unchecked")
	public List<Integer> findCompanyRoleIdByProperty(String propertyName, Object value,	int... rowStartIdxAndCount) throws Exception {
		return ( List<Integer> )super.findByProperty("BuyerResource", propertyName, value, rowStartIdxAndCount);
	}
}
