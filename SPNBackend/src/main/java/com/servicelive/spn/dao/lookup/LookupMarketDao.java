package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupMarket;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author Carlos  Garcia
 *
 */

public interface LookupMarketDao extends BaseDao{
	
	/**
	 * 
	 * @return List
	 * @throws Exception
	 */
	public List<LookupMarket> getMarkets()  throws Exception;
	
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupMarket> findAll (int... rowStartIdxAndCount) throws Exception;
	
	/**
	 * 
	 * @param id
	 * @return LookupMarket
	 * @throws Exception
	 */
	public LookupMarket findById(Integer id) throws Exception ;
		
}
