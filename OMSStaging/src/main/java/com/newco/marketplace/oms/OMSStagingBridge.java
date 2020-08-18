/**
 * 
 */
package com.newco.marketplace.oms;

import java.util.List;

import com.newco.marketplace.webservices.dao.ShcErrorLogging;
import com.newco.marketplace.webservices.dao.ShcOrder;
import com.newco.marketplace.webservices.dao.ShcOrderAddOn;
import com.newco.marketplace.webservices.dao.SpecialtyAddOn;
import com.newco.marketplace.webservices.dto.StagingDetails;
import com.newco.marketplace.webservices.seiImpl.StageDispatchRequestor;

/**
 * @author mjoshi1
 * 
 */
public class OMSStagingBridge {

	private StageDispatchRequestor stageDispatch;

	/**
	 * 
	 * @param stagingDetails
	 * @throws Exception
	 */

	public StageDispatchRequestor getStageDispatch() {
		return stageDispatch;
	}

	public void setStageDispatch(StageDispatchRequestor stageDispatch) {
		this.stageDispatch = stageDispatch;
	}

	public void stageDataAfterUnMarshalling(StagingDetails stagingDetails)
			throws Exception {
		stageDispatch.stageDataAfterUnMarshalling(stagingDetails);
	}

	/**
	 * 
	 * @param shcOrder
	 * @throws Exception
	 */

	public void stageShcOrderAfterTranslation(ShcOrder shcOrder)
			throws Exception {
		stageDispatch.stageShcOrderAfterTranslation(shcOrder);
	}

	/**
	 * 
	 * @param shcOrder
	 * @param shcErrorLoggingList
	 * @throws Exception
	 */

	public void stageDataAfterProcessing(ShcOrder shcOrder,
			List<ShcErrorLogging> shcErrorLoggingList) throws Exception {
		stageDispatch.stageDataAfterProcessing(shcOrder, shcErrorLoggingList);
	}

	/**
	 * 
	 * @param specialtyCode
	 * @param stockNumber
	 * @return
	 * @throws Exception
	 */

	public SpecialtyAddOn getCallCollectAddon(String specialtyCode,
			String stockNumber) throws Exception {

		return stageDispatch.getCallCollectAddon(specialtyCode, stockNumber);

	}

	/**
	 * 
	 * @param errorCode
	 * @param errorMessage
	 * @param orderNumber
	 * @param unitNumber
	 * @throws Exception
	 */
	public void persistErrors(String errorCode, String errorMessage,
			String orderNumber, String unitNumber) throws Exception {
		stageDispatch.persistErrors(errorCode, errorMessage, orderNumber,
				unitNumber);
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<ShcOrder> retrieveClosedOrders(final int npsNumOrdersToClose) throws Exception {
		return stageDispatch.retrieveClosedOrders(npsNumOrdersToClose);
	}

	/**
	 * 
	 * @param stagingDetails
	 * @param npsCallCloseFileName
	 * @return
	 * @throws Exception
	 */
	public int createNPSProcessLog(StagingDetails stagingDetails,
			String npsCallCloseFileName) throws Exception {
		return stageDispatch.createNPSProcessLog(stagingDetails,
				npsCallCloseFileName);
	}

	/**
	 * 
	 * @param orderNo
	 * @param unitNo
	 * @return
	 * @throws Exception
	 */

	public ShcOrder getStageOrder(String orderNo, String unitNo)
			throws Exception {
		return stageDispatch.getStageOrder(orderNo, unitNo);
	}
	
	public List<ShcOrder> findStagingOrders(List<String> unitAndOrderNumbers) {
		return stageDispatch.findStagingOrders(unitAndOrderNumbers);
	}

	public List<ShcOrder> findLatestStagingOrdersWithOrderNumberMatchingBeforeTestSuffix(List<String> unitAndOrderNumbers, String testSuffix) {
		return stageDispatch.findLatestStagingOrdersWithOrderNumberMatchingBeforeTestSuffix(unitAndOrderNumbers, testSuffix);
	}

	/**
	 * 
	 * @param specialtyCode
	 * @param divisionCode
	 * @return
	 */
	public List<SpecialtyAddOn> getMiscAddOn(String specialtyCode,
			String divisionCode) {
		return stageDispatch.getMiscAddOn(specialtyCode, divisionCode);
	}

	/**
	 * 
	 * @param orderNumber
	 * @param unitNumber
	 * @return
	 */
	public List<ShcOrderAddOn> getShcOrderDetails(String orderNumber,
			String unitNumber) {

		return stageDispatch.getShcOrderDetails(orderNumber, unitNumber);

	}

	/**
	 * 
	 * @param stagingDetails
	 */
	public boolean stageUpsoldInfo(StagingDetails stagingDetails) {
		return stageDispatch.stageUpsoldInfo(stagingDetails);
	}

	/**
	 * 
	 * @param orderNo
	 * @param unitNo
	 * @param laborPrice
	 * @param permitPrice
	 * @param serviceFee
	 * @throws Exception
	 */
	public void updateFinalPrice(String orderNo, String unitNo,
			Double finalPrice, Double permitPrice, Double serviceFee)
			throws Exception {
		stageDispatch.updateFinalPrice(orderNo, unitNo, finalPrice,
				permitPrice, serviceFee);

	}
}
