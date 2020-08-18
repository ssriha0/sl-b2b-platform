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

public class CancelOrderSignal extends Signal {
	
	private String reasonCode;
	private String reasonComment;
	private Integer reasonCodeId;
	private Double cancelAmount=0.0;
	private Identification id;
	private String userName;
	private static final String manageScopeSource = "CANCELLATION";
	
	private String reason;
	private String comment;
	 /**
     * @param soe
     * @param serviceOrder
     */
	
	@Override
	public void accessMiscParams(Map<String, Serializable> miscParams, Identification id, ServiceOrder so) {
		reasonCode = (String)miscParams.get("REASON_CODE");
		if(null != miscParams.get("REASON_CODE_ID")){
			reasonCodeId = Integer.valueOf((String)miscParams.get("REASON_CODE_ID"));
		}
		reasonComment = (String)miscParams.get("REASON_COMMENTS");
		if(null != miscParams.get(OrderfulfillmentConstants.PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT)){
			cancelAmount = Double.valueOf((String)miscParams.get(OrderfulfillmentConstants.PVKEY_RQSTD_ACTIVE_CANCELLATION_AMT));
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
	
    @Override
    public void update(SOElement soe, ServiceOrder so) {
    	SOElementCollection soec = (SOElementCollection)soe;
    	
		if(null == so.getTasks() || so.getTasks().isEmpty()){
			so.setTasks(new ArrayList<SOTask>());
		}
		
		for(SOTask task:so.getActiveTasks()){
			if(null != task.getFinalPrice()){
				// to update task history for price $0
				if(task.getFinalPrice().compareTo(new BigDecimal(0.0))!=0)
				{
				task.setFinalPrice(new BigDecimal(0.0));
				PricingUtil.addTaskPriceHistory(task, id.getBuyerResourceId().toString(),id.getFullname());
				}
			}
			task.setTaskStatus(TaskStatus.CANCELED.name());
		}
		
		if(!soec.getElements().isEmpty()){
			//updating the so_manage_scope table
			SOManageScope manageScope = new SOManageScope();
			manageScope.setReasonCodeId(reasonCodeId);
			manageScope.setReasonComments(reasonComment);
			manageScope.setCreatedDate(new Date());
			manageScope.setServiceOrder(so);
			manageScope.setModifiedBy(userName);
			manageScope.setManageScopeSource(manageScopeSource);
			serviceOrderDao.save(manageScope);
		
			for(SOElement element:soec.getElements()){
				int lastSeqNum = so.getTasks().size();
				SOTask task = (SOTask)element;
				task.setServiceOrder(so);
				task.setSoManageScope(manageScope);
				task.setTaskSeqNum(lastSeqNum+1);
				task.setSortOrder(lastSeqNum);
				PricingUtil.addTaskPriceHistory(task, id.getBuyerResourceId().toString(),id.getFullname());
				so.getTasks().add(task);
			}
		}		
		
		//update price in SO_PRICE table
		// update TotalAddonPriceGL to zero in SO_PRICE table as this will be considered for CEX calculation in GL.
		if(null!=so.getPrice() && null!=cancelAmount){
			so.getPrice().setDiscountedSpendLimitLabor(new BigDecimal(cancelAmount).setScale(2, RoundingMode.HALF_UP));
			so.getPrice().setDiscountedSpendLimitParts(new BigDecimal(0.0));
			so.getPrice().setTotalAddonPriceGL(new BigDecimal(0.0));
		}
		
		// deleting invoice parts  as this will be considered for CEX calculation in GL.
		
		if(so.getSoProviderInvoiceParts()!=null)
		{
		 for (SOProviderInvoiceParts providerInvoiceParts : so.getSoProviderInvoiceParts()) {
				serviceOrderDao.delete(providerInvoiceParts);
			}	
		}
		
		so.setSoProviderInvoiceParts(null);
		

		// setting the quantity of Addons to zero.
		if(so.getAddons() != null){
			for(SOAddon tadd : so.getAddons()){
				
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
			else{
				newSOPriceChangeHistory.setAction(OFConstants.ORDER_CANCELLED);
			}
			newSOPriceChangeHistory.setReasonComment(reason+" - "+comment);
			
			// call generic method to save so price change history
			PricingUtil.addSOPriceChangeHistory(so,newSOPriceChangeHistory, id.getBuyerResourceId().toString(), id.getFullname());						
		}
		serviceOrderDao.update(so);
    	
    }
    
}