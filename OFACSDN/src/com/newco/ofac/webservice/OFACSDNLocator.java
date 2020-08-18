/**
 * OFACSDNLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.newco.ofac.webservice;

public class OFACSDNLocator extends org.apache.axis.client.Service implements com.newco.ofac.webservice.OFACSDN {

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/**
 * This web services was created to aid banks in meeting the requirements
 * of the US Treasury Department's Office of Foreign Asset Control (OFAC).
 * OFAC restricts transactions with specific countries, organizations
 * and individuals.	The Office of Foreign Assets Control ('OFAC') of
 * the US Department of the Treasury administers and enforces economic
 * and trade sanctions based on US foreign policy and national security
 * goals against targeted foreign countries, terrorists, international
 * narcotics traffickers, and those engaged in activities related to
 * the proliferation of weapons of mass destruction. OFAC acts under
 * Presidential wartime and national emergency powers, as well as authority
 * granted by specific legislation, to impose controls on transactions
 * and freeze foreign assets under US jurisdiction. Many of the sanctions
 * are based on United Nations and other international mandates, are
 * multilateral in scope, and involve close cooperation with allied governments.
 */

    public OFACSDNLocator() {
    }


    public OFACSDNLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public OFACSDNLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for OFACSDNSoap
    private java.lang.String OFACSDNSoap_address = "http://www.webservicex.net/OFACSDN.asmx";

    public java.lang.String getOFACSDNSoapAddress() {
        return OFACSDNSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String OFACSDNSoapWSDDServiceName = "OFACSDNSoap";

    public java.lang.String getOFACSDNSoapWSDDServiceName() {
        return OFACSDNSoapWSDDServiceName;
    }

    public void setOFACSDNSoapWSDDServiceName(java.lang.String name) {
        OFACSDNSoapWSDDServiceName = name;
    }

    public com.newco.ofac.webservice.OFACSDNSoap getOFACSDNSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(OFACSDNSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getOFACSDNSoap(endpoint);
    }

    public com.newco.ofac.webservice.OFACSDNSoap getOFACSDNSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.newco.ofac.webservice.OFACSDNSoapStub _stub = new com.newco.ofac.webservice.OFACSDNSoapStub(portAddress, this);
            _stub.setPortName(getOFACSDNSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setOFACSDNSoapEndpointAddress(java.lang.String address) {
        OFACSDNSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @Override
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.newco.ofac.webservice.OFACSDNSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.newco.ofac.webservice.OFACSDNSoapStub _stub = new com.newco.ofac.webservice.OFACSDNSoapStub(new java.net.URL(OFACSDNSoap_address), this);
                _stub.setPortName(getOFACSDNSoapWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    @Override
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("OFACSDNSoap".equals(inputPortName)) {
            return getOFACSDNSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    @Override
	public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.webservicex.net/", "OFACSDN");
    }

    private java.util.HashSet ports = null;

    @Override
	public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://www.webservicex.net/", "OFACSDNSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("OFACSDNSoap".equals(portName)) {
            setOFACSDNSoapEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
