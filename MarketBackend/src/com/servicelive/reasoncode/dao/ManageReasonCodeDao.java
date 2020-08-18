package com.servicelive.reasoncode.dao;

import java.util.List;

import com.servicelive.domain.reasoncodemgr.ReasonCode;
import com.servicelive.domain.reasoncodemgr.ReasonCodeHist;
import com.servicelive.domain.reasoncodemgr.ReasonCodeTypes;

/**
 * 
 *
 */ 
public interface ManageReasonCodeDao 
{
	/**
	 * 
	 * @param buyerId
	 * @return List<RoutingRuleBuyerAssoc>
	 */
	public List<ReasonCode> getAllCancelReasonCodes(int buyerId, String type);
	public List<ReasonCode> getAllReasonCodes(int buyerId);	
	public String delete(ReasonCode rc);
	public String add(ReasonCode rc);
	public String checkInSO(int reasonCodeId,String type,int buyerId,String reasonCode);
	public void logHistory(ReasonCodeHist rc);
	public String getReasonCodeById(Integer id);
	public List<ReasonCode> getActiveReasonCodeByType(int buyerId,String type);
	public List<ReasonCodeTypes> getReasonTypes();
	public List<ReasonCodeHist> getHistory(int buyerId);
}
