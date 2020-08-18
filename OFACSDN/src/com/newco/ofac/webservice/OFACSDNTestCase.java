/**
 * OFACSDNTestCase.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.newco.ofac.webservice;

public class OFACSDNTestCase extends junit.framework.TestCase {
    public OFACSDNTestCase(java.lang.String name) {
        super(name);     
    }

    public void testOFACSDNSoapWSDL() throws Exception {
        javax.xml.rpc.ServiceFactory serviceFactory = javax.xml.rpc.ServiceFactory.newInstance();
        java.net.URL url = new java.net.URL(new com.newco.ofac.webservice.OFACSDNLocator().getOFACSDNSoapAddress() + "?WSDL");
        //System.out.println(url.toExternalForm());
        javax.xml.rpc.Service service = serviceFactory.createService(url, new com.newco.ofac.webservice.OFACSDNLocator().getServiceName());
        //System.out.println(service.getServiceName());
        //System.out.println(service.getWSDLDocumentLocation());
        assertTrue(service != null); 
    }

    public void test1OFACSDNSoapIsSDNAndBlockedPersons() throws Exception {
        com.newco.ofac.webservice.OFACSDNSoapStub binding;
        try {
            binding = (com.newco.ofac.webservice.OFACSDNSoapStub)
                          new com.newco.ofac.webservice.OFACSDNLocator().getOFACSDNSoap();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            throw new junit.framework.AssertionFailedError("JAX-RPC ServiceException caught: " + jre);
        }
        assertNotNull("binding is null", binding);

        // Time out after a minute
        binding.setTimeout(60000);

        // Test operation
//        SDNList sdnList = new SDNList();
//        SDN sdn1 = new SDN();
//        sdn1.setNameOfSDN("Nasir");
//        SDN sdn2 = new SDN();
//        sdn2.setNameOfSDN("BUSHUSHA");
//        SDN[] sdnArr = new SDN[]{sdn1};
//        sdnList.setSDNLists(sdnArr );
		com.newco.ofac.webservice.holders.SDNListHolder listHolder = new com.newco.ofac.webservice.holders.SDNListHolder();
	//	java.lang.String name = "BOUCHOUCHA";
		javax.xml.rpc.holders.BooleanHolder bl = new javax.xml.rpc.holders.BooleanHolder();
		binding.isSDNAndBlockedPersons("AGUIAR Raul", bl, listHolder);
		assertNotNull(listHolder);
		System.out.println("Boolean returned is:" + bl.value);
		//System.out.println(listHolder.value.getTotalRecords());
		System.out.println(listHolder.value.getSDNLists().length);
		int j = listHolder.value.getSDNLists().length ;
		if (j > 1)
		{
			for( int i = 0;i < j; i++)
			{
				SDN objSDN = listHolder.value.getSDNLists()[i];
				System.out.println("Name of sdn is: " + objSDN.getNameOfSDN());
				//System.out.println(objSDN.getAlternateIdentityName());
				//System.out.println(objSDN.getAlternateIdentityName());
				//System.out.println(objSDN.getCity());
				//System.out.println(objSDN.getCountry());
				//System.out.println(objSDN.getStreetAddress());
				
			}
		}
		else if (j == 1)
		{
			SDN objSDN = listHolder.value.getSDNLists()[j-1];
			System.out.println("Name of sdn is: " + objSDN.getNameOfSDN());
			
		}
		else{
			System.out.println("No records found");
		}
        // TBD - validate results
    }

}
