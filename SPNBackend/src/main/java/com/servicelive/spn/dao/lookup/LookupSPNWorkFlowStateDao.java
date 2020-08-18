package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.spn.dao.BaseDao;

/**
 * 
 * @author sldev
 *
 */
public interface LookupSPNWorkFlowStateDao extends BaseDao{
	/**
	 * Gets the list of all LookupStates objects
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSPNWorkflowState> getWorkflowStates()  throws Exception;
	
	/**
	 * Get List of workflow states for Provider Firm
	 * @param groupType
	 * @return
	 * @throws Exception
	 */
	public List<LookupSPNWorkflowState> getWorkflowStatesForGroupType(String groupType)  throws Exception;
	
	/**
	 * Gets the list of LookupStates objects
	 * @param rowStartIdxAndCount
	 * @return A List of LookupStates
	 * @throws Exception
	 */
	public List<LookupSPNWorkflowState> findAll (int... rowStartIdxAndCount) throws Exception;
	
	/**
	 * Gets a specific LookupStates object
	 * @param id
	 * @return  A LookupState object
	 * @throws Exception
	 */
	public LookupSPNWorkflowState findById(Integer id) throws Exception ;
	
	
	/**
	 * Gets a list of LookupStates objects for specific property value
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return  A List of LookupStates
	 * @throws Exception
	 */
	public List<LookupSPNWorkflowState> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;

}
