package com.servicelive.wallet.batch.ofac;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.util.Cryptography;
import com.servicelive.wallet.batch.BaseFileProcessor;
import com.servicelive.wallet.batch.IProcessor;
import com.servicelive.wallet.batch.ofac.dao.IOFACDao;
import com.servicelive.wallet.batch.ofac.vo.OFACProcessQueueVO;

// TODO: Auto-generated Javadoc
/**
 * The Class OFACFileProcessor.
 */
public class OFACFileProcessor extends BaseFileProcessor implements IProcessor {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(OFACFileProcessor.class.getName());

	/** The cryptography. */
	Cryptography cryptography;

	public Cryptography getCryptography() {
		return cryptography;
	}

	/** The ofac dao. */
	IOFACDao ofacDaoWallet;

	public IOFACDao getOfacDaoWallet() {
		return ofacDaoWallet;
	}

	public void setOfacDaoWallet(IOFACDao ofacDaoWallet) {
		this.ofacDaoWallet = ofacDaoWallet;
	}

	/** The ofac writer. */
	OFACWriter ofacWriter;

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.IProcessor#process()
	 */
	public void process() throws SLBusinessServiceException {

		try {

			ArrayList<OFACProcessQueueVO> ofacSummaryList = ofacDaoWallet.getOfacData();
			for (int i = 0; i < ofacSummaryList.size(); i++) {
				if(StringUtils.isNotBlank(ofacSummaryList.get(i).getTaxPayerId()))
				{
						ofacSummaryList.get(i).setTaxPayerId(cryptography.decryptKey(ofacSummaryList.get(i).getTaxPayerId()));
				}
				else
				{
					ofacSummaryList.get(i).setTaxPayerId("");
				}
			}
			if (ofacSummaryList != null && !ofacSummaryList.isEmpty()) {
				Calendar cal = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy_HHmmss");
				String dateStr = sdf.format(cal.getTime());
				String fileDir = getFileDirectory("ofac_file_dir");
				String fileName = dateStr + "_KYC.txt";
				logger.info("fileName should be: " + fileName);
				ofacWriter.writeRecordsToFile(ofacSummaryList, fileDir, fileName);
			}
		}

		catch (DataAccessException dae) {
			logger.error("OFACFileImpl-->DataAccessException-->", dae);

			throw new SLBusinessServiceException("OFACFileImpl-->DataAccessException-->", dae);
		} catch (Exception e) {
			logger.error("OFACFileImpl-->Exception-->", e);

			throw new SLBusinessServiceException("OFACFileImpl-->EXCEPTION-->", e);

		}

	}

	/**
	 * Sets the cryptography.
	 * 
	 * @param cryptography the new cryptography
	 */
	public void setCryptography(Cryptography cryptography) {

		this.cryptography = cryptography;
	}

	/**
	 * Sets the ofac writer.
	 * 
	 * @param ofacWriter the new ofac writer
	 */
	public void setOfacWriter(OFACWriter ofacWriter) {

		this.ofacWriter = ofacWriter;
	}

}
