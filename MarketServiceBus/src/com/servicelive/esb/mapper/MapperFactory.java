package com.servicelive.esb.mapper;

public class MapperFactory {
	
	public static final String MAPPER_PACKAGE = "com.servicelive.esb.mapper";
	public static final String SERVICE_ORDER = "CreateDraft";
	public static final String ROUTE_ORDER = "RouteOrder";
	public static final String INCIDENTEVENT_MAPPER = "IncidentEvent";
	
	public static Mapper getInstance(String buyerKey, String mapperType) throws Exception {
		String clazz = MAPPER_PACKAGE + "." + buyerKey + mapperType + "Mapper";
		
		Mapper mapperObject = (Mapper) Class.forName(clazz).newInstance();
		
		return mapperObject;
	}
}
