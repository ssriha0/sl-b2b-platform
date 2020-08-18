package com.newco.marketplace.business.iBusiness.IBuyerFileUpload;

import java.util.List;
import java.io.File;

import com.newco.marketplace.dto.vo.buyerUploadScheduler.RunningHistoryVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadFileVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadAuditErrorVO;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.vo.buyerFileUpload.UploadAuditVO;


/**
 * @author paugus2
 * 
 */
public interface IBuyerFileUploadBO{
	public boolean getValidZipCode(String zip) throws BusinessServiceException; 
	public List getTheSkillTree() throws BusinessServiceException;
	public String insertFiletoDb(UploadAuditVO uploadAuditVO) throws BusinessServiceException;
	public UploadFileVO getUserLogo(Integer buyerId)throws BusinessServiceException;
	public List<RunningHistoryVO> getRunningHistory(Integer buyerId)throws BusinessServiceException;
	public List<UploadAuditErrorVO> getErrorRecordList(Integer fileId)throws BusinessServiceException;  
}

