package com.newco.marketplace.webservices.sei;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.newco.marketplace.webservices.dao.ShcErrorLogging;
import com.newco.marketplace.webservices.dao.ShcOrder;
import com.newco.marketplace.webservices.dao.ShcOrderAddOn;
import com.newco.marketplace.webservices.dao.SpecialtyAddOn;
import com.newco.marketplace.webservices.dto.StagingDetails;


@WebService(name="StageOrderSEI", targetNamespace="http://sei.webservices.marketplace.newco.com")
public interface StageOrderSEI {

	
	@WebMethod(operationName = "stageDataAfterUnMarshalling")
	public void stageDataAfterUnMarshalling(StagingDetails stagingDetails) throws Exception;
	
	@WebMethod(operationName = "stageDataAfterProcessing")
	public void stageDataAfterProcessing(ShcOrder shcOrder,List<ShcErrorLogging> shcErrorLoggingList) throws Exception;
	
	@WebMethod(operationName = "persistErrors")
	public void persistErrors(String errorCode, String errorMessage, String orderNumber,String unitNumber);
	

	@WebMethod(operationName = "retrieveClosedOrders")
	public List<ShcOrder> retrieveClosedOrders(int npsOrderToClose);
	
	@WebMethod(operationName = "stageShcOrderAfterTranslation")
	public void stageShcOrderAfterTranslation(ShcOrder shcOrder);
	
	/**
     * Method to insert records to shc_npc_close_process_log table & update process_id in shc_order table, for closed records
	 * @param stagingDetails
	 * @param fileName
	 * @return int npsProcessId
	 */
	@WebMethod(operationName = "createNPSProcessLog")
	public int createNPSProcessLog(StagingDetails stagingDetails, String fileName);
	
	/**
	 * webservice call will be made to this method to do CloseSO updates for staging
	 * @param stagingDetails
	 * @return boolean isSuccess
	 */
	@WebMethod(operationName = "stageUpsoldInfo")
	public boolean stageUpsoldInfo(StagingDetails stagingDetails);
	/**
	 * Method to return the staging order based on the ordernumber and the unit no.
	 * @param orderNumber
	 * @param unitNumber
	 * @return
	 */
	@WebMethod(operationName = "getShcOrderDetails")
	public List<ShcOrderAddOn> getShcOrderDetails(String orderNumber, String unitNumber);
	
	@WebMethod(operationName = "getAddOnsWithMisc" )
	public List<SpecialtyAddOn> getMiscAddOn(String specialtyCode, String divisionCode);
	
	
	/**
	 * Updates the final price for regular and permit skus.
	 * 
	 * @param orderNo
	 * @param unitNo
	 * @param laborPrice
	 * @param permitPrice
	 * @param serviceFee
	 * @throws Exception
	 */
	@WebMethod(operationName = "updateFinalPrice")
	public void updateFinalPrice(String orderNo, String unitNo, Double laborPrice, Double permitPrice, Double serviceFee) throws Exception;
	/**
	 * method to return the shc order
	 * @param orderNo
	 * @param unitNo
	 * @return
	 * @throws Exception
	 */
	@WebMethod (operationName = "getStageOrder")
	public ShcOrder getStageOrder(String orderNo, String unitNo) throws Exception;
	
	/**
	 * 
	 * @param specialtyCode
	 * @param stockNumber
	 * @return
	 * @throws Exception
	 */
	@WebMethod (operationName = "getCallCollectAddon")
	public SpecialtyAddOn getCallCollectAddon(String specialtyCode, String stockNumber ) throws Exception;
}