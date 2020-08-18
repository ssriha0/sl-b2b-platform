/**
 * 
 */
package com.servicelive.spn.dao.common.impl;

import java.util.List;

import com.servicelive.domain.lookup.LookupSPNWorkflowEntity;
import com.servicelive.domain.spn.workflow.SPNWorkflowState;
import com.servicelive.domain.spn.workflow.SPNWorkflowStateHistory;
import com.servicelive.domain.spn.workflow.SPNWorkflowStatePK;
import com.servicelive.spn.dao.AbstractBaseDao;
import com.servicelive.spn.dao.common.SPNWorkflowDao;

/**
 * @author hoza
 *
 */
public class SPNWorkflowDaoImpl extends AbstractBaseDao implements
		SPNWorkflowDao {

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.common.SPNWorkflowDao#delete(com.servicelive.domain.spn.workflow.SPNWorkflowState)
	 */
	@SuppressWarnings("unused")
	public void delete(SPNWorkflowState entity) throws Exception {
			//DO NOT DELETE and WF INFORMATION
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.common.SPNWorkflowDao#findAll(int[])
	 */
	@SuppressWarnings("unchecked")
	public List<SPNWorkflowState> findAll(int... rowStartIdxAndCount) throws Exception {
		return ( List<SPNWorkflowState> ) super.findAll("SPNWorkflowState", rowStartIdxAndCount);
	}


	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.common.SPNWorkflowDao#findByProperty(java.lang.String, java.lang.Object, int[])
	 */
	@SuppressWarnings("unchecked")
	public List<SPNWorkflowState> findByProperty(String propertyName, Object value, int... rowStartIdxAndCount) throws Exception {
		return ( List<SPNWorkflowState> ) super.findByProperty("SPNWorkflowState", propertyName, value, rowStartIdxAndCount);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.common.SPNWorkflowDao#save(com.servicelive.domain.spn.workflow.SPNWorkflowState)
	 */
	public void save(SPNWorkflowState entity) throws Exception {
		super.save(entity);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.common.SPNWorkflowDao#update(com.servicelive.domain.spn.workflow.SPNWorkflowState)
	 */
	public SPNWorkflowState update(SPNWorkflowState entity) throws Exception {
		return (SPNWorkflowState) super.update(entity);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.spn.dao.common.SPNWorkflowDao#findById(java.lang.String, java.lang.Integer)
	 */
	public SPNWorkflowState findById(String entity, Integer entityId)
			throws Exception {
		LookupSPNWorkflowEntity luEntity = new LookupSPNWorkflowEntity();
		luEntity.setId(entity);
		SPNWorkflowStatePK pk = new SPNWorkflowStatePK();
		pk.setEntityId(entityId);
		pk.setWorkflowEntity(luEntity);
		return ( SPNWorkflowState) super.findById(SPNWorkflowState.class, pk);
	}
	
	public void saveHistory(SPNWorkflowStateHistory entity) throws Exception{
		super.save(entity);
	}

}
