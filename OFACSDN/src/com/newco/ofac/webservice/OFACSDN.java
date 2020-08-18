/**
 * OFACSDN.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.newco.ofac.webservice;

public interface OFACSDN extends javax.xml.rpc.Service {

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
    public java.lang.String getOFACSDNSoapAddress();

    public OFACSDNSoap getOFACSDNSoap() throws javax.xml.rpc.ServiceException;

    public OFACSDNSoap getOFACSDNSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
