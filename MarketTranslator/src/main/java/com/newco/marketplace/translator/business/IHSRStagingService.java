/**
 * 
 */
package com.newco.marketplace.translator.business;

import java.util.List;

import com.servicelive.staging.domain.hsr.HSRStageOrder;
import com.servicelive.staging.domain.hsr.HSRStageOrders;

/**
 * @author hoza
 *
 */
//I truely beleive that this Serice should reside in OMSStaging ( again mis named project ) project. But Since I am following the what has been done
//Previously I keep doing the same. Someday there will be detachment from the TRANSLATOR
public interface IHSRStagingService {
	public HSRStageOrder getHSRStageOrderForOrderNOAndUnitNO(String orderNo, String unitNo) throws Exception;
	public Boolean persistStageOrder(HSRStageOrders stageOrders);
	/**
	 * This method will return Updated HSRStageObject with SO id updated. If It doesnt find the order no and unit no combination it will return null.
	 * @param orderNo
	 * @param unitNo
	 * @param soId
	 * @return
	 * @throws Exception
	 */
	public HSRStageOrder updateHSRStageOrderWithSoId(String orderNo, String unitNo, String soId) throws Exception;
	public HSRStageOrders findHsrOrders(List<List<String>> unitAndOrderNumbers);
	public HSRStageOrders findLatestHsrOrdersWithOrderNumberMatchingBeforeTestSuffix(
			List<List<String>> unitAndOrderNumbers, String testSuffix);
}
