package com.servicelive.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * This class provides utility methods for monetary calculations for money type values.
 * 
 * @author Anand Wadhwani
 */
public final class MoneyUtil {

	/** Number of decimals to retain. Also referred to as "scale". */
	private static int DECIMALS = Currency.getInstance("USD").getDefaultFractionDigits();

	/** Constant for zero value in Double type. */
	public static final double DOUBLE_ZERO = 0.0d;

	/** Defined centrally, to allow for easy changes to the rounding mode. */
	private static int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;

	/**
	 * Returns the rounded sum of given two decimal numbers.
	 * 
	 * @param doubleNumber1 
	 * @param doubleNumber2 
	 * 
	 * @return Double rounded sum of given two decimal numbers
	 */
	public static Double add(Double doubleNumber1, Double doubleNumber2) {

		Double arg1 = doubleNumber1;
		if (arg1 == null) {
			arg1 = DOUBLE_ZERO;
		}
		Double arg2 = doubleNumber2;
		if (arg2 == null) {
			arg2 = DOUBLE_ZERO;
		}
		return MoneyUtil.getRoundedMoney(arg1 + arg2);
	}

	/**
	 * Takes two numbers and returns the proportion by dividing first number by another. The result is not rounded!
	 * 
	 * @param doubleNumber1 
	 * @param doubleNumber2 
	 * 
	 * @return The proportion rounded to four decimal digits
	 */
	public static Double getProportion(Double doubleNumber1, Double doubleNumber2) {

		if (doubleNumber1 == null || doubleNumber2 == null) {
			return null;
		}
		if (doubleNumber1 == DOUBLE_ZERO || doubleNumber2 == DOUBLE_ZERO) {
			return DOUBLE_ZERO;
		}
		BigDecimal bigDecimal = new BigDecimal(doubleNumber1 / doubleNumber2);
		return bigDecimal.doubleValue();
	}

	/**
	 * Takes two numbers and returns the proportion by dividing first number by another and returns the proportion rounded to given number of decimal digits.
	 * 
	 * @param doubleNumber1 
	 * @param doubleNumber2 
	 * @param roundUpToDecimalDigits 
	 * 
	 * @return The proportion rounded to four decimal digits
	 */
	public static Double getProportion(Double doubleNumber1, Double doubleNumber2, int roundUpToDecimalDigits) {

		if (doubleNumber1 == null || doubleNumber2 == null) {
			return null;
		}
		if (doubleNumber1 == DOUBLE_ZERO || doubleNumber2 == DOUBLE_ZERO) {
			return DOUBLE_ZERO;
		}
		BigDecimal bigDecimal = new BigDecimal(doubleNumber1 / doubleNumber2);
		bigDecimal = bigDecimal.setScale(roundUpToDecimalDigits, ROUNDING_MODE);
		return bigDecimal.doubleValue();
	}

	/**
	 * Takes any decimal number as input and returns a valid USD money with 2 decimals as scale and routed to half even (as done in banking).
	 * in all cases other than when the third digit after decinal is 5
	 * @param doubleNumber 
	 * 
	 * @return money with 2 decimals rounded
	 */
	public static Double getRoundedMoney(Double doubleNumber) {

		if (doubleNumber == null) {
			return null;
		}
		/*BigDecimal bigDecimal = new BigDecimal(doubleNumber);
		bigDecimal = bigDecimal.setScale(DECIMALS, ROUNDING_MODE);*/
		BigDecimal bigDecimal = new BigDecimal(doubleNumber);
		String numberAsString = Double.toString(doubleNumber);
		int decimalPos = numberAsString.indexOf(".");
		String afterDecimal = numberAsString.substring(decimalPos);
		if(afterDecimal.length()>=4 && afterDecimal.substring(3,4).equals("5")){
			bigDecimal = bigDecimal.setScale(DECIMALS, BigDecimal.ROUND_UP);
		}else{
			bigDecimal = bigDecimal.setScale(DECIMALS, BigDecimal.ROUND_HALF_EVEN);
		}		
		return bigDecimal.doubleValue();
	}
	
	/**
	 * Custom rounding function used to round down the service fee in cases where 
	 * third digit after decimal is 5
	 * 
	 * @param doubleNumber
	 * @return
	 */
	public static Double getRoundedMoneyCustom(Double doubleNumber) {
		if (doubleNumber == null) {
			return null;
		}
		BigDecimal bigDecimal = new BigDecimal(doubleNumber);
		String numberAsString = Double.toString(doubleNumber);
		int decimalPos = numberAsString.indexOf(".");
		String afterDecimal = numberAsString.substring(decimalPos);
		if(afterDecimal.length()>=4 && afterDecimal.substring(3,4).equals("5")){
			bigDecimal = bigDecimal.setScale(DECIMALS, BigDecimal.ROUND_DOWN);
		}else{
			bigDecimal = bigDecimal.setScale(DECIMALS, BigDecimal.ROUND_HALF_EVEN);
		}
		return bigDecimal.doubleValue();
	}
	
	/**
	 * Custom rounding function used to round down in cases where 
	 * third digit after decimal is 5, returns a BigDecimal
	 * 
	 * @param doubleNumber
	 * @return
	 */
	public static BigDecimal getRoundedMoneyCustomBigDecimal(Double doubleNumber) {
		if (doubleNumber == null) {
			return null;
		}
		BigDecimal bigDecimal = new BigDecimal(doubleNumber);
		String numberAsString = Double.toString(doubleNumber);
		int decimalPos = numberAsString.indexOf(".");
		String afterDecimal = numberAsString.substring(decimalPos);
		if(afterDecimal.length()>=4 && afterDecimal.substring(3,4).equals("5")){
			bigDecimal = bigDecimal.setScale(DECIMALS, BigDecimal.ROUND_DOWN);
		}else{
			bigDecimal = bigDecimal.setScale(DECIMALS, BigDecimal.ROUND_HALF_EVEN);
		}
		return bigDecimal;
	}
	
	/**
	 * Custom rounding function used to round up in cases where 
	 * third digit after decimal is 5, returns a BigDecimal
	 * 
	 * @param doubleNumber
	 * @return
	 */
	public static BigDecimal getRoundedMoneyBigDecimal(Double doubleNumber) {
		if (doubleNumber == null) {
			return null;
		}
		BigDecimal bigDecimal = new BigDecimal(doubleNumber);
		String numberAsString = Double.toString(doubleNumber);
		int decimalPos = numberAsString.indexOf(".");
		String afterDecimal = numberAsString.substring(decimalPos);
		if(afterDecimal.length()>=4 && afterDecimal.substring(3,4).equals("5")){
			bigDecimal = bigDecimal.setScale(DECIMALS, BigDecimal.ROUND_UP);
		}else{
			bigDecimal = bigDecimal.setScale(DECIMALS, BigDecimal.ROUND_HALF_EVEN);
		}
		return bigDecimal;
	}
	/**
	 * **.
	 * 
	 * @param finalSum final sum will be roundded money
	 * @param originalFundsList 
	 * 
	 * @return finalFundsList where the sum of the final funds list = fnialSum
	 * 
	 * ratios of individual over totals are equal for origional and final lists
	 */

	public static List<Double> spreadFundsByOriginalRatio(List<Double> originalFundsList, Double finalSum) {

		// If the final sum is null, return the orig list
		if (finalSum == null) {
			return originalFundsList;
		}

		// Create a new final list
		List<Double> finalList = new ArrayList<Double>();
		Double originalSum = new Double(0);
		Double roundedFinalSum = getRoundedMoney(finalSum);

		// original sum may or may not be a 2 decimal number at this point.

		for (Double aDouble : originalFundsList) {

			if (aDouble != null) {
				originalSum += aDouble;
			}
		}

		// blast out the new distributed funds values, round them like money
		for (Double aDouble : originalFundsList) {

			if (aDouble != null) {
				Double newValue = getRoundedMoney(aDouble / originalSum * roundedFinalSum);
				finalList.add(newValue);
			} else {
				finalList.add(null);
			}
		}

		// test the sum this should be 2 decimal
		// as there should not be error in adding rounded numbers
		Double testFinalSum = new Double(0);

		for (Double aDouble : finalList) {
			if (aDouble != null) {
				testFinalSum += aDouble;
			}
		}

		// if the final sum is not equal to the roundedFinalSum we should distribute the load.

		if (testFinalSum.compareTo(roundedFinalSum) != 0) {

			// get the number of pennies...... test final sum should be 2 decimal.
			Double difference = testFinalSum - roundedFinalSum;

			// declare the number of pennies
			int iterations = new Double(difference / .01).intValue();

			Double internalTarget = null;

			for (int i = 0; i < Math.abs(iterations); i++) {

				internalTarget = finalList.get(i % finalList.size());

				if (iterations > 0) { // our new sum is more value than the target

					internalTarget = internalTarget - .01;

				} else { // our new sum is less than the target.

					internalTarget = internalTarget + .01;
				}
				finalList.set(i % finalList.size(), internalTarget);
			}

		}

		return finalList;
	}

	/**
	 * Returns the rounded difference of two given numbers.
	 * 
	 * @param doubleNumber1 
	 * @param doubleNumber2 
	 * 
	 * @return Double rounded difference of given two decimal numbers
	 */
	public static Double subtract(Double doubleNumber1, Double doubleNumber2) {

		Double arg1 = doubleNumber1;
		if (arg1 == null) {
			arg1 = DOUBLE_ZERO;
		}
		Double arg2 = doubleNumber2;
		if (arg2 == null) {
			arg2 = DOUBLE_ZERO;
		}
		return MoneyUtil.getRoundedMoney(arg1 - arg2);
	}

}
