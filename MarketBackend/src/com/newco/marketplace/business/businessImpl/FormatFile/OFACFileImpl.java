package com.newco.marketplace.business.businessImpl.FormatFile;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.newco.marketplace.business.iBusiness.ach.IOFACFile;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.dto.vo.ach.OFACProcessLogVO;
import com.newco.marketplace.dto.vo.ach.OFACProcessQueueVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.iDao.OFACDao.OFACDao;
import com.newco.marketplace.util.BaseFileUtil;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.util.PropertiesUtils;

public class OFACFileImpl extends BaseFileUtil implements IOFACFile{
	private static final Logger logger = Logger.getLogger(OFACFileImpl.class.getName());
	private OFACDao ofacDao;
	private Cryptography cryptography;
	    
	@Override
	public void formatFile(File inputFile, File outputFile, HashMap propertyMap){
		FileFactory fileFactory = new FileFactory();
		OFACFileImpl ofacFileImpl = (OFACFileImpl)fileFactory.createFileImpl("OFAC");
	}
	
	/**
	 * Method to create a text file to pass the buyer/provider information to First Data
	 */
	public void writeOFACRecords() throws Exception {
		boolean isUpdateOFACProcessedDone = false;
		OFACProcessLogVO ofacProcessLogVO = new OFACProcessLogVO();
		try{
			// Fetching provider/buyer information for KYC file.
			ArrayList<OFACProcessQueueVO> ofacSummaryList= ofacDao.getOfacData();
			// Decrypting tax payer id for all providers/buyers.
			for(int i =0;i<ofacSummaryList.size();i++){
				CryptographyVO cryptographyVO = new CryptographyVO();
				if(StringUtils.isNotBlank(ofacSummaryList.get(i).getTaxPayerId())){
					cryptographyVO.setInput(ofacSummaryList.get(i).getTaxPayerId());
					cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);				
					cryptographyVO = cryptography.decryptKey(cryptographyVO);
					String taxPayerId = cryptographyVO.getResponse();
					ofacSummaryList.get(i).setTaxPayerId(taxPayerId);
				} else{
					ofacSummaryList.get(i).setTaxPayerId("");
				}	
			}
			if(null != ofacSummaryList && ofacSummaryList.size() != 0){
				ofacProcessLogVO = writeOFACRecordsToFile(ofacSummaryList);
				isUpdateOFACProcessedDone=true;
			} 
		} catch (DataAccessException dae) {
			logger.error("OFACFileImpl-->DataAccessException-->", dae);
			throw new BusinessServiceException("OFACFileImpl-->DataAccessException-->", dae);
		} catch(Exception e){
			logger.error("OFACFileImpl-->Exception-->", e);
			throw new BusinessServiceException("OFACFileImpl-->EXCEPTION-->", e);	
		} finally{
			ofacProcessLogVO.setProcessSuccessful(isUpdateOFACProcessedDone);
			ofacDao.insertOFACProcessLog(ofacProcessLogVO);
		}
	}
	
	/**
	 * Method to create a text file to pass the buyer/provider information to First Data
	 * This method will save the file in the required directory and logs the data in 
	 * ofac_process_log table.
	 */
	public OFACProcessLogVO  writeOFACRecordsToFile(ArrayList<OFACProcessQueueVO> ofacSummaryList) throws Exception{
		OFACProcessLogVO ofacProcessLogVO = new OFACProcessLogVO();
		Calendar cal = Calendar.getInstance();
		String periodStartDate = "";
		String periodEndDate = "";
		Long transactionCount = 0L;
		// Date format for file name 
		SimpleDateFormat sdf = new SimpleDateFormat(MPConstants.KYC_FILE_NAME_FORMAT);
		// Date format for Processed Date, Period Start Date and Period End Date
		SimpleDateFormat dateFormatWithTZ = new SimpleDateFormat(MPConstants.KYC_DATE_FORMAT);
		SimpleDateFormat dateFormatWithoutTZ = new SimpleDateFormat(MPConstants.KYC_DATE_FORMAT);
		// Setting Time Zone for Processed Date, Period Start Date and Period End Date as
		// EST5EDT
		dateFormatWithTZ.setTimeZone(TimeZone.getTimeZone(MPConstants.KYC_TIME_ZONE));
		String initiatedDate = dateFormatWithTZ.format(cal.getTime());
		// Assigning processedDate to OFACProcessLogVO for logging.
		ofacProcessLogVO.setProcessedDate(new Timestamp((dateFormatWithoutTZ.parse(initiatedDate)).getTime()));

		String dateStr = sdf.format(cal.getTime());
		String fileDir = PropertiesUtils.getPropertyValue(MPConstants.KYC_FILE_DIR);
		String fileName =  dateStr + "_KYC.txt";
		ofacProcessLogVO.setGeneratedFileName(fileName);
		logger.info("fileName should be: " + fileName);
		try{
			logger.info("Creating directory if one does not already exist. Dir will be: " + fileDir);
			boolean success = new File(fileDir).mkdir();
			if(success){
				logger.info("Directory was created successfully");
			} else{
				logger.info("Directory already exists");
			}
			// Fetching period end date of last KYC file from ofac_process_log table.
			// This date will be considered as the periodStartDate of current entry.
			Timestamp startDate = ofacDao.getPeriodStartDate();
			if(null == startDate){
				// If ofac_process_log table is empty, periodStartDate will be previous day.
				cal.add(Calendar.DAY_OF_YEAR, -1);
				periodStartDate = dateFormatWithTZ.format(cal.getTime());
				// Assigning periodStartDate to OFACProcessLogVO for logging.
				ofacProcessLogVO.setPeriodStartDate(new Timestamp((dateFormatWithoutTZ.parse(periodStartDate)).getTime()));
			} else{
				// Start date should be last period end date + one second.
				Calendar cal1 = Calendar.getInstance();
				cal1.setTimeInMillis(startDate.getTime());
				cal1.add(Calendar.SECOND, 1);
				periodStartDate = dateFormatWithoutTZ.format(cal1.getTime());
				// Assigning periodStartDate to OFACProcessLogVO for logging.
				ofacProcessLogVO.setPeriodStartDate(new Timestamp((dateFormatWithoutTZ.parse(periodStartDate)).getTime()));
			}
			//Setting the period end date
			// periodEndDate should be processedDate - one second.
			// Assigning periodEndDate to OFACProcessLogVO for logging.
			// SL-20075 - Correcting issue of getting future date for period end date
			if(null == startDate){
				cal.add(Calendar.DAY_OF_YEAR, 1);
			}
			cal.add(Calendar.SECOND, -1);
			periodEndDate = dateFormatWithTZ.format(cal.getTime());
			ofacProcessLogVO.setPeriodEndDate(new Timestamp((dateFormatWithoutTZ.parse(periodEndDate)).getTime()));
			
			//transaction count is the count of data records in the KYC file
			transactionCount = (long)(ofacSummaryList == null?0:ofacSummaryList.size());
			
			// Assigning transactionCount to OFACProcessLogVO for logging.
			ofacProcessLogVO.setTransactionCount(transactionCount);
			StringBuffer sb = new StringBuffer();
			// Writing Header Record to StringBuffer.
			writeOFACHeaderRecord(sb, initiatedDate);
			// Writing Detail Record to StringBuffer.
			writeOFACDetailRecord(ofacSummaryList, sb);
			// Writing Trailer Record to StringBuffer.
			writeOFACTrailerRecord(sb, periodStartDate, initiatedDate, periodEndDate, transactionCount);
			File outputFile = new File(fileDir + fileName);
			writeStringToFile(outputFile, sb.toString());
			
		} catch(IOException ioe){
			logger.error("OFACFileImpl IO Exception. Directory incorrect or could not write file", ioe);
			throw new Exception(ioe);
		} catch (DataAccessException dae) {
			logger.error("OFACFileImpl-->DataAccessException-->", dae);
			throw new BusinessServiceException("OFACFileImpl-->DataAccessException-->", dae);
		} catch(Exception e){
			logger.error("OFACFileImpl-->Exception-->", e);
			throw new BusinessServiceException("OFACFileImpl-->EXCEPTION-->", e);	
		}
		return ofacProcessLogVO;
	}
	
	/**
	 * Method to write the header record for KYC file
	 */
	private void writeOFACHeaderRecord(StringBuffer sb, String initiatedDate) {
		sb.append("0");
		sb.append("|");
		// Appending initiatedDate as processed date.
		sb.append(initiatedDate);
		sb.append("|");
		sb.append("UserType");
		sb.append("|");
		sb.append("Entity ID");
		sb.append("|");
		sb.append("TaxPayerID");
		sb.append("|");
		sb.append("User Name");
		sb.append("|");
		sb.append("User ID");
		sb.append("|");
		sb.append("Business Name");
		sb.append("|");
		sb.append("Admin Contact No");
		sb.append("|");
		sb.append("Admin First Name");
		sb.append("|");
		sb.append("Admin Last Name");
		sb.append("|");
		sb.append("Email");
		sb.append("|");
		sb.append("Street 1");
		sb.append("|");
		sb.append("Street 2");
		sb.append("|");
		sb.append("City");
		sb.append("|");
		sb.append("State");
		sb.append("|");
		sb.append("Zip");
		sb.append("|");
		sb.append("Apt No");
		sb.append("|");
		sb.append("V1 Account");
		sb.append("|");
		sb.append("V2 Account");
		sb.append("|");
		sb.append("Created Date");
		sb.append("|");
		sb.append("Date of Birth");
		sb.append("|");
		sb.append("Alternate ID Type");
		sb.append("|");
		sb.append("Alternate ID Country of Issuance");
		sb.append("|");
		sb.append("Alternate ID Number");
		sb.append("|");
		sb.append("\n");
	}
	
	/**
	 * Method to write the data record for KYC file
	 */
	private void writeOFACDetailRecord(ArrayList<OFACProcessQueueVO> ofacSummaryList, StringBuffer sb) {
		Iterator<OFACProcessQueueVO> i = ofacSummaryList.iterator();
		while(i.hasNext()){
			sb.append("1");
			sb.append("|");
			sb.append(i.next().toString());
			sb.append("\n");
		}
	}
	
	/**
	 * Method to write the trailer record for KYC file
	 */
	private void writeOFACTrailerRecord(StringBuffer sb, String periodStartDate, String initiatedDate, String periodEndDate, Long transactionCount) {
		sb.append("9");
		sb.append("|");
		// Appending initiatedDate as processed date.
		sb.append(initiatedDate);
		sb.append("|");
		sb.append(periodStartDate);
		sb.append("|");
		// Appending initiatedDate - one second as period end date.
		sb.append(periodEndDate);
		sb.append("|");
		// Transaction count should be Right justified, zero filled.
		sb.append(StringUtils.leftPad(transactionCount.toString(), 12, "0"));
		sb.append("|");
		// Transaction amount is 12 zeroes.
		sb.append(MPConstants.KYC_TRANSACTION_AMOUNT);
		sb.append("|");
		sb.append("\n");
	}
		
	public OFACDao getOfacDao() {
		return ofacDao;
	}
	public void setOfacDao(OFACDao ofacDao) {
		this.ofacDao = ofacDao;
	}
	public Cryptography getCryptography() {
		return cryptography;
	}
	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}
}
		

	

