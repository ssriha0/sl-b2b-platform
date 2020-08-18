package com.newco.batch.csatmapping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.newco.batch.ABatchProcess;
import com.newco.marketplace.auth.SecurityContext;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.business.iBusiness.survey.ISurveyBO;
import com.newco.marketplace.constants.Constants.AppPropConstants;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.survey.SurveyQuestionVO;
import com.newco.marketplace.dto.vo.survey.SurveyResponseVO;
import com.newco.marketplace.dto.vo.survey.SurveyVO;
import com.newco.marketplace.dto.vo.survey.WestSurveyErrorVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.interfaces.SurveyConstants;
import com.newco.marketplace.util.PropertiesUtils;
import com.newco.marketplace.utils.SecurityUtil;
import com.newco.marketplace.utils.UIUtils;
//21513
public class CSATIntegrationProcessor extends ABatchProcess implements Runnable{
	private IServiceOrderBO serviceOrderBO;
	private ISurveyBO surveyBO;
	static Integer successCount;
	static Integer failureCount;	
	List<String> errorsList;
	BufferedReader br;
	private static final String  CsatFileLocation = PropertiesUtils.getPropertyValue(AppPropConstants.CSAT_MAPPING);
	private static final String  CsatFileArchive = PropertiesUtils.getPropertyValue(AppPropConstants.CSAT_MAPPING_ARCHIVE);
	private static final String  CsatFileError = PropertiesUtils.getPropertyValue(AppPropConstants.CSAT_MAPPING_ERROR);
	Map<String,List<String>> errorFiles;
	SecurityContext securityContext = SecurityUtil.getSystemSecurityContext();
	File f;
	static Boolean moveFile;
	private static final Logger logger = Logger.getLogger(CSATIntegrationProcessor.class.getName());
	public CSATIntegrationProcessor(){

	}
	public CSATIntegrationProcessor(File f,Map<String,List<String>> errorFiles,List<String> errorsList,SecurityContext securityContext, ISurveyBO surveyBO, BufferedReader br){
		this.f = f;
		this.errorFiles = errorFiles;
		this.errorsList = errorsList;
		this.securityContext = securityContext;
		this.surveyBO = surveyBO;
		this.br = br;
	}

	public int execute() throws com.newco.marketplace.exception.core.BusinessServiceException {
		long startTime = System.currentTimeMillis();
		final int MILISECONDS_PER_SECOND = 1000;					
		logger.info("source file path -> " + CsatFileLocation);		
		logger.info("archive file path -> " + CsatFileArchive);		
		logger.info("error file path -> " + CsatFileError);
		File[] filesInDirectory = new File(CsatFileLocation).listFiles();		
		errorFiles = new HashMap<String, List<String>>();
		if(null != filesInDirectory ){
			for(File file : filesInDirectory){	
				f = file;
				String filePath = f.getAbsolutePath();
				String fileExtenstion = filePath.substring(filePath.lastIndexOf(".") + 1,filePath.length());
				errorsList = new ArrayList<String>();
				CSATIntegrationProcessor.successCount = 0;
				CSATIntegrationProcessor.failureCount = 0;
				CSATIntegrationProcessor.moveFile = true;
				if("csv".equalsIgnoreCase(fileExtenstion)){
					logger.info("CSV file found -> " + filePath);
					try {
						br = new BufferedReader(new FileReader(f));
						String headerLine = br.readLine();
						if(f.length()==0L || br == null){
							if (br != null) {
								br.close();
							}				
							errorsList.add("CSAT mapping file does not have any record");
							errorFiles.put(f.getName(),errorsList);
							throw new BusinessServiceException("CSAT mapping file does not have any record: "+f.getName());
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					
					Thread[] threadList = new Thread[10];
					for(int i = 0;i<10;i++){
						Runnable task = new CSATIntegrationProcessor(f,errorFiles,errorsList,securityContext,getSurveyBO(),br);
						Thread thread = new Thread(task);
						thread.start();
						threadList[i] = thread;
					}
					
					for(Thread thread : threadList){
						try {
							thread.join();
						} catch (InterruptedException e) {
							logger.info("InterruptedException Exception occurred");
							e.printStackTrace();
						}
					}					
					if(errorsList.size()>0){
						errorsList.add("FileName -> "+ f.getAbsolutePath());
						errorsList.add(CSATIntegrationProcessor.successCount+"#"+CSATIntegrationProcessor.failureCount);
						errorFiles.put(f.getName(), errorsList);
					}
					if(CSATIntegrationProcessor.moveFile){
						f.renameTo(new File(CsatFileArchive + f.getName()));
					}
					if (br != null) {
						try {
							br.close();
						} catch (IOException e) {
							logger.info("Exception while closing file");
							e.printStackTrace();
						}
					}
				}
			}
		}else{
			logger.info("directory does not contain any file");
		}
		if(!errorFiles.isEmpty()){
			long endTime = System.currentTimeMillis();
			for (String fileName : errorFiles.keySet()) {
				try {
					String fileExtension = fileName.split("\\.")[1].toString();
					PrintWriter printWriter = new PrintWriter(new File(CsatFileError+fileName.replace("."+fileExtension, "_error.txt")));
					for(String message : errorFiles.get(fileName)){
						printWriter.println(message);
					}
					printWriter.println("Time taken for CSATResponseScheduler: " + (endTime - startTime) / MILISECONDS_PER_SECOND + " seconds");
					printWriter.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				File file = new File(CsatFileLocation+fileName);
				file.renameTo(new File(CsatFileError +fileName));
			}
			String emailHostName = PropertiesUtils.getPropertyValue(AppPropConstants.SMTP_SERVER);
			String to = PropertiesUtils.getPropertyValue(AppPropConstants.CSAT_MAIL_TO);
			String from = PropertiesUtils.getPropertyValue(AppPropConstants.CSAT_MAIL_FROM);
			notifyError(errorFiles, emailHostName, to, from, CsatFileError);
		}
		return 1;
	}


	private Integer getSLCSATConverted(Integer value){
		Integer convertedValue=null;
		if(null != value){
			if(value.equals(9) || value.equals(8)){
				convertedValue=5;
			}else if(value.equals(7) || value.equals(6)){
				convertedValue=4;
			}else if(value.equals(5) || value.equals(4)){
				convertedValue=3;
			}else if(value.equals(3) || value.equals(2)){
				convertedValue=2;
			}else if(value.equals(1) || value.equals(0)){
				convertedValue=1;
			}
		}
		return convertedValue;
	}
	
	/**
	 * Private Method to send West Survey error notification email to production support
	 * @param String messageBody
	 * @param String subject
	 * @param String fileName
	 * @param File file
	 */
	private void sendEmail(String emailHostName, String to, String from, String subject, String messageBody, File file) {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(emailHostName);
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		try {
			MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, true);
			mimeHelper.setText(messageBody, false);
			mimeHelper.setSubject(subject);
			mimeHelper.setTo(to.split(","));
			mimeHelper.setFrom(from);
			mimeHelper.addAttachment(file.getName(), file);
			mailSender.send(mimeMessage);
		} catch (MessagingException msgEx) {
			logger.error("Unexpected error while sending email", msgEx);
		}
		logger.info("CSAT Survey Error notification email sent to: " + to);
	}
	
	/**
	 * Method to send West Survey error notification email to production support
	 * @param Map<String,List<WestSurveyErrorVO>> erroredFilesRecords
	 */
	public void notifyError(Map<String,List<String>> erroredFilesRecords, String emailHostName, String to, String from,
								String errFilePath) {
		for (String fileName : erroredFilesRecords.keySet()) {
			String subject = getSubject(fileName);
			String messagaeBody = getMessageBody(fileName, erroredFilesRecords.get(fileName));
			String fileExtension = fileName.split("\\.")[1].toString();
			File file = new File(CsatFileError+fileName.replace("."+fileExtension, "_error.txt"));
			sendEmail(emailHostName, to, from, subject, messagaeBody,file);
		}			
	}
	
	/**
	 * Method to get the subject for West Survey error notification email
	 * @param String fileName
	 * @return String subject
	 */
	private String getSubject(String fileName) {
		String subject = null;
		StringBuilder sb = new StringBuilder();
		sb.append(SurveyConstants.CSAT_MAIL_SUBJECT_FILE_RECORDS_FAILED);
		sb.append(fileName)
			.append(SurveyConstants.HYPHEN)
			.append(SurveyConstants.WEST_MAIL_SUBJECT_ENV_DESCR)
			.append(System.getenv("sl_app_lifecycle"));		
		return subject;
	}
	
	/**
	 * Method to get the message body for West Survey error notification email
	 * @param String fileName
	 * @return String
	 */
	private String getMessageBody(String fileName, List<String> erroredList) {
		StringBuilder sb = new StringBuilder();
		int success = Integer.parseInt(erroredList.get(erroredList.size()-1).split("#")[0]);
		int fail = Integer.parseInt(erroredList.get(erroredList.size()-1).split("#")[1]);
		if (null != erroredList && !erroredList.isEmpty()) {
			sb.append("Summary of CSAT survey file processing.").append(SurveyConstants.NEWLINE_CHAR);
			sb.append("\t").append(" 1. Total number of records").append(SurveyConstants.HYPHEN).append(success+fail).append(SurveyConstants.NEWLINE_CHAR);
			sb.append("\t").append(" 2. Number of successfull records").append(SurveyConstants.HYPHEN).append(success).append(SurveyConstants.NEWLINE_CHAR);
			sb.append("\t").append(" 3. Number of failed records").append(SurveyConstants.HYPHEN).append(fail).append(SurveyConstants.NEWLINE_CHAR).append(SurveyConstants.NEWLINE_CHAR);
			sb.append(SurveyConstants.WEST_MAIL_BODY_FILES_FAILED_DESCR).append(fileName).append(SurveyConstants.NEWLINE_CHAR).append(SurveyConstants.NEWLINE_CHAR);
			sb.append(SurveyConstants.CSAT_MAIL_BODY_RECORDS_FAILED_DESCR).append(SurveyConstants.NEWLINE_CHAR);
			sb.append(SurveyConstants.NEWLINE_CHAR).append(SurveyConstants.NEWLINE_CHAR);
			sb.append("Regards,").append(SurveyConstants.NEWLINE_CHAR);
			sb.append("ServiceLive Team");
		} 
		return sb.toString();
	}

	@Override
	public void setArgs(String[] args) {

	}

	public ISurveyBO getSurveyBO() {
		return surveyBO;
	}

	public void setSurveyBO(ISurveyBO surveyBO) {
		this.surveyBO = surveyBO;
	}

	public void run() {
		// TODO Auto-generated method stub
		final String cvsSplitBy = ",";		
		try {			
			String line = "";
			while ((line = this.br.readLine()) != null) {
				if(line.length() != 0){
					line = line.replaceAll("\"", "");
					String[] csatMapping = line.split(cvsSplitBy);
					ServiceOrder serviceOrder = this.surveyBO.getInHomeBuyers(csatMapping[4]);
					boolean  csatSOCount = this.surveyBO.saveCSATData(csatMapping[4]);
					if (StringUtils.isEmpty(line) || StringUtils.isEmpty(csatMapping[13]) || !csatMapping[13].matches("^[0-9]$") || null == serviceOrder || !csatSOCount) {
						if(null != serviceOrder && StringUtils.isEmpty(csatMapping[13])){
							this.errorsList.add(csatMapping[4]+"->"+"Q8 is empty/null");
						}else if(null != serviceOrder && !csatMapping[13].matches("^[0-9]$")){
							this.errorsList.add(csatMapping[4]+"->"+"Q8 is not valid, It should be numeric 0-9");
						}else if(null != serviceOrder && !csatSOCount){
							this.errorsList.add(csatMapping[4]+"->"+"For this serviceOrderId CSAT already processed");
						}else if(null == serviceOrder){
							this.errorsList.add(csatMapping[4]+"->"+"serviceOrderId is null");
						}else{
							this.errorsList.add("Error, Please check file manually");
						}
						CSATIntegrationProcessor.failureCount++;	
						CSATIntegrationProcessor.moveFile = false;
						continue;
					}
					SurveyVO surveyVO = new SurveyVO();		
					surveyVO.setSurveyType(SurveyConstants.SURVEY_TYPE_PROVIDER);
					surveyVO.setEntityType(SurveyConstants.ENTITY_BUYER);
					surveyVO.setUserId(serviceOrder.getBuyerResourceId());
					surveyVO.setServiceOrderID(csatMapping[4]);
					//TODO: Replace it from session 
					//Fetch the questions 
					surveyVO = this.surveyBO.retrieveQuestions(surveyVO,null);
					ArrayList<SurveyQuestionVO> questions  = surveyVO.getQuestions();
					SurveyResponseVO surveyResponseVO = null;
					for (SurveyQuestionVO question : questions) {
						ArrayList<SurveyResponseVO> responses = new ArrayList<SurveyResponseVO>();
						surveyResponseVO = new SurveyResponseVO();
						Integer scoreValue=getSLCSATConverted(Integer.parseInt(csatMapping[13]));
						surveyResponseVO.setAnswerId(this.surveyBO.getAnswer(question.getQuestionId(),scoreValue));
						surveyResponseVO.setSurveyId(surveyVO.getSurveyId());
						surveyResponseVO.setEntityTypeId(surveyVO.getEntityTypeId());
						surveyResponseVO.setEntityId(surveyVO.getUserId());
						surveyResponseVO.setQuestionId(question.getQuestionId());
						responses.add(surveyResponseVO);
						question.setResponses(responses);
					}
					surveyVO.setQuestions(questions);
					surveyVO.setSurveyComments(UIUtils.encodeSpecialChars("CSAT"));
					try{
						this.surveyBO.saveResponse(surveyVO, this.securityContext,null);
						CSATIntegrationProcessor.successCount++;
					}catch(Exception e){
						CSATIntegrationProcessor.moveFile = false;
						this.errorsList.add(csatMapping[4]+"->"+"Exception Occured while processing file:"+e.getMessage());
						e.printStackTrace();
					}																
				}							
			}			
		}catch(Exception e){
			logger.info("Exception occurred while processing file");
			e.printStackTrace();
		}	
	}

}
