package com.servicelive.spn.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * Comparator that uses reflection to compare objects
 * given an array of method names to use for comparison.
 * @param <F> class to be compared
 */
public class ReflectingComparator<F> implements Comparator<F> {
	
	private final ReflectionUtil REFLECTION_UTIL = new ReflectionUtil();

	private String[] methodNameList;

	private SortOption[] sortOption;
	
	static final int EQUALS = 0;

	static final int GREATER_THAN = 1;
	
	static final int LESS_THAN = -1;

	/**
	 * Construct a new ReflectingComparator.
	 * @param methodNameList method names to use for comparision
	 */
	public ReflectingComparator(String[] methodNameList) {
		this.methodNameList = methodNameList;
		this.sortOption = null;
	}

	/**
	 * Construct a new ReflectingComparator.
	 * @param methodNameList method names to use for comparision
	 * @param sortOption direction of sort (e.g. ascending)
	 */
	public ReflectingComparator(String[] methodNameList, SortOption[] sortOption) {
		this.methodNameList = methodNameList;
		this.sortOption = sortOption;
	}

	/**
	 * @return compares the objects and returns which is greater than or less than the other.
	 */
	public int compare(F object1, F object2) {
		return sortObject(object1, object2, 0);
	}

	/**
	 * 
	 * @param result is the compare to value
	 * @param methodNumber is the index of the methods being compared
	 * @return returns the result if the sort option is ascending, else returns the negative result.
	 * If no sort option is available, returns what ever was passed in.
	 */
	private int modifyResult(int result, int methodNumber) {
		if (this.sortOption == null) {
			return result;
		}
		return result * this.sortOption[methodNumber].getSortOptionValue();
	}

	/**
	 * 
	 * @param result
	 * @param methodNumber
	 * @return returns the uppercase version of a string or the object passed in.
	 */
	private Object handleIgnoreCase(Object result, int methodNumber) {
		if (this.sortOption == null) {
			return result;
		}
		if (this.sortOption[methodNumber].getIgnoreCase() == false) {
			return result;
		}
		if (result instanceof String == false) {
			return result;
		}
		return ((String) result).toUpperCase();
	}

    /**
     * 
     * @param object1
     * @param object2
     * @param methodNumber is the index of method to run on the objects.
     * @return the comparision of object1 and object2 for the method with index methodNumber.
     */
	private int sortObject(F object1, F object2, int methodNumber) {

		// Check if we have exhausted our methods to check
		if (methodNumber > this.methodNameList.length) {
			return 0;
		}

		// do checks for null
		if (object1 == null || object2 == null) {
			if (object1 == null && object2 == null) {
				return EQUALS;
			} else if (object1 == null) {
				return modifyResult(GREATER_THAN, methodNumber);
			} else {
				return modifyResult(LESS_THAN, methodNumber);
			}
		}

		// reflectively looking into the objects for comparision of the result of the methods
		// e.g. supports methods that look like getA().getB().getC()
		String methodNames = this.methodNameList[methodNumber];
		StringTokenizer st = new StringTokenizer(methodNames, ".");
		Object methodResult1 = object1;
		Object methodResult2 = object2;
		while (st.hasMoreTokens()) {
			String methodName = st.nextToken();
			methodResult1 = REFLECTION_UTIL.invokeMethod(methodResult1,	methodName);
			methodResult2 = REFLECTION_UTIL.invokeMethod(methodResult2,	methodName);
			if (methodResult1 == null || methodResult2 == null) {
				break;
			}
			methodResult1 = handleIgnoreCase(methodResult1, methodNumber);
			methodResult2 = handleIgnoreCase(methodResult2, methodNumber);
		}

		// check for null conditions
		if (methodResult1 == null || methodResult2 == null) {
			if (methodResult1 == null && methodResult2 == null) {
				// look at the next method if the object is equal at this method index
				if (methodNumber + 1 < this.methodNameList.length) {
					// don't need to modify the result
					int result = sortObject(object1, object2, methodNumber + 1);
					return result;
				}
				return EQUALS;
			} else if (methodResult1 == null) {
				return modifyResult(GREATER_THAN, methodNumber);
			} else {
				return modifyResult(LESS_THAN, methodNumber);
			}
		}

		int compareInt;
		// java.sql.Date when reflectively calling compareTo bomb out, so this is a work around.
		if (methodResult1 instanceof java.sql.Date) {
			java.sql.Date date1 = (java.sql.Date) methodResult1;
			java.sql.Date date2 = (java.sql.Date) methodResult2;
			compareInt = date1.compareTo(date2);
		} else if(methodResult1 instanceof java.lang.Enum) { 
			compareInt = 0; 
		} else {
			compareInt = ((Integer) REFLECTION_UTIL.invokeMethod(methodResult1,	"compareTo", new Object[] { methodResult2 })).intValue();
		}

		// look at the next method if the object is equal at this method index
		if (compareInt == EQUALS && methodNumber + 1 < this.methodNameList.length) {
			// don't need to modify the result
			int result = sortObject(object1, object2, methodNumber + 1);
			return result;
		}

		// if the objects weren't equal at this method return the appropriate comparision.
		return modifyResult(compareInt, methodNumber);
	}

	/**
	 * 
	 * @author svanloon
	 *
	 */
	class ReflectionUtil {

		/**
		 * Invoke method of given object.
		 * @param obj
		 * @param methodName
		 * @return object returned from invoked method
		 */
		public Object invokeMethod(Object obj, String methodName) {
			return invokeMethod(obj, methodName, new Object[0]);
		}

		/**
		 * Invoke method of given object.
		 * @param obj
		 * @param methodName
		 * @param params
		 * @return object returned from invoked method
		 */
		public Object invokeMethod(Object obj, String methodName,
				Object[] params) {
			try {
				Class<?>[] types = new Class[params.length];
				int i = 0;
				for (Object param : params) {
					types[i] = param.getClass();
					i++;
				}
				Method method = obj.getClass().getMethod(methodName, types);
				return method.invoke(obj, params);
			} catch (NoSuchMethodException e) {
				throw new RuntimeException(e);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
