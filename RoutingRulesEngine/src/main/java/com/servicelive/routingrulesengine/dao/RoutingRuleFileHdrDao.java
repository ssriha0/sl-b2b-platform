package com.servicelive.routingrulesengine.dao;

import java.util.List;

import com.servicelive.domain.routingrules.RoutingRuleFileHdr;
import com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO;

public interface RoutingRuleFileHdrDao {
	
	public List<RoutingRuleFileHdr> fetchFilesForProcessing();
	public RoutingRuleFileHdr save(RoutingRuleFileHdr routingRuleFileHdr) throws Exception;
	public RoutingRuleFileHdr update(RoutingRuleFileHdr routingRuleFileHdr) throws Exception;
	public void changeStatusToProccessing(List<Integer> fileIds) throws Exception;
	
	/**
	 * Get the uploaded files for the user
	 * @param pagingCriteria
	 * @return List<RoutingRuleFileHdr>
	 */
	public List<RoutingRuleFileHdr> getUploadedFilesForBuyer
		(RoutingRulesPaginationVO pagingCriteria, Integer buyerId) 
		throws Exception; 
	
	/**
	 *  Check if the file exists
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public boolean checkIfFileExists(String fileName, Integer buyerId) throws Exception;
	
	/**
	 * Get the total number of files uploaded
	 * @return Integer
	 * @throws Exception
	 */
	public Integer getUploadedFilesCount(Integer buyerId) throws Exception;

}
