/*
 * Created on Feb 20, 2007
 *
 * Author: dgold1
 * 
 * Revisions:
 * 
 */
package com.sears.os.utils;

import java.lang.reflect.Array;

public class ArrayUtils extends org.apache.commons.lang.ArrayUtils {
	
	public static String[] add(String[] array, String element) {
		 String[] newArray = (String[])copyArrayGrow1(array, String.class);
		 newArray[newArray.length - 1] = element;
		 return newArray;
	 }

	private static Object copyArrayGrow1(Object array, Class newArrayComponentType) {
		 if (array != null) {
			 int arrayLength = Array.getLength(array);
			 Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
			 System.arraycopy(array, 0, newArray, 0, arrayLength);
			 return newArray;
		 }
		 return Array.newInstance(newArrayComponentType, 1);
	 }
}
