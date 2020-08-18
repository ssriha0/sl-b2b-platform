package com.newco.batch.background;

import java.io.BufferedReader;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.business.iBusiness.audit.IAuditBusinessBean;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.vo.provider.BackgroundChkStatusVO;
import com.newco.marketplace.vo.provider.TMBackgroundCheckVO;

import com.newco.marketplace.persistence.iDao.vendor.VendorResourceDao;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.utils.DateUtils;
import com.newco.batch.ABatchProcess;
import com.newco.batch.background.vo.BackgroundCheckStatusVO;
import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.interfaces.AuditStatesInterface;
import com.newco.marketplace.interfaces.MPConstants;
import com.servicelive.common.util.BoundedBufferedReader;

public class BackgroundCheckPollingProcess extends ABatchProcess implements
		AuditStatesInterface {
	// test

	private static final Logger logger = Logger
			.getLogger(BackgroundCheckPollingProcess.class);

	private static Double ONE_HOUR = (double) (60 * 60 * 1000f);
	private static SimpleDateFormat headerDate = new SimpleDateFormat("EEEE, MMMM d, yyyy");
	public static int ZER0=0;

	String inputFileName;

	private IAuditBusinessBean myAuditBusinessBean;
	private VendorResourceDao vendorResourceDao;
	private DataSourceTransactionManager tm;
	private Cryptography cryptography;
	private TransactionStatus status;

	public IAuditBusinessBean getMyAuditBusinessBean() {
		return myAuditBusinessBean;
	}

	public Cryptography getCryptography() {
		return cryptography;
	}

	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}

	public void setMyAuditBusinessBean(IAuditBusinessBean myAuditBusinessBean) {
		this.myAuditBusinessBean = myAuditBusinessBean;
	}

	public VendorResourceDao getVendorResourceDao() {
		return vendorResourceDao;
	}

	public void setVendorResourceDao(VendorResourceDao vendorResourceDao) {
		this.vendorResourceDao = vendorResourceDao;
	}

	public DataSourceTransactionManager getTm() {
		return tm;
	}

	public void setTm(DataSourceTransactionManager tm) {
		this.tm = tm;
	}
   //SL-19866 executes old file format
	@Override
	public int execute() throws BusinessServiceException {

		File myFile = new File(inputFileName);
		BoundedBufferedReader aStream;
		try {
			aStream = new BoundedBufferedReader(new FileReader(myFile));
		} catch (FileNotFoundException fne) {
			logger.error(fne.getMessage());
			throw (new BusinessServiceException(fne.getMessage()));
		}

		// myAuditBusinessBean =
		// (IAuditBusinessBean)ctx.getBean("auditBusinessBean");
		// vendorResourceDao = (VendorResourceDao)
		// ctx.getBean("vendorResourceDao");
		// tm = (DataSourceTransactionManager)
		// ctx.getBean("transactionManager");
		// cryptography = (Cryptography) ctx.getBean("cryptography");

		String line;
		BackgroundCheckStatusVO myBackgroundCheckStatusVO = null;

		try {
			while ((line = aStream.readLinePlusOne()) != null) {
				myBackgroundCheckStatusVO = processRecord(line,
						myBackgroundCheckStatusVO,false);
			}

		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} finally {
			// close input file
			try {
				if (aStream != null) {
					aStream.close();
					logger.info("<BackgroundCheckPollingProcess::execute()>: Done processing input file");
				}
			} catch (Exception e) {
				// logging error as this can never occur
				logger.error(
						"Caught inside: <BackgroundCheckPollingProcess::execute()>:  Error: Got an exception that should not occur ",
						e);
			}
		}

		return 0;
	}

	// SL-19866 -process new PlusOne File format.

	public int executePlusOne () throws BusinessServiceException {

		File myFile = new File(inputFileName);
		BoundedBufferedReader aStream;
		try {
			aStream = new BoundedBufferedReader(new FileReader(myFile));
		} catch (FileNotFoundException fne) {
			logger.error(fne.getMessage());
			throw (new BusinessServiceException(fne.getMessage()));
		}

		// myAuditBusinessBean =
		// (IAuditBusinessBean)ctx.getBean("auditBusinessBean");
		// vendorResourceDao = (VendorResourceDao)
		// ctx.getBean("vendorResourceDao");
		// tm = (DataSourceTransactionManager)
		// ctx.getBean("transactionManager");
		// cryptography = (Cryptography) ctx.getBean("cryptography");

		String line;
		BackgroundCheckStatusVO myBackgroundCheckStatusVO = null;

		try {
			while ((line = aStream.readLinePlusOne()) != null) {
				
				long time=System.currentTimeMillis();
				myBackgroundCheckStatusVO = processRecord(line,
						myBackgroundCheckStatusVO,true);
				logger.info(" total time taken to process a record "+(System.currentTimeMillis() - time));
				
			}

		} catch (IOException e) {
			// Auto-generated catch block
			e.printStackTrace();
		} finally {
			// close input file
			try {
				if (aStream != null) {
					aStream.close();
					logger.info("<BackgroundCheckPollingProcess::execute()>: Done processing input file");
				}
			} catch (Exception e) {
				// logging error as this can never occur
				logger.error(
						"Caught inside: <BackgroundCheckPollingProcess::execute()>:  Error: Got an exception that should not occur ",
						e);
			}
		}

		return 0;
	}
	
	// sl-19667 update recertification status
	public void updateRecertificationstatus(){
		try{
			logger.info("update Recertificationstatus ");
			
			logger.info("PlusOne batch start time : "+Calendar.getInstance().getTime());
			vendorResourceDao.clearRecertificationstatus();	
			logger.info("PlusOne batch between 1 and 2 time : "+Calendar.getInstance().getTime());
			vendorResourceDao.updateRecertificationstatus();
			logger.info("PlusOne batch between 2 and 3 time : "+Calendar.getInstance().getTime());
			// update recertification status as 'In process'
			vendorResourceDao.updateRecertificationstatusInProcess();
			logger.info("PlusOne batch end time : "+Calendar.getInstance().getTime());
		}
		catch(Exception e){
			logger.info("Error in updating recertification "+ e);
		}
	}
	// sl-19387 processing each line of plus one file.
	BackgroundCheckStatusVO processRecord(String line,
			BackgroundCheckStatusVO myBackgroundCheckStatusVO,boolean isNewFileFormat) {
		try {
			logger.info("start processing record");
			long time=System.currentTimeMillis();
			if(isNewFileFormat){
				// sl-19387 new file format processing
				myBackgroundCheckStatusVO = parseLineNew(line);
			}else{
			myBackgroundCheckStatusVO = parseLine(line);
			}
	logger.info("time taken to parse Line"+(System.currentTimeMillis()-time));
	time=System.currentTimeMillis();
			TMBackgroundCheckVO tmbcVO = new TMBackgroundCheckVO();
			tmbcVO.setResourceId(myBackgroundCheckStatusVO
					.getTechId());
			// sl-19387 ignore the record with 'I' as screening status.
			if (Constants.IGNORE.equalsIgnoreCase(myBackgroundCheckStatusVO.getScreeningStatus())) {
				logger.info("**** ALERT - Ignore the I record ");		
				BackgroundChkStatusVO backgroundCheckStatusVO=convertDtotoVo(myBackgroundCheckStatusVO);
				// sl-19387 insert the backgroundcheck error 
				vendorResourceDao.insertBackgroundError(backgroundCheckStatusVO);
				return myBackgroundCheckStatusVO;
			}
			
			// getting background check status of the resource
			String bgCheckStatus = vendorResourceDao
					.getBackgroundCheckStatus(tmbcVO);
			logger.info("time taken vendorResourceDao.getBackgroundCheckStatus query "+(System.currentTimeMillis()-time));
			time=System.currentTimeMillis();
			if(null==bgCheckStatus || ("").equals(bgCheckStatus)){
				// sl-19387 if the resourceId(techId) doesnot exist get the resourceId with the plusOne key.
				
				Integer resourceIdFromPlusOneKey=vendorResourceDao.getResourceIdFromPlusOneKey(CryptoUtil.encryptKeyForSSNAndPlusOne(myBackgroundCheckStatusVO.getPlusoneKey()));
				logger.info("time taken vendorResourceDao.getResourceIdFromPlusOneKey query "+(System.currentTimeMillis()-time));
				time=System.currentTimeMillis();
				if(null!=resourceIdFromPlusOneKey){
					// sl-19387 set the resourceId in myBackgroundCheckStatusVO's techId
					tmbcVO.setResourceId(resourceIdFromPlusOneKey.toString());
					myBackgroundCheckStatusVO.setTechId(resourceIdFromPlusOneKey.toString());
					bgCheckStatus=vendorResourceDao
							.getBackgroundCheckStatus(tmbcVO);
					
					logger.info("time taken vendorResourceDao.getBackgroundCheckStatus(t query "+(System.currentTimeMillis()-time));
					time=System.currentTimeMillis();
				}
			}
			
			logger.info("now auditing dude=" + myBackgroundCheckStatusVO
					.getTechId());
			auditProvider(myBackgroundCheckStatusVO, bgCheckStatus);
			logger.info("time taken auditProvider "+(System.currentTimeMillis()-time));
			time=System.currentTimeMillis();

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (BusinessServiceException bse) {
			// there was a problem with the record
			logger.info("**** ALERT - Had a parsing error for the following record:\n****"
					+ line + "msg=" + bse.getMessage());
		} catch (DataServiceException e) {
			logger.info("Had an error in Background Check Process for "
					+ myBackgroundCheckStatusVO.getTechId() + ":"
					+ myBackgroundCheckStatusVO.getTechFname() + ":"
					+ myBackgroundCheckStatusVO.getTechLname() + "msg="
					+ e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return myBackgroundCheckStatusVO;
	}
	
	void auditProvider(BackgroundCheckStatusVO myBackgroundChkStatusVO,
			String bgCheckStatus)  {
          try{
        	 boolean bkngcheckerror=false;
	
		if (null==bgCheckStatus || ("").equals(bgCheckStatus)) {
			// log and return to processing
			logger.error("**** ALERT - bad techID came in "
					+ myBackgroundChkStatusVO.getTechId());
			BackgroundChkStatusVO backgroundCheckStatusVO=convertDtotoVo(myBackgroundChkStatusVO);
			// insert the backgroundcheck error 
			vendorResourceDao.insertBackgroundError(backgroundCheckStatusVO);
			return;
		}
	
		// SL-19667 get the previous background check information of the resource
		TMBackgroundCheckVO previousBackgroundInfo = new TMBackgroundCheckVO();
		previousBackgroundInfo.setResourceId(myBackgroundChkStatusVO.getTechId());
		long time=System.currentTimeMillis();
		previousBackgroundInfo=vendorResourceDao.getBackgroundCheckInformation(previousBackgroundInfo);
		logger.info("time taken vendorResourceDao.getBackgroundCheckInformation(p query "+(System.currentTimeMillis()-time));
		time=System.currentTimeMillis();
		//sl-19667 change the logic of background calculation
		/*
		 * 1)If Overall='Y' and Criminal ='Y' and Driving 'Y' => Clear
		 * 2)IfOverall='Y' and Criminal ='Y' and Driving 'H' => Clear 
		 * 3)If Overall='N' or Criminal or Driving = N => Adverse Findings
		 * 4) If Overall = 'P' and Recertification indicator <> 'Y' => In Process
		 * 5) If Overall = 'P' and Recertification indicator = 'Y' => Do Not
		 * Update 
		 * 6) If Overall = 'E' => Do not update the Background Check
		 * status, but update the certification validity as Expired.
		 */
		
		
		
		
		String teamMemberStatus="";
		String bagCheckStatus="";
		boolean doAudit=false;
		TMBackgroundCheckVO tmBackgroundCheckVO;
		if ((myBackgroundChkStatusVO.getOverall().equals(Constants.RATING_N))
				|| (myBackgroundChkStatusVO.getCrim().equals(Constants.RATING_N))
				|| (myBackgroundChkStatusVO.getDriv().equals(Constants.RATING_N))
				
				) {
			if (!bgCheckStatus
					.equals(AuditStatesInterface.RESOURCE_BACKGROUND_CHECK_ADVERSE_FINDINGS)) {
			
					teamMemberStatus=AuditStatesInterface.RESOURCE_PENDING_APPROVAL;
					doAudit=true;
				
			} 
			bagCheckStatus=AuditStatesInterface.RESOURCE_BACKGROUND_CHECK_ADVERSE_FINDINGS;

		}
		
		else 	if ((myBackgroundChkStatusVO.getOverall().equals(Constants.RATING_Y))
				&& (myBackgroundChkStatusVO.getCrim().equals(Constants.RATING_Y))
				&& ((myBackgroundChkStatusVO.getDriv().equals(Constants.RATING_Y) || myBackgroundChkStatusVO
						.getDriv().equals(Constants.RATING_H)))) {
			// if there are all Y's status is moved to passed
		 
				if (!bgCheckStatus
						.equals(AuditStatesInterface.RESOURCE_BACKGROUND_CHECK_CLEAR)) {
					// switch the status to IN PROCESS
						teamMemberStatus=AuditStatesInterface.RESOURCE_PENDING_APPROVAL;
						doAudit=true;	
				} 
				bagCheckStatus=AuditStatesInterface.RESOURCE_BACKGROUND_CHECK_CLEAR;

			} else if ((myBackgroundChkStatusVO.getOverall().equals(Constants.RATING_P))
					&& !((Constants.RECERTIFY).equals(previousBackgroundInfo.getRequestType()))
					) {
				if (!bgCheckStatus
						.equals(AuditStatesInterface.RESOURCE_BACKGROUND_CHECK_INPROCESS)) {
					// switch the status to IN PROCESS
						teamMemberStatus=AuditStatesInterface.RESOURCE_INCOMPLETE;
						doAudit=false;	
				}
				
				if(Constants.CLEAR_STATE.equals(previousBackgroundInfo
						.getBackgroundCheckStatus())){
					bagCheckStatus=AuditStatesInterface.RESOURCE_BACKGROUND_CHECK_CLEAR;
					previousBackgroundInfo
					.setRequestType(Constants.REQUEST_TYPE_R);
				}else{
				bagCheckStatus=AuditStatesInterface.RESOURCE_BACKGROUND_CHECK_INPROCESS;
				} 
			}

		logger.info("now auditing dude="
				+ myBackgroundChkStatusVO.getTechId() + "setting to "
				+ bagCheckStatus);

	
		
		if(null==bagCheckStatus || ("").equals(bagCheckStatus)){
			bagCheckStatus=previousBackgroundInfo.getBackgroundCheckStatus();
		}
		/**
		 * The code change has been made under JIRA Ticket No.- SL-17594
		 */
		// Splitting the plus One Key
		String ssnNameFrmFile = null;
		String plusOneKey = null;
		plusOneKey = myBackgroundChkStatusVO.getPlusoneKey();
		// get last 4 digits of ssn after the first 9 digits from the plusOne key of the file
		ssnNameFrmFile = plusOneKey.substring(9);
		// get last 4 digits of ssn from vendor_resource table
		//String ssnFrmDB = vendorResourceDao.getSsnFrmDB(Integer
		//		.valueOf(myBackgroundChkStatusVO.getTechId()));
		TMBackgroundCheckVO personalInfo = new TMBackgroundCheckVO();
		personalInfo=vendorResourceDao.getPersonalInfoForResource(Integer
				.valueOf(myBackgroundChkStatusVO.getTechId()));
		logger.info("time taken vendorResourceDao.getPersonalInfoForResource( query "+(System.currentTimeMillis()-time));
		time=System.currentTimeMillis();
		
		//R11_2
		//SL-20421
		if(StringUtils.isNotBlank(personalInfo.getFirstName())){
			personalInfo.setFirstName(personalInfo.getFirstName().trim());
		}
		if(StringUtils.isNotBlank(personalInfo.getLastName())){
			personalInfo.setLastName(personalInfo.getLastName().trim());
		}
		if(StringUtils.isNotBlank(myBackgroundChkStatusVO.getTechFname())){
			myBackgroundChkStatusVO.setTechFname(myBackgroundChkStatusVO.getTechFname().trim());
		}
		if(StringUtils.isNotBlank(myBackgroundChkStatusVO.getTechLname())){
			myBackgroundChkStatusVO.setTechLname(myBackgroundChkStatusVO.getTechLname().trim());
		}
		
		// finding match by comparing the input from file and data of the resource
		if (ssnNameFrmFile.equals(personalInfo.getSsn()) && myBackgroundChkStatusVO.getTechLname().
				equalsIgnoreCase(personalInfo.getLastName()) && myBackgroundChkStatusVO.getTechFname().
						equalsIgnoreCase(personalInfo.getFirstName() )) {
			beginWork();
			// create an audit record for vendor resource
			if (doAudit){
				myAuditBusinessBean.auditVendorResource(
						Integer.parseInt(myBackgroundChkStatusVO.getTechId()),   
						teamMemberStatus);
			}
			logger.info("time taken myAuditBusinessBean.auditVendorResource query "+(System.currentTimeMillis()-time));
			time=System.currentTimeMillis();
			// update is performed based on SSN value of the techId
			//String ssn = vendorResourceDao.getTechnicianSsn(Integer
			//		.valueOf(myBackgroundChkStatusVO.getTechId()));

			// update resource wf_state_id, plus one key, verification and
			// re-verification dates
			TMBackgroundCheckVO tmbcVO = new TMBackgroundCheckVO();
			tmbcVO.setResourceId(myBackgroundChkStatusVO.getTechId());
			tmbcVO.setWfEntity(AuditStatesInterface.RESOURCE_BACKGROUND_CHECK);
			Integer bcStateId=vendorResourceDao.getBackgroundStateId(bagCheckStatus);
			logger.info("time taken vendorResourceDao.getBackgroundStateId( query "+(System.currentTimeMillis()-time));
			time=System.currentTimeMillis();
			// set background state Id corresponding to background status.
			tmbcVO.setBackgroundStateId(bcStateId);
			tmbcVO.setBackgroundCheckStatus(bagCheckStatus);
			tmbcVO.setLastName(myBackgroundChkStatusVO.getTechLname());
			tmbcVO.setFirstName(myBackgroundChkStatusVO.getTechFname());
			if (StringUtils.isNotBlank(myBackgroundChkStatusVO
					.getVerificationDate())) {
				tmbcVO.setBackgroundVerificationDate(myBackgroundChkStatusVO
						.getVerificationDate());
			}
			if (StringUtils.isNotBlank(myBackgroundChkStatusVO
					.getRecertificationDate())) {
				tmbcVO.setBackgroundRecertificationDate(myBackgroundChkStatusVO
						.getRecertificationDate());
			}
			tmbcVO.setSsn(personalInfo.getEncryptedSSN());
			// encrypt.
			tmbcVO.setPlusOneKey(CryptoUtil.encryptKeyForSSNAndPlusOne(myBackgroundChkStatusVO
					.getPlusoneKey()));
			// set all the ratings and the screening status
			tmbcVO.setCriminal(myBackgroundChkStatusVO.getCrim());
			tmbcVO.setDriving(myBackgroundChkStatusVO.getDriv());
			tmbcVO.setOverall(myBackgroundChkStatusVO.getOverall());
			tmbcVO.setScreeningStatus(myBackgroundChkStatusVO.getScreeningStatus());
			//set plusone recertification ind
			if(null!=myBackgroundChkStatusVO.getRecertificationInd() && 
					(Constants.RATING_Y).equals(myBackgroundChkStatusVO.getRecertificationInd())){
				tmbcVO.setPlusOneRecertificationInd(true);
			}else if(null!=myBackgroundChkStatusVO.getRecertificationInd() && 
					(Constants.RATING_N).equals(myBackgroundChkStatusVO.getRecertificationInd())){ 
				tmbcVO.setPlusOneRecertificationInd(false);
			}
			
	         // set resource id
			tmbcVO.setResourceId(myBackgroundChkStatusVO.getTechId());
			tmbcVO.setFirmId(previousBackgroundInfo.getFirmId());
			// check whether there is change in old background info and new background info.
			setChangeComment(previousBackgroundInfo, tmbcVO);
			if(tmbcVO.isChange()){
				String backgroundRecertificationDate = null;
				String backgroundVerificationDate   = null;
				backgroundRecertificationDate=tmbcVO.getBackgroundRecertificationDate();
				backgroundVerificationDate=tmbcVO.getBackgroundVerificationDate();
				// donot update recertication date in sl_pro_bknd_chk if overall is 'P' and request type is 'R'
				if((Constants.RATING_P).equals(tmbcVO.getOverall()) && (Constants.RECERTIFY).equals(previousBackgroundInfo.getRequestType())) {	
					// setting null since the null date would not be updated in sl_pro_bknd_chk table.
					tmbcVO.setBackgroundRecertificationDate(null);
					tmbcVO.setBackgroundVerificationDate(null);
				}
			    tmbcVO.setRequestType(previousBackgroundInfo.getRequestType());
			   //Changing the query for performance issue
			   // vendorResourceDao.updateBackgroundCheckStatus(tmbcVO);
			    Integer bgCheckId = vendorResourceDao.getBgCheckId(tmbcVO);
			    logger.info("time taken vendorResourceDao.getBgCheckId( query "+(System.currentTimeMillis()-time));
			    time=System.currentTimeMillis();
			    tmbcVO.setBgCheckId(bgCheckId);
			    vendorResourceDao.updateBgDetails(tmbcVO);
			    
			    logger.info("time taken vendorResourceDao.updateBackgroundCheckStatus(t query "+(System.currentTimeMillis()-time));
				time=System.currentTimeMillis();
			    // update recertification date in history
			    tmbcVO.setBackgroundRecertificationDate(backgroundRecertificationDate);
			    tmbcVO.setBackgroundVerificationDate(backgroundVerificationDate);
				// insert the backgroundcheck history 
			   // tmbcVO.setRequestType(previousBackgroundInfo.getRequestType());
			    vendorResourceDao.insertBackgroundHistory(tmbcVO);
			    
			    logger.info("time taken vendorResourceDao.insertBackgroundHistory(t query "+(System.currentTimeMillis()-time));
				time=System.currentTimeMillis();
			    
			}
			commitWork();
		} else {
			logger.info("This record not match for "
					+ myBackgroundChkStatusVO.getTechId() + "is Miss match.");
			BackgroundChkStatusVO backgroundCheckStatusVO=convertDtotoVo(myBackgroundChkStatusVO);
			vendorResourceDao.insertBackgroundError(backgroundCheckStatusVO);
		}
		
	}catch(Exception e){
		logger.info("error in audit Provider"+e);
	}
			
		}// end else
	
	
	
	private BackgroundChkStatusVO  convertDtotoVo(BackgroundCheckStatusVO myBackgroundCheckStatusVO){
		
		BackgroundChkStatusVO backgroundCheckStatusVO=new BackgroundChkStatusVO();
		/* setting plusone_key,company_id,company_tech_id,client_tech_id,first_name,middle_name,last_name,suffix,
		recertification_ind,overall,criminal,driving,civil,verification_date,reverification_date,screening_status,
		*/
		backgroundCheckStatusVO.setPlusoneKey(myBackgroundCheckStatusVO.getPlusoneKey());
		backgroundCheckStatusVO.setClientCompanyId(myBackgroundCheckStatusVO.getClientCompanyId());
		backgroundCheckStatusVO.setServiceOrganizationId(myBackgroundCheckStatusVO.getServiceOrganizationId());
		backgroundCheckStatusVO.setTechId(myBackgroundCheckStatusVO.getTechId());
		backgroundCheckStatusVO.setTechFname(myBackgroundCheckStatusVO.getTechFname());
		backgroundCheckStatusVO.setTechMiddleName(myBackgroundCheckStatusVO.getTechMiddleName());
		backgroundCheckStatusVO.setTechLname(myBackgroundCheckStatusVO.getTechLname());
		
		//R11_2
		//SL-20421
		if(StringUtils.isNotBlank(backgroundCheckStatusVO.getTechFname())){
			backgroundCheckStatusVO.setTechFname(backgroundCheckStatusVO.getTechFname().trim());
		}
		if(StringUtils.isNotBlank(backgroundCheckStatusVO.getTechMiddleName())){
			backgroundCheckStatusVO.setTechMiddleName(backgroundCheckStatusVO.getTechMiddleName().trim());
		}
		if(StringUtils.isNotBlank(backgroundCheckStatusVO.getTechLname())){
			backgroundCheckStatusVO.setTechLname(backgroundCheckStatusVO.getTechLname().trim());
		}
		backgroundCheckStatusVO.setTechSuffix(myBackgroundCheckStatusVO.getTechSuffix());
		backgroundCheckStatusVO.setRecertificationInd(myBackgroundCheckStatusVO.getRecertificationInd());
		backgroundCheckStatusVO.setOverall(myBackgroundCheckStatusVO.getOverall());
		backgroundCheckStatusVO.setCrim(myBackgroundCheckStatusVO.getCrim());
		backgroundCheckStatusVO.setDriv(myBackgroundCheckStatusVO.getDriv());
		backgroundCheckStatusVO.setVerificationDate(myBackgroundCheckStatusVO.getVerificationDate());
		backgroundCheckStatusVO.setRecertificationDate(myBackgroundCheckStatusVO.getRecertificationDate());
		backgroundCheckStatusVO.setScreeningStatus(myBackgroundCheckStatusVO.getScreeningStatus());
		return backgroundCheckStatusVO;
		
	}

	private void checkBackgroundDates(
			BackgroundCheckStatusVO myBackgroundCheckStatusVO) {
		TMBackgroundCheckVO tmbcVO = new TMBackgroundCheckVO();
		Integer techId = Integer.valueOf(myBackgroundCheckStatusVO.getTechId());
		tmbcVO.setResourceId(techId.toString());
		logger.info("ResourceId :" + techId.toString());
		try {
			/*
			 * TMBackgroundCheckVO DBtmbcVO = vendorResourceDao
			 * .getBackgroundCheckDates(tmbcVO);
			 * logger.info("DBVerification Date :" +
			 * DBtmbcVO.getBackgroundVerificationDate());
			 * logger.info("DBReVerification Date :" +
			 * DBtmbcVO.getBackgroundRecertificationDate());
			 * logger.info("Verification Date :" +
			 * myBackgroundCheckStatusVO.getVerificationDate());
			 * logger.info("ReVerification Date :" +
			 * myBackgroundCheckStatusVO.getRecertificationDate());
			 */
			if ((null != myBackgroundCheckStatusVO.getTechId())
					&& (StringUtils.isNotBlank(myBackgroundCheckStatusVO
							.getVerificationDate()) || StringUtils
							.isNotBlank(myBackgroundCheckStatusVO
									.getRecertificationDate()))) {

				TMBackgroundCheckVO NewDBtmbcVO = new TMBackgroundCheckVO();
				if (StringUtils.isNotBlank(myBackgroundCheckStatusVO
						.getVerificationDate())) {
					NewDBtmbcVO
							.setBackgroundVerificationDate(myBackgroundCheckStatusVO
									.getVerificationDate());

				}
				if (StringUtils.isNotBlank(myBackgroundCheckStatusVO
						.getRecertificationDate())) {
					NewDBtmbcVO
							.setBackgroundRecertificationDate(myBackgroundCheckStatusVO
									.getRecertificationDate());
				}
				NewDBtmbcVO
						.setResourceId(myBackgroundCheckStatusVO.getTechId());

				vendorResourceDao.updateBackgroundCheckDates(NewDBtmbcVO);
			}
		} catch (DataServiceException dse) {
			logger.info("Had an error in Background Check Process for "
					+ myBackgroundCheckStatusVO.getTechId() + ":"
					+ myBackgroundCheckStatusVO.getTechFname() + ":"
					+ myBackgroundCheckStatusVO.getTechLname() + "msg="
					+ dse.getMessage());
		}
	}

	public String getInputFileName() {
		return inputFileName;
	}

	String decodeString(String input) {
		String result = null;
		try {
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(input);
			cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
			cryptographyVO = cryptography.decryptKey(cryptographyVO);
			result = cryptographyVO.getResponse();
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return result;
	}

	String encodeString(String input) {
		String result = null;
		try {
			CryptographyVO cryptographyVO = new CryptographyVO();
			cryptographyVO.setInput(input);
			cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
			cryptographyVO = cryptography.encryptKey(cryptographyVO);
			result = cryptographyVO.getResponse();
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return result;

	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	//old file format
	BackgroundCheckStatusVO parseLine(String line)
			throws BusinessServiceException {
		final int MAX_TOKENS = 11;
		String[] result = line.split("\\|", MAX_TOKENS);

		if (result.length < MAX_TOKENS) {
			logger.error("Invalid input line: " + line);
			throw new BusinessServiceException("Invalid input line: " + line);
		}

		BackgroundCheckStatusVO aBackgroundCheckStatusVO = new BackgroundCheckStatusVO();
		aBackgroundCheckStatusVO.setPlusoneKey(result[0]);
		aBackgroundCheckStatusVO.setClientCompanyId(result[1]);
		aBackgroundCheckStatusVO.setServiceOrganizationId(result[2]);
		aBackgroundCheckStatusVO.setTechId(result[3]);
		aBackgroundCheckStatusVO.setTechFname(result[4]);
		aBackgroundCheckStatusVO.setTechLname(result[5]);
		aBackgroundCheckStatusVO.setOverall(result[6]);
		aBackgroundCheckStatusVO.setCrim(result[7]);
		aBackgroundCheckStatusVO.setDriv(result[8]);
		aBackgroundCheckStatusVO.setVerificationDate(result[9]);
		aBackgroundCheckStatusVO.setRecertificationDate(result[10]);

		return aBackgroundCheckStatusVO;

	}

	
	// SL-19866 parse new PlusOne File format
	BackgroundCheckStatusVO parseLineNew(String line)
			throws BusinessServiceException {
		final int MAX_TOKENS = 16;
		String[] result = line.split("\\|", MAX_TOKENS);

		if (result.length < MAX_TOKENS) {
			logger.error("Invalid input line: " + line);
			throw new BusinessServiceException("Invalid input line: " + line);
		}
		/* Plus One Column and column index are given below:
		 * Technician PlusOne ID 0 Company ID 1 Company Technician ID 2 Client
		 * Technician ID 3 First Name 4 Middle Name 5 Last Name 6 Suffix 7
		 * Recertification Indicator 8 Overall Flag 9 Criminal 10 Driving 11
		 *  Verification Date 12 Recertification Date
		 * 13 Screening Status 14
		 */
		BackgroundCheckStatusVO aBackgroundCheckStatusVO = new BackgroundCheckStatusVO();
		aBackgroundCheckStatusVO.setPlusoneKey(result[0]);
		aBackgroundCheckStatusVO.setClientCompanyId(result[1]);
		aBackgroundCheckStatusVO.setServiceOrganizationId(result[2]);
		aBackgroundCheckStatusVO.setTechId(result[3]);
		aBackgroundCheckStatusVO.setTechFname(result[4]);
		aBackgroundCheckStatusVO.setTechMiddleName(result[5]);
		aBackgroundCheckStatusVO.setTechLname(result[6]);
		aBackgroundCheckStatusVO.setTechSuffix(result[7]);
		aBackgroundCheckStatusVO.setRecertificationInd(result[8]);
		aBackgroundCheckStatusVO.setOverall(result[9]);
		aBackgroundCheckStatusVO.setCrim(result[10]);
		aBackgroundCheckStatusVO.setDriv(result[11]);
		aBackgroundCheckStatusVO.setVerificationDate(result[12]);
		aBackgroundCheckStatusVO.setRecertificationDate(result[13]);
		aBackgroundCheckStatusVO.setScreeningStatus(result[14]);
		
		return aBackgroundCheckStatusVO;
	}
	
	
	
	
	
	
	@Override
	public void setArgs(String[] args) {
		// TODO Auto-generated method stub
		this.inputFileName = args[1];

	}

	public void updateStatus(String teamMemberStatus, String bagCheckStatus,
			BackgroundCheckStatusVO myBackgroundChkStatusVO, boolean doAudit,TMBackgroundCheckVO tmBackgroundCheckVO)
			throws DataServiceException, AuditException {
		logger.info("now auditing dude="
				+ myBackgroundChkStatusVO.getTechId() + "setting to "
				+ bagCheckStatus);

		beginWork();
		// create an audit record for vendor resource
		if (doAudit)
			myAuditBusinessBean.auditVendorResource(
					Integer.parseInt(myBackgroundChkStatusVO.getTechId()),
					teamMemberStatus);

		/**
		 * The code change has been made under JIRA Ticket No.- SL-17594
		 */

		// Splitting the plus One Key
		String ssnNameFrmFile = null;
		String plusOneKey = null;
		plusOneKey = myBackgroundChkStatusVO.getPlusoneKey();
		ssnNameFrmFile = plusOneKey.substring(9);

		String ssnFrmDB = vendorResourceDao.getSsnFrmDB(Integer
				.valueOf(myBackgroundChkStatusVO.getTechId()));

		if (ssnNameFrmFile.equals(ssnFrmDB)) {
			// update is performed based on SSN value of the techId
			String ssn = vendorResourceDao.getTechnicianSsn(Integer
					.valueOf(myBackgroundChkStatusVO.getTechId()));

			// update resource wf_state_id, plus one key, verification and
			// re-verification dates
			TMBackgroundCheckVO tmbcVO = new TMBackgroundCheckVO();
			tmbcVO.setResourceId(myBackgroundChkStatusVO.getTechId());
			tmbcVO.setWfEntity(AuditStatesInterface.RESOURCE_BACKGROUND_CHECK);
			
			Integer bcStateId=vendorResourceDao.getBackgroundStateId(bagCheckStatus);
			// set background state Id
			tmbcVO.setBackgroundStateId(bcStateId);
			tmbcVO.setBackgroundCheckStatus(bagCheckStatus);
			tmbcVO.setLastName(myBackgroundChkStatusVO.getTechLname());
			tmbcVO.setFirstName(myBackgroundChkStatusVO.getTechFname());
			if (StringUtils.isNotBlank(myBackgroundChkStatusVO
					.getVerificationDate())) {
				tmbcVO.setBackgroundVerificationDate(myBackgroundChkStatusVO
						.getVerificationDate());
			}
			if (StringUtils.isNotBlank(myBackgroundChkStatusVO
					.getRecertificationDate())) {
				tmbcVO.setBackgroundRecertificationDate(myBackgroundChkStatusVO
						.getRecertificationDate());
			}
			tmbcVO.setSsn(ssn);
			// need not encrypt.
			tmbcVO.setPlusOneKey(myBackgroundChkStatusVO
					.getPlusoneKey());
			
			// set all the ratings
			tmbcVO.setCriminal(myBackgroundChkStatusVO.getCrim());
			tmbcVO.setDriving(myBackgroundChkStatusVO.getDriv());
			tmbcVO.setDrug(myBackgroundChkStatusVO.getDrug());
			tmbcVO.setOverall(myBackgroundChkStatusVO.getOverall());
			
			//set plusone recertification ind
			if(null!=myBackgroundChkStatusVO.getRecertificationInd() && 
					("Y").equals(myBackgroundChkStatusVO.getRecertificationInd())){
				tmbcVO.setPlusOneRecertificationInd(true);
			}else if(null!=myBackgroundChkStatusVO.getRecertificationInd() && 
					("N").equals(myBackgroundChkStatusVO.getRecertificationInd())){
				tmbcVO.setPlusOneRecertificationInd(false);

			}
			// set approve date,confirmInd if Clear
			if(bcStateId.intValue()==9){
				//tmbcVO.setBackgroundApproveDate(new Date(System.currentTimeMillis()));
				tmbcVO.setBackgroundConfirmInd(true);
			}
	         // set resource id
			tmbcVO.setResourceId(myBackgroundChkStatusVO.getTechId());
			
			vendorResourceDao.updateBackgroundCheckStatus(tmbcVO);
			vendorResourceDao.insertBackgroundHistory(tmbcVO);
			
			
		} else {
			logger.info("This record not match for "
					+ myBackgroundChkStatusVO.getTechId() + "is Miss match.");
		}
		commitWork();
	}

	
	// sl-19677 form the background history comment.
	public void setChangeComment(TMBackgroundCheckVO previousBackgroundInfo,TMBackgroundCheckVO newBackgroudInfo){
		
		String changeComment="";
		boolean change=false;
		boolean displayInd=false;
		
		
		if (null != newBackgroudInfo.getBackgroundCheckStatus()
				&& null != previousBackgroundInfo.getBackgroundCheckStatus()
				&& Constants.CLEAR_STATE.equals(previousBackgroundInfo
						.getBackgroundCheckStatus())) {
			if (Constants.CLEAR_STATE.equals(newBackgroudInfo
					.getBackgroundCheckStatus())) {

				if (compareBackgroundInfo(
						previousBackgroundInfo
								.getBackgroundRecertificationDate(),
						newBackgroudInfo.getBackgroundRecertificationDate())) {
					change = true;
					previousBackgroundInfo
							.setRequestType(Constants.REQUEST_TYPE_R);
				}
			} else {
				change = true;
				previousBackgroundInfo.setRequestType(Constants.REQUEST_TYPE_R);
			}

		}
		// comparing  backgroundStateId
		if (compareBackgroundInfo(previousBackgroundInfo.getBackgroundStateId(),
				newBackgroudInfo.getBackgroundStateId())) {
			
			
			
			
			if (null != previousBackgroundInfo.getBackgroundCheckStatus()){
				newBackgroudInfo.setBackgroundStatusUpdateDate(new Date(System.currentTimeMillis()));
				 if ("In Process".equals(newBackgroudInfo.getBackgroundCheckStatus()
						)) {
					 
					 
					 
					changeComment = Constants.SCREENING_IN_PROCESS;	
					
				}else{
					changeComment = Constants.BACKGROUND_CHECK_STATUS_UPDATE+previousBackgroundInfo.getBackgroundCheckStatus()+" to " + newBackgroudInfo.getBackgroundCheckStatus();
			
				}
				change=true;
				displayInd=true;
		  }
		}
		else if(compareBackgroundInfo(previousBackgroundInfo.getOverall(),newBackgroudInfo.getOverall()) && ("P").equals(newBackgroudInfo.getOverall())){
			
			if((Constants.RECERTIFY).equals(previousBackgroundInfo.getRequestType())){
				changeComment="Recertification is in-progress"; 
			}else{
			changeComment =  Constants.SCREENING_IN_PROCESS;
			}
			change=true;
			displayInd=true;
		}
		

		// comparing overall
				if(compareBackgroundInfo(previousBackgroundInfo.getOverall(),newBackgroudInfo.getOverall())){
					change=true;
						if(("E").equals(newBackgroudInfo.getOverall())){
						displayInd=true;
						if(changeComment.length()>0){
							changeComment=changeComment+", "+Constants.BACKGROUND_CHECK_EXPIRED+newBackgroudInfo.getBackgroundRecertificationDate();
						}else{
							changeComment=Constants.BACKGROUND_CHECK_EXPIRED+newBackgroudInfo.getBackgroundRecertificationDate();
						}
						}
								
				   }
		
		
	    // comparing  backgroundVerificationDate 
		if(compareBackgroundInfo(previousBackgroundInfo.getBackgroundVerificationDate(),newBackgroudInfo.getBackgroundVerificationDate())){
			
			change=true;
			if(!displayInd){
			if(changeComment.length()>0){
				
				if(null==previousBackgroundInfo.getBackgroundVerificationDate()){
					changeComment=changeComment+", "+"Verification Date updated "+
							" to "+newBackgroudInfo.getBackgroundVerificationDate();
				}else{
					changeComment=changeComment+", "+Constants.VERIFICATION_DATE_UPDATE+previousBackgroundInfo.getBackgroundVerificationDate()+
							" to "+newBackgroudInfo.getBackgroundVerificationDate();
				}
				
			}else{
				if(null==previousBackgroundInfo.getBackgroundVerificationDate()){
					changeComment="Verification Date updated "+
							" to "+newBackgroudInfo.getBackgroundVerificationDate();
				}else{
					changeComment=Constants.VERIFICATION_DATE_UPDATE+previousBackgroundInfo.getBackgroundVerificationDate()+
							" to "+newBackgroudInfo.getBackgroundVerificationDate();
				}
			}
		}
			
		   }
		
		// comparing  backgroundReverificationDate 
		if(compareBackgroundInfo(previousBackgroundInfo.getBackgroundRecertificationDate(),newBackgroudInfo.getBackgroundRecertificationDate())){
					change=true;
					if(!displayInd){
					if(changeComment.length()>0){
						
						if(null==previousBackgroundInfo.getBackgroundRecertificationDate()){
							changeComment=changeComment+", "+"Recertification Date updated"+
									" to "+newBackgroudInfo.getBackgroundRecertificationDate();	
						}else{
							changeComment=changeComment+", "+Constants.REVERIFICATION_DATE_UPDATE+previousBackgroundInfo.getBackgroundRecertificationDate()+
									" to "+newBackgroudInfo.getBackgroundRecertificationDate();	
						}		
					}else{
						if(null==previousBackgroundInfo.getBackgroundRecertificationDate()){
							changeComment="Recertification Date updated"+
									" to "+newBackgroudInfo.getBackgroundRecertificationDate();	
						}else{
							changeComment=Constants.REVERIFICATION_DATE_UPDATE+previousBackgroundInfo.getBackgroundRecertificationDate()+
									" to "+newBackgroudInfo.getBackgroundRecertificationDate();	
						}
					}
		}
				   }

		  
		
		// comparing criminal,driving or recertification indicator ,screening status 
		if( compareBackgroundInfo(previousBackgroundInfo.getCriminal(),newBackgroudInfo.getCriminal())
			|| 	compareBackgroundInfo(previousBackgroundInfo.getDriving(),newBackgroudInfo.getDriving())
			|| compareBackgroundInfo(previousBackgroundInfo.getPlusOneRecertificationInd(),newBackgroudInfo.getPlusOneRecertificationInd())
			|| 	compareBackgroundInfo(previousBackgroundInfo.getScreeningStatus(),newBackgroudInfo.getScreeningStatus())
				){
			change=true;
			if(!displayInd){
			if(changeComment.length()>0){
				changeComment=changeComment+", "+Constants.BACKGROUND_CHECK_RESULTS_UPDATED;
			}
			else{
				changeComment=Constants.BACKGROUND_CHECK_RESULTS_UPDATED;
			}
		   }
		   }
		
		// recertification status
		String recertificationStatus="";
		if((Constants.RATING_P).equals(newBackgroudInfo.getOverall()) && (Constants.RECERTIFY).equals(previousBackgroundInfo.getRequestType())) {
			recertificationStatus=Constants.IN_PROCESS;	
			
			//logic to set  recert_before_expiry
			if(null!=previousBackgroundInfo.getBackgroundRecertificationDate() && !"".equals(previousBackgroundInfo.getBackgroundRecertificationDate())
				&& !("Y").equals(previousBackgroundInfo.getRecertBeforeExpiry())){
			java.util.Date recertificationDate=DateUtils.getDateFromString(previousBackgroundInfo.getBackgroundRecertificationDate(), "yyyy-MM-dd");
			Calendar c = Calendar.getInstance(); 
			c.setTime(recertificationDate); 
			c.add(Calendar.DATE, 1);
			recertificationDate = c.getTime();
			java.util.Date currentDate = new java.util.Date();
			currentDate.setHours(ZER0);
			currentDate.setMinutes(ZER0);
			currentDate.setSeconds(ZER0);	
			double days = getDaysBetweenDates(currentDate,recertificationDate).floatValue();		
		     if(days>=0.00){
			newBackgroudInfo.setRecertBeforeExpiry("Y");
		   }
			
			
		}
		  if(("Y").equals(previousBackgroundInfo.getRecertBeforeExpiry())){
				newBackgroudInfo.setRecertBeforeExpiry("Y");
		  }
			
			
		}
		else if(null!=newBackgroudInfo.getBackgroundRecertificationDate() && !"".equals(newBackgroudInfo.getBackgroundRecertificationDate())
				){
		 if(newBackgroudInfo.getBackgroundStateId().intValue()==9){
			 
			java.util.Date currentDate = new java.util.Date();
			currentDate.setHours(ZER0);
			currentDate.setMinutes(ZER0);
			currentDate.setSeconds(ZER0);	
			double days = getDaysBetweenDates(currentDate,DateUtils.getDateFromString(newBackgroudInfo.getBackgroundRecertificationDate(), "yyyy-MM-dd")).floatValue();		
			int day=(int) days;
		  if(day<=30 && day>0){
			if(currentDate.getDate()==DateUtils.getDateFromString(newBackgroudInfo.getBackgroundRecertificationDate(), "yyyy-MM-dd").getDate()){
				recertificationStatus=Constants.DUE_TODAY;
			}else{
			recertificationStatus="Due in "+day+" days";
			}
		  }else if(day==0){
			recertificationStatus=Constants.DUE_TODAY;
		  }else if(day<0){
			recertificationStatus=Constants.PAST_DUE_STRING;
		   }
				}
		
	     }
		 if(!"".equals(recertificationStatus)){
				newBackgroudInfo.setRecertificationStatus(recertificationStatus);
		 }
		 
		 if(null!=newBackgroudInfo.getBackgroundStateId()&& (newBackgroudInfo.getBackgroundStateId().intValue()==9
				 || newBackgroudInfo.getBackgroundStateId().intValue()==10)
				 && !(Constants.RATING_P).equals(newBackgroudInfo.getOverall()) && (Constants.RECERTIFY).equals(previousBackgroundInfo.getRequestType())  ){
			 
			 newBackgroudInfo.setRecertBeforeExpiry(null); 
		 }
		 
		 if(compareBackgroundInfo(previousBackgroundInfo.getRecertBeforeExpiry(),newBackgroudInfo.getRecertBeforeExpiry())){
			 change = true; 
			 newBackgroudInfo.setUpdateRecertBeforeExpiry(true);
		 }
		 
				newBackgroudInfo.setChangedComment(changeComment);
				newBackgroudInfo.setChange(change);
				if(displayInd){
					newBackgroudInfo.setDisplayInd(Constants.STRING_Y);
				}else{
					newBackgroudInfo.setDisplayInd(Constants.STRING_N); 
				}
				
	}
  public  Double getDaysBetweenDates(java.util.Date startDate, java.util.Date endDate) {
		
		
		return ((endDate.getTime() - startDate.getTime() + ONE_HOUR) / (ONE_HOUR * 24));
	}
	
	

	

	/**
	 * Returns a Date object after parsing the specified string with the required format
	 * 
	 * @param dateStr
	 * @param formatStr
	 * @return
	 */
	public  Date getDateFromString(String dateStr, String formatStr) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		try {
			date = (Date) sdf.parse(dateStr);
		} catch (Exception e) {
			// logger.info("Caught Exception and ignoring",e);
		}
		return date;
	}
	
	
	
	private static boolean compareBackgroundInfo(Object previousBackgroudInfo, Object newBackgroudInfo) {
	    return !((previousBackgroudInfo == newBackgroudInfo) ||
	    		(previousBackgroudInfo != null && previousBackgroudInfo.equals(newBackgroudInfo)));
	}
	
	
	public void beginWork() {
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		status = tm.getTransaction(def);
	}

	public void commitWork() {
		tm.commit(status);
	}

}
