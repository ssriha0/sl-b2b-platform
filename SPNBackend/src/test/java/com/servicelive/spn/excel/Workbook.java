package com.servicelive.spn.excel;

import java.util.List;

/**
 * 
 * @author svanloo
 *
 */
public class Workbook {
	private List<Sheet> sheets;

	/**
	 * @return the sheets
	 */
	public List<Sheet> getSheets() {
		return sheets;
	}

	/**
	 * @param sheets the sheets to set
	 */
	public void setSheets(List<Sheet> sheets) {
		this.sheets = sheets;
	}
}
