package com.newco.marketplace.search.types;


public enum ServiceTypes {
	
	Unknown("Unknown"),
	Delivery("Delivery"),
	Installation("Installation"),
	Repair("Repair"),
	Maintenance("Maintenance"),
	Training("Training"),
	Estimates("Estimates"),
	Service("Service");
	
	
	ServiceTypes(String value) {
		this.value = value;
	}
	
	private final String value;

		
	@Override
	public String toString() {
		return value;
	}
	
	public static ServiceTypes findType(String val) {
		if (val.equals(getValueInt(Delivery)) || val.equals(Delivery.toString())) {
			return Delivery;
		} else if (val.equals(Installation.toString())) {
			return Installation;
		} else if (val.equals(Repair.toString())) {
			return Repair;
		} else if (val.equals(Maintenance.toString())) {
			return Maintenance;
		} else if (val.equals(Training.toString())) {
			return Training;
		} else if (val.equals(Estimates.toString())) {
			return Estimates;
		} else if (val.equals(Service.toString())) {
			return Service;
		} 

		return Unknown;
	}
	
	private static String getValueInt(ServiceTypes val) {
		if (val == null) return null;
		
		return val.toString().substring(0, 3);
	}
	
	public String getValue() {
		return getValueInt(this);
	}
	

}
