package com.newco.batch.shipregistration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.velocity.exception.ParseErrorException;
import org.springframework.dao.DataAccessException;

import com.newco.batch.ABatchProcess;
import com.newco.marketplace.business.iBusiness.provider.IRegistrationBO;
import com.newco.marketplace.dto.vo.shipregistration.SHIPErrorVO;
import com.newco.marketplace.exception.AuditException;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.exception.EmailSenderException;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.vo.provider.LocationVO;
import com.newco.marketplace.vo.provider.ProviderRegistrationVO;
import com.servicelive.shipregistration.ISHIPProviderRegistrationService;

/*
 * The class is to process data in a excel file to register providers in ServiceLive.
 */
public class SHIPProviderRegistrationProcessor extends ABatchProcess implements ShipImportErrorMessages {
	private IRegistrationBO registrationBO;
	private ISHIPProviderRegistrationService providerRegistrationService;
	private static final Logger logger = Logger.getLogger(SHIPProviderRegistrationProcessor.class.getName());
	
	private static Date defaultDate=null;
	
	int rowCount = 0;

	@Override
	public int execute() {
		processFile();
		return 0;
	}

	/*
	 * Processes data in the sheets "Firm Data" and "Provider Data" of excel
	 * file. Validates the data and inserts the details in SL DB to register new
	 * SHIP provider firms and techs.
	 */
	private void processFile() {
		
		// Fetch from application properties
		String folderName = providerRegistrationService.getApplicationProperty(SHIP_INPUT_DIRECTORY);
		logger.info("Folder name from application property:" + folderName);
		File folder = new File(folderName);
		logger.info("checking wheather a directory:" + folder.isDirectory());
		logger.info("checking wheather a file:" + folder.isFile());
		File archiveFolder = new File(folderName + "/"+ARCHIEVE_FOLDER);
		Workbook workbook;
		ProviderRegistrationVO registrationVO = null;
		Sheet sheet = null;
		String fileName=null;
		String fileNameWithoutExtension=null;
		SHIPErrorVO errorVO = new SHIPErrorVO();
		List<SHIPErrorVO> errorVOList = new ArrayList<SHIPErrorVO>();
		List<String>errors=new ArrayList<String>();
		List<ProviderRegistrationVO> successRecordsForFirm = new ArrayList<ProviderRegistrationVO>();
		List<ProviderRegistrationVO> successRecordsForProvider = new ArrayList<ProviderRegistrationVO>();
		/* This for loop search for the required excel file in the ship directory */
		for (File fileEntry : folder.listFiles()) {
		    fileName = fileEntry.getName();
		    logger.info("File name using fileEntry.getName():" + fileName);
		    fileNameWithoutExtension=FilenameUtils.removeExtension(fileName);
		    logger.info("File name without extension is:"+ fileNameWithoutExtension);
			try {
				if(fileEntry.isFile()){
				FileInputStream parseFile = new FileInputStream(fileEntry);
				
				// Check the extension of files in the directory
				if (fileName.trim().endsWith(EXCEL_XLS)|| fileName.trim().endsWith(EXCEL_XLSX )) {
					
					// This workbook will hold the excel data.
					workbook = (Workbook)new XSSFWorkbook(parseFile);
					
					//Adding code to check two mandatory sheet one for firm and other for provider is present in xml
					String firmSheetName = providerRegistrationService.getApplicationProperty(FIRM_SHEET_NAME);
					
					//Firm sheet is in xml
					Sheet firmSheet = (Sheet) workbook.getSheet(firmSheetName);
					String crewSheetName = providerRegistrationService.getApplicationProperty(CREW_SHEET_NAME);
					
					//Provider sheet is in xml
					Sheet providerSheet = (Sheet) workbook.getSheet(crewSheetName);
					
					//If both sheets are available in the xml passed then proceed further else skip
					if(null!=firmSheet && null!=providerSheet){
					
						// Method to validate and create firm stub account
						logger.info("started creating stub account for firm");
						
						//Adding error list object to have one failure file for error at firm and provider
						List<SHIPErrorVO> validationErrorVOList = new ArrayList<SHIPErrorVO>();
						ValidateAndCreateStubAccountForFirm(workbook, sheet,registrationVO, fileNameWithoutExtension,validationErrorVOList,successRecordsForFirm);
						logger.info("Total size of validationErrorVOList after validating firm"+validationErrorVOList.size());
						
						// Method to validate and create provider stub account
						logger.info("started creating stub account for provider");
						ValidateAndCreateStubAccountForProvider(workbook, sheet,registrationVO, fileNameWithoutExtension,validationErrorVOList,successRecordsForProvider);
						
						logger.info("Total size of validationErrorVOList after validating provider"+validationErrorVOList.size());
						// Logs the information regarding error records.
						if (null != validationErrorVOList && validationErrorVOList.size() > 0) {
							logger.info("Started creating failure excel for provider and provider and firm");
						   //fileName=FilenameUtils.removeExtension(fileName);
							logAccountFailures(validationErrorVOList, fileNameWithoutExtension);
							logger.info("Successful Creation of  single failure file for provider and firm");
						}
						if ((null != successRecordsForFirm && successRecordsForFirm.size() > 0)
								||(null!=successRecordsForProvider && successRecordsForProvider.size() > 0)) {
							logger.info("started creating succes excel file for firm and Provider");
							//fileName=FilenameUtils.removeExtension(fileName);
							logSuccessAccounts(successRecordsForFirm,successRecordsForProvider,fileNameWithoutExtension);
						}	
						//Move the file to archive folder irrespective of the response
						logger.info("Moving input file to archieve folder");
						moveFileToDirectory(fileEntry, archiveFolder);
					}else{
						 errorVO.setSheetName(fileName);
						 if(null==firmSheet){
							 errors.add(INVALID_FIRM_SHEET + firmSheetName);
						 }
						  if(null==providerSheet){
							  logger.info("crew sheet name:"+crewSheetName);
							 errors.add(INVALID_PROVIDER_SHEET + crewSheetName);	 
						 }
						 errorVO.setErrorList(errors);
				         errorVOList.add(errorVO);
				         if (null != errorVOList && errorVOList.size() > 0) {
								logger.info("started creating failure excel file for firm");
							    //fileName=FilenameUtils.removeExtension(fileName);
								logAccountFailures(errorVOList, fileName);
								//Move the file to archive folder irrespective of the response
							}
				         logger.info("Moving input file to archieve folder");
						 moveFileToDirectory(fileEntry, archiveFolder);
				  }
				}
			  }else{
				  continue;
			  }
			} catch (FileNotFoundException fileNotFound) {
				logger.info("File not found Exception"+ fileNotFound.getMessage());
				fileNotFound.printStackTrace();

			} catch (IOException io) {
				logger.info("Io exception" + io.getMessage());
				io.printStackTrace();
			} catch (Exception e) {
				logger.info("Someother exception other than filenot found"+ e.getMessage());
				e.printStackTrace();
			}

		}
	}

	private void moveFileToDirectory(File sourceFile, File destinationDir) {
		Date date=Calendar.getInstance().getTime();	
		try{
			if(sourceFile.exists()){
				logger.info("source file exists");
			}
			if(destinationDir.exists()){
				 logger.info("Target directory exists");
				 File sourceInTarget=new File(destinationDir.getAbsolutePath()+"/"+sourceFile.getName());
				 if(sourceInTarget.exists()){
				 logger.info("File with name "+sourceFile.getName()+" already exists.Renaming the file.");
				 String newFilePath = sourceFile.getAbsolutePath().replace
				         (sourceFile.getName(), FilenameUtils.removeExtension(sourceFile.getName())+date.getTime()+".xlsx");
				 File newFile = new File(newFilePath);
				 try {
					    FileUtils.moveFile(sourceFile, newFile);
					  } catch (IOException e) {
					    e.printStackTrace();
					  }
					  FileUtils.moveFileToDirectory(newFile, destinationDir,false);
			}else{
				  logger.info("No need to rename,duplicate file does not exist");
				  FileUtils.moveFileToDirectory(sourceFile, destinationDir,false);
			}
			}else{
				logger.info("Target Directory not exist,creating new one and moving file");
			    FileUtils.moveFileToDirectory(sourceFile, destinationDir, true);
			    }
			}
		catch (IOException exception ) {
			exception.printStackTrace();
			logger.info("Exception in moving file to archieve folder");
		}
		
	}

	

	// Gets the sheet with Firm information
	public void ValidateAndCreateStubAccountForFirm(Workbook workbook,Sheet sheet, ProviderRegistrationVO registrationVO, String fileName,List<SHIPErrorVO> errorVOList,List<ProviderRegistrationVO>successRecordsForFirm )throws Exception {
		String firmSheetName = providerRegistrationService.getApplicationProperty(FIRM_SHEET_NAME);
		sheet = (Sheet) workbook.getSheet(firmSheetName);
		SHIPErrorVO errorVO = new SHIPErrorVO();
		Cell cell=null;
		String cellValue=null;
		Date cellValueDate=null;
		/*This method check the existence firm datas in work book
		 * Not tested properly.Hence commenting out
		 * 
		    if(null==sheet){
		         errorVO.setSheetName("Firm Data");
		         errors.add("There is no Firm Data sheet in file");
		         errorVO.setErrorList(errors);
		         errorVOList.add(errorVO);
		 }
		  */
		if (null != sheet) {
			rowCount = sheet.getPhysicalNumberOfRows()-1;
			logger.info("Row count for firm from physical no of rows : " + rowCount);
			/*Row index starts from 1 to exclude header row
			 * row 0:Header rows
			 * row 1:Data rows*/
			/*This method will handle if we delete any rows from excel.*/
			int notNullCountFirm = 0;
			for (Row row : sheet) {
			    for (Cell cellCheck : row) {
			        if (cellCheck.getCellType() != Cell.CELL_TYPE_BLANK) {
			            if (cellCheck.getCellType() != Cell.CELL_TYPE_STRING ||
			            		cellCheck.getStringCellValue().length() > 0) {
			                notNullCountFirm++;
			                break;
			            }
			        }
			    }
			}
			logger.info("Row count for firm from not null no of rows : " + notNullCountFirm);
			rowCount=notNullCountFirm-1;
			for (int row =0; row < rowCount; row++) {
				registrationVO = new ProviderRegistrationVO();
				Row rowObj = sheet.getRow(row+1);
				if(null!=rowObj){
				cell = rowObj.getCell(2);
				if(null!=cell){
				   cell.setCellType(Cell.CELL_TYPE_STRING);
				   cellValue = cell.getStringCellValue();
				   registrationVO.setSubContractorIdVal(cellValue);
				}else{
				   registrationVO.setSubContractorIdVal(DEFAULT_CELL_VALUE);
				}
				cell = rowObj.getCell(3);
				if(null!=cell){
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cellValue = cell.getStringCellValue();
					registrationVO.setFirstName(cellValue);
					
					//R11_2
					//SL-20421
					if(StringUtils.isNotBlank(registrationVO.getFirstName())){
						registrationVO.setFirstName(registrationVO.getFirstName().trim());
					}
				}else{
					registrationVO.setFirstName(DEFAULT_CELL_VALUE);	
				}

				cell = rowObj.getCell(4);
				if(null!=cell){
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cellValue = cell.getStringCellValue();
					registrationVO.setMiddleName(cellValue);
					
					//R11_2
					//SL-20421
					if(StringUtils.isNotBlank(registrationVO.getMiddleName())){
						registrationVO.setMiddleName(registrationVO.getMiddleName().trim());
					}
				}else{
					registrationVO.setMiddleName(DEFAULT_CELL_VALUE);
				}

				cell = rowObj.getCell(5);
				if(null!=cell){
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cellValue = cell.getStringCellValue();
					registrationVO.setLastName(cellValue);
					
					//R11_2
					//SL-20421
					if(StringUtils.isNotBlank(registrationVO.getLastName())){
						registrationVO.setLastName(registrationVO.getLastName().trim());
					}
				}else{
					registrationVO.setLastName(DEFAULT_CELL_VALUE);
					}

				cell = rowObj.getCell(6);
				if(null!=cell){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
				registrationVO.setBusinessStreet1(cellValue);
				}else{
					registrationVO.setBusinessStreet1(DEFAULT_CELL_VALUE);
				}

				cell = rowObj.getCell(7);
				if(cell!=null){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
				registrationVO.setBusinessStreet2(cellValue);
				}else{
					registrationVO.setBusinessStreet2(DEFAULT_CELL_VALUE);
				}
              
				cell = rowObj.getCell(8);
				if(null!=cell){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
				registrationVO.setBusinessCity(cellValue);
				}else{
					registrationVO.setBusinessCity(DEFAULT_CELL_VALUE);
				}

				cell = rowObj.getCell(9);
				if(null!=cell){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
				registrationVO.setBusinessState(cellValue);
				}else{
				registrationVO.setBusinessState(DEFAULT_CELL_VALUE);	
				}

				cell = rowObj.getCell(10);
				if(null!=cell){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
				registrationVO.setBusinessZip(cellValue);
				}else{
					registrationVO.setBusinessZip(DEFAULT_CELL_VALUE);
				}

				cell = rowObj.getCell(11);
				if(null!=cell){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
				registrationVO.setEmail(cellValue);
				}else{
					registrationVO.setEmail(DEFAULT_CELL_VALUE);
				}

				cell = rowObj.getCell(12);
				if(null!=cell){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
				registrationVO.setMainBusiPhoneNoVal(cellValue);
				}else{
					registrationVO.setMainBusiPhoneNoVal(DEFAULT_CELL_VALUE);
				}

				cell = rowObj.getCell(14);
				if(null!=cell){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
				registrationVO.setMainBusiMobileNoVal(cellValue);
				}else{
					registrationVO.setMainBusiMobileNoVal(DEFAULT_CELL_VALUE);
				}

				cell = rowObj.getCell(16);
				if(null!=cell){
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cellValue = cell.getStringCellValue();
					if(StringUtils.isBlank(cellValue) && (!StringUtils.isBlank(registrationVO.getFirstName()) && !StringUtils.isBlank(registrationVO.getLastName()))){
						String middleName = "";
						if(!StringUtils.isBlank(registrationVO.getMiddleName())){
							middleName = registrationVO.getMiddleName() + DOT + SPACE;
						}
						
						registrationVO.setDBAName(registrationVO.getFirstName() + SPACE + middleName + registrationVO.getLastName());
					}else if(!StringUtils.isBlank(cellValue)){
						registrationVO.setDBAName(cellValue);
					}
				}
				else{
					if(!StringUtils.isBlank(registrationVO.getFirstName()) && !StringUtils.isBlank(registrationVO.getLastName())){
						String middleName = "";
						if(!StringUtils.isBlank(registrationVO.getMiddleName())){
							middleName = registrationVO.getMiddleName() + DOT + SPACE;
						}
						registrationVO.setDBAName(registrationVO.getFirstName() + SPACE + middleName + registrationVO.getLastName());
					}else{
						registrationVO.setDBAName(DEFAULT_CELL_VALUE);
					}
				}

				cell = rowObj.getCell(18);
				if(null!=cell){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
				registrationVO.setPrimaryIndustry(cellValue);
				}else{
					registrationVO.setPrimaryIndustry(DEFAULT_CELL_VALUE);
				}
                /*This datas are fetched for credential information
                 * */
				cell=rowObj.getCell(39);
				if(null!=cell){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
				registrationVO.setlInsuranceCompany(cellValue);
				}else{
					registrationVO.setlInsuranceCompany(DEFAULT_CELL_VALUE);	
				}
				cell=rowObj.getCell(40);
				if(null!=cell){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
				registrationVO.setlPolicyNumberVal(cellValue);
				}else{
				    registrationVO.setlPolicyNumberVal(DEFAULT_CELL_VALUE);	
				}
				cell=rowObj.getCell(41);
				
				if(null!=cell){
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					cellValueDate = cell.getDateCellValue();
					if(null != cellValueDate){
						registrationVO.setlExpirationDate(cellValueDate);
					}else{
						registrationVO.setlExpirationDate(DEFAULT_DATE);	
					}
				}else{
					registrationVO.setlExpirationDate(DEFAULT_DATE);	
				}
				
				/*
				if(null!=cell){
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cellValue=cell.getStringCellValue();
					if(StringUtils.isNotBlank(cellValue)){
						
						logger.info("cellvalue liability "+ cellValue);
						cellValueDate = validateDateValue(cellValue);
						logger.info("cellvaluedate liability "+ cellValueDate);
						if(null != cellValueDate){
							registrationVO.setlExpirationDate(cellValueDate);
						}else{
							registrationVO.setlExpirationDate(DEFAULT_DATE);
						}
					}else{
						registrationVO.setlExpirationDate(DEFAULT_DATE);
					}

				}else{
					registrationVO.setlExpirationDate(DEFAULT_DATE);	
					logger.info("Is Null Liability cellValueDate :"+ registrationVO.getlExpirationDate());
				}
				*/ 
				cell=rowObj.getCell(42);
				if(null!=cell){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
				registrationVO.setWcInsuranceCompany(cellValue);
				}else{
					registrationVO.setWcInsuranceCompany(DEFAULT_CELL_VALUE);	
				}
				cell=rowObj.getCell(43);
				if(null!=cell){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
				registrationVO.setWcPolicyNumberVal(cellValue);
				}else{
					registrationVO.setWcPolicyNumberVal(DEFAULT_CELL_VALUE);	
				}
				cell=rowObj.getCell(44);
				
				if(null!=cell){
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					cellValueDate = cell.getDateCellValue();
					if(null != cellValueDate){
						registrationVO.setWcExpirationDate(cellValueDate);
					}else{
						registrationVO.setWcExpirationDate(DEFAULT_DATE);
					}
				}else{
					registrationVO.setWcExpirationDate(DEFAULT_DATE);	
				}
				
				
				/*
				if(null!=cell){
					cell.setCellType(Cell.CELL_TYPE_STRING);
					if(StringUtils.isNotBlank(cell.getStringCellValue())){
						cellValue = cell.getStringCellValue();
						cellValueDate = validateDateValue(cellValue);
						if(null != cellValueDate){
							registrationVO.setWcExpirationDate(cellValueDate);
						}else{
							registrationVO.setWcExpirationDate(DEFAULT_DATE);
						}
					}else{
						registrationVO.setWcExpirationDate(DEFAULT_DATE);
					}

				}else{
					registrationVO.setWcExpirationDate(DEFAULT_DATE);	
					logger.info("Is Null WC cellValueDate :"+ registrationVO.getWcExpirationDate());
				}
				*/
				cell=rowObj.getCell(46);
				if(null!=cell){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
				registrationVO.setaInsuranceCompany(cellValue);
				}else{
					registrationVO.setaInsuranceCompany(DEFAULT_CELL_VALUE);	
				}
				cell=rowObj.getCell(47);
				if(null!=cell){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
				registrationVO.setaPolicyNumberVal(cellValue);
				}else{
					registrationVO.setaPolicyNumberVal(DEFAULT_CELL_VALUE);	
				}
				cell=rowObj.getCell(48);
				if(null!=cell){
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					cellValueDate = cell.getDateCellValue();
					if(null != cellValueDate){
						registrationVO.setaExpirationDate(cellValueDate);
					}else{
						registrationVO.setaExpirationDate(DEFAULT_DATE);
					}

				}else{
					registrationVO.setaExpirationDate(DEFAULT_DATE);	
					logger.info("Is Null cellValueDate :"+ registrationVO.getaExpirationDate());
				}
				/*
				if(null!=cell){
					cell.setCellType(Cell.CELL_TYPE_STRING);
					if(StringUtils.isNotBlank(cell.getStringCellValue())){
						cellValue = cell.getStringCellValue();
						
						cellValueDate = validateDateValue(cellValue);
						if(null != cellValueDate){
							registrationVO.setaExpirationDate(cellValueDate);
						}else{
							registrationVO.setaExpirationDate(DEFAULT_DATE);
						}
					}else{
						registrationVO.setaExpirationDate(DEFAULT_DATE);
					}

				}else{
					registrationVO.setaExpirationDate(DEFAULT_DATE);	
					logger.info("Is Null Auto cellValueDate :"+ registrationVO.getaExpirationDate());
				}
				*/
				
				// Validates data for firm
				List<String> errorList = validateFieldsForFirm(registrationVO);
				ProviderRegistrationVO response = new ProviderRegistrationVO();
				if (errorList.size() > 0) {
					// Sets the information regarding validation errors.
					for(int i=0;i<errorList.size();i++){
					logger.info("Errors from Firm are:"+ errorList.get(i));
					}
					errorVO = new SHIPErrorVO();
					errorVO.setSheetName(FIRM_DATA);
					errorVO.setRowNo(row+1);
					errorVO.setFirstName(registrationVO.getFirstName());
					errorVO.setLastName(registrationVO.getLastName());
					errorVO.setErrorList(errorList);
					errorVOList.add(errorVO);
				} else {
					logger.info("Registering as a firm");
					response = providerRegistrationService.doRegisterFirm(registrationVO);
				 if (null != response&& null != response.getVendorContactResourceId()) {
					    logger.info("Inserting various firm details into ship_subcontractor_map table");
						providerRegistrationService.insertSubContractorInfo(response);
						successRecordsForFirm.add(response);
					}
				}
			}
		  }
		}
	  }
	
    /*
	private Date validateDateValue(String cellValue) {
		// TODO Auto-generated method stub
		 SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy hh:mm"); 
		 SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy"); 
		 Date newDate = null; 
		 try { 
			 System.out.println("inside method "+cellValue);
             newDate = (format1.parse(cellValue)); 
             return newDate;  
         }catch (ParseException exception) { 
        	 logger.info("ParseException1 for value : "+ cellValue);
             // Not in proper format 
             try { 
                     newDate = (format2.parse(cellValue)); 
                     return newDate;  
                     } catch (ParseException e) { 
                    	    logger.info("ParseException2 for value : "+ cellValue);
                             // TODO Auto-generated catch block 
                             return DEFAULT_DATE;
                     } 
         }catch (Exception exception) { 
        	 logger.info("Exception for value : "+ cellValue);
             return DEFAULT_DATE; 
         } 
 
	}
   */
	// Gets the sheet with Provider tech information
	public void ValidateAndCreateStubAccountForProvider(Workbook workbook,Sheet sheet, ProviderRegistrationVO registrationVO, String fileName,List<SHIPErrorVO> errorVOList, List<ProviderRegistrationVO> successRecordsForProvider)throws Exception {
		String crewSheetName = providerRegistrationService.getApplicationProperty(CREW_SHEET_NAME);
		sheet = (Sheet) workbook.getSheet(crewSheetName);
		SHIPErrorVO errorVO = new SHIPErrorVO();
		//	List<SHIPErrorVO> errorVOList = new ArrayList<SHIPErrorVO>();
		/*This method check the existence provider datas in work book
		 * Not tested properly.Hence commenting out
		 * 
		    if(null==sheet){
		         errorVO.setSheetName("provider Data");
		         errors.add("There is no Provider Data sheet in file");
		         errorVO.setErrorList(errors);
		         errorVOList.add(errorVO);
		 }
		  */
		Cell cell=null;
		String cellValue=null;
		if (null != sheet) {
			
			/*Row index starts from 1 to exclude header row
			 * row 0:Header rows
			 * row 1:Data rows*/
			rowCount = sheet.getPhysicalNumberOfRows()-1;
			logger.info("Row count for provider from physical No : "+ rowCount);
			int notNullCountProvider = 0;
			for (Row row : sheet) {
			    for (Cell cellCheck : row) {
			        if (cellCheck.getCellType() != Cell.CELL_TYPE_BLANK) {
			            if (cellCheck.getCellType() != Cell.CELL_TYPE_STRING ||
			            		cellCheck.getStringCellValue().length() > 0) {
			            	notNullCountProvider++;
			                break;
			            }
			        }
			    }
			}
			rowCount=notNullCountProvider-1;
			logger.info("Row count for provider from not null check : " + rowCount);
			for (int row =0; row < rowCount; row++) {
				registrationVO = new ProviderRegistrationVO();
				Row rowObj = sheet.getRow(row+1);
                if(null!=rowObj){          
				cell = rowObj.getCell(1);
				if(null!=cell){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
				registrationVO.setSubContractorIdVal(cellValue);
				}else{
					registrationVO.setSubContractorIdVal(DEFAULT_CELL_VALUE);
				}
				cell = rowObj.getCell(2);
				if(null!=cell){
				cell.setCellType(Cell.CELL_TYPE_STRING);
				cellValue = cell.getStringCellValue();
				registrationVO.setCrewVal(cellValue);
				}else{
					registrationVO.setCrewVal(DEFAULT_CELL_VALUE);
				}

				cell = rowObj.getCell(3);
				if(null!=cell){
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cellValue = cell.getStringCellValue();
					registrationVO.setLastName(cellValue);
					
					//R11_2
					//SL-20421
					if(StringUtils.isNotBlank(registrationVO.getLastName())){
						registrationVO.setLastName(registrationVO.getLastName().trim());
					}
				}else{
					registrationVO.setLastName(DEFAULT_CELL_VALUE);
				}

				cell = rowObj.getCell(4);
				if(null!=cell){
					cell.setCellType(Cell.CELL_TYPE_STRING);
					cellValue = cell.getStringCellValue();
					registrationVO.setFirstName(cellValue);
					
					//R11_2
					//SL-20421
					if(StringUtils.isNotBlank(registrationVO.getFirstName())){
						registrationVO.setFirstName(registrationVO.getFirstName().trim());
					}
				}else{
					registrationVO.setFirstName(DEFAULT_CELL_VALUE);
				}

				List<String> errorList = validateFieldsForResource(registrationVO);
				ProviderRegistrationVO response = new ProviderRegistrationVO();
				if (errorList.size() > 0) {
					for(int i=0;i<errorList.size();i++){
						logger.info("Errors From Providers are:"+ errorList.get(i));
						}
					errorVO = new SHIPErrorVO();
					errorVO.setSheetName(PROVIDER_DATA);
					errorVO.setRowNo(row + 1);
					errorVO.setFirstName(registrationVO.getFirstName());
					errorVO.setLastName(registrationVO.getLastName());
					errorVO.setErrorList(errorList);
					errorVOList.add(errorVO);
				} else {
					logger.info("Regestering as a provider");
					response = providerRegistrationService.doRegisterResource(registrationVO);
				if (null != response&& null != response.getVendorContactResourceId()) {
					logger.info("Inserting datas of provider into ship_subcontractor_map table");
					providerRegistrationService.insertSubContractorInfo(response);
					successRecordsForProvider.add(response);
					}
				}
             }
		  }     
		}
		
	}

	/*
	 * Validates data for Firm
	 */
	private List<String> validateFieldsForFirm(
			ProviderRegistrationVO registrationVO) {
        logger.info("Started validation for firm");
		List<String> errorList = new ArrayList<String>();
		validateManadatoryFieldsForFirm(errorList, registrationVO);
		validateFieldLength(errorList, registrationVO);
		//Validate fields for insurance Details
		validateInsuranceDetailsForFirm(errorList, registrationVO);
		if (!StringUtils.isBlank(registrationVO.getPrimaryIndustry())) {
			validatePrimaryIndustry(errorList, registrationVO);
		}
		  //Validating Business State
		 if(!StringUtils.isBlank(registrationVO.getBusinessState())){
	        	validateBusinessState(errorList, registrationVO);
	        }
		// also handle condition if any one of these is not present
		if (!StringUtils.isBlank(registrationVO.getBusinessZip())
				&& !StringUtils.isBlank(registrationVO.getBusinessState())) {
			validateZipState(errorList, registrationVO);
		}
        if(!StringUtils.isBlank(registrationVO.getEmail())){
        	if(!validateEmail(registrationVO.getEmail())){
        	errorList.add(INVALID_EMAIL_FORMAT);
        	}
        }
        /*This validation is to prevent duplicate entry of firm in servicelive
         *  with same subcontractorId, by querying ship_subcontractor_map
         * */
        if(!StringUtils.isBlank(registrationVO.getSubContractorIdVal())){
        	validateSubcontractorIdForFirm(errorList, registrationVO);
        	}
      
       
		return errorList;
	}

	

	private void validateInsuranceDetailsForFirm(List<String> errorList,ProviderRegistrationVO registrationVO){
		if(!StringUtils.isBlank(registrationVO.getlInsuranceCompany()) 
				 && registrationVO.getlInsuranceCompany().trim().length() > 100 ){
			errorList.add(INVALID_GL_INSURANCE_LENGTH);
			}
		if(!StringUtils.isBlank(registrationVO.getWcInsuranceCompany()) 
				 && registrationVO.getWcInsuranceCompany().trim().length() > 100 ){
			errorList.add(INVALID_WC_INSURANCE_LENGTH);
			}
		if(!StringUtils.isBlank(registrationVO.getaInsuranceCompany()) 
				 && registrationVO.getaInsuranceCompany().trim().length() > 100 ){
			errorList.add(INVALID_AO_INSURANCE_LENGTH);
			}
		logger.info("Liability Expiration Date:"+ registrationVO.getlExpirationDate());
	    if(null != registrationVO.getlExpirationDate()){
			int expirationDate=validateExpirationDate(registrationVO.getlExpirationDate());
			logger.info("Liability Expiration Date:"+ expirationDate);
			if(expirationDate < 0){
				//errorList.add(INVALID_GL_EDATE_FORMATE);
				registrationVO.setlExpirationDate(null);
			}
			else if(expirationDate >= 0){
				registrationVO.setlExpirationDate(registrationVO.getlExpirationDate());
			}
		}else if(null == registrationVO.getlExpirationDate()){
			logger.info("Liability Expiration Date is null or blank:");
			registrationVO.setlExpirationDate(null);
		}
			
		logger.info("Workmans Expiration Date:"+ registrationVO.getWcExpirationDate());
		if( null !=registrationVO.getWcExpirationDate()){
			int expirationDate=validateExpirationDate(registrationVO.getWcExpirationDate());
			logger.info("Workmans Expiration Date:"+ expirationDate);
			if(expirationDate < 0){
				//errorList.add(INVALID_WC_EDATE_FORMATE);
				registrationVO.setWcExpirationDate(null);
			}
			else if(expirationDate >= 0){
				registrationVO.setWcExpirationDate(registrationVO.getWcExpirationDate());
			}
		}else if(null == registrationVO.getWcExpirationDate()){
			logger.info("Workmans Expiration Date is null or blank:");
			registrationVO.setWcExpirationDate(null);
		}
		logger.info("Auto Expiration Date:"+ registrationVO.getaExpirationDate());
		if( null !=registrationVO.getaExpirationDate()){
			int expirationDate=validateExpirationDate(registrationVO.getaExpirationDate());
			logger.info("Auto Expiration Date:"+ expirationDate);
			if(expirationDate < 0){
				//errorList.add(INVALID_AO_EDATE_FORMATE);
				registrationVO.setaExpirationDate(null);
			}
			else if(expirationDate>=0){
				registrationVO.setaExpirationDate(registrationVO.getaExpirationDate());
			}
		}else if(null == registrationVO.getaExpirationDate()){
			logger.info("Auto Expiration Date is null or blank:");
			registrationVO.setaExpirationDate(null);
		}
	  }
	
    
	private int validateExpirationDate(Date date) {
		int i=-1;
		logger.info("Value for Date is:"+ date);
		Date dateTobeComparedWith = null;
	    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy"); 
		try {
		 dateTobeComparedWith = format.parse(DEFAULT_EXP_DATE);
		 i = date.compareTo(dateTobeComparedWith);	
		} catch (Exception e) {
			logger.info("Exception in comparing Date : " + date);
		}
		return i;
	}
	



	private void validateBusinessState(List<String> errorList,
			ProviderRegistrationVO registrationVO) {
		String stateList=null;
		stateList=providerRegistrationService.getValidBusinessStateCd(registrationVO);
		if(null==stateList){
			errorList.add(INVALID_BUSINESS_STATE);
		}
		
	}

	/*
	 * Validates data for Provider resource
	 */
	private List<String> validateFieldsForResource(
			ProviderRegistrationVO registrationVO) {

		List<String> errorList = new ArrayList<String>();
		validateManadatoryFieldsForResource(errorList, registrationVO);
		validateFieldLengthForResource(errorList, registrationVO);
		if (null != registrationVO.getSubContractorId() &&
				StringUtils.isNotBlank(registrationVO.getSubContractorId().toString())) {
			validateSubContractorForResource(errorList, registrationVO);
		}
		if(null != registrationVO.getSubContractorCrewId() &&
				StringUtils.isNotBlank(registrationVO.getSubContractorCrewId().toString())){
			validateSubcontractorCrewIdForResource(errorList, registrationVO);
		}
		return errorList;
	}

	

	/*
	 * Validates mandatory fields for Provider resource
	 */
	private void validateManadatoryFieldsForResource(List<String> errorList,
			ProviderRegistrationVO registrationVO) {
        
		logger.info("Validating Mandatory Fields for provider");
        boolean subContractorError  = false;
        boolean subContractorCrewIdError = false;
        if (StringUtils.isBlank(registrationVO.getSubContractorIdVal())) {
        	subContractorError = true;
 		} else {
			try {
				registrationVO.setSubContractorId(Integer
						.parseInt(registrationVO.getSubContractorIdVal()));
			} catch (NumberFormatException e) {
				subContractorError = true;
				logger.error("SubContractorId is not a number");
			}
		}
		if (StringUtils.isBlank(registrationVO.getCrewVal())) {
			subContractorCrewIdError = true;
		} else {
			try {
				registrationVO.setSubContractorCrewId(Integer
						.parseInt(registrationVO.getCrewVal()));
			} catch (NumberFormatException e) {
				subContractorCrewIdError = true;
				logger.error("SubContractor Crew Id is not a number");
			}
		}
		if(subContractorError){
	        errorList.add(INVALID_SUBCONTRACTOR_ID);
	    }
		if(subContractorCrewIdError){
			errorList.add(INVALID_SUBCONTRACTOR_CREW_ID);
		}
		if (StringUtils.isBlank(registrationVO.getFirstName())) {
			errorList.add(INVALID_FIRST_NAME_RESOURCE);
		}
		if (StringUtils.isBlank(registrationVO.getLastName())) {
			errorList.add(INVALID_LAST_NAME_RESOURCE);
		}
	}

	/*
	 * Validates mandatory field for Firm
	 */
	private void validateManadatoryFieldsForFirm(List<String> errorList,
			ProviderRegistrationVO registrationVO) {
            logger.info("Validating mandatory fields for firm");
          if (StringUtils.isBlank(registrationVO.getSubContractorIdVal())) {
			errorList.add(INVALID_SUBCONTRACTOR_ID);
		} else {
			try {
				registrationVO.setSubContractorId(Integer
						.parseInt(registrationVO.getSubContractorIdVal()));
			} catch (NumberFormatException e) {
				logger.error("SubContractorId is not a number");
			}
			if (null == registrationVO.getSubContractorId()) {
				errorList.add(INVALID_SUBCONTRACTOR_ID);
			}
		}
		
		if (StringUtils.isBlank(registrationVO.getFirstName())) {
			errorList.add(INVALID_FIRST_NAME);
		}
		if (StringUtils.isBlank(registrationVO.getLastName())) {
			errorList.add(INVALID_LAST_NAME);
		}
		if (StringUtils.isBlank(registrationVO.getEmail())) {
			errorList.add(INVALID_EMAIL_ID );
		}
		if (StringUtils.isBlank(registrationVO.getDBAName()) && (StringUtils.isBlank(registrationVO.getFirstName()) || StringUtils.isBlank(registrationVO.getLastName()))) {
			errorList.add(INVALID_DOING_BUSINESS_AS);
		}
		if (StringUtils.isBlank(registrationVO.getBusinessState())) {
			errorList.add(INVALID_STATE);
		}
	}

	/*
	 * Validates field length for Resource data
	 */
	private void validateFieldLengthForResource(List<String> errorList,
			ProviderRegistrationVO registrationVO) {
        logger.info("Validating Field length for provider");
		if (!StringUtils.isBlank(registrationVO.getFirstName())
				&& (registrationVO.getFirstName().trim().length() > 50)) {
			errorList
					.add(INVALID_FIRST_NAME_LENGTH);
		}
		if (!StringUtils.isBlank(registrationVO.getLastName())
				&& (registrationVO.getLastName().trim().length() > 50)) {
			errorList
					.add(INVALID_LAST_NAME_LENGTH);
		}

	}

	/*
	 * Validates field length for Firm data
	 */
	private void validateFieldLength(List<String> errorList,
			ProviderRegistrationVO registrationVO) {
        logger.info("Validating field length for firm");
		if (!StringUtils.isBlank(registrationVO.getFirstName())
				&& (registrationVO.getFirstName().trim().length() > 50)) {
			errorList
					.add(INVALID_FIRST_NAME_LENGTH);
		}
		if (!StringUtils.isBlank(registrationVO.getLastName())
				&& (registrationVO.getLastName().trim().length() > 50)) {
			errorList
					.add(INVALID_LAST_NAME_LENGTH );
		}
		if (!StringUtils.isBlank(registrationVO.getMiddleName())
				&& (registrationVO.getMiddleName().trim().length() > 50)) {
			errorList
					.add(INVALID_MIDDLE_NAME_LENGTH);
		}
		if (!StringUtils.isBlank(registrationVO.getEmail())
				&& (registrationVO.getEmail().trim().length() > 50)) {
			errorList.add(INVALID_EMAIL_FIELD_LENGTH);
		}
		if (!StringUtils.isBlank(registrationVO.getBusinessStreet1())
				&& (registrationVO.getBusinessStreet1().trim().length() > 40)) {
			errorList
					.add(INVALID_ADDRESS1_LENGTH);
		}
		if (!StringUtils.isBlank(registrationVO.getBusinessStreet2())
				&& (registrationVO.getBusinessStreet2().trim().length() > 40)) {
			errorList
					.add(INVALID_ADDRESS2_LENGTH);
		}
		if (!StringUtils.isBlank(registrationVO.getBusinessCity())
				&& (registrationVO.getBusinessCity().trim().length() > 30)) {
			errorList.add(INVALID_CITY_LENGTH);
		}
		if (!StringUtils.isBlank(registrationVO.getDBAName())
				&& (registrationVO.getDBAName().trim().length() > 30)) {
		    logger.info("registrationVO.getDBAName() - length : " + registrationVO.getDBAName().trim().length());
			errorList
					.add(INVALID_DBA_LENGTH);
		}
		if(!StringUtils.isBlank(registrationVO.getMainBusiMobileNoVal())){
			logger.info("Business Mobile No:"+registrationVO.getMainBusiMobileNoVal());
			try{
				registrationVO.setMainBusiMobileNo(Long.parseLong(registrationVO.getMainBusiMobileNoVal()));
			}catch (NumberFormatException nfe) {
				errorList.add(INVALID_CELL_NO);
			}
		}
		if(null!=registrationVO.getMainBusiMobileNo()
				&& (registrationVO.getMainBusiMobileNo().toString().trim().length()!=10)){
			errorList
			        .add(INVALID_CELL_NO_LENGTH);
			
		}
		if(!StringUtils.isBlank(registrationVO.getMainBusiPhoneNoVal())){
			logger.info("Business Mobile Phone No: " + registrationVO.getMainBusiPhoneNoVal());
			try{
				registrationVO.setMainBusiPhoneNo(Long.parseLong(registrationVO.getMainBusiPhoneNoVal()));
			}catch (NumberFormatException nfe) {
				errorList
				     .add(INVALID_HOME_NO );
			}
		}
		if( null != registrationVO.getMainBusiPhoneNo()
				&& (registrationVO.getMainBusiPhoneNo().toString().trim().length()!=10)){
			errorList
			        .add(INVALID_HOME_NO_LENGTH);
			
		}
	}
	/**
	 * The method is used to validate email
	 * 
	 * @param email:
	 *            the email to be validated
	 * @return boolean: returns true if valid email id or else returns false
	 */

	public boolean validateEmail(String email) {
		boolean valResult = false;
		if (email != null && email.length() > 1 && email.length() < 255) {
			// Set the email pattern string
			Pattern p = Pattern
					.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[_A-Za-z0-9-]+)");
			// Match the given string with the pattern
			Matcher m = p.matcher(email);
			valResult = m.matches();
		}
		return valResult;
	}
	/*
	 * Method to log details of successful accounts created
	 */
	private void logSuccessAccounts(
			List<ProviderRegistrationVO> successRecordsForFirm,List<ProviderRegistrationVO> successRecordsForProvider, String fileName) {
		String folderName = providerRegistrationService.getApplicationProperty(SHIP_SUCCESS_DIRECTORY);
		Date date=Calendar.getInstance().getTime();
		FileOutputStream fileOutputStream = null;
		File successLog = new File(folderName + "/"+ fileName+date.getTime() +SUCCESS_FILE);
		logger.info("Size of success records for firm is:"+successRecordsForFirm.size());
		logger.info("Size of success records for provider is:"+successRecordsForProvider.size());
		try {
			fileOutputStream = new FileOutputStream(successLog);
			// Create Sheet.
			logger.info("Started Logging sucess file for FIRM");
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
			XSSFSheet firmSheet = xssfWorkbook.createSheet(FIRM_SUCCESS_SHEET);
			firmSheet.setDefaultColumnWidth(30);
			XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
			dataStyle.setWrapText(true);
			// Create Header Row
			XSSFRow headerRow = firmSheet.createRow(0);
			XSSFCell headerCell1 = headerRow.createCell(0);
			headerCell1.setCellValue(USER_NAME);
			XSSFCell headerCell2 = headerRow.createCell(1);
			headerCell2.setCellValue(PASSWORD);
			XSSFCell headerCell3 = headerRow.createCell(2);
			headerCell3.setCellValue(EMAIL_ID);
			if (null != successRecordsForFirm) {
				for (int i = 0; i < successRecordsForFirm.size(); i++) {
					ProviderRegistrationVO row = successRecordsForFirm.get(i);
					// Create Data Row
					XSSFRow dataRow = firmSheet.createRow(i + 1);
					XSSFCell cell1 = dataRow.createCell(0);
					cell1.setCellStyle(dataStyle);
					cell1.setCellValue(row.getUserName());
					XSSFCell cell2 = dataRow.createCell(1);
					cell2.setCellStyle(dataStyle);
					cell2.setCellValue(row.getPassword());
					XSSFCell cell3 = dataRow.createCell(2);
					cell3.setCellStyle(dataStyle);
					cell3.setCellValue(row.getEmail());
					}
				}
			logger.info("Started Logging sucess file for Provider");
			XSSFSheet providerSheet = xssfWorkbook.createSheet(PROVIDER_SUCCESS_SHEET);
			providerSheet.setDefaultColumnWidth(30);
			dataStyle = xssfWorkbook.createCellStyle();
			dataStyle.setWrapText(true);
			XSSFRow headerRow1 = providerSheet.createRow(0);
			XSSFCell headerCell11 = headerRow1.createCell(0);
			headerCell11.setCellValue(FIRST_NAME);
			XSSFCell headerCell21 = headerRow1.createCell(1);
			headerCell21.setCellValue(LAST_NAME );
			XSSFCell headerCell31 = headerRow1.createCell(2);
			headerCell31.setCellValue(SUBCONTRACTOR_ID);
			if(null!= successRecordsForProvider){
				for (int i = 0; i < successRecordsForProvider.size(); i++) {
					ProviderRegistrationVO row = successRecordsForProvider.get(i);
					// Create Data Row
					XSSFRow dataRow = providerSheet.createRow(i + 1);
					XSSFCell cell1 = dataRow.createCell(0);
					cell1.setCellStyle(dataStyle);
					cell1.setCellValue(row.getFirstName());
					XSSFCell cell2 = dataRow.createCell(1);
					cell2.setCellStyle(dataStyle);
					cell2.setCellValue(row.getLastName());
					XSSFCell cell3 = dataRow.createCell(2);
					cell3.setCellStyle(dataStyle);
					cell3.setCellValue(row.getSubContractorId());
					}
				}
			xssfWorkbook.write(fileOutputStream);
			
		} catch (Exception e) {
			logger.info("Exception in logging success file" + e.getMessage());
			e.printStackTrace();
		} finally {
			/*
			 * Close File Output Stream
			 */
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	/*
	 * Method to log details of account failures
	 */
	private void logAccountFailures(List<SHIPErrorVO> errorVOList,String fileName) {
		String folderName = providerRegistrationService.getApplicationProperty(SHIP_FAILURE_DIRECTORY);
		Date date=Calendar.getInstance().getTime();
		FileOutputStream fileOutputStream = null;
		File errorLog = new File(folderName + "/"+ fileName+date.getTime() + FAILURE_FILE);
		try {
			fileOutputStream=new FileOutputStream(errorLog);
			// Create Sheet.
				XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
				XSSFSheet sheet = xssfWorkbook.createSheet(FAILURE_SHEET);
				sheet.setDefaultColumnWidth(30);
				XSSFCellStyle dataStyle = xssfWorkbook.createCellStyle();
				dataStyle.setWrapText(true);
				// Create Header Row
				XSSFRow headerRow = sheet.createRow(0);
				XSSFCell headerCell1 = headerRow.createCell(0);
				headerCell1.setCellValue(SHEET);
				XSSFCell headerCell2 = headerRow.createCell(1);
				headerCell2.setCellValue(ROW_NO);
				XSSFCell headerCell3 = headerRow.createCell(2);
				headerCell3.setCellValue(FIRST_NAME);
				XSSFCell headerCell4 = headerRow.createCell(3);
				headerCell4.setCellValue(LAST_NAME);
			    XSSFCell headerCell5 = headerRow.createCell(4);
				headerCell5.setCellValue(ERROR);
				if(null != errorVOList){
					for(int i=0;i < errorVOList.size();i++){
						SHIPErrorVO row=errorVOList.get(i);
						XSSFRow dataRow = sheet.createRow(i + 1);
						XSSFCell cell1 = dataRow.createCell(0);
						cell1.setCellStyle(dataStyle);
						cell1.setCellValue(row.getSheetName());
						XSSFCell cell2 = dataRow.createCell(1);
						cell2.setCellStyle(dataStyle);
						cell2.setCellValue(row.getRowNo());
						XSSFCell cell3 = dataRow.createCell(2);
						cell3.setCellStyle(dataStyle);
						cell3.setCellValue( null != row.getFirstName() ? row.getFirstName() : "");
						XSSFCell cell4 = dataRow.createCell(3);
						cell4.setCellStyle(dataStyle);
						cell4.setCellValue( null != row.getLastName() ? row.getLastName() : "");
						XSSFCell cell5 = dataRow.createCell(4);
						dataStyle.setWrapText(true);
						//Iterate over errorlist,try to add in single cell
						StringBuilder errors=new StringBuilder();
						if(null!= row.getErrorList()){
					         for(int p=0;p <row.getErrorList().size();p++){
					        	 logger.info("Errors inside writing excel method is:"+ row.getErrorList().get(p));
					        	 errors.append(p+1).append(".").append(row.getErrorList().get(p)).append("\n");
					    	}
					    }
						cell5.setCellStyle(dataStyle);
						cell5.setCellValue(errors.toString());
						
						
					}
					xssfWorkbook.write(fileOutputStream);
				}
				 
			}
		
		catch (Exception e) {
			logger.info("Exception in logging error file" + e.getMessage());
			e.printStackTrace();
		}
		finally {
			/*
			 * Close File Output Stream
			 */
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
}

	

	/*
	 * Validates primary industry of the SHIP Provider. Gets the corresponding
	 * SL primary industry id.
	 */
	private void validatePrimaryIndustry(List<String> errorList,
			ProviderRegistrationVO registrationVO) {
		Integer primaryIndustryId = providerRegistrationService
				.getSLIndustry(registrationVO.getPrimaryIndustry());
		if (null == primaryIndustryId) {
			errorList.add(INVALID_PRODUCT_DESCRIPTION);
		} else {
			registrationVO.setPrimaryIndustry(primaryIndustryId.toString());
		}

	}

	/*
	 * Validates the Sub contractor id For resource
	 */
	private void validateSubContractorForResource(List<String> errorList,
			ProviderRegistrationVO registrationVO) {
		Integer vendorId = providerRegistrationService.getVendorIdForSubContractorForResource(registrationVO.getSubContractorId());
		if (null == vendorId) {
			errorList.add(INVALID_SUBCONTRACTOR_ID_FOR_RESOURCE);
		} else {
			registrationVO.setVendorId(vendorId);
		}

	}
	/*
	 * Validates the subcontractor crew Id for resource
	 */
	private void validateSubcontractorCrewIdForResource(List<String> errorList,
			ProviderRegistrationVO registrationVO) {
	List<Integer> vendorIdList = providerRegistrationService.getVendorIdForSubContractorCrewIdForResource(registrationVO);
	if(null != vendorIdList && vendorIdList.size() > 0){
		errorList.add(INVALID_SUBCONTRACTOR_CREWID1  + registrationVO.getSubContractorCrewId() + 
				INVALID_SUBCONTRACTOR_CREWID2 +  registrationVO.getSubContractorId());
	}
	
	}
	/*Validate subcontractorId for Firm
	 * */
	private void validateSubcontractorIdForFirm(List<String> errorList,ProviderRegistrationVO registrationVO) {
		Integer vendorId = providerRegistrationService.getVendorIdForFirm(registrationVO.getSubContractorId());
		if (null != vendorId) {
			errorList.add(INVALID_SUBCONTRACTOR_ID1  + registrationVO.getSubContractorId() +  INVALID_SUBCONTRACTOR_ID2);
		}
		
	}
	/*
	 * Validate if zip and state are related.
	 */
	private void validateZipState(List<String> errorList,
			ProviderRegistrationVO registrationVO) {
		registrationVO.setStateType("business");
		try {
			registrationBO.loadZipSet(registrationVO);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		List zipList = registrationVO.getStateTypeList();
		if (!validateStateZip(zipList, registrationVO.getBusinessZip())) {
			errorList.add(INVALID_ZIP_CODE);
		}

	}

	private boolean validateStateZip(List list, String zip) {
		Iterator itr = list.iterator();
		while (itr.hasNext()) {
			String validZip = ((LocationVO) itr.next()).getZip();

			if (zip.equals(validZip)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void setArgs(String[] args) {
		// do nothing
	}

	public IRegistrationBO getRegistrationBO() {
		return registrationBO;
	}

	public void setRegistrationBO(IRegistrationBO registrationBO) {
		this.registrationBO = registrationBO;
	}

	public ISHIPProviderRegistrationService getProviderRegistrationService() {
		return providerRegistrationService;
	}

	public void setProviderRegistrationService(
			ISHIPProviderRegistrationService providerRegistrationService) {
		    this.providerRegistrationService = providerRegistrationService;
	}
	


	

}
