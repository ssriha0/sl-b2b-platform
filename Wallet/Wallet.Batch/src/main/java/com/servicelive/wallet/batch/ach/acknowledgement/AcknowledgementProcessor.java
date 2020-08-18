package com.servicelive.wallet.batch.ach.acknowledgement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.util.FileUtil;
import com.servicelive.wallet.batch.BaseFileProcessor;
import com.servicelive.wallet.batch.ach.dao.INachaDao;
import com.servicelive.wallet.batch.ach.dao.INachaMetaDataDao;
import com.servicelive.wallet.batch.ach.vo.AchResponseReasonVO;
import com.servicelive.wallet.batch.ach.vo.FieldDetailVO;
import com.servicelive.wallet.batch.ach.vo.NachaGenericRecordVO;
import com.servicelive.wallet.batch.ach.vo.NachaProcessLogHistoryVO;
import com.servicelive.wallet.batch.ach.vo.NachaProcessLogVO;
import com.servicelive.common.util.BoundedBufferedReader;
// TODO: Auto-generated Javadoc
/**
 * The Class AcknowledgementProcessor.
 */
public class AcknowledgementProcessor extends BaseFileProcessor {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(AcknowledgementProcessor.class.getName());

	/** The nacha dao. */
	private INachaDao nachaDao;

	/** The nacha meta data dao. */
	private INachaMetaDataDao nachaMetaDataDao;

	/**
	 * Ach acknowledgement parser.
	 * 
	 * @param filePath 
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private void achAcknowledgementParser(String filePath) throws SLBusinessServiceException {

		int start = 0;
		int end = 0;
		int recordSize = 0;
		String reasonCode = "";

		String emailSubject = CommonConstants.ACH_ACK_EMAIL_SUBJECT_SUCCESS;
		String result = "Failed";

		ArrayList<FieldDetailVO> fieldvoList = new ArrayList<FieldDetailVO>();
		NachaGenericRecordVO nachaGenVO = new NachaGenericRecordVO();
		FieldDetailVO fieldVO = new FieldDetailVO();
		fieldVO.setFieldCategory(CommonConstants.ACH_RESPONSE_CONFIRMATION);
		fieldVO.setBatchGroupId(0);
		
		try {
			fieldvoList = getFieldDetails(fieldVO);
		} catch (DataServiceException e2) {
			throw new SLBusinessServiceException(e2.getMessage(),e2);
		}

		// getting the reason codes from DB
		HashMap<String, AchResponseReasonVO> achRespReasonVOHash = new HashMap<String, AchResponseReasonVO>();
		
		try {
			achRespReasonVOHash = nachaDao.getAllReasons(CommonConstants.FILE_TYPE_ACKNOWLEDGEMENT);
		} catch (DataServiceException e1) {
			logger.error("Error in ACHAcknoledgementProcessor while getting Reason codes", e1);
			throw new SLBusinessServiceException(e1.getMessage(),e1);
		}
			
		String record = null;
		recordSize = fieldvoList.size();
		FileReader fr=null;
		BoundedBufferedReader br=null;
		try {
			
			
			fr = new FileReader(filePath);
			br = new BoundedBufferedReader(fr);

			String alertEmailAddress = getEmailAddressTo();
			
			while ((record = br.readLine()) != null && (!"".equals(record))) {
              
				for (int i = 0; i < recordSize; i++) {
					start = fieldvoList.get(i).getStartPosition() - 1;
					end = fieldvoList.get(i).getEndPosition();
					fieldvoList.get(i).setFieldValue(record.substring(start, end));
				}
				nachaGenVO.setFieldDetailVO(fieldvoList);
				HashMap<String, FieldDetailVO> recordHashMap = nachaGenVO.getHash();
				String dateTime =
					recordHashMap.get(CommonConstants.ACH_ACK_TEMPLATE_CREATED_DATE_FIELD).getFieldValue()
						+ recordHashMap.get(CommonConstants.ACH_ACK_TEMPLATE_CREATED_TIME_FIELD).getFieldValue();
				long achProcessLogId = 0;

				try {
					
					achProcessLogId = nachaDao.getProcessIdFromDateTime(dateTime);
					if (achProcessLogId == 0) {
						reasonCode = "Reason: Process log Id Missing";
						emailTemplateBO.sendAcknowledgmentNotificationEmail(alertEmailAddress, "Reason: Process log Id Missing.");
						//fr.close();
						return;
					}
				} catch (Exception e) {
					sendEmailtoAdmin("error at line number ", "", "");
					logger.error("Error in ACHAcknoledgementProcessor while sending email", e);
					//fr.close();
					throw new SLBusinessServiceException(e.getMessage(),e);
				}
				
				emailSubject = emailSubject + achProcessLogId;
				int achProcessStatusId = CommonConstants.ACH_ACK_RECEIVED_SUCCESS;

				Iterator iterator = achRespReasonVOHash.keySet().iterator();
				if (recordHashMap.get(CommonConstants.ACH_ACK_TEMPLATE_COMMENTS_FIELD) == null
					|| recordHashMap.get(CommonConstants.ACH_ACK_TEMPLATE_COMMENTS_FIELD).getFieldValue() == null) {
					achProcessStatusId = CommonConstants.ACH_ACK_RECEIVED_REJECTED;
					emailSubject = CommonConstants.ACH_ACK_EMAIL_SUBJECT_FAILURE + achProcessLogId + " Comments Missing in the file " + filePath;
				} else {
					while (iterator.hasNext()) {
						if (recordHashMap.get(CommonConstants.ACH_ACK_TEMPLATE_COMMENTS_FIELD).getFieldValue().trim().equals(iterator.next())) {

							achProcessStatusId = CommonConstants.ACH_ACK_RECEIVED_REJECTED;
							emailSubject =
								CommonConstants.ACH_ACK_EMAIL_SUBJECT_FAILURE + achProcessLogId + ".Reason:"
									+ recordHashMap.get(CommonConstants.ACH_ACK_TEMPLATE_COMMENTS_FIELD).getFieldValue();
							result = "Failed";
							//fr.close();
							break;
						}
					}
				}
				try {
					
					
					NachaProcessLogVO nachaProcessLogVO = new NachaProcessLogVO();
					nachaProcessLogVO.setInitiatedManually("N");
					nachaProcessLogVO.setProcessStatusId(achProcessStatusId);
					nachaProcessLogVO.setNachaProcessId(achProcessLogId);
					logger.info("Updatin achProcessStatusId ACHAcknoledgementProcessor" + achProcessStatusId);
					nachaDao.updatetNachaProcessLog(nachaProcessLogVO);
				} catch (DataServiceException e) {
					logger.info("Error in ACHAcknoledgementProcessor" + e);
					//fr.close();
					throw new SLBusinessServiceException(e.getMessage(),e);
				}

				NachaProcessLogHistoryVO nachaProcessLogHistoryVO = new NachaProcessLogHistoryVO();
				Timestamp timestamp = new Timestamp(new Date(System.currentTimeMillis()).getTime());

				nachaProcessLogHistoryVO.setAchProcessLogId(achProcessLogId);
				nachaProcessLogHistoryVO.setAchProcessStatusId(achProcessStatusId);
				String comments = recordHashMap.get(CommonConstants.ACH_ACK_TEMPLATE_COMMENTS_FIELD).getFieldValue();
				if (comments.trim().equals("")) {
					comments = "Ach Proccess Successful";
					result = "succeeded";
					reasonCode = " ";
				} else {
					reasonCode = "Reason: " + recordHashMap.get(CommonConstants.ACH_ACK_TEMPLATE_COMMENTS_FIELD).getFieldValue();
				}
				nachaProcessLogHistoryVO.setComments(comments);
				nachaProcessLogHistoryVO.setUpdatedBy(CommonConstants.ACH_PROCESS_INITIATER);
				nachaProcessLogHistoryVO.setUpdatedDate(timestamp);
				
				try {
					
					nachaDao.insertNachaProcessHistoryLog(nachaProcessLogHistoryVO);
				} catch (DataServiceException e) {
					logger.error("Error in ACHAcknoledgementProcessor --> insertNachaProcessHistoryLog -->", e);
					//fr.close();
					throw new SLBusinessServiceException(e.getMessage(),e);
				}
				if(!result.equals("succeeded"))
					sendEmailtoAdmin(reasonCode, filePath, result);
			}
			//fr.close();
		} catch (FileNotFoundException e) {
			throw new SLBusinessServiceException(e.getMessage(),e);
		} catch (IOException e) {
			throw new SLBusinessServiceException(e.getMessage(),e);
		} catch (DataServiceException e) {
			throw new SLBusinessServiceException(e.getMessage(),e);
		}
		finally
		{
			try
			{
			if(fr!=null)
			fr.close();
			if(br!=null)
			br.close();
			}
			catch(Exception e)
			{
				//logging error as this can never occur
				logger.error("Caught inside: <AcknowledgementProcessor::achAcknowledgementParser()>:Error: Got an exception that should not occur", e);
			}
		}

	}

	/**
	 * Gets the aCK archive file directory.
	 * 
	 * @return the aCK archive file directory
	 * 
	 * @throws DataServiceException 
	 */
	private String getACKArchiveFileDirectory() throws DataServiceException {

		return getFileDirectory(CommonConstants.ACKNOWLEDGEMENT_FILE_ARCHIVE_DIRECTORY);
	}

	/**
	 * Gets the aCK file directory.
	 * 
	 * @return the aCK file directory
	 * 
	 * @throws DataServiceException 
	 */
	private String getACKFileDirectory() throws DataServiceException {

		return getFileDirectory(CommonConstants.ACKNOWLEDGEMENT_FILE_DIRECTORY);
	}

	/**
	 * Gets the email address from.
	 * 
	 * @return the email address from
	 * 
	 * @throws DataServiceException 
	 */
	private String getEmailAddressFrom() throws DataServiceException {

		return applicationProperties.getPropertyValue(CommonConstants.SERVICELIVE_ADMIN);
	}

	/**
	 * Gets the email address to.
	 * 
	 * @return the email address to
	 * 
	 * @throws DataServiceException 
	 */
	private String getEmailAddressTo() throws DataServiceException {

		return applicationProperties.getPropertyValue(CommonConstants.SERVICELIVE_ADMIN);
	}

	/**
	 * Gets the field details.
	 * 
	 * @param fieldVO 
	 * 
	 * @return the field details
	 * 
	 * @throws DataServiceException 
	 */
	public ArrayList<FieldDetailVO> getFieldDetails(FieldDetailVO fieldVO) throws DataServiceException {

		ArrayList<FieldDetailVO> fieldvoList = new ArrayList<FieldDetailVO>();
		try {
			fieldvoList = nachaMetaDataDao.retrieveFieldDetais(fieldVO);
		} catch (DataServiceException dse) {
			logger.error("Error in ACHAcknoledgementProcessor while getting the Field Details", dse);
			throw dse;
		}
		return fieldvoList;

	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.IProcessor#process()
	 */
	public void process() throws SLBusinessServiceException {

		// change this to file path on connect direct
		String filePath;
		String alertEmailAddress = null;
		try {
			alertEmailAddress = getEmailAddressTo();
			String[] fileNames = FileUtil.getDirectoryFiles(getACKFileDirectory());

			if ((fileNames == null) || (fileNames != null && fileNames.length < 1)) {
				emailTemplateBO.sendAcknowledgmentNotificationEmail(getEmailAddressTo(),CommonConstants.ACKNOWLEDGEMENT_PROCESS_FAILURE_BODY);
			} else {
				for (int cnt = 0; cnt < fileNames.length; cnt++) {
					filePath = getACKFileDirectory() + fileNames[cnt];
					achAcknowledgementParser(filePath);
					FileUtil.moveFile(new File(filePath), new File(getACKArchiveFileDirectory() + fileNames[cnt]));
				}
			}
		} catch (Exception e) {
			emailTemplateBO.sendAcknowledgmentNotificationEmail(alertEmailAddress,e.getMessage());
			logger.error("ACHAcknowledgementProcessor-->achachAcknowledgementReaderAndParser()-->EXCEPTION-->", e);
			throw new SLBusinessServiceException(e);
		}
	}

	/**
	 * Send emailto admin.
	 * 
	 * @param reason 
	 * @param filePath 
	 * @param result 
	 * 
	 * @throws DataServiceException 
	 */
	private void sendEmailtoAdmin(String reason, String filePath, String result) throws DataServiceException {

		try {
			emailTemplateBO.sendAchAckResponseEmail(getEmailAddressTo(), getEmailAddressFrom(), CommonConstants.EMAIL_TEMPLATE_ACH_ACK, filePath, reason, result);
		} catch (DataServiceException e) {
			logger.error("Error in AcknoledgementProcessor --> sendEmailtoAdmin()-->", e);
			throw e;
		}
	}

	public void setNachaDao(INachaDao nachaDao) {
		this.nachaDao = nachaDao;
	}

	public void setNachaMetaDataDao(INachaMetaDataDao nachaMetaDataDao) {
		this.nachaMetaDataDao = nachaMetaDataDao;
	}

}
