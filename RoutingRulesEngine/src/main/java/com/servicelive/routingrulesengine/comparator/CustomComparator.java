package com.servicelive.routingrulesengine.comparator;
import java.util.Comparator;
import java.util.HashMap;


public class CustomComparator implements Comparator<Object>{
	
	private String value;
	private String type;

	public CustomComparator(String value,String type) {
		super();
		this.value = value;
		this.type = type;
	}

	public int compare(Object object, Object compareObject) {
		if("DEFAULT".equalsIgnoreCase(type)){
			String objValue = ((String) ((HashMap) object).get(value)).toLowerCase();
			String compareObjValue = ((String) ((HashMap) compareObject).get(value)).toLowerCase();
			return objValue.compareTo(compareObjValue);
		}else if("INTEGER".equalsIgnoreCase(type)){
			Integer objValue = Integer.parseInt((String) ((HashMap) object).get(value));
			Integer compareObjValue = Integer.parseInt((String) ((HashMap) compareObject).get(value));
			return objValue.compareTo(compareObjValue);
		}else{
			return 0;
		}
	}

}
