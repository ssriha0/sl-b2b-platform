package com.newco.batch.westsurvey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.marketplace.business.iBusiness.westsurvey.IWestSurveyBO;
import com.newco.marketplace.dto.vo.survey.WestImportSummaryVO;
import com.newco.marketplace.dto.vo.survey.WestSurveyErrorVO;
import com.newco.marketplace.dto.vo.survey.WestSurveyVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.util.scheduler.ABaseScheduler;

/**
 * This is the main job class for West Survey Response Scheduler Quartz Job
 */
public class WestSurveyResponseScheduler extends ABaseScheduler implements Job {

	private final Logger logger = Logger.getLogger(WestSurveyResponseScheduler.class);

	private IWestSurveyBO westSurveyBO;

	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext jobExecContext) throws JobExecutionException {
		ApplicationContext appContext = getApplicationContext(jobExecContext);
		processSurveyFiles(appContext);
	}

	//This method is used by JUnit as well
	void processSurveyFiles(ApplicationContext appContext) throws JobExecutionException {

		long startTime = System.currentTimeMillis();

		// Do we have any files to be processed?
		File[] surveyFiles = this.getWestFileList();
		if (surveyFiles.length == 0) {
			logger.info("No West Survey files found to be processed; exiting job");
			return;
		}

		// Get dependencies
		westSurveyBO = (IWestSurveyBO) appContext.getBean("westSurveyBO");
		WestSurveyErrorHandler westSurveyErrorHandler = (WestSurveyErrorHandler) appContext.getBean("westSurveyErrorHandler");

		List<File> parseErrorFilesList = new ArrayList<File>();
		Map<String, List<WestSurveyErrorVO>> erroredFileRecords = new HashMap<String, List<WestSurveyErrorVO>>();

		String errorDirectory = bootstrapConfig.getProperty(SurveyConstants.WEST_SURVEY_ERROR_FILE_PATH);
		String archiveDirectory = bootstrapConfig.getProperty(SurveyConstants.WEST_SURVEY_ARCHIVE_FILE_PATH);

		//ExcelParser<WestSurveyVO> excelParser = new ExcelParser<WestSurveyVO>(WestSurveyVO.class);

		// CSAT delimiter configured in application_properties
		String delimiter = null;
		try {
			delimiter = westSurveyBO.getCSATDelimiter();
		} catch (BusinessServiceException e1) {
			e1.printStackTrace();
			logger.error("Unable to fetch delimeter from DB, defaulting to delimeter: "+SurveyConstants.WEST_SURVEY_DELIMITER);
		}

		if(StringUtils.isBlank(delimiter)){
			delimiter = SurveyConstants.WEST_SURVEY_DELIMITER;
		}
		delimiter =SurveyConstants.ESCAPE_CHARACTER+delimiter;
		// Iterate, parse and process files
		for (File  file : surveyFiles) {

			logger.info("West Survey File Name = ["+file.getAbsolutePath()+"]");
			// Parse the .csv or .txt file
			List<WestSurveyVO> westSurveyList = null;
			String westFileName = file.getName();
			try {
				//westSurveyList = excelParser.parse(file.getPath(), SurveyConstants.DATA_START_EXCELROW_BATCH, ExcelParserProcessingType.POSITION_BASED);
				//R15_4 SL-20988, read from .txt or .csv file instead of .xls
				westSurveyList = parseTextOrCsvCSATFile(file.getPath(),delimiter);
				logger.info("West Survey Parsing successful. Total ["+westSurveyList.size()+"] records found to be processed.");
			} catch (Exception ex) {
				//Parse Exception caught & error file added to error Map
				parseErrorFilesList.add(file);
				erroredFileRecords.put(westFileName, null);

				// Move file to errors directory
				this.moveFileToDirectory(file, errorDirectory);
				logger.info("West Survey Parsing failed, file moved to errors directory, to be reprocessed later manually by Prod Support!");
				ex.printStackTrace();
				continue;
			}

			// Process records in the file
			WestImportSummaryVO westImportSummaryVO = processWestSurveyRecords(westFileName, westSurveyList);
			List<WestSurveyErrorVO> erroredSurveyObjects = westImportSummaryVO.getErroredSurveyObjects();
			if (!erroredSurveyObjects.isEmpty()) {
				erroredFileRecords.put(westFileName, erroredSurveyObjects);
			}

			// Move file to archive directory
			this.moveFileToDirectory(file, archiveDirectory);
			logger.info("File " + westFileName + " moved to archive directory.");

			// Save import summary to database
			try {
				westSurveyBO.saveWestImportResults(westImportSummaryVO);
			} catch (BusinessServiceException e) {
				throw new JobExecutionException("Job Failed while updating summary of rating data imported from west file: " + westFileName);
			}
		}

		//Calls the method to notify in case of any exception
		if (!erroredFileRecords.isEmpty()) {
			String emailHostName = bootstrapConfig.getProperty("WEST_MAIL_HOST");
			String to = bootstrapConfig.getProperty("WEST_MAIL_TO");
			String from = bootstrapConfig.getProperty("WEST_MAIL_FROM");
			String errFilePath = bootstrapConfig.getProperty("WEST_SURVEY_ERROR_FILE_PATH");
			String archFilePath = bootstrapConfig.getProperty("WEST_SURVEY_ARCHIVE_FILE_PATH");
			westSurveyErrorHandler.notifyError(erroredFileRecords, emailHostName, to, from, errFilePath, archFilePath);
		}

		long endTime = System.currentTimeMillis();
		final int MILISECONDS_PER_SECOND = 1000;
		logger.info("Time taken for WestSurveyResponseScheduler: " + (endTime - startTime) / MILISECONDS_PER_SECOND + " seconds");
	}

	/**
	 * R15_4 SL-20988, read from .txt or .csv file instead of .xls
	 * Method to fetch the list of West txt or csv files
	 */
	@SuppressWarnings("unchecked")
	private File[] getWestFileList() throws JobExecutionException {

		String dirName = bootstrapConfig.getProperty(SurveyConstants.WEST_SURVEY_DIR_PATH);
		File dir = new File(dirName);
		if (!dir.exists()) {
			throw new JobExecutionException("The source directory [" + dirName + "] for West Survey  does not exists.");
		}

		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir2, String name) {
				//return (name.endsWith(".csv") || name.endsWith(".txt"));
				//should accept the file even if the extension is of a different case.
				String fileExtension = getFileExtenstion(name);
				return (fileExtension.equalsIgnoreCase(SurveyConstants.FILE_EXTENSION_CSV) || fileExtension.equalsIgnoreCase(SurveyConstants.FILE_EXTENSION_TXT));
			}
		});
		Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
		return files;
	}

	/**
	 * get the file extension from the file name.
	 * @param fileName
	 * @return fileExtension
	 */
	private String getFileExtenstion(String fileName){
		String fileExtension ="";
		if(StringUtils.isNotBlank(fileName)){
			fileExtension = fileName.substring(fileName.lastIndexOf(SurveyConstants.DOT) + 1);
		}
		return fileExtension;     
	}
	/**
	 * Iterates and process given records; stores import summary in an object and returns the object
	 * @param westFileName
	 * @param westSurveyList
	 * @return
	 */
	private WestImportSummaryVO processWestSurveyRecords(String westFileName, List<WestSurveyVO> westSurveyList) {
		int successCount = 0;
		int skipCount = 0;
		int failureCount = 0;
		Integer buyerId = Integer.valueOf(bootstrapConfig.getProperty(SurveyConstants.BUYER_ID));
		List<WestSurveyErrorVO> erroredSurveyObjects = new ArrayList<WestSurveyErrorVO>();
		List<String> soIds = new ArrayList<String>();
		for (WestSurveyVO westSurveyVO : westSurveyList) {
			logger.info("Started processing SO:"+westSurveyVO.getSoId());
			try {
				//Saves the West Survey Response
				String soId = westSurveyBO.saveSurveyResponse(westSurveyVO, buyerId);
				if (soId != null) {
					soIds.add(soId);
					++successCount;
				} else {
					++skipCount;
				}
			} catch (Exception ex) {
				// Exception caught & stored in errorSurveyObjects
				logger.error("West Survey Record Processing Failed for so id  ["+westSurveyVO.getSoId()+"]", ex);
				//ex.printStackTrace();
				WestSurveyErrorVO westSurveyErrVO = new WestSurveyErrorVO();
				westSurveyErrVO.setErrorMessage(ex.getMessage());
				westSurveyErrVO.setWestSurveyVO(westSurveyVO);
				westSurveyErrVO.setException(ex);
				erroredSurveyObjects.add(westSurveyErrVO);
				++failureCount;
			}
			logger.info("FInished processing SO:"+westSurveyVO.getSoId());
		}
		logger.info("West Survey records processed Success Count = ["+successCount+"], Skip Count = ["+skipCount+"], Failure Count = ["+failureCount+"]");
		WestImportSummaryVO westImportSummaryVO = new WestImportSummaryVO(westFileName, westSurveyList.size(), successCount, skipCount, failureCount, erroredSurveyObjects, soIds);
		return westImportSummaryVO;
	}

	/**
	 * Method to move files to a new directory
	 * @param filePath
	 * @param directoryPath
	 */
	private void moveFileToDirectory(File sourceFile, String directoryPath) throws JobExecutionException {

		File dir = new File(directoryPath);	    
		String fileName = sourceFile.getName();

		File archivedFile = new File(dir, fileName);
		if (archivedFile.exists()) {
			archivedFile.delete();
		}

		boolean success = sourceFile.renameTo(archivedFile);
		if (!success) {
			throw new JobExecutionException("Failed to move file: " + fileName + " to directory: " + dir);
		}
	}


	/**
	 * R15_4 SL-20988, read from .txt or .csv file instead of .xls
	 * This is a new parser for reading the values from a .txt or .csv file format.
	 * @param filepath
	 * @return List<WestSurveyVO>
	 * @throws IOException
	 * @throws Exception
	 */
	private List<WestSurveyVO> parseTextOrCsvCSATFile(String filepath, String delimiter) throws IOException, Exception{
		List<WestSurveyVO> westSurveyVOList = new ArrayList<WestSurveyVO>();
		// FileReader reads text files in the default encoding.
		FileReader fileReader = null;
		// This will reference one line at a time
		BufferedReader bufferedReader = null;
		// This will reference one line at a time
		String line = null;
		try{
			fileReader = new FileReader(filepath);
			bufferedReader =  new BufferedReader(fileReader);
			while((line = bufferedReader.readLine()) != null) {
				WestSurveyVO surveyVO = getSurveyVO(line, delimiter);
				if(null != surveyVO){
					westSurveyVOList.add(surveyVO);
				}
			} 
		}finally{
			fileReader.close();
			bufferedReader.close();  
		}
		return westSurveyVOList;
	}

	/**
	 * R15_4 SL-20988 Map the WestSurveyVO object from one line read.
	 * @param line
	 * @param delimiter
	 * @return WestSurveyVO
	 */
	private WestSurveyVO getSurveyVO(String line, String delimiter){
		WestSurveyVO surveyVO = null;
		if(StringUtils.isNotBlank(line) && StringUtils.isNotBlank(delimiter)){
			String [] surveyDetails = line.split(delimiter);
			surveyVO = new WestSurveyVO();
			surveyVO.setFirmId(surveyDetails[0]);
			surveyVO.setSoId(surveyDetails[1]);
			surveyVO.setUnitNo(surveyDetails[2]);
			surveyVO.setOrderNo(surveyDetails[3]);
			surveyVO.setSurveyDate(surveyDetails[4]);
			surveyVO.setQuestion1(surveyDetails[5]);
			surveyVO.setQuestion2(surveyDetails[6]);
			surveyVO.setQuestion3(surveyDetails[7]);
			surveyVO.setQuestion4(surveyDetails[8]);
			surveyVO.setQuestion5(surveyDetails[9]);
			surveyVO.setQuestion6(surveyDetails[10]);
		}
		return surveyVO;
	}
}
