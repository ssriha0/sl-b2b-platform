
package com.newco.marketplace.utils;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Helper for admininstration
 *
 * @author <a href="mailto:taylor@apache.org">David Sean Taylor</a>
 * @author <a href="mailto:chris@bluesunrise.com">Chris Schaefer</a>
 * @version $Id: AdminUtil.java,v 1.4 2008/04/26 00:51:53 glacy Exp $
 */

 /**
 * Create Date:						Oct. 30, 2007
 * Modified for Covansys use by:	MTedder@covansys.com
 * Original source:
 * Code taken from package org.apache.jetspeed.administration.AdminUtil (jetspeed-api-2.1.2.jar);
 */
public class AdminUtil {
	
	//Removed capital case letters and special characters as per SL-19828
    protected static final char[] PASS_CHARS = {'a', 'b', 'c', 'd', 'e',
    	'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm','n', 'o', 'p', 'q', 'r',
    	's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    
    protected static final char[] PASS_NUMBERS = {'1', '2', '3', '4', '5',
    	'6', '7', '8', '9', '0'};

    public static String generatePassword(){
    	// Changing the length of the password to 8 characters as per SL-19828
    	
    	/*Create a random String[4]*/
    	RandomStrg rs = new RandomStrg();
		rs.generateRandomObject();
		rs.setLength(new Integer(4));		
		rs.setSingle(PASS_CHARS, PASS_CHARS.length);
		ArrayList upper = new ArrayList();
		ArrayList lower = new ArrayList();
		rs.setRanges(upper, lower);
		String retval = rs.getRandom();
		
		/*Create a random Number[4]*/
		RandomStrg rsNum = new RandomStrg();
		rsNum.generateRandomObject();
		rsNum.setLength(new Integer(4));
		rsNum.setSingle(PASS_NUMBERS, PASS_NUMBERS.length);
		ArrayList upperNum = new ArrayList();
		ArrayList lowerNum = new ArrayList();
		rsNum.setRanges(upperNum, lowerNum);
		String retvalNum = rsNum.getRandom();
		String pass = retval.concat(retvalNum);
		
		return pass;
    }


}//

/**
 * RandomStrg class will produce a variable set of random characters.
 * 
 * @author Rich Catlett
 * 
 * @version 1.0
 * 
 */
/**
 * Create Date:						Oct. 30, 2007
 * Modified for Covansys use by:	MTedder@covansys.com
 * Original source:
 * Code taken from package package org.apache.taglibs.random.RandomStrg: jakarta-taglibs-src-20060823.zip
*/

class RandomStrg {

    /**
     * generated password
     */
    private String randomstr;
    /**
     * flag determines if all chars ar to be used
     */
    private boolean allchars = false;
    /**
     * length of random string defaults to 8
     */
    private Integer length = new Integer(8);
    /**
     * list of all generated strings, list is stored at the application level
     */
    private HashMap hmap;
    /**
     * ArrayList for the lowerbound of the char sets
     */
    private ArrayList lower = null;
    /**
     * ArrayList for the upperbound of the char sets
     */
    private ArrayList upper = null;
    /**
     * Array for the set of chars that aren't part of a range
     */
    private char[] single = null;
    /**
     * counter for position in the array single
     */
    private int singlecount = 0;
    /**
     * boolean flat that tells the random char generator if there is a list of
     * single chars
     */
    private boolean singles = false;
    /**
     * the algorithm to use for a SecureRandom object
     */
    private String algorithm = null;
    /**
     * the provider package to check for the algorithm
     */
    private String provider = null;
    /**
     * boolean value that marks if a Random or SecureRandom object is to be used,
     * default value of false says that a Random object will be used
     */
    private boolean secure = false;
    /**
     * random object that could be used
     */
    private Random random = null;
    /**
     * SecureRandom object that could be used
     */
    private SecureRandom secrandom = null;

    /**
     * nethod determines if a Random or SecureRandom object is to be used to
     * generate the random number
     *
     */
    private final float getFloat() {
	if (random == null)
	    return secrandom.nextFloat();
	else
	    return random.nextFloat();
    }

    /**
     * generate the Random object that will be used for this random number
     * generator
     *
     */
//Comment MTedder@covansys.com    public final void generateRandomObject() throws JspException {
 	public final void generateRandomObject() {

	// check to see if the object is a SecureRandom object
	if (secure) {
	    try {
		// get an instance of a SecureRandom object
		if (provider != null)
		    // search for algorithm in package provider
		    random = SecureRandom.getInstance(algorithm, provider);
		else
		    random = SecureRandom.getInstance(algorithm);
	    } catch (NoSuchAlgorithmException ne) {
		//Comment MTedder@covansys.com throw new JspException(ne.getMessage());
	    } catch (NoSuchProviderException pe) {
		//Comment MTedder@covansys.com throw new JspException(pe.getMessage());
	    }
	} else
	    random = new Random();
    }

    /**
     * generate the random string
     *
     */
    private final void generaterandom() {

	// use all chars in the string
	if (allchars)
	    for (int i = 0; i < length.intValue(); i++)
		randomstr = randomstr + new Character((char)((int) 34 +
				     ((int)(getFloat() * 93)))).toString();
	else if (singles) {
	    // check if there are single chars to be included

	    if (upper.size() == 3) {
		// check for the number of ranges max 3 uppercase lowercase digits

		// build the random string
		for (int i = 0; i < length.intValue(); i++) {
		    // you have four groups to choose a random number from, to make
		    // the choice a little more random select a number out of 100

		    // get a random number even or odd
		    if (((int) (getFloat() * 100)) % 2 == 0) {

			// the number was even get another number even or odd
			if (((int) (getFloat() * 100)) % 2 == 0)
			    // choose a random char from the single char group
			    randomstr = randomstr + randomSingle().toString();
			else
			    // get a random char from the first range
			    randomstr = randomstr + randomChar((Character)lower.get(2),
						(Character)upper.get(2)).toString();
		    } else {
			// the number was odd

			if (((int) (getFloat() * 100)) % 2 == 0)
			    // choose a random char from the second range
			    randomstr = randomstr + randomChar((Character)lower.get(1),
					       (Character)upper.get(1)).toString();
			else
			    // choose a random char from the third range
			    randomstr = randomstr + randomChar((Character)lower.get(0),
					       (Character)upper.get(0)).toString();
		    }
		}
	    } else if (upper.size() == 2) {
		// single chars are to be included choose a random char from
		// two different ranges

		// build the random char from single chars and two ranges
		for (int i = 0; i < length.intValue(); i++) {
		    // select the single chars or a range to get each random char
		    // from

		    if (((int)(getFloat() * 100)) % 2 == 0) {

			// get random char from the single chars
			randomstr = randomstr + randomSingle().toString();
		    } else if (((int) (getFloat() * 100)) % 2 == 0) {

			// get the random char from the first range
			randomstr = randomstr + randomChar((Character)lower.get(1),
					       (Character)upper.get(1)).toString();
		    } else {

			// get the random char from the second range
			randomstr = randomstr + randomChar((Character)lower.get(0),
					       (Character)upper.get(0)).toString();
		    }
		}
	    } else if (upper.size() == 1) {

		// build the random string from single chars and one range
		for (int i = 0; i < length.intValue(); i++) {
		    if (((int) getFloat() * 100) % 2 == 0)
			// get a random single char
			randomstr = randomstr + randomSingle().toString();
		    else
			// get a random char from the range
			randomstr = randomstr + randomChar((Character)lower.get(0),
					       (Character)upper.get(0)).toString();
		}
	    } else {
		// build the rand string from single chars
		for (int i = 0; i < length.intValue(); i++)
		    randomstr = randomstr + randomSingle().toString();
	    }
	} else {

	    // no single chars are to be included in the random string
	    if (upper.size() == 3) {

		// build random strng from three ranges
		for (int i = 0; i < length.intValue(); i++) {

		    if (((int) (getFloat() * 100)) % 2 == 0) {

			// get random char from first range
			randomstr = randomstr + randomChar((Character)lower.get(2),
					       (Character)upper.get(2)).toString();
		    } else if (((int) (getFloat() * 100)) % 2 == 0) {

			// get random char form second range
			randomstr = randomstr + randomChar((Character)lower.get(1),
					       (Character)upper.get(1)).toString();
		    } else {

			// get random char from third range
			randomstr = randomstr + randomChar((Character)lower.get(0),
					       (Character)upper.get(0)).toString();
		    }
		}
	    } else if (upper.size() == 2) {

		// build random string from two ranges
		for (int i = 0; i < length.intValue(); i++) {
		    if (((int) (getFloat() * 100)) % 2 == 0)
			// get random char from first range
			randomstr = randomstr + randomChar((Character)lower.get(1),
					       (Character)upper.get(1)).toString();
		    else
			// get random char from second range
			randomstr = randomstr + randomChar((Character)lower.get(0),
					     (Character)upper.get(0)).toString();
		}
	    } else

		// build random string
		for (int i = 0; i < length.intValue(); i++)
		    // get random char from only range
		    randomstr = randomstr + randomChar((Character)lower.get(0),
					       (Character)upper.get(0)).toString();
	}
    }

    /**
     * generate a random char from the single char list
     *
     * @returns - a randomly selscted character from the single char list
     *
     */
    private final Character randomSingle() {

	return (new Character(single[(int)((getFloat() * singlecount) - 1)]));
    }

    /**
     * generate a random character
     *
     * @param lower  lower bound from which to get a random char
     * @param upper  upper bound from which to get a random char
     *
     * @returns - a randomly generated character
     *
     */
    private final Character randomChar(Character lower, Character upper) {
	int tempval;
	char low = lower.charValue();
	char up = upper.charValue();

	// get a random number in the range lowlow - lowup
	tempval = (int)((int)low + (getFloat() * ((int)(up - low))));

	// return the random char
	return (new Character((char) tempval));
    }

    /**
     * get the randomly created string for use with the
     * &lt;jsp:getProperty name=<i>"id"</i> property="randomstr"/&gt;
     *
     * @return - randomly created string
     *
     */
    public final String getRandom() {

	randomstr = new String();

	generaterandom(); // generate the first random string

	if (hmap != null) {

	    while (hmap.containsKey(randomstr)) {
		// random string has already been created generate a different one
		generaterandom();
	    }

	    hmap.put(randomstr, null);  // add the new random string
	}

	return randomstr;
    }

    /**
     * set the ranges from which to choose the characters for the random string
     *
     * @param low  set of lower ranges
     * @param up  set of upper ranges
     *
     */
    public final void setRanges(ArrayList low, ArrayList up) {
	lower = low;
	upper = up;
    }


    /**
     * set the hashmap that is used to check the uniqueness of random strings
     *
     * @param map  hashmap whose keys are used to insure uniqueness of random strgs
     *
     */
    public final void setHmap(HashMap map) {
	hmap = map;
    }

    /**
     * set the length of the random string
     *
     * @param value  length of the random string
     *
     */
    public final void setLength(Integer value) {
	length = value;
    }

    /**
     * set the algorithm name
     *
     * @param value  name of the algorithm to use for a SecureRandom object
     *
     */
    public final void setAlgorithm(String value) {
	algorithm = value;
	secure = true;  // a SecureRandom object is to be used
    }

    /**
     * set the provider name
     *
     * @param value  name of the package to check for the algorithm
     *
     */
    public final void setProvider(String value) {
	provider = value;
    }

    /**
     * set the allchars flag
     *
     * @param value  boolean value of the allchars flag
     *
     */
    public final void setAllchars(boolean value) {
	allchars = value;
    }

    /**
     * set the array of single chars to choose from for this random string and the
     * number of chars in the array
     *
     * @param chars  the array of single chars
     * @param value  the number of single chars
     *
     */
    public final void setSingle(char[] chars, int value) {
	single = chars;  // set the array of chars
	singlecount = value;  // set the number of chars in array single
	singles = true;  // set flag that single chars are in use
    }
}
