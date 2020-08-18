package com.servicelive.routingrulesweb.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.newco.marketplace.auth.SecurityContext;
import com.opensymphony.xwork2.ModelDriven;
import com.servicelive.common.exception.DataNotFoundException;
import com.servicelive.common.properties.IApplicationProperties;
import com.servicelive.domain.buyer.BuyerResource;
import com.servicelive.domain.routingrules.RoutingRuleErrorCause;
import com.servicelive.domain.routingrules.RoutingRuleFileHdr;
import com.servicelive.routingrulesengine.RoutingRulesConstants;
import com.servicelive.routingrulesengine.vo.RoutingRulesFileUploadVO;
import com.servicelive.routingrulesengine.vo.RoutingRulesPaginationVO;

// @NonSecurePage
public class RoutingRulesUploadTabAction  extends RoutingRulesBaseAction	 
		implements ModelDriven<Map<String,Object>>{
	
	Logger logger = Logger.getLogger(RoutingRulesUploadTabAction.class);	
	
	private static final long serialVersionUID = 0L;	
	private static final String DISPLAY_LIST = "displayList";	
	private static final String LIST_ROUTING_RULE_FILE_UPLOAD = "uploadedFileList";
	private static final String UPLOAD_VO = "uploadVO";
	private static final String UPLOAD_VO_MODAL = "uploadVOInModal";
		
	private static String _downloadCreateLink = null;
	private static String _downloadUpdateLink = null;
	private static String _downloadArchiveLink = null;
	
	private static String _pageRefreshTime = null;
		
	private Map<String,Object> model= new HashMap<String,Object>();
	private File uploadFile;
	private String uploadFileName;
	private String uploadFileContentType;
	
	private IApplicationProperties applicationProperties;
	
	public void setApplicationProperties(
			IApplicationProperties applicationProperties) {
		this.applicationProperties = applicationProperties;
	}

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	
	public String getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String getUploadFileContentType() {
		return uploadFileContentType;
	}

	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}
	
	private String getDownloadCreateFileLink() {
		if(_downloadCreateLink == null) {
			try {
				_downloadCreateLink = applicationProperties.getPropertyValue
					(RoutingRulesConstants.CREATE_RULE_TEMPLATE);
			} catch (DataNotFoundException e) {
				logger.error("could not get the sample link property from " +
						"database using default" , e);
			}
		} //default value
		return _downloadCreateLink;
	}
	
	private String getDownloadUpdateFileLink() {
		if(_downloadUpdateLink == null) {
			try {
				_downloadUpdateLink = applicationProperties.getPropertyValue
					(RoutingRulesConstants.UPDATE_RULE_TEMPLATE);
			} catch (DataNotFoundException e) {
				logger.error("could not get the sample link property from " +
						"database using default" , e);
			}
		} //default value
		return _downloadUpdateLink;
	}
	
	
	private String getDownloadArchiveFileLink() {
		if(_downloadArchiveLink == null) {
			try {
				_downloadArchiveLink = applicationProperties.getPropertyValue
					(RoutingRulesConstants.ARCHIVE_RULE_TEMPLATE);
			} catch (DataNotFoundException e) {
				logger.error("could not get the sample link property from " +
						"database using default" , e);
			}
		} //default value
		return _downloadArchiveLink;
	}
	
	private String getPageRefreshTime() {
		if(_pageRefreshTime == null) {
			try {
				_pageRefreshTime = applicationProperties.getPropertyValue
					(RoutingRulesConstants.UPLOAD_PAGE_REFRESH);
			} catch (DataNotFoundException e) {
				logger.error("could not get the time property from " +
						"database using default" , e);
			}
		} //default value
		return _pageRefreshTime;
	}

	/**
	 * 
	 * @return result
	 * @throws Exception
	 */
	public String display () throws Exception{	
		
		String returnValue = SUCCESS;
		
		// Handle Pagination
		RoutingRulesPaginationVO pagingCriteria = 
			(RoutingRulesPaginationVO)getSession().getAttribute
			("uploadRulePagination");
		
		RoutingRulesFileUploadVO uploadVO = (RoutingRulesFileUploadVO) 
			getSession().getAttribute(UPLOAD_VO);
		
		String pageLoad[]=(String[])getRequest().getAttribute("pageLoad");		
		if (pageLoad != null) {
			// If the page is loaded for the first time, there are no errors
			uploadVO = null;
		}
		if(null!=uploadVO){			
			model.put(UPLOAD_VO_MODAL, uploadVO);
		}
		
		String pageNumber[]=(String[])getRequest().getAttribute("pageNo");			
		if (pagingCriteria != null) {
			if (pageNumber != null) {
				Integer pageNo = Integer.parseInt(pageNumber[0]);
				pagingCriteria.setPageRequested(pageNo);				
			} else {
				pagingCriteria=null;
			}
		}
		
		// Handle Sorting Parameters
		String[] sortCol = (String[])getRequest().getAttribute("sortCol");	
		if (sortCol != null) {
			pagingCriteria.setSortCol(Integer.parseInt(sortCol[0])); 
		}
		String[] sortOrd = (String[])getRequest().getAttribute("sortOrd");		
		if (sortOrd != null) {
			pagingCriteria.setSortOrd(Integer.parseInt(sortOrd[0]));
		} 
		
		// If not the first time the page is being loaded
		if((null != sortCol && null != sortOrd) || null != pageNumber){
			returnValue = DISPLAY_LIST;	
		}		
		Integer buyerId = getContextBuyerId();	
		pagingCriteria=getRoutingRulesPaginationService().
			setUploadFileCriteria(pagingCriteria, buyerId);
		
		if (pageLoad != null) {
			pagingCriteria.setSortCol(2);
			pagingCriteria.setSortOrd(0);
		}
		// Get the uploaded file list for the buyer and show it on the screen
		List<RoutingRuleFileHdr> uploadedFilesInDB = getRoutingRulesService().
			getRoutingRulesFileHeaderList(pagingCriteria, buyerId);			
		
		Map<Integer, String> ruleCauseMap = new HashMap<Integer, String>();
		List<RoutingRuleErrorCause> errorCauseList = getRoutingRulesService().getErrorCause();	
		for (RoutingRuleErrorCause errorCause : errorCauseList) {
			ruleCauseMap.put(errorCause.getRoutingRuleErrorCauseId(),
					errorCause.getErrorCauseDesc());
		}
		List<RoutingRulesFileUploadVO> uploadedFiles = RoutingRulesFileUploadVO.
			getVOFromDomainObjects(uploadedFilesInDB,ruleCauseMap);		
		model.put(LIST_ROUTING_RULE_FILE_UPLOAD, uploadedFiles);
		model.put("downloadCreateFile", getDownloadCreateFileLink());
		model.put("downloadUpdateFile", getDownloadUpdateFileLink());
		model.put("downloadArchiveFile", getDownloadArchiveFileLink());
		getSession().setAttribute("uploadRulePagination", pagingCriteria);
		String refreshTime = (String) getSession().getAttribute("refreshTime");
		if(StringUtils.isEmpty(refreshTime)){
			getSession().setAttribute("refreshTime", getPageRefreshTime());
		}
		return returnValue;
	}	
	
	
	/**
	 * Upload and save the file details
	 * @return result
	 */
	@SuppressWarnings("unchecked")
	public String uploadFile() throws Exception{
		
		HttpSession session = getSession();
		SecurityContext securityContext = (SecurityContext) session.
			getAttribute("SecurityContext");
		
		RoutingRulesFileUploadVO uploadVO = new RoutingRulesFileUploadVO();
		Integer buyerId = getContextBuyerId();	
		File file = null;
		String fileName = "";
		File[] uploadedFile = (File[])model.get("uploadFile");	
		if(null!= uploadedFile && uploadedFile.length>0){
			file =  uploadedFile[0];	
		}else{	
			 // Add the error and return the model .			
			uploadVO.setUploadSuccess(false);
			uploadVO.setUploadError(RoutingRulesConstants.NO_FILE);
			model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(uploadVO));
			return SUCCESS;
		}
		
		String[] uploadedFileName = (String[])model.get("uploadFileFileName");
		if(uploadedFileName.length>0){
			fileName = uploadedFileName[0];			
			if(!fileName.trim().endsWith(".xls") && 
					!fileName.trim().endsWith(".xlsx")){
				// Error file extension
				uploadVO.setUploadSuccess(false);
				uploadVO.setUploadError(RoutingRulesConstants.WRONG_FILE_EXT);
				model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(uploadVO));
				return SUCCESS;
			}
			
			/* Check whether the file name already exists in the database.
			/* Remove the extension from the file name to check 
			 * for both .xls and .xlsx files*/
			int dotIndex = fileName.lastIndexOf("."); 
			String fileNameWithoutExt = fileName.substring(0, dotIndex);			
			boolean fileExists = getRoutingRulesService().checkIfFileExists(
					fileNameWithoutExt,buyerId);			
			if(fileExists){
				uploadVO.setUploadSuccess(false);
				uploadVO.setUploadError(RoutingRulesConstants.FILE_EXISTS);
				model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(uploadVO));
				return SUCCESS;
			}
		}
		
		File fileDir = null;
		try {
			// Check the file for number of work sheets and rows per sheet
			Workbook workbook = null;
			try{
				InputStream parseFile = new FileInputStream(file);
				if (fileName.trim().endsWith(".xls")){
					workbook = new HSSFWorkbook(parseFile);
				}else {
					workbook = new XSSFWorkbook(parseFile);			
				}
			}catch (Exception ie) {
				/* Avoid the mime type check. If the file is not a 
				 * valid xls or xlsx, the poi parser will throw an 
				 * Exception, which is caught and reported to the user
				 */
				uploadVO.setUploadSuccess(false);
				uploadVO.setUploadError(RoutingRulesConstants.INVALID_TEMPLATE);
				model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(uploadVO));
				return SUCCESS;
			}			
			
			if(null!=workbook){
			
				/* Get the following values from the database.
				 * 1.Number of allowed sheets
				 * 2.Number of header rows 
				 * 3.Number of records per sheet.
				 * */
				String numberOfAllowedSheets = null;
				String numberOfHeaderRows = null;
				String numberOfDataRows = null;
				String numberOfArchiveRows = null;
				
				try {
					numberOfAllowedSheets = applicationProperties.
						getPropertyValue(RoutingRulesConstants.RULE_FILE_SHEETS);
					numberOfHeaderRows = applicationProperties.
						getPropertyValue(RoutingRulesConstants.RULE_FILE_HEADERS);
					numberOfDataRows = applicationProperties.
						getPropertyValue(RoutingRulesConstants.RULE_FILE_DATA);
					numberOfArchiveRows = applicationProperties.
						getPropertyValue(RoutingRulesConstants.RULE_FILE_ARCHIVE_DATA);
				} catch (DataNotFoundException e) {
					logger.error("Could not get the properties from DB " +
							"for rule files" , e);
				}	
				
				int allowedSheets =	numberOfAllowedSheets!=null ? Integer.parseInt(numberOfAllowedSheets):
						Integer.parseInt(RoutingRulesConstants.RULE_FILE_SHEETS_DEFAULT);
						
				int allowedHeaders = numberOfHeaderRows!=null ?Integer.parseInt(numberOfHeaderRows):
						Integer.parseInt(RoutingRulesConstants.RULE_FILE_HEADERS_DEFAULT);
						
				int allowedDataRows = numberOfDataRows!=null ?Integer.parseInt(numberOfDataRows):
						Integer.parseInt(RoutingRulesConstants.RULE_FILE_DATA_DEFAULT);
				
				int allowedArchiveDataRows = numberOfArchiveRows!=null ?Integer.parseInt(numberOfArchiveRows):
					Integer.parseInt(RoutingRulesConstants.RULE_FILE_ARCHIVE_DATA_DEFAULT);
						
				int numSheets = workbook.getNumberOfSheets();
				if(numSheets > allowedSheets){
					uploadVO.setUploadSuccess(false);
					uploadVO.setUploadError(RoutingRulesConstants.TOO_MANY_SHEETS+new Integer(allowedSheets).toString());
					model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(uploadVO));
					return SUCCESS;
				}else{
					for (int sheetNo = 0; sheetNo < numSheets; sheetNo++) {
						Sheet sheet = (Sheet) workbook.getSheetAt(sheetNo);
						
					
						
						// Number of non-empty rows
						int rowCount = sheet.getPhysicalNumberOfRows();
						
						// TODO - Find actual non-empty 
						
						
						/* Check the action in the file 
						 * The maximum number of data rows is different for 
						 * different actions
						 * */  
						Row rowObj = sheet.getRow(0);
						String action = rowObj.getCell((short) 1).getStringCellValue();
						int totalAllowedCount=0;
						int totalAllowedHeaders = allowedHeaders -2; // 2 empty rows in header 
						boolean validHeader = true;
						validHeader = validHeaders(sheet, action,totalAllowedHeaders);
						if(!validHeader){
							uploadVO.setUploadSuccess(false);
							uploadVO.setUploadError(RoutingRulesConstants.INVALID_TEMPLATE);
							model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(uploadVO));
							return SUCCESS;
						}
						// Valid headers - Now validate the data 
						
						if(RoutingRulesConstants.UPLOAD_ACTION_ARCHIVE.equalsIgnoreCase(action) || 
								RoutingRulesConstants.UPLOAD_ACTION_INACTIVE.equalsIgnoreCase(action)){						
							totalAllowedCount = allowedArchiveDataRows;
						}else if(RoutingRulesConstants.UPLOAD_ACTION_ADD.equalsIgnoreCase(action) ||
								RoutingRulesConstants.UPLOAD_ACTION_DELETE.equalsIgnoreCase(action) ||
								RoutingRulesConstants.UPLOAD_ACTION_NEW.equalsIgnoreCase(action)){
							totalAllowedCount = allowedDataRows;
						}else{
							// Invalid data for action
							uploadVO.setUploadSuccess(false);
							uploadVO.setUploadError(RoutingRulesConstants.INVALID_TEMPLATE);
							model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(uploadVO));
							return SUCCESS;
						}
						
						
						if(rowCount> totalAllowedCount+allowedHeaders){
							uploadVO.setUploadSuccess(false);
							uploadVO.setUploadError(RoutingRulesConstants.TOO_MANY_LINES);
							model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(uploadVO));
							return SUCCESS;
						}else if (rowCount<=totalAllowedHeaders+1){ // At least one row of data
							uploadVO.setUploadSuccess(false);
							uploadVO.setUploadError(RoutingRulesConstants.INVALID_TEMPLATE);
							model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(uploadVO));
							return SUCCESS;
						}
					}
				 }
			}
					
			String ruleUploadDir = null;
			try {
				ruleUploadDir = applicationProperties.
					getPropertyValue(RoutingRulesConstants.
							ROUTING_RULE_FILE_DIR);
			} catch (DataNotFoundException e) {
				logger.error("could not get the file dir property from " +
						"database using default" , e);
				ruleUploadDir = RoutingRulesConstants.ROUTING_RULE_FILE_PATH;
			}	
			
			String destFileName = ruleUploadDir + fileName;
			fileDir = new File(destFileName);
			
			// Copy the file in case of no errors
			FileUtils.copyFile(file, fileDir);
	
			// Insert the details to the database.
			RoutingRuleFileHdr routingRuleFileHdr = new RoutingRuleFileHdr();
			routingRuleFileHdr.setFilePath(destFileName);
			routingRuleFileHdr.setRoutingRuleFileName(fileName);
			
			BuyerResource buyerResource = new BuyerResource();
			buyerResource.setResourceId(securityContext.getVendBuyerResId());
			routingRuleFileHdr.setUploadedBy(buyerResource);
			boolean adopted = securityContext.isAdopted();
			if(adopted){
				String adminName = securityContext.getSlAdminFName()+ 
					" "+ securityContext.getSlAdminLName();
				routingRuleFileHdr.setModifiedBy(adminName);
			}else{
				routingRuleFileHdr.setModifiedBy(getLoggedInName());
			}
			routingRuleFileHdr.setCreatedDate(new Date());
			routingRuleFileHdr.setModifiedDate(new Date());
			routingRuleFileHdr.setFileStatus(RoutingRulesConstants.
				FILE_STATUS_SCHEDULED);
			
			routingRuleFileHdr = getRoutingRulesService().saveRoutingRuleFile(
					routingRuleFileHdr);
			
			uploadVO.setUploadSuccess(true);
			uploadVO.setUploadedFileName(fileName);
			model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(uploadVO));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			uploadVO.setUploadSuccess(false);
			uploadVO.setUploadError(RoutingRulesConstants.INVALID_TEMPLATE);
			model.put(S_JSON_RESULT, getRoutingRulesService().getJSON(uploadVO));
			if (null != fileDir)
				FileUtils.forceDelete(fileDir);
		}
		return SUCCESS;
	}

	public void setModel(Map<String,Object> model) {
		this.model = model;
	}
	
	public Map<String,Object> getModel() {		
		return model;
	}
	
	private boolean validHeaders(Sheet worksheet,String action,int allowedHeaders){
		int[] commonHeaderFields = new int[] {0,1,2,3,4,5,6,7,8};
		int[] createHeaderFields = new int[] {0,1,2,3,4,5,6,7};
		int lastHeaderrow = 11;
		if(allowedHeaders > worksheet.getPhysicalNumberOfRows()){ // Since there are two empty rows in the current template
			return false;
		}else{			
			// Validate the common fields for all actions
			for(int count=0;count<commonHeaderFields.length;count++){
				int row = commonHeaderFields[count];
				Row rowObj = worksheet.getRow(row);
				String headerValue = rowObj.getCell((short) 0).getStringCellValue();
				if(!RoutingRulesConstants.commonFileHeaderMap.get(row).
						equalsIgnoreCase(headerValue)){
					return false;
				}
			}			
			if(RoutingRulesConstants.UPLOAD_ACTION_ARCHIVE.equalsIgnoreCase(action) || 
					RoutingRulesConstants.UPLOAD_ACTION_INACTIVE.equalsIgnoreCase(action)){
				Row rowObj = worksheet.getRow(lastHeaderrow);
				String headerValue = rowObj.getCell((short) 0).getStringCellValue();
				if(!RoutingRulesConstants.RULE_FILE_RULE_ID.equalsIgnoreCase(headerValue))
					return false;
			
			}else if(RoutingRulesConstants.UPLOAD_ACTION_ADD.equalsIgnoreCase(action) ||
					RoutingRulesConstants.UPLOAD_ACTION_DELETE.equalsIgnoreCase(action) ||
					RoutingRulesConstants.UPLOAD_ACTION_NEW.equalsIgnoreCase(action)){
				Row rowObj = worksheet.getRow(lastHeaderrow);
				// Validate the common fields for all actions
				for(int count=0;count<createHeaderFields.length;count++){
					String headerValue = rowObj.getCell((short) count).getStringCellValue();
					if(!RoutingRulesConstants.createFileHeaderMap.get(count).
							equalsIgnoreCase(headerValue)){
						return false;
					}
				}
			}
		}
		
		return true;
		
	}
}