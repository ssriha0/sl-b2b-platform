package com.servicelive.wallet.batch.gl.dao;

import java.util.Date;
import java.util.List;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.batch.gl.vo.GLDetailVO;
import com.servicelive.wallet.batch.gl.vo.GLSummaryVO;
import com.servicelive.wallet.batch.gl.vo.GlProcessLogVO;
import com.servicelive.wallet.batch.gl.vo.ShopifyGLProcessLogVO;
import com.servicelive.wallet.batch.gl.vo.ShopifyGLRuleVO;

// TODO: Auto-generated Javadoc
/**
 * The Interface IGLDao.
 */
public interface IGLDao {

	/**
	 * Creates the gl process log.
	 * 
	 * @param glProcessLogVO 
	 * 
	 * @return the integer
	 */
	public Integer createGLProcessLog(GlProcessLogVO glProcessLogVO);

	/**
	 * Gets the gL details.
	 * 
	 * @param glProcessLogId 
	 * @param isHSR
	 * 
	 * @return the gL details
	 * 
	 * @throws DataServiceException 
	 */
	public List<GLDetailVO> getGLDetails(Integer glProcessLogId, boolean isHSR) throws DataServiceException;

	/**
	 * Gets the ledger summary.
	 * 
	 * @param startDate 
	 * @param endDate 
	 * 
	 * @return the ledger summary
	 */
	public List<GLSummaryVO> getLedgerSummary(Date endDate);

	/**
	 * Run gl details.
	 * 
	 * @param glProcessLogId 
	 * 
	 * @throws DataServiceException 
	 */
	public void runGLDetails(Integer glProcessLogId) throws DataServiceException;

	/**
	 * Update ledger entry gl processed.
	 * 
	 * @param glSummaryList 
	 * @param latestProcessLogId 
	 * 
	 * @return true, if successful
	 */
	public boolean updateLedgerEntryGLProcessed(List<GLSummaryVO> glSummaryList);
	
	/**
	 * Get the GL Details for 9000 buyer.
	 * 
	 * @param glProcessId 
	 * @return the gL details
	 * 
	 * @throws DataServiceException 
	 */
	
	public List<GLDetailVO> getShopifyGLDetails(Integer glProcessId)throws DataServiceException;

	/**
	 * Get the GL Rules for 9000 buyer.
	 * 
	 * @return List<ShopifyGLRuleVO>
	 * 
	 * @throws DataServiceException 
	 */
	public List<ShopifyGLRuleVO> getRulesForGL()throws DataServiceException;
	public List<ShopifyGLRuleVO> getCustomRulesForGL(String buyerId)throws DataServiceException;

	/**
	 * Insert GL for 9000 buyer.
	 * 
	 * @param glDetailsVO
	 *  
	 * @throws DataServiceException 
	 */
	public void insertGLDetails(List<GLDetailVO> glDetailsVO) throws DataServiceException;

	/**
	 * Insert an entry in shopify_gl_process_log
	 * 
	 * @param shopifyGLProcessLogVO
	 * @return Integer
	 *  
	 * @throws DataServiceException 
	 */
	public Integer insertGLDetailsLog(ShopifyGLProcessLogVO shopifyGLProcessLogVO) throws DataServiceException;
}
