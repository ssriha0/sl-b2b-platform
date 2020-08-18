package com.newco.marketplace.api.services.leadsmanagement;
 




import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.api.utils.sax.SAXHandler;
import com.newco.marketplace.business.businessImpl.leadsmanagement.LeadProcessingBO;
import com.newco.marketplace.dto.vo.leadsmanagement.InsideSalesLeadCustVO;
import com.newco.marketplace.dto.vo.leadsmanagement.InsideSalesLeadVO; 
import com.newco.marketplace.interfaces.InsideSalesConstants;

public class InsideSalesLeadsService{
     
    private static Logger logger = Logger.getLogger(InsideSalesLeadsService.class);
    private LeadProcessingBO leadProcessingBO;
    private LeadManagementValidator leadManagementValidator;
     
    public String execute(String xml,String reason) {
               
        String response = "Lead Information updated sucessfully";          
        String processRecords = null;
        try {
            //  Get a switch to decide whether to update or not. 
            processRecords = leadProcessingBO.getPropertyValue("process_insidesales_leads");                         
            if(null!=processRecords && processRecords.equalsIgnoreCase("YES")){
                // Parse the string
            	logger.info("Request body xml"+xml);                     	
            	String values[] = xml.split("XML=|TIMESTAMP=|XML_EXPORT_REASON=");
            	// get the parameter list which include reason and the status.
            	List<String> parameterList=new ArrayList<String>();
            	       for(int count=0;count<values.length;count++){
            	    	  if(StringUtils.isNotBlank(values[count])){
            	    		  parameterList.add(values[count]);
            	    	  }
            	       }            	       
            	       if(parameterList.size()>1){
            	    	   reason=parameterList.get(2);
            	    	   xml=parameterList.get(0);
            	    	   
            	    	   logger.info("reason::"+reason);
            	    	   logger.info("encoded xml::"+xml);
            	    	   
            	    	   xml =  URLDecoder.decode(xml, "UTF-8"); // decode the XML
            	    	   xml = xml.substring(0, xml.length()-1); // Removing & 
            	    	   logger.info("decoded xml::"+xml);            	    	   
            	       }
            	 logger.info("reason obtained after splitting the requestBody"+reason);
            	 logger.info("xml obtained after splitting the requestBody"+xml);
            	 if(StringUtils.isNotBlank(reason)){
            	 reason=reason.trim();
            	 }
            	 if(StringUtils.isNotBlank(xml)){
            	 xml=xml.trim();  
            	 }
                InsideSalesLeadVO insideSalesLeadVO=new InsideSalesLeadVO();   
                List<InsideSalesLeadCustVO> insideSalesLeadCustList=new ArrayList<InsideSalesLeadCustVO>();
                InsideSalesLeadVO existingLead= null;
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser parser = factory.newSAXParser();
                InputStream inputs =new    ByteArrayInputStream(xml.getBytes());
                     SAXHandler saxHandler = new SAXHandler(InsideSalesConstants.GET_LEAD_ID); 
                     parser.parse(inputs, saxHandler);
                     insideSalesLeadVO=saxHandler.insideSalesVo;                
                if(null!=insideSalesLeadVO && null!=insideSalesLeadVO.getId() && StringUtils.isNotBlank(insideSalesLeadVO.getId())){                 	
                	// get the existing lead Information if exist
                	 existingLead=  leadProcessingBO.getInsideSalesLead(insideSalesLeadVO.getId());                	
                }else{
                	// if leadId in the xml is null or blank
                	logger.error("if leadId in the xml is null or blank");
                    response = "Lead Information update failed"; 
                    return response;
                }
                    // if XML_EXPORT_REASON is lead_edit or lead_status
                if(InsideSalesConstants.LEAD_EDIT.equals(reason) || InsideSalesConstants.LEAD_STATUS.equals(reason)){
                	// parse xml and get the data
                	InputStream is =new  ByteArrayInputStream(xml.getBytes());
                    SAXHandler handler = new SAXHandler(InsideSalesConstants.LEAD_INSERT);  
                    parser.parse(is, handler);
                    insideSalesLeadVO=handler.insideSalesVo;

                    insideSalesLeadCustList=handler.insideSalesLeadCustList; 
                    // if there exist a lead with the leadId then delete the lead
                	if(null!=existingLead && null!=existingLead.getIsleadId()){
                		// delete data from is_leads_custom_fields
                		leadProcessingBO.deleteLeadCustomFields(Integer.parseInt(existingLead.getId()));
                		// delete data from is_leads
                		leadProcessingBO.deleteLead(existingLead.getIsleadId());               		
                	}
                	// insert into is_leads              	
                	Integer isLeadId=leadProcessingBO.saveInsidesSalesLeadInfo(insideSalesLeadVO);

            		// insert into is_leads_custom_fields
                    if(!insideSalesLeadCustList.isEmpty()){
                   	 for(InsideSalesLeadCustVO insideSalesLeadCustVO:insideSalesLeadCustList){
                		 insideSalesLeadCustVO.setIsleadId(Integer.parseInt(insideSalesLeadVO.getId())); 
                	 }                    	 
                     	leadProcessingBO.saveInsidesSalesLeadCustInfo(insideSalesLeadCustList);                   	
                    } 
                    
                    if(null==existingLead || null==existingLead.getIsleadId()){
                	// parse xml and get the data to insert the status history with previous state as NULL
                	InputStream ins =new  ByteArrayInputStream(xml.getBytes());
                    SAXHandler shandler = new SAXHandler(InsideSalesConstants.LEAD_STATUS); 
                    parser.parse(ins, shandler); 
                    InsideSalesLeadVO insideSalesLead=shandler.insideSalesVo;
                    insideSalesLead.setIsleadId(Integer.parseInt(insideSalesLeadVO.getId()));
                    insideSalesLead.setCreatedDate(insideSalesLeadVO.getCreatedDate());
                    insideSalesLead.setPreviousStatus(null);
                    insideSalesLead.setId(insideSalesLeadVO.getId());            	
                 	leadProcessingBO.updateIsLeadStatusHistory(insideSalesLead); 
                 	  response = "Lead Information updated sucessfully";
                      return response;
                    }
                    
                  
            		// if there is a change in the lead status then insert into  is_leads_status_history
            	if( (null!=existingLead.getStatus()&& null==insideSalesLeadVO) ||
                    	(null==existingLead.getStatus()&& null!=insideSalesLeadVO)  ||
                    	(null!=existingLead.getStatus()&& null!=insideSalesLeadVO &&
                    	!existingLead.getStatus().equals(insideSalesLeadVO.getStatus()))){ 
                	// parse xml and get the data to insert the status history
                	InputStream ins =new  ByteArrayInputStream(xml.getBytes());
                    SAXHandler shandler = new SAXHandler(InsideSalesConstants.LEAD_STATUS); 
                    parser.parse(ins, shandler); 
                    insideSalesLeadVO=shandler.insideSalesVo;
                    insideSalesLeadVO.setIsleadId(Integer.parseInt(insideSalesLeadVO.getId()));
                    insideSalesLeadVO.setCreatedDate(existingLead.getCreatedDate());
                    insideSalesLeadVO.setPreviousStatus(existingLead.getStatus());
                    insideSalesLeadVO.setId(existingLead.getId());            	
                 	leadProcessingBO.updateIsLeadStatusHistory(insideSalesLeadVO);            	
                    	}
                    
                }else if(InsideSalesConstants.LEAD_ADD.equals(reason)){
                	// parse xml and get the data
                	InputStream is =new    ByteArrayInputStream(xml.getBytes());                
                    SAXHandler handler = new SAXHandler(InsideSalesConstants.LEAD_INSERT);  
                    parser.parse(is, handler);
                    insideSalesLeadVO=handler.insideSalesVo;
                    insideSalesLeadCustList=handler.insideSalesLeadCustList;                                    
                	// insert into is_leads              	                	               	
                    Integer isLeadId=leadProcessingBO.saveInsidesSalesLeadInfo(insideSalesLeadVO);                	
            		// insert into is_leads_custom_fields
                    if(!insideSalesLeadCustList.isEmpty()){
                   	 for(InsideSalesLeadCustVO insideSalesLeadCustVO:insideSalesLeadCustList){
                		 insideSalesLeadCustVO.setIsleadId(Integer.parseInt(insideSalesLeadVO.getId())); 
                	 }                    	 
                     leadProcessingBO.saveInsidesSalesLeadCustInfo(insideSalesLeadCustList);                   	
                    }
                    
                	// parse xml and get the data to insert the status history with previous state as NULL
                	InputStream ins =new  ByteArrayInputStream(xml.getBytes());
                    SAXHandler shandler = new SAXHandler(InsideSalesConstants.LEAD_STATUS); 
                    parser.parse(ins, shandler); 
                    InsideSalesLeadVO insideSalesLead=shandler.insideSalesVo;
                    insideSalesLead.setIsleadId(Integer.parseInt(insideSalesLeadVO.getId()));
                    insideSalesLead.setCreatedDate(insideSalesLeadVO.getCreatedDate());
                    insideSalesLead.setPreviousStatus(null);
                    insideSalesLead.setId(insideSalesLeadVO.getId());            	
                 	leadProcessingBO.updateIsLeadStatusHistory(insideSalesLead);  
                 	
                } else{
                    // if XML_EXPORT_REASON is not valid
                	logger.error("if XML_EXPORT_REASON is not valid");
                    response = "Lead Information update failed"; 
                }

            }else{
            	// if the switch to process inside sales data is off
                response = "Lead Information update failed"; 
            }                                   
        } catch (Exception e) {
            logger.error("Error occured while fetching data " + e);
            response = "Lead Information update failed"; 
        }                                    
        return response;
    }
    public LeadProcessingBO getLeadProcessingBO() {
        return leadProcessingBO;
    }
 
    public void setLeadProcessingBO(LeadProcessingBO leadProcessingBO) {
        this.leadProcessingBO = leadProcessingBO;
    }
 
    public LeadManagementValidator getLeadManagementValidator() {
        return leadManagementValidator;
    }
 
    public void setLeadManagementValidator(
            LeadManagementValidator leadManagementValidator) {
        this.leadManagementValidator = leadManagementValidator;
    }
}
 