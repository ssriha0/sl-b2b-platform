package com.servicelive.wallet.batch.activity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.batch.BaseFileProcessor;

// TODO: Auto-generated Javadoc
/**
 * 
 * 
 * @author nsanzer
 * Description: Class responsible for kicking off other classes that implement generatePlatformActivitiesWorksheet(wb).
 * When new and interesting reports are added, they could be hooked in here.
 * Report generated is:
 * /home/jboss/slive_data/node4/reconciliation/out/ach/daily_platform_activity_<todays_date>.xls
 */

public class DailyPlatformActivityBuilder extends BaseFileProcessor {

	/** The Constant fileNamePrefix. */
	private static final String fileNamePrefix = "daily_platform_activity_";
	
	private static Logger logger = Logger.getLogger(DailyPlatformActivityBuilder.class.getName());

	/** The daily entity balance report. */
	public IDailyPlatformActivity dailyEntityBalanceReport;

	/** The daily fulfillment health report. */
	public IDailyPlatformActivity dailyFulfillmentHealthReport;

	/** The daily reconciliation view file. */
	public IDailyPlatformActivity dailyReconciliationViewFile;

	/** The daily wallet balance verification. */
	public IDailyPlatformActivity dailyWalletBalanceVerification;

	/**
	 * Gets the email body.
	 * 
	 * @return the email body
	 * 
	 * @throws DataServiceException 
	 */
	private String getEmailBody() throws DataServiceException {

		return applicationProperties.getPropertyValue(CommonConstants.DAILY_RECONCILIATION_EMAIL_BODY);
	}

	/**
	 * Gets the email to.
	 * 
	 * @return the email to
	 * 
	 * @throws DataServiceException 
	 */
	private String getEmailTo() throws DataServiceException {

		return applicationProperties.getPropertyValue(CommonConstants.DAILY_RECONCILIATION_EMAIL_TO);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.IProcessor#process()
	 */
	public void process() throws SLBusinessServiceException {

		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			String fileName = fileNamePrefix + formatter.format(new Date());
			String fileNameWithDir = getFileDirectory(CommonConstants.DAILY_RECONCILIATION_FILE_DIRECTORY) + fileName + ".xls";
			HSSFWorkbook wb = new HSSFWorkbook();
			dailyReconciliationViewFile.generatePlatformActivitiesWorksheet(wb);
			dailyFulfillmentHealthReport.generatePlatformActivitiesWorksheet(wb);
			dailyWalletBalanceVerification.generatePlatformActivitiesWorksheet(wb);
			dailyEntityBalanceReport.generatePlatformActivitiesWorksheet(wb);
			// add additional reports here
			sendSpreadSheet(fileName, fileNameWithDir, wb);
		} catch (Exception e) {
			logger.error("Error happened during creation of Daily activity report", e);
			throw new SLBusinessServiceException(e);
		}
	}

	/**
	 * Description: Writes .xls to a directory and then sends it from there to recipients.
	 * 
	 * @param fileName 
	 * @param fileNameWithDir 
	 * 
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 * @throws MessagingException 
	 * @throws SLBusinessServiceException 
	 */
	public void sendSpreadSheet(String fileName, String fileNameWithDir,
			HSSFWorkbook wb ) throws FileNotFoundException, IOException, MessagingException, SLBusinessServiceException {

		try {
			FileOutputStream fileOut = new FileOutputStream(fileNameWithDir);
			wb.write(fileOut);
			fileOut.close();
			fileOut = null;
			wb = null;
			String[] sentTo = getEmailTo().split(";");
			emailTemplateBO.sendGenericEmailWithAttachment(sentTo, CommonConstants.SERVICE_LIVE_MAILID_SUPPORT, "Daily Reconciliation Report", getEmailBody(), fileNameWithDir,
				fileName);
		} catch (Exception e) {
			throw new SLBusinessServiceException(e);
		}
	}

	/**
	 * Sets the daily entity balance report.
	 * 
	 * @param dailyEntityBalanceReport the new daily entity balance report
	 */
	public void setDailyEntityBalanceReport(IDailyPlatformActivity dailyEntityBalanceReport) {

		this.dailyEntityBalanceReport = dailyEntityBalanceReport;
	}

	/**
	 * Sets the daily fulfillment health report.
	 * 
	 * @param dailyFulfillmentHealthReport the new daily fulfillment health report
	 */
	public void setDailyFulfillmentHealthReport(IDailyPlatformActivity dailyFulfillmentHealthReport) {

		this.dailyFulfillmentHealthReport = dailyFulfillmentHealthReport;
	}

	/**
	 * Sets the daily reconciliation view file.
	 * 
	 * @param dailyReconciliationViewFile the new daily reconciliation view file
	 */
	public void setDailyReconciliationViewFile(IDailyPlatformActivity dailyReconciliationViewFile) {

		this.dailyReconciliationViewFile = dailyReconciliationViewFile;
	}

	/**
	 * Sets the daily wallet balance verification.
	 * 
	 * @param dailyWalletBalanceVerification the new daily wallet balance verification
	 */
	public void setDailyWalletBalanceVerification(IDailyPlatformActivity dailyWalletBalanceVerification) {

		this.dailyWalletBalanceVerification = dailyWalletBalanceVerification;
	}

}
