package com.servicelive.integrationtest.xmlConverter;

import com.servicelive.integrationtest.domain.QueryCell;
import com.servicelive.integrationtest.domain.QueryRow;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class QueryRowConverter implements Converter {
	
	@SuppressWarnings("unchecked")
	public boolean canConvert(Class clazz) {
        return clazz.equals(QueryRow.class);
	}

	public void marshal(Object value, HierarchicalStreamWriter writer,
            MarshallingContext context) {
		QueryRow queryRow = (QueryRow) value;
		writer.startNode("row");
		for (QueryCell cell : queryRow.getResultCells()) {
			context.convertAnother(cell);
		}
		writer.endNode();
	}

	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		return null;
	}
}
