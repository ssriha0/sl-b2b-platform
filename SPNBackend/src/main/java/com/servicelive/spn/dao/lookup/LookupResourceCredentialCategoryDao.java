package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupResourceCredentialCategory;
import com.servicelive.domain.spn.network.SPNExclusionsVO;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author Carlos  Garcia
 *
 */

public interface LookupResourceCredentialCategoryDao extends BaseDao{
	
	/**
	 * 
	 * @return List
	 * @throws Exception
	 */
	public List<LookupResourceCredentialCategory> getResourceCredentialCategories()  throws Exception;
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupResourceCredentialCategory> findAll (int... rowStartIdxAndCount) throws Exception;
	
	/**
	 * 
	 * @param id
	 * @return LookupResourceCredentialCategory
	 * @throws Exception
	 */
	public LookupResourceCredentialCategory findById(Integer id) throws Exception ;
	
	/**
	 * @param typeIds
	 * @param networkId
	 * @return
	 */
	public List<SPNExclusionsVO> getExclusionsForCredentialTypes (
			List<Integer> typeIds, int networkId)throws Exception;
	
	
	/**
	 * @param categoryIds
	 * @param networkId
	 * @return
	 */
	public List<SPNExclusionsVO> getExclusionsForCredentialCategories(
			List<Integer> categoryIds, int networkId)throws Exception;
		
}
