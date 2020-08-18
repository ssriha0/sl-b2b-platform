package com.servicelive.orderfulfillment.signal;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOElementCollection;
import com.servicelive.orderfulfillment.domain.SOManageScope;
import com.servicelive.orderfulfillment.domain.SOPriceChangeHistory;
import com.servicelive.orderfulfillment.domain.SOTask;
import com.servicelive.orderfulfillment.domain.ServiceOrder;
import com.servicelive.orderfulfillment.domain.type.SOFieldsChangedType;
import com.servicelive.orderfulfillment.domain.type.SOPriceType;
import com.servicelive.orderfulfillment.domain.util.PricingUtil;
import com.servicelive.orderfulfillment.serviceinterface.vo.Identification;

public class ManageScopeSignal extends Signal {
	
	private String reasonCode;
	private String reasonComment;
	private String userName;
	private Integer reasonCodeId;
	private Identification id;
	private static final String manageScopeSource = "MANAGE_SCOPE";

	@Override
	public void accessMiscParams(Map<String, Serializable> miscParams, Identification id, ServiceOrder so) {
		reasonCode = (String)miscParams.get("REASON_CODE");
		reasonCodeId = Integer.valueOf((String)miscParams.get("REASON_CODE_ID"));
		reasonComment = (String)miscParams.get("REASON_COMMENTS");
		userName = id.getUsername();
		this.id = id;
	}
	
	@Override
	protected List<String> validate(SOElement soe, ServiceOrder soTarget) {
		List<String> returnVal = new ArrayList<String>();
		SOElementCollection soec = (SOElementCollection)soe;
		for(SOElement element: soec.getElements()){
			if(!(element instanceof SOTask)) {
				returnVal.add("Expected ServiceOrder but found " + soe.getTypeName());
	            return returnVal;
			}
		} 
		if(!soTarget.getPriceType().equals(SOPriceType.TASK_LEVEL.name())){
			returnVal.add("Scope change not possible for this type of SO. Use increase spend limit instead");
		}
		return returnVal;
	}
	
	@Override
	protected void update(SOElement soe, ServiceOrder so) {
		SOElementCollection soec = (SOElementCollection)soe;
		if(so.getTasks() == null || so.getTasks().isEmpty()){
			so.setTasks(new ArrayList<SOTask>());
		}
	
		//updating the so_manage_scope table
		SOManageScope manageScope = new SOManageScope();
		manageScope.setReasonCodeId(reasonCodeId);
		manageScope.setReasonComments(reasonComment);
		manageScope.setCreatedDate(new Date());
		manageScope.setServiceOrder(so);
		manageScope.setModifiedBy(userName);
		manageScope.setManageScopeSource(manageScopeSource);		
		serviceOrderDao.save(manageScope);
				
		BigDecimal additionalPrice = new BigDecimal("0.00").setScale(2);
		for(SOElement element:soec.getElements()){
			int lastSeqNum = so.getTasks().size();
			SOTask task = (SOTask)element;
			task.setServiceOrder(so);
			task.setSoManageScope(manageScope);
			task.setTaskSeqNum(lastSeqNum+1);
			task.setSortOrder(lastSeqNum);
			PricingUtil.addTaskPriceHistory(task, id.getBuyerResourceId().toString(),id.getFullname());
			so.getTasks().add(task);
			additionalPrice = additionalPrice.add(task.getFinalPrice());
		}
			
		//update price
		so.setSpendLimitLabor(so.getSpendLimitLabor().add(additionalPrice));
		so.getPrice().setDiscountedSpendLimitLabor(so.getPrice().getDiscountedSpendLimitLabor().add(additionalPrice));
		
		/** 
		* SL-18007 setting so price change history if there is a change in the so spend limit labour 
		* during scope change through front end  
		*/
		if (null != so.getSoPriceChangeHistory()){
			
			logger.info("18007: so price change history contins:" + so.getSoPriceChangeHistory());
								
			SOPriceChangeHistory newSOPriceChangeHistory = new SOPriceChangeHistory();
			//set specific components before passing to the generic method
			newSOPriceChangeHistory.setAction(OFConstants.SCOPE_UPDATED);
			newSOPriceChangeHistory.setReasonComment(reasonCode+" - "+reasonComment);
			so.setFromManageScope(true);
			//newSOPriceChangeHistory.setReasonRoleId(OFConstants.BUYER_ROLE);
			// call generic method to save so price change history
			PricingUtil.addSOPriceChangeHistory(so,newSOPriceChangeHistory, id.getBuyerResourceId().toString(), id.getFullname());						
		}
		
		serviceOrderDao.update(so);
	}
	
	@Override
	protected HashMap<String, Object> getLogMap(SOElement request,
			Identification id, ServiceOrder target)
			throws ServiceOrderException {
		HashMap<String,Object> map = new HashMap<String,Object>();
		BigDecimal maxPrice = new BigDecimal(0.00);
		if (null != target.getSpendLimitLabor()){
			maxPrice = maxPrice.add(target.getSpendLimitLabor());
		}
		if (null != target.getSpendLimitParts()){
			maxPrice = maxPrice.add(target.getSpendLimitParts());
		}
		/* Commented: permit price is included in spendlimitlabor (SL-17937)
		 * if (null != target.getTotalPermitPrice()){
			maxPrice = maxPrice.add(target.getTotalPermitPrice());
		} */
		StringBuilder skuList = new StringBuilder();
		SOElementCollection soec = (SOElementCollection)request;
		for(SOElement soe:soec.getElements()){
			SOTask task = (SOTask)soe;
			maxPrice = maxPrice.add(task.getFinalPrice());
			
			String sku = "";
    		if (null!=task.getExternalSku()){
    			if ("0".equals(task.getExternalSku())){
    				sku  = "NA";
    			}else{
    				sku = task.getExternalSku();
    			}
    		}			
			skuList.append(sku).append(",");
		}
		skuList.deleteCharAt(skuList.lastIndexOf(","));
		map.put("SO_MAX_PRICE", maxPrice);
		map.put("SKU_LIST", skuList.toString());
		return map;
	}
	
	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonComment() {
		return reasonComment;
	}

	public void setReasonComment(String reasonComment) {
		this.reasonComment = reasonComment;
	}

	public Integer getReasonCodeId() {
		return reasonCodeId;
	}

	public void setReasonCodeId(Integer reasonCodeId) {
		this.reasonCodeId = reasonCodeId;
	}

	public Identification getId() {
		return id;
	}

	public void setId(Identification id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
