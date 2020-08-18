
/**
 * UnrecoverableErrorFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */

package com.rsys.ws.client;

public class UnrecoverableErrorFault extends java.lang.Exception{
    
    private com.rsys.ws.fault.UnrecoverableErrorFault faultMessage;

    
        public UnrecoverableErrorFault() {
            super("UnrecoverableErrorFault");
        }

        public UnrecoverableErrorFault(java.lang.String s) {
           super(s);
        }

        public UnrecoverableErrorFault(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public UnrecoverableErrorFault(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.rsys.ws.fault.UnrecoverableErrorFault msg){
       faultMessage = msg;
    }
    
    public com.rsys.ws.fault.UnrecoverableErrorFault getFaultMessage(){
       return faultMessage;
    }
}
    