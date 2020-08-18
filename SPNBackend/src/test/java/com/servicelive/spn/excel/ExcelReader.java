package com.servicelive.spn.excel;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 
 * @author svanloon
 *
 */
public class ExcelReader {
	private static final Logger logger = Logger.getLogger(ExcelReader.class);
	/**
	 * 
	 *
	 */
	public ExcelReader() {
		super();
	}
	
	/**
	 * 
	 * @param clazz
	 * @return Workbook
	 * @throws IOException
	 */
	public Workbook read(Class<?> clazz) throws IOException
	{
		String resourceName = clazz.getCanonicalName() + ".xls";
		resourceName = resourceName.replace(".", "/");
		resourceName = resourceName.replace("/xls", ".xls");
		URL resource = ClassLoader.getSystemResource(resourceName);
		if(resource == null) {
			return null;
		}
		HSSFWorkbook wb = new HSSFWorkbook(resource.openStream());

		List<Sheet> sheets = new ArrayList<Sheet>();
		int i = 0;
		while(true) {
			try {
				HSSFSheet sheet = wb.getSheetAt(i);
				String sheetName = wb.getSheetName(i);
				Sheet mySheet = handleSheet(sheetName, sheet);
				sheets.add(mySheet);
				i++;
			} catch (IndexOutOfBoundsException e) {
				break;
			}
		}
		Workbook workbook = new Workbook();
		workbook.setSheets(sheets);
		return workbook;
	}

	@SuppressWarnings("unchecked")
	private Sheet handleSheet(String sheetName, HSSFSheet sheet) {
		Sheet mySheet = new Sheet();
		mySheet.setSheetName(sheetName);
		List<Row> rows = new ArrayList<Row>();
		boolean isFirstRow = true;
		List<String> columnNames = new ArrayList<String>();
		for(Iterator rowIter = sheet.rowIterator(); rowIter.hasNext();) {
			HSSFRow row = (HSSFRow) rowIter.next();
			if(row == null) {
				logger.info("row is null");
				continue;
			}
			
			List<Object> values = new ArrayList<Object>(); 
			for(Iterator cellIterator = row.cellIterator(); cellIterator.hasNext(); ) {
				HSSFCell cell   = (HSSFCell) cellIterator.next();
				if(isFirstRow) {
					String columnName = handleCell(cell).toString();
					columnNames.add(columnName);
				} else {
					Object obj = handleCell(cell);
					values.add(obj);
				}
			}

			if(isFirstRow) {
				isFirstRow = false;
				continue;
			}
			assert(columnNames.size() == values.size());
			//String sql = createInsert(sheetName, columnNames, values);
			Row myRow = new Row();
			myRow.setValues(values);
			rows.add(myRow);
		}
		mySheet.setColumnNames(columnNames);
		mySheet.setRows(rows);
		return mySheet;
	}

	private Object handleCell(HSSFCell cell) {
		if(cell == null) {
			return null;
		}
		int cellType = cell.getCellType();

		return handleCell(cellType, cell);
	}

	@SuppressWarnings("deprecation")
	private Object handleCell(int cellType, HSSFCell cell) {
		if (cellType == HSSFCell.CELL_TYPE_BOOLEAN) {
			return Boolean.valueOf(cell.getBooleanCellValue());
		} else if (cellType == HSSFCell.CELL_TYPE_STRING) {
			String value = cell.getStringCellValue();
			value = value.trim();
			if(value.equals("null")) {
				return null;
			} else if (value.equals("SYSDATE()")) {
				return new Function(value);
			}
			return value;
		} else if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
			double d = cell.getNumericCellValue();
			if(d == (int) d) {
				return Integer.valueOf((int) d);
			}
			return Double.valueOf(d); 
		} else if(cellType == HSSFCell.CELL_TYPE_FORMULA) {
			int tempCellType = cell.getCachedFormulaResultType();
			return handleCell(tempCellType, cell);
		} else {
			//There should be a date and maybe handle blanks
			return cell.getStringCellValue();
		}
	}
}
