
/**
 * DuplicateApiRequestFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */

package com.rsys.ws.client;

public class DuplicateApiRequestFault extends java.lang.Exception{
    
    private com.rsys.ws.fault.DuplicateApiRequestFault faultMessage;

    
        public DuplicateApiRequestFault() {
            super("DuplicateApiRequestFault");
        }

        public DuplicateApiRequestFault(java.lang.String s) {
           super(s);
        }

        public DuplicateApiRequestFault(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public DuplicateApiRequestFault(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.rsys.ws.fault.DuplicateApiRequestFault msg){
       faultMessage = msg;
    }
    
    public com.rsys.ws.fault.DuplicateApiRequestFault getFaultMessage(){
       return faultMessage;
    }
}
    