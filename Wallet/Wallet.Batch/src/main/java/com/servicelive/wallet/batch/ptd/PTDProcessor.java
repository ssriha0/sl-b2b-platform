package com.servicelive.wallet.batch.ptd;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.util.FileUtil;
import com.servicelive.common.util.StackTraceHelper;
import com.servicelive.wallet.batch.BaseFileProcessor;
import com.servicelive.wallet.batch.IProcessor;
import com.servicelive.wallet.batch.ptd.dao.IPTDDao;
import com.servicelive.wallet.batch.ptd.vo.PTDFileVO;
import com.servicelive.wallet.batch.ptd.vo.PTDFullfillmentEntryVO;
import com.servicelive.wallet.batch.ptd.vo.PTDProcessLogVO;
import com.servicelive.wallet.batch.ptd.vo.PTDRecordTypeVO;
import com.servicelive.wallet.batch.ptd.vo.PTDRecordVO;
import com.servicelive.wallet.batch.ptd.vo.PtdEntryVO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.valuelink.IValueLinkBO;

// TODO: Auto-generated Javadoc
/**
 * The Class PTDProcessor.
 */
public class PTDProcessor extends BaseFileProcessor implements IProcessor {

	/** The Constant ERROR_ORDER. */
	private static final Comparator<PTDFullfillmentEntryVO> ERROR_ORDER;

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(PTDProcessor.class);
	static {
		ERROR_ORDER = new Comparator<PTDFullfillmentEntryVO>() {

			public int compare(PTDFullfillmentEntryVO fe1, PTDFullfillmentEntryVO fe2) {

				return Integer.valueOf(fe2.getPtdErrorCode()).compareTo(fe1.getPtdErrorCode());
			}
		};
	}

	/** The ptd dao. */
	private IPTDDao ptdDao;

	/** The ptd message builder. */
	private PTDMessageBuilder ptdMessageBuilder;

	/** The ptd reader. */
	private PTDReader ptdReader;

	/** The ptd transformer. */
	private PTDTransformer ptdTransformer;

	/** The value link bo. */
	private IValueLinkBO valueLink;


	/**
	 * Builds the ptd date string.
	 * 
	 * @return the string
	 */
	private String buildPtdDateString() {

		Calendar cal = Calendar.getInstance();
		// TODO Not sure why do we need to have the hour when PTD ran?? What if PTD ran in the middle of the hour??
		// cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(PTD_HOUR_TO_RUN));
		// cal.set(Calendar.MINUTE, 0);
		// cal.set(Calendar.SECOND, 0);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return formatter.format(cal.getTime());
	}

	/**
	 * Description: Looks at the SOURCE_CODE, REQUEST_CODE and RESPONSE_CODE of incoming PTD file
	 * to determine whether an update is necessary or not.
	 * 
	 * @param ptdFullfillmentEntryVO 
	 * 
	 * @return 
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private int determinePTDUpdate(PTDFullfillmentEntryVO ptdFullfillmentEntryVO) throws SLBusinessServiceException {

		int returncode = CommonConstants.EC_NO_ERROR;
		int ptdRequestCode = ptdFullfillmentEntryVO.getPtdRequestCode();
		if ((ptdRequestCode == CommonConstants.PTD_STORE_BALANCE_INQUIRY && ptdFullfillmentEntryVO.getPtdSourceCode() != 200)
			|| (ptdRequestCode == CommonConstants.BALANCE_INQUIRY || ptdRequestCode == CommonConstants.PTD_CLGC_BALANCE_INQUIRY || ptdRequestCode == CommonConstants.PTD_CLGC_TRANS_HISTORY)) {
			returncode = CommonConstants.EC_IGNORE;
		} else if (ptdFullfillmentEntryVO.getPtdSourceCode() == 200) {
			returncode = CommonConstants.EC_VLBC_ACTIVITY;
		} else {
			if (ptdRequestCode != CommonConstants.TIME_OUT_REVERSAL_IGNORE) {
				// read from DB
				ValueLinkEntryVO feVo = valueLink.getValueLinkMessageDetail(ptdFullfillmentEntryVO.getFullfillmentEntryId());
				// compare request types.
				if (feVo == null) {
					returncode = CommonConstants.EC_PTD_ENTRY_NOT_FOUND;
				} else {
					if (ptdRequestCode == CommonConstants.ADMIN_TOOL_REDEEM || ptdRequestCode == CommonConstants.ADMIN_TOOL_RELOAD) {} 
					else if (feVo.getMessageTypeId() != null
						&& (feVo.getMessageTypeId() == CommonConstants.ACTIVATION && ptdRequestCode == CommonConstants.PTD_ACTIVATION)) {} 
					else if (feVo.getMessageTypeId() != null
						&& (feVo.getMessageTypeId() == CommonConstants.RELOAD && ptdRequestCode == CommonConstants.PTD_RELOAD)) {} 
					else if (feVo.getMessageTypeId() != null
						&& (feVo.getMessageTypeId() == CommonConstants.REDEMPTION && (ptdRequestCode == CommonConstants.PTD_REDEMPTION) || ptdRequestCode == CommonConstants.PTD_REDEMPTION_NO_NSF)) {} 
					else {
						returncode = CommonConstants.EC_SIGN_MISMATCH;
					}
					// compare amount - return = amount mismatch constant
					if (!feVo.getTransAmount().equals(ptdFullfillmentEntryVO.getTransAmount())) {// reload
						returncode = CommonConstants.EC_AMOUNT_MISMATCH;
					}
				}
			} else {
				// return = bad response constant
				returncode = CommonConstants.EC_BAD_RESPONSE;
			}
		}
		return returncode;
	}

	/**
	 * Gets the file and dir name.
	 * 
	 * @param files 
	 * 
	 * @return the file and dir name
	 */
	private String getFileAndDirName(String[] files) throws DataServiceException {
		// Prepare the file & archive directory name
		String fileName = files[0];
		String fileNameWithDir = getFileDirectory(CommonConstants.PTD_FILE_DIRECTORY) + fileName; // need to get the variable from application properties
		return fileNameWithDir;
	}

	private String getArchiveFileAndDirName(String[] files) throws DataServiceException {
		// Prepare the file & archive directory name
		String fileName = files[0];
		String fileNameWithDir = getFileDirectory(CommonConstants.PTD_ARCHIVE_FILE_DIRECTORY) + fileName; // need to get the variable from application properties
		return fileNameWithDir;
	}

	/**
	 * Gets the files.
	 * 
	 * @return the files
	 * 
	 * @throws Exception 
	 */
	private String[] getFiles() throws Exception {

		String ptdDir = getFileDirectory(CommonConstants.PTD_FILE_DIRECTORY);
		logger.info(ptdDir);
		return FileUtil.getDirectoryFiles(ptdDir);
	}

	/**
	 * This methods prepares a PTDFullfillmentEntryVO object given a
	 * PTDTransactionRecordVO.
	 * 
	 * @param ptdTransactionRecordVO 
	 * 
	 * @return 
	 */
	private PTDFullfillmentEntryVO getPTDFulFillmentEntryVO(PTDRecordVO ptdTransactionRecordVO) {

		HashMap<String, PTDRecordTypeVO> map = ptdTransactionRecordVO.getHash(ptdTransactionRecordVO.getPtdRecordTypeList());
		PTDFullfillmentEntryVO ptdFullfillmentEntryVO = new PTDFullfillmentEntryVO();
		PTDRecordTypeVO user1ptdRecordTypeVO = map.get("USER_1");
		if (!user1ptdRecordTypeVO.getFieldValue().trim().equals("")) {
			ptdFullfillmentEntryVO.setFullfillmentEntryId(Long.valueOf(user1ptdRecordTypeVO.getFieldValue().trim()));
		}
		PTDRecordTypeVO tempVO = map.get("AUTH_CODE");
		ptdFullfillmentEntryVO.setPtdAuthCode(tempVO.getFieldValue().trim());
		tempVO = map.get("REVERSAL_FLAG");
		ptdFullfillmentEntryVO.setPtdReversalFlag(Integer.parseInt(tempVO.getFieldValue().trim()));
		tempVO = map.get("RESPONSE_CODE");
		ptdFullfillmentEntryVO.setPtdResponseCode(Integer.parseInt(tempVO.getFieldValue().trim()));
		tempVO = map.get("REQUEST_CODE");
		ptdFullfillmentEntryVO.setPtdRequestCode(Integer.parseInt(tempVO.getFieldValue().trim()));
		tempVO = map.get("SOURCE_CODE");
		ptdFullfillmentEntryVO.setPtdSourceCode(Integer.parseInt(tempVO.getFieldValue().trim()));
		tempVO = map.get("ACCOUNT_NUMBER");
		ptdFullfillmentEntryVO.setPrimaryAccountNumber(Long.valueOf(tempVO.getFieldValue().trim()));
		tempVO = map.get("AMOUNT_SIGN");
		String amountSign = tempVO.getFieldValue();
		if ("-".equals(amountSign)) {
			ptdFullfillmentEntryVO.setEntryTypeId(1L);

		} else {
			ptdFullfillmentEntryVO.setEntryTypeId(2L);
		}
		tempVO = map.get("TRANSACTION_AMOUNT");
		ptdFullfillmentEntryVO.setTransAmount(new Double(tempVO.getFieldValue()) / 100);
		ptdFullfillmentEntryVO.setPtdReconsiledInd(CommonConstants.PTD_RECONCILATION_STATUS_SUCCESS);
		ptdFullfillmentEntryVO.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		ptdFullfillmentEntryVO.setModifiedDate(new Timestamp(System.currentTimeMillis()));

		return ptdFullfillmentEntryVO;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.IProcessor#process()
	 */
	public void process() throws SLBusinessServiceException {

		String mailBody = PTDUtil.getMailBodyText();
		try {
			try {
				ArrayList<PTDFullfillmentEntryVO> feVOs = processPTDFile();

				if (!feVOs.isEmpty()) {
					String updatedMailBody = ptdMessageBuilder.createPTDFailureReportEmail(feVOs);
					sendPTDEmail(mailBody + updatedMailBody, CommonConstants.PTD_PROCESS_SUCCESS_UNREC_SUBJECT);
					logger.info("END PTD Message Processor");
				}

			} catch (Exception e) {
				logger.error("An Exception occured in PTD message processing", e);
				mailBody += StackTraceHelper.getStackTrace(e);
				sendPTDEmail(mailBody, CommonConstants.PTD_PROCESS_FAILURE_SUBJECT);
			}
		} catch (DataNotFoundException e) {
			logger.error("Error while sending an email", e);
		}
	}

	/**
	 * Process ptd file.
	 * 
	 * @return the array list< ptd fullfillment entry v o>
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private ArrayList<PTDFullfillmentEntryVO> processPTDFile() throws SLBusinessServiceException {

		String[] files;
		try {
			files = getFiles();
		} catch (Exception e1) {
			throw new SLBusinessServiceException("Could not get PTD files", e1);
		}

		ArrayList<PTDFullfillmentEntryVO> ptdUnreconciledList = null;
		if ( files != null && files.length > 0) {
			try {
				// get the name of first file
				String fullFileName = getFileAndDirName(files);
				String archiveFileWithDir = getArchiveFileAndDirName(files);

				// Read file to VO
				PTDFileVO ptdFileVO = ptdReader.objectizePTDFile(fullFileName);

				// Create Process Log entry without the update count
				PTDProcessLogVO ptdProcessLogVO = ptdTransformer.getProcessLogVO(ptdFileVO);
				int ptdLogId = ptdDao.insertPTDProcessLog(ptdProcessLogVO);
				ptdProcessLogVO.setPtdLogId(ptdLogId);

				// reconcile transactions
				HashMap<String, Object> transMap = updateTransactions(ptdFileVO, ptdLogId);
				ptdUnreconciledList = (ArrayList<PTDFullfillmentEntryVO>) transMap.get("fullfillmentErrorList");
				// Sort in order of error_codes.
				Collections.sort(ptdUnreconciledList, ERROR_ORDER);

				// Update processCount now.
				ptdProcessLogVO.setPtdTransactionsUpdated((Integer) transMap.get("updateCount"));
				ptdDao.updatePTDProcessLogUpdateCount(ptdProcessLogVO);

				// log the transactions that didn't get reconciled properly to
				// ptd_transactions table
				ptdDao.insertPTDErrorTransactions(ptdUnreconciledList);				

				// 8. Archive the PTD file after successful completion of process
				FileUtil.moveFile(new File(fullFileName), new File(archiveFileWithDir));

			} catch (Exception e) {
				throw new SLBusinessServiceException("Error while processing the PTD file.", e);
			}
		} else {
			throw new SLBusinessServiceException("Did not find any files in the PTD directory.");
		}

		return ptdUnreconciledList;
	}

	/**
	 * Description: This method retrieves all transaction in SL database that are not seen in
	 * the PTD file that it received. Only transactions that are n days old are
	 * picked to be resent.
	 * 
	 * @param ptdTime 
	 * 
	 * @return 
	 */
	private ArrayList<PTDFullfillmentEntryVO> retrieveSLNonReconciledTransactions(String ptdTime) {

		ArrayList<PTDFullfillmentEntryVO> slNonReconcileds = new ArrayList<PTDFullfillmentEntryVO>();
		/* get all the transactions that need to be resent */
		ArrayList<java.lang.Long> myList = null;
		try {
			myList = (ArrayList<java.lang.Long>) ptdDao.getAllSLTransactions(ptdTime);
		} catch (DataServiceException e) {
			logger.error("Error retrieveing SLDB list", e);
		}
		for (Long l : myList) {
			ValueLinkEntryVO fullfillmentEntryVO = null;
			try {
				fullfillmentEntryVO = valueLink.getValueLinkMessageDetail(l);
			} catch (SLBusinessServiceException e) {
				logger.error("Error retrieveing individual SLDB record", e);
			}
			slNonReconcileds.add(new PTDFullfillmentEntryVO(fullfillmentEntryVO, CommonConstants.EC_SLDB_ENTRY_NOT_FOUND));
		}
		return slNonReconcileds;
	}

	/**
	 * 
	 * 
	 * @param mailBody 
	 * @param subject 
	 * 
	 * @throws DataNotFoundException 
	 * @throws Exception 	 */
	private void sendPTDEmail(String mailBody, String subject) throws DataNotFoundException {

		emailTemplateBO.sendPTDFileProcessAlert(applicationProperties.getPropertyValue(CommonConstants.SERVICELIVE_ADMIN), "", subject, mailBody);
	}

	/**
	 * Sets the ptd dao.
	 * 
	 * @param ptdDao the new ptd dao
	 */
	public void setPtdDao(IPTDDao ptdDao) {

		this.ptdDao = ptdDao;
	}

	/**
	 * Sets the ptd message builder.
	 * 
	 * @param ptdMessageBuilder the new ptd message builder
	 */
	public void setPtdMessageBuilder(PTDMessageBuilder ptdMessageBuilder) {

		this.ptdMessageBuilder = ptdMessageBuilder;
	}

	/**
	 * Sets the ptd reader.
	 * 
	 * @param ptdReader the new ptd reader
	 */
	public void setPtdReader(PTDReader ptdReader) {

		this.ptdReader = ptdReader;
	}

	/**
	 * Sets the ptd transformer.
	 * 
	 * @param ptdTransformer the new ptd transformer
	 */
	public void setPtdTransformer(PTDTransformer ptdTransformer) {

		this.ptdTransformer = ptdTransformer;
	}


	/**
	 * Sets the value link bo.
	 * 
	 * @param valueLinkBO the new value link bo
	 */
	public void setValueLink(IValueLinkBO valueLink) {
		this.valueLink = valueLink;
	}

	/**
	 * This method updates the transactions with PTD attributes like ptd
	 * reconcilation status, response code, request code etc.
	 * 
	 * @param ptdFileVO 
	 * @param ptdLogId 
	 * 
	 * @return 
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private HashMap<String, Object> updateTransactions(PTDFileVO ptdFileVO, int ptdLogId) throws SLBusinessServiceException {

		int successUpdateCount = 0;
		ArrayList<PTDFullfillmentEntryVO> fulfillmentErrors = new ArrayList<PTDFullfillmentEntryVO>();
		ArrayList<PtdEntryVO> entirePtdList = new ArrayList<PtdEntryVO>();
		try {
			if (ptdFileVO != null && ptdFileVO.getPtdTransactionList() != null && ptdFileVO.getPtdTransactionList().size() > 0) {
				for (PTDRecordVO ptdTransactionRecordVO : ptdFileVO.getPtdTransactionList()) {
					PTDFullfillmentEntryVO ptdFullfillmentEntryVO = getPTDFulFillmentEntryVO(ptdTransactionRecordVO);
					PtdEntryVO ptdEntryVO = ptdTransformer.createPTDEntryVO(ptdTransactionRecordVO, ptdFullfillmentEntryVO, ptdLogId);
					if (ptdFullfillmentEntryVO.getFullfillmentEntryId() != null) {
						int returncode = determinePTDUpdate(ptdFullfillmentEntryVO);

						if (returncode > CommonConstants.EC_IGNORE) {
							if (returncode == CommonConstants.EC_NO_ERROR) {
								int i = ptdDao.updatePTDInfo(ptdFullfillmentEntryVO);
								if (i == 1) {
									successUpdateCount++;
								} else {
									// Need to send these transactions in email
									ptdFullfillmentEntryVO.setPtdErrorCode(CommonConstants.EC_PTD_ENTRY_NOT_FOUND);
									fulfillmentErrors.add(ptdFullfillmentEntryVO);
									ptdEntryVO.setEmailSentFlag(1);
								}
							} else {
								// add the return code as error code
								ptdFullfillmentEntryVO.setPtdErrorCode(returncode);
								ptdFullfillmentEntryVO.setPtdLogId(ptdLogId);
								fulfillmentErrors.add(ptdFullfillmentEntryVO);
								ptdEntryVO.setEmailSentFlag(1);
							}
						}
					}
					entirePtdList.add(ptdEntryVO);
				}
			}
		} catch (DataServiceException dataServiceException) {
			throw new SLBusinessServiceException(dataServiceException);
		}
		ptdDao.insertPTDRecords(entirePtdList);
		// Add records from this runs window that are in SLDB and aren't in PTD file.
		fulfillmentErrors.addAll(retrieveSLNonReconciledTransactions(buildPtdDateString()));
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("updateCount", successUpdateCount);
		map.put("fullfillmentErrorList", fulfillmentErrors);
		return map;
	}

}
