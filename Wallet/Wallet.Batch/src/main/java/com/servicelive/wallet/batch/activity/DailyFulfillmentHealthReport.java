package com.servicelive.wallet.batch.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.batch.activity.dao.IPlatformActivityDao;
import com.servicelive.wallet.batch.activity.vo.DailyFulfillmentReportVO;

// TODO: Auto-generated Javadoc
/**
 * 
 * 
 * @author nsanzer
 * Description: Class that creates the worksheet containing the records from rpt_fulfillment_daily.
 * First is updated by a call to sp_fulfillment_daily_report.
 */

public class DailyFulfillmentHealthReport implements IDailyPlatformActivity {

	/** The Constant fileNamePrefix. */
	private static final String fileNamePrefix = "daily_fulfillment_";

	/** The platform activity dao. */
	private IPlatformActivityDao platformActivityDao;

	/**
	 * Description: Create Header row for report.
	 * 
	 * @param sheet 
	 * @param i 
	 * 
	 * @return 
	 */
	private int createRowHeader(HSSFSheet sheet, int i) {

		HSSFRow row = sheet.createRow((short) i);

		row.createCell((short) 0).setCellValue("report day");
		row.createCell((short) 1).setCellValue("total entries");
		row.createCell((short) 2).setCellValue("unreconciled entries");
		row.createCell((short) 3).setCellValue("total requests");
		row.createCell((short) 4).setCellValue("unmatched responses");
		i++;
		return i;
	}

	/**
	 * Description: Loop through records returned and insert into spreadsheet.
	 * 
	 * @param sheet 
	 * @param i 
	 * @param fulfillmentEntries 
	 */
	private void createRows(HSSFSheet sheet, int i, List<DailyFulfillmentReportVO> fulfillmentEntries,
		HSSFCellStyle cellStyle ) {

		HSSFRow row;
		for (DailyFulfillmentReportVO fulfillmentRptVO : fulfillmentEntries) {
			row = sheet.createRow((short) i);
			HSSFCell cell = row.createCell((short) 0);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(HSSFDateUtil.getExcelDate(fulfillmentRptVO.getReportDay()));

			row.createCell((short) 1).setCellValue(fulfillmentRptVO.getTotalEntries());
			row.createCell((short) 2).setCellValue(fulfillmentRptVO.getUnreconciledEntries());
			if (fulfillmentRptVO.getTotalRequests() != null)
				row.createCell((short) 3).setCellValue(fulfillmentRptVO.getTotalRequests());
			row.createCell((short) 4).setCellValue(fulfillmentRptVO.getUnmatchedResponses());
			i++;
		}
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.activity.IDailyPlatformActivity#generatePlatformActivitiesWorksheet(org.apache.poi.hssf.usermodel.HSSFWorkbook)
	 */
	public void generatePlatformActivitiesWorksheet(HSSFWorkbook wb) throws SLBusinessServiceException {

		HSSFSheet sheet = initalizeWorksheet(wb);
		int i = 1;
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
		i = createRowHeader(sheet, i);
		// insert data here
		platformActivityDao.populateFulfillmentRpt();
		List<DailyFulfillmentReportVO> fulfillmentEntries = platformActivityDao.getFulfillmentReportVOs();
		createRows(sheet, i, fulfillmentEntries, cellStyle);

	}

	/**
	 * Description: Set the name and date that will appear on the worksheet's tab.
	 * 
	 * @param wb 
	 * 
	 * @return <code>HSSFSheet</code>
	 */
	private HSSFSheet initalizeWorksheet(HSSFWorkbook wb) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String fileName = fileNamePrefix + formatter.format(new Date());
		HSSFSheet sheet = wb.createSheet(fileName);
		return sheet;
	}

	/**
	 * Sets the platform activity dao.
	 * 
	 * @param platformActivityDao the new platform activity dao
	 */
	public void setPlatformActivityDao(IPlatformActivityDao platformActivityDao) {

		this.platformActivityDao = platformActivityDao;
	}
}
