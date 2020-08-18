package com.newco.marketplace.persistence.iDao.alert;

import java.util.List;

import com.newco.marketplace.dto.vo.alert.WfStateTransitionVO;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * @author sahmad7
 * 
 */
public interface WfStateTransitionDao {

	public int update(WfStateTransitionVO wfStateTransitionVO)
			throws DataServiceException;

	public WfStateTransitionVO query(WfStateTransitionVO wfStateTransitionVO)
			throws DataServiceException;

	public List queryList(WfStateTransitionVO wfStateTransitionVO)
			throws DataServiceException;

	public WfStateTransitionVO insert(WfStateTransitionVO wfStateTransitionVO)
			throws DataServiceException;

}
