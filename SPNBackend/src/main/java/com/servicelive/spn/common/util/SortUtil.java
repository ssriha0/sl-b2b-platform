package com.servicelive.spn.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * This is a generic class for sorting object that are accessed through getMethods.
 * 
 * @author svanloon
 *
 * @param <E>
 */
public class SortUtil<E> {
	

	/**
	 *  This method allows one to sort by lastname, firstname, and middlename
	 *  
	 *  example:
	 *  
	 *  SortUtil<Person> sortUtil = new SortUtil<Person>();
	 *  Collection<Person> coll = ...;
	 *  coll = sortUtil.sort(coll, new String[]{"getLastName", "getFirstName", "getMiddleName"});
	 *  
	 * @param collectionToBeSorted
	 * @param methodNameList
	 * @return the sorted Collection
	 */
    public Collection<E> sort(Collection<E> collectionToBeSorted, String[] methodNameList) {
    	if (collectionToBeSorted == null) {
    		return null;
    	}

        List<E> arrayListToBeSorted = new ArrayList<E>(collectionToBeSorted);
        Collections.sort(arrayListToBeSorted, new ReflectingComparator<E>(methodNameList));
        return arrayListToBeSorted;
    }

    /**
	 *  This method allows one to sort by lastname, firstname, and middlename
	 *  
	 *  example:
	 *  
	 *  SortUtil<Person> sortUtil = new SortUtil<Person>();
	 *  Collection<Person> coll = ...;
	 *  coll = sortUtil.sort(coll, new String[]{"getLastName", "getFirstName", "getMiddleName"}, new SortOption[]{SortOption.ASCENDING, SortOption.ASCENDING, SortOption.ASCENDING});
	 *  
	 * @param collectionToBeSorted
	 * @param methodNameList
     * @param sortOption 
	 * @return the sorted Collection
	 */

    public Collection<E> sort(Collection<E> collectionToBeSorted, String[] methodNameList, SortOption[] sortOption) {
    	if (collectionToBeSorted == null) {
    		return null;
    	}

        List<E> arrayListToBeSorted = new ArrayList<E>(collectionToBeSorted);
        Collections.sort(arrayListToBeSorted, new ReflectingComparator<E>(methodNameList, sortOption));
        return arrayListToBeSorted;
    }
}