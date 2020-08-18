package com.newco.marketplace.business.iBusiness.lmscredential;

import java.util.List;

import com.newco.marketplace.exception.core.DataServiceException;
import com.servicelive.domain.lms.LmsFileProcessingErrorDTO;
import com.servicelive.domain.lms.LmsFileUploadDTO;

public interface IlmsCredentialFileUploadService {

	public void insert(LmsFileUploadDTO lmsFileUploadDetailDTO) throws DataServiceException;
	public List <Object>  getLmsHistory();
	public List<Object> getLmsErrorHistory(Integer resourceId, Integer fileId);
	public List<LmsFileUploadDTO> getFileContent(LmsFileUploadDTO lmsFileUploadDTO) throws DataServiceException;
	public void  saveOrUpdateFileErrorCountDetails(LmsFileUploadDTO lmsFileUploadDetailDTO) throws DataServiceException;
	public void  insertLmsErrorDetails(LmsFileProcessingErrorDTO lmsFileProcessingErrorDTO) throws DataServiceException;
	public LmsFileUploadDTO downloadFile(LmsFileUploadDTO lmsFileUploadDetailDTO) throws DataServiceException;
	public void saveOrUpdateFileDetails(LmsFileUploadDTO lmsFileUploadDetailDTO) throws DataServiceException;
	
}
