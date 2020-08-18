package com.servicelive.spn.services.interfaces;

import java.sql.SQLException;
import java.util.List;

import com.servicelive.domain.lookup.LookupSPNWorkflowState;
import com.servicelive.spn.common.detached.SPNAuditorQueueCountVO;

public interface IAuditorQueueCountService
{

	/**
	 * 
	 * @param buyerId
	 * @return List<SPNAuditorQueueCountVO>
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public abstract List<SPNAuditorQueueCountVO> getCounts(Integer buyerId) throws SQLException;

	/**
	 * 
	 * @param buyerId
	 * @param state
	 * @return List<SPNAuditorQueueCountVO>
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	public abstract List<SPNAuditorQueueCountVO> getCountsWithDetail(Integer buyerId, LookupSPNWorkflowState state) throws SQLException;

}