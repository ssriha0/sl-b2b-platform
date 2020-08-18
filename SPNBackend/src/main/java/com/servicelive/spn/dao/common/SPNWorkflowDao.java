/**
 * 
 */
package com.servicelive.spn.dao.common;

import java.util.List;

import com.servicelive.domain.spn.workflow.SPNWorkflowState;
import com.servicelive.domain.spn.workflow.SPNWorkflowStateHistory;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author hoza
 *
 */
public interface SPNWorkflowDao  extends BaseDao {
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<SPNWorkflowState> findAll ( int... rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param entity
	 * @param entityId
	 * @return SPNWorkflowState
	 * @throws Exception
	 */
	public SPNWorkflowState findById(String entity, Integer entityId) throws Exception;
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<SPNWorkflowState> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void save(SPNWorkflowState entity) throws Exception;
	/**
	 * 
	 * @param entity
	 * @return SPNWorkflowState
	 * @throws Exception
	 */
	public SPNWorkflowState update(SPNWorkflowState entity) throws Exception;
	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void delete(SPNWorkflowState entity) throws Exception;
	
	public void saveHistory(SPNWorkflowStateHistory entity) throws Exception;
}
