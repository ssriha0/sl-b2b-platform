package com.servicelive.wallet.batch.activity;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.batch.activity.dao.IPlatformActivityDao;
import com.servicelive.wallet.batch.activity.vo.WalletBalanceVO;

// TODO: Auto-generated Javadoc
/**
 * 
 * 
 * @author nsanzer
 * Description: Class responsible for determining whether there is any Wallet discrepancy for buyers or providers
 * This report only generates records that are in error. A blank worksheeet is good-news in this case.
 * No-news is good news.
 */

public class DailyWalletBalanceVerification implements IDailyPlatformActivity {

	/** The Constant fileNamePrefix. */
	private static final String fileNamePrefix = "daily_wallet_";

	/** The platform activity dao. */
	private IPlatformActivityDao platformActivityDao;

	/**
	 * Description: Loop through records returned and insert into spreadsheet.
	 * 
	 * @param sheet 
	 * @param i 
	 * @param wbVOs 
	 * 
	 * @return 
	 */
	private int createRow(HSSFSheet sheet, int i, List<WalletBalanceVO> wbVOs) {

		for (WalletBalanceVO walletBalanceVO : wbVOs) {
			HSSFRow row;

			row = sheet.createRow((short) i);
			row.createCell((short) 0).setCellValue(walletBalanceVO.getLedgerEntryId());
			row.createCell((short) 1).setCellValue(walletBalanceVO.getTransAmount());
			row.createCell((short) 2).setCellValue(walletBalanceVO.getValidatedAmount());
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

		HSSFRow row = sheet.createRow((short) i);

		row.createCell((short) 0).setCellValue("ledger entry id");
		row.createCell((short) 1).setCellValue("trans amount");
		row.createCell((short) 2).setCellValue("validated amount");
		i++;
		return i;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.activity.IDailyPlatformActivity#generatePlatformActivitiesWorksheet(org.apache.poi.hssf.usermodel.HSSFWorkbook)
	 */
	public void generatePlatformActivitiesWorksheet(HSSFWorkbook wb) throws SLBusinessServiceException {

		HSSFSheet sheet = initalizeWorksheet(wb);
		int i = 1;
		i = createRowHeader(sheet, i);
		WalletBalanceVO walletBalanceVO = setTimeWindow();
		// get distinct entites that did a transaction yesterday.
		List<Integer> entityIds = platformActivityDao.getUniqueEntityIds(walletBalanceVO);
		for (Integer entityId : entityIds) {
			walletBalanceVO.setEntityId(entityId);
			Integer entyTypeId = platformActivityDao.getEntityType(entityId);
			walletBalanceVO.setEntityType(entyTypeId);
			List<WalletBalanceVO> wbVOs = platformActivityDao.getAvailableBalanceVerification(walletBalanceVO);
			createRow(sheet, i, wbVOs);
			if (entyTypeId == 10) {
				List<WalletBalanceVO> wbVOs2 = platformActivityDao.getProjectBalanceVerification(walletBalanceVO);
				createRow(sheet, i, wbVOs2);
			}
		}
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
		String fileName = fileNamePrefix + formatter.format(new java.util.Date());
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

	/**
	 * Sets the time window.
	 * 
	 * @return the wallet balance vo
	 */
	private WalletBalanceVO setTimeWindow() {

		WalletBalanceVO walletBalanceVO = new WalletBalanceVO();
		// insert data here
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		walletBalanceVO.setStartDate(new Date(cal.getTimeInMillis()));

		cal.add(Calendar.DAY_OF_MONTH, 0);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		walletBalanceVO.setEndDate(new Date(cal.getTimeInMillis()));
		return walletBalanceVO;
	}
}
