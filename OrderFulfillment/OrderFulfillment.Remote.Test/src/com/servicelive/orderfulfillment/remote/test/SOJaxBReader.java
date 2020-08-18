package com.servicelive.orderfulfillment.remote.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SOElementCollection;

public class SOJaxBReader {
	
	public static SOElementCollection readServiceOrders(String fileName){
	    try {
			JAXBContext ctx = JAXBContext.newInstance(SOElement.class);
		    Unmarshaller unmarshaller = ctx.createUnmarshaller();
		    InputStream is = new FileInputStream(fileName);
		    SOElementCollection so = (SOElementCollection)unmarshaller.unmarshal(is);
		    return so;
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
