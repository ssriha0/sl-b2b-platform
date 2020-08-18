/**
 * 
 */
package com.servicelive.orderfulfillment.decision;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jbpm.api.model.OpenExecution;

import com.servicelive.orderfulfillment.common.OFConstants;
import com.servicelive.orderfulfillment.common.ProcessVariableUtil;
import com.servicelive.orderfulfillment.common.ServiceOrderException;
import com.servicelive.orderfulfillment.domain.SOWorkflowControls;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * @author Infosys
 *
 */
public class EvaluateRecallOrder extends AbstractServiceOrderDecision {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(EvaluateRecallOrder.class);
	
	public String decide(OpenExecution execution) {
		String repeatRepairOrderNumber=null;
    	String repeatRepairUnitNumber=null;
    	String warrantyOrder=null;
    	//Setting default status for notes.
    	String evaluateCriteria="WaitInDraftForRepeatRepair";
    	execution.setVariable(ProcessVariableUtil.REPEAT_REPAIR_NOTE,"noEligibleProviderFound");
		ServiceOrder serviceOrder = getServiceOrder(execution);
		repeatRepairOrderNumber = serviceOrder.getCustomRefValue(OFConstants.REPEAT_REPAIR_ORDER_NUMBER);
		logger.info("REPEAT_REPAIR_ORDER_NUMBER"+ repeatRepairOrderNumber);
		repeatRepairUnitNumber = serviceOrder.getCustomRefValue(OFConstants.REPEAT_REPAIR_UNIT_NUMBER);
		logger.info("REPEAT_REPAIR_UNIT_NUMBER"+ repeatRepairUnitNumber);
		if(StringUtils.isNotBlank(repeatRepairOrderNumber) && StringUtils.isNotBlank(repeatRepairUnitNumber)){
			warrantyOrder = serviceOrderDao.getInhomeWarrantyOrder(repeatRepairOrderNumber,repeatRepairUnitNumber);
			logger.info("Warranty Order for"+ serviceOrder.getSoId()+ "is"+ warrantyOrder);
			if(StringUtils.isNotBlank(warrantyOrder)){
				logger.info("Service Order associated with the "+serviceOrder.getSoId()+" is"+ warrantyOrder);
				//Set firm id and firm Name in service Order
				setWarrantyProviderFirm(serviceOrder,warrantyOrder);
				//Update Original Order Id in so_workflow_control
				setWorkFlowControls(serviceOrder,warrantyOrder,execution);
				        //if no provider firm is not found for the warranty order
						if(null!= serviceOrder.getInhomeAcceptedFirm()){
							evaluateCriteria = eavluateProviderFirmCompliance(serviceOrder,warrantyOrder,execution);
						}else{
							evaluateCriteria = "WaitInDraftForRepeatRepair";
						}
			 }else{
				 evaluateCriteria = "WaitInDraftForRepeatRepair";
				
			 }
		}//No warranty Order associated with the service order.Need to add note and move to draft status.
		else{
			evaluateCriteria = "WaitInDraftForRepeatRepair";
		}
		
		
		return evaluateCriteria;
	}
	/**@description : This will validate the firm compliance in SL and SPN and route the order based on following business
	 *   1) Firm Compliance in SL and SPN : Auto Route and Auto Accept===> Return hasProviders.
	 *   2) Firm Not Compliance in SL,but Compliance in SPN : CAR Route ==> Return carRoute
	 *   3) Firm Compliance in SL and Not Compliance in SPN : Place In Draft Status ==> Return noProviders
	 *   4) Firm Not Compliance in SL and Not Compliance in SPN : CAR Route==> Return carRoute
	 * @param serviceOrder
	 * @param warrantyOrder
	 * @param execution
	 * @return
	 */
	private String eavluateProviderFirmCompliance(ServiceOrder serviceOrder,String warrantyOrder, OpenExecution execution) {
		String evaluateCriteria="noProviders";
		Integer spnId = serviceOrder.getSpnId();
		Long vendorId = serviceOrder.getInhomeAcceptedFirm();
		boolean isFirmSLCompliance = false;
		boolean isFirmSpnCompliance = false;
		try{
			isFirmSLCompliance = serviceOrderDao.validateFirmStatus(vendorId);
			if(null!= spnId){
			   isFirmSpnCompliance = serviceOrderDao.validateFirmSpnCompliance(spnId, vendorId);
			}else{
			   //To do: expected behavior if NO SPN is available for current Order.
			   execution.setVariable(ProcessVariableUtil.REPEAT_REPAIR_NOTE,"noSpnFoundForOrder");
			   return evaluateCriteria;
			}
			if(isFirmSLCompliance && isFirmSpnCompliance){
				evaluateCriteria ="hasProviders";
				logger.info("firm is both SL and SPN complaince.hasproviders");
			}else if(!isFirmSLCompliance && isFirmSpnCompliance){
				logger.info("firm is SPN complaince.carRoute");
				evaluateCriteria ="carRoute";
				execution.setVariable(ProcessVariableUtil.REPEAT_REPAIR_NOTE,"providerFirmNotComplianceSL");
			}else if(isFirmSLCompliance && !isFirmSpnCompliance){
				logger.info("firm is SL complaince.noProviders");
				evaluateCriteria ="noProviders";
				execution.setVariable(ProcessVariableUtil.REPEAT_REPAIR_NOTE,"providerFirmNotComplianceSPN");
			}else{
				logger.info("firm is not complaince.carRoute");
				execution.setVariable(ProcessVariableUtil.REPEAT_REPAIR_NOTE,"providerFirmNotCompliance");
				evaluateCriteria ="carRoute";
			}
			
		}catch (Exception e) {
			logger.info("Exception in evaluating Providers for Inhome repeat Repair Order"+ e);
			throw new ServiceOrderException("Exception in evaluating Providers for Inhome repeat Repair Order");
		}
		return evaluateCriteria;
	}
	/**@Description: Setting original provider Firm id and name in service order transient variable.
	 * @param serviceOrder
	 * @param originalSoId
	 */
	private void setWarrantyProviderFirm(ServiceOrder serviceOrder,String originalSoId) throws ServiceOrderException {
		Long acceptedFirm = null;
		String firmName = null;
		ServiceOrder originalSo = serviceOrderDao.getServiceOrder(originalSoId);
		if(originalSo.getWfStateId().equals(OFConstants.SO_COMPLETED)||
				originalSo.getWfStateId().equals(OFConstants.SO_CLOSED)){
			acceptedFirm = originalSo.getAcceptedProviderId();
			if(null!=acceptedFirm && null!= originalSoId){
			    serviceOrder.setInhomeAcceptedFirm(acceptedFirm);
			    firmName = serviceOrderDao.getProviderFirmName(acceptedFirm);
			    serviceOrder.setInhomeAcceptedFirmName(firmName);
			}
			  
		}
		
		
	}

	/**@Description: Setting original so in workflow control
	 * @param serviceOrder
	 */
	private void setWorkFlowControls(ServiceOrder serviceOrder,String originalSoId,OpenExecution execution) {
		SOWorkflowControls soWorkflowControls = serviceOrder.getSOWorkflowControls();
		logger.info("entered into setworkflowcontrols method. value  for original_so_id and firm are"+originalSoId+","+ serviceOrder.getInhomeAcceptedFirm());
		if(StringUtils.isNotBlank(originalSoId) && null!= serviceOrder.getInhomeAcceptedFirm()){
			//Setting the original so in process variable for logging purpose
			execution.setVariable(ProcessVariableUtil.ORIGINAL_SO_ID,originalSoId);
			soWorkflowControls.setOriginalSoId(originalSoId);
			soWorkflowControls.setWarrantyProviderFirm(serviceOrder.getInhomeAcceptedFirm());
			serviceOrder.setSOWorkflowControls(soWorkflowControls);
			logger.info("inside setworkflowcontrols.Value for original_so_id and firm are"+soWorkflowControls.getOriginalSoId()+","+soWorkflowControls.getWarrantyProviderFirm());
		}
		
	}

}
