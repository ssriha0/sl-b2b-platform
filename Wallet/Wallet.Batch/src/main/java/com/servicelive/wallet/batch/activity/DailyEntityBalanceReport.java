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
import com.servicelive.wallet.batch.activity.vo.SpBalanceReportVO;

// TODO: Auto-generated Javadoc
/**
 * 
 * 
 * @author nsanzer
 * Description: Class that creates the worksheet containing the records from a call to
 * sp_balance_report. Depends on another nightly process which makes balance inquiries to
 * Valuelink.
 */

public class DailyEntityBalanceReport implements IDailyPlatformActivity {

	/** The Constant fileNamePrefix. */
	private static final String fileNamePrefix = "daily_entity_";

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

		row.createCell((short) 0).setCellValue("entity type id");
		row.createCell((short) 1).setCellValue("entity id");
		row.createCell((short) 2).setCellValue("account type");
		row.createCell((short) 3).setCellValue("promo code");
		row.createCell((short) 4).setCellValue("v account");
		row.createCell((short) 5).setCellValue("sl balance");
		row.createCell((short) 6).setCellValue("sl balance date");
		row.createCell((short) 7).setCellValue("vl balance");
		row.createCell((short) 8).setCellValue("vl balance date");

		i++;
		return i;
	}

	/**
	 * Description: Loop through records returned and insert into spreadsheet.
	 * 
	 * @param sheet 
	 * @param i 
	 * @param contacts 
	 */
	private void createRows(HSSFSheet sheet, int i, List<SpBalanceReportVO> contacts, HSSFCellStyle cellStyle) {

		HSSFRow row;
		for (SpBalanceReportVO spBalanceReportVO : contacts) {
			row = sheet.createRow((short) i);
			row.createCell((short) 0).setCellValue(spBalanceReportVO.getEntityTypeId());
			row.createCell((short) 1).setCellValue(spBalanceReportVO.getEntityId());
			row.createCell((short) 2).setCellValue(spBalanceReportVO.getAccountType());
			row.createCell((short) 3).setCellValue(spBalanceReportVO.getPromoCode());
			row.createCell((short) 4).setCellValue(spBalanceReportVO.getAccount());
			row.createCell((short) 5).setCellValue(spBalanceReportVO.getSlBalance());

			HSSFCell cell = row.createCell((short) 6);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(HSSFDateUtil.getExcelDate(spBalanceReportVO.getSlBalanceDate()));

			row.createCell((short) 7).setCellValue(spBalanceReportVO.getVlBalance());
			
			cell = row.createCell((short) 8);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(HSSFDateUtil.getExcelDate(spBalanceReportVO.getVlBalanceDate()));

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
		List<SpBalanceReportVO> contacts = platformActivityDao.getBalanceReportVOs();
		createRows(sheet, i, contacts, cellStyle);

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
