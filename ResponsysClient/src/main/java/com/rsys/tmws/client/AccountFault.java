
/**
 * AccountFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.5  Built on : Apr 30, 2009 (06:07:24 EDT)
 */

package com.rsys.tmws.client;

public class AccountFault extends java.lang.Exception{
    
    private com.rsys.tmws.fault.AccountFault faultMessage;

    
        public AccountFault() {
            super("AccountFault");
        }

        public AccountFault(java.lang.String s) {
           super(s);
        }

        public AccountFault(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public AccountFault(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(com.rsys.tmws.fault.AccountFault msg){
       faultMessage = msg;
    }
    
    public com.rsys.tmws.fault.AccountFault getFaultMessage(){
       return faultMessage;
    }
}
    