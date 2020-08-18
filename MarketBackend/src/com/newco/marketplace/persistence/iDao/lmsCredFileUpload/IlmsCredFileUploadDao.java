package com.newco.marketplace.persistence.iDao.lmsCredFileUpload;

import java.util.List;

import com.newco.marketplace.exception.core.DataServiceException;
import com.servicelive.domain.lms.LmsFileProcessingErrorDTO;
import com.servicelive.domain.lms.LmsFileUploadDTO;

public interface IlmsCredFileUploadDao {

	public void saveOrUpdateFileDetails(LmsFileUploadDTO LmsFileUploadDTO) throws DataServiceException; 
	public List <Object>  getLmsDetails();
	public List<Object> getLmsErrorHistory(Integer resourceId, Integer fileId);
	public List<LmsFileUploadDTO> getFileContent(LmsFileUploadDTO lmsFileUploadDTO) throws DataServiceException;
	public void updateFileUploadStatus() throws DataServiceException;
	public void insertLmsErrorDetails(LmsFileProcessingErrorDTO lmsFileProcessingErrorDTO) throws DataServiceException;
	public LmsFileUploadDTO getDownloadFile(LmsFileUploadDTO lmsFileUploadDetailDTO) throws DataServiceException;
}
