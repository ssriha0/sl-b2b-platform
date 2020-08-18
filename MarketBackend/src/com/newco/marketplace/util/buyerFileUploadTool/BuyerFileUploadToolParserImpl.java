package com.newco.marketplace.util.buyerFileUploadTool;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.buyerUploadScheduler.UploadToolConstants;


public class BuyerFileUploadToolParserImpl implements IBuyerFileUploadToolParser, UploadToolConstants {
	
	public List<Map<String, String>> parseBuyerXLSFile(byte[] bytes) throws Exception {
		List<Map<String, String>> hashMapSOList = new ArrayList<Map<String, String>>();
			OutputStream os = null;
			os = new FileOutputStream("tmp.xls");
			ByteArrayInputStream is = new ByteArrayInputStream(bytes);
			int bytesRead = 0;
			while ((bytesRead = is.read(bytes)) != -1) {
				os.write(bytes, 0, bytesRead);
			}
			File tmpXlsFile = new File("tmp.xls");
			if (tmpXlsFile == null || tmpXlsFile.isDirectory() || tmpXlsFile.length() == 0) {
				System.out.println("Invalid file content at"
						+ tmpXlsFile.getAbsolutePath()
						+ ". File will not be processed");
				tmpXlsFile.delete();
			}
			else
			{
				Workbook workbook = null;
				workbook = Workbook.getWorkbook(tmpXlsFile);
				Sheet sheet = workbook.getSheet(0);
				int rowCount = getRightRows(sheet);
				int columnCount = sheet.getColumns();
				if(columnCount == 91)
				{
					List<List<String>> parsedList = new ArrayList<List<String>>();
					
					// Ignore first two rows, as first two rows are excel header in the template provided to buyers
					for (int i = 2; i < rowCount; i++) {
						List<String> arr = new ArrayList<String>();
						for (int j = 0; j < columnCount; j++) {
							Cell cell = sheet.getCell(j, i);
							arr.add(cell.getContents().trim()); //Trimming leading/trailing spaces received in the file
						}
						arr.add(Integer.toString(i - 1));  // Add the row number (1 based)
						parsedList.add(arr);
					}
					hashMapSOList = createHashMapListForValidation(parsedList);
				}else{
					hashMapSOList = null;
				}
					tmpXlsFile.delete();
			}
		return hashMapSOList;

	}

	private static List<Map<String, String>> createHashMapListForValidation(List<List<String>> parsedList) {
		List<Map<String, String>> hashMapSOList = new ArrayList<Map<String, String>>();
		for (int i = 0; i < parsedList.size(); i++) {
			List<String> arr = parsedList.get(i);
			Map<String, String> hashMap = new HashMap<String, String>();
			int columnIndex = -1;
			hashMap.put(Main_Service_Catagory, arr.get(++columnIndex));
			hashMap.put(TaskOne_TaskName, arr.get(++columnIndex));
			hashMap.put(TaskOne_Category, arr.get(++columnIndex));
			hashMap.put(TaskOne_SubCategory, arr.get(++columnIndex));
			hashMap.put(TaskOne_Skill, arr.get(++columnIndex));
			hashMap.put(TaskOne_TaskComments, arr.get(++columnIndex));
			hashMap.put(TaskTwo_TaskName, arr.get(++columnIndex));
			hashMap.put(TaskTwo_Category, arr.get(++columnIndex));
			hashMap.put(TaskTwo_SubCategory, arr.get(++columnIndex));
			hashMap.put(TaskTwo_Skill, arr.get(++columnIndex));
			hashMap.put(TaskTwo_TaskComments, arr.get(++columnIndex));
			hashMap.put(TaskThree_TaskName, arr.get(++columnIndex));
			hashMap.put(TaskThree_Category, arr.get(++columnIndex));
			hashMap.put(TaskThree_SubCategory, arr.get(++columnIndex));
			hashMap.put(TaskThree_Skill, arr.get(++columnIndex));
			hashMap.put(TaskThree_TaskComments, arr.get(++columnIndex));
			hashMap.put(PartOne_Manufacturer, arr.get(++columnIndex));
			hashMap.put(PartOne_PartName, arr.get(++columnIndex));
			hashMap.put(PartOne_ModelNumber, arr.get(++columnIndex));
			hashMap.put(PartOne_Description, arr.get(++columnIndex));
			hashMap.put(PartOne_Quantity, arr.get(++columnIndex));
			hashMap.put(PartOne_InboundCarrier, arr.get(++columnIndex));
			hashMap.put(PartOne_InboundTracking, arr.get(++columnIndex));
			hashMap.put(PartOne_OutboundCarrier, arr.get(++columnIndex));
			hashMap.put(PartOne_OutboundTracking, arr.get(++columnIndex));
			hashMap.put(PartTwo_Manufacturer, arr.get(++columnIndex));
			hashMap.put(PartTwo_PartName, arr.get(++columnIndex));
			hashMap.put(PartTwo_ModelNumber, arr.get(++columnIndex));
			hashMap.put(PartTwo_Description, arr.get(++columnIndex));
			hashMap.put(PartTwo_Quantity, arr.get(++columnIndex));
			hashMap.put(PartTwo_InboundCarrier, arr.get(++columnIndex));
			hashMap.put(PartTwo_InboundTracking, arr.get(++columnIndex));
			hashMap.put(PartTwo_OutboundCarrier, arr.get(++columnIndex));
			hashMap.put(PartTwo_OutboundTracking, arr.get(++columnIndex));
			hashMap.put(PartThree_Manufacturer, arr.get(++columnIndex));
			hashMap.put(PartThree_PartName, arr.get(++columnIndex));
			hashMap.put(PartThree_ModelNumber, arr.get(++columnIndex));
			hashMap.put(PartThree_Description, arr.get(++columnIndex));
			hashMap.put(PartThree_Quantity, arr.get(++columnIndex));
			hashMap.put(PartThree_InboundCarrier, arr.get(++columnIndex));
			hashMap.put(PartThree_InboundTracking, arr.get(++columnIndex));
			hashMap.put(PartThree_OutboundCarrier, arr.get(++columnIndex));
			hashMap.put(PartThree_OutboundTracking, arr.get(++columnIndex));
			hashMap.put(Part_And_Material, arr.get(++columnIndex));
			hashMap.put(Location_Type, arr.get(++columnIndex));
			hashMap.put(Business_Name, arr.get(++columnIndex));
			hashMap.put(First_Name, arr.get(++columnIndex));
			hashMap.put(Last_Name, arr.get(++columnIndex));
			hashMap.put(Street_Name1, arr.get(++columnIndex));
			hashMap.put(Street_Name2, arr.get(++columnIndex));
			hashMap.put(Apt, arr.get(++columnIndex));
			hashMap.put(City, arr.get(++columnIndex));
			hashMap.put(State, arr.get(++columnIndex));
			hashMap.put(Zip, arr.get(++columnIndex));
			hashMap.put(Email, arr.get(++columnIndex));
			hashMap.put(Phone_Number, arr.get(++columnIndex));
			hashMap.put(Phone_Type, arr.get(++columnIndex));
			hashMap.put(Alternate_Phone_Number, arr.get(++columnIndex));
			hashMap.put(Alternate_Phone_Type, arr.get(++columnIndex));
			hashMap.put(Fax, arr.get(++columnIndex));
			hashMap.put(Service_Location_Notes, arr.get(++columnIndex));
			hashMap.put(Title, arr.get(++columnIndex));
			hashMap.put(Overview, arr.get(++columnIndex));
			hashMap.put(Buyer_TC, arr.get(++columnIndex));
			hashMap.put(Special_Instructions, arr.get(++columnIndex));
			hashMap.put(Service_Date_Type, arr.get(++columnIndex));
			hashMap.put(Service_Date, arr.get(++columnIndex));
			hashMap.put(Service_Time, arr.get(++columnIndex));
			hashMap.put(Service_End_Date, arr.get(++columnIndex));
			hashMap.put(Service_End_Time, arr.get(++columnIndex));
			hashMap.put(Provider_To_Confirm_Time, arr.get(++columnIndex));
			hashMap.put(Attachment1, arr.get(++columnIndex));
			hashMap.put(Desription1, arr.get(++columnIndex));
			hashMap.put(Attachment2, arr.get(++columnIndex));
			hashMap.put(Desription2, arr.get(++columnIndex));
			hashMap.put(Attachment3, arr.get(++columnIndex));
			hashMap.put(Desription3, arr.get(++columnIndex));
			hashMap.put(Branding_Information, arr.get(++columnIndex));
			for (int custRefCount = 0; custRefCount < MAX_SUPPORTED_CUSTOM_REF_COUNT; ++custRefCount) {
				hashMap.put(Custom_Reference_Value+custRefCount, arr.get(++columnIndex));
			}
			hashMap.put(Template_Id, arr.get(++columnIndex));
			hashMap.put(Labor_Spend_Limit, arr.get(++columnIndex));
			hashMap.put(Part_Spend_Limit, arr.get(++columnIndex));
			hashMap.put(Row_Number, arr.get(++columnIndex));
			hashMapSOList.add(hashMap);
		}
		return hashMapSOList;
	}

	private static int getRightRows(Sheet sheet) {
		int rsCols = sheet.getColumns();
		int rsRows = sheet.getRows();
		int afterRows = rsRows;
		for (int i = 1; i < rsRows; i++) {
			int nullCellNum = 0;
			for (int j = 0; j < rsCols; j++) {
				String val = sheet.getCell(j, i).getContents();
				val = StringUtils.trimToEmpty(val);
				if (StringUtils.isBlank(val))
					nullCellNum++;
			}
			if (nullCellNum >= rsCols)
				afterRows--;
		}
		return afterRows;
	}

}
