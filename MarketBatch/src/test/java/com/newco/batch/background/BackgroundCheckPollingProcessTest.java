package com.newco.batch.background;

import org.junit.Assert;
import org.junit.Test;

import com.newco.batch.background.vo.BackgroundCheckStatusVO;


public class BackgroundCheckPollingProcessTest {

	private BackgroundCheckPollingProcess process;
	private BackgroundCheckStatusVO testVO;
	
	@Test
	public void testparseLine(){
		String inputLine="FALLA09175729|SERV601790681|35053|35053|BRIAN|FALLIN|N|Y|F|2008-11-21|2010-10-28";
		BackgroundCheckStatusVO baseVO = createCompleteVO();
		process = new BackgroundCheckPollingProcess();
		try {
			 try {
				testVO = process.parseLine(inputLine);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception ex) {
			System.out.println("Caught exception processing input line " + ex.getMessage());
		}
				Assert.assertEquals(baseVO, testVO);
	}
	
	private BackgroundCheckStatusVO createCompleteVO() {
		BackgroundCheckStatusVO result = new BackgroundCheckStatusVO();
		result.setPlusoneKey("FALLA09175729");	
		result.setClientCompanyId("SERV601790681");	
		result.setServiceOrganizationId("35053");
		result.setTechId("35053");
		result.setTechFname("BRIAN");
		result.setTechLname("FALLIN");
		result.setOverall("N");
		result.setCrim("Y");
		result.setDriv("F");
		result.setVerificationDate("2008-11-21");
		result.setRecertificationDate("2010-10-28");
		
		return result;
	}
}
