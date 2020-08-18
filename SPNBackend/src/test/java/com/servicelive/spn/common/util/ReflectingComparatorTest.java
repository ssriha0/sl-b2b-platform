package com.servicelive.spn.common.util;


import java.sql.Date;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Test cases for the <code>ReflectingComparator</code> class.
 * It's responsible for individual comparisions.
 *
 * @author svanloon
 */
public class ReflectingComparatorTest extends TestCase {
    
	/**
     * This test method tests strings comparisions
     *
     */
    public void testStringCompare() {
    	SortBean object1 = new SortBean("a","b","c", null);
    	SortBean object2 = new SortBean("a","B","d", null);

    	int result;
    	ReflectingComparator<SortBean> comp;
    	
    	// test ascending
    	comp = new ReflectingComparator<SortBean>(new String[]{"getA"},new SortOption[]{SortOption.ASCENDING});
    	result = comp.compare(object1, object2);
    	Assert.assertEquals(ReflectingComparator.EQUALS, result);

    	// test ignore case
    	comp = new ReflectingComparator<SortBean>(new String[]{"getB"},new SortOption[]{SortOption.ASCENDING});
    	result = comp.compare(object1, object2);
    	Assert.assertTrue(ReflectingComparator.EQUALS < result);

    	// test ignore case
    	comp = new ReflectingComparator<SortBean>(new String[]{"getB"},new SortOption[]{SortOption.ASCENDING_IGNORE_CASE});
    	result = comp.compare(object1, object2);
    	Assert.assertTrue(ReflectingComparator.EQUALS == result);

    	comp = new ReflectingComparator<SortBean>(new String[]{"getC"},new SortOption[]{SortOption.ASCENDING});
    	result = comp.compare(object1, object2);
    	Assert.assertTrue(ReflectingComparator.EQUALS > result);


    	// test descending
    	comp = new ReflectingComparator<SortBean>(new String[]{"getA"},new SortOption[]{SortOption.DESCENDING});
    	result = comp.compare(object1, object2);
    	Assert.assertEquals(ReflectingComparator.EQUALS, result);

    	// test ignore case
    	comp = new ReflectingComparator<SortBean>(new String[]{"getB"},new SortOption[]{SortOption.DESCENDING});
    	result = comp.compare(object1, object2);
    	Assert.assertTrue(ReflectingComparator.EQUALS > result);

    	// test ignore case
    	comp = new ReflectingComparator<SortBean>(new String[]{"getB"},new SortOption[]{SortOption.DESCENDING_IGNORE_CASE});
    	result = comp.compare(object1, object2);
    	Assert.assertTrue(ReflectingComparator.EQUALS == result);

    	comp = new ReflectingComparator<SortBean>(new String[]{"getC"},new SortOption[]{SortOption.DESCENDING});
    	result = comp.compare(object1, object2);
    	Assert.assertTrue(ReflectingComparator.EQUALS < result);
    }

	/**
     * This test method tests what happens when values are null
     *
     */
    public void testNullCompare() {
    	SortBean object1 = new SortBean(null,"b",null, null);
    	SortBean object2 = new SortBean("a",null,null, null);

    	int result;
    	ReflectingComparator<SortBean> comp;

    	comp = new ReflectingComparator<SortBean>(new String[]{"getA"},new SortOption[]{SortOption.DESCENDING});
    	result = comp.compare(object1, object2);
    	Assert.assertTrue(ReflectingComparator.EQUALS > result);

    	comp = new ReflectingComparator<SortBean>(new String[]{"getA"},new SortOption[]{SortOption.ASCENDING});
    	result = comp.compare(object1, object2);
    	Assert.assertTrue(ReflectingComparator.EQUALS < result);

    	comp = new ReflectingComparator<SortBean>(new String[]{"getB"},new SortOption[]{SortOption.ASCENDING});
    	result = comp.compare(object1, object2);
    	Assert.assertTrue(ReflectingComparator.EQUALS > result);

    	comp = new ReflectingComparator<SortBean>(new String[]{"getB"},new SortOption[]{SortOption.DESCENDING});
    	result = comp.compare(object1, object2);
    	Assert.assertTrue(ReflectingComparator.EQUALS < result);

    	comp = new ReflectingComparator<SortBean>(new String[]{"getC"},new SortOption[]{SortOption.ASCENDING});
    	result = comp.compare(object1, object2);
    	Assert.assertEquals(ReflectingComparator.EQUALS, result);

    	comp = new ReflectingComparator<SortBean>(new String[]{"getC"},new SortOption[]{SortOption.DESCENDING});
    	result = comp.compare(object1, object2);
    	Assert.assertEquals(ReflectingComparator.EQUALS, result);
    }

    /**
     * 
     *
     */
    public void testDateCompare() {
    	long now = new java.util.Date().getTime();
    	SortBean object1 = new SortBean(null, null, null, new Date(now));
    	SortBean object2 = new SortBean(null, null, null, new Date(now + 5));
    	SortBean object3 = new SortBean(null, null, null, new Date(now));

    	int result;
    	ReflectingComparator<SortBean> comp;
    	
    	// equals
    	comp = new ReflectingComparator<SortBean>(new String[]{"getD"},new SortOption[]{SortOption.ASCENDING});
    	result = comp.compare(object1, object3);
    	Assert.assertEquals(ReflectingComparator.EQUALS, result);

    	comp = new ReflectingComparator<SortBean>(new String[]{"getD"},new SortOption[]{SortOption.DESCENDING});
    	result = comp.compare(object1, object3);
    	Assert.assertEquals(ReflectingComparator.EQUALS, result);

    	// ascending
    	comp = new ReflectingComparator<SortBean>(new String[]{"getD"},new SortOption[]{SortOption.ASCENDING});
    	result = comp.compare(object1, object2);
    	Assert.assertTrue(ReflectingComparator.EQUALS > result);

    	// descending
    	comp = new ReflectingComparator<SortBean>(new String[]{"getD"},new SortOption[]{SortOption.DESCENDING});
    	result = comp.compare(object1, object2);
    	Assert.assertTrue(ReflectingComparator.EQUALS < result);
    }
}