
/**
 * ResponsysWSServiceStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */
        package com.rsys.ws.client;

import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.servicelive.campaign.service.ResponsysInteractServiceImpl;
import com.rsys.ws.OptionalData;
import com.rsys.ws.Record;
import com.rsys.ws.RecordData;
import com.rsys.ws.TriggerData;

        

        /*
        *  ResponsysWSServiceStub java implementation
        */

        
        public class ResponsysWSServiceStub extends org.apache.axis2.client.Stub
        implements ResponsysWSService{
        protected org.apache.axis2.description.AxisOperation[] _operations;

        //hashmaps to keep the fault mapping
        private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
        private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
        private java.util.HashMap faultMessageMap = new java.util.HashMap();

        private static final Logger logger = Logger.getLogger(ResponsysWSServiceStub.class.getName());	
        private static int counter = 0;

        private static synchronized java.lang.String getUniqueSuffix(){
            // reset the counter if it is greater than 99999
            if (counter > 99999){
                counter = 0;
            }
            counter = counter + 1; 
            return java.lang.Long.toString(System.currentTimeMillis()) + "_" + counter;
        }

    
    private void populateAxisService() throws org.apache.axis2.AxisFault {

     //creating the Service with a unique name
     _service = new org.apache.axis2.description.AxisService("ResponsysWSService" + getUniqueSuffix());
     addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[46];
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "triggerCustomEvent"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[0]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "setDocumentImages"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[1]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "mergeTriggerSMS"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[2]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "haMergeTriggerSms"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[3]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "mergeListMembersRIID"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[4]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "createTableWithPK"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[5]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "triggerCampaignMessage"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[6]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "deleteListMembers"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[7]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "mergeTriggerEmail"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[8]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "deleteFolder"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[9]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "getDocumentContent"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[10]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "createFolder"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[11]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "deleteTableRecords"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[12]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "mergeIntoProfileExtension"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[13]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "doesContentLibraryFolderExist"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[14]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "truncateTable"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[15]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "retrieveTableRecords"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[16]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "setDocumentContent"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[17]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "deleteTable"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[18]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "mergeListMembers"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[19]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "deleteContentLibraryItem"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[20]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "deleteProfileExtensionMembers"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[21]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "getContentLibraryItem"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[22]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "createProfileExtensionTable"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[23]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "createTable"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[24]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "loginWithCertificate"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[25]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "mergeTableRecords"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[26]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "getLaunchStatus"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[27]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "createContentLibraryItem"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[28]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "deleteDocument"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[29]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "getDocumentImages"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[30]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "mergeTableRecordsWithPK"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[31]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "createDocument"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[32]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "authenticateServer"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[33]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "createContentLibraryFolder"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[34]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "retrieveProfileExtensionRecords"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[35]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "retrieveListMembers"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[36]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "deleteContentLibraryFolder"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[37]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "launchCampaign"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[38]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "listFolders"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[39]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "login"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[40]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "setContentLibraryItem"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[41]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "listContentLibraryFolders"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[42]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "scheduleCampaignLaunch"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[43]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "logout"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[44]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws.rsys.com", "haMergeTriggerEmail"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[45]=__operation;
            
        
        }

    //populates the faults
    private void populateFaults(){
         
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DuplicateApiRequestFault"),"com.rsys.ws.client.DuplicateApiRequestFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DuplicateApiRequestFault"),"com.rsys.ws.client.DuplicateApiRequestFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DuplicateApiRequestFault"),"com.rsys.ws.fault.DuplicateApiRequestFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","CustomEventFault"),"com.rsys.ws.client.CustomEventFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","CustomEventFault"),"com.rsys.ws.client.CustomEventFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","CustomEventFault"),"com.rsys.ws.fault.CustomEventFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.fault.DocumentFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DuplicateApiRequestFault"),"com.rsys.ws.client.DuplicateApiRequestFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DuplicateApiRequestFault"),"com.rsys.ws.client.DuplicateApiRequestFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DuplicateApiRequestFault"),"com.rsys.ws.fault.DuplicateApiRequestFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TriggeredMessageFault"),"com.rsys.ws.client.TriggeredMessageFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TriggeredMessageFault"),"com.rsys.ws.client.TriggeredMessageFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TriggeredMessageFault"),"com.rsys.ws.fault.TriggeredMessageFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DuplicateApiRequestFault"),"com.rsys.ws.client.DuplicateApiRequestFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DuplicateApiRequestFault"),"com.rsys.ws.client.DuplicateApiRequestFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DuplicateApiRequestFault"),"com.rsys.ws.fault.DuplicateApiRequestFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TriggeredMessageFault"),"com.rsys.ws.client.TriggeredMessageFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TriggeredMessageFault"),"com.rsys.ws.client.TriggeredMessageFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TriggeredMessageFault"),"com.rsys.ws.fault.TriggeredMessageFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnrecoverableErrorFault"),"com.rsys.ws.client.UnrecoverableErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnrecoverableErrorFault"),"com.rsys.ws.client.UnrecoverableErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnrecoverableErrorFault"),"com.rsys.ws.fault.UnrecoverableErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListFault"),"com.rsys.ws.client.ListFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListFault"),"com.rsys.ws.client.ListFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListFault"),"com.rsys.ws.fault.ListFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.client.TableFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.client.TableFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.fault.TableFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DuplicateApiRequestFault"),"com.rsys.ws.client.DuplicateApiRequestFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DuplicateApiRequestFault"),"com.rsys.ws.client.DuplicateApiRequestFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DuplicateApiRequestFault"),"com.rsys.ws.fault.DuplicateApiRequestFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TriggeredMessageFault"),"com.rsys.ws.client.TriggeredMessageFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TriggeredMessageFault"),"com.rsys.ws.client.TriggeredMessageFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TriggeredMessageFault"),"com.rsys.ws.fault.TriggeredMessageFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListFault"),"com.rsys.ws.client.ListFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListFault"),"com.rsys.ws.client.ListFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListFault"),"com.rsys.ws.fault.ListFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DuplicateApiRequestFault"),"com.rsys.ws.client.DuplicateApiRequestFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DuplicateApiRequestFault"),"com.rsys.ws.client.DuplicateApiRequestFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DuplicateApiRequestFault"),"com.rsys.ws.fault.DuplicateApiRequestFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TriggeredMessageFault"),"com.rsys.ws.client.TriggeredMessageFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TriggeredMessageFault"),"com.rsys.ws.client.TriggeredMessageFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TriggeredMessageFault"),"com.rsys.ws.fault.TriggeredMessageFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","FolderFault"),"com.rsys.ws.client.FolderFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","FolderFault"),"com.rsys.ws.client.FolderFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","FolderFault"),"com.rsys.ws.fault.FolderFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.fault.DocumentFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","FolderFault"),"com.rsys.ws.client.FolderFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","FolderFault"),"com.rsys.ws.client.FolderFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","FolderFault"),"com.rsys.ws.fault.FolderFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.client.TableFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.client.TableFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.fault.TableFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListExtensionFault"),"com.rsys.ws.client.ListExtensionFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListExtensionFault"),"com.rsys.ws.client.ListExtensionFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListExtensionFault"),"com.rsys.ws.fault.ListExtensionFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","FolderFault"),"com.rsys.ws.client.FolderFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","FolderFault"),"com.rsys.ws.client.FolderFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","FolderFault"),"com.rsys.ws.fault.FolderFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.client.TableFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.client.TableFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.fault.TableFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.client.TableFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.client.TableFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.fault.TableFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.fault.DocumentFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.client.TableFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.client.TableFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.fault.TableFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListFault"),"com.rsys.ws.client.ListFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListFault"),"com.rsys.ws.client.ListFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListFault"),"com.rsys.ws.fault.ListFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.fault.DocumentFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListExtensionFault"),"com.rsys.ws.client.ListExtensionFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListExtensionFault"),"com.rsys.ws.client.ListExtensionFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListExtensionFault"),"com.rsys.ws.fault.ListExtensionFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.fault.DocumentFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListFault"),"com.rsys.ws.client.ListFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListFault"),"com.rsys.ws.client.ListFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListFault"),"com.rsys.ws.fault.ListFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.client.TableFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.client.TableFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.fault.TableFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","AccountFault"),"com.rsys.ws.client.AccountFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","AccountFault"),"com.rsys.ws.client.AccountFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","AccountFault"),"com.rsys.ws.fault.AccountFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.client.TableFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.client.TableFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.fault.TableFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","CampaignFault"),"com.rsys.ws.client.CampaignFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","CampaignFault"),"com.rsys.ws.client.CampaignFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","CampaignFault"),"com.rsys.ws.fault.CampaignFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.fault.DocumentFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.fault.DocumentFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.fault.DocumentFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.client.TableFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.client.TableFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TableFault"),"com.rsys.ws.fault.TableFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.fault.DocumentFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","AccountFault"),"com.rsys.ws.client.AccountFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","AccountFault"),"com.rsys.ws.client.AccountFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","AccountFault"),"com.rsys.ws.fault.AccountFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","FolderFault"),"com.rsys.ws.client.FolderFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","FolderFault"),"com.rsys.ws.client.FolderFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","FolderFault"),"com.rsys.ws.fault.FolderFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListExtensionFault"),"com.rsys.ws.client.ListExtensionFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListExtensionFault"),"com.rsys.ws.client.ListExtensionFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListExtensionFault"),"com.rsys.ws.fault.ListExtensionFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListFault"),"com.rsys.ws.client.ListFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListFault"),"com.rsys.ws.client.ListFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","ListFault"),"com.rsys.ws.fault.ListFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","FolderFault"),"com.rsys.ws.client.FolderFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","FolderFault"),"com.rsys.ws.client.FolderFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","FolderFault"),"com.rsys.ws.fault.FolderFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","CampaignFault"),"com.rsys.ws.client.CampaignFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","CampaignFault"),"com.rsys.ws.client.CampaignFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","CampaignFault"),"com.rsys.ws.fault.CampaignFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","AccountFault"),"com.rsys.ws.client.AccountFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","AccountFault"),"com.rsys.ws.client.AccountFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","AccountFault"),"com.rsys.ws.fault.AccountFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.client.DocumentFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DocumentFault"),"com.rsys.ws.fault.DocumentFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","FolderFault"),"com.rsys.ws.client.FolderFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","FolderFault"),"com.rsys.ws.client.FolderFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","FolderFault"),"com.rsys.ws.fault.FolderFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","CampaignFault"),"com.rsys.ws.client.CampaignFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","CampaignFault"),"com.rsys.ws.client.CampaignFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","CampaignFault"),"com.rsys.ws.fault.CampaignFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DuplicateApiRequestFault"),"com.rsys.ws.client.DuplicateApiRequestFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DuplicateApiRequestFault"),"com.rsys.ws.client.DuplicateApiRequestFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","DuplicateApiRequestFault"),"com.rsys.ws.fault.DuplicateApiRequestFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnexpectedErrorFault"),"com.rsys.ws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TriggeredMessageFault"),"com.rsys.ws.client.TriggeredMessageFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TriggeredMessageFault"),"com.rsys.ws.client.TriggeredMessageFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","TriggeredMessageFault"),"com.rsys.ws.fault.TriggeredMessageFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnrecoverableErrorFault"),"com.rsys.ws.client.UnrecoverableErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnrecoverableErrorFault"),"com.rsys.ws.client.UnrecoverableErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws.rsys.com","UnrecoverableErrorFault"),"com.rsys.ws.fault.UnrecoverableErrorFault");
           


    }

    /**
      *Constructor that takes in a configContext
      */

    public ResponsysWSServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
       java.lang.String targetEndpoint)
       throws org.apache.axis2.AxisFault {
         this(configurationContext,targetEndpoint,false);
   }


   /**
     * Constructor that takes in a configContext  and useseperate listner
     */
   public ResponsysWSServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext,
        java.lang.String targetEndpoint, boolean useSeparateListener)
        throws org.apache.axis2.AxisFault {
         //To populate AxisService
         populateAxisService();
         populateFaults();

        _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext,_service);
        
	
        _serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(
                targetEndpoint));
        _serviceClient.getOptions().setUseSeparateListener(useSeparateListener);
        
    
    }

    /**
     * Default Constructor
     */
    public ResponsysWSServiceStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {
        
                    this(configurationContext,"https://ws2.responsys.net/webservices/services/ResponsysWSService" );
                
    }

    /**
     * Default Constructor
     */
    public ResponsysWSServiceStub() throws org.apache.axis2.AxisFault {
        
                    this("https://ws2.responsys.net/webservices/services/ResponsysWSService" );
                
    }

    /**
     * Constructor taking the target endpoint
     */
    public ResponsysWSServiceStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(null,targetEndpoint);
    }



        
                    /**
                     * Auto generated method signature
                     * Trigger Custom Event to 1 or more recipients.
                     * @see com.rsys.ws.client.ResponsysWSService#triggerCustomEvent
                     * @param triggerCustomEvent42
                    
                     * @param sessionHeader43
                    
                     * @throws com.rsys.ws.client.DuplicateApiRequestFault : 
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.CustomEventFault : 
                     */

                    

                            public  com.rsys.ws.TriggerCustomEventResponse triggerCustomEvent(

                            com.rsys.ws.TriggerCustomEvent triggerCustomEvent42,com.rsys.ws.SessionHeader sessionHeader43)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.DuplicateApiRequestFault
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.CustomEventFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:triggerCustomEventRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    triggerCustomEvent42,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "triggerCustomEvent")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader43!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader43 = toOM(sessionHeader43, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "triggerCustomEvent")));
                                                    addHeader(omElementsessionHeader43,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.TriggerCustomEventResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.TriggerCustomEventResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.DuplicateApiRequestFault){
                          throw (com.rsys.ws.client.DuplicateApiRequestFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.CustomEventFault){
                          throw (com.rsys.ws.client.CustomEventFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Set images to a document.
                     * @see com.rsys.ws.client.ResponsysWSService#setDocumentImages
                     * @param setDocumentImages45
                    
                     * @param sessionHeader46
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.DocumentFault : 
                     */

                    

                            public  com.rsys.ws.SetDocumentImagesResponse setDocumentImages(

                            com.rsys.ws.SetDocumentImages setDocumentImages45,com.rsys.ws.SessionHeader sessionHeader46)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.DocumentFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:setDocumentImagesRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    setDocumentImages45,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "setDocumentImages")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader46!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader46 = toOM(sessionHeader46, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "setDocumentImages")));
                                                    addHeader(omElementsessionHeader46,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.SetDocumentImagesResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.SetDocumentImagesResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.DocumentFault){
                          throw (com.rsys.ws.client.DocumentFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Merge And Send Triggered Message for 1 or more recipients.
                     * @see com.rsys.ws.client.ResponsysWSService#mergeTriggerSMS
                     * @param mergeTriggerSMS48
                    
                     * @param sessionHeader49
                    
                     * @throws com.rsys.ws.client.DuplicateApiRequestFault : 
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.TriggeredMessageFault : 
                     */

                    

                            public  com.rsys.ws.MergeTriggerSMSResponse mergeTriggerSMS(

                            com.rsys.ws.MergeTriggerSMS mergeTriggerSMS48,com.rsys.ws.SessionHeader sessionHeader49)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.DuplicateApiRequestFault
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.TriggeredMessageFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:mergeTriggerSMSRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    mergeTriggerSMS48,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "mergeTriggerSMS")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader49!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader49 = toOM(sessionHeader49, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "mergeTriggerSMS")));
                                                    addHeader(omElementsessionHeader49,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.MergeTriggerSMSResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.MergeTriggerSMSResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.DuplicateApiRequestFault){
                          throw (com.rsys.ws.client.DuplicateApiRequestFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.TriggeredMessageFault){
                          throw (com.rsys.ws.client.TriggeredMessageFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Merge And Send Triggered Message for 1 or more recipients.
                     * @see com.rsys.ws.client.ResponsysWSService#haMergeTriggerSms
                     * @param haMergeTriggerSms51
                    
                     * @param sessionHeader52
                    
                     * @throws com.rsys.ws.client.DuplicateApiRequestFault : 
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.TriggeredMessageFault : 
                     * @throws com.rsys.ws.client.UnrecoverableErrorFault : 
                     */

                    

                            public  com.rsys.ws.HaMergeTriggerSmsResponse haMergeTriggerSms(

                            com.rsys.ws.HaMergeTriggerSms haMergeTriggerSms51,com.rsys.ws.SessionHeader sessionHeader52)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.DuplicateApiRequestFault
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.TriggeredMessageFault
                        ,com.rsys.ws.client.UnrecoverableErrorFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:HaMergeTriggerSmsRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    haMergeTriggerSms51,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "haMergeTriggerSms")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader52!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader52 = toOM(sessionHeader52, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "haMergeTriggerSms")));
                                                    addHeader(omElementsessionHeader52,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.HaMergeTriggerSmsResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.HaMergeTriggerSmsResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.DuplicateApiRequestFault){
                          throw (com.rsys.ws.client.DuplicateApiRequestFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.TriggeredMessageFault){
                          throw (com.rsys.ws.client.TriggeredMessageFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.UnrecoverableErrorFault){
                          throw (com.rsys.ws.client.UnrecoverableErrorFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Merge data into a list returning the recipient ids.
                     * @see com.rsys.ws.client.ResponsysWSService#mergeListMembersRIID
                     * @param mergeListMembersRIID54
                    
                     * @param sessionHeader55
                    
                     * @throws com.rsys.ws.client.ListFault : 
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     */

                    

                            public  com.rsys.ws.MergeListMembersRIIDResponse mergeListMembersRIID(

                            com.rsys.ws.MergeListMembersRIID mergeListMembersRIID54,com.rsys.ws.SessionHeader sessionHeader55)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.ListFault
                        ,com.rsys.ws.client.UnexpectedErrorFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[4].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:mergeListMembersRIIDRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    mergeListMembersRIID54,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "mergeListMembersRIID")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader55!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader55 = toOM(sessionHeader55, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "mergeListMembersRIID")));
                                                    addHeader(omElementsessionHeader55,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.MergeListMembersRIIDResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.MergeListMembersRIIDResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.ListFault){
                          throw (com.rsys.ws.client.ListFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Creates an empty table with primary keys.
                     * @see com.rsys.ws.client.ResponsysWSService#createTableWithPK
                     * @param createTableWithPK57
                    
                     * @param sessionHeader58
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.TableFault : 
                     */

                    

                            public  com.rsys.ws.CreateTableWithPKResponse createTableWithPK(

                            com.rsys.ws.CreateTableWithPK createTableWithPK57,com.rsys.ws.SessionHeader sessionHeader58)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.TableFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[5].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:createTableWithPKRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    createTableWithPK57,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "createTableWithPK")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader58!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader58 = toOM(sessionHeader58, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "createTableWithPK")));
                                                    addHeader(omElementsessionHeader58,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.CreateTableWithPKResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.CreateTableWithPKResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.TableFault){
                          throw (com.rsys.ws.client.TableFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Send Triggered Message to 1 or more recipients.
                     * @see com.rsys.ws.client.ResponsysWSService#triggerCampaignMessage
                     * @param triggerCampaignMessage60
                    
                     * @param sessionHeader61
                    
                     * @throws com.rsys.ws.client.DuplicateApiRequestFault : 
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.TriggeredMessageFault : 
                     */

                    

                            public  com.rsys.ws.TriggerCampaignMessageResponse triggerCampaignMessage(

                            com.rsys.ws.TriggerCampaignMessage triggerCampaignMessage60,com.rsys.ws.SessionHeader sessionHeader61)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.DuplicateApiRequestFault
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.TriggeredMessageFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[6].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:triggerCampaignMessageRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    triggerCampaignMessage60,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "triggerCampaignMessage")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader61!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader61 = toOM(sessionHeader61, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "triggerCampaignMessage")));
                                                    addHeader(omElementsessionHeader61,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.TriggerCampaignMessageResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.TriggerCampaignMessageResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.DuplicateApiRequestFault){
                          throw (com.rsys.ws.client.DuplicateApiRequestFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.TriggeredMessageFault){
                          throw (com.rsys.ws.client.TriggeredMessageFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Deletes recipients from a list.
                     * @see com.rsys.ws.client.ResponsysWSService#deleteListMembers
                     * @param deleteListMembers63
                    
                     * @param sessionHeader64
                    
                     * @throws com.rsys.ws.client.ListFault : 
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     */

                    

                            public  com.rsys.ws.DeleteListMembersResponse deleteListMembers(

                            com.rsys.ws.DeleteListMembers deleteListMembers63,com.rsys.ws.SessionHeader sessionHeader64)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.ListFault
                        ,com.rsys.ws.client.UnexpectedErrorFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[7].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:deleteListMembersRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    deleteListMembers63,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "deleteListMembers")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader64!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader64 = toOM(sessionHeader64, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "deleteListMembers")));
                                                    addHeader(omElementsessionHeader64,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.DeleteListMembersResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.DeleteListMembersResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.ListFault){
                          throw (com.rsys.ws.client.ListFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Merge And Send Triggered Message for 1 or more recipients.
                     * @see com.rsys.ws.client.ResponsysWSService#mergeTriggerEmail
                     * @param mergeTriggerEmail66
                    
                     * @param sessionHeader67
                    
                     * @throws com.rsys.ws.client.DuplicateApiRequestFault : 
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.TriggeredMessageFault : 
                     */

                    

                            public  com.rsys.ws.MergeTriggerEmailResponse mergeTriggerEmail(

                            com.rsys.ws.MergeTriggerEmail mergeTriggerEmail66,com.rsys.ws.SessionHeader sessionHeader67)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.DuplicateApiRequestFault
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.TriggeredMessageFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
            	  logger.info("inside mergeTriggerEmail()");
            	  logger.info("inside mergeTriggerEmail() sessionId:"+sessionHeader67.getSessionId());
            	  logger.info("inside mergeTriggerEmail() mergeTriggerEmail66:"+mergeTriggerEmail66);
            	  
               logger.info("_serviceClient:"+_serviceClient);  
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[8].getName());
               if(null != _operationClient){
            	   logger.info("inside mergeTriggerEmail() _operationClient "+_operationClient.toString());
               }
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:mergeTriggerEmailRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              if(null != _operationClient){
           	   logger.info("inside mergeTriggerEmail() _operationClient1 "+_operationClient.toString());
              }

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    mergeTriggerEmail66,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "mergeTriggerEmail")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader67!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader67 = toOM(sessionHeader67, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "mergeTriggerEmail")));
                                                    addHeader(omElementsessionHeader67,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
         if(null != env){
         	   logger.info("inside mergeTriggerEmail() env "+env.toString());
            }
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);
        if(null != _operationClient.getOptions()){
        	logger.info("inside mergeTriggerEmail() _operationClient.getOptions().getProperties() "+ _operationClient.getOptions().getProperties());
        	logger.info("inside mergeTriggerEmail() _operationClient.getOptions().getMessageId() "+ _operationClient.getOptions().getMessageId());
        	logger.info("inside mergeTriggerEmail() _operationClient.getOptions().getPassword() "+ _operationClient.getOptions().getPassword());
        	logger.info("inside mergeTriggerEmail() _operationClient.getOptions().getUserName() "+ _operationClient.getOptions().getUserName());
        	logger.info("inside mergeTriggerEmail() _operationClient.getOptions().getFrom() "+ _operationClient.getOptions().getFrom());
        	logger.info("inside mergeTriggerEmail() _operationClient.getOptions().getAction() "+ _operationClient.getOptions().getAction());
        	logger.info("inside mergeTriggerEmail() _operationClient.getOptions().getTo() "+ _operationClient.getOptions().getTo());
        	logger.info("inside mergeTriggerEmail() _operationClient.getOptions().getReplyTo() "+ _operationClient.getOptions().getReplyTo());
        	
        }
        
        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.MergeTriggerEmailResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                                if(null != object){
                             	   logger.info("inside mergeTriggerEmail() responseObject "+object.toString());
                                }
                                        return (com.rsys.ws.MergeTriggerEmailResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.DuplicateApiRequestFault){
                          throw (com.rsys.ws.client.DuplicateApiRequestFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.TriggeredMessageFault){
                          throw (com.rsys.ws.client.TriggeredMessageFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Deletes an existing folder.
                     * @see com.rsys.ws.client.ResponsysWSService#deleteFolder
                     * @param deleteFolder69
                    
                     * @param sessionHeader70
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.FolderFault : 
                     */

                    

                            public  com.rsys.ws.DeleteFolderResponse deleteFolder(

                            com.rsys.ws.DeleteFolder deleteFolder69,com.rsys.ws.SessionHeader sessionHeader70)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.FolderFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[9].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:deleteFolderRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    deleteFolder69,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "deleteFolder")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader70!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader70 = toOM(sessionHeader70, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "deleteFolder")));
                                                    addHeader(omElementsessionHeader70,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.DeleteFolderResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.DeleteFolderResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.FolderFault){
                          throw (com.rsys.ws.client.FolderFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Get content of a document.
                     * @see com.rsys.ws.client.ResponsysWSService#getDocumentContent
                     * @param getDocumentContent72
                    
                     * @param sessionHeader73
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.DocumentFault : 
                     */

                    

                            public  com.rsys.ws.GetDocumentContentResponse getDocumentContent(

                            com.rsys.ws.GetDocumentContent getDocumentContent72,com.rsys.ws.SessionHeader sessionHeader73)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.DocumentFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[10].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:getDocumentContentRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getDocumentContent72,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "getDocumentContent")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader73!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader73 = toOM(sessionHeader73, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "getDocumentContent")));
                                                    addHeader(omElementsessionHeader73,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.GetDocumentContentResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.GetDocumentContentResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.DocumentFault){
                          throw (com.rsys.ws.client.DocumentFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Creates a new folder.
                     * @see com.rsys.ws.client.ResponsysWSService#createFolder
                     * @param createFolder75
                    
                     * @param sessionHeader76
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.FolderFault : 
                     */

                    

                            public  com.rsys.ws.CreateFolderResponse createFolder(

                            com.rsys.ws.CreateFolder createFolder75,com.rsys.ws.SessionHeader sessionHeader76)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.FolderFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[11].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:createFolderRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    createFolder75,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "createFolder")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader76!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader76 = toOM(sessionHeader76, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "createFolder")));
                                                    addHeader(omElementsessionHeader76,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.CreateFolderResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.CreateFolderResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.FolderFault){
                          throw (com.rsys.ws.client.FolderFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Deletes records from a table.
                     * @see com.rsys.ws.client.ResponsysWSService#deleteTableRecords
                     * @param deleteTableRecords78
                    
                     * @param sessionHeader79
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.TableFault : 
                     */

                    

                            public  com.rsys.ws.DeleteTableRecordsResponse deleteTableRecords(

                            com.rsys.ws.DeleteTableRecords deleteTableRecords78,com.rsys.ws.SessionHeader sessionHeader79)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.TableFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[12].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:deleteTableRecordsRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    deleteTableRecords78,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "deleteTableRecords")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader79!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader79 = toOM(sessionHeader79, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "deleteTableRecords")));
                                                    addHeader(omElementsessionHeader79,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.DeleteTableRecordsResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.DeleteTableRecordsResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.TableFault){
                          throw (com.rsys.ws.client.TableFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Merge data into a list extension returning the recipient ids.
                     * @see com.rsys.ws.client.ResponsysWSService#mergeIntoProfileExtension
                     * @param mergeIntoProfileExtension81
                    
                     * @param sessionHeader82
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.ListExtensionFault : 
                     */

                    

                            public  com.rsys.ws.MergeIntoProfileExtensionResponse mergeIntoProfileExtension(

                            com.rsys.ws.MergeIntoProfileExtension mergeIntoProfileExtension81,com.rsys.ws.SessionHeader sessionHeader82)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.ListExtensionFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[13].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:mergeIntoProfileExtensionRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    mergeIntoProfileExtension81,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "mergeIntoProfileExtension")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader82!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader82 = toOM(sessionHeader82, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "mergeIntoProfileExtension")));
                                                    addHeader(omElementsessionHeader82,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.MergeIntoProfileExtensionResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.MergeIntoProfileExtensionResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.ListExtensionFault){
                          throw (com.rsys.ws.client.ListExtensionFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Check if folder exists in the Content Library.
                     * @see com.rsys.ws.client.ResponsysWSService#doesContentLibraryFolderExist
                     * @param doesContentLibraryFolderExist84
                    
                     * @param sessionHeader85
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.FolderFault : 
                     */

                    

                            public  com.rsys.ws.DoesContentLibraryFolderExistResponse doesContentLibraryFolderExist(

                            com.rsys.ws.DoesContentLibraryFolderExist doesContentLibraryFolderExist84,com.rsys.ws.SessionHeader sessionHeader85)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.FolderFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[14].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:doesContentLibraryFolderExistRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    doesContentLibraryFolderExist84,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "doesContentLibraryFolderExist")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader85!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader85 = toOM(sessionHeader85, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "doesContentLibraryFolderExist")));
                                                    addHeader(omElementsessionHeader85,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.DoesContentLibraryFolderExistResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.DoesContentLibraryFolderExistResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.FolderFault){
                          throw (com.rsys.ws.client.FolderFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Truncate data in existing table.
                     * @see com.rsys.ws.client.ResponsysWSService#truncateTable
                     * @param truncateTable87
                    
                     * @param sessionHeader88
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.TableFault : 
                     */

                    

                            public  com.rsys.ws.TruncateTableResponse truncateTable(

                            com.rsys.ws.TruncateTable truncateTable87,com.rsys.ws.SessionHeader sessionHeader88)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.TableFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[15].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:truncateTableRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    truncateTable87,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "truncateTable")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader88!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader88 = toOM(sessionHeader88, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "truncateTable")));
                                                    addHeader(omElementsessionHeader88,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.TruncateTableResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.TruncateTableResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.TableFault){
                          throw (com.rsys.ws.client.TableFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Retrieves records from table.
                     * @see com.rsys.ws.client.ResponsysWSService#retrieveTableRecords
                     * @param retrieveTableRecords90
                    
                     * @param sessionHeader91
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.TableFault : 
                     */

                    

                            public  com.rsys.ws.RetrieveTableRecordsResponse retrieveTableRecords(

                            com.rsys.ws.RetrieveTableRecords retrieveTableRecords90,com.rsys.ws.SessionHeader sessionHeader91)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.TableFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[16].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:retrieveTableRecordsRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    retrieveTableRecords90,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "retrieveTableRecords")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader91!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader91 = toOM(sessionHeader91, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "retrieveTableRecords")));
                                                    addHeader(omElementsessionHeader91,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.RetrieveTableRecordsResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.RetrieveTableRecordsResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.TableFault){
                          throw (com.rsys.ws.client.TableFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Set content to a document.
                     * @see com.rsys.ws.client.ResponsysWSService#setDocumentContent
                     * @param setDocumentContent93
                    
                     * @param sessionHeader94
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.DocumentFault : 
                     */

                    

                            public  com.rsys.ws.SetDocumentContentResponse setDocumentContent(

                            com.rsys.ws.SetDocumentContent setDocumentContent93,com.rsys.ws.SessionHeader sessionHeader94)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.DocumentFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[17].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:setDocumentContentRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    setDocumentContent93,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "setDocumentContent")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader94!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader94 = toOM(sessionHeader94, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "setDocumentContent")));
                                                    addHeader(omElementsessionHeader94,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.SetDocumentContentResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.SetDocumentContentResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.DocumentFault){
                          throw (com.rsys.ws.client.DocumentFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Deletes an existing table.
                     * @see com.rsys.ws.client.ResponsysWSService#deleteTable
                     * @param deleteTable96
                    
                     * @param sessionHeader97
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.TableFault : 
                     */

                    

                            public  com.rsys.ws.DeleteTableResponse deleteTable(

                            com.rsys.ws.DeleteTable deleteTable96,com.rsys.ws.SessionHeader sessionHeader97)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.TableFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[18].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:deleteTableRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    deleteTable96,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "deleteTable")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader97!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader97 = toOM(sessionHeader97, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "deleteTable")));
                                                    addHeader(omElementsessionHeader97,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.DeleteTableResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.DeleteTableResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.TableFault){
                          throw (com.rsys.ws.client.TableFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Merge data into a list.
                     * @see com.rsys.ws.client.ResponsysWSService#mergeListMembers
                     * @param mergeListMembers99
                    
                     * @param sessionHeader100
                    
                     * @throws com.rsys.ws.client.ListFault : 
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     */

                    

                            public  com.rsys.ws.MergeListMembersResponse mergeListMembers(

                            com.rsys.ws.MergeListMembers mergeListMembers99,com.rsys.ws.SessionHeader sessionHeader100)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.ListFault
                        ,com.rsys.ws.client.UnexpectedErrorFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[19].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:mergeListMembersRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    mergeListMembers99,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "mergeListMembers")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader100!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader100 = toOM(sessionHeader100, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "mergeListMembers")));
                                                    addHeader(omElementsessionHeader100,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.MergeListMembersResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.MergeListMembersResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.ListFault){
                          throw (com.rsys.ws.client.ListFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Delete asset in the Content Library.
                     * @see com.rsys.ws.client.ResponsysWSService#deleteContentLibraryItem
                     * @param deleteContentLibraryItem102
                    
                     * @param sessionHeader103
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.DocumentFault : 
                     */

                    

                            public  com.rsys.ws.DeleteContentLibraryItemResponse deleteContentLibraryItem(

                            com.rsys.ws.DeleteContentLibraryItem deleteContentLibraryItem102,com.rsys.ws.SessionHeader sessionHeader103)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.DocumentFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[20].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:deleteContentLibraryItemRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    deleteContentLibraryItem102,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "deleteContentLibraryItem")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader103!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader103 = toOM(sessionHeader103, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "deleteContentLibraryItem")));
                                                    addHeader(omElementsessionHeader103,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.DeleteContentLibraryItemResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.DeleteContentLibraryItemResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.DocumentFault){
                          throw (com.rsys.ws.client.DocumentFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Deletes recipients from a profile extension.
                     * @see com.rsys.ws.client.ResponsysWSService#deleteProfileExtensionMembers
                     * @param deleteProfileExtensionMembers105
                    
                     * @param sessionHeader106
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.ListExtensionFault : 
                     */

                    

                            public  com.rsys.ws.DeleteProfileExtensionMembersResponse deleteProfileExtensionMembers(

                            com.rsys.ws.DeleteProfileExtensionMembers deleteProfileExtensionMembers105,com.rsys.ws.SessionHeader sessionHeader106)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.ListExtensionFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[21].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:deleteProfileExtensionMembersRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    deleteProfileExtensionMembers105,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "deleteProfileExtensionMembers")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader106!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader106 = toOM(sessionHeader106, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "deleteProfileExtensionMembers")));
                                                    addHeader(omElementsessionHeader106,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.DeleteProfileExtensionMembersResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.DeleteProfileExtensionMembersResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.ListExtensionFault){
                          throw (com.rsys.ws.client.ListExtensionFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Get asset in the Content Library.
                     * @see com.rsys.ws.client.ResponsysWSService#getContentLibraryItem
                     * @param getContentLibraryItem108
                    
                     * @param sessionHeader109
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.DocumentFault : 
                     */

                    

                            public  com.rsys.ws.GetContentLibraryItemResponse getContentLibraryItem(

                            com.rsys.ws.GetContentLibraryItem getContentLibraryItem108,com.rsys.ws.SessionHeader sessionHeader109)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.DocumentFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[22].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:getContentLibraryItemRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getContentLibraryItem108,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "getContentLibraryItem")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader109!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader109 = toOM(sessionHeader109, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "getContentLibraryItem")));
                                                    addHeader(omElementsessionHeader109,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.GetContentLibraryItemResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.GetContentLibraryItemResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.DocumentFault){
                          throw (com.rsys.ws.client.DocumentFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * 
                     * @see com.rsys.ws.client.ResponsysWSService#createProfileExtensionTable
                     * @param createProfileExtensionTable111
                    
                     * @param sessionHeader112
                    
                     * @throws com.rsys.ws.client.ListFault : 
                     */

                    

                            public  com.rsys.ws.CreateProfileExtensionTableResponse createProfileExtensionTable(

                            com.rsys.ws.CreateProfileExtensionTable createProfileExtensionTable111,com.rsys.ws.SessionHeader sessionHeader112)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.ListFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[23].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:createProfileExtensionTableRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    createProfileExtensionTable111,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "createProfileExtensionTable")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader112!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader112 = toOM(sessionHeader112, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "createProfileExtensionTable")));
                                                    addHeader(omElementsessionHeader112,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.CreateProfileExtensionTableResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.CreateProfileExtensionTableResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.ListFault){
                          throw (com.rsys.ws.client.ListFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Creates an empty table.
                     * @see com.rsys.ws.client.ResponsysWSService#createTable
                     * @param createTable114
                    
                     * @param sessionHeader115
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.TableFault : 
                     */

                    

                            public  com.rsys.ws.CreateTableResponse createTable(

                            com.rsys.ws.CreateTable createTable114,com.rsys.ws.SessionHeader sessionHeader115)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.TableFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[24].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:createTableRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    createTable114,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "createTable")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader115!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader115 = toOM(sessionHeader115, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "createTable")));
                                                    addHeader(omElementsessionHeader115,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.CreateTableResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.CreateTableResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.TableFault){
                          throw (com.rsys.ws.client.TableFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Login to the Responsys Web Services API using Certificate.
                     * @see com.rsys.ws.client.ResponsysWSService#loginWithCertificate
                     * @param loginWithCertificate117
                    
                     * @param authSessionHeader118
                    
                     * @throws com.rsys.ws.client.AccountFault : 
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     */

                    

                            public  com.rsys.ws.LoginWithCertificateResponse loginWithCertificate(

                            com.rsys.ws.LoginWithCertificate loginWithCertificate117,com.rsys.ws.AuthSessionHeader authSessionHeader118)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.AccountFault
                        ,com.rsys.ws.client.UnexpectedErrorFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[25].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:loginWithCertificateRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    loginWithCertificate117,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "loginWithCertificate")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (authSessionHeader118!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementauthSessionHeader118 = toOM(authSessionHeader118, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "loginWithCertificate")));
                                                    addHeader(omElementauthSessionHeader118,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.LoginWithCertificateResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.LoginWithCertificateResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.AccountFault){
                          throw (com.rsys.ws.client.AccountFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Merge data into a table.
                     * @see com.rsys.ws.client.ResponsysWSService#mergeTableRecords
                     * @param mergeTableRecords120
                    
                     * @param sessionHeader121
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.TableFault : 
                     */

                    

                            public  com.rsys.ws.MergeTableRecordsResponse mergeTableRecords(

                            com.rsys.ws.MergeTableRecords mergeTableRecords120,com.rsys.ws.SessionHeader sessionHeader121)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.TableFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[26].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:mergeTableRecordsRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    mergeTableRecords120,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "mergeTableRecords")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader121!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader121 = toOM(sessionHeader121, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "mergeTableRecords")));
                                                    addHeader(omElementsessionHeader121,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.MergeTableRecordsResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.MergeTableRecordsResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.TableFault){
                          throw (com.rsys.ws.client.TableFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Gets the launch info given a launch Id.
                     * @see com.rsys.ws.client.ResponsysWSService#getLaunchStatus
                     * @param getLaunchStatus123
                    
                     * @param sessionHeader124
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.CampaignFault : 
                     */

                    

                            public  com.rsys.ws.GetLaunchStatusResponse getLaunchStatus(

                            com.rsys.ws.GetLaunchStatus getLaunchStatus123,com.rsys.ws.SessionHeader sessionHeader124)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.CampaignFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[27].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:getLaunchStatusRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getLaunchStatus123,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "getLaunchStatus")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader124!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader124 = toOM(sessionHeader124, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "getLaunchStatus")));
                                                    addHeader(omElementsessionHeader124,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.GetLaunchStatusResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.GetLaunchStatusResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.CampaignFault){
                          throw (com.rsys.ws.client.CampaignFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Create asset in the Content Library.
                     * @see com.rsys.ws.client.ResponsysWSService#createContentLibraryItem
                     * @param createContentLibraryItem126
                    
                     * @param sessionHeader127
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.DocumentFault : 
                     */

                    

                            public  com.rsys.ws.CreateContentLibraryItemResponse createContentLibraryItem(

                            com.rsys.ws.CreateContentLibraryItem createContentLibraryItem126,com.rsys.ws.SessionHeader sessionHeader127)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.DocumentFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[28].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:createContentLibraryItemRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    createContentLibraryItem126,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "createContentLibraryItem")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader127!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader127 = toOM(sessionHeader127, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "createContentLibraryItem")));
                                                    addHeader(omElementsessionHeader127,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.CreateContentLibraryItemResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.CreateContentLibraryItemResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.DocumentFault){
                          throw (com.rsys.ws.client.DocumentFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Deletes a document.
                     * @see com.rsys.ws.client.ResponsysWSService#deleteDocument
                     * @param deleteDocument129
                    
                     * @param sessionHeader130
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.DocumentFault : 
                     */

                    

                            public  com.rsys.ws.DeleteDocumentResponse deleteDocument(

                            com.rsys.ws.DeleteDocument deleteDocument129,com.rsys.ws.SessionHeader sessionHeader130)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.DocumentFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[29].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:deleteDocumentRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    deleteDocument129,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "deleteDocument")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader130!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader130 = toOM(sessionHeader130, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "deleteDocument")));
                                                    addHeader(omElementsessionHeader130,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.DeleteDocumentResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.DeleteDocumentResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.DocumentFault){
                          throw (com.rsys.ws.client.DocumentFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Get images from a document.
                     * @see com.rsys.ws.client.ResponsysWSService#getDocumentImages
                     * @param getDocumentImages132
                    
                     * @param sessionHeader133
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.DocumentFault : 
                     */

                    

                            public  com.rsys.ws.GetDocumentImagesResponse getDocumentImages(

                            com.rsys.ws.GetDocumentImages getDocumentImages132,com.rsys.ws.SessionHeader sessionHeader133)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.DocumentFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[30].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:getDocumentImagesRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getDocumentImages132,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "getDocumentImages")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader133!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader133 = toOM(sessionHeader133, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "getDocumentImages")));
                                                    addHeader(omElementsessionHeader133,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.GetDocumentImagesResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.GetDocumentImagesResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.DocumentFault){
                          throw (com.rsys.ws.client.DocumentFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Merge data into a table using primary keys.
                     * @see com.rsys.ws.client.ResponsysWSService#mergeTableRecordsWithPK
                     * @param mergeTableRecordsWithPK135
                    
                     * @param sessionHeader136
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.TableFault : 
                     */

                    

                            public  com.rsys.ws.MergeTableRecordsWithPKResponse mergeTableRecordsWithPK(

                            com.rsys.ws.MergeTableRecordsWithPK mergeTableRecordsWithPK135,com.rsys.ws.SessionHeader sessionHeader136)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.TableFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[31].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:mergeTableRecordsWithPKRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    mergeTableRecordsWithPK135,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "mergeTableRecordsWithPK")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader136!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader136 = toOM(sessionHeader136, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "mergeTableRecordsWithPK")));
                                                    addHeader(omElementsessionHeader136,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.MergeTableRecordsWithPKResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.MergeTableRecordsWithPKResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.TableFault){
                          throw (com.rsys.ws.client.TableFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Creates a document.
                     * @see com.rsys.ws.client.ResponsysWSService#createDocument
                     * @param createDocument138
                    
                     * @param sessionHeader139
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.DocumentFault : 
                     */

                    

                            public  com.rsys.ws.CreateDocumentResponse createDocument(

                            com.rsys.ws.CreateDocument createDocument138,com.rsys.ws.SessionHeader sessionHeader139)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.DocumentFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[32].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:createDocumentRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    createDocument138,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "createDocument")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader139!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader139 = toOM(sessionHeader139, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "createDocument")));
                                                    addHeader(omElementsessionHeader139,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.CreateDocumentResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.CreateDocumentResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.DocumentFault){
                          throw (com.rsys.ws.client.DocumentFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Auhenticate the server.
                     * @see com.rsys.ws.client.ResponsysWSService#authenticateServer
                     * @param authenticateServer141
                    
                     * @throws com.rsys.ws.client.AccountFault : 
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     */

                    

                            public  com.rsys.ws.AuthenticateServerResponse authenticateServer(

                            com.rsys.ws.AuthenticateServer authenticateServer141)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.AccountFault
                        ,com.rsys.ws.client.UnexpectedErrorFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[33].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:authenticateServerRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    authenticateServer141,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "authenticateServer")));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.AuthenticateServerResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.AuthenticateServerResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.AccountFault){
                          throw (com.rsys.ws.client.AccountFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Create folder in the Content Library.
                     * @see com.rsys.ws.client.ResponsysWSService#createContentLibraryFolder
                     * @param createContentLibraryFolder143
                    
                     * @param sessionHeader144
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.FolderFault : 
                     */

                    

                            public  com.rsys.ws.CreateContentLibraryFolderResponse createContentLibraryFolder(

                            com.rsys.ws.CreateContentLibraryFolder createContentLibraryFolder143,com.rsys.ws.SessionHeader sessionHeader144)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.FolderFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[34].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:createContentLibraryFolderRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    createContentLibraryFolder143,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "createContentLibraryFolder")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader144!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader144 = toOM(sessionHeader144, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "createContentLibraryFolder")));
                                                    addHeader(omElementsessionHeader144,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.CreateContentLibraryFolderResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.CreateContentLibraryFolderResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.FolderFault){
                          throw (com.rsys.ws.client.FolderFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Retrieves records from a list extension.
                     * @see com.rsys.ws.client.ResponsysWSService#retrieveProfileExtensionRecords
                     * @param retrieveProfileExtensionRecords146
                    
                     * @param sessionHeader147
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.ListExtensionFault : 
                     */

                    

                            public  com.rsys.ws.RetrieveProfileExtensionRecordsResponse retrieveProfileExtensionRecords(

                            com.rsys.ws.RetrieveProfileExtensionRecords retrieveProfileExtensionRecords146,com.rsys.ws.SessionHeader sessionHeader147)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.ListExtensionFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[35].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:retrieveProfileExtensionRecordsRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    retrieveProfileExtensionRecords146,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "retrieveProfileExtensionRecords")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader147!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader147 = toOM(sessionHeader147, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "retrieveProfileExtensionRecords")));
                                                    addHeader(omElementsessionHeader147,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.RetrieveProfileExtensionRecordsResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.RetrieveProfileExtensionRecordsResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.ListExtensionFault){
                          throw (com.rsys.ws.client.ListExtensionFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Retrieves recipients from a list.
                     * @see com.rsys.ws.client.ResponsysWSService#retrieveListMembers
                     * @param retrieveListMembers149
                    
                     * @param sessionHeader150
                    
                     * @throws com.rsys.ws.client.ListFault : 
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     */

                    

                            public  com.rsys.ws.RetrieveListMembersResponse retrieveListMembers(

                            com.rsys.ws.RetrieveListMembers retrieveListMembers149,com.rsys.ws.SessionHeader sessionHeader150)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.ListFault
                        ,com.rsys.ws.client.UnexpectedErrorFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[36].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:retrieveListMembersRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    retrieveListMembers149,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "retrieveListMembers")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader150!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader150 = toOM(sessionHeader150, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "retrieveListMembers")));
                                                    addHeader(omElementsessionHeader150,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.RetrieveListMembersResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.RetrieveListMembersResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.ListFault){
                          throw (com.rsys.ws.client.ListFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Delete folder in the Content Library.
                     * @see com.rsys.ws.client.ResponsysWSService#deleteContentLibraryFolder
                     * @param deleteContentLibraryFolder152
                    
                     * @param sessionHeader153
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.FolderFault : 
                     */

                    

                            public  com.rsys.ws.DeleteContentLibraryFolderResponse deleteContentLibraryFolder(

                            com.rsys.ws.DeleteContentLibraryFolder deleteContentLibraryFolder152,com.rsys.ws.SessionHeader sessionHeader153)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.FolderFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[37].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:deleteContentLibraryFolderRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    deleteContentLibraryFolder152,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "deleteContentLibraryFolder")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader153!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader153 = toOM(sessionHeader153, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "deleteContentLibraryFolder")));
                                                    addHeader(omElementsessionHeader153,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.DeleteContentLibraryFolderResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.DeleteContentLibraryFolderResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.FolderFault){
                          throw (com.rsys.ws.client.FolderFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Launch a Campaign Immediately.
                     * @see com.rsys.ws.client.ResponsysWSService#launchCampaign
                     * @param launchCampaign155
                    
                     * @param sessionHeader156
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.CampaignFault : 
                     */

                    

                            public  com.rsys.ws.LaunchCampaignResponse launchCampaign(

                            com.rsys.ws.LaunchCampaign launchCampaign155,com.rsys.ws.SessionHeader sessionHeader156)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.CampaignFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[38].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:launchCampaignRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    launchCampaign155,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "launchCampaign")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader156!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader156 = toOM(sessionHeader156, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "launchCampaign")));
                                                    addHeader(omElementsessionHeader156,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.LaunchCampaignResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.LaunchCampaignResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.CampaignFault){
                          throw (com.rsys.ws.client.CampaignFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * List folders in the Responsys account.
                     * @see com.rsys.ws.client.ResponsysWSService#listFolders
                     * @param listFolders158
                    
                     * @param sessionHeader159
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     */

                    

                            public  com.rsys.ws.ListFoldersResponse listFolders(

                            com.rsys.ws.ListFolders listFolders158,com.rsys.ws.SessionHeader sessionHeader159)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[39].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:listFoldersRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    listFolders158,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "listFolders")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader159!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader159 = toOM(sessionHeader159, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "listFolders")));
                                                    addHeader(omElementsessionHeader159,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.ListFoldersResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.ListFoldersResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Login to the Responsys Web Services API.
                     * @see com.rsys.ws.client.ResponsysWSService#login
                     * @param login161
                    
                     * @throws com.rsys.ws.client.AccountFault : 
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     */

                    

                            public  com.rsys.ws.LoginResponse login(

                            com.rsys.ws.Login login161)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.AccountFault
                        ,com.rsys.ws.client.UnexpectedErrorFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[40].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:loginRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    login161,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "login")));
                                                
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.LoginResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.LoginResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.AccountFault){
                          throw (com.rsys.ws.client.AccountFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Update asset in the Content Library.
                     * @see com.rsys.ws.client.ResponsysWSService#setContentLibraryItem
                     * @param setContentLibraryItem163
                    
                     * @param sessionHeader164
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.DocumentFault : 
                     */

                    

                            public  com.rsys.ws.SetContentLibraryItemResponse setContentLibraryItem(

                            com.rsys.ws.SetContentLibraryItem setContentLibraryItem163,com.rsys.ws.SessionHeader sessionHeader164)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.DocumentFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[41].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:setContentLibraryItemRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    setContentLibraryItem163,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "setContentLibraryItem")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader164!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader164 = toOM(sessionHeader164, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "setContentLibraryItem")));
                                                    addHeader(omElementsessionHeader164,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.SetContentLibraryItemResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.SetContentLibraryItemResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.DocumentFault){
                          throw (com.rsys.ws.client.DocumentFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * List folders in the Content Library.
                     * @see com.rsys.ws.client.ResponsysWSService#listContentLibraryFolders
                     * @param listContentLibraryFolders166
                    
                     * @param sessionHeader167
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.FolderFault : 
                     */

                    

                            public  com.rsys.ws.ListContentLibraryFoldersResponse listContentLibraryFolders(

                            com.rsys.ws.ListContentLibraryFolders listContentLibraryFolders166,com.rsys.ws.SessionHeader sessionHeader167)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.FolderFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[42].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:listContentLibraryFoldersRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    listContentLibraryFolders166,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "listContentLibraryFolders")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader167!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader167 = toOM(sessionHeader167, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "listContentLibraryFolders")));
                                                    addHeader(omElementsessionHeader167,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.ListContentLibraryFoldersResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.ListContentLibraryFoldersResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.FolderFault){
                          throw (com.rsys.ws.client.FolderFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Schedule a Campaign Launch.
                     * @see com.rsys.ws.client.ResponsysWSService#scheduleCampaignLaunch
                     * @param scheduleCampaignLaunch169
                    
                     * @param sessionHeader170
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.CampaignFault : 
                     */

                    

                            public  com.rsys.ws.ScheduleCampaignLaunchResponse scheduleCampaignLaunch(

                            com.rsys.ws.ScheduleCampaignLaunch scheduleCampaignLaunch169,com.rsys.ws.SessionHeader sessionHeader170)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.CampaignFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[43].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:scheduleCampaignLaunchRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    scheduleCampaignLaunch169,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "scheduleCampaignLaunch")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader170!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader170 = toOM(sessionHeader170, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "scheduleCampaignLaunch")));
                                                    addHeader(omElementsessionHeader170,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.ScheduleCampaignLaunchResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.ScheduleCampaignLaunchResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.CampaignFault){
                          throw (com.rsys.ws.client.CampaignFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Logout of the Responsys Web Services API.
                     * @see com.rsys.ws.client.ResponsysWSService#logout
                     * @param logout172
                    
                     * @param sessionHeader173
                    
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     */

                    

                            public  com.rsys.ws.LogoutResponse logout(

                            com.rsys.ws.Logout logout172,com.rsys.ws.SessionHeader sessionHeader173)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.UnexpectedErrorFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[44].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:logoutRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    logout172,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "logout")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader173!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader173 = toOM(sessionHeader173, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "logout")));
                                                    addHeader(omElementsessionHeader173,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.LogoutResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.LogoutResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            
                    /**
                     * Auto generated method signature
                     * Merge And Send Triggered Message for 1 or more recipients.
                     * @see com.rsys.ws.client.ResponsysWSService#haMergeTriggerEmail
                     * @param haMergeTriggerEmail175
                    
                     * @param sessionHeader176
                    
                     * @throws com.rsys.ws.client.DuplicateApiRequestFault : 
                     * @throws com.rsys.ws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.ws.client.TriggeredMessageFault : 
                     * @throws com.rsys.ws.client.UnrecoverableErrorFault : 
                     */

                    

                            public  com.rsys.ws.HaMergeTriggerEmailResponse haMergeTriggerEmail(

                            com.rsys.ws.HaMergeTriggerEmail haMergeTriggerEmail175,com.rsys.ws.SessionHeader sessionHeader176)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.ws.client.DuplicateApiRequestFault
                        ,com.rsys.ws.client.UnexpectedErrorFault
                        ,com.rsys.ws.client.TriggeredMessageFault
                        ,com.rsys.ws.client.UnrecoverableErrorFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[45].getName());
              _operationClient.getOptions().setAction("urn:ws.rsys.com:ResponsysWS:HaMergeTriggerEmailRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    haMergeTriggerEmail175,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com",
                                                    "haMergeTriggerEmail")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader176!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader176 = toOM(sessionHeader176, optimizeContent(new javax.xml.namespace.QName("urn:ws.rsys.com", "haMergeTriggerEmail")));
                                                    addHeader(omElementsessionHeader176,env);
                                                
                                        }
                                    
        //adding SOAP soap_headers
         _serviceClient.addHeadersToEnvelope(env);
        // set the message context with that soap envelope
        _messageContext.setEnvelope(env);

        // add the message contxt to the operation client
        _operationClient.addMessageContext(_messageContext);

        //execute the operation client
        _operationClient.execute(true);

         
               org.apache.axis2.context.MessageContext _returnMessageContext = _operationClient.getMessageContext(
                                           org.apache.axis2.wsdl.WSDLConstants.MESSAGE_LABEL_IN_VALUE);
                org.apache.axiom.soap.SOAPEnvelope _returnEnv = _returnMessageContext.getEnvelope();
                
                
                                java.lang.Object object = fromOM(
                                             _returnEnv.getBody().getFirstElement() ,
                                             com.rsys.ws.HaMergeTriggerEmailResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.ws.HaMergeTriggerEmailResponse)object;
                                   
         }catch(org.apache.axis2.AxisFault f){

            org.apache.axiom.om.OMElement faultElt = f.getDetail();
            if (faultElt!=null){
                if (faultExceptionNameMap.containsKey(faultElt.getQName())){
                    //make the fault by reflection
                    try{
                        java.lang.String exceptionClassName = (java.lang.String)faultExceptionClassNameMap.get(faultElt.getQName());
                        java.lang.Class exceptionClass = java.lang.Class.forName(exceptionClassName);
                        java.lang.Exception ex=
                                (java.lang.Exception) exceptionClass.newInstance();
                        //message class
                        java.lang.String messageClassName = (java.lang.String)faultMessageMap.get(faultElt.getQName());
                        java.lang.Class messageClass = java.lang.Class.forName(messageClassName);
                        java.lang.Object messageObject = fromOM(faultElt,messageClass,null);
                        java.lang.reflect.Method m = exceptionClass.getMethod("setFaultMessage",
                                   new java.lang.Class[]{messageClass});
                        m.invoke(ex,new java.lang.Object[]{messageObject});
                        
                        if (ex instanceof com.rsys.ws.client.DuplicateApiRequestFault){
                          throw (com.rsys.ws.client.DuplicateApiRequestFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.UnexpectedErrorFault){
                          throw (com.rsys.ws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.TriggeredMessageFault){
                          throw (com.rsys.ws.client.TriggeredMessageFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.ws.client.UnrecoverableErrorFault){
                          throw (com.rsys.ws.client.UnrecoverableErrorFault)ex;
                        }
                        

                        throw new java.rmi.RemoteException(ex.getMessage(), ex);
                    }catch(java.lang.ClassCastException e){
                       // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.ClassNotFoundException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }catch (java.lang.NoSuchMethodException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    } catch (java.lang.reflect.InvocationTargetException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }  catch (java.lang.IllegalAccessException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }   catch (java.lang.InstantiationException e) {
                        // we cannot intantiate the class - throw the original Axis fault
                        throw f;
                    }
                }else{
                    throw f;
                }
            }else{
                throw f;
            }
            } finally {
                _messageContext.getTransportOut().getSender().cleanup(_messageContext);
            }
        }
            


       /**
        *  A utility method that copies the namepaces from the SOAPEnvelope
        */
       private java.util.Map getEnvelopeNamespaces(org.apache.axiom.soap.SOAPEnvelope env){
        java.util.Map returnMap = new java.util.HashMap();
        java.util.Iterator namespaceIterator = env.getAllDeclaredNamespaces();
        while (namespaceIterator.hasNext()) {
            org.apache.axiom.om.OMNamespace ns = (org.apache.axiom.om.OMNamespace) namespaceIterator.next();
            returnMap.put(ns.getPrefix(),ns.getNamespaceURI());
        }
       return returnMap;
    }

    
    
    private javax.xml.namespace.QName[] opNameArray = null;
    private boolean optimizeContent(javax.xml.namespace.QName opName) {
        

        if (opNameArray == null) {
            return false;
        }
        for (int i = 0; i < opNameArray.length; i++) {
            if (opName.equals(opNameArray[i])) {
                return true;   
            }
        }
        return false;
    }
     //https://ws2.responsys.net/webservices/services/ResponsysWSService
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.TriggerCustomEvent param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.TriggerCustomEvent.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.TriggerCustomEventResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.TriggerCustomEventResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.fault.DuplicateApiRequestFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.fault.DuplicateApiRequestFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.fault.UnexpectedErrorFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.fault.UnexpectedErrorFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.fault.CustomEventFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.fault.CustomEventFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.SessionHeader param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.SessionHeader.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.SetDocumentImages param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.SetDocumentImages.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.SetDocumentImagesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.SetDocumentImagesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.fault.DocumentFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.fault.DocumentFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.MergeTriggerSMS param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.MergeTriggerSMS.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.MergeTriggerSMSResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.MergeTriggerSMSResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.fault.TriggeredMessageFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.fault.TriggeredMessageFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.HaMergeTriggerSms param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.HaMergeTriggerSms.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.HaMergeTriggerSmsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.HaMergeTriggerSmsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.fault.UnrecoverableErrorFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.fault.UnrecoverableErrorFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.MergeListMembersRIID param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.MergeListMembersRIID.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.MergeListMembersRIIDResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.MergeListMembersRIIDResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.fault.ListFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.fault.ListFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.CreateTableWithPK param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.CreateTableWithPK.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.CreateTableWithPKResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.CreateTableWithPKResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.fault.TableFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.fault.TableFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.TriggerCampaignMessage param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.TriggerCampaignMessage.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.TriggerCampaignMessageResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.TriggerCampaignMessageResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.DeleteListMembers param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.DeleteListMembers.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.DeleteListMembersResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.DeleteListMembersResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.MergeTriggerEmail param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.MergeTriggerEmail.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.MergeTriggerEmailResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.MergeTriggerEmailResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.DeleteFolder param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.DeleteFolder.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.DeleteFolderResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.DeleteFolderResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.fault.FolderFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.fault.FolderFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.GetDocumentContent param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.GetDocumentContent.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.GetDocumentContentResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.GetDocumentContentResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.CreateFolder param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.CreateFolder.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.CreateFolderResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.CreateFolderResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.DeleteTableRecords param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.DeleteTableRecords.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.DeleteTableRecordsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.DeleteTableRecordsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.MergeIntoProfileExtension param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.MergeIntoProfileExtension.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.MergeIntoProfileExtensionResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.MergeIntoProfileExtensionResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.fault.ListExtensionFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.fault.ListExtensionFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.DoesContentLibraryFolderExist param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.DoesContentLibraryFolderExist.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.DoesContentLibraryFolderExistResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.DoesContentLibraryFolderExistResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.TruncateTable param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.TruncateTable.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.TruncateTableResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.TruncateTableResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.RetrieveTableRecords param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.RetrieveTableRecords.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.RetrieveTableRecordsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.RetrieveTableRecordsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.SetDocumentContent param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.SetDocumentContent.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.SetDocumentContentResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.SetDocumentContentResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.DeleteTable param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.DeleteTable.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.DeleteTableResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.DeleteTableResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.MergeListMembers param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.MergeListMembers.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.MergeListMembersResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.MergeListMembersResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.DeleteContentLibraryItem param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.DeleteContentLibraryItem.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.DeleteContentLibraryItemResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.DeleteContentLibraryItemResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.DeleteProfileExtensionMembers param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.DeleteProfileExtensionMembers.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.DeleteProfileExtensionMembersResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.DeleteProfileExtensionMembersResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.GetContentLibraryItem param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.GetContentLibraryItem.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.GetContentLibraryItemResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.GetContentLibraryItemResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.CreateProfileExtensionTable param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.CreateProfileExtensionTable.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.CreateProfileExtensionTableResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.CreateProfileExtensionTableResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.CreateTable param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.CreateTable.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.CreateTableResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.CreateTableResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.LoginWithCertificate param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.LoginWithCertificate.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.LoginWithCertificateResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.LoginWithCertificateResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.fault.AccountFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.fault.AccountFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.AuthSessionHeader param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.AuthSessionHeader.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.MergeTableRecords param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.MergeTableRecords.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.MergeTableRecordsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.MergeTableRecordsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.GetLaunchStatus param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.GetLaunchStatus.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.GetLaunchStatusResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.GetLaunchStatusResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.fault.CampaignFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.fault.CampaignFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.CreateContentLibraryItem param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.CreateContentLibraryItem.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.CreateContentLibraryItemResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.CreateContentLibraryItemResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.DeleteDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.DeleteDocument.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.DeleteDocumentResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.DeleteDocumentResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.GetDocumentImages param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.GetDocumentImages.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.GetDocumentImagesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.GetDocumentImagesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.MergeTableRecordsWithPK param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.MergeTableRecordsWithPK.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.MergeTableRecordsWithPKResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.MergeTableRecordsWithPKResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.CreateDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.CreateDocument.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.CreateDocumentResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.CreateDocumentResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.AuthenticateServer param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.AuthenticateServer.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.AuthenticateServerResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.AuthenticateServerResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.CreateContentLibraryFolder param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.CreateContentLibraryFolder.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.CreateContentLibraryFolderResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.CreateContentLibraryFolderResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.RetrieveProfileExtensionRecords param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.RetrieveProfileExtensionRecords.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.RetrieveProfileExtensionRecordsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.RetrieveProfileExtensionRecordsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.RetrieveListMembers param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.RetrieveListMembers.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.RetrieveListMembersResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.RetrieveListMembersResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.DeleteContentLibraryFolder param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.DeleteContentLibraryFolder.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.DeleteContentLibraryFolderResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.DeleteContentLibraryFolderResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.LaunchCampaign param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.LaunchCampaign.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.LaunchCampaignResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.LaunchCampaignResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.ListFolders param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.ListFolders.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.ListFoldersResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.ListFoldersResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.Login param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.Login.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.LoginResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.LoginResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.SetContentLibraryItem param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.SetContentLibraryItem.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.SetContentLibraryItemResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.SetContentLibraryItemResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.ListContentLibraryFolders param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.ListContentLibraryFolders.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.ListContentLibraryFoldersResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.ListContentLibraryFoldersResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.ScheduleCampaignLaunch param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.ScheduleCampaignLaunch.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.ScheduleCampaignLaunchResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.ScheduleCampaignLaunchResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.Logout param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.Logout.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.LogoutResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.LogoutResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.HaMergeTriggerEmail param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.HaMergeTriggerEmail.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.ws.HaMergeTriggerEmailResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.ws.HaMergeTriggerEmailResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.TriggerCustomEvent param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.TriggerCustomEvent.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.SetDocumentImages param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.SetDocumentImages.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.MergeTriggerSMS param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.MergeTriggerSMS.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.HaMergeTriggerSms param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.HaMergeTriggerSms.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.MergeListMembersRIID param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.MergeListMembersRIID.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.CreateTableWithPK param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.CreateTableWithPK.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.TriggerCampaignMessage param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.TriggerCampaignMessage.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.DeleteListMembers param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.DeleteListMembers.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.MergeTriggerEmail param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.MergeTriggerEmail.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.DeleteFolder param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.DeleteFolder.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.GetDocumentContent param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.GetDocumentContent.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.CreateFolder param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.CreateFolder.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.DeleteTableRecords param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.DeleteTableRecords.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.MergeIntoProfileExtension param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.MergeIntoProfileExtension.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.DoesContentLibraryFolderExist param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.DoesContentLibraryFolderExist.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.TruncateTable param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.TruncateTable.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.RetrieveTableRecords param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.RetrieveTableRecords.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.SetDocumentContent param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.SetDocumentContent.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.DeleteTable param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.DeleteTable.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.MergeListMembers param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.MergeListMembers.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.DeleteContentLibraryItem param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.DeleteContentLibraryItem.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.DeleteProfileExtensionMembers param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.DeleteProfileExtensionMembers.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.GetContentLibraryItem param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.GetContentLibraryItem.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.CreateProfileExtensionTable param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.CreateProfileExtensionTable.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.CreateTable param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.CreateTable.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.LoginWithCertificate param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.LoginWithCertificate.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.MergeTableRecords param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.MergeTableRecords.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.GetLaunchStatus param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.GetLaunchStatus.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.CreateContentLibraryItem param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.CreateContentLibraryItem.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.DeleteDocument param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.DeleteDocument.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.GetDocumentImages param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.GetDocumentImages.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.MergeTableRecordsWithPK param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.MergeTableRecordsWithPK.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.CreateDocument param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.CreateDocument.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.AuthenticateServer param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.AuthenticateServer.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.CreateContentLibraryFolder param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.CreateContentLibraryFolder.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.RetrieveProfileExtensionRecords param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.RetrieveProfileExtensionRecords.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.RetrieveListMembers param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.RetrieveListMembers.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.DeleteContentLibraryFolder param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.DeleteContentLibraryFolder.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.LaunchCampaign param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.LaunchCampaign.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.ListFolders param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.ListFolders.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.Login param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.Login.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.SetContentLibraryItem param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.SetContentLibraryItem.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.ListContentLibraryFolders param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.ListContentLibraryFolders.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.ScheduleCampaignLaunch param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.ScheduleCampaignLaunch.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.Logout param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.Logout.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.ws.HaMergeTriggerEmail param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.ws.HaMergeTriggerEmail.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             


        /**
        *  get the default envelope
        */
        private org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory){
        return factory.getDefaultEnvelope();
        }


        private  java.lang.Object fromOM(
        org.apache.axiom.om.OMElement param,
        java.lang.Class type,
        java.util.Map extraNamespaces) throws org.apache.axis2.AxisFault{

        try {
        
                if (com.rsys.ws.TriggerCustomEvent.class.equals(type)){
                
                           return com.rsys.ws.TriggerCustomEvent.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.TriggerCustomEventResponse.class.equals(type)){
                
                           return com.rsys.ws.TriggerCustomEventResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.DuplicateApiRequestFault.class.equals(type)){
                
                           return com.rsys.ws.fault.DuplicateApiRequestFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.CustomEventFault.class.equals(type)){
                
                           return com.rsys.ws.fault.CustomEventFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SetDocumentImages.class.equals(type)){
                
                           return com.rsys.ws.SetDocumentImages.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SetDocumentImagesResponse.class.equals(type)){
                
                           return com.rsys.ws.SetDocumentImagesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.DocumentFault.class.equals(type)){
                
                           return com.rsys.ws.fault.DocumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.MergeTriggerSMS.class.equals(type)){
                
                           return com.rsys.ws.MergeTriggerSMS.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.MergeTriggerSMSResponse.class.equals(type)){
                
                           return com.rsys.ws.MergeTriggerSMSResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.DuplicateApiRequestFault.class.equals(type)){
                
                           return com.rsys.ws.fault.DuplicateApiRequestFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.TriggeredMessageFault.class.equals(type)){
                
                           return com.rsys.ws.fault.TriggeredMessageFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.HaMergeTriggerSms.class.equals(type)){
                
                           return com.rsys.ws.HaMergeTriggerSms.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.HaMergeTriggerSmsResponse.class.equals(type)){
                
                           return com.rsys.ws.HaMergeTriggerSmsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.DuplicateApiRequestFault.class.equals(type)){
                
                           return com.rsys.ws.fault.DuplicateApiRequestFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.TriggeredMessageFault.class.equals(type)){
                
                           return com.rsys.ws.fault.TriggeredMessageFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnrecoverableErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnrecoverableErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.MergeListMembersRIID.class.equals(type)){
                
                           return com.rsys.ws.MergeListMembersRIID.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.MergeListMembersRIIDResponse.class.equals(type)){
                
                           return com.rsys.ws.MergeListMembersRIIDResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.ListFault.class.equals(type)){
                
                           return com.rsys.ws.fault.ListFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.CreateTableWithPK.class.equals(type)){
                
                           return com.rsys.ws.CreateTableWithPK.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.CreateTableWithPKResponse.class.equals(type)){
                
                           return com.rsys.ws.CreateTableWithPKResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.TableFault.class.equals(type)){
                
                           return com.rsys.ws.fault.TableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.TriggerCampaignMessage.class.equals(type)){
                
                           return com.rsys.ws.TriggerCampaignMessage.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.TriggerCampaignMessageResponse.class.equals(type)){
                
                           return com.rsys.ws.TriggerCampaignMessageResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.DuplicateApiRequestFault.class.equals(type)){
                
                           return com.rsys.ws.fault.DuplicateApiRequestFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.TriggeredMessageFault.class.equals(type)){
                
                           return com.rsys.ws.fault.TriggeredMessageFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.DeleteListMembers.class.equals(type)){
                
                           return com.rsys.ws.DeleteListMembers.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.DeleteListMembersResponse.class.equals(type)){
                
                           return com.rsys.ws.DeleteListMembersResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.ListFault.class.equals(type)){
                
                           return com.rsys.ws.fault.ListFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.MergeTriggerEmail.class.equals(type)){
                
                           return com.rsys.ws.MergeTriggerEmail.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.MergeTriggerEmailResponse.class.equals(type)){
                
                           return com.rsys.ws.MergeTriggerEmailResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.DuplicateApiRequestFault.class.equals(type)){
                
                           return com.rsys.ws.fault.DuplicateApiRequestFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.TriggeredMessageFault.class.equals(type)){
                
                           return com.rsys.ws.fault.TriggeredMessageFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.DeleteFolder.class.equals(type)){
                
                           return com.rsys.ws.DeleteFolder.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.DeleteFolderResponse.class.equals(type)){
                
                           return com.rsys.ws.DeleteFolderResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.FolderFault.class.equals(type)){
                
                           return com.rsys.ws.fault.FolderFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.GetDocumentContent.class.equals(type)){
                
                           return com.rsys.ws.GetDocumentContent.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.GetDocumentContentResponse.class.equals(type)){
                
                           return com.rsys.ws.GetDocumentContentResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.DocumentFault.class.equals(type)){
                
                           return com.rsys.ws.fault.DocumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.CreateFolder.class.equals(type)){
                
                           return com.rsys.ws.CreateFolder.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.CreateFolderResponse.class.equals(type)){
                
                           return com.rsys.ws.CreateFolderResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.FolderFault.class.equals(type)){
                
                           return com.rsys.ws.fault.FolderFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.DeleteTableRecords.class.equals(type)){
                
                           return com.rsys.ws.DeleteTableRecords.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.DeleteTableRecordsResponse.class.equals(type)){
                
                           return com.rsys.ws.DeleteTableRecordsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.TableFault.class.equals(type)){
                
                           return com.rsys.ws.fault.TableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.MergeIntoProfileExtension.class.equals(type)){
                
                           return com.rsys.ws.MergeIntoProfileExtension.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.MergeIntoProfileExtensionResponse.class.equals(type)){
                
                           return com.rsys.ws.MergeIntoProfileExtensionResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.ListExtensionFault.class.equals(type)){
                
                           return com.rsys.ws.fault.ListExtensionFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.DoesContentLibraryFolderExist.class.equals(type)){
                
                           return com.rsys.ws.DoesContentLibraryFolderExist.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.DoesContentLibraryFolderExistResponse.class.equals(type)){
                
                           return com.rsys.ws.DoesContentLibraryFolderExistResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.FolderFault.class.equals(type)){
                
                           return com.rsys.ws.fault.FolderFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.TruncateTable.class.equals(type)){
                
                           return com.rsys.ws.TruncateTable.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.TruncateTableResponse.class.equals(type)){
                
                           return com.rsys.ws.TruncateTableResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.TableFault.class.equals(type)){
                
                           return com.rsys.ws.fault.TableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.RetrieveTableRecords.class.equals(type)){
                
                           return com.rsys.ws.RetrieveTableRecords.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.RetrieveTableRecordsResponse.class.equals(type)){
                
                           return com.rsys.ws.RetrieveTableRecordsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.TableFault.class.equals(type)){
                
                           return com.rsys.ws.fault.TableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SetDocumentContent.class.equals(type)){
                
                           return com.rsys.ws.SetDocumentContent.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SetDocumentContentResponse.class.equals(type)){
                
                           return com.rsys.ws.SetDocumentContentResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.DocumentFault.class.equals(type)){
                
                           return com.rsys.ws.fault.DocumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.DeleteTable.class.equals(type)){
                
                           return com.rsys.ws.DeleteTable.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.DeleteTableResponse.class.equals(type)){
                
                           return com.rsys.ws.DeleteTableResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.TableFault.class.equals(type)){
                
                           return com.rsys.ws.fault.TableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.MergeListMembers.class.equals(type)){
                
                           return com.rsys.ws.MergeListMembers.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.MergeListMembersResponse.class.equals(type)){
                
                           return com.rsys.ws.MergeListMembersResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.ListFault.class.equals(type)){
                
                           return com.rsys.ws.fault.ListFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.DeleteContentLibraryItem.class.equals(type)){
                
                           return com.rsys.ws.DeleteContentLibraryItem.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.DeleteContentLibraryItemResponse.class.equals(type)){
                
                           return com.rsys.ws.DeleteContentLibraryItemResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.DocumentFault.class.equals(type)){
                
                           return com.rsys.ws.fault.DocumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.DeleteProfileExtensionMembers.class.equals(type)){
                
                           return com.rsys.ws.DeleteProfileExtensionMembers.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.DeleteProfileExtensionMembersResponse.class.equals(type)){
                
                           return com.rsys.ws.DeleteProfileExtensionMembersResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.ListExtensionFault.class.equals(type)){
                
                           return com.rsys.ws.fault.ListExtensionFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.GetContentLibraryItem.class.equals(type)){
                
                           return com.rsys.ws.GetContentLibraryItem.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.GetContentLibraryItemResponse.class.equals(type)){
                
                           return com.rsys.ws.GetContentLibraryItemResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.DocumentFault.class.equals(type)){
                
                           return com.rsys.ws.fault.DocumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.CreateProfileExtensionTable.class.equals(type)){
                
                           return com.rsys.ws.CreateProfileExtensionTable.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.CreateProfileExtensionTableResponse.class.equals(type)){
                
                           return com.rsys.ws.CreateProfileExtensionTableResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.ListFault.class.equals(type)){
                
                           return com.rsys.ws.fault.ListFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.CreateTable.class.equals(type)){
                
                           return com.rsys.ws.CreateTable.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.CreateTableResponse.class.equals(type)){
                
                           return com.rsys.ws.CreateTableResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.TableFault.class.equals(type)){
                
                           return com.rsys.ws.fault.TableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.LoginWithCertificate.class.equals(type)){
                
                           return com.rsys.ws.LoginWithCertificate.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.LoginWithCertificateResponse.class.equals(type)){
                
                           return com.rsys.ws.LoginWithCertificateResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.AccountFault.class.equals(type)){
                
                           return com.rsys.ws.fault.AccountFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.AuthSessionHeader.class.equals(type)){
                
                           return com.rsys.ws.AuthSessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.MergeTableRecords.class.equals(type)){
                
                           return com.rsys.ws.MergeTableRecords.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.MergeTableRecordsResponse.class.equals(type)){
                
                           return com.rsys.ws.MergeTableRecordsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.TableFault.class.equals(type)){
                
                           return com.rsys.ws.fault.TableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.GetLaunchStatus.class.equals(type)){
                
                           return com.rsys.ws.GetLaunchStatus.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.GetLaunchStatusResponse.class.equals(type)){
                
                           return com.rsys.ws.GetLaunchStatusResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.CampaignFault.class.equals(type)){
                
                           return com.rsys.ws.fault.CampaignFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.CreateContentLibraryItem.class.equals(type)){
                
                           return com.rsys.ws.CreateContentLibraryItem.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.CreateContentLibraryItemResponse.class.equals(type)){
                
                           return com.rsys.ws.CreateContentLibraryItemResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.DocumentFault.class.equals(type)){
                
                           return com.rsys.ws.fault.DocumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.DeleteDocument.class.equals(type)){
                
                           return com.rsys.ws.DeleteDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.DeleteDocumentResponse.class.equals(type)){
                
                           return com.rsys.ws.DeleteDocumentResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.DocumentFault.class.equals(type)){
                
                           return com.rsys.ws.fault.DocumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.GetDocumentImages.class.equals(type)){
                
                           return com.rsys.ws.GetDocumentImages.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.GetDocumentImagesResponse.class.equals(type)){
                
                           return com.rsys.ws.GetDocumentImagesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.DocumentFault.class.equals(type)){
                
                           return com.rsys.ws.fault.DocumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.MergeTableRecordsWithPK.class.equals(type)){
                
                           return com.rsys.ws.MergeTableRecordsWithPK.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.MergeTableRecordsWithPKResponse.class.equals(type)){
                
                           return com.rsys.ws.MergeTableRecordsWithPKResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.TableFault.class.equals(type)){
                
                           return com.rsys.ws.fault.TableFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.CreateDocument.class.equals(type)){
                
                           return com.rsys.ws.CreateDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.CreateDocumentResponse.class.equals(type)){
                
                           return com.rsys.ws.CreateDocumentResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.DocumentFault.class.equals(type)){
                
                           return com.rsys.ws.fault.DocumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.AuthenticateServer.class.equals(type)){
                
                           return com.rsys.ws.AuthenticateServer.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.AuthenticateServerResponse.class.equals(type)){
                
                           return com.rsys.ws.AuthenticateServerResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.AccountFault.class.equals(type)){
                
                           return com.rsys.ws.fault.AccountFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.CreateContentLibraryFolder.class.equals(type)){
                
                           return com.rsys.ws.CreateContentLibraryFolder.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.CreateContentLibraryFolderResponse.class.equals(type)){
                
                           return com.rsys.ws.CreateContentLibraryFolderResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.FolderFault.class.equals(type)){
                
                           return com.rsys.ws.fault.FolderFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.RetrieveProfileExtensionRecords.class.equals(type)){
                
                           return com.rsys.ws.RetrieveProfileExtensionRecords.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.RetrieveProfileExtensionRecordsResponse.class.equals(type)){
                
                           return com.rsys.ws.RetrieveProfileExtensionRecordsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.ListExtensionFault.class.equals(type)){
                
                           return com.rsys.ws.fault.ListExtensionFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.RetrieveListMembers.class.equals(type)){
                
                           return com.rsys.ws.RetrieveListMembers.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.RetrieveListMembersResponse.class.equals(type)){
                
                           return com.rsys.ws.RetrieveListMembersResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.ListFault.class.equals(type)){
                
                           return com.rsys.ws.fault.ListFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.DeleteContentLibraryFolder.class.equals(type)){
                
                           return com.rsys.ws.DeleteContentLibraryFolder.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.DeleteContentLibraryFolderResponse.class.equals(type)){
                
                           return com.rsys.ws.DeleteContentLibraryFolderResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.FolderFault.class.equals(type)){
                
                           return com.rsys.ws.fault.FolderFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.LaunchCampaign.class.equals(type)){
                
                           return com.rsys.ws.LaunchCampaign.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.LaunchCampaignResponse.class.equals(type)){
                
                           return com.rsys.ws.LaunchCampaignResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.CampaignFault.class.equals(type)){
                
                           return com.rsys.ws.fault.CampaignFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.ListFolders.class.equals(type)){
                
                           return com.rsys.ws.ListFolders.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.ListFoldersResponse.class.equals(type)){
                
                           return com.rsys.ws.ListFoldersResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.Login.class.equals(type)){
                
                           return com.rsys.ws.Login.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.LoginResponse.class.equals(type)){
                
                           return com.rsys.ws.LoginResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.AccountFault.class.equals(type)){
                
                           return com.rsys.ws.fault.AccountFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SetContentLibraryItem.class.equals(type)){
                
                           return com.rsys.ws.SetContentLibraryItem.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SetContentLibraryItemResponse.class.equals(type)){
                
                           return com.rsys.ws.SetContentLibraryItemResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.DocumentFault.class.equals(type)){
                
                           return com.rsys.ws.fault.DocumentFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.ListContentLibraryFolders.class.equals(type)){
                
                           return com.rsys.ws.ListContentLibraryFolders.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.ListContentLibraryFoldersResponse.class.equals(type)){
                
                           return com.rsys.ws.ListContentLibraryFoldersResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.FolderFault.class.equals(type)){
                
                           return com.rsys.ws.fault.FolderFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.ScheduleCampaignLaunch.class.equals(type)){
                
                           return com.rsys.ws.ScheduleCampaignLaunch.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.ScheduleCampaignLaunchResponse.class.equals(type)){
                
                           return com.rsys.ws.ScheduleCampaignLaunchResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.CampaignFault.class.equals(type)){
                
                           return com.rsys.ws.fault.CampaignFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.Logout.class.equals(type)){
                
                           return com.rsys.ws.Logout.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.LogoutResponse.class.equals(type)){
                
                           return com.rsys.ws.LogoutResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.HaMergeTriggerEmail.class.equals(type)){
                
                           return com.rsys.ws.HaMergeTriggerEmail.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.HaMergeTriggerEmailResponse.class.equals(type)){
                
                           return com.rsys.ws.HaMergeTriggerEmailResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.DuplicateApiRequestFault.class.equals(type)){
                
                           return com.rsys.ws.fault.DuplicateApiRequestFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.TriggeredMessageFault.class.equals(type)){
                
                           return com.rsys.ws.fault.TriggeredMessageFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.fault.UnrecoverableErrorFault.class.equals(type)){
                
                           return com.rsys.ws.fault.UnrecoverableErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.ws.SessionHeader.class.equals(type)){
                
                           return com.rsys.ws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    
   }
   