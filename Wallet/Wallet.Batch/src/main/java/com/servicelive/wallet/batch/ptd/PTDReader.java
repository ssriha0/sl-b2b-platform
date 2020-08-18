package com.servicelive.wallet.batch.ptd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.servicelive.common.CommonConstants;
import com.servicelive.wallet.batch.ptd.dao.IPTDDao;
import com.servicelive.wallet.batch.ptd.vo.PTDFileVO;
import com.servicelive.wallet.batch.ptd.vo.PTDRecordTypeVO;
import com.servicelive.wallet.batch.ptd.vo.PTDRecordVO;
import com.servicelive.common.util.BoundedBufferedReader;
// TODO: Auto-generated Javadoc
/**
 * The Class PTDReader.
 */
public class PTDReader {

	/** The ptd dao. */
	private IPTDDao ptdDao;

	/**
	 * Description:.
	 * 
	 * @param lineRecord 
	 * @param ptdRecordVO 
	 */
	private void getLoadedRecordTypeVO(String lineRecord, PTDRecordVO ptdRecordVO) {

		if (ptdRecordVO != null) {
			List<PTDRecordTypeVO> ptdRecordTypeList = ptdRecordVO.getPtdRecordTypeList();
			for (int i = 0; i < ptdRecordTypeList.size(); i++) {
				PTDRecordTypeVO ptd = (PTDRecordTypeVO) ptdRecordTypeList.get(i);
				ptd.setFieldValue(lineRecord.substring(ptd.getFieldStartposition(), ptd.getFieldEndPosition()));
			}
		}

	}

	/**
	 * Description: Creates a PTDFileVO object from a file.
	 * 
	 * @param file 
	 * 
	 * @return 
	 * 
	 * @throws Exception <code>PTDFileVO</code>
	 */
	public PTDFileVO objectizePTDFile(String file) throws Exception {

		FileReader fr = null;
		BoundedBufferedReader br = null;
		PTDFileVO ptdFileVO = new PTDFileVO();
		PTDRecordVO ptdFileHeaderRecordVO = null;
		PTDRecordVO ptdFileTrailerRecordVO = null;
		List<PTDRecordVO> ptdtransactionList = new ArrayList<PTDRecordVO>();
		try {
			fr = new FileReader(file);
			br = new BoundedBufferedReader(fr,100000,BoundedBufferedReader.DEFAULT_MAX_LINE_LENGTH); 
			String lineContent = null;
			while ((lineContent = br.readLine()) != null) {
				PTDRecordVO ptdRecordVO = processLine(lineContent);
				if (ptdRecordVO.getRecordIdentifier().equals(CommonConstants.PTD_HEADER)) {
					ptdFileHeaderRecordVO = new PTDRecordVO();
					ptdFileHeaderRecordVO.setPtdRecordTypeList(ptdDao.getPTDRecordTypeTemplate(CommonConstants.PTD_HEADER));
					getLoadedRecordTypeVO(lineContent, ptdFileHeaderRecordVO);
				} else if (ptdRecordVO.getRecordIdentifier().equals(CommonConstants.PTD_TRANSACTION)) {
					PTDRecordVO ptdTransactionRecordVO = new PTDRecordVO();
					ptdTransactionRecordVO.setPtdRecordTypeList(ptdDao.getPTDRecordTypeTemplate(CommonConstants.PTD_TRANSACTION));
					getLoadedRecordTypeVO(lineContent, ptdTransactionRecordVO);
					ptdtransactionList.add(ptdTransactionRecordVO);
				} else if (ptdRecordVO.getRecordIdentifier().equals(CommonConstants.PTD_TRAILER)) {
					ptdFileTrailerRecordVO = new PTDRecordVO();
					ptdFileTrailerRecordVO.setPtdRecordTypeList(ptdDao.getPTDRecordTypeTemplate(CommonConstants.PTD_TRAILER));
					getLoadedRecordTypeVO(lineContent, ptdFileTrailerRecordVO);

				}

			}
			ptdFileVO.setPtdFileHeaderRecordVO(ptdFileHeaderRecordVO);
			ptdFileVO.setPtdTransactionList(ptdtransactionList);
			ptdFileVO.setPtdFileTrailerRecordVO(ptdFileTrailerRecordVO);
		} 
		
		catch(Exception e)
		{
			throw e;
		}
		finally {
			fr.close();
			br.close();
		}
		return ptdFileVO;
	}

	/**
	 * Description:.
	 * 
	 * @param lineContent 
	 * 
	 * @return <code>PTDRecordVO</code>
	 */
	private PTDRecordVO processLine(String lineContent) {

		String firstChar = lineContent.substring(0, 1);
		PTDRecordVO ptdRecordVO = null;
		if ("0".equals(firstChar)) {
			ptdRecordVO = new PTDRecordVO();
			ptdRecordVO.setRecordIdentifier("PTD_HEADER");
			// getLoadedRecordTypeVO(lineContent,ptdRecordVO);
		} else if ("1".equals(firstChar)) {
			ptdRecordVO = new PTDRecordVO();
			ptdRecordVO.setRecordIdentifier("PTD_TRANSACTION");
		} else if ("9".equals(firstChar)) {
			ptdRecordVO = new PTDRecordVO();
			ptdRecordVO.setRecordIdentifier("PTD_TRAILER");
		}

		return ptdRecordVO;
	}

	/**
	 * Sets the ptd dao.
	 * 
	 * @param ptdDao the new ptd dao
	 */
	public void setPtdDao(IPTDDao ptdDao) {

		this.ptdDao = ptdDao;
	}
}
