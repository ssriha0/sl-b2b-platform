package com.servicelive.scmaudit;

import org.apache.log4j.Logger;
import org.junit.Test;
import static org.mockito.Mockito.*;
import com.servicelive.scmaudit.CodeCommitLogMonitor;

public class CodeCommitLogMonitorTest {
	private static Logger logger = Logger.getLogger(CodeCommitLogMonitorTest.class);
	private static String html = "<html><head><title>This is a JUnit Test case</title></head><body>This is a JUnit body</body></html>";
	private static String startPattern = "JUnit";
	private static String endPattern = "JUnit";
	private CodeCommitLogMonitor monitor;

	@Test
   public void getlatestRevision(){
	   monitor = mock(CodeCommitLogMonitor.class);
	   try{
		   monitor.execute();
	   }catch (Exception e) {
		   logger.error("Exception in finding pattern in html string:"+ e.getMessage());
		  }
   }
}
