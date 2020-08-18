/**
 *
 */
package com.servicelive.spn.dao.network;

import java.util.List;

import com.servicelive.domain.spn.network.SPNExclusionsVO;
import com.servicelive.domain.spn.network.SPNHeader;
import com.servicelive.spn.dao.BaseDao;

/**
 * @author hoza
 *
 */
public interface SPNHeaderDao  extends BaseDao {
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<SPNHeader> findAll ( int... rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param id
	 * @return SPNHeader
	 * @throws Exception
	 */
	public SPNHeader findById(Integer id) throws Exception;
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<SPNHeader> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;

	/**
	 * 
	 * @param spnId
	 * @return List
	 * @throws Exception
	 */
	public List<SPNHeader> findAliases(Integer spnId) throws Exception;
	/**
	 * 
	 * @return List<SPNHeader>
	 * @throws Exception
	 */
	public List<SPNHeader> findExpiredSPNNetworks() throws Exception;

	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void save(SPNHeader entity) throws Exception;
	/**
	 * 
	 * @param entity
	 * @return SPNHeader
	 * @throws Exception
	 */
	public SPNHeader update(SPNHeader entity) throws Exception;
	/**
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void delete(SPNHeader entity) throws Exception;
	/**
	 * 
	 * @param spnId
	 * @return int
	 * @throws Exception
	 */
	public int removeExistingApprovalCriteria(Integer spnId) throws Exception;
	
	/**
	 * @param spnId
	 * @throws Exception
	 */
	public void deleteNetworkOverrideInfo(Integer spnId) throws Exception;
	/**
	 * 
	 * @param spnId
	 * @return int
	 * @throws Exception
	 */
	public int removeExistingDocuments(Integer spnId) throws Exception;
	/**
	 * 
	 * @throws Exception
	 */
	public void flush() throws Exception;
	
	public Boolean hasExceptions(Integer spnId)throws Exception;
	
	/**
	 * @param spnId
	 * @throws Exception
	 * to delete exceptions
	 */
	public void removeExistingExceptions(Integer spnId)throws Exception;
	
	
	/**
	 * @param spnId
	 * @return
	 * to fetch exceptions
	 */
	public List<SPNExclusionsVO> getSPNExclusions(Integer spnId);
	
	
}
