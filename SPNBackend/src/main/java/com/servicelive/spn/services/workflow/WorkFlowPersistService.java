/**
 * 
 */
package com.servicelive.spn.services.workflow;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.business.EmailAlertBO;
import com.newco.marketplace.business.businessImpl.alert.AlertTask;
import com.servicelive.domain.spn.detached.Email;
import com.servicelive.domain.spn.workflow.SPNWorkflowState;
import com.servicelive.domain.spn.workflow.SPNWorkflowStateHistory;
import com.servicelive.domain.spn.workflow.SPNWorkflowStateHistoryPK;
import com.servicelive.spn.common.util.CalendarUtil;
import com.servicelive.spn.dao.common.SPNWorkflowDao;
import com.servicelive.spn.services.BaseServices;
import com.servicelive.spn.services.email.MailQueryStringFormatter;

/**
 * @author hoza
 *
 */
public class WorkFlowPersistService extends BaseServices {
	/* 
	 * (non-Javadoc)
	 * @see com.servicelive.spn.services.BaseServices#handleDates(java.lang.Object)
	 */
	@SuppressWarnings("unused")
	@Override
	protected void handleDates(Object entity) {
		// do nothing
	}


	private SPNWorkflowDao spnWorkflowDao;
	private WFTask workflowTask;
	private EmailAlertBO emailAlertBO;
	private MailQueryStringFormatter mailQueryStringFormatter;
	/**
	 * 
	 * @param entity
	 * @param statetoUpdate
	 * @param entityId
	 * @param modifiedBy
	 * @return boolean
	 */
	@Transactional ( propagation = Propagation.REQUIRED)
	public boolean signal(String entity, String statetoUpdate, Integer entityId, String modifiedBy) {
		SPNWorkflowState wfstate = workflowTask.getTask(entity, statetoUpdate, entityId, modifiedBy);
		return signal(wfstate);
	}
	
	/**
	 * 
	 * @param entity
	 * @param statetoUpdate
	 * @param entityId
	 * @param comments
	 * @param modifiedBy
	 * @return boolean
	 */
	@Transactional ( propagation = Propagation.REQUIRED)
	public boolean signal(String entity, String statetoUpdate, Integer entityId, String comments, String modifiedBy) {
		SPNWorkflowState wfstate = workflowTask.getTask(entity, statetoUpdate, entityId, modifiedBy);
		wfstate.setComments(comments);
		return signal(wfstate);
	}
	
	/**
	 * 
	 * @param entity
	 * @param statetoUpdate
	 * @param entityId
	 * @param modifiedBy
	 * @param emails
	 * @return boolean
	 */
	@Transactional ( propagation = Propagation.REQUIRED)
	public boolean signal(String entity, String statetoUpdate, Integer entityId, String modifiedBy, List<Email> emails) {
		SPNWorkflowState wfstate = workflowTask.getTask(entity, statetoUpdate, entityId, modifiedBy);
		return signal(wfstate, emails);
	}
	
	@Transactional ( propagation = Propagation.REQUIRED)
	private boolean signal(SPNWorkflowState wfstate) {
		return signal(wfstate, new ArrayList<Email>());
	}
	/**
	 * 
	 * @param wfstate
	 * @return boolean
	 */
	@Transactional ( propagation = Propagation.REQUIRED)
	private boolean signal(SPNWorkflowState wfstate, List<Email> emails) {
		try {
			
			AlertTask alertTask=null;
			//spnWorkflowDao.refresh(wfstate);
			spnWorkflowDao.save(wfstate);
			//send the associated email
			logger.info("Inserting to alert Task");
			for(Email email : emails) {
				alertTask=mailQueryStringFormatter.insertAlert(email);
				emailAlertBO.insertToAlertTask(alertTask);
				//emailService.sendEmail(email);
				
			}
			return true;
		} catch (Exception e) {
			logger.debug(e);
		}
		return false;
	}
	
	@Transactional ( propagation = Propagation.REQUIRED)
	public void saveWorkflowHistory(String wfEntityId, String wfEntityState, Integer entityId, String modifiedBy, String comments){
		try{
			SPNWorkflowStateHistoryPK spnHistoryPK  = new SPNWorkflowStateHistoryPK();
			spnHistoryPK.setEntityId(entityId);
			spnHistoryPK.setWfEntityId(wfEntityId);
			SPNWorkflowStateHistory spnHistory = new SPNWorkflowStateHistory();
			spnHistory.setWfEntityState(wfEntityState);
			spnHistory.setComments(comments);
			spnHistory.setModifiedBy(modifiedBy);
			spnHistory.setModifiedDate(CalendarUtil.getNow());
			spnHistory.setCreatedDate(CalendarUtil.getNow());
			spnHistory.setArchiveDate(CalendarUtil.getNow());
			spnHistory.setSpnWorkflowHistoryPk(spnHistoryPK);
			spnWorkflowDao.saveHistory(spnHistory);
		}catch (Exception e) {
			logger.debug(e);
		}
		
	}
	/**
	 * 
	 * @param entity
	 * @param entityId
	 * @return SPNWorkflowState
	 * @throws Exception
	 */
	public SPNWorkflowState getWfState(String entity, Integer entityId) throws Exception{
		return spnWorkflowDao.findById(entity, entityId);
		
	}


	/**
	 * @return the spnWorkflowDao
	 */
	public SPNWorkflowDao getSpnWorkflowDao() {
		return spnWorkflowDao;
	}


	/**
	 * @param spnWorkflowDao the spnWorkflowDao to set
	 */
	public void setSpnWorkflowDao(SPNWorkflowDao spnWorkflowDao) {
		this.spnWorkflowDao = spnWorkflowDao;
	}
	/**
	 * @param workflowTask the workflowTask to set
	 */
	public void setWorkflowTask(WFTask workflowTask) {
		this.workflowTask = workflowTask;
	}
	public MailQueryStringFormatter getMailQueryStringFormatter() {
		return mailQueryStringFormatter;
	}

	public void setMailQueryStringFormatter(
			MailQueryStringFormatter mailQueryStringFormatter) {
		this.mailQueryStringFormatter = mailQueryStringFormatter;
	}

	public EmailAlertBO getEmailAlertBO() {
		return emailAlertBO;
	}

	public void setEmailAlertBO(EmailAlertBO emailAlertBO) {
		this.emailAlertBO = emailAlertBO;
	}

}