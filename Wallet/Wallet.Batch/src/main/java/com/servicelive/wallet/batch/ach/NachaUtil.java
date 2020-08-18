package com.servicelive.wallet.batch.ach;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.util.FileUtil;
import com.servicelive.wallet.batch.ach.dao.INachaMetaDataDao;
import com.servicelive.wallet.batch.ach.vo.AddendaRecordVO;
import com.servicelive.wallet.batch.ach.vo.EntryDetailRecordVO;
import com.servicelive.wallet.batch.ach.vo.FieldDetailVO;
import com.servicelive.wallet.batch.ach.vo.FileControlRecordVO;

// TODO: Auto-generated Javadoc
/**
 * The Class NachaUtil.
 */
public class NachaUtil {

	/**
	 * Gets the addenda copy.
	 * 
	 * @param addendaOriginal 
	 * 
	 * @return the addenda copy
	 */
	public static AddendaRecordVO getAddendaCopy(AddendaRecordVO addendaOriginal) {

		AddendaRecordVO addendaCopy = new AddendaRecordVO();
		ArrayList<FieldDetailVO> fieldDetailsCopy = new ArrayList<FieldDetailVO>();
		ArrayList<FieldDetailVO> fieldDetailsOriginal = addendaOriginal.getFieldDetailVO();
		for (int p = 0; p < fieldDetailsOriginal.size(); p++) {
			FieldDetailVO fieldDetailCopy = new FieldDetailVO();
			FieldDetailVO fieldDetailOriginal = (FieldDetailVO) fieldDetailsOriginal.get(p);
			fieldDetailCopy.setEndPosition(fieldDetailOriginal.getEndPosition());
			fieldDetailCopy.setFieldDescription(fieldDetailOriginal.getFieldDescription());
			fieldDetailCopy.setFieldId(fieldDetailOriginal.getFieldId());
			fieldDetailCopy.setFieldName(fieldDetailOriginal.getFieldName());
			fieldDetailCopy.setFieldOrder(fieldDetailOriginal.getFieldOrder());
			fieldDetailCopy.setFieldSize(fieldDetailOriginal.getFieldSize());
			fieldDetailCopy.setFieldType(fieldDetailOriginal.getFieldType());
			fieldDetailCopy.setFieldValue(fieldDetailOriginal.getFieldValue());
			fieldDetailCopy.setStartPosition(fieldDetailOriginal.getStartPosition());
			fieldDetailsCopy.add(fieldDetailCopy);
		}
		addendaCopy.setFieldDetailVO(fieldDetailsCopy);
		addendaCopy.setRecord(addendaOriginal.getRecord());

		return addendaCopy;
	}

	/**
	 * Gets the aMPM.
	 * 
	 * @param calendar 
	 * 
	 * @return the aMPM
	 */
	public static String getAMPM(Calendar calendar) {

		if (calendar.get(Calendar.AM_PM) == 1) {
			return "PM";
		} else {
			return "AM";
		}

	}

	/**
	 * Gets the archive file name.
	 * 
	 * @param fileName 
	 * 
	 * @return the archive file name
	 */
	public static String getArchiveFileName(String fileName) {

		String temp = FileUtil.getFileNameWithoutExtension(fileName) + "_";
		String archiveFile = NachaUtil.getFileName(temp) + ".txt";
		return archiveFile;
	}

	/**
	 * This private method returns a template for each record object that conforms to
	 * NACHA format. The template is a list of FieldDetailVO object and each FieldDetailVO
	 * object holds information about the field size, their positions, their type (alpha/numeric)
	 * etc
	 * 
	 * @param metdataIdentifier represents the record that the method should return
	 * @param batchId used only for retrieving batch header template
	 * @param nachaMetaDataDao 
	 * 
	 * @return ArrayList returns a list of FieldDetailVO
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public static ArrayList<FieldDetailVO> getBaseMetaData(INachaMetaDataDao nachaMetaDataDao, String metdataIdentifier, int batchId) throws SLBusinessServiceException {

		ArrayList<FieldDetailVO> fdList = new ArrayList<FieldDetailVO>();
		FieldDetailVO fieldVO = new FieldDetailVO();
		fieldVO.setFieldCategory(metdataIdentifier);
		fieldVO.setBatchGroupId(batchId);
		try {
			fdList = nachaMetaDataDao.retrieveFieldDetais(fieldVO);
		} catch (Exception e) {
			throw new SLBusinessServiceException(CommonConstants.ACH_TEMPLATE_RETRIEVAL_ERROR + e.getStackTrace(),e);
		}
		return fdList;
	}

	/**
	 * Gets the consolidated ledger id.
	 * 
	 * @return the consolidated ledger id
	 */
	public static long getConsolidatedLedgerId() {

		Calendar calendar = new GregorianCalendar();
		int month = calendar.get(Calendar.MONTH) + 1;
		String id = String.valueOf(month) + calendar.get(Calendar.DAY_OF_MONTH) + calendar.get(Calendar.YEAR) + calendar.get(Calendar.HOUR) + calendar.get(Calendar.MINUTE)
				+ calendar.get(Calendar.SECOND);
		long longLedgerId = Long.valueOf(id);

		return longLedgerId;
	}

	/**
	 * Gets the control copy.
	 * 
	 * @param fileControlRecordVO 
	 * 
	 * @return the control copy
	 */
	public static FileControlRecordVO getControlCopy(FileControlRecordVO fileControlRecordVO) {

		FileControlRecordVO controlCopy = new FileControlRecordVO();
		ArrayList<FieldDetailVO> fieldDetailsCopy = new ArrayList<FieldDetailVO>();
		ArrayList<FieldDetailVO> fieldDetailsOriginal = fileControlRecordVO.getFieldDetailVO();
		for (int p = 0; p < fieldDetailsOriginal.size(); p++) {
			FieldDetailVO fieldDetailCopy = new FieldDetailVO();
			FieldDetailVO fieldDetailOriginal = (FieldDetailVO) fieldDetailsOriginal.get(p);
			fieldDetailCopy.setEndPosition(fieldDetailOriginal.getEndPosition());
			fieldDetailCopy.setFieldDescription(fieldDetailOriginal.getFieldDescription());
			fieldDetailCopy.setFieldId(fieldDetailOriginal.getFieldId());
			fieldDetailCopy.setFieldName(fieldDetailOriginal.getFieldName());
			fieldDetailCopy.setFieldOrder(fieldDetailOriginal.getFieldOrder());
			fieldDetailCopy.setFieldSize(fieldDetailOriginal.getFieldSize());
			fieldDetailCopy.setFieldType(fieldDetailOriginal.getFieldType());
			fieldDetailCopy.setFieldValue(fieldDetailOriginal.getFieldValue());
			fieldDetailCopy.setStartPosition(fieldDetailOriginal.getStartPosition());
			fieldDetailsCopy.add(fieldDetailCopy);
		}
		controlCopy.setFieldDetailVO(fieldDetailsCopy);

		return controlCopy;
	}

	/**
	 * Gets the entry detail copy.
	 * 
	 * @param edrOriginal 
	 * 
	 * @return the entry detail copy
	 */
	public static EntryDetailRecordVO getEntryDetailCopy(EntryDetailRecordVO edrOriginal) {

		EntryDetailRecordVO entryDetailCopy = new EntryDetailRecordVO();
		ArrayList<FieldDetailVO> fieldDetailsCopy = new ArrayList<FieldDetailVO>();
		ArrayList<FieldDetailVO> fieldDetailsOriginal = edrOriginal.getFieldDetailVO();
		for (int p = 0; p < fieldDetailsOriginal.size(); p++) {
			FieldDetailVO fieldDetailCopy = new FieldDetailVO();
			FieldDetailVO fieldDetailOriginal = (FieldDetailVO) fieldDetailsOriginal.get(p);

			fieldDetailCopy.setEndPosition(fieldDetailOriginal.getEndPosition());
			fieldDetailCopy.setFieldDescription(fieldDetailOriginal.getFieldDescription());
			fieldDetailCopy.setFieldId(fieldDetailOriginal.getFieldId());
			fieldDetailCopy.setFieldName(fieldDetailOriginal.getFieldName());
			fieldDetailCopy.setFieldOrder(fieldDetailOriginal.getFieldOrder());
			fieldDetailCopy.setFieldSize(fieldDetailOriginal.getFieldSize());
			fieldDetailCopy.setFieldType(fieldDetailOriginal.getFieldType());
			fieldDetailCopy.setFieldValue(fieldDetailOriginal.getFieldValue());
			fieldDetailCopy.setStartPosition(fieldDetailOriginal.getStartPosition());
			fieldDetailsCopy.add(fieldDetailCopy);
		}
		entryDetailCopy.setFieldDetailVO(fieldDetailsCopy);
		entryDetailCopy.setAddendaAvailable(edrOriginal.isAddendaAvailable());
		entryDetailCopy.setEntryDetailNumber(edrOriginal.getEntryDetailNumber());
		entryDetailCopy.setRecord(edrOriginal.getRecord());
		entryDetailCopy.setImmediateDestination(edrOriginal.getImmediateDestination());
		entryDetailCopy.setTransactionType(edrOriginal.getTransactionType());

		return entryDetailCopy;
	}

	/**
	 * Gets the file name.
	 * 
	 * @param fileNamePrefix 
	 * 
	 * @return the file name
	 */
	public static String getFileName(String fileNamePrefix) {

		Calendar calendar = new GregorianCalendar();
		int month = calendar.get(Calendar.MONTH) + 1;
		String fileName =
			fileNamePrefix + month + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "-" + calendar.get(Calendar.YEAR) + "_" + calendar.get(Calendar.HOUR) + "-"
				+ calendar.get(Calendar.MINUTE) + "-" + calendar.get(Calendar.SECOND) + "_" + getAMPM(calendar);
		return fileName;

	}

	/**
	 * Gets the next file id modifier.
	 * 
	 * @param iProcessLogCount 
	 * 
	 * @return the next file id modifier
	 */
	public static String getNextFileIdModifier(Integer iProcessLogCount) {

		String fileIdModifier = null;
		// if less than 26 then it will be from A-Z
		if (iProcessLogCount < 26) {
			iProcessLogCount = iProcessLogCount + 65;
			char charCount = (char) (iProcessLogCount.intValue());
			fileIdModifier = String.valueOf(charCount);
		}
		// if its between 26 to 36 then it will be 0-9
		else if (iProcessLogCount > 25 && iProcessLogCount < 36) {
			fileIdModifier = String.valueOf(iProcessLogCount.intValue() - 26);
		} else {
			fileIdModifier = "A";
		}

		return fileIdModifier;
	}

}
