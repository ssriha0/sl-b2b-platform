package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.spn.dao.BaseDao;
import com.servicelive.domain.lookup.LookupVendorCredentialCategory;
import com.servicelive.domain.spn.network.SPNExclusionsVO;
 /** 
  * @author Carlos  Garcia
 *
 */

public interface LookupVendorCredentialCategoryDao extends BaseDao{
	
	/**
	 * 
	 * @return List
	 * @throws Exception
	 */
	public List<LookupVendorCredentialCategory> getVendorCredentialCategories()  throws Exception;
	
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupVendorCredentialCategory> findAll (int... rowStartIdxAndCount) throws Exception;
	
	/**
	 * 
	 * @param id
	 * @return LookupVendorCredentialCategory
	 * @throws Exception
	 */
	public LookupVendorCredentialCategory findById(Integer id) throws Exception ;

	/**
	 * @param typeIds
	 * @param networkId
	 * @return
	 */
	public List<SPNExclusionsVO> getExclusionsForCredentialTypes(List<Integer> typeIds, int networkId) throws Exception;

	/**
	 * @param categoryIds
	 * @param networkId
	 * @return
	 */
	public List<SPNExclusionsVO> getExclusionsForCredentialCategories(List<Integer> categoryIds, int networkId)throws Exception;
		
}
