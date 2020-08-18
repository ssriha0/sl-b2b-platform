package com.newco.marketplace.util.scheduler;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.log4j.Logger;
import org.apache.ws.security.processor.Processor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;

import com.newco.batch.background.BackgroundCheckPollingProcess;
import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.util.PropertiesUtils;

public class BackgroundCheckProcessScheduler extends ABaseScheduler 
			implements Job {
	
	private static final Logger logger = Logger.getLogger(BackgroundCheckProcessScheduler.class.getName());
	
	private static final String inputFileDirectory = PropertiesUtils
		.getPropertyValue(Constants.AppPropConstants.BACKGROUND_CHECK_FILE_DIRECTORY);
	
	private static final String inputplusOneFileDirectory = PropertiesUtils
			.getPropertyValue(Constants.AppPropConstants.BACKGROUND_CHECK_PLUSONE_FILE_DIRECTORY);

	private static final String inputFileArchiveDirectory = PropertiesUtils
		.getPropertyValue(Constants.AppPropConstants.BACKGROUND_CHECK_FILE_ARCHIVE_DIRECTORY);

	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		logger.info("BackgroundCheckProcessScheduler start-->"+ new Date());
		ApplicationContext applicationContext = getApplicationContext(context);
		
		BackgroundCheckPollingProcess processor = (BackgroundCheckPollingProcess)applicationContext.getBean("backgroundCheckPollingProcess");
		//SL-19387 process new plusOne file
		processInputFile(processor);
		//SL-19387 process old plusOne file
		processPlusOneFile(processor);
		//SL-19387 update recertification_status in sl_pro_bgnd_chk table.
		processor.updateRecertificationstatus();
		logger.info("BackgroundCheckProcessScheduler end-->"+ new Date());
	}

	public static void main (String args[]) throws JobExecutionException {
		
		logger.info("BackgroundCheckProcessScheduler-->"+ new Date());
		ApplicationContext applicationContext = MPSpringLoaderPlugIn.getCtx();
		
		BackgroundCheckPollingProcess processor = (BackgroundCheckPollingProcess)applicationContext.getBean("backgroundCheckPollingProcess");
		processInputFile(processor);
		processPlusOneFile(processor);
		processor.updateRecertificationstatus();
	}
	
	/**
	 * This function is for moving the processed file to the archive directory 
	 * @param archiveDir
	 * @param inputFile
	 * @param fileName
	 * @throws JobExecutionException
	 */
	private static void archiveInputFile(File archiveDir, File inputFile,String fileName)throws JobExecutionException {
		
		// Archiving the file. If the file already exists then overwriting the existing one
		File archivedFile = new File(archiveDir,fileName);
		if (archivedFile.exists()) {
			archivedFile.delete();
		}
		
		boolean success = inputFile.renameTo(archivedFile);
		if (!success) {
			throw new JobExecutionException("BackgroundCheckProcessScheduler failed to move file: " + fileName + " to directory: " + inputFileArchiveDirectory);
		}
	}
	
	/**
	 * This function is for processing the file to the archive directory 
	 * process new plusOne File format.
	 * @param processor
	 * @throws BusinessServiceException
	 * @throws JobExecutionException
	 */
	@SuppressWarnings("unchecked")
	private static void processInputFile(BackgroundCheckPollingProcess processor) throws JobExecutionException {
		
		// Getting the input file directory and checking whether the folder exists or not 
		File inputDir = new File(inputFileDirectory);
		if (!inputDir.exists()) {
			logger.info("The source directory [" + inputFileDirectory + "] for Plus One files does not exists.");
			return;
		}
		
		// Getting the archive file directory and checking whether the folder exists or not 
		// If not existing then creating a new folder
		File archiveDir = new File(inputFileArchiveDirectory);	
		if (!archiveDir.exists()) {
			logger.info("The archive directory [" + inputFileDirectory + "] for Plus One files does not exists. So creating  the archive folder");
			archiveDir.mkdir();
		}
		
		// Reading the files in the input directory
		File[] files = inputDir.listFiles();
		if (files == null) {
			logger.info("No Plus One files found on " + new Date());
			return;
		}
		
		if (files.length>0) {
			// Sorting the read files based on the last modified time stamp of the files
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
			
			// Processing each file
			for (File inputFile : files) {
				String filePath = inputFile.getPath();
				processor.setInputFileName(filePath);
				try {
					processor.executePlusOne();
				} catch (BusinessServiceException bse) {
					throw new JobExecutionException("BackgroundCheckProcessScheduler failed! Error Message: " + bse.getMessage(), bse);
				}   
			    String fileName = inputFile.getName();
				
			    // Archiving the processed file
			    archiveInputFile(archiveDir, inputFile, fileName);
			}
		}
	}
	
	
	
	
	/**
	 * This function is for processing the file to the archive directory 
	 * This is for old file format.
	 * @param processor
	 * @throws BusinessServiceException
	 * @throws JobExecutionException
	 */
	@SuppressWarnings("unchecked")
	private static void processPlusOneFile(BackgroundCheckPollingProcess processor) throws JobExecutionException {
		
		// Getting the input file directory and checking whether the folder exists or not 
		File inputDir = new File(inputplusOneFileDirectory);
		if (!inputDir.exists()) {
			logger.info("The source directory [" + inputplusOneFileDirectory + "] for Plus One files does not exists.");
			return;
		}
		
		// Getting the archive file directory and checking whether the folder exists or not 
		// If not existing then creating a new folder
		File archiveDir = new File(inputFileArchiveDirectory);	
		if (!archiveDir.exists()) {
			logger.info("The archive directory [" + inputFileDirectory + "] for Plus One files does not exists. So creating  the archive folder");
			archiveDir.mkdir();
		}
		
		// Reading the files in the input directory
		File[] files = inputDir.listFiles();
		if (files == null) {
			logger.info("No Plus One files found on " + new Date());
			return;
		}
		
		if (files.length>0) {
			// Sorting the read files based on the last modified time stamp of the files
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);
			
			// Processing each file
			for (File inputFile : files) {
				String filePath = inputFile.getPath();
				processor.setInputFileName(filePath);
				try {
					processor.execute();
				} catch (BusinessServiceException bse) {
					throw new JobExecutionException("BackgroundCheckProcessScheduler failed! Error Message: " + bse.getMessage(), bse);
				}   
			    String fileName = inputFile.getName();
				
			    // Archiving the processed file
			    archiveInputFile(archiveDir, inputFile, fileName);
			}
		}
	}
	
	
	
	
}
