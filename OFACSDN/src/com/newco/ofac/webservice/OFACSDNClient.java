package com.newco.ofac.webservice;

public class OFACSDNClient {


    public boolean isSDNAndBlockedPersons(String resourceName) throws Exception {
        com.newco.ofac.webservice.OFACSDNSoapStub binding;
        try {
            binding = (com.newco.ofac.webservice.OFACSDNSoapStub)
                          new com.newco.ofac.webservice.OFACSDNLocator().getOFACSDNSoap();
        }
        catch (javax.xml.rpc.ServiceException jre) {
            if(jre.getLinkedCause()!=null)
                jre.getLinkedCause().printStackTrace();
            
            throw jre;
            
        }

        // Time out after a minute
        binding.setTimeout(60000);


		com.newco.ofac.webservice.holders.SDNListHolder listHolder = new com.newco.ofac.webservice.holders.SDNListHolder();

		javax.xml.rpc.holders.BooleanHolder bl = new javax.xml.rpc.holders.BooleanHolder();
		binding.isSDNAndBlockedPersons(resourceName, bl, listHolder);

		
		
		if(listHolder.value.getTotalRecords()> 0)
		{
			//return true;
		}
		
		int j = listHolder.value.getSDNLists().length ;
		if (j > 0)
		{
			for( int i = 0;i < j; i++)
			{
				SDN objSDN = listHolder.value.getSDNLists()[i];
				String sdnName = objSDN.getNameOfSDN();
				String alternateSdnName = objSDN.getAlternateIdentityName();

				if (sdnName!= null && (sdnName.equalsIgnoreCase(resourceName)))
				{
					return true;
				}
				else if (alternateSdnName!= null && (alternateSdnName.equalsIgnoreCase(resourceName)))
				{
					return true;
				}

			}
		}

		return false;

    }

}
