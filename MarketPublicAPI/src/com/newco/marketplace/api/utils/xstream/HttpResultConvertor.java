package com.newco.marketplace.api.utils.xstream;

import com.newco.marketplace.api.beans.HttpResult;
import com.newco.marketplace.api.beans.Result;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class HttpResultConvertor implements Converter {

	@Override
	public boolean canConvert(Class arg0) {
		return arg0.equals(HttpResult.class);  
	}

	@Override
	public void marshal(Object object, HierarchicalStreamWriter writer, MarshallingContext context) {
		HttpResult result = (HttpResult)object;   
	    writer.addAttribute("code", result.getCode());   
	    writer.setValue(result.getMessage());   

	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		HttpResult result = new HttpResult();
		 result.setMessage(reader.getValue());
		 result.setCode(reader.getAttribute("code"));
		 return result;
	}

}
