/**
 * 
 */
package com.servicelive.wallet.batch.gl;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.util.StackTraceHelper;
import com.servicelive.wallet.batch.BaseFileProcessor;
import com.servicelive.wallet.batch.IProcessor;
import com.servicelive.wallet.batch.gl.dao.IFiscalCalendarDao;
import com.servicelive.wallet.batch.gl.dao.IGLDao;
import com.servicelive.wallet.batch.gl.dao.ISHCOMSDao;
import com.servicelive.wallet.batch.gl.vo.FiscalCalendarVO;
import com.servicelive.wallet.batch.gl.vo.GLDetailVO;
import com.servicelive.wallet.batch.gl.vo.GLFeedVO;
import com.servicelive.wallet.batch.gl.vo.GLSummaryVO;
import com.servicelive.wallet.batch.gl.vo.GlProcessLogVO;

/**
 * The Class GLProcessor.
 */
public class GLProcessor extends BaseFileProcessor implements IProcessor {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(GLProcessor.class.getName());

	/** The gl dao. */
	private IGLDao glDao = null;

	/** The gl transformer. */
	private GLTransformer glTransformer;

	/** The gl writer. */
	private IGLWriter glWriter;

	/** The shc supplier dao. */
	private ISHCOMSDao shcSupplierDao = null;

	/**
	 * Creates the file name.
	 * 
	 * @return the string
	 */
	private String createFileName() {

		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MMddyyyy_HHmmss");
		String dateStr = sdf1.format(cal.getTime());

		String fileName = dateStr + "_gl.dat";
		return fileName;
	}

	/**
	 * Gets the gL admin.
	 * 
	 * @return the gL admin
	 * 
	 * @throws DataServiceException 
	 */
	private String getGLAdmin() throws DataServiceException {

		return applicationProperties.getPropertyValue("gl_admin");
	}

	/**
	 * Gl feed log entry.
	 * 
	 * @param fileName 
	 * @param startDate 
	 * @param endDate 
	 * 
	 * @return the integer
	 * 
	 * @throws IOException 
	 */
	private Integer glFeedLogEntry(String fileName, Date startDate, Date endDate) throws IOException {

		java.util.Date today = new java.util.Date();
		GlProcessLogVO glProcessLogVO = new GlProcessLogVO();
		Timestamp processedDate = new java.sql.Timestamp(today.getTime());
		glProcessLogVO.setProcessDate(processedDate);
		glProcessLogVO.setFileName(fileName);
		glProcessLogVO.setProcessSuccesfull(1);
		glProcessLogVO.setInitiatedManually(0);
		glProcessLogVO.setFromDate(startDate);
		glProcessLogVO.setToDate(endDate);
		Integer glProcessLogId = glDao.createGLProcessLog(glProcessLogVO);
		return glProcessLogId;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.IProcessor#process()
	 */
	public void process() throws SLBusinessServiceException {
		
		Calendar cal = Calendar.getInstance();

		// Go one day back to get the GL information
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		java.sql.Timestamp startDate = new java.sql.Timestamp(cal.getTime().getTime());
		long start = System.currentTimeMillis();
		process(startDate);
		long end = System.currentTimeMillis();
		logger.info("SL-19510 Test Logger: GLProcessor.process() total execution time:"+(end-start));
	}
	
	/**
	 * This is public because it is called from MarketFrontEnd
	 * which passes in date
	 * @param startDate
	 * @throws SLBusinessServiceException
	 */
	public void process(Date startDate) throws SLBusinessServiceException {
		try {
			try {

				List<GLSummaryVO> glSummaryList;
				Calendar cal = Calendar.getInstance();
				cal.setTime(startDate);
				cal.set(Calendar.HOUR_OF_DAY, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				java.sql.Timestamp endDate = new java.sql.Timestamp(cal.getTime().getTime());
				logger.info("End date is " + endDate);

				// Get list of transactions within dates entered. By default the date is the previous day.
				long glSummaryListStart = System.currentTimeMillis();
				glSummaryList = glDao.getLedgerSummary(endDate);
				long glSummaryListEnd = System.currentTimeMillis();
				logger.info("SL-19510 Test Logger: GLProcessor.process() glSummaryList execution time:"+(glSummaryListEnd-glSummaryListStart));
				Integer glProcessLogId = null;
				String fileName = createFileName();
				if (glSummaryList != null) logger.info("found " + glSummaryList.size() + " gl entries");
				if (glSummaryList != null && !glSummaryList.isEmpty() ) {
					// Entry for glProcessLogId
					
					long glProcessLogIdStart = System.currentTimeMillis();
					glProcessLogId = glFeedLogEntry(fileName, startDate, endDate);
					logger.info("SL-19510 Test Logger: GLProcessor.process() glProcessLogId:"+glProcessLogId);
					long glProcessLogIdEnd = System.currentTimeMillis();
					logger.info("SL-19510 Test Logger: GLProcessor.process() glProcessLogId execution time:"+(glProcessLogIdEnd-glProcessLogIdStart));
					
					long ledgerUpdateStart = System.currentTimeMillis();
					boolean ledgerUpdate = glDao.updateLedgerEntryGLProcessed(glSummaryList);
					long ledgerUpdateEnd = System.currentTimeMillis();
					logger.info("SL-19510 Test Logger: GLProcessor.process() ledgerUpdate execution time:"+(ledgerUpdateEnd-ledgerUpdateStart));

					logger.info("Ledger entries are updated " + ledgerUpdate);
										
					// To update the shc_order table
					long skuUpdateStart = System.currentTimeMillis();
					boolean skuUpdate = updateGLSKUProcessed(glProcessLogId, glSummaryList);
					long skuUpdateEnd = System.currentTimeMillis();
					logger.info("SL-19510 Test Logger: GLProcessor.process() skuUpdate execution time:"+(skuUpdateEnd-skuUpdateStart));
					logger.info("SKU entries are updated " + skuUpdate);
					
					// To invoke GLHistory table entry through stored proc
					long insertSHCGLHistoryRecordStart = System.currentTimeMillis();
					shcSupplierDao.insertSHCGLHistoryRecord(glProcessLogId);
					long insertSHCGLHistoryRecordEnd = System.currentTimeMillis();
					logger.info("SL-19510 Test Logger: GLProcessor.process() insertSHCGLHistoryRecord execution time:"+(insertSHCGLHistoryRecordEnd-insertSHCGLHistoryRecordStart));
					
					// Invoke the stored proc to insert data into rpt_gl_details table
					long runGLDetailsStart = System.currentTimeMillis();
					glDao.runGLDetails(glProcessLogId);
					long runGLDetailsEnd = System.currentTimeMillis();
					logger.info("SL-19510 Test Logger: GLProcessor.process() runGLDetails execution time:"+(runGLDetailsEnd-runGLDetailsStart));
					
					// Retrieve data exclusive of HSR for GL output file from rpt_gl_details table
					long glDetailVOListNoHSRStart = System.currentTimeMillis();
					List<GLDetailVO> glDetailVOListNoHSR = glDao.getGLDetails(glProcessLogId, false);
					long glDetailVOListNoHSREnd = System.currentTimeMillis();
					logger.info("SL-19510 Test Logger: GLProcessor.process() glDetailVOListNoHSR execution time:"+(glDetailVOListNoHSREnd-glDetailVOListNoHSRStart));
					
					long writeGLFeedNoHSRStart = System.currentTimeMillis();
					ArrayList<GLFeedVO> glFeedItemList = glTransformer.convertGLDetailVOListToGLFeedVOList(glDetailVOListNoHSR,startDate);
					glWriter.writeGLFeed(glFeedItemList, fileName);
					long writeGLFeedNoHSREnd = System.currentTimeMillis();
					logger.info("SL-19510 Test Logger: GLProcessor.process() writeGLFeedNoHSR execution time:"+(writeGLFeedNoHSREnd-writeGLFeedNoHSRStart));
					
					// Retrieve data of HSR for GL output file from rpt_gl_details table
					long glDetailVOListHSRStart = System.currentTimeMillis();
					List<GLDetailVO> glDetailVOListHSR = glDao.getGLDetails(glProcessLogId, true);
					long glDetailVOListHSREnd = System.currentTimeMillis();
					logger.info("SL-19510 Test Logger: GLProcessor.process() glDetailVOListHSR execution time:"+(glDetailVOListHSREnd-glDetailVOListHSRStart));
					
					long writeGLFeedHSRStart = System.currentTimeMillis();
					glFeedItemList = glTransformer.convertGLDetailVOListToGLFeedVOList(glDetailVOListHSR,startDate);
					glWriter.writeGLFeed(glFeedItemList, "3000_"+fileName);	
					long writeGLFeedHSREnd = System.currentTimeMillis();
					logger.info("SL-19510 Test Logger: GLProcessor.process() writeGLFeedHSR execution time:"+(writeGLFeedHSREnd-writeGLFeedHSRStart));
					//isUpdateGLProcessedDone = (ledgerUpdate && skuUpdate);
				}
			} catch (DataAccessException dae) {
				logger.error("writeGLFeed-->DataAccessException-->", dae);
				emailTemplateBO.sendTemplateEmail(getGLAdmin(), CommonConstants.SERVICE_LIVE_MAILID, CommonConstants.EMAIL_TEMPLATE_GLFEED_FAILED);
				throw new SLBusinessServiceException("writeGLFeed-->DataAccessException-->", dae);
			} catch (Exception e) {
				logger.error("writeGLFeed-->Exception-->", e);
				emailTemplateBO.sendTemplateEmail(getGLAdmin(), CommonConstants.SERVICE_LIVE_MAILID, CommonConstants.EMAIL_TEMPLATE_GLFEED_FAILED);
				throw new SLBusinessServiceException("writeGLFeed-->EXCEPTION-->", e);
			}
		} catch (DataServiceException e) {
			logger.error("writeGLFeed-->DataServiceException-->", e);
			throw new SLBusinessServiceException("Error while sending email.", e);
		}
		// return isUpdateGLProcessedDone;
	}
	
	private boolean updateGLSKUProcessed(Integer glProcessLogId, List<GLSummaryVO> glSummaryList) throws Exception {
		boolean updated = true;
		int soIdCount = 0;
		List<String> soIdList = new ArrayList<String>();
		if (glSummaryList == null || glSummaryList.isEmpty()) {
			updated = false;
		} else {
			// Get unique list of so_ids
			Set<String> soIdSet = new HashSet<String>();
			for (GLSummaryVO glSummaryVO : glSummaryList) {
				if (glSummaryVO.getSoId() != null) {
					soIdSet.add(glSummaryVO.getSoId());
					soIdCount++;
				}
			}
			if (soIdCount > 0) {
				soIdList.addAll(soIdSet);
				long updateGLSKUProcessedStart = System.currentTimeMillis();
				shcSupplierDao.markServiceOrdersBeingProcessed(glProcessLogId, soIdList);
				long updateGLSKUProcessedEnd = System.currentTimeMillis();
				logger.info("SL-19510 Test Logger: GLProcessor.updateGLSKUProcessed() updateGLSKUProcessed execution time:"+(updateGLSKUProcessedEnd-updateGLSKUProcessedStart));
			} else {
				updated = false;
			}
		}
		return updated;
	}

	/**
	 * Sets the gl dao.
	 * 
	 * @param glDao the new gl dao
	 */
	public void setGlDao(IGLDao glDao) {

		this.glDao = glDao;
	}

	/**
	 * Sets the gl transformer.
	 * 
	 * @param glTransformer the new gl transformer
	 */
	public void setGlTransformer(GLTransformer glTransformer) {

		this.glTransformer = glTransformer;
	}

	/**
	 * Sets the gl writer.
	 * 
	 * @param glWriter the new gl writer
	 */
	public void setGlWriter(IGLWriter glWriter) {

		this.glWriter = glWriter;
	}

	/**
	 * Sets the shc supplier dao.
	 * 
	 * @param shcSupplierDao the new shc supplier dao
	 */
	public void setShcSupplierDao(ISHCOMSDao shcSupplierDao) {

		this.shcSupplierDao = shcSupplierDao;
	}

}
