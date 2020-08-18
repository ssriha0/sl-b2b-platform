
/**
 * ResponsysWS57Stub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */
        package responsys.ws57.client;

        

        /*
        *  ResponsysWS57Stub java implementation
        */

        
        public class ResponsysWS57Stub extends org.apache.axis2.client.Stub
        implements ResponsysWS57{
        protected org.apache.axis2.description.AxisOperation[] _operations;

        //hashmaps to keep the fault mapping
        private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
        private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
        private java.util.HashMap faultMessageMap = new java.util.HashMap();

        private static int counter = 0;

        private static synchronized String getUniqueSuffix(){
            // reset the counter if it is greater than 99999
            if (counter > 99999){
                counter = 0;
            }
            counter = counter + 1; 
            return Long.toString(System.currentTimeMillis()) + "_" + counter;
        }

    
    private void populateAxisService() throws org.apache.axis2.AxisFault {

     //creating the Service with a unique name
     _service = new org.apache.axis2.description.AxisService("ResponsysWS57" + getUniqueSuffix());
     addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[51];
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "login"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[0]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "importFolder"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[1]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "getServerTimestamp"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[2]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "purgeDataSource"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[3]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "getCampaignsInDatamart"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[4]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "getReportOptions"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[5]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "copyDocument"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[6]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "cancel"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[7]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "updateDataSourceUsingMultipleColumns"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[8]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "listIndexes"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[9]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "showDocument"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[10]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "getCampaignProperties"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[11]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "logout"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[12]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "scrubDataSource"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[13]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "listFolderContents"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[14]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "listFolderObjects"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[15]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "getCampaignStatus"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[16]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "triggerFormRules"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[17]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "launchCampaign"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[18]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "checkResult"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[19]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "createDataSource"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[20]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "getLaunchesInDatamart"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[21]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "stopCampaign"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[22]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "listTablesForCampaign"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[23]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "createSQLDataSource"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[24]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "getDataSourceSchema"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[25]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "deleteDataSource"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[26]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "setCampaignProperties"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[27]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "removeDocument"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[28]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "copyCampaign"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[29]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "addIndex"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[30]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "runLaunchReport"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[31]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "downloadDataSourceByTimestamp"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[32]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "appendDataSource"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[33]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "deleteIndex"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[34]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "deleteFolder"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[35]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "truncateTable"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[36]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "unScheduleCampaign"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[37]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "getDataSourceRecordCount"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[38]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "exportFolder"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[39]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "updateDataSource"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[40]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "runTriggeredMessageReport"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[41]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "getLiveReportMetrics"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[42]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "uploadDocument"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[43]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "createCampaign"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[44]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "copyDataSource"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[45]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "deleteCampaign"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[46]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "downloadDataSource"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[47]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "purgeDataSourceByTimestamp"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[48]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "listFolders"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[49]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:ws57.responsys", "createFolder"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[50]=__operation;
            
        
        }

    //populates the faults
    private void populateFaults(){
         
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.client.RIFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.ws57.responsys","FaultException"),"responsys.ws57.fault.FaultException");
           


    }

    /**
      *Constructor that takes in a configContext
      */

    public ResponsysWS57Stub(org.apache.axis2.context.ConfigurationContext configurationContext,
       java.lang.String targetEndpoint)
       throws org.apache.axis2.AxisFault {
         this(configurationContext,targetEndpoint,false);
   }


   /**
     * Constructor that takes in a configContext  and useseperate listner
     */
   public ResponsysWS57Stub(org.apache.axis2.context.ConfigurationContext configurationContext,
        java.lang.String targetEndpoint, boolean useSeparateListener)
        throws org.apache.axis2.AxisFault {
         //To populate AxisService
         populateAxisService();
         populateFaults();

        _serviceClient = new org.apache.axis2.client.ServiceClient(configurationContext,_service);
        
	
        configurationContext = _serviceClient.getServiceContext().getConfigurationContext();

        _serviceClient.getOptions().setTo(new org.apache.axis2.addressing.EndpointReference(
                targetEndpoint));
        _serviceClient.getOptions().setUseSeparateListener(useSeparateListener);
        
    
    }

    /**
     * Default Constructor
     */
    public ResponsysWS57Stub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {
        
                    this(configurationContext,"http://ws4.responsys.net:80/webservices57/services/ResponsysWS57" );
                
    }

    /**
     * Default Constructor
     */
    public ResponsysWS57Stub() throws org.apache.axis2.AxisFault {
        
                    this("http://ws4.responsys.net:80/webservices57/services/ResponsysWS57" );
                
    }

    /**
     * Constructor taking the target endpoint
     */
    public ResponsysWS57Stub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(null,targetEndpoint);
    }



        
                    /**
                     * Auto generated method signature
                     * 
                     * @see responsys.ws57.client.ResponsysWS57#login
                     * @param login49
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.LoginResponse login(

                            responsys.ws57.Login login49)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    login49,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
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
                                             responsys.ws57.LoginResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.LoginResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#importFolder
                     * @param importFolder51
                    
                     * @param sessionHeader52
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.ImportFolderResponse importFolder(

                            responsys.ws57.ImportFolder importFolder51,responsys.ws57.SessionHeader sessionHeader52)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    importFolder51,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "importFolder")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader52!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader52 = toOM(sessionHeader52, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "importFolder")));
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
                                             responsys.ws57.ImportFolderResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.ImportFolderResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#getServerTimestamp
                     * @param getServerTimestamp54
                    
                     * @param sessionHeader55
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.GetServerTimestampResponse getServerTimestamp(

                            responsys.ws57.GetServerTimestamp getServerTimestamp54,responsys.ws57.SessionHeader sessionHeader55)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getServerTimestamp54,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "getServerTimestamp")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader55!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader55 = toOM(sessionHeader55, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "getServerTimestamp")));
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
                                             responsys.ws57.GetServerTimestampResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.GetServerTimestampResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#purgeDataSource
                     * @param purgeDataSource57
                    
                     * @param sessionHeader58
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.PurgeDataSourceResponse purgeDataSource(

                            responsys.ws57.PurgeDataSource purgeDataSource57,responsys.ws57.SessionHeader sessionHeader58)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    purgeDataSource57,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "purgeDataSource")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader58!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader58 = toOM(sessionHeader58, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "purgeDataSource")));
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
                                             responsys.ws57.PurgeDataSourceResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.PurgeDataSourceResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#getCampaignsInDatamart
                     * @param campaignsInDatamart60
                    
                     * @param sessionHeader61
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.CampaignsInDatamartResponse getCampaignsInDatamart(

                            responsys.ws57.CampaignsInDatamart campaignsInDatamart60,responsys.ws57.SessionHeader sessionHeader61)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[4].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    campaignsInDatamart60,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "getCampaignsInDatamart")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader61!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader61 = toOM(sessionHeader61, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "getCampaignsInDatamart")));
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
                                             responsys.ws57.CampaignsInDatamartResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.CampaignsInDatamartResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#getReportOptions
                     * @param getReportOptions63
                    
                     * @param sessionHeader64
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.GetReportOptionsResponse getReportOptions(

                            responsys.ws57.GetReportOptions getReportOptions63,responsys.ws57.SessionHeader sessionHeader64)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[5].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getReportOptions63,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "getReportOptions")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader64!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader64 = toOM(sessionHeader64, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "getReportOptions")));
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
                                             responsys.ws57.GetReportOptionsResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.GetReportOptionsResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#copyDocument
                     * @param copyDocument66
                    
                     * @param sessionHeader67
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.CopyDocumentResponse copyDocument(

                            responsys.ws57.CopyDocument copyDocument66,responsys.ws57.SessionHeader sessionHeader67)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[6].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    copyDocument66,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "copyDocument")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader67!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader67 = toOM(sessionHeader67, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "copyDocument")));
                                                    addHeader(omElementsessionHeader67,env);
                                                
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
                                             responsys.ws57.CopyDocumentResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.CopyDocumentResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * Cancel the Pending Operation.
                     * @see responsys.ws57.client.ResponsysWS57#cancel
                     * @param cancel69
                    
                     * @param sessionHeader70
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.CancelResponse cancel(

                            responsys.ws57.Cancel cancel69,responsys.ws57.SessionHeader sessionHeader70)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[7].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    cancel69,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "cancel")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader70!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader70 = toOM(sessionHeader70, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "cancel")));
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
                                             responsys.ws57.CancelResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.CancelResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#updateDataSourceUsingMultipleColumns
                     * @param updateDataSourceUsingMultipleColumns72
                    
                     * @param sessionHeader73
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.UpdateDataSourceUsingMultipleColumnsResponse updateDataSourceUsingMultipleColumns(

                            responsys.ws57.UpdateDataSourceUsingMultipleColumns updateDataSourceUsingMultipleColumns72,responsys.ws57.SessionHeader sessionHeader73)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[8].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    updateDataSourceUsingMultipleColumns72,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "updateDataSourceUsingMultipleColumns")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader73!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader73 = toOM(sessionHeader73, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "updateDataSourceUsingMultipleColumns")));
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
                                             responsys.ws57.UpdateDataSourceUsingMultipleColumnsResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.UpdateDataSourceUsingMultipleColumnsResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#listIndexes
                     * @param listIndexes75
                    
                     * @param sessionHeader76
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.ListIndexesResponse listIndexes(

                            responsys.ws57.ListIndexes listIndexes75,responsys.ws57.SessionHeader sessionHeader76)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[9].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    listIndexes75,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "listIndexes")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader76!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader76 = toOM(sessionHeader76, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "listIndexes")));
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
                                             responsys.ws57.ListIndexesResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.ListIndexesResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#showDocument
                     * @param showDocument78
                    
                     * @param sessionHeader79
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.ShowDocumentResponse showDocument(

                            responsys.ws57.ShowDocument showDocument78,responsys.ws57.SessionHeader sessionHeader79)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[10].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    showDocument78,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "showDocument")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader79!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader79 = toOM(sessionHeader79, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "showDocument")));
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
                                             responsys.ws57.ShowDocumentResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.ShowDocumentResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#getCampaignProperties
                     * @param getCampaignProperties81
                    
                     * @param sessionHeader82
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.GetCampaignPropertiesResponse getCampaignProperties(

                            responsys.ws57.GetCampaignProperties getCampaignProperties81,responsys.ws57.SessionHeader sessionHeader82)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[11].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getCampaignProperties81,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "getCampaignProperties")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader82!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader82 = toOM(sessionHeader82, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "getCampaignProperties")));
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
                                             responsys.ws57.GetCampaignPropertiesResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.GetCampaignPropertiesResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#logout
                     * @param logout84
                    
                     * @param sessionHeader85
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.LogoutResponse logout(

                            responsys.ws57.Logout logout84,responsys.ws57.SessionHeader sessionHeader85)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[12].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    logout84,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "logout")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader85!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader85 = toOM(sessionHeader85, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "logout")));
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
                                             responsys.ws57.LogoutResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.LogoutResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#scrubDataSource
                     * @param scrubDataSource87
                    
                     * @param sessionHeader88
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.ScrubDataSourceResponse scrubDataSource(

                            responsys.ws57.ScrubDataSource scrubDataSource87,responsys.ws57.SessionHeader sessionHeader88)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[13].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    scrubDataSource87,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "scrubDataSource")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader88!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader88 = toOM(sessionHeader88, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "scrubDataSource")));
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
                                             responsys.ws57.ScrubDataSourceResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.ScrubDataSourceResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#listFolderContents
                     * @param listFolderContents90
                    
                     * @param sessionHeader91
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.ListFolderContentsResponse listFolderContents(

                            responsys.ws57.ListFolderContents listFolderContents90,responsys.ws57.SessionHeader sessionHeader91)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[14].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    listFolderContents90,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "listFolderContents")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader91!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader91 = toOM(sessionHeader91, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "listFolderContents")));
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
                                             responsys.ws57.ListFolderContentsResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.ListFolderContentsResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#listFolderObjects
                     * @param listFolderObjects93
                    
                     * @param sessionHeader94
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.ListFolderObjectsResponse listFolderObjects(

                            responsys.ws57.ListFolderObjects listFolderObjects93,responsys.ws57.SessionHeader sessionHeader94)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[15].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    listFolderObjects93,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "listFolderObjects")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader94!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader94 = toOM(sessionHeader94, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "listFolderObjects")));
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
                                             responsys.ws57.ListFolderObjectsResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.ListFolderObjectsResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#getCampaignStatus
                     * @param getCampaignStatus96
                    
                     * @param sessionHeader97
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.GetCampaignStatusResponse getCampaignStatus(

                            responsys.ws57.GetCampaignStatus getCampaignStatus96,responsys.ws57.SessionHeader sessionHeader97)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[16].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getCampaignStatus96,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "getCampaignStatus")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader97!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader97 = toOM(sessionHeader97, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "getCampaignStatus")));
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
                                             responsys.ws57.GetCampaignStatusResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.GetCampaignStatusResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#triggerFormRules
                     * @param triggerFormRules99
                    
                     * @param sessionHeader100
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.TriggerFormRulesResponse triggerFormRules(

                            responsys.ws57.TriggerFormRules triggerFormRules99,responsys.ws57.SessionHeader sessionHeader100)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[17].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    triggerFormRules99,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "triggerFormRules")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader100!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader100 = toOM(sessionHeader100, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "triggerFormRules")));
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
                                             responsys.ws57.TriggerFormRulesResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.TriggerFormRulesResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#launchCampaign
                     * @param launchCampaign102
                    
                     * @param sessionHeader103
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.LaunchCampaignResponse launchCampaign(

                            responsys.ws57.LaunchCampaign launchCampaign102,responsys.ws57.SessionHeader sessionHeader103)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[18].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    launchCampaign102,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "launchCampaign")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader103!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader103 = toOM(sessionHeader103, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "launchCampaign")));
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
                                             responsys.ws57.LaunchCampaignResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.LaunchCampaignResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * Check the result of an on going operations using the previously returned IntermediateResult.
                     * @see responsys.ws57.client.ResponsysWS57#checkResult
                     * @param checkResult105
                    
                     * @param sessionHeader106
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.CheckResultResponse checkResult(

                            responsys.ws57.CheckResult checkResult105,responsys.ws57.SessionHeader sessionHeader106)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[19].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    checkResult105,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "checkResult")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader106!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader106 = toOM(sessionHeader106, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "checkResult")));
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
                                             responsys.ws57.CheckResultResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.CheckResultResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#createDataSource
                     * @param createDataSource108
                    
                     * @param sessionHeader109
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.CreateDataSourceResponse createDataSource(

                            responsys.ws57.CreateDataSource createDataSource108,responsys.ws57.SessionHeader sessionHeader109)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[20].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    createDataSource108,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "createDataSource")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader109!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader109 = toOM(sessionHeader109, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "createDataSource")));
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
                                             responsys.ws57.CreateDataSourceResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.CreateDataSourceResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#getLaunchesInDatamart
                     * @param launchesInDatamart111
                    
                     * @param sessionHeader112
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.LaunchesInDatamartResponse getLaunchesInDatamart(

                            responsys.ws57.LaunchesInDatamart launchesInDatamart111,responsys.ws57.SessionHeader sessionHeader112)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[21].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    launchesInDatamart111,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "getLaunchesInDatamart")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader112!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader112 = toOM(sessionHeader112, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "getLaunchesInDatamart")));
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
                                             responsys.ws57.LaunchesInDatamartResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.LaunchesInDatamartResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#stopCampaign
                     * @param stopCampaign114
                    
                     * @param sessionHeader115
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.StopCampaignResponse stopCampaign(

                            responsys.ws57.StopCampaign stopCampaign114,responsys.ws57.SessionHeader sessionHeader115)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[22].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    stopCampaign114,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "stopCampaign")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader115!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader115 = toOM(sessionHeader115, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "stopCampaign")));
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
                                             responsys.ws57.StopCampaignResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.StopCampaignResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#listTablesForCampaign
                     * @param listTablesForCampaign117
                    
                     * @param sessionHeader118
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.ListTablesForCampaignResponse listTablesForCampaign(

                            responsys.ws57.ListTablesForCampaign listTablesForCampaign117,responsys.ws57.SessionHeader sessionHeader118)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[23].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    listTablesForCampaign117,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "listTablesForCampaign")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader118!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader118 = toOM(sessionHeader118, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "listTablesForCampaign")));
                                                    addHeader(omElementsessionHeader118,env);
                                                
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
                                             responsys.ws57.ListTablesForCampaignResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.ListTablesForCampaignResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#createSQLDataSource
                     * @param createSQLDataSource120
                    
                     * @param sessionHeader121
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.CreateSQLDataSourceResponse createSQLDataSource(

                            responsys.ws57.CreateSQLDataSource createSQLDataSource120,responsys.ws57.SessionHeader sessionHeader121)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[24].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    createSQLDataSource120,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "createSQLDataSource")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader121!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader121 = toOM(sessionHeader121, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "createSQLDataSource")));
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
                                             responsys.ws57.CreateSQLDataSourceResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.CreateSQLDataSourceResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#getDataSourceSchema
                     * @param getDataSourceSchema123
                    
                     * @param sessionHeader124
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.GetDataSourceSchemaResponse getDataSourceSchema(

                            responsys.ws57.GetDataSourceSchema getDataSourceSchema123,responsys.ws57.SessionHeader sessionHeader124)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[25].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getDataSourceSchema123,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "getDataSourceSchema")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader124!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader124 = toOM(sessionHeader124, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "getDataSourceSchema")));
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
                                             responsys.ws57.GetDataSourceSchemaResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.GetDataSourceSchemaResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#deleteDataSource
                     * @param deleteDataSource126
                    
                     * @param sessionHeader127
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.DeleteDataSourceResponse deleteDataSource(

                            responsys.ws57.DeleteDataSource deleteDataSource126,responsys.ws57.SessionHeader sessionHeader127)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[26].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    deleteDataSource126,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "deleteDataSource")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader127!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader127 = toOM(sessionHeader127, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "deleteDataSource")));
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
                                             responsys.ws57.DeleteDataSourceResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.DeleteDataSourceResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#setCampaignProperties
                     * @param setCampaignProperties129
                    
                     * @param sessionHeader130
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.SetCampaignPropertiesResponse setCampaignProperties(

                            responsys.ws57.SetCampaignProperties setCampaignProperties129,responsys.ws57.SessionHeader sessionHeader130)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[27].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    setCampaignProperties129,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "setCampaignProperties")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader130!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader130 = toOM(sessionHeader130, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "setCampaignProperties")));
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
                                             responsys.ws57.SetCampaignPropertiesResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.SetCampaignPropertiesResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#removeDocument
                     * @param removeDocument132
                    
                     * @param sessionHeader133
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.RemoveDocumentResponse removeDocument(

                            responsys.ws57.RemoveDocument removeDocument132,responsys.ws57.SessionHeader sessionHeader133)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[28].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    removeDocument132,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "removeDocument")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader133!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader133 = toOM(sessionHeader133, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "removeDocument")));
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
                                             responsys.ws57.RemoveDocumentResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.RemoveDocumentResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#copyCampaign
                     * @param copyCampaign135
                    
                     * @param sessionHeader136
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.CopyCampaignResponse copyCampaign(

                            responsys.ws57.CopyCampaign copyCampaign135,responsys.ws57.SessionHeader sessionHeader136)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[29].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    copyCampaign135,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "copyCampaign")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader136!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader136 = toOM(sessionHeader136, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "copyCampaign")));
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
                                             responsys.ws57.CopyCampaignResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.CopyCampaignResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#addIndex
                     * @param addIndex138
                    
                     * @param sessionHeader139
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.AddIndexResponse addIndex(

                            responsys.ws57.AddIndex addIndex138,responsys.ws57.SessionHeader sessionHeader139)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[30].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    addIndex138,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "addIndex")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader139!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader139 = toOM(sessionHeader139, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "addIndex")));
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
                                             responsys.ws57.AddIndexResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.AddIndexResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#runLaunchReport
                     * @param runLaunchReport141
                    
                     * @param sessionHeader142
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.RunLaunchReportResponse runLaunchReport(

                            responsys.ws57.RunLaunchReport runLaunchReport141,responsys.ws57.SessionHeader sessionHeader142)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[31].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    runLaunchReport141,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "runLaunchReport")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader142!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader142 = toOM(sessionHeader142, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "runLaunchReport")));
                                                    addHeader(omElementsessionHeader142,env);
                                                
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
                                             responsys.ws57.RunLaunchReportResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.RunLaunchReportResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#downloadDataSourceByTimestamp
                     * @param downloadDataSourceByTimestamp144
                    
                     * @param sessionHeader145
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.DownloadDataSourceByTimestampResponse downloadDataSourceByTimestamp(

                            responsys.ws57.DownloadDataSourceByTimestamp downloadDataSourceByTimestamp144,responsys.ws57.SessionHeader sessionHeader145)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[32].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    downloadDataSourceByTimestamp144,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "downloadDataSourceByTimestamp")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader145!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader145 = toOM(sessionHeader145, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "downloadDataSourceByTimestamp")));
                                                    addHeader(omElementsessionHeader145,env);
                                                
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
                                             responsys.ws57.DownloadDataSourceByTimestampResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.DownloadDataSourceByTimestampResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#appendDataSource
                     * @param appendDataSource147
                    
                     * @param sessionHeader148
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.AppendDataSourceResponse appendDataSource(

                            responsys.ws57.AppendDataSource appendDataSource147,responsys.ws57.SessionHeader sessionHeader148)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[33].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    appendDataSource147,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "appendDataSource")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader148!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader148 = toOM(sessionHeader148, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "appendDataSource")));
                                                    addHeader(omElementsessionHeader148,env);
                                                
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
                                             responsys.ws57.AppendDataSourceResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.AppendDataSourceResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#deleteIndex
                     * @param deleteIndex150
                    
                     * @param sessionHeader151
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.DeleteIndexResponse deleteIndex(

                            responsys.ws57.DeleteIndex deleteIndex150,responsys.ws57.SessionHeader sessionHeader151)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[34].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    deleteIndex150,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "deleteIndex")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader151!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader151 = toOM(sessionHeader151, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "deleteIndex")));
                                                    addHeader(omElementsessionHeader151,env);
                                                
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
                                             responsys.ws57.DeleteIndexResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.DeleteIndexResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#deleteFolder
                     * @param deleteFolder153
                    
                     * @param sessionHeader154
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.DeleteFolderResponse deleteFolder(

                            responsys.ws57.DeleteFolder deleteFolder153,responsys.ws57.SessionHeader sessionHeader154)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[35].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    deleteFolder153,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "deleteFolder")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader154!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader154 = toOM(sessionHeader154, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "deleteFolder")));
                                                    addHeader(omElementsessionHeader154,env);
                                                
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
                                             responsys.ws57.DeleteFolderResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.DeleteFolderResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#truncateTable
                     * @param truncateTable156
                    
                     * @param sessionHeader157
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.TruncateTableResponse truncateTable(

                            responsys.ws57.TruncateTable truncateTable156,responsys.ws57.SessionHeader sessionHeader157)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[36].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    truncateTable156,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "truncateTable")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader157!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader157 = toOM(sessionHeader157, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "truncateTable")));
                                                    addHeader(omElementsessionHeader157,env);
                                                
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
                                             responsys.ws57.TruncateTableResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.TruncateTableResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#unScheduleCampaign
                     * @param unScheduleCampaign159
                    
                     * @param sessionHeader160
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.UnScheduleCampaignResponse unScheduleCampaign(

                            responsys.ws57.UnScheduleCampaign unScheduleCampaign159,responsys.ws57.SessionHeader sessionHeader160)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[37].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    unScheduleCampaign159,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "unScheduleCampaign")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader160!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader160 = toOM(sessionHeader160, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "unScheduleCampaign")));
                                                    addHeader(omElementsessionHeader160,env);
                                                
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
                                             responsys.ws57.UnScheduleCampaignResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.UnScheduleCampaignResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#getDataSourceRecordCount
                     * @param getDataSourceRecordCount162
                    
                     * @param sessionHeader163
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.GetDataSourceRecordCountResponse getDataSourceRecordCount(

                            responsys.ws57.GetDataSourceRecordCount getDataSourceRecordCount162,responsys.ws57.SessionHeader sessionHeader163)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[38].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getDataSourceRecordCount162,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "getDataSourceRecordCount")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader163!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader163 = toOM(sessionHeader163, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "getDataSourceRecordCount")));
                                                    addHeader(omElementsessionHeader163,env);
                                                
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
                                             responsys.ws57.GetDataSourceRecordCountResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.GetDataSourceRecordCountResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#exportFolder
                     * @param exportFolder165
                    
                     * @param sessionHeader166
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.ExportFolderResponse exportFolder(

                            responsys.ws57.ExportFolder exportFolder165,responsys.ws57.SessionHeader sessionHeader166)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[39].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    exportFolder165,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "exportFolder")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader166!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader166 = toOM(sessionHeader166, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "exportFolder")));
                                                    addHeader(omElementsessionHeader166,env);
                                                
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
                                             responsys.ws57.ExportFolderResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.ExportFolderResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#updateDataSource
                     * @param updateDataSource168
                    
                     * @param sessionHeader169
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.UpdateDataSourceResponse updateDataSource(

                            responsys.ws57.UpdateDataSource updateDataSource168,responsys.ws57.SessionHeader sessionHeader169)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[40].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    updateDataSource168,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "updateDataSource")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader169!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader169 = toOM(sessionHeader169, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "updateDataSource")));
                                                    addHeader(omElementsessionHeader169,env);
                                                
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
                                             responsys.ws57.UpdateDataSourceResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.UpdateDataSourceResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#runTriggeredMessageReport
                     * @param runTriggeredMessageReport171
                    
                     * @param sessionHeader172
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.RunTriggeredMessageReportResponse runTriggeredMessageReport(

                            responsys.ws57.RunTriggeredMessageReport runTriggeredMessageReport171,responsys.ws57.SessionHeader sessionHeader172)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[41].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    runTriggeredMessageReport171,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "runTriggeredMessageReport")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader172!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader172 = toOM(sessionHeader172, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "runTriggeredMessageReport")));
                                                    addHeader(omElementsessionHeader172,env);
                                                
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
                                             responsys.ws57.RunTriggeredMessageReportResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.RunTriggeredMessageReportResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#getLiveReportMetrics
                     * @param getLiveReportMetrics174
                    
                     * @param sessionHeader175
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.LiveReportMetricsResponse getLiveReportMetrics(

                            responsys.ws57.GetLiveReportMetrics getLiveReportMetrics174,responsys.ws57.SessionHeader sessionHeader175)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[42].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    getLiveReportMetrics174,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "getLiveReportMetrics")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader175!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader175 = toOM(sessionHeader175, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "getLiveReportMetrics")));
                                                    addHeader(omElementsessionHeader175,env);
                                                
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
                                             responsys.ws57.LiveReportMetricsResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.LiveReportMetricsResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#uploadDocument
                     * @param uploadDocument177
                    
                     * @param sessionHeader178
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.UploadDocumentResponse uploadDocument(

                            responsys.ws57.UploadDocument uploadDocument177,responsys.ws57.SessionHeader sessionHeader178)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[43].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    uploadDocument177,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "uploadDocument")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader178!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader178 = toOM(sessionHeader178, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "uploadDocument")));
                                                    addHeader(omElementsessionHeader178,env);
                                                
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
                                             responsys.ws57.UploadDocumentResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.UploadDocumentResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#createCampaign
                     * @param createCampaign180
                    
                     * @param sessionHeader181
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.CreateCampaignResponse createCampaign(

                            responsys.ws57.CreateCampaign createCampaign180,responsys.ws57.SessionHeader sessionHeader181)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[44].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    createCampaign180,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "createCampaign")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader181!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader181 = toOM(sessionHeader181, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "createCampaign")));
                                                    addHeader(omElementsessionHeader181,env);
                                                
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
                                             responsys.ws57.CreateCampaignResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.CreateCampaignResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#copyDataSource
                     * @param copyDataSource183
                    
                     * @param sessionHeader184
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.CopyDataSourceResponse copyDataSource(

                            responsys.ws57.CopyDataSource copyDataSource183,responsys.ws57.SessionHeader sessionHeader184)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[45].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    copyDataSource183,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "copyDataSource")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader184!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader184 = toOM(sessionHeader184, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "copyDataSource")));
                                                    addHeader(omElementsessionHeader184,env);
                                                
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
                                             responsys.ws57.CopyDataSourceResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.CopyDataSourceResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#deleteCampaign
                     * @param deleteCampaign186
                    
                     * @param sessionHeader187
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.DeleteCampaignResponse deleteCampaign(

                            responsys.ws57.DeleteCampaign deleteCampaign186,responsys.ws57.SessionHeader sessionHeader187)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[46].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    deleteCampaign186,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "deleteCampaign")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader187!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader187 = toOM(sessionHeader187, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "deleteCampaign")));
                                                    addHeader(omElementsessionHeader187,env);
                                                
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
                                             responsys.ws57.DeleteCampaignResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.DeleteCampaignResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#downloadDataSource
                     * @param downloadDataSource189
                    
                     * @param sessionHeader190
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.DownloadDataSourceResponse downloadDataSource(

                            responsys.ws57.DownloadDataSource downloadDataSource189,responsys.ws57.SessionHeader sessionHeader190)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[47].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    downloadDataSource189,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "downloadDataSource")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader190!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader190 = toOM(sessionHeader190, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "downloadDataSource")));
                                                    addHeader(omElementsessionHeader190,env);
                                                
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
                                             responsys.ws57.DownloadDataSourceResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.DownloadDataSourceResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#purgeDataSourceByTimestamp
                     * @param purgeDataSourceByTimestamp192
                    
                     * @param sessionHeader193
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.PurgeDataSourceByTimestampResponse purgeDataSourceByTimestamp(

                            responsys.ws57.PurgeDataSourceByTimestamp purgeDataSourceByTimestamp192,responsys.ws57.SessionHeader sessionHeader193)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[48].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    purgeDataSourceByTimestamp192,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "purgeDataSourceByTimestamp")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader193!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader193 = toOM(sessionHeader193, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "purgeDataSourceByTimestamp")));
                                                    addHeader(omElementsessionHeader193,env);
                                                
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
                                             responsys.ws57.PurgeDataSourceByTimestampResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.PurgeDataSourceByTimestampResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#listFolders
                     * @param listFolders195
                    
                     * @param sessionHeader196
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.ListFoldersResponse listFolders(

                            responsys.ws57.ListFolders listFolders195,responsys.ws57.SessionHeader sessionHeader196)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[49].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    listFolders195,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "listFolders")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader196!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader196 = toOM(sessionHeader196, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "listFolders")));
                                                    addHeader(omElementsessionHeader196,env);
                                                
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
                                             responsys.ws57.ListFoldersResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.ListFoldersResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
                     * @see responsys.ws57.client.ResponsysWS57#createFolder
                     * @param createFolder198
                    
                     * @param sessionHeader199
                    
                     * @throws responsys.ws57.client.RIFault : 
                     */

                    

                            public  responsys.ws57.CreateFolderResponse createFolder(

                            responsys.ws57.CreateFolder createFolder198,responsys.ws57.SessionHeader sessionHeader199)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,responsys.ws57.client.RIFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[50].getName());
              _operationClient.getOptions().setAction("\"\"");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    createFolder198,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys",
                                                    "createFolder")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader199!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader199 = toOM(sessionHeader199, optimizeContent(new javax.xml.namespace.QName("urn:ws57.responsys", "createFolder")));
                                                    addHeader(omElementsessionHeader199,env);
                                                
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
                                             responsys.ws57.CreateFolderResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (responsys.ws57.CreateFolderResponse)object;
                                   
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
                        
                        if (ex instanceof responsys.ws57.client.RIFault){
                          throw (responsys.ws57.client.RIFault)ex;
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
     //http://ws4.responsys.net:80/webservices57/services/ResponsysWS57
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.Login param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.Login.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.LoginResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.LoginResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.fault.FaultException param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.fault.FaultException.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.ImportFolder param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.ImportFolder.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.ImportFolderResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.ImportFolderResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.SessionHeader param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.SessionHeader.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.GetServerTimestamp param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.GetServerTimestamp.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.GetServerTimestampResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.GetServerTimestampResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.PurgeDataSource param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.PurgeDataSource.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.PurgeDataSourceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.PurgeDataSourceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.CampaignsInDatamart param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.CampaignsInDatamart.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.CampaignsInDatamartResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.CampaignsInDatamartResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.GetReportOptions param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.GetReportOptions.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.GetReportOptionsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.GetReportOptionsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.CopyDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.CopyDocument.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.CopyDocumentResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.CopyDocumentResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.Cancel param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.Cancel.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.CancelResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.CancelResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.UpdateDataSourceUsingMultipleColumns param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.UpdateDataSourceUsingMultipleColumns.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.UpdateDataSourceUsingMultipleColumnsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.UpdateDataSourceUsingMultipleColumnsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.ListIndexes param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.ListIndexes.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.ListIndexesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.ListIndexesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.ShowDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.ShowDocument.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.ShowDocumentResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.ShowDocumentResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.GetCampaignProperties param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.GetCampaignProperties.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.GetCampaignPropertiesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.GetCampaignPropertiesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.Logout param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.Logout.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.LogoutResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.LogoutResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.ScrubDataSource param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.ScrubDataSource.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.ScrubDataSourceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.ScrubDataSourceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.ListFolderContents param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.ListFolderContents.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.ListFolderContentsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.ListFolderContentsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.ListFolderObjects param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.ListFolderObjects.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.ListFolderObjectsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.ListFolderObjectsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.GetCampaignStatus param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.GetCampaignStatus.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.GetCampaignStatusResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.GetCampaignStatusResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.TriggerFormRules param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.TriggerFormRules.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.TriggerFormRulesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.TriggerFormRulesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.LaunchCampaign param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.LaunchCampaign.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.LaunchCampaignResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.LaunchCampaignResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.CheckResult param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.CheckResult.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.CheckResultResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.CheckResultResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.CreateDataSource param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.CreateDataSource.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.CreateDataSourceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.CreateDataSourceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.LaunchesInDatamart param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.LaunchesInDatamart.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.LaunchesInDatamartResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.LaunchesInDatamartResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.StopCampaign param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.StopCampaign.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.StopCampaignResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.StopCampaignResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.ListTablesForCampaign param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.ListTablesForCampaign.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.ListTablesForCampaignResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.ListTablesForCampaignResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.CreateSQLDataSource param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.CreateSQLDataSource.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.CreateSQLDataSourceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.CreateSQLDataSourceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.GetDataSourceSchema param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.GetDataSourceSchema.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.GetDataSourceSchemaResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.GetDataSourceSchemaResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.DeleteDataSource param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.DeleteDataSource.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.DeleteDataSourceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.DeleteDataSourceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.SetCampaignProperties param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.SetCampaignProperties.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.SetCampaignPropertiesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.SetCampaignPropertiesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.RemoveDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.RemoveDocument.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.RemoveDocumentResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.RemoveDocumentResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.CopyCampaign param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.CopyCampaign.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.CopyCampaignResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.CopyCampaignResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.AddIndex param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.AddIndex.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.AddIndexResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.AddIndexResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.RunLaunchReport param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.RunLaunchReport.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.RunLaunchReportResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.RunLaunchReportResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.DownloadDataSourceByTimestamp param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.DownloadDataSourceByTimestamp.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.DownloadDataSourceByTimestampResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.DownloadDataSourceByTimestampResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.AppendDataSource param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.AppendDataSource.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.AppendDataSourceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.AppendDataSourceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.DeleteIndex param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.DeleteIndex.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.DeleteIndexResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.DeleteIndexResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.DeleteFolder param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.DeleteFolder.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.DeleteFolderResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.DeleteFolderResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.TruncateTable param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.TruncateTable.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.TruncateTableResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.TruncateTableResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.UnScheduleCampaign param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.UnScheduleCampaign.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.UnScheduleCampaignResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.UnScheduleCampaignResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.GetDataSourceRecordCount param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.GetDataSourceRecordCount.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.GetDataSourceRecordCountResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.GetDataSourceRecordCountResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.ExportFolder param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.ExportFolder.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.ExportFolderResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.ExportFolderResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.UpdateDataSource param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.UpdateDataSource.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.UpdateDataSourceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.UpdateDataSourceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.RunTriggeredMessageReport param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.RunTriggeredMessageReport.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.RunTriggeredMessageReportResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.RunTriggeredMessageReportResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.GetLiveReportMetrics param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.GetLiveReportMetrics.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.LiveReportMetricsResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.LiveReportMetricsResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.UploadDocument param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.UploadDocument.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.UploadDocumentResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.UploadDocumentResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.CreateCampaign param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.CreateCampaign.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.CreateCampaignResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.CreateCampaignResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.CopyDataSource param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.CopyDataSource.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.CopyDataSourceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.CopyDataSourceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.DeleteCampaign param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.DeleteCampaign.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.DeleteCampaignResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.DeleteCampaignResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.DownloadDataSource param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.DownloadDataSource.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.DownloadDataSourceResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.DownloadDataSourceResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.PurgeDataSourceByTimestamp param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.PurgeDataSourceByTimestamp.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.PurgeDataSourceByTimestampResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.PurgeDataSourceByTimestampResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.ListFolders param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.ListFolders.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.ListFoldersResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.ListFoldersResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.CreateFolder param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.CreateFolder.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(responsys.ws57.CreateFolderResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(responsys.ws57.CreateFolderResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.Login param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.Login.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.ImportFolder param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.ImportFolder.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.GetServerTimestamp param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.GetServerTimestamp.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.PurgeDataSource param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.PurgeDataSource.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.CampaignsInDatamart param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.CampaignsInDatamart.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.GetReportOptions param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.GetReportOptions.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.CopyDocument param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.CopyDocument.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.Cancel param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.Cancel.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.UpdateDataSourceUsingMultipleColumns param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.UpdateDataSourceUsingMultipleColumns.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.ListIndexes param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.ListIndexes.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.ShowDocument param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.ShowDocument.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.GetCampaignProperties param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.GetCampaignProperties.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.Logout param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.Logout.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.ScrubDataSource param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.ScrubDataSource.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.ListFolderContents param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.ListFolderContents.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.ListFolderObjects param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.ListFolderObjects.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.GetCampaignStatus param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.GetCampaignStatus.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.TriggerFormRules param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.TriggerFormRules.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.LaunchCampaign param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.LaunchCampaign.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.CheckResult param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.CheckResult.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.CreateDataSource param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.CreateDataSource.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.LaunchesInDatamart param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.LaunchesInDatamart.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.StopCampaign param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.StopCampaign.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.ListTablesForCampaign param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.ListTablesForCampaign.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.CreateSQLDataSource param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.CreateSQLDataSource.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.GetDataSourceSchema param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.GetDataSourceSchema.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.DeleteDataSource param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.DeleteDataSource.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.SetCampaignProperties param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.SetCampaignProperties.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.RemoveDocument param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.RemoveDocument.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.CopyCampaign param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.CopyCampaign.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.AddIndex param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.AddIndex.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.RunLaunchReport param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.RunLaunchReport.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.DownloadDataSourceByTimestamp param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.DownloadDataSourceByTimestamp.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.AppendDataSource param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.AppendDataSource.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.DeleteIndex param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.DeleteIndex.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.DeleteFolder param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.DeleteFolder.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.TruncateTable param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.TruncateTable.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.UnScheduleCampaign param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.UnScheduleCampaign.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.GetDataSourceRecordCount param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.GetDataSourceRecordCount.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.ExportFolder param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.ExportFolder.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.UpdateDataSource param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.UpdateDataSource.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.RunTriggeredMessageReport param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.RunTriggeredMessageReport.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.GetLiveReportMetrics param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.GetLiveReportMetrics.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.UploadDocument param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.UploadDocument.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.CreateCampaign param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.CreateCampaign.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.CopyDataSource param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.CopyDataSource.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.DeleteCampaign param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.DeleteCampaign.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.DownloadDataSource param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.DownloadDataSource.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.PurgeDataSourceByTimestamp param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.PurgeDataSourceByTimestamp.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.ListFolders param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.ListFolders.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, responsys.ws57.CreateFolder param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(responsys.ws57.CreateFolder.MY_QNAME,factory));
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
        
                if (responsys.ws57.Login.class.equals(type)){
                
                           return responsys.ws57.Login.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.LoginResponse.class.equals(type)){
                
                           return responsys.ws57.LoginResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.ImportFolder.class.equals(type)){
                
                           return responsys.ws57.ImportFolder.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.ImportFolderResponse.class.equals(type)){
                
                           return responsys.ws57.ImportFolderResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.GetServerTimestamp.class.equals(type)){
                
                           return responsys.ws57.GetServerTimestamp.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.GetServerTimestampResponse.class.equals(type)){
                
                           return responsys.ws57.GetServerTimestampResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.PurgeDataSource.class.equals(type)){
                
                           return responsys.ws57.PurgeDataSource.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.PurgeDataSourceResponse.class.equals(type)){
                
                           return responsys.ws57.PurgeDataSourceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.CampaignsInDatamart.class.equals(type)){
                
                           return responsys.ws57.CampaignsInDatamart.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.CampaignsInDatamartResponse.class.equals(type)){
                
                           return responsys.ws57.CampaignsInDatamartResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.GetReportOptions.class.equals(type)){
                
                           return responsys.ws57.GetReportOptions.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.GetReportOptionsResponse.class.equals(type)){
                
                           return responsys.ws57.GetReportOptionsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.CopyDocument.class.equals(type)){
                
                           return responsys.ws57.CopyDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.CopyDocumentResponse.class.equals(type)){
                
                           return responsys.ws57.CopyDocumentResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.Cancel.class.equals(type)){
                
                           return responsys.ws57.Cancel.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.CancelResponse.class.equals(type)){
                
                           return responsys.ws57.CancelResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.UpdateDataSourceUsingMultipleColumns.class.equals(type)){
                
                           return responsys.ws57.UpdateDataSourceUsingMultipleColumns.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.UpdateDataSourceUsingMultipleColumnsResponse.class.equals(type)){
                
                           return responsys.ws57.UpdateDataSourceUsingMultipleColumnsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.ListIndexes.class.equals(type)){
                
                           return responsys.ws57.ListIndexes.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.ListIndexesResponse.class.equals(type)){
                
                           return responsys.ws57.ListIndexesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.ShowDocument.class.equals(type)){
                
                           return responsys.ws57.ShowDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.ShowDocumentResponse.class.equals(type)){
                
                           return responsys.ws57.ShowDocumentResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.GetCampaignProperties.class.equals(type)){
                
                           return responsys.ws57.GetCampaignProperties.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.GetCampaignPropertiesResponse.class.equals(type)){
                
                           return responsys.ws57.GetCampaignPropertiesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.Logout.class.equals(type)){
                
                           return responsys.ws57.Logout.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.LogoutResponse.class.equals(type)){
                
                           return responsys.ws57.LogoutResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.ScrubDataSource.class.equals(type)){
                
                           return responsys.ws57.ScrubDataSource.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.ScrubDataSourceResponse.class.equals(type)){
                
                           return responsys.ws57.ScrubDataSourceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.ListFolderContents.class.equals(type)){
                
                           return responsys.ws57.ListFolderContents.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.ListFolderContentsResponse.class.equals(type)){
                
                           return responsys.ws57.ListFolderContentsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.ListFolderObjects.class.equals(type)){
                
                           return responsys.ws57.ListFolderObjects.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.ListFolderObjectsResponse.class.equals(type)){
                
                           return responsys.ws57.ListFolderObjectsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.GetCampaignStatus.class.equals(type)){
                
                           return responsys.ws57.GetCampaignStatus.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.GetCampaignStatusResponse.class.equals(type)){
                
                           return responsys.ws57.GetCampaignStatusResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.TriggerFormRules.class.equals(type)){
                
                           return responsys.ws57.TriggerFormRules.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.TriggerFormRulesResponse.class.equals(type)){
                
                           return responsys.ws57.TriggerFormRulesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.LaunchCampaign.class.equals(type)){
                
                           return responsys.ws57.LaunchCampaign.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.LaunchCampaignResponse.class.equals(type)){
                
                           return responsys.ws57.LaunchCampaignResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.CheckResult.class.equals(type)){
                
                           return responsys.ws57.CheckResult.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.CheckResultResponse.class.equals(type)){
                
                           return responsys.ws57.CheckResultResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.CreateDataSource.class.equals(type)){
                
                           return responsys.ws57.CreateDataSource.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.CreateDataSourceResponse.class.equals(type)){
                
                           return responsys.ws57.CreateDataSourceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.LaunchesInDatamart.class.equals(type)){
                
                           return responsys.ws57.LaunchesInDatamart.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.LaunchesInDatamartResponse.class.equals(type)){
                
                           return responsys.ws57.LaunchesInDatamartResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.StopCampaign.class.equals(type)){
                
                           return responsys.ws57.StopCampaign.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.StopCampaignResponse.class.equals(type)){
                
                           return responsys.ws57.StopCampaignResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.ListTablesForCampaign.class.equals(type)){
                
                           return responsys.ws57.ListTablesForCampaign.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.ListTablesForCampaignResponse.class.equals(type)){
                
                           return responsys.ws57.ListTablesForCampaignResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.CreateSQLDataSource.class.equals(type)){
                
                           return responsys.ws57.CreateSQLDataSource.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.CreateSQLDataSourceResponse.class.equals(type)){
                
                           return responsys.ws57.CreateSQLDataSourceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.GetDataSourceSchema.class.equals(type)){
                
                           return responsys.ws57.GetDataSourceSchema.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.GetDataSourceSchemaResponse.class.equals(type)){
                
                           return responsys.ws57.GetDataSourceSchemaResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.DeleteDataSource.class.equals(type)){
                
                           return responsys.ws57.DeleteDataSource.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.DeleteDataSourceResponse.class.equals(type)){
                
                           return responsys.ws57.DeleteDataSourceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SetCampaignProperties.class.equals(type)){
                
                           return responsys.ws57.SetCampaignProperties.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SetCampaignPropertiesResponse.class.equals(type)){
                
                           return responsys.ws57.SetCampaignPropertiesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.RemoveDocument.class.equals(type)){
                
                           return responsys.ws57.RemoveDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.RemoveDocumentResponse.class.equals(type)){
                
                           return responsys.ws57.RemoveDocumentResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.CopyCampaign.class.equals(type)){
                
                           return responsys.ws57.CopyCampaign.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.CopyCampaignResponse.class.equals(type)){
                
                           return responsys.ws57.CopyCampaignResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.AddIndex.class.equals(type)){
                
                           return responsys.ws57.AddIndex.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.AddIndexResponse.class.equals(type)){
                
                           return responsys.ws57.AddIndexResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.RunLaunchReport.class.equals(type)){
                
                           return responsys.ws57.RunLaunchReport.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.RunLaunchReportResponse.class.equals(type)){
                
                           return responsys.ws57.RunLaunchReportResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.DownloadDataSourceByTimestamp.class.equals(type)){
                
                           return responsys.ws57.DownloadDataSourceByTimestamp.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.DownloadDataSourceByTimestampResponse.class.equals(type)){
                
                           return responsys.ws57.DownloadDataSourceByTimestampResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.AppendDataSource.class.equals(type)){
                
                           return responsys.ws57.AppendDataSource.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.AppendDataSourceResponse.class.equals(type)){
                
                           return responsys.ws57.AppendDataSourceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.DeleteIndex.class.equals(type)){
                
                           return responsys.ws57.DeleteIndex.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.DeleteIndexResponse.class.equals(type)){
                
                           return responsys.ws57.DeleteIndexResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.DeleteFolder.class.equals(type)){
                
                           return responsys.ws57.DeleteFolder.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.DeleteFolderResponse.class.equals(type)){
                
                           return responsys.ws57.DeleteFolderResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.TruncateTable.class.equals(type)){
                
                           return responsys.ws57.TruncateTable.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.TruncateTableResponse.class.equals(type)){
                
                           return responsys.ws57.TruncateTableResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.UnScheduleCampaign.class.equals(type)){
                
                           return responsys.ws57.UnScheduleCampaign.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.UnScheduleCampaignResponse.class.equals(type)){
                
                           return responsys.ws57.UnScheduleCampaignResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.GetDataSourceRecordCount.class.equals(type)){
                
                           return responsys.ws57.GetDataSourceRecordCount.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.GetDataSourceRecordCountResponse.class.equals(type)){
                
                           return responsys.ws57.GetDataSourceRecordCountResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.ExportFolder.class.equals(type)){
                
                           return responsys.ws57.ExportFolder.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.ExportFolderResponse.class.equals(type)){
                
                           return responsys.ws57.ExportFolderResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.UpdateDataSource.class.equals(type)){
                
                           return responsys.ws57.UpdateDataSource.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.UpdateDataSourceResponse.class.equals(type)){
                
                           return responsys.ws57.UpdateDataSourceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.RunTriggeredMessageReport.class.equals(type)){
                
                           return responsys.ws57.RunTriggeredMessageReport.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.RunTriggeredMessageReportResponse.class.equals(type)){
                
                           return responsys.ws57.RunTriggeredMessageReportResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.GetLiveReportMetrics.class.equals(type)){
                
                           return responsys.ws57.GetLiveReportMetrics.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.LiveReportMetricsResponse.class.equals(type)){
                
                           return responsys.ws57.LiveReportMetricsResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.UploadDocument.class.equals(type)){
                
                           return responsys.ws57.UploadDocument.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.UploadDocumentResponse.class.equals(type)){
                
                           return responsys.ws57.UploadDocumentResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.CreateCampaign.class.equals(type)){
                
                           return responsys.ws57.CreateCampaign.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.CreateCampaignResponse.class.equals(type)){
                
                           return responsys.ws57.CreateCampaignResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.CopyDataSource.class.equals(type)){
                
                           return responsys.ws57.CopyDataSource.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.CopyDataSourceResponse.class.equals(type)){
                
                           return responsys.ws57.CopyDataSourceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.DeleteCampaign.class.equals(type)){
                
                           return responsys.ws57.DeleteCampaign.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.DeleteCampaignResponse.class.equals(type)){
                
                           return responsys.ws57.DeleteCampaignResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.DownloadDataSource.class.equals(type)){
                
                           return responsys.ws57.DownloadDataSource.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.DownloadDataSourceResponse.class.equals(type)){
                
                           return responsys.ws57.DownloadDataSourceResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.PurgeDataSourceByTimestamp.class.equals(type)){
                
                           return responsys.ws57.PurgeDataSourceByTimestamp.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.PurgeDataSourceByTimestampResponse.class.equals(type)){
                
                           return responsys.ws57.PurgeDataSourceByTimestampResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.ListFolders.class.equals(type)){
                
                           return responsys.ws57.ListFolders.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.ListFoldersResponse.class.equals(type)){
                
                           return responsys.ws57.ListFoldersResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.CreateFolder.class.equals(type)){
                
                           return responsys.ws57.CreateFolder.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.CreateFolderResponse.class.equals(type)){
                
                           return responsys.ws57.CreateFolderResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.fault.FaultException.class.equals(type)){
                
                           return responsys.ws57.fault.FaultException.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (responsys.ws57.SessionHeader.class.equals(type)){
                
                           return responsys.ws57.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    
   }
   