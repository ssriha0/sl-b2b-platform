package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupSPNMeetAndGreetState;
import com.servicelive.spn.dao.BaseDao;

/**
 * 
 * @author svanloon
 *
 */
public interface LookupSPNMeetAndGreetStateDao extends BaseDao {
	/**
	 * 
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSPNMeetAndGreetState> findAll ( int... rowStartIdxAndCount) throws Exception;
	/**
	 * 
	 * @param id
	 * @return LookupSPNDocumentType
	 * @throws Exception
	 */
	public LookupSPNMeetAndGreetState findById(String id) throws Exception ;
	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	public List<LookupSPNMeetAndGreetState> findByProperty(String propertyName, Object value, int...rowStartIdxAndCount) throws Exception;

}
