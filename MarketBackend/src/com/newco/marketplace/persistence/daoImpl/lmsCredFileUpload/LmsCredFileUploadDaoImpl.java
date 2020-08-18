package com.newco.marketplace.persistence.daoImpl.lmsCredFileUpload;

import java.util.List;

import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.lmsCredFileUpload.IlmsCredFileUploadDao;
import com.newco.marketplace.utils.LmsCredUploadConstants;
import com.servicelive.autoclose.dao.impl.AbstractBaseDao;
import com.servicelive.domain.lms.LmsFileProcessingErrorDTO;
import com.servicelive.domain.lms.LmsFileUploadDTO;

/**
 * This is the implementation class of DAO layer to do the following action.
 * @saveOrUpdateFileDetails
 * @getLmsDetails
 * @getLmsErrorHistory
 * @getFileContent
 * @insertLmsErrorDetails
 * @getDownloadFile
 *
 */
public class LmsCredFileUploadDaoImpl extends AbstractBaseDao implements IlmsCredFileUploadDao {

	private static final Logger logger = Logger
			.getLogger(LmsCredFileUploadDaoImpl.class);

	// SL -21142
	@Transactional(propagation = Propagation.SUPPORTS)
	public void saveOrUpdateFileDetails(LmsFileUploadDTO lmsFileUploadDTO) throws DataServiceException {
		logger.info("IN LmsFileUploadDao save Method ");
		try {
			super.update(lmsFileUploadDTO);
		} catch (Exception ex) {
			String strMessage = "Unexpected error while saveOrUpdateFileDetails; root cause + " + ex.getMessage();
			logger.error(strMessage);
			throw new DataServiceException(strMessage, ex);

		}
	}
	
	public void updateFileUploadStatus() {
		String hql = "UPDATE LmsFileUploadDTO set fileStatus = :fileStatus "  + 
		                  "WHERE fileStatus = :fileStatus2";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("fileStatus", LmsCredUploadConstants.LMS_FILE_UPLOAD_STATUS_INACTIVE);
		//query.setParameter("buyerId", lmsFileUploadDTO.getBuyerId());
		query.setParameter("fileStatus2", LmsCredUploadConstants.LMS_FILE_UPLOAD_STATUS_UPLOADED);
		query.executeUpdate();
	}
	// - end SL 21142
	
	public List <Object> getLmsDetails() { 
		String hql = "select substring(lu.uploadFileFileName,1,length(lu.uploadFileFileName)-15) as fileName , DATE_FORMAT(lu.createdDate, '%m/%d/%Y %T') as createdDate,lu.rowCount,lu.errorRecordsCnt,lu.fileStatus,lu.id, concat(vw.firstName,'  ',vw.lastName) as uploadedBy from LmsFileUploadDTO lu ,AdminUserProfileContact vw where lu.resourceId=vw.resourceId  order by lu.createdDate desc";
		Query query = getEntityManager().createQuery(hql);
		try {
			@SuppressWarnings("unchecked")
			List <Object> lmsFileDetailsList = query.getResultList();
			return lmsFileDetailsList;
		} catch (Exception e) {
			logger.error("LmsFileUploadDaoImpl.findByResourceId NoResultException");
			e.printStackTrace();
			return null;
		}
	}

	public List<Object> getLmsErrorHistory(Integer resourceId,Integer fileId) {

		String hql = "select substring(lu.uploadFileFileName,1,length(lu.uploadFileFileName)-15) as fileName , le.recordText, le.failureReason  from LmsFileUploadDTO lu ,LmsFileProcessingErrorDTO le  where  lu.id= :fileId and lu.id=le.lmsFileUploadDTO  ";
		Query query = getEntityManager().createQuery(hql);
		query.setParameter("fileId", fileId);
		try {

			@SuppressWarnings("unchecked")
			List<Object> lmsFileErrorList = query.getResultList();
			return lmsFileErrorList;
		} catch (Exception e) {
			logger.error("LmsFileUploadDaoImpl.findByResourceId NoResultException");
			e.printStackTrace();
			return null;
		}
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<LmsFileUploadDTO> getFileContent(LmsFileUploadDTO lmsFileUploadDTO) throws DataServiceException{
		String hql ="from LmsFileUploadDTO where fileStatus= :fileStatus order by createdDate desc";
		try {
			Query query = getEntityManager().createQuery(hql);
			query.setParameter("fileStatus", lmsFileUploadDTO.getFileStatus());
			@SuppressWarnings("unchecked")
			List<LmsFileUploadDTO> lmsFileContentList = (List<LmsFileUploadDTO>) query.getResultList();
			return lmsFileContentList;
		} catch (Exception ex) {
			String strMessage = "Unexpected error while getPendingFileContent; root cause + " + ex.getMessage();
			logger.error(strMessage);
			throw new DataServiceException(strMessage, ex);
		}
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	public void insertLmsErrorDetails(LmsFileProcessingErrorDTO lmsFileProcessingErrorDTO) throws DataServiceException{
		try{
			super.save(lmsFileProcessingErrorDTO);
		}catch(Exception ex){
			String strMessage = "Unexpected error while insertLmsErrorDetails; root cause + " + ex.getMessage();
			logger.error(strMessage);
			throw new DataServiceException(strMessage, ex);
		}
	}
	@Transactional(propagation = Propagation.SUPPORTS)
	public LmsFileUploadDTO getDownloadFile(LmsFileUploadDTO lmsFileUploadDetailDTO) throws DataServiceException {
		String hql ="from LmsFileUploadDTO where id= :id ";
		try {
			LmsFileUploadDTO allFileContentList;
			Query query = getEntityManager().createQuery(hql);
			query.setParameter("id", lmsFileUploadDetailDTO.getId());
			allFileContentList = (LmsFileUploadDTO) query.getSingleResult();
			return allFileContentList;
		} catch (Exception ex) {
			String strMessage = "Unexpected error while getPendingFileContent; root cause + " + ex.getMessage();
			logger.error(strMessage);
			throw new DataServiceException(strMessage, ex);
		}
	}
}

