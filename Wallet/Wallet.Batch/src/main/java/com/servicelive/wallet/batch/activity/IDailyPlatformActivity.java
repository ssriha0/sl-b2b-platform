package com.servicelive.wallet.batch.activity;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

// TODO: Auto-generated Javadoc
/**
 * The Interface IDailyPlatformActivity.
 */
public interface IDailyPlatformActivity {

	/**
	 * Generate platform activities worksheet.
	 * 
	 * @param wb 
	 * 
	 * @throws Exception 
	 */
	public void generatePlatformActivitiesWorksheet(HSSFWorkbook wb) throws Exception;

}
