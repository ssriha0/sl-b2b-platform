package com.servicelive.orderfulfillment.signal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.servicelive.orderfulfillment.domain.LeadCancel;
import com.servicelive.orderfulfillment.domain.LeadElement;
import com.servicelive.orderfulfillment.domain.LeadHdr;
import com.servicelive.orderfulfillment.domain.LeadPostedFirm;

public class CompleteLeadSignal extends Signal{
    /**
     * @param leadElemet
     * @param leadObj
     */
    protected void updateLead(LeadElement leadElemet, LeadHdr targetleadHdr)  {
        logger.info("Begin update Method of CompleteLeadSignal:");
        //Updating lead hdr section
        LeadHdr sourceleadHdr = (LeadHdr) leadElemet;
        targetleadHdr.setCompletedDate(sourceleadHdr.getCompletedDate());
        targetleadHdr.setCompletedTime(sourceleadHdr.getCompletedTime());
        targetleadHdr.setCompletionComments(sourceleadHdr.getCompletionComments());
        targetleadHdr.setLeadLaborPrice(sourceleadHdr.getLeadLaborPrice());
        targetleadHdr.setLeadMaterialPrice(sourceleadHdr.getLeadMaterialPrice());
        BigDecimal finalPrice = null;
        try{
        	if(null!=sourceleadHdr.getLeadMaterialPrice()){
        	finalPrice = (sourceleadHdr.getLeadLaborPrice()).add(sourceleadHdr.getLeadMaterialPrice());
        }
        else{
        	finalPrice = sourceleadHdr.getLeadLaborPrice();
        }
        targetleadHdr.setLeadFinalPrice(finalPrice);
        }
        catch(Exception e){
            logger.info("Error in complete setting final price");
        }
        targetleadHdr.setLeadWfStatus(3);
        targetleadHdr.setModifiedDate(sourceleadHdr.getModifiedDate());
        targetleadHdr=mapPostedFirms(sourceleadHdr,targetleadHdr);
        logger.info("End update Method of CompleteLeadSignal.");
    }

	private LeadHdr mapPostedFirms(LeadHdr source, LeadHdr target) {
		LeadPostedFirm selectedFirm = source.getPostedFirms().get(0);
		List<LeadCancel> cancelProviderFirm=new ArrayList<LeadCancel>();
		Integer vendorId=selectedFirm.getVendorId();
		logger.info("Selected Vendor_Id : " + vendorId);
		logger.info("Selected CancelLeadFirms List Size : " + target.getCancelLeadFirms().size());
		for(LeadPostedFirm targetFirm:target.getPostedFirms()){
			    logger.info("Vendor_Id : " + targetFirm.getVendorId());
			    if(vendorId.equals(targetFirm.getVendorId())){
			    	targetFirm.setLeadFirmStatus(selectedFirm.getLeadFirmStatus());
			    	targetFirm.setResourceId(selectedFirm.getResourceId());
			    	targetFirm.setNoOfVisits(selectedFirm.getNoOfVisits());
			       }else{
			    	   boolean flag = false;
			    	   for(LeadCancel leadCancelVO:target.getCancelLeadFirms()){
			    		   logger.info("Cancelled Vendor_Id : " + leadCancelVO.getVendorId());
			    		   if(targetFirm.getVendorId().equals(leadCancelVO.getVendorId())){
			    			   flag = true;
			    			   logger.info("Vendor_Id exist in Cancelled List : " + leadCancelVO.getVendorId());
			    		   }
			    	   }
			    	   logger.info("Flag : " + flag);
			    	   if(!flag){
					    	targetFirm.setLeadFirmStatus(5);
					    	//create entry for lead cancellation
					    	LeadCancel leadCancelFirm=new LeadCancel();
					    	leadCancelFirm.setLead(target);
					    	leadCancelFirm.setCancelInitiatedBy("System");
					    	leadCancelFirm.setCancellationComments("Lead was completed by another provider");
					    	leadCancelFirm.setModifiedBy("System");
					    	leadCancelFirm.setVendorId(targetFirm.getVendorId());
					    	leadCancelFirm.setPrevState(targetFirm.getLeadFirmStatus());
							cancelProviderFirm.add(leadCancelFirm);
			    	   } 
			    }
		       }
		    target.setCancelLeadFirms(cancelProviderFirm);
		 return target;
	}   
}
