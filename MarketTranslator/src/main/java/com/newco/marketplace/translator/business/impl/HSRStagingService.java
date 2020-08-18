/**
 * 
 */
package com.newco.marketplace.translator.business.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.newco.marketplace.translator.business.IHSRStagingService;
import com.servicelive.staging.dao.hsr.IHSRStagingOrderDao;
import com.servicelive.staging.domain.hsr.HSRStageOrder;
import com.servicelive.staging.domain.hsr.HSRStageOrders;

/**
 * @author hoza
 *
 */
public class HSRStagingService implements IHSRStagingService{
	
	private IHSRStagingOrderDao hsrStagingOrderdao;
	private Logger logger = Logger.getLogger(HSRStagingService.class);
	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.business.IHSRStagingService#getHSRStageOrder(java.lang.String, java.lang.String)
	 */
	@Transactional (propagation = Propagation.SUPPORTS)
	public HSRStageOrder getHSRStageOrderForOrderNOAndUnitNO(String orderNo, String unitNo) throws Exception {

		return hsrStagingOrderdao.findByOrderNoAndUnitNo(orderNo,unitNo);
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.business.IHSRStagingService#persistStageOrder(java.util.List)
	 */
	@Transactional ( propagation = Propagation.REQUIRED)
	public Boolean persistStageOrder(HSRStageOrders stageOrders) {
	
		Boolean result = Boolean.FALSE;
		try {
			for ( HSRStageOrder stageOrder : stageOrders.getStageOrders() ) {
				try{
					hsrStagingOrderdao.update(stageOrder);
				//	hsrStagingOrderdao.save(stageOrder);
				}catch(Exception e) {
					String message = " Exception in staging " + stageOrders.getInputFilenameHoldingThisOrders() + " While processing order with \n Order NO = "+ stageOrder.getOrderNumber() + " and \n Unit Number "+ stageOrder.getUnitNumber();
					logger.error(message, e);
					result =  Boolean.FALSE;
				}
			}
			result = Boolean.TRUE;
		} catch (Exception e) {
			logger.error("Exception in staging the Objects", e);
			result = Boolean.FALSE;
		}
		
		return result;
	}

	/**
	 * @return the hsrStagingOrderdao
	 */
	public IHSRStagingOrderDao getHsrStagingOrderdao() {
		return hsrStagingOrderdao;
	}

	/**
	 * @param hsrStagingOrderdao the hsrStagingOrderdao to set
	 */
	public void setHsrStagingOrderdao(IHSRStagingOrderDao hsrStagingOrderdao) {
		this.hsrStagingOrderdao = hsrStagingOrderdao;
	}

	/* (non-Javadoc)
	 * @see com.newco.marketplace.translator.business.IHSRStagingService#updateHSRStageOrderWithSoId(java.lang.String, java.lang.String, java.lang.String)
	 */
	public HSRStageOrder updateHSRStageOrderWithSoId(String orderNo,
			String unitNo, String soId) throws Exception {
		
		return hsrStagingOrderdao.updateSoId(orderNo, unitNo, soId);
	}

	public HSRStageOrders findHsrOrders(List<List<String>> unitAndOrderNumbers) {
		List<String> unitAndOrderNumbersList = convertListChildrenToStrings(unitAndOrderNumbers);
		List<HSRStageOrder> hsrStageOrderList = 
			hsrStagingOrderdao.findAllByUnitAndOrderNumbers(unitAndOrderNumbersList);
		return convertToListWrapper(hsrStageOrderList);		
	}

	private HSRStageOrders convertToListWrapper(
			List<HSRStageOrder> hsrStageOrderList) {
		HSRStageOrders hsrStageOrders = new HSRStageOrders();
		hsrStageOrders.setStageOrders(hsrStageOrderList);
		return hsrStageOrders;
	}

	private List<String> convertListChildrenToStrings(
			List<List<String>> unitAndOrderNumbers) {
		List<String> unitAndOrderNumbersList = new ArrayList<String>(unitAndOrderNumbers.size());
		for (List<String> eachPair : unitAndOrderNumbers) {
			unitAndOrderNumbersList.add(eachPair.get(0) + eachPair.get(1));
		}
		return unitAndOrderNumbersList;
	}

	public HSRStageOrders findLatestHsrOrdersWithOrderNumberMatchingBeforeTestSuffix(
			List<List<String>> unitAndOrderNumbers, String testSuffix) {
		
		List<HSRStageOrder> hsrStageOrderList = new ArrayList<HSRStageOrder>();
		for (List<String> unitAndOrderNumberPair : unitAndOrderNumbers ) {
			String unitAndOrderNumber = String.format("%s%s", unitAndOrderNumberPair.get(0), unitAndOrderNumberPair.get(1));
			HSRStageOrder order = hsrStagingOrderdao.findLatestByUnitAndOrderNumbersWithTestSuffix(unitAndOrderNumber, testSuffix);
			if (order != null) {
				hsrStageOrderList.add(order);
			}
		}
		return convertToListWrapper(hsrStageOrderList);
	}
}
