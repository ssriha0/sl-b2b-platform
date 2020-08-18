
/*

 * @(#)MoneyUtilsTest.java

 *

 * Copyright 2008 Sears Holdings Corporation, All rights reserved.

 * SHC PROPRIETARY/CONFIDENTIAL.

 */

package com.newco.marketplace.utils;

 

import java.text.ParseException;

import java.util.ArrayList;

import java.util.List;

 

import junit.framework.TestCase;

 

/**

 * @author GKL

 *

 */

public class MoneyUtilsTest extends TestCase {

 

      /**

       * Test method for {@link com.newco.marketplace.utils.TimeUtils#isPastXTime(java.sql.Timestamp, java.sql.Timestamp, long)}.

       * @throws ParseException

       */

      public void testSpreadFunds() {

           

            List<Double> testArray = null;

            List<Double> returnArray = null;

            Double sumTarget = null;

                 

            sumTarget = new Double(125.55);

           

            testArray = new ArrayList<Double>();

            testArray.add(new Double(25.00));

            testArray.add(new Double(25.00));

            testArray.add(new Double(25.00));

            testArray.add(new Double(25.00));

            returnArray = MoneyUtil.spreadFundsByOriginalRatio(testArray, sumTarget);

            assertEquals(true, getSumOfArray(returnArray).equals(sumTarget));

           

            sumTarget = new Double(65);

           

            testArray = new ArrayList<Double>();

            testArray.add(new Double(25.00));

            testArray.add(new Double(25.00));

            returnArray = MoneyUtil.spreadFundsByOriginalRatio(testArray, sumTarget);

            assertEquals(true, getSumOfArray(returnArray).equals(sumTarget));

           

           

            sumTarget = new Double(191.55);

           

            testArray = new ArrayList<Double>();

            testArray.add(new Double(25.99));

            testArray.add(new Double(15.09));

            testArray.add(new Double(17.31));

            testArray.add(new Double(64.51));

            returnArray = MoneyUtil.spreadFundsByOriginalRatio(testArray, sumTarget);

            assertEquals(true, getSumOfArray(returnArray).equals(sumTarget));

                 

      }

 

      private Double getSumOfArray(List<Double> returnArray) {

            Double sumDouble = new Double(0);

            for(Double aDouble:returnArray)

                  sumDouble+= aDouble;

           

            return sumDouble;

           

      }

 

}
