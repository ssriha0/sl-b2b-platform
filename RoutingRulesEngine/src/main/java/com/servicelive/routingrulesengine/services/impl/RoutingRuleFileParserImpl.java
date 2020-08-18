package com.servicelive.routingrulesengine.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.servicelive.routingrulesengine.RoutingRulesConstants;
import com.servicelive.routingrulesengine.services.RoutingRuleFileParser;
import com.servicelive.routingrulesengine.vo.CustomReferenceVO;
import com.servicelive.routingrulesengine.vo.JobPriceVO;
import com.servicelive.routingrulesengine.vo.RoutingRuleUploadRuleVO;

public class RoutingRuleFileParserImpl extends RoutingRulesConstants implements
		RoutingRuleFileParser {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.servicelive.routingrulesengine.services.RoutingRuleFileParser#parseFile(java.io.File)
	 *      Generates the list of RoutingRuleUploadRuleVO from the uploaded
	 *      xls/xlsx file
	 */
	public List<RoutingRuleUploadRuleVO> parseFile(File file) throws Exception {
		Workbook workbook;

		List<RoutingRuleUploadRuleVO> uploadRuleVOList = new ArrayList<RoutingRuleUploadRuleVO>();
		String fileName = file.getName();
		// handle file not found exception
		FileInputStream parseFile = new FileInputStream(file);
		if (fileName.trim().endsWith(".xls"))
			workbook = (Workbook) new HSSFWorkbook(parseFile);
		else
			workbook = new XSSFWorkbook(parseFile);
		
		int sheetNo = 0; 

				Sheet sheet = (Sheet) workbook.getSheetAt(sheetNo);
				int rowCount = sheet.getPhysicalNumberOfRows();
				Row rowObj = sheet.getRow(0);
				String action = rowObj.getCell((short) 1).getStringCellValue();
				if (rowCount < 5) {
					RoutingRuleUploadRuleVO uploadRuleVO = new RoutingRuleUploadRuleVO();
					uploadRuleVO.setAction(UPLOAD_ACTION_ERROR);
					uploadRuleVO.setErrorType(FILE_ERROR_INVALID_FILE);
					uploadRuleVO.setFileaction(action);
					uploadRuleVOList.add(uploadRuleVO);
					return uploadRuleVOList;
				}
				// reading metadata
				
				if(action!=null){
					action = action.trim();
				}
				String comments = null;
				rowObj = sheet.getRow(1);
				Cell cell1 = rowObj.getCell((short) 1);
				if(cell1 != null){
					cell1.setCellType(Cell.CELL_TYPE_STRING);
					comments = cell1.getStringCellValue();
				}
				if (action.equalsIgnoreCase(UPLOAD_ACTION_ARCHIVE) || action.equalsIgnoreCase(UPLOAD_ACTION_INACTIVE)) {
					
					List<String> ruleIds = new ArrayList<String>();
					
					for (int rowNum = 12; rowNum < rowCount; rowNum++) {
						Row row = sheet.getRow(rowNum);
						Cell cell = row.getCell((short) 0);
						if (cell != null) {
							cell.setCellType(Cell.CELL_TYPE_STRING);
							String ruleId = cell.getStringCellValue();
							if (ruleId != null && !ruleId.trim().equals("")) {
								ruleIds.add(ruleId.trim());							
							}
						}
					}
					if(!ruleIds.isEmpty()){
						RoutingRuleUploadRuleVO uploadRuleVO = new RoutingRuleUploadRuleVO();
						uploadRuleVO.setAction(action);
						uploadRuleVO.setComments(comments);
						uploadRuleVO.setRuleIds(ruleIds);
						uploadRuleVOList.add(uploadRuleVO);
						return uploadRuleVOList;
					}else {
						RoutingRuleUploadRuleVO uploadRuleVO = new RoutingRuleUploadRuleVO();
						uploadRuleVO.setAction(UPLOAD_ACTION_ERROR);
						uploadRuleVO.setErrorType(FILE_ERROR_NO_RULE);
						uploadRuleVO.setFileaction(action);
						uploadRuleVOList.add(uploadRuleVO);
						return uploadRuleVOList;
					}		
				} else if (action.equalsIgnoreCase(UPLOAD_ACTION_NEW) || action.equalsIgnoreCase(UPLOAD_ACTION_ADD)
						|| action.equalsIgnoreCase(UPLOAD_ACTION_DELETE)) {

					RoutingRuleUploadRuleVO uploadRuleVO = new RoutingRuleUploadRuleVO();
					uploadRuleVO.setAction(action);
					uploadRuleVO.setComments(comments);
					String ruleName = null;
					rowObj = sheet.getRow(3);
					cell1 = rowObj.getCell((short) 1);
					if(cell1 != null){
						cell1.setCellType(Cell.CELL_TYPE_STRING);
						ruleName = cell1.getStringCellValue();	
					}
					String ruleId = null;
					rowObj = sheet.getRow(4);
					cell1 = rowObj.getCell((short) 1);
					if(cell1 != null){
						cell1.setCellType(Cell.CELL_TYPE_STRING);
						ruleId = cell1.getStringCellValue();
					}
					if (ruleName == null || ruleName.trim().equals("")) {
						if (action.equals(UPLOAD_ACTION_NEW)) {
							uploadRuleVO.setAction(UPLOAD_ACTION_ERROR);
							uploadRuleVO.setErrorType(FILE_ERROR_NO_RULE);
							uploadRuleVO.setFileaction(action);
							uploadRuleVOList.add(uploadRuleVO);
							return uploadRuleVOList;
						} else {
							if (ruleId == null || ruleId.trim().equals("")) {
								uploadRuleVO.setAction(UPLOAD_ACTION_ERROR);
								uploadRuleVO.setErrorType(FILE_ERROR_NO_RULE);
								uploadRuleVO.setFileaction(action);
								uploadRuleVOList.add(uploadRuleVO);
								return uploadRuleVOList;
							}
						}
					}
					uploadRuleVO.setRuleName(ruleName.trim());
					uploadRuleVO.setRuleId(ruleId.trim());
					rowObj = sheet.getRow(5);
					uploadRuleVO.setContactFirstName(rowObj.getCell((short) 1)
							.getStringCellValue());

					rowObj = sheet.getRow(6);
					uploadRuleVO.setContactLastName(rowObj.getCell((short) 1)
							.getStringCellValue());

					rowObj = sheet.getRow(7);
					uploadRuleVO.setEmailId(rowObj.getCell((short) 1)
							.getStringCellValue());

					List<String> providerFirmIds = new ArrayList<String>();
					List<String> zipCodes = new ArrayList<String>();
					List<CustomReferenceVO> customRefs = new ArrayList<CustomReferenceVO>();
					List<JobPriceVO> jobCodes = new ArrayList<JobPriceVO>();

					// reading CAR attributes
					for (int rowNum = 12; rowNum < rowCount; rowNum++) {
						Row row = sheet.getRow(rowNum);
						/*
						 * Col 0: Firm ID 1: Zip Codes 2 & 3: Custom Ref Name +
						 * Value 4: Job Code 5: Old JC 6: Price
						 * 
						 */
						String val = "";

						Cell cell = row.getCell((short) 0);
						if (cell != null) {
							cell.setCellType(Cell.CELL_TYPE_STRING);
							val = cell.getStringCellValue();
							if (val != null && !val.trim().equals(""))
								providerFirmIds.add(val);
						}

						cell = row.getCell((short) 1);
						if (cell != null) {
							cell.setCellType(Cell.CELL_TYPE_STRING);
							val = cell.getStringCellValue();
							if (val != null && !val.trim().equals(""))
								zipCodes.add(val);
						}
						
						val = "";
						cell = row.getCell((short) 2);
						if (cell != null) {
							cell.setCellType(Cell.CELL_TYPE_STRING);
							val = cell.getStringCellValue();
						}
						String customRefValue = "";
						cell = row.getCell((short) 3);
						if (cell != null) {
							cell.setCellType(Cell.CELL_TYPE_STRING);
							customRefValue = cell.getStringCellValue();
						}
						if ((val != null && !val.trim().equals("")) || (customRefValue != null && !customRefValue.trim().equals(""))) {
							if(val!=null){
								val = val.trim();
							}
							if(customRefValue!=null){
								customRefValue = customRefValue.trim();
							}
							CustomReferenceVO customRef = new CustomReferenceVO(
											val, customRefValue);
							customRefs.add(customRef);
						}
							
						val = "";
						String priceVal = null;
						cell = row.getCell((short) 4);
						if (cell != null) {
							cell.setCellType(Cell.CELL_TYPE_STRING);
							val = cell.getStringCellValue();
						}
						
						cell = row.getCell((short) 5);
						if (cell != null) {
							cell.setCellType(Cell.CELL_TYPE_STRING);
							priceVal = cell.getStringCellValue();
						}
						if ((val != null && !val.trim().equals("")) || (priceVal != null && !priceVal.trim().equals(""))) {
							if(val!=null){
								val = val.trim();
							}
							if(priceVal!=null){
								priceVal = priceVal.trim();
							}
							JobPriceVO jc = new JobPriceVO(val, priceVal);
							jobCodes.add(jc);
						}
					}
					uploadRuleVO.setCustRefs(customRefs);
					uploadRuleVO.setProviderFirmIds(providerFirmIds);
					uploadRuleVO.setZipCodes(zipCodes);
					uploadRuleVO.setJobPrice(jobCodes);
					uploadRuleVOList.add(uploadRuleVO);
				} else {
					RoutingRuleUploadRuleVO uploadRuleVO = new RoutingRuleUploadRuleVO();
					uploadRuleVO.setAction(UPLOAD_ACTION_ERROR);
					uploadRuleVO.setErrorType(FILE_ERROR_INVALID_FILE);
					uploadRuleVO.setFileaction(action);
					uploadRuleVOList.add(uploadRuleVO);
				}
		return uploadRuleVOList;
	}

}
