package com.servicelive.orderfulfillment.signal;

import com.servicelive.orderfulfillment.domain.LeadCancel;
import com.servicelive.orderfulfillment.domain.LeadElement;
import com.servicelive.orderfulfillment.domain.LeadHdr;
import com.servicelive.orderfulfillment.domain.LeadPostedFirm;

public class CancelLeadSignal extends Signal{
    /**
     * @param leadElemet
     * @param leadObj
     */
    protected void updateLead(LeadElement leadElement, LeadHdr targetleadHdr)  {
        logger.info("Begin update Method of CancelLeadSignal:");
        //Updating lead hdr section
        LeadHdr sourceleadHdr = (LeadHdr) leadElement;
        targetleadHdr.setModifiedDate(sourceleadHdr.getModifiedDate());
        if(sourceleadHdr.getLeadWfStatus()!=null)
        {
        targetleadHdr.setLeadWfStatus(sourceleadHdr.getLeadWfStatus());
        }
        //updating  lead cancel section
        //targetleadHdr.setCancelLead(sourceleadHdr.getCancelLead());
        //targetleadHdr=mapLeadCancellation(sourceleadHdr,targetleadHdr);
        targetleadHdr.setCancelLeadFirms(sourceleadHdr.getCancelLeadFirms());
        
        //updating lead contact info section
        if(sourceleadHdr.getLeadContactInfo()!=null)
        {
        	targetleadHdr.getLeadContactInfo().setSwyrReward(sourceleadHdr.getLeadContactInfo().getSwyrReward());
        }
        
        //updating posted providers wf status
         targetleadHdr=mapPostedFirms(sourceleadHdr,targetleadHdr);
        logger.info("End update Method of CancelLeadSignal.");
    }  
    
    
    private LeadHdr mapPostedFirms(LeadHdr source, LeadHdr target) {
    	
    	for(LeadPostedFirm targetFirm:target.getPostedFirms())
    	{
    		for(LeadPostedFirm sourceFirm:source.getPostedFirms())
    		{
    			if(targetFirm.getVendorId().equals(sourceFirm.getVendorId()))
    			{
    				targetFirm.setLeadFirmStatus(sourceFirm.getLeadFirmStatus());
    			}
    		}
    	}
    	return target;

    }
    
    private LeadHdr mapLeadCancellation(LeadHdr source, LeadHdr target) {
    	
    	for(LeadCancel targetCancel:target.getCancelLeadFirms())
    	{
    		for(LeadCancel sourceCancel:source.getCancelLeadFirms())
    		{
    			if(targetCancel.getVendorId().equals(sourceCancel.getVendorId()))
    			{
    				//targetFirm.setLeadFirmStatus(sourceFirm.getLeadFirmStatus());
    				targetCancel.setCancellationComments(sourceCancel.getCancellationComments());
    				targetCancel.setCancellationReasonCode(sourceCancel.getCancellationReasonCode());
    				targetCancel.setModifiedBy(sourceCancel.getModifiedBy());
    				targetCancel.setVendorId(sourceCancel.getVendorId());
    				targetCancel.setCancelInitiatedBy(sourceCancel.getCancelInitiatedBy());
    			}
    		}
    	}
    	return target;

    }
}
