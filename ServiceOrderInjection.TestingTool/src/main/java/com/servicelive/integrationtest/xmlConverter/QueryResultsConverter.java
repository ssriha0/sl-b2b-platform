package com.servicelive.integrationtest.xmlConverter;

import com.servicelive.integrationtest.domain.QueryResults;
import com.servicelive.integrationtest.domain.QueryRow;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class QueryResultsConverter implements Converter {

	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
        return clazz.equals(QueryResults.class);
	}

	public void marshal(Object value, HierarchicalStreamWriter writer,
            MarshallingContext context) {
		QueryResults queryResults = (QueryResults) value;
		writer.addAttribute("name", queryResults.getQueryName());
		
		for (QueryRow row : queryResults.getResults()) {
			context.convertAnother(row);
		}
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return null;
	}
	
}
