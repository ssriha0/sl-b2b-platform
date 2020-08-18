package com.newco.marketplace.business.iBusiness.api.beans.leadsmanagement;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class FirmIdPriceConvertor implements Converter{   
	  public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context){   
		FirmIdPrice firmIdPrice = (FirmIdPrice)object;   
	    writer.addAttribute("Price", firmIdPrice.getPrice());   
	    writer.setValue(firmIdPrice.getId());   
	  }   
	  
	  @SuppressWarnings("unchecked")
	public boolean canConvert(Class theClass){   
	    return theClass.equals(FirmIdPrice.class);   
	  }   
	  
	  //you don't need to implement this method   
	  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context){   
		  FirmIdPrice firmIdPrice = new FirmIdPrice();
	    // reader.moveDown();
		firmIdPrice.setId(reader.getValue());
		firmIdPrice.setPrice(reader.getAttribute("Price"));
	     //reader.moveUp();
	     return firmIdPrice;
	  }   
	}  
