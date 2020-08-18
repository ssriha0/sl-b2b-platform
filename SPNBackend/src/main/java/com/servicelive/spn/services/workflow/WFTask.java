package com.servicelive.spn.services.workflow;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.servicelive.domain.lookup.LookupSPNWorkflowEntity;
import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.domain.spn.workflow.SPNWorkflowState;
import com.servicelive.domain.spn.workflow.SPNWorkflowStatePK;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.dao.common.SPNWorkflowDao;

/**
 * 
 * @author hoza
 *
 */
public class WFTask
{	
	private final Log logger  =  LogFactory.getLog(this.getClass());
	private SPNWorkflowDao spnWorkflowDao;
	
	/**
	 * 
	 * @param entity
	 * @param statetoUpdate
	 * @param entityId
	 * @param modifiedBy
	 * @return SPNWorkflowState
	 */
	@Transactional ( propagation = Propagation.SUPPORTS)
	public SPNWorkflowState getTask(String entity, String statetoUpdate, Integer entityId,String modifiedBy) {
		// 1) get Entity
		// 2) check if exist , if exist save to history the original 
		// 3) if not exist insert the row in history that when created
		SPNWorkflowState wfstate;
		try {
			wfstate = spnWorkflowDao.findById(entity, entityId);
		} catch (Exception e1) {
			logger.debug(e1);
			wfstate = null;
		}
		//if nothing exists
		if(wfstate == null){
			wfstate = getWFStateObject(entity, statetoUpdate, entityId,	modifiedBy);
		} else {
			wfstate.setModifiedDate(CalendarUtil.getNow());
			wfstate.setModifiedBy(modifiedBy);
			LookupSPNWorkflowState workflowState = new LookupSPNWorkflowState();
			workflowState.setId(statetoUpdate);
			wfstate.setWorkflowState(workflowState);
		}
		return wfstate;
		
	}

	/**
	 * @param entity
	 * @param statetoUpdate
	 * @param entityId
	 * @param modifiedBy
	 * @return
	 */
	private SPNWorkflowState getWFStateObject(String entity, String statetoUpdate, Integer entityId, String modifiedBy) {
		LookupSPNWorkflowEntity luEntity = new LookupSPNWorkflowEntity();
		luEntity.setId(entity);

		SPNWorkflowStatePK pk = new SPNWorkflowStatePK();
		pk.setEntityId(entityId);
		pk.setWorkflowEntity(luEntity);
		
		LookupSPNWorkflowState luState = new LookupSPNWorkflowState();
		luState.setId(statetoUpdate);

		SPNWorkflowState wfstate = new SPNWorkflowState();
		wfstate.setSpnWorkflowPk(pk);
		wfstate.setModifiedBy(modifiedBy);
		wfstate.setWorkflowState(luState);
		wfstate.setCreatedDate(CalendarUtil.getNow());
		wfstate.setModifiedDate(CalendarUtil.getNow());
		return wfstate;
	}

	/**
	 * @param spnWorkflowDao the spnWorkflowDao to set
	 */
	public void setSpnWorkflowDao(SPNWorkflowDao spnWorkflowDao) {
		this.spnWorkflowDao = spnWorkflowDao;
	}
}