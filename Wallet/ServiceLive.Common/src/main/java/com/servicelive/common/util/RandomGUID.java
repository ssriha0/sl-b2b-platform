package com.servicelive.common.util;

// TODO: Auto-generated Javadoc
/**
 * The Class RandomGUID.
 */
@Deprecated
public class RandomGUID {

	/** The Constant LIMIT_INT. */
	public static final int LIMIT_INT = (int) Math.pow(2, 32); // MAX SIGNED 32 BIT NUMBER IS 2147483647 OR 2^31 - 1

	/**
	 * Find between million and hundred million.
	 * 
	 * @return the int
	 */
	private static int findBetweenMillionAndHundredMillion() {

		int randomNo = (int) (Math.random() * 10000000);
		if (randomNo < 1000000) {
			return findBetweenMillionAndHundredMillion();
		}
		if (randomNo == 10000000) {
			return findBetweenMillionAndHundredMillion();
		}

		return randomNo;
	}

	/**
	 * Find int between hundred and thousand.
	 * 
	 * @return the int
	 */
	private static int findIntBetweenHundredAndThousand() {

		int randomNo = (int) (Math.random() * 1000);
		if (randomNo < 100) {
			return findIntBetweenHundredAndThousand();
		}
		if (randomNo >= 214) {
			return findIntBetweenHundredAndThousand();
		}

		return randomNo;
	}

	/**
	 * Returns 32-bit signed number that's guaranteed to be 10-digits
	 * MAX SIGNED 32 BIT NUMBER IS 2147483647 OR 2^31 - 1.
	 * 
	 * @return 
	 */
	public static Long generateGUID() {

		StringBuffer id = new StringBuffer();
		int timestamp = findBetweenMillionAndHundredMillion();
		id.append(String.valueOf(timestamp));
		id.insert(0, findIntBetweenHundredAndThousand());

		return Long.valueOf(id.toString());
	}
}
