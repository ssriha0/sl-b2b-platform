package com.newco.marketplace.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class CreditCardValidatonUtil {	

	private static String maskCreditCard(String sentenceThatMightContainACreditCard) {		
		String strippedCreditCard = null;		
		String replaceNumbersThatMightContainACreditCard= sentenceThatMightContainACreditCard.replaceAll("[^0-9,]", " ");
		strippedCreditCard=removeSpacesForCCNumber(replaceNumbersThatMightContainACreditCard);	    
		return strippedCreditCard;
	}

	private static String removeSpacesForCCNumber(String numbersThatMightContainACreditCard) {

		StringBuilder sb2 = new StringBuilder();
		List<String> listOfSeparatedNumbers = new ArrayList<String>(
				Arrays.asList(numbersThatMightContainACreditCard.split("\\  ")));
		listOfSeparatedNumbers.removeAll(Arrays.asList("", null));
		for (String s2 : listOfSeparatedNumbers) {
			StringBuilder s = new StringBuilder(s2.trim());
			int count = 0;
			if (s.length() >= 13 && s.length() <= 19) {
				for (int i = 0; i < s.length(); i++) {
					if ((s.charAt(i) == ' ') && (i == 4 || i == 8 || i == 12)) {
						count++;
						s.deleteCharAt(i);
					}
				}

				if (count == 3)
					sb2.append(s.toString() + " ");
				else
					sb2.append(s2.toString().trim() + " ");

			}

		}

		return sb2.toString();
	}
	
	// credit card validations
	public static boolean validateCCNumbers(String numbersThatMightContainACreditCard) {
		if(numbersThatMightContainACreditCard == null || numbersThatMightContainACreditCard.isEmpty()) {
			return false;
		}		
		String numbersThatMightContainACC=maskCreditCard(numbersThatMightContainACreditCard);
		if(numbersThatMightContainACC.isEmpty())
			return false;
		boolean ccValidationFlag = false;
		String regex = "^[1-9][0-9]{12,16}$";
		StringTokenizer st = new StringTokenizer(numbersThatMightContainACC, " ");
		while (st.hasMoreTokens()) {
			String temp = st.nextToken();
			if (temp.matches(regex)) {
				if (checkCreditCardFormat(temp))
					ccValidationFlag = true;
			}
		}
		return ccValidationFlag;
	}
	
	private static boolean checkCreditCardFormat(String ccNumber) {
		int sum = 0;
		boolean alternate = false;
		for (int i = ccNumber.length() - 1; i >= 0; i--) {
			int n = Integer.parseInt(ccNumber.substring(i, i + 1));
		  //It will double every alternate digit. If the double is greater than 9, then add the both digits so that final number is of single digit
			if (alternate) {
				n *= 2;
				if (n > 9) {
					n = (n % 10) + 1;
				}
			}
		  //Now sum all the digits in the number, the unchanged numbers and the doubled numbers
			sum += n;
			alternate = !alternate;
		}
	   //The final sum should be multiple of 10 or mod 10 of the number should be 0. If it is not then its not a valid credit card number
		return (sum % 10 == 0);
	}
}
