package com.newco.marketplace.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * This class provides utility methods for monetary calculations for money type values.
 * 
 * @author Anand Wadhwani
 * 
 */
public final class MoneyUtil {
	
	/**
	 * Defined centrally, to allow for easy changes to the rounding mode.
	 */
	private static int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
	
	/**
	 * Number of decimals to retain. Also referred to as "scale".
	 */
	private static int DECIMALS = Currency.getInstance("USD").getDefaultFractionDigits();
	
	/**
	 * Constant for zero value in Double type
	 */
	public static final double DOUBLE_ZERO = 0.0d;
	
	/**
	 * Takes any decimal number as input and returns a valid USD money with 2 decimals as scale and routed to half even (as done in banking)
	 * in all cases other than when the third digit after decinal is 5
	 * @param doubleNumber
	 * @return money with 2 decimals rounded
	 * 
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
	 * Takes two numbers and returns the proportion by dividing first number by another. The result is not rounded!
	 * 
	 * @param doubleNumber1
	 * @param doubleNumber2
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
	 * @param Double doubleNumber1
	 * @param Double doubleNumber2
	 * @param int roundUpToDecimalDigits -  The number of decimal digits the result should be rounded up to
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
	 * Returns the rounded sum of given two decimal numbers.
	 * 
	 * @param doubleNumber1
	 * @param doubleNumber2
	 * @return Double rounded sum of given two decimal numbers
	 */
	public static Double add(Double doubleNumber1, Double doubleNumber2) {
		if (doubleNumber1 == null) {
			doubleNumber1 = DOUBLE_ZERO;
		}
		if (doubleNumber2 == null) {
			doubleNumber2 = DOUBLE_ZERO;
		}
		return MoneyUtil.getRoundedMoney(doubleNumber1 + doubleNumber2);
	}
	
	/**
	 * Returns the rounded difference of two given numbers.
	 * 
	 * @param doubleNumber1
	 * @param doubleNumber2
	 * @return Double rounded difference of given two decimal numbers
	 */
	public static Double subtract(Double doubleNumber1, Double doubleNumber2) {
		if (doubleNumber1 == null) {
			doubleNumber1 = DOUBLE_ZERO;
		}
		if (doubleNumber2 == null) {
			doubleNumber2 = DOUBLE_ZERO;
		}
		return MoneyUtil.getRoundedMoney(doubleNumber1 - doubleNumber2);
	}
	
    /****

    *

    * @param origionalFundsList

    * @param finalSum

    *          final sum will be roundded money

    * @return finalFundsList where the sum of the final funds list = fnialSum

    *             ratios of individual over totals are equal for origional and final lists

    */

  

	   public static List<Double> spreadFundsByOriginalRatio(List<Double>originalFundsList , Double finalSum){

		   //  If the final sum is null, return the orig list
	         if(finalSum == null){
	               return originalFundsList;
	         }

	         // Create a new final list
	         List<Double> finalList = new ArrayList<Double>();
	         Double originalSum = new Double(0);
	         Double roundedFinalSum = getRoundedMoney(finalSum);
















	         // original sum may or may not be a 2 decimal number at this point.


	         for(Double aDouble : originalFundsList){








	               if(aDouble!= null){
	                     originalSum += aDouble;                       
	               }
	         }

	         // blast out the new distributed funds values, round them like money
	         for(Double aDouble : originalFundsList){



	               if(aDouble!= null){
	                     Double newValue = getRoundedMoney(aDouble/originalSum * roundedFinalSum);
	                     finalList.add(newValue);
	               }
	               else{
	                     finalList.add(null);
	               }
	         }

	         // test the sum  this should be 2 decimal
	         // as there should not be error in adding rounded numbers
	         Double testFinalSum = new Double(0);






	         for(Double aDouble : finalList){
	               if(aDouble!= null){
	                     testFinalSum += aDouble;
	               }
	         }

	         // if the final sum is not equal to the roundedFinalSum we should distribute the load.

	         if(testFinalSum.compareTo(roundedFinalSum) !=0 ){

	               // get the number of pennies...... test final sum should be 2 decimal.
	               Double difference = testFinalSum - roundedFinalSum;
	               

	               // declare the number of pennies
	               int iterations = new Double(difference/.01).intValue();

	               Double internalTarget = null;

	               for (int i = 0; i < Math.abs(iterations); i++) {

	                     internalTarget = finalList.get(i%finalList.size());

	                     if(iterations > 0){ // our new sum is more value than the target

	                           internalTarget =  internalTarget - .01;

	                     }else{ // our new sum is less than the target.

	                           internalTarget =  internalTarget + .01;                                
	                     }
	                     finalList.set(i%finalList.size(), internalTarget);
	               }
	               
	               
	         }
	         
	         //printAdjustedFinalSum(finalList, finalSum);
	         
	         return finalList;
	   }
	   
	   static private void printAdjustedFinalSum(List<Double> adjPriceList, Double targetSum)
	   {
           Double adjFinalSum=0.0;
           for(Double price : adjPriceList)
           {
        	   adjFinalSum += price;
           }
           System.out.println("adjusted Final Sum:" + adjFinalSum);
           System.out.println("adjusted Final Sum(rounded):" + getRoundedMoney(adjFinalSum) +  " targetSum:" + targetSum);		   
	   }
	   
	   
		/**
		 * The following code formats a Double according to Locale.
		 * The format method accepts the Double as an argument and returns the formatted number in a String.
		 * 
		 * @param amount in double
		 * @param currentLocale e.g:  Locale.US, Locale.FR etc
		 * @return String converted amount
		 */
		public static String convertDoubleToCurrency(Double amount, Locale currentLocale) {
			NumberFormat numberFormatter;
			numberFormatter = NumberFormat.getNumberInstance(currentLocale);
			if (amount == null) {
				amount = DOUBLE_ZERO;
			}
			String amountFormatter=numberFormatter.format(amount);
			
			return amountFormatter;
			
		}
	   
	
}
