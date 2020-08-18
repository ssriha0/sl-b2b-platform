package com.newco.marketplace.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.enums.ExcelParserProcessingType;
import com.newco.marketplace.exception.core.ExcelParserException;

/**
 * This class parses an excel file and converts it into a bean object. The conversion is based on custom-annotations in the bean class. 
 * The class can handle Header Based Conversions and Position Based Conversions.
 * Position Based Conversions indicate the the column position in the excel sheet should match to that specified in the Bean object.
 */
public class ExcelParser<T> {

	private static final Logger logger = Logger.getLogger(ExcelParser.class);

	// The Excel POJO Annotated Class class object
	Class<T> annotationClass;
	
	// Private HashMap collection object to store the bean attribute - excel header mappings.  
	private HashMap<String, String> annotationHashMap = new HashMap<String, String>();

	/**
	 * Empty Constructor
	 */
	@SuppressWarnings("unused")
	private ExcelParser() {
		// Do nothing
	}
	
	/**
	 * Public Constructor processes the given class and stores the declared annotation in the annotationHashMap object
	 * @param annotationClass: The class on which annotation processing has to be performed
	 */
	public ExcelParser(Class<T> annotationClass) {
		
		this.annotationClass = annotationClass;
		
		Field[] declaredFields = annotationClass.getDeclaredFields();
		for (Field eachField : declaredFields) {
			ExcelAlias excelAliasAnnotation = eachField.getAnnotation(ExcelAlias.class);
			if (null != excelAliasAnnotation) {
				annotationHashMap.put(eachField.getName(), excelAliasAnnotation.value());
			}
		}
		logger.info(annotationHashMap.toString());
	}

	/**
	 * This method parses the given excel file. A variable which defines the start of valid data is also passed to this method. 
	 * @param filePath - String that specifies the path of the file
	 * @param dataStartOnExcelRow - variable that specifies the start of valid data on the excel sheet
	 * @param processingType - variable that specifies whether to perform position based or header based parsing
	 * @return Object- Returns a collection of bean objects after transforming the excel file
	 */
	public List<T> parse(String filePath, int dataStartOnExcelRow, ExcelParserProcessingType processingType) throws ExcelParserException{

		FileInputStream fileInputStream = null;
		WorkbookSettings workbookSetting = null;
		Workbook workbook = null;
		Sheet sheet = null;
		Cell columnData[] = null;
		int columnCount = 0;
		int startRowCount = 0;
		String key = "";

		// HashMap object to store the parsed information
		HashMap<String, List<String>> contentData = new HashMap<String, List<String>>();
		// ArrayList object that contains the list of Bean Objects.
		List<T> objectList = new ArrayList<T>();
		
		try {
			fileInputStream = new FileInputStream(new File(filePath));
			workbookSetting = new WorkbookSettings();
			workbookSetting.setLocale(new Locale("en", "EN"));
			workbook = Workbook.getWorkbook(fileInputStream, workbookSetting);

			// Assumption that there is a single work sheet that we need to process.
			sheet = workbook.getSheet(0);
			columnCount = sheet.getColumns();

			// Total number of valid records in the sheet. (Each record will map to a bean object)
			int totalRecordCount = getValidRecordCount(sheet, dataStartOnExcelRow)- dataStartOnExcelRow;

			// Row number of the last valid record, on the excel sheet
			int dataEndOnExcelRow = dataStartOnExcelRow + totalRecordCount;

			// If it is position based parsing, do not include the header row, else include
			if (processingType == ExcelParserProcessingType.POSITION_BASED) {
				startRowCount = dataStartOnExcelRow;
			} else if (processingType == ExcelParserProcessingType.HEADER_BASED) {
				startRowCount = dataStartOnExcelRow - 1;
			}

			for (int columnNumber = 0; columnNumber < columnCount; columnNumber++) {
				columnData = sheet.getColumn(columnNumber);

				// If it is position based parsing, annotations will be the position (number), else it will be the header name.
				if (processingType==ExcelParserProcessingType.POSITION_BASED) {
					key = String.valueOf(columnNumber + 1);
				} else if (processingType==ExcelParserProcessingType.HEADER_BASED) {
					key = columnData[startRowCount].getContents();
				} 

				// Process each row (that contains valid records) from the excel sheet. 
				List<String> list = new ArrayList<String>();
				for (int rowNumber = dataStartOnExcelRow; rowNumber < dataEndOnExcelRow; rowNumber++) {
					// add the list of entries to the collection object
					list.add(columnData[rowNumber].getContents());
				}
				// store the list of entries against the key
				contentData.put(key, list);
			}

			logger.info("Worksheet processing complete. Content data is loaded in memory");

			// Create each bean object and put it in the bean object collection list
			for (int recordCounter = 0; recordCounter < totalRecordCount; ++recordCounter) {
				T returnObject =  createObject(contentData, recordCounter);
				objectList.add(returnObject);
			}

			logger.info("Completed parsing file and converting into VO");
			logger.info("Size of VO list = " + objectList.size());
		} catch (Exception e){
			logger.error("Error while parsing the excel file",e);
			throw new ExcelParserException(e); 
		} finally {
			if (null != workbook) {
				workbook.close();
			}
			try {
				fileInputStream.close();
			} catch (IOException ioEx) {
				logger.fatal("Error caught during excel parsing", ioEx);
			}
		}
		return objectList;
	}

	/**
	 * This method finds out the number of records in the given sheet It internally checks for null rows and assumes that a null row indicates end of data.
	 * @param sheet - The work sheet to check
	 * @param dataStartRow - The record from which valid data starts (Entered by the user) 
	 * @return Count that indicates the number of valid records that can be converted into bean objects.
	 */
	private int getValidRecordCount(Sheet sheet, int dataStartRow) {
		int rowCount = sheet.getRows();
		for (int validRecordCount = dataStartRow; validRecordCount < rowCount; validRecordCount++) {
			Cell[] rowCells = sheet.getRow(validRecordCount);
			boolean isRowEmpty = true;
			for (Cell cell : rowCells) {
				if (StringUtils.isNotBlank(cell.getContents())) {
					isRowEmpty = false;
					break;
				}
			}
			if (isRowEmpty) {
				return validRecordCount;
			}
		}
		return rowCount;
	}

	/**
	 * This method is used to create the bean object for each corresponding record in the excel sheet. It uses reflection API to dynamically create a class and to populate its fields. 
	 * @param contentData- HashMap that contains the content data for the bean
	 * @param iteration- The counter which corresponds to each record (each bean)
	 * @return Object- Bean object of the type WestSurveyVO 
	 * @throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException
	 */
	@SuppressWarnings("unchecked")
	public T createObject(HashMap<String, List<String>> contentData, int recordCounter)
			throws ClassNotFoundException, SecurityException, NoSuchMethodException, IllegalArgumentException,
					IllegalAccessException, InvocationTargetException, InstantiationException {
		
		// Create instance of POJO object
		T returnObject = annotationClass.newInstance();
		
		final String STRING_CLASS = "class java.lang.String";
		Class[] types = new Class[] { String.class };
		
		// Set values from fields using setter methods corresponding to respective field
		for (Field field : annotationClass.getDeclaredFields()) {
			String fieldName = field.getName();
			String header = annotationHashMap.get(fieldName);
			if (header != null) {
				ArrayList<String> values = (ArrayList<String>) contentData.get(header);
				String value = (String) values.get(recordCounter);
				if (STRING_CLASS.equals(field.getType().toString())) {
					Method setterMethod = annotationClass.getMethod("set" + fieldName.substring(0, 1).toUpperCase()+ fieldName.substring(1), types);
					setterMethod.invoke(returnObject, new Object[] { new String(value) });
				}
			}
		}
		return returnObject; 
	}
	
	/**
	 * This method parses the given excel file. Here we assume that we have to process the entire file. i.e. no rows to skip
	 * @param filePath - String that specifies the path of the file
	 * @param processingType - variable that specifies whether to perform position based or header based parsing
	 * @return Object- Returns a collection of bean objects after transforming the excel file 
	 * @throws ExcelParserException 
	 */
	public List<T> parse(String filePath, ExcelParserProcessingType processingType) throws ExcelParserException {
		// Default dataStartOnExcelRow to zero
		return parse(filePath, 0, processingType);
	}
	
	/**
	 * This method parses the given array byte. It internally converts it into a temporary excel file and uses it for conversion.
	 * @param byte[] - Binary data of excel spreadsheet
	 * @param dataStartOnExcelRow - variable that specifies the start of valid data on the excel sheet
	 * @param processingType - variable that specifies whether to perform position based or header based parsing
	 * @return List<T> - Returns a collection of bean objects after transforming the excel file 
	 * @throws ExcelParserException 
	 */
	public List<T> parse(byte[] bytes, int dataStartOnExcelRow, ExcelParserProcessingType processingType) throws ExcelParserException {
		
		// Create temporary excel spreadsheet file name
		String tempDir = System.getProperty("java.io.temp");
		String timestamp = new SimpleDateFormat("yyyyMMdd.HHmmssss").format(Calendar.getInstance().getTime());
		String tempXLSFileName = tempDir + File.separator + "ExcelParser_" + timestamp + ".xls";
		
		// Write byte array to a temporary excel spreadsheet file
		OutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(tempXLSFileName);
			outputStream.write(bytes, 0, bytes.length);
		} catch (IOException ioEx) {
			logger.error("Unexpected IO Error while creating temporary excel sheet from given binary data", ioEx);
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException ioEx) {
					logger.fatal("Exception while closing stream", ioEx);
				}
			}
		}
		
		// Parse the excel file now
		List<T> returnObjects = parse(tempXLSFileName, dataStartOnExcelRow, processingType);
		
		// Delete the temporary excel file
		File tempXLSFile = new File(tempXLSFileName);
		tempXLSFile.delete();
		
		return returnObjects;
	}
	
	/**
	 * This method parses the given array byte. It internally converts it into a temporary excel file and uses it for conversion.
	 * @param byte[] - Binary data of excel spreadsheet
	 * @param processingType - variable that specifies whether to perform position based or header based parsing
	 * @return List<T> - Returns a collection of bean objects after transforming the excel file 
	 * @throws ExcelParserException 
	 */
	public List<T> parse(byte[] bytes, ExcelParserProcessingType processingType) throws ExcelParserException {
		// Default dataStartOnExcelRow to zero
		return parse(bytes, 0, processingType);
	}

}
