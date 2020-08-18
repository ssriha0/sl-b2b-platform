package com.newco.marketplace.translator.business;

import java.util.List;

import com.newco.marketplace.translator.dto.SkuPrice;
import com.newco.marketplace.webservices.dao.ShcErrorLogging;
import com.newco.marketplace.webservices.dao.ShcOrder;
import com.newco.marketplace.webservices.dto.StagingDetails;

public interface IStagingService {
	
	/**
	 * @param stagingDetails
	 * @param buyerID TODO
	 */
	public void stageDataAfterUnMarshalling(StagingDetails stagingDetails, int buyerID) throws Exception;
	
	/**
	 * @param shcOrder
	 * @param skus
	 */
	public void stageDataAfterTranslation(ShcOrder shcOrder, List<SkuPrice> skus,int buyerId);
	
	/**
	 * @param stagingServiceOrder
	 */
	public void stageDataAfterProcessing(ShcOrder shcOrder,List<ShcErrorLogging> shcErrorLoggingList) throws Exception;
	/**
     * @param String errorCode
     * @param String errorMessage
     * @param StagingDetails stagingData
     */
	public void persistErrors(String errorCode, String errorMessage, String orderNumber,String unitNumber);
	
	/**
	 * This method returns those closed orders for which NPS Call Close has not been processed yet.
	 * @return ShcOrder[] Array of Closed orders
	 */
	public ShcOrder[] retrieveClosedOrders();
	
	/**
     * Method to insert records to shc_npc_close_process_log table & update process_id in shc_order table, for closed records
	 * @param stagingDetails
	 * @param fileName
	 * @return int npsProcessId
	 */
	public int createNPSProcessLog(StagingDetails stagingDetails, String fileName);
	/**
	 * method to return the staging order
	 * @param orderNo
	 * @param unitNo
	 * @return
	 */
	public ShcOrder getShcOrder(String orderNo, String unitNo);
	
	/**
	 * method to return list of staging orders
	 * @param unitAndOrderNo
	 * @return
	 */
	public ShcOrder[] findShcOrders(List<String> unitAndOrderNo);

	/**
	 * method to return list of staging orders
	 * @param unitAndOrderNo
	 * @param testSuffix
	 * @return
	 */
	public ShcOrder[] findLatestShcOrdersWithOrderNumberMatchingBeforeTestSuffix(List<String> unitAndOrderNumbers, String testSuffix);
}
