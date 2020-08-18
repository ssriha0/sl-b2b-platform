package com.servicelive.spn.excel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author svanloo
 *
 */
public class HsqlSqlGenerator {
	
	/**
	 * 
	 * @param workbook
	 * @return List
	 */
	public List<String> generateSql(Workbook workbook) {
		List<String> sqls = new ArrayList<String>();
		for(Sheet sheet: workbook.getSheets()) {
			for(Row row: sheet.getRows()) {
				String sql = createInsert(sheet.getSheetName(), sheet.getColumnNames(), row.getValues());
				sqls.add(sql);
			}
		}
		return sqls;
	}

	private String createInsert(String tableName, List<String> columnNames, List<Object> values) {

		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO ");
		sb.append(tableName);
		sb.append(" ( ");
		sb.append(createColumns(columnNames));
		sb.append(" ) values ( ");
		sb.append(createValues(values));
		sb.append(" )");
		return sb.toString();
	}

	private String createColumns(List<String> columnNames) {
		boolean isFirst = true;
		StringBuilder sb = new StringBuilder();
		for(String columnName: columnNames) {
			if(isFirst) {
				isFirst = false;
			} else {
				sb.append(", ");
			}
			sb.append(columnName);
		}
		return sb.toString();
	}

	private String createValues(List<Object> values) {
		boolean isFirst = true;
		StringBuilder sb = new StringBuilder();
		for(Object value: values) {
			if(isFirst) {
				isFirst = false;
			} else {
				sb.append(", ");
			}

			if(value == null) {
				sb.append("null");
			} else if(value instanceof String) {
				//need to encode properly
				sb.append("'");
				sb.append(value);
				sb.append("'");
			} else if(value instanceof Number) {
				sb.append(value);
			} else if (value instanceof Boolean) {
				sb.append(String.valueOf(value).toLowerCase());
			} else if (value instanceof Function) {
				// will have to convert functions to DB specific calls.
				sb.append(value.toString());
			} else if (value instanceof Date) {
				// need to figure out how to insert datae
				throw new RuntimeException("Can't insert dates yet");
			} else {
				throw new RuntimeException("Type " + value.getClass() + " is unhandled");
			}
		}
		return sb.toString();
	}

}
