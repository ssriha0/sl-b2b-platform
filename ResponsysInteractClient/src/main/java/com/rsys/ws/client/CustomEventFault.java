
/**
 * CustomEventFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */

package com.rsys.ws.client;

public class CustomEventFault extends java.lang.Exception{
    
    private com.rsys.ws.fault.CustomEventFault faultMessage;

    
        public CustomEventFault() {
            super("CustomEventFault");
        }

        public CustomEventFault(java.lang.String s) {
           super(s);
        }

        public CustomEventFault(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public CustomEventFault(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.rsys.ws.fault.CustomEventFault msg){
       faultMessage = msg;
    }
    
    public com.rsys.ws.fault.CustomEventFault getFaultMessage(){
       return faultMessage;
    }
}
    