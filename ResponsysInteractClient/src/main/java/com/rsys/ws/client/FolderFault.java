
/**
 * FolderFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */

package com.rsys.ws.client;

public class FolderFault extends java.lang.Exception{
    
    private com.rsys.ws.fault.FolderFault faultMessage;

    
        public FolderFault() {
            super("FolderFault");
        }

        public FolderFault(java.lang.String s) {
           super(s);
        }

        public FolderFault(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public FolderFault(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.rsys.ws.fault.FolderFault msg){
       faultMessage = msg;
    }
    
    public com.rsys.ws.fault.FolderFault getFaultMessage(){
       return faultMessage;
    }
}
    