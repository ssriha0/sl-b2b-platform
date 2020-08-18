package com.servicelive.wallet.batch.gl.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.batch.gl.vo.GLDetailVO;
import com.servicelive.wallet.batch.gl.vo.GLSummaryVO;
import com.servicelive.wallet.batch.gl.vo.GlProcessLogVO;
import com.servicelive.wallet.batch.gl.vo.ShopifyGLProcessLogVO;
import com.servicelive.wallet.batch.gl.vo.ShopifyGLRuleVO;

// TODO: Auto-generated Javadoc
/**
 * The Class GLDao.
 */
public class GLDao extends ABaseDao implements IGLDao {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(GLDao.class.getName());

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.IGLDao#createGLProcessLog(com.servicelive.wallet.batch.gl.vo.GlProcessLogVO)
	 */
	public Integer createGLProcessLog(GlProcessLogVO glProcessLogVO) {

		Integer glProcessLogId = (Integer) this.insert("gl_feed_long.insert", glProcessLogVO);
		return glProcessLogId;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.IGLDao#getGLDetails(java.lang.Integer)
	 */
	/*public List<GLDetailVO> getGLDetails(Integer glProcessLogId) throws DataServiceException {

		return (List<GLDetailVO>) queryForList("getGLDetails.query", glProcessLogId);
	}*/
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.IGLDao#getGLDetails(java.lang.Integer)
	 */
	public List<GLDetailVO> getGLDetails(Integer glProcessLogId, boolean isHSR) throws DataServiceException {

		if(isHSR){
			return (List<GLDetailVO>) queryForList("getGLDetailsHSR.query", glProcessLogId);			
		}else{
			return (List<GLDetailVO>) queryForList("getGLDetails.query", glProcessLogId);
		}

	}	

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.IGLDao#getLedgerSummary(java.util.Date, java.util.Date)
	 */
	public List<GLSummaryVO> getLedgerSummary(Date glDate) {
		//SL-20398 : setting time stamp of start date as 00:00:00 and 
		//the end date as (end date + 1) 00:00:00
		//as the date function is removed from the below query
		Map<String, Date> parameter = new HashMap<String, Date>();
		Date fromDate = glDate;
		Date toDate = glDate;
		if(null != glDate){
			Calendar fromDateCal = Calendar.getInstance();
			fromDateCal.setTime(glDate);
			fromDateCal.set(Calendar.HOUR_OF_DAY, 0);
			fromDateCal.set(Calendar.MINUTE, 0);
			fromDateCal.set(Calendar.SECOND, 0);
			fromDate = new java.sql.Date(fromDateCal.getTime().getTime());
		
			Calendar toDateCal = Calendar.getInstance();
			toDateCal.setTime(glDate);
			//adding 1 to end date to get all the transactions till previous day 23:59:59
			toDateCal.add(Calendar.DAY_OF_MONTH,1);
			toDateCal.set(Calendar.HOUR_OF_DAY, 0);
			toDateCal.set(Calendar.MINUTE, 0);
			toDateCal.set(Calendar.SECOND, 0);
			toDate = new java.sql.Date(toDateCal.getTime().getTime());
		}
		parameter.put("fromDate", fromDate);
		parameter.put("toDate", toDate);
		List<GLSummaryVO> glSummaryVOs = this.queryForList("gl_summary.query", parameter);
		return glSummaryVOs;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.IGLDao#runGLDetails(java.lang.Integer)
	 */
	public void runGLDetails(Integer glProcessLogId) throws DataServiceException {

		insert("runGLDetails.storeproc", glProcessLogId);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.IGLDao#updateLedgerEntryGLProcessed(java.util.List, java.lang.Integer)
	 */
	public boolean updateLedgerEntryGLProcessed(List<GLSummaryVO> ledgerEntries) {

		try {
			Integer latestProcessLogId = getLatestGLProcessLogId();
			logger.info("SL-19510 Test Logger: GLDao.updateLedgerEntryGLProcessed() latestProcessLogId:"+latestProcessLogId);
			
			for (GLSummaryVO glSummaryVO : ledgerEntries) {
				glSummaryVO.setGlProcessId(latestProcessLogId);
				continue;
			}
			update("ledger_entry_glprocessed.update", ledgerEntries);
			return true;
		} catch (Exception e) {
			logger.error("Problem occured in the update of GL related ledgerEntryIds");
			return false;
		}
	}
	
	public Integer getLatestGLProcessLogId() throws DataServiceException{
		
		return (Integer) queryForObject("gl_process_log_id.query", null);
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.IGLDao#getShopifyGLDetails()
	 * Method to fetch the GL details for buyer 9000
	 */
	public List<GLDetailVO> getShopifyGLDetails(Integer glProcessId) throws DataServiceException {

		List<GLDetailVO> glDetails = null;
		try{
			glDetails = (List<GLDetailVO>) queryForList("getShopifyGLDetails.query", glProcessId);
			
		}catch (Exception e) {
			throw new DataServiceException("Exception occurred while fetching the GL details for buyer 9000", e);
		}
		return glDetails;
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.IGLDao#getRulesForGL()
	 * Method to fetch the GL details for buyer 9000
	 */
	public List<ShopifyGLRuleVO> getCustomRulesForGL(String buyerId)throws DataServiceException{
		
		List<ShopifyGLRuleVO> glRuleDetails = null;
		try{
			glRuleDetails = (List<ShopifyGLRuleVO>) queryForList("getCustomGLRules.query", buyerId);
			
		}catch (Exception e) {
			throw new DataServiceException("Exception occurred while fetching the GL rules for buyer 9000", e);
		}
		return glRuleDetails;
	}	
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.IGLDao#getRulesForGL()
	 * Method to fetch the GL details for buyer 9000
	 */
	public List<ShopifyGLRuleVO> getRulesForGL()throws DataServiceException{
		
		List<ShopifyGLRuleVO> glRuleDetails = null;
		try{
			glRuleDetails = (List<ShopifyGLRuleVO>) queryForList("getShopifyGLRules.query", null);
			
		}catch (Exception e) {
			throw new DataServiceException("Exception occurred while fetching the GL rules for buyer 9000", e);
		}
		return glRuleDetails;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.IGLDao#insertGLDetails()
	 * Insert GL for 9000 buyer.
	 */
	public void insertGLDetails(List<GLDetailVO> glDetailsVO) throws DataServiceException{
		
		try{
			batchInsert("shopifyGLDetails.insert", glDetailsVO);
			
		}catch (Exception e) {
			throw new DataServiceException("Exception occurred while inserting the GL details for buyer 9000", e);
		}
	}

	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.IGLDao#insertGLDetailsLog()
	 * method to insert an entry in shopify_gl_process_log
	 */
	public Integer insertGLDetailsLog(ShopifyGLProcessLogVO shopifyGLProcessLogVO) throws DataServiceException{
		Integer glProcessLogId;
		try{
			 glProcessLogId = (Integer) this.insert("shopify_gl_process_log.insert", shopifyGLProcessLogVO);
		}catch (Exception e) {
			throw new DataServiceException("Exception occurred while inserting an entry in shopify_gl_process_log", e);
		}
		return glProcessLogId;
	}
}
