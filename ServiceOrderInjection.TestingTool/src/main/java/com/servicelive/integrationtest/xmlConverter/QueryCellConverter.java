package com.servicelive.integrationtest.xmlConverter;

import com.servicelive.integrationtest.domain.QueryCell;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class QueryCellConverter implements Converter {
	
	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
        return clazz.equals(QueryCell.class);
	}

	public void marshal(Object value, HierarchicalStreamWriter writer,
            MarshallingContext context) {
		QueryCell queryCell = (QueryCell) value;
		writer.startNode("result");
		writer.addAttribute("query", queryCell.getQueryName());
		writer.addAttribute("column", queryCell.getColumnName());
		writer.setValue(queryCell.getResult());
		writer.endNode();
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return null;
	}
}
