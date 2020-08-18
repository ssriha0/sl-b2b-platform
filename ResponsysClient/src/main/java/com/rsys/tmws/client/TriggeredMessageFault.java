
/**
 * TriggeredMessageFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */

package com.rsys.tmws.client;

public class TriggeredMessageFault extends java.lang.Exception{
    
    private com.rsys.tmws.fault.TriggeredMessageFault faultMessage;

    
        public TriggeredMessageFault() {
            super("TriggeredMessageFault");
        }

        public TriggeredMessageFault(java.lang.String s) {
           super(s);
        }

        public TriggeredMessageFault(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public TriggeredMessageFault(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.rsys.tmws.fault.TriggeredMessageFault msg){
       faultMessage = msg;
    }
    
    public com.rsys.tmws.fault.TriggeredMessageFault getFaultMessage(){
       return faultMessage;
    }
}
    