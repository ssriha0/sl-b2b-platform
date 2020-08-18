package com.servicelive.reasoncode.services;

import java.util.List;

import com.servicelive.domain.reasoncodemgr.ReasonCode;
import com.servicelive.domain.reasoncodemgr.ReasonCodeTypes;
import com.servicelive.domain.reasoncodemgr.ReasonCodeHist;

/**
 * 
 *
 */ 
public interface ManageReasonCodeService {
	List<ReasonCode> getAllCancelReasonCodes(int buyerId, String type);
	List<ReasonCode> getAllReasonCodes(int buyerId);	
	String add(ReasonCode rc);
	String delete(ReasonCode rc);
	String checkInSO(int reasonCodeId,String type,int buyerId,String reasonCode);
	List<ReasonCodeTypes> getReasonTypes();
	List<ReasonCodeHist> getHistory(int buyerId);
	
}

