package com.servicelive.wallet.main;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.servicelive.gl.db.DBAccess;
import com.servicelive.wallet.batch.gl.GLTransformer;
import com.servicelive.wallet.batch.gl.GLWriter;
import com.servicelive.wallet.batch.gl.vo.GLDetailVO;
import com.servicelive.wallet.batch.gl.vo.GLFeedVO;

public class GenerateGLMain {

	
	private static final Logger logger = Logger.getLogger(GenerateGLMain.class.getName());

	
	public static void main(String[] args){
		
		GLTransformer glTransformer = new GLTransformer();
		GLWriter glWriter = new GLWriter();
		DBAccess dba = new DBAccess();
		String fileName = createFileName();
		Calendar cal = Calendar.getInstance();
		cal.set(2015, Calendar.JANUARY, 16); //Year, month and day of month
		Date startDate = cal.getTime();

		//List<GLDetailVO> glDetailVOListNoHSR = glDao.getGLDetails(glProcessLogId, false);
		/*List<GLDetailVO> glDetailVOListNoHSR;
		try {
			glDetailVOListNoHSR = dba.getGLDetailsRest();
			ArrayList<GLFeedVO> glFeedItemList = glTransformer.convertGLDetailVOListToGLFeedVOList(glDetailVOListNoHSR,startDate);
			glWriter.writeGLFeed(glFeedItemList, fileName);
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}*/
		// Retrieve data of HSR for GL output file from rpt_gl_details table
		//List<GLDetailVO> glDetailVOListHSR = glDao.getGLDetails(glProcessLogId, true);
		List<GLDetailVO> glDetailVOListHSR;
		try {
			glDetailVOListHSR = dba.getGLDetailsInHome();
			ArrayList<GLFeedVO> glFeedItemListInHome = glTransformer.convertGLDetailVOListToGLFeedVOList(glDetailVOListHSR,startDate);
			glWriter.writeGLFeed(glFeedItemListInHome, "3000_"+fileName);
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	

	}

	
	private static String createFileName() {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MMddyyyy_HHmmss");
		//To change the name of the file change the cal object here
		cal.set(2015, Calendar.JANUARY, 16);
		String dateStr = sdf1.format(cal.getTime());

		String fileName = dateStr + "_gl.dat";
		return fileName;
	}

}
