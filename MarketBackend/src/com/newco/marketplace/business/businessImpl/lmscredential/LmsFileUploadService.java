package com.newco.marketplace.business.businessImpl.lmscredential;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.business.iBusiness.lmscredential.IlmsCredentialFileUploadService;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.lmsCredFileUpload.IlmsCredFileUploadDao;
import com.newco.marketplace.utils.LmsCredUploadConstants;
import com.servicelive.domain.lms.LmsFileProcessingErrorDTO;
import com.servicelive.domain.lms.LmsFileUploadDTO;

public class LmsFileUploadService implements IlmsCredentialFileUploadService {

	private static final Logger logger = Logger.getLogger(LmsFileUploadService.class);
	private IlmsCredFileUploadDao lmsFileUploadDao;

	public IlmsCredFileUploadDao getLmsFileUploadDao() {
		return lmsFileUploadDao;
	}

	public void setLmsFileUploadDao(IlmsCredFileUploadDao lmsFileUploadDao) {
		this.lmsFileUploadDao = lmsFileUploadDao;
	}

	@Transactional
	public void insert(LmsFileUploadDTO lmsFileUploadDetailDTO) throws DataServiceException{
		String fileName = null;
		SimpleDateFormat formater=new SimpleDateFormat("yyyyMMddHHmmss");
		Date dt=new Date();
		if(lmsFileUploadDetailDTO.getUploadFileFileName() != null) {
			fileName = (lmsFileUploadDetailDTO.getUploadFileFileName().substring(0,lmsFileUploadDetailDTO.getUploadFileFileName().length()-4)+"_").concat(formater.format(dt));
			lmsFileUploadDetailDTO.setUploadFileFileName(fileName);
		}
		lmsFileUploadDetailDTO.setBlobFromFile(lmsFileUploadDetailDTO.getUploadFile());

		//lmsFileUploadDetailDTO.setCreatedDate(dt);
		//lmsFileUploadDetailDTO.setProcessedOn(dt);
		lmsFileUploadDetailDTO.setFileStatus(LmsCredUploadConstants.LMS_FILE_UPLOAD_STATUS_UPLOADED);
		try{
		lmsFileUploadDao.updateFileUploadStatus();
		lmsFileUploadDao.saveOrUpdateFileDetails(lmsFileUploadDetailDTO);
		}catch(Exception ex){
		String strMessage = "Unexpected error while insert; root cause + " + ex.getMessage();
			logger.error(strMessage);
			throw new DataServiceException(strMessage, ex);
		}
	}

	public List <Object> getLmsHistory() {
		return lmsFileUploadDao.getLmsDetails();
	}

	@Transactional
	public void saveOrUpdateFileDetails(LmsFileUploadDTO lmsUploadDTO) throws DataServiceException{
		try{
			lmsFileUploadDao.saveOrUpdateFileDetails(lmsUploadDTO);
			}catch(Exception ex){
			String strMessage = "Unexpected error while saveOrUpdateFileDetails; root cause + " + ex.getMessage();
				logger.error(strMessage);
				throw new DataServiceException(strMessage, ex);
			}
	}

	public List<Object> getLmsErrorHistory(Integer resourceId, Integer fileId) {
		return lmsFileUploadDao.getLmsErrorHistory(resourceId,fileId);
	}
	
	
	
	@Transactional
	public List<LmsFileUploadDTO> getFileContent(LmsFileUploadDTO lmsFileUploadDTO) throws DataServiceException{
		List<LmsFileUploadDTO>lmsList=lmsFileUploadDao.getFileContent(lmsFileUploadDTO);
		File outputFile = null ;
		for (LmsFileUploadDTO lmsFileUpload : lmsList) {
			String fileName=lmsFileUpload.getUploadFileFileName();
			outputFile = new File(fileName);
			try {
				FileUtils.writeByteArrayToFile(outputFile, lmsFileUpload.getFileContent());
				lmsFileUpload.setFileByteToText(outputFile);
			} catch (Exception ex) {
				logger.error("Could not write data to file " + fileName);
				String strMessage = "Unexpected error while getFileContent; root cause + " + ex.getMessage();
			    logger.error(strMessage);
			    throw new DataServiceException(strMessage, ex);
			}
		}
		return lmsList;
	}

	@Transactional
	public void  saveOrUpdateFileErrorCountDetails(LmsFileUploadDTO lmsFileUploadDetailDTO) throws DataServiceException{
		logger.info("Inside saveOrUpdateFileErrorCountDetails() for file :"+ lmsFileUploadDetailDTO.getFileName());
		try{
		// SL-21142
		// lmsFileUploadDao.updateFileUploadStatus();
		lmsFileUploadDao.saveOrUpdateFileDetails(lmsFileUploadDetailDTO);
		}catch(Exception ex){
		String strMessage = "Unexpected error while saveOrUpdateFileErrorCountDetails; root cause + " + ex.getMessage();
			    logger.error(strMessage);
			    throw new DataServiceException(strMessage, ex);
		}
	}

	@Transactional
	public void  insertLmsErrorDetails(LmsFileProcessingErrorDTO lmsFileProcessingErrorDTO) throws DataServiceException{
		logger.info("Inside insertLmsErrorDetails()");
		try {
			lmsFileUploadDao.insertLmsErrorDetails(lmsFileProcessingErrorDTO);
		} catch (Exception ex) {
			String strMessage = "Unexpected error while insertLmsErrorDetails(); root cause + " + ex.getMessage();
			logger.error(strMessage);
			throw new DataServiceException(strMessage, ex);

		}
	}

	@Transactional
	public LmsFileUploadDTO downloadFile(LmsFileUploadDTO lmsFileUploadDetailDTO) throws DataServiceException {
		return lmsFileUploadDao.getDownloadFile(lmsFileUploadDetailDTO);
	}
}
