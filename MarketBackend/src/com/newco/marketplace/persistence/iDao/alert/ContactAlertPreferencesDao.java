package com.newco.marketplace.persistence.iDao.alert;

import java.util.List;

import com.newco.marketplace.dto.vo.alert.ContactAlertPreferencesVO;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * @author sahmad7
 * 
 */
public interface ContactAlertPreferencesDao {

	public int update(ContactAlertPreferencesVO preferencesVO)
			throws DataServiceException;

	public ContactAlertPreferencesVO query(
			ContactAlertPreferencesVO preferencesVO)
			throws DataServiceException;

	public List queryList(ContactAlertPreferencesVO preferencesVO)
			throws DataServiceException;

	public ContactAlertPreferencesVO insert(
			ContactAlertPreferencesVO preferencesVO)
			throws DataServiceException;

}
