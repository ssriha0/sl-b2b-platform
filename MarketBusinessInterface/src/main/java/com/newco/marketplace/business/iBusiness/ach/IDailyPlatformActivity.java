package com.newco.marketplace.business.iBusiness.ach;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public interface IDailyPlatformActivity {

	public void generatePlatformActivitiesWorksheet(HSSFWorkbook wb) throws Exception;
	
	
}
