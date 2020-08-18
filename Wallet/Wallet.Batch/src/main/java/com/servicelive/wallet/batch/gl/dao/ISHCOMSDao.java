package com.servicelive.wallet.batch.gl.dao;

import java.util.Date;
import java.util.List;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.batch.gl.vo.ShopifyDetailsVO;

// TODO: Auto-generated Javadoc
/**
 * The Interface ISHCOMSDao.
 */
public interface ISHCOMSDao {

	/**
	 * Insert shcgl history record.
	 * 
	 * @param glProcessLogId 
	 * 
	 * @throws DataServiceException 
	 */
	public void insertSHCGLHistoryRecord(Integer glProcessLogId) throws DataServiceException;

	/**
	 * Method to get the summary accounts on all unprocessed records oms sku's.
	 * 
	 * @param glProcessId
	 * @param soIdList 
	 */
	public void markServiceOrdersBeingProcessed(Integer glProcessId, List<String> soIdList);

	/**
	 * Method to get the order details of buyer 9000 orders
	 * based on status of SO
	 * 
	 * @param startDate
	 * @param endDate
	 * 
	 * retrun List<ShopifyDetailsVO>
	 */
	public List<ShopifyDetailsVO> getShopifyOrderDetails(Date startDate, Date endDate, String status) throws DataServiceException;
	public List<ShopifyDetailsVO> getOrderDetailsForGL(Date startDate, Date endDate, String status, List<String> ruleList, String buyerId) throws DataServiceException;

	/**
	 * Method to update the shopify_gl_state in so_price table in order to avoid the multiple picking of data by batch.
	 * 
	 * @param glProcessId
	 * @param soIdList 
	 */
	public void updateSOGLStatus( List<ShopifyDetailsVO> shopifyUpdateList)throws DataServiceException;

}
