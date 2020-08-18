package com.servicelive.spn.dao.lookup.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.lookup.LookupSPNWorkFlowStateDao;

/**
 * 
 * @author sldev
 *
 */
@Repository("lookupSPNWorkflowStateDao")
public class LookupSPNWorkFlowStateDaoImpl extends AbstractBaseDao implements LookupSPNWorkFlowStateDao{
	@SuppressWarnings("unchecked")
	public List<LookupSPNWorkflowState> findAll(int... rowStartIdxAndCount) throws Exception {
		List <LookupSPNWorkflowState> orderList = (List <LookupSPNWorkflowState>)super.findAllOrderByDesc("LookupMarket", "market_name", rowStartIdxAndCount);
		return orderList;
	}

	public LookupSPNWorkflowState findById(Integer id) throws Exception {
		return (LookupSPNWorkflowState) super.findById(LookupSPNWorkflowState.class, id);
	}

	/**
	 * 
	 * @param propertyName
	 * @param value
	 * @param rowStartIdxAndCount
	 * @return List
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LookupSPNWorkflowState> findByProperty(String propertyName, Object value,	int... rowStartIdxAndCount) throws Exception {
		return (List<LookupSPNWorkflowState>) super.findByProperty("LookupSPNWorkflowState",propertyName,value, rowStartIdxAndCount);
	}

	public List<LookupSPNWorkflowState> getWorkflowStates() throws Exception {
		return findAll();
	}
	
	/**
	 * get list of workFlow states for given groupType
	 * @param groupType
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<LookupSPNWorkflowState> getWorkflowStatesForGroupType(String groupType)  throws Exception{
		List<LookupSPNWorkflowState> workFlowStatesList = findByProperty("groupType",groupType);
		return workFlowStatesList;
	}

}
