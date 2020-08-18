package com.servicelive.reasoncode.services.impl;

import java.util.List;
 
import com.servicelive.domain.reasoncodemgr.ReasonCode;
import com.servicelive.domain.reasoncodemgr.ReasonCodeTypes;
import com.servicelive.domain.reasoncodemgr.ReasonCodeHist;
import com.servicelive.reasoncode.dao.ManageReasonCodeDao;
import com.servicelive.reasoncode.services.ManageReasonCodeService;


public class ManageReasonCodeServiceImpl implements ManageReasonCodeService {
private ManageReasonCodeDao manageReasonCodeDao;

	//fetch all reason codes
	public List<ReasonCode> getAllReasonCodes(int buyerId)
	{
		List<ReasonCode> resultSer = null;
		resultSer = manageReasonCodeDao.getAllReasonCodes(buyerId);
		return resultSer;
	}
	
	//fetch all cancel codes
	public List<ReasonCode> getAllCancelReasonCodes(int buyerId, String type)
	{
		List<ReasonCode> resultSer = null;
		resultSer = manageReasonCodeDao.getAllCancelReasonCodes(buyerId,type);
		return resultSer;
	}
	
	//add reason code
	public String add(ReasonCode rc){
		return manageReasonCodeDao.add(rc);			
	}
	
	//delete/archive reason code
	public String delete(ReasonCode rc){
		return manageReasonCodeDao.delete(rc);	
	}
	
	//check reason code is used by SO
	public String checkInSO(int reasonCodeId,String type,int buyerId,String reasonCode){
		return manageReasonCodeDao.checkInSO(reasonCodeId,type,buyerId,reasonCode);
	}
	
	//fetch all reason types
	public List<ReasonCodeTypes> getReasonTypes() {
		List<ReasonCodeTypes> resultSer = null;
		resultSer = manageReasonCodeDao.getReasonTypes();
		return resultSer;
	}
	
	//fetch deleted/archived general reason code
	public List<ReasonCodeHist> getHistory(int buyerId)
	{
		List<ReasonCodeHist> resultSer = null;
		resultSer = manageReasonCodeDao.getHistory(buyerId);
		return resultSer;
	}
	
	public ManageReasonCodeDao getManageReasonCodeDao() {
		return manageReasonCodeDao;
	}
	public void setManageReasonCodeDao(ManageReasonCodeDao manageReasonCodeDao) {
		this.manageReasonCodeDao = manageReasonCodeDao;
	}
}
