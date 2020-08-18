package com.newco.marketplace.web.utils;

import java.util.Comparator;

public class TreeMapSortingComparator implements Comparator{
	
	public int compare(Object obj1,Object obj2){
		Integer val1 = Integer.parseInt((String)obj1);
		Integer val2 = Integer.parseInt((String)obj2);
		return val1.compareTo(val2);
	}
}
