package com.newco.marketplace.api.beans;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ErrorConvertor implements Converter{   
	  public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context){   
		  ErrorResult error = (ErrorResult)object;   
	    writer.addAttribute("code", error.getCode());   
	    writer.setValue(error.getMessage());   
	  }   
	  
	  @SuppressWarnings("unchecked")
	public boolean canConvert(Class theClass){   
	    return theClass.equals(ErrorResult.class);   
	  }   
	  
	  //you don't need to implement this method   
	  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context){   
		     ErrorResult errorResult = new ErrorResult();
		    // reader.moveDown();
		     errorResult.setMessage(reader.getValue());
		     errorResult.setCode(reader.getAttribute("code"));
		     //reader.moveUp();
		     return errorResult;
		  }   
	}  
