package com.servicelive.wallet.creditcard;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ResultConvertor implements Converter{   
	  public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context){   
	    Result result = (Result)object;   
	    writer.addAttribute("code", result.getCode());   
	    writer.setValue(result.getMessage());   
	  }   
	  
	  @SuppressWarnings("unchecked")
	public boolean canConvert(Class theClass){   
	    return theClass.equals(Result.class);   
	  }   
	  
	  //you don't need to implement this method   
	  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context){   
	     Result result = new Result();
	    // reader.moveDown();
	     result.setMessage(reader.getValue());
	     result.setCode(reader.getAttribute("code"));
	     //reader.moveUp();
	     return result;
	  }   
	}  
