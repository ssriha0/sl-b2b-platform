package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupResourceCredentialType;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author Carlos  Garcia
 *
 */

public interface LookupResourceCredentialTypeDao extends BaseDao{
	
	/**
	 * 
	 * @return List
	 * @throws Exception
	 */
	public List<LookupResourceCredentialType> getResourceCredentialTypes()  throws Exception;
	
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupResourceCredentialType> findAll (int... rowStartIdxAndCount) throws Exception;
	
	/**
	 * 
	 * @param id
	 * @return LookupResourceCredentialType
	 * @throws Exception
	 */
	public LookupResourceCredentialType findById(Integer id) throws Exception ;
		
}
