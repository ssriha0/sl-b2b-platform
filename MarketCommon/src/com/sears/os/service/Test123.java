/*
 * Created on Feb 20, 2007
 *
 * Author: dgold1
 * 
 * Revisions:
 * 
 */
package com.sears.os.service;

import java.util.ArrayList;
import java.util.List;

public class Test123 {

	public static void main(String[] args) {
		
		List list = new ArrayList();
		list.add("foo");
		list.add("fee");
		list.add("fii");
	
		String[] s = listToStringArray(list);
		
		for(int i = 0; i < s.length; i++) {
			System.out.println(s[i]);	
		}
	}
	
	public static String[] listToStringArray(List list) {
		
		String[] arr = new String[0];
		
		if(list == null || list.size() == 0)
			return arr;
				
		return (String[])list.toArray(arr);	
	}	
}
