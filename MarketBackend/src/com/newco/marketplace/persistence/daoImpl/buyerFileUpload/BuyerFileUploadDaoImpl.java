package com.newco.marketplace.persistence.daoImpl.buyerFileUpload;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.newco.marketplace.dto.vo.buyerUploadScheduler.RunningHistoryVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadFileVO;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.buyerFileUpload.IBuyerFileUploadDao;
import com.newco.marketplace.persistence.service.document.DocumentService;
import com.newco.marketplace.vo.buyerFileUpload.UploadAuditVO;
import com.sears.os.dao.impl.ABaseImplDao;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadAuditErrorVO;


public class BuyerFileUploadDaoImpl extends ABaseImplDao implements IBuyerFileUploadDao {
	
	private DocumentService documentService;
		
	public UploadFileVO getLogoByBuyerId(Integer buyerId) throws DataServiceException
	{
		logger.info("inside DAO");
		UploadFileVO uploadFileVO = new UploadFileVO();
		try{
			List<UploadFileVO> uploadFileVOList = (List<UploadFileVO>)getSqlMapClientTemplate().queryForList("user.logo",buyerId);
			if (uploadFileVOList != null && !uploadFileVOList.isEmpty()) {
				uploadFileVO = uploadFileVOList.get(0);
				uploadFileVO.setLogo(documentService.getDocumentBlobByDocumentId(uploadFileVO.getDocumentId()));
			}			
		}catch(DataServiceException ex){
			throw new DataServiceException("Exception @BuyerDaoImpl.getLogoByBuyerId()",ex);			
		}
		
		return uploadFileVO;
	}
	
	public List getrunningHistory(Integer buyerResourceId){
		logger.info("Inside DAO - get Running History");
		List<RunningHistoryVO> runningHistoryList2 = getSqlMapClientTemplate().queryForList("running.history", buyerResourceId);
		return runningHistoryList2;
	}
		protected final Log logger = LogFactory.getLog(getClass());
		
		public int testquery(int test){
			logger.info("inside test query");
			int test1 =(Integer)getSqlMapClientTemplate().queryForObject("count.primarycategory",test);
			logger.info("inside test query finished" +test1);
			return test1;
		}
		public String insertFile(UploadAuditVO uploadAuditVO) throws DataServiceException{
			logger.info("inside fileuploaddao");
			try{ 
				Integer fileId = (Integer) getSqlMapClientTemplate().insert("so.insertfiletodb", uploadAuditVO);
				uploadAuditVO.setFileId(fileId);
			}
			catch(Exception e){
				logger.info("error inside fileuploaddao");
				throw new DataServiceException("Exception Occured - BuyerDaoImpl- getBuyerDetailForServiceOrder()",	e);
			}
			return "success";
		}
		public int generateTestId(){
			int maxTestId=(Integer)getSqlMapClientTemplate().queryForObject("so.selectmaxtestid");
			logger.info("max id is"+maxTestId);
			return maxTestId+1000;
		}

	public boolean getValidZipCode(String zip){
		try{	
			logger.info("zip is :"+zip+":");
		Integer zipCount=(Integer)getSqlMapClientTemplate().queryForObject("so.getvalidzip",zip);
		logger.info("zip is "+zipCount);
		if(zipCount!=0){
			logger.info("zip ids valid");
			return true;
		}
		else{
			return false;
		}
		}
		catch(Exception e){
			logger.error("Exception @BuyerDaoImpl.getValidZipCode : ",e);
			return false;
			
			}
		}
	public List getTheSkillTree(){
		try{	
		
		List skillTree=getSqlMapClientTemplate().queryForList("so.gettheskilltree");
		return skillTree;
		}
		catch(Exception e){
			logger.error("Exception @BuyerDaoImpl.getTheSkillTree : ",e);
			return null;
			
			}
		}
	public List getFileToCreateSo(){
		try{	
		
			//UploadAuditDTO uploadAuditDTO=(UploadAuditDTO)getSqlMapClientTemplate().queryForObject("getFileForCreateSo", nameOfFile);
			List<UploadAuditVO> fileList=getSqlMapClientTemplate().queryForList("getFileForCreateSo");
			logger.info("inside get file conetsnt");
			return fileList;
		}
		catch(Exception e){
			logger.error("Exception @BuyerDaoImpl.getFileToCreateSo : ",e);
			return null;
			
			}
		}
	public List getAllFileEntries(){
		try{	
			
			List fileNameList=getSqlMapClientTemplate().queryForList("getAllEntriesFromAudit");
			logger.info("getAllFileEntries");
			return fileNameList;
		}
		catch(Exception e){
			logger.error("Exception @BuyerDaoImpl.getAllFileEntries : ",e);
			return null;
			
			}
	}
	
	/**
	 * This method is used to fetch the list of error records in an uploaded file.
	 * @param fileId
	 * @return errorRecordList
	 */
	public List<UploadAuditErrorVO> retrieveErrorRecordList(Integer fileId) throws DataServiceException{
		List<UploadAuditErrorVO> errorRecordList = null;
		try{			
			errorRecordList =getSqlMapClientTemplate().queryForList("so.getUploadErrorRecords",fileId);			
		}
		catch(Exception e){ 
			throw new DataServiceException("Error", e);			
		}
		return errorRecordList;
	}
	
	public DocumentService getDocumentService() {
		return documentService;
	}

	public void setDocumentService(DocumentService documentService) {
		this.documentService = documentService;
	}
}
