package com.servicelive.wallet.batch.ach;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.wallet.batch.ach.dao.INachaMetaDataDao;
import com.servicelive.wallet.batch.ach.vo.AddendaRecordVO;
import com.servicelive.wallet.batch.ach.vo.BatchControlRecordVO;
import com.servicelive.wallet.batch.ach.vo.BatchHeaderRecordVO;
import com.servicelive.wallet.batch.ach.vo.BatchRecordVO;
import com.servicelive.wallet.batch.ach.vo.EntryDetailRecordVO;
import com.servicelive.wallet.batch.ach.vo.EntryRecordVO;
import com.servicelive.wallet.batch.ach.vo.FieldDetailVO;
import com.servicelive.wallet.batch.ach.vo.FileControlRecordVO;
import com.servicelive.wallet.batch.ach.vo.FileHeaderRecordVO;
import com.servicelive.wallet.batch.ach.vo.NachaFileVO;
import com.servicelive.wallet.batch.ach.vo.NachaGenericRecordVO;
import com.servicelive.common.util.BoundedBufferedReader;

// TODO: Auto-generated Javadoc
/**
 * The Class AchFileReader.
 */
public class AchFileReader {

	/** The nacha meta data dao. */
	private INachaMetaDataDao nachaMetaDataDao;

	/**
	 * Gets the loaded record vo.
	 * 
	 * @param lineRecord 
	 * @param nachaGenericRecordVO 
	 * 
	 * @return the loaded record vo
	 */
	private void getLoadedRecordVO(String lineRecord, NachaGenericRecordVO nachaGenericRecordVO) {

		if (nachaGenericRecordVO != null) {
			ArrayList<FieldDetailVO> fieldDetails = nachaGenericRecordVO.getFieldDetailVO();
			for (int i = 0; i < fieldDetails.size(); i++) {
				FieldDetailVO fd = (FieldDetailVO) fieldDetails.get(i);
				fd.setFieldValue(lineRecord.substring(fd.getStartPosition() - 1, fd.getEndPosition()));
			}
		}

	}

	/**
	 * Gets the nacha meta data dao.
	 * 
	 * @return the nacha meta data dao
	 */
	public INachaMetaDataDao getNachaMetaDataDao() {

		return nachaMetaDataDao;
	}

	/**
	 * Objectize file contents.
	 * 
	 * @param file 
	 * 
	 * @return the array list< nacha file v o>
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public ArrayList<NachaFileVO> objectizeFileContents(String file) 
		throws SLBusinessServiceException {

		FileReader fr = null;
		BoundedBufferedReader br = null;
		NachaFileVO nachaFileVO = null;
		ArrayList<NachaFileVO> nachaFileList = null;

		try {
			try {
				fr = new FileReader(file);
				br = new BoundedBufferedReader(fr,2000,BoundedBufferedReader.DEFAULT_MAX_LINE_LENGTH);
				boolean batchFlag = false;
				boolean addendaFlag = false;
				BatchRecordVO batchRecordVO = null;
				nachaFileList = new ArrayList<NachaFileVO>();
				ArrayList<BatchRecordVO> batches = null;
				ArrayList<EntryRecordVO> entryrecords = null;
				EntryRecordVO entryRecord = null;

				EntryDetailRecordVO origEntryDetailRecordVO = new EntryDetailRecordVO();
				AddendaRecordVO origAddendaRecordVO = new AddendaRecordVO();

				ArrayList<FieldDetailVO> entryFieldDetailVO = NachaUtil.getBaseMetaData(nachaMetaDataDao, CommonConstants.ORIGINATION_ENTRY_DETAIL, 0);
				ArrayList<FieldDetailVO> addendaFieldDetailVO = NachaUtil.getBaseMetaData(nachaMetaDataDao, CommonConstants.RETURN_ADDENDA, 0);

				origEntryDetailRecordVO.setFieldDetailVO(entryFieldDetailVO);
				origAddendaRecordVO.setFieldDetailVO(addendaFieldDetailVO);
				String lineContent = null;
				while ((lineContent = br.readLine()) != null) {
					// lineContent = br.readLine();
					NachaGenericRecordVO nachaGenericRecordVO = processLine(lineContent);

					if (nachaGenericRecordVO.getRecordIdentifier().equals(CommonConstants.ORIGINATION_FILE_HEADER)) {
						nachaFileVO = new NachaFileVO();
						batches = new ArrayList<BatchRecordVO>();
						FileHeaderRecordVO fileHeaderRecordVO = (FileHeaderRecordVO) nachaGenericRecordVO;
						nachaFileVO.setFileHeaderRecordVO(fileHeaderRecordVO);
					} else if (nachaGenericRecordVO.getRecordIdentifier().equals(CommonConstants.ORIGINATION_BATCH_HEADER)) {
						BatchHeaderRecordVO batchHeaderRecordVO = (BatchHeaderRecordVO) nachaGenericRecordVO;
						entryrecords = new ArrayList<EntryRecordVO>();
						batchFlag = true;
						batchRecordVO = new BatchRecordVO();
						batchRecordVO.setBatchHeaderRecord(batchHeaderRecordVO);

					} else if (nachaGenericRecordVO.getRecordIdentifier().equals(CommonConstants.ORIGINATION_ENTRY_DETAIL)) {
						EntryDetailRecordVO entryDetailRecordVO = (EntryDetailRecordVO) nachaGenericRecordVO;
						if (batchFlag == true) {
							entryRecord = new EntryRecordVO();
							entryDetailRecordVO = NachaUtil.getEntryDetailCopy(origEntryDetailRecordVO);
							getLoadedRecordVO(lineContent, entryDetailRecordVO);
							HashMap<String, FieldDetailVO> map = entryDetailRecordVO.getHash(entryDetailRecordVO.getFieldDetailVO());
							FieldDetailVO fieldDetailVO_AI = (FieldDetailVO) map.get("ADDENDA_RECORD_INDICATOR");
							String addendaIndicator = fieldDetailVO_AI.getFieldValue();
							entryRecord.setEntryDetailRecord(entryDetailRecordVO);
							if ("1".equals(addendaIndicator)) {
								addendaFlag = true;
							} else {
								addendaFlag = false;
							}
							entryrecords.add(entryRecord);
						} else {
							throw new NachaValidationException("Inconsistent state of object population");
						}
					} else if (nachaGenericRecordVO.getRecordIdentifier().equals(CommonConstants.ORIGINATION_ADDENDA)) {
						AddendaRecordVO addendaRecordVO = (AddendaRecordVO) nachaGenericRecordVO;
						if (addendaFlag == true) {
							addendaRecordVO = NachaUtil.getAddendaCopy(origAddendaRecordVO);
							getLoadedRecordVO(lineContent, addendaRecordVO);
							entryRecord.setAddendaRecord(addendaRecordVO);
						}
					} else if (nachaGenericRecordVO.getRecordIdentifier().equals(CommonConstants.ORIGINATION_BATCH_CONTROL)) {
						BatchControlRecordVO batchControlRecordVO = (BatchControlRecordVO) nachaGenericRecordVO;
						batchFlag = false;
						batchRecordVO.setEntryRecords(entryrecords);
						batchRecordVO.setBatchControlRecord(batchControlRecordVO);
						batches.add(batchRecordVO);

					} else if (nachaGenericRecordVO.getRecordIdentifier().equals(CommonConstants.ORIGINATION_FILE_CONTROL)) {
						FileControlRecordVO fileControlRecordVO = (FileControlRecordVO) nachaGenericRecordVO;

						fileControlRecordVO = NachaUtil.getControlCopy(fileControlRecordVO);
						getLoadedRecordVO(lineContent, fileControlRecordVO);

						nachaFileVO.setFileControlRecordVO(fileControlRecordVO);
						nachaFileList.add(nachaFileVO);
						nachaFileVO.setBatchRecords(batches);
					}
				}
				// nachaFileVO.setBatchRecords(batches);
				// dispose all the resources after using them.
			} catch (FileNotFoundException e) {
				throw new SLBusinessServiceException(e.getMessage(),e);
			} catch (IOException e) {
				throw new SLBusinessServiceException(e.getMessage(),e);
			} finally {
				fr.close();
				br.close();
			}
		} catch (IOException e) {
			throw new SLBusinessServiceException(e.getMessage(),e);
		}
		return nachaFileList;
	}

	/**
	 * Process line.
	 * 
	 * @param lineContent 
	 * 
	 * @return the nacha generic record vo
	 * 
	 * @throws SLBusinessServiceException 
	 */
	public NachaGenericRecordVO processLine(String lineContent) throws SLBusinessServiceException {

		String firstChar = lineContent.substring(0, 1);
		NachaGenericRecordVO nachaGenericRecordVO = null;
		try {
			if (firstChar.equals(CommonConstants.ACH_RECORD_TYPE_FILE_HEADER)) {
				FileHeaderRecordVO fileHeaderRecord = new FileHeaderRecordVO();
				fileHeaderRecord.setFieldDetailVO(NachaUtil.getBaseMetaData(nachaMetaDataDao, CommonConstants.ORIGINATION_FILE_HEADER, 0));
				getLoadedRecordVO(lineContent, fileHeaderRecord);
				fileHeaderRecord.setRecordIdentifier(CommonConstants.ORIGINATION_FILE_HEADER);
				nachaGenericRecordVO = (NachaGenericRecordVO) fileHeaderRecord;
			}
			if (firstChar.equals(CommonConstants.ACH_RECORD_TYPE_FILE_CONTROL)) {
				FileControlRecordVO fileControlRecord = new FileControlRecordVO();
				if (lineContent.equals(CommonConstants.ACH_RECORD_TYPE_BLOCK_CONTROL)) {
					fileControlRecord.setRecordIdentifier(CommonConstants.ORIGINATION_BLOCK_CONTROL);

				} else {
					fileControlRecord.setFieldDetailVO(NachaUtil.getBaseMetaData(nachaMetaDataDao, CommonConstants.ORIGINATION_FILE_CONTROL, 0));
					getLoadedRecordVO(lineContent, fileControlRecord);
					fileControlRecord.setRecordIdentifier(CommonConstants.ORIGINATION_FILE_CONTROL);
				}
				nachaGenericRecordVO = (NachaGenericRecordVO) fileControlRecord;
			}
			if (firstChar.equals(CommonConstants.ACH_RECORD_TYPE_BATCH_HEADER)) {
				BatchHeaderRecordVO batchHeaderRecord = new BatchHeaderRecordVO();
				batchHeaderRecord.setFieldDetailVO(NachaUtil.getBaseMetaData(nachaMetaDataDao, CommonConstants.ORIGINATION_BATCH_HEADER, 0));
				getLoadedRecordVO(lineContent, batchHeaderRecord);
				batchHeaderRecord.setRecordIdentifier(CommonConstants.ORIGINATION_BATCH_HEADER);
				nachaGenericRecordVO = (NachaGenericRecordVO) batchHeaderRecord;
			}
			if (firstChar.equals(CommonConstants.ACH_RECORD_TYPE_BATCH_CONTROL)) {
				BatchControlRecordVO batchControlRecord = new BatchControlRecordVO();
				batchControlRecord.setFieldDetailVO(NachaUtil.getBaseMetaData(nachaMetaDataDao, CommonConstants.ORIGINATION_BATCH_CONTROL, 0));
				getLoadedRecordVO(lineContent, batchControlRecord);
				batchControlRecord.setRecordIdentifier(CommonConstants.ORIGINATION_BATCH_CONTROL);
				nachaGenericRecordVO = (NachaGenericRecordVO) batchControlRecord;
			}
			if (firstChar.equals(CommonConstants.ACH_RECORD_TYPE_ENTRY_DETAIL)) {
				EntryDetailRecordVO entryDetailRecord = new EntryDetailRecordVO();
				entryDetailRecord.setRecordIdentifier(CommonConstants.ORIGINATION_ENTRY_DETAIL);
				nachaGenericRecordVO = (NachaGenericRecordVO) entryDetailRecord;
			}
			if (firstChar.equals(CommonConstants.ACH_RECORD_TYPE_ADDENDA)) {
				AddendaRecordVO addendaRecordVO = new AddendaRecordVO();
				addendaRecordVO.setFieldDetailVO(NachaUtil.getBaseMetaData(nachaMetaDataDao, CommonConstants.RETURN_ADDENDA, 0));
				getLoadedRecordVO(lineContent, addendaRecordVO);
				addendaRecordVO.setRecordIdentifier(CommonConstants.ORIGINATION_ADDENDA);
				nachaGenericRecordVO = (NachaGenericRecordVO) addendaRecordVO;
			}
		} catch (Exception e) {
			throw new SLBusinessServiceException(e.getMessage(),e);
		}
		return nachaGenericRecordVO;
	}

	/**
	 * Sets the nacha meta data dao.
	 * 
	 * @param nachaMetaDataDao the new nacha meta data dao
	 */
	public void setNachaMetaDataDao(INachaMetaDataDao nachaMetaDataDao) {

		this.nachaMetaDataDao = nachaMetaDataDao;
	}

}
