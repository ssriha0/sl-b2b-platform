package com.servicelive.orderfulfillment.signal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.servicelive.domain.so.TaskStatus;
import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.domain.SOAddon;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.SOManageScope;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOProviderInvoiceParts;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.serviceinterface.OrderfulfillmentConstants;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;
/**
 * Signal class for Cancellation API (v1.2) SL 18041
 * 
 * */
public class CancelOrderSignalAPI extends Signal {
	private Double cancelAmount = Double.valueOf(0);
	private Identification id;
	private String userName;
	private String reasonComment;
	private Integer reasonCodeId;
	private String reasonCode;
	private static final String manageScopeSource = "CANCELLATION";
	
	private String reason;
	private String comment;

	/**
	 * Sets cancel amount from misc parameters
	 * */
	@Override
	public void accessMiscParams(Map<String, Serializable> miscParams,
			Identification id, ServiceOrder so) {
		reasonCode = (String)miscParams.get(OrderfulfillmentConstants.PVKEY_MANAGESCOPE_REASON_CODE);
		if(null != miscParams.get(OrderfulfillmentConstants.PVKEY_MANAGESCOPE_REASON_CODE_ID)){	
			reasonCodeId = Integer.valueOf((String)miscParams.get(OrderfulfillmentConstants.PVKEY_MANAGESCOPE_REASON_CODE_ID));
		}
		reasonComment = (String)miscParams.get(OrderfulfillmentConstants.PVKEY_MANAGESCOPE_REASON_CODE_COMMENTS);
		if (null != miscParams
				.get(OrderfulfillmentConstants.PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT)) {
			cancelAmount = Double
					.valueOf((String) miscParams
							.get(OrderfulfillmentConstants.PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT));
		}
		//SL-18007
		if(null != miscParams.get(OrderfulfillmentConstants.PVKEY_CANCELLATION_REASON)){
			reason = (String)miscParams.get(OrderfulfillmentConstants.PVKEY_CANCELLATION_REASON);
		}
		if(null != miscParams.get(OrderfulfillmentConstants.PVKEY_CANCELLATION_COMMENT)){
			comment = (String)miscParams.get(OrderfulfillmentConstants.PVKEY_CANCELLATION_COMMENT);
		}
		userName = id.getUsername();
		this.id = id;
	}

	/**
	 * Sets the tasks final price, status and spend limit labor for SO..
	 * @param ServiceOrder
	 * @param SOElement
	 * */
	
	@Override
	public void update(SOElement soe, ServiceOrder so) {
		SOElementCollection soec = (SOElementCollection) soe;
		//Update the Active tasks.
		updateTasks(so);
		//Add work order SKU for TASK_LEVEL so 
		addWorkOrderSKU(so,soec);
		// update price in SO_PRICE table
		// update TotalAddonPriceGL to zero in SO_PRICE table as this will be
		// considered for CEX calculation in GL.
		if (null != so.getPrice() && null != cancelAmount) {
			so.getPrice().setDiscountedSpendLimitLabor(
					new BigDecimal(cancelAmount).setScale(2,
							RoundingMode.HALF_UP));
			so.getPrice().setDiscountedSpendLimitParts(BigDecimal.ZERO);
			so.getPrice().setTotalAddonPriceGL(BigDecimal.ZERO);
		}

		// deleting invoice parts as this will be considered for CEX calculation
		// in GL.
		if (so.getSoProviderInvoiceParts() != null) {
			for (SOProviderInvoiceParts providerInvoiceParts : so
					.getSoProviderInvoiceParts()) {
				serviceOrderDao.delete(providerInvoiceParts);
			}
		}
		so.setSoProviderInvoiceParts(null);
		// setting the quantity of Add-ons to zero.
		if (so.getAddons() != null) {
			for (SOAddon tadd : so.getAddons()) {
				tadd.setQuantity(0);
			}
		}
		

		/** 
		* SL-18007 setting so price change history if there is a change in the so spend limit labour 
		* during cancellation 
		*/
		if (null != so.getSoPriceChangeHistory()){
			
			logger.info("18007: so price change history contins:" + so.getSoPriceChangeHistory());
								
			SOPriceChangeHistory newSOPriceChangeHistory = new SOPriceChangeHistory();
			so.setFromCancelFlow(true);
			//set specific components before passing to the generic method
			if(100 == so.getWfStateId()){
				newSOPriceChangeHistory.setAction(OFConstants.ORDER_DELETED);
			}
			else if (110 == so.getWfStateId()){
				newSOPriceChangeHistory.setAction(OFConstants.ORDER_VOIDED);
			}
			else {
				newSOPriceChangeHistory.setAction(OFConstants.ORDER_CANCELLED);
			}
			newSOPriceChangeHistory.setReasonComment(reason+" - "+comment);
			//newSOPriceChangeHistory.setReasonRoleId(OFConstants.BUYER_ROLE);
			// call generic method to save so price change history
			PricingUtil.addSOPriceChangeHistory(so,newSOPriceChangeHistory, id.getBuyerResourceId().toString(), id.getFullname());						
		}
		
		//Update SO 
		serviceOrderDao.update(so);
	}
	
	
	/** Updates Status of Active tasks as Cancelled and Price as zero.
	 * @param serviceOrder {@link ServiceOrder}
	 */
	
	private void updateTasks(ServiceOrder serviceOrder) {
		if (null == serviceOrder.getTasks() || serviceOrder.getTasks().isEmpty()) {
			serviceOrder.setTasks(new ArrayList<SOTask>());
		}
		for (SOTask task : serviceOrder.getActiveTasks()) {
			if (null != task.getFinalPrice() && BigDecimal.ZERO.compareTo(task.getFinalPrice())!= 0) {
				// to update task history for price $0
				task.setFinalPrice(BigDecimal.ZERO); 
				PricingUtil.addTaskPriceHistory(task, id.getBuyerResourceId().toString(), id.getFullname());
			}
			//Update the status of existing tasks as Deleted.
			task.setTaskStatus(TaskStatus.CANCELED.name());
		}		
	}

	/**  Adds Work order SKUs (exists only for TASK_LEVEL priced SOs.)
	 * @param serviceOrder
	 * @param soElements : A collection which contains Work order Tasks
	 * */
	private void addWorkOrderSKU(ServiceOrder serviceOrder, SOElementCollection soElements) {
		if (null != soElements && null != soElements.getElements()
				&& !soElements.getElements().isEmpty()) {
			/*SL 18041 doesn't consider manage scope. If it
			 * requires we need these code, provided manage scope
			 *  reason code and comments should be there.*/
			
			// updating the so_manage_scope table
			SOManageScope manageScope = new SOManageScope();
			manageScope.setReasonCodeId(reasonCodeId);
			manageScope.setReasonComments(reasonComment);
			manageScope.setCreatedDate(new Date());
			manageScope.setServiceOrder(serviceOrder);
			manageScope.setModifiedBy(userName);
			manageScope.setManageScopeSource(manageScopeSource);
			serviceOrderDao.save(manageScope);
	
			for (SOElement element : soElements.getElements()) {
				int lastSeqNum = serviceOrder.getTasks().size();
				SOTask task = (SOTask) element;
				task.setServiceOrder(serviceOrder);
				//Set manage scope to null as it is out of scope in this enhancement.
				task.setSoManageScope(manageScope);
				task.setTaskSeqNum(lastSeqNum + 1);
				task.setSortOrder(lastSeqNum);
				//Updates so_tasks_price_history
				PricingUtil.addTaskPriceHistory(task, id.getBuyerResourceId()
						.toString(), id.getFullname());
				serviceOrder.getTasks().add(task);
			}
		}
	}
}
