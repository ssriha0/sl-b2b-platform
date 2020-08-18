package com.servicelive.wallet.batch.gl.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servicelive.common.ABaseDao;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.batch.gl.vo.ShopifyDetailsVO;

// TODO: Auto-generated Javadoc
/**
 * The Class SHCOMSDao.
 */
public class SHCOMSDao extends ABaseDao implements ISHCOMSDao {

	// Dependency on service order constants we need to refactor this
	/** The Constant SERVICE_ORDER_CLOSED_STATUS. */
	public final static int SERVICE_ORDER_CLOSED_STATUS = 180;

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.ISHCOMSDao#insertSHCGLHistoryRecord(java.lang.Integer)
	 */
	public void insertSHCGLHistoryRecord(Integer glProcessLogId) throws DataServiceException {

		insert("insertSHCGLHistory.storeproc", glProcessLogId);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.ISHCOMSDao#updateOMSSKU(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public void markServiceOrdersBeingProcessed(Integer glProcessId, List<String> soIdList) {
		Map updateOMSData = new HashMap();
		updateOMSData.put("glProcessId", glProcessId);
		updateOMSData.put("soIdList", soIdList);
		update("so_price.gl_process_id.update", updateOMSData);
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.ISHCOMSDao#getShopifyOrders(java.util.Date, java.util.Date, String)
	 */
	public List<ShopifyDetailsVO> getRelayOrderDetails(Date startDate, Date endDate, String status) throws DataServiceException {
		
		List<ShopifyDetailsVO> shopifyDetails = null;
		
		try{
			Map<String, Object> params = new HashMap <String, Object>();
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("status", status);
			shopifyDetails = (List<ShopifyDetailsVO>)queryForList("getRelayOrderDetails.select", params);
			
		}catch(Exception e){
			throw new DataServiceException("Error while fetching relay order details in SHCOMSDao.getRelayOrderDetails()", e);
		}
		
		return shopifyDetails;
	}
	
	public List<ShopifyDetailsVO> getOrderDetailsForGL(Date startDate, Date endDate, String status, List<String> ruleList, String buyerId) throws DataServiceException {
		
		List<ShopifyDetailsVO> shopifyDetails = null;
		
		try{
			Map<String, Object> params = new HashMap <String, Object>();
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("status", status);
			params.put("ruleList", ruleList);
			params.put("buyerId", buyerId);

			shopifyDetails = (List<ShopifyDetailsVO>)queryForList("getRelayOrderDetailsLedger.select", params);
			
		}catch(Exception e){
			throw new DataServiceException("Error while fetching relay order details in SHCOMSDao.getRelayOrderDetails()", e);
		}
		
		return shopifyDetails;
	}	

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.ISHCOMSDao#getShopifyOrders(java.util.Date, java.util.Date, String)
	 */
	public List<ShopifyDetailsVO> getShopifyOrderDetails(Date startDate, Date endDate, String status) throws DataServiceException {
		
		List<ShopifyDetailsVO> shopifyDetails = null;
		
		try{
			Map<String, Object> params = new HashMap <String, Object>();
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("status", status);
			shopifyDetails = (List<ShopifyDetailsVO>)queryForList("getShopifyOrderDetails.select", params);
			
		}catch(Exception e){
			throw new DataServiceException("Error while fetching shopify order details in SHCOMSDao.getShopifyOrderDetails()", e);
		}
		
		return shopifyDetails;
	}
	
	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.ISHCOMSDao#updateSOGLStatus(java.util.List)
	 */
	public void updateSOGLStatus( List<ShopifyDetailsVO> shopifyUpdateList)throws DataServiceException{
		try{
			if(null!=shopifyUpdateList && !shopifyUpdateList.isEmpty()){
				batchUpdate("status.update", shopifyUpdateList);
			}
		}catch(Exception e){
			throw new DataServiceException("Error while fetching shopify order details in SHCOMSDao.updateSOGLStatus()", e);
		}
	}
}
