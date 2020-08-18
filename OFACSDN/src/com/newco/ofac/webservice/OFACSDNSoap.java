/**
 * OFACSDNSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.newco.ofac.webservice;

public interface OFACSDNSoap extends java.rmi.Remote {

    /**
     * This web services was created to aid banks in meeting the requirements
     * of the US Treasury Department's Office of Foreign Asset Control (OFAC).
     * OFAC restricts transactions with specific countries, organizations
     * and individuals.
     */
    public void isSDNAndBlockedPersons(java.lang.String names, javax.xml.rpc.holders.BooleanHolder isSDNAndBlockedPersonsResult, com.newco.ofac.webservice.holders.SDNListHolder SDNLists) throws java.rmi.RemoteException;
}
