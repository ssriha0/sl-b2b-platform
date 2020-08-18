
package com.newco.marketplace.api.utils.mappers.so.v1_1;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.beans.Results;
import com.newco.marketplace.api.beans.so.price.CurrentPrice;
import com.newco.marketplace.api.beans.so.price.FinalPrice;
import com.newco.marketplace.api.beans.so.price.MaxPrice;
import com.newco.marketplace.api.beans.so.price.OrderStatus;
import com.newco.marketplace.api.beans.so.price.SOLevelPriceHistory;
import com.newco.marketplace.api.beans.so.price.SOLevelpriceHistoryRecord;
import com.newco.marketplace.api.beans.so.price.SOPriceHistoryResponse;
import com.newco.marketplace.api.beans.so.price.ServiceOrderPriceHistory;
import com.newco.marketplace.api.beans.so.price.ServiceOrders;
import com.newco.marketplace.api.beans.so.price.TaskLevelPriceHistory;
import com.newco.marketplace.api.beans.so.price.TaskLevelPriceHistoryRecord;
import com.newco.marketplace.api.beans.so.price.TaskPriceHistory;
import com.newco.marketplace.api.utils.utility.CommonUtility;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.dto.vo.serviceorder.ProblemResolutionSoVO;
import com.newco.marketplace.dto.vo.serviceorder.ProviderInvoicePartsVO;
import com.newco.marketplace.dto.vo.serviceorder.SOTaskPriceHistoryVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderAddonVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.SoPriceChangeHistory;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.MoneyUtil;

/**
 * This class would act as a Mapper class for Mapping Service Object to
 * SOPriceHistoryResponse Object.
 *
 * @author Infosys
 * @version 1.0
 */
public class SOPriceHistoryMapper {
	private Logger logger = Logger.getLogger(SOPriceHistoryMapper.class);
	private IServiceOrderBO serviceOrderBO;
	private static final  Integer PERMIT_TASK = 1;
	BigDecimal ZERO = new BigDecimal("0.00");


	/**
	 * @param serviceOrders
	 * @param infoLevel
	 * @param invalidResponses 
	 * @return
	 */
	public SOPriceHistoryResponse mapPriceHistory(List<ServiceOrder> serviceOrders, Integer infoLevel,
			Map<String,Results> invalidOrders){

		SOPriceHistoryResponse priceHistoryResponse = new SOPriceHistoryResponse();
		List<ServiceOrderPriceHistory> orderPriceHistories = new ArrayList<ServiceOrderPriceHistory>();
		ServiceOrderPriceHistory orderPriceHistory;
		int validCount = 0;
		int invalidCount = 0;
		
		for(ServiceOrder serviceOrder: serviceOrders){
			if(serviceOrder != null){
				orderPriceHistory= new ServiceOrderPriceHistory();
				Results invalidResponse = null;
				if(null!= invalidOrders && null!= invalidOrders.get(serviceOrder.getSoId())){
					invalidResponse = invalidOrders.get(serviceOrder.getSoId());
				}
				if(invalidResponse != null){
					orderPriceHistory.setResults(invalidResponse);
					orderPriceHistories.add(orderPriceHistory);
					invalidCount++;
				}else{
					orderPriceHistory =  mapPriceHistoryForValidSos(serviceOrder,infoLevel);
					orderPriceHistories.add(orderPriceHistory);
					//for testing

					Results results = Results.getSuccess("Price history has been retrieved successfully for"+
							" "+serviceOrder.getSoId());
					orderPriceHistory.setResults(results);
					validCount++;
				}
			}
		}
		ServiceOrders orders = new ServiceOrders();
		orders.setOrderPriceHistories(orderPriceHistories);
		
		// invalidCount+validCount should be equal to serviceOrders size
		if(validCount > 0){
			//for testing
			Results results = Results.getSuccess("Price history has been retrieved successfully for" +
					" "+validCount+" "+ "service order(s).");
			if(null!=results){
				priceHistoryResponse.setResults(results);
			}
		}
		priceHistoryResponse.setServiceOrdersList(orders);
		return priceHistoryResponse;

	}

	/**
	 * Map individual service order details
	 * @param serviceOrder
	 * @param infoLevel
	 * @return
	 */
	private ServiceOrderPriceHistory mapPriceHistoryForValidSos(ServiceOrder serviceOrder, 
			Integer infoLevel) {
		ServiceOrderPriceHistory orderPriceHistory = new ServiceOrderPriceHistory();
		OrderStatus orderStatus = new OrderStatus();
		CurrentPrice currentprice = new CurrentPrice();
		SOLevelPriceHistory solevelpricehistory;
		TaskLevelPriceHistory tasklevelpricehistory;
		
		// map order status
		mapOrderStatus(serviceOrder, orderStatus);
		orderPriceHistory.setOrderStatus(orderStatus);
		
		// Map Current Price		
		if (OrderConstants.CURRENT_PRICE_INFO==infoLevel) {
			mapCurrentPrice(serviceOrder,currentprice);
			orderPriceHistory.setCurrentPrice(currentprice);
		}
		
		// Map SO LEVEL
		if (OrderConstants.SO_LEVEL_PRICE_HISTORY==infoLevel) {
			solevelpricehistory = new SOLevelPriceHistory();
			mapSoLevelPriceHistory(serviceOrder,solevelpricehistory);
			orderPriceHistory.setSoLevelPriceHistory(solevelpricehistory);
		} else if (OrderConstants.TASK_LEVEL_PRICE_HISTORY==(infoLevel)) { // Map SO LEVEL and TASK LEVEL
			solevelpricehistory = new SOLevelPriceHistory();
			mapSoLevelPriceHistory(serviceOrder,solevelpricehistory);
			orderPriceHistory.setSoLevelPriceHistory(solevelpricehistory);
			
			tasklevelpricehistory = new TaskLevelPriceHistory();
			mapTaskLevelPriceHistory(serviceOrder,tasklevelpricehistory);
			orderPriceHistory.setTaskLevelPriceHistory(tasklevelpricehistory);
		}
		return orderPriceHistory;
	}

	

	/**
	 * @param serviceOrder
	 * @param tasklevelpricehistory
	 * this method maps the task level price history
	 * 
	 */
	private void mapTaskLevelPriceHistory(ServiceOrder serviceOrder,
			TaskLevelPriceHistory tasklevelpricehistory) {
		
		List<ServiceOrderTask> serviceOrderTaskList= serviceOrder.getTasks();
		List<TaskLevelPriceHistoryRecord> taskLevelPriceHistoryRecords = new ArrayList<TaskLevelPriceHistoryRecord>();

		for(ServiceOrderTask serviceOrderTask : serviceOrderTaskList){
			TaskLevelPriceHistoryRecord taskLevelPriceHistoryRecord = new TaskLevelPriceHistoryRecord();

			taskLevelPriceHistoryRecord.setSku(serviceOrderTask.getSku());
			taskLevelPriceHistoryRecord.setTaskName(serviceOrderTask.getTaskName());
			
			List<SOTaskPriceHistoryVO>  priceHistoryVOs = serviceOrderTask.getPriceHistoryList();
			List<TaskPriceHistory>  taskPricehistories = new ArrayList<TaskPriceHistory>();
			if( null != priceHistoryVOs && !priceHistoryVOs.isEmpty()){
				for(SOTaskPriceHistoryVO soTaskPriceHistoryVO: priceHistoryVOs){
					TaskPriceHistory taskPriceHistory = new TaskPriceHistory();
					if (null !=soTaskPriceHistoryVO){
						taskPriceHistory.setChangedDate(CommonUtility.sdfToDate.format(soTaskPriceHistoryVO.getCreatedDate()));
						taskPriceHistory.setChangedByUserId(soTaskPriceHistoryVO.getModifiedBy());
						taskPriceHistory.setTaskPrice(soTaskPriceHistoryVO.getPrice());
						taskPriceHistory.setChangedByUserName(soTaskPriceHistoryVO.getModifiedByName());
											
						// Add to List
						taskPricehistories.add(taskPriceHistory);
				}
			}
			if(null!=taskPricehistories && taskPricehistories.size()>0){
				taskLevelPriceHistoryRecord.setTaskPricehistory(taskPricehistories);
			}
			taskLevelPriceHistoryRecords.add(taskLevelPriceHistoryRecord);
		}
		if(null!=taskLevelPriceHistoryRecords && taskLevelPriceHistoryRecords.size()>0){
			tasklevelpricehistory.setTaskLevelPriceHistoryRecord(taskLevelPriceHistoryRecords);
		}
	}
}

	/**
	 * @param serviceOrder
	 * @param solevelpricehistory
	 *  
	 * 	 * this method maps the so level price history
	 * 
	 */
	private void mapSoLevelPriceHistory(ServiceOrder serviceOrder,
			SOLevelPriceHistory solevelpricehistory) {
		List<SoPriceChangeHistory> priceChangeHistory = serviceOrder.getSoPriceChangeHistoryList();
		if(priceChangeHistory != null){
			SOLevelpriceHistoryRecord soLevelPriceHistoryRecord;
			List<SOLevelpriceHistoryRecord> soLevelpriceHistoryRecords = new ArrayList<SOLevelpriceHistoryRecord>();

			for(SoPriceChangeHistory soPriceChangeHistory: priceChangeHistory){
				if(null != soPriceChangeHistory){
				soLevelPriceHistoryRecord = new SOLevelpriceHistoryRecord();
				soLevelPriceHistoryRecord.setAction(soPriceChangeHistory.getAction());
				soLevelPriceHistoryRecord.setChangedByUserId(soPriceChangeHistory.getModifiedBy());
				soLevelPriceHistoryRecord.setChangedByUserName(soPriceChangeHistory.getModifiedByName());
				soLevelPriceHistoryRecord.setChangedDate(CommonUtility.sdfToDate.format(soPriceChangeHistory.getCreatedDate()));
				soLevelPriceHistoryRecord.setReasonCode(soPriceChangeHistory.getReasonComment());
				soLevelPriceHistoryRecord.setLaborPriceChange(soPriceChangeHistory.getSoLabourPrice());
				soLevelPriceHistoryRecord.setPartPriceChange(soPriceChangeHistory.getSoMaterialsPrice());
				if(null != soPriceChangeHistory.getSoAddonPrice() && !(soPriceChangeHistory.getSoAddonPrice().toString().equals(ZERO.toString()))){
					soLevelPriceHistoryRecord.setAddonPriceChange(soPriceChangeHistory.getSoAddonPrice());
				}
				if(null != soPriceChangeHistory.getSoPartsInvoicePrice() && !(soPriceChangeHistory.getSoPartsInvoicePrice().toString().equals(ZERO.toString()))){
					soLevelPriceHistoryRecord.setInvoicePartPriceChange(soPriceChangeHistory.getSoPartsInvoicePrice());
				}
				if(null != soPriceChangeHistory.getSoPermitPrice() && !(soPriceChangeHistory.getSoPermitPrice().toString().equals(ZERO.toString()))){
					soLevelPriceHistoryRecord.setPermitPriceChange(soPriceChangeHistory.getSoPermitPrice());
				}
				soLevelPriceHistoryRecord.setTotalPrice(soPriceChangeHistory.getSoTotalPrice());
				
				// Add to list
				soLevelpriceHistoryRecords.add(soLevelPriceHistoryRecord);
			}
			solevelpricehistory.setSoLevelPriceHistoryRecord(soLevelpriceHistoryRecords);
		}
	}
}

	/**
	 * @param serviceOrder
	 * @param currentprice
	 * 
	 * this method maps the current price details of a an so
	 * 
	 */
	private void mapCurrentPrice(ServiceOrder serviceOrder,CurrentPrice currentprice) {
		MaxPrice maxPrice;
		FinalPrice finalPrice;

		maxPrice = new MaxPrice();
		mapMaxPrice(serviceOrder,maxPrice,currentprice);
		currentprice.setMaxPrice(maxPrice);
		
		Integer wfStateId = serviceOrder.getWfStateId();
		if(wfStateId!= null){
			if (!wfStateId.equals(OrderConstants.DRAFT_STATUS)){
				double price = serviceOrder.getPostingFee();
				currentprice.setBuyerPostingFee(MoneyUtil.getRoundedMoneyBigDecimal(price));
			}
			if((wfStateId.equals(OrderConstants.COMPLETED_STATUS))|| (wfStateId.equals(OrderConstants.VOIDED_STATUS))
					||(wfStateId.equals(OrderConstants.CLOSED_STATUS))||(wfStateId.equals(OrderConstants.CANCELLED_STATUS))
					||(wfStateId.equals(OrderConstants.DELETED_STATUS))){
				finalPrice = new FinalPrice();
				mapFinalPrice(serviceOrder,finalPrice,currentprice);
				currentprice.setFinalPrice(finalPrice);
			}
		}
	}

	/**
	 * @param serviceOrder
	 * @param finalPrice
	 * @param currentprice
	 * 
	 * this method maps final price to current price
	 * 
	 */
	private void mapFinalPrice(ServiceOrder serviceOrder,
			FinalPrice finalPrice, CurrentPrice currentprice) {
		BigDecimal labour = new BigDecimal("0.00");
		BigDecimal parts  = new BigDecimal("0.00");
		BigDecimal finalTotalPrice = new BigDecimal("0.00");
		BigDecimal addOnPrice = new BigDecimal("0.00");
		BigDecimal addOnPermitPrice = new BigDecimal("0.00");
		BigDecimal invoicePartsPrice = new BigDecimal("0.00");
		Double price ;		
		//calculate permit price
				double totalPermitPrice =0;
				BigDecimal totalPermit =  new BigDecimal("0.00");
				for (ServiceOrderTask task : serviceOrder.getActiveTasks(serviceOrder.getTasks())) {
					if(PERMIT_TASK.equals(task.getTaskType())){
						if(null!= task.getSellingPrice() && null!= task.getFinalPrice() && task.getSellingPrice().doubleValue()>task.getFinalPrice().doubleValue()){
							totalPermitPrice = totalPermitPrice + task.getFinalPrice().doubleValue();
						}else if(null!= task.getSellingPrice() && null!= task.getFinalPrice() && task.getSellingPrice().doubleValue()<=task.getFinalPrice().doubleValue()){
							totalPermitPrice = totalPermitPrice + task.getSellingPrice().doubleValue();
						}
					
					}
		    	
		    	}
				if(totalPermitPrice >=0){
					totalPermit = new BigDecimal(totalPermitPrice);
		
				}
				price = serviceOrder.getLaborFinalPrice();

		if(price!=null){
		  //labour = new BigDecimal(serviceOrder.getLaborFinalPrice());
			labour=MoneyUtil.getRoundedMoneyBigDecimal(serviceOrder.getLaborFinalPrice());
			finalPrice.setFinalPriceForLabor(labour.subtract(((totalPermit!=null)?totalPermit:new BigDecimal("0.00"))));

		}
		price =serviceOrder.getPartsFinalPrice();

		if(price!=null){
		  //parts = new BigDecimal(serviceOrder.getPartsFinalPrice());
			parts=MoneyUtil.getRoundedMoneyBigDecimal(serviceOrder.getPartsFinalPrice());
			finalPrice.setFinalPriceForParts(parts);
		}

		List<ServiceOrderAddonVO> soAddons = serviceOrder.getUpsellInfo();
		if (null != soAddons){
			for (ServiceOrderAddonVO soAddon: soAddons){
				if(soAddon.getSku().equals("99888")){
					addOnPermitPrice = addOnPermitPrice.add(MoneyUtil.getRoundedMoneyBigDecimal(soAddon.getRetailPrice()*soAddon.getQuantity()));
				}else{
					addOnPrice = addOnPrice.add(getAddonPrice(soAddon));					
				}
			}
		}
		//round addon price 
		/*if(addOnPrice!=null){
			addOnPrice=addOnPrice.setScale(2, RoundingMode.HALF_DOWN);
		}*/
		totalPermit = totalPermit.add(addOnPermitPrice);
		finalPrice.setFinalPriceForAddon(addOnPrice);
		List<ProviderInvoicePartsVO> invoicePartsVOs = serviceOrder.getInvoiceParts();
		if(null != invoicePartsVOs){
			for(ProviderInvoicePartsVO invoicePartsVO : invoicePartsVOs){
				invoicePartsPrice = invoicePartsPrice.add(invoicePartsVO.getFinalPrice());
			}
		}
		//round invoice parts price
		if(invoicePartsPrice!=null){
		  invoicePartsPrice=MoneyUtil.getRoundedMoneyBigDecimal(invoicePartsPrice.doubleValue());
			
		}
		finalPrice.setFinalPriceForPartsInvoice(invoicePartsPrice);
		
		if(labour!=null && parts!=null){
			//finalTotalPrice = labour.add(parts);
			finalTotalPrice = finalPrice.getFinalPriceForLabor().add(finalPrice.getFinalPriceForParts()==null?new BigDecimal("0.00"):finalPrice.getFinalPriceForParts());
		}
		if(totalPermit!=null){
			finalTotalPrice = finalTotalPrice.add(totalPermit);
		}
		if(addOnPrice!=null){
			finalTotalPrice = finalTotalPrice.add(addOnPrice);
		}
		if(invoicePartsPrice !=null){
			finalTotalPrice = finalTotalPrice.add(invoicePartsPrice);

		}
		//round finalprice
		if(finalTotalPrice!=null){
			finalTotalPrice=MoneyUtil.getRoundedMoneyBigDecimal(finalTotalPrice.doubleValue());
		}
		currentprice.setTotalFinalPrice(finalTotalPrice);
		
		
		if(serviceOrder.getWfStateId() == 160 || serviceOrder.getWfStateId()==180){
			
			finalTotalPrice = finalTotalPrice==null?new BigDecimal(0):finalTotalPrice;

			BigDecimal serviceFee = MoneyUtil.getRoundedMoneyCustomBigDecimal((finalTotalPrice.doubleValue() - totalPermitPrice)*0.1d);
			serviceFee = serviceFee==null?new BigDecimal(0):serviceFee;
			currentprice.setProviderServiceFee(serviceFee);
			currentprice.setProviderPayment(MoneyUtil.getRoundedMoneyBigDecimal(finalTotalPrice.doubleValue() - serviceFee.doubleValue()));
			//currentprice.setProviderServiceFee((finalTotalPrice.subtract(new BigDecimal(totalPermitPrice)).multiply(new BigDecimal(0.10))));

		}		
	}
	
    public BigDecimal getAddonPrice(ServiceOrderAddonVO soAddon){
        //return this.getRetailPrice().multiply(new BigDecimal(1 - this.margin)).setScale(2, RoundingMode.HALF_EVEN).multiply(new BigDecimal(this.quantity)).setScale(2, RoundingMode.HALF_EVEN);
     	BigDecimal addonPrice = MoneyUtil.getRoundedMoneyBigDecimal(soAddon.getQuantity() * (soAddon.getRetailPrice() - (soAddon.getRetailPrice() * soAddon.getMargin())));
        return addonPrice;
    }	

	/**
	 * @param serviceOrder
	 * @param maxPrice
	 * @param currentprice
	 * 
	 * this method maps max price to current price
	 * 
	 */
	private void mapMaxPrice(ServiceOrder serviceOrder, MaxPrice maxPrice, CurrentPrice currentprice) {
		BigDecimal labour;
		BigDecimal parts;
		BigDecimal permitTaskPrice = new BigDecimal("0.00");

		labour = new BigDecimal(serviceOrder.getSpendLimitLabor());
		
		parts = new BigDecimal(serviceOrder.getSpendLimitParts());
		
		List<ServiceOrderTask> soTasks = serviceOrder.getActiveTasks(serviceOrder.getTasks());
		permitTaskPrice = mapCustomerPrePaid(soTasks, permitTaskPrice);
		logger.info("permit price is : "+permitTaskPrice);
		//permitTaskPrice=permitTaskPrice.setScale(2, RoundingMode.HALF_DOWN);
		permitTaskPrice=MoneyUtil.getRoundedMoneyBigDecimal(permitTaskPrice.doubleValue());
		if(null != permitTaskPrice && !(permitTaskPrice.toString().equals(ZERO.toString()))){
			maxPrice.setCustomerPrePaid(permitTaskPrice);
		}
		
		if(labour!=null && permitTaskPrice!=null){
			labour = labour.subtract(permitTaskPrice);
		}
		if(labour!=null){
			//labour=labour.setScale(2, RoundingMode.HALF_DOWN);
			labour=MoneyUtil.getRoundedMoneyBigDecimal(labour.doubleValue());
		}
		maxPrice.setMaxLabourPrice(labour);
		
		if(parts!=null){
			//parts=parts.setScale(2, RoundingMode.HALF_DOWN);
			parts=MoneyUtil.getRoundedMoneyBigDecimal(parts.doubleValue());
		}
		maxPrice.setMaxPartsPrice(parts);

		
		
		currentprice.setTotalMaxPrice((labour.add(parts)));

	}

	/**
	 * @param soTasks
	 * @param permitTaskPrice
	 * 
	 * this method maps customer pre-paid for max price
	 * 
	 */
	private BigDecimal mapCustomerPrePaid(List<ServiceOrderTask> soTasks,
			BigDecimal permitTaskPrice) {

		Double totalPermitPrice = new Double(0.00);
		if (null != soTasks){
			for(ServiceOrderTask task: soTasks){
				if(PERMIT_TASK.equals(task.getTaskType())){
					if(null!= task.getSellingPrice() && null!= task.getFinalPrice() && task.getSellingPrice().doubleValue()>task.getFinalPrice().doubleValue()){
						totalPermitPrice = totalPermitPrice + task.getFinalPrice().doubleValue();
					}else if(null!= task.getSellingPrice() && null!= task.getFinalPrice() && task.getSellingPrice().doubleValue()<=task.getFinalPrice().doubleValue()){
						totalPermitPrice = totalPermitPrice + task.getSellingPrice().doubleValue();
					}
				
				}
	    	
	    	}
		}
		if(totalPermitPrice != null && totalPermitPrice >= 0){
			permitTaskPrice = new BigDecimal(totalPermitPrice);
		}
		return permitTaskPrice;
	}

	/**
	 * This method is for mapping the orderstatus Response
	 *
	 * @param serviceOrder
	 *            ServiceOrder
	 * @param orderStatus
	 *            OrderStatus
	 * @return void
	 */
	private void mapOrderStatus(ServiceOrder serviceOrder,
			OrderStatus orderStatus) {

		if (null != serviceOrder.getCreatedDate()) {
			orderStatus.setCreatedDate(CommonUtility.sdfToDate
					.format(serviceOrder.getCreatedDate()));
		}
		if (null != serviceOrder.getRoutedDate()) {
			orderStatus.setPostedDate(CommonUtility.sdfToDate
					.format(serviceOrder.getRoutedDate()));
		}
		orderStatus.setSoId(StringUtils.isEmpty(serviceOrder.getSoId()) ? ""
				: serviceOrder.getSoId());
		orderStatus
		.setStatus(StringUtils.isEmpty(serviceOrder.getStatus()) ? ""
				: serviceOrder.getStatus());
		orderStatus.setSubstatus(StringUtils.isEmpty(serviceOrder
				.getSubStatus()) ? "" : serviceOrder.getSubStatus());

		ProblemResolutionSoVO pbResVo = null;

		try {
			pbResVo = serviceOrderBO.getProblemDesc(serviceOrder.getSoId());
		}  catch (Exception e) {
			logger.error("Exception Occurred" + e.getMessage());
		}


		if(null!=pbResVo)
			orderStatus.setProblemDescription(pbResVo.getPbComment());
	}

	public IServiceOrderBO getServiceOrderBO() {
		return serviceOrderBO;
	}

	public void setServiceOrderBO(IServiceOrderBO serviceOrderBO) {
		this.serviceOrderBO = serviceOrderBO;
	}


}