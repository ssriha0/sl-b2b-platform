package com.servicelive.spn.dao.lookup;

import java.util.List;

import com.servicelive.domain.lookup.LookupEmailTemplate;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.spn.dao.BaseDao;

/**
 * 
 * @author svanloo
 *
 */
public interface LookupEmailTemplateDao extends BaseDao {
	/**
	 * This method is used just to list all of the EmailTemplates
	 * 
	 * @param rowStartIdxAndCount
	 * @return list of LookupEmailTemplates
	 * @throws Exception shouldn't every be thrown
	 */
	public List<LookupEmailTemplate> findAll(int... rowStartIdxAndCount) throws Exception;

	/**
	 * 
	 * @param state
	 * @return the list of templates that 
	 * @throws Exception
	 */
	public List<LookupEmailTemplate> findByLookupSPNWorkflowState(LookupSPNWorkflowState state) throws Exception;

}
