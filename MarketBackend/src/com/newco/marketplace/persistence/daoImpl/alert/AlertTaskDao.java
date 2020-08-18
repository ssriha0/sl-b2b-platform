package com.newco.marketplace.persistence.daoImpl.alert;

import java.util.List;

import com.newco.marketplace.dto.vo.alert.AlertTaskVO;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * @author sahmad7
 * 
 */
public interface AlertTaskDao {

	public int update(AlertTaskVO taskTableVO)
			throws DataServiceException;

	public AlertTaskVO query(AlertTaskVO taskTableVO)
			throws DataServiceException;

	public List<AlertTaskVO> queryList(AlertTaskVO taskTableVO)
			throws DataServiceException;

	public AlertTaskVO insert(AlertTaskVO taskTableVO)
			throws DataServiceException;

}
