
/**
 * UnexpectedErrorFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */

package com.rsys.ws.client;

public class UnexpectedErrorFault extends java.lang.Exception{
    
    private com.rsys.ws.fault.UnexpectedErrorFault faultMessage;

    
        public UnexpectedErrorFault() {
            super("UnexpectedErrorFault");
        }

        public UnexpectedErrorFault(java.lang.String s) {
           super(s);
        }

        public UnexpectedErrorFault(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public UnexpectedErrorFault(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.rsys.ws.fault.UnexpectedErrorFault msg){
       faultMessage = msg;
    }
    
    public com.rsys.ws.fault.UnexpectedErrorFault getFaultMessage(){
       return faultMessage;
    }
}
    