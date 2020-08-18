package com.newco.marketplace.business.businessImpl.BuyerFileUpload;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.provider.ABaseBO;
import com.newco.marketplace.business.iBusiness.IBuyerFileUpload.IBuyerFileUploadBO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.RunningHistoryVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadAuditErrorVO;
import com.newco.marketplace.dto.vo.buyerUploadScheduler.UploadFileVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.buyerFileUpload.IBuyerFileUploadDao;
import com.newco.marketplace.vo.buyerFileUpload.UploadAuditVO;

public class BuyerFileUploadBOImpl extends ABaseBO implements IBuyerFileUploadBO {
	
	private static final Logger logger = Logger.getLogger(BuyerFileUploadBOImpl.class);
	private IBuyerFileUploadDao buyerFileUploadDao;

	public UploadFileVO getUserLogo(Integer buyerId) throws BusinessServiceException{
		UploadFileVO uploadFileVO = new UploadFileVO();
		try {
			uploadFileVO = buyerFileUploadDao.getLogoByBuyerId(buyerId);
		} catch(DataServiceException ex) {
			logger.error("Unexpected error in BuyerFileUploadBOImpl.getUserLogo", ex);
			throw new BusinessServiceException("Exception @BuyerFileUploadBOImpl.getUserLogo due to : ", ex);
		}		
		return uploadFileVO;	
	}
	
	public List getRunningHistory(Integer buyerResourceId){
		List<RunningHistoryVO> runningHistoryList = buyerFileUploadDao.getrunningHistory(buyerResourceId);
		return runningHistoryList;
	}
	
	public boolean getValidZipCode(String zip) throws BusinessServiceException {
		try {
			return buyerFileUploadDao.getValidZipCode(zip);
		} catch (DataServiceException ex) {
			logger.error("Unexpected error in BuyerFileUploadBOImpl.getValidZipCode", ex);
			throw new BusinessServiceException("DB Exception @BuyerFileUploadBOImpl.getValidZipCode() due to", ex);
		}
	}
	
	public List getTheSkillTree() throws BusinessServiceException {
		List skillTree;
		try {
			skillTree=buyerFileUploadDao.getTheSkillTree();
		} catch (DataServiceException ex) {
			logger.error("Unexpected error in BuyerFileUploadBOImpl.getTheSkillTree", ex);
			throw new BusinessServiceException("DB Exception @BuyerFileUploadBOImpl.getTheSkillTree() due to", ex);
		}
		return skillTree;
	}

	public String insertFiletoDb(UploadAuditVO uploadAuditVO) throws BusinessServiceException {
		try {
			logger.info("inside insertfiletodb");
			String fileName = null;
			SimpleDateFormat formater=new SimpleDateFormat("yyyyMMddHHmmss");
			Date dt=new Date();
			if(uploadAuditVO.getUploadFileName() != null) {
				fileName = uploadAuditVO.getUploadFileName().concat(formater.format(dt));
				uploadAuditVO.setUploadFileName(fileName);
				buyerFileUploadDao.insertFile(uploadAuditVO);
				return "success";
			}
			logger.info("inside insertfiletodb");
		} catch(Exception e){
			logger.error("Unexpected error in BuyerFileUploadBOImpl.insertFiletoDb", e);
			throw new BusinessServiceException("General Exception @BuyerFileUploadBOImpl.getTheSkillTree() due to ", e);
		}
		return "failure";
	}
	
	/**
	 * This method is used to fetch the list of error records in an uploaded file.
	 * @param fileId
	 * @return errorRecordList
	 */
	public List<UploadAuditErrorVO> getErrorRecordList(Integer fileId) throws BusinessServiceException {
		List<UploadAuditErrorVO> errorRecordList = new ArrayList<UploadAuditErrorVO>();
		try {			
			errorRecordList = buyerFileUploadDao.retrieveErrorRecordList(fileId);		
		} catch(Exception e){		
			logger.error("Unexpected error in BuyerFileUploadBOImpl.getErrorRecordList", e);
			throw new BusinessServiceException("General Exception @BuyerFileUploadBOImpl.getTheSkillTree() due to ", e);
		}
		return errorRecordList;
	} 
	
	public IBuyerFileUploadDao getBuyerFileUploadDao() {
		return buyerFileUploadDao;
	}
	
	public void setBuyerFileUploadDao(IBuyerFileUploadDao buyerFileUploadDao) {
		this.buyerFileUploadDao = buyerFileUploadDao;
	}

}
