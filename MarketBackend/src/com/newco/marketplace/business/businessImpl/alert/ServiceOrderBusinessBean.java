/**
 * 
 */
package com.newco.marketplace.business.businessImpl.alert;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.alert.AlertTaskVO;
import com.newco.marketplace.dto.vo.alert.ContactAlertPreferencesVO;
import com.newco.marketplace.dto.vo.alert.StateTransitionVO;
import com.newco.marketplace.dto.vo.alert.WfStateTransitionVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.iDao.alert.AlertTaskDao;
import com.newco.marketplace.persistence.iDao.alert.ContactAlertPreferencesDao;
import com.newco.marketplace.persistence.iDao.alert.WfStateTransitionDao;

/**
 * @author sahmad7
 * 
 */
public class ServiceOrderBusinessBean {

	private final static Logger logger = Logger
			.getLogger(ServiceOrderBusinessBean.class.getName());

	private AlertTaskDao alertTaskDao;
	private ContactAlertPreferencesDao contactAlertPreferencesDao;
	private WfStateTransitionDao wfStateTransitionDao;

	public boolean newServiceOrder(Object stateObj) {
		AlertTaskVO alertTaskVO;
		StateTransitionVO stateTransitionVO = (StateTransitionVO)stateObj;
		WfStateTransitionVO wfStateTransitionVO = new WfStateTransitionVO();
		wfStateTransitionVO.setSourceState(stateTransitionVO.getSourceState());
		wfStateTransitionVO.setTargetState(stateTransitionVO.getTargetState());
		try {
			wfStateTransitionVO = wfStateTransitionDao.query(wfStateTransitionVO);
			ArrayList<Contact> contactList = stateTransitionVO.getContactList();
			for (int z = 0; z < contactList.size(); z++) {
				Contact contact = (Contact)contactList.get(z);
				ContactAlertPreferencesVO capvo = new ContactAlertPreferencesVO();
				capvo.setContactId(contact.getContactId());
				capvo = (ContactAlertPreferencesVO)contactAlertPreferencesDao.query(capvo);
				
				alertTaskVO = new AlertTaskVO();
				alertTaskVO.setWfTemplateId(wfStateTransitionVO.getTemplateId());
				alertTaskVO.setTargetKey(capvo.getContactId().toString());
				alertTaskVO.setPayload(stateTransitionVO.getPayload());
				alertTaskVO.setPayloadKey(stateTransitionVO.getPayloadKey());
				
				alertTaskVO.setCompletionIndicator(false);
				
				//Insert records into the TaskTable for each prefered alertType
				if(capvo.getEmailAlert()){
					alertTaskVO.setAlertTypeId(MPConstants.EMAIL);
					alertTaskDao.insert(alertTaskVO);
				}
				
				if(capvo.getSmsAlert()){
					alertTaskVO.setAlertTypeId(MPConstants.SMS);
					alertTaskDao.insert(alertTaskVO);
				}
				
				if(capvo.getPagerAlert()){
					alertTaskVO.setAlertTypeId(MPConstants.PAGER);
					alertTaskDao.insert(alertTaskVO);
				}	
			}

		} catch (DataServiceException dse) {
			logger.info(dse);
			logger.info("EXCEPTION11: " + dse);
			return false;
		}catch (Exception e){
			logger.info("EXCEPTION22: " + e);
		}
		return true;
	}

	public void acceptedServiceOrder(AlertTaskVO alertTaskVO) {
		try {

			alertTaskDao.update(alertTaskVO);

		} catch (DataServiceException dse) {
			logger.info(dse);
		}
	}

	public void rejectedServiceOrder(AlertTaskVO alertTaskVO) {
		try {

			alertTaskDao.update(alertTaskVO);

		} catch (DataServiceException dse) {
			logger.info(dse);
		}
	}

	public AlertTaskDao getTaskTableDao() {
		return alertTaskDao;
	}

	public void setTaskTableDao(AlertTaskDao alertTaskDao) {
		this.alertTaskDao = alertTaskDao;
	}

	public AlertTaskDao getAlertTaskDao() {
		return alertTaskDao;
	}

	public void setAlertTaskDao(AlertTaskDao alertTaskDao) {
		this.alertTaskDao = alertTaskDao;
	}

	public ContactAlertPreferencesDao getContactAlertPreferencesDao() {
		return contactAlertPreferencesDao;
	}

	public void setContactAlertPreferencesDao(
			ContactAlertPreferencesDao contactAlertPreferencesDao) {
		this.contactAlertPreferencesDao = contactAlertPreferencesDao;
	}

	public WfStateTransitionDao getWfStateTransitionDao() {
		return wfStateTransitionDao;
	}

	public void setWfStateTransitionDao(WfStateTransitionDao wfStateTransitionDao) {
		this.wfStateTransitionDao = wfStateTransitionDao;
	}
}
