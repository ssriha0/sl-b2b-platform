package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupStates;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author Mahmud Khair
 *
 */
public interface LookupStatesDao extends BaseDao{
	
	/**
	 * Gets the list of all LookupStates objects
	 * @return List
	 * @throws Exception
	 */
	public List<LookupStates> findStatesNotBlackedOut()  throws Exception;
	
	/**
	 * Gets the list of LookupStates objects
	 * @param rowStartIdxAndCount
	 * @return A List of LookupStates
	 * @throws Exception
	 */
	public List<LookupStates> findAll (int... rowStartIdxAndCount) throws Exception;
	
	/**
	 * Gets a specific LookupStates object
	 * @param id
	 * @return  A LookupState object
	 * @throws Exception
	 */
	public LookupStates findById(Integer id) throws Exception ;
	
	
	/**
	 * Gets a list of LookupStates objects for specific property value
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return  A List of LookupStates
	 * @throws Exception
	 */
	public List<LookupStates> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;
}
