package com.newco.marketplace.persistence.iDao.buyerFileUpload;

import java.util.List;

import com.newco.marketplace.dto.vo.buyerUploadScheduler.RunningHistoryVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadFileVO;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadAuditErrorVO;
import com.newco.marketplace.vo.buyerFileUpload.UploadAuditVO;

public interface IBuyerFileUploadDao{
	
	public int testquery(int test) throws DataServiceException;
	public String insertFile(UploadAuditVO uploadAuditVO) throws DataServiceException;
	public int generateTestId() throws DataServiceException;
	public boolean getValidZipCode(String zip) throws DataServiceException;
	public List getTheSkillTree() throws DataServiceException;
	public List getFileToCreateSo() throws DataServiceException;
	public List getAllFileEntries() throws DataServiceException;
	public UploadFileVO getLogoByBuyerId(Integer buyerId) throws DataServiceException;
	public List<RunningHistoryVO> getrunningHistory(Integer buyerId);
	public List<UploadAuditErrorVO> retrieveErrorRecordList(Integer fileId) throws DataServiceException;
}
