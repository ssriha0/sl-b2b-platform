
/**
 * DocumentFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */

package com.rsys.ws.client;

public class DocumentFault extends java.lang.Exception{
    
    private com.rsys.ws.fault.DocumentFault faultMessage;

    
        public DocumentFault() {
            super("DocumentFault");
        }

        public DocumentFault(java.lang.String s) {
           super(s);
        }

        public DocumentFault(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public DocumentFault(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.rsys.ws.fault.DocumentFault msg){
       faultMessage = msg;
    }
    
    public com.rsys.ws.fault.DocumentFault getFaultMessage(){
       return faultMessage;
    }
}
    