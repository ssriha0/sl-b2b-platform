package com.servicelive.wallet.batch.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.batch.activity.dao.IPlatformActivityDao;
import com.servicelive.wallet.batch.activity.vo.ReconciliationViewVO;

// TODO: Auto-generated Javadoc
/**
 * 
 * 
 * @author nsanzer
 * Description: Class responsible for giving high-level information on the nightly processes that run.
 * Also indicates whether the processes have run or not.
 */

public class DailyReconciliationViewFile implements IDailyPlatformActivity {

	/** The Constant fileNamePrefix. */
	private static final String fileNamePrefix = "daily_reconciliation_";

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(DailyReconciliationViewFile.class.getName());

	/** The platformActivity dao. */
	private IPlatformActivityDao platformActivityDao;

	/**
	 * Description: Makes calls to get amounts on Nacha process.
	 * 
	 * @param amounts 
	 * 
	 * @throws DataServiceException 
	 */
	private void buildNachaMap(Map<String, ReconciliationViewVO> amounts) throws DataServiceException {

		ReconciliationViewVO buyerACHDeposits = platformActivityDao.getNachaProcessView(100, 10);
		amounts.put("Buyer ACH deposits", buyerACHDeposits);
		ReconciliationViewVO searsInstantACHConsolidatedEntry = platformActivityDao.getNachaProcessViewForConsolidatedAutoAchEntry();
		amounts.put("Consolidated Instant ACH Deposit", searsInstantACHConsolidatedEntry);
		ReconciliationViewVO slaDeposits = platformActivityDao.getNachaProcessView(100, 90);
		amounts.put("SLA deposits", slaDeposits);
		ReconciliationViewVO providerWithdraw = platformActivityDao.getNachaProcessView(800, 20);
		amounts.put("Provider withdraw", providerWithdraw);
		ReconciliationViewVO buyerACHRefunds = platformActivityDao.getNachaProcessView(800, 10);
		amounts.put("Buyer ACH refunds", buyerACHRefunds);
	}

	/**
	 * Description: Makes calls to get amounts on Nacha process.
	 * 
	 * @param sheet 
	 * @param i 
	 * @param amounts 
	 * 
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	private int createAmountEntries(HSSFSheet sheet, int i, Map<String, ReconciliationViewVO> amounts) {

		for (Object element : amounts.entrySet()) {
			Map.Entry entry = (Map.Entry) element;
			String key = (String) entry.getKey();
			ReconciliationViewVO reconVO = (ReconciliationViewVO) entry.getValue();
			if (reconVO != null) {
				createRowWithData(sheet, i, reconVO);
			} else {
				createRowNoData(sheet, i, key);
			}
			i++;
		}
		return i;
	}

	/**
	 * Description: Create Header row for report.
	 * 
	 * @param sheet 
	 * @param i 
	 * 
	 * @return 
	 */
	private int createRowHeader(HSSFSheet sheet, int i) {

		i++;
		HSSFRow row = sheet.createRow((short) i);

		row.createCell((short) 0).setCellValue("");
		row.createCell((short) 1).setCellValue("Process");
		row.createCell((short) 2).setCellValue("Date last ran");
		row.createCell((short) 3).setCellValue("");
		row.createCell((short) 4).setCellValue("Sent");
		row.createCell((short) 5).setCellValue("Amount");
		i++;

		row = sheet.createRow((short) i);
		row.createCell((short) 0).setCellValue("");
		i++;
		return i;
	}

	/**
	 * Description: Create a last ran row for nightly batch processes.
	 * 
	 * @param formatter 
	 * @param sheet 
	 * @param i 
	 * @param lastRan 
	 * @param processDesc 
	 * 
	 * @return 
	 */
	private int createRowLastRan(SimpleDateFormat formatter, HSSFSheet sheet, int i, Date lastRan, String processDesc) {

		HSSFRow row;
		row = sheet.createRow((short) i);
		row.createCell((short) 0).setCellValue("");
		row.createCell((short) 1).setCellValue(processDesc);
		if (lastRan != null) row.createCell((short) 2).setCellValue(formatter.format(lastRan));
		i++;
		lastRan = null;
		return i;
	}

	/**
	 * Description: create a row when VO has no data.
	 * 
	 * @param sheet 
	 * @param i 
	 * @param rowDesc 
	 */
	private void createRowNoData(HSSFSheet sheet, int i, String rowDesc) {

		HSSFRow row;
		row = sheet.createRow((short) i);
		row.createCell((short) 0).setCellValue("");
		row.createCell((short) 1).setCellValue("");
		row.createCell((short) 2).setCellValue("");
		row.createCell((short) 3).setCellValue(rowDesc);
		row.createCell((short) 4).setCellValue(0);
		row.createCell((short) 5).setCellValue(0.0);
	}

	/**
	 * Description: Acknowledgement,Origination,Periodic Transaction Detail,General Ledger and Tran rows.
	 * 
	 * @param formatter 
	 * @param sheet 
	 * @param i 
	 * 
	 * @return 
	 * 
	 * @throws DataServiceException 
	 */
	private int createRowsLastRanDate(SimpleDateFormat formatter, HSSFSheet sheet, int i) throws DataServiceException {

		HSSFRow row;
		int x = i;
		row = sheet.createRow((short) x);
		row.createCell((short) 0).setCellValue("");
		x++;

		Integer processStatusId = 0;
		
		processStatusId = 30;
		Date lastRan = platformActivityDao.getNachaProcessLastRanDate(processStatusId);
		x = createRowLastRan(formatter, sheet, x, lastRan, "Acknowledgement");

		processStatusId = 50;
		lastRan = platformActivityDao.getNachaProcessLastRanDate(processStatusId);
		x = createRowLastRan(formatter, sheet, x, lastRan, "Origination");

		row = sheet.createRow((short) x);
		row.createCell((short) 0).setCellValue("");
		x++;

		lastRan = platformActivityDao.getPTDProcessLastRanDate();
		x = createRowLastRan(formatter, sheet, x, lastRan, "Periodic Transaction Detail");

		row = sheet.createRow((short) x);
		row.createCell((short) 0).setCellValue("");
		x++;

		lastRan = platformActivityDao.getGeneralLedgerProcessLastRanDate();
		x = createRowLastRan(formatter, sheet, x, lastRan, "General Ledger");

		row = sheet.createRow((short) x);
		row.createCell((short) 0).setCellValue("");
		x++;

		lastRan = platformActivityDao.getTransProcessLastRanDate();
		x = createRowLastRan(formatter, sheet, x, lastRan, "Tran");
		return x;
	}

	/**
	 * Description: create a row with data from VO.
	 * 
	 * @param sheet 
	 * @param i 
	 * @param reconViewVO 
	 */
	private void createRowWithData(HSSFSheet sheet, int i, ReconciliationViewVO reconViewVO) {

		HSSFRow row;
		row = sheet.createRow((short) i);
		row.createCell((short) 0).setCellValue("");
		row.createCell((short) 1).setCellValue("");
		row.createCell((short) 2).setCellValue("");
		row.createCell((short) 3).setCellValue(reconViewVO.getTransactionType());
		row.createCell((short) 4).setCellValue(reconViewVO.getNumOfRecords());
		row.createCell((short) 5).setCellValue(reconViewVO.getAmount());
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.activity.IDailyPlatformActivity#generatePlatformActivitiesWorksheet(org.apache.poi.hssf.usermodel.HSSFWorkbook)
	 */
	public void generatePlatformActivitiesWorksheet(HSSFWorkbook wb) throws SLBusinessServiceException {

		// Nacha Record

		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");

		String fileName = fileNamePrefix + formatter.format(new Date());

		formatter = new SimpleDateFormat("MM/dd/yyyy");

		HSSFSheet sheet = wb.createSheet(fileName);
		int i = 1;
		HSSFRow row;
		i = createRowHeader(sheet, i);

		// insert data here
		try {
			row = sheet.createRow((short) i);
			row.createCell((short) 0).setCellValue("");
			row.createCell((short) 1).setCellValue("Nacha");
			java.sql.Date nachaProcessLastRanDate = platformActivityDao.getNachaProcessLastRanDate();
			if (nachaProcessLastRanDate != null) {
				row.createCell((short) 2).setCellValue(formatter.format(nachaProcessLastRanDate));
			}
			i++;

			Map<String, ReconciliationViewVO> amounts = new HashMap<String, ReconciliationViewVO>();
			buildNachaMap(amounts);

			i = createAmountEntries(sheet, i, amounts);

			i = createRowsLastRanDate(formatter, sheet, i);

			Map<String, ReconciliationViewVO> tranAmounts = new HashMap<String, ReconciliationViewVO>();
			ReconciliationViewVO buyerCCDeposits = platformActivityDao.getTransProcessView(1200);
			tranAmounts.put("Buyer CC deposits", buyerCCDeposits);
			ReconciliationViewVO buyerCCRefunds = platformActivityDao.getTransProcessView(1400);
			tranAmounts.put("Buyer CC refunds", buyerCCRefunds);

			i = createAmountEntries(sheet, i, tranAmounts);
		} catch (DataServiceException e) {
			logger.error("Error while generating the report" + e);
		}
	}

	/**
	 * Sets the nacha dao.
	 * 
	 * @param nachaDao the new nacha dao
	 */
	public void setPlatformActivityDao(IPlatformActivityDao platformActivityDao) {

		this.platformActivityDao = platformActivityDao;
	}

}
