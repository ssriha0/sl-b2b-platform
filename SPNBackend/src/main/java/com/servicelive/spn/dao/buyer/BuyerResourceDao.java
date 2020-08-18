package com.servicelive.spn.dao.buyer;

import java.util.List;

import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author cgarcia
 *
 */
public interface BuyerResourceDao extends BaseDao {

	/**
	 * 
	 * @param id
	 * @return Buyer
	 * @throws Exception
	 */
	public BuyerResource findById(Integer id) throws Exception;

	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<BuyerResource> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;
	
	/**
	 * @param propertyName 
	 * @param value 
	 * @param rowStartIdxAndCount 
	 * @return  List
	 * @throws Exception 
	 * 
	 */
	public List<Integer> findCompanyRoleIdByProperty(String propertyName, Object value,	int... rowStartIdxAndCount) throws Exception;

}
