
/**
 * TriggeredMessageWSStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */
        package com.rsys.tmws.client;

        

        /*
        *  TriggeredMessageWSStub java implementation
        */

        
        public class TriggeredMessageWSStub extends org.apache.axis2.client.Stub
        implements TriggeredMessageWS{
        protected org.apache.axis2.description.AxisOperation[] _operations;

        //hashmaps to keep the fault mapping
        private java.util.HashMap faultExceptionNameMap = new java.util.HashMap();
        private java.util.HashMap faultExceptionClassNameMap = new java.util.HashMap();
        private java.util.HashMap faultMessageMap = new java.util.HashMap();

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
     _service = new org.apache.axis2.description.AxisService("TriggeredMessageWS" + getUniqueSuffix());
     addAnonymousOperations();

        //creating the operations
        org.apache.axis2.description.AxisOperation __operation;

        _operations = new org.apache.axis2.description.AxisOperation[4];
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:tmws.rsys.com", "login"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[0]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:tmws.rsys.com", "sendTriggeredMessages"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[1]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:tmws.rsys.com", "logout"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[2]=__operation;
            
        
                   __operation = new org.apache.axis2.description.OutInAxisOperation();
                

            __operation.setName(new javax.xml.namespace.QName("urn:tmws.rsys.com", "checkTriggeredMessages"));
	    _service.addOperation(__operation);
	    

	    
	    
            _operations[3]=__operation;
            
        
        }

    //populates the faults
    private void populateFaults(){
         
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","AccountFault"),"com.rsys.tmws.client.AccountFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","AccountFault"),"com.rsys.tmws.client.AccountFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","AccountFault"),"com.rsys.tmws.fault.AccountFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","UnexpectedErrorFault"),"com.rsys.tmws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","UnexpectedErrorFault"),"com.rsys.tmws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","UnexpectedErrorFault"),"com.rsys.tmws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","UnexpectedErrorFault"),"com.rsys.tmws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","UnexpectedErrorFault"),"com.rsys.tmws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","UnexpectedErrorFault"),"com.rsys.tmws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","TriggeredMessageFault"),"com.rsys.tmws.client.TriggeredMessageFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","TriggeredMessageFault"),"com.rsys.tmws.client.TriggeredMessageFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","TriggeredMessageFault"),"com.rsys.tmws.fault.TriggeredMessageFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","AccountFault"),"com.rsys.tmws.client.AccountFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","AccountFault"),"com.rsys.tmws.client.AccountFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","AccountFault"),"com.rsys.tmws.fault.AccountFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","UnexpectedErrorFault"),"com.rsys.tmws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","UnexpectedErrorFault"),"com.rsys.tmws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","UnexpectedErrorFault"),"com.rsys.tmws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","UnexpectedErrorFault"),"com.rsys.tmws.client.UnexpectedErrorFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","UnexpectedErrorFault"),"com.rsys.tmws.client.UnexpectedErrorFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","UnexpectedErrorFault"),"com.rsys.tmws.fault.UnexpectedErrorFault");
           
              faultExceptionNameMap.put( new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","TriggeredMessageFault"),"com.rsys.tmws.client.TriggeredMessageFault");
              faultExceptionClassNameMap.put(new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","TriggeredMessageFault"),"com.rsys.tmws.client.TriggeredMessageFault");
              faultMessageMap.put( new javax.xml.namespace.QName("urn:fault.tmws.rsys.com","TriggeredMessageFault"),"com.rsys.tmws.fault.TriggeredMessageFault");
           


    }

    /**
      *Constructor that takes in a configContext
      */

    public TriggeredMessageWSStub(org.apache.axis2.context.ConfigurationContext configurationContext,
       java.lang.String targetEndpoint)
       throws org.apache.axis2.AxisFault {
         this(configurationContext,targetEndpoint,false);
   }


   /**
     * Constructor that takes in a configContext  and useseperate listner
     */
   public TriggeredMessageWSStub(org.apache.axis2.context.ConfigurationContext configurationContext,
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
    public TriggeredMessageWSStub(org.apache.axis2.context.ConfigurationContext configurationContext) throws org.apache.axis2.AxisFault {
        
                    this(configurationContext,"http://rtm4.responsys.net:80/tmws/services/TriggeredMessageWS" );
                
    }

    /**
     * Default Constructor
     */
    public TriggeredMessageWSStub() throws org.apache.axis2.AxisFault {
        
                    this("http://rtm4.responsys.net:80/tmws/services/TriggeredMessageWS" );
                
    }

    /**
     * Constructor taking the target endpoint
     */
    public TriggeredMessageWSStub(java.lang.String targetEndpoint) throws org.apache.axis2.AxisFault {
        this(null,targetEndpoint);
    }



        
                    /**
                     * Auto generated method signature
                     * Login to the Triggered Message Web Service.
                     * @see com.rsys.tmws.client.TriggeredMessageWS#login
                     * @param login2
                    
                     * @throws com.rsys.tmws.client.AccountFault : 
                     * @throws com.rsys.tmws.client.UnexpectedErrorFault : 
                     */

                    

                            public  com.rsys.tmws.LoginResponse login(

                            com.rsys.tmws.Login login2)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.tmws.client.AccountFault
                        ,com.rsys.tmws.client.UnexpectedErrorFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[0].getName());
              _operationClient.getOptions().setAction("urn:tmws.rsys.com:TriggeredMessagePort:loginRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    login2,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:tmws.rsys.com",
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
                                             com.rsys.tmws.LoginResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.tmws.LoginResponse)object;
                                   
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
                        
                        if (ex instanceof com.rsys.tmws.client.AccountFault){
                          throw (com.rsys.tmws.client.AccountFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.tmws.client.UnexpectedErrorFault){
                          throw (com.rsys.tmws.client.UnexpectedErrorFault)ex;
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
                     * Send Triggered Message to one or more recipients.
                     * @see com.rsys.tmws.client.TriggeredMessageWS#sendTriggeredMessages
                     * @param sendTriggeredMessages4
                    
                     * @param sessionHeader5
                    
                     * @throws com.rsys.tmws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.tmws.client.TriggeredMessageFault : 
                     */

                    

                            public  com.rsys.tmws.SendTriggeredMessagesResponse sendTriggeredMessages(

                            com.rsys.tmws.SendTriggeredMessages sendTriggeredMessages4,com.rsys.tmws.SessionHeader sessionHeader5)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.tmws.client.UnexpectedErrorFault
                        ,com.rsys.tmws.client.TriggeredMessageFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[1].getName());
              _operationClient.getOptions().setAction("urn:tmws.rsys.com:TriggeredMessagePort:sendTriggeredMessagesRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    sendTriggeredMessages4,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:tmws.rsys.com",
                                                    "sendTriggeredMessages")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader5!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader5 = toOM(sessionHeader5, optimizeContent(new javax.xml.namespace.QName("urn:tmws.rsys.com", "sendTriggeredMessages")));
                                                    addHeader(omElementsessionHeader5,env);
                                                
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
                                             com.rsys.tmws.SendTriggeredMessagesResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.tmws.SendTriggeredMessagesResponse)object;
                                   
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
                        
                        if (ex instanceof com.rsys.tmws.client.UnexpectedErrorFault){
                          throw (com.rsys.tmws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.tmws.client.TriggeredMessageFault){
                          throw (com.rsys.tmws.client.TriggeredMessageFault)ex;
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
                     * Logout of the Triggered Message Web Service.
                     * @see com.rsys.tmws.client.TriggeredMessageWS#logout
                     * @param logout7
                    
                     * @param sessionHeader8
                    
                     * @throws com.rsys.tmws.client.AccountFault : 
                     * @throws com.rsys.tmws.client.UnexpectedErrorFault : 
                     */

                    

                            public  com.rsys.tmws.LogoutResponse logout(

                            com.rsys.tmws.Logout logout7,com.rsys.tmws.SessionHeader sessionHeader8)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.tmws.client.AccountFault
                        ,com.rsys.tmws.client.UnexpectedErrorFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[2].getName());
              _operationClient.getOptions().setAction("urn:tmws.rsys.com:TriggeredMessagePort:logoutRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    logout7,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:tmws.rsys.com",
                                                    "logout")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader8!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader8 = toOM(sessionHeader8, optimizeContent(new javax.xml.namespace.QName("urn:tmws.rsys.com", "logout")));
                                                    addHeader(omElementsessionHeader8,env);
                                                
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
                                             com.rsys.tmws.LogoutResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.tmws.LogoutResponse)object;
                                   
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
                        
                        if (ex instanceof com.rsys.tmws.client.AccountFault){
                          throw (com.rsys.tmws.client.AccountFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.tmws.client.UnexpectedErrorFault){
                          throw (com.rsys.tmws.client.UnexpectedErrorFault)ex;
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
                     * Check Triggered Message Status for one or more trigger message IDs.
                     * @see com.rsys.tmws.client.TriggeredMessageWS#checkTriggeredMessages
                     * @param checkTriggeredMessages10
                    
                     * @param sessionHeader11
                    
                     * @throws com.rsys.tmws.client.UnexpectedErrorFault : 
                     * @throws com.rsys.tmws.client.TriggeredMessageFault : 
                     */

                    

                            public  com.rsys.tmws.CheckTriggeredMessagesResponse checkTriggeredMessages(

                            com.rsys.tmws.CheckTriggeredMessages checkTriggeredMessages10,com.rsys.tmws.SessionHeader sessionHeader11)
                        

                    throws java.rmi.RemoteException
                    
                    
                        ,com.rsys.tmws.client.UnexpectedErrorFault
                        ,com.rsys.tmws.client.TriggeredMessageFault{
              org.apache.axis2.context.MessageContext _messageContext = null;
              try{
               org.apache.axis2.client.OperationClient _operationClient = _serviceClient.createClient(_operations[3].getName());
              _operationClient.getOptions().setAction("urn:tmws.rsys.com:TriggeredMessagePort:checkTriggeredMessagesRequest");
              _operationClient.getOptions().setExceptionToBeThrownOnSOAPFault(true);

              
              
                  addPropertyToOperationClient(_operationClient,org.apache.axis2.description.WSDL2Constants.ATTR_WHTTP_QUERY_PARAMETER_SEPARATOR,"&");
              

              // create a message context
              _messageContext = new org.apache.axis2.context.MessageContext();

              

              // create SOAP envelope with that payload
              org.apache.axiom.soap.SOAPEnvelope env = null;
                    
                                                    
                                                    env = toEnvelope(getFactory(_operationClient.getOptions().getSoapVersionURI()),
                                                    checkTriggeredMessages10,
                                                    optimizeContent(new javax.xml.namespace.QName("urn:tmws.rsys.com",
                                                    "checkTriggeredMessages")));
                                                
                                               env.build();
                                    
                                        // add the children only if the parameter is not null
                                        if (sessionHeader11!=null){
                                            
                                                    org.apache.axiom.om.OMElement omElementsessionHeader11 = toOM(sessionHeader11, optimizeContent(new javax.xml.namespace.QName("urn:tmws.rsys.com", "checkTriggeredMessages")));
                                                    addHeader(omElementsessionHeader11,env);
                                                
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
                                             com.rsys.tmws.CheckTriggeredMessagesResponse.class,
                                              getEnvelopeNamespaces(_returnEnv));

                               
                                        return (com.rsys.tmws.CheckTriggeredMessagesResponse)object;
                                   
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
                        
                        if (ex instanceof com.rsys.tmws.client.UnexpectedErrorFault){
                          throw (com.rsys.tmws.client.UnexpectedErrorFault)ex;
                        }
                        
                        if (ex instanceof com.rsys.tmws.client.TriggeredMessageFault){
                          throw (com.rsys.tmws.client.TriggeredMessageFault)ex;
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
     //http://rtm4.responsys.net:80/tmws/services/TriggeredMessageWS
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.tmws.Login param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.tmws.Login.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.tmws.LoginResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.tmws.LoginResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.tmws.fault.AccountFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.tmws.fault.AccountFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.tmws.fault.UnexpectedErrorFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.tmws.fault.UnexpectedErrorFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.tmws.SendTriggeredMessages param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.tmws.SendTriggeredMessages.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.tmws.SendTriggeredMessagesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.tmws.SendTriggeredMessagesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.tmws.fault.TriggeredMessageFault param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.tmws.fault.TriggeredMessageFault.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.tmws.SessionHeader param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.tmws.SessionHeader.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.tmws.Logout param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.tmws.Logout.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.tmws.LogoutResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.tmws.LogoutResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.tmws.CheckTriggeredMessages param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.tmws.CheckTriggeredMessages.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
            private  org.apache.axiom.om.OMElement  toOM(com.rsys.tmws.CheckTriggeredMessagesResponse param, boolean optimizeContent)
            throws org.apache.axis2.AxisFault {

            
                        try{
                             return param.getOMElement(com.rsys.tmws.CheckTriggeredMessagesResponse.MY_QNAME,
                                          org.apache.axiom.om.OMAbstractFactory.getOMFactory());
                        } catch(org.apache.axis2.databinding.ADBException e){
                            throw org.apache.axis2.AxisFault.makeFault(e);
                        }
                    

            }
        
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.tmws.Login param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.tmws.Login.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.tmws.SendTriggeredMessages param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.tmws.SendTriggeredMessages.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.tmws.Logout param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.tmws.Logout.MY_QNAME,factory));
                                                            return emptyEnvelope;
                                                        } catch(org.apache.axis2.databinding.ADBException e){
                                                            throw org.apache.axis2.AxisFault.makeFault(e);
                                                        }
                                                

                                        }
                                
                             
                             /* methods to provide back word compatibility */

                             
                                    
                                        private  org.apache.axiom.soap.SOAPEnvelope toEnvelope(org.apache.axiom.soap.SOAPFactory factory, com.rsys.tmws.CheckTriggeredMessages param, boolean optimizeContent)
                                        throws org.apache.axis2.AxisFault{

                                             
                                                    try{

                                                            org.apache.axiom.soap.SOAPEnvelope emptyEnvelope = factory.getDefaultEnvelope();
                                                            emptyEnvelope.getBody().addChild(param.getOMElement(com.rsys.tmws.CheckTriggeredMessages.MY_QNAME,factory));
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
        
                if (com.rsys.tmws.Login.class.equals(type)){
                
                           return com.rsys.tmws.Login.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.tmws.LoginResponse.class.equals(type)){
                
                           return com.rsys.tmws.LoginResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.tmws.fault.AccountFault.class.equals(type)){
                
                           return com.rsys.tmws.fault.AccountFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.tmws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.tmws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.tmws.SendTriggeredMessages.class.equals(type)){
                
                           return com.rsys.tmws.SendTriggeredMessages.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.tmws.SendTriggeredMessagesResponse.class.equals(type)){
                
                           return com.rsys.tmws.SendTriggeredMessagesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.tmws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.tmws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.tmws.fault.TriggeredMessageFault.class.equals(type)){
                
                           return com.rsys.tmws.fault.TriggeredMessageFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.tmws.SessionHeader.class.equals(type)){
                
                           return com.rsys.tmws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.tmws.Logout.class.equals(type)){
                
                           return com.rsys.tmws.Logout.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.tmws.LogoutResponse.class.equals(type)){
                
                           return com.rsys.tmws.LogoutResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.tmws.fault.AccountFault.class.equals(type)){
                
                           return com.rsys.tmws.fault.AccountFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.tmws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.tmws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.tmws.SessionHeader.class.equals(type)){
                
                           return com.rsys.tmws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.tmws.CheckTriggeredMessages.class.equals(type)){
                
                           return com.rsys.tmws.CheckTriggeredMessages.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.tmws.CheckTriggeredMessagesResponse.class.equals(type)){
                
                           return com.rsys.tmws.CheckTriggeredMessagesResponse.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.tmws.fault.UnexpectedErrorFault.class.equals(type)){
                
                           return com.rsys.tmws.fault.UnexpectedErrorFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.tmws.fault.TriggeredMessageFault.class.equals(type)){
                
                           return com.rsys.tmws.fault.TriggeredMessageFault.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
                if (com.rsys.tmws.SessionHeader.class.equals(type)){
                
                           return com.rsys.tmws.SessionHeader.Factory.parse(param.getXMLStreamReaderWithoutCaching());
                    

                }
           
        } catch (java.lang.Exception e) {
        throw org.apache.axis2.AxisFault.makeFault(e);
        }
           return null;
        }



    
   }
   