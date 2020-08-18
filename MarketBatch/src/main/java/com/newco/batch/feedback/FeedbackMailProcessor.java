package com.newco.batch.feedback;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.newco.marketplace.dto.vo.feedback.FeedbackVO;
import com.newco.marketplace.dto.vo.feedback.MobileFeedbackVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.DateUtils;
import com.servicelive.feedback.services.FeedbackService;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

public class FeedbackMailProcessor {

	private static final Logger logger = Logger
	.getLogger(FeedbackMailProcessor.class);
	private FeedbackService feedbackService;

	public void process() throws BusinessServiceException {

		String fileDir = "";
		String fileName = "";
		int numberOfDays = 0;
		try {
			numberOfDays = Integer.parseInt(PropertiesUtils.getPropertyValue(MPConstants.FEEDBACK_DAYS_INTERVAL));
			
			//If the application properties is not found default the feedback days interval to 7 days 
			if( numberOfDays == 0 ){
				numberOfDays = MPConstants.DEFAULT_FEEDBACK_DAYS_INTERVAL;
			}
			
			// Get the directory value from application_properties
			fileDir = PropertiesUtils.getPropertyValue(MPConstants.FEEDBACK_FILE_DIRECTORY);
			logger.info("Feedback file directory : " + fileDir);
			if (StringUtils.isBlank(fileDir)) {
				logger.error("application property " + MPConstants.FEEDBACK_FILE_DIRECTORY + "is empty");
			}
			
			// Fetch the feedback details from the feedback table -Order management feedback			
			List<FeedbackVO> feedbackVOlist = feedbackService.loadFeedback(numberOfDays);
			if (null != feedbackVOlist && feedbackVOlist.size() > 0) {
				logger.info("Feedback fetched : " + feedbackVOlist.size());
				
				//File name returned will have full path including the file name
				fileName = generateExcel(feedbackVOlist, fileDir);
				logger.info("File path with file name after generate excel" + fileName);
				sendEmail(fileName, fileDir,numberOfDays,MPConstants.OM_REPORT);
			} else{
				
				//Send a mail which states that there is no feedback and no attachment
				logger.info("No feedbacks available for the week FileName :"+fileName);
				sendEmail(fileName, fileDir,numberOfDays,MPConstants.OM_REPORT);
			}
			
			// Reset variables
			fileName = "";
			
			// Mobile app feedback
			List<MobileFeedbackVO> mobileDeedbackVOlist = feedbackService.loadMobileFeedback(numberOfDays);
			if (null != mobileDeedbackVOlist && mobileDeedbackVOlist.size() > 0) {
				logger.info("Mobile Feedback fetched : " + mobileDeedbackVOlist.size());
				
				// File name returned will have full path including the file name
				fileName = generateMobileFeedbackExcel(mobileDeedbackVOlist, fileDir);
				logger.info("File path with file name after generate excel :"+ fileName);
				sendEmail(fileName, fileDir, numberOfDays, MPConstants.MOBILE_REPORT);
			}else {
				
				// Send a mail which states that there is no feedback and no attachment				
				logger.info("No feedbacks available for the week FileName :"+fileName);
				sendEmail(fileName, fileDir, numberOfDays,MPConstants.MOBILE_REPORT);
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Error in FeedbackMailProcessor:" + e);
		}
	}
	
	
	/**
	 * Private Method to send Feedback details collected for to the designated
	 * mail id
	 * 
	 * @param String
	 *            messageBody
	 * @param String
	 *            subject
	 * @param String
	 *            fileName
	 * @param File
	 *            file
	 */

	private String generateMobileFeedbackExcel(List<MobileFeedbackVO> mobileDeedbackVOlist, String fileDir) {
		FileOutputStream fileOutputStream = null;
		String fileName = "";
		
		/*Get the number of columns in the excel */
		int noOfColumnsIntheSheet = 13;		
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String createdDate = "";
		
		try {
			new File(fileDir).mkdir();
						
			// Create a timestamp to attach to the excel file generated
			SimpleDateFormat sdf = new SimpleDateFormat(MPConstants.ATTACH_DATE_IN_FEEDBACK_EXCEL_FILE);
			String fileDate = sdf.format(new Date());
			fileName = fileDir + MPConstants.MOBILE_FEEDBACK + fileDate + MPConstants.XLSX_FORMAT;
			
			fileOutputStream = new FileOutputStream(fileName);
			
			// Create Sheet.
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
			XSSFSheet sheet = xssfWorkbook.createSheet(MPConstants.MOBILE_FEEDBACK_SHEET);			

			/*Style for header*/
			XSSFCellStyle headerCellStyle = xssfWorkbook.createCellStyle();
			//headerCellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
			headerCellStyle.setBorderTop((short) 1);    // single line border  
			headerCellStyle.setBorderBottom((short) 1); // single line border 

	        XSSFFont headerCellFont = xssfWorkbook.createFont();
	        headerCellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        headerCellStyle.setFont(headerCellFont);
			
	        /*Style for the data*/
			XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
			dataStyle.setWrapText(true);
			
			// Create Header Row
			XSSFRow headerRow = sheet.createRow(0);
			
			// Write row for header and apply style. Get the count for the columns added so that the auto 
			// width can be set for the columns
			XSSFCell headerCell0 = headerRow.createCell(0);
			headerCell0.setCellValue(MPConstants.FEEDBACK_ID);
			headerCell0.setCellStyle(headerCellStyle);
			
			XSSFCell headerCell1 = headerRow.createCell(1);
			headerCell1.setCellValue(MPConstants.FEEDBACK_FIRST_NAME);
			headerCell1.setCellStyle(headerCellStyle);
			
			XSSFCell headerCell2 = headerRow.createCell(2);
			headerCell2.setCellValue(MPConstants.FEEDBACK_LAST_NAME);
			headerCell2.setCellStyle(headerCellStyle);			
			
			XSSFCell headerCell3 = headerRow.createCell(3);
			headerCell3.setCellValue(MPConstants.FEEDBACK_RESOURCE_ID);
			headerCell3.setCellStyle(headerCellStyle);			
			
			XSSFCell headerCell4 = headerRow.createCell(4);
			headerCell4.setCellValue(MPConstants.FEEDBACK_USER_NAME);
			headerCell4.setCellStyle(headerCellStyle);			
			
			XSSFCell headerCell5 = headerRow.createCell(5);
			headerCell5.setCellValue(MPConstants.FEEDBACK_EMAIL);
			headerCell5.setCellStyle(headerCellStyle);		
			
			XSSFCell headerCell6 = headerRow.createCell(6);
			headerCell6.setCellValue(MPConstants.FEEDBACK_PHONE_NO);
			headerCell6.setCellStyle(headerCellStyle);			
			
			XSSFCell headerCell7 = headerRow.createCell(7);
			headerCell7.setCellValue(MPConstants.FEEDBACK_FIRM_ID);
			headerCell7.setCellStyle(headerCellStyle);			
			
			XSSFCell headerCell8 = headerRow.createCell(8);
			headerCell8.setCellValue(MPConstants.FEEDBACK_FIRM_NAME);
			headerCell8.setCellStyle(headerCellStyle);			
			
			XSSFCell headerCell9 = headerRow.createCell(9);
			headerCell9.setCellValue(MPConstants.FEEDBACK_COMMENTS);
			headerCell9.setCellStyle(headerCellStyle);			
			
			XSSFCell headerCell10 = headerRow.createCell(10);
			headerCell10.setCellValue(MPConstants.FEEDBACK_APP_VERSION);
			headerCell10.setCellStyle(headerCellStyle);			
			
			XSSFCell headerCell11 = headerRow.createCell(11);
			headerCell11.setCellValue(MPConstants.FEEDBACK_OS_VERSION);
			headerCell11.setCellStyle(headerCellStyle);		
			
			XSSFCell headerCell12 = headerRow.createCell(12);
			headerCell12.setCellValue(MPConstants.FEEDBACK_CREATED_DATE);
			headerCell12.setCellStyle(headerCellStyle);		
			
			int i = 0;
			for (MobileFeedbackVO mobileFeedbackVO : mobileDeedbackVOlist) {
				if (null != mobileFeedbackVO) {
					
					// Create data row
					XSSFRow dataRow = sheet.createRow(i + 1);
					
					// Create cells and set the style
					XSSFCell cell0 = dataRow.createCell(0);
					cell0.setCellStyle(dataStyle);
					XSSFCell cell1 = dataRow.createCell(1);
					cell1.setCellStyle(dataStyle);
					XSSFCell cell2 = dataRow.createCell(2);
					cell2.setCellStyle(dataStyle);
					XSSFCell cell3 = dataRow.createCell(3);
					cell3.setCellStyle(dataStyle);
					XSSFCell cell4 = dataRow.createCell(4);
					cell4.setCellStyle(dataStyle);
					XSSFCell cell5 = dataRow.createCell(5);
					cell5.setCellStyle(dataStyle);
					XSSFCell cell6 = dataRow.createCell(6);
					cell6.setCellStyle(dataStyle);
					XSSFCell cell7 = dataRow.createCell(7);
					cell7.setCellStyle(dataStyle);
					XSSFCell cell8 = dataRow.createCell(8);
					cell8.setCellStyle(dataStyle);
					XSSFCell cell9 = dataRow.createCell(9);
					cell9.setCellStyle(dataStyle);
					XSSFCell cell10 = dataRow.createCell(10);
					cell10.setCellStyle(dataStyle);
					XSSFCell cell11 = dataRow.createCell(11);
					cell11.setCellStyle(dataStyle);					
					XSSFCell cell12 = dataRow.createCell(12);
					cell12.setCellStyle(dataStyle);
					
					
					// Feedback id
					if (null != mobileFeedbackVO.getFeedbackId()) {
						cell0.setCellValue(mobileFeedbackVO.getFeedbackId());
					} else {
						cell0.setCellValue(MPConstants.BLANK);
					}

					// First Name
					if (!StringUtils.isBlank(mobileFeedbackVO.getFirstName())) {
						cell1.setCellValue(mobileFeedbackVO.getFirstName());
					} else {
						cell1.setCellValue(MPConstants.BLANK);
					}

					// Last Name
					if (!StringUtils.isBlank(mobileFeedbackVO.getLastName())) {
						cell2.setCellValue(mobileFeedbackVO.getLastName());
					} else {
						cell2.setCellValue(MPConstants.BLANK);
					}

					// Resource id
					if (null != mobileFeedbackVO.getCreatedBy()) {
						cell3.setCellValue(mobileFeedbackVO.getCreatedBy());
					} else {
						cell3.setCellValue(MPConstants.BLANK);
					}

					// User Name
					if (!StringUtils.isBlank(mobileFeedbackVO.getUserName())) {
						cell4.setCellValue(mobileFeedbackVO.getUserName());
					} else {
						cell4.setCellValue(MPConstants.BLANK);
					}

					// Email
					if (!StringUtils.isBlank(mobileFeedbackVO.getEmail())) {
						cell5.setCellValue(mobileFeedbackVO.getEmail());
					} else {
						cell5.setCellValue(MPConstants.BLANK);
					}

					// Phone Number
					if (!StringUtils.isBlank(mobileFeedbackVO
							.getContactNumber())) {
						cell6.setCellValue(mobileFeedbackVO.getContactNumber());
					} else {
						cell6.setCellValue(MPConstants.BLANK);
					}

					// Firm ID
					if (null != mobileFeedbackVO.getCompanyId()) {
						cell7.setCellValue(mobileFeedbackVO.getCompanyId());
					} else {
						cell7.setCellValue(MPConstants.BLANK);
					}

					// Firm Name
					if (!StringUtils.isBlank(mobileFeedbackVO.getFirmName())) {
						cell8.setCellValue(mobileFeedbackVO.getFirmName());
					} else {
						cell8.setCellValue(MPConstants.BLANK);
					}

					// Comments
					if (!StringUtils.isBlank(mobileFeedbackVO.getComments())) {
						cell9.setCellValue(mobileFeedbackVO.getComments());
					} else {
						cell9.setCellValue(MPConstants.BLANK);
					}

					// App Version
					if (!StringUtils.isBlank(mobileFeedbackVO.getAppVersion())) {
						cell10.setCellValue(mobileFeedbackVO.getAppVersion());
					} else {
						cell10.setCellValue(MPConstants.BLANK);
					}

					// Device Version
					if (!StringUtils.isBlank(mobileFeedbackVO.getDeviceOS())) {
						cell11.setCellValue(mobileFeedbackVO.getDeviceOS());
					} else {
						cell11.setCellValue(MPConstants.BLANK);
					}

					// Created date
					createdDate = "";

					if (null != mobileFeedbackVO.getCreatedDate()) {
						createdDate = df.format(mobileFeedbackVO
								.getCreatedDate());
						cell12.setCellValue(createdDate);
					} else {
						cell12.setCellValue(MPConstants.BLANK);
					}
				}
				i = i + 1;
			}
			
			/*Set the size of the column to auto*/
			for(int iloop=0;iloop<=noOfColumnsIntheSheet; iloop++){ 
				sheet.autoSizeColumn(iloop);
			}
			xssfWorkbook.write(fileOutputStream);
		} catch (Exception e) {
			 e.printStackTrace();
			 logger.error("Error in FeedbackMailProcessor while writing a file to output stream:" + e);
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			}catch (IOException ex) {
                ex.printStackTrace();
                logger.error("Error in FeedbackMailProcessor while closing file:" + ex);
			}	

		}
		logger.info("File Name  : " + fileName);
		return fileName;
	}

	/**
	 * Private Method to send Feedback details collected for to the designated
	 * mail id
	 * 
	 * @param String
	 *            messageBody
	 * @param String
	 *            subject
	 * @param String
	 *            fileName
	 * @param File
	 *            file
	 */

	private String generateExcel(List<FeedbackVO> feedbackVOlist, String fileDir) {
		// Get the location to place the csv file
		
		String mounted_location = "";
		//String mounted_location_till = "";
		String feedback_save_location = "";
		FileOutputStream fileOutputStream = null;
		String fileName = "";
		String feedbackUrlOrFileSystem = "";
		
		/*Get the number of columns in the excel */
		int noOfColumnsIntheSheet = 0;
		int headerCellOrder = 0;
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String createdDate = "";
		
		try {
			new File(fileDir).mkdir();
			//Get the mounted location from aplication_properties table
			mounted_location = PropertiesUtils.getPropertyValue(MPConstants.FEEDBACK_MOUNTED_LOCATION);
			//mounted_location_till = PropertiesUtils.getPropertyValue(MPConstants.FEEDBACK_MOUNTED_LOCATION_TILL);
			feedback_save_location = PropertiesUtils.getPropertyValue(MPConstants.FEEDBACK_SAVE_LOCATION);
			feedbackUrlOrFileSystem = PropertiesUtils.getPropertyValue(MPConstants.FEEDBACK_URL_LOCATION_TYPE);
			logger.info("Mounted location : " + mounted_location);
			logger.info("Feedback save location : " + feedback_save_location);
			//logger.info("Mounted location till : " + mounted_location_till);
			
			// Create a timestamp to attach to the excel file generated
			SimpleDateFormat sdf = new SimpleDateFormat(MPConstants.ATTACH_DATE_IN_FEEDBACK_EXCEL_FILE);
			String fileDate = sdf.format(new Date());
			fileName = fileDir + MPConstants.FEED_BACK + fileDate + MPConstants.XLSX_FORMAT;
			
			fileOutputStream = new FileOutputStream(fileName);
			
			// Create Sheet.
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
			XSSFSheet sheet = xssfWorkbook.createSheet(MPConstants.FEEDBACK_SHEET);
			//sheet.setDefaultColumnWidth(30);

			/*Style for header*/
			XSSFCellStyle headerCellStyle = xssfWorkbook.createCellStyle();
			//headerCellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
			headerCellStyle.setBorderTop((short) 1);    // single line border  
			headerCellStyle.setBorderBottom((short) 1); // single line border 

	        XSSFFont headerCellFont = xssfWorkbook.createFont();
	        headerCellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        headerCellStyle.setFont(headerCellFont);
			
	        /*Style for the data*/
			XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
			dataStyle.setWrapText(true);
			
			// Create Header Row
			XSSFRow headerRow = sheet.createRow(0);
			headerCellOrder = 0;		
			// Write row for header and apply style. Get the count for the columns added so that the auto 
			// width can be set for the columns
			XSSFCell headerCell0 = headerRow.createCell(headerCellOrder);
			headerCell0.setCellValue(MPConstants.FEEDBACK_FIRST_NAME);
			headerCell0.setCellStyle(headerCellStyle);
			noOfColumnsIntheSheet = noOfColumnsIntheSheet + 1;
			headerCellOrder = headerCellOrder + 1;
			
			XSSFCell headerCell1 = headerRow.createCell(headerCellOrder);
			headerCell1.setCellValue(MPConstants.FEEDBACK_LAST_NAME);
			headerCell1.setCellStyle(headerCellStyle);
			noOfColumnsIntheSheet = noOfColumnsIntheSheet + 1;
			headerCellOrder = headerCellOrder + 1;
			
			XSSFCell headerCell2 = headerRow.createCell(headerCellOrder);
			headerCell2.setCellValue(MPConstants.FEEDBACK_USER_ID);
			headerCell2.setCellStyle(headerCellStyle);
			noOfColumnsIntheSheet = noOfColumnsIntheSheet + 1;
			headerCellOrder = headerCellOrder + 1;
			
			XSSFCell headerCell3 = headerRow.createCell(headerCellOrder);
			headerCell3.setCellValue(MPConstants.FEEDBACK_FIRM_NAME);
			headerCell3.setCellStyle(headerCellStyle);
			noOfColumnsIntheSheet = noOfColumnsIntheSheet + 1;
			headerCellOrder = headerCellOrder + 1;
			
			XSSFCell headerCell4 = headerRow.createCell(headerCellOrder);
			headerCell4.setCellValue(MPConstants.FEEDBACK_FIRM_ID);
			headerCell4.setCellStyle(headerCellStyle);
			noOfColumnsIntheSheet = noOfColumnsIntheSheet + 1;
			headerCellOrder = headerCellOrder + 1;
			
			XSSFCell headerCell5 = headerRow.createCell(headerCellOrder);
			headerCell5.setCellValue(MPConstants.FEEDBACK_CREATED_DATE);
			headerCell5.setCellStyle(headerCellStyle);
			noOfColumnsIntheSheet = noOfColumnsIntheSheet + 1;
			headerCellOrder = headerCellOrder + 1;
			
			
			XSSFCell headerCell6 = headerRow.createCell(headerCellOrder);
			headerCell6.setCellValue(MPConstants.FEEDBACK_CATEGORY);
			headerCell6.setCellStyle(headerCellStyle);
			noOfColumnsIntheSheet = noOfColumnsIntheSheet + 1;
			headerCellOrder = headerCellOrder + 1;
			
			XSSFCell headerCell7 = headerRow.createCell(headerCellOrder);
			headerCell7.setCellValue(MPConstants.FEEDBACK_OM_PAGENAME);
			headerCell7.setCellStyle(headerCellStyle);
			noOfColumnsIntheSheet = noOfColumnsIntheSheet + 1;
			headerCellOrder = headerCellOrder + 1;
			
			XSSFCell headerCell8 = headerRow.createCell(headerCellOrder);
			headerCell8.setCellValue(MPConstants.FEEDBACK_OM_TAB);
			headerCell8.setCellStyle(headerCellStyle);
			noOfColumnsIntheSheet = noOfColumnsIntheSheet + 1;
			headerCellOrder = headerCellOrder + 1;
			
			XSSFCell headerCell9 = headerRow.createCell(headerCellOrder);
			headerCell9.setCellValue(MPConstants.FEEDBACK_URL);
			headerCell9.setCellStyle(headerCellStyle);
			noOfColumnsIntheSheet = noOfColumnsIntheSheet + 1;
			headerCellOrder = headerCellOrder + 1;
			
			XSSFCell headerCell10 = headerRow.createCell(headerCellOrder);
			headerCell10.setCellValue(MPConstants.FEEDBACK_SCREENSHOT);
			headerCell10.setCellStyle(headerCellStyle);
			noOfColumnsIntheSheet = noOfColumnsIntheSheet + 1;
			headerCellOrder = headerCellOrder + 1;
			
			XSSFCell headerCell11 = headerRow.createCell(headerCellOrder);
			headerCell11.setCellValue(MPConstants.FEEDBACK_COMMENTS);
			headerCell11.setCellStyle(headerCellStyle);
			noOfColumnsIntheSheet = noOfColumnsIntheSheet + 1;
			headerCellOrder = headerCellOrder + 1;
			
			/**/
			/*Commented the code as per the new CR SL:18927
			XSSFCell headerCell12 = headerRow.createCell(headerCellOrder);
			headerCell12.setCellValue(MPConstants.FEEDBACK_CONTACT_ME);
			headerCell12.setCellStyle(headerCellStyle);
			noOfColumnsIntheSheet = noOfColumnsIntheSheet + 1;
			headerCellOrder = headerCellOrder + 1;
			*/
			XSSFCreationHelper helper= xssfWorkbook.getCreationHelper();
			
			/*To underline the hyperlink and make the font color as blue */
			XSSFCellStyle hlink_style = xssfWorkbook.createCellStyle();
	        XSSFFont hlink_font = xssfWorkbook.createFont();
	        hlink_font.setUnderline(HSSFFont.U_SINGLE);
	        hlink_font.setColor(HSSFColor.BLUE.index);
	        hlink_style.setFont(hlink_font);
			/**/
			
			int i = 0;
			for (FeedbackVO feedbackVO : feedbackVOlist) {
				XSSFHyperlink hyper_link=helper.createHyperlink(Hyperlink.LINK_URL);
				/*Commenting the below line for testing for QA*/
				//XSSFHyperlink hyper_link=helper.createHyperlink(Hyperlink.LINK_FILE);
				if (null != feedbackVO) {
					XSSFRow dataRow = sheet.createRow(i + 1);
					// Write data in data row
					// Firm first name
					XSSFCell cell0 = dataRow.createCell(0);
					cell0.setCellStyle(dataStyle);
					if (!StringUtils.isBlank(feedbackVO.getFirstName())) {
						cell0.setCellValue(feedbackVO.getFirstName());
						logger.info("Firm First Name : "
								+ feedbackVO.getFirstName());
					} else {
						cell0.setCellValue(MPConstants.BLANK);
						logger.info("Firm First Name : " + MPConstants.BLANK);
					}
					
					//Firm last name
					XSSFCell cell1 = dataRow.createCell(1);
					cell1.setCellStyle(dataStyle);
					if (!StringUtils.isBlank(feedbackVO.getLastName())) {
						cell1.setCellValue(feedbackVO.getLastName());
						logger.info("Firm Last Name : "
								+ feedbackVO.getLastName());
					} else {
						cell1.setCellValue(MPConstants.BLANK);
						logger.info("Firm Last Name : " + MPConstants.BLANK);
					}

					//User Id
					XSSFCell cell2 = dataRow.createCell(2);
					cell2.setCellStyle(dataStyle);
					if (null != feedbackVO.getResourceId()) {
						cell2.setCellValue(feedbackVO.getResourceId());
						logger.info("Resource Id : "
								+ feedbackVO.getResourceId());
					} else {
						cell2.setCellValue(MPConstants.BLANK);
						logger.info("Resource Id : " + MPConstants.BLANK);
					}

					//Firm Name
					XSSFCell cell3 = dataRow.createCell(3);
					cell3.setCellStyle(dataStyle);
					if (!StringUtils.isBlank(feedbackVO.getFirmName())) {
						cell3.setCellValue(feedbackVO.getFirmName());
						logger.info("Firm Name: "
								+ feedbackVO.getFirmName());
					} else {
						cell3.setCellValue(MPConstants.BLANK);
						logger.info("Firm Name : " + MPConstants.BLANK);
					}

					//Firm Id
					XSSFCell cell4 = dataRow.createCell(4);
					cell4.setCellStyle(dataStyle);
					if (null != feedbackVO.getCompanyId()) {
						cell4.setCellValue(feedbackVO.getCompanyId());
						logger.info("Firm Id : " + feedbackVO.getCompanyId());
					} else {
						cell4.setCellValue(MPConstants.BLANK);
						logger.info("Firm Id : " + MPConstants.BLANK);
					}
                   //Created date
					createdDate = "";
						
					XSSFCell cell5 = dataRow.createCell(5);
					cell5.setCellStyle(dataStyle);
					if (null != feedbackVO.getCreatedDate()) {
						createdDate = df.format(feedbackVO.getCreatedDate());
						cell5.setCellValue(createdDate);
						logger.info("Created Date : " + feedbackVO.getCreatedDate());
					} else {
						cell5.setCellValue(MPConstants.BLANK);
						logger.info("Created Date : " + MPConstants.BLANK);
					}
					
					//Category
					XSSFCell cell6 = dataRow.createCell(6);
					cell6.setCellStyle(dataStyle);
					if (!StringUtils.isBlank(feedbackVO.getCategory())) {
						cell6.setCellValue(feedbackVO.getCategory());
						logger.info("Category : " + feedbackVO.getCategory());
					} else {
						cell6.setCellValue(MPConstants.BLANK);
						logger.info("Category : " + MPConstants.BLANK);
					}
					//

					//PageName
					XSSFCell cell7 = dataRow.createCell(7);
					cell7.setCellStyle(dataStyle);
					if (!StringUtils.isBlank(feedbackVO.getPageName())) {
						cell7.setCellValue(feedbackVO.getPageName());
						logger.info("PageName : " + feedbackVO.getPageName());
					} else {
						cell7.setCellValue(MPConstants.BLANK);
						logger.info("PageName : " + MPConstants.BLANK);
					}

					//Tab
					XSSFCell cell8 = dataRow.createCell(8);
					cell8.setCellStyle(dataStyle);
					if (!StringUtils.isBlank(feedbackVO.getTabName())) {
						cell8.setCellValue(feedbackVO.getTabName());
						logger.info("Tab : " + feedbackVO.getTabName());
					} else {
						cell8.setCellValue(MPConstants.BLANK);
						logger.info("Tab : " + MPConstants.BLANK);
					}

					//Url
					XSSFCell cell9 = dataRow.createCell(9);
					cell9.setCellStyle(dataStyle);
					if (!StringUtils.isBlank(feedbackVO.getSourceURL())) {
						cell9.setCellValue(feedbackVO.getSourceURL());
						logger.info("Url : " + feedbackVO.getSourceURL());
					} else {
						cell9.setCellValue(MPConstants.BLANK);
						logger.info("Url : " + MPConstants.BLANK);
					}

					//Screenshot
					XSSFCell cell10 = dataRow.createCell(10);
					cell10.setCellStyle(dataStyle);
					if (!StringUtils.isBlank(feedbackVO.getFileName())) {
						logger.info("File name attached by the user : " + feedbackVO.getFileName());
						String hyperLinkPath = "";
						if (!StringUtils.isBlank(feedbackVO.getScreenshotURL())) {
							logger.info("ScreenshotURL : " + feedbackVO.getScreenshotURL());
							//hyperLinkPath= feedbackVO.getScreenshotURL().replace(mounted_location_till, mounted_location);
							hyperLinkPath= feedbackVO.getScreenshotURL().replace(feedback_save_location, mounted_location);
							
							logger.info("Mounted_location: " + mounted_location);
							logger.info("Replacing ScreenshotURL with mounted_location: " + hyperLinkPath);
							hyperLinkPath = hyperLinkPath.replace(MPConstants.SPACE, MPConstants.REPLACE_SPACE);
							if(feedbackUrlOrFileSystem.equals(MPConstants.FEEDBACK_URL_OR_FILESYSTEM_TYPE)){
								/*Added a code to acccess from a host location*/
								logger.info("FEEDBACK_URL_OR_FILESYSTEM_TYPE : " + feedbackUrlOrFileSystem);
								hyper_link.setAddress(hyperLinkPath);
							}else{
								/*Commenting the below line for testing for QA*/
								logger.info("FEEDBACK_URL_OR_FILESYSTEM_TYPE : " + feedbackUrlOrFileSystem);
								hyper_link.setAddress("file://"+hyperLinkPath);
							}	
							cell10.setHyperlink(hyper_link);
							
						}
						logger.info("HyperLinkPath: " + hyperLinkPath);
						cell10.setCellValue(feedbackVO.getFileName());
						cell10.setCellStyle(hlink_style);
						
					}
					else {
						cell10.setCellValue(MPConstants.BLANK);
						logger.info("Screenshot : " + MPConstants.BLANK);
					}

					//Comments
					XSSFCell cell11 = dataRow.createCell(11);
					cell11.setCellStyle(dataStyle);
					if (!StringUtils.isBlank(feedbackVO.getFeedbackComments())) {
						cell11.setCellValue(feedbackVO.getFeedbackComments());
						logger.info("Comments : "
								+ feedbackVO.getFeedbackComments());
					} else {
						cell11.setCellValue(MPConstants.BLANK);
						logger.info("Comments: " + MPConstants.BLANK);
					}

					//Contact Indicator
					/* Commented the code as per the new CR SL:18927
					XSSFCell cell11 = dataRow.createCell(11);
					cell11.setCellStyle(dataStyle);
					if (null != feedbackVO.getContactInd()) {

						if (feedbackVO.getContactInd()) {
							cell11
							.setCellValue(MPConstants.FEEDBACK_CONTACT_ME_YES);
							logger.info("Contact Indicator YES: "
									+ feedbackVO.getContactInd());
						} else {
							cell11
							.setCellValue(MPConstants.FEEDBACK_CONTACT_ME_NO);
							logger.info("Contact Indicator NO: "
									+ feedbackVO.getContactInd());
						}

					} else {
						cell11.setCellValue(MPConstants.BLANK);
						logger.info("Contact Indicator : " + MPConstants.BLANK);
					}
					*/

				}
				i = i + 1;

			}
			/*Set the size of the column to auto*/
			for(int iloop=0;iloop< noOfColumnsIntheSheet; iloop++){ 
				sheet.autoSizeColumn(iloop);
			}
			xssfWorkbook.write(fileOutputStream);
		} catch (Exception e) {
			 e.printStackTrace();
			 logger.error("Error in FeedbackMailProcessor while writing a file to output stream:" + e);
		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			}catch (IOException ex) {
                ex.printStackTrace();
                logger.error("Error in FeedbackMailProcessor while closing file:" + ex);
			}	

		}
		logger.info("File Name  : " + fileName);
		return fileName;
	}

	/**
	 * Private Method to send Feedback details collected for to the designated
	 * mail id
	 * 
	 * @param String
	 *            messageBody
	 * @param String
	 *            subject
	 * @param String
	 *            fileName
	 * @param File
	 *            file
	 */
	private void sendEmail(String fileName, String fileDir,int numberOfDays, String reportType) {
		logger.info("Inside sendEmail()");
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		// Get the list of email ids to which the feedback has to be send
		String toAddresses = "";
		if(StringUtils.equalsIgnoreCase(reportType, MPConstants.OM_REPORT)){
			toAddresses = PropertiesUtils.getPropertyValue(MPConstants.FEEDBACK_EMAIL_GROUP);
		}else if (StringUtils.equalsIgnoreCase(reportType, MPConstants.MOBILE_REPORT)){
			toAddresses = PropertiesUtils.getPropertyValue(MPConstants.MOBILE_FEEDBACK_EMAIL_GROUP);
		}	
		
		// Split the email ids to a string of array
		String toRecipients[] = toAddresses.split(MPConstants.SPLIT);

		mailSender.setHost(MPConstants.MAIL_HOST);
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		//Get todays date
		DateFormat dateFormat = new SimpleDateFormat(MPConstants.FEEDBACK_EMAIL_MESSAGE_DATE);
		Date toDate = new Date();
		logger.info("Todays date  : " + dateFormat.format(toDate));
		Date fromDate = DateUtils.addDaysToDate(toDate, numberOfDays * -1);
		logger.info("From date  : " + fromDate);
		
		//Message body 
		String messageBody = "";
		
		// Attach the file to be send
		File file = null;
		if(!StringUtils.isBlank(fileName)){
			file = new File(fileName);
			//Get the actual file name only from the full path
			fileName = fileName.replace(fileDir, "");
			logger.info("File name of the attached file in the email : " + fileName);
			if(StringUtils.equalsIgnoreCase(reportType, MPConstants.OM_REPORT)){
				messageBody = MPConstants.FEEDBACK_EMAIL_MESSAGE_BODY + dateFormat.format(fromDate.getTime()) 
	            + MPConstants.FEEDBACK_EMAIL_MESSAGE_BODY_TO + dateFormat.format(toDate.getTime()) 
	            + MPConstants.FEEDBACK_EMAIL_MESSAGE_BODY_DOT;
			}else if (StringUtils.equalsIgnoreCase(reportType, MPConstants.MOBILE_REPORT)){
				messageBody = MPConstants.MOBILE_FEEDBACK_EMAIL_MESSAGE_BODY + dateFormat.format(fromDate.getTime()) 
	            + MPConstants.FEEDBACK_EMAIL_MESSAGE_BODY_TO + dateFormat.format(toDate.getTime()) 
	            + MPConstants.FEEDBACK_EMAIL_MESSAGE_BODY_DOT;
			}
		}else{	
			// Send a different message if no feedbacks are available
			if(StringUtils.equalsIgnoreCase(reportType, MPConstants.OM_REPORT)){
				messageBody = MPConstants.EMAIL_MESSAGE_BODY_WHEN_NO_FEEDBACK + dateFormat.format(fromDate.getTime()) 
				+ MPConstants.FEEDBACK_EMAIL_MESSAGE_BODY_TO + dateFormat.format(toDate.getTime()) 
				+ MPConstants.FEEDBACK_EMAIL_MESSAGE_BODY_DOT;
			}else if (StringUtils.equalsIgnoreCase(reportType, MPConstants.MOBILE_REPORT)){
				messageBody = MPConstants.MOBILE_EMAIL_MESSAGE_BODY_WHEN_NO_FEEDBACK + dateFormat.format(fromDate.getTime()) 
	            + MPConstants.FEEDBACK_EMAIL_MESSAGE_BODY_TO + dateFormat.format(toDate.getTime()) 
	            + MPConstants.FEEDBACK_EMAIL_MESSAGE_BODY_DOT;
			}
		}
		
		try {
			MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage,true);
			//mimeHelper.setText(messageBody, true);
			mimeHelper.setText(messageBody);
			if(StringUtils.equalsIgnoreCase(reportType, MPConstants.OM_REPORT)){
				mimeHelper.setSubject(MPConstants.FEEDBACK_MAIL_SUBJECT);
			}else if (StringUtils.equalsIgnoreCase(reportType, MPConstants.MOBILE_REPORT)){
				mimeHelper.setSubject(MPConstants.MOBILE_FEEDBACK_MAIL_SUBJECT);
			}
			mimeHelper.setTo(toRecipients);
			mimeHelper.setFrom(MPConstants.EMAIL_FROM);
			//No need to attach a file when there are no feedbacks
			if(!StringUtils.isBlank(fileName)){
				mimeHelper.addAttachment(fileName, file);
			}
			mailSender.send(mimeMessage);
			logger.info("Mail send with attachment with name : " + fileName);
		} catch (MessagingException msgEx) {
			msgEx.printStackTrace();
			logger.error("Unexpected error while sending email", msgEx);
		}
	}

	public FeedbackService getFeedbackService() {
		return feedbackService;
	}

	public void setFeedbackService(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
	}

}