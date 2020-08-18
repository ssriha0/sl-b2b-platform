package com.servicelive.spn.common.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import junit.framework.TestCase;

/**
 * Test cases for the <code>SortUtil</code> class. 
 * It's responsible for testing lists.
 * 
 */
public class SortUtilTest extends TestCase
{

    /**
     *
     */
    public void testSort() {

		StackOfInts stackOne = new StackOfInts(15);
		StackOfInts stackTwo = new StackOfInts(20);
		StackOfInts stackThree = new StackOfInts(5);
		StackOfInts stackFour = new StackOfInts(1);

		//populate stackOne
		for (int j = 0 ; j < 15 ; j++) {
			stackOne.push(j);
		}

		//populate stackTwo
		for (int j = 0 ; j < 20 ; j++) {
			stackTwo.push(2*j);
		}

		for (int j = 0 ; j < 5 ; j++) {
			stackThree.push(3*j);
		}

		for (int j = 0 ; j < 1 ; j++) {
			stackFour.push(4*j);
		}

		StackOfInts[] data = {stackOne, stackTwo, stackThree, stackFour};
		List<StackOfInts> _rset = Arrays.asList(data);

		Collection<StackOfInts> _result = new SortUtil<StackOfInts>().sort(_rset, new String[]{"getStackSize"});
		_rset = new ArrayList<StackOfInts>(_result);

		StackOfInts stack = _rset.get(0);
		assertTrue(stack.getStackSize() == 1);

        stack = _rset.get(1);
        assertTrue(stack.getStackSize() == 5);

		stack = _rset.get(2);
		assertTrue(stack.getStackSize() == 15);

		stack = _rset.get(3);
		assertTrue(stack.getStackSize() == 20);
    }

    private void checkListToArray(List<?> sortedList, Object[] srcArray, int[] validSequence) {

        if (sortedList.size() != srcArray.length) {
            fail("List size " + sortedList.size() + " does not match src array size " + srcArray.length);
        }

        for(int i = 0; i < sortedList.size(); i++) {
            assertSame("Objects different for index: " + i, srcArray[validSequence[i]], sortedList.get(i));
        }
    }

    /**
     * This test method tests handling of equalsIgnoreCase
     *
     */
    public void testEqualsIgnoreCase(){

        // Create mock object array to define sort needs //
        SortBean array[] = 
        {
                new SortBean("DD","bizarre","blank", null),
        		new SortBean("AA","later","not cool", null),
        		new SortBean("ddd","dabba","bunny", null),
        		new SortBean("aaa","later","neat", null),
                new SortBean("bbb","dabba","doo", null)
        };

        List<SortBean> _list = Arrays.asList(array);

        int order[];

        // ascending equals
        order =  new int[] {1,0,3,4,2};
        Collection<SortBean> _temp = new SortUtil<SortBean>().sort(_list, new String[] { "getD", "getA"} );
        _list = new ArrayList<SortBean>(_temp);
        checkListToArray(_list, array, order);

        // ascending equalsIgnoreCase
        order =  new int[] {1,3,4,0,2};
        _temp = new SortUtil<SortBean>().sort(_list, new String[] { "getA"}, new SortOption[] {SortOption.ASCENDING_IGNORE_CASE});
        _list = new ArrayList<SortBean>(_temp);
        checkListToArray(_list, array, order);

        // descending equals
        order =  new int[] {2,4,3,0,1};
        _temp = new SortUtil<SortBean>().sort(_list, new String[] { "getA"}, new SortOption[] {SortOption.DESCENDING});
        _list = new ArrayList<SortBean>(_temp);
        checkListToArray(_list, array, order);

        // descending equalsIgnoreCase
        order =  new int[] {2,0,4,3,1};
        _temp = new SortUtil<SortBean>().sort(_list, new String[] { "getA"}, new SortOption[] {SortOption.DESCENDING_IGNORE_CASE});
        _list = new ArrayList<SortBean>(_temp);
        checkListToArray(_list, array, order);
    }
    /**
     * This test method tests multi sorting of fields to ensure lists are sorted properly.
     *
     */
    public void testDateSort() {
        
        long now = new java.util.Date().getTime();

        // Create mock object array to define sort needs //
        SortBean array[] = 
        {
                new SortBean(null, null, null, new Date(now + 1)),
                new SortBean(null, null, null, new Date(now)),
                new SortBean(null, null, null, new Date(now + 2)),
                new SortBean(null, null, null, new Date(now + 3)),
                new SortBean(null, null, null, new Date(now + 4))
        };

        List<SortBean> _list = Arrays.asList(array);

        int order[];
        //ascending
        order =  new int[]{1,0,2,3,4};
        Collection<SortBean> _temp = new SortUtil<SortBean>().sort(_list, new String[] { "getD" } );
        _list = new ArrayList<SortBean>(_temp);
        checkListToArray(_list, array, order);

        //descending
        order =  new int[]{4, 3, 2, 0, 1};
        _temp = new SortUtil<SortBean>().sort(_list, new String[] { "getD" }, new SortOption[] {SortOption.DESCENDING});
        _list = new ArrayList<SortBean>(_temp);
        checkListToArray(_list, array, order);
    }

    /**
     * This test method tests multi sorting of fields to ensure lists are sorted properly.
     *
     */
    public void testMultiFieldSort() {
        int ascSortList[] =  {4, 3, 2, 0, 1};
        int descSortList[] =  {1, 0, 4, 2, 3};

        // Create mock object array to define sort needs //
        SortBean array[] = 
        {
                new SortBean("wow","later","neat", null),
                new SortBean("wow","later","not cool", null),
                new SortBean("aaba","dabba","doo", null),
                new SortBean("aaba","bizarre","blank", null),
                new SortBean("aa","dabba","bunny", null)
        };

        List<SortBean> _list = Arrays.asList(array);

        Collection<SortBean> _temp = new SortUtil<SortBean>().sort(_list, new String[] { "getA", "getB", "getC" } );
        _list = new ArrayList<SortBean>(_temp);

        checkListToArray(_list, array, ascSortList);

        _temp = new SortUtil<SortBean>().sort(_list, new String[] { "getB", "getA", "getC" }, new SortOption[] {SortOption.DESCENDING, SortOption.ASCENDING, SortOption.DESCENDING});
        _list = new ArrayList<SortBean>(_temp);

        checkListToArray(_list, array, descSortList);
    }

    /**
     * 
     * Document the testEnum method 
     *
     */
    public void testEnum() {
    	WrapperWithEnum array[] = {
    			new WrapperWithEnum(MyEnum.JUSTIN),
    			new WrapperWithEnum(MyEnum.BOB),
    			new WrapperWithEnum(MyEnum.TONY),
    			new WrapperWithEnum(MyEnum.STEVE),
    			new WrapperWithEnum(null)
    	};
    	
    	Collection<WrapperWithEnum> sortedColl = new SortUtil<WrapperWithEnum>().sort(Arrays.asList(array), new String[]{"getEnum.getId"});
    	List<WrapperWithEnum> sortedList = new ArrayList<WrapperWithEnum>(sortedColl);
    	int ascSortList[] = {1,0,3,2,4};
    	checkListToArray(sortedList, array, ascSortList);
    }


    private class WrapperWithEnum {

    	/**
    	 * 
    	 * Constructs a new <code>WrapperWithEnum</code> object. 
    	 *
    	 * @param e
    	 */
    	public WrapperWithEnum(MyEnum e) {
    		this._enum = e;
    	}

    	private MyEnum _enum;

		/**
		 * Returns the enum.
		 *
		 * @return the enum.
		 */
		public MyEnum getEnum() {
			return _enum;
		}

		/**
		 * Sets the enum.
		 *
		 * @param enum1 The new value for enum.
		 */
		public void setEnum(MyEnum enum1) {
			_enum = enum1;
		}
    	@Override
		public String toString() {
    		return _enum.toString();
    	}
    }


    private class StackOfInts {

    	private int[] stack;
    	private int next = 0;  // index of last item in stack + 1

    	/**
    	 * @param size
    	 */
    	public StackOfInts(int size) {
    		//create an array large enough to hold the stack
    		stack = new int[size];
    	}

    	/**
    	 * @param on
    	 */
    	public void push(int on) {
    		if (next < stack.length) {
    		   stack[next++] = on;
    		}
    	}

    	/**
    	 * @return true if empty
    	 */
    	public boolean isEmpty() {
    		return next == 0;
    	}

    	/**
    	 * @return the value from the top of the stack
    	 */
    	public int pop(){
    		if (isEmpty() == false) {
    		   return stack[--next]; // top item on stack
    		}
    		return 0;
    	}

    	/**
    	 * @return the stack size
    	 */
    	public int getStackSize() {
    		return next;
    	}
    }
}

/**
 * 
 * Document the SortUtilTest class 
 *
 * @author svanloon
 */
enum MyEnum {
	/** */STEVE(3), /** */TONY(4), /** */BOB(1), /** */JUSTIN(2);
	private int id;
	private MyEnum(int id) {
		this.id = id;
	}
	/**
	 * Returns the id.
	 *
	 * @return the id.
	 */
	public int getId() {
		return id;
	}
}