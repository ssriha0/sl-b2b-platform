package com.servicelive.esb.util;

import org.junit.Test;

import com.servicelive.util.MarketESBUtil;

public class MarketESBUtilTest {
	private static MarketESBUtil util;

	@Test
	public void testRenameInputFileToWorkFile() {
		util = new MarketESBUtil();
		String fileName = "All fields_GDO2.startProcessGW";
		String workSuffix = "work";
		String workFileName = "";
		
		try {
			workFileName = util.renameInputFileToWorkFile(fileName, workSuffix);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}

