
/**
 * RIFault.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.4.1  Built on : Aug 13, 2008 (05:03:35 LKT)
 */

package responsys.ws57.client;

public class RIFault extends java.lang.Exception{
    
    private responsys.ws57.fault.FaultException faultMessage;
    
    public RIFault() {
        super("RIFault");
    }
           
    public RIFault(java.lang.String s) {
       super(s);
    }
    
    public RIFault(java.lang.String s, java.lang.Throwable ex) {
      super(s, ex);
    }
    
    public void setFaultMessage(responsys.ws57.fault.FaultException msg){
       faultMessage = msg;
    }
    
    public responsys.ws57.fault.FaultException getFaultMessage(){
       return faultMessage;
    }
}
    