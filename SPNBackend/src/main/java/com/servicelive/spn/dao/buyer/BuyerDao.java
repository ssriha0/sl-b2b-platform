package com.servicelive.spn.dao.buyer;

import java.util.List;

import com.servicelive.domain.buyer.Buyer;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author hoza
 *
 */
public interface BuyerDao extends BaseDao {
	/**
	 * 
	 * @param id
	 * @return Buyer
	 * @throws Exception
	 */
	public Buyer findById(Integer id) throws Exception;
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<Buyer> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;

}
