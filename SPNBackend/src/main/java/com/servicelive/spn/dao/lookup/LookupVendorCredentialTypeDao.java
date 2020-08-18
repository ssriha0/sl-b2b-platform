package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupVendorCredentialType;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author Carlos  Garcia
 *
 */

public interface LookupVendorCredentialTypeDao extends BaseDao{
	
	/**
	 * 
	 * @return List
	 * @throws Exception
	 */
	public List<LookupVendorCredentialType> getVendorCredentialTypes()  throws Exception;
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupVendorCredentialType> findAll (int... rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param id
	 * @return LookupVendorCredentialType
	 * @throws Exception
	 */
	public LookupVendorCredentialType findById(Integer id) throws Exception ;
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public  List<LookupVendorCredentialType> findAllbyRemovingInsurance() throws Exception ;
		
}
