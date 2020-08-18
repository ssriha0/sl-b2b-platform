package com.servicelive.wallet.batch.ptd;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.batch.ptd.vo.PTDFullfillmentEntryVO;
import com.servicelive.wallet.serviceinterface.vo.ValueLinkEntryVO;
import com.servicelive.wallet.valuelink.IValueLinkBO;

// TODO: Auto-generated Javadoc
/**
 * The Class PTDMessageBuilder.
 */
public class PTDMessageBuilder {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(PTDMessageBuilder.class.getName());

	/** The value link bo. */
	private IValueLinkBO valueLink;

	/**
	 * Sets the value link bo.
	 * 
	 * @param valueLinkBO the new value link bo
	 */
	public void setValueLink(IValueLinkBO valueLink) {
		this.valueLink = valueLink;
	}

	/**
	 * Description: Method to append details of fullfillment transaction to the email report.
	 * 
	 * @param mailBody 
	 * @param feVO 
	 * 
	 * @throws SLBusinessServiceException 
	 */
	private void appendPtdEmailSectionDetail(StringBuilder mailBody, PTDFullfillmentEntryVO feVO) throws SLBusinessServiceException {

		long fullfillmentEntryId = 0;
		ValueLinkEntryVO feVoFromDb = new ValueLinkEntryVO();
		if (feVO.getFullfillmentEntryId() != null) {
			fullfillmentEntryId = feVO.getFullfillmentEntryId();
			feVoFromDb = valueLink.getValueLinkMessageDetail(fullfillmentEntryId);
		}
		mailBody.append("\n   Trans ID: " + feVO.getFullfillmentEntryId() + "\tPTD req code: " + feVO.getPtdRequestCode() + "\tPTD res code: " + feVO.getPtdResponseCode()
			+ "\tPTD src code: " + feVO.getPtdSourceCode() + "\tPTD PAN: " + feVO.getPrimaryAccountNumber() + "\tTrans Amt: " + feVO.getTransAmount());

		if (feVoFromDb != null) {
			mailBody.append("\n   Group ID: " + feVoFromDb.getFullfillmentGroupId() + "\tRule ID: " + feVoFromDb.getFullfillmentEntryRuleId() + "\tEntity ID: "
				+ feVoFromDb.getLedgerEntityId() + "\tBus Tran ID: " + feVoFromDb.getBusTransId());
		}
	}

	/**
	 * Description: If counter for error group is zero, append the header to the report section.
	 * 
	 * @param mailBody 
	 * @param counter 
	 * @param feVO 
	 */
	private void appendPtdEmailSectionHeader(StringBuilder mailBody, int counter, PTDFullfillmentEntryVO feVO) {

		if (feVO.getPtdErrorCode() == CommonConstants.EC_AMOUNT_MISMATCH && counter == 0) {
			mailBody.append("\nRecords in error with Amount Mismatches:");
		} else if (feVO.getPtdErrorCode() == CommonConstants.EC_SIGN_MISMATCH && counter == 0) {
			mailBody.append("\nRecords in error with message-type Mismatches (redemption or reload):");
		} else if (feVO.getPtdErrorCode() == CommonConstants.EC_BAD_RESPONSE && counter == 0) {
			mailBody.append("\nRecords that are time out reversals from Sharp :");
		} else if (feVO.getPtdErrorCode() == CommonConstants.EC_NO_RESPONSE && counter == 0) {
			mailBody.append("\nRecords with no Response:");
		} else if (feVO.getPtdErrorCode() == CommonConstants.EC_PTD_ENTRY_NOT_FOUND && counter == 0) {
			mailBody.append("\nRecords in PTD file not found in SLDB:");
		} else if (feVO.getPtdErrorCode() == CommonConstants.EC_SLDB_ENTRY_NOT_FOUND && counter == 0) {
			mailBody.append("\nRecords in SLDB with no match in PTD from ValueLink:");
		} else if (feVO.getPtdErrorCode() == CommonConstants.EC_VLBC_ACTIVITY && counter == 0) {
			mailBody.append("\nValueLink activity records, that are not balance or history inquiries:");
		}
	}

	/**
	 * Creates the ptd failure report email.
	 * 
	 * @param feVOs 
	 * 
	 * @return the string
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public String createPTDFailureReportEmail(ArrayList<PTDFullfillmentEntryVO> feVOs) throws SLBusinessServiceException {

		// Create Email to report on transactions to the SL Ops that still could not be reconciled
		logger.info("BEGIN send SL Ops alert email");
		String mailBody = createPTDTransactionEmailDetails(feVOs);
		logger.info("END send SL Ops alert email");
		return mailBody;
	}

	/**
	 * Description: Formats the summary email to be sent with unreconciled PTD entries.
	 * 
	 * @param feVOs 
	 * 
	 * @return 
	 * 
	 * @throws SLBusinessServiceException 
	 * @throws Exception 	 */
	private String createPTDTransactionEmailDetails(ArrayList<PTDFullfillmentEntryVO> feVOs) throws SLBusinessServiceException {

		StringBuilder mailBody = new StringBuilder();
		mailBody.append("\n\nTotal Records in error: " + feVOs.size());
		mailBody.append("\n__________________________________");
		mailBody.append("\n" + CommonConstants.PTD_NON_RECONCILED_TRANS_MSG + "For \nptd_process_log.ptd_log_id: " + feVOs.get(0).getPtdLogId() + "\n\n");
		int holdEc = feVOs.get(0).getPtdErrorCode();

		int counter = 0;
		for (PTDFullfillmentEntryVO feVO : feVOs) {
			if (feVO.getPtdErrorCode() != holdEc) {
				holdEc = feVO.getPtdErrorCode();
				mailBody.append("\nSubtotal:" + counter);
				mailBody.append("\n");
				mailBody.append("\n----------------------------------");
				counter = 0;
			}

			appendPtdEmailSectionHeader(mailBody, counter, feVO);
			appendPtdEmailSectionDetail(mailBody, feVO);
			counter++;
		}
		mailBody.append("\nSubtotal:" + counter);
		return mailBody.toString();
	}

}
