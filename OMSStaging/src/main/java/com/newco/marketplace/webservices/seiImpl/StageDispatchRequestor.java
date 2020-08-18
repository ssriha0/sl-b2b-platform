package com.newco.marketplace.webservices.seiImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.newco.marketplace.webservices.dao.IShcErrorLoggingDAO;
import com.newco.marketplace.webservices.dao.IShcMerchandiseDAO;
import com.newco.marketplace.webservices.dao.IShcNPSProcessLogDAO;
import com.newco.marketplace.webservices.dao.IShcOrderAddOnDAO;
import com.newco.marketplace.webservices.dao.IShcOrderDAO;
import com.newco.marketplace.webservices.dao.IShcOrderSkuDAO;
import com.newco.marketplace.webservices.dao.IShcOrderTransactionDAO;
import com.newco.marketplace.webservices.dao.IShcUpsellPaymentDAO;
import com.newco.marketplace.webservices.dao.ISpecialtyAddOnDAO;
import com.newco.marketplace.webservices.dao.ShcErrorLogging;
import com.newco.marketplace.webservices.dao.ShcMerchandise;
import com.newco.marketplace.webservices.dao.ShcNPSProcessLog;
import com.newco.marketplace.webservices.dao.ShcOrder;
import com.newco.marketplace.webservices.dao.ShcOrderAddOn;
import com.newco.marketplace.webservices.dao.ShcOrderSku;
import com.newco.marketplace.webservices.dao.ShcOrderTransaction;
import com.newco.marketplace.webservices.dao.ShcUpsellPayment;
import com.newco.marketplace.webservices.dao.SpecialtyAddOn;
import com.newco.marketplace.webservices.dto.StagingDetails;
import com.newco.marketplace.webservices.sei.StageOrderSEI;
import com.newco.marketplace.webservices.util.MoneyUtil;
import com.newco.marketplace.webservices.util.StagingConstants;

public class StageDispatchRequestor implements StageOrderSEI {

	private IShcOrderDAO shcOrderDAO;
	private IShcOrderSkuDAO shcOrderSkuDAO;
	private IShcMerchandiseDAO shcMerchandiseDAO;
	private IShcOrderTransactionDAO shcOrderTransactionDAO;
	private IShcErrorLoggingDAO shcErrorLoggingDAO;
	private IShcNPSProcessLogDAO shcNPSProcessLogDAO;
	private IShcUpsellPaymentDAO shcUpsellPaymentDAO;
	private ISpecialtyAddOnDAO specialtyAddOnDAO;
	private IShcOrderAddOnDAO shcOrderAddOnDAO;
	private JpaTransactionManager txManager;

	private Logger logger = Logger.getLogger(StageDispatchRequestor.class);

	/**
	 * Method which persists the staging data after unmarshalling
	 * 
	 * @param StagingDetails
	 */
	
	public void stageDataAfterUnMarshalling(StagingDetails stagingDetails) throws Exception {
		TransactionStatus txStatus = txManager
			.getTransaction(new DefaultTransactionDefinition());
		try {
			ShcOrder savedOrder = null;
			if (stagingDetails != null) {
		
				List<ShcOrder> shcOrderList = stagingDetails
						.getStageServiceOrder();
				for (ShcOrder shcOrder : shcOrderList) {
					if (shcOrder != null) {
						mapWsDtoToJpaDto(shcOrder);
						savedOrder = shcOrderDAO.findByOrderNoAndUnitNo(
								shcOrder.getOrderNo(), shcOrder.getUnitNo());
		
						Set<ShcOrderTransaction> shcOrderTransactionSet = shcOrder
								.getShcOrderTransactions();
						if (shcOrderTransactionSet != null) {
							for (ShcOrderTransaction shcOrderTransaction : shcOrderTransactionSet) {
								if (savedOrder == null) {
									shcOrderDAO.save(shcOrder);
									break;
								}
								Integer wfStateId = savedOrder.getWfStateId();
								logger.debug("wfStateId = " + wfStateId);
								if (shcOrderTransaction.getTransactionType().equals(
											StagingConstants.TRANSACTION_TYPE_UPDATE)) {
									shcOrder.setShcOrderId(savedOrder.getShcOrderId());
									updateNpsStatus(shcOrder);
									if (wfStateId == null || !wfStateId.equals(StagingConstants.CLOSED_STATE)) {
										this.updateMerchandise(shcOrder);
										this.saveOrderTransaction(shcOrder);
										
									}
									break;
								}
							}
						} else {
							logger.warn("Transaction is null");
						}
					}
				}
			}
			txManager.commit(txStatus);
		} catch (Exception e) {
			if (!txStatus.isCompleted()) {
				txManager.rollback(txStatus);
			}
			logger.error("Exception in persist StageData after unmarshalling.",
					e);
			throw new Exception(
					"Exception in persist StageData after unmarshalling.");
		}
	}


	
	private void mapWsDtoToJpaDto(ShcOrder shcOrder) {
		for (ShcOrderSku shcOrderSku : shcOrder.getShcOrderSkus()) {
			shcOrderSku.setShcOrder(shcOrder);
		}
		for (ShcMerchandise shcMerchandise : shcOrder.getShcMerchandises()) {
			shcMerchandise.setShcOrder(shcOrder);
		}
		for (ShcOrderTransaction shcOrderTransaction : shcOrder
				.getShcOrderTransactions()) {
			shcOrderTransaction.setShcOrder(shcOrder);
			for (ShcErrorLogging shcErrorLogging : shcOrderTransaction
					.getShcErrorLoggings()) {
				shcErrorLogging.setShcOrderTransaction(shcOrderTransaction);
			}
		}
		for (ShcUpsellPayment shcUpsellPayment : shcOrder
				.getShcUpsellPayments()) {
			shcUpsellPayment.setShcOrder(shcOrder);
		}
		for (ShcOrderAddOn shcOrderAddOn : shcOrder.getShcOrderAddOns()) {
			shcOrderAddOn.setShcOrder(shcOrder);
		}

	}

	private void mapAllJpsDtoToWsDto(List<ShcOrder> shcOrders) {
		for (ShcOrder shcOrder : shcOrders) {
			mapJpaDtoToWsDto(shcOrder);
		}
	}
	
	private void mapJpaDtoToWsDto(ShcOrder shcOrder) {
		for (ShcOrderSku shcOrderSku : shcOrder.getShcOrderSkus()) {
			shcOrderSku.setShcOrder(null);
		}
		for (ShcMerchandise shcMerchandise : shcOrder.getShcMerchandises()) {
			shcMerchandise.setShcOrder(null);
		}
		for (ShcOrderTransaction shcOrderTransaction : shcOrder
				.getShcOrderTransactions()) {
			shcOrderTransaction.setShcOrder(null);
			shcOrderTransaction.setXmlFragment(null);
			for (ShcErrorLogging shcErrorLogging : shcOrderTransaction
					.getShcErrorLoggings()) {
				shcErrorLogging.setShcOrderTransaction(null);
			}
		}
		for (ShcUpsellPayment shcUpsellPayment : shcOrder
				.getShcUpsellPayments()) {
			shcUpsellPayment.setShcOrder(null);
		}
		for (ShcOrderAddOn shcOrderAddOn : shcOrder.getShcOrderAddOns()) {
			shcOrderAddOn.setShcOrder(null);
		}

	}

	private void mapJpaDtoToWsDto(List<ShcOrderAddOn> shcOrderAddons) {

		for (ShcOrderAddOn shcOrderAddOn : shcOrderAddons) {
			shcOrderAddOn.setShcOrder(null);
		}

	}


	public void stageShcOrderAfterTranslation(ShcOrder shcOrder) {
		ShcOrder savedOrder;
		TransactionStatus txStatus = txManager.getTransaction(new DefaultTransactionDefinition());
		mapWsDtoToJpaDto(shcOrder);
		savedOrder = shcOrderDAO.findByOrderNoAndUnitNo(
				shcOrder.getOrderNo(), shcOrder.getUnitNo());

		try {
			if (savedOrder != null) {
				Integer wfStateId = savedOrder.getWfStateId();
				logger.debug("wfStateId = " + wfStateId);
				if (wfStateId == null || !wfStateId.equals(StagingConstants.CLOSED_STATE)) {
					this.updateSkus(shcOrder);
	
					Set<ShcOrderAddOn> orderAddons = shcOrder
							.getShcOrderAddOns();
					for (ShcOrderAddOn orderAddon : orderAddons) {
						if ("CC".equalsIgnoreCase(orderAddon
								.getCoverage())) {
							shcOrderAddOnDAO.save(orderAddon);
						}
					}
				}
				txManager.commit(txStatus);
			}
		} catch (Exception ex) {
			if (!txStatus.isCompleted()) {
				txManager.rollback(txStatus);
			}
			logger.error("Exception in persist StageData after translation ",
					ex);
		}
	}

	public ShcOrder getStageOrder(String orderNo, String unitNo)
			throws Exception {
		ShcOrder shcOrder = null;
		shcOrder = shcOrderDAO.findByOrderNoAndUnitNo(orderNo, unitNo);
		if (shcOrder != null)
			mapJpaDtoToWsDto(shcOrder);
		return shcOrder;
	}

	public List<ShcOrder> findStagingOrders(List<String> unitAndOrderNumbers) {
		List<ShcOrder> allOrders = shcOrderDAO.findAllByUnitAndOrderNumbers(unitAndOrderNumbers);
		mapAllJpsDtoToWsDto(allOrders);
		return allOrders;
	}
	
	public List<ShcOrder> findLatestStagingOrdersWithOrderNumberMatchingBeforeTestSuffix(List<String> unitAndOrderNumbers, final String testSuffix) {
		List<ShcOrder> allOrders = new ArrayList<ShcOrder>();
		for (final String unitAndOrderNumber : unitAndOrderNumbers ) {
			ShcOrder order = shcOrderDAO.findLatestByUnitAndOrderNumbersWithTestSuffix(unitAndOrderNumber, testSuffix);
			if (order != null) {
				allOrders.add(order);
			}
		}
		mapAllJpsDtoToWsDto(allOrders);
		return allOrders;
	}
	
	/**
	 * Method which persists the staging data after processing
	 * 
	 * @param StagingDetails
	 */
	public void stageDataAfterProcessing(ShcOrder shcOrder,
			List<ShcErrorLogging> shcErrorLoggingList) throws Exception {
		TransactionStatus txStatus = txManager
				.getTransaction(new DefaultTransactionDefinition());
		try {
			try {
				mapWsDtoToJpaDto(shcOrder);
				
				String soId = shcOrder.getSoId();
				Integer shcOrderId = shcOrder.getShcOrderId();
				logger.info("~~~~~~~~~~~~ SO_ID = ["+soId+"] shc_order_id = ["+shcOrderId+"]");
				shcOrderDAO.updateShcOrder(soId, shcOrderId);
				
				
				// Update sku prices if conditional overrides were applied during injection
				Set<ShcOrderSku> skus = shcOrder.getShcOrderSkus();
				for (ShcOrderSku sku : skus) {
					logger.debug("updating regular/non-permit sku#:" + sku.getSku()
							+ " with sellingPrice:" + sku.getSellingPrice());
					shcOrderSkuDAO.update(sku);
				}

				
				txManager.commit(txStatus);
			} catch (Exception ex) {
				// No business rule defined, exception should already have
				// logged at root
				// Proceed with logging errors if any
				if (!txStatus.isCompleted()) {
					txManager.rollback(txStatus);
				}
			}
			txStatus = txManager
					.getTransaction(new DefaultTransactionDefinition());
			if (shcErrorLoggingList != null) {
				for (ShcErrorLogging shcErrorLogging : shcErrorLoggingList) {
					if (shcErrorLogging != null) {
						shcErrorLoggingDAO.save(shcErrorLogging);
					}

				}
			}
			txManager.commit(txStatus);
		} catch (Exception e) {
			if (!txStatus.isCompleted()) {
				txManager.rollback(txStatus);
			}
		}
	}

	/**
	 * Method to persist the stage data to ShcOrderTransaction Table
	 * 
	 * @param ShcOrder
	 */
	private void saveOrderTransaction(ShcOrder serviceOrder) throws Exception {

		// Persist data for OrderTransaction Table
		Set<ShcOrderTransaction> shcOrderTransactionSet = serviceOrder
				.getShcOrderTransactions();
		if (shcOrderTransactionSet != null) {
			for (ShcOrderTransaction shcOrderTransaction : shcOrderTransactionSet) {
				shcOrderTransactionDAO.save(shcOrderTransaction);
			}
		}
	}
	
	/**
	 * Update the nps status of Shc Order
	 * @param shcOrder
	 */
	private void updateNpsStatus(ShcOrder shcOrder) {
		shcOrderDAO.updateShcOrderNpsStatus(shcOrder.getNpsStatus(), shcOrder.getShcOrderId());
		
	}

	/**
	 * Method to update the stage data to ShcMerchandise Table
	 * 
	 * @param ShcOrder
	 */
	private void updateMerchandise(ShcOrder shcOrder) {
		ShcOrder savedOrder = shcOrderDAO.findById(shcOrder.getShcOrderId());
		for (ShcMerchandise shcMerchandise : savedOrder.getShcMerchandises()) {
			shcMerchandiseDAO.delete(shcMerchandiseDAO.findById(shcMerchandise
					.getShcMerchandiseId()));
		}

		savedOrder = shcOrderDAO.findById(shcOrder.getShcOrderId());
		savedOrder.setShcMerchandises(shcOrder.getShcMerchandises());
		savedOrder.setOmsProcessId(shcOrder.getOmsProcessId());		
		shcOrderDAO.update(savedOrder);
	}

	
	private void updateSkus(ShcOrder shcOrder) {
		ShcOrder savedOrder = shcOrderDAO.findByOrderNoAndUnitNo(shcOrder
				.getOrderNo(), shcOrder.getUnitNo());

		List<ShcOrderSku> savedOrderSkus = shcOrderSkuDAO.findByShcOrder(savedOrder);

		Map<String, ShcOrderSku> existingSkus = new HashMap<String, ShcOrderSku>();
		Map<String, ShcOrderSku> newSkus = new HashMap<String, ShcOrderSku>();

		for (ShcOrderSku savedOrderSku : savedOrderSkus) {
			existingSkus.put(savedOrderSku.getSku() + "-"
					+ savedOrderSku.getShcOrder().getShcOrderId() + "-"
					+ savedOrderSku.getAddOnInd(), savedOrderSku);
		}

		Set<ShcOrderSku> newSkuSet = shcOrder.getShcOrderSkus();

		for (ShcOrderSku savedOrderSku : newSkuSet) {
			newSkus.put(savedOrderSku.getSku() + "-"
					+ savedOrderSku.getShcOrder().getShcOrderId() + "-"
					+ savedOrderSku.getAddOnInd(), savedOrderSku);
		}

		for (ShcOrderSku aNewSku : newSkuSet) { 
			ShcOrderSku potentialExistingSku = existingSkus
					.get(aNewSku.getSku() + "-" + aNewSku.getShcOrder().getShcOrderId() + "-"
							+ aNewSku.getAddOnInd());
			if (potentialExistingSku == null) {
				shcOrderSkuDAO.save(aNewSku);
			} else {
				aNewSku.setShcOrderSkuId(potentialExistingSku.getShcOrderSkuId());
				shcOrderSkuDAO.update(aNewSku);
			}
		}
		
		for (ShcOrderSku anOldSku : savedOrderSkus) {
			if (!(newSkus.containsKey(anOldSku.getSku() + "-"
					+ anOldSku.getShcOrder().getShcOrderId() + "-" + anOldSku.getAddOnInd()))) {
				shcOrderSkuDAO.delete(shcOrderSkuDAO.findById(anOldSku.getShcOrderSkuId()));
			}
		}
		
		savedOrder = shcOrderDAO.findById(shcOrder.getShcOrderId());
		savedOrder.setShcOrderSkus(newSkuSet);
		shcOrderDAO.update(savedOrder);
	}

	/**
	 * Method to persist errors
	 * 
	 * @param String
	 *            errorCode
	 * @param String
	 *            errorMessage
	 * @param String
	 *            orderNumber
	 * @param String
	 *            unitNumber
	 */
	public void persistErrors(String errorCode, String errorMessage,
			String orderNumber, String unitNumber) {
		TransactionStatus txn = txManager
				.getTransaction(new DefaultTransactionDefinition());
		try {
			// Persist data for ErrorLogging Table

			ShcErrorLogging shcErrorLogging = new ShcErrorLogging();

			ShcOrder savedShcOrder = shcOrderDAO.findByOrderNoAndUnitNo(
					orderNumber, unitNumber);
			int orderID = savedShcOrder.getShcOrderId().intValue();
			List<ShcOrderTransaction> savedShcOrderTransactions = shcOrderTransactionDAO
					.findByShcOrderId(Integer.valueOf(orderID));
			// get the latest transactionID using the orderID
			int size = savedShcOrderTransactions.size();
			int[] transactionIDs = new int[size];
			int i = 0;
			for (ShcOrderTransaction shcOrderTransaction : savedShcOrderTransactions) {
				transactionIDs[i] = shcOrderTransaction.getShcOrderTransactionId().intValue();
				i++;
			}
			Arrays.sort(transactionIDs);

			int transactionID = transactionIDs[size - 1];
			String modifiedBy = savedShcOrder.getModifiedBy();
			ShcOrderTransaction shcOrderTrans = new ShcOrderTransaction();
			shcOrderTrans.setShcOrderTransactionId(Integer.valueOf(transactionID));
			shcErrorLogging.setShcOrderTransaction(shcOrderTrans);
			shcErrorLogging.setErrorCode(errorCode);
			shcErrorLogging.setErrorMessage(errorMessage);
			shcErrorLogging.setModifiedBy(modifiedBy);

			shcErrorLoggingDAO.save(shcErrorLogging);
			txManager.commit(txn);
		} catch (Exception ex) {
			if (!txn.isCompleted()) {
				txManager.rollback(txn);
			}
			logger.error("Exception in persist ShcErrorLogging Data", ex);
		}
	}

	public boolean stageUpsoldInfo(StagingDetails stagingDetails) {
		boolean isSuccess= false;
		TransactionStatus txStatus = txManager
				.getTransaction(new DefaultTransactionDefinition());
		ShcOrder shcOrder = null;
		try {
			if (stagingDetails.getStageServiceOrder() != null
					&& !stagingDetails.getStageServiceOrder().isEmpty()) {
				shcOrder = stagingDetails.getStageServiceOrder().get(0);
			}
			if (shcOrder != null) {
				String soId = shcOrder.getSoId();	
				ShcOrder shcOrderRetrieved=null;
				//Check if the staged data exists with the passed in OrderNo and UnitNo and update with passed in soId if required.
				if(shcOrder.getUnitNo()!=null && shcOrder.getOrderNo()!=null){
					shcOrderRetrieved =shcOrderDAO.findByOrderNoAndUnitNo(shcOrder.getOrderNo(), shcOrder.getUnitNo());
					if(shcOrderRetrieved!=null && (shcOrderRetrieved.getSoId()==null || 
								StringUtils.equals(shcOrderRetrieved.getSoId(),StagingConstants.FAILED_SERVICE_ORDER_NO))){
							shcOrderRetrieved.setSoId(shcOrder.getSoId());
							shcOrderDAO.update(shcOrderRetrieved);
					}
				}
				//If staged data exists, continue with updates. Return true.
				if(shcOrderRetrieved!=null){
					isSuccess=true;
					mapWsDtoToJpaDto(shcOrder);
												
					Set<ShcOrderSku> shcAddOnSkusList = stagingDetails
							.getStageServiceOrder().get(0).getShcOrderSkus();
					Set<ShcUpsellPayment> shcUpsellPayments = stagingDetails
							.getStageServiceOrder().get(0).getShcUpsellPayments();
					Set<ShcMerchandise> shcMerchandises = stagingDetails
							.getStageServiceOrder().get(0).getShcMerchandises();
					persistShcOrderDetails(soId, shcOrder);
					persistShcOrderAddOnSkus(soId, shcAddOnSkusList);
					persistUpsellPaymentInfo(soId, shcUpsellPayments);
					persistMerchandiseInfo(soId, shcMerchandises);
				}			
		  }			
			txManager.commit(txStatus);
		} catch (Exception e) {
			if (!txStatus.isCompleted()) {
				txManager.rollback(txStatus);
			}
			String message = "Error occurred while staging Upsell Info";
			logger.error(message + " due to " + e.getMessage(), e);
			// return web service response object
		}
		return isSuccess;
	}

	private void persistShcOrderAddOnSkus(String soId,
			Set<ShcOrderSku> shcAddOnSkusList) throws Exception {
		try {
			// get orderId for given soId
			ShcOrder shcOrder = null;
			List<ShcOrder> shcOrders = shcOrderDAO.findBySoId(soId);
			if (shcOrders != null && !shcOrders.isEmpty()) {
				shcOrder = shcOrders.get(0);
			}
			// persist all the addOns
			if (shcAddOnSkusList != null && shcOrder != null) {
				for (ShcOrderSku addonSku : shcAddOnSkusList) {
					addonSku.setShcOrder(shcOrder);
					setSkuDefaultValues(addonSku);
					shcOrderSkuDAO.save(addonSku);
				}
			}

		} catch (Exception e) {
			logger
					.error("Error occured in persistUpsellPaymentInfo while persisitng Payment Info-->", e);
			throw e;
		}
	}

	private void persistUpsellPaymentInfo(String soId,
			Set<ShcUpsellPayment> upsellPayments) throws Exception {
		try {
			// get orderId for given soId
			ShcOrder shcOrder = null;
			List<ShcOrder> shcOrders = shcOrderDAO.findBySoId(soId);
			if (shcOrders != null && !shcOrders.isEmpty()) {
				shcOrder = shcOrders.get(0);
				if (upsellPayments != null && shcOrder != null) {
					for (ShcUpsellPayment upsellPayment : upsellPayments) {
						upsellPayment.setShcOrder(shcOrder);
						// persist payment Info
						shcUpsellPaymentDAO.save(upsellPayment);
					}
				}

			}
		} catch (Exception e) {
			logger
					.error("Error occured in persistUpsellPaymentInfo while persisitng Payment Info-->", e);
			throw e;
		}
	}

	private void persistShcOrderDetails(String soId, ShcOrder updatedShcOrder)
			throws Exception {
		try {
			// get orderId for given soId
			ShcOrder shcOrder = null;
			List<ShcOrder> shcOrders = shcOrderDAO.findBySoId(soId);
			if (shcOrders != null && !shcOrders.isEmpty()) {
				shcOrder = shcOrders.get(0);
			}
			// updated shc Order info
			if (shcOrder != null) {
				shcOrder.setCompletedDate(updatedShcOrder.getCompletedDate());
				shcOrder.setRoutedDate(updatedShcOrder.getRoutedDate());
				shcOrder.setResolutionDescr(updatedShcOrder
						.getResolutionDescr());
				shcOrder.setWfStateId(updatedShcOrder.getWfStateId());
				shcOrderDAO.update(shcOrder);
			}

		} catch (Exception e) {
			logger
					.error("Error occured in persistUpsellPaymentInfo while persisitng Payment Info-->", e);
			throw e;
		}
	}

	private void persistMerchandiseInfo(String soId, Set<ShcMerchandise> newMerchandises) throws Exception {
		
		if (newMerchandises == null || newMerchandises.isEmpty()) {
			return;
		}
		
		try {
			// get orderId for given soId
			ShcOrder shcOrder = null;
			List<ShcOrder> shcOrders = shcOrderDAO.findBySoId(soId);
			if (shcOrders != null && !shcOrders.isEmpty()) {
				shcOrder = shcOrders.get(0);
				if (shcOrder != null) {
					Set<ShcMerchandise> shcMerchandises = shcOrder.getShcMerchandises();
					if (shcMerchandises != null && !shcMerchandises.isEmpty()) {
						ShcMerchandise shcMerchandise = shcMerchandises.iterator().next();
						ShcMerchandise newMerchandise = newMerchandises.iterator().next();
						if (StringUtils.isNotBlank(newMerchandise.getBrand())) {
							shcMerchandise.setBrand(newMerchandise.getBrand());
						}
						if (StringUtils.isNotBlank(newMerchandise.getDescription())) {
							shcMerchandise.setDescription(newMerchandise.getDescription());
						}
						if (StringUtils.isNotBlank(newMerchandise.getSerialNumber())) {
							shcMerchandise.setSerialNumber(newMerchandise.getSerialNumber());
						}
						if (StringUtils.isNotBlank(newMerchandise.getModel())) {
							shcMerchandise.setModel(newMerchandise.getModel());
						}
						shcMerchandiseDAO.update(shcMerchandise);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Error occured in persistMerchandiseInfo while persisitng serial number-->", e);
			throw e;
		}
	}

	private void setSkuDefaultValues(ShcOrderSku addonSku) {
		addonSku.setChargeCode(StagingConstants.UPSELL_SKU_CHARGE_CODE);
		addonSku.setCoverage(StagingConstants.UPSELL_SKU_COVERAGE);
		addonSku.setPermitSkuInd(StagingConstants.UPSELL_PERMIT_SKU_IND);
		addonSku.setPriceRatio(StagingConstants.UPSELL_SKU_PRICE_RATIO);
		addonSku.setType(StagingConstants.UPSELL_SKU_TYPE);
		addonSku.setStatus(StagingConstants.UPSELL_SKU_STATUS);
	}

	/**
	 * Method to retrieve closed unprocessed Orders
	 * 
	 * @return List<ShcOrder>
	 */
	public List<ShcOrder> retrieveClosedOrders(final int npsNumOrdersToClose) {
		List<ShcOrder> shcOrderList = shcOrderDAO.findClosedUnprocessedOrders(npsNumOrdersToClose);
		for (ShcOrder shcOrder : shcOrderList) {
			mapJpaDtoToWsDto(shcOrder);
		}
		return shcOrderList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.webservices.business.IStagingService#createNPSProcessLog(com.newco.marketplace.webservices.dto.StagingDetails,
	 *      java.lang.String)
	 */
	public int createNPSProcessLog(StagingDetails stagingDetails,
			String npsCallCloseFileName) {
		int npsProcessId = -1;
		List<ShcOrder> stagingOrdersList = stagingDetails
				.getStageServiceOrder();
		if (null != stagingOrdersList && !stagingOrdersList.isEmpty()) {
			TransactionStatus txn = txManager
					.getTransaction(new DefaultTransactionDefinition());
			try {
				// Insert record to shc_npc_close_process_log table
				ShcNPSProcessLog shcNPSProcessLog = new ShcNPSProcessLog();
				shcNPSProcessLog.setFileName(npsCallCloseFileName);
				shcNPSProcessLog.setModifiedBy(stagingOrdersList.get(0)
						.getModifiedBy());
				shcNPSProcessLogDAO.save(shcNPSProcessLog);
				npsProcessId = shcNPSProcessLog.getNpsProcessId().intValue();

				// Update nps process id in all closed orders
				for (ShcOrder shcOrder : stagingOrdersList) {
					if (shcOrder != null) {
						mapWsDtoToJpaDto(shcOrder);
						shcOrder.setNpsProcessId(Integer.valueOf(npsProcessId));
						shcOrderDAO.update(shcOrder);
					}
				}
				// Commit DB transaction
				txManager.commit(txn);
			} catch (Throwable th) {
				logger.error("Unexpected error while creating NPS process log", th);
				npsProcessId = -1;
				if (!txn.isCompleted()) {
					txManager.rollback(txn);
				}
			}
		}
		return npsProcessId;
	}

	public List<SpecialtyAddOn> getMiscAddOn(String specialtyCode,
			String divisionCode) {
		return specialtyAddOnDAO.findAddOnsWithMiscBySpecialtyCode(
				specialtyCode, divisionCode);
	}

	public List<ShcOrderAddOn> getShcOrderDetails(String orderNumber,
			String unitNumber) {

		ShcOrder savedOrder = shcOrderDAO.findByOrderNoAndUnitNo(orderNumber,
				unitNumber);
		List<ShcOrderAddOn> addons = shcOrderAddOnDAO
				.findByShcOrderId(savedOrder.getShcOrderId());
		mapJpaDtoToWsDto(addons);
		return addons;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.newco.marketplace.webservices.sei.StageOrderSEI#updateFinalPrice(java.lang.String,
	 *      java.lang.String, java.lang.Double, java.lang.Double)
	 */
	public void updateFinalPrice(String orderNo, String unitNo,
			Double finalPrice, Double permitPrice, Double serviceFee)
			throws Exception {
		try {
			TransactionStatus txn = txManager.getTransaction(new DefaultTransactionDefinition());
			ShcOrder shcOrder = shcOrderDAO.findByOrderNoAndUnitNo(orderNo,
					unitNo);
			if (shcOrder != null) {

				logger.debug("updating final price for stage order " + " soID:"
						+ shcOrder.getSoId() + " finalPrice:" + finalPrice
						+ " permitPrice:" + permitPrice);
				//finalPrice is final price for labour+parts
				Double adjustedFinalLaborPrice = finalPrice;
				if (isThereAnyPermitSku(shcOrder)) {
					// Subtract permit price, if it is provided.
					if (permitPrice != null && permitPrice.doubleValue() > 0.0) {
						// most elegant solution of not ending up with negative adjusted labor price
						adjustedFinalLaborPrice = Double.valueOf(adjustedFinalLaborPrice.doubleValue() - Math.min(permitPrice.doubleValue(),finalPrice.doubleValue()));
					}
				}
				updateFinalPriceForSkus(permitPrice, shcOrder,
						adjustedFinalLaborPrice, serviceFee);

			} else {
				logger
						.debug("The order with orderNo:"
								+ orderNo
								+ " unitNo:"
								+ unitNo
								+ " is not a staged order. upateFinalPrice() did not update any price.");
			}
			txManager.commit(txn);
		} catch (Exception e) {
			logger.error("Final price could not be updated", e);
			throw e;
		}

	}

	/**
	 * Check if there is any permit sku in the service order
	 * 
	 * @param shcOrder
	 * @return
	 */
	private boolean isThereAnyPermitSku(ShcOrder shcOrder) {
		for (ShcOrderSku sku : shcOrder.getShcOrderSkus()) {
			if (sku == null)
				continue;

			// Permit Skus set permitPrice as final price
			if (sku.getPermitSkuInd() != null && sku.getPermitSkuInd().intValue() == 1) {
				return true;
			}
		}
		return false;

	}

	/**
	 * @param permitPrice
	 * @param shcOrder
	 * @param adjustedFinalLaborPrice
	 * @param serviceFee
	 * @throws Exception
	 */
	private void updateFinalPriceForSkus(Double permitPrice, ShcOrder shcOrder,
			Double adjustedFinalLaborPrice, Double serviceFee) throws Exception {
		List<ShcOrderSku> skus = new ArrayList<ShcOrderSku>();
		List<Double> prices = new ArrayList<Double>();
		List<Double> serviceFees = new ArrayList<Double>();
		TransactionStatus txn = txManager.getTransaction(new DefaultTransactionDefinition());
		Double totalRatio = 0.0d;
		ShcOrderSku primarySku = null;
		
		// Check if any price ratios exist for the given skus, 
		for (ShcOrderSku sku : shcOrder.getShcOrderSkus()) {
			if (null !=  sku.getPriceRatio() ) {
				totalRatio = totalRatio + sku.getPriceRatio();
			}
			if (null !=  sku.getType() ) {
				if(sku.getType().equals(StagingConstants.PRIMARY_SKU_TYPE)){
					primarySku = sku;
				}
			}
		}
		
		// update the Primary (type='R') sku's price ratio = 1.000 if no price ratio exists
		if(null != primarySku && totalRatio<=0.0){
			primarySku.setPriceRatio(1.0000);
		}
		
		for (ShcOrderSku sku : shcOrder.getShcOrderSkus()) {
			if (sku == null)
				continue;

			// Permit Skus set permitPrice as final price
			if (sku.getPermitSkuInd() != null && sku.getPermitSkuInd().intValue() == 1) {
				sku.setFinalPrice(permitPrice);
				logger.debug("updating permit sku#:" + sku.getSku()
						+ " with finalPrice:" + sku.getFinalPrice());
				shcOrderSkuDAO.update(sku);
			}
			// Regular Skus get labor price times price ratio at final
			// price.
			else {
				if (sku.getPriceRatio() != null) {
					Double skuPrice = Double.valueOf(sku.getPriceRatio().doubleValue() * adjustedFinalLaborPrice.doubleValue());
					Double skuServiceFee = Double.valueOf(sku.getPriceRatio().doubleValue() * serviceFee.doubleValue());
					if (skuPrice != null && skuPrice.doubleValue() > 0.0) {
						skus.add(sku);
						prices.add(skuPrice);
						serviceFees.add(skuServiceFee);
					}
				}
			}
		}
		updateFinalPriceForNonPermitSkus(adjustedFinalLaborPrice, skus, prices,
				serviceFee, serviceFees);
		txManager.commit(txn);
	}

	/**
	 * @param adjustedFinalLaborPrice
	 * @param skus
	 * @param prices
	 * @param serviceFee
	 * @param serviceFees
	 */
	private void updateFinalPriceForNonPermitSkus(
			Double adjustedFinalLaborPrice, List<ShcOrderSku> skus,
			List<Double> prices, Double serviceFee, List<Double> serviceFees)
			throws Exception {
		// Need to recalculate prices to handle rounding errors
		List<Double> spreadPrices = MoneyUtil.spreadFundsByOriginalRatio(
				prices, adjustedFinalLaborPrice);
		List<Double> spreadServiceFees = MoneyUtil.spreadFundsByOriginalRatio(
				serviceFees, serviceFee);
		// Put these values back in the skus, and save to DB
		int i = 0;
		ShcOrderSku sku;
		TransactionStatus txn = txManager.getTransaction(new DefaultTransactionDefinition());
		for (Double price : spreadPrices) {
			sku = skus.get(i);
			if (sku != null) {
				sku.setFinalPrice(price);
				sku.setServiceFee(spreadServiceFees.get(i++));
				logger.debug("updating regular/non-permit sku#:" + sku.getSku()
						+ " with finalPrice:" + sku.getFinalPrice());
				shcOrderSkuDAO.update(sku);
			}
		}
		txManager.commit(txn);
	}

	/**
	 * method to get the speciality addon by speciality code and addon id
	 */
	public SpecialtyAddOn getCallCollectAddon(String specialtyCode,
			String stockNumber) throws Exception {
		return specialtyAddOnDAO.findBySpecialtyCodeAndStockNumber(
				specialtyCode, stockNumber);
	}

	public IShcOrderDAO getShcOrderDAO() {
		return shcOrderDAO;
	}

	public void setShcOrderDAO(IShcOrderDAO shcOrderDAO) {
		this.shcOrderDAO = shcOrderDAO;
	}

	public IShcOrderSkuDAO getShcOrderSkuDAO() {
		return shcOrderSkuDAO;
	}

	public void setShcOrderSkuDAO(IShcOrderSkuDAO shcOrderSkuDAO) {
		this.shcOrderSkuDAO = shcOrderSkuDAO;
	}

	public IShcMerchandiseDAO getShcMerchandiseDAO() {
		return shcMerchandiseDAO;
	}

	public void setShcMerchandiseDAO(IShcMerchandiseDAO shcMerchandiseDAO) {
		this.shcMerchandiseDAO = shcMerchandiseDAO;
	}

	public IShcOrderTransactionDAO getShcOrderTransactionDAO() {
		return shcOrderTransactionDAO;
	}

	public void setShcOrderTransactionDAO(
			IShcOrderTransactionDAO shcOrderTransactionDAO) {
		this.shcOrderTransactionDAO = shcOrderTransactionDAO;
	}

	public IShcErrorLoggingDAO getShcErrorLoggingDAO() {
		return shcErrorLoggingDAO;
	}

	public void setShcErrorLoggingDAO(IShcErrorLoggingDAO shcErrorLoggingDAO) {
		this.shcErrorLoggingDAO = shcErrorLoggingDAO;
	}

	public JpaTransactionManager getTxManager() {
		return txManager;
	}

	public void setTxManager(JpaTransactionManager txManager) {
		this.txManager = txManager;
	}

	public IShcUpsellPaymentDAO getShcUpsellPaymentDAO() {
		return shcUpsellPaymentDAO;
	}

	public void setShcUpsellPaymentDAO(IShcUpsellPaymentDAO shcUpsellPaymentDAO) {
		this.shcUpsellPaymentDAO = shcUpsellPaymentDAO;
	}

	public IShcNPSProcessLogDAO getShcNPSProcessLogDAO() {
		return shcNPSProcessLogDAO;
	}

	public void setShcNPSProcessLogDAO(IShcNPSProcessLogDAO shcNPSProcessLogDAO) {
		this.shcNPSProcessLogDAO = shcNPSProcessLogDAO;
	}

	/**
	 * @return the specialtyAddOnDAO
	 */
	public ISpecialtyAddOnDAO getSpecialtyAddOnDAO() {
		return specialtyAddOnDAO;
	}

	/**
	 * @param specialtyAddOnDAO
	 *            the specialtyAddOnDAO to set
	 */
	public void setSpecialtyAddOnDAO(ISpecialtyAddOnDAO specialtyAddOnDAO) {
		this.specialtyAddOnDAO = specialtyAddOnDAO;
	}

	public IShcOrderAddOnDAO getShcOrderAddOnDAO() {
		return shcOrderAddOnDAO;
	}

	public void setShcOrderAddOnDAO(IShcOrderAddOnDAO shcOrderAddOnDAO) {
		this.shcOrderAddOnDAO = shcOrderAddOnDAO;
	}
}
