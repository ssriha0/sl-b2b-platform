package com.servicelive.manage1099.util;

import junit.framework.Assert;

import org.junit.Test;

public class CommonUtilTest {

	private CommonUtil util;
	@Test
	public void testReplaceFirstOccurrence(){
		util = new CommonUtil();
		String original="power driven"; 
		String toReplace=" "; 
		String replaceWith="-";
		
		String result = "";
		result = util.replaceFirstOccurrence(original, toReplace, replaceWith);
	
		Assert.assertEquals("power-driven", result);
	}


}
