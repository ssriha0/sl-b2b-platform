package com.newco.marketplace.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import junit.framework.TestCase;

public class RandomGUIDTest extends TestCase {
	private static final int LIMIT_INT = (int) Math.pow(2, 32) ; // MAX SIGNED 32 BIT NUMBER IS 2147483647 OR 2^31 - 1
	private RandomGUID guid = new RandomGUID();
	private static final int runs = 1000000;
	private static final boolean isPrinting = false;
	private static final Logger logger = Logger.getLogger(RandomGUIDTest.class);

	private Result generateResults(String methodName) throws Exception {
		Set<Long> set = new HashSet<Long>(runs);
		Map<Long, Integer> map = new HashMap<Long, Integer>(runs);
		map.put(Long.valueOf(0L), Integer.valueOf(0));
		for (int i = 0; i < runs; i++) {
			final Long rand2 = (Long) guid.getClass().getMethod(methodName).invoke(guid);//guid._generateGUID(); // note the _call

			if (isPrinting) {
				System.out.println("generateResults() " + rand2);
			}

			assertNotNull(rand2);
			assertTrue("Generated number ["+rand2+"] larger than "+LIMIT_INT, rand2 < LIMIT_INT);
			assertTrue(rand2.longValue() < Math.pow(10, 10));
			if(methodName.equals("generateGUID")) {
				assertTrue("Number too small for "+methodName+": "+rand2,rand2.longValue() > Math.pow(10, 9) - 1);
			} else {
				//_generateGUID
				assertTrue("Number too small for "+methodName+": "+rand2,rand2.longValue() > Math.pow(10, 8) - 1);
			}

			if (!set.add(rand2)) {
				final Integer k2 = map.get(rand2);
				if (k2 == null) {
					map.put(rand2, 1);
				} else {
					map.put(rand2, k2 + 1);
				}
			}
		}
		set = null;
		int count = 0;
		Set<Long> keySet = map.keySet();
		for (Long tempLong : keySet) {
			count += map.get(tempLong).intValue();
		}

		Result result = new Result();
		result.setCount(Integer.valueOf(count));
		result.setMap(map);
		return result;
	}

	public void test() {
		try {
			//Result m1 = generateResults("_generateGUID");
			Result m2 = generateResults("generateGUID");

			logger.debug("Runs: " + runs);
			//logger.debug("Old method duplicate list:" +m1.getMap());
			//logger.debug("Old method duplicate count: "+m1.getCount());
			logger.debug("New method duplicate list:" +m2.getMap());
			logger.debug("New method duplicate count: "+m2.getCount());
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	class Result {
		private Integer count;
		private Map<Long, Integer> map;
		/**
		 * @return the count
		 */
		public Integer getCount() {
			return count;
		}
		/**
		 * @param count the count to set
		 */
		public void setCount(Integer count) {
			this.count = count;
		}
		/**
		 * @return the map
		 */
		public Map<Long, Integer> getMap() {
			return map;
		}
		/**
		 * @param map the map to set
		 */
		public void setMap(Map<Long, Integer> map) {
			this.map = map;
		}
		
	}
}
