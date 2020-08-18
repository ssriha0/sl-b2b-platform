package com.newco.batch.smsdatasynch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.commons.lang.StringUtils;



import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.smsdatasynch.service.ISMSDataSynchService;
import com.newco.marketplace.smsdatasynch.vo.SMSDataSynchVO;
import com.newco.marketplace.util.PropertiesUtils;




/**
 * @author Infosys
 * SL-18979
 * This class reads the file from Vibes
 * to synch the opt-in opt-out data of the providers in ServiceLive
 */
public class SMSDataSynchProcess {

	private static final Logger logger = Logger.getLogger(SMSDataSynchProcess.class);

	private ISMSDataSynchService smsDataSynchProcessService;

	//Input folder
	private static final String inputFileDirectory = PropertiesUtils
			.getPropertyValue(Constants.AppPropConstants.VIBES_INPUT_FILE_DIRECTORY);

	//Archive folder
	private static final String archiveFileDirectory = PropertiesUtils
			.getPropertyValue(Constants.AppPropConstants.VIBES_ARCHIVE_FILE_DIRECTORY);

	//Error folder
	private static final String errorFileDirectory = PropertiesUtils
			.getPropertyValue(Constants.AppPropConstants.VIBES_ERROR_FILE_DIRECTORY);
	//TODO:To be removed
	/*private static final String inputFileDirectory = "D://ServiceLive/R16_1/VibesSMSJira/fileSys/indata";

	private static final String archiveFileDirectory = "D://ServiceLive/R16_1/VibesSMSJira/fileSys/archive/";

	private static final String errorFileDirectory = "D://ServiceLive/R16_1/VibesSMSJira/fileSys/error/";*/


	/**
	 * SL-18979
	 * This method reads the file from the directory and 
	 * updates the opt-in status of the providers in the file
	 * @throws BusinessServiceException
	 */
	public void processFile() throws BusinessServiceException{

		// Getting the input file directory and checking whether the folder exists or not 
		File inputDir = new File(inputFileDirectory);
		if (!inputDir.exists()) {
			logger.error("The source directory [" + inputFileDirectory + "] for data synch file from Vibes does not exists.");
			return;
		}

		// Reading the files in the input directory
		File[] files = inputDir.listFiles();
		if (files == null || (null != files && 0 == files.length)) {
			logger.error("No data synch file from Vibes found on " + new Date());
			return;
		}
		try{

			//Fetching the no. of records to be processed in one go.
			//int processingLimit = Constants.VIBES_RECORDS_PROCESSING_LIMIT;

			//If there are more than one file, 
			//then the oldest file should be processed first
			if(files.length > 1){

				//sort the files based on modified date
				Arrays.sort(files, new Comparator<File>() {
					public int compare(File f1, File f2) {
						return Long.valueOf(f1.lastModified()).compareTo(f2.lastModified());
					}
				});
			}
			Map<String, SMSDataSynchVO> recordsFromFile = new HashMap<String, SMSDataSynchVO>();

			for (File inputFile : files) {					
				if(null != inputFile && inputFile.isFile()){

					String fileName = inputFile.getName();

					// Check the extension of files in the directory
					if (null != fileName && (fileName.trim().endsWith(Constants.EXTN_TXT) || fileName.trim().endsWith(Constants.EXTN_TXT_CAPS))) {

						boolean error = false;

						//Read the file
						BufferedReader fileReader = new BufferedReader(new FileReader(inputFile));

						if(null != fileReader){

							String line = "";
							int recordsCount = 0;
							int personIdCol = Constants.PERSON_ID_COL;
							//int optInStatusCol = 5;
							int subEventCol =Constants.SUB_EVENT_COL;
							int mdnCol = Constants.MDN_COL;
							int subsListIdCol = Constants.SUBS_LIST_ID_COL;
							int optInDateCol = Constants.OPTINDATE_COL;
							int optOutDateCol = Constants.OPTOUTDATE_COL;
							String[] record = null;
							SMSDataSynchVO dataSynchVO = new SMSDataSynchVO();
							try{
								// Fetching the subscription_list_id from DB
								String subscriptionIdFrmDB = smsDataSynchProcessService.fetchSubscriptionId(Constants.VIBESSUBSCRIPTIONID);
								
								while (null != (line = fileReader.readLine())) {

									try{
										
										record = line.split(Constants.TAB_DELIMITER);

										//No need to check the header details for calculating the position
										/*if(0 == recordsCount){

										if(null != record){
											for(int i=0; i<record.length; i++){
												if(null != record[i]){
													//Header name of the person id column will be 'person_id'
													//If there is any change in the header name, 
													//we are assuming that the first column will have the person id
													if(Constants.PERSON_ID.equalsIgnoreCase(record[i].trim())){
														personIdCol = i;
													}else if(Constants.MDN.equalsIgnoreCase(record[i].trim())){
														subEventCol = i;
													}else if(Constants.OPT_IN_DATE.equalsIgnoreCase(record[i].trim())){
														optInDateCol = i;
													}else if(Constants.OPT_OUT_DATE.equalsIgnoreCase(record[i].trim())){
														optOutDateCol = i;
													}

													//Header name of the subscription event column will be 'subscription_event'
													//If there is any change in the header name, 
													//we are assuming that the 9th column will have the subscription event
													else if(Constants.SUBSCRIPTION_EVENT.equalsIgnoreCase(record[i].trim())){
														subEventCol = i;
													}
												}
											}
										}
									}
										 */
										//Process the records excluding the header
										if(0 == recordsCount){
											recordsCount ++;
										}
										else if(recordsCount > 0 && null != record && record.length>0){
											
											String recSubsListId=record[subsListIdCol];

											//Validating if the record has the same subscription_list_id as in DB
											if(StringUtils.isNotBlank(subscriptionIdFrmDB) && StringUtils.isNotBlank(recSubsListId) 
													&& (subscriptionIdFrmDB.equalsIgnoreCase(recSubsListId))){
											//Get the person Id and subscription event of the participants
											dataSynchVO = mapRecordDetails(record,personIdCol,subEventCol,mdnCol,optInDateCol,optOutDateCol);
											if(null != dataSynchVO){
												//Populate the map with personId and opt-in status
												if(recordsFromFile.containsKey(record[personIdCol])){
													dataSynchVO = formTheLatestEntry(dataSynchVO,recordsFromFile.get(record[personIdCol]));
													recordsFromFile.put(record[personIdCol], dataSynchVO);
												}else{
													recordsFromFile.put(record[personIdCol], dataSynchVO);

												}
												recordsCount++;

											  }
											}
										}

									}catch(Exception e){
										logger.error("Vibes Data Sync Error occured during processing the record :"+ line.toString() + " .Exception : "+e.getMessage());
									}

								}
								
								if(recordsCount == 0 || recordsCount == 1){
									fileReader.close();
									//If the file had no records read or only header, move the file to error directory
									moveFileToDirectory(inputFile, fileName, errorFileDirectory);
									error = true;
								}	


							}catch(IOException ioe){
								ioe.printStackTrace();
								//if error, close the reader
								fileReader.close();
								//If the file cannot be read, move it to error folder
								moveFileToDirectory(inputFile, fileName, errorFileDirectory);
								error = true;
							}catch(Exception ioe){
								ioe.printStackTrace();
								//if error, close the reader
								fileReader.close();
								//If the file cannot be read, move it to error folder
								moveFileToDirectory(inputFile, fileName, errorFileDirectory);
								error = true;
							}
						}

						if(!error){

							//Once the processing is complete, 
							//close the reader
							fileReader.close();
							//archive the processed file
							moveFileToDirectory(inputFile, fileName, archiveFileDirectory);
						}
					}
					else{
						//If the file is not a txt file, it cannot be processed
						//Move it to error folder
						moveFileToDirectory(inputFile, fileName, errorFileDirectory);
					}
				}
			}
			if(null != recordsFromFile && !recordsFromFile.isEmpty()){
				processRecords(recordsFromFile);
			}


		}
		catch(Exception e){
			throw new BusinessServiceException("Exception in SMSDataSynchProcess.processFile() due to "+ e, e);
		}

	}


	/**
	 * @param latterInFileVO
	 * @param formerInFileVO
	 * @return
	 * @throws Exception
	 * if the date for any of the entry is invalid, the latest record in the file will be taken and updated
	 */
	private SMSDataSynchVO formTheLatestEntry(SMSDataSynchVO latterInFileVO,
			SMSDataSynchVO formerInFileVO) throws Exception {

		SMSDataSynchVO dataSynchVO = null;
		try{
			dataSynchVO = formerInFileVO;
			Date formerDate = null;
			Date latterDate = null;

			if(formerInFileVO.isOptInInd()){
				formerDate = formerInFileVO.getOptInDate(); 

			}else{
				formerDate = formerInFileVO.getOptOutDate(); 

			}
			if(latterInFileVO.isOptInInd()){
				latterDate = latterInFileVO.getOptInDate(); 

			}else{
				latterDate = latterInFileVO.getOptOutDate(); 

			}
			if(null!= formerDate && null!= latterDate){
				if(formerDate.after(latterDate)){
					dataSynchVO = formerInFileVO;
				}else if(latterDate.after(formerDate)){
					dataSynchVO = latterInFileVO;
				}
			}else{
				dataSynchVO = latterInFileVO;
			}
		}catch(Exception e){
			throw e;
		}
		return dataSynchVO;
	}


	/**
	 * @param record
	 * @param personIdCol
	 * @param subEventCol
	 * @param mdnCol
	 * @param optInDateCol
	 * @param optOutDateCol
	 * @return
	 * @throws BusinessServiceException
	 */
	private SMSDataSynchVO mapRecordDetails(String[] record, int personIdCol, int subEventCol, int mdnCol, int optInDateCol, int optOutDateCol) throws BusinessServiceException{
		SMSDataSynchVO dataSynchVO = null;
		Date optDate =null;
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		formatter.setLenient(false);
		try{
			if(StringUtils.isNotBlank(record[personIdCol])){
				dataSynchVO = new SMSDataSynchVO();
				dataSynchVO.setPersonId(record[personIdCol]);
				dataSynchVO .setMdn(record[mdnCol]);
				dataSynchVO.setSubscriptionEvent(record[subEventCol].trim());
				String status = null;

				if(Constants.OPT_IN.equalsIgnoreCase(record[subEventCol].trim())){
					status = Constants.TRUE;
				}
				else if(Constants.OPT_OUT.equalsIgnoreCase(record[subEventCol].trim())){
					status = Constants.FALSE_CAPS;
				}else{
					throw new BusinessServiceException("Invalid subscription event for the personId: "+record[personIdCol]);
				}
				// In case of invalid date, the value will be set as null
				if(StringUtils.isNotBlank(record[optOutDateCol])){
					try{
						optDate = formatter.parse(record[optOutDateCol]);
						dataSynchVO.setOptOutDate(optDate);
					}catch(ParseException e){
						logger.error("Invalid optOutDate for personId"+ record[personIdCol]);
						
					}
				}
				// In case of invalid date, the value will be set as null
				if(StringUtils.isNotBlank(record[optInDateCol])){
					try{
						optDate = formatter.parse(record[optInDateCol]);
						dataSynchVO.setOptInDate(optDate);
					}catch(ParseException e){
						logger.error("Invalid optInDate for personId"+ record[personIdCol]);
					}

				}
				if(Constants.TRUE.equalsIgnoreCase(status)){
					dataSynchVO.setOptInInd(true);
				}
				else if(Constants.FALSE_CAPS.equalsIgnoreCase(status)){
					dataSynchVO.setOptInInd(false);
				}
			}
		}catch(Exception e){
			throw new BusinessServiceException(e);
			
		}

		return dataSynchVO;
	}


	/**
	 * SL-18979
	 * Based on the status of the record in DB, update the status and other details retrieved from Data Sync file in tables
	 * updates the status
	 * @param recordsFromFile
	 * @return 
	 * @throws BusinessServiceException
	 */
	private void processRecords(Map<String, SMSDataSynchVO> recordsFromFile) throws BusinessServiceException{

		try{

			List<SMSDataSynchVO> recordsToBeUpdated = new ArrayList<SMSDataSynchVO>();
			List<String> inputPersonIds = new ArrayList<String>(recordsFromFile.keySet());
			List<SMSDataSynchVO> recordsFromDB = smsDataSynchProcessService.fetchSubscriptionDetails(inputPersonIds);
			SMSDataSynchVO recordInFile = null;
			SMSDataSynchVO recordToBeInserted = null;
			//If opt_ind =0, update alt contact to primary email in contact table
			List<String> personIdsUpdAltContact = new ArrayList<String>();
			//For the person ids in the file, get the opt-in status in SL
			//Check if there is any change in opt-in status
			if(null != recordsFromDB && !recordsFromDB.isEmpty()){

				for(String personIdFrmFile : recordsFromFile.keySet()){
					for(SMSDataSynchVO recordFrmDB : recordsFromDB){
						recordToBeInserted = null;
						if(null != personIdFrmFile && null != recordFrmDB && null != recordFrmDB.getPersonId()								
								&& personIdFrmFile.equals(recordFrmDB.getPersonId().trim())){
							recordInFile = recordsFromFile.get(personIdFrmFile);
							recordToBeInserted = copyFileRecord(recordInFile);
							recordToBeInserted.setSubscriptionId(recordFrmDB.getSubscriptionId());
							if(Constants.PENDING.equals(recordFrmDB.getStatus()) && recordInFile.isOptInInd()){
								recordToBeInserted.setStatus(Constants.ACTIVE);
							}else if (!recordInFile.isOptInInd()){
								recordToBeInserted.setStatus(Constants.DELETED);
								personIdsUpdAltContact.add(recordFrmDB.getPersonId());
							}
							else{
								recordToBeInserted.setStatus(recordFrmDB.getStatus());
							}
							recordsToBeUpdated.add(recordToBeInserted);
						}
					}

				}
				//Updating alt contact method in contact table if opt_in_ind=0
				if (null!=personIdsUpdAltContact && !(personIdsUpdAltContact.isEmpty())){
					List<Integer> resourceIdList = smsDataSynchProcessService.fetchResourceIds(personIdsUpdAltContact);
					if (null!=resourceIdList && !(resourceIdList.isEmpty())){
						smsDataSynchProcessService.updateAltContact(resourceIdList);
					}
				}
				//Update the tables
				smsDataSynchProcessService.updateSubscriptionStatus(recordsToBeUpdated);
			}

		}catch(Exception e){
			throw new BusinessServiceException("Exception in SMSDataSynchProcess.processRecords() due to "+ e, e);
		}

	}

	/**
	 * @param recordInFile
	 * @return
	 */
	private SMSDataSynchVO copyFileRecord(SMSDataSynchVO recordInFile) {
		SMSDataSynchVO dataSynchVO =null;
		if(null != recordInFile){
			dataSynchVO = new SMSDataSynchVO();
			dataSynchVO.setPersonId(recordInFile.getPersonId());
			dataSynchVO .setMdn(recordInFile.getMdn());
			dataSynchVO.setSubscriptionEvent(recordInFile.getSubscriptionEvent());
			dataSynchVO.setOptInInd(recordInFile.isOptInInd());
			if(null != recordInFile.getOptOutDate()){
				dataSynchVO.setOptOutDate(recordInFile.getOptOutDate()); 
			}
			if(null != recordInFile.getOptInDate()){
				dataSynchVO.setOptInDate(recordInFile.getOptInDate());
			}
		}

		return dataSynchVO;
	}


	/**
	 * SL-18979
	 * This method is to move the file to archive/error folder
	 * incase of failure
	 * @param inputFile
	 * @param fileName
	 * @return 
	 * @throws BusinessServiceException
	 */
	private void moveFileToDirectory(File inputFile, String fileName, String fileDirectory) throws BusinessServiceException {

		// Getting the file directory and checking whether the folder exists or not 
		// If not exists then creating a new folder
		File directory = new File(fileDirectory);	
		if (!directory.exists()) {
			logger.info("The directory [" + fileDirectory + "] for data synch file from Vibes does not exists. So creating the folder");
			directory.mkdir();
		}

		// Moving the file. If the file already exists then overwriting the existing one
		File file = new File(directory, fileName);
		if (file.exists()) {
			file.delete();
		}

		boolean success = inputFile.renameTo(file);
		if (!success) {
			throw new BusinessServiceException("SMSDataSynchProcess failed to move file: " + fileName + " to directory: " + fileDirectory);
		}
	}


	public ISMSDataSynchService getSmsDataSynchProcessService() {
		return smsDataSynchProcessService;
	}

	public void setSmsDataSynchProcessService(
			ISMSDataSynchService smsDataSynchProcessService) {
		this.smsDataSynchProcessService = smsDataSynchProcessService;
	}

}