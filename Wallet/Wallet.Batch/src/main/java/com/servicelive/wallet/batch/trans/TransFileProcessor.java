package com.servicelive.wallet.batch.trans;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.util.MoneyUtil;
import com.servicelive.wallet.batch.BaseFileProcessor;
import com.servicelive.wallet.batch.IProcessor;
import com.servicelive.wallet.batch.trans.dao.ITransFileDao;
import com.servicelive.wallet.batch.trans.vo.TransactionBatchVO;
import com.servicelive.wallet.batch.trans.vo.TransactionProcessLogVO;
import com.servicelive.wallet.batch.trans.vo.TransactionRecordVO;

/**
 * The Class TransFileProcessor.
 */
public class TransFileProcessor extends BaseFileProcessor implements IProcessor {

	private static final Logger logger = Logger.getLogger(TransFileProcessor.class.getName());

	/** The Constant fileName. */
	private static final String fileName = "trans";

	/** The Constant fileNameExt. */
	private static final String fileNameExt = ".txt";

	// private static final String TRANS_FILE_ARCHIVE_DIRECTORY = PropertiesUtils.getPropertyValue(CommonConstants.TRANS_FILE_ARCHIVE_DIRECTORY);
	/** The Constant MAIL_BODY. */
	private static final String MAIL_BODY = "Process Name: TransFileProcessManager\n" + "Date : " + new Timestamp(System.currentTimeMillis()) + "\n" + "Exception Details: ";

	/** The trans file dao. */
	private ITransFileDao transFileDao;

	/** The trans file transformer. */
	private TransFileTransformer transFileTransformer;

	/**
	 * Adds the trans log.
	 * 
	 * @param transactionProcessLogVO 
	 * 
	 * @return the long
	 * 
	 * @throws DataServiceException 
	 */
	private Long addTransLog(TransactionProcessLogVO transactionProcessLogVO) throws DataServiceException {

		return transFileDao.updateTransLog(transactionProcessLogVO);
	}

	/**
	 * Check two digit format.
	 * 
	 * @param input 
	 * 
	 * @return the string
	 */
	private String checkTwoDigitFormat(String input) {

		if (input.length() != 2) {
			input = "0" + input;
		}
		return input;
	}

	/**
	 * Gets the consolidated deposit entry amount from ach process queue.
	 * 
	 * @return the consolidated deposit entry amount from ach process queue
	 * 
	 * @throws DataServiceException 
	 */
	private Double getConsolidatedDepositEntryAmountFromAchProcessQueue() throws DataServiceException {

		return transFileDao.getConsolidatedDepositEntryAmountFromAchProcessQueue();
	}

	/**
	 * Gets the consolidated refund entry amount from ach process queue.
	 * 
	 * @return the consolidated refund entry amount from ach process queue
	 * 
	 * @throws DataServiceException 
	 */
	private Double getConsolidatedRefundEntryAmountFromAchProcessQueue() throws DataServiceException {

		return transFileDao.getConsolidatedRefundEntryAmountFromAchProcessQueue();
	}

	/**
	 * Gets the credit transactions.
	 * 
	 * @return the credit transactions
	 * 
	 * @throws DataServiceException 
	 */
	private List<TransactionRecordVO> getCreditTransactions() throws DataServiceException {

		return transFileDao.getAllCreditCardTransactions();
	}

	/**
	 * Gets the date.
	 * 
	 * @return the date
	 */
	private String getDate() {

		Calendar calendar = new GregorianCalendar();
		int month = calendar.get(Calendar.MONTH) + 1;
		String date = calendar.get(Calendar.YEAR) + "-" + checkTwoDigitFormat(String.valueOf(month)) + "-" + checkTwoDigitFormat(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
		return date;
	}

	/**
	 * Gets the file full path.
	 * 
	 * @return the file full path
	 * 
	 * @throws DataServiceException 
	 */
	public String getFileFullPath() throws DataServiceException {

		return getFileDirectory(CommonConstants.TRANS_FILE_DIRECTORY) + fileName + getDate() + getTime() + fileNameExt;
	}

	/**
	 * Gets the time.
	 * 
	 * @return the time
	 */
	private String getTime() {

		Calendar calendar = new GregorianCalendar();
		String time = "-" + checkTwoDigitFormat(String.valueOf(calendar.get(Calendar.HOUR)));
		return time;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.IProcessor#process()
	 */
	public void process() throws SLBusinessServiceException {

		String lineBreak = "\n";
		String machineName = "";
		String fullFileName = "";
		String errorDirName;
		ArrayList<TransactionRecordVO> al=null;
		try {
			String slStoreNo = applicationProperties.getPropertyValue(CommonConstants.SL_STORE_NO);
			fullFileName = getFileFullPath();
			logger.info("creating file " + fullFileName);
			machineName = InetAddress.getLocalHost().getHostName() + "( " + (InetAddress.getLocalHost().getHostAddress()) + " )";
			
			//SL-20853 Trans file changes
			boolean pciVersion=isPCIVersion();   
            if(pciVersion){
            	al = (ArrayList<TransactionRecordVO>) getTransactions();
            }
            else {
            	al = (ArrayList<TransactionRecordVO>) getCreditTransactions();
            }
			ArrayList<Long> achProcessIds = new ArrayList<Long>();
			double creditCardDepositsSum = 0.0;
			double creditCardRefundsSum = 0.0;
			
            FileOutputStream fos = new FileOutputStream(fullFileName, false);

			for (TransactionRecordVO transactionRecordVO : al) {
				transactionRecordVO.setPciVersion(pciVersion); 
				logger.info("inside the for loop");
                if (transactionRecordVO.getTransactionType() == CommonConstants.CREDIT_DEPOSIT_TRANSACTION_TYPE_ID)
                {
                	creditCardDepositsSum = creditCardDepositsSum + transactionRecordVO.getAmount(); 
                }
                else if (transactionRecordVO.getTransactionType() == CommonConstants.CREDIT_REFUND_TRANSACTION_TYPE_ID)
                {
                	creditCardRefundsSum = MoneyUtil.getRoundedMoney(creditCardRefundsSum + transactionRecordVO.getAmount());
                } 
                
                fos.write(Integer.parseInt("B1", 16));
                fos.write(transFileTransformer.transformHeaderData(transactionRecordVO, slStoreNo));
                
                fos.write(Integer.parseInt("C1", 16));
                fos.write(transFileTransformer.transformLineItemData(transactionRecordVO));
                
                fos.write(Integer.parseInt("B5", 16));
                fos.write(transFileTransformer.transformPaymentData(transactionRecordVO));
                fos.write(lineBreak.getBytes());
                
                achProcessIds.add(transactionRecordVO.getAchProcessId());
			}
            
            fos.flush();
            fos.close();
            
			Double consolidatedRollUpDepositAmt = getConsolidatedDepositEntryAmountFromAchProcessQueue();
			Double consolidatedRollUpRefundAmt = getConsolidatedRefundEntryAmountFromAchProcessQueue();

			if (consolidatedRollUpDepositAmt == null) {
				consolidatedRollUpDepositAmt = 0.0;
			}

			if (consolidatedRollUpRefundAmt == null) {
				consolidatedRollUpRefundAmt = 0.0;
			}
			creditCardDepositsSum = MoneyUtil.getRoundedMoney(creditCardDepositsSum);
			Long transLogid = addTransLog(transFileTransformer.getTransactionProcessLogVO(achProcessIds.size(), fullFileName, creditCardDepositsSum, creditCardRefundsSum));
            
			// update database status- adding transProcessLogId and updating processId for ach_process_queue table
			TransactionBatchVO transactionBatchVO = new TransactionBatchVO();
			transactionBatchVO.setAchProcessIds(achProcessIds);
			transactionBatchVO.setProcessLogId(transLogid);
			logger.info("marking all the transactions to status 90");
			updateTransactionStatus(transactionBatchVO);

			if ((consolidatedRollUpDepositAmt.doubleValue() != creditCardDepositsSum) || (consolidatedRollUpRefundAmt.doubleValue() != MoneyUtil.getRoundedMoney(creditCardRefundsSum))) {
				StringBuilder sb = new StringBuilder();
				sb.append("consolidatedRollUpDepositAmt '").append(consolidatedRollUpDepositAmt.doubleValue()).append("' == creditCardDepositsSum '").append(creditCardDepositsSum);
				sb.append("\nconsolidatedRollUpRefundAmt '").append(consolidatedRollUpRefundAmt.doubleValue()).append("' == creditCardRefundsSum '").append(creditCardRefundsSum);
				sb.append("\n File ").append(fullFileName).append(" is wrong, but being created for posible resend anyway.");
				logger.error(sb.toString());
				emailTemplateBO.sendFailureEmail("System : " + machineName + " \n" + MAIL_BODY + CommonConstants.ACH_TRAN_TOTAL_MISMATCH + " \n" + sb.toString());
			
				//On error moving the file to the error folder
				try{
					errorDirName = getFileDirectory(CommonConstants.TRANS_FILE_ERROR_DIRECTORY);
				}catch (DataNotFoundException e) {
					errorDirName = getFileDirectory(CommonConstants.TRANS_FILE_DIRECTORY)+"error/";
					File errorDir = new File(errorDirName);
					if(!errorDir.exists()){
						errorDir.mkdir();
					}
				}

				boolean isErrorFileMoved = new File(fullFileName).renameTo(new File(errorDirName, fileName + getDate() + getTime() + fileNameExt));
				if(!isErrorFileMoved){
					logger.info("Moving Error file failed.");
				}
			} else {
				logger.info("all values matched encrypting file");
				Runtime.getRuntime().exec("/ftp/scripts/encryptFile " + fullFileName);
			}
			transFileDao.updateCCRollupRecords(); //This has to happen after the validation in the preceeding if statement.
			
		} catch (Exception e) {
			logger.error("System : " + machineName + " \n" + "Error in producing Transfile:\n" + e.toString(), e);
			emailTemplateBO.sendFailureEmail("System : " + machineName + " \n" + "Error in producing Transfile:\n" + e.toString()); 
		}

	}

	

	/**
	 * Sets the trans file dao.
	 * 
	 * @param transFileDao the new trans file dao
	 */
	public void setTransFileDao(ITransFileDao transFileDao) {

		this.transFileDao = transFileDao;
	}

	/**
	 * Sets the trans file transformer.
	 * 
	 * @param transFileTransformer the new trans file transformer
	 */
	public void setTransFileTransformer(TransFileTransformer transFileTransformer) {

		this.transFileTransformer = transFileTransformer;
	}

	/**
	 * Update transaction status.
	 * 
	 * @param transactionBatchVO 
	 * 
	 * @throws DataServiceException 
	 */
	private void updateTransactionStatus(TransactionBatchVO transactionBatchVO) throws DataServiceException {

		transFileDao.updateTransactionStatus(transactionBatchVO);
	}
    
	/**
	 * SL-20853
	 * Method to fetch the details for the transaction file generation
	 * @return List<TransactionRecordVO>
	 * @throws DataServiceException
	 */
	private List<TransactionRecordVO> getTransactions() throws DataServiceException{
		return transFileDao.getTransactions();
	}
	
	/**
	 * SL-20853 
	 * Method to check whether the file is PCI Version or not
	 * @return boolean
	 * @throws DataServiceException
	 */
	private boolean isPCIVersion() throws DataServiceException {
		return transFileDao.isPCIVersion();
		
	}
}
